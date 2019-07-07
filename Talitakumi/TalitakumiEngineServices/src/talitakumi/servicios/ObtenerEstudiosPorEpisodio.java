/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Collection;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Analisis;
import talitakumi.model.core.Analisishechos;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.EpisodiosExAdicionales;
import talitakumi.model.core.Estudios;

/**
 *
 * @author rupertus
 */
public class ObtenerEstudiosPorEpisodio implements AbstractService {

    EntityManager em;
    Logger logger;

    private Episodios episodio;

    public ObtenerEstudiosPorEpisodio () {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }


    @Override
    public Object invoke(Object o) {

        episodio = (Episodios) o;

        Vector <Estudios> estudios; // - TODO traerlo de una base de datos

        estudios = (Vector<Estudios>) new ObtenerEstudiosPorActividad().invoke(episodio.getTipoepisodio().getId());

        // Agora viene la carga de examenes complementarios :

        String qry = "select ex from EpisodiosExAdicionales ex where ex.episodio.id = :epi";
        Query q = em.createQuery(qry);
        q.setParameter("epi", episodio.getId());

        for(EpisodiosExAdicionales eea : (Collection <EpisodiosExAdicionales>)q.getResultList()) {

            for(Analisis a : eea.getEstudio().getAnalisisCollection()) {}
            estudios.add(eea.getEstudio());
        }
        return(estudios);
    }

    private int obtenerScoringAnalisis(Analisishechos ae) {

        int x = 0;
        float min, max, hallado;

        Analisis a = ae.getAnalisis();
        char disc = a.getTiporesultado();

        switch (disc) {

            case 'T' :
                   if(a.getValorrefminimo().equals(ae.getValorhallado()))
                       x = 1;
                   else
                       x = 2;
                   break;

            case 'N' :
                   max = Float.parseFloat(a.getValorrefmaximo());
                   min = Float.parseFloat(a.getValorrefminimo());
                   hallado = Float.parseFloat(ae.getValorhallado());

                   if(hallado >= min && hallado <= max)
                       x = 1;
                   else
                       x = 2;
                   break;
        }

        return(x);
    }
}
