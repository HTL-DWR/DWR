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

namespace DWR.Model
{
    class XMLConverter
    {
        private string URL = "http://10.211.55.4:8080/DWR_WebServer/rest/";
        private HttpClient client = new HttpClient();

        public XMLConverter() 
        {
            client.BaseAddress = new Uri(URL);
        }

        public Object Request(String UMLParameters, Type type) 
        { 
            client.DefaultRequestHeaders.Accept.Add(
            new MediaTypeWithQualityHeaderValue("text/xml"));

            HttpResponseMessage response = client.GetAsync(UMLParameters).Result;

            if (response.IsSuccessStatusCode)
            {
                // Parse the response body. Blocking!
                String ResponseXML = response.Content.ReadAsStringAsync().Result;


                XmlRootAttribute xroot = new XmlRootAttribute();
                xroot.ElementName = "DWR";
                xroot.IsNullable = true;

                Console.WriteLine(ResponseXML);

                XmlSerializer serializer = new XmlSerializer(type, xroot);
                XmlReader reader = XmlReader.Create(new StringReader(ResponseXML));
                return serializer.Deserialize(reader);

            }

            throw new Exception("{0} ({1})"+ (int)response.StatusCode+ response.ReasonPhrase);
        }
    }
}
