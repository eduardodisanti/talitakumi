
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import talitakumi.framework.Sesion;
import talitakumi.genericontrollers.pacientes.ModificarPacienteCtrl;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Pacientes;
import talitakumi.model.core.Personas;
import talitakumi.model.core.Usuarios;
import ui.UITelefonista;
import view.DialogoError;

/**
 *
 * @author rupertus
 */
public class TelefonistaCtrl {

    private List <Object> listaagenda;
    private UITelefonista uic;
    private List<Clientes> procedencias;
    private HashMap<Integer, String> hmprocedencias; // TODO - CAGADA PARA LLEVAR LOS CLIENTES - ARREGLAR
    private Logger logger;
    private FileAppender appender;
    private Date fechadesde, fechahasta;
    private String horaelegida;
    private HeartBeat hb;
    private int turnoelegido;
    private boolean isLoaded;
    private DefaultTableModel dftm;

    public TelefonistaCtrl() {

        logger = Sesion.getLogger();

        uic = new UITelefonista();

        //actrl = new AgendarCtrl();

        uic.salirListener(new SalirListener());
        uic.recargarListener(new RecargarListener());
        uic.crearPacientesListener(new PacientesListener());
        uic.crearBuscarListener(new BuscarListener());
        uic.crearAgendarListener(new AgendarListener());
        uic.almacenarCuadernoListener(new AlmacenarCuadernoListener());

        uic.crearCalendarioListener1(new CalendarioCambiardoListener());

        isLoaded = false;
        uic.setVisible(true);
        isLoaded = true;
        Usuarios u = (Usuarios)(Sesion.getVariableEntorno("usuario"));
        uic.setFuncionario(u.getUsuario());
        uic.doMaximize();

        procedencias = (List) (Sesion.getServiceDelegatorRemote().invoke("ServicioCargarClientes", null));

        hmprocedencias = new HashMap<Integer, String>();
        for(Clientes cl : procedencias) {
            hmprocedencias.put(cl.getId(), cl.getNombre());
        }

        hb = new HeartBeat();
        fechaElegida();
        hb.start();
    }


   private void cerrar() {

       uic.setVisible(false);
       uic.dispose();
       hb.parar();

   }

   private void pacientesPulsado() {

       hb.running = false;
       new ModificarPacienteCtrl();
       hb.running = true;
   }
   
   private void fechaElegida() {
       
       Date fechaant = fechadesde;
       if(fechaant==null) {
           fechaant = new Date();
       }
       
       fechadesde = uic.getFechaElegida();
       horaelegida = uic.getHoraElegida();
       fechahasta = fechadesde;
       uic.setFechaActual(fechadesde);
       
       //if(!fechaant.equals(fechadesde)) {
            cargarAgenda();
            //cargarAgendaFecha(fechadesde, horaelegida);
       //}
       
       fechaant = fechadesde;
   }

