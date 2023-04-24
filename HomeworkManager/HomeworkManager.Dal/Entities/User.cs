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
        public ICollection<RefreshToken> RefreshTokens { get; set; } = new HashSet<RefreshToken>();
        public ICollection<Group> Groups { get; set; } = new HashSet<Group>();
        public ICollection<Group> CreatedGroups { get; set; } = new HashSet<Group>();
    }
}
