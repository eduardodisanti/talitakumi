/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.laboratorio.ctrl;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.laboratorio.ui.UILaboratorio;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.Carnetdesalud;
import talitakumi.model.core.Consultorios;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.EpisodiosExAdicionales;
import talitakumi.model.core.Facturas;
import talitakumi.model.core.Mensajes;
import talitakumi.model.core.Pacientes;
import view.PanelMedilab;

/**
 *
 * @author rupertus
 */
public class LaboratorioController extends AbstractVisualController {

    private int consultorio;
    private int times;
    private String consultorioTexto;
    private AbstractVisualController c;
    private Date fechadesde;

    private Vector <Episodios> listaagenda;
    
    private UILaboratorio uic;

    private Logger logger;
    private FileAppender appender;
    private int pacienteElegido;
    private HeartBeat hb;
    private PanelMedilab pm;
    int filtro;
    private Vector <Integer> listamensajes;
    private Boolean mostrarexterno;
    private EpisodiosExAdicionales eea;

    public LaboratorioController() {

        logger = Sesion.getLogger();
        pacienteElegido = -1;
        filtro = 0;

        DefaultComboBoxModel cbm = new DefaultComboBoxModel();
        uic = new UILaboratorio();

        cbm.addElement("Sin hacer");
        cbm.addElement("Hechos");
        uic.setTipoVistaModel(cbm);

        consultorio = ((Consultorios)Sesion.getLugarlogon()).getId();
        consultorioTexto = ((Consultorios)Sesion.getLugarlogon()).getDescripcion();

        uic.setAnchoLista(300);
        pm = new PanelMedilab();

        uic.setPanelUtil(pm);
        uic.doMaximize();
        uic.crearAgendaListener(new AgendaListener());
        uic.salirListener(new CancelarListener());
        uic.cambiarFiltroListener(new CambiarFiltroListener());
        uic.cambiarFechaListener(new CambiarFechaListener());
        uic.imprimirListener(new ImprimirListener());

        uic.AgregarCambioBuscarListener(new BuscarPacienteListener());

        uic.leerMensajesListener(new MensajesListener());
        uic.setTitle("Laboratorio");

        hb = new HeartBeat();
        hb.start();

        uic.setVisible(true);
    }


    
    private void cambiarFiltro() throws Exception {

        filtro = uic.getFiltro();
        this.cargarEpisodiosConsultorio();
    }

    private void imprimir() {

        //new BuscarEstudioController();
        new ImprimirLaboratorioController().setFecha(fechadesde);
    }

    private void cambiarFecha() throws Exception {

        this.cargarEpisodiosConsultorio();
    }
    // TODO - tiene que ser un servicio

    private void cargarMensajesLaboratorio() {

        List <Mensajes> lista = (List<Mensajes>) Sesion.getServiceDelegatorRemote().invoke("CargarMensajes", "laboratorio");
        int mensajes = 0;

        DefaultComboBoxModel cbm = new DefaultComboBoxModel();
        listamensajes = new Vector();
        
        for(Mensajes m : lista) {

            ++mensajes;
            cbm.addElement(m.getTexto());
            listamensajes.addElement(m.getId());
        }
        
        if(mensajes > 0) {
            uic.encenderLed();
        }
        else {
            uic.apagarLed();
        }

        uic.setMensajesModel(cbm);
    }

    private void seleccionarMensaje() {

        Integer idmsg = uic.getMensajeElegido();

        Sesion.getServiceDelegatorRemote().invoke("MarcarMensajeLeido", idmsg);
    }

