/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.cajas.ctrl;

import talitakumi.informes.control.ListadoDiarioControl;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;
import talitakumi.cajas.ui.UICaja;
import talitakumi.framework.Sesion;

/**
 *
 * @author rupertus
 */
public class CajaController {

    UICaja uic;

    EntityManager em;
    Logger logger;
    
    public CajaController() {

        this.logger = Sesion.getLogger();

        System.out.println(Sesion.getNumerousuario());
        uic = new UICaja(null, true);
        uic.crearCajaListener(new CajaListener());
        this.agregarModeloTablaCaja();
        uic.setVisible(true);
    }

       private void informeElegido() {

       String tarea = uic.getTareaElegida();
       Date fechadesde, fechahasta;

       fechadesde= uic.getFechaDesde();
       fechahasta= uic.getFechaHasta();

       if(tarea.equals("Listado Diario")) {
           ListadoDiarioControl l = new ListadoDiarioControl(fechadesde, fechahasta, (Integer) Sesion.getVariableEntorno("numerofuncionario"));
       } else
                  if(tarea.equals("Cambiar rollo")) {
                    CambiarRolloController l = new CambiarRolloController();
       }

   }
public class CajaListener implements MouseListener {

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

  private void agregarModeloTablaCaja() {


            DefaultTableModel dftm = new DefaultTableModel();
            dftm.addColumn("");
            dftm.addColumn("Tarea");


                ImageIcon actividad = null;
                String desc = "";


                actividad = new ImageIcon(getClass().getResource("/imagenes/cajas.jpg"));
                desc = "Cambiar rollo";
                dftm.addRow(new Object[]{actividad, desc});

                actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                desc = "Listado Diario";

                dftm.addRow(new Object[]{actividad, desc});


            uic.setModeloTabla(dftm);
            uic.setAnchoColumna(0, 64);
            uic.setAnchoColumna(1, 500);
   }
}
