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
using DWR.Model;
using System.Data;
using System.Data.OleDb;
using System.Media;

namespace DWR
{
    /// <summary>
    /// Interaction logic for Login.xaml
    /// </summary>
    public partial class Login : Window
    {
       
        //private String ConnectionString = "Provider=OraOLEDB.Oracle; Data Source=212.152.179.117:1521/ora11g; User Id=d5bhifs11;Password=d5bhifs11;OLEDB.NET=True;";
        private String ConnectionString = "Provider=OraOLEDB.Oracle; Data Source=192.168.128.151:1521/ora11g; User Id=d5bhifs11;Password=d5bhifs11;OLEDB.NET=True;";

        private static MD5hash hasher = new MD5hash();

        public Login()
        {
            InitializeComponent();

           /* SoundPlayer player = new SoundPlayer(@"\\Mac\Home\Documents\Schule\BSD REL ORG\DWR neu\DWR\DWR\Resources\song.wav");
            player.Play();*/
        }

        private void btnRegister_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                using (DataBaseConnection db = new DataBaseConnection(this.ConnectionString))
                {
                    IList<String> parameterNames = new List<String>();
                    parameterNames.Add("uname");
                    parameterNames.Add("d_name");
                    parameterNames.Add("passwd_hash");

                    IList<OleDbType> parameterTypes = new List<OleDbType>();
                    parameterTypes.Add(OleDbType.VarChar);
                    parameterTypes.Add(OleDbType.VarChar);
                    parameterTypes.Add(OleDbType.VarChar);

                    IList<ParameterDirection> parameterDirections = new List<ParameterDirection>();
                    parameterDirections.Add(ParameterDirection.Input);
                    parameterDirections.Add(ParameterDirection.Input);
                    parameterDirections.Add(ParameterDirection.Input);

                    IList<Object> parameterValues = new List<Object>();
                    parameterValues.Add(txtUser.Text);
                    parameterValues.Add("Dorf von " + txtUser.Text);

                    String pw_hashed = MD5hash.GetHashString(txtPassowrd.Password);

                    parameterValues.Add(pw_hashed);

                    db.ExecuteSqlProcedure("CREATE_NEW_USER", parameterNames, parameterTypes, parameterDirections, parameterValues);
                    lblStatus.Content = "Neuer Nutzer registriert";
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error: " + ex.Message);
            }
        }

        private void btnLogin_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                using (DataBaseConnection db = new DataBaseConnection(this.ConnectionString))
                {
                    using (IDataReader reader = db.ExecuteSqlCommand("select decode(count(uname),0,'false','true'), passwd_hash from spieler where uname = '" + txtUser.Text + "' group by passwd_hash"))
                    {
                        reader.Read();
                        if (((String)reader[0]).Equals("false"))
                        {
                            throw new Exception("Login falsch: Name existiert nicht!");
                        }
                        else
                        {
                            String pw_hashed = ((String)reader["passwd_hash"]);

                            if (!pw_hashed.Equals(MD5hash.GetHashString(txtPassowrd.Password)))
                            {
                                throw new Exception("Login falsch: Passwort falsch!");
                            }

                            MainWindow mw = new MainWindow(txtUser.Text);
                            mw.Show();
                            this.Close();
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error: " + ex.Message);
            }
        }
    }
}
