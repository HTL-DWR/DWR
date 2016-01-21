using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DWR_Client_Metro.Model
{
    public class Dorf
    {
        public int id  { get; set; }
        public String name { get; set; }
        public List<Gebaeude> gebaude { get; set; }

        public Rohstoffe rohstoffe { get; set; }

        public Truppen truppen { get; set; }    
        public List<Unterstuezung> unsterstuezungen;

        public List<StatischesEventGebaeudeBau> statischeEventsGebaeudeBau { get; set; }
        public List<StatischesEventTruppenAusbildung> statischeEventsTruppenAusbildung { get; set; }
        public List<BewegungsEventTruppen> bewegungsEventsTruppen { get; set; }
        public List<BewegungsEventRohstoffe> bewegungsEventsRohstoffe { get; set; }


        public Dorf(String _name)
        {
            this.name = _name;
        }

        public Dorf() { 
     
        }
    }
}
