/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.control.medicos;

import objetosreporte.PendientesCarnet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import talitakumi.engine.framework.DataParameter;
import talitakumi.engine.framework.invoker.sesionweb.ClientSesion;
import talitakumi.informes.IReporte;

/**
 *
 * @author rupertus
 */
public class InformeDeHCOdontologica implements IReporte{

    private Integer episodio;
    ArrayList<PendientesCarnet> lps = new ArrayList<PendientesCarnet>();

    public void listar(Integer epsiodio) {

        this.episodio = episodio;
        //new Reporter("diagnosticosanitario.jasper").listar(hm);
    }

    @Override
    public List createBeanCollection() {
        List lm = new ArrayList();

        lm = getCollection1();

        return(lm);
    }

    private List getCollection1() {

        List lm = new ArrayList<PendientesCarnet>();
        DataParameter dp = new DataParameter();
        dp.set("episodio", episodio);
        try {
            lm = (List) ClientSesion.getInvoker().invoke("CargarEpisodiosPorRango", dp);
        } catch (Exception ex) {
            System.out.println("Error InformeDeHCOdontologica " + ex);
            Logger.getLogger(InformeDeHCOdontologica.class.getName()).log(Level.SEVERE, null, ex);
        }

        return(lm);
    }
    
}
