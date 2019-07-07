/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.framework.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import talitakumi.framework.Sesion;

/**
 *
 * @author rupertus
 */
public abstract class AbstractVisualController {
    
    abstract public void guardarDatos() throws Exception;
    abstract public JPanel getPanel();
    abstract public void cerrarTodo();
    abstract public void vaciarDatos();

    public abstract void setActividad(String descripcion);
    
    public void setGuardarListener(ActionListener almacenarListener) {

        
    }

    public class AlmacenarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            try {
                guardarDatos();
            } catch (Exception ex) {
                System.err.println("Error " + ex);
                Sesion.getLogger().error(ex);
              }
        }
    }

    public class CancelarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            cerrarTodo();
        }
    }
    
    public class NuevoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            vaciarDatos();
        }
    }
}