   private void cargarAgenda() {

       dftm = new DefaultTableModel();
       dftm.addColumn("Act");
       dftm.addColumn(" - ");
       dftm.addColumn("Cons");
       dftm.addColumn("Hora");
       dftm.addColumn("Cliente");
       dftm.addColumn("Disponibles");

       hb.running = false;

        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("HH:mm");

        int tipoListado = 0;

        HashMap<String, Object> params = new HashMap();

        System.out.println("Fecha desde es :  " + fechadesde);
        System.out.println("Fecha hasta es :  " + fechahasta);

        params.put("fechadesde",  fechadesde);
        params.put("fechahasta",  fechahasta);
        params.put("tipolistado", tipoListado);
        params.put("orden",       "alfabetico");

        listaagenda = (List<Object>) Sesion.getServiceDelegatorRemote().invoke("CargarAgendaTelefonistaINET", params);

        ImageIcon actividad = new ImageIcon(getClass().getResource("/imagenes/bookmark.png"));
        ImageIcon nada = new ImageIcon(getClass().getResource("/imagenes/gris.gif"));

        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm");//dd/MM/yyyy
        Date now = new Date();
        String ahora = sdfDate.format(now);

       String procedencia = "-";
       if (!listaagenda.isEmpty()) {
            for(Object obj : listaagenda) {

                Object [] o = (Object[]) obj;

                String xminutos = o[1]+"";
                if(xminutos.length()==1) {
                    xminutos = "0" + xminutos;
                }
                String xhora = o[0]+"";
                if(xhora.length()==1) {
                    xhora = "0" + xhora;
                }
                xhora+= ":" + xminutos;
                Integer lugares = (Integer) o[2];
                String cons = "";

                if(xhora.compareTo(ahora) > 0 || fechadesde.after(now)){
                    dftm.addRow(new Object[]{actividad, nada, cons, xhora, procedencia ,lugares});
                    System.out.println("Agregando " + xhora + " " + lugares);
                    cargarAgendaFecha(dftm, fechadesde, xhora);
                }
            }
       }

       uic.setHora(sdf.format(new Date()));
       uic.setModelListaAgenda(dftm);

       uic.setAnchoColumna(0, 32);
       uic.setAnchoColumna(1, 32);
       uic.setAnchoColumna(2, 60);
       uic.setAnchoColumna(3, 200);
       uic.setAnchoColumna(4, 100);
       uic.setAnchoColumna(5, 60);

       hb.running = true;
   }

   private void cargarAgendaFecha(DefaultTableModel dftm1, Date fecha, String hora) {

      HashMap<String, Integer> resumen = new HashMap();
      HashMap<String, Object> params = new HashMap();

      Integer cant;

//    dftm1.addColumn("Act");
//    dftm1.addColumn("Paciente");
//    dftm1.addColumn("Hora");

    System.out.println("Fecha desde es :  " + fechadesde);
    System.out.println("Fecha hasta es :  " + fechahasta);

    params.put("fechadesde",  fechadesde);
    params.put("fechahasta",  fechahasta);
    params.put("tipolistado", 0);
    params.put("orden",       "alfabetico");
    params.put("hora",  hora);

    ImageIcon nada = new ImageIcon(getClass().getResource("/imagenes/gris.gif"));
    List<Agenda> listaagendaX = (List<Agenda>) Sesion.getServiceDelegatorRemote().invoke("CargarPacientesAnotadosTelefonista", params);
    resumen = (HashMap<String, Integer>) Sesion.getServiceDelegatorRemote().invoke("CargarResumenPacientesAnotadosTelefonista", params);
       ImageIcon actividad = null;

            String procedencia = "";
            for(Agenda a : listaagendaX) {

                Personas p = null;
                try {
                    p = obtenerPersona(a.getPaciente());
                } catch (Exception ex) {

                    p = new Personas();
                    System.out.println("Error feo " + ex);
                }


                String nombre = p.getApellidos() + " " + p.getNombres();
                System.out.println("Paciente es : " + nombre + " " + p.getDocumento() + " act=" + a.getActividad());
                String consultorio = a.getAgendaPK().getConsultorio();
                System.out.println("[CONSULTORIO] ==>" + consultorio);


                Integer act = a.getActividad();
                actividad = new ImageIcon(getClass().getResource("/imagenes/bookmark.png"));

                String clave = "Otros";
                System.out.println(" paso por aca " );
                switch (act.intValue()) { // TODO - PROPERTIES
                    case 0 :  actividad = new ImageIcon(getClass().getResource("/imagenes/gris.gif"));
                              break;
                    case 1 :  actividad = new ImageIcon(getClass().getResource("/imagenes/libretamini.png"));
                              clave = "Libreta amateur";
                              break;
                    case 2 :  actividad = new ImageIcon(getClass().getResource("/imagenes/carnetmini.png"));
                              clave = "Carnet de salud";
                              break;
                    case 8 :  actividad = new ImageIcon(getClass().getResource("/imagenes/taximini.png"));
                              clave = "Libreta prof";
                              break;
                    case 10 :  actividad = new ImageIcon(getClass().getResource("/imagenes/autobus-rojomini.png"));
                              clave = "Psicotecnico";
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
                              clave = "Chequeo";
                              break;
                }

                if(a.getCliente()==null) {
                    procedencia = "!Particular";
                }
                else {
                    procedencia = a.getCliente().getNombre();
                }
                if(a.getAgendaPK().getConsultorio().equals("INET") || a.getAgendaPK().getConsultorio().equals("TEL") || a.getAgendaPK().getConsultorio().equals("")) {
                    try {
                        dftm1.addRow(new Object[]{nada, actividad, consultorio, nombre, procedencia, a.getAgendaPK().getHora()});
                    } catch(Exception ex) {
                         dftm1.addRow(new Object[]{nada, actividad, consultorio, nombre, procedencia, a.getAgendaPK().getHora()});
                      }
                }
       }

       String res = "<html><body><table border=0 bgcolor='#000000' cellspacing='1px'><tr bgcolor='#ffffff'><th>Servicio</th><th>Anotados</th></tr>";
       for(String s : resumen.keySet()) {

           cant = resumen.get(s);

           res+="<tr bgcolor='#ffffff'><td>" + s + "</td><td align='right'>" + cant + "</td></tr>";
       }
       res+="</table></body></html>";

       uic.setResumen(res);
   }
    
