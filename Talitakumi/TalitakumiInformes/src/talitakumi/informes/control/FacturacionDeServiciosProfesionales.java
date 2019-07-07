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
public class FacturacionDeServiciosProfesionales {

    public FacturacionDeServiciosProfesionales (Date fechadesde, Date fechahasta, DataParameter dp){

         HashMap m = new HashMap();
         
         m.put("fechadesde", fechadesde);
         m.put("fechahasta", fechahasta);
         m.put("cliente_desde", dp.get("cliente_desde"));
         m.put("cliente_hasta", dp.get("cliente_hasta"));
         m.put("tipo_factura", dp.get("tipo_factura"));
         m.put("emisor", dp.get("emisor"));

        System.out.println("1:" + dp.get("cliente_desde"));
        System.out.println("2:" + dp.get("cliente_hasta"));
        System.out.println("3:" + dp.get("tipo_factura"));
        System.out.println("4:" + dp.get("emisor"));

         new Reporter("FacturacionDeServiciosProfesionales.jasper").listar(m);

        }

    
    
    }

