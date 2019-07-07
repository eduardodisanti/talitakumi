/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import talitakumi.framework.*;
import talitakumi.model.core.Personas;



/**
 *
 * @author rupertus
 */
public class ConvertPersona implements Converter {

    @Override
    public DataParameter fromObject(Object p) {

        DataParameter dp = new DataParameter();

        Personas o = (Personas) p;
        dp.set("id", o.getId());
        dp.set("apellidos", o.getApellidos());
        dp.set("ciudad", o.getCiudad());
        dp.set("nombres", o.getNombres());
        dp.set("fechanacimiento", o.getFechanacimiento());
        dp.set("documento", o.getDocumento());
        dp.set("telefono", o.getTelefono());
        dp.set("sexo", o.getSexo());
        dp.set("email", o.getEmail());
        dp.set("domicilio", o.getDomicilio());
        dp.set("movil", o.getMovil());
        dp.set("foto", o.getFoto());
        dp.set("telefono", o.getTelefono());

        return(dp);
    }

    @Override
    public Personas fromDP(DataParameter dp) {

        Personas p = null;



        return(p);
    }

}
