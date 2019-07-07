/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.EpisodiosExAdicionales;
import talitakumi.model.core.Estudioshechos;

/**
 *
 * @author rupertus
 */

public class CargarEpisodio implements AbstractService {

    EntityManager em;
    Logger logger;


    public CargarEpisodio() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();

    }

    @Override
    public Object invoke(Object o) {

        Integer id = (Integer) o;

        em.flush();
        Episodios e = em.find(Episodios.class, id);

        
        if(e==null) {
            System.out.println("CargarEpisodio : error al cargar el episodio : " + id);
        } else {

                    for(EpisodiosExAdicionales t : e.getEpisodiosExAdicionalesCollection()){}

                    e.getCarnetdesalud();
                    System.out.println("CDS = " + e.getCarnetdesalud());
                    for(Object t : e.getEpisodioodontologoCollection()) {}
                    for(Object t : e.getEpisodiosenfermedadesCollection()) {}
                    for(Object t : e.getEpisodiosmedicamentosCollection()) {}
                    for(Object t : e.getEpisodiosviciosCollection()) {}
                    for(Object t : e.getEpisodiosantecedentesCollection()) {}
                    for(Object t : e.getEpisodiosestudiosCollection()) {}
                    for(Object t : e.getEpisodiosvacunasCollection()) {}
                    for(Object t : e.getEpisodioexamenfisicoCollection()) {}
                    
                    for(Estudioshechos t : e.getEstudioshechosCollection()) {
                        for(Object z : t.getAnalisishechosCollection()) {}
                    }
                    for(Object vd : e.getPaciente().getVencimientosdocCollection()) {}
                    for(Object t : e.getAgendaCollection()) {}
        }
        return(e);
    }

}
