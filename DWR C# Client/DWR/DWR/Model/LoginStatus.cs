using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DWR.Model
{
    public class LoginStatus
    {
        public String status { get; set; }
        public String sessionId { get; set; }
        public String username { get; set; }

        public LoginStatus() { 
        
        }

        public LoginStatus(String _status, String _sessionId, String _username) {
            this.status = _status;
            this.sessionId = _sessionId;
            this.username = _username;
        }
    }
}
