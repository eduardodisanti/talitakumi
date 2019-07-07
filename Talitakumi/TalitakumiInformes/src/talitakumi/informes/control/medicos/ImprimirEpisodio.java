/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.control.medicos;

import talitakumi.model.core.Episodios;

/**
 *
 * @author rupertus
 */
public class ImprimirEpisodio {

    public ImprimirEpisodio(Episodios e) {

        int ta = e.getTipoepisodio().getTipoactividad().getId();

        switch(ta) {

            case 1:
                    imprimirLDC(e);
                    break;

            case 2:
                    imprimirCDS(e);
                    break;
        }
    }

    private void imprimirLDC(Episodios e) {
        
    }

    private void imprimirCDS(Episodios e) {

        new ReportCDSCtrl(e);
    }

}
