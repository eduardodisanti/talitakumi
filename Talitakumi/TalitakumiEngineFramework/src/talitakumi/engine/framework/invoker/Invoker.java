/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.engine.framework.invoker;

/**
 *
 * @author rupertus
 */
public interface Invoker {

    public Object invoke(String nombreservicio, Object parametros) throws Exception;
    public Object invoke(Class unaClase, Object parametros) throws Exception;
}
