/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.DataParameter;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Personas;

/**
 *
 * @author rupertus
 */
public class BuscarPacientesConNombreParecido implements AbstractService {

    String nombres, apellidos;

    EntityManager em;
    Logger logger;

    public BuscarPacientesConNombreParecido() {
        
        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }
    public void setParameters(Object o) {

        DataParameter dp = (DataParameter) o;

        nombres = (String) dp.get("nombres");
        apellidos = (String) dp.get("apellidos");
    }

    @Override
    public Object invoke(Object o) {

        List<Episodios> p;
        this.setParameters(o);
        Query q; // = em.createQuery("SELECT Episodios(e) FROM Personas p where p.apellidos like :apellidos and p.nombres like :nombres order by p.apellidos, p.nombres");
        
        q = em.createQuery("SELECT e FROM Episodios e where e.paciente.personas.apellidos like :apellidos and e.paciente.personas.nombres like :nombres order by e.paciente.personas.apellidos, e.paciente.personas.nombres, e.paciente.personas.documento, e.fecha DESC");

        q.setParameter("apellidos", apellidos + "%");
        q.setParameter("nombres", nombres + "%");
        try {
              p = q.getResultList();
        } catch(Exception e) {

            p=null;
        }

        ArrayList<Episodios> lista = new ArrayList<Episodios>();
        String nombre = null, apellido = null;
        int documento = -1;
        for(Episodios e : p) {

            if(apellido==null) {
                apellidos = e.getPaciente().getPersonas().getApellidos();
            }
            if(nombre==null) {
                nombre = e.getPaciente().getPersonas().getNombres();
            }
            
            if(documento==-1) {
                documento = e.getPaciente().getPersonas().getDocumento();
            }


            System.out.println("NOMBRE   ES " + e.getPaciente().getPersonas().getApellidos());
            System.out.println("APELLIDO ES " + e.getPaciente().getPersonas().getNombres());
            System.out.println("DOCUMENTO   " + e.getPaciente().getPersonas().getDocumento());
            
            if(!e.getPaciente().getPersonas().getApellidos().equals(apellido) ||
               !e.getPaciente().getPersonas().getNombres().equals(nombre) || 
                e.getPaciente().getPersonas().getDocumento() != documento
              )
               {
                    lista.add(e);
                    apellido = e.getPaciente().getPersonas().getApellidos();
                    nombre = e.getPaciente().getPersonas().getNombres();
                    documento = e.getPaciente().getPersonas().getDocumento();
                    
                    System.out.println("ENTRE A AGREGAR   " + e.getPaciente().getPersonas().getDocumento());
            }
        }

        //lista.add(ultimoEpisodio);
        return(lista);
    }

}
