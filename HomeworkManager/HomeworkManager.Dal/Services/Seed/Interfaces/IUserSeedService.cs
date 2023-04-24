using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace HomeworkManager.Dal.Services.Seed.Interfaces
{
    public interface IUserSeedService
    {
        Task SeedUserAsync();
    }
}
