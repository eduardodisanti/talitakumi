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
import talitakumi.model.core.Agenda;

/**
 *
 * @author rupertus
 */
public class CargarPacientesAnotadosTelefonista implements AbstractService {

    private EntityManager em;
    private Logger logger;
    private Date fecha;
    private String horadesde;
    private String horahasta;

    public CargarPacientesAnotadosTelefonista() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    private void setParameters(Object o) {

        HashMap hm = (HashMap) o;

        fecha = (Date) hm.get("fechadesde");

        String hora = (String) hm.get("hora");
        Integer hh1 = Integer.parseInt(hora.substring(0, 2));
        Integer mm1 = Integer.parseInt(hora.substring(3, 5));
        System.out.println("Hora = " + hora);

        int mm2 = mm1 + 15;
        int hh2 = hh1;
        if(mm2>=60) {
            mm1 = 0;
            mm2++;
        }

        horadesde = hora;
        horahasta = hh2 + ":" + mm2;
        if(horahasta.length()<5)
            horahasta = "0" + horahasta;
        System.out.println("Horas = " + horadesde + " y " + horahasta);
    }

    @Override
    public Object invoke(Object o) {

        Date hoy = new Date();

            this.setParameters(o);

            String qry = "SELECT a FROM Agenda a where a.agendaPK.fecha=:fecha and a.agendaPK.hora >= :horadesde and a.agendaPK.hora <= :horahasta order by a.agendaPK.fecha, a.agendaPK.hora";
            Query q = em.createQuery(qry);
            q.setParameter ("fecha", fecha);
            q.setParameter ("horadesde", horadesde);
            q.setParameter ("horahasta", horahasta);
            Collection <Agenda> c = q.getResultList();
        return(c);
    }
}
