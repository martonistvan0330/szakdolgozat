using System.ComponentModel.DataAnnotations;

namespace HomeworkManager.Web.Authentication.Models
{
    public class UserModel
    {
        [Required]
        public string UserName { get; set; }
        [Required]
        public string Password { get; set; }
        [Required]
        public string Email { get; set; }
    }
}
