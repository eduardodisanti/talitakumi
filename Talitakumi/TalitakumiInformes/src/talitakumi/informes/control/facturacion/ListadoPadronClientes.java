/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.control.facturacion;

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
public class ListadoPadronClientes {

    public ListadoPadronClientes (){

         HashMap m = new HashMap();

         new Reporter("ListadoPadronClientes.jasper").listar(m);

        }


}
