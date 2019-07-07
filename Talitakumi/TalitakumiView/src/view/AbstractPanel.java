/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 *
 * @author rupertus
 */
public abstract class AbstractPanel extends JPanel{

    public abstract void setGuardarListener(ActionListener al);
    public abstract void setCancelarListener(ActionListener al);
}
