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
        private String ConnectionString = "Provider=OraOLEDB.Oracle; Data Source=192.168.128.151:1521/ora11g; User Id=d5bhifs11;Password=d5bhifs11;OLEDB.NET=True;";


        public GlobalMap()
        {
            InitializeComponent();
            PaintVillage();
        }

        private void PaintVillage()
        {
            Canvas canvasPanel = new Canvas();
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
                        Rectangle.Fill = new SolidColorBrush(Colors.Red);
                        Rectangle.MouseLeftButtonDown += redRectangle_MouseLeftButtonDown;
                        Canvas.SetLeft(Rectangle, Double.Parse(reader[1].ToString()) * 10);
                        Canvas.SetTop(Rectangle, Double.Parse(reader[2].ToString()) * 10);
                        canvasPanel.Children.Add(Rectangle);
                    }
                }
            }
            scvMap.Content = canvasPanel;
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
