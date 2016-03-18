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
using Newtonsoft.Json;

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

        public int login(String username, String password) 
        {
            //MessageBox.Show("LoginSession?uname=" + username + "&passhash=" + MD5hash.GetHashString(password));


            String pwHash = MD5hash.GetHashString(password).ToLower();
            

            String json = Request("LoginSession?uname=" + username + "&passhash=" + pwHash, Method.GET);
            ResponseObject response = new System.Web.Script.Serialization.JavaScriptSerializer().Deserialize<ResponseObject>(json);
            //int wird returnt
            if (response.isOk())
                return Int32.Parse(response.data.ToString());
            throw new Exception(response.errormsg);
        }

        public String register(String username, String password) 
        {
            //MessageBox.Show("Credentials?username=" + username + "&password_md5=" + MD5hash.GetHashString(password));
            String pwHash = MD5hash.GetHashString(password).ToLower();

            String ret = Request("Credentials?username=" + username + "&password_md5=" + pwHash, Method.PUT);

            ResponseObject response = new System.Web.Script.Serialization.JavaScriptSerializer().Deserialize<ResponseObject>(ret);

            if (response.isOk())
                return (String)response.data;
            throw new Exception(response.errormsg);
        }

        public Spieler getSpielerDetial(String username)
        {
            String json = Request("SpielerDetail?username=" + username, Method.GET);
            ResponseObject response = new System.Web.Script.Serialization.JavaScriptSerializer().Deserialize<ResponseObject>(json);

            if (response.isOk())
            {
                return new System.Web.Script.Serialization.JavaScriptSerializer().Deserialize<Spieler>(this.getDataJsonStrignFromROStrign(json)); 
            }
            throw new Exception(response.errormsg);
        }

        public Dorf getDorfDetial(int dorfid, int session)
        {
            String json = Request("DorfDetailFull?id=" + dorfid + "&sessionid="+session, Method.GET);

            ResponseObject response = new System.Web.Script.Serialization.JavaScriptSerializer().Deserialize<ResponseObject>(json);

            if (response.isOk())
            {
                return new System.Web.Script.Serialization.JavaScriptSerializer().Deserialize<Dorf>(this.getDataJsonStrignFromROStrign(json)); 
            }
            throw new Exception(response.errormsg);
        }

        public MapDorfCollection getDorfCoordinates() {
            String json = Request("Map", Method.GET);
            
            ResponseObject response = new System.Web.Script.Serialization.JavaScriptSerializer().Deserialize<ResponseObject>(json);


            if (response.isOk())
            {
             

                //MessageBox.Show(this.getDataJsonStrignFromROStrign(json));
                MapDorfCollection mdc = new System.Web.Script.Serialization.JavaScriptSerializer().Deserialize<MapDorfCollection>(this.getDataJsonStrignFromROStrign(json));

                foreach (DorfMap d in mdc.doerfer) {
                    Console.WriteLine("----" + d.id);
                }

                return mdc;
               
            }

           throw new Exception(response.errormsg);
        }

        internal void BuildCommand(int id, string gebTyp, int session)
        {
            String json = Request("BuildCommand?dorfid=" + id + "&GebTyp="+gebTyp +"&sessionid=" + session, Method.GET);
            ResponseObject response = new System.Web.Script.Serialization.JavaScriptSerializer().Deserialize<ResponseObject>(json);

            if (!response.isOk())
                throw new Exception(response.errormsg);

        }

        public BuildEventList CurrentBuildEvents (int dorfId, int session) 
        {
            String json = Request("CurrentBuildEvents?dorfId=" + dorfId + "&sessionid=" + session, Method.GET);
            ResponseObject response = new System.Web.Script.Serialization.JavaScriptSerializer().Deserialize<ResponseObject>(json);

            if (response.isOk())
            {
                return new System.Web.Script.Serialization.JavaScriptSerializer().Deserialize<BuildEventList>(this.getDataJsonStrignFromROStrign(json));
            }
            throw new Exception(response.errormsg);

        }

        public RekrutEventList CurrentRekrutEvents(int dorfId, int session)
        {
            String json = Request("CurrentRekrutEvents?dorfId=" + dorfId + "&sessionid=" + session, Method.GET);
            ResponseObject response = new System.Web.Script.Serialization.JavaScriptSerializer().Deserialize<ResponseObject>(json);

            if (response.isOk())
            {
                return new System.Web.Script.Serialization.JavaScriptSerializer().Deserialize<RekrutEventList>(this.getDataJsonStrignFromROStrign(json));
            }
            throw new Exception(response.errormsg);

        }

        public DorfMap DorfDetailMap(int dorfId) 
        {
            String json = Request("DorfDetailMap?id=" + dorfId , Method.GET);
            ResponseObject response = new System.Web.Script.Serialization.JavaScriptSerializer().Deserialize<ResponseObject>(json);

            if (response.isOk())
            {
                return new System.Web.Script.Serialization.JavaScriptSerializer().Deserialize<DorfMap>(this.getDataJsonStrignFromROStrign(json));
            }
            throw new Exception(response.errormsg);
        }


        private String getDataJsonStrignFromROStrign(String json) {
            String dataString = json.Split(new[] { "data\":" }, StringSplitOptions.None)[1];
            return dataString.Remove(dataString.Length - 1);
        }

     

    }
}
