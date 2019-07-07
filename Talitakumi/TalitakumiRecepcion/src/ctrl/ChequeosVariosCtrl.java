package ctrl;

import javax.swing.JPanel;
import org.apache.log4j.Logger;
import talitakumi.framework.controller.AbstractVisualController;
import ui.UIChequeosVarios;

/**
 *
 * @author rupertus
 */
public class ChequeosVariosCtrl extends AbstractVisualController {

    private Logger logger;
    private UIChequeosVarios ui;
    public ChequeosVariosCtrl() {

        ui = new UIChequeosVarios(null, true);

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

    @Override
    public void vaciarDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
