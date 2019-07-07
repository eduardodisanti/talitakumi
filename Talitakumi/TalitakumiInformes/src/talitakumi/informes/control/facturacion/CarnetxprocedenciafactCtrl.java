/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.control.facturacion;

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
public class CarnetxprocedenciafactCtrl {


    Logger logger;
     public CarnetxprocedenciafactCtrl(Date fechadesde, Date fechahasta, DataParameter dp) {

            logger = Sesion.getLogger();

            listar(fechadesde, fechahasta, dp);
     }



     public void listar(Date fechadesde, Date fechahasta, DataParameter dp) {

         HashMap m = new HashMap();

         m.put("fechadesde", fechadesde);
         m.put("fechahasta", fechahasta);
         m.put("clientedesde", dp.get("cliente_desde"));
         m.put("clientehasta", dp.get("cliente_hasta"));
         new Reporter("carnetxprocedenciafact.jasper").listar(m);
     }
}