/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import talitakumi.engine.framework.DataParameter;
import talitakumi.framework.Sesion;
import talitakumi.model.core.Actividades;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.Personas;
import ui.UIBuscarAgendaPaciente;

/**
 *
 * @author victoria
 */
public class BuscarAgendaPacienteCtrl {

    private UIBuscarAgendaPaciente uia;
    private List<Actividades> actividades;
    private int turno;

    public BuscarAgendaPacienteCtrl(){

        uia = new UIBuscarAgendaPaciente(null, false);

        uia.setSize(850,250);
        uia.setLocationRelativeTo(null);
        uia.setVisible(true);
        
        uia.setCancelarListener(new SalirListener());
        uia.setCargarPacienteListener(new CargarPacienteListener());
        uia.setPacienteElegidoListener(new BorrarLineaListener());
        
    }

    private void cerrar() {

       uia.setVisible(false);
       uia.dispose();

   }

    private void cargarPaciente() {

        int cedula = uia.getCedula();

        try {
            Personas p = obtenerPersona(cedula);

            if(p!=null)
                mostrarPaciente(p);
        } catch(Exception ex) {

            System.out.println("Error cargarPaciente en telefonista " + ex);
        }
    }

    Personas obtenerPersona(int cedula) throws Exception {

        Personas p = (Personas) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarPersona", cedula);

        return(p);
    }

    private void borrarLinea() {

        try {
            DataParameter dp = new DataParameter();
            String xfecha = uia.getFechaElegida();
            String xhora = uia.getHoraElegida();
            Integer xturno = uia.getTurnoElegido();
            String xcons = uia.getConsultorioElegido();

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            dp.set("fecha", sdf.parse(xfecha));
            dp.set("hora", xhora);
            dp.set("turno", xturno);
            dp.set("consultorio", xcons);

            Object resp = Sesion.getServiceDelegatorRemote().invoke("BorrarTurnoAgenda", dp);
            cargarPaciente();
        } catch(Exception ex) {

            System.out.println("Error borrandoLinea Agenda : " + ex);
        }

    }

    private void mostrarPaciente(Personas p) {

        uia.setApellidos(p.getApellidos());
        uia.setNombres(p.getNombres());
//        uia.setTelefono(p.getMovil());
//        uia.setSexo(p.getSexo());

        DataParameter dp = new DataParameter();
        dp.set("paciente", p.getPacientes().getId());
        dp.set("fechadesde", new Date());
        dp.set("fechahasta", null);
        List<Agenda> la = (List<Agenda>) Sesion.getServiceDelegatorRemote().invoke("CargarAgendaPaciente", dp);

        DefaultTableModel dftm = new DefaultTableModel();

        dftm.addColumn("Accion");
        dftm.addColumn("Fecha");
        dftm.addColumn("Hora");
        dftm.addColumn(" - ");
        dftm.addColumn("Consultorio");
        dftm.addColumn("Servicio");

        ImageIcon actividad = null;
        ImageIcon borrar = new ImageIcon(getClass().getResource("/imagenes/remove.png"));
        
        for(Agenda a : la) {


            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String xfecha = sdf.format(a.getAgendaPK().getFecha());

                Integer act = a.getActividad();
                actividad = new ImageIcon(getClass().getResource("/imagenes/bookmark.png"));

                System.out.println(" paso por aca " );
                switch (act.intValue()) { // TODO - PROPERTIES
                    case 0 :  actividad = new ImageIcon(getClass().getResource("/imagenes/gris.gif"));
                              break;
                    case 1 :  actividad = new ImageIcon(getClass().getResource("/imagenes/libretamini.png"));
                              break;
                    case 2 :  actividad = new ImageIcon(getClass().getResource("/imagenes/carnetmini.png"));
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
                    case 14 :  actividad = new ImageIcon(getClass().getResource("/imagenes/bookmark.png"));
                              break;
                    case 15 : actividad = new ImageIcon(getClass().getResource("/imagenes/humano.png"));
                              break;
                }
            dftm.addRow(new Object[]{borrar, xfecha, a.getAgendaPK().getHora(), a.getAgendaPK().getTurno(), a.getAgendaPK().getConsultorio(), actividad});
        }

        uia.setModeloTabla(dftm);
//        uia.setAnchoColumna(0, 50);
//        uia.setAnchoColumna(1, 50);
//        uia.setAnchoColumna(2, 10);
//        uia.setAnchoColumna(3, 200);
        

    }

    private class BorrarLineaListener implements MouseListener {


        @Override
        public void mouseClicked(MouseEvent me) {

            System.out.println("mouseClicked");
            borrarLinea();
        }

        @Override
        public void mousePressed(MouseEvent me) {
            //System.out.println("mousePressed");
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            //System.out.println("mouseReleased");
        }

        @Override
        public void mouseEntered(MouseEvent me) {
            //System.out.println("mouseEntered");
        }

        @Override
        public void mouseExited(MouseEvent me) {
            //System.out.println("mouseExited");
        }
    }

    private class SalirListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            cerrar();
        }
    }

    private class CargarPacienteListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent fe) {

        }

        @Override
        public void focusLost(FocusEvent fe) {

            cargarPaciente();
        }

    }
}
