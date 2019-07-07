/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.vacunas;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Vacunas;
import view.PanelAdicionales;

/**
 *
 * @author rupertus
 */
public class VacunasCtrl extends AbstractVisualController {

    private PanelAdicionales panel;
    private Logger logger;
    private Object result;
    private Episodios e;
    private List<Vacunas> le;
    private DefaultListModel estudios;
    private int actividad;

    public VacunasCtrl(int idactividad) {

        actividad = idactividad;
        result = null;
        this.logger = Sesion.getLogger();

        panel = new PanelAdicionales();
        estudios = new DefaultListModel();
        cargarEstudios();

        panel.setModel(estudios);
        panel.setVisible(true);

        
    }

    private void cargarEstudios() {

        le = (List<Vacunas>) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarVacunas", 0);

        for(Vacunas est : le) {

            JCheckBox jbc = new JCheckBox();
            jbc.setText(est.getDescripcion());
            jbc.setVisible(true);
            estudios.addElement(jbc);
        }
    }

    public List<Vacunas> obtenerEstudiosElegidos() {

        ArrayList <Vacunas> resp = new ArrayList<Vacunas>();


        ArrayList<Integer> j = panel.getItemsElegidos();
        for(Integer i : j) {
            
            resp.add((Vacunas) (le.get(i)));
        }
        return(resp);
    }

    public void setEpisodio(Episodios epi) {

        e = epi;
    }

    @Override
    public void guardarDatos() throws Exception {

    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void cerrarTodo() {
         panel.setVisible(false);
    }

    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void vaciarDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
