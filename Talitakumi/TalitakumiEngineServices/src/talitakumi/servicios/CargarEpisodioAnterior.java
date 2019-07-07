/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Episodioodontologo;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Pacientes;

/**
 *
 * @author rupertus
 */
public class CargarEpisodioAnterior implements AbstractService {

    EntityManager em;
    Logger logger;

    public CargarEpisodioAnterior() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        Episodios lastE = null;
        Integer id = (Integer) o;

        em.flush();
        Episodios e = em.find(Episodios.class, id);

        if(e==null) {
            System.out.println("CargarEpisodioAnterior : error al cargar el episodio : " + id);
        } else {
            Episodios theE = (Episodios) new CargarEpisodio().invoke(id);
            Pacientes p = theE.getPaciente();

            String qry = "select e from Episodios e where e.paciente.id = :paciente and e.id <> :id order by e.id desc";
            System.out.println("Consulta de episodios : " + qry);

            Query q = em.createQuery(qry);
            q.setMaxResults(1);
            q.setParameter("paciente", p.getId());
            q.setParameter("id", id);

            lastE = (Episodios) q.getSingleResult();
            for(Episodioodontologo eo : lastE.getEpisodioodontologoCollection()) {}
        }

        if(lastE!=null) {
            lastE = (Episodios) new CargarEpisodio().invoke(lastE.getId());
            System.out.println("lastE = " + lastE + " eo = " + lastE.getEpisodioodontologoCollection());
        }
        return(lastE);
    }
}
