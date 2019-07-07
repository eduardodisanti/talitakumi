/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.webservices.agenda;

import java.util.HashMap;
import talitakumi.webservices.IFObjetoSerializado;

/**
 *
 * @author rupertus
 */
public class PersonaSerializada implements IFObjetoSerializado {

    private String nombre;
    HashMap <String, String> objeto;

    public PersonaSerializada() {

        nombre = "PERSONA";
        objeto = new HashMap<String, String>();
    }

    public void addObjectName(String aname) {

        nombre = aname;
    }

    public void addTag(String aname, String avalue) {

        objeto.put(aname, avalue);
    }

    public HashMap<String, String> getObject() {

        return(objeto);
    }

    public String getObjectName() {
        return(nombre);
    }


}
