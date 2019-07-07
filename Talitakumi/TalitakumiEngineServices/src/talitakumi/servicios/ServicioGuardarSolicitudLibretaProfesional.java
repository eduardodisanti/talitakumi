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
import talitakumi.model.core.Libretaprofesional;
import talitakumi.model.core.Personas;
import talitakumi.model.core.Roundrobin;
import talitakumi.model.core.Seguros;
import talitakumi.model.core.Timming;

/**
 *
 * @author rupertus
 */
public class ServicioGuardarSolicitudLibretaProfesional implements AbstractService {

    private Integer actividad;
    private Libretaprofesional lp;
    private EntityManager em;
    private Logger logger;
    private String xconsultorio=null;
    private HashMap result;
    private Boolean inmediato;
    private String externo = "N";

    public ServicioGuardarSolicitudLibretaProfesional() {

        
        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
        result = new HashMap();

    }

    public void setParameters(Object o) {


        DataParameter dp = (DataParameter) o;
        lp = (Libretaprofesional) dp.get("actividad");
        externo = (String) dp.get("externo");
        if(externo==null) {
            externo = "N";
        }


    }

    @Override
    public Object invoke(Object o) {


        this.setParameters(o);
        this.almacenar();
        return(result);
    }

        public void almacenar() {

        int turno = 1;
        Episodios epi = lp.getEpisodios();
        Agenda a = new Agenda();

        String ellog = "Almacenando libreta profesional  : " + new Date();

        try {
                Date hoy;
                
                xconsultorio="800";
                
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

//                if(inmediato)
//                    turno = 0;
////                    ahora = "00:" + "00:" + cal.get(Calendar.SECOND) + ":" + cal.get(Calendar.MILLISECOND);
//                else
                     turno = 1;
         // Meto la agenda
                AgendaPK apk = new AgendaPK(hoy, ahora,1,xconsultorio);
                apk.setFecha(hoy);
                apk.setHora(ahora);
                apk.setTurno(turno);
                apk.setConsultorio(xconsultorio);
                
                a.setEpisodio(epi);
                a.setMedico(epi.getMedico());
                a.setAgendaPK(apk);
                a.setActividad(8); // TODO sacarlo del hardcode
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

                lp.setEpisodios(epi);
                lp.setEpisodio(epi.getId());

                ellog += "\n Persistiendo libreta prof ";
                System.out.println(ellog);
                em.persist(lp);
                em.merge(lp);
                em.flush();

                AbstractService asf = new ServicioFacturar();
                asf.invoke(epi);

                System.out.println("Invocado servicio facturar");

                ellog += "\n Commit ";
                //tx.commit();
                //result = consultorio;
                result.put("episodio", epi.getId());
                result.put("consultorio", xconsultorio);
                //em.clear();
                System.out.println("fin almacenando ");
        } catch (Exception e) {
                //tx.rollback();
                System.out.println("e es " + e);
                System.out.println("ellog es " + ellog);
                logger.error(e);
                logger.error(ellog);
        }
    }

    private Integer obtenerConsultorio() {

        System.out.println("obteniendo consultorio ");
        ServicioObtenerConsultorio soc = new ServicioObtenerConsultorio();
        DataParameter dp = new DataParameter();
        dp.set("inmediato", inmediato);
        dp.set("actividad", 8);

        soc.setParameters(dp); // TODO pasarle la actividad

System.out.println("==> BREAK #1");
        Consultorios cons = (Consultorios) soc.invoke();
System.out.println("==> BREAK #2");

        Query q = (Query) em.createNamedQuery("Roundrobin.findByActividad");
        q.setParameter("actividad", 8);
        Roundrobin r = (Roundrobin) q.getSingleResult();
System.out.println("==> BREAK #3");
        r.setUltimo(cons.getId());
System.out.println("==> BREAK #4");
        em.persist(r);
        em.merge(r);
        em.flush();
System.out.println("==> BREAK #5");
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
