/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.DataParameter;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Facturas;
import talitakumi.model.core.Numerador;
import talitakumi.model.core.Usuarios;


/**
 *
 * @author rupertus
 */
public class ServicioFacturarExterno implements AbstractService {

    private Episodios episodio;
    private int numero;
    private EntityManager em;
    private Numerador numerador;
    private Logger logger;
    
    public ServicioFacturarExterno() throws Exception{

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

        DataParameter dp = new DataParameter();
        dp.set("cliente", f.getEpisodio().getCliente());
        dp.set("actividad", episodio.getTipoepisodio().getId());

        Float precio = (Float) new ServicioCargarPrecioXactividad().invoke(dp);
        if(precio== null || precio==0F) {
                        precio = preciobase.floatValue();
        }

        f.setImporte(precio);

        f.setSerie("");
        f.setNumero(0);
        f.setEntregado("S");
        f.setTipo('R');
        f.setRollo(0);
        f.setFechaentregado(new Date());
        f.setEmisor(1); // VER (VICTORIA)

        f.setComentario("-INET-");
        f.setDescuentos(0);

        Usuarios u = (Usuarios) new CargarUsuario().invoke("medilab");
        f.setFuncionario(u);

        em.persist(f);
        em.merge(f);
        em.flush();

        /*numerador.setValor(numero);
        em.persist(numerador);
        em.merge(numerador);*/

        return(true);
    }

}
