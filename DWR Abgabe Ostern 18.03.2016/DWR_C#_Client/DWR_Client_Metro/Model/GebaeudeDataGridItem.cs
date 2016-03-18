using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;

namespace DWR_Client_Metro.Model
{
    public class GebaeudeDataGridItem
    {
        public String name { get; set; }
        public int level { get; set; }
        public Button buttonBau { get; set; }
        public String thumpnail { get; set; }

        public GebaeudeDataGridItem(String _name, int _level, Button _buttonBau, String _thumpnail) {
            this.name = _name;
            this.level = _level;
            this.buttonBau = _buttonBau;
            this.thumpnail = _thumpnail;
        }
    }
}
