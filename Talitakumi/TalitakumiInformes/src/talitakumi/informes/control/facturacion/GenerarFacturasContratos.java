/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.control.facturacion;

import java.util.Date;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.DataParameter;
import talitakumi.framework.Sesion;
import view.DialogoInformacion;

/**
 *
 * @author eduardodisanti
 */
class GenerarFacturasContratos {

    private Date fechadesde, fechahasta;
    private int desde, hasta;
    private Logger logger;

    public GenerarFacturasContratos(Date fechadesde, Date fechahasta, int desde, int hasta) {

        this.fechadesde = fechadesde;
        this.fechahasta = fechahasta;
        this.desde = desde;
        this.hasta = hasta;

        
    }

    public void doTheMagic() {

        logger = Sesion.getLogger();
        DataParameter dp = new DataParameter();

        dp.set("desde", desde);
        dp.set("hasta", hasta);
        dp.set("fechadesde", fechadesde);
        dp.set("fechahasta", fechahasta);
        try {
            Boolean resp = (Boolean) Sesion.getServiceDelegatorRemote().invoke("ServicioGenerarFacturasContratos", dp);

            if(resp) {

                DialogoInformacion dlgerror = new DialogoInformacion(null, "Ok", "Generar facturas", "Proceso terminado", true);
                dlgerror.agregarTexto("<br><b>Las facturas se han generado correctamente</b>");
                dlgerror.setVisible(true);

            }
         } catch(Exception ex) {
             
                DialogoInformacion dlgerror = new DialogoInformacion(null, "Error", "Error generando facturas", "Error", true);
                dlgerror.agregarTexto("<br><b>Error</b>"+ex.toString());
                dlgerror.setVisible(true);
         }
    }
}
