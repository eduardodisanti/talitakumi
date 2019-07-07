/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//diego@vertexray
//alex@vertexray.com

package talitakumi.supervisionmedica.ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.laboratorio.ctrl.BuscarEstudioController;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.Chequeos;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Consultorios;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Pacientes;
import talitakumi.supervisionmedica.ui.UIFormularioHCLSupervision;
import view.DialogoInformacion;
import view.PanelMedilab;
import view.UIConsultorio;

/**
 *
 * @author rupertus
 */

public class SupervisionMedicaCtrl extends AbstractVisualController {

        // Declaracion de Variables.

    int consultorio;
    private String consultorioTexto;
    private AbstractVisualController c;

    private Vector <Agenda> listaagenda;
    private UIConsultorio uic;

    private Logger logger;
    private FileAppender appender;
    private int pacienteElegido;
    
    private HeartBeat hb;
    private PanelMedilab pm;
    private UIFormularioHCLSupervision panel;
    private Date fecha;
    private Agenda agenda;
    private Episodios epis;

        // Fin de declaracion de variables.

    public SupervisionMedicaCtrl() {

        logger = Sesion.getLogger();
        pacienteElegido = -1;

        uic = new UIConsultorio();


        consultorio = ((Consultorios)Sesion.getLugarlogon()).getId();
        consultorioTexto = ((Consultorios)Sesion.getLugarlogon()).getDescripcion();

        uic.setAnchoLista(350);
        pm = new PanelMedilab();
        uic.setPanelUtil(pm);
        uic.doMaximize();
        uic.crearAgendaListener(new AgendaListener());
        uic.salirListener(new CancelarListener());
        uic.AgregarCambioBuscarListener(new BuscarPacienteListener());
        uic.cambiarFiltroListener(new CambiarFiltroListener());
        uic.cambiarFechaListener(new CambiarFechaListener());

        uic.setImprimirListener(new ImprimirListener());
        hb = new HeartBeat();
        hb.start();

        uic.setVisible(true);


    }

    private boolean cargarHCMedicoLaboral(Episodios e) {

        boolean resp;

        panel = new UIFormularioHCLSupervision();
        setGuardarListener(new AlmacenarListener());
        if(e.getImghistoria()!=null) {
            panel.setImagen(e.getImghistoria());
            uic.setPanelUtil(panel);
            resp = true;
        } else {
            DialogoInformacion dlg = new DialogoInformacion(null, "Error en episodio", "No se encuentra la imagen de la historia", "No se puede trabajar sobre esta ficha", true);
            dlg.setVisible(true);
            resp = false;
        }
        
        return(resp);
    }

    private void cambiarFecha() throws Exception {

        this.cargarAgendaConsultorio();
    }
    private void cambiarFiltro() throws Exception {

        this.cargarAgendaConsultorio();
    }
    // TODO - tiene que ser un servicio
    
