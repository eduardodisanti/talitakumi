/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.control.estadisticas;

import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;
import talitakumi.informes.ui.UIInformes;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import talitakumi.framework.DataParameter;
import talitakumi.framework.Sesion;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Procedencias;

/**
 *
 * @author rupertus
 */

public class ListadosDeVencimiento {

    UIInformes uic;
    Logger logger;
    private DataParameter dp;
    private List<Clientes> procedencias;
    private List<Procedencias> academias;


    public ListadosDeVencimiento() {

        logger = Sesion.getLogger();
        uic = new UIInformes();
        uic.crearInformeListener(new InformeListener());
        this.agregarModeloTablaInformes();
        uic.setVisible(true);

        llenarClientes();
    }

    private void llenarClientes() {
        procedencias = (List) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarClientes", "S");

        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();

        dcbm.addElement("--TODOS--");
        for(Clientes p : procedencias) {

            dcbm.addElement(p.getNombre());
        }

        uic.setModeloProcedencias(dcbm);
    }

       private void informeElegido() {

       String tarea = uic.getTareaElegida();
       Date fechadesde, fechahasta;
       int cliente, desde, hasta, aca, adesde, ahasta;

       dp = new DataParameter();
       fechadesde= uic.getFechaDesde();
       fechahasta= uic.getFechaHasta();


       cliente = 0;
       aca = 0;
       int idx = uic.getCliente();

       if(idx != 0)
            cliente = procedencias.get( idx - 1).getId();

       if(cliente==0) {
           desde = 0;
           hasta = 999999999;
       } else
           desde = hasta = cliente;

       idx = uic.getProcedencia();
       if(idx != 0)
            aca = academias.get( idx - 1).getId();

       if(aca==0) {
           adesde = 0;
           ahasta = 999999999;
       } else
           adesde = ahasta = aca;

       dp.set("fechadesde", fechadesde);
       dp.set("fechahasta", fechahasta);
       dp.set("cliente_desde", desde);
       dp.set("cliente_hasta", hasta);


       if(tarea.equals("Vencimientos de Carne de Salud")) {
           ListadoVencimientoCarnetDeSalud l = new ListadoVencimientoCarnetDeSalud(fechadesde, fechahasta, dp);
       }

       if(tarea.equals("Vencimientos de Libretas de conducir")) {
           ListadoVencimientoLDC l = new ListadoVencimientoLDC(fechadesde, fechahasta, dp);
       }


   }


public class InformeListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent arg0) {

            informeElegido();
        }

        @Override
        public void mousePressed(MouseEvent arg0) {

        }

        @Override
        public void mouseReleased(MouseEvent arg0) {

        }

        @Override
        public void mouseEntered(MouseEvent arg0) {

        }

        @Override
        public void mouseExited(MouseEvent arg0) {

        }

   }

  private void agregarModeloTablaInformes() {


            DefaultTableModel dftm = new DefaultTableModel();
            dftm.addColumn("");
            dftm.addColumn("Tarea");


                ImageIcon actividad = null;
                String desc = "";


                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Vencimientos de Carne de Salud";
                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Vencimientos de Libretas de conducir";
                dftm.addRow(new Object[]{actividad, desc});

            uic.setModeloTabla(dftm);
            uic.setAnchoColumna(0, 64);
            uic.setAnchoColumna(1, 500);
   }

}
