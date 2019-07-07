package talitakumi.medicos.ui;

import com.kcreativa.imgrendering.SwingImageCreator;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import org.apache.log4j.Logger;
import talitakumi.framework.Sesion;

import talitakumi.model.core.Agenda;
import talitakumi.model.core.Consultorios;
import talitakumi.model.core.Enfermedades;
import talitakumi.model.core.Episodioactitudes;
import talitakumi.model.core.Episodioagudezasauditivas;
import talitakumi.model.core.Episodioagudezasvisuales;
import talitakumi.model.core.Episodioexameneneuro;
import talitakumi.model.core.Episodiointegrideadme;
import talitakumi.model.core.Episodiopresion;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Episodiosenfermedades;
import talitakumi.model.core.EpisodiosenfermedadesPK;
import talitakumi.model.core.Episodiosmedicamentos;
import talitakumi.model.core.EpisodiosmedicamentosPK;
import talitakumi.model.core.Episodiosvicios;
import talitakumi.model.core.EpisodiosviciosPK;
import talitakumi.model.core.Libretasdeconducir;
import talitakumi.model.core.Medicamentos;
import talitakumi.model.core.Medicos;
import talitakumi.model.core.Pacientes;
import talitakumi.model.core.Personas;
import talitakumi.model.core.Procedencias;
import talitakumi.model.core.Viciossociales;
import talitakumi.servicios.libretadeconducir.ServicioCalculadorVigencia;
import view.DialogoError;
import view.DialogoInformacion;


/**
 *
 * @author rupertus
 */
public class UILibretaDeConducir extends javax.swing.JPanel {


    int procedencia;
    List <Procedencias> listaprocedencias;

    int episodio;
    String consultorio;
    Personas persona;
    Episodios epis;
    Libretasdeconducir  ldc;
    Agenda agendaActual;
    List <Agenda> listaagenda;
    private boolean usarsegundatomapresiones;
    private int medicoactuante;
    private Episodioactitudes episodioActitudes;
    private Episodioagudezasauditivas episodioagudezasauditivas;
    private Episodioexameneneuro episodioexameneneuro;
    private Episodiopresion episodiopresion;
    private Episodiointegrideadme episodiointegrideadme;
    private Episodioagudezasvisuales episodioagudezasvisuales;
    private Collection<Episodiosvicios> episodiosvicios;
    private Collection<Episodiosmedicamentos> episodiosmedicamentos;
    private Logger logger;
    private Pacientes p;

    /** Creates new form UILibretaDeConducir */
    public UILibretaDeConducir() {
        initComponents();

        logger = Sesion.getLogger();

        Date hoy;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        hoy = new java.util.Date();
        jXFechaNacimiento.setFormats("dd/MM/yyyy");

        //jXFechaNacimiento.setDate(hoy);
        jXFechaSolicitud.setDate(hoy);
        jXFechaSolicitud.setFormats("dd/MM/yyyy");

        medicoactuante = 1;
        episodio = 0;
        procedencia = 0;
        consultorio = ((Consultorios)Sesion.getLugarlogon()).getId()+"";

        welcome();

        this.comboAfeccionesOculares.requestFocus();

        this.jtfscod.setValue("1.00");
        this.jtfscoi.setValue("1.00");
    }

    public void setGuardarListener(ActionListener almacenarListener) {

        jButton1.addActionListener(almacenarListener);

    }

    public Medicos cargarMedico() {
        Medicos m;

        try {
            m = (Medicos) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarMedico", medicoactuante);
        }  catch (Exception ex) {
            logger.error(new Date()+" error llamando ServicioCargarMedico");
            logger.error(ex);
            m = null;
        }

        return(m);
    }

    public void almacenar() {

        try {  // TODO - Invocar el servicio
                this.cargarObjetos();
                this.prepararAlmacenar();

                Medicos m = cargarMedico();
                agendaActual.setMedico(cargarMedico());
                agendaActual.getEpisodio().setMedico(m);

                agendaActual.getEpisodio().setTerminado('S');

// TODO - mal - si se rompe la transaccion estoy cagado
                // TODO - tomar datos del paciente de la vista y hacer un agendaActual.getEpisodio().setPaciente(p);
                agendaActual.getEpisodio().setPaciente(p);

                agendaActual.getEpisodio().setTerminado('N');
                Sesion.getServiceDelegatorRemote().invoke("ServicioModificarAgenda", agendaActual);
                //Sesion.getServiceDelegatorRemote().invoke("ServicioFacturar", agendaActual.getEpisodio());

        } catch (Exception e) {

                DialogoError de = new DialogoError(null, "Error fatal", "No se pudo almacenar la libreta", e.toString(), true);
                de.setVisible(true);
        }
        
        this.vaciarFormulario();
    }

    private void alterarDebeUsar() {
        
            
            modificarVista();
            alterarDebeUsarVista();
            alterarDebeUsarAudio();
    }

    private int buscarProcedencia(int agencia) {
       int z = 0;
       int idx = 0;

       for(Procedencias proc : listaprocedencias) {

           if(proc.getId()==agencia) {
               idx=z;
           } else
                 z++;
       }

       return(idx);
    }

    private void cargarEpisodiomedicamentos() throws Exception {


            if(jcbMedicacionOcular.isSelected())
                agregarMedicamentoLista(1,"");

            if(jcbHipoglucemiantes.isSelected())
                agregarMedicamentoLista(2, jtbDosisHipoglucemiantes.getText());

            if(jcbSicofarmacos.isSelected())
                agregarMedicamentoLista(3, jtbDosisSicofarmacos.getText());

            if(jcbMedicacionSiquiatrica.isSelected())
                agregarMedicamentoLista(4, jtbDosisMedPsiq.getText());

            if(jCheckBox4.isSelected())
                agregarMedicamentoLista(5,"");

    }

    private void agregarMedicamentoLista(int medicamento, String dosis) throws Exception {

            Episodiosmedicamentos epim = new Episodiosmedicamentos();

            Medicamentos ms = (Medicamentos) Sesion.getServiceDelegatorRemote().invoke("CargarMedicamento", medicamento);

            epim.setMedicamentos(ms);
            epim.setDosis(dosis);
            epim.setEpisodiosmedicamentosPK(new EpisodiosmedicamentosPK(medicamento, episodio));

            agendaActual.getEpisodio().getEpisodiosmedicamentosCollection().add(epim);
    }

    private void cargarEnfermedades() throws Exception {

        int aux;

        if(jcbAfeccionesOculares.isSelected()) {

            switch(comboAfeccionesOculares.getSelectedIndex()) {

                case 0 : aux = 1;
                         break;

                case 1 : aux = 2;
                         break;

                case 2 : aux = 19;
                         break;

                case 3 : aux = 18;
                         break;
                         
                default : aux = 18;
                         break;
           }

           agregarEnfermedadLista(aux);
        }

        if(jcbHTA.isSelected())
           agregarEnfermedadLista(20);

        if(jcbMarcapaso.isSelected())
           agregarEnfermedadLista(5);

        if(jcbRenales.isSelected())
           agregarEnfermedadLista(3);

        if(jcbDiabetes.isSelected())
           agregarEnfermedadLista(7);

        if(jcbTraumatismos.isSelected())
           agregarEnfermedadLista(8);

        if(jcbAccidentes.isSelected())
           agregarEnfermedadLista(9);

        if(jcbIntoleranciaGluc.isSelected())
           agregarEnfermedadLista(21);

        if(jcbFacturas.isSelected())
           agregarEnfermedadLista(22);

        if(jcbAfeccionesPsiquiatricas.isSelected())
           agregarEnfermedadLista(23);

        if(jcbOtrasEnfNeuro.isSelected())
           agregarEnfermedadLista(17);

        if(jcbEpilepsiaControlada.isSelected())
           agregarEnfermedadLista(16);

        if(jcbEnfermedaesNeuro.isSelected())
           agregarEnfermedadLista(12);

        if(jcbOtrasEnfVasculares.isSelected())
           agregarEnfermedadLista(11);

        if(jcbAfeccionesAuditivas.isSelected())
           agregarEnfermedadLista(10);

    }

    private void agregarEnfermedadLista(int enfermedad) throws Exception {

        Episodiosenfermedades epim = new Episodiosenfermedades();


        Enfermedades ms = (Enfermedades)  Sesion.getServiceDelegatorRemote().invoke("CargarEnfermedad", enfermedad);

        epim.setEnfermedades(ms);
        epim.setDetalles("");
        epim.setEpisodiosenfermedadesPK(new EpisodiosenfermedadesPK(enfermedad, episodio));

        agendaActual.getEpisodio().getEpisodiosenfermedadesCollection().add(epim);
    }

    private void cargarEpisodiovicios() throws Exception {

        if(jcbConsumeAlcohol.isSelected()) {

            Episodiosvicios ev = new Episodiosvicios();

            Viciossociales vs = (Viciossociales)  Sesion.getServiceDelegatorRemote().invoke("CargarVicioSocial", 1);
            ev.setViciossociales(vs);

            if(jrbFinesDeSemana.isSelected())
                ev.setDetalles("FDS");
            else
                if(jrbDiariamente.isSelected())
                    ev.setDetalles("DIA");
                else
                    ev.setDetalles("OCA");

            ev.setEpisodiosviciosPK(new EpisodiosviciosPK(1, episodio));
            agendaActual.getEpisodio().getEpisodiosviciosCollection().add(ev);
        }
    }

    private void modificarVista() {

            float valorOcularCC     = 0,
                  valorOcularCCI    = 0,
                  valorOcularCCD    = 0,
                  valorOcularCCCLCD = 0,
                  valorOcularCCCLCI = 0,
                  valorOcularCCCLC  = 0,
                  valorOcularCCCLID = 0,
                  valorOcularCCCLII = 0;

            valorOcularCCD = Float.parseFloat((String)this.jtfccod.getValue());
            valorOcularCCI = Float.parseFloat((String)this.jtfccoi.getValue());
            valorOcularCC  = valorOcularCCD + valorOcularCCI;
        
            valorOcularCCCLCD = Float.parseFloat((String)this.jtfclcod.getValue());
            valorOcularCCCLCI = Float.parseFloat((String)this.jtfclcoi.getValue());
            valorOcularCCCLC  = valorOcularCCCLCD + valorOcularCCCLCI;

            valorOcularCCCLID = Float.parseFloat((String)this.jtfcliod.getValue());
            valorOcularCCCLII = Float.parseFloat((String)this.jtfclioi.getValue());
            
            if(valorOcularCCCLID!=0 || valorOcularCCCLCD!=0 || valorOcularCCD!=0)
                this.jtfscod.setValue("0.00");
            
            if(valorOcularCCCLCD!=0 || valorOcularCCCLID!=0 || valorOcularCCD!=0)
                this.jtfscod.setValue("0.00");

            if(valorOcularCCCLCI!=0 || valorOcularCCCLII!=0 || valorOcularCCI!=0)
                this.jtfscoi.setValue("0.00");         
            
            agregarCodigo(35, (valorOcularCC != 0));
            if((valorOcularCCCLC != 0 && valorOcularCCCLC > 1.4 && valorOcularCC != 0)) {
                agregarCodigo(41, true);
                agregarCodigo(27, false);
            }
            else
                if((valorOcularCCCLC != 0 && valorOcularCCCLC <= 1.4 && valorOcularCC != 0)) {
                    agregarCodigo(41, false);
                    agregarCodigo(27, true);
                } else 
                       if(valorOcularCCCLC > 1.4) {
                           agregarCodigo(15,true);
                           agregarCodigo(1, false);
                       } else
                             if(valorOcularCCCLC < 1.4 && valorOcularCCCLC !=0) {
                                 agregarCodigo(15,true);
                                 agregarCodigo(1, true);
                             } else
                                    if(valorOcularCCCLC < 0.2 && valorOcularCCCLC !=0) {
                                        agregarCodigo(28,true);
                                        agregarCodigo(1, true);
                                    }

            if(valorOcularCCCLC ==0) {
                                 agregarCodigo(27, false);
                                 agregarCodigo(41, false);
            }
    }

    private void alterarDebeUsarAudio() {

        boolean sordo = false;
        if(jcbHipoacusia.isSelected()) {

            int i = jcbGradoHipoAcusia.getSelectedIndex();
            this.jcbAudifonoIzquierdo.setSelected(i>=0);
            this.jcbAudifonoDerecho.setSelected(i>=0);
            this.jcbDobleEspejoRetrovisor.setSelected(i==2);
            
        } else {
                
                sordo = jcbSordera.isSelected();
                this.jcbAudifonoIzquierdo.setSelected(sordo);
                this.jcbAudifonoDerecho.setSelected(sordo);
                this.jcbDobleEspejoRetrovisor.setSelected(sordo);
                
                this.jcbAudifonoIzquierdo.setSelected(this.jcbAudifono.isSelected()|| sordo);
                this.jcbAudifonoDerecho.setSelected(this.jcbAudifono.isSelected()  || sordo);
        }

        
    }

