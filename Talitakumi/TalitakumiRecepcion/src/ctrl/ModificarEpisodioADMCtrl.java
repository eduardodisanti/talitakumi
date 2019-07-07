/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;
import talitakumi.genericontrollers.pacientes.BuscarUnPacienteController;
import talitakumi.framework.DataParameter;
import talitakumi.framework.Sesion;
import talitakumi.informes.control.medicos.ImprimirEpisodio;
import talitakumi.model.core.Carnetdesalud;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Facturas;
import talitakumi.model.core.Personas;
import talitakumi.servicios.ConvertPersona;
import view.DialogoError;
import view.UIComentarEpisodioADM;
import view.UIModificarEpisodioADM;
import view.UIPaciente;
import view.UIVerImagenHistoria;

/**
 *
 * @author rupertus
 */
public class ModificarEpisodioADMCtrl {

    private UIModificarEpisodioADM uil;
    UIPaciente uipac;
    Logger logger;
    DefaultTableModel dftm;

    public ModificarEpisodioADMCtrl() {

        uipac = new UIPaciente();
        uil = new UIModificarEpisodioADM(null, true);
        uipac.setFormatoCedulaLibre();
        //uipac.setFormatoCedulaUruguaya();

        uipac.setBuscarPacienteListener(new BuscarPacienteListener());
        uil.setBuscarImprimirEpisodio(new ImprimirEpisodioListener());
        //uil.setVerEpisodio(new ImprimirEpisodioListener());
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

    private void buscarPaciente() {

        BuscarUnPacienteController bupc = new BuscarUnPacienteController();
        int cedula = bupc.getCedulaElegida();
        uipac.setCedula(cedula);
        actuarEnPersona();
    }

    private void cargarActividad() {

        int idepisodio = (Integer) uil.getIdEpisodioElegido();

        Episodios e = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio", idepisodio);

        e = (Episodios) new ModificarActividadesDispatcher(e).getResult();

        if(e!=null)
             Sesion.getServiceDelegatorRemote().invoke("ServicioAlmacenarEpisodio", e);
    }

    public void setCedulaListener() {

        uil.setCedulaListener(new CedulaListener());
    }

    private void cargarDatosActividad(Personas p) {

        crearModeloTabla();
        List<Episodios> le = (List<Episodios>) Sesion.getServiceDelegatorRemote().invoke("ObtenerActividadesDePersona", p);

        String fechax;

        int linea = 0;
        for(Episodios e : le) {
            Carnetdesalud cds = e.getCarnetdesalud();
            int idcli = e.getCliente(); // TODO - BIG CAGADA LA REPUTA QUE LO PARIO ME FALTO EL FK A CLIENTE por lo que tengo que llamar al EJB
            
            Clientes cliente = (Clientes) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarCliente", idcli);

            
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            fechax = sdf.format(e.getFecha());
            //List <Facturas>lf = (List<Facturas>) Sesion.getServiceDelegatorRemote().invoke("CargarFacturasEpisodio", e);

            String facturas = "";
            //for(Facturas f : lf) {
            Facturas f = (Facturas) Sesion.getServiceDelegatorRemote().invoke("CargarUnaFacturaPorEpisodio", e.getId());
            if(f!=null) {
                float total = f.getImporte() - f.getDescuentos();
                String s = f.getSerie() + f.getNumero() + " T:" + f.getTipo() +  " $" + total + " Obs:" + f.getComentario();
                facturas += "\n" + s;
            }
            //}


            String vodo = "";
            String vmed = "";
            String vlab = "";
            if(cds!=null) {
                vodo = cds.getVigenciaodontologo() + "";
                vmed = cds.getVigenciamedico() + "";
                vlab = cds.getVigencialaboratorio() + "";
                int num  = cds.getVigenciaodontologo();
                if(num==-1)
                    vodo=" ";
                num  = cds.getVigenciamedico();
                if(num==-1)
                    vmed=" ";
                num  = cds.getVigencialaboratorio();
                if(num==-1)
                    vlab=" ";
              }
            if(e.getLibretasdeconducir()!=null)
                vmed = e.getLibretasdeconducir().getVigencia() + "";

             dftm.addRow(new Object[]{e.getId(), e.getTipoepisodio().getDescripcion(), fechax, e.getMedico().getNombre(), vmed, vodo, vlab, cliente.getNombre(), facturas});
             linea++;
        }
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
    private void setTablaActividadesListener() {

        uil.setEpisodioElegidoListener(new EpisodioElegidoListener());
    }

    private void imprimirEpisodio() {

        int idepisodio = (Integer) uil.getIdEpisodioElegido();

        Episodios e = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio", idepisodio);

        new ImprimirEpisodio(e);
    }

    private void comentarEpisodio() throws IOException {

        int idepisodio = (Integer) uil.getIdEpisodioElegido();

        Episodios e = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio", idepisodio);

        UIComentarEpisodioADM imgdlg = new UIComentarEpisodioADM(null, true);
        imgdlg.setComentarios(e.getComentarios());
        imgdlg.setVisible(true);

        e.setComentarios(imgdlg.getComentarios());

        if(e!=null)
             Sesion.getServiceDelegatorRemote().invoke("ServicioAlmacenarEpisodio", e);

    }

    private void verEpisodio() throws IOException {

        int idepisodio = (Integer) uil.getIdEpisodioElegido();

        Episodios e = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio", idepisodio);
        System.out.println(" ES NUL? " +e.getImghistoria());
        if(e.getImghistoria()!=null){
            UIVerImagenHistoria imgdlg = new UIVerImagenHistoria(null, true);
            imgdlg.setImagenEpisodio(e.getImghistoria());
            imgdlg.setVisible(true);
        }
        else
            new DialogoError(null, "", "EL EPISODIO NO TIENE IMGHISTORIA","EL EPISODIO NO TIENE IMGHISTORIA", true).setVisible(true);
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
    private class CedulaListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent arg0) {

        }

        @Override
        public void focusLost(FocusEvent arg0) {
            actuarEnPersona();
        }

    }

    private class BuscarPacienteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            buscarPaciente();
        }
        
    }
}
