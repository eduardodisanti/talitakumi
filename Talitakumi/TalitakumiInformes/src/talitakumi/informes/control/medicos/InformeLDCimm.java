/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.control.medicos;

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
public class InformeLDCimm {

    public InformeLDCimm (Date fechadesde, Date fechahasta){

         HashMap m = new HashMap();

         String sfechadesde, sfechahasta;

         DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
         sfechadesde = df.format(fechadesde);
                       df = new SimpleDateFormat("yyyy-MM-dd");
         sfechahasta = df.format(fechahasta);


         m.put("fechadesde", fechadesde);
         m.put("fechahasta", fechahasta);

         new Reporter("EstadisticaLDCimm.jasper").listar(m);

        }



    }

