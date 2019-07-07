/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import talitakumi.framework.DataParameter;
import talitakumi.framework.Sesion;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.AgendaPK;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Libretasdeconducir;
import talitakumi.model.core.Medicos;
import talitakumi.model.core.Pacientes;
import talitakumi.model.core.Personas;
import talitakumi.model.core.Procedencias;
import talitakumi.model.core.Vencimientosdoc;
import talitakumi.servicios.ConvertPersona;
import ui.UILibretaRecepcion;
import view.DialogoInformacion;
import view.UIPaciente;

/**
 *
 * @author rupertus
 */
public class LibretaRecepcionCtrl extends talitakumi.framework.controller.AbstractVisualController {

    private UILibretaRecepcion panel;
    private Logger logger;
    private List<Procedencias> procedencias;
    private List<Clientes> clientes;
    private Object result;
    //private HashMap result;
    private String lasterr = "";
    private Personas paux;
    private AgendaPK a;
    private UIPaciente uipac;
    private String observaciones;

    public LibretaRecepcionCtrl() throws Exception {


        crearCotorro();
    }

    public LibretaRecepcionCtrl(Agenda a) throws Exception {

        crearCotorro();

        int idpac = a.getPaciente();
        Pacientes pac = (Pacientes) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarPaciente", idpac);
        Personas p;
        p = pac.getPersonas();
        
        
        uipac.setCedula(p.getDocumento());
        uipac.setFechanacimiento(p.getFechanacimiento());

        observaciones = a.getObservaciones();
        actuarEnPersona(observaciones);
    }

    private void crearCotorro() throws Exception {
   result = null;
        this.logger = talitakumi.framework.Sesion.getLogger();

        panel = new UILibretaRecepcion();
        System.out.println(panel);
        uipac = new UIPaciente();

        uipac.setFormatoCedulaLibre();

        panel.setVisible(true);
        uipac.setVisible(true);
        panel.setPanelUtil(uipac);

        panel.setCancelarListener(new CancelarListener());
        panel.setGuardarListener(new AlmacenarListener());
        panel.setCedulaListener(new CedulaListener());

        setListaProcedencias();
        panel.empezarEdicion();
        setListaClientes();

        uipac.setEnabledVtoCds(true);
        uipac.setEnabledVtoLdc(false);
    }

    private void setListaProcedencias() throws Exception {

        procedencias = (List) talitakumi.framework.Sesion.getServiceDelegatorRemote().invoke("ServicioCargarProcedencias", null);
        panel.setListaProcedencias(procedencias);
    }

    private void setListaClientes() throws Exception {

        clientes = (List) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarClientes", "S");
        panel.setListaClientes(clientes);
    }

    @Override
    public void cerrarTodo() {

        panel.setVisible(false);
        panel.dispose();
    }

    @Override
    public void guardarDatos() throws Exception {

              
       Boolean nuevo = false;
       int cedula = panel.getCedula();
        if(cedula==0) {
                DialogoInformacion dlg = new DialogoInformacion(panel, "Error en datos", "La cédula no puede ser vacía o 0", "Corrijala e intentelo nuevamente", true);
                dlg.setVisible(true);
                throw new Exception();
         }
       Personas p = obtenerPersona(cedula);
       
        if(p==null) {
           p = new Personas();
           p.setDocumento(cedula);
           p.setId(null);
           nuevo=true;
        }

        p.setApellidos(panel.getApellidos());
        p.setCiudad(panel.getCiudad());
        p.setDocumento(cedula);
        p.setDomicilio(panel.getDomicilio());
        Date d = panel.getFechanacimiento();
        p.setFechanacimiento(d);

        p.setMovil(panel.getMovil());
        p.setNombres(panel.getNombres());
        p.setSexo(panel.getSexo());
        p.setTelefono(panel.getTelefono());

        Date vigencialibretaanterior = null;
        try {
            vigencialibretaanterior = panel.getVigenciaLibretaAnterior();
        } catch (ParseException ex) {
            // TODO - arreglar esto para que de error
            vigencialibretaanterior = new GregorianCalendar().getTime();
        }
        
        if(validarDatos(p) && validarVigencia(vigencialibretaanterior)) {
            Libretasdeconducir ldc = new Libretasdeconducir();
            ldc.setVigenciaanterior(vigencialibretaanterior);
            ldc.setApto(' ');
            ldc.setCategoria(panel.getCategoria());
            ldc.setCodigo1(panel.getCodigo1());
            ldc.setCodigo2(panel.getCodigo2());
            ldc.setCodigo3(panel.getCodigo3());
            ldc.setCodigo3(panel.getCodigo3());
            ldc.setCodigo4(panel.getCodigo4());
            ldc.setCodigo5(panel.getCodigo5());
            ldc.setCodigo6(panel.getCodigo6());

            Procedencias pr;

            int idx = panel.getProcedenciaElegida();
            pr = procedencias.get(idx);

            Clientes cl;
            
            idx = panel.getClienteELegido();
            cl = clientes.get(idx);

            ldc.setProcedencia(pr.getId());
            ldc.setTipo(panel.getTipoLibreta());
            ldc.setVigencia(0);
            ldc.setDocumento("0");

            Episodios e = new Episodios();

            Pacientes pac = p.getPacientes();
            if(pac == null) {
                pac = new Pacientes();
                pac.setPersonas(p);
                pac.setId(p.getId());
                e.setPaciente(pac);
                pac.setFechaingreso(new Date());
                e.setCliente(cl.getId());
            }

            Boolean inmediato = panel.getInmediato();
                talitakumi.engine.framework.DataParameter dp = new talitakumi.engine.framework.DataParameter();
                dp.set("inmediato", inmediato);
                dp.set("actividad", ldc);


            try {
                Medicos m = (Medicos) talitakumi.framework.Sesion.getServiceDelegatorRemote().invoke("ServicioCargarMedico", 0);
                e.setMedico(m);
            }  catch (Exception ex) {
                logger.error(new Date()+" error llamando ServicioCargarMedico");
                logger.error(ex);
            }

            Clientes cliente = null;

         /*   try {
                cliente = (Clientes) talitakumi.framework.Sesion.getServiceDelegatorRemote().invoke("ServicioCargarCliente", 1);
                e.setCliente(cliente.getId());
            }  catch (Exception ex) {
                logger.error(new Date()+" error llamando ServicioCargarCliente");
                logger.error(ex);
            } */

            ldc.setEpisodios(e);

            Vencimientosdoc vd = new Vencimientosdoc();
            Date venclibretaanterior = panel.getVencimientoLDC();
            Date venccarnet = panel.getVencimientoCDS();

            List<Vencimientosdoc> lvd = (List<Vencimientosdoc>) pac.getVencimientosdocCollection();
            if(lvd==null) {

                lvd = new ArrayList<Vencimientosdoc>();
            }
            if(venclibretaanterior!=null) {

                vd.setId(null);
                vd.setDocumento(1);
                vd.setPaciente(pac);
                vd.setVence(venclibretaanterior);
                lvd.add(vd);
            }

            if(venccarnet!=null) {

                vd.setId(null);
                vd.setDocumento(2);
                vd.setPaciente(pac);
                vd.setVence(venccarnet);
                lvd.add(vd);
            }

            pac.setVencimientosdocCollection(lvd);
            p.setPacientes(pac);
            
            //if(!nuevo){
                        paux = (Personas) Sesion.getServiceDelegatorRemote().invoke("ServicioGuardarPersona", p);
            //}

            e.setPaciente(paux.getPacientes());
            result = (Integer) talitakumi.framework.Sesion.getServiceDelegatorRemote().invoke("ServicioGuardarSolicitudLibreta", dp);

            System.out.println("****************" +result);
            String msg, info;

            if(result == null) {

                msg = "Error interno - no se pudo agendar -";
                info = "Error LibretaRecepcionCtrl";
            } else {
                  info = "<h2>Agendado consultorio : " + result +"</h2>";
                  msg = "Agendado correctamente";
               }
            DialogoInformacion dlg = new DialogoInformacion(panel, "Resultado de la transaccion", msg, info, true);
            dlg.setVisible(true);
            if(result!=null) {
               panel.setVisible(false);
               panel.dispose();
            }
      } else {
            new DialogoInformacion(panel, "Error en los datos", "Falla en los requisitos", "La persona no cumple los requisitos para su categoria<br>"+lasterr, true).setVisible(true);
       }
    }

