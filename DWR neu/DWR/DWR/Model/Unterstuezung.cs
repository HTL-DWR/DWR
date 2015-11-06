using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DWR.Model
{
    public class Unterstuezung
    {
        private Spieler unterstuetzer { get; set; }
        private Truppen truppen { get; set; }

        public Unterstuezung(Spieler _unterstuetzer, Truppen _truppen) {
            this.unterstuetzer = _unterstuetzer;
            this.truppen = _truppen;
        }

    }
}
