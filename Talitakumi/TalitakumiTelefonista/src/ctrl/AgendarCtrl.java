/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import talitakumi.framework.Sesion;
import talitakumi.model.core.Actividades;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.AgendaPK;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Pacientes;
import talitakumi.model.core.Personas;
import talitakumi.model.core.Seguros;
import ui.UIAgendar;
import view.DialogoError;
import view.DialogoInformacion;

/**
 *
 * @author victoria
 */
public class AgendarCtrl {

    private UIAgendar uia;
    private List<Actividades> actividades;
    private int turno;
    private List<Clientes> procedencias;

    public AgendarCtrl(Date fecha, String hora, int turno){

        uia = new UIAgendar();

        uia.setSize(850,250);
        uia.setLocationRelativeTo(null);
        uia.setFecha(fecha);
        uia.setHora(hora);
        uia.setVisible(true);
        
        uia.setCancelarListener(new SalirListener());
        uia.setAlmacenarListener(new AlmacenarListener());
        uia.setCargarPacienteListener(new CargarPacienteListener());
        
        cargarActividades();
        try {
            setListaProcedencias();
        } catch(Exception ex) {
            new DialogoError(null, "Error", "No se pueden cargar los clientes", ex.getMessage(), true).setVisible(true);
        }
        setActividadesEnUI();
        
        this.turno = turno;
    }

    private void cerrar() {

       uia.setVisible(false);
       uia.dispose();

   }
    
    
    
    private void almacenar() {
       
        try {

       int indiceprocedencia = uia.getProcedenciaElegida();
       Clientes proc = procedencias.get(indiceprocedencia);

       Personas p = obtenerPersona(uia.getPaciente());
       Pacientes pac;
       System.out.println("Obtuve persona");
        if(p==null) {
           p = new Personas();
           p.setDocumento(uia.getPaciente());
           p.setId(null);
           p.setCiudad("Ciudad");
           p.setMovil("Móvil");
           p.setFechanacimiento(null);
           p.setDomicilio("DOMICILIO");
           p.setFechanacimiento(new Date());
        }

        if(p.getPacientes()==null) {
           pac = new Pacientes();
           pac.setPersonas(p);
           p.setPacientes(pac);
           pac.setFechaingreso(new Date());
        }
        pac = p.getPacientes();
        p.setApellidos(uia.getApellidos());
        p.setNombres(uia.getNombres());

        p.setDocumento(uia.getPaciente());
        //p.setDomicilio("DOMICILIO");

        p.setSexo(uia.getSexo());
        p.setTelefono(uia.getTelefono());

        if(pac.getSeguro()==null) {
                   Seguros seguro = new Seguros();
                   seguro.setId(0);
                   pac.setSeguro(seguro);
        }

        Sesion.getServiceDelegatorRemote().invoke("ServicioGuardarPersona", p);
        Agenda a = new Agenda();
        AgendaPK apk = new AgendaPK();
        
        apk.setConsultorio("TEL");
        apk.setFecha(uia.getFecha());
        apk.setHora(formatHora(uia.getHora()));
        apk.setTurno(turno);
        
        a.setActividad(actividades.get(uia.getActividad()).getId());
        a.setAgendaPK(apk);
        a.setEpisodio(null);
        a.setHabilitado('S');
        a.setMedico(null);

        a.setCliente(proc);
        a.setObservaciones(uia.getObservaciones());

        // ALMACENAR EPISODIO

        p = obtenerPersona(uia.getPaciente());

        a.setPaciente(uia.getPaciente());
        a.setSesid(0);
        a.setVino('N');        


        a.setPaciente(p.getId());
        Sesion.getServiceDelegatorRemote().invoke("ServicioAlmacenarAgenda", a);

        uia.setVisible(false);
        uia.dispose();
        
        } catch(Exception ex) {
            new DialogoInformacion(null, "Error al almacenar", "Error en el sistema ", "La persona <B>NO ESTA ANOTADA</B> -<br> Error : "+ex.getMessage(), true).setVisible(true);
        }
    }

    private void cargarPaciente() {

        int cedula = uia.getCedula();

        try {
            Personas p = obtenerPersona(cedula);

            if(p!=null)
                mostrarPaciente(p);
        } catch(Exception ex) {

            System.out.println("Error cargarPaciente en telefonista " + ex);
        }
    }

    private void cargarActividades() {
        List<Actividades> tmpact = (List<Actividades>) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarActividadesALL", 2);

        actividades = new ArrayList<Actividades>();
        for(Actividades act : tmpact) {
                actividades.add(act);
               /* if(act.getId().equals(1) ||
                  act.getId().equals(2) ||
                  act.getId().equals(3) ||
                  act.getId().equals(4) ||
                  act.getId().equals(7) ||
                  act.getId().equals(8) ||
                  act.getId().equals(10) ||
                  act.getId().equals(35) ||
                  act.getId().equals(37) ||
                  act.getId().equals(38) ||
                  act.getId().equals(39)
                 ) {
                actividades.add(act);
            }
            */
        }
    }

    private void setActividadesEnUI() {
        
        for(Actividades a : actividades) {
            
            uia.addActividad(a.getDescripcion());
        }
    }

    Personas obtenerPersona(int cedula) throws Exception {

        Personas p = (Personas) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarPersona", cedula);

        return(p);
    }

    private void mostrarPaciente(Personas p) {

        uia.setApellidos(p.getApellidos());
        uia.setNombres(p.getNombres());
        uia.setTelefono(p.getMovil());
        uia.setSexo(p.getSexo());
    }

    private void setListaProcedencias() throws Exception {

        procedencias = (List) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarClientes", "S");
        uia.setListaProcedencias(procedencias);

    }

    private String formatHora(String hora) {

        String t[] = hora.split("[:]");
        String hh = t[0];
        String mm = t[1];


        if(hh.length()==1)
            hh="0"+hh;

        if(mm.length()==1) {
            mm="0"+mm;
        }

        return(hh+":"+mm);
    }
    
    private class AlmacenarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            almacenar();
        }
        
    }
    
    private class SalirListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            cerrar();
        }
    }

    private class CargarPacienteListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent fe) {

        }

        @Override
        public void focusLost(FocusEvent fe) {

            cargarPaciente();
        }

    }
}
