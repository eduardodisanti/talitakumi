/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author rupertus
 */
public class FechaHoy  {

    Date fecha;

    public FechaHoy() {

        GregorianCalendar g = new GregorianCalendar();

        fecha = g.getTime();
    }

    public Date getFecha() {

        return(fecha);
    }
}