    private void cargarEpisodiosConsultorio() throws Exception {

        if(listaagenda != null) {
            listaagenda.clear();
        }

        char cantestudios='N';

        listaagenda = new Vector();
        List <Episodios> tmplistaagenda;

        if(hb.isRunning()) {
            hb.setRunning(false);
            pm.setConsultorio(consultorioTexto);
            Episodios e = new Episodios();

            //AbstractService as = new ServiceDelegator().getService("CargarEpisodiosConsultorio");

            HashMap <String, Object> parametros = new HashMap();
            parametros.put("actividadaquitar", 1);
            fechadesde = uic.getFechaDesde();
            parametros.put("fecha", fechadesde);
            parametros.put("odontologo", false);
            parametros.put("vino", 'N');
            parametros.put("filtro", 0);
            parametros.put("consultorio", null);
        

            //tmplistaagenda = (List<Episodios>) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodiosConsultorio", parametros);

            //tmplistaagenda = prepararPantalla((List<Episodios>) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodiosConsultorio", parametros));
            tmplistaagenda = prepararPantalla((List<Agenda>) Sesion.getServiceDelegatorRemote().invoke("CargarAgendaConsultorio", parametros));
            //CargarAgendaConsultorio

            DefaultTableModel dftm = new DefaultTableModel();
            dftm.addColumn("Documento");
            dftm.addColumn("Paciente");
            dftm.addColumn("Act");
            dftm.addColumn("ID");
            dftm.addColumn("Tubo");

            Boolean mostrar = true;


            Episodios epi = null;
            int i=0;
            for(Episodios ee : tmplistaagenda) {

                //System.out.println(ee.getId()+" "+ee.getLibretaprofesional() +"\n");
                //for(int i=0; i < largo;i++) {
                    //e = tmplistaagenda.get(i);
                    e = ee;
                    epi = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio", ee.getId());
    //                if(e!=null) {
    //                    char cantestudios = 'N';

                    cantestudios='N';
                    int scorelab = 0;
                    String donde = "";

                    Carnetdesalud cds = e.getCarnetdesalud();
                    if(cds!=null) {

                        int vigencia = cds.getVigencialaboratorio();
                        if(vigencia==-1) {
                            cantestudios='N';
                        }
                        else {
                            cantestudios='S';
                        }
                    } else {
                            cantestudios='N';

                            scorelab    = (Integer) Sesion.getServiceDelegatorRemote().invoke("ObtenerEstadoExamenesLaboratorio", epi);
                            if(scorelab >= 1 && scorelab <= 3) {
                            cantestudios = 'S';
                        }
                            else {
                            cantestudios = 'N';
                        }
                        }
                        
                        mostrar = definirMostrarPaciente(ee.getPaciente());

                        //System.out.print("El episodio = " + " tipo " + ee.getTipoepisodio().getTipoactividad().getDescripcion() + " " + ee.getId() + " tiene cantesudios = " + cantestudios + " y mostrar = " + mostrar + " paciente " + e.getPaciente().getPersonas().getApellidos());
                                                
                        if(((cantestudios == 'N' && filtro ==0) || (cantestudios != 'N' && filtro==1))
                          && (mostrar)) {

                            Pacientes p = new Pacientes();

                            p = e.getPaciente();
                            String nombre = p.getPersonas().getApellidos() + " " + p.getPersonas().getNombres();

                            int cedula = e.getPaciente().getPersonas().getDocumento();
                            String act = " - ";
                            int a = e.getTipoepisodio().getTipoactividad().getId();
                            
                            switch(a) {

                                case 1: act = "LDC";
                                        break;
                                case 2: act = "CDS";
                                        break;
                                case 9: act = "LPR";
                                        break;
                                case 13: act= "PAR";
                                         break;
                                case 15: act= "CHK";
                                         break;
                            }

                            if(esExterno(epi)) {

                                act = act+"/E";
                            }
                            
                            //System.out.println("a es  : " +a); // Tabla tipodeactividad
                            String tubo = "-";
                            if(e.getEpisodioextraccionista()!=null) {
                             tubo = "" + e.getEpisodioextraccionista().getTubo();
                            }
                            if(a==2 || a==9 || ((a==15 || a==13) && necesitaParaclinica(epi))) {
                                dftm.addRow(new Object[]{cedula, nombre, act, i, tubo});
                                listaagenda.add(e);
                                i++;
                              //  System.out.println("[SHOW]");
                            }
                            //else
                            //    System.out.println("[HIDE]");
                        }
                        //else
                        //    System.out.println("[DISCARD]");
            }
            uic.setModelListaConsultas(dftm);
            uic.setAnchoColumna(0, 70);
            uic.setAnchoColumna(1, 160);
            uic.setAnchoColumna(2, 60);
            uic.setAnchoColumna(3, 5);
            uic.setAnchoColumna(4, 40);
            
            uic.setDividerLocation(350);

            Font f = new Font("Arial", Font.PLAIN, 14);

            uic.setFontTabla(f);
            if(pacienteElegido != -1) {
                uic.setPacienteElegido(pacienteElegido);
            }

            hb.setRunning(true);
       }
        
    }

