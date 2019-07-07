/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.framework;

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

    @Override
    public void set(String clave, Object valor) {

        h.put(clave, valor);
    }

    @Override
    public Object get(String clave) {

        return(h.get(clave));
    }

    
}
