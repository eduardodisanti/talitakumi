/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import talitakumi.framework.controller.TalitakumiDispatcher;
import talitakumi.laboratorio.ctrl.EstudiosController;
import talitakumi.model.core.Episodios;
import talitakumi.servicios.Reporter;
import view.DialogoInformacion;
import view.UIGenerico;

/**
 *
 * @author rupertus
 */
public class MostrarLaboratorioDispatcher implements TalitakumiDispatcher {

    private Object result;
    private Episodios epi;

    public MostrarLaboratorioDispatcher(Episodios e) {

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

            case 9 : tratarLibretaProfesional(e);
                     result = e;
                     break;

            case 12 :
            case 13:
                     tratarLaboratorio(e);
                     result = e;
                     break;

            default : tratarActividadDesconocida();
                     break;
        }
        return(ok);
    }

    private Object tratarCarnetDeSalud(Episodios e) {

        UIGenerico uic = new UIGenerico(null, true);

        epi = e;
        EstudiosController ec = new EstudiosController(e);
        ec.setMostrarBotonAlmacenar(false);
        uic.setPanelUtil(ec.getPanel());
        uic.setImprimirListener(new ImprimirListener());

        uic.setModal(false);
        uic.setVisible(true);
        return(e);
    }

    private Object tratarLibretaProfesional(Episodios e) {

        UIGenerico uic = new UIGenerico(null, true);

        epi = e;
        EstudiosController ec = new EstudiosController(e);
        ec.setMostrarBotonAlmacenar(false);
        uic.setPanelUtil(ec.getPanel());
        uic.setImprimirListener(new ImprimirListener());

        uic.setModal(false);
        uic.setVisible(true);
        return(e);
    }

    private void tratarActividadDesconocida() {

        new DialogoInformacion(null, "Modificar episodio", "No es posible modificar episodio", "ModificarActividadesDispatcher : ActividadDesconocida", true).setVisible(true);

    }

    public Object getResult() {
        return(result);
    }

    private void emitirEstudio(Episodios e) {

         int idepisodio;

         idepisodio = e.getId();


         HashMap m = new HashMap();

         m.put("episodiodesde", idepisodio);
         m.put("episodiohasta", idepisodio);
         new Reporter("analisislaboratorio.jasper").listar(m);

    }

    private Object tratarLaboratorio(Episodios e) {
        UIGenerico uic = new UIGenerico(null, true);

        epi = e;
        EstudiosController ec = new EstudiosController(e);
        ec.setMostrarBotonAlmacenar(false);
        uic.setPanelUtil(ec.getPanel());
        uic.setImprimirListener(new ImprimirListener());

        uic.setModal(false);
        uic.setVisible(true);
        return(e);
    }

    private class ImprimirListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            emitirEstudio(epi);
        }

        
    }
}
