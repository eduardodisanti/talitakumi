/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kcreativa.talitakumi.talitakumiwebapp.pages.consultorio;

import com.kcreativa.talitakumi.talitakumiwebapp.services.GenericSelectModel;
import com.kcreativa.talitakumi.talitakumiwebapp.services.ISesionWeb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import talitakumi.engine.framework.invoker.sesionweb.ClientSesion;
import talitakumi.model.core.Actividades;
import talitakumi.model.core.Carnetdesalud;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Enfermedades;
import talitakumi.model.core.Episodioagudezasvisuales;
import talitakumi.model.core.Episodioexamenfisico;
import talitakumi.model.core.EpisodioexamenfisicoPK;
import talitakumi.model.core.Episodiopresion;
import talitakumi.model.core.Episodios;
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
import talitakumi.model.core.Viciossociales;

/**
 *
 * @author rupertus
 */
public class CarnetDeSaludPage {


    @Component(id = "cds") private Form _form;

    @Property
    private String empresa;
    @Property private Date fecha;
    @Inject
    private ISesionWeb sesion;

    @InjectComponent
    private Zone pacienteZone;

    private Vector<Clientes> _listaclientes = new Vector<Clientes>();

    @Persist
    @Property
    private Clientes cliente;

    private GenericSelectModel<Personas> _clientes;

    @Persist
    @Property private Integer documento;
    @Property private String apellidos;
    @Property private String nombres;
    @Property private Date fechanacimiento;
    @Persist
    @Property private String sexo;

    @Inject
    private PropertyAccess _access;

    @Property private Boolean foto;
    @Property private Boolean antecedentesHTA;
    @Property private Boolean antecedentesDiabetes;
    @Property private Boolean antecedentesOtros;

    @Property private Boolean antecedentesFUMADOR;
    @Property private Boolean antecedentesALCOHOL;
    @Property private Boolean antecedentesDIABETICOS;
    @Property private Boolean antecedentesTIROIDEOS;
    @Property private Integer antecedentesDIABETICOSTIPO;
    @Property private Integer antecedentesTIROIDEOSTIPO;

    @Property private String antecedentesMEDICAMENTOS;

    @Property private Boolean antecedentesALERGIA_ALIMENTOS;
    @Property private Boolean antecedentesALERGIA_POLVOS;
    @Property private Boolean antecedentesALERGIA_MEDICAMENTOS;
    @Property private Boolean antecedentesALERGIA_OTROS;

    @Property private Boolean antecedentesCARDIOVASCULARES_HTA;
    @Property private Boolean antecedentesCARDIOVASCULARES_PALPITACIONES;
    @Property private Boolean antecedentesCARDIOVASCULARES_TAQUICARDIA;
    @Property private Boolean antecedentesCARDIOVASCULARES_ARRITMIA;
    @Property private Boolean antecedentesCARDIOVASCULARES_SOPLO;
    @Property private Boolean antecedentesCARDIOVASCULARES_FIEBREREUMATICA;
    @Property private Boolean antecedentesCARDIOVASCULARES_INFARTOMIOCARDIO;
    @Property private Boolean antecedentesCARDIOVASCULARES_ECG;
    @Property private String antecedentesCARDIOVASCULARES_OTROS;

    @Property private Boolean antecedentesPLEUROPULMONARES_ASMA;
    @Property private Boolean antecedentesPLEUROPULMONARES_NEUMOPATIA;
    @Property private Boolean antecedentesPLEUROPULMONARES_BRONQUITIS;

    @Property private Boolean antecedentesDIGESTIVOS_HEPATITIS;
    @Property private Boolean antecedentesDIGESTIVOS_GASTRITIS;
    @Property private Boolean antecedentesDIGESTIVOS_ULCERA;
    @Property private Boolean antecedentesDIGESTIVOS_DIVERTICULOS;
    @Property private Boolean antecedentesDIGESTIVOS_ALTERACIONES;
    @Property private Boolean antecedentesDIGESTIVOS_HEMORROIDES;
    @Property private Boolean antecedentesDIGESTIVOS_LITIASIS;

    @Property private Boolean antecedentesRENALES_INFECCION;
    @Property private Boolean antecedentesRENALES_LITIASIS;
    @Property private Boolean antecedentesRENALES_COLICOS;
    @Property private Boolean antecedentesRENALES_INFECCIONURINARIA;

    @Property private Boolean antecedentesOSTEOARTICULARES_TENDINITIS;
    @Property private Boolean antecedentesOSTEOARTICULARES_LUMBALGIAS;
    @Property private Boolean antecedentesOSTEOARTICULARES_FRACTURAS;
    @Property private Boolean antecedentesOSTEOARTICULARES_ARTROSIS;

    @Property private Boolean antecedentesOFTALMOLOGICOS_USALENTES;

    @Property private Boolean antecedentesNEUROLOGICOS_VERTIGO;
    @Property private Boolean antecedentesNEUROLOGICOS_EPILEPSIA;

    @Property private Boolean antecedentesINTERNACIONES;
    @Property private Boolean antecedentesAUDITIVOS;
    @Property private Boolean antecedentesVENEREOS;
    @Property private Boolean antecedentesSIQUIATRICOS;
    @Property private Boolean antecedentesQUIRURGICOS;

    @Property private Boolean antecedentesOBSTETRICOS_EMBARAZOS;
    @Property private Boolean antecedentesOBSTETRICOS_ABORTOS;
    @Property private Boolean antecedentesOBSTETRICOS_DIU;
    @Property private Boolean antecedentesOBSTETRICOS_ACO;
    @Property private Boolean antecedentesOBSTETRICOS_AUTOEXAMENDEMAMA;
    @Property private Boolean antecedentesOBSTETRICOS_PAPANICOLAU;
    @Property private Date antecedentesOBSTETRICOS_PAPANICOLAU_FECHA;
    @Property private String antecedentesOBSTETRICOS_PAPANICOLAU_MEDICO;
    @Property private String antecedentesOBSTETRICOS_PAPANICOLAU_CLINICA;
    @Property private Boolean antecedentesOBSTETRICOS_MAMOGRAFIA;
    @Property private Date antecedentesOBSTETRICOS_MAMOGRAFIA_FECHA;
    @Property private String antecedentesOBSTETRICOS_MAMOGRAFIA_MEDICO;
    @Property private String antecedentesOBSTETRICOS_MAMOGRAFIA_CLINICA;


    @Property private Integer examen_fisicoPESO = 0;
    @Property private Integer examen_fisicoTALLA = 0;
    @Property private Integer examen_fisicoIMC = 0;
    @Property private Integer examen_fisicoPERIMETROABDOMINAL = 0;

    @Property private Boolean examen_fisico_pielymucosas_MICOSOS;
    @Property private Boolean examen_fisico_pielymucosas_ALERGIAS;
    @Property private Boolean examen_fisico_pielymucosas_PSORIASIS;
    @Property private Boolean examen_fisico_pielymucosas_LUNARES;

    @Property private String examen_fisico_LINFOGANGLIONAR;

    @Property private Boolean examen_fisico_CUELLO;
    @Property private Integer examen_fisico_CUELLO_GRADO;

    @Property private Boolean examen_fisico_VASCULAR_CENTRAL_RI;
    @Property private Integer examen_fisico_VASCULAR_CENTRAL_FRECUENCIA = 0;
    @Property private Boolean examen_fisico_VASCULAR_CENTRAL_SOPLOS;

    @Property private Integer examen_fisico_TA_PT_SISTOLICA = 0;
    @Property private Integer examen_fisico_TA_PT_DIASTOLICA = 0;

    @Property private Integer examen_fisico_TA_ST_SISTOLICA = 0;
    @Property private Integer examen_fisico_TA_ST_DIASTOLICA = 0;

    @Property private Integer examen_fisico_PULSO = 0;

    @Property private Boolean examen_fisico_MAMAS_TUMORACION;

    @Property private Boolean examen_fisico_PLEUROPULMONAR_MAV;
    @Property private String  examen_fisico_PLEUROPULMONAR;

    @Property private Boolean examen_fisico_VENOSO_VARICES;

    @Property private String examen_fisico_ABDOMEN_CICATRIZ;
    @Property private Boolean examen_fisico_ABDOMEN_BLANDO = true;
    @Property private Boolean examen_fisico_ABDOMEN_DEPRESIBLE = true;
    @Property private Boolean examen_fisico_ABDOMEN_INDOLORO = true;
    @Property private Boolean examen_fisico_ABDOMEN_TUMOR;

