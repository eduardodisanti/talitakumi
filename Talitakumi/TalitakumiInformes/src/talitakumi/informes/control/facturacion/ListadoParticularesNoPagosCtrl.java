/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.control.facturacion;

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
public class ListadoParticularesNoPagosCtrl {

    Logger logger;
    public ListadoParticularesNoPagosCtrl(Date fechadesde, Date fechahasta, DataParameter dp) {

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
         m.put("fecha_desde", sfechadesde);
         m.put("fecha_hasta", sfechahasta);
         m.put("clientedesde", dp.get("cliente_desde"));
         m.put("clientehasta", dp.get("cliente_hasta"));

         new Reporter("listadoparticularesnopagos.jasper").listar(m);
     }
}
