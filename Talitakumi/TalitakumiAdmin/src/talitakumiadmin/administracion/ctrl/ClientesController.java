/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumiadmin.administracion.ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.model.core.Clientes;
import talitakumiadmin.administracion.ui.UIClientes;

/**
 *
 * @author rupertus
 */
class ClientesController extends AbstractVisualController {

    private UIClientes uilp;
    private List<Clientes> procedencias;
    private int cliente = 0;
    private boolean nuevo = true;
    private Clientes clienteBean;
    
    public ClientesController() {

        uilp = new UIClientes();
        uilp.addClienteListener(new ClientesListener());
        uilp.addAlmacenarListener(new AlmacenarListener());
        uilp.addNuevoListener(new NuevoListener());
        try {
            setListaProcedencias();
        } catch (Exception ex) {
            Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void nuevoCliente() {

        int newid;

        nuevo = true;

        newid = obtenerUltimoId();
        uilp.setID(newid);
        uilp.setCiudad("");
        uilp.setDepartamento("");
        uilp.setDomicilio("");
        uilp.setDomicilioFiscal("");
        uilp.setEmail("");
        uilp.setFacturar('S');
        uilp.setFacturaMismoMes('N');
        uilp.setHabilitado('S');
        uilp.setImporteContrato(0.0F);
        uilp.setNombre("");
        uilp.setObservaciones("");
        uilp.setRazonSocial("");
        uilp.setRut("");
        uilp.setTelefonos("");
        uilp.setWeb("");
    }
    private void setListaActividades(int cliente) {
     
        Clientes c = (Clientes) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarCliente", cliente);
        clienteBean = c;
        uilp.setID(c.getId());
        uilp.setCiudad(c.getCiudad());
        uilp.setDepartamento(c.getDepartamento());
        uilp.setDomicilio(c.getDomicilio());
        uilp.setDomicilioFiscal(c.getDomicilioFiscal());
        uilp.setEmail(c.getEmail());
        uilp.setFacturar(c.getFacturar());
        uilp.setFacturaMismoMes(c.getFacturarMismoMes());
        uilp.setHabilitado(c.getHabilitado());
        uilp.setImporteContrato(c.getImporteContrato());
        uilp.setNombre(c.getNombre());
        uilp.setObservaciones(c.getObservaciones());
        uilp.setRazonSocial(c.getRazonSocial());
        uilp.setRut(c.getRut());
        uilp.setTelefonos(c.getTelefonos());
        uilp.setWeb(c.getWeb());
        uilp.setEmisor(c.getEmisor());
    }

    private void clienteElegido() {

        cliente = uilp.getClienteElegido();
        setListaActividades(cliente);
        nuevo = false;
    }
    private void setListaProcedencias() throws Exception {

        DefaultTableModel dftm = new DefaultTableModel();
        dftm.addColumn("Id");
        dftm.addColumn("Cliente");
        dftm.addColumn("Activo");
        uilp.setModelListaConsultas(dftm);

        procedencias = (List<Clientes>) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarClientes", null);
        int largo = procedencias.size();
            for(int i=0; i < largo;i++) {
                Clientes p = new Clientes();

                p = procedencias.get(i);
                String nombre = p.getNombre();

                dftm.addRow(new Object[]{p.getId(), nombre, procedencias.get(i).getHabilitado()});
            }
            uilp.setAnchoColumna(0, 30);
            uilp.setAnchoColumna(1, 230);
            uilp.setAnchoColumna(0, 30);

    }

    @Override
    public void guardarDatos() throws Exception {


        Clientes c = clienteBean;
        if(c==null) {
            c = new Clientes();
        }
        c.setCiudad(uilp.getCiudad());
        c.setDepartamento(uilp.getDepartamento());
        c.setDomicilio(uilp.getDomicilio());
        c.setDomicilioFiscal(uilp.getDomicilioFiscal());
        c.setEmail(uilp.getEmail());
        c.setFacturar(uilp.getFacturar());
        c.setFacturarMismoMes(uilp.getFacturarMismoMes());
        c.setHabilitado(uilp.getHabilitado());
        c.setId(uilp.getId());
        c.setImporteContrato(uilp.getImporteContrato());
        c.setNombre(uilp.getNombre());
        c.setObservaciones(uilp.getObservaciones());
        c.setRazonSocial(uilp.getRazonSocial());
        c.setRut(uilp.getRut());
        c.setTelefonos(uilp.getTelefonos());
        c.setWeb(uilp.getWeb());
        c.setEmisor(uilp.getEmisor());

        System.out.println("Fin guardado datos");

        Sesion.getServiceDelegatorRemote().invoke("AlmacenarCliente", c);
        setListaProcedencias();
    }

    @Override
    public JPanel getPanel() {
        return(uilp);
    }

    @Override
    public void cerrarTodo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private int obtenerUltimoId() {

        int last = 0;
        for(Clientes c : procedencias) {
            
            if(c.getId() > last)
                last = c.getId();
        }

        return(++last);
    }

    @Override
    public void vaciarDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private class ClientesListener implements MouseListener {

        public void mouseClicked(MouseEvent e) {

            clienteElegido();
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }

    }

    public class NuevoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            nuevoCliente();
        }
    }
}
