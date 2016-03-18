using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DWR_Client_Metro.Model
{
    public class MapDorfCollection
    {

        public List<DorfMap> doerfer { get; set; }

        public MapDorfCollection(List<DorfMap> _doerfer)
        {
            this.doerfer = doerfer;
        }

        public MapDorfCollection() { 
            this.doerfer = new List<DorfMap>();
        }
    }
}
