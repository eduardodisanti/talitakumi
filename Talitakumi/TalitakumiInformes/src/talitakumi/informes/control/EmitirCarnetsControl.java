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
import talitakumi.framework.Sesion;
import talitakumi.servicios.Reporter;


/**
 *
 * @author rupertus
 */
public class EmitirCarnetsControl {


    Logger logger;
     public EmitirCarnetsControl() {

            logger = Sesion.getLogger();

            listar();
     }



     public void listar() {

         HashMap m = new HashMap();

         long token = (new Date()).getTime();
         
         Sesion.getServiceDelegatorRemote().invoke("ServicioPreprarEmisionCarnets", token);
         m.put("fecha_desde", new Date());
         m.put("token", token);
         new Reporter("EmitirCarnets.jasper").listar(m);
         //new Reporter("ResumenDeCajaEfectivo1.jasper").listar(m);
         
         int i=0;
         i++;
     }
}