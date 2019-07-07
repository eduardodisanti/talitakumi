 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.control.medicos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;
import talitakumi.framework.DataParameter;
import talitakumi.framework.Sesion;
import talitakumi.genericontrollers.pacientes.BuscarUnPacienteController;
import talitakumi.model.core.Carnetdesalud;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Facturas;
import talitakumi.model.core.Libretasdeconducir;
import talitakumi.model.core.Personas;
import talitakumi.servicios.ConvertPersona;
import view.UIComentarEpisodioADM;
import view.UIModificarEpisodioADM;
import view.UIPaciente;
import view.UIVerImagenHistoria;

/**
 *
 * @author rupertus
 */
public class ConsultarHC {


    private UIModificarEpisodioADM uil;
    UIPaciente uipac;
    Logger logger;
    DefaultTableModel dftm;


     public ConsultarHC() {

        uipac = new UIPaciente();
        uil = new UIModificarEpisodioADM(null, true);

        uipac.setBuscarPacienteListener(new BuscarPacienteListener());
        uil.setBuscarImprimirEpisodio(new ImprimirEpisodioListener());
        uil.setVerEpisodio(new VerEpisodioListener());
        uil.setComentarEpisodio(new ComentarEpisodioListener());
        uil.setPanelPaciente(uipac);
        this.setCedulaListener();
        this.setTablaActividadesListener();

            crearModeloTabla();
            uil.setAnchoColumna(0, 80);
            uil.setAnchoColumna(1, 150);
            uil.setAnchoColumna(2, 100);
            uil.setAnchoColumna(3, 100);
            uil.setAnchoColumna(4, 30);
            uil.setAnchoColumna(5, 30);
            uil.setAnchoColumna(6, 30);
            uil.setAnchoColumna(7, 150);
            uil.setAnchoColumna(8, 250);

        uipac.setVisible(true);
        uil.setVisible(true);

     }

     public void listar(Date fechadesde, Date fechahasta) {

         Vector <Clientes> lc = null;
         HashMap m = new HashMap();

         DefaultTableModel dftm = new DefaultTableModel();

         lc = (Vector<Clientes>) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarClientes", 0);

         dftm.addColumn("Codigo");
         dftm.addColumn("Cliente");

         for(Clientes c : lc) {
             dftm.addRow(new Object[]{c.getId(), c.getNombre()});
         }

         String sfechadesde, sfechahasta;

         DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
         sfechadesde = df.format(fechadesde);
                       df = new SimpleDateFormat("yyyy-MM-dd");
         sfechahasta = df.format(fechahasta);
         m.put("fecha_desde", sfechadesde);
         m.put("fecha_hasta", sfechahasta);
        
     }

    private void buscarPaciente() {

        BuscarUnPacienteController bupc = new BuscarUnPacienteController();
        int cedula = bupc.getCedulaElegida();
        uipac.setCedula(cedula);
        actuarEnPersona();
    }

