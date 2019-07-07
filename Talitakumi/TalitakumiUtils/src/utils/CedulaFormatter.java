/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.text.ParseException;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author rupertus
 */
public class CedulaFormatter extends MaskFormatter {

    public CedulaFormatter() throws ParseException {
        super("#######-#");
    }
}
