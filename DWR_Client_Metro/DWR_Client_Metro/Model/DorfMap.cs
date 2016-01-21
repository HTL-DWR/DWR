using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DWR_Client_Metro.Model
{
    public class DorfMap
    {
        public int id { get; set; }
        public String name { get; set; }
        public String owner { get; set; }
        public String clan { get; set; }
        public int x { get; set; }
        public int y { get; set; }
        public Truppen truppen { get; set; }

        public DorfMap()
        {
            this.id = -1;
            this.name = "noname";
            this.owner = "noname";
            this.clan = "";
            this.x = -1;
            this.y = -1;
            this.truppen = new Truppen();
        }

        public DorfMap(int id, String name, String owner, String clan, int x, int y,
                Truppen truppen)
        {
            this.id = id;
            this.name = name;
            this.owner = owner;
            this.clan = clan;
            this.x = x;
            this.y = y;
            this.truppen = truppen;
        }

    }
}
