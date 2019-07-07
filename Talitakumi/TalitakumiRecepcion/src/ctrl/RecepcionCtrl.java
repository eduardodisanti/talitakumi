
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import talitakumi.cajas.ctrl.TirillaCajaControl;
import talitakumi.engine.framework.DataParameter;
import talitakumi.framework.Sesion;
import talitakumi.genericontrollers.pacientes.ModificarPacienteCtrl;
import talitakumi.informes.control.EmitirCarnetsControl;
import talitakumi.informes.control.ListadoDiarioControl;
import talitakumi.informes.control.ResumenDeCajaControl;
import talitakumi.laboratorio.ctrl.BuscarEstudioController;
import talitakumi.model.core.Actividades;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.Carnetdesalud;
import talitakumi.model.core.Chequeos;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Consultorios;
import talitakumi.model.core.Consultoriosactivos;
import talitakumi.model.core.Episodioodontologo;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Facturas;
import talitakumi.model.core.Libretaprofesional;
import talitakumi.model.core.Libretasdeconducir;
import talitakumi.model.core.Pacientes;
import talitakumi.model.core.Personas;
import talitakumi.model.core.Timming;
import talitakumi.model.core.Usuarios;
import talitakumi.services.AbstractService;
import talitakumi.services.ServiceDelegator;
import talitakumi.servicios.Reporter;
import ui.UIRecepcion;
import view.DialogoError;
import view.DialogoInformacion;
import view.UIComentarEpisodioADM;
import view.UISiNo;

/**
 *
 * @author rupertus
 */
public class RecepcionCtrl {

    private List <Agenda> listaagenda;
    private UIRecepcion uic;
    private List<Clientes> procedencias;
    private HashMap<Integer, String> hmprocedencias; // TODO - CAGADA PARA LLEVAR LOS CLIENTES - ARREGLAR
    private Logger logger;
    private FileAppender appender;
    private Date fechadesde, fechahasta;
    private HeartBeat hb;
    private List listafacturas;
    private char entregado;
    private int times;

    private List<Agenda> alertas;

    public RecepcionCtrl() {

        times = 0;
        logger = Sesion.getLogger();

        uic = new UIRecepcion();

        uic.crearCopiaCDSListener(new CopiaCDSListener());
        uic.crearCopiaLaboratorioListener(new CopiaLaboratorioListener());
        uic.crearParaclinicosListener(new ParaclinicosListener());
        uic.crearVacunasListener(new VacunasListener());
        uic.crearPsicotecnicosListener(new PsicotecnicosListener());
        uic.crearLibretaListener(new LibretaListener());
        uic.crearCarnetListener(new CarnetListener());
        uic.crearLibretaProfListener(new LibretaProfListener());
        uic.salirListener(new SalirListener());
        uic.recargarListener(new RecargarListener());
        uic.crearFacturaElegidaListener(new FacturaElegidaListener());
        uic.crearListadoDiarioListener(new ListadoListener());
        uic.crearCondicionesListadoListener(new CambiarListado());
        uic.crearPacientesListener(new PacientesListener());
        uic.crearLaboratorioListener(new LaboratorioListener());
        uic.crearConsultoriosInmediatosListener(new ConsultoriosInmediatosListener());
        uic.crearBuscarListener(new BuscarListener());
        uic.crearChequeosListener(new ChequeosListener());
//        uic.crearHigieneListener(new HigieneListener());

        uic.resumenDeCajaListener(new ResumenCajaListener());
        uic.tirillaCajaListener(new TirillaCajaListener());

        uic.setVisible(true);
        Usuarios u = (Usuarios)(Sesion.getVariableEntorno("usuario"));
        uic.setFuncionario(u.getUsuario());
        uic.doMaximize();

        uic.setFechaDesde(new Date());
        uic.setFechaHasta(new Date());
        entregado = 'N';

        uic.setConsultoriosListener(new ConsultoriosListener());
        uic.setPacientesListener(new PacienteElegidoListener());
        uic.setObservacionesTelefonistaListener(new ObservacionesTelefonistaListener());
        uic.setEmitirCarnetListener(new EmitirCarnetListenerListener());

        cargarConsultorios();
        procedencias = new ArrayList<Clientes>();
        for(Clientes c : (List<Clientes>) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarClientes", "S")) {

            if(c.getHabilitado()=='S') {
                procedencias.add(c);
            }
        }
        //procedencias = (List) (Sesion.getServiceDelegatorRemote().invoke("ServicioCargarClientes", "S"));

        hmprocedencias = new HashMap<Integer, String>();
        for(Clientes cl : procedencias) {
            hmprocedencias.put(cl.getId(), cl.getNombre());
        }

        hb = new HeartBeat();
        hb.start();
    }

   private boolean esCajero() {

       Boolean resp = true;

       resp = (Boolean)Sesion.getVariableEntorno("Cajeros");
       if(resp==null) {
           resp = false;
       }

       
       return(resp);
   }

   private void ejecutarClickAgenda(Agenda a) {


       int te = a.getActividad();

       switch(te) {

           case 1 : 
                    agendarLibreta(a);
                    break;
                    
           case 2 :
                    agendarCarnet(a);
                    break;
       }
       
   }

   private void cargarPacienteElegido() {

     String fase = "1-";
     Agenda a = null;
     try {
       fase+="2-";
       int idx = uic.getIndiceTablaPacientes();
       fase+="3-";
       if(idx >= 0) {
           fase+="4-";
           int col = uic.getColumnaTablaPacientes();
           fase+="5-";
           a = buscarAgenda(idx);
           if(a.getAgendaPK().getConsultorio().equals("9001") || 
              a.getAgendaPK().getConsultorio().equals("INET") ||
              a.getAgendaPK().getConsultorio().equals("E") ||
              a.getAgendaPK().getConsultorio().equals("TEL")) {

               ejecutarClickAgenda(a);
           } else {
               fase+="6-";
               //a = listaagenda.get(idx);
               if(col < 3) {
                 fase+="7-";
                 int scoring = obtenerScoring(a);
                 fase+="8- SCORING ="+scoring;

                 switch(scoring) {

                   case 0 : sinTerminar(a);
                            break;
                   case 1 : noPuedoEntregar(a);
                            break;
                   case 2 : entregarConLimitaciones(a);
                            break;
                   case 3 : entregar(a);
                            break;
                   case 4 : sinTerminar(a);
                            break;

                }
             }  else {

                        switch(col) {
                            case 3 : mostrarHistoriaMedica(a);
                                     break;
                            case 4 : mostrarHistoriaLab(a);
                                     break;
                            case 5 : mostrarHistoriaOdo(a);
                                     break;
                            case 9 : comentarEpisodio(a.getEpisodio());
                                     break;
                        }
                }
           }
       }
     } catch(Exception ex) {
         new DialogoError(null, "Error interno : " + fase, "Error entregando documento", ex.toString(), true).setVisible(true);
     }
   }

