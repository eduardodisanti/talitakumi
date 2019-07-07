/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.control;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import talitakumi.framework.DataParameter;
import talitakumi.servicios.Reporter;

/**
 *
 * @author rupertus
 */
public final class ListadoCJP {

     public ListadoCJP(Date fechadesde, Date fechahasta, DataParameter dp) throws Exception {

            listar(fechadesde, fechahasta, dp);
     }

     public void listar(Date fechadesde, Date fechahasta, DataParameter dp) throws Exception {

         HashMap m = new HashMap();

         String sfechadesde, sfechahasta;
         int dto = 10;
         int emisor = 1;

         Properties defaultProps = new Properties();
         FileInputStream in = new FileInputStream("talitakumi.properties");
         defaultProps.load(in);
         in.close();

         dto = Integer.parseInt(defaultProps.getProperty("CJP_SOUPER", "10"));

         DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
         sfechadesde = df.format(fechadesde);
                       df = new SimpleDateFormat("yyyy-MM-dd");
         sfechahasta = df.format(fechahasta);
         m.put("fecha_desde", sfechadesde);
         m.put("fecha_hasta", sfechahasta);
         m.put("clientedesde", dp.get("cliente_desde"));
         m.put("clientehasta", dp.get("cliente_hasta"));
         m.put("REPORT_SOUPER", dto);
         m.put("emisor", dp.get("emisor"));
         new Reporter("listadocjp.jasper").listar(m);
     }
}
