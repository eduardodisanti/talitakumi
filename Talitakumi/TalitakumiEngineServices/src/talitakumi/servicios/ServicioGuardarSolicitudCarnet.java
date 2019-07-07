/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.DataParameter;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
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
public class ServicioGuardarSolicitudCarnet implements AbstractService {

    Carnetdesalud ldc;

    EntityManager em;
    Logger logger;
    String xconsultorio=null;

    HashMap result;
    private Boolean inmediato;



    public ServicioGuardarSolicitudCarnet() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();

        result = new HashMap();
    }

    public void setParameters(Object o) {

        DataParameter dp = (DataParameter) o;
        ldc = (Carnetdesalud) dp.get("actividad");
        inmediato = (Boolean) dp.get("inmediato");
    }

    @Override
    public Object invoke(Object o) {


        this.setParameters(o);
        this.almacenar();
        return(result);
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
                ahora = formatHora(ahora);
                ellog += "\n ahora asignada : " + hoy;

                String x[] = ahora.split(":");
                if(x[0].length()!=2)
                    ahora = "0"+ahora;

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
                ldc.setVigencialaboratorio(-1);
                ldc.setVigenciaodontologo(-1);
                ldc.setVigenciamedico(-1);
                ldc.setEpisodios(epi);
                ldc.setImpreso(0L);

                ellog += "\n Persistiendo carnet ";
                em.persist(ldc);
                em.merge(ldc);

System.out.println("----> BREAK 6");

                epi.setCarnetdesalud(ldc);
                em.merge(ldc);
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

System.out.println("----> BREAK 7");
        } catch (Exception e) {
                //tx.rollback();
                System.out.println("e es " + e);
                System.out.println("ellog es " + ellog);
                logger.error(e);
                logger.error(ellog);
        }

    }

    private Integer obtenerConsultorio() {

        Integer resp = null;

        Integer idpaciente = ldc.getEpisodios().getPaciente().getId();
        HashMap<String, Object> adp = new HashMap<String, Object>();
        CargarAgenda ca = new CargarAgenda();

        adp.put("fechadesde", ldc.getEpisodios().getFecha());
        adp.put("fechahasta", ldc.getEpisodios().getFecha());
        adp.put("tipolistado", 1);
        adp.put("orden", "fecha");

        List<Agenda> la = (List<Agenda>) ca.invoke(adp);

        for(Agenda a : la) {

            if(a.getPaciente() == idpaciente) {

                if(!a.getAgendaPK().getConsultorio().equals("INET") && !a.getAgendaPK().getConsultorio().equals("TEL")) {

                    resp = Integer.parseInt(a.getAgendaPK().getConsultorio());
                    break;
                }
            }
        }
        if(resp==null) { // TODO - No me gusta nada como está esto
            ServicioObtenerConsultorio soc = new ServicioObtenerConsultorio();
            DataParameter dp = new DataParameter();
            dp.set("inmediato", inmediato);
            dp.set("actividad", 2);
  
            soc.setParameters(dp); // TODO pasarle la actividad

            Consultorios cons = (Consultorios) soc.invoke();

            Query q = (Query) em.createNamedQuery("Roundrobin.findByActividad");
            q.setParameter("actividad", 2);
            Roundrobin r = (Roundrobin) q.getSingleResult();

            r.setUltimo(cons.getId());

            em.persist(r);
            em.merge(r);
            em.flush();
            resp = (Integer)r.getUltimo();
          }
        return(resp);
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