   private void copiarCDS() {
        try {
            CopiarCDSCtrl lrc = new CopiarCDSCtrl();
        } catch (Exception ex) {
            new DialogoError(null, "Error interno", "Error intentando agendar Copiar Carnet de salud", ex.getMessage(), true).setVisible(true);
           logger.error(ex);
        }
   }

   private void copiarLaboratorio() {
        try {
            CopiarLaboratorioCtrl lrc = new CopiarLaboratorioCtrl();
        } catch (Exception ex) {
            new DialogoError(null, "Error interno", "Error intentando agendar CopiarLaboratorio", ex.getMessage(), true).setVisible(true);
           logger.error(ex);
        }
   }

   private void agendarParaclinicos() {
        try {
            ParaclinicosRecepcionCtrl lrc = new ParaclinicosRecepcionCtrl();
        } catch (Exception ex) {
            new DialogoError(null, "Error interno", "Error intentando agendar Paraclinicos", ex.getMessage(), true).setVisible(true);
           logger.error(ex);
        }
   }

   private void agendarVacunas() {
        try {
            VacunasRecepcionCtrl lrc = new VacunasRecepcionCtrl();
        } catch (Exception ex) {
            new DialogoError(null, "Error interno", "Error intentando agendar Vacunas", ex.getMessage(), true).setVisible(true);
           logger.error(ex);
        }
   }

   private void agendarPsicotecnicos() {
        try {
            PsicotecnicosRecepcionCtrl lrc = new PsicotecnicosRecepcionCtrl();
        } catch (Exception ex) {
            new DialogoError(null, "Error interno", "Error intentando agendar Psicotecnicos", ex.getMessage(), true).setVisible(true);
           logger.error(ex);
        }
   }

   private void agendarLibretaProf() {
        try {
            LibretaProfRecepcionCtrl lrc = new LibretaProfRecepcionCtrl();
        } catch (Exception ex) {
            new DialogoError(null, "Error interno", "Error intentando agendar libreta profesional", ex.getMessage(), true).setVisible(true);
           logger.error(ex);
        }

   }

   private void agendarLibreta() {
        try {
            LibretaRecepcionCtrl lrc = new LibretaRecepcionCtrl();
        } catch (Exception ex) {
            new DialogoError(null, "Error interno", "Error intentando agendar libreta", ex.getMessage(), true).setVisible(true);
           logger.error(ex);
        }
       
   }

   private void agendarLibreta(Agenda a) {
        try {
            LibretaRecepcionCtrl lrc = new LibretaRecepcionCtrl(a);
        } catch (Exception ex) {
            new DialogoError(null, "Error interno", "Error intentando agendar libreta", ex.getMessage(), true).setVisible(true);
           logger.error(ex);
        }

   }

   private void agendarCarnet() {
        try {
            CarnetRecepcionCtrl lrc = new CarnetRecepcionCtrl();
        } catch (Exception ex) {
            new DialogoError(null, "Error interno", "Error intentando agendar carnet", ex.getMessage(), true).setVisible(true);
           logger.error(ex);
        }
   }

   private void agendarCarnet(Agenda a) {
        try {
            CarnetRecepcionCtrl lrc = new CarnetRecepcionCtrl(a);
        } catch (Exception ex) {
            new DialogoError(null, "Error interno", "Error intentando agendar carnet", ex.getMessage(), true).setVisible(true);
           logger.error(ex);
        }
   }

    private void cambiarEstadoConsultorio(Integer ce) {

        try {

            Boolean ok = (Boolean) Sesion.getServiceDelegatorRemote().invoke("CambiarConsultorioActivo", ce);
            if(!ok) {
                new DialogoError(null, "Error interno", "Error cambiando estado del consultorio", "El servicio devolvio false", true).setVisible(true);
            }

        } catch (Exception e) {
           new DialogoError(null, "Error interno", "Error cambiando estado del consultorio", e.getMessage(), true).setVisible(true);
           logger.error(e);
        }


    }

    private void cargarConsultoriosEnUI(Collection<Consultorios> lista) {

        for(Consultorios c : lista) {

            uic.agregarConsultorio(c.getDescripcion(), c.getId());
        }
    }

   private void cargarConsultorioElegido() {

       Integer ce = uic.getConsultorioElegido();

       cambiarEstadoConsultorio(ce);
   }

   private void cargarFactura() {

       hb.running = false;
        new FacturaControl(uic.getFacturaElegida());

        hb.running = true;
        this.cargarFacturas();
   }

   private void cargarFacturas() {

        try{
            Facturas f = new Facturas();

            fechadesde = uic.getFechaDesde();
            fechahasta = uic.getFechaHasta();

            HashMap <String, Object> params = new HashMap();

            params.put("fechadesde", fechadesde);
            params.put("fechahasta", fechahasta);
            params.put("entregado", 'N');

            listafacturas = (List<Facturas>) Sesion.getServiceDelegatorRemote().invoke("CargarFacturas", params);
            int largo = listafacturas.size();
            DefaultTableModel dftm = new DefaultTableModel();
            dftm.addColumn("Paciente");
            dftm.addColumn("Factura");

            for(int i=0; i < largo;i++) {
               try{
                ImageIcon actividad = null;
                Pacientes p = new Pacientes();

                String nombre = "#ERR";
                f = (Facturas) listafacturas.get(i);
                if(f.getEpisodio()!=null) {
                    p = f.getEpisodio().getPaciente();
                    nombre = p.getPersonas().getNombres()+" "+p.getPersonas().getApellidos();
                }

                int act = f.getId();
                dftm.addRow(new Object[]{nombre, act});
               }
            catch (Exception ex){
                System.out.println("Excepcion al cargar la factura dentro del for "+ex);
            }
            }

            uic.setModelListaFacturas(dftm);

           uic.setAnchoColumnaFacturas(0, 180);
           uic.setAnchoColumnaFacturas(1, 50);
       }
            catch (Exception e){
                System.out.println("Excepcion al cargar la factura "+e);
            }
   }
  
   

