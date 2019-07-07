/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.odontologo.ui;

import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import view.UIConsultorio;

/**
 *
 * @author rupertus
 */
public class UIOdontologo extends UIConsultorio {

    public Date getFechaDesde() {
        return(super.getFecha());
    }


    public void setModeloFiltro(DefaultComboBoxModel cbm) {

        super.setTipoVistaModel(cbm);
    }
}
