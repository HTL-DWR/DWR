using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using System.Xml;
using System.Xml.Serialization;
using System.Web.Script.Serialization;
using System.Drawing;
using System.Windows;


namespace DWR_Client_Metro.Model
{
    enum Method { 
        GET, PUT, POST, DELETE
    };


    class WebServiceCom
    {
        private  string URL = "http://10.211.55.4:8080/DWR_WebServer/rest/";
        private  HttpClient client = new HttpClient();

        private static WebServiceCom wsc = null;

        private WebServiceCom() 
        {
            client.BaseAddress = new Uri(URL);
        }

        public static WebServiceCom newInstance() {
            if (wsc == null)
            {
                wsc = new WebServiceCom();
            }

            return wsc;
        }

        private  String  Request(String UMLParameters, Method method) 
        { 
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
            HttpResponseMessage response = null;

            switch (method) { 
                case Method.GET: 
                     response = client.GetAsync(UMLParameters).Result;
                break;

                case Method.PUT:
                response = client.PutAsync(UMLParameters, null).Result; 
                break;

                case Method.POST:
                response = client.PostAsync(UMLParameters, null).Result;
                break;

                case Method.DELETE:
                 response = client.DeleteAsync(UMLParameters).Result;
                break;
            }

            if (response.IsSuccessStatusCode)
            {
              
               /* String ResponseXML = response.Content.ReadAsStringAsync().Result;


                XmlRootAttribute xroot = new XmlRootAttribute();
                xroot.ElementName = "DWR";
                xroot.IsNullable = true;

                Console.WriteLine(ResponseXML);

                XmlSerializer serializer = new XmlSerializer(type, xroot);
                XmlReader reader = XmlReader.Create(new StringReader(ResponseXML));
                return serializer.Deserialize(reader);*/

                //Wird nur noch der JSON-String returnt
                return response.Content.ReadAsStringAsync().Result;
            }

            throw new Exception("{0} ({1})"+ (int)response.StatusCode+ response.ReasonPhrase);
        }

        public LoginStatus login(String username, String password) 
        {
            String json = Request("Credentials/?username=" + username + "&password_md5=" + MD5hash.GetHash(password), Method.GET);
            return new System.Web.Script.Serialization.JavaScriptSerializer().Deserialize<LoginStatus>(json);
        }

        public String register(String username, String password) 
        {
            String ret = Request("Credentials/?username=" + username + "&password_md5=" + MD5hash.GetHash(password), Method.PUT);
            return ret;
        }

        public Spieler getSpielerDetial(String username)
        {
            String json = Request("SpielerDetail/?username=" + username, Method.GET);
            return new System.Web.Script.Serialization.JavaScriptSerializer().Deserialize<Spieler>(json);
        }

        public Dorf getDorfDetial(int dorfid)
        {
            String json = Request("DorfDetailFull/?id=" + dorfid, Method.GET);
            return new System.Web.Script.Serialization.JavaScriptSerializer().Deserialize<Dorf>(json);
        }

        public MapDorfCollection getDorfCoordinates() {
            String json = Request("Map/", Method.GET);
            Console.WriteLine(json);
            return new System.Web.Script.Serialization.JavaScriptSerializer().Deserialize<MapDorfCollection>(json);
        }
    }
}