   private void cerrar() {

       uic.setVisible(false);
       uic.dispose();
       hb.running = false;
       hb.parar();

   }

    private int obtenerScoring(Agenda a) {

        Integer score = 0;
        Integer scoremedico = 0;
        Integer scoreodo    = 0;
        Integer scorelab    = 0;
        
        Episodios e = a.getEpisodio();
        int tipoactividad = e.getTipoepisodio().getTipoactividad().getId();

        switch (tipoactividad) {

            case 9: case 8:
                scorelab = (Integer) Sesion.getServiceDelegatorRemote().invoke("ObtenerEstadoExamenesLaboratorio", e);
                /*
                if (scorelab == 2) // TODO - Sacar cuando ya no quieran que el lab no considere los resultados patológicos
                {
                    scorelab = 3;
                }
                */
                score = scorelab;
                break;

            case 14:
                scoreodo = (Integer) Sesion.getServiceDelegatorRemote().invoke("ObtenerEstadoRevisionOdontologica", e);
                break;

            case 15:
                if (e.getChequeos() == null) {
                    score = 1;
                } else if (e.getChequeos().getDictamenApto() == 'S') {
                    score = 3;
                } else {
                    score = 1;
                }
                break;

            case 2:
                scoremedico = (Integer) Sesion.getServiceDelegatorRemote().invoke("ObtenerEstadoCarnetDeSaludMedico", e.getCarnetdesalud());
                scorelab = (Integer) Sesion.getServiceDelegatorRemote().invoke("ObtenerEstadoExamenesLaboratorio", e);
                scoreodo = (Integer) Sesion.getServiceDelegatorRemote().invoke("ObtenerEstadoRevisionOdontologica", e);

                int tempscore = scoremedico * scoreodo * scorelab;
                if (tempscore < 1) {
                    score = 0;
                } else if (tempscore >= 6 && tempscore <= 12) {
                    score = 2;
                } else {
                    score = 3;
                }
                break;

            case 10:
                if (a.getEpisodio().getFacturas().getEntregado().equals("S")) {
                    score = 3;
                } else {
                    score = 1;
                }
                break;

            case 1:
                if (e.getLibretasdeconducir().getApto() == 'N') {
                    score = 1;
                } else {
                    score = 3;
                }
                break;
        }
        
        return(score);
    }

   private void consultoriosInmediatosPulsado() {

       hb.running = false;
       new InmediatosCtrl();
       hb.running = true;
       
   }

   private void pacientesPulsado() {

       hb.running = false;
       new ModificarPacienteCtrl();
       hb.running = true;
   }


   private void laboratorioPulsado() {

       hb.running = false;
       new BuscarEstudioController();
       hb.running = true;
   }

   private void emitirTirillaDeCaja() {

       TirillaCajaControl ldc = new TirillaCajaControl();

   }

   private void emitirResumenDeCaja() {

       int idusuario = ((Usuarios)Sesion.getVariableEntorno("usuario")).getFuncionario();
       ResumenDeCajaControl ldc = new ResumenDeCajaControl(uic.getFechaDesde(), uic.getFechaHasta(), idusuario);

   }

   private void listadoDiario() {

       int idusuario = ((Usuarios)Sesion.getVariableEntorno("usuario")).getFuncionario();
       ListadoDiarioControl ldc = new ListadoDiarioControl(uic.getFechaDesde(), uic.getFechaHasta(), idusuario);
         try {
            imprimirTicketControl();
         } catch(Exception ex) {
             new DialogoInformacion(null,"Error", "No se pudo emitir ticket de control ", ex.getMessage(), true).setVisible(true);
         }

   }

