/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.control;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import talitakumi.framework.DataParameter;
import talitakumi.servicios.Reporter;

/**
 *
 * @author victoria
 */
public class ListadoDeServiciosProfesionales {

    public ListadoDeServiciosProfesionales (Date fechadesde, Date fechahasta, DataParameter dp){

         HashMap m = new HashMap();

         String sfechadesde, sfechahasta;

         DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
         sfechadesde = df.format(fechadesde);
                       df = new SimpleDateFormat("yyyy-MM-dd");
         sfechahasta = df.format(fechahasta);
         
         m.put("fechadesde", fechadesde);
         m.put("fechahasta", fechahasta);
         m.put("cliente_desde", dp.get("cliente_desde"));
         m.put("cliente_hasta", dp.get("cliente_hasta"));
         m.put("tipo_factura", dp.get("tipo_factura"));
         m.put("emisor", dp.get("emisor"));



         new Reporter("ListadoDeServiciosProfesionales.jasper").listar(m);

        }

    
    
    }