    @Property private Boolean examen_fisico_ZONAS_HERNARIAS_ID;
    @Property private Boolean examen_fisico_ZONAS_HERNARIAS_II;
    @Property private Boolean examen_fisico_ZONAS_HERNARIAS_FOSASLUMBARES;

    @Property private Boolean examen_fisico_OSTEOARTICULAR;
    @Property private Boolean examen_fisico_MUSCULAR;

    @Property private Float vision_OD = 0.00f;
    @Property private Float vision_OI = 0.00f;
    @Property private Float vision_CC_OD = 0.00f;
    @Property private Float vision_CC_OI = 0.00f;

    @Property private Boolean vacunaATT;
    @Property private Date    vacunaATT_VENCE;
    @Property private Boolean vacunaGRIPE;
    @Property private Boolean vacunaHEPATITISA;
    @Property private Boolean vacunaHEPATITISB;
    @Property private Boolean vacunaTIFOIDEA;

    @Property private Boolean actividadFISICA;

    @Property private String controlesMEDICOS;

    @Property private Boolean sugerencias_CONSULTAMEDICA;
    @Property private Boolean sugerencias_EXAMENLABORATORIO;
    @Property private Boolean sugerencias_CONTROLPESO;
    @Property private Boolean sugerencias_EJERCICIOS;

    @Property private String impresiongeneral;
    @Property private String observaciones;

    @Property private Boolean cartamedico;
    @Property private Boolean negado;
    @Property private Integer vigenciameses;

    @Persist
    private Pacientes paciente;

    @Persist
    private Personas p;
    private Episodios e;
    private Carnetdesalud cds;

    void onActivate() {

        fecha = new Date();
        empresa = (String) sesion.getVariable("empresa");
//        if(cliente==null)
//            cliente = new Clientes();
        try {
            Vector<Clientes> lista = (Vector<Clientes>) ClientSesion.getServiceDelegatorRemote().invoke("ServicioCargarClientes", "S");

            _listaclientes = new Vector<Clientes>();

            for(Clientes c : lista) {
                if(c.getId()!=1) {
                    _listaclientes.add(c);
                }
            }

        } catch (Exception ex) {
            System.out.println("error cargando lista clientes " + ex);
        }
    }


    public Object onCedulaChanged(String dato) {

        _form.clearErrors();

        cargarPersona(dato);

        return(pacienteZone.getBody());
    }

    public void onActionFromAlmacenar() {
        System.out.println("--------------- submiteado");
       try {
            guardarDatos();
            limpiarDatos();
        } catch (Exception ex) {
            _form.recordError("NO SE PUDO ALMACENAR AL CARNET : " + ex);
        }
    }

    public void onSubmitFromCDS() {

        System.out.println("--------------- submiteado");
        try {
            guardarDatos();
        } catch (Exception ex) {
            _form.recordError("NO SE PUDO ALMACENAR AL CARNET : " + ex);
        }
    }

    public void cargarPersona(Object undato) {

        String xcedula = (String) undato;
        Integer cedula = null;

        try {
               cedula = Integer.parseInt(xcedula);
               documento = cedula;
               p = (Personas) ClientSesion.getServiceDelegatorRemote().invoke("CargarPersona", cedula);
               if(p==null) {
                   p = new Personas();
                   p.setDocumento(documento);
               }
               nombres = p.getNombres();
               apellidos = p.getApellidos();
               fechanacimiento = p.getFechanacimiento();
               sexo = p.getSexo() + "";
               documento = p.getDocumento();


        } catch(Exception ex) {
            _form.clearErrors();
            _form.recordError("Error en cedula, revise el formato : (" + xcedula + ") " + ex);
        }
    }

     public GenericSelectModel<Clientes> getClientes(){
        return  new GenericSelectModel<Clientes>(_listaclientes, Clientes.class,"nombre", "id", _access);
    }
    private void onLoad() {

        limpiarDatos();
        try {
            _listaclientes = (Vector<Clientes>) ClientSesion.getServiceDelegatorRemote().invoke("ServicioCargarClientes", "S");
        } catch (Exception ex) {
            System.out.println("error cargando lista clientes " + ex);
        }
    }
    private void guardarDatos() throws Exception {

        System.out.println("====> GUARDAR DATOS vigencia=" + vigenciameses);
                e = new Episodios();
                e.setFecha(fecha);
                e.setTerminado('N');

                cds = new Carnetdesalud();

                cds.setImpreso(0L);
                cds.setVigencia(vigenciameses);
                
                int aux, plazo;

                aux = vigenciameses;
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

                if(cartamedico) {
                    cds.setCartamedicotratante('S');
                }
                else {
                    cds.setCartamedicotratante('N');
                }

                cds.setCausamedica('N');
                if(negado) {
                      plazo = 0;
                      cds.setCausamedica('S');
                }

                cds.setVigenciamedico(plazo);

                //e.setCarnetdesalud(cds);
                cds.setEpisodios(e);

                e.setId(null);
                e.setCliente(cliente.getId());

                paciente = p.getPacientes();
                if(paciente==null) {

                    paciente = new Pacientes();
                }
                paciente.setId(p.getId());
                paciente.setPersonas(p);
                p.setSexo(sexo.charAt(0));
                p.setFechanacimiento(fechanacimiento);
                p.setNombres(nombres);
                p.setApellidos(apellidos);
                p.setDocumento(documento);

                e.setPaciente(paciente);

                Actividades act = (Actividades) ClientSesion.getServiceDelegatorRemote().invoke("ServicioCargarActividad", 2);
                e.setTipoepisodio(act);

                try {
                    Medicos m = (Medicos) ClientSesion.getServiceDelegatorRemote().invoke("ServicioCargarMedico", 53);
                    e.setMedico(m);
                }  catch (Exception ex) {
                    _form.recordError("ERROR #1 " + ex);
                }

                cds.setVigenciamedico(plazo);
                cds.setEpisodios(e);

        System.out.println("====> GUARDAR DATOS #1");
        cds = cargarDatosCDS(cds);
        cds = (Carnetdesalud) ClientSesion.getServiceDelegatorRemote().invoke("ServicioGuardarCarnetExterno", cds);

        System.out.println("====> GUARDAR DATOS #2 " + cds);

        //System.out.println("DATOS ALMACENADOS : " + cds.getEpisodio() + " " + cds.getEpisodio());
        e.setId(cds.getEpisodio());
        System.out.println("break 0.1");
        e.setEpisodiosviciosCollection(cargarVicios());
        System.out.println("break 0.2");
        e.setEpisodiosmedicamentosCollection(cargarMedicamentos());
        System.out.println("break 0.3");
        cargarAntecedentesFamiliares();
        System.out.println("break 0.4");
        cargarAntecedentes1();
        System.out.println("break 0.5");
        cargarAntecedentesAlergias();
        System.out.println("break 0.6");
        cargarAntecedentesCardiovasculares();
        System.out.println("break 0.7");
        cargarAntecedentesPleuropulmonares();

        System.out.println("break 1");
        cargarAntecedentesDigestivos();
        System.out.println("break 2");
        cargarAntecedentesRenales();
        System.out.println("break 3");
        cargarAntecedentesOseos();
        System.out.println("break 4");
        cargarAntecedentesOftalmologicos();
        System.out.println("break 5");
        cargarAntecedentesNeurologicos();
        System.out.println("break 6");
        cargarAntecedentesInternaciones();
        System.out.println("break 7");
        cargarAntecedentesAuditivos();
        System.out.println("break 8");
        cargarAntecedentesVenereos();
        System.out.println("break 9");
        cargarAntecedentesSiquiatricos();
        System.out.println("break 10");
        cargarAntecedentesQuirurgicos();
        System.out.println("break 11");
        cargarAntecedentesObstetricos();
        System.out.println("break 12");

        cargarExamenFisico();
        System.out.println("break 13");
        cargarEnfermedadesPielMucosas();
        System.out.println("break 14");
        cargarEnfermedadesLinfoganglionar();
        System.out.println("break 15");
        cargarEnfermedadesSistemaVascular();
        System.out.println("break 16");
        cargarPresiones();
        System.out.println("break 17");
        cargarEnfermedadesMamas();
        System.out.println("break 18");
        cargarEnfermedadesPleuropulmonares();
        System.out.println("break 19");
        cargarEnfermedadesVenosas();
        System.out.println("break 20");
        cargarEnfermedadesAbdomen();
        System.out.println("break 21");
        //cargarZonasHernarias();
        System.out.println("break 22");
        cargarOsteoarticular();
        System.out.println("break 23");
        cargarVision();
        System.out.println("break 24");
        cargarSectorMuscular();
        System.out.println("break 25");

        cargarVacunas();
        System.out.println("break 26");
        cargarActividadFisica();
        System.out.println("break 27");
        cargarEstudiosMedicos();
        System.out.println("break 28");

        System.out.println("break 30");
//        ClientSesion.getInvoker().invoke("ServicioAlmacenarEpisodio", e);
        
//        ClientSesion.getServiceDelegatorRemote().invoke("ServicioAgendarEpisodioExterno", e);
        System.out.println("break 31 ===> EPISODIO ALMACENADO");
//        ClientSesion.getServiceDelegatorRemote().invoke("ServicioFacturarExterno", e);
        System.out.println("break 32 ===> EPISODIO FACTURADO");
    }

