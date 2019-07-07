/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.examenes;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Estudios;
import view.PanelAdicionales;

/**
 *
 * @author rupertus
 */
public class ExamenesAdicionalesCtrl extends AbstractVisualController {

    private PanelAdicionales panel;
    private Logger logger;
    private Object result;
    private Episodios e;
    private List<Estudios> le;
    private DefaultListModel estudios;
    private int actividad;

    public ExamenesAdicionalesCtrl(int idactividad) {

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

        List<Estudios> temp = (List<Estudios>) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarEstudiosALL", 0);
        le = new ArrayList<Estudios>();



        for(Estudios est : temp) {

            if(
               (est.getId()!= 6) && (est.getId()!= 99) && (est.getId()!= 20) && (est.getId()!= 100) &&
               (est.getId()!= 2) && (est.getId()!= 23) && (est.getId()!= 19) &&
               (est.getId()!= 29) && (est.getId()!= 18)
               )
                {
                    JCheckBox jbc = new JCheckBox();
                    jbc.setText(est.getDescripcion());
                    jbc.setVisible(true);
                    estudios.addElement(jbc);
                    le.add(est);
                }
        }
    }

    public List<Estudios> obtenerEstudiosElegidos() {

        ArrayList <Estudios> resp = new ArrayList<Estudios>();


        ArrayList<Integer> j = panel.getItemsElegidos();
        for(Integer i : j) {
            
            resp.add((Estudios) (le.get(i)));
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

    @Override
    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void vaciarDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
