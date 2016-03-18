using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DWR_Client_Metro.Model
{
    class BuildEventList
    {
        public List<BuildEvent> buildEvents { get; set; } 

        public BuildEventList(List<BuildEvent> buildEvents) 
        {
            this.buildEvents = buildEvents;
        }

        public BuildEventList() 
        {

        }
    }
}
