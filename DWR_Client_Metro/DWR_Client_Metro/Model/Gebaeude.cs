using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DWR_Client_Metro.Model
{
    public class Gebaeude
    {
        public String name { get; set; }
        public int lvl { get; set; }

        public Gebaeude(String _name, int _level) {
            this.name = _name;
            this.lvl = _level;
        }

        public Gebaeude() { }
    }
}
