/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Episodioodontologo;
import talitakumi.model.core.Episodios;

/**
 *
 * @author rupertus
 */
public class ObtenerEstadoRevisionOdontologica implements AbstractService {

    EntityManager em;
    Logger logger;
    Episodios e;
    Collection <Episodioodontologo> estudios;

    public ObtenerEstadoRevisionOdontologica() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    public void setParameters(Object o) {

        e = (Episodios) o;
    }

    @Override
    public Object invoke(Object o) {

        this.setParameters(o);
        int scoring = 4;
        int score = 0;
        int caries = 0;
        int extracciones = 0;

        Query q = em.createQuery("select e from Episodioodontologo e where e.episodioodontologoPK.episodio = :episodio");
        q.setParameter("episodio", e.getId());
        estudios = (Collection<Episodioodontologo>) q.getResultList();
        for(Episodioodontologo ee : estudios) {

 
                score = obtenerScoringEstudio(ee);
                if(score < scoring)
                    scoring = score;

                int enf = ee.getEnfermedades().getId();
                if(enf == 24)
                    ++caries;
                if(enf == 27)
                    ++extracciones;
        }

        if((extracciones == 1 && caries >= 6) ||
           (caries > 6) ||
           (extracciones >=2)) {
            score = 2;
        }

        if(score < scoring)
               scoring = score;

        if(scoring == 4)
            scoring=0;
        return(scoring);
    }

    private int obtenerScoringEstudio(Episodioodontologo ae) {

        int x = 3;

        if(ae.getLesionblanca()=='S' || ae.getPatologiamaligna()=='S') {
            x = 1;
        }
            

        return(x);
    }
}