   private void cargarAgenda() throws ParseException {

            //Agenda a = new Agenda();
            Date horaingreso;
            String horaingresox;
            crearAlertas();

            SimpleDateFormat sdf;
            sdf = new SimpleDateFormat("HH:mm");
            System.out.println("HORA ES  : " +sdf);

            int tipoListado = uic.getCondicionesListado();

            fechadesde = uic.getFechaDesde();
            fechahasta = uic.getFechaHasta();
            HashMap<String, Object> params = new HashMap();

            params.put("fechadesde",  fechadesde);
            params.put("fechahasta",  fechahasta);
            params.put("tipolistado", tipoListado);
            params.put("orden",       "alfabetico");

            listaagenda = (List<Agenda>) Sesion.getServiceDelegatorRemote().invoke("CargarAgenda", params);
            
            DefaultTableModel dftm = new DefaultTableModel();
            dftm.addColumn("Act");
            dftm.addColumn("Paciente");
            dftm.addColumn("Consultorio");
            dftm.addColumn("Med");
            dftm.addColumn("Lab");
            dftm.addColumn("Odo");
            dftm.addColumn("Ingreso");
            dftm.addColumn("id");
            dftm.addColumn("Procedencia");
            dftm.addColumn("nota");

            System.out.println("CARGAR AGENDA BREAK #1");
            if (!listaagenda.isEmpty()) {

             System.out.println("CARGAR AGENDA BREAK #2");
             boolean mostrar = false;
             boolean terminado = false;
             for(Agenda a : listaagenda) {

             if(a.getEpisodio()==null) {
                 terminado = false;
             } else {
                     terminado = a.getEpisodio().getTerminado()=='S';
                 }

             System.out.println("CARGAR AGENDA BREAK #3");
             mostrar = false;
             if(tipoListado==0 && !terminado) {
                     mostrar = true;
                 }
             if(tipoListado==1) {
                     mostrar = true;
                 }
             if(tipoListado==2) {
                     mostrar = terminado;
                 }

             if(a.getAgendaPK().getConsultorio().equals("9001") || a.getAgendaPK().getConsultorio().equals("E")) {
                 mostrar = false;
             }
             if(mostrar) {
                     if(a.getEpisodio()!=null) {

                        ImageIcon actividad = null;
                        ImageIcon med       = null;
                        ImageIcon odo       = null;
                        ImageIcon lab       = null;

                        System.out.println("CARGAR AGENDA BREAK #4");
                        Pacientes p = a.getEpisodio().getPaciente();

                        System.out.println("CARGAR AGENDA BREAK #5");
                        String nombre = p.getPersonas().getApellidos() + " " + p.getPersonas().getNombres();
                        String consultorio = a.getAgendaPK().getConsultorio();

                        odo = obtenerIconoOdontologo(a.getEpisodio());

                        lab = obtenerIconoLaboratorio(a.getEpisodio());

                        med = obtenerIconoMedico(a.getEpisodio());

                        System.out.println("CARGAR AGENDA BREAK #6");
                        int act = a.getActividad();

                        actividad = getIconoActividad(act);

                        long atraso = 0;
                        Timming tim =  a.getEpisodio().getTimming();
                        if(tim != null) {
                            horaingreso = tim.getHoraingreso();

                            sdf = new SimpleDateFormat("HH:mm");
                            horaingresox = sdf.format(horaingreso);

                        }
                        else {
                             horaingresox = "";
                         }

                        System.out.println("CARGAR AGENDA BREAK #7");
                        atraso = obtenerAtraso(a.getAgendaPK().getFecha(), a.getAgendaPK().getHora());
                        Integer cliente = a.getEpisodio().getCliente();
                        if(cliente==null) {
                             cliente = 0;
                         }
                        String xproc = "ERR#" + cliente;
                        if(cliente!=1) {
                            xproc = hmprocedencias.get(cliente);
                        } else {
                             xproc = "Particulares";
                         }

                        if(xproc==null) {
                             xproc = " - " + cliente;
                         }


                        System.out.println("CARGAR AGENDA BREAK #8");
                        ImageIcon coment = new ImageIcon(getClass().getResource("/imagenes/gris.gif"));
                        if(a.getEpisodio().getComentarios()!=null) {
                             if(a.getEpisodio().getComentarios().length()>0) {
                                coment = new ImageIcon(getClass().getResource("/imagenes/ledrojo2.gif"));
                            }
                         }

                         System.out.println("CARGAR AGENDA BREAK #9");
                        try {
                            if(atraso > 3600 && a.getEpisodio().getTerminado()!='S' && atraso <= 10800) {
                               agregarAtraso(a);
                            }
                            if(!estaEnAlerta(a) || tienePendientes(a)) {
                                dftm.addRow(new Object[]{actividad, nombre, consultorio, med, lab, odo, horaingresox, a.getEpisodio().getId(), xproc, coment});
                            }

                            System.out.println("CARGAR AGENDA BREAK #10");
                         } catch(Exception ex) {
                             //dftm.addRow(new Object[]{actividad, nombre, consultorio, med, lab, odo, horaingresox, a.getEpisodio().getId(), xproc, coment});
                             System.out.println("Error excepcion TalitakumiRecepcion #888" + ex);
                           }
                } else {

                         System.out.println("CARGAR AGENDA BREAK #11");
                         if(!estaEnAlerta(a)) {
                                System.out.println("CARGAR AGENDA BREAK #11.1");
                                ImageIcon gris = new ImageIcon(getClass().getResource("/imagenes/gris.gif"));
                                System.out.println("CARGAR AGENDA BREAK #11.2");
                                ImageIcon actividad = getIconoActividad(a.getActividad());
                                System.out.println("CARGAR AGENDA BREAK #11.3");
                                Personas p = cargarPersona(a.getPaciente());
                                System.out.println("CARGAR AGENDA BREAK #11.4");
                                if(p!=null) {
                                    dftm.addRow(new Object[]{actividad, p.getApellidos() + " " + p.getNombres(), a.getAgendaPK().getConsultorio(), gris, gris, gris, a.getAgendaPK().getHora(), a.getSesid(), a.getCliente().getNombre(), gris});
                                }
                            System.out.println("CARGAR AGENDA BREAK #11.5");
                         }
                         System.out.println("CARGAR AGENDA BREAK #12");
                }
               }
          }

       }
            System.out.println("CARGAR AGENDA BREAK #13");
           uic.setModelListaAgenda(dftm);

           int z = uic.getAnchoPantalla();

           uic.setAnchoColumna(0, z *32 / 1024);
           uic.setAnchoColumna(1, z *300 / 1024);
           uic.setAnchoColumna(2, z *100 / 1024);
           uic.setAnchoColumna(3, z *32 / 1024);
           uic.setAnchoColumna(4, z *32 / 1024);
           uic.setAnchoColumna(5, z *32 / 1024);
           uic.setAnchoColumna(6, z *50 / 1024);
           uic.setAnchoColumna(7, z *44 / 1024);
           uic.setAnchoColumna(8, z *88 / 1024);
           uic.setAnchoColumna(9, z *32 / 1024);

           uic.setHora(sdf.format(new Date()));
           System.out.println("CARGAR AGENDA BREAK #14");
   }

    private ImageIcon getIconoActividad(int act) {

        ImageIcon actividad = new ImageIcon(getClass().getResource("/imagenes/core.png"));
        
        try {
                actividad = new ImageIcon(getClass().getResource("/imagenes/bookmark.png"));

                Actividades activity = cargarActividad(act);
                
                String src = "/imagenes/" + activity.getIcono();
                //System.out.println(" paso por aca " );

                actividad = new ImageIcon(getClass().getResource(src));
/*                switch (act) { // TODO - PROPERTIES

                    case 1 :  actividad = new ImageIcon(getClass().getResource(src));
                              break;
                    case 2 | 3 | 4 | 5 | 7:  actividad = new ImageIcon(getClass().getResource("/imagenes/carnetmini.png"));
                              break;
                    case 8 :  actividad = new ImageIcon(getClass().getResource("/imagenes/taximini.png"));
                              break;
                    case 10 :  actividad = new ImageIcon(getClass().getResource("/imagenes/autobus-rojomini.png"));
                              break;
                    case 11 :  actividad = new ImageIcon(getClass().getResource("/imagenes/copiacarnetmini.png"));
                              break;
                    case 12 :  actividad = new ImageIcon(getClass().getResource("/imagenes/laboratoriomini.png"));
                              break;
                    case 13 :  actividad = new ImageIcon(getClass().getResource("/imagenes/laboratoriomini.png"));
                              break;
                    case 14 :  actividad = new ImageIcon(getClass().getResource("/imagenes/vacunamini.png"));
                              break;
                    case 15 : actividad = new ImageIcon(getClass().getResource("/imagenes/humano.png"));
                              break;
 * 
 */

        } catch(Exception ex) {
            System.out.println("---> ERROR cargando icono de actividad " + act); 
       }
       return(actividad);
    }

    private void mostrarConsultoriosActivos(Collection<Consultoriosactivos> lista) {

        
        for(Consultoriosactivos ca : lista) {

            uic.cambiarEstadoConsultorio(ca.getConsultorio(), ca.getActivo()=='S');
            
        }
        uic.setPermitirCambiarConsultorios(esCajero());
    }

