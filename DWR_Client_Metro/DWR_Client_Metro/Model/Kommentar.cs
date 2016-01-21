using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DWR_Client_Metro.Model
{
    public class Kommentar
    {
        public Spieler verfasser { get; set; }
        public String text { get; set; }
        public DateTime erstellungszeitpunkt { get; set; }

        public Kommentar(Spieler _verfasser, String _text)
        {
            this.verfasser = _verfasser;
            this.text = _text;

            this.erstellungszeitpunkt = DateTime.Now;

        }

        public Kommentar() { }
    }
}
