/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes;

import java.util.Date;
import talitakumi.framework.DataParameter;

/**
 *
 * @author rupertus
 */
public interface ListadoController {

    public void listar(Date fechadesde, Date fechahasta, DataParameter dp);
}
