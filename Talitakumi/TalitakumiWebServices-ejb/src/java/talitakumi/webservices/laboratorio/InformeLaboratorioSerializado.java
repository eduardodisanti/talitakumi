/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.webservices.laboratorio;

import java.util.HashMap;
import talitakumi.webservices.IFObjetoSerializado;

/**
 *
 * @author eduardodisanti
 */
public class InformeLaboratorioSerializado implements IFObjetoSerializado {
    private String nombre;
    HashMap <String, String> objeto;

    public InformeLaboratorioSerializado() {

        nombre = "INFORME_LABORATORIO";
        objeto = new HashMap<String, String>();
    }

    @Override
    public void addObjectName(String aname) {

        nombre = aname;
    }

    @Override
    public void addTag(String aname, String avalue) {

        objeto.put(aname, avalue);
    }

    @Override
    public HashMap<String, String> getObject() {

        return(objeto);
    }

    @Override
    public String getObjectName() {
        return(nombre);
    }
}
