/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import talitakumi.engine.framework.DataParameter;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Estudioshechos;

/**
 *
 * @author rupertus
 */
public class CargarExamenesSinValidar implements AbstractService {

    private EntityManager em;
    private Date fecha;
    private Integer resultados;

    public CargarExamenesSinValidar() {

        this.em = Sesion.getEntitymanager();
    }

    private void setParameters(Object o) {

        DataParameter hm = (DataParameter) o;

        fecha = (Date) hm.get("fecha");
        resultados = (Integer) hm.get("resultados");
        
        if(resultados==null) {
            resultados = 9000;
        }
    }

    @Override
    public Object invoke(Object o) {

            this.setParameters(o);
            Date hoy = fecha;
            
            System.out.println("Cargando exámenes sin validar de fecha " + hoy);
            String qry = "SELECT a FROM Estudioshechos a WHERE a.supervisor = 0 and a.episodio.fecha =:hoy order by a.episodio.fecha, a.episodio.paciente.personas.documento, a.episodio.paciente.personas.apellidos, a.episodio.paciente.personas.nombres, a.episodio.id, a.id";
            System.out.println("CargarAgendaConsultorio es : " + qry);
            Query q = em.createQuery(qry);
            q.setMaxResults(resultados);
            q.setParameter("hoy", hoy);
            
            List<Estudioshechos> l = q.getResultList();
            
            System.out.println("Cargados exámenes sin validar " + l.size());
            return(l);
    }
}
