/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.medicos.ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.logging.Level;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.medicos.ui.UIVacunas;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.EpisodiosVacunasAdicionales;
import talitakumi.model.core.Personas;

/**
 *
 * @author victoria
 */

public class VacunasCtrl extends AbstractVisualController {

    private Logger logger;
    private UIVacunas uiv1;
    private HeartBeat hb;
    private Agenda a;


    public VacunasCtrl(Agenda a) {

        this.a = a;
        Personas p = a.getEpisodio().getPaciente().getPersonas();
        int episodio = a.getEpisodio().getId();

        logger = Sesion.getLogger();

        uiv1 = new UIVacunas(episodio);

        p = a.getEpisodio().getPaciente().getPersonas();

        System.out.println("Empieza a setear datos de la persona");
        uiv1.setNombre(p.getNombres());
        System.out.println("NOMBRE : " +p.getNombres());
        uiv1.setApellidos(p.getApellidos());
        uiv1.setSexo(p.getSexo());
        uiv1.setDocumento(p.getDocumento());
        uiv1.setDomicilio(p.getDomicilio());
        uiv1.setCiudad(p.getCiudad());
        uiv1.setTelefono(p.getTelefono());
        uiv1.setCelular(p.getMovil());
        uiv1.setFechanacimiento(p.getFechanacimiento());
        uiv1.setEdad(p.getFechanacimiento());
        uiv1.setVacunasListener(new VacunasListener());
        AgregarVacunas(episodio);
        uiv1.setVisible(true);
        
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
    
    private void cargarVacunaElegida() {

       Integer ve = uiv1.getVacunaElegida();

    }


    @Override
    public void guardarDatos() throws Exception {

        // TODO - VER COMO HACER!!. ESTA AGENDA NO ES CONOCIDA. (VICTORIA)
        a.getEpisodio().setTerminado('S');
        Sesion.getServiceDelegatorRemote().invoke("ServicioModificarAgenda", a);
    }

    @Override
    public JPanel getPanel() {
        return(uiv1);
    }

    @Override
    public void cerrarTodo() {
        cerrarFrame();
    }

    @Override
    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
   

    public class VacunasListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            cargarVacunaElegida();
        }
    }

     
    @Override
    public void setGuardarListener(ActionListener al) {

        uiv1.setGuardarListener(al);

        try {
              guardarDatos();
            
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(VacunasCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void AgregarVacunas(int episodio){

         Collection<EpisodiosVacunasAdicionales> lista2 = null;

         lista2 = (Collection) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodiosVacunasAdicionalesPorEpisodio" , episodio);

         for(EpisodiosVacunasAdicionales epivac : lista2){
             int episodio_vacuna = epivac.getVacuna().getId();
             String descripcion_vacuna = epivac.getVacuna().getDescripcion();

             uiv1.agregarVacuna(descripcion_vacuna, episodio_vacuna);
         }
    }
   
   private void cerrarFrame() {

            hb.parar();
            uiv1.setVisible(false);
            uiv1.dispose();
   }
   
  }
