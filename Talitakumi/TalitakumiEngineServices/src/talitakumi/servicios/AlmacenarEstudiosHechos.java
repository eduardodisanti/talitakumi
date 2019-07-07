/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Vector;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Analisishechos;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Estudioshechos;
import talitakumi.model.core.Numerador;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Actividades;

/**
 *
 * @author rupertus
 */
public class AlmacenarEstudiosHechos implements AbstractService {

    private Vector <Estudioshechos> eh;
    private EntityManager em;
    private Logger logger;
    private Episodios epi;

    public AlmacenarEstudiosHechos() {

        em = Sesion.getEntitymanager();
        logger = Sesion.getLogger();
    }

    public void setParameters(Object o) {

        epi =  (Episodios) o;
    }

    public Object invoke(Object o) {

        epi =  (Episodios) o;

        eliminarEstudiosViejos(epi);
        Actividades idact = epi.getTipoepisodio();
        boolean res = false;
        Episodios oldepi;

        epi =  (Episodios) o;

        oldepi = em.find(Episodios.class, epi.getId());
        //EntityTransaction tx = em.getTransaction();
        //tx.begin();

        eh = (Vector<Estudioshechos>) epi.getEstudioshechosCollection();

        for(Estudioshechos e : eh) {

            if(e.getId()==null) {
                Numerador numerador = new Numerador();
                numerador = em.find(Numerador.class, 6);
                //em.lock(numerador,  LockModeType.READ);

                numerador.setValor(numerador.getValor() + 1);
                em.persist(numerador);
                em.merge(numerador);
                e.setId(numerador.getValor());
                Vector <Analisishechos> ah = (Vector<Analisishechos>) e.getAnalisishechosCollection();
                for(Analisishechos a : ah) {
                           a.setEstudiohecho(e);
                           //a.setId(null);
                }
            }

//            em.persist(e);
//            em.merge(e);

        }
        oldepi.setEstudioshechosCollection(eh);
        epi = oldepi;
        if(epi.getCarnetdesalud()!=null)
            epi.getCarnetdesalud().setVigencialaboratorio(0);
        epi.setTipoepisodio(idact);
        em.merge(epi);
        em.persist(epi);
        em.flush();

        AbstractService as = new ObtenerEstadoExamenesLaboratorio();

        Integer score = 0;
        int vigencia = -1;
        try {
            score = (Integer) as.invoke(epi);
            System.out.println("Score al almacenar es : " + score);
        } catch(Exception ex) {

            System.out.println("Error almacenar estudios hechos obtener estado : " + ex);
        }
        if(score==3)
            vigencia = 24;
        else
            if(score==2)
                vigencia = 24;
            else
                vigencia = 0;

        if(epi.getCarnetdesalud()!=null)
            epi.getCarnetdesalud().setVigencialaboratorio(vigencia);

        em.merge(epi);
        em.flush();
        //tx.commit();
        res = true;
        return(res);
    }

    public Object getResultado() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void eliminarEstudiosViejos(Episodios epi) {

        Episodios ane = (Episodios) new CargarEpisodio().invoke(epi.getId());

        for(Estudioshechos ee : ane.getEstudioshechosCollection()) {

            System.out.println("EE es " + ee);
            em.remove(ee);
            em.flush();
        }
    }

}
