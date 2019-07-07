/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.control;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.apache.log4j.Logger;
import talitakumi.framework.DataParameter;
import talitakumi.framework.Sesion;
import talitakumi.servicios.Reporter;


/**
 *
 * @author rupertus
 */
 public class ListadoActosDiaCtrl {


    Logger logger;
    public ListadoActosDiaCtrl(Date fechadesde, Date fechahasta, DataParameter dp) {

         logger = Sesion.getLogger();

            listar(fechadesde, fechahasta, dp);
     }



     public void listar(Date fechadesde, Date fechahasta, DataParameter dp) {

         HashMap m = new HashMap();

         String sfechadesde, sfechahasta;

         DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
         sfechadesde = df.format(fechadesde);
                       df = new SimpleDateFormat("yyyy-MM-dd");
         sfechahasta = df.format(fechahasta);

         m.put("fechadesde", fechadesde);
         m.put("fechahasta", fechahasta);
//         m.put("cliente_desde", dp.get("cliente_desde"));
//         m.put("cliente_hasta", dp.get("cliente_hasta"));
//         m.put("tipo_factura", dp.get("tipo_factura"));


         new Reporter("listadoactosxdia.jasper").listar(m);
     }
}