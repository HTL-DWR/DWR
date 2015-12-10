using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Effects;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using System.Data.OleDb;

namespace DWR
{
    /// <summary>
    /// Interaction logic for GlobalMap.xaml
    /// </summary>
    public partial class GlobalMap : Window
    {
        //private String ConnectionString = "Provider=OraOLEDB.Oracle; Data Source=192.168.128.151:1521/ora11g; User Id=d5bhifs11;Password=d5bhifs11;OLEDB.NET=True;";
        private String ConnectionString = "Provider=OraOLEDB.Oracle; Data Source=212.152.179.117:1521/ora11g; User Id=d5bhifs11;Password=d5bhifs11;OLEDB.NET=True;";

        public GlobalMap()
        {
            InitializeComponent();

            this.Background = Brushes.BurlyWood;
            PaintVillage();
            rectRes.Fill = Brushes.Transparent;
            rectTrup.Fill = Brushes.Transparent;

        }

        private void PaintVillage()
        {
            
            Canvas canvasPanel = new Canvas();
            Image i = setPicture();
            canvasPanel.Children.Add(i);
            using (DataBaseConnection db = new DataBaseConnection(this.ConnectionString))
            {
                using (IDataReader reader = db.ExecuteSqlCommand("select to_char(d.name), round(t.x, 20) as X , round(t.y,20) as Y from dorf d, TABLE(SDO_UTIL.GETVERTICES(d.d_location)) t ")) //where WHERE SDO_ANYINTERACT(d.location, ) = 'TRUE'
                {
                    while (reader.Read())
                    {
                        Rectangle Rectangle = new Rectangle();
                        Rectangle.Width = 10;
                        Rectangle.Height = 10;
                        Rectangle.ToolTip = reader[0].ToString();
                        ImageBrush ib = new ImageBrush();
                        ib.ImageSource = new BitmapImage(new Uri("pack://application:,,,/Resources/images.png"));
                        Rectangle.Fill = ib;
                        Rectangle.MouseLeftButtonDown += redRectangle_MouseLeftButtonDown;
                        Canvas.SetLeft(Rectangle, Math.Abs(Double.Parse(reader[1].ToString()) * 20)); //Betrag einer Zahl
                        Canvas.SetTop(Rectangle, Math.Abs(Double.Parse(reader[2].ToString()) * 20));
                        canvasPanel.Children.Add(Rectangle);
                        Console.WriteLine(reader[0].ToString() + "X:" + reader[1].ToString() + "Y:" + reader[2].ToString());
                    }
                }
            }
            scvMap.Content = canvasPanel;
            
        }

        private Image setPicture() {
            BitmapImage carBitmap = new BitmapImage(new Uri("pack://application:,,,/Resources/images.png"));
            Image i = new Image();
            i.Source = carBitmap;
            return i;
        }

        private void redRectangle_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            try
            {
                using (DataBaseConnection db = new DataBaseConnection(this.ConnectionString))
                {
                    using (IDataReader reader = db.ExecuteSqlCommand("select t.Schwert, t.Reiter,t.Lanze, NVL(s.CLAN,0), s.UNAME from Truppe t inner join movable m on m.id = t.id inner join dorf d on d.id = m.did  inner join Spieler s on s.UNAME = d.OWNER where d.name = '" + ((Rectangle)sender).ToolTip + "'"))
                    {

                        reader.Read();
                        lblAmountFighters.Content = reader[0];
                        lblAmountRiders.Content = reader[1];
                        lblAmountLancer.Content = reader[2];
                        lblDorfName.Content = ((Rectangle)sender).ToolTip;
                        lblClan.Content = reader[3];
                        lblPlayer.Content = reader[4];
                        reader.Close();
                    }

                    using (IDataReader reader = db.ExecuteSqlCommand("select t.Holz, t.Stein, t.Lehm from ResGruppe t inner join movable m on m.id = t.id inner join dorf d on d.id = m.did  where d.name = '" + ((Rectangle)sender).ToolTip + "'"))
                    {
                        reader.Read();
                        lblAmountHolz.Content = reader[0];
                        lblAmountStein.Content = reader[1];
                        lblAmountLehm.Content = reader[2];
                        reader.Close();
                    }


                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}
