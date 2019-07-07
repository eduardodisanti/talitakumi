/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.framework;

/**
 *
 * @author rupertus
 */
public interface Converter {

    DataParameter fromObject(Object o);
    Object fromDP(DataParameter dp);
}