    private int obtenerEstadoCarnetDeSaludMedico(Episodios episodio) {

        int score = 0;
        if(episodio.getMedico().getCodigo()!=0) {
            try {
                    Integer scoring = (Integer) Sesion.getServiceDelegatorRemote().invoke("ObtenerEstadoCarnetDeSaludMedico", episodio.getCarnetdesalud());
                    if(scoring == null)
                        score = -1;
                    else
                        score = scoring;

             } catch(Exception e) {
                 score = -1;
             }
        }
        return(score);
    }

    private ImageIcon obtenerIconoLaboratorio(Episodios episodio) {

        String imagen = "/imagenes/gris.gif";
        ImageIcon lab;

        try {
                if(episodio.getTipoepisodio().getTipoactividad().getId()== 2 ||
                   episodio.getTipoepisodio().getTipoactividad().getId()==9) {
                   imagen = "/imagenes/reloj.png";
                   Integer scoring = 0;
                   //if(episodio.getCarnetdesalud().getVigencialaboratorio()!=-1)
                   scoring = (Integer) Sesion.getServiceDelegatorRemote().invoke("ObtenerEstadoExamenesLaboratorio", episodio);
                   if(scoring == 1) {
                          imagen = "/imagenes/cancel.png";
                   } else
                        if(scoring == 2)
                            imagen = "/imagenes/critico.png";
                        else
                         if(scoring == 3)
                            imagen = "/imagenes/ok.png";
                     }
                if(episodio.getTipoepisodio().getTipoactividad().getId() == 9 ) {

                    if(!imagen.equals("/imagenes/reloj.png"))
                       imagen = "/imagenes/ok.png";
                }
         } catch(Exception e) {
             imagen = "/imagenes/core.png";
             System.out.println("Error estado laboratorio " + e);
         }

         lab = new ImageIcon(getClass().getResource(imagen));
         return(lab);
    }

    private ImageIcon obtenerIconoMedico(Episodios episodio) {

        ImageIcon med;
        String imagen = "/imagenes/reloj.png";

        int tact = episodio.getTipoepisodio().getTipoactividad().getId();
        if(tact==2 || tact==1) {
         Carnetdesalud cds = episodio.getCarnetdesalud();
         if(cds!=null) {
        
            int score = obtenerEstadoCarnetDeSaludMedico(episodio);

            switch (score)  {
                    case -1: imagen = "/imagenes/core.png";
                            break;
                    case 1 : imagen = "/imagenes/cancel.png";
                            break;
                    case 2 : imagen = "/imagenes/critico.png";
                            break;
                    case 3 : imagen = "/imagenes/ok.png";
                            break;
            }
        } else {
                Libretasdeconducir ldc = episodio.getLibretasdeconducir();
                if(ldc!=null) {
                    if(ldc.getApto()=='N')
                        imagen = "/imagenes/cancel.png";
                    else
                        imagen = "/imagenes/ok.png";
                }
          }
        } else {
                if(tact==15) {

                    imagen = "/imagenes/reloj.png";
                    if(episodio.getChequeos()!=null) {
                        if(episodio.getChequeos().getDictamenApto()=='S')
                            imagen = "/imagenes/ok.png";
                        else
                            if(episodio.getChequeos().getDictamenApto()=='N')
                                imagen = "/imagenes/cancel.png";
                            else
                                imagen = "/imagenes/reloj.png";
                   }
                } else
                 imagen = "/imagenes/gris.gif";
               }
        med = new ImageIcon(getClass().getResource(imagen));
        return(med);
    }

    private ImageIcon obtenerIconoOdontologo(Episodios episodio) {
        String imagen = "/imagenes/gris.gif";
        ImageIcon odo;

        try {
            if(episodio.getTipoepisodio().getTipoactividad().getId()==2) {
                if(episodio.getCarnetdesalud().getVigenciaodontologo()==6) {
                       imagen = "/imagenes/critico.png";
                } else
                        if(episodio.getCarnetdesalud().getVigenciaodontologo()==0) {
                              imagen = "/imagenes/cancel.png";
                       } else 
                            if(episodio.getCarnetdesalud().getVigenciaodontologo()==-1) {
                              imagen = "/imagenes/reloj.png";
                       } else {
                                imagen = "/imagenes/ok.png";
                         }
            } else {
                if(episodio.getTipoepisodio().getTipoactividad().getId()==14) {
                   imagen = "/imagenes/reloj.png";
                   Integer scoring = (Integer) Sesion.getServiceDelegatorRemote().invoke("ObtenerEstadoRevisionOdontologica", episodio);
                   if(scoring == 1) {
                          imagen = "/imagenes/cancel.png";
                   } else
                        if(scoring == 2)
                            imagen = "/imagenes/critico.png";
                        else
                         if(scoring ==3)
                            imagen = "/imagenes/ok.png";
                     }
                }                   
         } catch(Exception e) {
             imagen = "/imagenes/core.png";
         }

         odo = new ImageIcon(getClass().getResource(imagen));
         return(odo);

    }

    private void sinTerminar(Agenda a) {


        emitirTirilla(a.getEpisodio().getId());
        String mensaje = "No esta terminado";

        new DialogoInformacion(null,"Entregar documento", "No es posible entregar el documento", "Los procedmientos no han sido terminados", true).setVisible(true);

    }

    private void noPuedoEntregar(Agenda a) {

        String mensaje = "No puede entregarse";

        String motivos, motivomedico = "", motivoodontologico = "", motivolaboratorio = "";

        motivos="";

        if(a.getEpisodio().getCarnetdesalud()!=null) {
            if(a.getEpisodio().getCarnetdesalud().getCausamedica()=='S') {
                motivomedico = "NEGADO CAUSA MEDICA";
            }
            if(a.getEpisodio().getCarnetdesalud().getVigenciaodontologo()==0) {
                motivomedico = "NEGADO CAUSA ODONTOLOGICA";
            }

            if(a.getEpisodio().getCarnetdesalud().getVigencialaboratorio()==0) {
                motivomedico = "NEGADO POR LABORATORIO";
            }
            motivos = motivomedico + "<br>" + motivoodontologico + "<br>" + motivolaboratorio;
        }
        new DialogoInformacion(null,"Entregar documento", "No es posible entregar el documento", motivos, true).setVisible(true);
    }

    private void entregarConLimitaciones(Agenda a) {

            entregar(a);
    }

