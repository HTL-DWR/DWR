using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Controls;

namespace DWR_Client_Metro
{
    class EventTimer
    {
        public void startTimer(ListView l, String bez, int duration, DorfWindow dw)
        {
            Task median = Task.Run(() =>
            {
                int h = 0;
                int m = 0;
                int s = 10;
                int counter = 5;
                bool isAdded = false;

                m = duration / 60;
                s = duration % 60;

                while (h > 0 || s > 0 || m > 0)
                {
                    l.Dispatcher.Invoke(System.Windows.Threading.DispatcherPriority.Normal, new Action( //invoke syncronises gui and dispacher
                         delegate ()              //code in delegate can use the thread to change the gui
                        {
                                if (counter >= 5)
                                {
                                    Object b = bez + ":" + h + ":" + m + ":" + s;

                                    b = bez + ":" + h + ":" + m + ":" + s;
                                    l.Items.Add(b);

                                    

                                    counter = 0;

                                    try
                                    {
                                        if (isAdded == true)
                                            l.Items.RemoveAt(0);
                                    }
                                    catch (Exception e) { }
                                    isAdded = true;
                                }


                            }));

                    s = s - 1;
                    if (s == -1)
                    {
                        m -= 1;
                        s = 59;
                    }
                    if (m == -1)
                    {
                        h -= 1;
                        m = 59;
                    }
                    if (h == 0 && m == 0 && s == 0)
                    {
                        l.Dispatcher.Invoke(System.Windows.Threading.DispatcherPriority.Normal, new Action( //invoke syncronises gui and dispacher
                                delegate ()              //code in delegate can use the thread to change the gui
                                {
                                    //dw.UpdateDorf();
                                    l.Items.Add(bez + "Finish");
                                   
                                }));
                    }
                    counter++;
                    Thread.Sleep(1000);
                }



            });
        }
    }
}
