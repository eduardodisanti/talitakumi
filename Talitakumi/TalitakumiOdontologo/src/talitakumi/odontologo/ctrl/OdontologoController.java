/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.odontologo.ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import talitakumi.model.core.Carnetdesalud;
import talitakumi.model.core.Consultorios;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Pacientes;
import talitakumi.odontologo.ui.UIOdontologo;
import talitakumi.odontologo.ui.UIRevisionOdontologica;
import view.PanelMedilab;

/**
 *
 * @author rupertus
 */
public class OdontologoController extends AbstractVisualController {

    Date fechadesde;
    int consultorio;
    String consultorioTexto;
    AbstractVisualController c;

    Vector <Episodios> listaagenda;
    UIOdontologo uic;
    UIRevisionOdontologica uir;


    Logger logger;
    FileAppender appender;
    private int pacienteElegido;
    HeartBeat hb;
    PanelMedilab pm;
    int filtro;

    public OdontologoController() {

        logger = Sesion.getLogger();
        pacienteElegido = -1;
        filtro = 0;

        DefaultComboBoxModel cbm = new DefaultComboBoxModel();
        uic = new UIOdontologo();

        cbm.addElement("Sin hacer");
        cbm.addElement("Hechos");
        uic.setTipoVistaModel(cbm);

        consultorio = ((Consultorios)Sesion.getLugarlogon()).getId();
        consultorioTexto = ((Consultorios)Sesion.getLugarlogon()).getDescripcion();

        uic.setAnchoLista(350);
        pm = new PanelMedilab();

        uic.setPanelUtil(pm);
        uic.doMaximize();
        uic.crearAgendaListener(new AgendaListener());
        uic.salirListener(new CancelarListener());
        uic.cambiarFiltroListener(new CambiarFiltroListener());
        uic.cambiarFechaListener(new CambiarFechaListener());
        hb = new HeartBeat();
        hb.start();

        hb.setRunning(true);
        uic.setVisible(true);

    }


    private void cambiarFiltro() throws Exception {

        filtro = uic.getFiltro();
        this.cargarEpisodiosConsultorio();

    }

    // TODO - tiene que ser un servicio
    
    private void cargarEpisodiosConsultorio() throws Exception {

        int j=0;
        listaagenda = new Vector();
        List <Episodios> templistaagenda;
        if(hb.isRunning()) {
            
            pm.setConsultorio(consultorioTexto);
            Episodios e = new Episodios();

            HashMap <String, Object> parametros = new HashMap();
            parametros.put("actividadaquitar", 0);
            fechadesde = uic.getFechaDesde();
            parametros.put("fecha", fechadesde);
            parametros.put("orden_pedido", "llegada");

            hb.setRunning(false);
            templistaagenda = (List<Episodios>) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodiosConsultorio", parametros);
            hb.setRunning(true);
           
            System.out.println("TAMAÑO--------" +templistaagenda.size());

            int largo = templistaagenda.size();
            DefaultTableModel dftm = new DefaultTableModel();
            dftm.addColumn("Documento");
            dftm.addColumn("Paciente");
            dftm.addColumn("Act");
            dftm.addColumn("ID");

            for(int i=0; i < largo;i++) {
                e = templistaagenda.get(i);

                int cantestudios = 0; //e.getEpisodioodontologoCollection().size();

                Integer vigencia = -1;
                if(e.getCarnetdesalud()!=null)
                    vigencia = e.getCarnetdesalud().getVigenciaodontologo();
                Integer scoring = (Integer) Sesion.getServiceDelegatorRemote().invoke("ObtenerEstadoRevisionOdontologica", e);
                System.out.println("scoring" +scoring);
                if(scoring < 1 || scoring > 3) {

                    vigencia = -1;
                } else
                      vigencia = 1;

                if(e.getTipoepisodio().getId()==15) {
                   if(e.getTerminado()=='S') {
                      vigencia = 1;
                   }
                }
                
                if(vigencia > 0) {
                    cantestudios = 1;
                }

                boolean mirar = e.getTipoepisodio().getId()==16 || e.getCarnetdesalud()!=null;

                
                if(((cantestudios == 0 && filtro ==0) || (cantestudios != 0 && filtro==1))
                   && (mirar && e.getChequeos()==null)) {
                    
                    String act = " - ";
                            int a = e.getTipoepisodio().getTipoactividad().getId();
                            System.out.println("a es " +a);
                            switch(a) {

                                case 1: act = "LDC";
                                        break;
                                case 2: act = "CDS";
                                        break;
                                case 9: act = "LPR";
                                        break;
                                case 15: act= "CHK";
                                         break;
                            }
                            
                    Pacientes p = new Pacientes();

                    p = e.getPaciente();
                    String nombre = p.getPersonas().getApellidos() + " " + p.getPersonas().getNombres();

                    int cedula = e.getPaciente().getPersonas().getDocumento();

                    System.out.println("Tipo de actividad es " +a);
                    if(a==1 || a==2 || a==9 || a==15) {
                        dftm.addRow(new Object[]{cedula, nombre, act, j});
                        j++;
                        listaagenda.add(e);
                    }
                }
            }

            uic.setModelListaConsultas(dftm);
            uic.setAnchoColumna(0, 90);
            uic.setAnchoColumna(1, 200);
            uic.setAnchoColumna(3, 5);
            if(pacienteElegido != -1){
                uic.setPacienteElegido(pacienteElegido);
                System.out.println("Nunca entro aca");
                
            }
            
            hb.setRunning(true);
            
       }
    }

    private void cargarEstudio(Episodios e) {

        Episodios unEpisodio = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio", e.getId());
        Carnetdesalud cds = unEpisodio.getCarnetdesalud();
        if(cds == null) {
           cds = (Carnetdesalud) Sesion.getServiceDelegatorRemote().invoke("CargarCarnetDeSalud", e);
           unEpisodio.setCarnetdesalud(cds);
        }
        
        RevisionOdontologicaController ec = new RevisionOdontologicaController(unEpisodio);
        ec.setGuardarListener(new LocalAlmacenarListener());
        uic.setPanelUtil(ec.getPanel());
        
    }

   private void pacienteElegido() {

       pacienteElegido = uic.getItemElegido();
       Episodios e = listaagenda.get(pacienteElegido);

           cargarEstudio(e);

           uic.colapsarListaEspera();
          
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
           c.guardarDatos();
           this.resetVista();
           this.cargarEpisodiosConsultorio();
    }

    @Override
    public JPanel getPanel() {
        
        return(null);
    }

    @Override
    public void cerrarTodo() {
            cerrarFrame();
    }
    
    private void cambiarFecha() throws Exception {

        fechadesde = uic.getFechaDesde();
        this.cargarEpisodiosConsultorio();
    }

    @Override
    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void vaciarDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
   class CambiarFiltroListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            try {
                cambiarFiltro();
            } catch(Exception e) {

                // TODO - informar de este error
                logger.error(e);
                System.out.println("Error " + e);
            }
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

   class CambiarFechaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            try {
                cambiarFecha();
            } catch(Exception e) {
                logger.error(e);
                // TODO - informar error
                System.out.println("Error " + e);
            }
        }
   }

   class LocalAlmacenarListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {

            uic.resetVista();
     
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
            } catch(Exception e) {
                // TODO - informar de este error
                logger.error(e);
                System.out.println("Error " + e);
            }
             
           try {
                    Thread.sleep(30000);
               } catch (InterruptedException ex) {
                    logger.error(ex);
                    System.out.println("Error " + ex);
            }
      }
    }

        private void setRunning(boolean b) {
            running = b;
        }
   }
}
