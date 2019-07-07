package talitakumi.servicios;

import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.AgendaPK;

public class ServicioAlmacenarAgenda implements AbstractService {

    private EntityManager em;
    private Logger logger;
    Agenda a;

    public ServicioAlmacenarAgenda() {

        em = Sesion.getEntitymanager();
        logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        Agenda age;
        AgendaPK agepk;
        a =  (Agenda) o;

        String hora = a.getAgendaPK().getHora();
        String x[] = hora.split(":");
        if(x[0].length()!=2) {
            hora = "0"+hora;
        }

       HashMap dp = new HashMap();
        dp.put("fechadesde", a.getAgendaPK().getFecha());
        dp.put("fechahasta", a.getAgendaPK().getFecha());
        dp.put("tipolistado", 1);
        dp.put("orden", "alfabetico");


        int idx = 0;
        List<Agenda> la = (List<Agenda>) new CargarAgendaInternet().invoke(dp);

        if(la!=null) {
            idx = la.size();
        }

        hora += ":" + idx;
        a.getAgendaPK().setHora(hora);
        
        boolean res = false;

        try {
            System.out.println("Error servicio almacenar agenda " + a.getAgendaPK().getConsultorio() + "-" + a.getAgendaPK().getHora() + "-" + a.getAgendaPK().getTurno()+ "-" + a.getAgendaPK().getFecha());
            age = em.find(Agenda.class, a.getAgendaPK());
            if(age==null) {
                age = a;
                agepk = a.getAgendaPK();
                age.setAgendaPK(agepk);
                em.persist(age);
                em.merge(age);

                em.flush();
                res = true;
            }
        } catch(Exception ex) {

            System.out.println("Error servicio almacenar agenda " + ex);
            res = false;
        }
        return(res);
    }
}
