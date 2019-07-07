/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Episodioodontologo;
import talitakumi.model.core.Episodios;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.EpisodioodontologoPK;
import talitakumi.model.core.Vencimientosdoc;

/**
 *
 * @author rupertus
 */
public class AlmacenarEpisodioOdontologo implements AbstractService {

    Vector <Episodioodontologo> eh;
    private EntityManager em;
    private Logger logger;
    Episodios epi;

    public AlmacenarEpisodioOdontologo() {

        em = Sesion.getEntitymanager();
        logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        
        EpisodioodontologoPK epk;
        Episodios oldepi;

        epi =  (Episodios) o;
        boolean res = false;
        oldepi = em.find(Episodios.class, epi.getId());
        

        if(oldepi!=null) {
            oldepi.setEpisodioodontologoCollection(epi.getEpisodioodontologoCollection());
            oldepi.setEpisodiosenfermedadesCollection(epi.getEpisodiosenfermedadesCollection());
            oldepi.getCarnetdesalud().setVigenciaodontologo(epi.getCarnetdesalud().getVigenciaodontologo());
            //EntityTransaction tx = em.getTransaction();
            //tx.begin();
            epi = oldepi;
        }
        try {
            em.merge(epi);
            em.persist(epi);
            em.flush();
        } catch(Exception ex) {
            res = false;
            logger.error("ERROR Almacenar EpisodioOdontologo" + ex);
        }
        //tx.commit();

        Vencimientosdoc vd = new Vencimientosdoc();
        vd.setId(null);
        vd.setEmitido(epi.getFecha());
        vd.setDocumento(900); // TODO - Properties
        vd.setPaciente(epi.getPaciente());
        vd.setVence(calcularVencimiento(epi.getFecha(), epi.getCarnetdesalud().getVigenciaodontologo()));
        res = true;
        return(res);
    }

    private Date calcularVencimiento(Date f, int meses) {

        Calendar cl = Calendar.getInstance();

        cl.setTime(f);
        cl.add(Calendar.MONTH, meses); 

        return(cl.getTime());
    }

    public Object getResultado() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
