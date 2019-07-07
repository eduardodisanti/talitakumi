/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.services;

/**
 *
 * @author rupertus
 */
public interface AbstractService {

    abstract void setParameters(Object o);
    abstract Object invoke() throws Exception;
    abstract Object getResultado();
}
