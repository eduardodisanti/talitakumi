/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import talitakumi.framework.controller.TalitakumiDispatcher;
import talitakumi.model.core.Episodios;
import view.DialogoInformacion;

/**
 *
 * @author rupertus
 */
public class ModificarActividadesDispatcher implements TalitakumiDispatcher {

    Object result;

    public ModificarActividadesDispatcher(Episodios e) {

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

            case 12 :
            case 13 :
                     new MostrarLaboratorioDispatcher(e);
                     result = e;
                     break;

            default : tratarActividadDesconocida();
                     break;
        }
        return(ok);
    }

    private Object tratarCarnetDeSalud(Episodios e) {

        ModificarCDSCtrl mcds = new ModificarCDSCtrl();
        mcds.setEpisodio(e);

        return(mcds.modificar());
    }

    private void tratarActividadDesconocida() {

        new DialogoInformacion(null, "Modificar episodio", "No es posible modificar episodio", "ModificarActividadesDispatcher : Actividad Desconocida", true).setVisible(true);

    }

    public Object getResult() {
        return(result);
    }
}
