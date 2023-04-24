using HomeworkManager.Dal.Constants;
using HomeworkManager.Dal.Entities;
using HomeworkManager.Web.Authentication.Models;
using Microsoft.AspNetCore.Identity;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Security.Cryptography;
using System.Text;

namespace HomeworkManager.Web.Authentication.Services
{
    public class JwtService
    {
        private const int EXPIRATION_MINUTES = 1;

        private readonly IConfiguration _configuration;
        private readonly UserManager<User> _userManager;

        public JwtService(IConfiguration configuration, UserManager<User> userManager)
        {
            _configuration = configuration;
            _userManager = userManager;
        }

        public async Task<AuthenticationResponse> CreateTokens(User user)
        {
            var expiration = DateTime.UtcNow.AddMinutes(EXPIRATION_MINUTES);

            var accessToken = CreateJwtAccessToken(
                await CreateClaims(user),
                CreateSigningCredentials(),
                expiration
            );

            var refreshToken = GenerateRefreshToken();

            await UpdateUserAsync(user, refreshToken);

            var tokenHandler = new JwtSecurityTokenHandler();

            return new AuthenticationResponse
            {
                AccessToken = tokenHandler.WriteToken(accessToken),
                RefreshToken = refreshToken,
                Expiration = expiration
            };
        }

        public async Task<AuthenticationResponse> RefreshTokens(string accessToken, string refreshToken)
        {
            var tokenValidationParameters = new TokenValidationParameters
            {
                ValidateAudience = false,
                ValidateIssuer = false,
                ValidateIssuerSigningKey = true,
                IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_configuration["JWT:Key"])),
                ValidateLifetime = false
            };

            var tokenHandler = new JwtSecurityTokenHandler();

            var principal = tokenHandler.ValidateToken(accessToken, tokenValidationParameters, out SecurityToken securityToken);

            if (securityToken is not JwtSecurityToken jwtSecurityToken || !jwtSecurityToken.Header.Alg.Equals(SecurityAlgorithms.HmacSha256, StringComparison.InvariantCultureIgnoreCase))
            {
                throw new Exception("Invalid access token");
            }

            var user = await _userManager.FindByNameAsync(principal.Identity.Name);

            if (!user.RefreshTokens.Select(rt => rt.Token).Contains(refreshToken))
            {
                throw new Exception("Invalid refresh token");
            }

            return await CreateTokens(user);
        }

        private JwtSecurityToken CreateJwtAccessToken(Claim[] claims, SigningCredentials credentials, DateTime expiration) =>
            new JwtSecurityToken(
                _configuration["Jwt:Issuer"],
                _configuration["Jwt:Audience"],
                claims,
                expires: expiration,
                signingCredentials: credentials
            );

        private async Task<Claim[]> CreateClaims(User user)
        {
            var claims = new List<Claim> {
                new Claim(JwtRegisteredClaimNames.Sub, _configuration["Jwt:Subject"]),
                new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString()),
                new Claim(JwtRegisteredClaimNames.Iat, DateTime.UtcNow.ToString()),
                new Claim(ClaimTypes.NameIdentifier, user.Id.ToString()),
                new Claim(ClaimTypes.Name, user.UserName),
                new Claim(ClaimTypes.Email, user.Email)
            };

            foreach (var role in (await _userManager.GetRolesAsync(user)))
            {
                claims.Add(new Claim(ClaimTypes.Role, role));
            }

            return claims.ToArray();
        }
            

        private SigningCredentials CreateSigningCredentials() =>
            new SigningCredentials(
                new SymmetricSecurityKey(
                    Encoding.UTF8.GetBytes(_configuration["Jwt:Key"])
                ),
                SecurityAlgorithms.HmacSha256
            );

        private string GenerateRefreshToken()
        {
            var randomNumber = new byte[64];
            using var rng = RandomNumberGenerator.Create();
            rng.GetBytes(randomNumber);
            return Convert.ToBase64String(randomNumber);
        }

        private async Task UpdateUserAsync(User user, string refreshToken)
        {
            user.RefreshTokens.Add(new RefreshToken { Token = refreshToken, UserId = user.Id});

            await _userManager.UpdateAsync(user);
        }
    }
}
