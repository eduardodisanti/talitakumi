/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.DataParameter;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.AgendaPK;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Consultorios;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Personas;
import talitakumi.model.core.Roundrobin;
import talitakumi.model.core.Seguros;
import talitakumi.model.core.Servicioexparaclin;
import talitakumi.model.core.Timming;

/**
 *
 * @author rupertus
 */
public class ServicioGuardarSolicitudParaclinicos implements AbstractService {

    private Servicioexparaclin ldc;

    private EntityManager em;
    private Logger logger;
    private String xconsultorio=null;

    private HashMap result;
    private Boolean inmediato, pasaAconsultorio, externo;


    public ServicioGuardarSolicitudParaclinicos() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();

        result = new HashMap();
    }

    public void setParameters(Object o) {

        DataParameter dp = (DataParameter) o;
        ldc = (Servicioexparaclin) dp.get("actividad");
        inmediato = (Boolean) dp.get("inmediato");
        pasaAconsultorio = (Boolean) dp.get("pasaAconsultorio");
        externo = (Boolean) dp.get("externo");
        
        System.out.println("ServicioGuardarSolicitudParclinico " + externo);
        if(externo==null) {
            externo = true;
        }

    }

    @Override
    public Object invoke(Object o) {


        this.setParameters(o);
        this.almacenar();
        return(result);
    }

        public void almacenar() {

        Episodios epi = ldc.getEpisodios1();
        Agenda a = new Agenda();

        String ellog = "Almacenando Paraclinicos  : " + new Date() + " ";
        int act = epi.getTipoepisodio().getTipoactividad().getId();
        try {
                Date hoy;

                
                if(!externo) {
                    ellog += "\n Obtener Consultorio : ";
                    int consultorio = obtenerConsultorio();
                    xconsultorio = consultorio + "";

                } else
                    xconsultorio = "9001";
                ellog += "\n Consultorio es : " + xconsultorio;
                String ahora;
                GregorianCalendar gc = new GregorianCalendar();
                hoy = gc.getTime();

                Calendar cal = Calendar.getInstance();

                ahora = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
                ahora = formatHora(ahora);
                ellog += "\n ahora asignada : " + hoy;

                String x[] = ahora.split(":");
                if(x[0].length()!=2)
                    ahora = "0"+ahora;
                Personas p = epi.getPaciente().getPersonas();
                if(p.getId()==null) {
                    em.persist(p);
                    em.merge(p);
                    em.flush();

                    epi.getPaciente().setFechaingreso(hoy);
                    epi.getPaciente().setId(p.getId());

                    Seguros seguro = new Seguros();
                    seguro.setId(0);
                    epi.getPaciente().setSeguro(seguro);
                }

                epi.setId(null);
                epi.setFecha(hoy);
                epi.setTerminado('N');

                em.persist(epi);
                em.merge(epi);
                em.flush();

                Timming t = new Timming();
                t.setFechaingreso(hoy);
                t.setHoraingreso(hoy);
                t.setEpisodio(epi.getId());
                epi.setTimming(t);
                em.persist(t);
                em.merge(t);
                em.flush();

         // Meto la agenda
                AgendaPK apk = new AgendaPK(hoy, ahora,1,xconsultorio);
                apk.setFecha(hoy);
                apk.setHora(ahora);
                apk.setTurno(1);
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
         
                ellog += "\n Persistiendo agenda ";
                System.out.println(ellog);
                em.persist(a);
                em.merge(a);
                em.flush();

                ldc.setEpisodios1(epi);
                ldc.setEpisodios(epi.getId());

                ellog += "\n Persistiendo Paraclinicos ";
                System.out.println(ellog);
                em.persist(ldc);
                em.merge(ldc);
                em.flush();

                System.out.println("Invocado servicio facturar");
                AbstractService asf;
                if(!externo) {
                    asf = new ServicioFacturar();
                    asf.invoke(epi);

                    System.out.println("Invocado servicio facturar");
                } else {
                    asf = new ServicioFacturarExterno();
                    asf.invoke(epi);

                    System.out.println("Invocado servicio facturar");
                }

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

    private Integer obtenerConsultorio() {
        ServicioObtenerConsultorio soc = new ServicioObtenerConsultorio();
        DataParameter dp = new DataParameter();

        dp.set("actividad", 13); // TODO Properties
        dp.set("inmediato", false);
        //soc.setParameters(2); // TODO pasarle la actividad

        soc.setParameters(dp);
        Consultorios cons = (Consultorios) soc.invoke();

        return(cons.getId());
    }

    private Integer obtenerConsultorio1() {
        ServicioObtenerConsultorio soc = new ServicioObtenerConsultorio();
        soc.setParameters(2); // TODO pasarle la actividad

        Consultorios cons = (Consultorios) soc.invoke();

        Query q = (Query) em.createNamedQuery("Roundrobin.findByActividad");
        q.setParameter("actividad", 2);
        Roundrobin r = (Roundrobin) q.getSingleResult();

        r.setUltimo(cons.getId());

        em.persist(r);
        em.merge(r);
        return((Integer)r.getUltimo());

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
