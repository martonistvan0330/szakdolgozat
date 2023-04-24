using HomeworkManager.Dal.Constants;
using HomeworkManager.Dal.Entities;
using HomeworkManager.Web.Authentication.Models;
using HomeworkManager.Web.Authentication.Services;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Text;

namespace HomeworkManager.Web.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class AuthController : ControllerBase
    {
        private readonly UserManager<User> _userManager;
        private readonly JwtService _jwtService;

        public AuthController(
            UserManager<User> userManager,
            JwtService jwtService
        )
        {
            _userManager = userManager;
            _jwtService = jwtService;
        }

        [HttpPost("Register")]
        public async Task<ActionResult<UserModel>> PostUser(UserModel user)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var identityUser = new User() { UserName = user.UserName, Email = user.Email };

            var createResult = await _userManager.CreateAsync(
                identityUser,
                user.Password
            );

            var addToRoleResult = await _userManager.AddToRoleAsync(identityUser, Roles.Teacher);

            if (!createResult.Succeeded || !addToRoleResult.Succeeded)
            {
                return BadRequest("User could not be created: " +
                    string.Join(", ", createResult.Errors.Concat(addToRoleResult.Errors).Select(e => e.Description)));
            }

            user.Password = null;
            return Created("", user);
        }

        [HttpPost("BearerToken")]
        public async Task<ActionResult<AuthenticationResponse>> CreateBearerToken(AuthenticationRequest request)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest("Bad credentials");
            }

            var user = await _userManager.FindByNameAsync(request.UserName);

            if (user == null)
            {
                return BadRequest("Bad credentials");
            }

            var isPasswordValid = await _userManager.CheckPasswordAsync(user, request.Password);

            if (!isPasswordValid)
            {
                return BadRequest("Bad credentials");
            }

            var token = await _jwtService.CreateTokens(user);

            return Ok(token);
        }

        [HttpPost("Refresh")]
        public async Task<ActionResult<AuthenticationResponse>> Refresh(RefreshRequest tokens)
        {
            try
            {
                var token = await _jwtService.RefreshTokens(tokens.AccessToken, tokens.RefreshToken);
                return Ok(token);
            }
            catch (Exception ex)
            {
                return Unauthorized(ex.Message);
            }
        }
    }
}
