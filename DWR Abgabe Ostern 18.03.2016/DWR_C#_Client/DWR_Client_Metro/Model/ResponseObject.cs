using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DWR_Client_Metro.Model
{
    class ResponseObject
    {
        public Boolean ok { get; set; }
        public String errormsg { get; set; }
        public Object data { get; set; }

        public Boolean isOk() {
            return ok;
        }

        public void setOk(Boolean ok) {
            this.ok = ok;
        }

        public ResponseObject() { 
        }
    }
}
