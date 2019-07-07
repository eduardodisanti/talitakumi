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

/**
 *
 * @author rupertus
 */
public class ServicioPreprarEmisionCarnets implements AbstractService {

    private Long token;

    private EntityManager em;
    private Logger logger;

    public ServicioPreprarEmisionCarnets() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }


    @Override
    public Object invoke(Object o) {

        token = (Long) o;
        //Query q = em.createQuery("SELECT p FROM Carnetdesalud p  where p.impreso = 0");
        //q.setParameter("token", token);
        Query query = em.createQuery("UPDATE Carnetdesalud e SET e.impreso = :token WHERE e.impreso = 0 and e.episodios.terminado='S'");
        query.setParameter("token", token);
        query.executeUpdate();

        int cant = 1;
/*
        for(Carnetdesalud cds : (List<Carnetdesalud>) q.getResultList()) {
            
            if(cds.getEpisodios().getTerminado()=='S') {
                System.out.println("CARNET A IMPRIMIR CON TOKEN " + token);
                cds.setImpreso(token);
                new ServicioAlmacenarEpisodio().invoke(cds.getEpisodios());
                System.out.println("CARNET ALMACENADO CON TOKEN " + token);
                
                Query query = em.createQuery("UPDATE Carnetdesalud e SET e.impreso = :token WHERE e.impreso = 0 and e.episodios.terminado='S'");
                query.setParameter("token", token);
                query.executeUpdate();
            }
            cant++;
        }
*/        
        return(cant);
    }

}
