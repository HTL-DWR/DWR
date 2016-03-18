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
using MahApps.Metro.Controls;
using MahApps.Metro.Controls.Dialogs;
using DWR_Client_Metro.Model;
using System.Media;


namespace DWR_Client_Metro
{

    public partial class MainWindow : MetroWindow
    {

        public MainWindow()
        {
            InitializeComponent();

           // SoundPlayer player = new SoundPlayer(@"../../DWR-Resources/song.wav");
           // player.Play();
        }

        private void btnLogin_Click(object sender, RoutedEventArgs e)
        {

            try
            {
                
                int session = WebServiceCom.newInstance().login(txtUser.Text, txtPassowrd.Password);

                 DorfWindow mw = new DorfWindow(txtUser.Text, session);
                 mw.Show();
                 this.Close(); 

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            } 

        }

        private async void btnRegister_Click(object sender, RoutedEventArgs e)
        {
           try
           {
               await this.ShowMessageAsync("Registrierung:", (WebServiceCom.newInstance().register(txtUser.Text, txtPassowrd.Password)));
           }
           catch (Exception ex)
           {
               MessageBox.Show("Error: " + ex.Message);
           }
            
        }
    }
}
