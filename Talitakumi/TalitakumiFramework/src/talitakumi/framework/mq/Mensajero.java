/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.framework.mq;

import java.util.HashMap;
import talitakumi.framework.Sesion;

/**
 *
 * @author rupertus
 */
public class Mensajero {

    String de, para, mensaje;
    public Mensajero() {

    }

    public void enviar(String de, String para, String mensaje) {

        HashMap hm = new HashMap();

        hm.put("de", de);
        hm.put("para", para);
        hm.put("texto", mensaje);
        
        Sesion.getServiceDelegatorRemote().invoke("EnviarMensaje", hm);
    }
}
