
package talitakumi.medicos.ctrl;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.framework.mq.Mensajero;
import talitakumi.medicos.ui.UICarnetDeSalud;
import talitakumi.medicos.ui.UIElectroCardiograma;
import talitakumi.medicos.ui.UIListaParaclinicos;
import talitakumi.model.core.Actividades;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.Carnetdesalud;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Enfermedades;
import talitakumi.model.core.Episodioagudezasvisuales;
import talitakumi.model.core.Episodioexamenfisico;
import talitakumi.model.core.EpisodioexamenfisicoPK;
import talitakumi.model.core.Episodiopresion;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.EpisodiosExAdicionales;
import talitakumi.model.core.Episodiosantecedentes;
import talitakumi.model.core.EpisodiosantecedentesPK;
import talitakumi.model.core.Episodiosenfermedades;
import talitakumi.model.core.EpisodiosenfermedadesPK;
import talitakumi.model.core.Episodiosestudios;
import talitakumi.model.core.EpisodiosestudiosPK;
import talitakumi.model.core.Episodiosmedicamentos;
import talitakumi.model.core.EpisodiosmedicamentosPK;
import talitakumi.model.core.Episodiosvacunas;
import talitakumi.model.core.EpisodiosvacunasPK;
import talitakumi.model.core.Episodiosvicios;
import talitakumi.model.core.EpisodiosviciosPK;
import talitakumi.model.core.Estudios;
import talitakumi.model.core.Examenfisico;
import talitakumi.model.core.Medicamentos;
import talitakumi.model.core.Medicos;
import talitakumi.model.core.Pacientes;
import talitakumi.model.core.Personas;
import talitakumi.model.core.Vacunas;
import talitakumi.model.core.Vencimientosdoc;
import talitakumi.model.core.Viciossociales;
import view.DialogoError;

/**
 *
 * @author rupertus
 */
class CarnetDeSaludCtrl extends AbstractVisualController {

      private UICarnetDeSalud uil;
      private Agenda agenda;
      private Episodios epis;
      private List<Clientes> procedencias;
      private Personas p;
      private Pacientes pac;

     
      public CarnetDeSaludCtrl(Agenda a) {

        uil = new UICarnetDeSalud();
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
/*DUDA*/        Carnetdesalud uncds = (Carnetdesalud) Sesion.getServiceDelegatorRemote().invoke("CargarCarneDeSalud", epis);
        if(uncds == null) {
            uncds = new Carnetdesalud();
        }
        epis.setCarnetdesalud(uncds);
        a.setEpisodio(epis);

        pac = theepis.getPaciente();
        llenarVencimientos(theepis.getPaciente());

        if(avisarElectro(a)) {

            UIElectroCardiograma dlg = new UIElectroCardiograma(null, true);
            dlg.setMotivo("Ficha medica deportiva mayor de 40 años");
            dlg.setPersona(p.getApellidos() + " " + p.getNombres());
            dlg.setVisible(true);
        }

        if(avisarParaclinicos(a)) {

            boolean avisar = false;
            UIListaParaclinicos dlg = new UIListaParaclinicos(null, true);
            for(EpisodiosExAdicionales e : a.getEpisodio().getEpisodiosExAdicionalesCollection()) {
                System.out.println("E:" + e.getEstudio().getDescripcion());
                if(e.getEstudio().getMedico().equals("S")) {
                    dlg.addEstudio(e.getEstudio().getDescripcion());
                    avisar=true;
                }
            }
            dlg.setPersona(p.getApellidos() + " " + p.getNombres());
            if(avisar) {
                dlg.setVisible(true);
            }
        }


    }

      private void llenarVencimientos(Pacientes p) {

        try {
        for(Vencimientosdoc vd : p.getVencimientosdocCollection()) {

            if(vd.getDocumento()==101L) {
                uil.setVencimientoATT(vd.getVence());
            }

            if(vd.getDocumento()==221) {
                uil.setVencimientoPAP(vd.getVence());
            }

            if(vd.getDocumento()==222) {
                uil.setVencimientoMAMO(vd.getVence());
            }

            if(vd.getDocumento()==2)  { // TODO - properties
                System.out.println("----->BREAK 1");
                uil.setVencimientoCDS(vd.getVence());
                System.out.println("----->BREAK 2");
                uil.setFechaOtorgadoAnterior(vd.getEmitido());
            }
        }
      }
          catch(Exception ex) {

               System.out.println("Bigg kk en llenar vencimientos  " + ex);
      }
    }

    private void setListaProcedencias() throws Exception {

        procedencias = (List) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarClientes", "S");
        uil.setListaProcedencias(procedencias);
    }

