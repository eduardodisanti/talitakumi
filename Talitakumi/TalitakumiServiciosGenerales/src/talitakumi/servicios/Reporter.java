/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package talitakumi.servicios;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author rupertus
 */

public class Reporter {

    String reporte;

    public Reporter(String archivo) {

        reporte = archivo;
    }

    public void listar( Map parametros) {

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://angus:3306/talitakumi?user=root&password=root");

            //JasperDesign jasperDesign = JRXmlLoader.load(reporte);
            //JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            InputStream input = new FileInputStream(reporte);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(input, parametros, con);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception ex) {
            String connectMsg = "Could not create the report " + ex.getMessage() + " " + ex.getLocalizedMessage();
            System.out.println(connectMsg);
            System.out.println("Reporte es : " + reporte);
            System.out.println("Error jasper es " + ex);


        }
    }
}