    private List<Episodios> prepararPantalla(List<Agenda> vector) {

        //List<Episodios> lista = new ArrayList<Episodios>();
        List<Episodios> lista = new ArrayList<Episodios>();

        //for(Episodios e : vector) {
        for(Agenda e : vector) {
            
            if(e.getEpisodio()!=null) {
                System.out.println("EVALUANDO : " + e.getEpisodio().getPaciente().getPersonas().getApellidos());
                Facturas f = (Facturas) Sesion.getServiceDelegatorRemote().invoke("CargarUnaFacturaPorEpisodio", e.getEpisodio().getId());
                if(f!=null) {
                    System.out.print("f (" + f.getId() + " es " + f.getEntregado() + " tipo es " + f.getTipo());
                    if(f.getEntregado().equals("S") ||f.getTipo()=='R') {
                        lista.add(e.getEpisodio());
                        System.out.println(" [ADD]");
                    } else {
                            System.out.println(" [DISCARDED]");
                    }
                } else {
                    System.out.println("OJO FACTURA DE EPISODIO " + e.getEpisodio().getId() + " es null");
                }
//                for(Facturas f : epi.getFacturasCollection()) {
//                if(f.getEntregado().equals("S") || f.getTipo()=='R')
//                    lista.add(e.getEpisodio());
//                    break;
//                }
            }
//            else
//                lista.add(e.getEpisodio());
        }
        return(lista);
    }

    private void buscarPaciente(char c) {

        String buscar = uic.getBuscar() + c;
        boolean encontre = false;
        int i=0;
        int numerobuscar;

        try {
            numerobuscar = Integer.parseInt(buscar);
        } catch(Exception e) {

            numerobuscar = 0;
        }
        while(!encontre && i < listaagenda.size()) {
            
          Episodios e = listaagenda.elementAt(i);
          if(e.getPaciente().getPersonas().getApellidos().startsWith(buscar) || 
             e.getPaciente().getPersonas().getDocumento() == numerobuscar) {
                encontre = true;
                uic.setPacienteEncontradoBuscar(i);
            } else {
                i++;
            }
        }
    }

    private void cargarEstudio(Episodios e) {


        Episodios unEpisodio = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio", e.getId());
        EstudiosController ec = new EstudiosController(unEpisodio);
        uic.setPanelUtil(ec.getPanel());

    }

   private void pacienteElegido() {

       hb.setRunning(false);
       pacienteElegido = uic.getItemElegido();

       if(pacienteElegido != -1) {
          Episodios e = listaagenda.get(pacienteElegido);
          cargarEstudio(e);
       }
       hb.setRunning(true);
   }

   private void cerrarFrame() {

            hb.parar();
            uic.setVisible(false);
            uic.dispose();
   }

   private void resetVista() {

       uic.resetVista();
       uic.setPanelUtil(new PanelMedilab());
   }

    @Override
    public void guardarDatos() throws Exception {
           c.guardarDatos();
           this.resetVista();
           cargarEpisodiosConsultorio();
    }

    @Override
    public JPanel getPanel() {
        
        return(null);
    }

