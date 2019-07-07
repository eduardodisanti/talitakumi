/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.control.medicos;

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

public class ListadosDiagnosticosMedicosCtrl {

    UIInformes uic;
    Logger logger;
    private DataParameter dp;
    private List<Clientes> procedencias;
    private List<Procedencias> academias;


    public ListadosDiagnosticosMedicosCtrl() {

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


       if(tarea.equals("Diagnosticos sanitarios")) {
           DiagnosticoSanitario l = new DiagnosticoSanitario();
           l.listar(fechadesde, fechahasta);
       }

       if(tarea.equals("Consultar historia clinica")) {
           ConsultarHC l = new ConsultarHC();
           l.listar(fechadesde, fechahasta);
       }

       //NO FUNCIONA.
       if(tarea.equals("Pendientes carnet de salud")) {
           PendientesCarnetDeSalud l = new PendientesCarnetDeSalud();
           l.listar(fechadesde, fechahasta);
       }

       if(tarea.equals("Informe de HC Odontologica")) {

           InformeDeHCOdontologica l = new InformeDeHCOdontologica();
           l.listar(desde);
       }

       if(tarea.equals("Pendientes de Chequeos")) {

            ListadoPendientesChequeos PendientesChequeos = new ListadoPendientesChequeos (fechadesde, fechahasta, dp);
       }

       if(tarea.equals("Listado de Seguimiento de Chequeos")) {
           ListadoSeguimientoChequeos ListadoSeguimientoChequeos = new ListadoSeguimientoChequeos(fechadesde);
       }

       if(tarea.equals("Estadística de Libretas de conduicir para IMM")) {
           InformeLDCimm l = new InformeLDCimm(fechadesde, fechahasta);
       }

       if(tarea.equals("Listado de productividad médica")) {
           InformeProductividadMedica l = new InformeProductividadMedica(fechadesde, fechahasta);
       }

       if(tarea.equals("Listado de productividad odontológica")) {
           InformeProductividadMedica l = new InformeProductividadMedica(fechadesde, fechahasta);
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
                desc = "Diagnosticos sanitarios";
                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Consultar historia clinica";
                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Informe de HC Odontologica";
                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Pendientes Carnet de Salud";
                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Pendientes de Chequeos";
                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Listado de Seguimiento de Chequeos";
                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Estadística de Libretas de conduicir para IMM";
                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Listado de productividad médica";
                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Listado de productividad odontológica";
                dftm.addRow(new Object[]{actividad, desc});

            uic.setModeloTabla(dftm);
            uic.setAnchoColumna(0, 64);
            uic.setAnchoColumna(1, 500);
   }

}
