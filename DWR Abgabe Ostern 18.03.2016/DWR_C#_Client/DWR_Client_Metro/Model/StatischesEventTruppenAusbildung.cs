using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DWR_Client_Metro.Model
{
    public class StatischesEventTruppenAusbildung
    {
        public Truppen truppen { get; set; }

        public StatischesEventTruppenAusbildung(Truppen _truppen) {
            this.truppen = _truppen;
        }

        public StatischesEventTruppenAusbildung() { }
    }
}
