/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Actividades;
import talitakumi.model.core.Agenda;

/**
 *
 * @author rupertus
 */
public class CargarResumenPacientesAnotadosTelefonista implements AbstractService {

    private EntityManager em;
    private Logger logger;
    private Date fecha;
    private String horadesde;
    private String horahasta;

    public CargarResumenPacientesAnotadosTelefonista() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    private void setParameters(Object o) {

        HashMap hm = (HashMap) o;

        fecha = (Date) hm.get("fechadesde");
        horadesde = "00:00";
        horahasta = "23:59:00";
    }

    @Override
    public Object invoke(Object o) {

        Integer cant;
        String clave;
        Date hoy = new Date();
        HashMap<String, Integer> resumen = new HashMap();

            this.setParameters(o);

            String qry = "SELECT a FROM Agenda a where a.agendaPK.fecha=:fecha and a.agendaPK.hora >= :horadesde and a.agendaPK.hora <= :horahasta order by a.agendaPK.fecha, a.agendaPK.hora";
            Query q = em.createQuery(qry);
            q.setParameter ("fecha", fecha);
            q.setParameter ("horadesde", horadesde);
            q.setParameter ("horahasta", horahasta);

            Collection <Agenda> c = q.getResultList();


            for(Agenda a : c) {

                Actividades act = (Actividades) new ServicioCargarActividad().invoke(a.getActividad());
                clave = act.getTipoactividad().getDescripcion();
                cant = resumen.get(clave);
                if(cant==null)
                    cant = 0;

                cant++;
                resumen.put(clave, cant);

            }
        return(resumen);
    }
}
