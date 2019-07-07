/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.webservices.laboratorio;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import talitakumi.webservices.IFObjetoSerializado;
import talitakumi.webservices.TWSStandards;

/**
 *
 * @author eduardodisanti
 */
@WebService()
@Stateless()
public class InformeLaboratorioWebService {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GenerarInforme")
    public String GenerarInforme(@WebParam(name = "pin")
    Integer pin, @WebParam(name = "cedula")
    int cedula) {

        IFObjetoSerializado informe = (IFObjetoSerializado) new InformeLaboratorioSerializado();
        byte[] pdfData;

        TWSStandards tws = new TWSStandards();

        String url, resp = null;
        Boolean laresp = true;

        try {
            tws.conectar();

           HashMap parametros = new HashMap();
           parametros.put("episodiodesde", pin);
           parametros.put("episodiohasta", pin);
           parametros.put("internet", "S");

           String nombre = pin + "R";
           
           String url1 = "/srv/www/laboratorio/" + nombre + ".pdf";

           //String url1 = "/Users/eduardodisanti/Desktop/" + nombre + ".pdf";
           url = "http://medilabremoto.dyndns.org/laboratorio/" + nombre + ".pdf";

            informe.addTag("url", url);

            resp = tws.makeResponse(laresp, "INFORME", informe);


           //InputStream input = new FileInputStream("/srv/sistemas/talitakumi/analisislaboratorio.jasper");
           InputStream input = new FileInputStream("/srv/sistemas/talitakumi/FueraDeServicio.jasper");
           //InputStream input = new FileInputStream("/Users/eduardodisanti/kCreativa/Medilab/Talitakumi/Talitakumi/FueraDeServicio.jasper");
           
            
            InitialContext initalContext = new InitialContext();
            DataSource datasource = (DataSource)initalContext.lookup("talitakumidb");
            
            System.out.println("Break 3 datasource = " + datasource);
           
           Connection con = datasource.getConnection();

           System.out.println("con es " + con);

           JasperPrint jasperPrint = JasperFillManager.fillReport(input, parametros, con);

           JasperExportManager.exportReportToPdfFile(jasperPrint, url1);

           System.out.println("con es " + con + " url es " + url);
        } catch (Exception ex) {
            resp = "NAK " + ex;
        }

        return resp;
    }

}


