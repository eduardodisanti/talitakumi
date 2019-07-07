/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumiadmin.administracion.ctrl;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.model.core.Usuarios;
import talitakumiadmin.administracion.ui.UIUsuarios;

/**
 *
 * @author rupertus
 */
class UsuariosController extends AbstractVisualController {

    private UIUsuarios uilp;
    private boolean isLoaded;
    private Usuarios e;
    
    public UsuariosController() {

        uilp = new UIUsuarios();
        uilp.addAlmacenarListener(new AlmacenarListener());
        uilp.addCerrarListener(new CancelarListener());
        uilp.addEstudioElegidoListener(new EstudioElegidoListener());

        isLoaded = false;
        uilp.setVisible(true);
        isLoaded = true;

        try {
            cargarEstudios();
        } catch (Exception ex) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error MoldeAgendaController " + ex);
        }
    }

   private void cargarEstudios() {

       Collection<Usuarios> cl = (Collection<Usuarios>) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarUsuarios", 0);

       DefaultTableModel dftm = new DefaultTableModel();
       dftm.addColumn("Usuario");
       dftm.addColumn("Cargo");
       dftm.addColumn("Nivel");
       dftm.addColumn("Funcionario");
       dftm.addColumn("Médico");

       for(Usuarios f : cl) {

            dftm.addRow(new Object[]{f.getUsuario(), f.getCargo(), f.getNivel(), f.getFuncionario(), f.getMedico()});
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

       String id = uilp.getUsuarioElegido();
       e = cargarEstudio(id);
       
       uilp.setCargo(e.getCargo());
       uilp.setFuncionario(e.getFuncionario());
       uilp.setEmail(e.getEmail());
       uilp.setMedico(e.getMedico());
       uilp.setNivel(e.getNivel());
       uilp.setRol(e.getRol());
       uilp.setUsuario(e.getUsuario());
       uilp.setClave(e.getClave());
       
   }

    private Usuarios cargarEstudio(String id) {

        e = (Usuarios) Sesion.getServiceDelegatorRemote().invoke("CargarUsuario", id);

        return(e);
    }

    @Override
    public void guardarDatos() throws Exception {

        if(e==null) {
            e = new Usuarios();
        }
        e.setCargo(uilp.getCargo());
        e.setFuncionario(uilp.getFuncionario());
        e.setEmail(uilp.getEmail());
        e.setMedico(uilp.getMedico());
        e.setNivel(uilp.getNivel());
        e.setRol(uilp.getRol());
        e.setUsuario(uilp.getUsuario());
        e.setClave(uilp.getClave());
        Sesion.getServiceDelegatorRemote().invoke("AlmacenarUsuario", e);
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
