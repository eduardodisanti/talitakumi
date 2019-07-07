/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumiadmin.administracion.ctrl;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import talitakumi.framework.DataParameter;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.model.core.Moldeagenda;
import talitakumi.model.core.Tipodeactividad;
import talitakumiadmin.administracion.ui.UIMoldeAgenda;

/**
 *
 * @author rupertus
 */
class MoldeAgendaController extends AbstractVisualController {

    private UIMoldeAgenda uilp;
    private int cliente = 0;
    private List<Tipodeactividad> actividades;
    private Tipodeactividad tipoactividad;
    
    public MoldeAgendaController() {

        uilp = new UIMoldeAgenda();
        uilp.addAlmacenarListener(new AlmacenarListener());
        uilp.addElegirActividadListener(new ActividadListener());

        try {
            setListaActividades();
        } catch (Exception ex) {
            Logger.getLogger(MoldeAgendaController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error MoldeAgendaController " + ex);
        }

    }

    private void setListaActividades() {
     
        DefaultTableModel dftm = new DefaultTableModel();
        dftm.addColumn("Id");
        dftm.addColumn("Actividad");

        uilp.setModelListaActividades(dftm);

        actividades = (List<Tipodeactividad>) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarTipoActividades", 2);
        int largo = actividades.size();
            for(int i=0; i < largo;i++) {

                Tipodeactividad p = new Tipodeactividad();

                p = actividades.get(i);
                String nombre = p.getDescripcion();

                dftm.addRow(new Object[]{p.getId(), nombre});
            }
            uilp.setAnchoColumnaPrecios(0, 30);
            uilp.setAnchoColumnaPrecios(1, 260);
    }
    
    @Override
    public void guardarDatos() throws Exception {

        Moldeagenda ma;
        ArrayList<Moldeagenda> lma = new ArrayList<Moldeagenda>();

        DataParameter dp = uilp.getData();

        Integer hora     = (Integer) dp.get("hora");
        Integer minutos  = (Integer) dp.get("minutos");
        Integer duracion = (Integer) dp.get("duracion");
        Integer lugares = (Integer) dp.get("lugares");
        
        if(dp.get("lunes")!=null) {
            ma = crearNuevoMoldeAgenda(hora, minutos, duracion, lugares, tipoactividad);
            ma.setDia(1);
            lma.add(ma);
            tipoactividad.setMoldeagendaCollection(lma);
        }
        if(dp.get("martes")!=null) {
            ma = crearNuevoMoldeAgenda(hora, minutos, duracion, lugares, tipoactividad);
            ma.setDia(2);
            lma.add(ma);
            tipoactividad.setMoldeagendaCollection(lma);
        }
        if(dp.get("miercoles")!=null) {
            ma = crearNuevoMoldeAgenda(hora, minutos, duracion, lugares, tipoactividad);
            ma.setDia(3);
            lma.add(ma);
            tipoactividad.setMoldeagendaCollection(lma);
        }
        if(dp.get("jueves")!=null) {
            ma = crearNuevoMoldeAgenda(hora, minutos, duracion, lugares, tipoactividad);
            ma.setDia(4);
            lma.add(ma);
            tipoactividad.setMoldeagendaCollection(lma);
        }
        if(dp.get("viernes")!=null) {
            ma = crearNuevoMoldeAgenda(hora, minutos, duracion, lugares, tipoactividad);
            ma.setDia(5);
            lma.add(ma);
            tipoactividad.setMoldeagendaCollection(lma);
        }
        if(dp.get("sabado")!=null) {
            ma = crearNuevoMoldeAgenda(hora, minutos, duracion, lugares, tipoactividad);
            ma.setDia(6);
            lma.add(ma);
            tipoactividad.setMoldeagendaCollection(lma);
        }

        Sesion.getServiceDelegatorRemote().invoke("AlmacenarTipoDeActividad", tipoactividad);
    }


    @Override
    public JPanel getPanel() {
        return(uilp);
    }

    @Override
    public void cerrarTodo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void elegirActividad() {
        uilp.habilitarFormulario(true);

        Integer tact = obtenerTipoDeActividad();
        tipoactividad = (Tipodeactividad) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarTipoActividad", tact);

        DataParameter dp = new DataParameter();

       if(tipoactividad!=null) {
            if(tipoactividad.getExamenesactividadCollection()!=null) {
               for(Moldeagenda ma : tipoactividad.getMoldeagendaCollection()) {
                  if(ma.getDia()==1) {
                       dp.set("lunes", true);
                   }
                  if(ma.getDia()==2) {
                       dp.set("martes", true);
                   }
                  if(ma.getDia()==3) {
                       dp.set("miercoles", true);
                   }
                  if(ma.getDia()==4) {
                       dp.set("jueves", true);
                   }
                  if(ma.getDia()==5) {
                       dp.set("viernes", true);
                   }
                  if(ma.getDia()==6) {
                       dp.set("sabado", true);
                   }

                  dp.set("duracion", ma.getDuracion());
                  dp.set("hora", ma.getHora());
                  dp.set("minutos", ma.getMinutos());
                  dp.set("lugares", ma.getLugares());

                  uilp.setData(dp);
              }
           }
        }
    }

    private Integer obtenerTipoDeActividad() {

        Integer t = null;

        int idx = uilp.getFilaElegida();

        t = actividades.get(idx).getId();
        
        return(t);
    }

    private Moldeagenda crearNuevoMoldeAgenda(Integer hora, Integer minutos, Integer duracion, Integer lugares, Tipodeactividad tipoactividad) {
        Moldeagenda max = new Moldeagenda();
        max.setHora(hora);
        max.setMinutos(minutos);
        max.setDuracion(duracion);
        max.setLugares(lugares);
        max.setConsultorio("-");
        max.setTurno(0);
        max.setServicio(tipoactividad);

        return(max);
    }

    @Override
    public void vaciarDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private class ActividadListener implements MouseListener {

        public void mouseClicked(MouseEvent me) {

                elegirActividad();
        }

        public void mousePressed(MouseEvent me) {
            
        }

        public void mouseReleased(MouseEvent me) {
            
        }

        public void mouseEntered(MouseEvent me) {
            
        }

        public void mouseExited(MouseEvent me) {
            
        }

    }
}