    @Override
    public void guardarDatos() throws Exception {

        System.out.println("entro a guardar datos");

        epis.setEpisodiosviciosCollection(cargarVicios());
        epis.setEpisodiosmedicamentosCollection(cargarMedicamentos());

        cargarAntecedentesFamiliares();
        cargarAntecedentes1();
        cargarAntecedentesAlergias();
        cargarAntecedentesCardiovasculares();
        cargarAntecedentesPleuropulmonares();
        cargarAntecedentesDigestivos();
        cargarAntecedentesRenales();
        cargarAntecedentesOseos();
        cargarAntecedentesOftalmologicos();
        cargarAntecedentesNeurologicos();
        cargarAntecedentesInternaciones();
        cargarAntecedentesAuditivos();
        cargarAntecedentesVenereos();
        cargarAntecedentesSiquiatricos();
        cargarAntecedentesQuirurgicos();
        cargarAntecedentesObstetricos();

        cargarExamenFisico();
        cargarEnfermedadesPielMucosas();
        cargarEnfermedadesLinfoganglionar();
        cargarEnfermedadesSistemaVascular();
        cargarPresiones();
        cargarEnfermedadesMamas();
        cargarEnfermedadesPleuropulmonares();
        cargarEnfermedadesVenosas();
        cargarEnfermedadesAbdomen();
        cargarZonasHernarias();
        cargarOsteoarticular();
        cargarVision();
        cargarSectorMuscular();

        cargarVacunas();
        cargarActividadFisica();
        cargarEstudiosMedicos();

        cargarImagenEpisodio();

        Carnetdesalud cds = agenda.getEpisodio().getCarnetdesalud();
        cds = cargarDatosCDS(cds);

        int aux, plazo;

        aux = uil.getVigenciaMedico();
        if(!uil.getATT()) { // No tiene vacuna antitetánica
            aux = 0;
        }
        if(p.getSexo()=='F' && calcularEdad(p) >= 40) {
            
            if(!uil.getPapaNicolau() && !uil.getPapaNicolauClinica().equals("NO CORRESPONDE")) {
                aux = 0;
            }
            else
                if(!uil.getMamo() && !uil.getMamografiaClinica().equals("NO CORRESPONDE")) {
                aux = 0;
            }
        }

        if(aux==0) {
            plazo = 6;
        }
        else
            if(aux==1) {
            plazo = 12;
        }
            else {
            plazo = 24;
        }
        cds.setCartamedicotratante(uil.getCartaMedicoTrantate());
        boolean negado = uil.getCausaMedica() == 'S';
        cds.setCausamedica(uil.getCausaMedica());

        if(!uil.getATT()) {
            negado = true;
        }

        if(negado) {
            plazo = 0;
        }

        cds.setVigenciamedico(plazo);

        epis.setCarnetdesalud(cds);
        cds.setEpisodio(epis.getId());
        cds.setEpisodios(epis);

        Medicos m = (Medicos) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarMedico", Sesion.getNumeromedico());
        epis.setMedico(m);
        
        agenda.setEpisodio(epis);
        Collection<Vencimientosdoc> lvd = pac.getVencimientosdocCollection();
        if(lvd==null) {
            lvd = new ArrayList<Vencimientosdoc>();
        }


        try {
                Vencimientosdoc vencatt = new Vencimientosdoc();

                vencatt.setPaciente(pac);
                vencatt.setVence(uil.getVenceATT());
                vencatt.setId(null);
                vencatt.setDocumento(101);
                vencatt.setEmitido(new Date());
                lvd.add(vencatt);
        } catch(Exception ex) {
            
            System.out.println("===> No se pudo parsear vatt");
            new DialogoError(null, "Atención", "No se puede cargar vencimiento de VATT", "No se ha podido cargar el vencimiento de vacuna antitetanica, PUEDE CONTINUAR NORMALMENTE CON LA CONSULTA", true).setVisible(true);
        }
        try {
                Vencimientosdoc vencpap = new Vencimientosdoc();

                vencpap.setPaciente(pac);
                vencpap.setVence(uil.getPapaNicolauFecha());
                vencpap.setId(null);
                vencpap.setDocumento(221);
                vencpap.setEmitido(new Date());
                lvd.add(vencpap);
        } catch(Exception ex) {
            
            System.out.println("===> No se pudo parsear pap");
            System.out.println("===> No se pudo parsear vatt");
            new DialogoError(null, "Atención", "No se puede cargar vencimiento de PAP", "No se ha podido cargar el vencimiento de PAP, PUEDE CONTINUAR NORMALMENTE CON LA CONSULTA", true).setVisible(true);

        }
        try {
                Vencimientosdoc vencmamo = new Vencimientosdoc();
                vencmamo.setPaciente(pac);
                vencmamo.setVence(uil.getMamografiaFecha());
                vencmamo.setId(null);
                vencmamo.setDocumento(222);
                vencmamo.setEmitido(new Date());
                lvd.add(vencmamo);
        } catch(Exception ex) {
            
            System.out.println("===> No se pudo parsear mamo");
            new DialogoError(null, "Atención", "No se puede cargar vencimiento de MAMOGRAFIA", "No se ha podido cargar el vencimiento de MAMOGRAFIA, PUEDE CONTINUAR NORMALMENTE CON LA CONSULTA", true).setVisible(true);

        }
        
        epis.setPaciente(pac);
        // TODO ALMACENAR EL PACIENTE APARTE
        
        Boolean res = (Boolean) Sesion.getServiceDelegatorRemote().invoke("ServicioModificarAgenda", agenda);

        if(!res) {
            new DialogoError(null, "Error al almacenar", "Error almacenando", "No se pudo almacenar", true).setVisible(true);
        }
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

    private void cargarActividadFisica() {
        Episodiosestudios ee = new Episodiosestudios();

        if(uil.getActividadesFisicas()) {

            ee.setClinica("");
            ee.setEpisodios(epis);
            ee.setEpisodiosestudiosPK(new EpisodiosestudiosPK(epis.getId(), 99));
            ee.setEstudios(cargarEstudio(99));
            ee.setMedico("");
            ee.setVencimiento(new Date());

            epis.getEpisodiosestudiosCollection().add(ee);
        }
    }

    private void cargarAntecedentesFamiliares() {

        Episodiosantecedentes ea = new Episodiosantecedentes();

        if(uil.getFamHTA()) {
            ea.setEnfermedades(cargarEnfermedad(93));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 93));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
        if(uil.getFamDiabetes()) {
            int sel = uil.getTipoDiabetesAP();
            int enfa = 94;
            if(sel==0)
                enfa = 97;
            else
                enfa = 98;
            ea.setEnfermedades(cargarEnfermedad(enfa));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), enfa));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
        if(uil.getFamMarcapasos()) {
            ea.setEnfermedades(cargarEnfermedad(95));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 95));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
        if(uil.getFamOtros()) {
            ea.setEnfermedades(cargarEnfermedad(96));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 96));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }

    }

    private Carnetdesalud cargarDatosCDS(Carnetdesalud cds) {

        cds.setObservaciones(uil.getObservaciones());
        cds.setImpresiongeneral(uil.getImpresionGeneral());
        cds.setSugerenciacm(uil.getSugerenciascm());
        cds.setSugerenciacontpeso(uil.getSugerenciacontpeso());
        cds.setSugerenciaejercicios(uil.getSugerenciaejercicios());
        cds.setSugerenciaexlab(uil.getSugerenciaexlab());
        cds.setFechaexpedido(new Date());
        return(cds);
    }

    private void cargarEstudiosMedicos() {
        Episodiosestudios ee = new Episodiosestudios();

        if(uil.getEstudiosMedicos().length() > 0) {

            ee.setClinica("");
            ee.setEpisodios(epis);
            ee.setEpisodiosestudiosPK(new EpisodiosestudiosPK(epis.getId(), 23));
            ee.setEstudios(cargarEstudio(23));
            ee.setMedico("");
            ee.setClinica(uil.getEstudiosMedicos());
            ee.setVencimiento(new Date());

            epis.getEpisodiosestudiosCollection().add(ee);
        }
    }

    private void cargarAntecedentes1() {

        Episodiosantecedentes ea = new Episodiosantecedentes();

        if(uil.getDiabetico()) {
            ea.setEnfermedades(cargarEnfermedad(7));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 7));
            epis.getEpisodiosantecedentesCollection().add(ea);

            cambiarEpisodioDiabetico();
        }
        if(uil.getTiroideo()) {
            int idx = uil.getTipoTiroideoAP();
            int enf = 29;
            switch(idx) {

                case 0 : enf = 99 ;
                        break;
                case 1 : enf = 100 ;
                        break;
                case 2 : enf = 101 ;
                        break;
            }

            ea.setEnfermedades(cargarEnfermedad(enf));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), enf));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
    }

    private void cargarAntecedentesAlergias() {
        Episodiosantecedentes ea = new Episodiosantecedentes();

        if(uil.getAlergiaAlimentos()) {
            ea.setEnfermedades(cargarEnfermedad(30));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 30));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
        if(uil.getAlergiaPolvos()) {
            ea.setEnfermedades(cargarEnfermedad(31));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 31));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
        if(uil.getAlergiaMedicamentos()) {
            ea.setEnfermedades(cargarEnfermedad(33));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 33));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
        if(uil.getAlergiaOtros()) {
            ea.setEnfermedades(cargarEnfermedad(32));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 32));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }

    }

    private void cargarAntecedentesCardiovasculares() {
        Episodiosantecedentes ea = new Episodiosantecedentes();

         if(uil.getHTA()) {
            ea.setEnfermedades(cargarEnfermedad(20));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 20));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
         if(uil.getPalpitaciones()) {
            ea.setEnfermedades(cargarEnfermedad(34));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 34));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
         if(uil.getTaquicardia()) {
            ea.setEnfermedades(cargarEnfermedad(35));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 35));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
         if(uil.getArritmias()) {
            ea.setEnfermedades(cargarEnfermedad(36));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 36));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
         if(uil.getSoplo()) {
            ea.setEnfermedades(cargarEnfermedad(37));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 37));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
         if(uil.getFibreReumatica()) {
            ea.setEnfermedades(cargarEnfermedad(38));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 38));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
         if(uil.getInfartoMiocardio()) {
            ea.setEnfermedades(cargarEnfermedad(39));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 39));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
         if(uil.getOtrosCardio().length()>0) {
            ea.setEnfermedades(cargarEnfermedad(11));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 11));
            ea.setDetalles(uil.getOtrosCardio());
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
    }

    private void cargarAntecedentesPleuropulmonares() {
        Episodiosantecedentes ea = new Episodiosantecedentes();

         if(uil.getAsma()) {
            ea.setEnfermedades(cargarEnfermedad(40));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 40));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
         if(uil.getNeumopatia()) {
            ea.setEnfermedades(cargarEnfermedad(41));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 41));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
         if(uil.getBronquitis()) {
            ea.setEnfermedades(cargarEnfermedad(42));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 42));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
    }

    private void cargarAntecedentesDigestivos() {
        Episodiosantecedentes ea = new Episodiosantecedentes();

         if(uil.getHepatitis()) {
            ea.setEnfermedades(cargarEnfermedad(43));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 43));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
         if(uil.getGastritis()) {
            ea.setEnfermedades(cargarEnfermedad(44));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 44));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
         if(uil.getUlcera()) {
            ea.setEnfermedades(cargarEnfermedad(45));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 45));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
         if(uil.getDiverticulos()) {
            ea.setEnfermedades(cargarEnfermedad(46));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 46));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
         if(uil.getAlteracionesDelTransito()) {
            ea.setEnfermedades(cargarEnfermedad(47));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 47));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
         if(uil.getHemorroides()) {
            ea.setEnfermedades(cargarEnfermedad(48));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 48));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
         if(uil.getLitiasisVesicular()) {
            ea.setEnfermedades(cargarEnfermedad(49));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 49));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }


    }

    private void cargarAntecedentesRenales() {
         Episodiosantecedentes ea = new Episodiosantecedentes();

         if(uil.getInfeccionRinon()) {
            ea.setEnfermedades(cargarEnfermedad(50));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 50));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }

         if(uil.getLitiasisRenal()) {
            ea.setEnfermedades(cargarEnfermedad(51));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 51));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }

         if(uil.getColicosNefriticos()) {
            ea.setEnfermedades(cargarEnfermedad(52));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 52));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
         if(uil.getInfeccionUrinariaBaja()) {
            ea.setEnfermedades(cargarEnfermedad(53));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 53));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
    }

    private void cargarAntecedentesOseos() {
         Episodiosantecedentes ea = new Episodiosantecedentes();

         if(uil.getTendinitis()) {
            ea.setEnfermedades(cargarEnfermedad(54));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 54));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }

         if(uil.getLumbalgias()) {
            ea.setEnfermedades(cargarEnfermedad(55));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 55));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }

         if(uil.getFracturas()) {
            ea.setEnfermedades(cargarEnfermedad(56));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 56));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
         if(uil.getArtrosis()) {
            ea.setEnfermedades(cargarEnfermedad(57));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 57));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
    }

    private void cargarAntecedentesOftalmologicos() {
         Episodiosantecedentes ea = new Episodiosantecedentes();

         if(uil.getUsaLentes()) {
            ea.setEnfermedades(cargarEnfermedad(58));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 58));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
    }

    private void cargarAntecedentesNeurologicos() {
         Episodiosantecedentes ea = new Episodiosantecedentes();

         if(uil.getVertigo()) {
            ea.setEnfermedades(cargarEnfermedad(59));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 59));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }

         if(uil.getEpilepsia()) {
            ea.setEnfermedades(cargarEnfermedad(60));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 60));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }

    }

    private void cargarAntecedentesInternaciones() {
         Episodiosantecedentes ea = new Episodiosantecedentes();

         if(uil.getInternaciones()) {
            ea.setEnfermedades(cargarEnfermedad(61));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 61));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
    }

    private void cargarAntecedentesAuditivos() {
         Episodiosantecedentes ea = new Episodiosantecedentes();

         if(uil.getAntecedentesAuditivos()) {
            ea.setEnfermedades(cargarEnfermedad(62));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 62));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
    }

    private void cargarAntecedentesVenereos() {
         Episodiosantecedentes ea = new Episodiosantecedentes();

         if(uil.getAntecedentesVenereos()) {
            ea.setEnfermedades(cargarEnfermedad(63));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 63));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
    }

    private void cargarAntecedentesSiquiatricos() {
         Episodiosantecedentes ea = new Episodiosantecedentes();

         if(uil.getAntecedentesSiquiatricos()) {
            ea.setEnfermedades(cargarEnfermedad(64));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 64));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
    }

    private void cargarAntecedentesQuirurgicos() {
         Episodiosantecedentes ea = new Episodiosantecedentes();

         if(uil.getAntecedentesQuirurgicos()) {
            ea.setEnfermedades(cargarEnfermedad(65));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 65));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }
    }
    
    private void cargarAntecedentesObstetricos() {
         Episodiosantecedentes ea = new Episodiosantecedentes();

         if(uil.getEmbarazos()) {
            ea.setEnfermedades(cargarEnfermedad(66));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 66));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }

         if(uil.getAbortos()) {
            ea.setEnfermedades(cargarEnfermedad(67));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 67));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }

         if(uil.getDIU()) {
            ea.setEnfermedades(cargarEnfermedad(68));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 68));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }

         if(uil.getACO()) {
            ea.setEnfermedades(cargarEnfermedad(69));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(epis.getId(), 69));
            epis.getEpisodiosantecedentesCollection().add(ea);
        }

         if(uil.getAutoexamenDeMama()) {

            Episodiosestudios ee = new Episodiosestudios();
            ee.setEpisodios(epis);
            ee.setClinica("");
            ee.setEpisodiosestudiosPK(new EpisodiosestudiosPK(epis.getId(), 20));
            ee.setEstudios(cargarEstudio(20));
            ee.setMedico("");
            ee.setVencimiento(new Date());
            epis.getEpisodiosestudiosCollection().add(ee);
        }

         if(uil.getNoCorrespondePAP()) {

            try {
                Episodiosestudios ee = new Episodiosestudios();
                ee.setEpisodios(epis);
                ee.setClinica("NO CORRESPONDE");
                ee.setMedico("NO CORRESPONDE");
                ee.setVencimiento(new Date());
                ee.setEpisodiosestudiosPK(new EpisodiosestudiosPK(epis.getId(), 21));
                ee.setEstudios(cargarEstudio(21));
                epis.getEpisodiosestudiosCollection().add(ee);
            } catch (Exception ex) {
                Logger.getLogger(CarnetDeSaludCtrl.class.getName()).log(Level.SEVERE, null, ex);
            }
         }

         if(uil.getPapaNicolau()) {
            try {
                Episodiosestudios ee = new Episodiosestudios();
                ee.setEpisodios(epis);
                ee.setClinica(uil.getPapaNicolauClinica());
                ee.setMedico(uil.getPapaNicolauMedico());
                ee.setVencimiento(uil.getPapaNicolauFecha());
                ee.setEpisodiosestudiosPK(new EpisodiosestudiosPK(epis.getId(), 21));
                ee.setEstudios(cargarEstudio(21));
                epis.getEpisodiosestudiosCollection().add(ee);
            } catch (ParseException ex) {
                Logger.getLogger(CarnetDeSaludCtrl.class.getName()).log(Level.SEVERE, null, ex);

            }
        }

         if(uil.getNoCorrespondeMAMO()) {

            try {
                Episodiosestudios ee = new Episodiosestudios();
                ee.setEpisodios(epis);
                ee.setClinica("NO CORRESPONDE");
                ee.setMedico("NO CORRESPONDE");
                ee.setVencimiento(new Date());
                ee.setEpisodiosestudiosPK(new EpisodiosestudiosPK(epis.getId(), 22));
                ee.setEstudios(cargarEstudio(22));
                epis.getEpisodiosestudiosCollection().add(ee);
            } catch (Exception ex) {
                Logger.getLogger(CarnetDeSaludCtrl.class.getName()).log(Level.SEVERE, null, ex);
            }
         }

         if(uil.getMamo()) {
            try {
                Episodiosestudios ee = new Episodiosestudios();
                ee.setEpisodios(epis);
                ee.setClinica(uil.getMamografiaClinica());
                ee.setMedico(uil.getMamografiaMedico());
                ee.setVencimiento(uil.getMamografiaFecha());
                ee.setEpisodiosestudiosPK(new EpisodiosestudiosPK(epis.getId(), 22));
                ee.setEstudios(cargarEstudio(22));
                epis.getEpisodiosestudiosCollection().add(ee);
            } catch (ParseException ex) {
                Logger.getLogger(CarnetDeSaludCtrl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Enfermedades cargarEnfermedad(int id) {

        Enfermedades e = (Enfermedades)Sesion.getServiceDelegatorRemote().invoke("CargarEnfermedad", id);

        return(e);
    }

    private void cargarEnfermedadesAbdomen() {

        Episodiosenfermedades ee = new Episodiosenfermedades();

         if(uil.getCicatrizAbdomen().length() > 0) {
            ee.setEnfermedades(cargarEnfermedad(85));
            ee.setEpisodios(epis);
            ee.setDetalles(uil.getCicatrizAbdomen());
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(85, epis.getId()));
            epis.getEpisodiosenfermedadesCollection().add(ee);
        }

         if(!uil.getAbdomenIndoloro()) {
            ee.setEnfermedades(cargarEnfermedad(86));
            ee.setEpisodios(epis);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(86, epis.getId()));
            epis.getEpisodiosenfermedadesCollection().add(ee);
        }

         if(uil.getAbdomenTumores()) {
            ee.setEnfermedades(cargarEnfermedad(87));
            ee.setEpisodios(epis);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(87, epis.getId()));
            epis.getEpisodiosenfermedadesCollection().add(ee);
        }

        if(!uil.getAbdomenBlando()) {
            ee.setEnfermedades(cargarEnfermedad(88));
            ee.setEpisodios(epis);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(88, epis.getId()));
            epis.getEpisodiosenfermedadesCollection().add(ee);
        }
         if(!uil.getAbdomenDepresible()) {
            ee.setEnfermedades(cargarEnfermedad(89));
            ee.setEpisodios(epis);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(89, epis.getId()));
            epis.getEpisodiosenfermedadesCollection().add(ee);
        }

        if(uil.getTumoresAbdominales()) {

            if(uil.getTumorZ1()) {
                ee.setEnfermedades(cargarEnfermedad(104));
                ee.setEpisodios(epis);
                ee.setDetalles("");
                ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(104, epis.getId()));
                epis.getEpisodiosenfermedadesCollection().add(ee);
            }
            if(uil.getTumorZ2()) {
                ee.setEnfermedades(cargarEnfermedad(105));
                ee.setEpisodios(epis);
                ee.setDetalles("");
                ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(105, epis.getId()));
                epis.getEpisodiosenfermedadesCollection().add(ee);
            }
            if(uil.getTumorZ3()) {
                ee.setEnfermedades(cargarEnfermedad(106));
                ee.setEpisodios(epis);
                ee.setDetalles("");
                ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(106, epis.getId()));
                epis.getEpisodiosenfermedadesCollection().add(ee);
            }
            if(uil.getTumorZ4()) {
                ee.setEnfermedades(cargarEnfermedad(107));
                ee.setEpisodios(epis);
                ee.setDetalles("");
                ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(107, epis.getId()));
                epis.getEpisodiosenfermedadesCollection().add(ee);
            }
            if(uil.getTumorZ5()) {
                ee.setEnfermedades(cargarEnfermedad(108));
                ee.setEpisodios(epis);
                ee.setDetalles("");
                ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(108, epis.getId()));
                epis.getEpisodiosenfermedadesCollection().add(ee);
            }
            if(uil.getTumorZ6()) {
                ee.setEnfermedades(cargarEnfermedad(109));
                ee.setEpisodios(epis);
                ee.setDetalles("");
                ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(109, epis.getId()));
                epis.getEpisodiosenfermedadesCollection().add(ee);
            }
            if(uil.getTumorZ7()) {
                ee.setEnfermedades(cargarEnfermedad(110));
                ee.setEpisodios(epis);
                ee.setDetalles("");
                ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(110, epis.getId()));
                epis.getEpisodiosenfermedadesCollection().add(ee);
            }
            if(uil.getTumorZ8()) {
                ee.setEnfermedades(cargarEnfermedad(111));
                ee.setEpisodios(epis);
                ee.setDetalles("");
                ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(111, epis.getId()));
                epis.getEpisodiosenfermedadesCollection().add(ee);
            }
            if(uil.getTumorZ9()) {
                ee.setEnfermedades(cargarEnfermedad(112));
                ee.setEpisodios(epis);
                ee.setDetalles("");
                ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(112, epis.getId()));
                epis.getEpisodiosenfermedadesCollection().add(ee);
            }

        }
    }

    private void cargarEnfermedadesLinfoganglionar() {

        Episodiosenfermedades ee = new Episodiosenfermedades();

         if(uil.getLinfoganglionar().length() > 0) {
            ee.setEnfermedades(cargarEnfermedad(75));
            ee.setEpisodios(epis);
            ee.setDetalles(uil.getLinfoganglionar());
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(75, epis.getId()));
            epis.getEpisodiosenfermedadesCollection().add(ee);
        }

         if(uil.getCuello()) {
            int codenf = 1;

            if(uil.getCuelloGrado() == 4)
               codenf=102;
            else
                if(uil.getCuelloGrado() == 3)
                    codenf=78;
                else
                    if(uil.getCuelloGrado() == 2)
                        codenf=77;
                    else
                        if(uil.getCuelloGrado() == 1)
                            codenf=76;

            ee.setEpisodios(epis);
            ee.setDetalles("");
            ee.setEnfermedades(cargarEnfermedad(codenf));
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(codenf, epis.getId()));
            epis.getEpisodiosenfermedadesCollection().add(ee);
        }
    }

    private void cargarEnfermedadesPielMucosas() {

        Episodiosenfermedades ee = new Episodiosenfermedades();

         if(uil.getMicosis()) {
            ee.setEnfermedades(cargarEnfermedad(71));
            ee.setEpisodios(epis);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(71, epis.getId()));
            epis.getEpisodiosenfermedadesCollection().add(ee);
        }

         if(uil.getAlergias()) {
            ee.setEnfermedades(cargarEnfermedad(72));
            ee.setEpisodios(epis);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(72, epis.getId()));
            epis.getEpisodiosenfermedadesCollection().add(ee);
        }

         if(uil.getAlergias()) {
            ee.setEnfermedades(cargarEnfermedad(73));
            ee.setEpisodios(epis);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(73, epis.getId()));
            epis.getEpisodiosenfermedadesCollection().add(ee);
        }

         if(uil.getAlergias()) {
            ee.setEnfermedades(cargarEnfermedad(74));
            ee.setEpisodios(epis);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(74, epis.getId()));
            epis.getEpisodiosenfermedadesCollection().add(ee);
        }
    }

    private void cargarEnfermedadesPleuropulmonares() {

        Episodiosenfermedades ee = new Episodiosenfermedades();

        if(uil.getMAV()) {
            ee.setEnfermedades(cargarEnfermedad(82));
            ee.setEpisodios(epis);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(82, epis.getId()));
            epis.getEpisodiosenfermedadesCollection().add(ee);
        }
        if(uil.getEnfPleuropulmonares().length() > 0) {
            ee.setEnfermedades(cargarEnfermedad(83));
            ee.setEpisodios(epis);
            ee.setDetalles(uil.getEnfPleuropulmonares());
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(83, epis.getId()));
            epis.getEpisodiosenfermedadesCollection().add(ee);
        }
    }

    private void cargarEnfermedadesVenosas() {

        Episodiosenfermedades ee = new Episodiosenfermedades();

        if(uil.getVenosas()) {
            ee.setEnfermedades(cargarEnfermedad(84));
            ee.setEpisodios(epis);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(84, epis.getId()));
            epis.getEpisodiosenfermedadesCollection().add(ee);
        }
    }

    private void cargarEnfermedadesSistemaVascular() {

        Episodiosenfermedades ee = new Episodiosenfermedades();
        
        if(uil.getRI()) {
            ee.setEnfermedades(cargarEnfermedad(79));
            ee.setEpisodios(epis);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(79, epis.getId()));
            epis.getEpisodiosenfermedadesCollection().add(ee);
        }

        if(uil.getSoplos()) {
            ee.setEnfermedades(cargarEnfermedad(80));
            ee.setEpisodios(epis);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(80, epis.getId()));
            epis.getEpisodiosenfermedadesCollection().add(ee);
        }
    }


    private void cargarEnfermedadesMamas() {

        Episodiosenfermedades ee = new Episodiosenfermedades();

        if(uil.getMamasTumores()) {
            ee.setEnfermedades(cargarEnfermedad(81));
            ee.setEpisodios(epis);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(81, epis.getId()));
            epis.getEpisodiosenfermedadesCollection().add(ee);
        }

    }

    private Estudios cargarEstudio(int id) {


        Estudios e = (Estudios)Sesion.getServiceDelegatorRemote().invoke("CargarEstudio", id);

        return(e);
    }

    private void cargarExamenFisico() {

        Episodioexamenfisico eef = new Episodioexamenfisico();
        eef.setEpisodioexamenfisicoPK(new EpisodioexamenfisicoPK(epis.getId(), 1));
        eef.setEpisodios(epis);
        eef.setExamenfisico(cargarExamenFisico(1));
        eef.setValor(uil.getPeso());
        epis.getEpisodioexamenfisicoCollection().add(eef);

        eef = new Episodioexamenfisico();
        eef.setEpisodioexamenfisicoPK(new EpisodioexamenfisicoPK(epis.getId(), 2));
        eef.setEpisodios(epis);
        eef.setExamenfisico(cargarExamenFisico(2));
        eef.setValor(uil.getTalla());
        epis.getEpisodioexamenfisicoCollection().add(eef);

        eef = new Episodioexamenfisico();
        eef.setEpisodioexamenfisicoPK(new EpisodioexamenfisicoPK(epis.getId(), 3));
        eef.setEpisodios(epis);
        eef.setExamenfisico(cargarExamenFisico(3));
        eef.setValor(uil.getIMC());
        epis.getEpisodioexamenfisicoCollection().add(eef);

        eef = new Episodioexamenfisico();
        eef.setEpisodioexamenfisicoPK(new EpisodioexamenfisicoPK(epis.getId(), 4));
        eef.setEpisodios(epis);
        eef.setExamenfisico(cargarExamenFisico(4));
        eef.setValor(uil.getPerimetroAbdominal());
        epis.getEpisodioexamenfisicoCollection().add(eef);

        eef = new Episodioexamenfisico();
        eef.setEpisodioexamenfisicoPK(new EpisodioexamenfisicoPK(epis.getId(), 5));
        eef.setEpisodios(epis);
        eef.setExamenfisico(cargarExamenFisico(5));
        eef.setValor(uil.getPulso());
        epis.getEpisodioexamenfisicoCollection().add(eef);

        eef = new Episodioexamenfisico();
        eef.setEpisodioexamenfisicoPK(new EpisodioexamenfisicoPK(epis.getId(), 6));
        eef.setEpisodios(epis);
        eef.setExamenfisico(cargarExamenFisico(6));
        eef.setValor(uil.getFrecuencia());
        epis.getEpisodioexamenfisicoCollection().add(eef);

    }

    private Examenfisico cargarExamenFisico(int id) {

        Examenfisico ef = (Examenfisico) Sesion.getServiceDelegatorRemote().invoke("CargarExamenFisico", id);

        return(ef);
    }

    private Medicamentos cargarMedicamento(int id) {

        Medicamentos vs = (Medicamentos) Sesion.getServiceDelegatorRemote().invoke("CargarMedicamento", id);
        return(vs);
    }

    private Collection<Episodiosmedicamentos> cargarMedicamentos() {


        if(uil.getMedicamentos().length()>0) {
             Episodiosmedicamentos em = new Episodiosmedicamentos();
             em.setDosis("");
             em.setEpisodios(epis);
             em.setMedicamentos(cargarMedicamento(6));
             EpisodiosmedicamentosPK episodiosmedicamentosPK = new EpisodiosmedicamentosPK();
             episodiosmedicamentosPK.setEpisodio(epis.getId());
             episodiosmedicamentosPK.setMedicamento(6);
             em.setEpisodiosmedicamentosPK(episodiosmedicamentosPK);
        }

        return(epis.getEpisodiosmedicamentosCollection());
    }

    private void cargarPresiones() {
        Episodiopresion ep = new Episodiopresion();

        ep.setEpisodios(epis);
        ep.setEpisodio(epis.getId());
        ep.setDiastolicaprimeratoma(uil.getDiastolicaPT());
        ep.setDiastolicasegundatoma(uil.getDiastolicaST());
        ep.setSistolicaprimeratoma(uil.getSistolicaPT());
        ep.setSistolicasegundatoma(uil.getSistolicaST());

        epis.setEpisodiopresion(ep);

        modificarEpisodiosSegunPresiones();
    }

    private void modificarEpisodiosSegunPresiones() {

        int dst = epis.getEpisodiopresion().getDiastolicasegundatoma();
        int sst = epis.getEpisodiopresion().getSistolicasegundatoma();

        int idact = epis.getTipoepisodio().getId() * 100 + 1;
        if(sst > 140 ||
           dst > 90) {
            Actividades act = (Actividades) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarActividad", idact);
            epis.setTipoepisodio(act);
            Personas p = epis.getPaciente().getPersonas();
            String txt = "CREATININEMIA " + p.getApellidos() + " " + p.getNombres() + " " + p.getDocumento();
            new Mensajero().enviar("CONSULTA MEDICA", "laboratorio", txt);
        }
    }

    private void cargarVacunas() {
        Episodiosvacunas ev;
        
        if(uil.getATT()) {
            ev = new Episodiosvacunas();
            ev.setEpisodios(epis);
            ev.setEpisodiosvacunasPK(new EpisodiosvacunasPK(epis.getId(), 1));
            ev.setVacunas(cargarVacunas(1));
            ev.setVence(uil.getVenceATT());
            
            epis.getEpisodiosvacunasCollection().add(ev);
        }

        if(uil.getGripe()) {
            ev = new Episodiosvacunas();
            ev.setEpisodios(epis);
            ev.setEpisodiosvacunasPK(new EpisodiosvacunasPK(epis.getId(), 4));
            ev.setVacunas(cargarVacunas(4));

            epis.getEpisodiosvacunasCollection().add(ev);
        }

        if(uil.getHepatitisA()) {
            ev = new Episodiosvacunas();
            ev.setEpisodios(epis);
            ev.setEpisodiosvacunasPK(new EpisodiosvacunasPK(epis.getId(), 2));
            ev.setVacunas(cargarVacunas(2));

            epis.getEpisodiosvacunasCollection().add(ev);
        }

        if(uil.getHepatitisC()) {
            ev = new Episodiosvacunas();
            ev.setEpisodios(epis);
            ev.setEpisodiosvacunasPK(new EpisodiosvacunasPK(epis.getId(), 3));
            ev.setVacunas(cargarVacunas(3));

            epis.getEpisodiosvacunasCollection().add(ev);
        }

        if(uil.getTifoidea()) {
            ev = new Episodiosvacunas();
            ev.setEpisodios(epis);
            ev.setEpisodiosvacunasPK(new EpisodiosvacunasPK(epis.getId(), 5));
            ev.setVacunas(cargarVacunas(5));

            epis.getEpisodiosvacunasCollection().add(ev);
        }

    }

    private Vacunas cargarVacunas(int id) {

        Vacunas v = (Vacunas) Sesion.getServiceDelegatorRemote().invoke("CargarVacuna", id);

        return(v);
    }

    private Collection<Episodiosvicios> cargarVicios() {

        Episodiosvicios ev = new Episodiosvicios();

        if(uil.getFumador()) {
            ev.setEpisodios(epis);
            ev.setViciossociales(cargarVicio(2));
            ev.setEpisodiosviciosPK(new EpisodiosviciosPK(2,epis.getId()));
            ev.setDetalles(".");
            epis.getEpisodiosviciosCollection().add(ev);
        }

        if(uil.getBebedor()) {
            ev.setEpisodios(epis);
            ev.setViciossociales(cargarVicio(1));
            ev.setEpisodiosviciosPK(new EpisodiosviciosPK(1, epis.getId()));
            ev.setDetalles(".");
            epis.getEpisodiosviciosCollection().add(ev);
        }

        return(epis.getEpisodiosviciosCollection());
    }

    Viciossociales cargarVicio(int id) {


        Viciossociales vs = (Viciossociales) Sesion.getServiceDelegatorRemote().invoke("CargarVicioSocial", id);
        return(vs);
    }

    private void cargarVision() {

        Episodioagudezasvisuales ev = new Episodioagudezasvisuales();

        ev.setEpisodios(epis);
        ev.setEpisodio(epis.getId());
        ev.setScod(uil.getOD());
        ev.setScoi(uil.getOI());
        ev.setCcod(uil.getCCOD());
        ev.setCcoi(uil.getCCOI());
        epis.setEpisodioagudezasvisuales(ev);
    }

    private void cargarZonasHernarias() {
        
        Episodiosenfermedades ee = new Episodiosenfermedades();

        if(uil.getHerniaID()) {
            ee.setEnfermedades(cargarEnfermedad(90));
            ee.setEpisodios(epis);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(90, epis.getId()));
            epis.getEpisodiosenfermedadesCollection().add(ee);
        }

        if(uil.getHerniaII()) {
            ee.setEnfermedades(cargarEnfermedad(91));
            ee.setEpisodios(epis);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(91, epis.getId()));
            epis.getEpisodiosenfermedadesCollection().add(ee);
        }
        if(!uil.getFosasLumbaresLibres()) {
            ee.setEnfermedades(cargarEnfermedad(92));
            ee.setEpisodios(epis);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(92, epis.getId()));
            epis.getEpisodiosenfermedadesCollection().add(ee);
        }

    }

    private void cargarOsteoarticular() {
        
        Episodiosenfermedades ee = new Episodiosenfermedades();

        if(uil.getOsteoArticular()) {
            ee.setEnfermedades(cargarEnfermedad(114));
            ee.setEpisodios(epis);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(93, epis.getId()));
            epis.getEpisodiosenfermedadesCollection().add(ee);
        }
    }

    private void cargarSectorMuscular() {

        Episodiosenfermedades ee = new Episodiosenfermedades();

        if(uil.getMuscular()) {
            ee.setEnfermedades(cargarEnfermedad(115));
            ee.setEpisodios(epis);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(94, epis.getId()));
            epis.getEpisodiosenfermedadesCollection().add(ee);
        }
    }

    private void cambiarEpisodioDiabetico() {

       int idact = epis.getTipoepisodio().getId() * 100 + 1;
       Actividades act = (Actividades) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarActividad", idact);
       if(act!=null)
            epis.setTipoepisodio(act);

       Personas pers = epis.getPaciente().getPersonas();
       String txt = "CREATININEMIA " + pers.getApellidos() + " " + pers.getNombres() + " " + pers.getDocumento();
       new Mensajero().enviar("CONSULTA MEDICA", "laboratorio", txt);
    }
    private int calcularEdad(Personas p) {

        int edad = 0;

        GregorianCalendar hoy = new GregorianCalendar();
        Date ffnac = p.getFechanacimiento();
        GregorianCalendar fnac = new GregorianCalendar();
        fnac.setTime(ffnac);

        hoy.setTime(new Date());

        long tiempo = (hoy.getTimeInMillis() - fnac.getTimeInMillis());

        long edadL = (long) (tiempo / (1000 * 60 * 60));

        edadL /= (365 * 24);
        edad = (int)edadL;
        return(edad);
    }

    //@Override
    @Override
    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private boolean avisarElectro(Agenda a) {

        int x = a.getEpisodio().getTipoepisodio().getId();
        Personas unap = a.getEpisodio().getPaciente().getPersonas();

        return((calcularEdad(unap) >= 40) && (x==4000 || x==4 || x==401  || x==402));
    }

    private boolean avisarParaclinicos(Agenda a) {

        boolean resp = false;
        if(a.getEpisodio().getEpisodiosExAdicionalesCollection()!=null) {
            resp = !a.getEpisodio().getEpisodiosExAdicionalesCollection().isEmpty();
        }
        return(resp);
    }

    @Override
    public void vaciarDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