    @Override
    public void cerrarTodo() {
            cerrarFrame();
    }

    private Boolean definirMostrarPaciente(Pacientes p) {

        long edad = calcularEdad(p);
        return(edad >= 14);
    }

    private Boolean pasarChkALaboratorio(Episodios epi){

           return(epi.getChequeos().getLaboratorio()==1);
    }

    @Override
    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private boolean esExterno(Episodios ee) {


        boolean ext = false;
        for(Agenda a:  ee.getAgendaCollection()) {

            if(a.getAgendaPK().getConsultorio().equals("9001") ||
               a.getAgendaPK().getConsultorio().equals("E")) {
                ext = true;
                break;
           }
        }

        return(ext);
    }

    @Override
    public void vaciarDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   class ImprimirListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            try {
                times = 0;
                imprimir();
            } catch(Exception e) {
                logger.error(e);
                // TODO - informar error
            }
        }
   }

   class CambiarFechaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            try {
                times = 0;
                cambiarFecha();
            } catch(Exception e) {
                logger.error(e);
                // TODO - informar error
            }
        }
   }

   class CambiarFiltroListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            try {
                times = 0;
                cambiarFiltro();
            } catch(Exception e) {
                logger.error(e);
                // TODO - informar error
            }
        }
   }

   class AgendaListener implements MouseListener {

        public void actionPerformed(ActionEvent arg0) {

            times = 0;
            pacienteElegido();
        }

        @Override
        public void mouseClicked(MouseEvent arg0) {
            
             times = 0;
             pacienteElegido();
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

   class MensajesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            times = 0;
            Integer idmsg = listamensajes.elementAt(uic.getMensajeElegido());
            Sesion.getServiceDelegatorRemote().invoke("MarcarMensajeLeido", idmsg);
            cargarMensajesLaboratorio();
            // TODO -- actuar en excepciones
        }


   }

   class BuscarPacienteListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent arg0) {

            times = 0;
            int codigo = arg0.getKeyChar();
            if(codigo==10) {
                pacienteElegido();
            }
            else {
                buscarPaciente((char)codigo);
            }
        }

        @Override
        public void keyPressed(KeyEvent arg0) {
            
        }

        @Override
        public void keyReleased(KeyEvent arg0) {
            
        }

   }

   class HeartBeat extends Thread {
        private boolean running;

   public HeartBeat() {

       super();
       running = true;
   }

   public void parar() {

       running = false;

   }

   public boolean isRunning() {

       return(running);
   }

   @Override
   public void run() {

      while(running) {
            try {
                cargarEpisodiosConsultorio();
                cargarMensajesLaboratorio();
            } catch(Exception e) {
                logger.error(e);
                System.out.println(e);
            }
           try {
                    Thread.sleep(30000);
                    times++;
                    if(times>=2) {
                        times = 0;
                        running = false;
                        System.out.println("Salida por timeout de laboratorio ctrl ");
                        
                    }
               } catch (InterruptedException ex) {
                    logger.error(ex);
                    System.out.println("ERROR en LaboratorioCtrl : " + ex);
            }
      }
    }

        private void setRunning(boolean b) {
            running = b;
        }
   }

    private int calcularEdad(Pacientes p) {

        Calendar hoy = Calendar.getInstance();
        Calendar fechaNac = Calendar.getInstance();

        fechaNac.setTime(p.getPersonas().getFechanacimiento());

        int anios = hoy.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);


        return(anios);
    }

    private Boolean necesitaParaclinica(Episodios epi) {

        Boolean b = false;
        if(epi.getEpisodiosExAdicionalesCollection() != null){
            for(EpisodiosExAdicionales exa : epi.getEpisodiosExAdicionalesCollection()){
              if(exa != null) {
                    if(exa.getEstudio().getLaboratorio().equals("M")){
                       b = true;
                       break;
                    }
                }
            }

        }
        
        return(b);
    }


}
