/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.control;

import ctrl.RecepcionCtrl;
import ctrl.TelefonistaCtrl;
import talitakumi.informes.control.InformesController;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Vector;
import javax.naming.InitialContext;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;  //import talitakumi.administracion.ctrl.AdminController;
import talitakumi.cajas.ctrl.CajaController;
import talitakumi.engine.framework.invoker.ServiceDelegatorRemote;
import talitakumi.extraccionistas.ctrl.PuestoExtraccionistaCtrl;
import talitakumi.laboratorio.ctrl.LaboratorioController;
import talitakumi.medicos.ctrl.ConsultorioCtrl;
import talitakumi.odontologo.ctrl.OdontologoController;
import talitakumi.supervisionmedica.ctrl.SupervisionMedicaCtrl;
import talitakumi.utilidades.ctrl.UtilidadesController;
import talitakumiadmin.administracion.ctrl.AdminController;
import view.DialogoInformacion;
import view.FrameEleccionTareas;
import view.UISplash;

/**
 *
 * @author rupertus
 */
public class TalitakumiCtrl {


    FrameEleccionTareas uic;

    Logger logger;
    FileAppender appender;

    public TalitakumiCtrl() {


        UISplash dlg = new UISplash(null, false);

        
        dlg.setVisible(true);
        dlg.setCantidadTareas(5);
        dlg.setSistemas("Talitakumi");
        dlg.setVersion("1.4.5");
        
        try {
            dlg.setProgreso(1);
            dlg.setMensaje("Conectando al servidor de aplicaciones ...");
            Properties props = new Properties();
            props.load(new FileInputStream("jndi.properties"));

            InitialContext ctx = new InitialContext(props);

            ServiceDelegatorRemote sdr = (ServiceDelegatorRemote) ctx.lookup("services.ServiceDelegatorRemote");
            
            talitakumi.framework.Sesion.setServiceDelegatorRemote(sdr);
            crearLogger();
            dlg.setProgreso(2);
            dlg.setMensaje("Conectando a la unidad de persistencia ...");
            dlg.setProgreso(3);
            dlg.setProgreso(4);
            dlg.setMensaje("Iniciando interfaz grafico ...");
            uic = new FrameEleccionTareas();
            dlg.setProgreso(5);
            uic.crearTareaListener(new TareaListener());

            dlg.setMensaje("Terminado ...");
            dlg.setVisible(false);
 
            talitakumi.framework.Sesion.setLogger(logger);
            
            //org.eclipse.persistence.internal.helper.Helper.isZeroValidPrimaryKey = true;
            
            if(this.doLogin()) {

                Vector roles = (Vector) sdr.invoke("ServicioCargarRoles", talitakumi.framework.Sesion.getSingleton().getNombreUsuario());
                 //AbstractService s = new ServiceDelegator().getService("ServicioCargarRoles");
                 //s.setParameters(Sesion.getSingleton().getNombreUsuario());
                 //Vector roles = (Vector) s.invoke();
                 talitakumi.framework.Sesion.getSingleton().setRoles(roles);
                 agregarModeloTablaTareas(roles);
                 uic.setVisible(true);   
            } else {
                      salir();
              }

        } catch (Exception ex) {
                System.out.println("ex" +ex);
                ex.printStackTrace();
                dlg.setVisible(false);
                logger.error(ex);
                DialogoInformacion dlgerror = new DialogoInformacion(null, "Error", "Error al inicial", "Error", true);
                dlgerror.agregarTexto("<br><b>Error</b>"+ex.toString());
                dlgerror.setVisible(true);
        }

        dlg.dispose();
    }

    private boolean doLogin() throws Exception {

        boolean ok = false;
        LoginCtrl l = new LoginCtrl();
        ok = l.getPermission();
        talitakumi.framework.Sesion.setLogged(ok);
        
        if(ok) {
            talitakumi.framework.Sesion.setNombreUsuario(l.getUsuario());
            talitakumi.framework.Sesion.setLugarlogon(l.getLugar());
        }
        l.disponer();
        return ok;
        }

    private void salir() {

        uic.limpiar();
        uic.dispose();
    }

    


