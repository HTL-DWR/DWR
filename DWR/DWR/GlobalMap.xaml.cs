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
using System.Windows.Media.Effects;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace DWR
{
    /// <summary>
    /// Interaction logic for GlobalMap.xaml
    /// </summary>
    public partial class GlobalMap : Window
    {
        DrawingGroup dGroup = new DrawingGroup();
        public GlobalMap()
        {
            InitializeComponent();
            PaintVillage();            
        }

        private void PaintVillage()
        {
            Pen shapeOutlinePen = new Pen(Brushes.Red, 2);
            shapeOutlinePen.Freeze();

            using (DrawingContext dc = dGroup.Open())
            {
                int i = 0;
                //Foreach Village in area
                while (i <= 100)
                {
                    //Replace i an i with X and Y coordinate to create the village
                    dc.DrawRectangle(Brushes.Blue, shapeOutlinePen, new Rect(i, i, 1, 1));
                    i+= 20;
                }
            }

            //Drawing image to Scroll view
            Image theImage = new Image();
            DrawingImage dImageSource = new DrawingImage(dGroup);
            theImage.Source = dImageSource;
            scvMap.Content = theImage;
        }
    }
}
