/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package talitakumi.extraccionistas.ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.Consultorios;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Facturas;
import talitakumi.model.core.Pacientes;
import view.UIConsultorio;
import view.PanelMedilab;

/**
 *
 * @author rupertus
 */
public class PuestoExtraccionistaCtrlOLD extends AbstractVisualController {

    int consultorio;
    String consultorioTexto;
    AbstractVisualController c;

    Vector <Agenda> listaagenda;
    UIConsultorio uic;

    Logger logger;
    FileAppender appender;
    private int pacienteElegido;
    
    HeartBeat hb;
    PanelMedilab pm;
    int filtro;

    public PuestoExtraccionistaCtrlOLD() {

        logger = Sesion.getLogger();
        pacienteElegido = -1;

        uic = new UIConsultorio();

        consultorio = ((Consultorios)Sesion.getLugarlogon()).getId();
        consultorioTexto = ((Consultorios)Sesion.getLugarlogon()).getDescripcion();

        uic.setAnchoLista(300);
        pm = new PanelMedilab();
        uic.setPanelUtil(pm);
        uic.doMaximize();
        uic.crearAgendaListener(new AgendaListener());
        uic.salirListener(new CancelarListener());
        uic.AgregarCambioBuscarListener(new BuscarPacienteListener());
        uic.cambiarFiltroListener(new CambiarFiltroListener());
        uic.cambiarFechaListener(new CambiarFechaListener());

        hb = new HeartBeat();
        hb.start();

        uic.setVisible(true);

    }

    private void cambiarFecha() throws Exception {

        this.cargarAgendaConsultorio();
    }
    private void cambiarFiltro() throws Exception {

        //this.cargarAgendaConsultorio();
        filtro = uic.getFiltro();
        this.cargarAgendaConsultorio();

    }
    // TODO - tiene que ser un servicio
    
    private void cargarAgendaConsultorio() throws Exception {

            Vector <Agenda> listatemp;
            listaagenda = new Vector<Agenda>();
            Agenda a = new Agenda();
            Date fecha = uic.getFecha();
                    
            uic.agrisarlabelfecha(true);
            int tipolistado = uic.getTipoLista();


           // AbstractService as = new ServiceDelegator().getService("cargarAgendaConsultorio");

            HashMap<String, Object> params = new HashMap();

            params.put("fechadesde",  fecha);
            params.put("fechahasta",  fecha);
            params.put("tipolistado", 0);
            params.put("paga", true);
            params.put("orden",       "alfabetico");

            params.put("fecha", fecha);
            params.put("odontologo", false);
            params.put("vino", 'N');
            params.put("filtro", 0);
            params.put("consultorio", null);


  
            //listatemp = (Vector<Agenda>) Sesion.getServiceDelegatorRemote().invoke("CargarAgenda", params);
            listatemp = prepararPantalla((Vector<Agenda>) Sesion.getServiceDelegatorRemote().invoke("CargarAgendaConsultorio", params));

            System.out.println("tamaño listaagenda" +listaagenda.size());
            //as.setParameters(params);

           // listaagenda = (Vector<Agenda>) as.invoke();


            int largo = listatemp.size();
            DefaultTableModel dftm = new DefaultTableModel();
            dftm.addColumn("Act");
            dftm.addColumn("Hora");
            dftm.addColumn("Paciente");

            Boolean mostrar = true;

            for(int i=0; i < largo;i++) {

                char cantestudios;

                    cantestudios='N';
                    int scorelab = 0;

                String actividad = "";
                Pacientes p = new Pacientes();

                a = listatemp.get(i);

                    Episodios epi = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio", a.getEpisodio().getId());
                    scorelab    = (Integer) Sesion.getServiceDelegatorRemote().invoke("ObtenerEstadoExamenesLaboratorio", epi);
                    if(scorelab >= 1 && scorelab <= 3)
                       cantestudios = 'S';
                    else
                       cantestudios = 'N';

                if(((cantestudios == 'N' && filtro ==0) || (cantestudios != 'N' && filtro==1))
                          && (mostrar && epi.getChequeos()==null)) {

                p = a.getEpisodio().getPaciente();
                String nombre = p.getPersonas().getApellidos() + " " + p.getPersonas().getNombres();

                int act = a.getEpisodio().getTipoepisodio().getTipoactividad().getId();

                actividad = "GEN:"+act;
                switch (act) {

                    case 1 :  actividad = "LC";
                              break;
                    case 2 :  actividad = "CS";
                              break;
                    case 8:   actividad = "LPR";
                              break;
                    case 9:   actividad = "LPR";
                              break;
                    case 15 : actividad = "CHK";
                              break;
                    case 10:  actividad = "PS";
                              break;
                    case 13:  actividad = "EXA";
                              break;
                }


                // OJALDRE !!! - ESTO FUE LO CAMBIADO PARA QUE NO APAREZCA EL CONS
                // SI EL ODONTOLOGO NO LO VIO
                //if(a.getEpisodio().getCarnetdesalud().getVigenciaodontologo()!= -1)
                if(act!=1 && act!=11 && act!=14 && act!=10 && act!=12 && !a.getAgendaPK().getConsultorio().equals("9001")) {
                    String hora = a.getAgendaPK().getHora().substring(0,5);
                    listaagenda.add(a);
                    dftm.addRow(new Object[]{actividad, hora, nombre});
                }
            }
         }
            uic.setModelListaConsultas(dftm);
            uic.setAnchoColumna(0, 30);
            uic.setAnchoColumna(1, 50);
            uic.setAnchoColumna(2, 210);
            if(pacienteElegido != -1)
                uic.setPacienteElegido(pacienteElegido);
            uic.agrisarlabelfecha(false);
    }