    private void entregar(Agenda a) {

        int epi = a.getEpisodio().getId();
        Date hoy = new Date();
        
        // Entrego solo si me confirmar
            UISiNo dlgSiNo = new UISiNo(null, true);
            Timming t = new Timming();
            t.setEpisodio(epi);

            dlgSiNo.setTexto("<h2>Esta seguro que deseea entregar ? </h2>");
            dlgSiNo.setVisible(true);

            if(dlgSiNo.getReturnStatus()==UISiNo.RET_OK) {
                int maxvigencia = 0;
                int vigenciamedico, vigenciaodo, vigencialab;
                a.getEpisodio().setTerminado('S');
                t.setFechaatencion(hoy);
                t.setHoraatencion(hoy);
                a.setVino('S');

                if(a.getEpisodio().getTipoepisodio().getTipoactividad().getId()==2) {

                    vigenciamedico = a.getEpisodio().getCarnetdesalud().getVigenciamedico();
                    vigenciaodo = a.getEpisodio().getCarnetdesalud().getVigenciamedico();
                    vigencialab = a.getEpisodio().getCarnetdesalud().getVigencialaboratorio();

                    if(vigenciamedico > maxvigencia) {
                        maxvigencia = vigenciamedico;
                    }
                    if(vigenciaodo > maxvigencia) {
                        maxvigencia = vigenciaodo;
                    }
                    if(vigencialab > maxvigencia) {
                        maxvigencia = vigencialab;
                    }
                    a.getEpisodio().getCarnetdesalud().setVigencia(maxvigencia);
                 }
                 a.getEpisodio().setTerminado('S');
                 Sesion.getServiceDelegatorRemote().invoke("ServicioModificarAgenda", a);
                

                String textovigencia = "Vigencia otorgada " + maxvigencia + " meses";
                new DialogoInformacion(null, "Entregar documento","Puede entregar el documento", textovigencia, true).setVisible(true);
                
                // EMITIR CARNET DE SALUD
                /*
                if(a.getEpisodio().getCarnetdesalud()!=null) {
                       emitirCarnetDeSalud(a, maxvigencia);
                }
                * */
            }
    }

    private void emitirCarnetDeSalud(Agenda a, int vigenciameses) {
                    HashMap m = new HashMap();
                    m.put("nombres", a.getEpisodio().getPaciente().getPersonas().getNombres());
                    m.put("apellidos", a.getEpisodio().getPaciente().getPersonas().getApellidos());
                    m.put("documento", a.getEpisodio().getPaciente().getPersonas().getDocumento());
                    m.put("fecha_nacimiento", a.getEpisodio().getPaciente().getPersonas().getFechanacimiento());
                    m.put("fecha_expedicion", a.getEpisodio().getFecha());
                    m.put("fecha_vence", calcularVencimiento(vigenciameses, a.getEpisodio().getFecha()));
                    m.put("sexo", a.getEpisodio().getPaciente().getPersonas().getSexo()+"");
                
                    new Reporter("carnet.jasper").listar(m);        
    }
    
    private void mostrarHistoriaMedica(Agenda a) {

        if(a.getEpisodio()!=null) {
            Episodios e = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio", a.getEpisodio().getId());

            e = (Episodios) new ModificarActividadesDispatcher(e).getResult();

            if(e!=null)
                 Sesion.getServiceDelegatorRemote().invoke("ServicioAlmacenarEpisodio", e);
        }
    }

    private void mostrarHistoriaLab(Agenda a) {

        if(a.getEpisodio()!=null) {
          Episodios e = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio", a.getEpisodio().getId());
          e = (Episodios) new MostrarLaboratorioDispatcher(e).getResult();
         }
    }

    private void mostrarHistoriaOdo(Agenda a) {
        if(a.getEpisodio()!=null) {
          Episodios e = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio", a.getEpisodio().getId());
          e = (Episodios) new MostrarOdontologoDispatcher(e).getResult();
        }
    }

    private Agenda buscarAgenda(int idx) {

        String nombrebuscado  = (String) uic.getValorEnXY(idx, 1);
        //Integer elid = (Integer) uic.getValorEnXY(idx, 7);
        Object xelid = (Object) uic.getValorEnXY(idx, 7);

        Integer elid;

        if(xelid instanceof Integer) {
            elid = (Integer)uic.getValorEnXY(idx, 7);
        }
        else {
            elid = ((Long)uic.getValorEnXY(idx, 7)).intValue();
        }
        
        Agenda resp = null;

        for(Agenda a : listaagenda) {

            if(a.getEpisodio()!=null) {
                Personas pers = a.getEpisodio().getPaciente().getPersonas();

                String nombres = pers.getApellidos() + " " + pers.getNombres();
                int unid = a.getEpisodio().getId();
                if(nombres.equals(nombrebuscado) && unid == elid) {
                    resp = a;
                    break;
                }
            } else {

                    System.out.println("ELID es " + elid);
                    Personas unpac = cargarPersona(a.getPaciente());
                    String nombres = unpac.getApellidos() + " " + unpac.getNombres();
                    Long unid = a.getSesid();
                    if(nombres.equals(nombrebuscado) && unid.intValue() == elid) {
                        resp = a;
                        break;
                    }
            }
        }

        return(resp);
    }

    private void emitirTirilla(int idepisodio) {
        
        Calendar hoy = Calendar.getInstance();
        Calendar fechaNac = Calendar.getInstance();

        Episodios e = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio", idepisodio);
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
         m.put("id", idepisodio);
         new Reporter("TirillaLaboratorio.jasper").listar(m);


    }

    private void comentarEpisodio(Episodios episodio) {


        int idepisodio = (Integer) episodio.getId();

        Episodios e = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio", idepisodio);

        UIComentarEpisodioADM imgdlg = new UIComentarEpisodioADM(null, true);
        imgdlg.setComentarios(e.getComentarios());
        imgdlg.setVisible(true);

        e.setComentarios(imgdlg.getComentarios());

        if(e!=null)
             Sesion.getServiceDelegatorRemote().invoke("ServicioAlmacenarEpisodio", e);

    }

    private int obtenerAtraso(Date fecha, String xhoraingreso) {

        int resp = 0;
        try {
            SimpleDateFormat unf = new SimpleDateFormat("dd/MM/yyyy");

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy:HH:mm");
            Date horaingreso = sdf.parse(unf.format(fecha) + ":" + xhoraingreso);
            Date horaactual = new Date();

             resp = (int)(( horaactual.getTime() - horaingreso.getTime()) / 1000);
        } catch(Exception ex) {

        }

        return(resp);
    }

