/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.medicos.ctrl.libreta;

import java.awt.event.ActionListener;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.medicos.ui.UILibretaDeConducir;
import talitakumi.model.core.Agenda;

/**
 *
 * @author rupertus
 */

public class LibretaDeConducirCtrl extends AbstractVisualController {

    UILibretaDeConducir uil;
    Agenda agenda;

    public LibretaDeConducirCtrl(Agenda a) {


        //agenda = a;
        uil = new UILibretaDeConducir();

        agenda = a;
        // agenda = (Agenda) Sesion.getServiceDelegatorRemote().invoke("CargarUnaAgenda", a);

        uil.cargarConsulta(a);
    }

    @Override
    public UILibretaDeConducir getPanel() {

        return(uil);
    }

    @Override
    public void guardarDatos() {

        uil.almacenar();
    }

    UILibretaDeConducir getUIPanel() {

        return(uil);
    }

    public void setVisible(boolean b) {

        uil.setVisible(b);
    }

    @Override
    public void cerrarTodo() {
        
    }

    @Override
    public void setGuardarListener(ActionListener al) {

        uil.setGuardarListener(al);
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
