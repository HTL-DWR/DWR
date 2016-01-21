using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DWR_Client_Metro.Model
{
    public class StatischesEventGebaeudeBau : Event
    {
        private Gebaeude gebaeude { get; set; }

        public StatischesEventGebaeudeBau(Gebaeude _gebaude) {
            this.gebaeude = _gebaude;
        }

        public StatischesEventGebaeudeBau() { }
    }
}
