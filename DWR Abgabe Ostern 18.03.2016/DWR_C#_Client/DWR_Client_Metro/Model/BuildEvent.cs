using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DWR_Client_Metro.Model
{
    class BuildEvent
    {
        public String dorfName { get; set; }
        public Gebaeude gebauede { get; set; }
        public int duration { get; set; }

        public BuildEvent() { }

        public BuildEvent(String dorfName, Gebaeude gebauede, int duration) {
            this.dorfName = dorfName;
            this.gebauede = gebauede;
            this.duration = duration;
        }

    }
}
