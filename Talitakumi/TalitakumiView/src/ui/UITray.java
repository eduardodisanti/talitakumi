/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;

import java.awt.SystemTray;
import java.awt.TrayIcon;

/**
 *
 * @author eduardo
 */
public class UITray {

    final TrayIcon trayIcon = null;
    boolean soportado;

    public UITray() {

        soportado = SystemTray.isSupported();
    }
}
