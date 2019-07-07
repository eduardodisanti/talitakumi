/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.laboratorio.ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;
import talitakumi.framework.Sesion;
import talitakumi.laboratorio.ui.UIImprimirLaboratorio;
import talitakumi.model.core.Estudioshechos;
import talitakumi.model.core.Personas;

/**
 *
 * @author eduardo
 */
public class ImprimirLaboratorioController {

    private UIImprimirLaboratorio ui;
    private Personas p = null;
    private Vector <Estudioshechos> epis;
    private Date fecha;

    public ImprimirLaboratorioController() {

            ui = new UIImprimirLaboratorio(null, false);

            ui.setImprimirListadoListener(new ImprimirListadoListener());
            ui.setCerrarListener(new CerrarListener());
            ui.setBuscarEstudioListener(new BuscarEstudioListener());
            ui.setVisible(true);
    }

    private void cerrarUI() {

        ui.setVisible(false);
        ui.dispose();
    }

    private void buscarEstudio() {

        new BuscarEstudioController();
        cerrarUI();
    }

    private void imprimirListado() {

        new ImprimirListadoDiarioLaboratorioController().setFecha(fecha);
        cerrarUI();
    }

    void setFecha(Date fechadesde) {

        fecha = fechadesde;
    }

    private class CerrarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            cerrarUI();
        }
    }

    private class BuscarEstudioListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            buscarEstudio();
        }
    }

    private class ImprimirListadoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            imprimirListado();
        }
    }

    Personas obtenerPersona(int cedula) throws Exception {


        p = (Personas) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarPersona", cedula);

        return(p);
    }

}
