/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.DataParameter;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.AgendaPK;
import talitakumi.model.core.Chequeos;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Personas;
import talitakumi.model.core.Seguros;
import talitakumi.model.core.Timming;

/**
 *
 * @author rupertus
 */

public class ServicioGuardarChequeoExterno implements AbstractService {

    private Chequeos ldc;

    private EntityManager em;
    private Logger logger;
    private String xconsultorio=null;
    private HashMap result;
    private Boolean inmediato;
    private Date fecha;


    public ServicioGuardarChequeoExterno() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();

        result = new HashMap();
    }

    public void setParameters(Object o) {

        DataParameter dp = (DataParameter) o;
        inmediato = (Boolean) dp.get("inmediato");
        ldc = (Chequeos) dp.get("actividad");
        fecha = (Date) dp.get("fecha");

    }

    @Override
    public Object invoke(Object o) {

        this.setParameters(o);
        this.almacenar();
        return(result);
    }

        public void almacenar() {

        int turno = 1;
        Episodios epi = ldc.getEpisodio();
        Agenda a = new Agenda();

        String ellog = "Almacenando Chequeos  : " + new Date() + " ";
        int act = epi.getTipoepisodio().getTipoactividad().getId();
        try {
                //Date hoy;

                System.out.println("LA ACTIVIDAD ESSSSSSSS!!!!!!!!  " +act);

                xconsultorio = "9001";
                ellog += "\n Consultorio es : " + xconsultorio;
     //           GregorianCalendar gc = new GregorianCalendar();
//                hoy = gc.getTime();

                Calendar cal = Calendar.getInstance();

                String ahora = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
                ahora = formatHora(ahora);
                ellog += "\n ahora asignada : " + fecha;

                System.out.println("FECHAAAA ESSS!!!!!!!!  " +fecha);

                Personas p = epi.getPaciente().getPersonas();
                                          
                if(p.getId()==null) {
                    em.persist(p);
                    em.merge(p);
                    em.flush();

                    epi.getPaciente().setFechaingreso(fecha);
                    epi.getPaciente().setId(p.getId());

                    Seguros seguro = new Seguros();
                    seguro.setId(0);
                    epi.getPaciente().setSeguro(seguro);
                }

                epi.setId(null);
                epi.setFecha(fecha);
                epi.setTerminado('N');

                em.persist(epi);
                em.merge(epi);
                em.flush();

                Timming t = new Timming();
                t.setFechaingreso(fecha);
                t.setHoraingreso(fecha);
                t.setEpisodio(epi.getId());
                epi.setTimming(t);
                em.persist(t);
                em.merge(t);
                em.flush();

                if(inmediato)
                    turno = 0;
//                    ahora = "00:" + "00:" + cal.get(Calendar.SECOND) + ":" + cal.get(Calendar.MILLISECOND);
                else
                     turno = 1;
         // Meto la agenda
                AgendaPK apk = new AgendaPK(fecha, xconsultorio ,1,xconsultorio);
                apk.setFecha(fecha);
                apk.setHora(ahora);
                apk.setTurno(turno);
                apk.setConsultorio(xconsultorio);

                a.setEpisodio(epi);
                a.setMedico(epi.getMedico());   
                a.setAgendaPK(apk);
                a.setActividad(act); // TODO sacarlo del hardcode
                a.setVino('S');
                a.setHabilitado('S');
                a.setPaciente(epi.getPaciente().getId());
                a.setObservaciones("");
                Clientes cliente = (Clientes) new ServicioCargarCliente().invoke(epi.getCliente());
                a.setCliente(cliente);

                ellog += "\n Persistiendo agenda " + a;
                System.out.println(ellog);
                em.persist(a);
                em.merge(a);
                em.flush();

                ldc.setEpisodio(epi);
                //ldc.setEpisodio(epi.getId());
                ldc.setEpisodio(epi);

                ellog += "\n Persistiendo Externos " + ldc + " epi es " + epi;
                System.out.println(ellog);
                em.persist(ldc);
                em.merge(ldc);
                em.flush();

                System.out.println("Invocado servicio facturar");
                AbstractService asf = new ServicioFacturarExterno();
                asf.invoke(epi);
                

                ellog += "\n Commit ";
                //tx.commit();
                //result = consultorio;
                result.put("episodio", epi.getId());
                result.put("consultorio", xconsultorio);
                //em.clear();
        } catch (Exception e) {
                //tx.rollback();
                System.out.println("e es " + e);
                System.out.println("ellog es " + ellog);
                logger.error(e);
                logger.error(ellog);
        }
    }

    private String formatHora(String ahora) {

        String s[] = ahora.split("[:]");

        if(s[0].length()==1)
            s[0]="0"+s[0];

        if(s[1].length()==1)
            s[1]="0"+s[1];

        if(s[2].length()==1)
            s[2]="0"+s[2];

        return(s[0] + ":" + s[1] + ":" + s[2]);
    }
}
