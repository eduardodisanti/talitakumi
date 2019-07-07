/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.control.medicos;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import talitakumi.model.core.Analisishechos;
import talitakumi.model.core.Carnetdesalud;
import talitakumi.model.core.Episodioagudezasvisuales;
import talitakumi.model.core.Episodioexamenfisico;
import talitakumi.model.core.Episodiopresion;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Episodiosantecedentes;
import talitakumi.model.core.Episodiosenfermedades;
import talitakumi.model.core.Episodiosestudios;
import talitakumi.model.core.Episodiosmedicamentos;
import talitakumi.model.core.Episodiosvacunas;
import talitakumi.model.core.Episodiosvicios;
import talitakumi.model.core.Estudioshechos;
import talitakumi.servicios.Reporter;

/**
 *
 * @author rupertus
 */
class ReportCDSCtrl {

    private HashMap<String, Object> params = new HashMap<String, Object>();
    private Episodios epi;

    public ReportCDSCtrl(Episodios e) {

        epi = e;
        obtenerAntecedentesFamiliares();
        obtenerAntecedentesPersonales();
        obtenerAntecedentesPesonalesEstudios();
        obtenerExamenFisico();
        obtenerVicios();
        obtenerAgudezasVisuales();

        obtenerEnfermedades();
        obtenerVacunas();
        obtenerEstudiosMedicos();
        obtenerLaboratorio();
        obtenerPresiones();

        Carnetdesalud cds = epi.getCarnetdesalud();

        Calendar hoy = Calendar.getInstance();
        Calendar fechaNac = Calendar.getInstance();

        fechaNac.setTime(e.getPaciente().getPersonas().getFechanacimiento());

    	int years = hoy.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
    	if (hoy.get(Calendar.MONTH) < fechaNac.get(Calendar.MONTH)) years --;
    	if (hoy.get(Calendar.MONTH) == fechaNac.get(Calendar.MONTH) &&
    		hoy.get(Calendar.DATE) < fechaNac.get(Calendar.DATE)	) years --;



        String nombres = e.getPaciente().getPersonas().getNombres() + " " + e.getPaciente().getPersonas().getApellidos();
        params.put("vigencia_medico", cds.getVigencia());
        params.put("carta_medico_tratante", cds.getCartamedicotratante()+"");
        params.put("negado_causa_medica", cds.getCausamedica()+"");
        params.put("impresion_general", cds.getImpresiongeneral());
        params.put("sug_control_peso", cds.getSugerenciacontpeso()+"");
        params.put("sug_ejercicios", cds.getSugerenciaejercicios()+"");
        params.put("sug_laboratorio", cds.getSugerenciaexlab()+"");
        params.put("fecha_expedido", e.getFecha());
        params.put("episodio", e.getId());
        params.put("nombre", nombres);
        params.put("documento", e.getPaciente().getPersonas().getDocumento());
        params.put("domicilio", e.getPaciente().getPersonas().getDomicilio());
        params.put("telefono", e.getPaciente().getPersonas().getTelefono());
        params.put("fechanac", e.getPaciente().getPersonas().getFechanacimiento());
        params.put("edad", years);
        params.put("sexo", e.getPaciente().getPersonas().getSexo()+"");
        

        new Reporter("cds.jasper").listar(params);

        System.out.println("Voy a imprimir historia odontologica");
        params = new HashMap<String, Object>();
        params.put("idepisodio", epi.getId());
        new Reporter("HistoriaOdontologica.jasper").listar(params);
    }

    private void obtenerAgudezasVisuales() {

        Episodioagudezasvisuales eav = epi.getEpisodioagudezasvisuales();
        if(eav!=null){
        params.put("ojoderechoSC", eav.getScod());
        params.put("ojoizquierdoSC", eav.getScoi());
        params.put("ojoderechoCC", eav.getCcod());
        params.put("ojoizquierdoCC", eav.getCcoi());
        }
    }

    private void obtenerEnfermedades() {

        Collection<Episodiosenfermedades> eac = epi.getEpisodiosenfermedadesCollection();

        String tag;
        Object res = false;
        for(Episodiosenfermedades ea : eac) {

            int codenf = ea.getEnfermedades().getId();

            tag = ea.getEnfermedades().getTagEnfermedad();
            if(ea.getDetalles()!=null) {
                if(ea.getDetalles().length()>0)
                    res = ea.getDetalles();
            } else
                   res = true;
            params.put(tag, res);
        }
    }

