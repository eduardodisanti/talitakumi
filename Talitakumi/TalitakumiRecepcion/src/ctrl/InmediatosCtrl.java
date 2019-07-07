/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import org.apache.log4j.Logger;
import talitakumi.framework.Sesion;
import talitakumi.model.core.Consultorios;
import talitakumi.model.core.Consultoriosactivos;
import ui.UIInmediatos;
import view.DialogoError;

/**
 *
 * @author eduardodisanti
 */
public class InmediatosCtrl {

    private Logger logger;
    private UIInmediatos uic;

    public InmediatosCtrl() {

        logger = Sesion.getLogger();
        uic = new UIInmediatos(null, true);
        uic.setConsultoriosListener(new ConsultoriosListener());
        cargarConsultorios();
        cargarConsultoriosActivos();
        uic.setVisible(true);
    }

    private void mostrarConsultoriosActivos(Collection<Consultoriosactivos> lista) {


        for(Consultoriosactivos ca : lista) {

            uic.cambiarEstadoConsultorio(ca.getConsultorio(), ca.getConsultorios().getHabilitadourgente().equals("S"));

        }
        uic.setPermitirCambiarConsultorios(esCajero());
    }

   private boolean esCajero() {

       Boolean resp = true;

       resp = (Boolean)Sesion.getVariableEntorno("Cajeros");
       if(resp==null)
           resp = false;

           resp = true;
       return(resp);
   }

    private void cambiarEstadoConsultorio(Integer ce) {

        try {

            Boolean ok = (Boolean) Sesion.getServiceDelegatorRemote().invoke("CambiarConsultorioInmediato", ce);
            if(!ok) {
                new DialogoError(null, "Error interno", "Error cambiando estado del consultorio", "El servicio devolvio false", true).setVisible(true);
            }

        } catch (Exception e) {
           new DialogoError(null, "Error interno", "Error cambiando estado del consultorio", e.getMessage(), true).setVisible(true);
           logger.error(e);
        }
    }

    private void cargarConsultoriosEnUI(Collection<Consultorios> lista) {

        String s = "";
        String h = "";
        for(Consultorios c : lista) {

            s = c.getDescripcion();
//            h = c.getHabilitadourgente();
//            if(h.equals("S"))
//                s = "[!]" + s;
//            else
//                s = "[ ]" + s;
            uic.agregarConsultorio(s, c.getId());
        }
    }

   private void cargarConsultoriosActivos() {

       Collection <Consultoriosactivos>lista;
       try {
           lista = (Collection) Sesion.getServiceDelegatorRemote().invoke("CargarConsultoriosActivos", null);
           mostrarConsultoriosActivos(lista);
       } catch (Exception e) {
           logger.error(e);
           System.out.println("Error " + e);
           new DialogoError(null, "Error interno", "Error cargando consultorios inmediatos", e.getMessage(), true).setVisible(true);
       }
   }

   private void cargarConsultorioElegido() {

       Integer ce = uic.getConsultorioElegido();

       cambiarEstadoConsultorio(ce);
   }
   
   public class ConsultoriosListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            cargarConsultorioElegido();
        }
   }

   private void cargarConsultorios() {

       Collection <Consultorios>lista;
       try {
           lista = (Collection) Sesion.getServiceDelegatorRemote().invoke("CargarListaConsultorios", null);

           cargarConsultoriosEnUI(lista);
       } catch (Exception e) {
           logger.error(e);
       }
   }
}