    private Personas obtenerPersona(int id) throws Exception {

        Personas p;
        Pacientes pac;
        if(id==0) {
            pac = new Pacientes();
            pac.setPersonas(new Personas());
        } else {
            pac = (Pacientes) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarPaciente", id);
        }

        p = pac.getPersonas();
        return(p);
    }

    private void cargarCuaderno() {

        String contenido = (String) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarCuaderno", fechadesde);
        uic.setContenidoCuaderno(contenido);
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

      int times = 0;
      while(running) {
           if(running) {
               cargarAgenda();
               if(times==0) {
                cargarCuaderno();
               } else {
                   ++times;
                   if(times >= 10) {
                       times = 0;
                   }
               }
             }
           try {
                    Thread.sleep(120000);
               } catch (InterruptedException ex) {
                    logger.error(ex);
            }
      }
     }
   }

   private void guardarNotas() {

       hb.running = false;

       String s = uic.getContenidoCuaderno(horaelegida);
       Sesion.getServiceDelegatorRemote().invoke("AlmacenarCuaderno", s);
       hb.running = true;
   }

   private void agendarPaciente() {

       hb.running = false;
       horaelegida = uic.getHoraElegida();
       if(horaelegida==null) {
           new DialogoError(null, "Error ", "No se ha elegido una hora", "<b>No se ha elegido una hora, no se puede agendar</b>", true).setVisible(true);
       } else {
           turnoelegido = uic.getTurnoElegido();
           int disponibles = uic.getDisponibles();
           if(disponibles > 0) {
                AgendarCtrl actrl = new AgendarCtrl(fechadesde, horaelegida, turnoelegido);
           }
       }

       hb.running = true;
   }
   
   private void buscarPaciente() {

       BuscarAgendaPacienteCtrl mcac = new BuscarAgendaPacienteCtrl();
       
   }


   public class SalirListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            cerrar();
        }

   }
   
   public class CalendarioCambiardoListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent pce) {
            if(isLoaded)
                fechaElegida();
        }

   }

   public class AgendarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            agendarPaciente();
        }

   }

   public class AlmacenarCuadernoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            guardarNotas();
        }

   }

   public class BuscarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            buscarPaciente();
        }

   }


   public class RecargarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            cargarAgenda();
        }

   }

   public class PacientesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            pacientesPulsado();
        }
   }

}
