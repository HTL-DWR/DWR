using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using DWR.Model;
using System.Data;

namespace DWR
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private Spieler spieler;
        private GlobalMap gm;
        private Notification nw;
        private Benachrichtigung bw;
        private String ConnectionString = "Provider=OraOLEDB.Oracle; Data Source=212.152.179.117:1521/ora11g; User Id=d5bhifs11;Password=d5bhifs11;OLEDB.NET=True;";
        private Dorf dorf;
        //private String ConnectionString = "Provider=OraOLEDB.Oracle; Data Source=192.168.128.151:1521/ora11g; User Id=d5bhifs11;Password=d5bhifs11;OLEDB.NET=True;";

        public MainWindow(String _username)
        {
            try
            {

                spieler = (Spieler)new XMLConverter().Request("SpielerDetail/?username=" + _username, typeof(Spieler));
                dorf = (Dorf)new XMLConverter().Request("DorfDetailFull/?id=" + spieler.doerfer[0], typeof(Dorf));
                GridDoerfer.ItemsSource = spieler.doerfer;

                fillWithDorfData(dorf);
                InitializeComponent();
                /*spieler.doerfer = getDoerferNames();
                GridDoerfer.SelectedIndex = 0;

                fillWithDorfData((Dorf)GridDoerfer.SelectedItem);*/
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error: " + ex.Message);
            }
        }

        private void fillWithDorfData(Dorf dorf) {
            lblDorfName.Content = dorf.name;

            lblAmountHolz.Content = dorf.rohstoffe.holz;
            lblAmountStein.Content = dorf.rohstoffe.stein;
            lblAmountLehm.Content = dorf.rohstoffe.lehm;

            lblAmountFighters.Content = dorf.truppen.schwert;
            lblAmountRiders.Content = dorf.truppen.reiter;
            lblAmountLancer.Content = dorf.truppen.lanze;
            lblAmountArcher.Content = dorf.truppen.bogen;

            GridGebäude.ItemsSource = dorf.gebaude;

            pnlMap.Background = Brushes.DarkGreen;
            Canvas Gebäude = addPictureToBuilding();
            pnlMap.Content = Gebäude;
            pnlMap.VerticalScrollBarVisibility = ScrollBarVisibility.Hidden;

        }

        private Canvas addPictureToBuilding()
        {
            Canvas GebäudeInDorf = new Canvas();

            int top = 40;
            int left = 20;
            int counter = 0;

            foreach(Gebaeude g in dorf.gebaude)
            {   
                Rectangle Rectangle = new Rectangle();
                Rectangle.Width = 60;
                Rectangle.Height = 30;
                ImageBrush ib = new ImageBrush();
                ib.ImageSource = new BitmapImage(new Uri("pack://application:,,,/Resources/"+ g.name.ToLower()+".png"));
                Rectangle.Fill = ib;
                Canvas.SetLeft(Rectangle, top); //Betrag einer Zahl
                Canvas.SetTop(Rectangle, left);
                GebäudeInDorf.Children.Add(Rectangle);

                if (counter < 3 )
                {
                    top += 20;
                }
                else
                {
                    counter = 0;
                    top -= 60;
                    left += 20;
                }
            }

            return GebäudeInDorf;
        }

        private void MenuItemMap_Click(object sender, RoutedEventArgs e)
        {
            gm = new GlobalMap();
            gm.Show();
        }

        private void MenuItem_Click(object sender, RoutedEventArgs e)
        {
            nw = new Notification();
            nw.Show();
        }

        private void MenuItem_Click_1(object sender, RoutedEventArgs e)
        {
            bw = new Benachrichtigung();
            bw.Show();
        }

        /*
                private List<Dorf> getDoerferNames()
        {
            List<Dorf> doerfer = new List<Dorf>();

            using (DataBaseConnection db = new DataBaseConnection(this.ConnectionString))
            {
                using (IDataReader readerDorf = db.ExecuteSqlCommand("select name from dorf where owner = '" + spieler.name + "'"))
                {
                    while (readerDorf.Read())
                    {
                        doerfer.Add(new Dorf((String)readerDorf["name"]));
                    }
                }
            }

            return doerfer;
        }

        private Rohstoffe getDorfRes(String _dname)
        {
            Rohstoffe rohstoffe;

            using (DataBaseConnection db = new DataBaseConnection(this.ConnectionString))
            {
                System.Diagnostics.Debug.WriteLine("select r.holz, r.stein, r.lehm from resgruppe r inner join movable m on m.id = r.id  inner join dorf d on d.id = m.did where d.name='" + _dname +"'");

                using (IDataReader reader = db.ExecuteSqlCommand("select r.holz, r.stein, r.lehm from resgruppe r inner join movable m on m.id = r.id  inner join dorf d on d.id = m.did where d.name='" + _dname + "'"))
                {
                    reader.Read();
                    rohstoffe = new Rohstoffe(Int16.Parse(reader["holz"].ToString()), Int16.Parse(reader["stein"].ToString()), Int16.Parse(reader["lehm"].ToString()));
                }
            }

            return rohstoffe;
        }

        private Truppen getDorfTruppen(String _dname) 
        {
            Truppen truppen;

            using (DataBaseConnection db = new DataBaseConnection(this.ConnectionString))
            {
                using (IDataReader reader = db.ExecuteSqlCommand("select t.schwert, t.reiter, t.bogen, t.lanze from truppe t inner join movable m on m.id = t.id  inner join dorf d on d.id = m.did inner join spieler s on s.uname = d.owner where s.uname = '" + spieler.name  + "' and d.name='" + _dname + "'"))
                {
                    reader.Read();
                    truppen = new Truppen(Int16.Parse(reader["schwert"].ToString()), Int16.Parse(reader["reiter"].ToString()), Int16.Parse(reader["bogen"].ToString()), Int16.Parse(reader["lanze"].ToString()));
                }
            }

            return truppen;
        }

         private List<Gebaeude> getDorfGebaeude(String _dname)
        {
            List<Gebaeude> gebaeude = new List<Gebaeude>();

            using (DataBaseConnection db = new DataBaseConnection(this.ConnectionString))
            {
                using (IDataReader reader = db.ExecuteSqlCommand("select gt.name, b.lvl from dorf d inner join bau b on b.did=d.id inner join geb_typ gt on b.tid = gt.id where d.name='" + _dname +"'")) 
                {
                    while (reader.Read())
                    {
                        gebaeude.Add(new Gebaeude((String)reader["name"], Int16.Parse(reader["lvl"].ToString()))); 
                    }
                }
            }

            return gebaeude;
        }
        */


    }
}
