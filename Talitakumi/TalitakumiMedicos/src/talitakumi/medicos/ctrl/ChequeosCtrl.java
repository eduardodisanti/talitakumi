package talitakumi.medicos.ctrl;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.medicos.ui.UIChequeos;
import talitakumi.medicos.ui.UICuestionarioMedico;
import talitakumi.medicos.ui.UICuestionarioMedicoSexoFemenino;
import talitakumi.medicos.ui.UIHistoriaClinicaLaboral;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Medicos;
import talitakumi.model.core.Personas;
import view.DialogoError;

/**
 *
 * @author rupertus
 */

 public class ChequeosCtrl extends AbstractVisualController  {

        private Personas p;
        private Agenda agenda;
        private Episodios epis;
        private Logger logger;
        private int pacienteElegido;
        private UIChequeos uic;
        private HeartBeat hb;
        private UICuestionarioMedico uicm;
      

    public ChequeosCtrl  (Agenda a) {

      logger = Sesion.getLogger();
      pacienteElegido = -1;

      uicm = new UICuestionarioMedico();

      p = a.getEpisodio().getPaciente().getPersonas();

      System.out.println("Empieza a setear datos de la persona");
      uicm.setNombre(p.getNombres());
      System.out.println("NOMBRE : " +p.getNombres());
      uicm.setApellido(p.getApellidos());
      uicm.setSexo(p.getSexo());
      uicm.setDocumento(p.getDocumento());
      uicm.setDomicilio(p.getDomicilio());
      uicm.setCiudad(p.getCiudad());
      uicm.setTelefono(p.getTelefono());
      uicm.setCelular(p.getMovil());
      uicm.setFechanacimiento(p.getFechanacimiento());
      uicm.setEdad(p.getFechanacimiento());

      UIHistoriaClinicaLaboral hcl = new UIHistoriaClinicaLaboral();
      UICuestionarioMedicoSexoFemenino cmsf;

      if(p.getSexo()=='F')
          cmsf = new UICuestionarioMedicoSexoFemenino();
      else
          cmsf = null;
      
      uic = new talitakumi.medicos.ui.UIChequeos(uicm, hcl, cmsf);
      uic.setVisible(true);
     
      agenda = a;
      epis = agenda.getEpisodio();
      Integer id = epis.getId();
      Episodios theepis = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio", id);
      if(theepis!=null)
         epis = theepis;

      hb = new HeartBeat();
      hb.start();

      uic.ocultarBotonAceptar(false);
    }

    @Override


    public UIChequeos getPanel() {
     
        return(uic);

    }
    @Override
    public void setGuardarListener(ActionListener al) {

        uic.setAlemacenarListener(al);
    }

    @Override
    public void guardarDatos() throws Exception {

        System.out.println("Almacenar datos");

        agenda.setEpisodio(epis);
        cargarImagenEpisodio();

        Medicos m = (Medicos) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarMedico", Sesion.getNumeromedico());
        epis.setMedico(m);

        epis.setTerminado('N');

        Boolean res = (Boolean) Sesion.getServiceDelegatorRemote().invoke("ServicioModificarAgenda", agenda);

        if(!res) {
            new DialogoError(null, "Error al almacenar", "Error almacenando", "No se pudo almacenar", true).setVisible(true);
        } else {
                    
        }

    }

    private void cargarImagenEpisodio() throws IOException {

        uic.ocultarBotonAceptar(true);
        BufferedImage img = uic.getImagenEpisodio();

        uic.ocultarBotonAceptar(false);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        ImageIO.write(img,"png",bos);

        epis.setImghistoria(bos.toByteArray());


    }

    @Override
    public void cerrarTodo() {
         cerrarFrame();
    }

     
    @Override
    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void cerrarFrame() {

            hb.parar();
            uic.setVisible(false);
            uic.dispose();
   }

    @Override
    public void vaciarDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    class HeartBeat extends Thread {
        private boolean running;

            public HeartBeat() {

            super();
            running = true;
     }

        public void parar() {

           running = false;
       }


    }
}