    private void actuarEnPersona() {

        Integer cedula = uil.getCedula();
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

                setPersona(dp);
            } catch(Exception e) {
                logger.error(new Date() + " " + e);
            }
        }

        cargarDatosActividad(p);
    }

    private void setTablaActividadesListener() {

        uil.setEpisodioElegidoListener(new EpisodioElegidoListener());
    }

    public void setPersona(DataParameter dp) {

        uipac.setPaciente(dp);
    }

    Personas obtenerPersona(int cedula) throws Exception {

        Personas p = (Personas) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarPersona", cedula);
        return(p);
    }

    private void crearModeloTabla() {
         dftm = new DefaultTableModel();
            dftm.addColumn("Id");
            dftm.addColumn("Actividad");
            dftm.addColumn("Fecha");
            dftm.addColumn("Medico actuante");
            dftm.addColumn("Med");
            dftm.addColumn("Odo");
            dftm.addColumn("Lab");
            dftm.addColumn("Cliente");
            dftm.addColumn("Facturas");

            uil.setModelTabla(dftm);
    }

    private void cargarActividad() {

        int idepisodio = (Integer) uil.getIdEpisodioElegido();

        Episodios e = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio", idepisodio);

        new ReportCDSCtrl(e);
    }

    private void cargarDatosActividad(Personas p) {

        crearModeloTabla();
        List<Episodios> le = (List<Episodios>) Sesion.getServiceDelegatorRemote().invoke("ObtenerActividadesDePersona", p);

        String fechax;

        int linea = 0;
        for(Episodios e : le) {
            Carnetdesalud cds = e.getCarnetdesalud();
            Libretasdeconducir lcond = e.getLibretasdeconducir();
            int idcli = e.getCliente(); // TODO - BIG CAGADA LA REPUTA QUE LO PARIO ME FALTO EL FK A CLIENTE por lo que tengo que llamar al EJB

            Clientes cliente = (Clientes) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarCliente", idcli);

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                fechax = sdf.format(e.getFecha());
                List <Facturas>lf = (List<Facturas>) Sesion.getServiceDelegatorRemote().invoke("CargarFacturasEpisodio", e);

                String facturas = "";
                for(Facturas f : lf) {

                        float total = f.getImporte() - f.getDescuentos();
                        String s = f.getSerie() + f.getNumero() + " T:" + f.getTipo() +  " $" + total + " Obs:" + f.getComentario();
                        facturas += "\n" + s;
                }

            String vodo = "";
            String vmed = "";
            String vlab = "";

            if(lcond!=null) {
                vmed = lcond.getVigencia() + "";
            }
            if(cds!=null) {
                
                int num  = cds.getVigenciaodontologo();
                if(num==-1)
                    vodo=" ";
                else
                    vodo = num+"";
                num  = cds.getVigenciamedico();
                if(num==-1)
                    vmed=" ";
                else
                    vmed = num+"";
                num  = cds.getVigencialaboratorio();
                if(num==-1)
                    vlab=" ";
                else
                    vlab = num+"";
            }
             dftm.addRow(new Object[]{e.getId(), e.getTipoepisodio().getDescripcion(), fechax, e.getMedico().getNombre(), vmed, vodo, vlab, cliente.getNombre(), facturas});
             linea++;
            
        }
    }

    private class VerEpisodioListener  implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                verEpisodio();
            } catch(Exception ex) {
                System.out.println("Big kk rendering img "+ex);
                ex.printStackTrace();
            }
        }
    }

    private class ComentarEpisodioListener  implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                comentarEpisodio();
            } catch(Exception ex) {
                System.out.println("Big kk rendering img "+ex);
                ex.printStackTrace();
            }
        }

    }

    private class ImprimirEpisodioListener  implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            imprimirEpisodio();
        }

    }

    private void comentarEpisodio() throws IOException {

        int idepisodio = (Integer) uil.getIdEpisodioElegido();

        Episodios e = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio", idepisodio);

        UIComentarEpisodioADM imgdlg = new UIComentarEpisodioADM(null, true);
        imgdlg.setComentarios(e.getComentarios());
        imgdlg.setVisible(true);

        e.setComentarios(imgdlg.getComentarios());
    }

    private void verEpisodio() throws IOException {

        int idepisodio = (Integer) uil.getIdEpisodioElegido();

        Episodios e = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio", idepisodio);

        UIVerImagenHistoria imgdlg = new UIVerImagenHistoria(null, true);
        imgdlg.setImagenEpisodio(e.getImghistoria());
        imgdlg.setVisible(true);
    }

    private void imprimirEpisodio() {

        int idepisodio = (Integer) uil.getIdEpisodioElegido();

        Episodios e = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio", idepisodio);

        new ImprimirEpisodio(e);
    }

    private class BuscarPacienteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            buscarPaciente();
        }
        
    }

    public void setCedulaListener() {

        uil.setCedulaListener(new CedulaListener());
    }

    private class CedulaListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent arg0) {

        }

        @Override
        public void focusLost(FocusEvent arg0) {
            actuarEnPersona();
        }

    }
    
    private class EpisodioElegidoListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent arg0) {

            cargarActividad();
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
