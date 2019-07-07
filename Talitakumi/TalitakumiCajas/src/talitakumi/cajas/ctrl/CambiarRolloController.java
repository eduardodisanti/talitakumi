/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.cajas.ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import org.apache.log4j.Logger;
import talitakumi.cajas.ui.UICambiarRollo;
import talitakumi.engine.framework.DataParameter;
import talitakumi.framework.Sesion;
import view.DialogoError;

/**
 *
 * @author rupertus
 */
public class CambiarRolloController {

    UICambiarRollo uic;

    Logger logger;

    public CambiarRolloController() {

        logger = Sesion.getLogger();
        uic = new UICambiarRollo(null, true);
        uic.setCambiarListener(new CambiarListener());
        uic.setCancelarListener(new CancelarListener());
        uic.setVisible(true);
    }

    private void cambiarRollo() {

        Integer rollo = uic.getRollo();
        Integer emisor = uic.getEmisor();
        String msg;

        DataParameter dp = new DataParameter();
        dp.set("emisor", emisor);
        dp.set("rollo", rollo);
        if(rollo != 0) {

            try {
                boolean result = (Boolean) Sesion.getServiceDelegatorRemote().invoke("ServicioCambiarRollo", dp);
               //(Boolean) Sesion.getServiceDelegatorRemote().invoke("ServicioCambiarRollo", rollo);
                if(result)
                   msg = "Se ha cambiado el rollo";
                else
                  msg = "No se ha cambiado el rollo";
            }  catch (Exception ex) {
                logger.error(new Date()+" error llamando ServicioCambiarRollo");
                logger.error(ex);
                msg = "No se ha cambiado el rollo por un error interno";
            }

        } else {

            msg = "No se ha cambiado el rollo";
        }

        new DialogoError(null, "Cambio de rollo", msg, "<h2>"+msg+"</h2>", true).setVisible(true);
        salir();
    }

    private void salir() {

        uic.setVisible(false);
        uic.dispose();
    }

    public class CambiarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            cambiarRollo();
        }
    }

        public class CancelarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            salir();
        }
    }
}