    private void cargarCDS(Agenda a) throws Exception {
        c = new ActoExtraccionCtrl(a);
        c.setActividad(a.getEpisodio().getTipoepisodio().getDescripcion());
        uic.setPanelUtil(c.getPanel());
        c.setGuardarListener(new AlmacenarListener());
    }

    private void cargarExamenes(Agenda a) throws Exception {
        c = new ActoExtraccionCtrl(a);
        c.setActividad(a.getEpisodio().getTipoepisodio().getDescripcion());
        uic.setPanelUtil(c.getPanel());
        c.setGuardarListener(new AlmacenarListener());
    }

    private void cargarLibreta(Agenda a) throws Exception {
        c = new ActoExtraccionCtrl(a);
        c.setActividad(a.getEpisodio().getTipoepisodio().getDescripcion());
        uic.setPanelUtil(c.getPanel());
        c.setGuardarListener(new AlmacenarListener());
    }

    private void cargarChequeos(Agenda a) throws Exception {
        c = new ActoExtraccionCtrl(a);
        c.setActividad(a.getEpisodio().getTipoepisodio().getDescripcion());
        uic.setPanelUtil(c.getPanel());
        c.setGuardarListener(new AlmacenarListener());
    }

    private void cargarLp(Agenda a) throws Exception {
        c= new ActoExtraccionCtrl(a);
        c.setActividad(a.getEpisodio().getTipoepisodio().getDescripcion());
        uic.setPanelUtil(c.getPanel());
        c.setGuardarListener(new AlmacenarListener());
    }


   private void pacienteElegido() throws Exception {

       boolean ok = false;
       pacienteElegido = uic.getItemElegido();

       Agenda a = listaagenda.elementAt(pacienteElegido);
       int tipoActividad = a.getEpisodio().getTipoepisodio().getTipoactividad().getId();
       //int tipodeactividad = a.getActividad();

       if(tipoActividad==1) {
           cargarLibreta(a);
           ok = true;
       }
       if(tipoActividad==2 || tipoActividad == 3 || tipoActividad==4) {
           cargarCDS(a);
           ok = true;
       }
       if(tipoActividad==15) {
           cargarChequeos(a);
           ok = true;
       }
       if(tipoActividad==8 || tipoActividad==9) {
           cargarLp(a);
           ok = true;
       }

       if(tipoActividad==13) {
           cargarExamenes(a);
           ok = true;
       }
       if(ok)
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
           //c.guardarDatos();
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

    @Override
    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
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

          Agenda e = listaagenda.get(i);
          if(e.getEpisodio().getPaciente().getPersonas().getApellidos().startsWith(buscar) ||
             e.getEpisodio().getPaciente().getPersonas().getDocumento() == numerobuscar) {
                encontre = true;
                uic.apuntarFilaAgenda(i);
            } else
                   i++;
        }
    }

    private Vector<Agenda> prepararPantalla(Vector<Agenda> vector) {

        Vector<Agenda> lista = new Vector<Agenda>();

        for(Agenda a : vector) {

            if(a.getEpisodio()!=null) {
                Facturas f = (Facturas) Sesion.getServiceDelegatorRemote().invoke("CargarUnaFacturaPorEpisodio", a.getEpisodio().getId());
                if(f!=null) {
                    if(f.getEntregado().equals("S") ||f.getTipo()=='R')
                        lista.add(a);
                }
//                for(Facturas f : epi.getFacturasCollection()) {
//                if(f.getEntregado().equals("S") || f.getTipo()=='R')
//                    lista.add(a);
//                    break;
//                }
            }
//            else
//                lista.add(a);

        }
        return(lista);
    }

   class AgendaListener implements MouseListener {

        public void actionPerformed(ActionEvent arg0) {
            try {
                pacienteElegido();
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(PuestoExtraccionistaCtrlOLD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void mouseClicked(MouseEvent arg0) {
            try {
                pacienteElegido();
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(PuestoExtraccionistaCtrlOLD.class.getName()).log(Level.SEVERE, null, ex);
            }
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
              try {
                pacienteElegido();
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(PuestoExtraccionistaCtrl.class.getName()).log(Level.SEVERE, null, ex);
            }
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
             }
           try {
                    Thread.sleep(60000);
               } catch (InterruptedException ex) {
                    logger.error(ex);
            }
      }
    }
   }

}
