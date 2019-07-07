/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.DataParameter;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Actividades;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.AgendaPK;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Consultorios;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Libretasdeconducir;
import talitakumi.model.core.Personas;
import talitakumi.model.core.Roundrobin;
import talitakumi.model.core.Seguros;
import talitakumi.model.core.Timming;

/**
 *
 * @author rupertus
 */

public class ServicioGuardarSolicitudLibreta implements AbstractService {

    Libretasdeconducir ldc;

    EntityManager em;
    Logger logger;
    Object result;
    private Boolean inmediato;
    String xconsultorio=null;


    public ServicioGuardarSolicitudLibreta() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }


    @Override
    public Object invoke(Object o) {

        DataParameter dp = (DataParameter) o;
        ldc = (Libretasdeconducir) dp.get("actividad");
        inmediato = (Boolean) dp.get("inmediato");

        this.almacenar();
        return(result);
    }

        public void almacenar() {

        int turno = 1;
        Episodios epi = ldc.getEpisodios();
        Agenda a = new Agenda();

        String ellog = "Almacenando carnet de salud : " + new Date();

        try {
                AbstractService snum = new ServicioNumerador();
                int nuevonumero = (Integer) snum.invoke(1);

                Date hoy;
                int consultorio = obtenerConsultorio();
                xconsultorio = consultorio + "";
                String ahora;
                GregorianCalendar gc = new GregorianCalendar();
                hoy = gc.getTime();

                Calendar cal = Calendar.getInstance();


                if(inmediato)
                    turno = 0;
//                    ahora = "00:" + "00:" + cal.get(Calendar.SECOND) + ":" + cal.get(Calendar.MILLISECOND);
                else
                     turno = 1;

                ahora = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
                ellog += "\n ahora asignada : " + hoy;
                ahora = formatHora(ahora);

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

                
                //if(inmediato)
                  //  turno = 0;
//                    ahora = "00:" + "00:" + cal.get(Calendar.SECOND) + ":" + cal.get(Calendar.MILLISECOND);
                //else
                  //   turno = 1;
               
                // Meto la agenda
                AgendaPK apk = new AgendaPK(hoy, ahora,1,xconsultorio);
                apk.setFecha(hoy);
                apk.setHora(ahora);
                apk.setTurno(turno);
                apk.setConsultorio(xconsultorio);

                epi.setId(null);
                epi.setFecha(hoy);
                epi.setTerminado('N');
                
                Actividades act = new Actividades();
                act = em.find(Actividades.class, 1);
                epi.setTipoepisodio(act);

                ellog += "\n Persistiendo agenda ";
                em.persist(epi);
                em.merge(epi);
                em.flush();

                Timming t = new Timming();
                t.setEpisodio(epi.getId());
                t.setFechaingreso(hoy);
                t.setHoraingreso(hoy);
                epi.setTimming(t);
                em.persist(t);
                em.merge(t);
                em.flush();

                a.setMedico(epi.getMedico()); 
                a.setAgendaPK(apk);
                a.setActividad(1); // TODO sacarlo del hardcode
                a.setVino('S');
                a.setHabilitado('S');
                a.setPaciente(epi.getPaciente().getId());
                a.setEpisodio(epi);
                a.setObservaciones("");
                Clientes cliente = (Clientes) new ServicioCargarCliente().invoke(epi.getCliente());
                a.setCliente(cliente);

                ellog += "\n Persistiendo libreta ";
                em.persist(a);
                em.merge(a);

                ldc.setEpisodio(epi.getId());
                em.persist(ldc);
                em.merge(ldc);
                em.flush();

                result = consultorio;
                ellog += "\n Commit ";
               
                AbstractService asf = new ServicioFacturar();
                asf.invoke(epi);



        } catch (Exception e) {
                System.out.println("Error excepcion " + e);
                logger.error(e);
        }

    }

    private Integer obtenerConsultorio() {

        ServicioObtenerConsultorio soc = new ServicioObtenerConsultorio();
        DataParameter dp = new DataParameter();
        dp.set("inmediato", inmediato);
        dp.set("actividad", 1);

        soc.setParameters(dp); // TODO pasarle la actividad

        Consultorios cons = (Consultorios) soc.invoke();

        Query q = (Query) em.createNamedQuery("Roundrobin.findByActividad");
        q.setParameter("actividad", 1);
        Roundrobin r = (Roundrobin) q.getSingleResult();

        r.setUltimo(cons.getId());

        em.persist(r);
        em.merge(r);
        em.flush();
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
