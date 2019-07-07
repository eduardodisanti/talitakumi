
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.laboratorio.ui;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import view.UIConsultorio;

/**
 *
 * @author rupertus
 */
public class UILaboratorio extends UIConsultorio {

    public Date getFechaDesde() {
        
        return(super.getFecha());
    }

    public void imprimirListener(ActionListener imprimirListener) {
        super.setImprimirListener(imprimirListener);
    }

    public void setFontTabla(Font f) {
        super.setFontTablaAgenda(f);
    }


    public void setModeloFiltro(DefaultComboBoxModel cbm) {

        super.setTipoVistaModel(cbm);
    }

    @Override
    public void AgregarCambioBuscarListener(KeyListener ke) {

        super.AgregarCambioBuscarListener(ke);
    }

    @Override
    public String getBuscar() {

        return(super.getBuscar());
    }

    public void setPacienteEncontradoBuscar(int i) {

        super.apuntarFilaAgenda(i);
    }

    public void encenderLed() {
        super.setLed(true);
    }

    public void apagarLed() {
        super.setLed(false);
    }

    @Override
    public void setMensajesModel(DefaultComboBoxModel cbm) {
        super.setMensajesModel(cbm);
    }

    @Override
    public Integer getMensajeElegido() {
        return(super.getMensajeElegido());
    }

    public void leerMensajesListener(ActionListener mensajesListener) {

        super.setMensajesListener(mensajesListener);
    }
}
