using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DWR_Client_Metro.Model
{
    public class Spieler
    {
        public String username { get; set; }
        public String passwort { get; set; }

        public List<int> doerfer { get; set; }

        public Spieler(String _username, String _passwort) {
            username = _username;
            passwort = _passwort;

            doerfer = new List<int>();
        }

        public Spieler() { doerfer = new List<int>(); }
    }
}
