/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.control;

import talitakumi.informes.control.facturacion.ListadoLibretasPorCliente;
import talitakumi.informes.control.estadisticas.Estadisticaldc1Ctrl;
import talitakumi.informes.control.facturacion.ListadoLibretasPorAcademiaFact;
import talitakumi.informes.control.facturacion.CarnetxprocedenciafactcredCtrl;
import talitakumi.informes.control.facturacion.CarnetxprocedenciafactCtrl;
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
import talitakumi.informes.control.caja.ListadosDeCajaCtrl;
import talitakumi.informes.control.estadisticas.EstadisticaDeServicios;
import talitakumi.informes.control.estadisticas.ListadosDeVencimiento;
import talitakumi.informes.control.facturacion.LPRxprocedenciafactcredCtrl;
import talitakumi.informes.control.facturacion.ListadoLibretasPorAcademia;
import talitakumi.informes.control.facturacion.ListadosDeFacturacion;
import talitakumi.informes.control.mantenimiento.ListadosDeMantenimientoCtrl;
import talitakumi.informes.control.medicos.ListadosDiagnosticosMedicosCtrl;
import talitakumi.laboratorio.ctrl.BuscarEstudioController;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Procedencias;


/**
 *
 * @author rupertus
 */

public class InformesController {

    private UIInformes uic;
    private Logger logger;
    private DataParameter dp;
    private List<Clientes> procedencias;
    private List<Procedencias> academias;
    

    public InformesController() {

        logger = Sesion.getLogger();
        uic = new UIInformes();
        uic.crearInformeListener(new InformeListener());
        this.agregarModeloTablaInformes();
        uic.setVisible(true);

        llenarClientes();
        llenarAcademias();
       
    }

       private void informeElegido () throws Exception {

       String tarea = uic.getTareaElegida();

       String tipo_factura, uiemisor;
       Date fechadesde, fechahasta;
       int cliente, desde, hasta, aca, adesde, ahasta, emisor;

       
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


       

       dp.set("cliente_desde", desde);
       dp.set("cliente_hasta", hasta);
       dp.set("academiadesde", adesde);
       dp.set("academiahasta", ahasta);
       dp.set("fechadesde", fechadesde);
       dp.set("fechahasta", fechahasta);
       dp.set("tipo_factura",tipo_factura );
       dp.set("emisor", emisor);

       

      
       if(tarea.equals("Liquidacion libretas de conducir por academia")) {
           ListadoLibretasPorAcademia l = new ListadoLibretasPorAcademia(fechadesde, fechahasta, dp);
       }

// LISTADO DE PARTICULARES NO PAGOS NO ESTA EN EL DIRECTORIO.

//       if(tarea.equals("Listado de particulares no pagos")) {
//           ListadoParticularesNoPagosCtrl l = new ListadoParticularesNoPagosCtrl(fechadesde, fechahasta, dp);
//       }


       if(tarea.equals("Listado Para Contador")) {
            ListadoContador ListadoContador = new ListadoContador(fechadesde, fechahasta, dp);
       }

       if(tarea.equals("Consulta x paciente")) {
           //ModificarEpisodioADMCtrl mcac = new ModificarEpisodioADMCtrl();
       }

       if(tarea.equals("Listado CJP")) {

           ListadoCJP l = new ListadoCJP(fechadesde, fechahasta, dp);
       }

       if(tarea.equals("Listado de actos x dia")) {
           ListadoActosDiaCtrl l = new ListadoActosDiaCtrl(fechadesde, fechahasta, dp);
       }

      
       if(tarea.equals("Imprimir examenes de laboratorio")) {
            BuscarEstudioController // <editor-fold defaultstate="collapsed" desc="comment">
                    buscarEstudioController// </editor-fold>
 = new BuscarEstudioController();
       }

       if(tarea.equals("Libretas de conducir por academia y tipo de pago")) {
            ListadoLibretasPorAcademiaFact listadoLibretasPorAcademiaFact = new ListadoLibretasPorAcademiaFact(fechadesde, fechahasta, dp);
       }

       if(tarea.equals("Carne de salud emitidos entre fecha por procedencia")) {
            CarnetxprocedenciafactCtrl carnetxprocedenciafactCtrl = new CarnetxprocedenciafactCtrl(fechadesde, fechahasta, dp);
       }

       if(tarea.equals("Facturacion de carnet de salud")) {
            CarnetxprocedenciafactcredCtrl carnetxprocedenciafactcredCtrl = new CarnetxprocedenciafactcredCtrl(fechadesde, fechahasta, dp);
       }

       if(tarea.equals("Listado de ventas contado")) {
            ListadodeventascontadoCtrl listadodeventascontadoCtrl = new ListadodeventascontadoCtrl(fechadesde, fechahasta, dp);
       }

       if(tarea.equals("Estadistica de Libretas de conducir")) {
            Estadisticaldc1Ctrl estadisticaldc1Ctrl = new Estadisticaldc1Ctrl(fechadesde, fechahasta, dp);
       }

       if(tarea.equals("Estadistica de servicios")) {
            EstadisticaDeServicios estadisticaDeServicios = new EstadisticaDeServicios(fechadesde, fechahasta, dp);
       }

       if(tarea.equals("Listados de mantenimiento")) {
            ListadosDeMantenimientoCtrl listadosDeMantenimientoCtrl = new ListadosDeMantenimientoCtrl();
       }

       if(tarea.equals("Informes y listados medicos")) {
            ListadosDiagnosticosMedicosCtrl listadosDiagnosticosMedicosCtrl = new ListadosDiagnosticosMedicosCtrl();
       }

       if(tarea.equals("Informes de caja")) {
            ListadosDeCajaCtrl listadosDeCajaCtrl = new ListadosDeCajaCtrl();
       }

       if(tarea.equals("Listado Libretas de conducir por cliente")) {
            ListadoLibretasPorCliente listadoLibretasPorCliente = new ListadoLibretasPorCliente(fechadesde, fechahasta, dp);
       }

       if(tarea.equals("Facturacion de Libretas profesionales")) {
            LPRxprocedenciafactcredCtrl lPRxprocedenciafactcredCtrl = new LPRxprocedenciafactcredCtrl(fechadesde, fechahasta, dp);
       }

       if(tarea.equals("Supervision Administrativa")) {
            ListadoControlMarisaCtrl listadoControlMarisaCtrl = new ListadoControlMarisaCtrl(fechadesde, fechahasta, dp);
       }

       if(tarea.equals("CarneSaludTipoProcedencia")) {
                 
            CarneSaludTipoProcedencia CarneSaludTipoProcedencia = new CarneSaludTipoProcedencia(fechadesde, fechahasta, dp);
       }

       if(tarea.equals("LibretaProfesional")) {

            LibretaProfesional LibretaProfesional = new LibretaProfesional(fechadesde, fechahasta, dp);
       }

       if(tarea.equals("LibretasAmateurPorTipo")) {

            LibretasAmateurPorTipo LibretasAmateurPorTipo = new LibretasAmateurPorTipo(fechadesde, fechahasta, dp);
       }

       if(tarea.equals("LibretasAmateurPorProcedencia")) {

            LibretasAmateurPorProcedencia LibretasAmateurPorProcedencia = new LibretasAmateurPorProcedencia(fechadesde, fechahasta, dp);
       }

       if(tarea.equals("Listados de vencimiento")) {

            ListadosDeVencimiento ldv = new ListadosDeVencimiento();
       }

       if(tarea.equals("Listados de facturación")) {

            ListadosDeFacturacion ldv = new ListadosDeFacturacion();
       }


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

    private void llenarAcademias() {
        academias = (List) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarProcedencias", null);

        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();

        dcbm.addElement("--TODAS--");
        for(Procedencias p : academias) {

            dcbm.addElement(p.getDescripcion());
        }

        uic.setModeloAcademias(dcbm);
    }


 public class InformeListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent arg0) {

