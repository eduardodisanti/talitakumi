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
import talitakumi.model.core.Estudios;

/**
 *
 * @author rupertus
 */
public class ObtenerEstudiosPorActividad implements AbstractService {

    EntityManager em;
    Logger logger;

    int actividad;

    public ObtenerEstudiosPorActividad () {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }


    @Override
    public Object invoke(Object o) {

        actividad = (Integer) o;

        Vector <Estudios> estudios = new Vector(); // - TODO traerlo de una base de datos

        if(actividad!=12 && actividad!=13) {

            Integer actividadmayor = actividad;
            Integer actividadmenor = 0;

            if(actividad > 100) {
                  actividadmayor = actividad / 100;
                  actividadmenor = actividad % 100;
            }

            System.out.println("Actividad mayor = " + actividadmayor);
            System.out.println("Actividad menor = " + actividadmenor);
            
            if(actividadmayor >= 2 && actividadmayor <10 || actividad==2000) {

                Estudios e = new Estudios();
                for(int i = 1; i<= 4; i++) {
                    
                        Query q = em.createNamedQuery("Estudios.findById");
                        q.setParameter("id", i);
                        e = (Estudios) q.getSingleResult();

                        Collection<Analisis> ca =  e.getAnalisisCollection();
                        for(Analisis a : ca) {
                            a.getId();
                        }
                        estudios.add(e);
                    
                }

               if(actividadmayor == 6) {
                    Query q = em.createNamedQuery("Estudios.findById");
                    q.setParameter("id", 5);
                    e = (Estudios) q.getSingleResult();

                    Collection<Analisis> ca =  e.getAnalisisCollection();
                    for(Analisis a : ca) {
                        a.getId();
                    }
                    estudios.add(e);
               }

               if(actividadmayor == 8) {
                    Query q = em.createNamedQuery("Estudios.findById");
                    q.setParameter("id", 5);
                    e = (Estudios) q.getSingleResult();

                    Collection<Analisis> ca =  e.getAnalisisCollection();
                    for(Analisis a : ca) {
                        a.getId();
                    }
                    estudios.add(e);
               }

               if(actividadmayor == 5) {
                    Query q = em.createNamedQuery("Estudios.findById");
                    q.setParameter("id", 5);
                    e = (Estudios) q.getSingleResult();

                    Collection<Analisis> ca =  e.getAnalisisCollection();
                    for(Analisis a : ca) {
                        a.getId();
                    }
                    estudios.add(e);

                    q = em.createNamedQuery("Estudios.findById");
                    q.setParameter("id", 8);
                    e = (Estudios) q.getSingleResult();

                    ca =  e.getAnalisisCollection();
                    for(Analisis a : ca) {
                        a.getId();
                    }
                    estudios.add(e);
               }
                if(actividadmenor == 1) {
                    Query q = em.createNamedQuery("Estudios.findById");
                    q.setParameter("id", 7);
                    e = (Estudios) q.getSingleResult();

                    Collection<Analisis> ca =  e.getAnalisisCollection();
                    for(Analisis a : ca) {
                        a.getId();
                    }
                    estudios.add(e);
                }
            }
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
