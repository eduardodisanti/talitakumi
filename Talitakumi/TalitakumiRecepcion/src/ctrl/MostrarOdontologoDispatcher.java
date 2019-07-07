/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import talitakumi.framework.controller.TalitakumiDispatcher;
import talitakumi.model.core.Episodios;
import talitakumi.odontologo.ctrl.RevisionOdontologicaController;
import view.DialogoInformacion;
import view.UIGenerico;

/**
 *
 * @author rupertus
 */
public class MostrarOdontologoDispatcher implements TalitakumiDispatcher {

    Object result;

    public MostrarOdontologoDispatcher(Episodios e) {

        result = null;
        this.dispatch(e);
    }

    @Override
    public Object dispatch(Object o) {
        
        return(cargarActividad((Episodios) o));
    }

    private Boolean cargarActividad(Episodios e) {

        Boolean ok = false;

        int idactividad = e.getTipoepisodio().getTipoactividad().getId(); // TODO - ver la forma de hacerlo sin el case

        switch(idactividad) {

            case 2 : tratarCarnetDeSalud(e);
                     result = e;
                     break;

            default : tratarActividadDesconocida();
                     break;
        }
        return(ok);
    }

    private Object tratarCarnetDeSalud(Episodios e) {

        UIGenerico uic = new UIGenerico(null, true);

        RevisionOdontologicaController ec = new RevisionOdontologicaController(e);
        ec.setMostrarBotonAlmacenar(false);
        uic.setPanelUtil(ec.getPanel());

        uic.setVisible(true);
        return(e);
    }

    private void tratarActividadDesconocida() {

        new DialogoInformacion(null, "Modificar episodio", "No es posible modificar episodio", "ModificarActividadesDispatcher : ActividadDesconocida", true).setVisible(true);

    }

    public Object getResult() {
        return(result);
    }
}
