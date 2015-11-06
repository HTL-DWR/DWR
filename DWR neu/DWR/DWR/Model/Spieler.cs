using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DWR.Model
{
    public class Spieler
    {
        public String name { get; set; }
        public String passwort { get; set; }

        public List<Dorf> doerfer { get; set; }

        public Spieler(String _name, String _passwort) {
            name = _name;
            passwort = _passwort;

            doerfer = new List<Dorf>();
        }

        public Spieler() { doerfer = new List<Dorf>(); }
    }
}
