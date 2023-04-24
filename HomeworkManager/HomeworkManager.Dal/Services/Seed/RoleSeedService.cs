using HomeworkManager.Dal.Constants;
using HomeworkManager.Dal.Services.Seed.Interfaces;
using Microsoft.AspNetCore.Identity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace HomeworkManager.Dal.Services.Seed
{
    public class RoleSeedService : IRoleSeedService
    {
        private readonly RoleManager<IdentityRole<int>> _roleManager;

        public RoleSeedService(RoleManager<IdentityRole<int>> roleManager)
        {
            _roleManager = roleManager;
        }

        public async Task SeedRoleAsync()
        {
            if (!(await _roleManager.RoleExistsAsync(Roles.Administrator)))
            {
                await _roleManager.CreateAsync(new IdentityRole<int> { Name  = Roles.Administrator });
            }

            if (!(await _roleManager.RoleExistsAsync(Roles.Student)))
            {
                await _roleManager.CreateAsync(new IdentityRole<int> { Name = Roles.Student });
            }

            if (!(await _roleManager.RoleExistsAsync(Roles.Teacher)))
            {
                await _roleManager.CreateAsync(new IdentityRole<int> { Name = Roles.Teacher });
            }
        }
    }
}
