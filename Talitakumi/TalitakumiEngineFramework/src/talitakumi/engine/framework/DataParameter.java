/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.engine.framework;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author rupertus
 */
public class DataParameter implements IDataParameter, Serializable {

    private HashMap<String, Object> h;

    public DataParameter() {

        h = new HashMap();
    }

    public void set(String clave, Object valor) {

        h.put(clave, valor);
    }

    public Object get(String clave) {

        return(h.get(clave));
    }

    
}
