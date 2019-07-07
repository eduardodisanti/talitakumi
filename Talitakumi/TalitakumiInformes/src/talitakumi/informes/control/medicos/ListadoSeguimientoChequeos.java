/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.control.medicos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import talitakumi.servicios.Reporter;

/**
 *
 * @author victoria
 */

public class ListadoSeguimientoChequeos {

    public ListadoSeguimientoChequeos (Date fechadesde){

         HashMap m = new HashMap();

         String sfechadesde;

         DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
         sfechadesde = df.format(fechadesde);
                       df = new SimpleDateFormat("yyyy-MM-dd");
         
         m.put("fechadesde", fechadesde);
         
         new Reporter("ListadoSeguimientoChequeos.jasper").listar(m);

    }


}