    private void obtenerAntecedentesFamiliares() {

        Collection<Episodiosantecedentes> eac = epi.getEpisodiosantecedentesCollection();

        for(Episodiosantecedentes ea : eac) {

            if(ea.getEnfermedades().getId()==93)
                params.put("ant_fam_hta", true);
            else
                if(ea.getEnfermedades().getId()==96)
                    params.put("ant_fam_diabetes", true);
        }
    }

    private void obtenerAntecedentesPersonales() {
        
        Collection<Episodiosantecedentes> eac = epi.getEpisodiosantecedentesCollection();

        for(Episodiosantecedentes ea : eac) {

            int codenf = ea.getEnfermedades().getId();

            switch (codenf) {

                case 99 :
                            params.put("ant_per_tiroideos_hiper", true);
                        break;
                case 100 :
                            params.put("ant_tiroideos_hiper", true);
                        break;
                case 101 :
                            params.put("ant_tiroideos_hipo", true);
                        break;
                case 93 :
                            params.put("ant_per_hta", true);
                        break;
                case 94 :
                            params.put("ant_per_diabetes", true);
                        break;
                case 98 :
                            params.put("ant_per_diabetes_tipo", 2);
                            params.put("ant_per_diabetes", true);
                        break;
                case 97 :
                           params.put("ant_per_diabetes", true);
                           params.put("ant_per_diabetes_tipo", 1);
                        break;
                case 30 :
                            params.put("ant_per_alergia_alimentos", true);
                        break;
                case 31 :
                            params.put("ant_per_alergia_polvos", true);
                        break;
                case 32 :
                            params.put("ant_per_alergia_otros", true);
                        break;
                case 33 :
                            params.put("ant_per_alergia_medicamentos", true);
                        break;
                case 20 :
                            params.put("ant_per_cardio_hta", true);
                        break;
                case 34 :
                            params.put("ant_per_cardio_palpitaciones", true);
                        break;
                case 35 :
                            params.put("ant_per_cardio_taquicardia", true);
                        break;
                case 36 :
                            params.put("ant_per_cardio_arritmias", true);
                        break;
                case 37 :
                            params.put("ant_per_cardio_soplo", true);
                        break;
                case 38 :
                            params.put("ant_per_cardio_fiebre_reumatica", true);
                        break;
                case 39 :
                            params.put("ant_per_cardio_infarto_miocardio", true);
                        break;
                case 11 :
                            params.put("ant_per_cardio_otros", ea.getDetalles());
                        break;
                case 40 :
                            params.put("ant_per_pleuro_asma", true);
                        break;
                case 41 :
                            params.put("ant_per_pleuro_neumopatia", true);
                        break;
                case 42 :
                            params.put("ant_per_pleuro_bronquitis", true);
                        break;
                case 43 :
                            params.put("ant_per_digestivos_hepatitis", true);
                        break;
                case 44 :
                            params.put("ant_per_digestivos_gastritis", true);
                        break;
                case 45 :
                            params.put("ant_per_digestivos_ulcera", true);
                        break;
                case 46 :
                            params.put("ant_per_digestivos_diverticuos", true);
                        break;
                case 47 :
                            params.put("ant_per_digestivos_alteraciones_transito", true);
                        break;
                case 48 :
                            params.put("ant_per_digestivos_hemorroides", true);
                        break;
                case 49 :
                            params.put("ant_per_digestivos_litiasisvesicular", true);
                        break;
                case 50 :
                            params.put("ant_per_renales_infeccionrinon", true);
                        break;
                case 51 :
                            params.put("ant_per_renales_litiasisrenal", true);
                        break;
                case 52 :
                            params.put("ant_per_renales_colicosnefriticos", true);
                        break;
                case 53 :
                            params.put("ant_per_renales_infeccionurinariabaja", true);
                        break;
                case 54 :
                            params.put("ant_per_oseos_tendinitis", true);
                        break;
                case 55 :
                            params.put("ant_per_oseos_lumbalgias", true);
                        break;
                case 56 :
                            params.put("ant_per_oseos_fracturas", true);
                        break;
                case 57 :
                            params.put("ant_per_oseos_artrosis", true);
                        break;
                        
                case 58 :
                            params.put("ant_per_oftalmologicos_lentes", true);
                        break;

                case 59 :
                            params.put("ant_per_neurologicos_vertigo", true);
                        break;
                case 60 :
                            params.put("ant_per_neurologicos_epilepsia", true);
                        break;

                case 61 :
                            params.put("ant_per_internaciones", true);
                        break;

                case 62 :
                            params.put("ant_per_auditivos", true);
                        break;

                case 63 :
                            params.put("ant_per_venereos", true);
                        break;

                case 64 :
                            params.put("ant_per_psiquiatricos", true);
                        break;

                case 65 :
                            params.put("ant_per_quirurgicos", true);
                        break;

                case 66 :
                            params.put("ant_obst_embarazos", true);
                        break;
                case 67 :
                            params.put("ant_obst_abortos", true);
                        break;
                case 68 :
                            params.put("ant_obst_diu", true);
                        break;
                case 69 :
                            params.put("ant_obst_aco", true);
                        break;
                case 70 :
                            params.put("ant_obst_embarazos", true);
                        break;

                case 71 :
                            params.put("ant_piel_mucosasMicosis", true);
                        break;
                case 72 :
                            params.put("ant_piel_mucosasAlergias", true);
                        break;
                case 73 :
                            params.put("ant_piel_mucosasAlergias", true);
                        break;
                case 74 :
                            params.put("ant_piel_mucosasAlergias", true);
                        break;


            }

        }
    }

