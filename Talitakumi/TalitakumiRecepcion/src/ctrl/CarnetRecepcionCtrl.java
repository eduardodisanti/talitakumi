/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import com.kcreativa.livecam.QuickCam;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;
import talitakumi.framework.DataParameter;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.laboratorio.ExamenesAdicionalesCtrl;
import talitakumi.model.core.Actividades;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.Carnetdesalud;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.EpisodiosExAdicionales;
import talitakumi.model.core.Estudios;
import talitakumi.model.core.Medicos;
import talitakumi.model.core.Pacientes;
import talitakumi.model.core.Personas;
import talitakumi.model.core.Vencimientosdoc;
import talitakumi.servicios.ConvertPersona;
import talitakumi.servicios.Reporter;
import ui.UICarnetRecepcion;
import view.DialogoInformacion;
import view.UIPaciente;

/**
 *
 * @author rupertus
 */

class CarnetRecepcionCtrl extends AbstractVisualController {

    private UICarnetRecepcion panel;
    private Logger logger;
    private List<Clientes> procedencias;
    private HashMap result;
    private QuickCam camara;
    private UIPaciente uipac;
    private ExamenesAdicionalesCtrl ead;
    private DefaultTableModel dftmANT;
    private DialogoInformacion dlg;
    private Personas paux;
    private String observaciones;

    CarnetRecepcionCtrl() throws Exception {


            crearCotorro();

    }

    CarnetRecepcionCtrl(Agenda a) throws Exception {


        crearCotorro();

        Pacientes pac;
        Personas p;
        
        int idpac = a.getPaciente();
        pac = (Pacientes) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarPaciente", idpac);
        p = pac.getPersonas();
        uipac.setCedula(p.getDocumento());
        uipac.setFechanacimiento(p.getFechanacimiento());

        observaciones = a.getObservaciones();
        actuarEnPersona(observaciones);
    }

    private void crearCotorro() throws Exception {
        result = null;
        this.logger = Sesion.getLogger();
        panel = new UICarnetRecepcion();
        uipac = new UIPaciente();

        uipac.setFormatoCedulaLibre();

        uipac.agregarActivarCamaraListener(new ActivarCamaraListener());
        uipac.agregarTomarFotoListener(new TomarFotoListener());

        panel.setVisible(true);
        uipac.setVisible(true);
        panel.setPanelUtil(uipac);

        panel.setCancelarListener(new CancelarListener());
        panel.setGuardarListener(new AlmacenarListener());
        panel.setCedulaListener(new CedulaListener());
        panel.setCambiarFormatoCedulaListener(new CarnetComunListener());
        setListaProcedencias();
        setListaAdicionales();
        //setListaAntecedentes();
        panel.empezarEdicion();

        uipac.setEnabledVtoCds(false);
        uipac.setEnabledVtoLdc(true);
    }

    private void setListaProcedencias() throws Exception {

        procedencias = (List) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarClientes", "S");
        panel.setListaProcedencias(procedencias);
    }

    private void cambiarFormatoCedula() {

        panel.cambiarFormatCedulaSegunTipoCarnet();
    }

    @Override
    public void cerrarTodo() {

        panel.setVisible(false);
        panel.dispose();
    }

