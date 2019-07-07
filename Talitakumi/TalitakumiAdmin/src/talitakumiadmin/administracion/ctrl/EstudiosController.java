/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumiadmin.administracion.ctrl;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.model.core.Estudios;
import talitakumiadmin.administracion.ui.UIEstudios;

/**
 *
 * @author rupertus
 */
class EstudiosController extends AbstractVisualController {

    private UIEstudios uilp;
    private boolean isLoaded;
    private Estudios e;
    private boolean nuevo = false;
    
    public EstudiosController() {

        uilp = new UIEstudios();
        uilp.addAlmacenarListener(new AlmacenarListener());
        uilp.addCerrarListener(new CancelarListener());
        uilp.addNuevoListener(new NuevoListener());
        uilp.addEstudioElegidoListener(new EstudioElegidoListener());

        isLoaded = false;
        uilp.setVisible(true);
        isLoaded = true;

        try {
            cargarEstudios();
        } catch (Exception ex) {
            Logger.getLogger(EstudiosController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error MoldeAgendaController " + ex);
        }
    }

   private void cargarEstudios() {

       Collection<Estudios> cl = (Collection<Estudios>) Sesion.getServiceDelegatorRemote().invoke("CargarEstudios", 0);


       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

       DefaultTableModel dftm = new DefaultTableModel();
       dftm.addColumn("id");
       dftm.addColumn("Descripcion");
       dftm.addColumn("Precio base");
       dftm.addColumn("Laboratorio");
       dftm.addColumn("Medico");
       for(Estudios f : cl) {

            dftm.addRow(new Object[]{f.getId(), f.getDescripcion(), f.getPreciobase(), f.getLaboratorio(), f.getMedico()});
       }

       uilp.setModeloFeriados(dftm);
   }

    @Override
    public void vaciarDatos() {
        
       Integer id = uilp.getId();
       e = null;

       uilp.setDescripcion("");
       uilp.setTagEstudio("-");
       uilp.setPrecioBase(0.0f);
       uilp.setLaboratorio("N");
       uilp.setMedico("N");
       
       nuevo = true;
    }

   public class EstudioElegidoListener implements MouseListener {


        @Override
        public void mouseClicked(MouseEvent me) {
            
        }

        @Override
        public void mousePressed(MouseEvent me) {
            fechaElegida();
            nuevo = false;
        }

        @Override
        public void mouseReleased(MouseEvent me) {

        }

        @Override
        public void mouseEntered(MouseEvent me) {

        }

        @Override
        public void mouseExited(MouseEvent me) {

        }
   }

   private void fechaElegida() {

       Integer id = uilp.getId();
       e = cargarEstudio(id);

       uilp.setDescripcion(e.getDescripcion());
       uilp.setTagEstudio(e.getTagEstudio());
       uilp.setPrecioBase(e.getPreciobase());
       uilp.setLaboratorio(e.getLaboratorio());
       uilp.setMedico(e.getMedico());
   }

    private Estudios cargarEstudio(Integer id) {

        e = (Estudios) Sesion.getServiceDelegatorRemote().invoke("CargarEstudio", id);

        return(e);
    }

    @Override
    public void guardarDatos() throws Exception {

        if(nuevo) {
            e = new Estudios();
        }
            
        //e = new Estudios();
        e.setPreciobase(uilp.getPrecioBase());
        e.setDescripcion(uilp.getDescricpion());
        e.setLaboratorio(uilp.getLaboratorio());
        e.setTagEstudio(uilp.getTagEstudio());
        e.setMedico(uilp.getMedico()); 
        
        Sesion.getServiceDelegatorRemote().invoke("AlmacenarEstudio", e);
        cargarEstudios();
    }


    @Override
    public JPanel getPanel() {
        return(uilp);
    }

    @Override
    public void cerrarTodo() {
        uilp.setVisible(false);
    }

    @Override
    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
