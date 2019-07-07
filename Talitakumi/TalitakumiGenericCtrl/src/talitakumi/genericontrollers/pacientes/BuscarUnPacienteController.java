/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.genericontrollers.pacientes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.model.core.Personas;
import view.PanelMedilab;
import view.UIBuscarPaciente;
import talitakumi.engine.framework.DataParameter;
import talitakumi.model.core.Episodios;

/**
 *
 * @author rupertus
 */
public class BuscarUnPacienteController extends AbstractVisualController {

    AbstractVisualController c;

    UIBuscarPaciente uic;

    Logger logger;
    FileAppender appender;
    private int pacienteElegido;
    PanelMedilab pm;
    int filtro;
    private Integer cedulaelegida;

    public BuscarUnPacienteController() {

        logger = Sesion.getLogger();
        pacienteElegido = -1;
        filtro = 0;

        uic = new UIBuscarPaciente(null, true);

        uic.setBuscarPacienteListener(new BuscarPacienteListener());
        uic.setPacienteListener(new PacienteListener());
        uic.setVisible(true);

    }

    private void buscarPaciente() {

        DataParameter dp = new DataParameter();
        dp.set("nombres", uic.getNombres());
        dp.set("apellidos", uic.getApellidos());

        String buscar = uic.getBuscar() + c;

        
           List <Episodios> epis = (List<Episodios>) Sesion.getServiceDelegatorRemote().invoke("BuscarPacientesConNombreParecido", dp);

           DefaultTableModel dtm = new DefaultTableModel();
           dtm.addColumn("Id");
           dtm.addColumn("Apellidos");
           dtm.addColumn("Nombres");
           dtm.addColumn("Fecha");
           for(Episodios e : epis) {
               
              Personas p = e.getPaciente().getPersonas();
              String xfecha = "";
              SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
              xfecha = sdf.format(e.getFecha());
              Object [] o = {p.getDocumento(), p.getApellidos(), p.getNombres(), xfecha};
              dtm.addRow(o);
           }

           uic.setDatosTabla(dtm);
      
    }
    private void cerrar() {


    }

   public int getCedulaElegida() {

//       if(cedulaelegida > 999999)
//           cedulaelegida *= 10;
       
       return(cedulaelegida);
   }

   private void pacienteElegido() {

       cedulaelegida = uic.getItemElegido();

       uic.setVisible(false);

   }

   private void cerrarFrame() {

            uic.setVisible(false);
            uic.dispose();
   }

    @Override
    public void guardarDatos() throws Exception {
           c.guardarDatos();
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

   class PacienteListener implements MouseListener {

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

   class BuscarPacienteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            buscarPaciente();
        }
      }
   }
