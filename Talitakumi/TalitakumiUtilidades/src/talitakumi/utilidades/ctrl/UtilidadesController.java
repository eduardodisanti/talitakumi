/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.utilidades.ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import talitakumi.framework.controller.AbstractController;
import talitakumi.utilidades.ui.UIUtilidades;



/**
 *
 * @author rupertus
 */
public class UtilidadesController implements AbstractController {

    private UIUtilidades uia;
    public UtilidadesController() {

        uia = new UIUtilidades();
        uia.salirListener(new SalirListener());
        uia.crearItemListener(new ItemListener());
        uia.setVisible(true);
        crearMenuActividades();
    }

    public void crearMenuActividades() {

        DefaultTableModel dftm = new DefaultTableModel();
            dftm.addColumn("");
            dftm.addColumn("Servicios externos");

        ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/externosmini.png"));
        dftm.addRow(new Object[]{icono, "Chequeo Externo"});

        icono = new ImageIcon(getClass().getResource("/imagenes/monitoreomini.png"));
        dftm.addRow(new Object[]{icono, "Monitoreo biológico"});

        icono = new ImageIcon(getClass().getResource("/imagenes/cursosmini.png"));
        dftm.addRow(new Object[]{icono, "Cursos"});

        icono = new ImageIcon(getClass().getResource("/imagenes/paraclinicosmini.png"));
        dftm.addRow(new Object[]{icono, "Paraclinicos"});

        icono = new ImageIcon(getClass().getResource("/imagenes/vacunamini.png"));
        dftm.addRow(new Object[]{icono, "Vacunas"});

        icono = new ImageIcon(getClass().getResource("/imagenes/ruidomini.png"));
        dftm.addRow(new Object[]{icono, "Ruido"});

        icono = new ImageIcon(getClass().getResource("/imagenes/iluminacionmini.png"));
        dftm.addRow(new Object[]{icono, "Iluminacion"});

        icono = new ImageIcon(getClass().getResource("/imagenes/calidadairemini.png"));
        dftm.addRow(new Object[]{icono, "Calidad del aire"});

        icono = new ImageIcon(getClass().getResource("/imagenes/agentesquimicosmini.png"));
        dftm.addRow(new Object[]{icono, "Agentes quimicos"});

        icono = new ImageIcon(getClass().getResource("/imagenes/amiantomini.png"));
        dftm.addRow(new Object[]{icono, "Amianto"});

        icono = new ImageIcon(getClass().getResource("/imagenes/puestodetrabajomini.png"));
        dftm.addRow(new Object[]{icono, "Puesto de trabajo"});

        icono = new ImageIcon(getClass().getResource("/imagenes/cursosmini.png"));
        dftm.addRow(new Object[]{icono, "Consultorias"});

        uia.setModeloTareas(dftm);
        uia.setAnchoColumna(0, 48);
        uia.setAnchoColumna(1, 150);
    }

    public void salir() {

        uia.setVisible(false);
        uia.dispose();
    }

    class SalirListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            salir();
        }
    }

    private void accionElegida() {

        int opcion = uia.getOpcion();

        JPanel p = null;
        switch(opcion) {

            case 0:    try {
                                ChequeoExternoCtrl1 cec = new ChequeoExternoCtrl1();
                                //p = cec.getPanel();
                                p = null;
                        } catch(Exception ex) {}
                        break;

            case 1:    try {
                                MonitoreoBiologicoCtrl cec = new MonitoreoBiologicoCtrl();
                                //p = cec.getPanel();
                                p = null;
                        } catch(Exception ex) {}
                        break;

            case 2:    try {
                                CursosCtrl cec = new CursosCtrl();
                                //p = cec.getPanel();
                                p = null;
                        } catch(Exception ex) {}
                        break;

            case 3:    try {
                                ParaclinicosUtilidadesCtrl cec = new ParaclinicosUtilidadesCtrl();
                                //p = cec.getPanel();
                                p = null;
                        } catch(Exception ex) {}
                        break;

            case 4:    try {
                                VacunasUtilidadesCtrl cec = new VacunasUtilidadesCtrl();
                                //p = cec.getPanel();
                                p = null;
                        } catch(Exception ex) {}
                        break;

            case 5:    try {
                                RuidoCtrl cec = new RuidoCtrl();
                                //p = cec.getPanel();
                                p = null;
                        } catch(Exception ex) {}
                        break;

            case 6:    try {
                                IluminacionCtrl cec = new IluminacionCtrl();
                                //p = cec.getPanel();
                                p = null;
                        } catch(Exception ex) {}
                        break;

            case 7:    try {
                                CalidadAireCtrl cec = new CalidadAireCtrl();
                                //p = cec.getPanel();
                                p = null;
                        } catch(Exception ex) {}
                        break;

            case 8:    try {
                                AgentesQuimicosCtrl cec = new AgentesQuimicosCtrl();
                                //p = cec.getPanel();
                                p = null;
                        } catch(Exception ex) {}
                        break;

            case 9:    try {
                                AmiantoCtrl cec = new AmiantoCtrl();
                                //p = cec.getPanel();
                                p = null;
                        } catch(Exception ex) {}
                        break;

            case 10:    try {
                                PuestoDeTrabajoCtrl cec = new PuestoDeTrabajoCtrl();
                                //p = cec.getPanel();
                                p = null;
                        } catch(Exception ex) {}
                        break;

            case 11:    try {
                                ConsultoriasCtrl cec = new ConsultoriasCtrl();
                                //p = cec.getPanel();
                                p = null;
                        } catch(Exception ex) {}
                        break;
        }

        if(p!=null)
            uia.setPanelUtil(p);
        uia.setVisible(true);
        //uia.setAlwaysOnTop(true);
    }

    class ItemListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent arg0) {

            accionElegida();
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
}
