/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.engine.framework;

/**
 *
 * @author rupertus
 */
public interface IDataParameter {

    public void set(String clave, Object valor);
    public Object get(String clave);
}
