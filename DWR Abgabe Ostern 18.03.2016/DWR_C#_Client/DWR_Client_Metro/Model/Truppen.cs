using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DWR_Client_Metro.Model
{
    public class Truppen
    {
        public int schwert { get; set; }
        public int reiter { get; set; }
        public int bogen { get; set; }
        public int lanze { get; set; }

        public Truppen(int _schwert, int _reiter, int _bogen, int _lanze) {
            this.schwert = _schwert;
            this.reiter = _reiter;
            this.bogen = _bogen;
            this.lanze = _lanze;
        }

        public Truppen() { }
    }
}
