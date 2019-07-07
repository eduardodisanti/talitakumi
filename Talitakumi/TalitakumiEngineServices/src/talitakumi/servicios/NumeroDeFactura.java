/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

/**
 *
 * @author rupertus
 */
public class NumeroDeFactura {

    private String serie;
    private int numero;

    public NumeroDeFactura(String unaSerie, int unNumero) {

        serie = unaSerie;
        numero = unNumero;

    }

    public String getSerie() {

        return(serie);
    }

    int getNumero() {
        return(numero);
    }

    public void addOne() {

        numero++;
    }
}
