package talitakumi.servicios;

import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Agenda;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.AgendaPK;
import talitakumi.model.core.Episodios;

public class ServicioModificarAgenda implements AbstractService {

    private EntityManager em;
    private Logger logger;
    Agenda a;

    public ServicioModificarAgenda() {

        em = Sesion.getEntitymanager();
        logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        Agenda age;
        AgendaPK agepk;
        a =  (Agenda) o;

        boolean res = false;

        try {

            Episodios e = a.getEpisodio();

            age = em.find(Agenda.class, a.getAgendaPK());
            if(age!=null)
                agepk = age.getAgendaPK();
            else {
                age = a;
                agepk = a.getAgendaPK();
            }

        a = age;
        a.setAgendaPK(agepk);

        if(e!=null) {
          a.setEpisodio(em.merge(e));
        } else
          a.setEpisodio(null);

        em.persist(a);
        em.merge(a);

        em.flush();
        res = true;
        } catch(Exception ex) {
            System.out.println(" false PORQUE : " +ex);
            res = false;
        }
        return(res);
    }
}
