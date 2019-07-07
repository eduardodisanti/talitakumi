/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Date;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Pacientes;
import talitakumi.model.core.Personas;
import talitakumi.model.core.Seguros;

/**
 *
 * @author rupertus
 */

public class ServicioGuardarPersona implements AbstractService {

    Personas p;

    EntityManager em;
    Logger logger;

    Object result;


    public ServicioGuardarPersona() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        p = (Personas) o;
        this.almacenar();
        return(p);
    }

        public Personas almacenar() {

        Boolean nuevo = true;
        Personas pers = null;

        if(p.getId()!=null)
           nuevo = p.getId()==0;
        else
           nuevo = true;

        System.out.println(" --------------- NUEVO = " + nuevo);
        
        if(!nuevo) {
            pers = em.find(Personas.class, p.getId());
        } else
            pers = p;

        pers.setApellidos(p.getApellidos());
        pers.setCiudad(p.getCiudad());
        pers.setDocumento(p.getDocumento());
        pers.setDomicilio(p.getDomicilio());
        pers.setEmail(p.getEmail());
        pers.setFechanacimiento(p.getFechanacimiento());
        pers.setFoto(p.getFoto());
        pers.setGeoX(p.getGeoX());
        pers.setGeoY(p.getGeoY());
        pers.setId(p.getId());
        pers.setMovil(p.getMovil());
        pers.setNombres(p.getNombres());
        pers.setSexo(p.getSexo());
        pers.setTelefono(p.getTelefono());

        Pacientes pac = p.getPacientes();

        if(pac==null) {

            System.out.println("========= PACIENTE NUEVO ");
            pac = new Pacientes();
            pac.setPersonas(pers);
            pac.setFechaingreso(new Date());
            pac.setId(pers.getId());
        }

        if(pac.getSeguro()==null) {

            Seguros seguro = new Seguros();
            seguro.setId(0);

            seguro = em.find(Seguros.class, 0);

            pac.setSeguro(seguro);

        }
        pers.setPacientes(pac);
        if(p.getPacientes()!=null)
            pac.setVencimientosdocCollection(p.getPacientes().getVencimientosdocCollection());

        if(nuevo) {
            pers.setPacientes(null);
            em.persist(pers);
            em.flush();
            pac.setId(pers.getId());
            pac.setPersonas(pers);
            em.persist(pac);
            pers.setPacientes(pac);
            System.out.println("ID DE LA PERSONA ES " + pac.getId());
        }

        p = em.merge(pers);
        em.flush();

        return(p);
    }
}
