/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumiadmin.administracion.ctrl;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.model.core.Procedencias;
import talitakumiadmin.administracion.ui.UIProcedencias;

/**
 *
 * @author rupertus
 */
class ProcedenciasController extends AbstractVisualController {

    private UIProcedencias uilp;
    private boolean isLoaded;
    private boolean nuevo;
    private Procedencias e;
    private List<Procedencias> procedencias;
    public ProcedenciasController() {

        uilp = new UIProcedencias();
        uilp.addAlmacenarListener(new AlmacenarListener());
        uilp.addCerrarListener(new CancelarListener());
        uilp.addEstudioElegidoListener(new EstudioElegidoListener());
        uilp.addNuevoListener(new NuevoListener());

        isLoaded = false;
        uilp.setVisible(true);
        isLoaded = true;
        nuevo = false;

        try {
            cargarEstudios();
        } catch (Exception ex) {
            Logger.getLogger(ProcedenciasController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error MoldeAgendaController " + ex);
        }
    }

   private void cargarEstudios() {

       Collection<Procedencias> cl = (Collection<Procedencias>) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarProcedencias", null);
       
       procedencias = new ArrayList<Procedencias>();
       for(Procedencias p : cl) {
           procedencias.add(p);
       }


       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

       DefaultTableModel dftm = new DefaultTableModel();
       dftm.addColumn("id");
       dftm.addColumn("Descripcion");
       dftm.addColumn("Hbilitada");
       for(Procedencias f : procedencias) {

            dftm.addRow(new Object[]{f.getId(), f.getDescripcion(), f.getHabilitado()});
       }

       uilp.setModeloFeriados(dftm);
   }

    @Override
    public void vaciarDatos() {
       nuevo = true;
       uilp.setDescripcion("");
       uilp.setHabilitada('S');
    }

    private Integer obtenerID() {
        Integer id = 0;
        
        for(Procedencias p : procedencias) {
            if(p.getId()>id) {
                id = p.getId()+1;
            }
        }
        return(id);
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
       uilp.setHabilitada(e.getHabilitado());
   }

    private Procedencias cargarEstudio(Integer id) {

        Procedencias p = null;

        for(Procedencias unap : procedencias) {
            if(unap.getId()==id) {
                p = unap;
                break;  
            }
        }
        return(p);
    }

    @Override
    public void guardarDatos() throws Exception {

        if(nuevo) {
            e = new Procedencias();
            e.setId(obtenerID());
        }
        e.setDescripcion(uilp.getDescricpion());
        e.setHabilitado(uilp.getHabilitada());
        Sesion.getServiceDelegatorRemote().invoke("AlmacenarObjeto", e);
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
