/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.AgendaPK;
import talitakumi.model.core.Carnetdesalud;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Personas;
import talitakumi.model.core.Seguros;
import talitakumi.model.core.Timming;

/**
 *
 * @author rupertus
 */
public class ServicioGuardarCarnetExterno implements AbstractService {

    private Carnetdesalud ldc;

    private EntityManager em;
    private Logger logger;
    private String xconsultorio="9001";

    private Object result;


    public ServicioGuardarCarnetExterno() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
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

    public Object invokeA(Object o) {

        this.setParameters(o);
        this.almacenar();
        System.out.println("LDC ES : " + ldc);
        return(ldc);
    }

        public void almacenar() {

        int turno = 1;
        Episodios epi = ldc.getEpisodios();
        Agenda a = new Agenda();

        String ellog = "Almacenando carnet de salud : " + new Date();

        //EntityTransaction tx = em.getTransaction();
        try {
                //tx.begin();

                //AbstractService snum = new ServiceDelegator("ServicioNumerador");
                //snum.setParameters(1);

                Date hoy;
                xconsultorio = "9001";
                String ahora;
                GregorianCalendar gc = new GregorianCalendar();
                hoy = gc.getTime();
                Calendar cal = Calendar.getInstance();

                turno = 1;

                ahora = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
                ahora = formatHora(ahora);
                ellog += "\n ahora asignada : " + hoy;

                String x[] = ahora.split(":");
                if(x[0].length()!=2) {
                    ahora = "0"+ahora;
                }

System.out.println("----> BREAK 3");
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
System.out.println("----> BREAK 4");
                epi.setId(null);
                epi.setFecha(hoy);
                epi.setTerminado('N');

                em.persist(epi);
                em.merge(epi);
                em.flush();

                System.out.println("----------------- " +epi.getId());
                Timming t = new Timming();
                t.setFechaingreso(hoy);
                t.setHoraingreso(hoy);
                t.setEpisodio(epi.getId());
                epi.setTimming(t);
                em.persist(t);
                em.merge(t);
                em.flush();
                
                ldc.setEpisodio(epi.getId());
                ldc.setEpisodios(epi);
                em.persist(ldc);
                em.merge(ldc);


System.out.println("----> BREAK 5");
                //em.persist(epi);
                //em.merge(epi);

         // Meto la agenda
                AgendaPK apk = new AgendaPK(hoy, ahora,1,xconsultorio);
                apk.setFecha(hoy);
                apk.setHora(ahora);
                apk.setTurno(turno);
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
                
                if(ldc.getVigencialaboratorio()==0) {
                    ldc.setVigencialaboratorio(-1);
                }
                if(ldc.getVigenciaodontologo()==0) {
                    ldc.setVigenciaodontologo(-1);
                }
                if(ldc.getVigenciamedico()==0) {
                    ldc.setVigenciamedico(-1);
                }
                
                
                ldc.setEpisodios(epi);

                ellog += "\n Persistiendo carnet ";
                em.persist(ldc);
                em.merge(ldc);

System.out.println("----> BREAK 6");

                epi.setCarnetdesalud(ldc);
                em.merge(ldc);
                em.flush();

                AbstractService asf = new ServicioFacturarExterno();
                asf.invoke(epi);

                System.out.println("Invocado servicio facturar");

                ellog += "\n Commit ";
                //tx.commit();
                //result = consultorio;
                //result.put("episodio", epi.getId());
                //result.put("consultorio", xconsultorio);
                //em.clear();

System.out.println("----> BREAK 7");
                result = ldc;
        } catch (Exception e) {
                //tx.rollback();
                System.out.println("e es " + e);
                System.out.println("ellog es " + ellog);
                logger.error(e);
                logger.error(ellog);
        }

    }
        public void almacenarA() {

        Episodios epi = ldc.getEpisodios();

        String ellog = "Almacenando carnet de salud : " + new Date();

        try {

                Personas p = epi.getPaciente().getPersonas();
                Date hoy = new Date();
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
                
                System.out.println("EPI ANTES DEL FLUSH " + epi);
                em.flush();
                System.out.println("EPI LUEGO DEL FLUSH " + epi);

                Timming t = new Timming();
                t.setFechaingreso(hoy);
                t.setHoraingreso(hoy);
                t.setEpisodio(epi.getId());
                epi.setTimming(t);
                em.persist(t);
                em.merge(t);
                //em.persist(epi);
                //em.merge(epi);

                ldc.setEpisodio(epi.getId());
                
                if(ldc.getVigencialaboratorio()==0) {
                    ldc.setVigencialaboratorio(-1);
                }
                if(ldc.getVigenciaodontologo()==0) {
                    ldc.setVigenciaodontologo(-1);
                }
                if(ldc.getVigenciamedico()==0) {
                    ldc.setVigenciamedico(-1);
                }
                
                ellog += "\n Persistiendo carnet " + epi.getId();
                System.out.println("Persistiendo carnet " + epi.getId());
                em.persist(ldc);
                em.merge(ldc);

               String ahora;
                GregorianCalendar gc = new GregorianCalendar();
                Calendar cal = Calendar.getInstance();

                int turno = 1;

                ahora = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
                ahora = formatHora(ahora);
                Agenda a = new Agenda();
                AgendaPK apk = new AgendaPK(epi.getFecha(), ahora,1,"9001");
                apk.setFecha(epi.getFecha());
                apk.setHora(ahora);
                apk.setTurno(turno);
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

                a.setCliente(cliente);

                ellog += "\n Persistiendo agenda ";
                em.persist(a);
                em.merge(a);
                em.flush();

                ellog += "\n Commit ";
                //tx.commit();
                result = ldc;
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
