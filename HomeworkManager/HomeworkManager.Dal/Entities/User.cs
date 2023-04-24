using Microsoft.AspNetCore.Identity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace HomeworkManager.Dal.Entities
{
    public class User : IdentityUser<int>
    {
        public HashSet<RefreshToken> RefreshTokens { get; set; } = new();
    }
}
