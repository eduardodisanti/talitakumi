/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.helpers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Vector;

/**
 *
 * @author rupertus
 */
public class InformeLocator {

    private String clave, titulo, archivo;
    private Vector<ParametroInforme> parametros;

    public InformeLocator(String nombreinforme) throws FileNotFoundException {

        FileReader fr = new FileReader(nombreinforme);
        

    }
}
