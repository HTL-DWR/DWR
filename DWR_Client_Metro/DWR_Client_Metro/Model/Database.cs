using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DWR_Client_Metro.Model
{
    public class Database
    {
        public List<Spieler> spieler { get; set; }
        public List<Board> forum { get; set; }
        
        public Database() {
            spieler = new List<Spieler>();
            forum = new List<Board>();
        }

        public void addSpieĺer(String _name, String _passwort) {
            if (isUsernameUsed(_name)) 
                throw new Exception("Exception: Name ist bereits in verwendung");

            Spieler spielerToAdd = new Spieler(_name, _passwort);

        }

        public void addDorfToSpieler(Dorf _dorf, Spieler _spieler) { 
            
        
        }

        private bool isUsernameUsed(String _name) {
            bool retVal = false;

            foreach(Spieler s in spieler) {
                if (s.username.Equals(_name))
                    retVal = true;
            }

            return retVal;
        }
    }
}
