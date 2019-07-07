/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.webservices.agenda;

import java.text.SimpleDateFormat;
import talitakumi.webservices.RespuestaSerializada;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ejb.Stateless;
import talitakumi.framework.DataParameter;
import talitakumi.framework.Sesion;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.AgendaPK;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Pacientes;
import talitakumi.webservices.TWSStandards;
import talitakumi.model.core.Personas;
import talitakumi.model.core.Seguros;
import talitakumi.webservices.IFObjetoSerializado;

/**
 *
 * @author rupertus
 */
@WebService()
@Stateless()
public class AgendaWebServices {

    @WebMethod(operationName = "anularCita")
    public String anularCita(@WebParam(name = "sid") Long sid) {

        DataParameter dp = new DataParameter();
        Boolean laresp = false;
        String resp = null;
        String citaprevia = null;
        IFObjetoSerializado cita = new CitaSerializada();

        TWSStandards tws = new TWSStandards();

        try {
            tws.conectar();

            Agenda a = (Agenda) Sesion.getServiceDelegatorRemote().invoke("BorrarAgendaPorSid",sid);

            if(a==null) {
                laresp = false;
            } else {
                    laresp = true;
                    citaprevia = tws.formatDate(a.getAgendaPK().getFecha());
                    cita.addTag("fecha", citaprevia);
                    cita.addTag("hora", a.getAgendaPK().getHora());
            }
            resp = tws.makeResponse(laresp, "RESULTADO OPERACION", cita);
        } catch (Exception ex) {
            resp = "ERROR " + ex;
            System.out.println("Error consultar cita " + ex);
            Logger.getLogger(AgendaWebServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resp;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "consultarCita")
    public String consultarCita(@WebParam(name = "sid") Long sid) {

        DataParameter dp = new DataParameter();
        Long sessionID = new Date().getTime();
        Boolean laresp = false;
        String resp = null;
        String citaprevia = null;
        IFObjetoSerializado cita = new CitaSerializada();

        TWSStandards tws = new TWSStandards();

        try {
            tws.conectar();

            Agenda a = (Agenda) Sesion.getServiceDelegatorRemote().invoke("CargarAgendaPorSid",sid);

            if(a==null) {
                laresp = false;
            } else {
                    laresp = true;
                    citaprevia = tws.formatDate(a.getAgendaPK().getFecha());
                    cita.addTag("fecha", citaprevia);
                    cita.addTag("hora", a.getAgendaPK().getHora());
            }
            resp = tws.makeResponse(laresp, "DATOS CITA", cita);
        } catch (Exception ex) {
            resp = "ERROR " + ex;
            System.out.println("Error consultar cita " + ex);
            Logger.getLogger(AgendaWebServices.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resp;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "selectHorario")
    public String selectHorario(
                    @WebParam(name = "fecha") String xfecha,
                    @WebParam(name = "hora") String hora,
                    @WebParam(name = "sid") long sid
                    ){

        String resp = null;
        DataParameter dp = getEntorno(sid);
        TWSStandards tws = new TWSStandards();
        if(dp!=null) {

            try {
                Date fecha = tws.makeDate(xfecha);
                int actividad = (Integer) obtenerVariableEntorno(sid, "actividad");
                int cliente = (Integer)  Sesion.getVariableEntorno("cliente");
                tws.conectar();

                IFObjetoSerializado rs = new RespuestaSerializada();

                Agenda a = new Agenda();
                AgendaPK apk = new AgendaPK();

                apk.setConsultorio("INET");
                apk.setFecha(fecha);
                apk.setHora(hora);
                apk.setTurno(0);
                a.setActividad(actividad);
                a.setAgendaPK(apk);

                a.setEpisodio(null);
                a.setHabilitado('S');
                a.setMedico(null);
                a.setVino('N');
                a.setPaciente(cliente);
                a.setSesid(sid);
                a.setObservaciones("INTERNET");
                Clientes cli = (Clientes) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarCliente",1);
                a.setCliente(cli);

                System.err.println("SelectHorario - LISTO PARA INVOCAR SERVICIOALMACENARAGENDA " + a);
                boolean rsp = (Boolean) Sesion.getServiceDelegatorRemote().invoke("ServicioAlmacenarAgenda",a);
                System.err.println("SelectHorario - RESULTADO SERVICIOALMACENARAGENDA " + rsp);

                if(rsp == true) {
                    rs.addObjectName("IDCITA");
                    rs.addTag("IDCITA", sid+"");
                    resp = tws.makeResponse(true, "AGENDADO", rs);
                    cleanSession(sid);
                } else
                    resp = tws.makeResponse(false, "NO SE PUDO AGENDAR", null);

            } catch (Exception ex) {
                resp = "ERROR " + ex;
                System.out.println("Error selectHorario " + ex);
                Logger.getLogger(AgendaWebServices.class.getName()).log(Level.SEVERE, null, ex);
            }
         } else {
            try {
                    resp = tws.makeResponse(false, "SESION EXPIRADA", null);
            } catch (Exception ex) {
                    System.out.println("Error selectHorario " + ex);
                    Logger.getLogger(AgendaWebServices.class.getName()).log(Level.SEVERE, null, ex);
            }
         }

        return resp;
     }


    @WebMethod(operationName = "getDisponibilidades")
    public String getDisponibilidades(
                    @WebParam(name = "fecha") String xfecha,
                    @WebParam(name = "cantDias") int cantDias,
                    @WebParam(name = "servicio") int tiposervicio,
                    @WebParam(name = "sid") Long sid
                    ) {


        Integer z = (Integer) Sesion.getVariableEntorno("cliente");
        System.out.println("El cliente es : " + z);
        String resp = null;

        agregarASesion(sid, "actividad", tiposervicio);
        TWSStandards tws = new TWSStandards();
     try {
       tws.conectar();

       Date fecha;
       SimpleDateFormat sdf;

       //fecha = sdf.parse(xfecha);
       fecha = tws.makeDate(xfecha);

       Calendar cale = GregorianCalendar.getInstance();
       cale.setTime(fecha);
       cale.add(Calendar.DAY_OF_WEEK, cantDias);
       Date fechahasta = cale.getTime();

       sdf = new SimpleDateFormat("HH:mm");

       int tipoListado = 0;

       HashMap<String, Object> params = new HashMap();

       System.out.println("Fecha desde es :  " + fecha);
       System.out.println("Fecha hasta es :  " + fechahasta);

       params.put("fechadesde",  fecha);
       params.put("fechahasta",  fechahasta);
       params.put("tipolistado", tipoListado);
       params.put("orden",       "alfabetico");
       params.put("tipolistado", 0);

       List <Object> listaagenda = (List<Object>) Sesion.getServiceDelegatorRemote().invoke("CargarAgendaTelefonistaINET", params);

       SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm");
       Date now = new Date();
       String ahora = sdfDate.format(now);
       HorarioSerializado hs = null;
       IFObjetoSerializado ds = new DisponibilidadesSerializado();

       cale = GregorianCalendar.getInstance();
       cale.setTime(fecha);
       int diadehoy = cale.get(Calendar.DAY_OF_WEEK);
       int i = 0;

       System.out.println("BREAK#3");
       if (!listaagenda.isEmpty()) {

            for(Object obj : listaagenda) {

                Object [] o = (Object[]) obj;

                String xminutos = o[1]+"";
                if(xminutos.length()==1)
                    xminutos = "0" + xminutos;
                String xhora = o[0]+"";
                if(xhora.length()==1)
                    xhora = "0" + xhora;
                xhora+= ":" + xminutos;
                Integer lugares = (Integer) o[2];

                Date laFecha = (Date) o[3];

                System.out.println("FECHA ES " + fecha + " contra " + xhora);
                //if(xhora.compareTo(ahora) > 0 || fecha.after(now)){

                    hs = new HorarioSerializado();

                    hs.addTag("FECHA", tws.formatDate(laFecha));
                    hs.addTag("HORA", xhora);
                    hs.addTag("DIADELASEMANA", diadehoy + "");

                    ds.addTag("I-" + hs.getObjectName() + "-" + i, tws.serializeToXML(hs));
                    System.out.println("Agregando horario a respuesta web " + tws.formatDate(laFecha) + " " + xhora + " " + lugares);
                    i++;
                //}
            }
       }
       resp=tws.makeResponse(true, "HORARIOS DISPONIBLES", ds);
     } catch (Exception ex) {
            System.out.println("Exception ex " + ex);
     }

        
      return(resp);
    }
    /**
     * Web service operation
     */
    @WebMethod(operationName = "getDisponibilidades2")
    public String getDisponibilidades2(
                    @WebParam(name = "fecha") String xfecha,
                    @WebParam(name = "cantDias") int cantDias,
                    @WebParam(name = "servicio") int tiposervicio,
                    @WebParam(name = "sid") Long sid
                    ){

        int z = (Integer) Sesion.getVariableEntorno("cliente");
        System.out.println("El cliente es : " + z);
        String resp = null;

        try {

            agregarASesion(sid, "actividad", tiposervicio);
            TWSStandards tws = new TWSStandards();
            tws.conectar();

            Date hoy = tws.makeDate(xfecha);
            Date hasta = new Date();
            Calendar cale = new GregorianCalendar();
            Calendar cale1 = new GregorianCalendar();
            cale.setTime(hoy);
            cale1.setTime(hasta);
            cale1.add(Calendar.DAY_OF_YEAR, cantDias);

            HorarioSerializado hs = null;
            IFObjetoSerializado ds = new DisponibilidadesSerializado();

            int i = 0;
            int diadehoy = cale.get(Calendar.DAY_OF_WEEK);
            int minutoinicial;
            int horafinal;
            String xhh;
            String xmm;
            while(cale.getTime().before(cale1.getTime())) {

                int hh = 7;
                int weekday = cale.get(Calendar.DAY_OF_WEEK);
                if(weekday==7) {
                         minutoinicial = 30;
                         horafinal = 17;
                } else {
                         minutoinicial = 0;
                         horafinal = 18;
                  }

                while(hh <= horafinal) {

                    for(int mm=0;mm<=45;mm+=15) {
                        hs = new HorarioSerializado();

                        xhh = hh+"";
                        if(xhh.length()==1)
                            xhh = "0"+xhh;

                        xmm = mm+"";
                        if(xmm.length()==1)
                            xmm = "0"+xmm;

                        hs.addTag("FECHA", tws.formatDate(cale.getTime()));
                        hs.addTag("HORA", xhh + ":" + xmm);
                        hs.addTag("DIADELASEMANA", diadehoy + "");

                        ds.addTag("I-" + hs.getObjectName() + "-" + i, tws.serializeToXML(hs));
                        //System.out.println("Ahora voy en " + hh + " : " + mm);
                        i++;
                    }
                    ++hh;
                }
                cale.add(Calendar.DAY_OF_YEAR, 1);
            }

            resp=tws.makeResponse(true, "HORARIOS DISPONIBLES", ds);
        } catch (Exception ex) {
            resp = "ERROR " + ex;
            Logger.getLogger(AgendaWebServices.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error getDisponibilidades " + ex);
        }

        return(resp);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "identificarse")
    public String identificarse(@WebParam(name = "documento")
    String documento) {

        DataParameter dp = new DataParameter();
        Long sessionID = new Date().getTime();
        Boolean laresp = false;
        String resp = null;
        String citaprevia = null;
        IFObjetoSerializado persona = new PersonaSerializada();

        dp.set("cliente", dp);
        dp.set("horainicio", new Date());
        Sesion.putVariableEntorno("SID" + sessionID, dp);
        TWSStandards tws = new TWSStandards();
        
        try {
            tws.conectar();
            Personas per = (Personas) obtenerPersona(documento);

            if(per==null) {
                per = new Personas();
                per.setId(null);
                per.setNombres("");
                per.setApellidos("");
                per.setCiudad("");
                per.setDomicilio("");
                per.setDocumento(0);
                per.setEmail("");
                per.setFechanacimiento(new Date());
                per.setMovil("");
                per.setSexo(' ');
                per.setTelefono("");
                laresp = false;
            } else {
                    laresp = true;
                    Date hoy = new Date();
                    Agenda a = (Agenda) Sesion.getServiceDelegatorRemote().invoke("CargarUltimaCitaPersona",per);
                    if(a==null)
                          citaprevia=null;
                    else
                        if(a.getAgendaPK().getFecha().after(hoy) || a.getAgendaPK().getFecha().equals(hoy)) {
                        
                            citaprevia = a.getSesid()+"";
                        }
            }
            persona.addTag("documento", documento);
            persona.addTag("nombres", per.getNombres());
            persona.addTag("apellidos", per.getApellidos());
            persona.addTag("domicilio", per.getDomicilio());
            persona.addTag("ciudad", per.getCiudad());
            persona.addTag("telefono", per.getTelefono());
            persona.addTag("celular", per.getMovil());
            persona.addTag("email", per.getEmail());
            persona.addTag("fechanacimiento", tws.formatDate(per.getFechanacimiento()));
            persona.addTag("sexos", per.getSexo()+"");
            persona.addTag("id", per.getId()+"");
            persona.addTag("SID", sessionID+"");

            if(citaprevia!=null) {
                persona.addTag("citaprevia", citaprevia);
            }
            
            resp = tws.makeResponse(laresp, "DATOS PERSONA", persona);
            Sesion.putVariableEntorno("cliente", per.getId());
            
        } catch (Exception ex) {
            resp = "ERROR " + ex;
            System.out.println("Error identificarse " + ex);
            Logger.getLogger(AgendaWebServices.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resp;
    }


    @WebMethod(operationName = "actualizardatospaciente")
    public String actualizardatospaciente(
                        @WebParam(name = "id") Integer id,
                        @WebParam(name = "documento") String documento,
                        @WebParam(name = "nombres") String nombres,
                        @WebParam(name = "apellidos") String apellidos,
                        @WebParam(name = "domicilio") String domicilio,
                        @WebParam(name = "ciudad") String ciudad,
                        @WebParam(name = "telefono") String telefono,
                        @WebParam(name = "celular") String celular,
                        @WebParam(name = "email") String email,
                        @WebParam(name = "fechanacimiento") String fechanacimiento,
                        @WebParam(name = "sexo") String sexo
                        )
    {

        String resp = null;
        TWSStandards tws = new TWSStandards();
        try {
            tws.conectar();

            Boolean nuevo = false;
            Pacientes pac = null;
            Personas p = obtenerPersona(documento);
            if(p==null) {
                   p = new Personas();
                   p.setDocumento(Integer.parseInt(documento));
                   p.setId(null);
                   nuevo = true;
            }
//            else {
//                   p = new Personas();
//                   p.setId(null);
//                   pac = new Pacientes();
//                   pac.setFechaingreso(new Date());
//                   Seguros seguro = new Seguros();
//                   seguro.setId(0);
//                   pac.setSeguro(seguro);
//                   p.setPacientes(pac);
//                   pac.setId(p.getId());
//                   pac.setPersonas(p);
//            }

            p.setDocumento(Integer.parseInt(documento));
            p.setApellidos(apellidos);
            p.setCiudad(ciudad);
            p.setDomicilio(domicilio);
            Date fecha = tws.makeDate(fechanacimiento);
            p.setFechanacimiento(fecha);

            p.setNombres(nombres);
            p.setSexo(sexo.charAt(0));
            p.setTelefono(telefono);
            p.setMovil(celular);
            p.setEmail(email);

            pac = new Pacientes();
            pac.setPersonas(p);
            pac.setId(p.getId());
            if(pac.getSeguro()==null) {
                   Seguros seguro = new Seguros();
                   seguro.setId(0);
                   pac.setSeguro(seguro);
            }

            p = almacenarPersona(p);

            Sesion.putVariableEntorno("cliente", p.getId());
            System.out.println("Persona = " + p.getId());
            
            resp = tws.makeResponse(true, "DATOS ACTUALIZADOS CORRECTAMENTE", null);

        } catch (Exception ex) {
            try {
             resp = tws.makeResponse(false, ex.getMessage(), null);
            } catch(Exception exx) {
                System.out.println("Error actualizando datos persona " + exx);
                Logger.getLogger(AgendaWebServices.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AgendaWebServices.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resp;
    }

    private Personas almacenarPersona(Personas o) throws Exception {


        Personas p = (Personas) Sesion.getServiceDelegatorRemote().invoke("ServicioGuardarPersona", o);
        return(p);
    }
    private Personas obtenerPersona(String documento) {


        Personas p = null;
        try {
               p = (Personas) Sesion.getServiceDelegatorRemote().invoke("CargarPersona", Integer.parseInt(documento));

        } catch(Exception ex) {

            p = null;
        }
        return(p);
    }

    private DataParameter getEntorno(long id) {

        DataParameter dp = (DataParameter) Sesion.getVariableEntorno("SID" + id);

        return(dp);
    }

    private void cleanSession(long id) {

        Sesion.putVariableEntorno("SID" + id, null);
    }

    private void agregarASesion(long id, String clave, Object x) {

        DataParameter dp = (DataParameter) Sesion.getVariableEntorno("SID" + id);

        dp.set(clave, x);
    }

    private Object obtenerVariableEntorno(long id, String clave) {

        DataParameter dp = (DataParameter) Sesion.getVariableEntorno("SID" + id);

        return(dp.get(clave));
    }
}
