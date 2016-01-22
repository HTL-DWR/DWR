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
using System.Windows.Shapes;
using MahApps.Metro.Controls;
using DWR_Client_Metro.Model;

namespace DWR_Client_Metro
{

    public partial class DorfWindow : MetroWindow
    {
        private Spieler spieler;
        private MapWindow mapWindow;
        /*private Notification nw;
        private Benachrichtigung bw;*/
        private String ConnectionString = "Provider=OraOLEDB.Oracle; Data Source=212.152.179.117:1521/ora11g; User Id=d5bhifs11;Password=d5bhifs11;OLEDB.NET=True;";
        private Dorf dorf;
        //private String ConnectionString = "Provider=OraOLEDB.Oracle; Data Source=192.168.128.151:1521/ora11g; User Id=d5bhifs11;Password=d5bhifs11;OLEDB.NET=True;";

        public DorfWindow(String _username)
        {
            InitializeComponent();


            try
            {
                Spieler spieler = WebServiceCom.newInstance().getSpielerDetial(_username);


                dorf = WebServiceCom.newInstance().getDorfDetial(spieler.doerfer[0]);

                GridDoerfer.ItemsSource = spieler.doerfer;

                fillWithDorfData(dorf);
                InitializeComponent();
                
                spieler.doerfer = getDoerferNames();

                GridDoerfer.SelectedIndex = 0;
                fillWithDorfData((Dorf)GridDoerfer.SelectedItem);

                Canvas canvasBackgroundMap = new Canvas();

                   
                BitmapImage carBitmap = new BitmapImage(new Uri("pack://application:,,,/DWR-Resources/dorf_background.jpg"));

                rectBev.Fill = Brushes.Transparent;
                ListJobs.Background = Brushes.Transparent;
                GridGebäude.Background = Brushes.Transparent;
                GridDoerfer.Background = Brushes.Transparent;
                Image i = new Image();
                i.Source = carBitmap;
                canvasBackgroundMap.Children.Add(i);

                pnlMap.Content = canvasBackgroundMap;
            }
            catch (Exception ex)
            {
               // MessageBox.Show("Error: " + ex.Message);
            }
        }

        private List<int> getDoerferNames()
        {
            throw new NotImplementedException();
        }

        private void fillWithDorfData(Dorf dorf)
        {
            lblDorfName.Content = dorf.name;

            lblAmountHolz.Content = dorf.rohstoffe.holz;
            lblAmountStein.Content = dorf.rohstoffe.stein;
            lblAmountLehm.Content = dorf.rohstoffe.lehm;

            lblAmountFighters.Content = dorf.truppen.schwert;
            lblAmountRiders.Content = dorf.truppen.reiter;
            lblAmountLancer.Content = dorf.truppen.lanze;
            lblAmountArcher.Content = dorf.truppen.bogen;

            fillGridGebaeude();

            Canvas Gebäude = addPictureToBuilding();
            pnlMap.Content = Gebäude;
            pnlMap.VerticalScrollBarVisibility = ScrollBarVisibility.Hidden;

        }

        private void fillGridGebaeude() 
        {
            //GridGebäude.ItemsSource = dorf.gebaude;
            foreach (Gebaeude g in dorf.gebaude)
            {
                Button bau = new Button();
                if (g.lvl != 0)
                {
                    bau.Content = "Ausbauen";
                }
                else
                {
                    bau.Content = "Bauen";
                }
                //bau.Click +=

                //Image img = new Image();
                //img.Source = new BitmapImage(new Uri("pack://application:,,,/DWR-Resources/pixelart/" + g.name.ToLower() + ".png"));

                //Umlaut ä bei Hauptgebäude entfernen
                g.name = g.name.Replace("ä", "ae");

                Console.WriteLine("+++++++++" + g.name);

                this.GridGebäude.Items.Add(new GebaeudeDataGridItem(g.name, g.lvl, bau, "pack://application:,,,/DWR-Resources/pixelart/" + g.name.ToLower() + ".png"));

            }
        
        
        
        }

        private Canvas addPictureToBuilding()
        {
            Canvas GebäudeInDorf = new Canvas();

            int top = 40;
            int left = 20;

            foreach (Gebaeude g in dorf.gebaude)
            {
                Rectangle Rectangle = new Rectangle();
                Rectangle.Width = 80;
                Rectangle.Height = 40;
                ImageBrush ib = new ImageBrush();

                //Umlaut ä bei Hauptgebäude entfernen
                g.name = g.name.Replace("ä", "ae");

                Console.WriteLine("----------" + g.name);

                ib.ImageSource = new BitmapImage(new Uri("pack://application:,,,/DWR-Resources/pixelart/"+ g.name.ToLower()+".png"));
                Rectangle.Fill = ib;
                
                GebäudeInDorf.Children.Add(Rectangle);

                switch (g.name.ToLower()) { 
                    case "hauptgebaeude":
                        left = 340;
                        top = 130;
                    break;

                    case "lehmgrube":
                        left = 360;
                        top = 190;
                    break;

                    case "holzfaeller":
                        left = 50;
                        top = 120;
                    break;

                    case "kaserne":
                         left = 290;
                        top = 50;
                    break;

                    case "steinmetz":
                        left = 10;
                        top = 10;
                    break;
                }

                Canvas.SetLeft(Rectangle, left);
                Canvas.SetTop(Rectangle, top);
            }

            return GebäudeInDorf;
        }

        private void click_btnMap(object sender, RoutedEventArgs e)
        {
            this.mapWindow = new MapWindow();
            this.mapWindow.Show();
        }

        private void click_btnBenachrichtigungen(object sender, RoutedEventArgs e)
        {

        }

        private void click_btnStamm(object sender, RoutedEventArgs e)
        {

        }
    }
}
