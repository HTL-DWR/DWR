﻿using System;
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
using System.Windows.Media.Effects;
using System.Data.OleDb;
using System.Data;
using MahApps.Metro.Controls.Dialogs;

namespace DWR_Client_Metro
{

    public partial class MapWindow : MetroWindow
    {
        private MapDorfCollection doerfer; 

        public MapWindow()
        {
            InitializeComponent();
            doerfer = WebServiceCom.newInstance().getDorfCoordinates();
            PaintVillage();
            rectTrup.Fill = Brushes.Transparent;
        }

        private void PaintVillage()
        {

            Canvas canvasPanel = new Canvas();
           // Image i = setPicture();
           // canvasPanel.Children.Add(i);
            ImageBrush ib = new ImageBrush();
            Console.WriteLine(doerfer.doerfer.ToString());
            foreach (DorfMap currDorf in doerfer.doerfer)
            {

                Rectangle Rectangle = new Rectangle();
                Rectangle.Width = 20;
                Rectangle.Height = 20;
                Rectangle.ToolTip = currDorf.id;
                ib.ImageSource = new BitmapImage(new Uri("pack://application:,,,/DWR-Resources/pixelart/pin2.PNG"));
                Rectangle.Fill = ib;
                Rectangle.MouseLeftButtonDown += redRectangle_MouseLeftButtonDown;
                Canvas.SetLeft(Rectangle, Math.Abs(Double.Parse(currDorf.x.ToString()) * 20)); //Betrag einer Zahl
                Canvas.SetTop(Rectangle, Math.Abs(Double.Parse(currDorf.y.ToString()) * 20));
                canvasPanel.Children.Add(Rectangle);
                Console.WriteLine("Dorf-------------------" + currDorf.name + "---x" + currDorf.x + "---y" + currDorf.y );
            }

                                   
            scvMap.Content = canvasPanel;

        }

        private Image setPicture()
        {
            BitmapImage carBitmap = new BitmapImage(new Uri("pack://application:,,,/Resources/images.png"));
            Image i = new Image();
            i.Source = carBitmap;
            return i;
        }

        private void redRectangle_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {

            DorfMap currentDorf = doerfer.doerfer.Find(x => x.id.ToString() == ((Rectangle)sender).ToolTip.ToString());
            
            lblAmountFighters.Content = currentDorf.truppen.schwert;
            lblAmountRiders.Content = currentDorf.truppen.reiter;
            lblAmountLancer.Content = currentDorf.truppen.lanze;
            lblDorfName.Content = currentDorf.name;
            lblClan.Content = currentDorf.clan;
            lblPlayer.Content = currentDorf.owner;
            lblAmountArcher.Content = currentDorf.truppen.bogen;
        }

        private async void btnFight_Click(object sender, RoutedEventArgs e)
        {
             await this.ShowMessageAsync("Angriff!", "Das Dorf '" + lblDorfName.Content + "' von '" + lblPlayer.Content + "' wird angegriffen!!!");
        }

        private async void btnSupport_Click(object sender, RoutedEventArgs e)
        {
            await this.ShowMessageAsync("Unterstützung!", "Das Dorf '" + lblDorfName.Content + "' von '" + lblPlayer.Content + "' wird jetzt von dir unterstützt!!!");
        }

        private async void btnTrade_Click(object sender, RoutedEventArgs e)
        {
            await this.ShowMessageAsync("Handel!", "Du handelst jetzt mit dem Dorf '" + lblDorfName.Content + "' von '" + lblPlayer.Content + "'!!!");
        }
    }
}