    private void alterarDebeUsarVista() {
       
            float valorOcularSC    = 0,
                  valorOcularCC    = 0,
                  valorOcularCCCLC = 0,
                  valorOcularCCCLI = 0;
        
            valorOcularSC = Float.parseFloat((String)this.jtfscod.getValue())  +
                            Float.parseFloat((String)this.jtfscoi.getValue());

            valorOcularCC = Float.parseFloat((String)this.jtfccod.getValue())  +
                            Float.parseFloat((String)this.jtfccoi.getValue());
        
            valorOcularCCCLC = Float.parseFloat((String)this.jtfclcod.getValue()) +
                               Float.parseFloat((String)this.jtfclcoi.getValue());

            valorOcularCCCLI = Float.parseFloat((String)this.jtfcliod.getValue()) +
                               Float.parseFloat((String)this.jtfclioi.getValue());
                        
            jcbLentesCorrectores.setSelected(valorOcularCC > 0);
            jcbLentesDeContacto.setSelected(valorOcularCCCLC > 0);
            jcbLentesIntraoculares.setSelected(valorOcularCCCLI > 0);
           
            boolean b =  valorOcularCC    >= 1.5 ||
                         valorOcularCCCLC >= 1.5 ||
                         valorOcularCCCLI >= 1.5 || 
                         valorOcularSC    >= 1.5;


            agregarCodigo(1,!b);

            if(valorOcularCC > 0 && valorOcularCCCLC > 0 && (valorOcularCC < 1.5 || valorOcularCCCLC < 1.5)) {
                
                agregarCodigo(01, false);
//                agregarCodigo(15, false);
                agregarCodigo(35, false);
                agregarCodigo(27, true);
                agregarCodigo(39, false);
                agregarCodigo(40, false);
                
            } else 
                  if(valorOcularCC > 0 && valorOcularCCCLC > 0 && (valorOcularCC >= 1.5 || valorOcularCCCLC >= 1.5)) {
                         agregarCodigo(27, false);
                         agregarCodigo(39, false);
                         agregarCodigo(40, false);
                  }

    }

    private boolean calcularPresionesPrimeraToma() {

        boolean st;
        //int pts = Integer.parseInt(this.jtbPTPSistolica.getText());
        int pts = (Integer)this.jspinnerPTSistolica.getValue();
        int ptd = (Integer)this.jspinnerPTDiastolica.getValue();

        if (pts >= 150 || ptd >= 90)
            st = true;
        else
            st = false;

        return(st);
    }

    private boolean calcularPresionesSegundaToma() {

        boolean st;
        //int pts = Integer.parseInt(this.jtbPTPSistolica.getText());
        int pts = (Integer)this.jspinnerSTSistolica.getValue();
        int ptd = (Integer)this.jspinnerSTDiastolica.getValue();

        if (pts >= 150 || ptd >= 90)
            st = true;
        else
            st = false;

        return(st);
    }

    private void cargarObjetos() throws Exception {

          cargarLibreta();
          cargarEpisodioActitudes();
          cargarEpisodioagudezasauditivas();
          cargarEpisodioexameneneuro();
          
          cargarEpisodioagudezasvisuales();
          cargarEpisodiointegrideadme();
          cargarEpisodiopresion();
          cargarEpisodiovicios();
          cargarEpisodiomedicamentos();
          leerDatosPaciente();

          cargarImagenEpisodio();
    }


  private BufferedImage getImagenEpisodio() {

        BufferedImage img = null;

        SwingImageCreator sic = new SwingImageCreator();
        img = sic.createImage(this);

        return(img);
  }

  private void cargarImagenEpisodio() throws IOException {

      BufferedImage img = this.getImagenEpisodio();
      ByteArrayOutputStream bos = new ByteArrayOutputStream();

      ImageIO.write(img,"png",bos);

      agendaActual.getEpisodio().setImghistoria(bos.toByteArray());
  }

  private void cargarEpisodioActitudes() {

      if(episodioActitudes==null) {

          episodioActitudes = new Episodioactitudes();
      }

      
      episodioActitudes.setEpisodio(episodio);
      episodioActitudes.setDescripcion((String) this.jcbActitud.getSelectedItem());     
      agendaActual.getEpisodio().setEpisodioactitudes(episodioActitudes);
//      agendaActual.getEpisodio().setEpisodioactitudes(ea);
        //agendaActual.getEpisodio().getEpisodioactitudes().setEpisodio(episodio);
        //agendaActual.getEpisodio().getEpisodioactitudes().setDescripcion((String) this.jcbActitud.getSelectedItem());

    }

    private void cargarEpisodioagudezasauditivas() {

        if(episodioagudezasauditivas == null) {
            episodioagudezasauditivas = new Episodioagudezasauditivas();
            agendaActual.getEpisodio().setEpisodioagudezasauditivas(episodioagudezasauditivas);
        }
        
        agendaActual.getEpisodio().getEpisodioagudezasauditivas().setEpisodio(episodio);
        if(this.jcbHipoacusia.isSelected())
           agendaActual.getEpisodio().getEpisodioagudezasauditivas().setGrado(this.jcbGradoHipoAcusia.getSelectedIndex());
        else
           agendaActual.getEpisodio().getEpisodioagudezasauditivas().setGrado(0);

        if(this.jcbSordera.isSelected())
            agendaActual.getEpisodio().getEpisodioagudezasauditivas().setGrado(3);

        if(this.jcbAudifono.isSelected())
            agendaActual.getEpisodio().getEpisodioagudezasauditivas().setAudifono('B');
        else
            agendaActual.getEpisodio().getEpisodioagudezasauditivas().setAudifono('N');
        if(this.jcbAudifonoIUnilateral.isSelected() && this.jcbAudifono.isSelected()) 
            agendaActual.getEpisodio().getEpisodioagudezasauditivas().setAudifono('U');

    }

    private void cargarLibreta() throws Exception {

        char apto, indicar;

        if(this.jcbApto.isSelected())
              apto = 'S';
        else
              apto = 'N';
        ldc.setApto(apto);
        ldc.setCategoria(this.jtfCategoria.getText());
        ldc.setCodigo1(Integer.parseInt(this.jtbCodigo1.getText()));
        ldc.setCodigo2(Integer.parseInt(this.jtbCodigo2.getText()));
        ldc.setCodigo3(Integer.parseInt(this.jtbCodigo3.getText()));
        ldc.setCodigo4(Integer.parseInt(this.jtbCodigo7.getText()));
        ldc.setCodigo5(Integer.parseInt(this.jtbCodigo8.getText()));
        ldc.setCodigo6(Integer.parseInt(this.jtbCodigo9.getText()));

        ldc.setVigenciaanterior(jXVigenciaAnterior.getDate());

        if(this.jcbLentesCorrectores.isSelected())
            indicar = 'S';
        else
            indicar = 'N';
        ldc.setIndiclentescorrectores(indicar);

         if(this.jcbLentesDeContacto.isSelected())
            indicar = 'S';
        else
            indicar = 'N';
        ldc.setIndiclentescontacto(indicar);

        if(this.jcbDobleEspejoRetrovisor.isSelected())
            indicar = 'S';
        else
            indicar = 'N';
        ldc.setIndicdobleretrovisor(indicar);

        if(this.jcbAudifonoDerecho.isSelected())
            indicar = 'S';
        else
            indicar = 'N';
        ldc.setIndicaudifonoderecho(indicar);

        if(this.jcbAudifonoIzquierdo.isSelected())
            indicar = 'S';
        else
            indicar = 'N';
        ldc.setIndicaudifonoizquierdo(indicar);


        if(this.jcbLentesIntraoculares.isSelected())
            indicar = 'S';
        else
            indicar = 'N';
        ldc.setIndiclentesintraoculares(indicar);

        ldc.setEpisodio(episodio);

        ldc.setObservaciones(this.jtbObservaciones.getText());

        ldc.setVigencia(Integer.parseInt(this.jtfVigencia.getText()));

        Date va = this.jXVigenciaAnterior.getDate();

        ldc.setVigenciaanterior(va);

        this.jcbHomologacion.setEnabled(false);

        HashMap <String, Boolean> params = new HashMap();

        params.put("aspirante", this.jRadioButton1.isSelected());
        params.put("renovacion", this.jRadioButton2.isSelected());
        params.put("homologacion", jcbHomologacion.isSelected());
        params.put("cambiocat", this.jcbCambioCat.isSelected());
        params.put("solohomologa", this.jRadioButton3.isSelected());
        Integer tipo = (Integer) Sesion.getServiceDelegatorRemote().invoke("ServicioDeterminarTipoLibreta", params);
        
        ldc.setTipo(tipo);

        int idxproced = this.jcbProcedencia.getSelectedIndex();
        int proced = listaprocedencias.get(idxproced).getId();

        ldc.setProcedencia(proced);
        ldc.setDocumento(this.documento.getText());

        agendaActual.getEpisodio().setLibretasdeconducir(ldc);

    }


    private void cargarEpisodiopresion() {


        if(episodiopresion == null) {
            
            episodiopresion = new Episodiopresion();
            agendaActual.getEpisodio().setEpisodiopresion(episodiopresion);
        }
        agendaActual.getEpisodio().getEpisodiopresion().setEpisodio(episodio);
        agendaActual.getEpisodio().getEpisodiopresion().setSistolicaprimeratoma((Integer)this.jspinnerPTSistolica.getValue());
        agendaActual.getEpisodio().getEpisodiopresion().setDiastolicaprimeratoma((Integer)this.jspinnerPTDiastolica.getValue());
        agendaActual.getEpisodio().getEpisodiopresion().setSistolicaprimeratoma((Integer)this.jspinnerSTSistolica.getValue());
        agendaActual.getEpisodio().getEpisodiopresion().setDiastolicaprimeratoma((Integer)this.jspinnerSTDiastolica.getValue());

    }

    private void cargarEpisodiointegrideadme() {

        if(episodiointegrideadme==null) {
            episodiointegrideadme = new Episodiointegrideadme();
            
            agendaActual.getEpisodio().setEpisodiointegrideadme(episodiointegrideadme);
        }
        agendaActual.getEpisodio().getEpisodiointegrideadme().setEpisodio(episodio);
        agendaActual.getEpisodio().getEpisodiointegrideadme().setDescripcion((String) this.jcbActitud.getSelectedItem());

    }

    private void cargarEpisodioagudezasvisuales() {


        if(episodioagudezasvisuales == null) {
        
            episodioagudezasvisuales = new Episodioagudezasvisuales();
            agendaActual.getEpisodio().setEpisodioagudezasvisuales(episodioagudezasvisuales);
        }
        
        agendaActual.getEpisodio().getEpisodioagudezasvisuales().setEpisodio(episodio);
        float n = Float.parseFloat((String)this.jtfccod.getValue()); 
        agendaActual.getEpisodio().getEpisodioagudezasvisuales().setCcod(Float.parseFloat((String)this.jtfccod.getValue()));
        agendaActual.getEpisodio().getEpisodioagudezasvisuales().setCcoi(Float.parseFloat((String)this.jtfscoi.getValue()));
        agendaActual.getEpisodio().getEpisodioagudezasvisuales().setScod(Float.parseFloat((String)this.jtfscod.getValue()));
        agendaActual.getEpisodio().getEpisodioagudezasvisuales().setScoi(Float.parseFloat((String)this.jtfscoi.getValue()));
        agendaActual.getEpisodio().getEpisodioagudezasvisuales().setClentescontactood(Float.parseFloat((String)this.jtfclcod.getValue()));
        agendaActual.getEpisodio().getEpisodioagudezasvisuales().setClentescontactooi(Float.parseFloat((String)this.jtfclcoi.getValue()));
        agendaActual.getEpisodio().getEpisodioagudezasvisuales().setClentesintraocularesod(Float.parseFloat((String)this.jtfcliod.getValue()));
        agendaActual.getEpisodio().getEpisodioagudezasvisuales().setClentesintraocularesoi(Float.parseFloat((String)this.jtfclioi.getValue()));
        
        if(this.jcbEstrabismo.isSelected())
            agendaActual.getEpisodio().getEpisodioagudezasvisuales().setEstrabismo('S');
        else
            agendaActual.getEpisodio().getEpisodioagudezasvisuales().setEstrabismo('N');

        if(this.jcbNistagmus.isSelected())
            agendaActual.getEpisodio().getEpisodioagudezasvisuales().setNistagmus('S');
        else
            agendaActual.getEpisodio().getEpisodioagudezasvisuales().setNistagmus('N');

        if(this.jcbVisionBinocular.isSelected())
            agendaActual.getEpisodio().getEpisodioagudezasvisuales().setVisionbinocular('S');
        else
            agendaActual.getEpisodio().getEpisodioagudezasvisuales().setVisionbinocular('N');

        if(this.jcbDebajoBorde.isSelected())
            agendaActual.getEpisodio().getEpisodioagudezasvisuales().setPtosispalpebraldebajoborde(1);
        else
            agendaActual.getEpisodio().getEpisodioagudezasvisuales().setPtosispalpebraldebajoborde(0);

        if(this.jcbSobreBorde.isSelected())
            agendaActual.getEpisodio().getEpisodioagudezasvisuales().setPtosispalpebralsobreborde(1);
        else
            agendaActual.getEpisodio().getEpisodioagudezasvisuales().setPtosispalpebralsobreborde(0);

    }

