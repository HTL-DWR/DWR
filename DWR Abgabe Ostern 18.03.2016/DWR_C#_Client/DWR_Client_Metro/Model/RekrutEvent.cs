using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DWR_Client_Metro.Model
{
    class RekrutEvent
    {
        public Truppen truppen { get; set; }
        public int duration { get; set; }

        public RekrutEvent()
        {

        }

        public RekrutEvent(int duration, Truppen truppen) 
        {
            this.duration = duration;
            this.truppen = truppen;
        }
    }
}
