/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.laboratorio.ctrl;

import java.util.Date;
import java.util.HashMap;
import org.apache.log4j.Logger;
import talitakumi.framework.Sesion;
import talitakumi.servicios.Reporter;

/**
 *
 * @author rupertus
 */
class ImprimirListadoDiarioLaboratorioController {

    Logger logger;
    public ImprimirListadoDiarioLaboratorioController() {

            logger = Sesion.getLogger();

    }

     public void setFecha(Date f) {

         listar(f, f);
     }
     public void listar(Date fechadesde, Date fechahasta) {

         HashMap m = new HashMap();

         m.put("fechahoy", fechadesde);
         //m.put("fecha_hasta", sfechahasta);
         new Reporter("planillalaboratorio.jasper").listar(m);
     }

}
