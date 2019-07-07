/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.utilidades.ctrl;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;
import talitakumi.examenes.ExamenesAdicionalesCtrl;
import talitakumi.framework.DataParameter;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.model.core.Actividades;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.EpisodiosExAdicionales;
import talitakumi.model.core.Estudios;
import talitakumi.model.core.Medicos;
import talitakumi.model.core.Pacientes;
import talitakumi.model.core.Personas;
import talitakumi.model.core.Procedencias;
import talitakumi.model.core.Servicioexparaclin;
import talitakumi.servicios.ConvertPersona;
import talitakumi.servicios.Reporter;
import talitakumi.utilidades.ui.UICalidadAire;
import view.DialogoInformacion;
import view.UIPaciente;

/**
 *
 * @author rupertus
 */
public class CalidadAireCtrl extends AbstractVisualController {

    private UICalidadAire panel;
    private Logger logger;
    private List<Procedencias> procedencias;
    private List<Clientes> clientes;
    private HashMap result;
    private String lasterr = "";
    private DefaultTableModel dftmANT;
    private ExamenesAdicionalesCtrl ead;

    public CalidadAireCtrl() throws Exception {

        result = null;
        this.logger = Sesion.getLogger();
        panel = new UICalidadAire();
        UIPaciente uipac = new UIPaciente();
        uipac.setFormatoCedulaLibre();

        uipac.setVisible(true);
        panel.setPanelUtil(uipac);

        panel.setCancelarListener(new CancelarListener());
        panel.setGuardarListener(new AlmacenarListener());
        panel.setCedulaListener(new CedulaListener());

        setListaProcedencias();
        panel.empezarEdicion();
        setListaClientes();
        setListaExamenes();
        panel.setVisible(true);
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

       int cedula = panel.getCedula();
       Personas p = obtenerPersona(cedula);

        if(p==null) {
           p = new Personas();
           p.setDocumento(cedula);
           p.setId(null);
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
            Servicioexparaclin ldc = new Servicioexparaclin();

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
            Actividades act = (Actividades) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarActividad", 27);
            e.setTipoepisodio(act);

            try {
                Medicos m = (Medicos) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarMedico", 0);
                e.setMedico(m);
            }  catch (Exception ex) {
                logger.error(new Date()+" error llamando ServicioCargarMedico");
                logger.error(ex);
            }

            ArrayList<EpisodiosExAdicionales> leea = new ArrayList<EpisodiosExAdicionales>();
            for(Estudios est : ead.obtenerEstudiosElegidos()) {

                    EpisodiosExAdicionales eea = new EpisodiosExAdicionales();
                    eea.setEpisodio(e);
                    eea.setId(null);
                    eea.setEstudio(est);
                    leea.add(eea);
            }
            e.setEpisodiosExAdicionalesCollection(leea);

            ldc.setEpisodios1(e);
            ldc.setEpisodios(e.getId());

            talitakumi.engine.framework.DataParameter dp1 = new talitakumi.engine.framework.DataParameter();
            dp1.set("actividad", ldc);
            dp1.set("inmediato", inmediato);

            result = (HashMap) Sesion.getServiceDelegatorRemote().invoke("ServicioGuardarSolicitudChequeos", dp1);
            String msg, info;

            if(result == null) {
                msg = "Error interno - no se pudo agendar -";
                info = "Error ParaclinicosCtrl";
            } else {
                  info = "<h2>Pasado a consultorio " + result.get("consultorio") + "</h2>";
                  msg = "Anotado correctamente";
               }
            DialogoInformacion dlg = new DialogoInformacion(null, "Resultado de la transaccion", msg, info, true);
            dlg.setVisible(true);
            if(result!=null) {
               panel.setVisible(false);
               panel.dispose();
               emitirTirilla(e);
            }
      } else {
            new DialogoInformacion(null, "Error en los datos", "Falla en los requisitos", "La persona no cumple los requisitos para su categoria<br>"+lasterr, true).setVisible(true);
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

        Date fdn = p.getFechanacimiento();
        long edad = (new Date().getTime() - fdn.getTime());

        edad = edad / 1000 / 60 / 60 / 24 / 367;

        // TODO - ver que si tiene 17 falte solo un mes para los 18

        if(edad > 80 || edad < 18)
            resultado = false;

        return(resultado);
    }

    @Override
    public void setActividad(String descripcion) {
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

   private void setListaExamenes() {

        ead = new ExamenesAdicionalesCtrl(15);
        panel.setContenidoAuxiliar(ead.getPanel());
    }

    private void emitirTirilla(Episodios e) {

        Calendar hoy = Calendar.getInstance();
        Calendar fechaNac = Calendar.getInstance();

        fechaNac.setTime(e.getPaciente().getPersonas().getFechanacimiento());

    	int years = hoy.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
    	if (hoy.get(Calendar.MONTH) < fechaNac.get(Calendar.MONTH)) years --;
    	if (hoy.get(Calendar.MONTH) == fechaNac.get(Calendar.MONTH) &&
    		hoy.get(Calendar.DATE) < fechaNac.get(Calendar.DATE)	) years --;

         HashMap m = new HashMap();

         m.put("fecha", e.getFecha());
         m.put("apellidos", e.getPaciente().getPersonas().getApellidos());
         m.put("nombres", e.getPaciente().getPersonas().getNombres());
         m.put("cedula", e.getPaciente().getPersonas().getDocumento());
         m.put("hora", " ");
         m.put("edad", years);
         m.put("id", (Integer)result.get("episodio"));
         new Reporter("TirillaLaboratorio.jasper").listar(m);
    }
    
    public void vaciarDatos() {
        
    }
}
