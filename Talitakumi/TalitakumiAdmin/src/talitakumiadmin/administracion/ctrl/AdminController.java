/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumiadmin.administracion.ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import talitakumi.admin.UIAcademias;
import talitakumi.admin.UIConsultorios;
import talitakumi.admin.UIConsultoriosActivos;
import talitakumi.admin.UIEmisores;
import talitakumi.admin.UIEnfermedades;
import talitakumi.admin.UIMedicamentos;
import talitakumi.admin.UIPersonasADM;
import talitakumi.admin.UITiposDeActividad;
import talitakumi.admin.UIVacunas;
import talitakumi.framework.controller.AbstractController;
import talitakumiadmin.administracion.ui.UIAdministracion;



/**
 *
 * @author rupertus
 */
public class AdminController implements AbstractController {

    UIAdministracion uia;
    public AdminController() {

        uia = new UIAdministracion();
        uia.salirListener(new SalirListener());
        uia.crearItemListener(new ItemListener());
        uia.setVisible(true);
        crearMenuActividades();
    }

    public void crearMenuActividades() {

        DefaultTableModel dftm = new DefaultTableModel();
            dftm.addColumn("");
            dftm.addColumn("Mantenimiento");

        ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/configure.png"));
        dftm.addRow(new Object[]{icono, "Actividades"});

        icono = new ImageIcon(getClass().getResource("/imagenes/personal.png"));
        dftm.addRow(new Object[]{icono, "Medicos"});

        icono = new ImageIcon(getClass().getResource("/imagenes/core.png"));
        dftm.addRow(new Object[]{icono, "Enfermedades"});

        icono = new ImageIcon(getClass().getResource("/imagenes/configure.png"));
        dftm.addRow(new Object[]{icono, "Medicamentos"});

        icono = new ImageIcon(getClass().getResource("/imagenes/clave.png"));
        dftm.addRow(new Object[]{icono, "Usuarios"});

        icono = new ImageIcon(getClass().getResource("/imagenes/wizard.png"));
        dftm.addRow(new Object[]{icono, "Consultorios"});

        icono = new ImageIcon(getClass().getResource("/imagenes/wizard.png"));
        dftm.addRow(new Object[]{icono, "Personas"});

        icono = new ImageIcon(getClass().getResource("/imagenes/wizard.png"));
        dftm.addRow(new Object[]{icono, "Consultorios activos"});
        uia.setModeloTareas(dftm);

        icono = new ImageIcon(getClass().getResource("/imagenes/wizard.png"));
        dftm.addRow(new Object[]{icono, "Clientes"});
        uia.setModeloTareas(dftm);

        icono = new ImageIcon(getClass().getResource("/imagenes/wizard.png"));
        dftm.addRow(new Object[]{icono, "Academias"});
        uia.setModeloTareas(dftm);

        icono = new ImageIcon(getClass().getResource("/imagenes/wizard.png"));
        dftm.addRow(new Object[]{icono, "Tipos de actividad"});
        uia.setModeloTareas(dftm);

        icono = new ImageIcon(getClass().getResource("/imagenes/configure.png"));
        dftm.addRow(new Object[]{icono, "Lista de precios"});

        icono = new ImageIcon(getClass().getResource("/imagenes/configure.png"));
        dftm.addRow(new Object[]{icono, "Estudios"});

        icono = new ImageIcon(getClass().getResource("/imagenes/configure.png"));
        dftm.addRow(new Object[]{icono, "Vacunas"});

        icono = new ImageIcon(getClass().getResource("/imagenes/configure.png"));
        dftm.addRow(new Object[]{icono, "Molde agenda"});

        icono = new ImageIcon(getClass().getResource("/imagenes/configure.png"));
        dftm.addRow(new Object[]{icono, "Notas de agenda"});

        icono = new ImageIcon(getClass().getResource("/imagenes/configure.png"));
        dftm.addRow(new Object[]{icono, "Feriados"});

        icono = new ImageIcon(getClass().getResource("/imagenes/configure.png"));
        dftm.addRow(new Object[]{icono, "Emisores"});
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

            case 0 :    p = (new ActividadesController()).getPanel();
                        break;

            case 1 :    p = (new MedicosController()).getPanel();
                        break;

            case 2 :    p = new UIEnfermedades();
                        break;

            case 3 :    p = new UIMedicamentos();
                        break;

            case 4 :    p = (new UsuariosController()).getPanel();
                        break;

            case 5 :    p = new UIConsultorios();
                        break;

            case 6 :    p = new UIPersonasADM();
                        break;

            case 7 :    p = new UIConsultoriosActivos();
                        break;

            case 8 :    p = (new ClientesController()).getPanel();
                        break;

            case 9 :    p = (new ProcedenciasController()).getPanel();
                        break;

            case 10 :   p = new UITiposDeActividad();
                        break;

            case 11 :   p = (new ListaDePreciosController()).getPanel();
                        break;

            case 12 :   p = (new EstudiosController()).getPanel();
                        break;

            case 13 :   p = new UIVacunas();
                        break;

            case 14 :   p = (new MoldeAgendaController()).getPanel();
                        break;

            case 15 :   p = (new NotasAgendaController()).getPanel();
                        break;

            case 16 :   p = (new FeriadosController()).getPanel();
                        break;

            case 17 :   p = new UIEmisores();
                        break;
        }

        uia.setPanelUtil(p);
        uia.setVisible(true);
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
