/*

 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.genericontrollers.pacientes;

import com.kcreativa.livecam.QuickCam;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Date;
import java.util.List;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import talitakumi.framework.DataParameter;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Pacientes;
import talitakumi.model.core.Personas;
import view.DialogoInformacion;
import view.UIModificarPaciente;
import view.UIPaciente;
import talitakumi.servicios.ConvertPersona;

/**
 *
 * @author rupertus
 */
public class ModificarPacienteCtrl extends AbstractVisualController {

    UIModificarPaciente panel;
    Logger logger;
    List<Clientes> procedencias;
    Object result;
    QuickCam camara;
    UIPaciente uipac;

    public ModificarPacienteCtrl() {

        result = null;
        this.logger = Sesion.getLogger();
        panel = new UIModificarPaciente();
        uipac = new UIPaciente();

        uipac.agregarActivarCamaraListener(new ActivarCamaraListener());
        uipac.agregarTomarFotoListener(new TomarFotoListener());
        //uipac.setFormatoCedulaLibre();
        uipac.setFormatoCedulaUruguaya();

        panel.setVisible(true);
        uipac.setVisible(true);
        panel.setPanelUtil(uipac);

        panel.setCancelarListener(new CancelarListener());
        panel.setGuardarListener(new AlmacenarListener());
        panel.setCedulaListener(new CedulaListener());
    }
    @Override
    public void guardarDatos() throws Exception {
        int cedula = panel.getCedula();

        Date d = panel.getFechanacimiento();
        if(d == null) {
            DialogoInformacion dlg = new DialogoInformacion(panel, "Error en datos", "La fecha de nacimiento es incorrecta", "Corrijala e intentelo nuevamente", true);
            dlg.setVisible(true);
            throw new Exception();
        } else {
              //
                logger.error(new Date() + " Comienzo modificar paciente " + cedula);

              //
                Personas p = obtenerPersona(cedula);
                if(p==null) {
                   p = new Personas();
                   p.setDocumento(cedula);
                   p.setId(null);
                }

                p.setApellidos(panel.getApellidos());
                p.setCiudad(panel.getCiudad());
                p.setDocumento(cedula);
                p.setDomicilio(panel.getDomicilio());
                p.setFechanacimiento(d);
                p.setDocumento(cedula);

                p.setMovil(panel.getMovil());
                p.setNombres(panel.getNombres());
                p.setSexo(panel.getSexo());
                p.setTelefono(panel.getTelefono());

                Pacientes pac = new Pacientes();
                pac.setPersonas(p);
                pac.setId(p.getId());

                try {
                    System.out.println("Invocando ServicioGuardarPersona cedula es "+p.getDocumento());
                    result = Sesion.getServiceDelegatorRemote().invoke("ServicioGuardarPersona", p);
                }  catch (Exception ex) {
                    logger.error(ex);
                }

                String msg, info;

                if(result == null) {

                    msg = "Error interno - no se pudo agendar -";
                    info = "Error invocando ServicioGuardarPersona";
                    logger.error("Error invlocando GuardarSolicitudCarnet cedula : " + p.getDocumento() + " " + info);
                } else {
                      info = "<h2>Modificado correctamente </h2>";
                      msg = "Ok";
                      logger.error("Guardado correctamente cedula : " + p.getDocumento());
                   }
                DialogoInformacion dlg = new DialogoInformacion(panel, "Resultado de la transaccion", msg, info, true);
                dlg.setVisible(true);
                if(result!=null) {
                   panel.setVisible(false);
                   panel.dispose();
                }

        }
    }

    @Override
    public JPanel getPanel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void cerrarTodo() {
         panel.setVisible(false);
         panel.dispose();
    }

    Personas obtenerPersona(int cedula) throws Exception {

        Personas p = (Personas) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarPersona", cedula);

        return(p);
    }

    private void actuarEnPersona() {

        int cedula = panel.getCedula();
        Personas p = null;
        try {
            p = obtenerPersona(cedula);
        } catch (Exception ex) {
            logger.error(ex);
        }

        if(p!=null) {
            try {
                DataParameter dp = new DataParameter();
                ConvertPersona conv = new ConvertPersona();
                dp = conv.fromObject(p);

                panel.setPersona(dp);
            } catch(Exception e) {
                logger.error(new Date() + " " + e);
            }
        }
    }

    public void activarCamara() {

        camara = new QuickCam(uipac.getAnchoFoto(), uipac.getAltoFoto(), 0);
        uipac.getPanelFoto().add(camara);
        camara.setVisible(true);
        camara.empezar();
        uipac.setActivoBotonActivarCamara(false);
    }

    public void tomarFoto() {

        camara.pausa();
        camara.getLastImage();
        uipac.setActivoBotonActivarCamara(true);

    }

    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void vaciarDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public class ActivarCamaraListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            activarCamara();
        }

    }

    public class TomarFotoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            tomarFoto();
        }

    }

    public class CedulaListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent arg0) {

        }

        @Override
        public void focusLost(FocusEvent arg0) {

            actuarEnPersona();
        }
    }
}
