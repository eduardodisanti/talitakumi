/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.control.estadisticas;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import talitakumi.framework.DataParameter;
import talitakumi.servicios.Reporter;

/**
 *
 * @author victoria
 */
public class ListadoVencimientoCarnetDeSalud {

    public ListadoVencimientoCarnetDeSalud(Date fechadesde, Date fechahasta, DataParameter dp)
 {
        HashMap m = new HashMap();

        Calendar cale = GregorianCalendar.getInstance();

        cale.setTime(fechadesde);
        cale.add(Calendar.YEAR, -2);

         m.put("fecha_desde", cale.getTime());
         m.put("fecha_hasta", fechahasta);
         m.put("fecha_hoy", fechadesde);

         new Reporter("vencimientocds.jasper").listar(m);

        }

}