    // TODO - tiene que ser un servicio
   private void tareaElegida() {

       String tarea = uic.getTareaElegida();

        if(tarea.equals("Salir")) {

                        this.salir();
        } else
            if(tarea.equals("Consultorio")) {
                ConsultorioCtrl cct = new ConsultorioCtrl();
            } else
                if(tarea.equals("Cajeros")) {
                    talitakumi.framework.Sesion.putVariableEntorno("Cajeros", true);
                    RecepcionCtrl cct = new RecepcionCtrl();
                } else
                    if(tarea.equals("Recepcion")) {
                        RecepcionCtrl cct = new RecepcionCtrl();
                    } else
                       if(tarea.equals("Administracion del sistema")) {

                            AdminController cct = new AdminController();
                       } else 
                           if(tarea.equals("Informes")) {

                            InformesController cct = new InformesController();
                       } else
                           if(tarea.equals("Caja")) {
                            CajaController cct = new CajaController();
                       } else
                           if(tarea.equals("Laboratorio")) {
                            LaboratorioController cct = new LaboratorioController();
                       } else
                           if(tarea.equals("Odontologo")) {
                            OdontologoController cct = new OdontologoController();
                       } else
                             if(tarea.equals("Extraccionista")) {
                                    PuestoExtraccionistaCtrl cct = new PuestoExtraccionistaCtrl();
                       } else
                             if(tarea.equals("Telefonistas")) {
                                    TelefonistaCtrl cct = new TelefonistaCtrl();
                           } else
                             if(tarea.equals("Servicios externos")) {
                                    UtilidadesController cct = new UtilidadesController();
                           } else
                             if(tarea.equals("Supervisión Médica")) {
                                    SupervisionMedicaCtrl cct = new SupervisionMedicaCtrl();
                           }
   }

    private void crearLogger() {
        logger = Logger.getLogger(TalitakumiCtrl.class);
        logger.setLevel((Level)Level.WARN);
        appender = null;
          try {
             appender = new FileAppender(new PatternLayout(),"talitakumi.log");
             logger.addAppender(appender);
          } catch(Exception e) {}
    }

   public class TareaListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent arg0) {

            tareaElegida();
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

   private void agregarModeloTablaTareas(Vector roles) {

       int largo = roles.size();
            DefaultTableModel dftm = new DefaultTableModel();
            dftm.addColumn("");
            dftm.addColumn("Menu");

            for(int i=0; i < largo; i++) {

                ImageIcon actividad = null;
                String desc = "";

                String xtarea = (String) roles.elementAt(i);
                
                switch (xtarea.charAt(0)) {

                    case '1' :  actividad = new ImageIcon(getClass().getResource("/imagenes/bookmark.png"));
                              desc = "Consultorio";
                              break;

                    case '2' :  actividad = new ImageIcon(getClass().getResource("/imagenes/personal.png"));
                              desc = "Recepcion";
                              break;

                    case '4' :  actividad = new ImageIcon(getClass().getResource("/imagenes/informes.png"));
                              desc = "Informes";
                              break;

                    case '5' :  actividad = new ImageIcon(getClass().getResource("/imagenes/cajas.jpg"));
                              desc = "Caja";
                              break;

                    case '3' :  actividad = new ImageIcon(getClass().getResource("/imagenes/configure.png"));
                              desc = "Administracion del sistema";
                              break;

                    case '6' :  actividad = new ImageIcon(getClass().getResource("/imagenes/laboratoriomini.png"));
                              desc = "Laboratorio";
                              break;

                    case '7' :  actividad = new ImageIcon(getClass().getResource("/imagenes/odontologomini.png"));
                              desc = "Odontologo";
                              break;
                    case 'C' :  actividad = new ImageIcon(getClass().getResource("/imagenes/personal.png"));
                              desc = "Cajeros";
                              break;
                    case 'E' :  actividad = new ImageIcon(getClass().getResource("/imagenes/extraccionistamini.png"));
                              desc = "Extraccionista";
                              break;
                    case 'T' :  actividad = new ImageIcon(getClass().getResource("/imagenes/telefonistamini.png"));
                              desc = "Telefonistas";
                              break;

                    case 'U' :  actividad = new ImageIcon(getClass().getResource("/imagenes/externosmini.png"));
                              desc = "Servicios externos";
                              break;

                    case 'S' :  actividad = new ImageIcon(getClass().getResource("/imagenes/make.png"));
                              desc = "Supervisión Médica";
                              break;


                }
                if(!desc.equals("")) {
                    dftm.addRow(new Object[]{actividad, desc});
                }
            }

            ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/clave.png"));
            dftm.addRow(new Object[]{icono, "Cambiar clave"});
            icono = new ImageIcon(getClass().getResource("/imagenes/miniexit.png"));
            dftm.addRow(new Object[]{icono, "Salir"});
            uic.setModeloTabla(dftm);
            uic.setAnchoColumna(0, 64);
            uic.setAnchoColumna(1, 500);
   }
}
