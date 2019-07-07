/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.ModificadorEpisodio;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Episodiosestudios;
import talitakumi.model.core.EpisodiosestudiosPK;
import talitakumi.model.core.Episodiosvacunas;
import talitakumi.model.core.EpisodiosvacunasPK;
import talitakumi.model.core.Estudios;
import talitakumi.model.core.Vacunas;
import ui.UIModificarCDS;
import view.DialogoInformacion;

/**
 *
 * @author rupertus
 */
public class ModificarCDSCtrl implements ModificadorEpisodio {

    private Episodios e;
    
    public ModificarCDSCtrl() {


        
    }

    private void verLaboratorio() {

        try {
            new MostrarLaboratorioDispatcher(e);
        } catch(Exception ex) {
            DialogoInformacion dlg = new DialogoInformacion(null, "1", "2", ex.getLocalizedMessage(), true);
            dlg.setVisible(true);
        }

    }


    private void pasarAOdontologo() {

        try {
            Sesion.getServiceDelegatorRemote().invoke("ServicioAgendarRevisionOdontologica",e.getCarnetdesalud());
        } catch(Exception ex) {
            DialogoInformacion dlg = new DialogoInformacion(null, "1", "2", ex.getLocalizedMessage(), true);
            dlg.setVisible(true);
        }
    }

    @Override
    public Object modificar() {

        UIModificarCDS uimcds = new UIModificarCDS(null, false);

        cargarVacunasATT(uimcds);
        cargarEpisodioPAP(uimcds);
        cargarEpisodioMAMO(uimcds);
        cargarVigencias(uimcds);

        uimcds.setPasarOdontologo(new PasarOdontologoListener());
        uimcds.setVerLaboratorio(new VerLaboratorioListener());
        uimcds.setVisible(true);

        String obs = "" + e.getCarnetdesalud().getObservaciones();
        uimcds.setObservaciones(obs);

        if(uimcds.getATT()) {

            String xvencimientoATT = uimcds.getVencimientoATT();
            if(xvencimientoATT != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date vencimientoATT = sdf.parse(xvencimientoATT);
                    modificarVacunatATT(e, vencimientoATT);
                } catch (ParseException ex) {
                    Logger.getLogger(ModificarCDSCtrl.class.getName()).log(Level.SEVERE, null, ex);
                }
             }
        }

