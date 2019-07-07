/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import talitakumi.framework.DataParameter;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.genericontrollers.pacientes.BuscarUnPacienteController;
import talitakumi.model.core.Actividades;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Medicos;
import talitakumi.model.core.Otrosservicios;
import talitakumi.model.core.Pacientes;
import talitakumi.model.core.Personas;
import talitakumi.model.core.Procedencias;
import talitakumi.servicios.ConvertPersona;
import ui.UICopiarLaboratorio;
import view.DialogoInformacion;
import view.UIPaciente;

/**
 *
 * @author rupertus
 */
public class CopiarLaboratorioCtrl extends AbstractVisualController {

    private UICopiarLaboratorio panel;
    private Logger logger;
    private List<Procedencias> procedencias;
    private List<Clientes> clientes;
    private HashMap result;
    private String lasterr = "";
    private Personas paux;
    private UIPaciente uipac;

    public CopiarLaboratorioCtrl() throws Exception {

        result = null;
        this.logger = Sesion.getLogger();
        panel = new UICopiarLaboratorio();
        uipac = new UIPaciente();

        panel.setVisible(true);
        uipac.setVisible(true);
        uipac.setFormatoCedulaLibre();
        //uipac.setFormatoCedulaUruguaya();
        panel.setPanelUtil(uipac);

        panel.setCancelarListener(new CancelarListener());
        panel.setGuardarListener(new AlmacenarListener());
        panel.setCedulaListener(new CedulaListener());

        setListaProcedencias();
        panel.empezarEdicion();
        setListaClientes();
        uipac.setEnabledVtoCds(false);
        uipac.setEnabledVtoLdc(false);
        uipac.setBuscarPacienteListener(new BuscarPacienteListener());
    }

    private void setListaProcedencias() throws Exception {

        procedencias = (List) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarProcedencias", null);
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
           nuevo = true;
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

        
        if(validarDatos(p)) {
            Otrosservicios ldc = new Otrosservicios();

            Boolean inmediato = panel.getInmediato();
            talitakumi.engine.framework.DataParameter dp = new talitakumi.engine.framework.DataParameter();
            dp.set("inmediato", inmediato);
            dp.set("actividad", ldc);

            Procedencias pr;

            int idx = panel.getProcedenciaElegida();
            pr = procedencias.get(idx);

            Clientes cl;
            
            idx = panel.getClienteELegido();
            cl = clientes.get(idx);

            ldc.setProcedencia(pr);

            Episodios e = new Episodios();

            Pacientes pac = new Pacientes();
            pac.setPersonas(p);
            pac.setId(p.getId());
            e.setPaciente(pac);
            e.setCliente(cl.getId());
            Actividades act = (Actividades) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarActividad", 12);
            e.setTipoepisodio(act);

            try {
                Medicos m = (Medicos) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarMedico", 0);
                e.setMedico(m);
            }  catch (Exception ex) {
                logger.error(new Date()+" error llamando ServicioCargarMedico");
                logger.error(ex);
            }

            ldc.setEpisodios(e);

            talitakumi.engine.framework.DataParameter dp1 = new talitakumi.engine.framework.DataParameter();
            dp1.set("actividad", ldc);
            dp1.set("inmediato", inmediato);

            if(!nuevo){
                        paux = (Personas) Sesion.getServiceDelegatorRemote().invoke("ServicioGuardarPersona", p);
            }

            result = (HashMap) Sesion.getServiceDelegatorRemote().invoke("ServicioGuardarSolicitudCopiaLaboratorio", dp1);

            String msg, info;

            if(result == null) {

                msg = "Error interno - no se pudo agendar -";
                info = "Error PsicotecnicosCtrl";
            } else {
                  info = "<h2>Pasado a caja " + "</h2>";
                  msg = "Anotado correctamente";
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

        Personas p = (Personas) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarPersona", cedula);

        return(p);
    }

    public Object getResult() {

        return(result);
    }

    private void actuarEnPersona() {

        int cedula = panel.getCedula();
        Personas p = null;
        try {
            p = obtenerPersona(cedula);
        } catch (Exception ex) {
            logger.error(ex);
        }

        if(p!=null) {
            try {
                DataParameter dp = new DataParameter();
                ConvertPersona conv = new ConvertPersona();
                dp = conv.fromObject(p);

                panel.setPersona(dp);
            } catch(Exception e) {
                logger.error(new Date() + " " + e);
            }
        }
    }

    private boolean validarDatos(Personas p) {

        Boolean resultado = true;

//        Date fdn = p.getFechanacimiento();
//        long edad = (new Date().getTime() - fdn.getTime());
//
//        edad = edad / 1000 / 60 / 60 / 24 / 367;
//
//        // TODO - ver que si tiene 17 falte solo un mes para los 18
//        
//        if(edad > 80 || edad < 18)
//            resultado = false;

        return(resultado);
    }

    private boolean validarVigencia(Date vigencialibretaanterior) {

        Date fechahoy = new Date();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(fechahoy);

        gc.add(GregorianCalendar.DATE, 31);
        fechahoy = gc.getTime();

        if(!vigencialibretaanterior.before(fechahoy)) {
            lasterr = "Vigencia libreta anterior en mas de 30 dias ";
        }
        return(vigencialibretaanterior.before(fechahoy));
    }

    @Override
    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void vaciarDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public class CedulaListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent arg0) {

        }

        @Override
        public void focusLost(FocusEvent arg0) {

            actuarEnPersona();
        }
    }
    
    private void buscarPaciente() {

        BuscarUnPacienteController bupc = new BuscarUnPacienteController();
        int cedula = bupc.getCedulaElegida();
        uipac.setCedula(cedula);
        actuarEnPersona();
    }
    
    public class BuscarPacienteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            try {

                buscarPaciente();
            } catch (Exception ex) {
                System.err.println("Error " + ex);
                Sesion.getLogger().error(ex);
              }
        }
    }
}
