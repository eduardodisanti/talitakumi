/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.control.mantenimiento;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import talitakumi.framework.DataParameter;
import talitakumi.informes.ListadoController;
import talitakumi.servicios.Reporter;

/**
 *
 * @author rupertus
 */
class ListadoMantenimentoCtrl implements ListadoController{

    String informe;


    public ListadoMantenimentoCtrl(String elinforme) {

        informe = elinforme;
    }

    @Override
    public void listar(Date fechadesde, Date fechahasta, DataParameter dp) {

         HashMap m = new HashMap();

         String sfechadesde, sfechahasta;

         DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
         sfechadesde = df.format(fechadesde);
                       df = new SimpleDateFormat("yyyy-MM-dd");
         sfechahasta = df.format(fechahasta);
         m.put("fecha_desde", sfechadesde);
         m.put("fecha_hasta", sfechahasta);
         new Reporter(informe).listar(m);
    }

}
