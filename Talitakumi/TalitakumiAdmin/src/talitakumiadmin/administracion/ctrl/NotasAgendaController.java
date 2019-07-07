/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumiadmin.administracion.ctrl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.model.core.Moldeagenda;
import talitakumi.model.core.Tipodeactividad;
import talitakumiadmin.administracion.ui.UINotasAgenda;

/**
 *
 * @author rupertus
 */
class NotasAgendaController extends AbstractVisualController {

    private UINotasAgenda uilp;
    
    public NotasAgendaController() {

        uilp = new UINotasAgenda();
        uilp.addAlmacenarListener(new AlmacenarListener());

        try {
            setTexto();
        } catch (Exception ex) {
            Logger.getLogger(NotasAgendaController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error MoldeAgendaController " + ex);
        }

    }
    
    @Override
    public void guardarDatos() throws Exception {

        String txt = uilp.getContenidoCuaderno();
        Sesion.getServiceDelegatorRemote().invoke("AlmacenarCuaderno", txt);
    }


    @Override
    public JPanel getPanel() {
        return(uilp);
    }

    @Override
    public void cerrarTodo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Moldeagenda crearNuevoMoldeAgenda(Integer hora, Integer minutos, Integer duracion, Integer lugares, Tipodeactividad tipoactividad) {
        Moldeagenda max = new Moldeagenda();
        max.setHora(hora);
        max.setMinutos(minutos);
        max.setDuracion(duracion);
        max.setLugares(lugares);
        max.setConsultorio("-");
        max.setTurno(0);
        max.setServicio(tipoactividad);

        return(max);
    }

    private void setTexto() {
        String txt = (String) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarCuaderno", null);
        uilp.setContenidoCuaderno(txt);
    }

    @Override
    public void vaciarDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
