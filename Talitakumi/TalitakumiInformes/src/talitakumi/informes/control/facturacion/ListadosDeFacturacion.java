/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.control.facturacion;

import talitakumi.informes.control.estadisticas.*;
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
import talitakumi.informes.control.FacturacionDeServiciosProfesionales;
import talitakumi.informes.control.ListadoDeServiciosProfesionales;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Procedencias;

/**
 *
 * @author rupertus
 */

public class ListadosDeFacturacion {

    UIInformes uic;
    Logger logger;
    private DataParameter dp;
    private List<Clientes> procedencias;
    private List<Procedencias> academias;


    public ListadosDeFacturacion() {

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
       int cliente, desde, hasta, aca, adesde, ahasta, emisor;
       String tipo_factura, uiemisor;

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

             uiemisor = uic.getUiemisor();

       if(uiemisor.equals("Gaston Moratorio"))
           emisor = 1;
       else
           if(uiemisor.equals("Raul Barañano"))
               emisor = 2;
           else
               if(uiemisor.equals("Medicina Laboral"))
                   emisor = 3;
               else
                   emisor = 0;

       tipo_factura = uic.getTipoPago();

       if(tipo_factura.equals("CREDITO"))
           tipo_factura = "R" ;
       else
           if(tipo_factura.equals("CONTADO"))
               tipo_factura = "C" ;
           else
               tipo_factura = "T";



       dp.set("fechadesde", fechadesde);
       dp.set("fechahasta", fechahasta);
       dp.set("cliente_desde", desde);
       dp.set("cliente_hasta", hasta);
       dp.set("tipo_factura", tipo_factura);
       dp.set("emisor", emisor);


       if(tarea.equals("Listado de Servicios Profesionales")) {
            ListadoDeServiciosProfesionales listadoDeServicios = new ListadoDeServiciosProfesionales(fechadesde, fechahasta, dp);
       }

       if(tarea.equals("Listados de vencimiento")) {
            ListadosDeVencimiento listadoDeServicios = new ListadosDeVencimiento();
       }
       if(tarea.equals("Facturación de Servicios Profesionales")) {
            FacturacionDeServiciosProfesionales listadoDeServicios = new FacturacionDeServiciosProfesionales(fechadesde, fechahasta, dp);
       }

       if(tarea.equals("Facturación automatica de servicios")) {
            FacturacionServicios listadoDeServicios = new FacturacionServicios(fechadesde, fechahasta, dp);
       }

       if(tarea.equals("Sobres de facturacion")) {
            SobresDeFacturacion listadoDeServicios = new SobresDeFacturacion(fechadesde, fechahasta, dp);
       }

       if(tarea.equals("Generar facturas de contratos")) {

           GenerarFacturasContratos gfc = new GenerarFacturasContratos(fechadesde, fechahasta, desde, hasta);

           gfc.doTheMagic();
       }
       if(tarea.equals("Padron clientes")) {

           ListadoPadronClientes lpc = new ListadoPadronClientes();
           
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
                desc = "Listado de Servicios Profesionales";
                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Facturación de Servicios Profesionales";
                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Facturación automatica de servicios";
                dftm.addRow(new Object[]{actividad, desc});
                
                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Sobres de facturacion";
                dftm.addRow(new Object[]{actividad, desc});


                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Listados de vencimiento";
                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Generar facturas de contratos";
                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Padron Clientes";
                dftm.addRow(new Object[]{actividad, desc});


            uic.setModeloTabla(dftm);
            uic.setAnchoColumna(0, 64);
            uic.setAnchoColumna(1, 500);
   }

}