    private void cargarActividadFisica() {

      ArrayList<Episodiosestudios> ef =  (ArrayList<Episodiosestudios>) e.getEpisodiosestudiosCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosestudios>();          
          e.setEpisodiosestudiosCollection(ef);
      }
      
      try {
        Episodiosestudios ee = new Episodiosestudios();

        if(actividadFISICA) {

            ee.setClinica("");
            ee.setEpisodios(e);
            ee.setEpisodiosestudiosPK(new EpisodiosestudiosPK(e.getId(), 99));
            ee.setEstudios(cargarEstudio(99));
            ee.setMedico("");
            ee.setVencimiento(new Date());

            e.getEpisodiosestudiosCollection().add(ee);
        }
        } catch(Exception ex) {
            _form.recordError("Error cargar actividad fisica" + ex);
        }
    }

    private void cargarAntecedentesFamiliares() {

      ArrayList<Episodiosantecedentes> ef =  (ArrayList<Episodiosantecedentes>) e.getEpisodiosantecedentesCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosantecedentes>();
          e.setEpisodiosantecedentesCollection(ef);
      }


    try {
        Episodiosantecedentes ea = new Episodiosantecedentes();

        if(antecedentesHTA) {
            ea.setEnfermedades(cargarEnfermedad(93));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 93));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
        if(antecedentesDIABETICOS) {
            int sel = antecedentesDIABETICOSTIPO;
            int enfa = 94;
            if(sel==0) {
                enfa = 97;
            }
            else {
                enfa = 98;
            }
            ea.setEnfermedades(cargarEnfermedad(enfa));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), enfa));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
