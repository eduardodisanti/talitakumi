package talitakumi.servicios;

import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Episodios;

public class ServicioAlmacenarEpisodio implements AbstractService {

    private EntityManager em;
    private Logger logger;
    Episodios e;

    public ServicioAlmacenarEpisodio() {

        em = Sesion.getEntitymanager();
        logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        e =  (Episodios) o;
        Episodios epis = em.find(Episodios.class, e.getId());
        boolean res = false;

        if(epis==null) {
            System.out.println("=====> EPISODIO NUEVO <=====");
            em.persist(e);
        }
        
        em.merge(e);

        em.flush();
        res = true;
        return(res);
    }
}
