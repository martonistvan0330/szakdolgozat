using System.ComponentModel.DataAnnotations;

namespace HomeworkManager.Web.Authentication.Models
{
    public class RefreshRequest
    {
        [Required]
        public string AccessToken { get; set; }
        [Required]
        public string RefreshToken { get; set; }
    }
}