//        if(antecedentesC) {
//            ea.setEnfermedades(cargarEnfermedad(95));
//            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 95));
//            e.getEpisodiosantecedentesCollection().add(ea);
//        }
        if(antecedentesOtros) {
            ea.setEnfermedades(cargarEnfermedad(96));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 96));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
        } catch(Exception ex) {
            _form.recordError("Error cargar antecedentes " + ex);
        }
    }

    private Carnetdesalud cargarDatosCDS(Carnetdesalud cds) {

        cds.setObservaciones(observaciones);
        cds.setImpresiongeneral(impresiongeneral);
        
        Character cm = 'N';
        if(cartamedico) {
            cm = 'S';
        }
        cds.setSugerenciacm(cm);

        cm = 'N';
        if(sugerencias_CONTROLPESO) {
            cm = 'S';
        }
        cds.setSugerenciacontpeso(cm);

        cm = 'N';

        if(sugerencias_EJERCICIOS) {
            cm = 'S';
        }
        cds.setSugerenciaejercicios(cm);

        cm = 'N';
        if(sugerencias_EXAMENLABORATORIO) {
            cm = 'S';
        }
        cds.setSugerenciaexlab(cm);
        
        cds.setFechaexpedido(new Date());
        return(cds);
    }

    private void cargarEstudiosMedicos() {

      ArrayList<Episodiosestudios> ef =  (ArrayList<Episodiosestudios>) e.getEpisodiosestudiosCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosestudios>();
          e.setEpisodiosestudiosCollection(ef);
      }

      try {
        Episodiosestudios ee = new Episodiosestudios();

        if(controlesMEDICOS!=null) {

            ee.setClinica("");
            ee.setEpisodios(e);
            ee.setEpisodiosestudiosPK(new EpisodiosestudiosPK(e.getId(), 23));
            ee.setEstudios(cargarEstudio(23));
            ee.setMedico("");
            ee.setClinica(controlesMEDICOS);
            ee.setVencimiento(new Date());

            e.getEpisodiosestudiosCollection().add(ee);
        }
        } catch(Exception ex) {
            _form.recordError("Estudios medicos " + ex);
        }
    }

    private void cargarAntecedentes1() {

      ArrayList<Episodiosantecedentes> ef =  (ArrayList<Episodiosantecedentes>) e.getEpisodiosantecedentesCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosantecedentes>();
          e.setEpisodiosantecedentesCollection(ef);
      }

    try {
        Episodiosantecedentes ea = new Episodiosantecedentes();

        if(antecedentesDIABETICOS) {
            ea.setEnfermedades(cargarEnfermedad(7));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 7));
            e.getEpisodiosantecedentesCollection().add(ea);

            try {
                    cambiarEpisodioDiabetico();
           } catch(Exception ex) {
               
           }
        }
        if(antecedentesTIROIDEOS) {
            int idx = antecedentesTIROIDEOSTIPO;
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
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), enf));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
      } catch(Exception ex) {
           _form.recordError("Error cargar antecedentes " + ex);
      }
    }

    private void cargarAntecedentesAlergias() {

      ArrayList<Episodiosantecedentes> ef =  (ArrayList<Episodiosantecedentes>) e.getEpisodiosantecedentesCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosantecedentes>();
          e.setEpisodiosantecedentesCollection(ef);
      }
     try {


        Episodiosantecedentes ea = new Episodiosantecedentes();

        if(antecedentesALERGIA_ALIMENTOS) {
            ea.setEnfermedades(cargarEnfermedad(30));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 30));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
        if(antecedentesALERGIA_POLVOS) {
            ea.setEnfermedades(cargarEnfermedad(31));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 31));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
        if(antecedentesALERGIA_MEDICAMENTOS) {
            ea.setEnfermedades(cargarEnfermedad(33));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 33));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
        if(antecedentesALERGIA_OTROS) {
            ea.setEnfermedades(cargarEnfermedad(32));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 32));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
        } catch(Exception ex) {
            _form.recordError("Error cargar alergias " + ex);
        }
    }

    private void cargarAntecedentesCardiovasculares() {

      ArrayList<Episodiosantecedentes> ef =  (ArrayList<Episodiosantecedentes>) e.getEpisodiosantecedentesCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosantecedentes>();
          e.setEpisodiosantecedentesCollection(ef);
      }

    try {
        Episodiosantecedentes ea = new Episodiosantecedentes();

         if(antecedentesCARDIOVASCULARES_HTA) {
            ea.setEnfermedades(cargarEnfermedad(20));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 20));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
         if(antecedentesCARDIOVASCULARES_PALPITACIONES) {
            ea.setEnfermedades(cargarEnfermedad(34));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 34));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
         if(antecedentesCARDIOVASCULARES_TAQUICARDIA) {
            ea.setEnfermedades(cargarEnfermedad(35));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 35));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
         if(antecedentesCARDIOVASCULARES_ARRITMIA) {
            ea.setEnfermedades(cargarEnfermedad(36));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 36));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
         if(antecedentesCARDIOVASCULARES_SOPLO) {
            ea.setEnfermedades(cargarEnfermedad(37));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 37));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
         if(antecedentesCARDIOVASCULARES_FIEBREREUMATICA) {
            ea.setEnfermedades(cargarEnfermedad(38));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 38));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
         if(antecedentesCARDIOVASCULARES_INFARTOMIOCARDIO) {
            ea.setEnfermedades(cargarEnfermedad(39));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 39));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
         if(antecedentesCARDIOVASCULARES_OTROS != null) {
            ea.setEnfermedades(cargarEnfermedad(11));
            ea.setDetalles(antecedentesCARDIOVASCULARES_OTROS);
            e.getEpisodiosantecedentesCollection().add(ea);
        }
        } catch(Exception ex) {
            _form.recordError("Error cargar enfermedadesCardiovasculares " + ex);
        }
    }

    private void cargarAntecedentesPleuropulmonares() {

      ArrayList<Episodiosantecedentes> ef =  (ArrayList<Episodiosantecedentes>) e.getEpisodiosantecedentesCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosantecedentes>();
          e.setEpisodiosantecedentesCollection(ef);
      }

     try {
        Episodiosantecedentes ea = new Episodiosantecedentes();

         if(antecedentesPLEUROPULMONARES_ASMA) {
            ea.setEnfermedades(cargarEnfermedad(40));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 40));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
         if(antecedentesPLEUROPULMONARES_NEUMOPATIA) {
            ea.setEnfermedades(cargarEnfermedad(41));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 41));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
         if(antecedentesPLEUROPULMONARES_BRONQUITIS) {
            ea.setEnfermedades(cargarEnfermedad(42));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 42));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
        } catch(Exception ex) {
            _form.recordError("Error cargar ant. pleuropulmonares " + ex);
        }
    }

    private void cargarAntecedentesDigestivos() {

      ArrayList<Episodiosantecedentes> ef =  (ArrayList<Episodiosantecedentes>) e.getEpisodiosantecedentesCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosantecedentes>();
          e.setEpisodiosantecedentesCollection(ef);
      }

     try {
        Episodiosantecedentes ea = new Episodiosantecedentes();

         if(antecedentesDIGESTIVOS_HEPATITIS) {
            ea.setEnfermedades(cargarEnfermedad(43));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 43));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
         if(antecedentesDIGESTIVOS_GASTRITIS) {
            ea.setEnfermedades(cargarEnfermedad(44));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 44));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
         if(antecedentesDIGESTIVOS_ULCERA) {
            ea.setEnfermedades(cargarEnfermedad(45));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 45));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
         if(antecedentesDIGESTIVOS_DIVERTICULOS) {
            ea.setEnfermedades(cargarEnfermedad(46));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 46));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
         if(antecedentesDIGESTIVOS_ALTERACIONES) {
            ea.setEnfermedades(cargarEnfermedad(47));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 47));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
         if(antecedentesDIGESTIVOS_HEMORROIDES) {
            ea.setEnfermedades(cargarEnfermedad(48));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 48));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
         if(antecedentesDIGESTIVOS_LITIASIS) {
            ea.setEnfermedades(cargarEnfermedad(49));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 49));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
         } catch(Exception ex) {
            _form.recordError("Error cargar antecedentes digestivos " + ex);
        }
    }

    private void cargarAntecedentesRenales() {

      ArrayList<Episodiosantecedentes> ef =  (ArrayList<Episodiosantecedentes>) e.getEpisodiosantecedentesCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosantecedentes>();
          e.setEpisodiosantecedentesCollection(ef);
      }

     try {
         Episodiosantecedentes ea = new Episodiosantecedentes();

         if(antecedentesRENALES_INFECCION) {
            ea.setEnfermedades(cargarEnfermedad(50));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 50));
            e.getEpisodiosantecedentesCollection().add(ea);
        }

         if(antecedentesRENALES_LITIASIS) {
            ea.setEnfermedades(cargarEnfermedad(51));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 51));
            e.getEpisodiosantecedentesCollection().add(ea);
        }

         if(antecedentesRENALES_COLICOS) {
            ea.setEnfermedades(cargarEnfermedad(52));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 52));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
         if(antecedentesRENALES_INFECCIONURINARIA) {
            ea.setEnfermedades(cargarEnfermedad(53));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 53));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
        } catch(Exception ex) {
            _form.recordError("Error cargar antecedentes renales " + ex);
        }
    }

    private void cargarAntecedentesOseos() {

      ArrayList<Episodiosantecedentes> ef =  (ArrayList<Episodiosantecedentes>) e.getEpisodiosantecedentesCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosantecedentes>();
          e.setEpisodiosantecedentesCollection(ef);
      }

     try {
         Episodiosantecedentes ea = new Episodiosantecedentes();

         if(antecedentesOSTEOARTICULARES_TENDINITIS) {
            ea.setEnfermedades(cargarEnfermedad(54));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 54));
            e.getEpisodiosantecedentesCollection().add(ea);
        }

         if(antecedentesOSTEOARTICULARES_LUMBALGIAS) {
            ea.setEnfermedades(cargarEnfermedad(55));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 55));
            e.getEpisodiosantecedentesCollection().add(ea);
        }

         if(antecedentesOSTEOARTICULARES_FRACTURAS) {
            ea.setEnfermedades(cargarEnfermedad(56));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 56));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
         if(antecedentesOSTEOARTICULARES_ARTROSIS) {
            ea.setEnfermedades(cargarEnfermedad(57));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 57));
            e.getEpisodiosantecedentesCollection().add(ea);
        }

        } catch(Exception ex) {
            _form.recordError("Error cargar antecedentes oseos " + ex);
        }
    }

    private void cargarAntecedentesOftalmologicos() {

      ArrayList<Episodiosantecedentes> ef =  (ArrayList<Episodiosantecedentes>) e.getEpisodiosantecedentesCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosantecedentes>();
          e.setEpisodiosantecedentesCollection(ef);
      }

     try {
         Episodiosantecedentes ea = new Episodiosantecedentes();

         if(antecedentesOFTALMOLOGICOS_USALENTES) {
            ea.setEnfermedades(cargarEnfermedad(58));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 58));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
        } catch(Exception ex) {
            _form.recordError("Error cargar antecedentes oftalmologicos " + ex);
        }
    }

    private void cargarAntecedentesNeurologicos() {


      ArrayList<Episodiosantecedentes> ef =  (ArrayList<Episodiosantecedentes>) e.getEpisodiosantecedentesCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosantecedentes>();
          e.setEpisodiosantecedentesCollection(ef);
      }

      try {
         Episodiosantecedentes ea = new Episodiosantecedentes();

         if(antecedentesNEUROLOGICOS_VERTIGO) {
            ea.setEnfermedades(cargarEnfermedad(59));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 59));
            e.getEpisodiosantecedentesCollection().add(ea);
        }

         if(antecedentesNEUROLOGICOS_EPILEPSIA) {
            ea.setEnfermedades(cargarEnfermedad(60));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 60));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
        } catch(Exception ex) {
            _form.recordError("Error cargar antecedentes neuro " + ex);
        }
    }

    private void cargarAntecedentesInternaciones() {


      ArrayList<Episodiosantecedentes> ef =  (ArrayList<Episodiosantecedentes>) e.getEpisodiosantecedentesCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosantecedentes>();
          e.setEpisodiosantecedentesCollection(ef);
      }

        try {
         Episodiosantecedentes ea = new Episodiosantecedentes();

         if(antecedentesINTERNACIONES) {
            ea.setEnfermedades(cargarEnfermedad(61));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 61));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
       } catch(Exception ex) {
            _form.recordError("Error cargar antecedentes internacioness " + ex);
       }
    }

    private void cargarAntecedentesAuditivos() {

      ArrayList<Episodiosantecedentes> ef =  (ArrayList<Episodiosantecedentes>) e.getEpisodiosantecedentesCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosantecedentes>();
          e.setEpisodiosantecedentesCollection(ef);
      }

     try {
         Episodiosantecedentes ea = new Episodiosantecedentes();

         if(antecedentesAUDITIVOS) {
            ea.setEnfermedades(cargarEnfermedad(62));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 62));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
        } catch(Exception ex) {
            _form.recordError("Error cargar antecedentes auditivos " + ex);
        }
    }

    private void cargarAntecedentesVenereos() {

      ArrayList<Episodiosantecedentes> ef =  (ArrayList<Episodiosantecedentes>) e.getEpisodiosantecedentesCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosantecedentes>();
          e.setEpisodiosantecedentesCollection(ef);
      }

      try {
         Episodiosantecedentes ea = new Episodiosantecedentes();

         if(antecedentesVENEREOS) {
            ea.setEnfermedades(cargarEnfermedad(63));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 63));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
        } catch(Exception ex) {
            _form.recordError("Error cargar antecedentes venereos " + ex);
        }
    }

    private void cargarAntecedentesSiquiatricos() {

      ArrayList<Episodiosantecedentes> ef =  (ArrayList<Episodiosantecedentes>) e.getEpisodiosantecedentesCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosantecedentes>();
          e.setEpisodiosantecedentesCollection(ef);
      }

        try {
         Episodiosantecedentes ea = new Episodiosantecedentes();

         if(antecedentesSIQUIATRICOS) {
            ea.setEnfermedades(cargarEnfermedad(64));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 64));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
        } catch(Exception ex) {
            _form.recordError("Error cargar antecedentes siquiatricos " + ex);
        }
    }

    private void cargarAntecedentesQuirurgicos() {

      ArrayList<Episodiosantecedentes> ef =  (ArrayList<Episodiosantecedentes>) e.getEpisodiosantecedentesCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosantecedentes>();
          e.setEpisodiosantecedentesCollection(ef);
      }

     try {
         Episodiosantecedentes ea = new Episodiosantecedentes();

         if(antecedentesQUIRURGICOS) {
            ea.setEnfermedades(cargarEnfermedad(65));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 65));
            e.getEpisodiosantecedentesCollection().add(ea);
        }
        } catch(Exception ex) {
            _form.recordError("Error cargar antecedentes quirurgicos" + ex);
        }
    }

    private void cargarAntecedentesObstetricos() {

      System.out.println("cargarAntecedentesObstetricos() #ENTRANDO ");
      ArrayList<Episodiosantecedentes> ef =  (ArrayList<Episodiosantecedentes>) e.getEpisodiosantecedentesCollection();
      System.out.println("cargarAntecedentesObstetricos() #0 " + ef);

      if(ef==null) {
          ef = new ArrayList<Episodiosantecedentes>();
          e.setEpisodiosantecedentesCollection(ef);
      }

      System.out.println("cargarAntecedentesObstetricos() #0.4 NULL " + ef);

      ArrayList<Episodiosestudios> eec =  (ArrayList<Episodiosestudios>) e.getEpisodiosestudiosCollection();
      if(eec==null) {

          eec = new ArrayList<Episodiosestudios>();
          e.setEpisodiosestudiosCollection(eec);
      }

      try {
         Episodiosantecedentes ea = new Episodiosantecedentes();

         if(antecedentesOBSTETRICOS_EMBARAZOS) {
            ea.setEnfermedades(cargarEnfermedad(66));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 66));
            e.getEpisodiosantecedentesCollection().add(ea);
        }

         if(antecedentesOBSTETRICOS_ABORTOS) {
            ea.setEnfermedades(cargarEnfermedad(67));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 67));
            e.getEpisodiosantecedentesCollection().add(ea);
        }

         if(antecedentesOBSTETRICOS_DIU) {
            ea.setEnfermedades(cargarEnfermedad(68));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 68));
            e.getEpisodiosantecedentesCollection().add(ea);
        }

         if(antecedentesOBSTETRICOS_ACO) {
            ea.setEnfermedades(cargarEnfermedad(69));
            ea.setEpisodiosantecedentesPK(new EpisodiosantecedentesPK(e.getId(), 69));
            e.getEpisodiosantecedentesCollection().add(ea);
        }

         if(antecedentesOBSTETRICOS_AUTOEXAMENDEMAMA) {

            Episodiosestudios ee = new Episodiosestudios();
            ee.setEpisodios(e);
            ee.setClinica("");
            ee.setEpisodiosestudiosPK(new EpisodiosestudiosPK(e.getId(), 20));
            ee.setEstudios(cargarEstudio(20));
            ee.setMedico("");
            ee.setVencimiento(new Date());
            e.getEpisodiosestudiosCollection().add(ee);
        }

         if(antecedentesOBSTETRICOS_PAPANICOLAU) {
            try {

                Episodiosestudios ee = new Episodiosestudios();

                ee.setEpisodios(e);
                ee.setClinica(antecedentesOBSTETRICOS_PAPANICOLAU_CLINICA);
                ee.setMedico(antecedentesOBSTETRICOS_PAPANICOLAU_MEDICO);
                ee.setVencimiento(antecedentesOBSTETRICOS_PAPANICOLAU_FECHA);
                ee.setEpisodiosestudiosPK(new EpisodiosestudiosPK(e.getId(), 21));
                ee.setEstudios(cargarEstudio(21));

                e.getEpisodiosestudiosCollection().add(ee);
                System.out.println("cargarAntecedentesObstetricos() #7 ");
            } catch (Exception ex) {

                 System.out.println("error cargarAntecedentesObstetricos() #7 " + ex);
                _form.recordError("Error en fecha pap " + ex);

            }
        }

         if(antecedentesOBSTETRICOS_MAMOGRAFIA) {
            try {
                Episodiosestudios ee = new Episodiosestudios();
                ee.setEpisodios(e);
                ee.setClinica(antecedentesOBSTETRICOS_MAMOGRAFIA_CLINICA);
                ee.setMedico(antecedentesOBSTETRICOS_MAMOGRAFIA_MEDICO);
                ee.setVencimiento(antecedentesOBSTETRICOS_MAMOGRAFIA_FECHA);
                ee.setEpisodiosestudiosPK(new EpisodiosestudiosPK(e.getId(), 22));
                ee.setEstudios(cargarEstudio(22));
                e.getEpisodiosestudiosCollection().add(ee);
            } catch (Exception ex) {
                _form.recordError("Error en fecha mamografia " + ex);

            }
        }
        } catch(Exception ex) {
            _form.recordError("Error cargar antecedentes venereos " + ex);
        }
    }

    private Enfermedades cargarEnfermedad(int id) throws Exception {

        Enfermedades enf = (Enfermedades)ClientSesion.getServiceDelegatorRemote().invoke("CargarEnfermedad", id);

        return(enf);
    }

    private void cargarEnfermedadesAbdomen() {

      ArrayList<Episodiosenfermedades> ef =  (ArrayList<Episodiosenfermedades>) e.getEpisodiosenfermedadesCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosenfermedades>();
          e.setEpisodiosenfermedadesCollection(ef);
      }

    try {
        Episodiosenfermedades ee = new Episodiosenfermedades();

         if(examen_fisico_ABDOMEN_CICATRIZ != null) {
            ee.setEnfermedades(cargarEnfermedad(85));
            ee.setEpisodios(e);
            ee.setDetalles(examen_fisico_ABDOMEN_CICATRIZ);
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(85, e.getId()));
            e.getEpisodiosenfermedadesCollection().add(ee);
        }

         if(!examen_fisico_ABDOMEN_INDOLORO) {
            ee.setEnfermedades(cargarEnfermedad(86));
            ee.setEpisodios(e);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(86, e.getId()));
            e.getEpisodiosenfermedadesCollection().add(ee);
        }

         if(examen_fisico_ABDOMEN_TUMOR) {
            ee.setEnfermedades(cargarEnfermedad(87));
            ee.setEpisodios(e);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(87, e.getId()));
            e.getEpisodiosenfermedadesCollection().add(ee);
        }

        if(!examen_fisico_ABDOMEN_BLANDO) {
            ee.setEnfermedades(cargarEnfermedad(88));
            ee.setEpisodios(e);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(88, e.getId()));
            e.getEpisodiosenfermedadesCollection().add(ee);
        }
         if(!examen_fisico_ABDOMEN_DEPRESIBLE) {
            ee.setEnfermedades(cargarEnfermedad(89));
            ee.setEpisodios(e);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(89, e.getId()));
            e.getEpisodiosenfermedadesCollection().add(ee);
        }

        } catch (Exception ex) {
            _form.recordError("Error al cargar enfermedades " + ex);
        }
    }

    private void cargarEnfermedadesLinfoganglionar() {

      ArrayList<Episodiosenfermedades> ef =  (ArrayList<Episodiosenfermedades>) e.getEpisodiosenfermedadesCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosenfermedades>();
          e.setEpisodiosenfermedadesCollection(ef);
      }

    try {
        Episodiosenfermedades ee = new Episodiosenfermedades();

         if(examen_fisico_LINFOGANGLIONAR != null) {
            ee.setEnfermedades(cargarEnfermedad(75));
            ee.setEpisodios(e);
            ee.setDetalles(examen_fisico_LINFOGANGLIONAR);
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(75, e.getId()));
            e.getEpisodiosenfermedadesCollection().add(ee);
        }

         if(examen_fisico_CUELLO) {
            int codenf = 1;

            if(examen_fisico_CUELLO_GRADO == 4) {
                 codenf=102;
             }
            else
                if(examen_fisico_CUELLO_GRADO == 3) {
                    codenf=78;
             }
                else
                    if(examen_fisico_CUELLO_GRADO == 2) {
                        codenf=77;
             }
                    else
                        if(examen_fisico_CUELLO_GRADO == 1) {
                            codenf=76;
             }

            ee.setEpisodios(e);
            ee.setDetalles("");
            ee.setEnfermedades(cargarEnfermedad(codenf));
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(codenf, e.getId()));
            e.getEpisodiosenfermedadesCollection().add(ee);
        }

      } catch(Exception ex) {
            _form.recordError("Error cargando episodioenfermedades " + ex);
      }
    }

    private void cargarEnfermedadesPielMucosas() {

      ArrayList<Episodiosenfermedades> ef =  (ArrayList<Episodiosenfermedades>) e.getEpisodiosenfermedadesCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosenfermedades>();
          e.setEpisodiosenfermedadesCollection(ef);
      }

     try {
        Episodiosenfermedades ee = new Episodiosenfermedades();

         if(examen_fisico_pielymucosas_MICOSOS) {
            ee.setEnfermedades(cargarEnfermedad(71));
            ee.setEpisodios(e);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(71, e.getId()));
            e.getEpisodiosenfermedadesCollection().add(ee);
        }

         if(examen_fisico_pielymucosas_ALERGIAS) {
            ee.setEnfermedades(cargarEnfermedad(72));
            ee.setEpisodios(e);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(72, e.getId()));
            e.getEpisodiosenfermedadesCollection().add(ee);
        }

         if(examen_fisico_pielymucosas_PSORIASIS) {
            ee.setEnfermedades(cargarEnfermedad(73));
            ee.setEpisodios(e);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(73, e.getId()));
            e.getEpisodiosenfermedadesCollection().add(ee);
        }

         if(examen_fisico_pielymucosas_LUNARES) {
            ee.setEnfermedades(cargarEnfermedad(74));
            ee.setEpisodios(e);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(74, e.getId()));
            e.getEpisodiosenfermedadesCollection().add(ee);
        }
      } catch(Exception ex) {
            _form.recordError("Error cargando episodioenfermedades " + ex);
      }
    }

    private void cargarEnfermedadesPleuropulmonares() {

      ArrayList<Episodiosenfermedades> ef =  (ArrayList<Episodiosenfermedades>) e.getEpisodiosenfermedadesCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosenfermedades>();
          e.setEpisodiosenfermedadesCollection(ef);
      }

     try {
        Episodiosenfermedades ee = new Episodiosenfermedades();

        if(examen_fisico_PLEUROPULMONAR_MAV) {
            ee.setEnfermedades(cargarEnfermedad(82));
            ee.setEpisodios(e);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(82, e.getId()));
            e.getEpisodiosenfermedadesCollection().add(ee);
        }
        if(examen_fisico_PLEUROPULMONAR != null) {
            ee.setEnfermedades(cargarEnfermedad(83));
            ee.setEpisodios(e);
            ee.setDetalles(examen_fisico_PLEUROPULMONAR);
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(83, e.getId()));
            e.getEpisodiosenfermedadesCollection().add(ee);
        }
      } catch(Exception ex) {
            _form.recordError("Error cargando episodioenfermedades " + ex);
      }
    }

    private void cargarEnfermedadesVenosas() {

      ArrayList<Episodiosenfermedades> ef =  (ArrayList<Episodiosenfermedades>) e.getEpisodiosenfermedadesCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosenfermedades>();
          e.setEpisodiosenfermedadesCollection(ef);
      }

     try {
        Episodiosenfermedades ee = new Episodiosenfermedades();

        if(examen_fisico_VENOSO_VARICES) {
            ee.setEnfermedades(cargarEnfermedad(84));
            ee.setEpisodios(e);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(84, e.getId()));
            e.getEpisodiosenfermedadesCollection().add(ee);
        }
      } catch(Exception ex) {
            _form.recordError("Error cargando episodioenfermedades " + ex);
      }
    }

    private void cargarEnfermedadesSistemaVascular() {

      try {
        Episodiosenfermedades ee = new Episodiosenfermedades();

        if(examen_fisico_VASCULAR_CENTRAL_RI) {
            ee.setEnfermedades(cargarEnfermedad(79));
            ee.setEpisodios(e);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(79, e.getId()));
            e.getEpisodiosenfermedadesCollection().add(ee);
        }

        if(examen_fisico_VASCULAR_CENTRAL_SOPLOS) {
            ee.setEnfermedades(cargarEnfermedad(80));
            ee.setEpisodios(e);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(80, e.getId()));
            e.getEpisodiosenfermedadesCollection().add(ee);
        }
      } catch(Exception ex) {
            _form.recordError("Error cargando episodioenfermedades " + ex);
      }
    }


    private void cargarEnfermedadesMamas() {

      ArrayList<Episodiosenfermedades> ef =  (ArrayList<Episodiosenfermedades>) e.getEpisodiosenfermedadesCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosenfermedades>();
          e.setEpisodiosenfermedadesCollection(ef);
      }

    try {
        Episodiosenfermedades ee = new Episodiosenfermedades();

        if(examen_fisico_MAMAS_TUMORACION) {
            ee.setEnfermedades(cargarEnfermedad(81));
            ee.setEpisodios(e);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(81, e.getId()));
            e.getEpisodiosenfermedadesCollection().add(ee);
        }
      } catch(Exception ex) {
            _form.recordError("Error cargando episodioenfermedades " + ex);
      }
    }

    private Estudios cargarEstudio(int id) throws Exception {


        Estudios est = (Estudios)ClientSesion.getServiceDelegatorRemote().invoke("CargarEstudio", id);

        return(est);
    }

    private void cargarExamenFisico() {

        Collection <Episodioexamenfisico> ef = e.getEpisodioexamenfisicoCollection();
        if(ef == null) {
                ef = new ArrayList<Episodioexamenfisico>();
                e.setEpisodioexamenfisicoCollection(ef);
        }

        try {

        System.out.println("ex f 1");
        Episodioexamenfisico eef = new Episodioexamenfisico();
        System.out.println("ex f 1.1 -ID=" + e.getId());
        eef.setEpisodioexamenfisicoPK(new EpisodioexamenfisicoPK(e.getId(), 1));
        System.out.println("ex f 1.2");
        eef.setEpisodios(e);
        System.out.println("ex f 1.3");
        eef.setExamenfisico(cargarExamenFisico(1));
        System.out.println("ex f 1.4");
        eef.setValor(examen_fisicoPESO);
        System.out.println("ex f 1.5");
        e.getEpisodioexamenfisicoCollection().add(eef);

        System.out.println("ex f 2");

        eef = new Episodioexamenfisico();
        eef.setEpisodioexamenfisicoPK(new EpisodioexamenfisicoPK(e.getId(), 2));
        eef.setEpisodios(e);
        eef.setExamenfisico(cargarExamenFisico(2));
        eef.setValor(examen_fisicoTALLA);
        e.getEpisodioexamenfisicoCollection().add(eef);

System.out.println("ex f 3");

        eef = new Episodioexamenfisico();
        eef.setEpisodioexamenfisicoPK(new EpisodioexamenfisicoPK(e.getId(), 3));
        eef.setEpisodios(e);
        eef.setExamenfisico(cargarExamenFisico(3));
        eef.setValor(examen_fisicoIMC);
        e.getEpisodioexamenfisicoCollection().add(eef);

System.out.println("ex f 4");

        eef = new Episodioexamenfisico();
        eef.setEpisodioexamenfisicoPK(new EpisodioexamenfisicoPK(e.getId(), 4));
        eef.setEpisodios(e);
        eef.setExamenfisico(cargarExamenFisico(4));
        eef.setValor(examen_fisicoPERIMETROABDOMINAL);
        e.getEpisodioexamenfisicoCollection().add(eef);

System.out.println("ex f 5");

        eef = new Episodioexamenfisico();
        eef.setEpisodioexamenfisicoPK(new EpisodioexamenfisicoPK(e.getId(), 5));
        eef.setEpisodios(e);
        eef.setExamenfisico(cargarExamenFisico(5));
        eef.setValor(examen_fisico_PULSO);
        e.getEpisodioexamenfisicoCollection().add(eef);

System.out.println("ex f 6");

        eef = new Episodioexamenfisico();
        eef.setEpisodioexamenfisicoPK(new EpisodioexamenfisicoPK(e.getId(), 6));
        eef.setEpisodios(e);
        eef.setExamenfisico(cargarExamenFisico(6));
        eef.setValor(examen_fisico_VASCULAR_CENTRAL_FRECUENCIA);
        e.getEpisodioexamenfisicoCollection().add(eef);
System.out.println("ex f 7");
        } catch(Exception ex) {
            _form.recordError("Error cargando examen fisico " + ex);
        }
    }

    private Examenfisico cargarExamenFisico(int id) throws Exception {

System.out.println("ex LOAD");
        Examenfisico ef = (Examenfisico) ClientSesion.getServiceDelegatorRemote().invoke("CargarExamenFisico", id);

System.out.println("ex LOAD #2");
        return(ef);
    }

    private Medicamentos cargarMedicamento(int id) throws Exception {

        Medicamentos vs = (Medicamentos) ClientSesion.getServiceDelegatorRemote().invoke("CargarMedicamento", id);
        return(vs);
    }

    private Collection<Episodiosmedicamentos> cargarMedicamentos() throws Exception {

      ArrayList<Episodiosmedicamentos> ef =  (ArrayList<Episodiosmedicamentos>) e.getEpisodiosmedicamentosCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosmedicamentos>();
          e.setEpisodiosmedicamentosCollection(ef);
      }

        if(antecedentesMEDICAMENTOS != null) {
             Episodiosmedicamentos em = new Episodiosmedicamentos();
             em.setDosis("");
             em.setEpisodios(e);
             em.setMedicamentos(cargarMedicamento(6));
             EpisodiosmedicamentosPK episodiosmedicamentosPK = new EpisodiosmedicamentosPK();
             episodiosmedicamentosPK.setEpisodio(e.getId());
             episodiosmedicamentosPK.setMedicamento(6);
             em.setEpisodiosmedicamentosPK(episodiosmedicamentosPK);
        }

        return(e.getEpisodiosmedicamentosCollection());
    }

    private void cargarPresiones() {
        Episodiopresion ep = new Episodiopresion();

        ep.setEpisodios(e);
        ep.setEpisodio(e.getId());
        ep.setDiastolicaprimeratoma(examen_fisico_TA_PT_DIASTOLICA);
        ep.setDiastolicasegundatoma(examen_fisico_TA_ST_DIASTOLICA);
        ep.setSistolicaprimeratoma(examen_fisico_TA_PT_SISTOLICA);
        ep.setSistolicasegundatoma(examen_fisico_TA_ST_SISTOLICA);

        e.setEpisodiopresion(ep);

        modificarEpisodiosSegunPresiones();
    }

    private void modificarEpisodiosSegunPresiones() {

//        int dst = e.getEpisodiopresion().getDiastolicasegundatoma();
//        int sst = e.getEpisodiopresion().getSistolicasegundatoma();
//
//        int idact = e.getTipoepisodio().getId() * 100 + 1;
//        if(sst > 140 ||
//           dst > 90) {
//            Actividades act = (Actividades) ClientSesion.getInvoker().invoke("ServicioCargarActividad", idact);
//            e.setTipoepisodio(act);
//            String txt = "CREATININEMIA " + p.getApellidos() + " " + p.getNombres() + " " + p.getDocumento();
//            new Mensajero().enviar("CONSULTA MEDICA", "laboratorio", txt);
//        }
    }

    private void cargarVacunas() {

      ArrayList<Episodiosvacunas> ef =  (ArrayList<Episodiosvacunas>) e.getEpisodiosvacunasCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosvacunas>();
          e.setEpisodiosvacunasCollection(ef);
      }

      try {
        Episodiosvacunas ev;

        if(vacunaATT) {
            ev = new Episodiosvacunas();
            ev.setEpisodios(e);
            ev.setEpisodiosvacunasPK(new EpisodiosvacunasPK(e.getId(), 1));
            ev.setVacunas(cargarVacuna(1));
            ev.setVence(vacunaATT_VENCE);

            e.getEpisodiosvacunasCollection().add(ev);
        }

        if(vacunaGRIPE) {
            ev = new Episodiosvacunas();
            ev.setEpisodios(e);
            ev.setEpisodiosvacunasPK(new EpisodiosvacunasPK(e.getId(), 4));
            ev.setVacunas(cargarVacuna(4));

            e.getEpisodiosvacunasCollection().add(ev);
        }

        if(vacunaHEPATITISA) {
            ev = new Episodiosvacunas();
            ev.setEpisodios(e);
            ev.setEpisodiosvacunasPK(new EpisodiosvacunasPK(e.getId(), 2));
            ev.setVacunas(cargarVacuna(2));

            e.getEpisodiosvacunasCollection().add(ev);
        }

        if(vacunaHEPATITISB) {
            ev = new Episodiosvacunas();
            ev.setEpisodios(e);
            ev.setEpisodiosvacunasPK(new EpisodiosvacunasPK(e.getId(), 3));
            ev.setVacunas(cargarVacuna(3));

            e.getEpisodiosvacunasCollection().add(ev);
        }

        if(vacunaTIFOIDEA) {
            ev = new Episodiosvacunas();
            ev.setEpisodios(e);
            ev.setEpisodiosvacunasPK(new EpisodiosvacunasPK(e.getId(), 5));
            ev.setVacunas(cargarVacuna(5));

            e.getEpisodiosvacunasCollection().add(ev);
        }
        } catch(Exception ex) {
            _form.recordError("Error cargar vacunas " + ex);
        }
    }

    private Vacunas cargarVacuna(int id) throws Exception {

        Vacunas v = (Vacunas) ClientSesion.getServiceDelegatorRemote().invoke("CargarVacuna", id);

        return(v);
    }

    private Collection<Episodiosvicios> cargarVicios() {

      ArrayList<Episodiosvicios> ef =  (ArrayList<Episodiosvicios>) e.getEpisodiosviciosCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosvicios>();
          e.setEpisodiosviciosCollection(ef);
      }

      try {
        Episodiosvicios ev = new Episodiosvicios();

        if(antecedentesFUMADOR) {
            ev.setEpisodios(e);
            ev.setViciossociales(cargarVicio(2));
            ev.setEpisodiosviciosPK(new EpisodiosviciosPK(2,e.getId()));
            ev.setDetalles(".");
            e.getEpisodiosviciosCollection().add(ev);
        }

        if(antecedentesALCOHOL) {
            ev.setEpisodios(e);
            ev.setViciossociales(cargarVicio(1));
            ev.setEpisodiosviciosPK(new EpisodiosviciosPK(1, e.getId()));
            ev.setDetalles(".");
            e.getEpisodiosviciosCollection().add(ev);
        }
        } catch(Exception ex) {
            _form.recordError("Error cargar Vicios " + ex);
        }
        return(e.getEpisodiosviciosCollection());
    }

    Viciossociales cargarVicio(int id) throws Exception {


        Viciossociales vs = (Viciossociales) ClientSesion.getServiceDelegatorRemote().invoke("CargarVicioSocial", id);
        return(vs);
    }

    private void cargarVision() {

        Episodioagudezasvisuales ev = new Episodioagudezasvisuales();

        ev.setEpisodios(e);
        ev.setEpisodio(e.getId());
        ev.setScod(vision_OD);
        ev.setScoi(vision_OI);
        ev.setCcod(vision_CC_OD);
        ev.setCcoi(vision_CC_OI);
        e.setEpisodioagudezasvisuales(ev);
    }

    private void cargarZonasHernarias() {

      String msg = " * ";
      ArrayList<Episodiosenfermedades> ef =  (ArrayList<Episodiosenfermedades>) e.getEpisodiosenfermedadesCollection();
      if(ef==null) {
          ef = new ArrayList<Episodiosenfermedades>();
          e.setEpisodiosenfermedadesCollection(ef);
      }

      try {
        Episodiosenfermedades ee = new Episodiosenfermedades();

        if(examen_fisico_ZONAS_HERNARIAS_ID) {
            ee.setEnfermedades(cargarEnfermedad(90));
            ee.setEpisodios(e);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(90, e.getId()));
            e.getEpisodiosenfermedadesCollection().add(ee);
            msg = " brk1 ";
        }

        if(examen_fisico_ZONAS_HERNARIAS_II) {
            ee.setEnfermedades(cargarEnfermedad(91));
            ee.setEpisodios(e);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(91, e.getId()));
            e.getEpisodiosenfermedadesCollection().add(ee);
            msg = " brk2 ";
        }
        if(!examen_fisico_ZONAS_HERNARIAS_FOSASLUMBARES) {
            ee.setEnfermedades(cargarEnfermedad(92));
            ee.setEpisodios(e);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(92, e.getId()));
            e.getEpisodiosenfermedadesCollection().add(ee);
            msg = " brk3 ";
        }
        } catch(Exception ex) {
            _form.recordError("Error cargar zonas hernarias " + msg + " "+ ex);
        }
    }

    private void cargarOsteoarticular() {

        Collection <Episodiosenfermedades> ef = e.getEpisodiosenfermedadesCollection();
        if(ef == null)
                ef = new ArrayList<Episodiosenfermedades>();

     try {
        Episodiosenfermedades ee = new Episodiosenfermedades();

        if(examen_fisico_OSTEOARTICULAR) {
            ee.setEnfermedades(cargarEnfermedad(93));
            ee.setEpisodios(e);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(93, e.getId()));
            e.getEpisodiosenfermedadesCollection().add(ee);
        }
        } catch(Exception ex) {
            _form.recordError("Error cargar osteoarticular " + ex);
        }
    }

    private void cargarSectorMuscular() {

     try {
        Episodiosenfermedades ee = new Episodiosenfermedades();

        if(examen_fisico_MUSCULAR) {
            ee.setEnfermedades(cargarEnfermedad(94));
            ee.setEpisodios(e);
            ee.setDetalles("");
            ee.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(94, e.getId()));
            e.getEpisodiosenfermedadesCollection().add(ee);
        }
        } catch(Exception ex) {
            _form.recordError("Error cargar sector muscular " + ex);
        }
    }

    private void cambiarEpisodioDiabetico() throws Exception {

//       int idact = e.getTipoepisodio().getId() * 100 + 1;
//       Actividades act = (Actividades) ClientSesion.getInvoker().invoke("ServicioCargarActividad", idact);
//       e.setTipoepisodio(act);
//
//       p = e.getPaciente().getPersonas();
//       String txt = "CREATININEMIA " + p.getApellidos() + " " + p.getNombres() + " " + p.getDocumento();
//       new Mensajero().enviar("CONSULTA MEDICA", "laboratorio", txt);
    }

    private void limpiarDatos() {
      antecedentesHTA = false;
      antecedentesDiabetes = false;
      antecedentesOtros = false;

      antecedentesFUMADOR = false;
      antecedentesALCOHOL = false;
      antecedentesDIABETICOS = false;
      antecedentesTIROIDEOS = false;
      antecedentesDIABETICOSTIPO = 0;
      antecedentesTIROIDEOSTIPO = 0;

      antecedentesMEDICAMENTOS = "";

      antecedentesALERGIA_ALIMENTOS = false;
      antecedentesALERGIA_POLVOS = false;
      antecedentesALERGIA_MEDICAMENTOS = false;
      antecedentesALERGIA_OTROS = false;

      antecedentesCARDIOVASCULARES_HTA = false;
      antecedentesCARDIOVASCULARES_PALPITACIONES = false;
      antecedentesCARDIOVASCULARES_TAQUICARDIA = false;
      antecedentesCARDIOVASCULARES_ARRITMIA = false;
      antecedentesCARDIOVASCULARES_SOPLO = false;
      antecedentesCARDIOVASCULARES_FIEBREREUMATICA = false;
      antecedentesCARDIOVASCULARES_INFARTOMIOCARDIO = false;
      antecedentesCARDIOVASCULARES_ECG = false;
      antecedentesCARDIOVASCULARES_OTROS = "";

      antecedentesPLEUROPULMONARES_ASMA = false;
      antecedentesPLEUROPULMONARES_NEUMOPATIA = false;
      antecedentesPLEUROPULMONARES_BRONQUITIS = false;

      antecedentesDIGESTIVOS_HEPATITIS = false;
      antecedentesDIGESTIVOS_GASTRITIS = false;
      antecedentesDIGESTIVOS_ULCERA = false;
      antecedentesDIGESTIVOS_DIVERTICULOS = false;
      antecedentesDIGESTIVOS_ALTERACIONES = false;
      antecedentesDIGESTIVOS_HEMORROIDES = false;
      antecedentesDIGESTIVOS_LITIASIS = false;

      antecedentesRENALES_INFECCION = false;
      antecedentesRENALES_LITIASIS = false;
      antecedentesRENALES_COLICOS = false;
      antecedentesRENALES_INFECCIONURINARIA = false;

      antecedentesOSTEOARTICULARES_TENDINITIS = false;
      antecedentesOSTEOARTICULARES_LUMBALGIAS = false;
      antecedentesOSTEOARTICULARES_FRACTURAS = false;
      antecedentesOSTEOARTICULARES_ARTROSIS = false;

      antecedentesOFTALMOLOGICOS_USALENTES = false;

      antecedentesNEUROLOGICOS_VERTIGO = false;
      antecedentesNEUROLOGICOS_EPILEPSIA = false;

      antecedentesINTERNACIONES = false;
      antecedentesAUDITIVOS = false;
      antecedentesVENEREOS = false;
      antecedentesSIQUIATRICOS = false;
      antecedentesQUIRURGICOS = false;

      antecedentesOBSTETRICOS_EMBARAZOS = false;
      antecedentesOBSTETRICOS_ABORTOS = false;
      antecedentesOBSTETRICOS_DIU = false;
      antecedentesOBSTETRICOS_ACO = false;
      antecedentesOBSTETRICOS_AUTOEXAMENDEMAMA = false;
      antecedentesOBSTETRICOS_PAPANICOLAU = false;
      antecedentesOBSTETRICOS_PAPANICOLAU_FECHA = null;
      antecedentesOBSTETRICOS_PAPANICOLAU_MEDICO = "";
      antecedentesOBSTETRICOS_PAPANICOLAU_CLINICA = "";
      antecedentesOBSTETRICOS_MAMOGRAFIA  = false;
      antecedentesOBSTETRICOS_MAMOGRAFIA_FECHA = null;
      antecedentesOBSTETRICOS_MAMOGRAFIA_MEDICO = "";
      antecedentesOBSTETRICOS_MAMOGRAFIA_CLINICA = "";


      examen_fisicoPESO = 0;
      examen_fisicoTALLA = 0;
      examen_fisicoIMC = 0;
      examen_fisicoPERIMETROABDOMINAL = 0;

      examen_fisico_pielymucosas_MICOSOS = false;
      examen_fisico_pielymucosas_ALERGIAS = false;
      examen_fisico_pielymucosas_PSORIASIS = false;
      examen_fisico_pielymucosas_LUNARES = false;

      examen_fisico_LINFOGANGLIONAR = "";

      examen_fisico_CUELLO = false;
      examen_fisico_CUELLO_GRADO = 0;

      examen_fisico_VASCULAR_CENTRAL_RI = false;
      examen_fisico_VASCULAR_CENTRAL_FRECUENCIA = 0;
      examen_fisico_VASCULAR_CENTRAL_SOPLOS = false;

      examen_fisico_TA_PT_SISTOLICA = 0;
      examen_fisico_TA_PT_DIASTOLICA = 0;

      examen_fisico_TA_ST_SISTOLICA = 0;
      examen_fisico_TA_ST_DIASTOLICA = 0;

      examen_fisico_PULSO = 0;

      examen_fisico_MAMAS_TUMORACION = false;

      examen_fisico_PLEUROPULMONAR_MAV = false;
       examen_fisico_PLEUROPULMONAR = "";

      examen_fisico_VENOSO_VARICES = false;

      examen_fisico_ABDOMEN_CICATRIZ = "";
      examen_fisico_ABDOMEN_BLANDO = false;
      examen_fisico_ABDOMEN_DEPRESIBLE = false;
      examen_fisico_ABDOMEN_INDOLORO = false;
      examen_fisico_ABDOMEN_TUMOR = false;

      examen_fisico_ZONAS_HERNARIAS_ID = false;
      examen_fisico_ZONAS_HERNARIAS_II = false;
      examen_fisico_ZONAS_HERNARIAS_FOSASLUMBARES = false;

      examen_fisico_OSTEOARTICULAR = false;
      examen_fisico_MUSCULAR = false;

      vision_OD = 0.00f;
      vision_OI = 0.00f;
      vision_CC_OD = 0.00f;
      vision_CC_OI = 0.00f;

      vacunaATT = false;
         vacunaATT_VENCE = null;
      vacunaGRIPE  = false;
      vacunaHEPATITISA = false;
      vacunaHEPATITISB = false;
      vacunaTIFOIDEA = false;

      actividadFISICA = false;

      controlesMEDICOS = "";

      sugerencias_CONSULTAMEDICA = false;
      sugerencias_EXAMENLABORATORIO = false;
      sugerencias_CONTROLPESO = false;
      sugerencias_EJERCICIOS = false;

      impresiongeneral = "";
      observaciones = "";

      cartamedico = false;
      negado = false;
      vigenciameses = 0;

        examen_fisico_ABDOMEN_BLANDO = true;
        examen_fisico_ABDOMEN_DEPRESIBLE = true;
        examen_fisico_ABDOMEN_INDOLORO = true;

    }
}