            try {
                informeElegido();
            } catch(Exception ex) {
                // TODO - Tratar
            }
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
                desc = "Listado Para Contador";
                dftm.addRow(new Object[]{actividad, desc});


                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Listado CJP";
                dftm.addRow(new Object[]{actividad, desc});

                
                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Listado de actos x dia";
                dftm.addRow(new Object[]{actividad, desc});

//LISTADO DE PARTICULARES NO PAGOS NO ESTA EN EL DIRECTORIO.

//                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
//                desc = "Listado de particulares no pagos";
//                dftm.addRow(new Object[]{actividad, desc});
            
                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Liquidacion libretas de conducir por academia";
                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Listado Libretas de conducir por cliente";
                dftm.addRow(new Object[]{actividad, desc});

//                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
//                desc = "Libretas de conducir por academia y tipo de pago";
//                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Estadistica de Libretas de conducir";
                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Carne de salud emitidos entre fecha por procedencia";
                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Estadistica de servicios";
                dftm.addRow(new Object[]{actividad, desc});

//                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
//                desc = "Facturacion de carnet de salud";
//                dftm.addRow(new Object[]{actividad, desc});

//                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
//                desc = "Facturacion de Libretas profesionales";
//                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Listado de ventas contado";
                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/laboratoriomini.png"));
                desc = "Imprimir examenes de laboratorio";
                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Listados de mantenimiento";
                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Informes y listados medicos";
                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Informes de caja";
                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Supervision Administrativa";
                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Listados de facturación";
                dftm.addRow(new Object[]{actividad, desc});


//                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
//                desc = "CarneSaludTipoProcedencia";
//                dftm.addRow(new Object[]{actividad, desc});

//                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
//                desc = "LibretaProfesional";
//                dftm.addRow(new Object[]{actividad, desc});
                
//                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
//                desc = "LibretasAmateurPorTipo";
//                dftm.addRow(new Object[]{actividad, desc});
//
//                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
//                desc = "LibretasAmateurPorProcedencia";
//                dftm.addRow(new Object[]{actividad, desc});

                
            uic.setModeloTabla(dftm);
            uic.setAnchoColumna(0, 64);
            uic.setAnchoColumna(1, 500);
  
  }

}
