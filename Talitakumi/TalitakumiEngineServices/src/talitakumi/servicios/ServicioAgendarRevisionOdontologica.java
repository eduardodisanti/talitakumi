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
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Actividades;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.AgendaPK;
import talitakumi.model.core.Carnetdesalud;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Consultorios;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Personas;
import talitakumi.model.core.Roundrobin;
import talitakumi.model.core.Seguros;
import talitakumi.model.core.Timming;

/**
 *
 * @author rupertus
 */
public class ServicioAgendarRevisionOdontologica implements AbstractService {

    Carnetdesalud ldc;

    EntityManager em;
    Logger logger;
    String xconsultorio=null;

    HashMap result;


    public ServicioAgendarRevisionOdontologica() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();

        result = new HashMap();
    }

    public void setParameters(Object o) {

        ldc = (Carnetdesalud) o;
    }

    @Override
    public Object invoke(Object o) {


        this.setParameters(o);
        this.almacenar();
        return(result);
    }

    public void almacenar() {

        Date hoy = new Date();
        Episodios xepi = ldc.getEpisodios();
        Episodios epi = new Episodios();

        epi.setCarnetdesalud(null);
        epi.setCliente(epi.getCliente());
        epi.setId(null);
        epi.setFecha(hoy);
        epi.setTipoepisodio((Actividades) new ServicioCargarActividad().invoke(15));
        epi.setPaciente(xepi.getPaciente());
        epi.setMedico(xepi.getMedico());

        Agenda a = new Agenda();

        String ellog = "Almacenando Revision : " + new Date();

//        try {
                //tx.begin();

                //AbstractService snum = new ServiceDelegator("ServicioNumerador");
                //snum.setParameters(1);

                //int consultorio = obtenerConsultorio();
                xconsultorio = "ODO";
                String ahora;
                GregorianCalendar gc = new GregorianCalendar();
                hoy = gc.getTime();

                Calendar cal = Calendar.getInstance();

                ahora = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
                ellog += "\n ahora asignada : " + hoy;

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
                a.setActividad(2); // TODO sacarlo del hardcode
                a.setVino('S');
                a.setHabilitado('S');
                a.setPaciente(epi.getPaciente().getId());
                a.setObservaciones("");
                Clientes cliente = (Clientes) new ServicioCargarCliente().invoke(epi.getCliente());
                a.setCliente(cliente);
         
                ellog += "\n Persistiendo agenda ";
                em.persist(a);
                em.merge(a);
                em.flush();

                ldc.setEpisodio(epi.getId());
                //ldc.setVigencialaboratorio(-1);
                ldc.setVigenciaodontologo(-1);
                //ldc.setVigenciamedico(-1);
                ldc.setEpisodios(epi);

                ellog += "\n Persistiendo carnet ";
                em.persist(ldc);
                em.merge(ldc);

                epi.setCarnetdesalud(ldc);
                em.merge(ldc);
                em.flush();

                ellog += "\n Commit ";
                //tx.commit();
                //result = consultorio;
                result.put("episodio", epi.getId());
                result.put("consultorio", xconsultorio);
                System.out.println("el log es " + ellog);
                //em.clear();
//        } catch (Exception e) {
//                //tx.rollback();
//                System.out.println("e es " + e);
//                System.out.println("ellog es " + ellog);
//                logger.error(e);
//                logger.error(ellog);
//        }

    }

    private Integer obtenerConsultorio() {

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

}
