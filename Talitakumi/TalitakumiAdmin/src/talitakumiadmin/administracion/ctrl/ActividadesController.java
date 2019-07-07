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
import talitakumi.model.core.Actividades;
import talitakumiadmin.administracion.ui.UIActividades;

/**
 *
 * @author rupertus
 */
class ActividadesController extends AbstractVisualController {

    private UIActividades uilp;
    private boolean isLoaded;
    private Actividades e;
    
    public ActividadesController() {

        uilp = new UIActividades();
        uilp.addAlmacenarListener(new AlmacenarListener());
        uilp.addCerrarListener(new CancelarListener());
        uilp.addEstudioElegidoListener(new EstudioElegidoListener());

        isLoaded = false;
        uilp.setVisible(true);
        isLoaded = true;

        try {
            cargarEstudios();
        } catch (Exception ex) {
            Logger.getLogger(ActividadesController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error MoldeAgendaController " + ex);
        }
    }

   private void cargarEstudios() {

       Collection<Actividades> cl = (Collection<Actividades>) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarActividadesALL", 0);


       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

       DefaultTableModel dftm = new DefaultTableModel();
       dftm.addColumn("id");
       dftm.addColumn("Descripcion");
       dftm.addColumn("Precio base");
       dftm.addColumn("Tipo de actividad");
       dftm.addColumn("Emisor");
       for(Actividades f : cl) {

            dftm.addRow(new Object[]{f.getId(), f.getDescripcion(), f.getPrecio(), f.getTipoactividad().getDescripcion(), f.getEmisor()});
       }

       uilp.setModeloFeriados(dftm);
   }

    @Override
    public void vaciarDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   public class EstudioElegidoListener implements MouseListener {


       @Override
        public void mouseClicked(MouseEvent me) {
            
        }

       @Override
        public void mousePressed(MouseEvent me) {
            fechaElegida();
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
       uilp.setPrecioBase(e.getPrecio());
       uilp.setTipoActividad(e.getTipoactividad().getDescripcion());
       uilp.setEmisor(e.getEmisor());
   }

    private Actividades cargarEstudio(Integer id) {

        e = (Actividades) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarActividad", id);

        return(e);
    }

    @Override
    public void guardarDatos() throws Exception {

        e.setPrecio(uilp.getPrecioBase());
        e.setDescripcion(uilp.getDescricpion());
        e.setPrecio(uilp.getPrecioBase());
        e.setEmisor(uilp.getEmisor());
        Sesion.getServiceDelegatorRemote().invoke("AlmacenarActividad", e);
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
