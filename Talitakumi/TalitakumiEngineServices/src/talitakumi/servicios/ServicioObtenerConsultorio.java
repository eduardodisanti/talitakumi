/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.DataParameter;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.localonserver.AbstractLocalService;
import talitakumi.model.core.Consultorios;
import talitakumi.model.core.Consultoriosactivos;
import talitakumi.model.core.Roundrobin;

/**
 *
 * @author rupertus
 */
public class ServicioObtenerConsultorio implements AbstractLocalService {
    Integer actividad;
    Boolean inmediato;
    DataParameter dp;
    EntityManager em;
    Logger logger;
    private Consultorios consultorio;
    private char urgente;


    public ServicioObtenerConsultorio() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    @Override
    public void setParameters(Object o) {

        dp = (DataParameter)o;
        actividad = (Integer) dp.get("actividad");
        inmediato = (Boolean) dp.get("inmediato");

        if (inmediato == true)
             urgente = 'S';
        else urgente = 'N';

    }

    @Override
    public Object invoke() {


        Roundrobin r = obtenerConsultorioAnterior();
        Consultorios cons = null;
        Consultorios primercons = null;
        
        boolean pv = true;
        Integer elultimo = r.getUltimo();

System.out.println("==> BREAK SOC #1");
        Query q = em.createQuery("SELECT p FROM Consultoriosactivos p where p.activo = 'S'");

        List <Consultoriosactivos>listac = q.getResultList();
        boolean flag = false;
System.out.println("==> BREAK SOC #2");
        for(Consultoriosactivos ca : listac) {

            if(inmediato.equals(Boolean.TRUE) && ca.getConsultorios().getHabilitadourgente().equals("S") ||
               inmediato.equals(Boolean.FALSE) && (ca.getConsultorios().getHabilitadourgente().equals("N"))
               ) {
            if(pv) {

                primercons = ca.getConsultorios();
                pv = false;
            }

            if(flag) {
               cons = ca.getConsultorios();
               break;
            }

            int c = ca.getConsultorios().getId();
            if(c == elultimo) {

                flag = true;
                System.out.println(flag);
            }
          }
        }
        if(!flag)
           cons = primercons;

        if(cons==null)
            cons = primercons;

        System.out.println("CONS = " + cons);
        return(cons);
    }

    @Override
    public Object getResultado() {
        return(consultorio);
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    private Roundrobin obtenerConsultorioAnterior() {

        System.out.println("===>ServicioObtenerConsultorio : obtenerConsultorioAnterior actividad="+actividad);
        Query q = (Query) em.createNamedQuery("Roundrobin.findByActividad");
        q.setParameter("actividad", actividad);
        Roundrobin r = (Roundrobin) q.getSingleResult();

        return(r);
    }
}
