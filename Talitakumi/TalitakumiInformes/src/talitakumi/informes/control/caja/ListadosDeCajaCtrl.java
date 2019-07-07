/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.control.caja;

import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;
import talitakumi.informes.ui.UIInformes;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import talitakumi.framework.Sesion;

/**
 *
 * @author rupertus
 */
public class ListadosDeCajaCtrl {

    UIInformes uic;
    Logger logger;


    public ListadosDeCajaCtrl() {

        logger = Sesion.getLogger();
        uic = new UIInformes();
        uic.crearInformeListener(new InformeListener());
        this.agregarModeloTablaInformes();
        uic.setVisible(true);
    }

       private void informeElegido() {

       String tarea = uic.getTareaElegida();
       Date fechadesde, fechahasta;

       fechadesde= uic.getFechaDesde();
       fechahasta= uic.getFechaHasta();

       if(tarea.equals("Informe diario de cajas")) {
           ListadoCajaControl l = new ListadoCajaControl(fechadesde, fechahasta);
       }

       if(tarea.equals("Listado para el contador")) {
          ListadoContadorCtrl l = new ListadoContadorCtrl(fechadesde, fechahasta);
       }

       if(tarea.equals("Listado de Pendientes de caja")) {
           ListadoPendientesCtrl l = new ListadoPendientesCtrl(fechadesde, fechahasta);
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
                desc = "Informe diario de cajas";
                dftm.addRow(new Object[]{actividad, desc});

//                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
//                desc = "Listado para el contador";
//                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Listado de Pendientes de caja";
                dftm.addRow(new Object[]{actividad, desc});

            uic.setModeloTabla(dftm);
            uic.setAnchoColumna(0, 64);
            uic.setAnchoColumna(1, 500);
   }

}
