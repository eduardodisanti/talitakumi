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
import talitakumi.model.core.Actividades;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.EpisodiosExAdicionales;
import talitakumi.model.core.EpisodiosVacunasAdicionales;
import talitakumi.model.core.Estudios;
import talitakumi.model.core.Facturas;
import talitakumi.model.core.Numerador;
import talitakumi.model.core.Usuarios;


/**
 *
 * @author rupertus
 */
public class ServicioFacturar implements AbstractService {

    private Episodios episodio;
    private int numero;
    private EntityManager em;
    private Numerador numerador;
    private Logger logger;
    
    public ServicioFacturar() throws Exception{

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

        Float precio = 0F;
        precio = cargarImporteFactura(dp, preciobase);

        f.setImporte(precio);

        f.setSerie("");
        f.setNumero(0);
        f.setEntregado("N");
        f.setRollo(0);
        f.setFechaentregado(new Date());

        f.setComentario(" ");
        f.setDescuentos(0);
        
        f.setFuncionario((Usuarios) Sesion.getVariableEntorno("usuario"));
        
        int emisor = episodio.getTipoepisodio().getEmisor();
        if(emisor==0) {
            Actividades a;
            a = episodio.getTipoepisodio();
            if(a.getId()==8 || a.getId()==10 || a.getId()==14 || a.getId()==37 || a.getId()==38){
                emisor = 2;
            } else if (a.getId()<=33 && a.getId()>= 16 || a.getId()==36 ){
                emisor = 2;
            } else {
                emisor = 1;
            }
        }
        f.setEmisor(emisor);

        em.persist(f);
        em.merge(f);
        em.flush();
        em.refresh(f);
        em.refresh(episodio);

        /*numerador.setValor(numero);
        em.persist(numerador);
        em.merge(numerador);*/

        return(true);
    }

    private Float cargarImporteFactura(DataParameter dp, Float preciobase) {

        Float importe = (Float) new ServicioCargarPrecioXactividad().invoke(dp);
        if(importe== null || importe==0F) {
                        importe = preciobase.floatValue();
        }

        if(importe==null) {
            importe = 0F;
        }
        
        for(EpisodiosExAdicionales eax : episodio.getEpisodiosExAdicionalesCollection()) {

            Estudios e = eax.getEstudio();
            dp.set("estudio", e.getId());
            Float precio = (Float) new ServicioCargarPrecioXactividad().invoke(dp);
            if(precio==null || precio==0f)
               importe+=eax.getEstudio().getPreciobase();
            else
               importe += precio;
        }

        for(EpisodiosVacunasAdicionales eax : episodio.getEpisodiosVacunasAdicionalesCollection()) {

            importe+=eax.getVacuna().getPreciobase();
        }


        System.out.println("Break 5 " + importe);
        return(importe);
    }

}
