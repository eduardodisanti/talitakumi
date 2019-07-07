/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Facturas;

/**
 *
 * @author rupertus
 */
public class CargarEpisodiosConsultorioPagos implements AbstractService {

    private EntityManager em;
    private Logger logger;
    private Integer actividadaquitar;
    private Date fecha;

    public CargarEpisodiosConsultorioPagos() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        HashMap<String, Object> parametros = (HashMap)(o);

         actividadaquitar = (Integer)parametros.get("actividadaquitar");
         fecha = (Date)parametros.get("fecha");
         Date hoy = fecha;

         String qry = "SELECT e FROM Episodios e WHERE e.tipoepisodio.tipoactividad.id <> :actividadaquitar AND e.fecha = :hoy order by e.paciente.personas.apellidos";

         Query q = em.createQuery(qry);
         q.setParameter ("actividadaquitar", actividadaquitar);
         q.setParameter("hoy", hoy);
         q.setMaxResults(100);

         List<Episodios> z = q.getResultList();
//         for(Episodios epi : z) {
//             for(Facturas f : epi.getFacturasCollection()) {}
//         }

         List<Episodios> listaaux = new ArrayList<Episodios>();

         for(Episodios epi : z) {
             Boolean pago = null;
             if(epi.getFacturas()!=null){

                 Facturas f = epi.getFacturas();

                 if(pago== null){
                      pago = f.getEntregado().equals("S");
                 }
                 System.out.println("++++++++++++++++++++++++++ 1");
                 pago = pago && f.getEntregado().equals("S");
                 System.out.println("++++++++++++++++++++++++++ 2");
             }
                 System.out.println("++++++++++++++++++++++++++ 3" + pago);

             if(pago==null)
                 pago=false;

             if(pago){
                 System.out.println("Factura pagada : "+ pago);
                 listaaux.add(epi);
             }
         }
        System.out.println("+++++++++++++++++++++++ 4  . :  " +listaaux.size());
        return(listaaux);

    }
}
