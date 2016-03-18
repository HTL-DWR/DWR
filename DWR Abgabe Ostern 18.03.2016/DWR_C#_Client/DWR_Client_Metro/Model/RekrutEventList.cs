using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DWR_Client_Metro.Model
{
    class RekrutEventList
    {
        public List<RekrutEvent> rekrutEvents;

        public RekrutEventList(List<RekrutEvent> rekrutEvents) 
        {
            this.rekrutEvents = rekrutEvents;
        }

        public RekrutEventList() 
        { 
        
        }
    }
}
