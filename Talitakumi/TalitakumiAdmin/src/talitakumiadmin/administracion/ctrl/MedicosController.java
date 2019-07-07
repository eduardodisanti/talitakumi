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
import talitakumi.model.core.Medicos;
import talitakumiadmin.administracion.ui.UIMedicos;

/**
 *
 * @author rupertus
 */
class MedicosController extends AbstractVisualController {

    private UIMedicos uilp;
    private boolean isLoaded;
    private Medicos e;
    
    public MedicosController() {

        uilp = new UIMedicos();
        uilp.addAlmacenarListener(new AlmacenarListener());
        uilp.addCerrarListener(new CancelarListener());
        uilp.addNuevoListener(new NuevoListener());
        uilp.addEstudioElegidoListener(new EstudioElegidoListener());

        isLoaded = false;
        uilp.setVisible(true);
        isLoaded = true;

        try {
            cargarMedicos();
        } catch (Exception ex) {
            Logger.getLogger(MedicosController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error MoldeAgendaController " + ex);
        }
    }

   private void cargarMedicos() {

       Collection<Medicos> cl = (Collection<Medicos>) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarMedicos", 0);

       DefaultTableModel dftm = new DefaultTableModel();
       dftm.addColumn("Codigo");
       dftm.addColumn("Nombre");
       dftm.addColumn("Especialidad");
       dftm.addColumn("Activo");
       dftm.addColumn("Exerno");
       dftm.addColumn("Cedula");

       for(Medicos f : cl) {

            dftm.addRow(new Object[]{f.getCodigo(), f.getNombre(), f.getEspecialidad(), f.getActivo(), f.getExterno(), f.getCedula()});
       }

       uilp.setModeloFeriados(dftm);
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

   @Override
   public void vaciarDatos() {
       uilp.setActivo('S');
       uilp.setCedula(0);
       uilp.setCodigo(0);
       uilp.setEspecialidad(1);
       uilp.setExterno('N');
       uilp.setNombre("");
   }
   
   private void fechaElegida() {

       Integer id = uilp.getUsuarioElegido();
       e = cargarMedico(id);
       
       uilp.setActivo(e.getActivo());
       uilp.setCedula(e.getCedula());
       uilp.setCodigo(e.getCodigo());
       uilp.setEspecialidad(e.getEspecialidad());
       uilp.setExterno(e.getExterno());
       uilp.setNombre(e.getNombre());
   }

    private Medicos cargarMedico(Integer id) {

        e = (Medicos) Sesion.getServiceDelegatorRemote().invoke("CargarMedico", id);

        return(e);
    }

    @Override
    public void guardarDatos() throws Exception {

        if(e==null) {
            e = new Medicos();
        }
        
        e.setActivo(uilp.getActivo());
        e.setCedula(uilp.getCedula());
        e.setCodigo(uilp.getCodigo());
        e.setEspecialidad(uilp.getEspecialidad());
        e.setExterno(uilp.getExterno());
        e.setNombre(uilp.getNombre());

        Sesion.getServiceDelegatorRemote().invoke("AlmacenarMedico", e);
        cargarMedicos();
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
