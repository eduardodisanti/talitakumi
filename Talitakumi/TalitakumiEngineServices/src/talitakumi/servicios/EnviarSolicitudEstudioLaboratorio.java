/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.DataParameter;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Facturas;
import talitakumi.model.core.Numerador;

/**
 *
 * @author rupertus
 */
public class EnviarSolicitudEstudioLaboratorio implements AbstractService {

    private Episodios episodio;
    private int numero;
    private EntityManager em;
    private Numerador numerador;
    private Logger logger;
    
    public EnviarSolicitudEstudioLaboratorio() throws Exception{

        em = Sesion.getEntitymanager();
        logger = Sesion.getLogger();
        numero = 0;
    }

    private void obtenerIDFactura() {

        Query q = em.createNamedQuery("Numerador.findById");

        q.setParameter ("id", 3);

        numerador = (Numerador) q.getSingleResult();

        numero = numerador.getValor();

    }

    @Override
    public Object invoke(Object o) throws Exception {
        
       // obtenerIDFactura();
       // numero++;

        episodio = (Episodios) o;
        Facturas f = new Facturas();
        f.setEpisodio(episodio);

        AbstractService sd;
        try {
            sd = new ServicioCargarValorActividad();

        } catch (Exception ex) {
            logger.error(ex);
            System.out.println("Error en Service delegator es : " + ex);
            return(false);
        }

        Float preciobase = (Float) sd.invoke(episodio.getTipoepisodio());
        System.out.println("Break 1");

        DataParameter dp = new DataParameter();
        dp.set("cliente", f.getEpisodio().getCliente());
        dp.set("actividad", episodio.getTipoepisodio().getId());

        return(true);
    }
}
