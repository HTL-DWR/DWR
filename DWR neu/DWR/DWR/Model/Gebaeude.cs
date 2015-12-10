using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DWR.Model
{
    public class Gebaeude
    {
        public String name { get; set; }
        public int level { get; set; }

        public Gebaeude(String _name, int _level) {
            this.name = _name;
            this.level = _level;
        }
    }
}
