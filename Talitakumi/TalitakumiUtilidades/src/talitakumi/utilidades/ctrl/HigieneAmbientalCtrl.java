package talitakumi.utilidades.ctrl;

import javax.swing.JPanel;
import org.apache.log4j.Logger;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.utilidades.ui.UIHigieneAmbiental;

/**
 *
 * @author rupertus
 */
public class HigieneAmbientalCtrl extends AbstractVisualController {

    private Logger logger;
    private UIHigieneAmbiental ui;
    public HigieneAmbientalCtrl() {

        ui = new UIHigieneAmbiental(null, true);
    }

    public Integer getOpcion() {

        ui.setVisible(true);
        return(ui.getOpcion());
    }

    @Override
    public void guardarDatos() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JPanel getPanel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void cerrarTodo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void vaciarDatos() {
        
    }
}
