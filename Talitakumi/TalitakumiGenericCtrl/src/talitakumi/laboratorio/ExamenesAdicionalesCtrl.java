/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.laboratorio;

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
import talitakumi.model.core.Examenesactividad;
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
    private List<Examenesactividad> le;
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

        le = (List<Examenesactividad>) Sesion.getServiceDelegatorRemote().invoke("CargarEstudiosDeLaboratorio", actividad);
        System.out.println("CANTIDAD DE ESTUDIOS ES : " +le.size());
        for(Examenesactividad est : le) {
     
            JCheckBox jbc = new JCheckBox();
            jbc.setText(est.getExamen().getDescripcion());
            jbc.setVisible(true);
            estudios.addElement(jbc);
        }

//        JCheckBox jbc = new JCheckBox();
//        jbc.setText("PSA");
//        estudios.addElement(jbc);
//        jbc.setVisible(true);
//
//        JCheckBox jbcc = new JCheckBox();
//        jbcc.setText("VES");
//        estudios.addElement(jbcc);
//        jbcc.setVisible(true);
        

    }

    public List<Estudios> obtenerEstudiosElegidos() {

        ArrayList <Estudios> resp = new ArrayList<Estudios>();


        ArrayList<Integer> j = panel.getItemsElegidos();
        for(Integer i : j) {
            
            resp.add((Estudios) (le.get(i)).getExamen());
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
