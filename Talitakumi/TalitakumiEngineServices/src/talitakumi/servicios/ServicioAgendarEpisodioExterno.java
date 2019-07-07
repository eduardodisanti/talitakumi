/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Calendar;
import java.util.Vector;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.AgendaPK;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Episodioodontologo;
import talitakumi.model.core.Episodios;

/**
 *
 * @author rupertus
 */
public class ServicioAgendarEpisodioExterno implements AbstractService {

    Vector <Episodioodontologo> eh;
    private EntityManager em;
    private Logger logger;
    Episodios epi;

    public ServicioAgendarEpisodioExterno() {

        em = Sesion.getEntitymanager();
        logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

       Calendar cal = Calendar.getInstance();

       String ahora = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
       ahora = formatHora(ahora);
       
        System.out.println("==> ServicioAgendarEpisodioExterno : " + ahora);
        epi = (Episodios) o;

                AgendaPK apk = new AgendaPK(epi.getFecha(), ahora, 1,"9001");
                Agenda a = new Agenda();
                apk.setFecha(epi.getFecha());
                apk.setHora(ahora);
                apk.setTurno(1);
                apk.setConsultorio("9001");

                a.setEpisodio(epi);
                a.setMedico(epi.getMedico());
                a.setAgendaPK(apk);
                a.setActividad(2); // TODO sacarlo del hardcode
                a.setVino('S');
                a.setHabilitado('S');
                a.setPaciente(epi.getPaciente().getId());

                a.setObservaciones("");
                Clientes cliente = (Clientes) new ServicioCargarCliente().invoke(epi.getCliente());
                a.setCliente(cliente);

                em.persist(a);
                //em.merge(a);
                em.flush();

        return(a);
    }

    public Object getResultado() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

 private String formatHora(String ahora) {

        String s[] = ahora.split("[:]");

        if(s[0].length()==1) {
            s[0]="0"+s[0];
        }

        if(s[1].length()==1) {
            s[1]="0"+s[1];
        }

        if(s[2].length()==1) {
            s[2]="0"+s[2];
        }

        return(s[0] + ":" + s[1] + ":" + s[2]);
    }
}
