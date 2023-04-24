using HomeworkManager.Dal;
using HomeworkManager.Dal.Constants;
using HomeworkManager.Dal.Entities;
using HomeworkManager.Dal.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace HomeworkManager.Web.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class GroupController : ControllerBase
    {
        private readonly ApplicationDbContext _context;
        private readonly UserManager<User> _userManager;

        public GroupController(ApplicationDbContext context, UserManager<User> userManager)
        {
            _context = context;
            _userManager = userManager;
        }

        [Authorize]
        [HttpGet]
        public async Task<ActionResult<IEnumerable<GroupHeader>>> GetAll()
        {
            var user = await _userManager.FindByNameAsync(User.Identity.Name);
            return await _context.Groups
                .Where(g => g.Users.Contains(user))
                .Select(g => new GroupHeader(g.Name, g.Description)
                {
                    Id = g.Id,
                    CoverUrl = g.CoverUrl
                }).ToListAsync();
        }

        [Authorize(Roles = Roles.Teacher)]
        [HttpPost]
        public async Task<ActionResult<int>> Create(NewGroup newGroup)
        {
            var user = await _userManager.FindByNameAsync(User.Identity.Name);

            var group = new Group(newGroup.Name, newGroup.Description);
            group.CreatorId = user.Id;
            group.Users.Add(user);

            _context.Groups.Add(group);
            await _context.SaveChangesAsync();
            
            return group.Id;
        }
    }
}