    private void cargarAgendaConsultorio() throws Exception {

            Agenda a = new Agenda();
            fecha = uic.getFecha();
            int j=0;

            uic.agrisarlabelfecha(true);
            int tipolistado = uic.getTipoLista();

            HashMap<String, Object> params = new HashMap();
            params.put("consultorio", 0);
            params.put("vino", 'S');
            params.put("filtro", tipolistado);
            params.put("fecha", fecha);

            listaagenda = prepararPantalla((Vector<Agenda>) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodiosNoTerminados", params));


            int largo = listaagenda.size();
            DefaultTableModel dftm = new DefaultTableModel();
            dftm.addColumn("Fecha");
            dftm.addColumn("Procedencia");
            dftm.addColumn("Actividad");
            dftm.addColumn("Paciente");
            dftm.addColumn("ID");

            ImageIcon inm;
            for(int i=0; i < largo;i++) {

                String actividad = "";
                Pacientes p = new Pacientes();

                a = listaagenda.get(i);
                if(a.getEpisodio()!=null) {
                    p = a.getEpisodio().getPaciente();
                    String nombre = p.getPersonas().getApellidos() + " " + p.getPersonas().getNombres();

                    int act = a.getActividad();

                    switch (act) {
                        case 15 : actividad = "CHK";
                                  break;
                    }

                    boolean inmediato = a.getAgendaPK().getTurno()==0;

                    if(inmediato) {
                        inm = new ImageIcon(getClass().getResource("/imagenes/ledrojo2.gif"));
                    }
                    else {
                        inm = new ImageIcon(getClass().getResource("/imagenes/gris.gif"));
                    }

                    String cliente = cargarNombreCliente(a.getEpisodio().getCliente());
                    Chequeos chk = a.getEpisodio().getChequeos();
                    if(chk==null) {

                            dftm.addRow(new Object[]{formatFecha(a.getAgendaPK().getFecha()), cliente , actividad, nombre, j});                    
                    }
                    j++;
                }
            }
            uic.setModelListaConsultas(dftm);
            uic.setAnchoColumna(0, 70);
            uic.setAnchoColumna(1, 100);
            uic.setAnchoColumna(2, 50);
            uic.setAnchoColumna(3, 110);
            uic.setAnchoColumna(4, 5);
            if(pacienteElegido != -1) {
                uic.setPacienteElegido(pacienteElegido);
            }
            uic.agrisarlabelfecha(false);
    }

   private String formatFecha(Date fecha) {

       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

       return(sdf.format(fecha));
   }

   private String cargarNombreCliente(int id) throws Exception {

       String s = "-";

       Clientes cli = (Clientes) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarCliente", id);

       s = cli.getNombre();
       
       return(s);
   }
   
   private void pacienteElegido() {

       pacienteElegido = uic.getItemElegido(4);

       agenda = listaagenda.get(pacienteElegido);
       epis = agenda.getEpisodio();

       if(cargarHCMedicoLaboral(epis)) {
            uic.colapsarListaEspera();
       }
   }

   private void cerrarFrame() {

            hb.parar();
            uic.setVisible(false);
            uic.dispose();
   }

   private void resetVista() {

       uic.resetVista();
       uic.setPanelUtil(new PanelMedilab());
       uic.expandirListaEspera();
   }

    @Override
    public void guardarDatos() throws Exception {

           Chequeos chk = agenda.getEpisodio().getChequeos();

           if(chk==null) {
               chk = new Chequeos();
               chk.setEpisodio(agenda.getEpisodio());
               agenda.getEpisodio().setChequeos(chk);
               chk.setId(null);
               chk.setTextLabo(panel.getObservaciones());
           }

           chk.setDictamenApto(panel.getDictamenApto());
           chk.setVigencia(panel.getVigencia());
           
           
           epis.setChequeos(chk);
           chk.setEpisodio(epis);
           agenda.setEpisodio(epis);
           
           Sesion.getServiceDelegatorRemote().invoke("ServicioAlmacenarEpisodio", epis);
           Sesion.getServiceDelegatorRemote().invoke("ServicioModificarAgenda", agenda);

           this.resetVista();
           cargarAgendaConsultorio();
    }

    @Override
    public JPanel getPanel() {
        
        return(null);
    }

    @Override
    public void cerrarTodo() {
            cerrarFrame();
    }

    //@Override
    @Override
    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Vector<Agenda> prepararPantalla(Vector<Agenda> vector) {
        
        Vector<Agenda> lista = new Vector<Agenda>();
        
        for(Agenda a : vector) {
        
                    lista.add(a);
        }
        
        return(lista);
    }
    @Override
    public void setGuardarListener(ActionListener al) {

        panel.setGuardarListener(al);
    }

    @Override
    public void vaciarDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   class TipoVistaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            try {
                cargarAgendaConsultorio();
            } catch (Exception ex) {
                // TODO - avisar error
                logger.error(ex);
            }
        }


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

          Agenda e = listaagenda.elementAt(i);
          if(e.getEpisodio().getPaciente().getPersonas().getApellidos().startsWith(buscar) ||
             e.getEpisodio().getPaciente().getPersonas().getDocumento() == numerobuscar) {
                encontre = true;
                uic.apuntarFilaAgenda(i);
            } else
                   i++;
        }
    }

   class AgendaListener implements MouseListener {

        public void actionPerformed(ActionEvent arg0) {

            pacienteElegido();
        }

        @Override
        public void mouseClicked(MouseEvent arg0) {
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
   class BuscarPacienteListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent arg0) {

            int codigo = arg0.getKeyChar();
            if(codigo==10)
              pacienteElegido();
            else
              buscarPaciente((char)codigo);
        }

        @Override
        public void keyPressed(KeyEvent arg0) {

        }

        @Override
        public void keyReleased(KeyEvent arg0) {

        }

   }

   class CambiarFiltroListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            try {
                cambiarFiltro();
            } catch (Exception ex) {
                // TODO - informar este error
                logger.error(ex);
            }
        }
   }

   class CambiarFechaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            try {
                cambiarFecha();
            } catch (Exception ex) {
                // TODO - informar este error
                logger.error(ex);
            }
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
   
   @Override
   public void run() {

      while(running) {
             try {
                  cargarAgendaConsultorio();
             } catch(Exception e) {
                 // TODO - cachear esta excepcion
                 System.out.println("Error SupervisionMedicaCtrl running:" + e);
             }
           try {
                    Thread.sleep(60000);
               } catch (InterruptedException ex) {
                    logger.error(ex);
            }
      }
    }
   }

   class ImprimirListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            try {
                imprimir();
            } catch(Exception e) {
                logger.error(e);
                // TODO - informar error
            }
        }
   }

    private void imprimir() {

        //new BuscarEstudioController();
        new BuscarEstudioController();
    }

}
