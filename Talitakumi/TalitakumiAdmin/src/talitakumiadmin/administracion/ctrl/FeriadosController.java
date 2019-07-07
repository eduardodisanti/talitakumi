/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumiadmin.administracion.ctrl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.model.core.Feriados;
import talitakumi.model.core.Moldeagenda;
import talitakumi.model.core.Tipodeactividad;
import talitakumiadmin.administracion.ui.UIFeriadosController;

/**
 *
 * @author rupertus
 */
class FeriadosController extends AbstractVisualController {

    private UIFeriadosController uilp;
    private boolean isLoaded;
    
    public FeriadosController() {

        uilp = new UIFeriadosController();
        uilp.addAlmacenarListener(new AlmacenarListener());
        uilp.addCerrarListener(new CancelarListener());
        uilp.crearCalendarioListener1(new CalendarioCambiardoListener());

        isLoaded = false;
        uilp.setVisible(true);
        isLoaded = true;

        try {
            uilp.setFecha(new Date());
            cargarFeriados();
        } catch (Exception ex) {
            Logger.getLogger(FeriadosController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error MoldeAgendaController " + ex);
        }
    }

   private void cargarFeriados() {

       Collection<Feriados> cl = (Collection<Feriados>) Sesion.getServiceDelegatorRemote().invoke("CargarFeriados", new Date());


       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

       DefaultTableModel dftm = new DefaultTableModel();
       dftm.addColumn("Fecha");
       for(Feriados f : cl) {

            dftm.addRow(new Object[]{sdf.format(f.getFecha())});
       }

       uilp.setModeloFeriados(dftm);
   }

    @Override
    public void vaciarDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
   public class CalendarioCambiardoListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent pce) {
            if(isLoaded)
                fechaElegida();
        }
   }

   private void fechaElegida() {

       Date fecha = uilp.getFecha();
       uilp.setFecha(fecha);

   }

    @Override
    public void guardarDatos() throws Exception {

        Date fecha = uilp.getFecha();
        Sesion.getServiceDelegatorRemote().invoke("MarcarFeriado", fecha);
        cargarFeriados();
    }


    @Override
    public JPanel getPanel() {
        return(uilp);
    }

    @Override
    public void cerrarTodo() {
        uilp.setVisible(false);
    }

    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Moldeagenda crearNuevoMoldeAgenda(Integer hora, Integer minutos, Integer duracion, Integer lugares, Tipodeactividad tipoactividad) {

        Moldeagenda max = new Moldeagenda();
        max.setHora(hora);
        max.setMinutos(minutos);
        max.setDuracion(duracion);
        max.setLugares(lugares);
        max.setConsultorio("-");
        max.setTurno(0);
        max.setServicio(tipoactividad);

        return(max);
    }
}