    @Override
    public JPanel getPanel() {

        return(panel.getPanelUtil());
    }

    Personas obtenerPersona(int cedula) throws Exception {

        Personas p = (Personas) talitakumi.framework.Sesion.getServiceDelegatorRemote().invoke("ServicioCargarPersona", cedula);

        return(p);
    }

    public Object getResult() {

        return(result);
    }

    private void actuarEnPersona(String observaciones) {

        int cedula = panel.getCedula();
        Personas p = null;
        try {
            p = obtenerPersona(cedula);
        } catch (Exception ex) {
            logger.error(ex);
        }

        if(p!=null) {
            try {
                talitakumi.framework.DataParameter dp = new DataParameter();
                ConvertPersona conv = new ConvertPersona();
                dp = conv.fromObject(p);
                dp.set("observaciones", observaciones);

                panel.setPersona(dp);
                panel.setVencimientoLibretaAnterior(cargarVencimientoLibreta(p.getPacientes()));
                panel.setVencimientoCarnetAnterior(cargarVencimientoCarnet(p.getPacientes()));
                
            } catch(Exception e) {
                logger.error(new Date() + " " + e);
            }
        }
    }

    private boolean validarDatos(Personas p) {

        Boolean resultado = true;

        String categoria = panel.getCategoria();

        Date fdn = p.getFechanacimiento();
        long edad = (new Date().getTime() - fdn.getTime());

        edad = edad / 1000 / 60 / 60 / 24 / 367;

        // TODO - ver que si tiene 17 falte solo un mes para los 18
        
        if(edad > 75 || edad < 16 || (edad < 17 && !categoria.equals("G1")))
            resultado = false;

        return(resultado);
    }

    private boolean validarVigencia(Date vigencialibretaanterior) throws Exception {

        Date fechahoy = new Date();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(fechahoy);

        gc.add(GregorianCalendar.DATE, 31);
        fechahoy = gc.getTime();

        int homologacion = panel.getTipoLibreta();
        Boolean ok = vigencialibretaanterior.before(fechahoy) || (homologacion ==5 || homologacion==4);

        if(!ok) {
            lasterr = "Vigencia libreta anterior en mas de 30 dias ";
        }
        return(ok);
    }

    @Override
    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void vaciarDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Date cargarVencimientoLibreta(Pacientes pac) {
        
        Date f = null;
        for(Vencimientosdoc vd : pac.getVencimientosdocCollection()) {
            
            if(vd.getDocumento()==1) {
                f = vd.getVence();
            }
        }
        
        return(f);
    }

    private Date cargarVencimientoCarnet(Pacientes pac) {
        
        Date f = null;
        for(Vencimientosdoc vd : pac.getVencimientosdocCollection()) {
            
            if(vd.getDocumento()==2) {
                f = vd.getVence();
            }
        }
        
        return(f);
    }

    public class CedulaListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent arg0) {

        }

        @Override
        public void focusLost(FocusEvent arg0) {

            actuarEnPersona(observaciones);
        }
    }
}