    @Override
    public void guardarDatos() throws Exception {

        int cedula = panel.getCedula();
        
        Date d = panel.getFechanacimiento();
        if(d == null) {
            DialogoInformacion dlg = new DialogoInformacion(panel, "Error en datos", "La fecha de nacimiento es incorrecta", "Corrijala e intentelo nuevamente", true);
            dlg.setVisible(true);
            throw new Exception();
        } else 
            if(cedula==0) {
                DialogoInformacion dlg = new DialogoInformacion(panel, "Error en datos", "La cédula no puede ser vacía o 0", "Corrijala e intentelo nuevamente", true);
                dlg.setVisible(true);
                throw new Exception();
            } else {
                
                logger.error(new Date() + " Comienzo almacenar carnet de salud cedula " + cedula);

                Boolean nuevo = false;
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
                p.setFechanacimiento(d);
                p.setMovil(panel.getMovil());
                p.setNombres(panel.getNombres());
                p.setSexo(panel.getSexo());
                p.setTelefono(panel.getTelefono());
                p.setEmail(panel.getEmail());

                Episodios e = new Episodios();

                Carnetdesalud ldc = new Carnetdesalud();

                Boolean inmediato = panel.getInmediato();
                talitakumi.engine.framework.DataParameter dp = new talitakumi.engine.framework.DataParameter();
  
                dp.set("inmediato", inmediato);
                dp.set("actividad", ldc);

                Clientes cliente = null;
                int idx = panel.getProcedenciaElegida();
                cliente = procedencias.get(idx);
                ldc.setCliente(cliente.getId());
                ldc.setVigencia(0);

                e.setCliente(cliente.getId());
                Pacientes pac = p.getPacientes();
                if(pac == null) {
                    pac = new Pacientes();
                    pac.setPersonas(p);
                    pac.setId(p.getId());
                    pac.setFechaingreso(new Date());
                    p.setPacientes(pac);
                }
                e.setPaciente(pac);
                ArrayList<EpisodiosExAdicionales> leea = new ArrayList<EpisodiosExAdicionales>();
                for(Estudios est : ead.obtenerEstudiosElegidos()) {

                    EpisodiosExAdicionales eea = new EpisodiosExAdicionales();
                    eea.setEpisodio(e);
                    eea.setId(null);
                    eea.setEstudio(est);
                    leea.add(eea);

                }

                e.setEpisodiosExAdicionalesCollection(leea);

                int ele = panel.getBotonCarnetElegido();

                Actividades act = (Actividades) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarActividad", ele);
                e.setTipoepisodio(act);

                try {
                    Medicos m = (Medicos) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarMedico", 0);
                    e.setMedico(m);
                }  catch (Exception ex) {
                    logger.error(ex);
                }

                ldc.setEpisodios(e);
                result = null;
                try {
                        logger.error("Invocando ServicioGuardarSolicitudCarnet cedula es "+p.getDocumento());
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
                        result = (HashMap) Sesion.getServiceDelegatorRemote().invoke("ServicioGuardarSolicitudCarnet", dp);
                }  catch (Exception ex) {
                    logger.error(ex);
                }

                String msg, info;

                if(result == null) {
                    msg = "Error interno - no se pudo agendar -";
                    info = "Error invocando ServicioGuardarSolicitudCarnet<br><h2>FIJARSE QUE TENGA CONSULTORIOS HABILITADOS</h2>";
                    logger.error("Error invocando GuardarSolicitudCarnet cedula : " + p.getDocumento() + " " + info);
                } else {
                      info = "<h2>Agendado consultorio : " + result.get("consultorio") +"</h2>";
                      msg = "Agendado correctamente";
                      logger.error("Guardado correctamente cedula : " + p.getDocumento());
                 }

                DialogoInformacion dlg = new DialogoInformacion(panel, "Resultado de la transaccion", msg, info, true);
                dlg.setVisible(true);
                if(result !=null) {
                   panel.setVisible(false);
                   panel.dispose();
                }
                emitirTirilla(e);
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

    private void actuarEnPersona(String observaciones) {

        int cedula = panel.getCedula();
        Personas p = null;
        try {
            p = obtenerPersona(cedula);
            cargarAntecedentes(p);
        } catch (Exception ex) {
            logger.error(ex);
        }

        if(p!=null) {
            try {
                DataParameter dp = new DataParameter();
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

    public void activarCamara() {

        camara = new QuickCam(uipac.getAnchoFoto(), uipac.getAltoFoto(), 0);
        uipac.getPanelFoto().add(camara);
        camara.setVisible(true);
        camara.empezar();
        uipac.setActivoBotonActivarCamara(false);
    }

    public void tomarFoto() {

        camara.pausa();
        camara.getLastImage();
        uipac.setActivoBotonActivarCamara(true);

    }


    private void emitirTirilla(Episodios e) {

        Calendar hoy = Calendar.getInstance();
        Calendar fechaNac = Calendar.getInstance();

        fechaNac.setTime(e.getPaciente().getPersonas().getFechanacimiento());

    	int years = hoy.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
    	if (hoy.get(Calendar.MONTH) < fechaNac.get(Calendar.MONTH)) {
            years --;
        }
    	if (hoy.get(Calendar.MONTH) == fechaNac.get(Calendar.MONTH) &&
    		hoy.get(Calendar.DATE) < fechaNac.get(Calendar.DATE)	) {
            years --;
        }


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

    private void setListaAdicionales() {
        ead = new ExamenesAdicionalesCtrl(2);
        
        panel.setContenidoAuxiliar(ead.getPanel());
    }

    private void crearModeloTablaAntecedentes() {
            dftmANT = new DefaultTableModel();
            dftmANT.addColumn("Id");
            dftmANT.addColumn("Actividad");
            dftmANT.addColumn("Fecha");
            dftmANT.addColumn("Vencimiento CDS");
            dftmANT.addColumn("Vencimiento Odo");

            panel.setModeloTablaAntecedentes(dftmANT);
    }

    private void cargarAntecedentes(Personas p) throws Exception {

        UICarnetRecepcion uic;
        crearModeloTablaAntecedentes();
        List<Episodios> le = (List<Episodios>) Sesion.getServiceDelegatorRemote().invoke("ObtenerActividadesDePersona", p);

        String fechax;
        String xVencimientoCDS, xVencimientoODO;
        int linea = 0;
        for(Episodios e : le) {
            Carnetdesalud cds = e.getCarnetdesalud();

            Calendar f1 = Calendar.getInstance();

            if(cds!=null) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                fechax = sdf.format(e.getFecha());

                f1.setTime(e.getFecha());
                int num = cds.getVigencia();
                if(num==-1)
                    xVencimientoCDS = "NO VISTO";
                    else
                    if(num==0)
                        xVencimientoCDS = "   -";
                    else {
                            f1.add(Calendar.MONTH, num);
                            xVencimientoCDS = sdf.format(f1.getTime());
                    }


                f1.setTime(e.getFecha());
                num = cds.getVigenciaodontologo();
                if(num==-1) {
                    xVencimientoODO = "NO VISTO";
                }
                else
                    if(num==0) {
                    xVencimientoODO = "NEGADO";
                }
                    else {
                        f1.add(Calendar.MONTH, num);
                        xVencimientoODO = sdf.format(f1.getTime());
                    }
                
                dftmANT.addRow(new Object[]{e.getId(), e.getTipoepisodio().getDescripcion(), fechax, xVencimientoCDS, xVencimientoODO});
                linea++;

                
//                if(cds.getVigencialaboratorio()==0 || cds.getVigenciamedico()==0 || cds.getVigenciaodontologo()==0 && cds.getVigencialaboratorio()!= -1 && cds.getVigenciamedico()!= -1 && cds.getVigenciaodontologo()!= -1){
//                    DialogoInformacion dlg = new DialogoInformacion(panel,"","ATENCION CARNE DE SALUD NEGADO"," ", true);
//                    dlg.setVisible(true);
//                    throw new Exception();
//                }
//                else
                    if(cds.getVigencialaboratorio()==6 || cds.getVigenciamedico()==6 || cds.getVigenciaodontologo()==6 /*&& cds.getVigencialaboratorio()!= -1 && cds.getVigenciamedico()!= -1 && cds.getVigenciaodontologo()!= -1*/){
                        DialogoInformacion dlg = new DialogoInformacion(panel,"","ATENCION ULTIMA VIGENCIA 6 MESES","Última vigencia 6 meses ", true);
                        dlg.setVisible(true);
                        throw new Exception();

                    }

//                if(cds.getVigencialaboratorio()== -1 || cds.getVigenciamedico()== -1 || cds.getVigenciaodontologo()== -1){
//                    DialogoInformacion dlg = new DialogoInformacion(panel,"","ATENCION NO COMPLETÓ EL CICLO"," ", true);
//                    dlg.setVisible(true);
//                    throw new Exception();
//
//                }
                
            }
        }
    }

    @Override
    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void vaciarDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public class ActivarCamaraListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            activarCamara();
        }

    }

    public class TomarFotoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            tomarFoto();
        }

    }

    public class CarnetComunListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            cambiarFormatoCedula();
        }
    }

    public class CedulaListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent arg0) {

            uipac.enfocarPaciente();
        }

        @Override
        public void focusLost(FocusEvent arg0) {

            actuarEnPersona(observaciones);
        }
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
}