    private void cargarEpisodioexameneneuro() {

        if(episodioexameneneuro==null) {
              episodioexameneneuro = new Episodioexameneneuro();
              agendaActual.getEpisodio().setEpisodioexameneneuro(episodioexameneneuro);
        }

        agendaActual.getEpisodio().getEpisodioexameneneuro().setEpisodio(episodio);
        agendaActual.getEpisodio().getEpisodioexameneneuro().setDescripcion((String) this.jcbActitud.getSelectedItem());

    }

    private int calcularEdad() {

        int edad = 0;

        GregorianCalendar hoy = new GregorianCalendar();
        Date ffnac = jXFechaNacimiento.getDate();
        GregorianCalendar fnac = new GregorianCalendar();
        fnac.setGregorianChange(ffnac);

        long tiempo = (hoy.getTimeInMillis() - fnac.getTimeInMillis());

        edad = (int) tiempo / (24 * 60 * 60 * 1000);

        if(edad==0)
            edad = 40;
        return(edad);
    }

    private void calcularVigencia() {
        try {
            cargarObjetos();
            ServicioCalculadorVigencia cb = new ServicioCalculadorVigencia();
            int edad = this.calcularEdad();
            cb.setUsarsegundatomapresiones(usarsegundatomapresiones);
            cb.setEdad(edad);
            cb.setLibreta(agendaActual.getEpisodio().getLibretasdeconducir());
            cb.setEpisodio(agendaActual.getEpisodio());
            cb.calcular();
            int mv = cb.getMaximavigencia();
            this.jtfVigencia.setText(mv + "");
            if (mv == 0) {
                noApto();
            }
        } catch (Exception ex) {
            logger.error("Error calculando vigencia : ");
            logger.error(new Date());
            logger.error(ex);
            logger.error("------------------------------------------------");
            DialogoError dlg = new DialogoError(null, "Error al calcular vigencia", "Error calculando vigencia", ex.toString(), true);
            dlg.setVisible(true);
        }

    }

   
    private void habilitarSegundaToma(boolean b) {

            this.jspinnerSTSistolica.setEnabled(b);
            this.jspinnerSTDiastolica.setEnabled(b);
            this.jspinnerSTDiastolica.setValue(50);
            this.jspinnerSTSistolica.setValue(50);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        rectanglePainter1 = new org.jdesktop.swingx.painter.RectanglePainter();
        buttonGroupSexo = new javax.swing.ButtonGroup();
        buttonHabitoAlcoholico = new javax.swing.ButtonGroup();
        jPanel10 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jXFechaSolicitud = new org.jdesktop.swingx.JXDatePicker();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jtbNumero = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtfCedulaIdentidad = new javax.swing.JFormattedTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jXVigenciaAnterior = new org.jdesktop.swingx.JXDatePicker();
        jPanel3 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jcbHTA = new javax.swing.JCheckBox();
        comboAfeccionesOculares = new javax.swing.JComboBox();
        jcbMarcapaso = new javax.swing.JCheckBox();
        jcbRenales = new javax.swing.JCheckBox();
        jcbDiabetes = new javax.swing.JCheckBox();
        jcbGlicemia = new javax.swing.JCheckBox();
        jcbFondoDeOjo = new javax.swing.JCheckBox();
        jcbTraumatismos = new javax.swing.JCheckBox();
        jcbAccidentes = new javax.swing.JCheckBox();
        jcbAfeccionesAuditivas = new javax.swing.JCheckBox();
        jcbOtrasEnfVasculares = new javax.swing.JCheckBox();
        jcbEnfermedaesNeuro = new javax.swing.JCheckBox();
        jcbFacturas = new javax.swing.JCheckBox();
        jcbAfeccionesOculares = new javax.swing.JCheckBox();
        jcbAfeccionesPsiquiatricas = new javax.swing.JCheckBox();
        jcbEpilepsiaControlada = new javax.swing.JCheckBox();
        jcbOtrasEnfNeuro = new javax.swing.JCheckBox();
        jcbIntoleranciaGluc = new javax.swing.JCheckBox();
        jcbProcedencia = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jtbApellido = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel7 = new javax.swing.JLabel();
        jtbNombre = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel8 = new javax.swing.JLabel();
        jcbDomicilio = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel9 = new javax.swing.JLabel();
        jtbCiudad = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel10 = new javax.swing.JLabel();
        jtfTelefono = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel11 = new javax.swing.JLabel();
        jtbCelular = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jrbMasculino = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        jXFechaNacimiento = new org.jdesktop.swingx.JXDatePicker();
        jrbFemenino = new javax.swing.JRadioButton();
        jcbEmail = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel18 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jcbMedicacionOcular = new javax.swing.JCheckBox();
        jcbHipoglucemiantes = new javax.swing.JCheckBox();
        jcbSicofarmacos = new javax.swing.JCheckBox();
        jcbMedicacionSiquiatrica = new javax.swing.JCheckBox();
        jtbHipoglucemiantes = new com.kcreativa.pitosycornetas.KTextBox();
        jtbSicofarmacos = new com.kcreativa.pitosycornetas.KTextBox();
        jtbMedPsiq = new com.kcreativa.pitosycornetas.KTextBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jtbDosisHipoglucemiantes = new com.kcreativa.pitosycornetas.KTextBox();
        jtbDosisSicofarmacos = new com.kcreativa.pitosycornetas.KTextBox();
        jtbDosisMedPsiq = new com.kcreativa.pitosycornetas.KTextBox();
        jPanel6 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jcbConsumeAlcohol = new javax.swing.JCheckBox();
        jrbFinesDeSemana = new javax.swing.JRadioButton();
        jrbDiariamente = new javax.swing.JRadioButton();
        jrbOcacionalmente = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jcbActitud = new javax.swing.JComboBox();
        jPanel8 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jspinnerPTSistolica = new javax.swing.JSpinner();
        jspinnerPTDiastolica = new javax.swing.JSpinner();
        jspinnerSTSistolica = new javax.swing.JSpinner();
        jspinnerSTDiastolica = new javax.swing.JSpinner();
        jPanel9 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jcbEstrabismo = new javax.swing.JCheckBox();
        jcbNistagmus = new javax.swing.JCheckBox();
        jcbVisionBinocular = new javax.swing.JCheckBox();
        jLabel16 = new javax.swing.JLabel();
        jcbSobreBorde = new javax.swing.JCheckBox();
        jcbDebajoBorde = new javax.swing.JCheckBox();
        jtfccod = new javax.swing.JSpinner();
        jtfccoi = new javax.swing.JSpinner();
        jtfscod = new javax.swing.JSpinner();
        jtfscoi = new javax.swing.JSpinner();
        jtfclcoi = new javax.swing.JSpinner();
        jtfclcod = new javax.swing.JSpinner();
        jtfcliod = new javax.swing.JSpinner();
        jtfclioi = new javax.swing.JSpinner();
        jPanel11 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jcbHipoacusia = new javax.swing.JCheckBox();
        jcbGradoHipoAcusia = new javax.swing.JComboBox();
        jcbSordera = new javax.swing.JCheckBox();
        jcbAudifono = new javax.swing.JCheckBox();
        jcbAudifonoIUnilateral = new javax.swing.JCheckBox();
        jPanel12 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jtbIntegridadME = new com.kcreativa.pitosycornetas.KTextBox();
        jPanel13 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jcbRotenberg = new javax.swing.JCheckBox();
        jcbDedoNariz = new javax.swing.JCheckBox();
        jPanel14 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jcbApto = new javax.swing.JCheckBox();
        jLabel54 = new javax.swing.JLabel();
        jcbLentesCorrectores = new javax.swing.JCheckBox();
        jcbLentesDeContacto = new javax.swing.JCheckBox();
        jcbLentesIntraoculares = new javax.swing.JCheckBox();
        jcbAudifonoIzquierdo = new javax.swing.JCheckBox();
        jcbAudifonoDerecho = new javax.swing.JCheckBox();
        jcbDobleEspejoRetrovisor = new javax.swing.JCheckBox();
        jPanel15 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jtfVigencia = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel56 = new javax.swing.JLabel();
        jtfCategoria = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel57 = new javax.swing.JLabel();
        jtbCodigo1 = new com.kcreativa.pitosycornetas.KTextBox();
        jtbCodigo2 = new com.kcreativa.pitosycornetas.KTextBox();
        jtbCodigo3 = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jtbCodigo4 = new com.kcreativa.pitosycornetas.KTextBox();
        jtbCodigo5 = new com.kcreativa.pitosycornetas.KTextBox();
        jtbCodigo6 = new com.kcreativa.pitosycornetas.KTextBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbObservaciones = new javax.swing.JTextArea();
        jLabel60 = new javax.swing.JLabel();
        documento = new com.kcreativa.pitosycornetas.KTextBox();
        paseIMM = new javax.swing.JCheckBox();
        jtbCodigo7 = new com.kcreativa.pitosycornetas.KTextBox();
        jtbCodigo8 = new com.kcreativa.pitosycornetas.KTextBox();
        jtbCodigo9 = new com.kcreativa.pitosycornetas.KTextBox();
        jButton1 = new javax.swing.JButton();
        jcbHomologacion = new javax.swing.JCheckBox();
        jcbCambioCat = new javax.swing.JCheckBox();
        jRadioButton3 = new javax.swing.JRadioButton();
        jLabel17 = new javax.swing.JLabel();

        org.jdesktop.layout.GroupLayout jPanel10Layout = new org.jdesktop.layout.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );

        setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jXFechaSolicitud.setEditable(false);
        jXFechaSolicitud.setEnabled(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logomedilab150x94.jpg"))); // NOI18N

        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("NÚMERO");

        jtbNumero.setEditable(false);
        jtbNumero.setText("00000");
        jtbNumero.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N

        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("CÉDULA DE IDENTIDAD");

        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("FECHA");

        jtfCedulaIdentidad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtfCedulaIdentidad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##,###,###"))));
        jtfCedulaIdentidad.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("ASPIRANTE");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("RENOVACIÓN");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("VIGENCIA LIBRETA ANTERIOR");

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));

        jLabel14.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel14.setText("ANTECEDENTES PERSONALES");

        jcbHTA.setText("HIPERTENSION ARTERIAL");
        jcbHTA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbHTAActionPerformed(evt);
            }
        });

        comboAfeccionesOculares.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MIOPÍA", "ASTIGMATISMO", "PRESBICIA", "OTROS" }));
        comboAfeccionesOculares.setEnabled(false);

        jcbMarcapaso.setText("MARCAPASO - BY PASS");
        jcbMarcapaso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbMarcapasoActionPerformed(evt);
            }
        });

        jcbRenales.setText("ENFERMEDADES RENALES");
        jcbRenales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbRenalesActionPerformed(evt);
            }
        });

        jcbDiabetes.setText("DIABETES");
        jcbDiabetes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbDiabetesActionPerformed(evt);
            }
        });

        jcbGlicemia.setText("GLICEMIA");
        jcbGlicemia.setEnabled(false);

        jcbFondoDeOjo.setText("FONDO DE OJO");
        jcbFondoDeOjo.setEnabled(false);

        jcbTraumatismos.setText("TRAUMATISMOS");

        jcbAccidentes.setText("ACCIDENTES");

        jcbAfeccionesAuditivas.setText("AFECCIONES AUDITIVAS");

        jcbOtrasEnfVasculares.setText("OTRAS ENF. VASCULARES");

        jcbEnfermedaesNeuro.setText("ENFERMEDADES NEUROLÓGICAS");
        jcbEnfermedaesNeuro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbEnfermedaesNeuroActionPerformed(evt);
            }
        });

        jcbFacturas.setText("FRACTURAS, CIRUGIA ÓSEA");

        jcbAfeccionesOculares.setBackground(new java.awt.Color(204, 204, 255));
        jcbAfeccionesOculares.setText("AFECCIONES OCULARES");
        jcbAfeccionesOculares.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbAfeccionesOcularesActionPerformed(evt);
            }
        });

        jcbAfeccionesPsiquiatricas.setText("AFECCIONES PSIQUIATRICAS");
        jcbAfeccionesPsiquiatricas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbAfeccionesPsiquiatricasActionPerformed(evt);
            }
        });

        jcbEpilepsiaControlada.setText("EPILEPSIA CONTROLADA");
        jcbEpilepsiaControlada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbEpilepsiaControladaActionPerformed(evt);
            }
        });

        jcbOtrasEnfNeuro.setText("OTRAS ENF. NEUROLOGICAS");
        jcbOtrasEnfNeuro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbOtrasEnfNeuroActionPerformed(evt);
            }
        });

        jcbIntoleranciaGluc.setText("INTOLERANCIA A LA GLUCOSA");
        jcbIntoleranciaGluc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbIntoleranciaGlucActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel3Layout.createSequentialGroup()
                                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel14)
                                    .add(jPanel3Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .add(jcbAfeccionesOculares)))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(comboAfeccionesOculares, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jcbHTA)
                                    .add(jcbMarcapaso)
                                    .add(jcbRenales)
                                    .add(jPanel3Layout.createSequentialGroup()
                                        .add(jcbDiabetes)
                                        .add(41, 41, 41)
                                        .add(jcbGlicemia)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jcbFondoDeOjo))
                                    .add(jcbTraumatismos))))
                        .add(165, 165, 165)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jcbOtrasEnfNeuro)
                            .add(jcbOtrasEnfVasculares)
                            .add(jcbAfeccionesAuditivas)
                            .add(jcbFacturas)
                            .add(jcbAfeccionesPsiquiatricas)
                            .add(jcbEpilepsiaControlada)
                            .add(jcbEnfermedaesNeuro)))
                    .add(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jcbAccidentes)
                            .add(jcbIntoleranciaGluc))))
                .addContainerGap(413, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jcbAfeccionesPsiquiatricas)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jcbFacturas))
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jPanel3Layout.createSequentialGroup()
                                .add(jLabel14)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(comboAfeccionesOculares, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jcbAfeccionesOculares))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jcbHTA)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jcbMarcapaso)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jcbRenales)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jcbDiabetes)
                                    .add(jcbGlicemia)
                                    .add(jcbFondoDeOjo))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jcbTraumatismos))
                            .add(jPanel3Layout.createSequentialGroup()
                                .add(26, 26, 26)
                                .add(jcbAfeccionesAuditivas)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jcbOtrasEnfVasculares)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jcbEnfermedaesNeuro)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jcbEpilepsiaControlada)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(jcbOtrasEnfNeuro)
                                .add(34, 34, 34)))
                        .add(jcbAccidentes)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jcbIntoleranciaGluc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, Short.MAX_VALUE))
        );

        jcbProcedencia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Particular" }));

        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("APELLIDOS");

        jtbApellido.setEditable(false);
        jtbApellido.setText("ap");
        jtbApellido.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N

        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("NOMBRES");

        jtbNombre.setEditable(false);
        jtbNombre.setText("nom");
        jtbNombre.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jtbNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtbNombreActionPerformed(evt);
            }
        });

        jLabel8.setText("DOMICILIO");

        jcbDomicilio.setEditable(false);
        jcbDomicilio.setText("calle y numero");
        jcbDomicilio.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N

        jLabel9.setText("CIUDAD");

        jtbCiudad.setEditable(false);
        jtbCiudad.setText("Montevideo");
        jtbCiudad.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N

        jLabel10.setText("TELÉFONO");

        jtfTelefono.setEditable(false);
        jtfTelefono.setText("numero ");
        jtfTelefono.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N

        jLabel11.setText("CELULAR");

        jtbCelular.setEditable(false);
        jtbCelular.setText("numero");
        jtbCelular.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N

        jLabel12.setText("EMAIL");

        jLabel15.setText("SEXO");

        jrbMasculino.setSelected(true);
        jrbMasculino.setText("Masculino");

        jLabel13.setText("FECHA DE NACIMIENTO");

        jXFechaNacimiento.setEditable(false);

        jrbFemenino.setText("Femenino");

        jcbEmail.setEditable(false);
        jcbEmail.setText("@");
        jcbEmail.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPanel4Layout.createSequentialGroup()
                        .add(jLabel13)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jXFechaNacimiento, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel15)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jrbMasculino)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jrbFemenino))
                    .add(jPanel4Layout.createSequentialGroup()
                        .add(jLabel10)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jtfTelefono, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 193, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jLabel11)
                        .add(18, 18, 18)
                        .add(jtbCelular, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 182, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel12)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jcbEmail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 181, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel4Layout.createSequentialGroup()
                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(jPanel4Layout.createSequentialGroup()
                                .add(jLabel8)
                                .add(2, 2, 2)
                                .add(jcbDomicilio, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel4Layout.createSequentialGroup()
                                .add(jLabel6)
                                .add(2, 2, 2)
                                .add(jtbApellido, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 199, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(18, 18, 18)
                                .add(jLabel7)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jtbNombre, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 178, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jtbCiudad, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(400, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6)
                    .add(jLabel7)
                    .add(jtbNombre, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jtbApellido, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel8)
                    .add(jcbDomicilio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel9)
                    .add(jtbCiudad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel10)
                    .add(jLabel11)
                    .add(jtfTelefono, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jtbCelular, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel12)
                    .add(jcbEmail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel13)
                    .add(jXFechaNacimiento, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel15)
                    .add(jrbMasculino)
                    .add(jrbFemenino))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel18.setText("Procedencia");

        jPanel5.setBackground(new java.awt.Color(153, 153, 153));

        jLabel19.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel19.setText("MEDICAMENTOS USADOS");

        jcbMedicacionOcular.setText("MEDICACIÓN OCULAR");
        jcbMedicacionOcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbMedicacionOcularActionPerformed(evt);
            }
        });

        jcbHipoglucemiantes.setText("HIPOGLUCEMIANTES");
        jcbHipoglucemiantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbHipoglucemiantesActionPerformed(evt);
            }
        });

        jcbSicofarmacos.setText("SICOFARMACOS");
        jcbSicofarmacos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbSicofarmacosActionPerformed(evt);
            }
        });

        jcbMedicacionSiquiatrica.setText("MEDICACIÓN SIQUIATRICA");
        jcbMedicacionSiquiatrica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbMedicacionSiquiatricaActionPerformed(evt);
            }
        });

        jtbHipoglucemiantes.setEditable(false);
        jtbHipoglucemiantes.setEditando(true);

        jtbSicofarmacos.setEditable(false);
        jtbSicofarmacos.setEditando(true);

        jtbMedPsiq.setEditable(false);
        jtbMedPsiq.setEditando(true);

        jCheckBox4.setText("ANTIHIPERTENSIVOS U OTROS");

        jLabel20.setText("DOSIS");

        jLabel21.setText("DOSIS");

        jLabel22.setText("DOSIS");

        jtbDosisHipoglucemiantes.setEditable(false);
        jtbDosisHipoglucemiantes.setEditando(true);

        jtbDosisSicofarmacos.setEditable(false);
        jtbDosisSicofarmacos.setEditando(true);

        jtbDosisMedPsiq.setEditable(false);
        jtbDosisMedPsiq.setEditando(true);

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel19)
                    .add(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jcbSicofarmacos)
                            .add(jcbMedicacionOcular)
                            .add(jPanel5Layout.createSequentialGroup()
                                .add(jcbHipoglucemiantes)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                    .add(jtbSicofarmacos, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(jtbHipoglucemiantes, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)))
                            .add(jPanel5Layout.createSequentialGroup()
                                .add(jcbMedicacionSiquiatrica)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jtbMedPsiq, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 366, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                .add(18, 18, 18)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel5Layout.createSequentialGroup()
                        .add(jLabel20)
                        .add(24, 24, 24)
                        .add(jtbDosisHipoglucemiantes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 217, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jCheckBox4)
                    .add(jPanel5Layout.createSequentialGroup()
                        .add(jLabel22)
                        .add(24, 24, 24)
                        .add(jtbDosisMedPsiq, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 217, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel5Layout.createSequentialGroup()
                        .add(jLabel21)
                        .add(24, 24, 24)
                        .add(jtbDosisSicofarmacos, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(jLabel19)
                .add(18, 18, 18)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel5Layout.createSequentialGroup()
                        .add(jCheckBox4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel20)
                            .add(jtbDosisHipoglucemiantes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(14, 14, 14)
                        .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel21)
                            .add(jtbDosisSicofarmacos, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel22)
                            .add(jtbDosisMedPsiq, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jPanel5Layout.createSequentialGroup()
                        .add(jcbMedicacionOcular)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jcbHipoglucemiantes)
                            .add(jtbHipoglucemiantes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jcbSicofarmacos)
                            .add(jtbSicofarmacos, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jcbMedicacionSiquiatrica)
                            .add(jtbMedPsiq, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(204, 204, 255));

        jLabel23.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel23.setText("HABITO ALCOHOLICO");

        jcbConsumeAlcohol.setText("CONSUME ALCOHOL");
        jcbConsumeAlcohol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbConsumeAlcoholActionPerformed(evt);
            }
        });

        buttonHabitoAlcoholico.add(jrbFinesDeSemana);
        jrbFinesDeSemana.setText("FINES DE SEMANA");
        jrbFinesDeSemana.setEnabled(false);

        buttonHabitoAlcoholico.add(jrbDiariamente);
        jrbDiariamente.setText("DIARIAMENTE");
        jrbDiariamente.setEnabled(false);

        buttonHabitoAlcoholico.add(jrbOcacionalmente);
        jrbOcacionalmente.setText("OCACIONALMENTE");
        jrbOcacionalmente.setEnabled(false);

        org.jdesktop.layout.GroupLayout jPanel6Layout = new org.jdesktop.layout.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel6Layout.createSequentialGroup()
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel23)
                    .add(jPanel6Layout.createSequentialGroup()
                        .add(123, 123, 123)
                        .add(jcbConsumeAlcohol)
                        .add(18, 18, 18)
                        .add(jrbFinesDeSemana)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jrbDiariamente)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jrbOcacionalmente)))
                .addContainerGap(450, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel6Layout.createSequentialGroup()
                .add(jLabel23)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jcbConsumeAlcohol)
                    .add(jrbFinesDeSemana)
                    .add(jrbDiariamente)
                    .add(jrbOcacionalmente))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(153, 153, 153));

        jLabel24.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel24.setText("ACTITUD Y COMPORTAMIENTO");

        jcbActitud.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SERENO", "ANSIOSO", "CONFLICTIVO" }));

        org.jdesktop.layout.GroupLayout jPanel7Layout = new org.jdesktop.layout.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel7Layout.createSequentialGroup()
                .add(jLabel24)
                .add(18, 18, 18)
                .add(jcbActitud, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 152, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(801, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel7Layout.createSequentialGroup()
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel24)
                    .add(jPanel7Layout.createSequentialGroup()
                        .add(16, 16, 16)
                        .add(jcbActitud, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(204, 204, 255));

        jLabel25.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel25.setText("PRESIÓN ARTERIAL");

        jLabel26.setFont(new java.awt.Font("Lucida Grande", 3, 13)); // NOI18N
        jLabel26.setText("PRIMERA TOMA");

        jLabel27.setText("SISTÓLICA");

        jLabel28.setText("DIASTÓLICA");

        jLabel29.setFont(new java.awt.Font("Lucida Grande", 3, 13)); // NOI18N
        jLabel29.setText("SEGUNDA TOMA");

        jLabel30.setText("SISTÓLICA");

        jLabel31.setText("DIASTÓLICA");

        jspinnerPTSistolica.setModel(new javax.swing.SpinnerNumberModel(120, 50, 200, 10));
        jspinnerPTSistolica.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jspinnerPTSistolicaStateChanged(evt);
            }
        });

        jspinnerPTDiastolica.setModel(new javax.swing.SpinnerNumberModel(80, 50, 200, 10));

        jspinnerSTSistolica.setModel(new javax.swing.SpinnerNumberModel(120, 50, 200, 10));
        jspinnerSTSistolica.setEnabled(false);
        jspinnerSTSistolica.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jspinnerSTSistolicaStateChanged(evt);
            }
        });

        jspinnerSTDiastolica.setModel(new javax.swing.SpinnerNumberModel(80, 50, 200, 10));
        jspinnerSTDiastolica.setEnabled(false);
        jspinnerSTDiastolica.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jspinnerSTDiastolicaStateChanged(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel8Layout = new org.jdesktop.layout.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createSequentialGroup()
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel25)
                    .add(jPanel8Layout.createSequentialGroup()
                        .add(52, 52, 52)
                        .add(jLabel26)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabel27)
                        .add(1, 1, 1)
                        .add(jspinnerPTSistolica, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel28)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jspinnerPTDiastolica, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jLabel29)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel30)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jspinnerSTSistolica, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 57, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jLabel31)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jspinnerSTDiastolica, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(278, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createSequentialGroup()
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel8Layout.createSequentialGroup()
                        .add(jLabel25)
                        .add(0, 90, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addContainerGap(64, Short.MAX_VALUE)
                        .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel26)
                            .add(jLabel27)
                            .add(jspinnerPTSistolica, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel28)
                            .add(jspinnerPTDiastolica, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel31)
                            .add(jLabel30)
                            .add(jspinnerSTDiastolica, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel29)
                            .add(jspinnerSTSistolica, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(153, 153, 153));

        jLabel32.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel32.setText("AGUDEZA VISUAL");

        jLabel33.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel33.setText("S/C");

        jLabel34.setText("O.D");

        jLabel35.setText("O.I");

        jLabel38.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel38.setText("C/C");

        jLabel39.setText("O.D");

        jLabel40.setText("O.I");

        jLabel41.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel41.setText("C/LENTES DE CONTACTO");

        jLabel42.setText("O.D");

        jLabel43.setText("O.I");

        jLabel44.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel44.setText("C/LENTES INTRAOCULARES");

        jLabel45.setText("O.D");

        jLabel46.setText("O.I");

        jcbEstrabismo.setText("ESTRABISMO");
        jcbEstrabismo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbEstrabismoActionPerformed(evt);
            }
        });

        jcbNistagmus.setText("NISTAGMUS");
        jcbNistagmus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbNistagmusActionPerformed(evt);
            }
        });

        jcbVisionBinocular.setSelected(true);
        jcbVisionBinocular.setText("VISION BINOCULAR");
        jcbVisionBinocular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbVisionBinocularActionPerformed(evt);
            }
        });

        jLabel16.setText("PTOSIS PAPEBRAL");

        jcbSobreBorde.setText("SOBRE BORDE");

        jcbDebajoBorde.setText("DEBAJO DE BORDE");

        jtfccod.setModel(new javax.swing.SpinnerListModel(new String[] {"0.00", "0.10", "0.20", "0.30", "0.40", "0.50", "0.60", "0.70", "0.80", "0.90", "1.00"}));
        jtfccod.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jtfccodStateChanged(evt);
            }
        });

        jtfccoi.setModel(new javax.swing.SpinnerListModel(new String[] {"0.00", "0.10", "0.20", "0.30", "0.40", "0.50", "0.60", "0.70", "0.80", "0.90", "1.00"}));
        jtfccoi.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jtfccoiStateChanged(evt);
            }
        });

        jtfscod.setModel(new javax.swing.SpinnerListModel(new String[] {"0.00", "0.10", "0.20", "0.30", "0.40", "0.50", "0.60", "0.70", "0.80", "0.90", "1.00"}));
        jtfscod.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jtfscodStateChanged(evt);
            }
        });

        jtfscoi.setModel(new javax.swing.SpinnerListModel(new String[] {"0.00", "0.10", "0.20", "0.30", "0.40", "0.50", "0.60", "0.70", "0.80", "0.90", "1.00"}));
        jtfscoi.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jtfscoiStateChanged(evt);
            }
        });

        jtfclcoi.setModel(new javax.swing.SpinnerListModel(new String[] {"0.00", "0.10", "0.20", "0.30", "0.40", "0.50", "0.60", "0.70", "0.80", "0.90", "1.00"}));
        jtfclcoi.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jtfclcoiStateChanged(evt);
            }
        });

        jtfclcod.setModel(new javax.swing.SpinnerListModel(new String[] {"0.00", "0.10", "0.20", "0.30", "0.40", "0.50", "0.60", "0.70", "0.80", "0.90", "1.00"}));
        jtfclcod.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jtfclcodStateChanged(evt);
            }
        });

        jtfcliod.setModel(new javax.swing.SpinnerListModel(new String[] {"0.00", "0.10", "0.20", "0.30", "0.40", "0.50", "0.60", "0.70", "0.80", "0.90", "1.00"}));
        jtfcliod.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jtfcliodStateChanged(evt);
            }
        });

        jtfclioi.setModel(new javax.swing.SpinnerListModel(new String[] {"0.00", "0.10", "0.20", "0.30", "0.40", "0.50", "0.60", "0.70", "0.80", "0.90", "1.00"}));
        jtfclioi.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jtfclioiStateChanged(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel9Layout = new org.jdesktop.layout.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel9Layout.createSequentialGroup()
                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel9Layout.createSequentialGroup()
                        .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel9Layout.createSequentialGroup()
                                .add(142, 142, 142)
                                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jPanel9Layout.createSequentialGroup()
                                        .add(jLabel34)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jtfscod, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(jPanel9Layout.createSequentialGroup()
                                        .add(jLabel35)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                        .add(jtfscoi, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                            .add(jPanel9Layout.createSequentialGroup()
                                .add(119, 119, 119)
                                .add(jLabel33)))
                        .add(18, 18, 18)
                        .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel40)
                            .add(jLabel39))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jtfccoi, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jtfccod, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel38))
                        .add(93, 93, 93)
                        .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel41)
                            .add(jPanel9Layout.createSequentialGroup()
                                .add(24, 24, 24)
                                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(jPanel9Layout.createSequentialGroup()
                                        .add(jLabel43)
                                        .add(16, 16, 16)
                                        .add(jtfclcoi, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(jPanel9Layout.createSequentialGroup()
                                        .add(jLabel42)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jtfclcod, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                        .add(36, 36, 36)
                        .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel44)
                            .add(jPanel9Layout.createSequentialGroup()
                                .add(24, 24, 24)
                                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(jPanel9Layout.createSequentialGroup()
                                        .add(jLabel46)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                        .add(jtfclioi, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(jPanel9Layout.createSequentialGroup()
                                        .add(jLabel45)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jtfcliod, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))))
                    .add(jLabel32)
                    .add(jPanel9Layout.createSequentialGroup()
                        .add(jLabel16)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jcbDebajoBorde)
                            .add(jPanel9Layout.createSequentialGroup()
                                .add(jcbSobreBorde)
                                .add(141, 141, 141)
                                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jPanel9Layout.createSequentialGroup()
                                        .add(183, 183, 183)
                                        .add(jcbNistagmus))
                                    .add(jPanel9Layout.createSequentialGroup()
                                        .add(55, 55, 55)
                                        .add(jcbEstrabismo)))
                                .add(36, 36, 36)
                                .add(jcbVisionBinocular)))))
                .addContainerGap(335, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel9Layout.createSequentialGroup()
                .add(jLabel32)
                .add(5, 5, 5)
                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel9Layout.createSequentialGroup()
                        .add(jLabel44)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jtfcliod, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel45))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jtfclioi, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel46)))
                    .add(jPanel9Layout.createSequentialGroup()
                        .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jLabel38)
                            .add(jLabel41))
                        .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel9Layout.createSequentialGroup()
                                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jLabel39)
                                    .add(jtfccod, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jLabel40)
                                    .add(jtfccoi, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                            .add(jPanel9Layout.createSequentialGroup()
                                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jPanel9Layout.createSequentialGroup()
                                        .add(2, 2, 2)
                                        .add(jLabel42)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jtfclcod, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jLabel43)
                                    .add(jtfclcoi, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                    .add(jPanel9Layout.createSequentialGroup()
                        .add(jLabel33)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel34)
                            .add(jtfscod, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jtfscoi, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel35))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel9Layout.createSequentialGroup()
                        .add(8, 8, 8)
                        .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jcbVisionBinocular)
                            .add(jcbNistagmus)
                            .add(jcbEstrabismo)))
                    .add(jcbSobreBorde)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel9Layout.createSequentialGroup()
                        .add(jLabel16)
                        .add(17, 17, 17)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jcbDebajoBorde)
                .addContainerGap())
        );

        jPanel11.setBackground(new java.awt.Color(204, 204, 255));

        jLabel50.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel50.setText("AGUDEZA AUDITIVA");

        jcbHipoacusia.setText("HIPOACUSIA");
        jcbHipoacusia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbHipoacusiaActionPerformed(evt);
            }
        });

        jcbGradoHipoAcusia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "LEVE", "MODERADA", "SEVERA" }));
        jcbGradoHipoAcusia.setEnabled(false);
        jcbGradoHipoAcusia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbGradoHipoAcusiaActionPerformed(evt);
            }
        });

        jcbSordera.setText("SORDERA");
        jcbSordera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbSorderaActionPerformed(evt);
            }
        });

        jcbAudifono.setText("AUDIFONO");
        jcbAudifono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbAudifonoActionPerformed(evt);
            }
        });

        jcbAudifonoIUnilateral.setText("UNILATERAL");
        jcbAudifonoIUnilateral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbAudifonoIUnilateralActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel11Layout = new org.jdesktop.layout.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel11Layout.createSequentialGroup()
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel50)
                    .add(jPanel11Layout.createSequentialGroup()
                        .add(131, 131, 131)
                        .add(jcbHipoacusia)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jcbGradoHipoAcusia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jcbSordera)
                        .add(32, 32, 32)
                        .add(jcbAudifono)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jcbAudifonoIUnilateral)))
                .addContainerGap(461, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel11Layout.createSequentialGroup()
                .add(jLabel50)
                .add(18, 18, 18)
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jcbHipoacusia)
                    .add(jcbGradoHipoAcusia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jcbSordera)
                    .add(jcbAudifono)
                    .add(jcbAudifonoIUnilateral))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(153, 153, 153));

        jLabel51.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel51.setText("INTEGRIDAD MUSCULO ESQUELÉTICA");

        jtbIntegridadME.setEditando(true);
        jtbIntegridadME.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtbIntegridadMEActionPerformed(evt);
            }
        });
        jtbIntegridadME.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtbIntegridadMEFocusLost(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel12Layout = new org.jdesktop.layout.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel12Layout.createSequentialGroup()
                .add(jLabel51)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jtbIntegridadME, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 616, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(305, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel51)
                    .add(jtbIntegridadME, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(204, 204, 255));

        jLabel52.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel52.setText("Exámen neurológico");

        jcbRotenberg.setText("PRUEBA DE ROTTENBERG");

        jcbDedoNariz.setText("PRUEBA DEDO - NARIZ");

        org.jdesktop.layout.GroupLayout jPanel13Layout = new org.jdesktop.layout.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel13Layout.createSequentialGroup()
                .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel52)
                    .add(jPanel13Layout.createSequentialGroup()
                        .add(156, 156, 156)
                        .add(jcbRotenberg)
                        .add(224, 224, 224)
                        .add(jcbDedoNariz)))
                .addContainerGap(441, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel13Layout.createSequentialGroup()
                .add(jLabel52)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jcbRotenberg)
                    .add(jcbDedoNariz))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBackground(new java.awt.Color(153, 153, 153));

        jLabel53.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel53.setText("DICTAMEN");

        jcbApto.setFont(new java.awt.Font("Lucida Grande", 3, 13)); // NOI18N
        jcbApto.setText("APTO");
        jcbApto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbAptoActionPerformed(evt);
            }
        });

        jLabel54.setFont(new java.awt.Font("Lucida Grande", 2, 13)); // NOI18N
        jLabel54.setText("DEBE USAR");

        jcbLentesCorrectores.setText("LENTES CORRECTORES");
        jcbLentesCorrectores.setEnabled(false);

        jcbLentesDeContacto.setText("LENTES DE CONTACTO");
        jcbLentesDeContacto.setEnabled(false);

        jcbLentesIntraoculares.setText("LENTES INTRAOCULARES");
        jcbLentesIntraoculares.setEnabled(false);

        jcbAudifonoIzquierdo.setText("AUDIFONO IZQUIERDO");
        jcbAudifonoIzquierdo.setEnabled(false);

        jcbAudifonoDerecho.setText("AUDIFONO DERECHO");
        jcbAudifonoDerecho.setEnabled(false);
        jcbAudifonoDerecho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbAudifonoDerechoActionPerformed(evt);
            }
        });

        jcbDobleEspejoRetrovisor.setText("DOBLE ESPEJO RETROVISOR");
        jcbDobleEspejoRetrovisor.setEnabled(false);
        jcbDobleEspejoRetrovisor.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jcbDobleEspejoRetrovisorStateChanged(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel14Layout = new org.jdesktop.layout.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel14Layout.createSequentialGroup()
                .add(jPanel14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel53)
                    .add(jPanel14Layout.createSequentialGroup()
                        .add(65, 65, 65)
                        .add(jcbApto)
                        .add(36, 36, 36)
                        .add(jLabel54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 76, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jPanel14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel14Layout.createSequentialGroup()
                                .add(jcbAudifonoIzquierdo)
                                .add(11, 11, 11)
                                .add(jcbAudifonoDerecho)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(jcbDobleEspejoRetrovisor))
                            .add(jPanel14Layout.createSequentialGroup()
                                .add(jcbLentesCorrectores)
                                .add(14, 14, 14)
                                .add(jcbLentesDeContacto)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jcbLentesIntraoculares)))))
                .addContainerGap(357, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel14Layout.createSequentialGroup()
                .add(jPanel14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel53)
                    .add(jPanel14Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jcbApto)
                            .add(jcbLentesCorrectores)
                            .add(jcbLentesIntraoculares)
                            .add(jLabel54)
                            .add(jcbLentesDeContacto))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jcbAudifonoIzquierdo)
                    .add(jcbDobleEspejoRetrovisor)
                    .add(jcbAudifonoDerecho))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel15.setBackground(new java.awt.Color(204, 204, 255));

        jLabel55.setText("VIGENCIA");

        jtfVigencia.setText("2");
        jtfVigencia.setEditando(true);
        jtfVigencia.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N

        jLabel56.setText("CATEGORÍA");

        jtfCategoria.setEditable(false);
        jtfCategoria.setText("A1");
        jtfCategoria.setEditando(true);

        jLabel57.setText("CÓDIGOS");

        jtbCodigo1.setColumns(3);
        jtbCodigo1.setText("000");
        jtbCodigo1.setEditando(true);

        jtbCodigo2.setColumns(3);
        jtbCodigo2.setText("000");
        jtbCodigo2.setEditando(true);

        jtbCodigo3.setColumns(3);
        jtbCodigo3.setText("000");
        jtbCodigo3.setEditando(true);

        jLabel58.setText("OBSERVACIONES");

        jLabel59.setText("CÓDIGOS NO LIMITANTES");

        jtbCodigo4.setColumns(3);
        jtbCodigo4.setText("000");
        jtbCodigo4.setEditando(true);

        jtbCodigo5.setColumns(3);
        jtbCodigo5.setText("000");
        jtbCodigo5.setEditando(true);

        jtbCodigo6.setColumns(3);
        jtbCodigo6.setText("000");
        jtbCodigo6.setEditando(true);

        jtbObservaciones.setColumns(20);
        jtbObservaciones.setRows(5);
        jScrollPane1.setViewportView(jtbObservaciones);

        jLabel60.setText("DOCUMENTO IMM");

        documento.setColumns(15);
        documento.setEditando(true);

        paseIMM.setText("PASE A IMM");

        jtbCodigo7.setColumns(3);
        jtbCodigo7.setText("000");
        jtbCodigo7.setEditando(true);

        jtbCodigo8.setColumns(3);
        jtbCodigo8.setText("000");
        jtbCodigo8.setEditando(true);

        jtbCodigo9.setColumns(3);
        jtbCodigo9.setText("000");
        jtbCodigo9.setEditando(true);

        org.jdesktop.layout.GroupLayout jPanel15Layout = new org.jdesktop.layout.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel15Layout.createSequentialGroup()
                .add(jLabel55)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jtfVigencia, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                .add(51, 51, 51)
                .add(jLabel56)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jtfCategoria, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(44, 44, 44)
                .add(jLabel57)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jtbCodigo3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jtbCodigo2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jtbCodigo1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel15Layout.createSequentialGroup()
                        .add(jtbCodigo8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel59))
                    .add(jtbCodigo7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jtbCodigo9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jtbCodigo4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jtbCodigo6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jtbCodigo5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(309, Short.MAX_VALUE))
            .add(jPanel15Layout.createSequentialGroup()
                .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel15Layout.createSequentialGroup()
                        .add(jLabel58)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 893, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel15Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel60)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(documento, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 99, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(29, 29, 29)
                        .add(paseIMM)))
                .addContainerGap(194, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel15Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel55)
                        .add(jtfVigencia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel56)
                        .add(jtfCategoria, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel15Layout.createSequentialGroup()
                        .add(jtbCodigo4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jtbCodigo5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel59))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jtbCodigo6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel15Layout.createSequentialGroup()
                        .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jtbCodigo1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jtbCodigo7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jtbCodigo2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel57)
                            .add(jtbCodigo8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jtbCodigo3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jtbCodigo9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel58)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 76, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel60)
                    .add(documento, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(paseIMM))
                .addContainerGap())
        );

        jButton1.setText("Almacenar");

        jcbHomologacion.setText("HOMOLOGACIÓN");
        jcbHomologacion.setEnabled(false);

        jcbCambioCat.setText("CAMBIO DE CATEGORÍA");
        jcbCambioCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbCambioCatActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("SOLO HOMOLOGACION");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 51, 153));
        jLabel17.setText("LIBRETA DE CONDUCIR");

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel13, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2Layout.createSequentialGroup()
                            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jPanel2Layout.createSequentialGroup()
                                    .add(jLabel5)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(jXVigenciaAnterior, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(jPanel2Layout.createSequentialGroup()
                                    .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 178, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(jLabel17))
                                .add(jPanel2Layout.createSequentialGroup()
                                    .add(jLabel3)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(jtfCedulaIdentidad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 118, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                            .add(13, 13, 13)
                            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jPanel2Layout.createSequentialGroup()
                                    .add(204, 204, 204)
                                    .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                        .add(jPanel2Layout.createSequentialGroup()
                                            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                                .add(jRadioButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 119, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .add(jPanel2Layout.createSequentialGroup()
                                                    .add(jRadioButton2)
                                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                    .add(jcbHomologacion))
                                                .add(jRadioButton3)
                                                .add(jcbCambioCat))
                                            .add(78, 78, 78))
                                        .add(jPanel2Layout.createSequentialGroup()
                                            .add(jLabel4)
                                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                            .add(jXFechaSolicitud, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                            .add(132, 132, 132))
                                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2Layout.createSequentialGroup()
                                            .add(jLabel2)
                                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                            .add(jtbNumero, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 119, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                                .add(jPanel2Layout.createSequentialGroup()
                                    .add(163, 163, 163)
                                    .add(jLabel18)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(jcbProcedencia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel12, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel11, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel8, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel14, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(jPanel15, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(380, 380, 380)
                        .add(jButton1)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(jLabel2)
                                .add(jtbNumero, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jLabel17))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jLabel4)
                            .add(jXFechaSolicitud, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jRadioButton1)
                        .add(0, 0, 0)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jRadioButton2)
                            .add(jcbHomologacion))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jcbCambioCat)
                        .add(3, 3, 3)
                        .add(jRadioButton3))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 112, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel3)
                            .add(jtfCedulaIdentidad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel5)
                    .add(jXVigenciaAnterior, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jcbProcedencia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel18))
                .add(18, 18, 18)
                .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel13, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton1)
                .addContainerGap(186, Short.MAX_VALUE))
        );

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        

        boolean b=this.jRadioButton2.isSelected();
        this.jcbHomologacion.setEnabled(b);
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jtbNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtbNombreActionPerformed
       
}//GEN-LAST:event_jtbNombreActionPerformed

    private void jcbHTAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbHTAActionPerformed
        
}//GEN-LAST:event_jcbHTAActionPerformed

    private void jcbMarcapasoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbMarcapasoActionPerformed
       
}//GEN-LAST:event_jcbMarcapasoActionPerformed

    private void jcbRenalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbRenalesActionPerformed
        

        agregarCodigo(17, jcbRenales.isSelected());
}//GEN-LAST:event_jcbRenalesActionPerformed

    private void jcbMedicacionOcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbMedicacionOcularActionPerformed
        
}//GEN-LAST:event_jcbMedicacionOcularActionPerformed

    private void jcbDiabetesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbDiabetesActionPerformed
        
        
        boolean b = jcbDiabetes.isSelected();
        
        jcbGlicemia.setEnabled(b);
        jcbFondoDeOjo.setEnabled(b);

        agregarCodigo(3,b);
    }//GEN-LAST:event_jcbDiabetesActionPerformed

    private void jcbHipoglucemiantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbHipoglucemiantesActionPerformed
        
        boolean pa = jcbHipoglucemiantes.isSelected();
        jtbHipoglucemiantes.setEditable(pa);
        jtbDosisHipoglucemiantes.setEditable(pa);

        if(pa)
            jtbHipoglucemiantes.requestFocus();
    }//GEN-LAST:event_jcbHipoglucemiantesActionPerformed

    private void jcbSicofarmacosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbSicofarmacosActionPerformed
        
        boolean z = jcbSicofarmacos.isSelected();
        jtbSicofarmacos.setEditable(z);
        jtbDosisSicofarmacos.setEditable(z);

        if(z)
            jtbSicofarmacos.requestFocus();
    }//GEN-LAST:event_jcbSicofarmacosActionPerformed

    private void jcbMedicacionSiquiatricaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbMedicacionSiquiatricaActionPerformed
        
          boolean z = jcbMedicacionSiquiatrica.isSelected();
          jtbMedPsiq.setEditable(z);
          jtbDosisMedPsiq.setEditable(z);

          if(z)
            jtbMedPsiq.requestFocus();

    }//GEN-LAST:event_jcbMedicacionSiquiatricaActionPerformed

    private void jcbConsumeAlcoholActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbConsumeAlcoholActionPerformed
        

        boolean z = jcbConsumeAlcohol.isSelected();
        boolean a = jrbFinesDeSemana.isSelected();
        boolean b = jrbDiariamente.isSelected();
        boolean c = jrbOcacionalmente.isSelected();

        jrbDiariamente.setEnabled(z);
        jrbOcacionalmente.setEnabled(z);
        jrbFinesDeSemana.setEnabled(z);

        if(z) {

            if(!a && !b && !c)
                jrbOcacionalmente.setSelected(true);
        } else {
                jrbDiariamente.setSelected(false);
                jrbOcacionalmente.setSelected(false);
                jrbFinesDeSemana.setSelected(false);
        }

    }//GEN-LAST:event_jcbConsumeAlcoholActionPerformed

    private void jcbHipoacusiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbHipoacusiaActionPerformed


        boolean z = jcbHipoacusia.isSelected();

        agregarCodigo(5,z);

        jcbGradoHipoAcusia.setEnabled(z);

        jcbSordera.setEnabled(!z);
        alterarDebeUsar();
    }//GEN-LAST:event_jcbHipoacusiaActionPerformed

    private void jcbAptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbAptoActionPerformed
        
        boolean z = jcbApto.isEnabled();

        jcbLentesCorrectores.setEnabled(z);
        jcbLentesDeContacto.setEnabled(z);
        jcbLentesIntraoculares.setEnabled(z);
        jcbAudifonoIzquierdo.setEnabled(z);
        jcbAudifonoDerecho.setEnabled(z);
        jcbDobleEspejoRetrovisor.setEnabled(z);

        calcularVigencia();

    }//GEN-LAST:event_jcbAptoActionPerformed


    private void jcbVisionBinocularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbVisionBinocularActionPerformed
        
        boolean b = jcbEstrabismo.isSelected() || jcbNistagmus.isSelected() || !jcbVisionBinocular.isSelected();

        agregarCodigo(28,b);
    }//GEN-LAST:event_jcbVisionBinocularActionPerformed

    private void jcbNistagmusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbNistagmusActionPerformed
        
         boolean b = jcbEstrabismo.isSelected() || jcbNistagmus.isSelected() || !jcbVisionBinocular.isSelected();

        agregarCodigo(1,b);
    }//GEN-LAST:event_jcbNistagmusActionPerformed

    private void jcbEstrabismoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbEstrabismoActionPerformed
        
         boolean b = jcbEstrabismo.isSelected() || jcbNistagmus.isSelected() || !jcbVisionBinocular.isSelected();

        agregarCodigo(1,b);
    }//GEN-LAST:event_jcbEstrabismoActionPerformed

    private void jcbAfeccionesOcularesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbAfeccionesOcularesActionPerformed
        
        boolean b;

        b = this.jcbAfeccionesOculares.isEnabled();
        this.comboAfeccionesOculares.setEnabled(b);
        agregarCodigo(1,b);
        
        alterarDebeUsar();
}//GEN-LAST:event_jcbAfeccionesOcularesActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
       
        boolean b;

        b=this.jRadioButton2.isSelected();
        this.jcbHomologacion.setEnabled(b);
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jspinnerPTSistolicaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jspinnerPTSistolicaStateChanged
       
