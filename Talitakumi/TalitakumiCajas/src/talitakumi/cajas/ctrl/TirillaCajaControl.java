package talitakumi.cajas.ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;
import talitakumi.cajas.ui.UITirillaCaja;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.model.core.Facturas;
import talitakumi.model.core.Personas;
import view.DialogoError;

/**
 *
 * @author rupertus
 */
public class TirillaCajaControl extends AbstractVisualController {

    private UITirillaCaja uif;
    private Logger logger;
    private Date fecha;
    private DefaultTableModel dftm;

    public TirillaCajaControl() {

        this.logger = Sesion.getLogger();
        fecha = new Date();

        uif = new UITirillaCaja(null, false);
        uif.setFechaListener(new FechaListener());
        uif.setCerrarListener(new CerrarListener());
        uif.setAnularListener(new AnularListener());
        cargarCaja();
    }

    private void anularFactura() {

        Integer idfactura = uif.getFacturaActual();
        if(idfactura != null) {

            Integer id = (Integer) dftm.getValueAt(idfactura, 8);
            Character xtipo = (Character) dftm.getValueAt(idfactura, 5);

            if(xtipo.equals('R')) {
                 Boolean ok = (Boolean) Sesion.getServiceDelegatorRemote().invoke("ServicioAnularFactura", id);
                 cargarCaja();
            } else {
                     //Boolean ok = (Boolean) Sesion.getServiceDelegatorRemote().invoke("ServicioAnularFactura", id);
                     //cargarCaja();
                     new DialogoError(null, "Error", "No es posible anular contado", "NO SE PUEDE ANULAR UN TICKET DE CONTAADO", true).setVisible(true);
                   }
        }

    }

    private void cargarCaja() {

        dftm = new DefaultTableModel();
        dftm.addColumn("Paciente");
        dftm.addColumn("Rollo");
        dftm.addColumn("Numero");
        dftm.addColumn("Importe");
        dftm.addColumn("Descuentos");
        dftm.addColumn("Tipo");
        dftm.addColumn("Comentarios");
        dftm.addColumn("id");
        dftm.addColumn("idF");

        HashMap hm = new HashMap();
        hm.put("fechadesde", fecha);
        hm.put("fechahasta", fecha);
        hm.put("entregado",'S');
        List<Facturas> lf = (List<Facturas>) Sesion.getServiceDelegatorRemote().invoke("CargarFacturas", hm);

        for(Facturas f : lf) {
            String nombre = "";
            Personas p = f.getEpisodio().getPaciente().getPersonas();

            nombre = p.getApellidos() + " " + p.getNombres();

            dftm.addRow(new Object[]{nombre, f.getRollo(), f.getNumero(), f.getImporte(), f.getDescuentos(), f.getTipo(), f.getComentario(), f.getEpisodio().getId(), f.getId() });
        }

        uif.setModeloTalblaTirilla(dftm);
        uif.setVisible(true);
    }

    private void cambiarFecha() {

        fecha = uif.getFecha();
        cargarCaja();
    }

    @Override
    public void guardarDatos() throws Exception {

    }

    @Override
    public JPanel getPanel() {

        return null;
    }

    @Override
    public void cerrarTodo() {
         uif.setVisible(false);
    }

    
    @Override
    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void vaciarDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public class FechaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            cambiarFecha();
        }
   }

    public class AnularListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            anularFactura();
        }
   }

    public class CerrarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            cerrarTodo();
        }
   }
}