    private void cargarMedicamentos() {

        Collection<Episodiosmedicamentos> epm = epi.getEpisodiosmedicamentosCollection();

        String s = "";
        for(Episodiosmedicamentos epim : epm) {

            s+= epim.getMedicamentos().getDescripcion() + " " + epim.getDosis() + " - ";
        }

        params.put("ant_per_medicamentos", s);
    }

    private void obtenerAntecedentesPesonalesEstudios() {

        for(Episodiosestudios ee : epi.getEpisodiosestudiosCollection()) {

            int cod = ee.getEstudios().getId();

            switch(cod) {

                   case 20 :
                            params.put("ant_obst_autoexamen_mama", true);
                   break;

                   case 21 :
                            params.put("ant_obst_PAP", true);
                            params.put("ant_obst_PAPvence", ee.getVencimiento());
                            params.put("ant_obst_PAPclinica", ee.getClinica());
                            params.put("ant_obst_PAPmedico", ee.getMedico());
                   break;

                   case 22 :
                            params.put("ant_obst_MAMO", true);
                            params.put("ant_obst_MAMOvence", ee.getVencimiento());
                            params.put("ant_obst_MAMOclinica", ee.getClinica());
                            params.put("ant_obst_MAMOmedico", ee.getMedico());
                   break;
            }
        }
    }

    private void obtenerExamenFisico() {

        Collection<Episodioexamenfisico> eef = epi.getEpisodioexamenfisicoCollection();

        for(Episodioexamenfisico ee : eef) {

            int cod = ee.getExamenfisico().getId();

            String clave = "ex_fis_" + ee.getExamenfisico().getDescripcion();
            params.put(clave, ee.getValor());
        }
    }

    private void obtenerVacunas() {

        Collection<Episodiosvacunas> eac = epi.getEpisodiosvacunasCollection();

        String tag;
        Object res = false;
        for(Episodiosvacunas ea : eac) {

            int codenf = ea.getVacunas().getId();

            tag = ea.getVacunas().getTagVacuna();

            params.put(tag, ea.getVence());
        }

    }

    private void obtenerEstudiosMedicos() {
        Collection<Episodiosestudios> eac = epi.getEpisodiosestudiosCollection();

        String tag, clave;
        for(Episodiosestudios ea : eac) {

            tag = ea.getEstudios().getTagEstudio();
            params.put(tag, true);
            clave = tag + "_clinica";
            params.put(clave, ea.getClinica());
            clave = tag + "_medico";
            params.put(clave, ea.getMedico());
            clave = tag + "_vence";
            params.put(clave, ea.getVencimiento());
        }
    }

    private void obtenerVicios() {
        Collection<Episodiosvicios> eac = epi.getEpisodiosviciosCollection();

        String tag, clave;
        for(Episodiosvicios ea : eac) {

            tag = "V" + ea.getViciossociales().getId();
            params.put(tag, true);
        }
    }

    private void obtenerLaboratorio() {
        Collection<Estudioshechos> eac = epi.getEstudioshechosCollection();

        String tag;
        for(Estudioshechos ea : eac) {

            tag = ea.getEstudio().getTagEstudio();
            for(Analisishechos a : ea.getAnalisishechosCollection()) {

                params.put("A"+a.getAnalisis().getId(), a.getValorhallado());
            }
        }
    }

    private void obtenerPresiones() {

        Episodiopresion ep = epi.getEpisodiopresion();
        if(ep!=null){
        params.put("DISTOLICAST", ep.getDiastolicasegundatoma());
        params.put("SISTOLICAST", ep.getSistolicasegundatoma());
        params.put("DISTOLICAPT", ep.getDiastolicaprimeratoma());
        params.put("SISTOLICAPT", ep.getSistolicaprimeratoma());
        }
    }
}

/*
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

*/