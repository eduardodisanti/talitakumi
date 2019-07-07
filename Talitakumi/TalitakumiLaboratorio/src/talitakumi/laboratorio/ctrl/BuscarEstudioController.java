/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.laboratorio.ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import talitakumi.framework.DataParameter;
import talitakumi.framework.Sesion;
import talitakumi.laboratorio.ui.UIBuscarEstudios;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Estudioshechos;
import talitakumi.model.core.Personas;
import talitakumi.servicios.ConvertPersona;
import talitakumi.servicios.Reporter;

/**
 *
 * @author eduardo
 */
public class BuscarEstudioController {

    private UIBuscarEstudios ui;
    private Personas p = null;
    private Vector <Estudioshechos> epis;
    private Date fechadesde, fechahasta;
    private DefaultTableModel dftm;

    public BuscarEstudioController() {
            ui = new UIBuscarEstudios(null, false);

            ui.setImprimirListener(new ImprimirEstudioListener());
            ui.setCerrarListener(new CerrarListener());
            ui.setCambiarPersonaListener(new CambiarPersonaListener());
            ui.setVisible(true);
            ui.setBuscarListener(new BuscarListener());
            ui.setFdesdeListener(new BuscarListener());
            ui.setFhastaListener(new BuscarListener());
            ui.setImprimirTirillaListener(new ImprimirTirillaListener());

            ui.setFechas();
    }


    private void cargarEstudios(Integer idpersona) {

        epis = (Vector) Sesion.getServiceDelegatorRemote().invoke("CargarEstudiosLaboratorioPorPaciente", idpersona);
        dftm = new DefaultTableModel();

        dftm.addColumn("Fecha");
        dftm.addColumn("Estudio");
        dftm.addColumn("Epi");

        fechadesde = ui.getFechaDesde();
        fechahasta = ui.getFechaHasta();
        for(Estudioshechos ee : epis) {

            Date d = ee.getEpisodio().getFecha();
            if(((d.after(fechadesde) || d.equals(fechadesde))) &&
               (d.before(fechahasta) || d.equals(fechahasta))) {
                String xfecha;
                DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                xfecha = sdf.format(ee.getEpisodio().getFecha());
                dftm.addRow(new Object[]{xfecha, ee.getEstudio().getDescripcion(), ee.getEpisodio().getId()});
            }
        }

        ui.setModeloTabla(dftm);
    }

    private void imprimir() {
         int idepisodio;
         int idx = ui.getLineaElegida();


         idepisodio = (Integer)dftm.getValueAt(idx, 2);
         //idepisodio = epis.elementAt(idx).getEpisodio().getId();


         HashMap m = new HashMap();

         m.put("episodiodesde", idepisodio);
         m.put("episodiohasta", idepisodio);
         new Reporter("analisislaboratorio.jasper").listar(m);


    }

    private void emitirTirilla() {
         int idepisodio;
         int idx = ui.getLineaElegida();


         idepisodio = (Integer)dftm.getValueAt(idx, 2);
         //idepisodio = epis.elementAt(idx).getEpisodio().getId();
        Calendar hoy = Calendar.getInstance();
        Calendar fechaNac = Calendar.getInstance();

        Episodios e = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio", idepisodio);
        fechaNac.setTime(e.getPaciente().getPersonas().getFechanacimiento());

    	int years = hoy.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
    	if (hoy.get(Calendar.MONTH) < fechaNac.get(Calendar.MONTH)) years --;
    	if (hoy.get(Calendar.MONTH) == fechaNac.get(Calendar.MONTH) &&
    		hoy.get(Calendar.DATE) < fechaNac.get(Calendar.DATE)	) years --;


         HashMap m = new HashMap();

         m.put("fecha", e.getFecha());
         m.put("apellidos", e.getPaciente().getPersonas().getApellidos());
         m.put("nombres", e.getPaciente().getPersonas().getNombres());
         m.put("cedula", e.getPaciente().getPersonas().getDocumento());
         m.put("hora", " ");
         m.put("edad", years);
         m.put("id", idepisodio);
         new Reporter("TirillaCompleta.jasper").listar(m);


    }

    private void cerrarUI() {

        ui.setVisible(false);
        ui.dispose();
    }

    private class CerrarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            cerrarUI();
        }
    }

    private void actuarEnPersona() {

        Integer cedula = ui.getCedula();

        try {
            p = obtenerPersona(cedula);
        } catch (Exception ex) {
            Sesion.getLogger().error(ex);
        }

        if(p!=null) {
            try {
                DataParameter dp = new DataParameter();
                ConvertPersona conv = new ConvertPersona();
                dp = conv.fromObject(p);

                ui.setPersona(dp);
                cargarEstudios(p.getPacientes().getId());
            } catch(Exception e) {
                Sesion.getLogger().error(new Date() + " " + e);
            }
        }
    }
    Personas obtenerPersona(int cedula) throws Exception {


        p = (Personas) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarPersona", cedula);

        return(p);
    }

    public class ImprimirEstudioListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            imprimir();
        }
    }

    public class ImprimirTirillaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            emitirTirilla();
        }


    }

    public class BuscarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            actuarEnPersona();
        }

    }

    public class CambiarPersonaListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent arg0) {

        }

        @Override
        public void focusLost(FocusEvent arg0) {

            actuarEnPersona();
        }
    }
}
