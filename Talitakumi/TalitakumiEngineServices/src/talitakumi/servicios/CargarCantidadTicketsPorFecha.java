/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;

/**
 *
 * @author rupertus
 */
public class CargarCantidadTicketsPorFecha implements AbstractService {

    private EntityManager em;
    private Logger logger;
    private Date fecha;

    public CargarCantidadTicketsPorFecha() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    public void setParameters(Object o) {

        fecha = (Date) o;
    }

    @Override
    public Object invoke(Object o) {

        this.setParameters(o);
        Query q = em.createQuery("SELECT count(e) FROM Facturas e where e.fechaentregado = :fecha and e.entregado='S' and e.rollo<>0 and e.tipo='C' and e.emisor=1");
        q.setParameter("fecha", fecha);

        System.out.println("SELECT count(e) FROM Facturas e where e.fechaentregado = :fecha and e.rollo<>0 " + fecha);
        Long i = (Long) q.getSingleResult();
        System.out.println("count es " + i);
        return(i);
    }

}