    private Actividades cargarActividad(int actividad) {

        Actividades a = null;

        a = (Actividades) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarActividad", actividad);
        return(a);
    }

    private Personas cargarPersona(int paciente) {

        Pacientes p = null;
        Personas pers = null;
        try {
            p = (Pacientes) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarPaciente", paciente);
            if(p.getPersonas()!=null) {
                pers = p.getPersonas();
            } else {
                System.out.println("ERROR : HAY UN PACIENTE " + paciente + " que tiene persona en null");
            }
        } catch(Exception ex) {
            System.out.println("ERROR : HAY UN PACIENTE " + paciente + " QUE DA ERROR CUANDO SE CARGA " + ex);
        }
        
        return(pers);
    }

    private boolean tienePendientes(Agenda a) {

        boolean resp = true;
        if(a.getEpisodio()==null) {
            resp = false;
        } else {

            Episodios e = a.getEpisodio();
            Chequeos c = e.getChequeos();
            if(c!=null) {
                if(c.getVigencia()!=null) {
                    resp = false;
                }
            }

            Libretaprofesional lp = e.getLibretaprofesional();
            if(lp!=null) {
                    resp = false;
            }
            if(a.getAgendaPK().getConsultorio().equals("9001") || a.getAgendaPK().getConsultorio().equals("E")) {
                resp = false;
            }
        }

        return(resp);
    }

    private Date calcularVencimiento(int maxvigencia, Date fecha) {
        
        Calendar cale = GregorianCalendar.getInstance();
        cale.add(Calendar.MONDAY, maxvigencia);
        
        return(cale.getTime());
    }

   class HeartBeat extends Thread {
        private boolean running;

        public void parar() {

            running = false;
        }

     public HeartBeat() {
            super();
            running = true;
     }
        
   @Override
     public void run() {

      while(running) {
           if(running) {
               try {
                   System.out.println("BEFORE RecepcionCtrl.run cargarAgenda");
                cargarAgenda();
                    System.out.println("BEFORE RecepcionCtrl.run cargarFacturas");
                cargarFacturas();
                System.out.println("BEFORE RecepcionCtrl.run cargarConsultoriosActivos");
                cargarConsultoriosActivos();
                    System.out.println("BEFORE RecepcionCtrl.run while");
                cargarAlertas();
               } catch(Exception ex) {

                   //new DialogoError(uic, "Error !", "Error de sistema en RecepcionCtrl", ex.getMessage(), true).setVisible(true);
                   System.out.println("Error RecepcionCtrl.run " + ex);
               }
             }
           try {
                    Thread.sleep(300000);
                    times++;
                    if(times>=2) {
                        times = 0;
                        running = false;
                        System.out.println("Salida por timeout de recepcion ctrl ");
                        cerrar();
                    }
               } catch (InterruptedException ex) {
                    logger.error(ex);
                    System.out.println("ERROR en RecepcionCtrl : " + ex);
            }
      }
     }

   }

    private void crearAlertas() {
        alertas = new ArrayList<Agenda>();
    }

    private void agregarAtraso(Agenda a) {

        alertas.add(a);
    }

    private void cargarAlertas() throws ParseException {
        DefaultTableModel dftm = new DefaultTableModel();

        int atraso = 0;
        int atrasoAlerta1 = 4800;
        int atrasoAlerta0 = 3600;

        ImageIcon icono;

        dftm.addColumn("AL");
        dftm.addColumn("-");
        dftm.addColumn("Paciente");
        dftm.addColumn("Hora");

        
        for(Agenda a : alertas) {

            if(a.getEpisodio()!=null && !a.getAgendaPK().getConsultorio().equals("9001") && 
                    !a.getAgendaPK().getConsultorio().equals("E")) {
                if(a.getEpisodio().getTipoepisodio().getTipoactividad().getId()==1  ||
                   a.getEpisodio().getTipoepisodio().getTipoactividad().getId()==2  ||
                   a.getEpisodio().getTipoepisodio().getTipoactividad().getId()==14
                  ) {
                      // CONTROL PARA LAS LIBRETAS DE CONDUCIR
                      if(a.getEpisodio().getTipoepisodio().getId().equals(1) ||
                         a.getEpisodio().getTipoepisodio().getId().equals(8) ||
                         a.getEpisodio().getTipoepisodio().getId().equals(9)) {
                          atrasoAlerta0 = 1800;
                          atrasoAlerta1 = 3600;
                      } else {
                          atrasoAlerta0 = 3600;
                          atrasoAlerta1 = 4800;
                      }

                      ImageIcon serv = getIconoActividad(a.getActividad());
                      String xhora = a.getAgendaPK().getHora();
                      atraso = obtenerAtraso(a.getAgendaPK().getFecha(), xhora);

                      if((atraso < 4800) && (atraso > 3600)) {
                          icono = new ImageIcon(getClass().getResource("/imagenes/alertaMini.png"));
                      } else {
                          icono = new ImageIcon(getClass().getResource("/imagenes/peligroMini.jpg"));
                        }

                      String nombre = a.getEpisodio().getPaciente().getPersonas().getApellidos() + " " + a.getEpisodio().getPaciente().getPersonas().getNombres();
                      dftm.addRow(new Object[]{icono, serv, nombre, a.getAgendaPK().getHora()});
             }
            }
        }

        uic.cargarAlertas(dftm);
        uic.setAnchoColumnaAlertas(0, 25);
        uic.setAnchoColumnaAlertas(1, 25);
        uic.setAnchoColumnaAlertas(2, 100);
        uic.setAnchoColumnaAlertas(3, 70);

    }

    private boolean estaEnAlerta(Agenda a) {

        int atraso = 0;
        int atrasoAlerta1 = 4800;
        int atrasoAlerta0 = 3600;

        boolean resp = false;

        Actividades act = cargarActividad(a.getActividad());

        int tipoepisodio = act.getTipoactividad().getId();
        
        if(tipoepisodio==1  ||
                 tipoepisodio==2  ||
                 tipoepisodio==14
                ) {
                    if(act.getId().equals(1) ||
                               act.getId().equals(8) ||
                               act.getId().equals(9)) {
                                atrasoAlerta0 = 1800;
                                atrasoAlerta1 = 3600;
                            } else {
                                atrasoAlerta0 = 3600;
                                atrasoAlerta1 = 4800;
                            }
                    
                    String xhora = a.getAgendaPK().getHora();
                    atraso = obtenerAtraso(a.getAgendaPK().getFecha(), xhora);

                    if(atraso > 4800) {

                        resp = true;
                    }
        }

        return(resp);

    }

