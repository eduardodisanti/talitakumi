/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.engineservices;

/**
 *
 * @author rupertus
 */
public interface AbstractService {

    abstract Object invoke(Object parametros) throws Exception;
}
