using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DWR.Model
{
    public class Rohstoffe
    {
        public int holz { get; set; }
        public int stein { get; set; }
        public int lehm { get; set; }

        public Rohstoffe(int _holz, int _stein, int _lehm) {
            this.holz = _holz;
            this.stein = _stein;
            this.lehm = _lehm;
        }

        public Rohstoffe() { }
    }
}