    private void imprimirTicketControl() throws Exception {

        AbstractService s = new ServiceDelegator().getService("ImprimirTicketDeControl");

        Long numero = (Long) talitakumi.framework.Sesion.getServiceDelegatorRemote().invoke("CargarCantidadTicketsPorFecha", new Date());

        DataParameter dp = new DataParameter();
        dp.set("numtick", numero);
        s.setParameters(dp);
        s.invoke();
    }

   private void menuChequeos() {

           ChequeosVariosCtrl mcac = new ChequeosVariosCtrl();

       Integer opcion = mcac.getOpcion();

       if(opcion!=null) {
           int o = opcion.intValue();
            try {
                           switch(o) {
                
                               case 1: new IngresoCtrl();
                                       break;
                               case 2: new PreventivoCtrl();
                                       break;
                               case 3: new SantanderCtrl();
                                       break;
                               case 4: new AlturaCtrl();
                                       break;
                               case 5: new EgresoCtrl();
                                       break;
                               case 6: new ReintegroCtrl();
                                       break;
                               case 7: new JuntaMedicaCtrl();
                                       break;
                               case 8: new ConsultaMedicaCtrl();
                                       break;
                               case 9: new HabilitacionBSECtrl();
                                       break;
                               case 10: new IngresoChoferCtrl();
                                       break;
                               case 11: new ChequeoPersonalizadoCtrl();
                                       break;
                               case 12: new PesoYTallaCtrl();
                                       break;
                               case 13: new ChequeoArmasCtrl();
                                       break;


                           }
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(RecepcionCtrl.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
   }

   private void buscarPaciente() {

       ModificarEpisodioADMCtrl mcac = new ModificarEpisodioADMCtrl();
       
   }

   public void cambiarCondicionesListado() {


       int tipoListado = uic.getCondicionesListado();
       if(tipoListado == 0) {
           entregado = 'S';
       }
       else {
           entregado = 'N';
       }
   }

   private void cargarConsultoriosActivos() {

       Collection <Consultoriosactivos>lista;
       try {
           lista = (Collection) Sesion.getServiceDelegatorRemote().invoke("CargarConsultoriosActivos", null);
           mostrarConsultoriosActivos(lista);
       } catch (Exception e) {
           logger.error(e);
       }
   }

   private void cargarConsultorios() {

       Collection <Consultorios>lista;
       try {
           lista = (Collection) Sesion.getServiceDelegatorRemote().invoke("CargarListaConsultorios", null);

           cargarConsultoriosEnUI(lista);
       } catch (Exception e) {
           logger.error(e);
       }
   }
   
   private void emitirCarnetPorImpresora() {
       
       EmitirCarnetsControl emc = new EmitirCarnetsControl();
   }

   public class TirillaCajaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            times = 0;
            emitirTirillaDeCaja();
        }
   }

   public class ResumenCajaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            times = 0;
            emitirResumenDeCaja();
        }
   }

   public class CopiaCDSListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            times = 0;
            copiarCDS();
        }
   }

   public class CopiaLaboratorioListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            times = 0;
            copiarLaboratorio();
        }
   }

   public class ParaclinicosListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            times = 0;
            agendarParaclinicos();
        }
   }

   public class VacunasListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            times = 0;
            agendarVacunas();
        }
   }

   public class PsicotecnicosListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            times = 0;
            agendarPsicotecnicos();
        }
   }

   public class LibretaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            times = 0;
            agendarLibreta();
        }
   }

   public class LibretaProfListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            times = 0;
            agendarLibretaProf();
        }

   }

   public class CarnetListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            times = 0;
            agendarCarnet();
        }

   }

   public class SalirListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            times = 0;
            cerrar();
        }

   }

   public class ChequeosListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            times = 0;
            menuChequeos();
        }

   }

   public class BuscarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            times = 0;
            buscarPaciente();
        }

   }

   public class ListadoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            times = 0;
            listadoDiario();
        }

   }

   public class CambiarListado implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            cambiarCondicionesListado();
            try {
                times = 0;
                cargarAgenda();
                cargarAlertas();
            } catch(Exception ex) {
                new DialogoError(uic, "Error de sistema AVISAR !", "Error de sistema en RecargarListener", ex.getMessage(), true);
            }
        }

   }

   public class RecargarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            try {
                times = 0;
                cargarAgenda();
                cargarFacturas();
                cargarConsultorios();
                cargarAlertas();
            } catch(Exception ex) {
                new DialogoError(uic, "Error de sistema AVISAR !", "Error de sistema en RecargarListener", ex.getMessage(), true);
            }
        }

   }

   public class PacienteElegidoListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent arg0) {

            times = 0;
            cargarPacienteElegido();
        }

        @Override
        public void mousePressed(MouseEvent arg0) {

        }

        @Override
        public void mouseReleased(MouseEvent arg0) {

        }

        @Override
        public void mouseEntered(MouseEvent arg0) {

        }

        @Override
        public void mouseExited(MouseEvent arg0) {

        }
   }

   public class FacturaElegidaListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent arg0) {

            times = 0;
            cargarFactura();
        }

        @Override
        public void mousePressed(MouseEvent arg0) {

        }

        @Override
        public void mouseReleased(MouseEvent arg0) {

        }

        @Override
        public void mouseEntered(MouseEvent arg0) {

        }

        @Override
        public void mouseExited(MouseEvent arg0) {
            
        }
   }

   public class ConsultoriosListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            times = 0;
            cargarConsultorioElegido();
        }
   }

   public class LaboratorioListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            times = 0;
            laboratorioPulsado();
        }
   }

   public class PacientesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            times = 0;
            pacientesPulsado();
        }
   }

   public class ConsultoriosInmediatosListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            times = 0;
            consultoriosInmediatosPulsado();
        }
   }
   
    public class EmitirCarnetListenerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            emitirCarnetPorImpresora();
        }
   }
    
   public class ObservacionesTelefonistaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            String contenido = cargarCuaderno();
            
            new DialogoInformacion(null, "Notas de telefonista", "",contenido, true).setVisible(true);
            
        }
   }
   
    private String cargarCuaderno() {

        String contenido;
        try {
            contenido = (String) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarCuaderno", fechadesde);
        } catch(Exception ex) {
            contenido = "Error cargando cuaderno de telefonista ";
        }
        
        return(contenido);
    }
}
