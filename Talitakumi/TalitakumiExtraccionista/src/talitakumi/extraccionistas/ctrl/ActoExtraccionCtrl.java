
package talitakumi.extraccionistas.ctrl;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import talitakumi.extraccionistas.ui.UIActoExtraccion;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Episodioextraccionista;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Estudios;
import talitakumi.model.core.Pacientes;
import talitakumi.model.core.Personas;
import view.DialogoError;

/**
 *
 * @author rupertus
 */

class ActoExtraccionCtrl extends AbstractVisualController {

      private UIActoExtraccion uil;
      private Agenda agenda;
      private Episodios epis;
      private List<Clientes> procedencias;
      private Vector<Estudios> estudios;
      private Personas p;
      Episodioextraccionista ee;

    public ActoExtraccionCtrl(Agenda a) throws Exception {

        uil = new UIActoExtraccion();
        uil.setVisible(true);

        p = a.getEpisodio().getPaciente().getPersonas();
        uil.setNombre(p.getNombres());
        uil.setApellidos(p.getApellidos());
        uil.setSexo(p.getSexo());
        uil.setDocumento(p.getDocumento());
        uil.setDomicilio(p.getDomicilio());
        uil.setCiudad(p.getCiudad());
        uil.setTelefono(p.getTelefono());
        uil.setMovil(p.getMovil());
        uil.setFechaNacimiento(p.getFechanacimiento());
        uil.setEdad(p.getFechanacimiento());

        agenda = a;
        epis = agenda.getEpisodio();
        Integer id = epis.getId();
        Episodios theepis = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio", id);
        if(theepis!=null) {
            epis = theepis;
        }
        a.setEpisodio(epis);

        cargarEstudios();

    }

    @Override
    public void guardarDatos() throws Exception {

        agenda.setEpisodio(epis);
        System.out.println(" AGENDA :  "  +agenda!=null);
        Boolean res = (Boolean) Sesion.getServiceDelegatorRemote().invoke("ServicioModificarAgenda", agenda); //NULL

        if(!res) {
            new DialogoError(null, "Error al almacenar", "Error almacenando", "No se pudo almacenar", true).setVisible(true);
        }
        System.out.println("resultadoo es  : " +res);


        ee = new Episodioextraccionista();
        epis.setEpisodioextraccionista(ee);
        ee.setEpisodios(epis);
        ee.setEpisodio(epis.getId());
        
        ee.setApResCompletos(uil.getApResCompletos());
        ee.setApResOrina(uil.getApResOrina());
        ee.setApResSangre(uil.getApResSangre());
        ee.setAyuno(uil.getAyuno());
        ee.setDiabetes(uil.getDiabetes());
        ee.setTraeOrina(uil.getTraeOrina());
        
        // TODO - OJIBARO hay que meterlo en el esquema
        ee.setTubo(Integer.parseInt(uil.getMuestra())); 
        Sesion.getServiceDelegatorRemote().invoke("ServicioAlmacenarEpisodio", epis);

    }

    private void cargarImagenEpisodio() throws IOException {

        BufferedImage img = uil.getImagenEpisodio();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
        ImageIO.write(img,"png",bos);
                
        epis.setImghistoria(bos.toByteArray());
    }

    @Override
    public JPanel getPanel() {
         return(uil);
    }

    @Override
    public void cerrarTodo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setGuardarListener(ActionListener al) {

        uil.setGuardarListener(al);
    }


    private int calcularEdad(Personas p) {

        int edad = 0;

        GregorianCalendar hoy = new GregorianCalendar();
        Date ffnac = p.getFechanacimiento();
        GregorianCalendar fnac = new GregorianCalendar();
        fnac.setGregorianChange(ffnac);

        long tiempo = (hoy.getTimeInMillis() - fnac.getTimeInMillis());

        edad = (int) tiempo / (24 * 60 * 60 * 1000);

        return(edad);
    }

    @Override
    public void setActividad(String descripcion) {
        uil.setServicio(descripcion);
    }

    private void cargarEstudios() {

        Integer cod = epis.getTipoepisodio().getId();

        Vector<Estudios> estnormales =  (Vector<Estudios>) Sesion.getServiceDelegatorRemote().invoke("ObtenerEstudiosPorEpisodio", cod);
        DefaultListModel dlm = new DefaultListModel();
          estudios =  (Vector<Estudios>)  Sesion.getServiceDelegatorRemote().invoke("ObtenerEstudiosPorEpisodio", epis);

          for(Estudios e : estudios) {
                dlm.addElement(e.getDescripcion());
          }

        uil.setModeloAdicionales(dlm);
    }

    private boolean existeEstudio(Estudios e, List<Estudios> estnormales) {

        boolean resp = false;

        for(Estudios est : estnormales) {
            if(est.getId().equals(e.getId())) {
                resp = true;
                break;
            }
        }

        return resp;
    }

    private void mostrarResultados(Boolean diabetes){
        uil.setDiabetes(diabetes);
    }

    @Override
    public void vaciarDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
