/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.control.estadisticas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import talitakumi.framework.DataParameter;
import talitakumi.servicios.Reporter;

/**
 *
 * @author rupertus
 */
public class Estadisticaldc1Ctrl {

     private List listafacturas;

     public Estadisticaldc1Ctrl(Date fechadesde, Date fechahasta, DataParameter dp) {

            listar(fechadesde, fechahasta, dp);
     }

     public void listar(Date fechadesde, Date fechahasta, DataParameter dp) {

         HashMap m = new HashMap();

         m.put("fechadesde", fechadesde);
         m.put("fechahasta", fechahasta);
         m.put("clientedesde", dp.get("cliente_desde"));
         m.put("clientehasta", dp.get("cliente_hasta"));
         m.put("academiadesde", dp.get("academiadesde"));
         m.put("academiahasta", dp.get("academiahasta"));

         new Reporter("libretasestadistica1.jasper").listar(m);
     }
}
