/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Date;
import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;

/**
 *
 * @author rupertus
 */
public class CargarEpisodiosConsultorio implements AbstractService {

    private EntityManager em;
    private Logger logger;
    private Integer actividadaquitar;
    private Date fecha;

    public CargarEpisodiosConsultorio() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
      }

    @Override
    public Object invoke(Object o) {

        String orden = "e.paciente.personas.apellidos";
        HashMap<String, Object> parametros = (HashMap)(o);

         actividadaquitar = (Integer)parametros.get("actividadaquitar");
         fecha = (Date)parametros.get("fecha");
         String orden_pedido = (String) parametros.get("orden_pedido");
         
         if(orden_pedido==null) {
             orden_pedido = "paciente";
         }
         
         if(orden_pedido.equals("llegada")) {
             orden = "e.id";
         }
         Date hoy = fecha;

         String qry = "SELECT e FROM Episodios e WHERE e.tipoepisodio.tipoactividad.id <> :actividadaquitar AND e.fecha = :hoy order by " + orden;

         Query q = em.createQuery(qry);
         q.setParameter ("actividadaquitar", actividadaquitar);
         q.setParameter("hoy", hoy);
         //q.setMaxResults(100);

         return(q.getResultList());

//         for(Episodios epi : z) {
//             for(Facturas f : epi.getFacturasCollection()) {}
//         }

    }
}
