/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.control.caja;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.apache.log4j.Logger;
import talitakumi.framework.Sesion;
import talitakumi.servicios.Reporter;


/**
 *
 * @author rupertus
 */
public class ListadoCajaControl {


    Logger logger;
     public ListadoCajaControl(Date fechadesde, Date fechahasta) {

            logger = Sesion.getLogger();

            listar(fechadesde, fechahasta);
     }



     public void listar(Date fechadesde, Date fechahasta) {

         HashMap m = new HashMap();

         String sfechadesde, sfechahasta;

         DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
         sfechadesde = df.format(fechadesde);
                       df = new SimpleDateFormat("yyyy-MM-dd");
         sfechahasta = df.format(fechahasta);
         m.put("fecha_desde", sfechadesde);
         m.put("fecha_hasta", sfechahasta);


         new Reporter("listadocaja.jasper").listar(m);
         new Reporter("ResumenDeCajaEfectivo.jasper").listar(m);
     }
}