/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.webservices;

import java.util.HashMap;

/**
 *
 * @author rupertus
 */
public interface IFObjetoSerializado {


    public void addObjectName(String aname);
    public void addTag(String aname, String Object);
    public HashMap<String, String> getObject();

    public String getObjectName();

    @Override
    public String toString();
}
