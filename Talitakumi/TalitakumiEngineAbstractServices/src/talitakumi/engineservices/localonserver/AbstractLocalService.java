/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.engineservices.localonserver;

/**
 *
 * @author rupertus
 */
public interface AbstractLocalService {

    abstract void setParameters(Object o);
    abstract Object invoke() throws Exception;
    abstract Object getResultado();
}
