/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Actividades;
import talitakumi.model.core.Facturas;
import talitakumi.model.core.Usuarios;

/**
 *
 * @author rupertus
 */
public class AlmacenarFacturaFacturada implements AbstractService {

    private EntityManager em;
    private Logger logger;
    Facturas f;
    Actividades a;
  
    public AlmacenarFacturaFacturada() {

        em = Sesion.getEntitymanager();
        logger = Sesion.getLogger();
        
    }

    @Override
    public Object invoke(Object o) {


        f =  (Facturas) o;
        Usuarios funcionario = f.getFuncionario();
        Facturas vf = f;
        boolean res = false;
        Query q = em.createNamedQuery("Facturas.findById");
        q.setParameter("id", (Integer)f.getId());

        f = (Facturas) q.getSingleResult();

        int emisor = f.getEpisodio().getTipoepisodio().getEmisor();
        System.out.println("====[AlmacenarFacturaFacturada]===>El emisor es " + emisor + " siendo el tipo " + f.getEpisodio().getTipoepisodio() + " en donde emisor de la actividad es " + f.getEpisodio().getTipoepisodio());

        if(emisor==0) {

            a = f.getEpisodio().getTipoepisodio();
            if(a.getId()==8 || a.getId()==10 || a.getId()==14 || a.getId()==37 || a.getId()==38){
                emisor = 3;
            } else if (a.getId()<=33 && a.getId()>= 16 || a.getId()==36 ){
                emisor = 3;
            } else {
                emisor = 1;
            }
        }

        f.setCantidad(vf.getCantidad());
        f.setComentario(vf.getComentario());
        f.setDescuentos(vf.getDescuentos());
        f.setImporte(vf.getImporte());
        f.setEntregado(vf.getEntregado());
        f.setNumero(vf.getNumero());
        f.setRollo(vf.getRollo());
        f.setTipo(vf.getTipo());
        f.setFechaentregado(new Date());
        f.setEmisor(emisor);
        f.setFuncionario(funcionario);
        
        em.persist(f);
        em.merge(f);
        em.flush();
        em.refresh(f);
        res = true;
        return(res);
    }

}
