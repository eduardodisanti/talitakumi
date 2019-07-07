/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.webservices;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import talitakumi.engine.framework.invoker.ServiceDelegatorRemote;
import talitakumi.framework.Sesion;



/**
 *
 * @author rupertus
 */
public class TWSStandards {

    public static final String FORMATO_FECHAS = "dd-MM-yyyy";
    private ServiceDelegatorRemote sdr = null;
    private boolean conectado = false;

    public void conectar() throws NamingException {
    
     if(Sesion.getServiceDelegatorRemote()==null) {
        System.out.println("Seteado sdr en " + sdr);

        Properties props = new Properties();

        props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        props.setProperty("java.naming.factory.url.pkgs",  "com.sun.enterprise.naming");
        props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
        props.setProperty("org.omg.CORBA.ORBInitialHost", "elias");
        props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
        
        InitialContext ctx = new InitialContext(props);

         sdr = (ServiceDelegatorRemote) ctx.lookup("services.ServiceDelegatorRemote");

         Sesion.setServiceDelegatorRemote(sdr);

        conectado = true;
      }
     else
         System.out.println("Usado sdr desde  " + Sesion.getServiceDelegatorRemote());
      
    }

    public String serializeToXML(IFObjetoSerializado obj) throws Exception {

        String resp = null;
        String larespuesta;


        if(obj!=null) {

            Element root = new Element(obj.getObjectName());

            HashMap hm = obj.getObject();
            for(String clave : (Set <String>) hm.keySet()) {

                Element elem = new Element(clave);
                elem.setText((String)hm.get(clave));
                root.addContent(elem);
            }
            Document doc = new Document(root);
            doc.setDocType(new DocType("xml"));

            XMLOutputter outputter = new XMLOutputter();
            resp = outputter.outputString(doc);
        }
        
        return(resp);
    }

    public String makeResponse(boolean ok, String text, IFObjetoSerializado obj) throws Exception {

        String resp = null;
        String larespuesta;
        
        if(ok)
            larespuesta = "ACK";
        else
            larespuesta = "NAK";


        Element root = new Element("TALITAKUMI");

        Element respuesta = new Element("RESPUESTA");
        respuesta.setText(larespuesta);
        root.addContent(respuesta);

        Element texto = new Element("TEXTO");
        texto.setText(text);
        root.addContent(texto);

        if(obj!=null) {
            Element objetos = new Element("OBJETOS");
            root.addContent(objetos);

            HashMap hm = obj.getObject();
            Element elobjeto = new Element(obj.getObjectName());
            objetos.addContent(elobjeto);
            for(String clave : (Set <String>) hm.keySet()) {

                if(clave.startsWith("I-")) {


                    Document aux = readXML((String)hm.get(clave));

                    Element r = aux.getRootElement();
                    Element subroot = new Element(r.getName());

                    elobjeto.addContent(subroot);
                    List<Element> l = r.getChildren();

                    for(Element e : l) {
                        Element elem = new Element(e.getName());
                        elem.setText(e.getText());
                        subroot.addContent(elem);
                    }

                } else {
                    Element elem = new Element(clave);
                    elem.setText((String)hm.get(clave));
                    elobjeto.addContent(elem);
                }
            }
        }
        Document doc = new Document(root);
        doc.setDocType(new DocType("xml"));

        XMLOutputter outputter = new XMLOutputter();
        resp = outputter.outputString(doc);
        return(resp);
    }

    private Document readXML(String xmlObject) {
    

        SAXBuilder builder = new SAXBuilder();
        Reader in = new StringReader(xmlObject);
        Document doc = null;
        Element root = null;
        Element meta = null;
        Element _code = null;
        Element _description = null;
        String code = null;
        String description = "";
        try
        {
            doc = builder.build(in);
            root = doc.getRootElement();
            meta = root.getChild("commResponse").getChild("meta");
            _code = meta.getChild("code");
            _description = meta.getChild("description");
            code = _code.getText();
            description = _description.getText();

        } catch (JDOMException e) {
                // do what you want
            } catch (IOException e) {

                } catch (Exception e) {

                }

        return(doc);
    }

    public String formatDate(Date adate) {

        DateFormat df = new SimpleDateFormat(FORMATO_FECHAS);

        String x = null;
        try {
             x = df.format(adate);
        } catch(Exception ex) {

        }
        return(x);
    }

    public Date makeDate(String fechanacimiento) throws ParseException {

       DateFormat df = new SimpleDateFormat(FORMATO_FECHAS);

       return(df.parse(fechanacimiento));
    }

}