        if(uimcds.getMamo()) {
            String xvencimientoMamo = uimcds.getVencimientoMamo();
            String medicoMamo = uimcds.getMedicoMamo();
            String clinicaMamo = uimcds.getClinicaMamo();
            if(xvencimientoMamo != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date vencimientoMamo = sdf.parse(xvencimientoMamo);
                    modificarVencimientoMamo(e, vencimientoMamo, medicoMamo, clinicaMamo);
                } catch (ParseException ex) {
                    Logger.getLogger(ModificarCDSCtrl.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
         }

        if(uimcds.getPAP()) {
            String xvencimientoPAP = uimcds.getVencimientoPAP();
            String medicoPAP = uimcds.getMedicoPAP();
            String clinicaPAP = uimcds.getClinicaPAP();
            if(xvencimientoPAP != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date vencimientoPAP = sdf.parse(xvencimientoPAP);
                    modificarVencimientoPAP(e, vencimientoPAP, medicoPAP, clinicaPAP);
                } catch (ParseException ex) {
                    Logger.getLogger(ModificarCDSCtrl.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
         }
         modificarVigencia(e, uimcds.getVigencia());
         modificarVigenciaLaboratorio(e, uimcds.getVigenciaLaboratorio());
         modificarVigenciaMedica(e, uimcds.getVigenciaMedico());
         modificarVigenciaOdontologo(e, uimcds.getVigenciaOdontologo());
        
        return(e);
    }

    private void modificarVigencia(Episodios une, int vigencia) {

        une.getCarnetdesalud().setVigencia(vigencia);
    }

    private void modificarVacunatATT(Episodios une, Date vencimientoATT) {

        boolean encontre = false;
        List<Episodiosvacunas> lev = (List<Episodiosvacunas>) une.getEpisodiosvacunasCollection();

        for(Episodiosvacunas unev : lev ) {

            if(unev.getVacunas().getId()==1) {
                encontre = true;
                unev.setVence(vencimientoATT);
            }
        }

        if(!encontre) {
            Episodiosvacunas unev = new Episodiosvacunas();
            EpisodiosvacunasPK evpk = new EpisodiosvacunasPK();
            evpk.setEpisodio(e.getId());
            evpk.setVacuna(1);

            unev.setEpisodios(e);
            unev.setEpisodiosvacunasPK(evpk);
            unev.setVacunas((Vacunas)Sesion.getServiceDelegatorRemote().invoke("CargarVacuna",1));
            unev.setVence(vencimientoATT);

            e.getEpisodiosvacunasCollection().add(unev);
        }
    }

    @Override
    public void setEpisodio(Object o) {

        e = (Episodios) o;
    }

    public void cargarEpisodioMAMO(UIModificarCDS uimcds) {

        List<Episodiosestudios> lev = (List<Episodiosestudios>) (e.getEpisodiosestudiosCollection());

        Date vencimientomamo = null;
        String medico = "", clinica = "";
        boolean mamo = false;

        for(Episodiosestudios epiv : lev) {

            if(epiv.getEstudios().getId()==22) {
                mamo = true;
                vencimientomamo = epiv.getVencimiento();
                clinica = epiv.getClinica();
                medico = epiv.getMedico();
            }
        }

        uimcds.setMamo(mamo);
        uimcds.setVencimientoMAMO(vencimientomamo);
        uimcds.setMedicoMAMO(medico);
        uimcds.setClinicaMAMO(clinica);
    }

    public void cargarEpisodioPAP(UIModificarCDS uimcds) {

        List<Episodiosestudios> lev = (List<Episodiosestudios>) (e.getEpisodiosestudiosCollection());

        Date vencimientopap = null;
        boolean pap = false;
        String medico = "", clinica = "";

        for(Episodiosestudios epiv : lev) {

            if(epiv.getEstudios().getId()==21) {
                pap = true;
                vencimientopap = epiv.getVencimiento();
                clinica = epiv.getClinica();
                medico = epiv.getMedico();
            }
        }

        uimcds.setPAP(pap);
        uimcds.setVencimientoPAP(vencimientopap);
        uimcds.setMedicoPAP(medico);
        uimcds.setClinicaPAP(clinica);
    }

    public void cargarVigencias(UIModificarCDS uimcds) {

        int vigenciaactual = e.getCarnetdesalud().getVigencia();
        int vigenciaodo = e.getCarnetdesalud().getVigenciaodontologo();
        int vigenciamed = e.getCarnetdesalud().getVigenciamedico();
        int vigencialab = e.getCarnetdesalud().getVigencialaboratorio();

        uimcds.setVigenciaActual(vigenciaactual);
        uimcds.setVigenciaMedico(vigenciamed);
        uimcds.setVigenciaOdontologo(vigenciaodo);
        uimcds.setVigenciaLaboratorio(vigencialab);
    }

    public void cargarVacunasATT(UIModificarCDS uimcds) {

        List<Episodiosvacunas> lev = (List<Episodiosvacunas>) (e.getEpisodiosvacunasCollection());

        Date vencimientoatt = null;
        boolean vacatt = false;

        for(Episodiosvacunas epiv : lev) {

            if(epiv.getVacunas().getId()==1) {
                 vacatt = true;
                 vencimientoatt = epiv.getVence();
            }
        }

        if(vencimientoatt != null) {
            String xvencimientoatt;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            xvencimientoatt = sdf.format(vencimientoatt);
            uimcds.setVacunaAtt(vacatt);
            uimcds.setVencimientoAtt(xvencimientoatt);
        }
    }

    private void modificarVigenciaLaboratorio(Episodios e, int vigenciaLaboratorio) {
        e.getCarnetdesalud().setVigencialaboratorio(vigenciaLaboratorio);
    }

    private void modificarVigenciaMedica(Episodios e, int vigenciaMedico) {
        e.getCarnetdesalud().setVigenciamedico(vigenciaMedico);
    }

    private void modificarVigenciaOdontologo(Episodios e, int vigenciaOdontologo) {
        e.getCarnetdesalud().setVigenciaodontologo(vigenciaOdontologo);
    }

    private void modificarVencimientoMamo(Episodios e, Date vencimientoMamo, String unMedico, String clinicaMamo) {
        boolean encontre = false;
        List<Episodiosestudios> lev = (List<Episodiosestudios>) e.getEpisodiosestudiosCollection();

        for(Episodiosestudios unev : lev ) {

            if(unev.getEstudios().getId()==22) {
                encontre = true;
                unev.setVencimiento(vencimientoMamo);
                unev.setMedico(unMedico);
                unev.setClinica(clinicaMamo);
            }
        }

        if(!encontre) {
            Episodiosestudios unev = new Episodiosestudios();
            EpisodiosestudiosPK evpk = new EpisodiosestudiosPK();
            evpk.setEpisodio(e.getId());
            evpk.setEstudio(22);

            unev.setEpisodios(e);
            unev.setEpisodiosestudiosPK(evpk);
            unev.setEstudios((Estudios)Sesion.getServiceDelegatorRemote().invoke("CargarEstudio",22));
            unev.setVencimiento(vencimientoMamo);
            unev.setMedico(unMedico);
            unev.setClinica(clinicaMamo);

            e.getEpisodiosestudiosCollection().add(unev);
        }
  
    }

    private void modificarVencimientoPAP(Episodios e, Date vencimientoPAP, String unMedico, String clinicaPAP) {
        boolean encontre = false;
        List<Episodiosestudios> lev = (List<Episodiosestudios>) e.getEpisodiosestudiosCollection();

        for(Episodiosestudios unev : lev ) {

            if(unev.getEstudios().getId()==21) {
                encontre = true;
                unev.setVencimiento(vencimientoPAP);
                unev.setMedico(unMedico);
                unev.setClinica(clinicaPAP);
            }
        }

        if(!encontre) {
            Episodiosestudios unev = new Episodiosestudios();
            EpisodiosestudiosPK evpk = new EpisodiosestudiosPK();
            evpk.setEpisodio(e.getId());
            evpk.setEstudio(21);

            unev.setEpisodios(e);
            unev.setEpisodiosestudiosPK(evpk);
            unev.setEstudios((Estudios)Sesion.getServiceDelegatorRemote().invoke("CargarEstudio",21));
            unev.setVencimiento(vencimientoPAP);
            unev.setMedico(unMedico);
            unev.setClinica(clinicaPAP);

            e.getEpisodiosestudiosCollection().add(unev);
        }
    }

    private class PasarOdontologoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            pasarAOdontologo();
        }
        
    }

    private class VerLaboratorioListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            verLaboratorio();
        }

    }

}
