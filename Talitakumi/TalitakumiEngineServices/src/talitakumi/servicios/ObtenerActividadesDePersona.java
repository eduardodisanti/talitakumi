/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Analisis;
import talitakumi.model.core.Analisishechos;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Personas;

/**
 *
 * @author rupertus
 */
public class ObtenerActividadesDePersona implements AbstractService {

    EntityManager em;
    Logger logger;

    Personas persona;

    public ObtenerActividadesDePersona () {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }


    @Override
    public Object invoke(Object o) {

        persona = (Personas) o;

        List <Episodios>l = new ArrayList();

        String query = "select e from Episodios e where e.paciente.id=" + persona.getPacientes().getId() + " order by e.fecha desc";
        System.out.println(query);
        l = (List<Episodios>) em.createQuery(query).getResultList();
        return(l);
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