//GEN-LAST:event_jspinnerPTSistolicaStateChanged
        hookPresiones();
    }

    private void jspinnerSTSistolicaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jspinnerSTSistolicaStateChanged
       

        hookCodigoPresiones();
}//GEN-LAST:event_jspinnerSTSistolicaStateChanged

    private void jspinnerSTDiastolicaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jspinnerSTDiastolicaStateChanged
        

        hookCodigoPresiones();
    }//GEN-LAST:event_jspinnerSTDiastolicaStateChanged

    private void jcbSorderaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbSorderaActionPerformed
        
        boolean z = jcbSordera.isSelected();

        jcbHipoacusia.setEnabled(!z);
        agregarCodigo(5, z);

        alterarDebeUsar();

    }//GEN-LAST:event_jcbSorderaActionPerformed

    private void jtfccodStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jtfccodStateChanged
       
        alterarDebeUsar();        
}//GEN-LAST:event_jtfccodStateChanged

    private void jtfccoiStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jtfccoiStateChanged
        
        alterarDebeUsar();
}//GEN-LAST:event_jtfccoiStateChanged

    private void jtfscodStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jtfscodStateChanged
        
         alterarDebeUsar();
}//GEN-LAST:event_jtfscodStateChanged

    private void jtfscoiStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jtfscoiStateChanged
        
         alterarDebeUsar();
}//GEN-LAST:event_jtfscoiStateChanged

    private void jtfclcoiStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jtfclcoiStateChanged
       
         alterarDebeUsar();
         generarCodigosLentesDeContacto();
}//GEN-LAST:event_jtfclcoiStateChanged

    private void jtfclcodStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jtfclcodStateChanged
                
        alterarDebeUsar();
        generarCodigosLentesDeContacto();
}//GEN-LAST:event_jtfclcodStateChanged

    private void jtfcliodStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jtfcliodStateChanged
        
        alterarDebeUsar();
}//GEN-LAST:event_jtfcliodStateChanged

    private void jtfclioiStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jtfclioiStateChanged
        
        alterarDebeUsar();
}//GEN-LAST:event_jtfclioiStateChanged

    private void jcbGradoHipoAcusiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbGradoHipoAcusiaActionPerformed
       
        alterarDebeUsar();
    }//GEN-LAST:event_jcbGradoHipoAcusiaActionPerformed

    private void jcbAudifonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbAudifonoActionPerformed
        
        agregarCodigo(30, jcbAudifonoIUnilateral.isSelected());
        agregarCodigo(5, jcbAudifonoIUnilateral.isSelected());
        alterarDebeUsar();
    }//GEN-LAST:event_jcbAudifonoActionPerformed

    private void jcbAudifonoDerechoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbAudifonoDerechoActionPerformed
        
    }//GEN-LAST:event_jcbAudifonoDerechoActionPerformed

    private void jcbDobleEspejoRetrovisorStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jcbDobleEspejoRetrovisorStateChanged
      
        agregarCodigo(29, jcbDobleEspejoRetrovisor.isSelected());
    }//GEN-LAST:event_jcbDobleEspejoRetrovisorStateChanged

    private void jcbEnfermedaesNeuroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbEnfermedaesNeuroActionPerformed
        
        agregarCodigo(6, jcbEnfermedaesNeuro.isSelected());
        agregarCodigo(6, !jcbEpilepsiaControlada.isSelected());
        agregarCodigo(7, jcbEpilepsiaControlada.isSelected());
        
        if(!jcbEnfermedaesNeuro.isSelected())
            jcbEnfermedaesNeuro.setSelected(jcbEpilepsiaControlada.isSelected());
}//GEN-LAST:event_jcbEnfermedaesNeuroActionPerformed

    private void jtbIntegridadMEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtbIntegridadMEActionPerformed
       
        agregarCodigo(8, jtbIntegridadME.getText().length()!=0);
    }//GEN-LAST:event_jtbIntegridadMEActionPerformed

    private void jcbAfeccionesPsiquiatricasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbAfeccionesPsiquiatricasActionPerformed
        
        agregarCodigo(10, jcbAfeccionesPsiquiatricas.isSelected());
    }//GEN-LAST:event_jcbAfeccionesPsiquiatricasActionPerformed

    private void jcbCambioCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbCambioCatActionPerformed
        
        boolean b;

        b=this.jRadioButton2.isSelected();
        this.jcbHomologacion.setEnabled(b);
    }//GEN-LAST:event_jcbCambioCatActionPerformed

    private void jtbIntegridadMEFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtbIntegridadMEFocusLost
                agregarCodigo(8, jtbIntegridadME.getText().length()!=0);
    }//GEN-LAST:event_jtbIntegridadMEFocusLost

    private void jcbAudifonoIUnilateralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbAudifonoIUnilateralActionPerformed
        
        agregarCodigo(30, jcbAudifonoIUnilateral.isSelected());
        agregarCodigo(5, jcbAudifonoIUnilateral.isSelected());
}//GEN-LAST:event_jcbAudifonoIUnilateralActionPerformed

    private void jcbEpilepsiaControladaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbEpilepsiaControladaActionPerformed
       
        agregarCodigo(6, !jcbEpilepsiaControlada.isSelected());
        agregarCodigo(7, jcbEpilepsiaControlada.isSelected());
        this.jcbEnfermedaesNeuro.setSelected(jcbEpilepsiaControlada.isSelected());
}//GEN-LAST:event_jcbEpilepsiaControladaActionPerformed

    private void jcbOtrasEnfNeuroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbOtrasEnfNeuroActionPerformed
        
        this.jcbEnfermedaesNeuro.setSelected(jcbOtrasEnfNeuro.isSelected() || jcbEpilepsiaControlada.isSelected());
}//GEN-LAST:event_jcbOtrasEnfNeuroActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jcbIntoleranciaGlucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbIntoleranciaGlucActionPerformed
        
        agregarCodigo(16, jcbIntoleranciaGluc.isSelected());
}//GEN-LAST:event_jcbIntoleranciaGlucActionPerformed

    private void jtbApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtbApellidoActionPerformed
        
    }//GEN-LAST:event_jtbApellidoActionPerformed

    public void cargarConsulta(Agenda unaagendaActual) {

        agendaActual = unaagendaActual;
        jcbCambioCat.setSelected(false);
        jcbHomologacion.setSelected(false);

        epis = agendaActual.getEpisodio();
        Integer id = epis.getId();
        Episodios theepis = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio", id);
        if(theepis!=null) {
            epis = theepis;
        }

        Libretasdeconducir unaldc = (Libretasdeconducir) Sesion.getServiceDelegatorRemote().invoke("CargarLibretaDeConducir", epis);
        ldc = epis.getLibretasdeconducir();
        ldc = unaldc;
        epis.setLibretasdeconducir(ldc);
        agendaActual.setEpisodio(epis);

        episodioActitudes = epis.getEpisodioactitudes();
        episodioagudezasauditivas = epis.getEpisodioagudezasauditivas();
        episodioexameneneuro = epis.getEpisodioexameneneuro();
        episodiopresion = epis.getEpisodiopresion();
        episodiointegrideadme = epis.getEpisodiointegrideadme();
        episodioagudezasvisuales = epis.getEpisodioagudezasvisuales();
        episodiosvicios = epis.getEpisodiosviciosCollection();
        
        episodiosmedicamentos = epis.getEpisodiosmedicamentosCollection();

        episodio = epis.getId();
        this.setidEpisodio(epis.getId());
        p = agendaActual.getEpisodio().getPaciente();
        this.jtfCedulaIdentidad.setText(""+p.getPersonas().getDocumento());
        this.jXFechaNacimiento.setDate(p.getPersonas().getFechanacimiento());

        Date fechita = p.getPersonas().getFechanacimiento();

        this.jXFechaSolicitud.setDate(agendaActual.getAgendaPK().getFecha());

        //unaldc = agendaActual.getEpisodio().getLibretasdeconducir();
        if(unaldc==null)  {
            
        } else {
                this.jXVigenciaAnterior.setDate(agendaActual.getEpisodio().getLibretasdeconducir().getVigenciaanterior());
                this.jtfCategoria.setText(agendaActual.getEpisodio().getLibretasdeconducir().getCategoria());

                int agencia = agendaActual.getEpisodio().getLibretasdeconducir().getProcedencia();
                if(agencia==900) agencia=22;
                jcbProcedencia.setSelectedIndex(buscarProcedencia(agencia));
                int tipo = agendaActual.getEpisodio().getLibretasdeconducir().getTipo();
                switch (tipo) {

                    case 1 :
                            jRadioButton1.setSelected(true);
                            break;
                    case 2 :
                            jRadioButton2.setSelected(true);
                            break;
                    case 3 :
                            jRadioButton2.setSelected(true);
                            jcbCambioCat.setSelected(true);
                            break;
                    case 4 :
                            jRadioButton2.setSelected(true);
                            jcbHomologacion.setSelected(true);
                            break;

                    case 5 :
                            jRadioButton2.setSelected(true);
                            jcbCambioCat.setSelected(true);
                            jcbHomologacion.setSelected(true);
                            break;
                }
        }
        this.jtbApellido.setText(p.getPersonas().getApellidos());
        this.jtbNombre.setText(p.getPersonas().getNombres());
        this.jcbDomicilio.setText(p.getPersonas().getDomicilio());
        this.jtbCiudad.setText(p.getPersonas().getCiudad());
        this.jtfTelefono.setText(p.getPersonas().getTelefono());
        this.jtbCelular.setText(p.getPersonas().getMovil());
        this.jcbEmail.setText(p.getPersonas().getEmail());
        this.jrbMasculino.setSelected(p.getPersonas().getSexo()=='M');
        this.jrbFemenino.setSelected(p.getPersonas().getSexo()=='F');

        int codigo1, codigo2, codigo3, codigo4, codigo5, codigo6;
        codigo1 = agendaActual.getEpisodio().getLibretasdeconducir().getCodigo1();
        codigo2 = agendaActual.getEpisodio().getLibretasdeconducir().getCodigo2();
        codigo3 = agendaActual.getEpisodio().getLibretasdeconducir().getCodigo3();
        codigo4 = agendaActual.getEpisodio().getLibretasdeconducir().getCodigo4();
        codigo5 = agendaActual.getEpisodio().getLibretasdeconducir().getCodigo5();
        codigo6 = agendaActual.getEpisodio().getLibretasdeconducir().getCodigo6();
        
        if((codigo1 + codigo2 + codigo3) != 0) {
            new DialogoInformacion(null, "Códigos de libreta de conducir anteriores", "El paciente tiene códigos : ", codigo1+"<br>" + codigo2+"<br>"+codigo3+"<br>"+codigo4+"<br>"+codigo5+"<br>"+codigo6+"<br> se han pasado a loscasilleros correspondientes" ,true).setVisible(true);
        }
        this.jtbCodigo1.setText(codigo1 + "");
        this.jtbCodigo2.setText(codigo2 + "");
        this.jtbCodigo3.setText(codigo3 + "");
        this.jtbCodigo7.setText(codigo4 + "");
        this.jtbCodigo8.setText(codigo5 + "");
        this.jtbCodigo9.setText(codigo6 + "");
    }

    private void hookCodigoPresiones() {

        boolean st;
         st = calcularPresionesSegundaToma();
         agregarCodigo(2, st);
    }

    private void agregarCodigo(int elCodigo, boolean crear) {

        int codigo1, codigo2, codigo3, codigo4, codigo5, codigo6;

        codigo1 = Integer.parseInt(this.jtbCodigo1.getText());
        codigo2 = Integer.parseInt(this.jtbCodigo2.getText());
        codigo3 = Integer.parseInt(this.jtbCodigo3.getText());
        
        codigo4 = Integer.parseInt(this.jtbCodigo4.getText());
        codigo5 = Integer.parseInt(this.jtbCodigo5.getText());
        codigo6 = Integer.parseInt(this.jtbCodigo6.getText());

        if(elCodigo != 35 && elCodigo != 41) {
        if(crear) {

            if(codigo1==elCodigo)
                codigo1 = 0;
            if(codigo2==elCodigo)
                codigo2 = 0;
            if(codigo3==elCodigo)
                codigo3 = 0;
            
            if(codigo1==0||codigo1==elCodigo)
                codigo1=elCodigo;
            else
                if(codigo2==0||codigo2==elCodigo)
                    codigo2=elCodigo;
                else
                    if(codigo3==0||codigo3==elCodigo)
                        codigo3=elCodigo;

        } else {
                    if(codigo1==elCodigo)
                        codigo1=0;
                
                    if(codigo2==elCodigo)
                            codigo2=0;
     
                    if(codigo3==elCodigo)
                            codigo3=0;

        }

        this.jtbCodigo1.setText(codigo1+"");
        this.jtbCodigo2.setText(codigo2+"");
        this.jtbCodigo3.setText(codigo3+"");
       } else 
            { 
            if(crear) {
              

                if(codigo4==0||codigo4==elCodigo)
                    codigo4=elCodigo;
                else
                    if(codigo5==0||codigo5==elCodigo)
                        codigo5=elCodigo;
                    else
                        if(codigo6==0||codigo6==elCodigo)
                            codigo6=elCodigo;

            } else {
                        if(codigo4==elCodigo)
                            codigo4=0;

                        if(codigo5==elCodigo)
                                codigo5=0;

                        if(codigo6==elCodigo)
                                codigo6=0;

            }
            this.jtbCodigo4.setText(codigo4+"");
            this.jtbCodigo5.setText(codigo5+"");
            this.jtbCodigo6.setText(codigo6+"");
       }
            
    }

    private void hookPresiones() {


        boolean st;


        st = calcularPresionesPrimeraToma();
        habilitarSegundaToma(st);

        this.usarsegundatomapresiones = st;
    }
    
    private void generarCodigosLentesDeContacto() {

        float valorOcularSC_OD        = 0,
              valorOcularSC_OI        = 0,
              valorOcularCC_OD        = 0,
              valorOcularCC_OI        = 0,
              valorOcularCCCLC_OD     = 0,
              valorOcularCCCLC_OI     = 0,
              valorOcularCCCLI_OD     = 0,
              valorOcularCCCLI_OI     = 0;
        
           valorOcularSC_OD = Float.parseFloat((String)this.jtfscod.getValue());
           valorOcularSC_OI = Float.parseFloat((String)this.jtfscoi.getValue());

           valorOcularCC_OD = Float.parseFloat((String)this.jtfccod.getValue());
           valorOcularCC_OI = Float.parseFloat((String)this.jtfccoi.getValue());
        
           valorOcularCCCLC_OD = Float.parseFloat((String)this.jtfclcod.getValue());
           valorOcularCCCLC_OI = Float.parseFloat((String)this.jtfclcoi.getValue());

           valorOcularCCCLI_OD = Float.parseFloat((String)this.jtfcliod.getValue());
           valorOcularCCCLI_OI = Float.parseFloat((String)this.jtfclioi.getValue());
           
           agregarCodigo(39, false);
           agregarCodigo(40, false);
           
           if(valorOcularCCCLC_OD > 0 && valorOcularCCCLC_OI == 0) {
               agregarCodigo(15, false);
               agregarCodigo(39, true);
           }
           if(valorOcularCCCLC_OD == 0 && valorOcularCCCLC_OI > 0) {
               agregarCodigo(15, false);
               agregarCodigo(40, true);
           }               
           
           agregarCodigo(15, valorOcularCCCLC_OD > 0 && valorOcularCCCLC_OI > 0);
              
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroupSexo;
    private javax.swing.ButtonGroup buttonHabitoAlcoholico;
    private javax.swing.JComboBox comboAfeccionesOculares;
    private com.kcreativa.pitosycornetas.KTextBox documento;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXDatePicker jXFechaNacimiento;
    private org.jdesktop.swingx.JXDatePicker jXFechaSolicitud;
    private org.jdesktop.swingx.JXDatePicker jXVigenciaAnterior;
    private javax.swing.JCheckBox jcbAccidentes;
    private javax.swing.JComboBox jcbActitud;
    private javax.swing.JCheckBox jcbAfeccionesAuditivas;
    private javax.swing.JCheckBox jcbAfeccionesOculares;
    private javax.swing.JCheckBox jcbAfeccionesPsiquiatricas;
    private javax.swing.JCheckBox jcbApto;
    private javax.swing.JCheckBox jcbAudifono;
    private javax.swing.JCheckBox jcbAudifonoDerecho;
    private javax.swing.JCheckBox jcbAudifonoIUnilateral;
    private javax.swing.JCheckBox jcbAudifonoIzquierdo;
    private javax.swing.JCheckBox jcbCambioCat;
    private javax.swing.JCheckBox jcbConsumeAlcohol;
    private javax.swing.JCheckBox jcbDebajoBorde;
    private javax.swing.JCheckBox jcbDedoNariz;
    private javax.swing.JCheckBox jcbDiabetes;
    private javax.swing.JCheckBox jcbDobleEspejoRetrovisor;
    private com.kcreativa.pitosycornetas.KTextBox jcbDomicilio;
    private com.kcreativa.pitosycornetas.KTextBox jcbEmail;
    private javax.swing.JCheckBox jcbEnfermedaesNeuro;
    private javax.swing.JCheckBox jcbEpilepsiaControlada;
    private javax.swing.JCheckBox jcbEstrabismo;
    private javax.swing.JCheckBox jcbFacturas;
    private javax.swing.JCheckBox jcbFondoDeOjo;
    private javax.swing.JCheckBox jcbGlicemia;
    private javax.swing.JComboBox jcbGradoHipoAcusia;
    private javax.swing.JCheckBox jcbHTA;
    private javax.swing.JCheckBox jcbHipoacusia;
    private javax.swing.JCheckBox jcbHipoglucemiantes;
    private javax.swing.JCheckBox jcbHomologacion;
    private javax.swing.JCheckBox jcbIntoleranciaGluc;
    private javax.swing.JCheckBox jcbLentesCorrectores;
    private javax.swing.JCheckBox jcbLentesDeContacto;
    private javax.swing.JCheckBox jcbLentesIntraoculares;
    private javax.swing.JCheckBox jcbMarcapaso;
    private javax.swing.JCheckBox jcbMedicacionOcular;
    private javax.swing.JCheckBox jcbMedicacionSiquiatrica;
    private javax.swing.JCheckBox jcbNistagmus;
    private javax.swing.JCheckBox jcbOtrasEnfNeuro;
    private javax.swing.JCheckBox jcbOtrasEnfVasculares;
    private javax.swing.JComboBox jcbProcedencia;
    private javax.swing.JCheckBox jcbRenales;
    private javax.swing.JCheckBox jcbRotenberg;
    private javax.swing.JCheckBox jcbSicofarmacos;
    private javax.swing.JCheckBox jcbSobreBorde;
    private javax.swing.JCheckBox jcbSordera;
    private javax.swing.JCheckBox jcbTraumatismos;
    private javax.swing.JCheckBox jcbVisionBinocular;
    private javax.swing.JRadioButton jrbDiariamente;
    private javax.swing.JRadioButton jrbFemenino;
    private javax.swing.JRadioButton jrbFinesDeSemana;
    private javax.swing.JRadioButton jrbMasculino;
    private javax.swing.JRadioButton jrbOcacionalmente;
    private javax.swing.JSpinner jspinnerPTDiastolica;
    private javax.swing.JSpinner jspinnerPTSistolica;
    private javax.swing.JSpinner jspinnerSTDiastolica;
    private javax.swing.JSpinner jspinnerSTSistolica;
    private com.kcreativa.pitosycornetas.KTextBox jtbApellido;
    private com.kcreativa.pitosycornetas.KTextBox jtbCelular;
    private com.kcreativa.pitosycornetas.KTextBox jtbCiudad;
    private com.kcreativa.pitosycornetas.KTextBox jtbCodigo1;
    private com.kcreativa.pitosycornetas.KTextBox jtbCodigo2;
    private com.kcreativa.pitosycornetas.KTextBox jtbCodigo3;
    private com.kcreativa.pitosycornetas.KTextBox jtbCodigo4;
    private com.kcreativa.pitosycornetas.KTextBox jtbCodigo5;
    private com.kcreativa.pitosycornetas.KTextBox jtbCodigo6;
    private com.kcreativa.pitosycornetas.KTextBox jtbCodigo7;
    private com.kcreativa.pitosycornetas.KTextBox jtbCodigo8;
    private com.kcreativa.pitosycornetas.KTextBox jtbCodigo9;
    private com.kcreativa.pitosycornetas.KTextBox jtbDosisHipoglucemiantes;
    private com.kcreativa.pitosycornetas.KTextBox jtbDosisMedPsiq;
    private com.kcreativa.pitosycornetas.KTextBox jtbDosisSicofarmacos;
    private com.kcreativa.pitosycornetas.KTextBox jtbHipoglucemiantes;
    private com.kcreativa.pitosycornetas.KTextBox jtbIntegridadME;
    private com.kcreativa.pitosycornetas.KTextBox jtbMedPsiq;
    private com.kcreativa.pitosycornetas.KTextBox jtbNombre;
    private com.kcreativa.pitosycornetas.KTextBox jtbNumero;
    private javax.swing.JTextArea jtbObservaciones;
    private com.kcreativa.pitosycornetas.KTextBox jtbSicofarmacos;
    private com.kcreativa.pitosycornetas.KTextBox jtfCategoria;
    private javax.swing.JFormattedTextField jtfCedulaIdentidad;
    private com.kcreativa.pitosycornetas.KTextBox jtfTelefono;
    private com.kcreativa.pitosycornetas.KTextBox jtfVigencia;
    private javax.swing.JSpinner jtfccod;
    private javax.swing.JSpinner jtfccoi;
    private javax.swing.JSpinner jtfclcod;
    private javax.swing.JSpinner jtfclcoi;
    private javax.swing.JSpinner jtfcliod;
    private javax.swing.JSpinner jtfclioi;
    private javax.swing.JSpinner jtfscod;
    private javax.swing.JSpinner jtfscoi;
    private javax.swing.JCheckBox paseIMM;
    private org.jdesktop.swingx.painter.RectanglePainter rectanglePainter1;
    // End of variables declaration//GEN-END:variables

    private void noApto() {

        jtbObservaciones.setText("NO APTO - PASE A IMM");
        jcbApto.setSelected(false);
    }

    private void prepararAlmacenar() throws Exception {
          cargarEpisodiovicios();
          cargarEpisodiomedicamentos();
          cargarEnfermedades();
    }
    // End of variables declaration

    private void welcome() {

        cargarProcedencias();

    }

    public void setidEpisodio(int unaid) {


        episodio = unaid;
    }

    public void setProcedencia(int p) {

        procedencia = p;
        this.jcbProcedencia.setSelectedIndex(p);
    }

    private void cargarProcedencias() {


        try {
                listaprocedencias = (List<Procedencias>)  Sesion.getServiceDelegatorRemote().invoke("ServicioCargarProcedencias", null);

                int largo = listaprocedencias.size();
                DefaultComboBoxModel dfcm = new DefaultComboBoxModel();

                for(int i=0; i!=largo;i++) {

                    dfcm.addElement(listaprocedencias.get(i).getDescripcion());
                }
                this.jcbProcedencia.setModel(dfcm);
                this.jcbProcedencia.setSelectedIndex(procedencia);
            } catch(Exception e) {
                logger.error("cargarProcedencias UILibretaDeConducir " +e);
            }
    }

    private void vaciarFormulario() {
        
    this.comboAfeccionesOculares.setSelectedIndex(0);

    jcbAfeccionesAuditivas.setSelected(false);
    jcbOtrasEnfVasculares.setSelected(false);
    jcbEnfermedaesNeuro.setSelected(false);
    jCheckBox4.setSelected(false);

    jRadioButton1.setSelected(true);
    jRadioButton2.setSelected(false);
    
    
    jcbAccidentes.setSelected(false);
    jcbActitud.setSelectedIndex(0);
    jcbAfeccionesOculares.setSelected(false);
    jcbAfeccionesPsiquiatricas.setSelected(false);
    jcbApto.setSelected(false);
    jcbAudifono.setSelected(false);
    jcbAudifonoDerecho.setSelected(false);
    jcbAudifonoIUnilateral.setSelected(false);
    jcbAudifonoIzquierdo.setSelected(false);
    jcbCambioCat.setSelected(false);
    jcbConsumeAlcohol.setSelected(false);
    jcbDebajoBorde.setSelected(false);
    jcbDedoNariz.setSelected(false);
    jcbDiabetes.setSelected(false);
    jcbDobleEspejoRetrovisor.setSelected(false);
    jcbDomicilio.setText("");
    jcbEmail.setText("");
    jcbEpilepsiaControlada.setSelected(false);
    jcbEstrabismo.setSelected(false);
    jcbFacturas.setSelected(false);
    jcbFondoDeOjo.setSelected(false);
    jcbGlicemia.setSelected(false);
    jcbGradoHipoAcusia.setSelectedIndex(0);
    jcbHTA.setSelected(false);
    jcbHipoacusia.setSelected(false);
    jcbHipoglucemiantes.setSelected(false);
    jcbHomologacion.setSelected(false);
    jcbLentesCorrectores.setSelected(false);
    jcbLentesDeContacto.setSelected(false);
    jcbLentesIntraoculares.setSelected(false);
    jcbMarcapaso.setSelected(false);
    jcbMedicacionOcular.setSelected(false);
    jcbMedicacionSiquiatrica.setSelected(false);
    jcbNistagmus.setSelected(false);
    jcbOtrasEnfNeuro.setSelected(false);
    jcbProcedencia.setSelectedIndex(0);
    jcbRenales.setSelected(false);
    jcbRotenberg.setSelected(false);
    jcbSicofarmacos.setSelected(false);
    jcbSobreBorde.setSelected(false);
    jcbSordera.setSelected(false);
    jcbTraumatismos.setSelected(false);
    jcbVisionBinocular.setSelected(true);
    jrbDiariamente.setSelected(false);
    jrbFemenino.setSelected(true);
    jrbFinesDeSemana.setSelected(false);
    jrbMasculino.setSelected(false);
    jrbOcacionalmente.setSelected(true);
    jspinnerPTDiastolica.setValue(80);
    jspinnerPTSistolica.setValue(120);
    jspinnerSTDiastolica.setValue(50);
    jspinnerSTSistolica.setValue(50);
    jspinnerSTSistolica.setEnabled(false);
    jspinnerSTDiastolica.setEnabled(false);
    jtfccod.setValue("0.00");
    jtfccoi.setValue("0.00");
    jtfclcod.setValue("0.00");
    jtfclcoi.setValue("0.00");
    jtfcliod.setValue("0.00");
    jtfclioi.setValue("0.00");
    jtfscod.setValue("1.00");
    jtfscoi.setValue("1.00");
    jtbApellido.setText("");
    jtbCelular.setText("");
    jtbCiudad.setText("");
    jtbCodigo1.setText("0");
    jtbCodigo2.setText("0");
    jtbCodigo3.setText("0");
    jtbCodigo4.setText("0");
    jtbCodigo5.setText("0");
    jtbCodigo6.setText("0");
    jtbDosisHipoglucemiantes.setText("");
    jtbDosisMedPsiq.setText("");
    jtbDosisSicofarmacos.setText("");
    jtbHipoglucemiantes.setText("");
    jtbIntegridadME.setText("");
    jtbMedPsiq.setText("");
    jtbNombre.setText("");
    jtbNumero.setText("");
    jtbObservaciones.setText("");
    
    jtbSicofarmacos.setText("");
    jtfCategoria.setText("A1");
    jtfCedulaIdentidad.setText("");
    jtfTelefono.setText("");
    jtfVigencia.setText("2");


    }

    private void leerDatosPaciente() {

        p.getPersonas().setApellidos(this.jtbApellido.getText());
        p.getPersonas().setNombres(this.jtbNombre.getText());
        p.getPersonas().setDomicilio(this.jcbDomicilio.getText());
        p.getPersonas().setCiudad(this.jtbCiudad.getText());
        p.getPersonas().setTelefono(this.jtfTelefono.getText());
        p.getPersonas().setMovil(this.jtbCelular.getText());
        p.getPersonas().setEmail(this.jcbEmail.getText());
        if(this.jrbMasculino.isSelected())
            p.getPersonas().setSexo('M');
        else
            p.getPersonas().setSexo('F');

    }
}
