using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DWR.Model
{
    public class Board
    {
        public Spieler verfasser { get; set; }
        public String titel { get; set; }
        public String text { get; set; }
        public DateTime erstellungszeitpunkt { get; set; }

        public List<Kommentar> kommentare { get; set; }

        public Board(Spieler _verfasser, String _titel, String _text, DateTime _erstellungszeitpunkt) {
            verfasser = _verfasser;
            titel = _titel;
            text = _text;
            erstellungszeitpunkt = DateTime.Now;
        }
    }
}
