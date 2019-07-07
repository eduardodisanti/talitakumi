/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumiadmin.administracion.ctrl;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import talitakumi.engine.framework.DataParameter;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.model.core.Actividades;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Estudios;
import talitakumiadmin.administracion.ui.UIListaDePrecios;

/**
 *
 * @author rupertus
 */
class ListaDePreciosController extends AbstractVisualController {

    private UIListaDePrecios uilp;
    private List<Clientes> procedencias;
    private int cliente = 0;
    
    public ListaDePreciosController() {

        uilp = new UIListaDePrecios();
        uilp.addClienteListener(new ClientesListener());
        uilp.addAlmacenarListener(new AlmacenarListener());
        try {
            setListaProcedencias();
        } catch (Exception ex) {
            Logger.getLogger(ListaDePreciosController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setListaActividades(int cliente) {
     
        DefaultTableModel dftm = new DefaultTableModel();
        dftm.addColumn("Id");
        dftm.addColumn("T");
        dftm.addColumn("Actividad");
        dftm.addColumn("Descuento");
        uilp.setModelListaActividades(dftm);
        
        List<Actividades> actividades;
        actividades = (List<Actividades>) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarActividadesALL", 2);
        int largo = actividades.size();
            for(int i=0; i < largo;i++) {

                String actividad = "";
                Actividades p = new Actividades();

                p = actividades.get(i);
                String nombre = p.getDescripcion();
                Float preciobase = p.getPrecio();
                Float precio = null;

                precio = cargarPrecio(cliente, p, null, "A");
                if(precio == null || precio == 0f || precio.equals(0.0F))
                    precio = preciobase.floatValue();
                dftm.addRow(new Object[]{p.getId(), "A", nombre, precio+""});
            }

        List<Estudios> estudios = (List<Estudios>) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarEstudiosALL", 2);
        for(Estudios e : estudios) {

            Float precio = null;

            precio = cargarPrecio(cliente, null, e, "E");

            dftm.addRow(new Object[]{e.getId(), "E", e.getDescripcion(), precio+""});
        }

        uilp.setAnchoColumnaPrecios(0, 30);
        uilp.setAnchoColumnaPrecios(1, 10);
        uilp.setAnchoColumnaPrecios(2, 260);
        uilp.setAnchoColumnaPrecios(3, 100);
    }

    private void clienteElegido() {

        cliente = uilp.getClienteElegido();
        setListaActividades(cliente);
    }
    private void setListaProcedencias() throws Exception {

        DefaultTableModel dftm = new DefaultTableModel();
        dftm.addColumn("Id");
        dftm.addColumn("Cliente");
        uilp.setModelListaConsultas(dftm);

        procedencias = (List<Clientes>) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarClientes", "S");
        int largo = procedencias.size();
            for(int i=0; i < largo;i++) {

                String actividad = "";
                Clientes p = new Clientes();

                p = procedencias.get(i);
                String nombre = p.getNombre();

                dftm.addRow(new Object[]{p.getId(), nombre});
            }
            uilp.setAnchoColumna(0, 30);
            uilp.setAnchoColumna(1, 260);

    }

    @Override
    public void guardarDatos() throws Exception {

        int i[] = uilp.getListaClientes();
        for(int j=0;j<i.length;j++) {

            cliente = uilp.getClienteEnRow(i[j]);
            HashMap<String, Float> hm = uilp.getPrecios();

            DataParameter dp = new DataParameter();
            for(String act : hm.keySet()) {

                String [] clave = act.split("-");

                if(clave.length > 1) {
                    dp.set("cliente", cliente);
                    dp.set("actividad", Integer.parseInt(clave[0]));
                    dp.set("tipo", clave[1]);
                    dp.set("precio", hm.get(act));
                    System.out.println("Almacenando " + cliente + " - " + clave[0] + " precio=" + hm.get(act));
                    Sesion.getServiceDelegatorRemote().invoke("ServicioAlmacenarPrecioXactividad", dp);
                }
            }
        }
        System.out.println("Fin guardado datos");
    }

    @Override
    public JPanel getPanel() {
        return(uilp);
    }

    @Override
    public void cerrarTodo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private float cargarPrecio(int cliente, Actividades a, Estudios e, String tipo) {

        float precio = 0f;
        Integer act = null;
        Integer est = null;

        if(a!=null)
            act = a.getId();
        if(e!=null)
            est = e.getId();


        DataParameter dp = new DataParameter();
        dp.set("cliente", cliente);
        dp.set("actividad", act);
        dp.set("tipo", est);
        dp.set("estudio", est);
        try {

             precio = (Float) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarPrecioXactividad", dp);
        } catch(Exception ex) {

        }
        return(precio);
    }

    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
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

}
