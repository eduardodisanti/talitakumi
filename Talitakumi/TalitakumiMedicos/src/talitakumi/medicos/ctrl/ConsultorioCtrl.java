/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//diego@vertexray
//alex@vertexray.com

package talitakumi.medicos.ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
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
import talitakumi.medicos.ctrl.libreta.LibretaDeConducirCtrl;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.Consultorios;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.EpisodiosExAdicionales;
import talitakumi.model.core.Pacientes;
import talitakumi.model.core.Timming;
import view.DialogoInformacion;
import view.PanelMedilab;
import view.UIConsultorio;

/**
 *
 * @author rupertus
 */

public class ConsultorioCtrl extends AbstractVisualController {

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
    private Date fecha;

        // Fin de declaracion de variables.

    public ConsultorioCtrl() {

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
            params.put("consultorio", consultorio);
            params.put("vino", 'S');
            params.put("filtro", tipolistado);
            params.put("fecha", fecha);

            listaagenda = prepararPantalla((Vector<Agenda>) Sesion.getServiceDelegatorRemote().invoke("CargarAgendaConsultorio", params));


            int largo = listaagenda.size();
            DefaultTableModel dftm = new DefaultTableModel();
            dftm.addColumn("Inm");
            dftm.addColumn("Act");
            dftm.addColumn("Hora");
            dftm.addColumn("Paciente");
            dftm.addColumn("ID");

            ImageIcon inm;
            for(int i=0; i < largo;i++) {

                String actividad = "";
                Pacientes p = new Pacientes();

                a = listaagenda.get(i);
                p = a.getEpisodio().getPaciente();
                String nombre = p.getPersonas().getApellidos() + " " + p.getPersonas().getNombres();

                int act = a.getActividad();

                switch (act) {

                    case 1 :  actividad = "LC";
                              break;
                    case 2 :  actividad = "CS";
                              break;
                    case 13 : actividad = "EXA";
                              break;
                    case 15 : actividad = "CHK";
                              break;
                    case 14: actividad = "VAC";
                              break;
                }

                boolean inmediato = a.getAgendaPK().getTurno()==0;
                
                if(inmediato) {
                    inm = new ImageIcon(getClass().getResource("/imagenes/ledrojo2.gif"));
                }
                else {
                    inm = new ImageIcon(getClass().getResource("/imagenes/gris.gif"));
                }

                String hora = a.getAgendaPK().getHora();
                dftm.addRow(new Object[]{inm, hora.substring(0,5), actividad, nombre, j});
                j++;
            }
            uic.setModelListaConsultas(dftm);
            uic.setAnchoColumna(0, 30);
            uic.setAnchoColumna(1, 50);
            uic.setAnchoColumna(2, 50);
            uic.setAnchoColumna(3, 210);
            uic.setAnchoColumna(4, 5);
            if(pacienteElegido != -1) {
                uic.setPacienteElegido(pacienteElegido);
            }
            uic.agrisarlabelfecha(false);
    }

    private void cargarCDS(Agenda a) {
        c = new CarnetDeSaludCtrl(a);
        uic.setPanelUtil(c.getPanel());
        c.setGuardarListener(new AlmacenarListener());
    }

    private void cargarLibreta(Agenda a) {

        //LibretaDeConducirCtrl
        c = new LibretaDeConducirCtrl(a);
        uic.setPanelUtil(c.getPanel());
        c.setGuardarListener(new AlmacenarListener());
    }

    private void cargarChequeos(Agenda a) {
        c = new talitakumi.medicos.ctrl.ChequeosCtrl(a);
        uic.setPanelUtil(c.getPanel());
        c.setGuardarListener(new AlmacenarListener());
    }

    private void cargarVacunas(Agenda a) { 
        c = new talitakumi.medicos.ctrl.VacunasCtrl(a);
        uic.setPanelUtil(c.getPanel()); // uic= UIConsultorio
        c.setGuardarListener(new AlmacenarListener());
    }

   private void pacienteElegido() {

       pacienteElegido = uic.getItemElegido(4);

       Agenda a = listaagenda.get(pacienteElegido);
       int tipoActividad = a.getEpisodio().getTipoepisodio().getTipoactividad().getId();
       int tipodeactividad = a.getActividad();

       if(tipoActividad==1) {
           cargarLibreta(a);
       }
       if(tipoActividad==2 || tipoActividad ==3 || tipoActividad==4) {
           cargarCDS(a);
       }
       if(tipodeactividad==15) {
           cargarChequeos(a);
       }
       if(tipoActividad ==14) {
           cargarVacunas(a);
       }

       uic.colapsarListaEspera();
       notificarExamenesAdicionales(a);

       /* if(tipodeactividad==13) {
           notificarExamenesAdicionales(a);
       } else {
           uic.colapsarListaEspera();
         }
       */
   }

   private void notificarExamenesAdicionales(Agenda a) {

       String lista = "";
       Episodios e;
       if(a.getEpisodio()!=null) {
           if(a.getEpisodio()!=null) {

               e = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio", a.getEpisodio().getId());
               for(EpisodiosExAdicionales exa : e.getEpisodiosExAdicionalesCollection()) {

                   if(exa.getEstudio().getMedico().equals("S")) {
                       lista += exa.getEstudio().getDescripcion() + "\n";
                   }
                }
               if(lista.length()>0) {
                   String infopac = e.getPaciente().getPersonas().getApellidos() + " " + e.getPaciente().getPersonas().getNombres();
                   mostrarListaEstudiosARealizar(infopac, lista);
               }
           }
       }
   }

   private void mostrarListaEstudiosARealizar(String txtpac, String lista) {

       DialogoInformacion di = new DialogoInformacion(null, "Lista de exámenes a realizar", "Lista de examenes adicionales a realizar a " + txtpac, lista, true);
       di.setVisible(true);
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
        
            boolean inmediato = a.getAgendaPK().getTurno()==0;
            if(inmediato || estaEnTiempo(a))  {

                if(a.getEpisodio().getCarnetdesalud()!=null) {
                    Episodios epi = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio",a.getEpisodio().getId());
                    if(epi.getCarnetdesalud().getVigenciaodontologo()!= -1) {
                        lista.add(a);
                    }
                } else {
                    lista.add(a);
                }
             }
        }
        return(lista);
    }

    private boolean estaEnTiempo(Agenda a) {
        
        Calendar cal = Calendar.getInstance();

        Date ahora = cal.getTime();

        long difa = 0;

        boolean resp = false;
        
        if(a.getEpisodio()!=null) {
            Timming t = a.getEpisodio().getTimming();
            if(t != null) {
                
               difa = ahora.getTime() - t.getHoraingreso().getTime();
               if(difa >= 300000)
                   resp = true;
            }
        } 
        return(resp);
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
