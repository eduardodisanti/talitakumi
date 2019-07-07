

package talitakumi.medicos.ui;

import com.kcreativa.imgrendering.SwingImageCreator;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import talitakumi.model.core.Clientes;

/**
 *
 * @author rupertus
 */
public class UICarnetDeSalud extends javax.swing.JPanel {

    /** Creates new form UICarnetDeSalud */
    public UICarnetDeSalud() {
        initComponents();

        welcome();
    }

   
    public void setFormatoAntitetanica() {
        try {
            vencimientoantitetanica.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (ParseException ex) {
            //Logger.getLogger(UICarnetDeSalud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setFormatoPap() {
        try {
            fechapap.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (ParseException ex) {
            //Logger.getLogger(UICarnetDeSalud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setFormatoMamografia() {
        try {
            fechamamo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (ParseException ex) {
            //Logger.getLogger(UICarnetDeSalud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean getATT() {
        return(antitetanica.isSelected());
    }

    public boolean getAbdomenBlando() {
        return(efabdomenblando.isSelected());
    }

    

    public boolean getAbdomenDepresible() {
        return(efabdomendepresible.isSelected());
    }

    public boolean getAbdomenIndoloro() {
        return(efabdomendolor.isSelected());
    }

    public boolean getAbdomenTumores() {
        return(efabdomentumor.isSelected());
    }

    public boolean getActividadesFisicas() {
        return(actividadesfisicas.isSelected());
    }

    public boolean getAlergiaAlimentos() {
        return(antalergiaaliemntos.isSelected());
    }

    public boolean getAlergiaPolvos() {
        return(antalergiapolvos.isSelected());
    }

    public boolean getAlergiaMedicamentos() {
        return(antalergiamedicamentos.isSelected());
    }

    public boolean getAlergiaOtros() {
        return(antalergiaotros.isSelected());
    }

    public boolean getAntecedentesAuditivos() {
        return(antecedentesauditivos.isSelected());
    }

    public boolean getAntecedentesVenereos() {
         return(antvenereos.isSelected());
    }

    public boolean getAntecedentesQuirurgicos() {
         return(antquirurgicos.isSelected());
    }

    public boolean getAntecedentesSiquiatricos() {
         return(antsiquiatricos.isSelected());
    }

    public boolean getAsma() {
         return(asma.isSelected());
    }

    public String getCicatrizAbdomen() {
        return(abdomencicatriz.getText());
    }

    public boolean getCuello() {
        return(cuello.isSelected());
    }

    public int getCuelloGrado() {
        return(gradocuello.getSelectedIndex());
    }

    public int getDiastolicaPT() {
        return((Integer)jspinnerPTDiastolica.getValue());
    }

    public int getDiastolicaST() {
        return((Integer)jspinnerSTDiastolica.getValue());
    }

    public boolean getGripe() {
        return(gripe.isSelected());
    }

    public boolean getHepatitisA() {
        return(hepatitisa.isSelected());
    }

    public boolean getHepatitisC() {
        return(hepatitisc.isSelected());
    }

    public String getImpresionGeneral() {
        return(impresiongeneral.getText());
    }

    public String getObservaciones() {
        return(observaciones.getText());
    }

    public Character getSugerenciacontpeso() {

        if(efsugerenciacontrolpeso.isSelected())
            return('S');
        else
            return('N');
    }

    public Character getSugerenciaejercicios() {

        if(efsugerenciaejercicios.isSelected())
            return('S');
        else
            return('N');
    }

    public Character getSugerenciaexlab() {

        if(efsugerenciaexlaboratorio.isSelected())
            return('S');
        else
            return('N');
    }

    public Character getSugerenciascm() {

        if(efsugerenciaconsultamedica.isSelected())
            return('S');
        else
            return('N');
    }

    public boolean getTifoidea() {
        return(tifoidea.isSelected());
    }

    public Float getOD() {
        return(Float.parseFloat((String)visionlejosOD.getValue()));
    }

    public Float getOI() {
        return(Float.parseFloat((String)visionlejosOD.getValue()));
    }

    public Integer getPeso() {

        return((Integer)peso.getValue());
    }

    public Integer getPulso() {
        return((Integer)pulso.getValue());
    }

    public Integer getFrecuencia() {
        return((Integer)frecuencia.getValue());
    }

    public Integer getSistolicaPT() {
        return((Integer)jspinnerPTSistolica.getValue());
    }

    public Integer getSistolicaST() {
        return((Integer)jspinnerSTSistolica.getValue());
    }

    public boolean getEmbarazos() {
         return(antEmbarazos.isSelected());
    }

    public boolean getAbortos() {
         return(antAbortos.isSelected());
    }

    public boolean getDIU() {
         return(antDIU.isSelected());
    }

    public boolean getACO() {
         return(antACO.isSelected());
    }

    public boolean getAutoexamenDeMama() {
         return(antAutoexamenMama.isSelected());
    }

    public String getEstudiosMedicos() {
       return(controlesmedicos.getText());
    }

    public boolean getFamHTA() {
        return(antecedentesHTA.isSelected());
    }

    public boolean getFamDiabetes() {
        return(antecedentesDiabetes.isSelected());
    }

    public boolean getFamMarcapasos() {
        //return(antecedentesMarcapasos.isSelected());
        return(false);
    }

    public boolean getFamOtros() {
        return(antecedentesOtros.isSelected());
    }

    public boolean getHerniaID() {
        return(efherniaid.isSelected());
    }

    public boolean getHerniaII() {
        return(efherniaii.isSelected());
    }

    public boolean getFosasLumbaresLibres() {
        return(effosaslumbares.isSelected());
    }

    public String getLinfoganglionar() {
        return(linfoganglionar.getText());
    }

    public boolean getMAV() {
        return(mav.isSelected());
    }

    public String getEnfPleuropulmonares() {
        return(pleuropulmonar.getText());
    }

    public boolean getMamasTumores() {
        return(tumoresmamas.isSelected());
    }

    public boolean getMicosis() {
       return(micosis.isSelected());
    }

    public boolean getAlergias() {
       return(alergias.isSelected());
    }

    public boolean getMuscular() {
        return(efsectormuscular.isSelected());
    }

    public boolean getOsteoArticular() {
        return(efosteoarticular.isSelected());
    }

    public boolean getPsoriasis() {
       return(psoriasis.isSelected());
    }

    public boolean getLunares() {
       return(lunares.isSelected());
    }

    public boolean getPapaNicolau() {
         return(antpapanicolau.isSelected());
    }

    public boolean getNoCorrespondePAP() {
         return(nocorrespondepap.isSelected());
    }

    public boolean getNoCorrespondeMAMO() {
         return(nocorrespondemamo.isSelected());

    }
    public Date getPapaNicolauFecha() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = sdf.parse(fechapap.getText());
        return(d);
    }

    public String getPapaNicolauMedico() {
        return(medicopap.getText());
    }

    public String getPapaNicolauClinica() {
        return(clinicapap.getText());
    }

    public boolean getMamo() {
         return(mamografia.isSelected());
    }

    public Date getMamografiaFecha() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = sdf.parse(fechamamo.getText());
        return(d);
    }

    public String getMamografiaMedico() {
        return(medicomamo.getText());
    }

    public String getMamografiaClinica() {
        return(clinicamamo.getText());
    }


    public boolean getHepatitis() {
        return(hepatitis.isSelected());
    }

    public boolean getGastritis() {
        return(gastritis.isSelected());
    }

    public boolean getInfeccionRinon() {
        return(infeccionrinon.isSelected());
    }

    public boolean getInternaciones() {
        return(internaciones.isSelected());
    }

    public boolean getLitiasisRenal() {
        return(litiasisrenal.isSelected());
    }
    
    public boolean getColicosNefriticos() {
        return(colicosnefriticos.isSelected());
    }

    public boolean getInfeccionUrinariaBaja() {
        return(infeccionurinariabaja.isSelected());
    }

    public boolean getRI() {
        return(svri.isSelected());
    }

    public boolean getSoplos() {
        return(soplos.isSelected());
    }

    public Integer getTalla() {
       return((Integer)talla.getValue());
    }

    public Integer getIMC() {
       Float f = Float.parseFloat(imc.getText());
       return(f.intValue());
    }

    public Integer getPerimetroAbdominal() {
       return((Integer)perimetroabdominal.getValue());
    }

    public boolean getTendinitis() {
        return(tendinitis.isSelected());
    }

    public boolean getLumbalgias() {
        return(lumbalgias.isSelected());
    }

    public boolean getFracturas() {
        return(fracturas.isSelected());
    }

    public boolean getArtrosis() {
        return(artrosis.isSelected());
    }

    public boolean getUlcera() {
        return(ulcera.isSelected());
    }

    public boolean getDiverticulos() {
        return(diverticulos.isSelected());
    }

    public boolean getAlteracionesDelTransito() {
        return(alteracionesdeltransito.isSelected());
    }

    public boolean getHemorroides() {
        return(hemorroides.isSelected());
    }

    public boolean getLitiasisVesicular() {
        return(litiasisvesicular.isSelected());
    }

    public boolean getNeumopatia() {
         return(neumopatia.isSelected());
    }

    public boolean getBronquitis() {
         return(broncopatias.isSelected());
    }
    public char getCartaMedicoTrantate() {

        char aux = 'N';
        
        if(cartamedico.isSelected())
            aux = 'S';

        return(aux);
    }

    public char getCausaMedica() {

        char aux = 'N';

        if(causamedica.isSelected())
            aux = 'S';
        System.out.println(" causa medica es : " + causamedica.isSelected());

        return(aux);

    }

    public boolean getDiabetico() {
        return(antecedenteDiabetes.isSelected());
    }

    public boolean getFumador() {
        return(antecedenteFumador.isSelected());
    }

    public boolean getBebedor() {
        return(antecedenteAlcohol.isSelected());
    }

    public boolean getHTA() {
        return(antecedentesHTA.isSelected());
    }

    public boolean getPalpitaciones() {
        return(antpalpitaciones.isSelected());
    }

    public boolean getTaquicardia() {
        return(anttaquicardias.isSelected());
    }

     public boolean getArritmias() {
        return(antarritmia.isSelected());
    }

    public boolean getSoplo() {
        return(antsoplo.isSelected());
    }

    public boolean getFibreReumatica() {
        return(antfiebrereumatica.isSelected());
    }

    public boolean getInfartoMiocardio() {
        return(antinfartomiocardio.isSelected());
    }

    public String getOtrosCardio() {
        return(cardioOtros.getText());
    }

    public String getMedicamentos() {

        return(antecedentesMedicamentos.getText());
    }

    public boolean getTiroideo() {
        return(antecedenteTiroideos.isSelected());
    }

    public boolean getUsaLentes() {
        return(usalentes.isSelected());
    }

    public Date getVenceATT() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = new Date();
        try {
            d = sdf.parse(vencimientoantitetanica.getText());
        } catch (ParseException ex) {
            Logger.getLogger(UICarnetDeSalud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return(d);
    }

    public boolean getVenosas() {
        return(svvarices.isSelected());
    }

    public boolean getVertigo() {
        return(vertigo.isSelected());
    }

    public boolean getEpilepsia() {
        return(epilepsia.isSelected());
    }


    public int getVigenciaMedico() {

        return(plazo.getSelectedIndex());
    }

    public void setApellidos(String apellidos) {
        this.jtbApellido3.setText(apellidos);
    }

    public void setCiudad(String ciudad) {
        this.jtbCiudad3.setText(ciudad);
    }

    public void setDocumento(int documento) {

        this.jtfCedulaIdentidad.setText(documento+"");
    }

    public void setDomicilio(String domicilio) {
        this.jcbDomicilio3.setText(domicilio);
    }

    public void setEdad(Date fechanacimiento) {

        Calendar hoy = Calendar.getInstance();
        Calendar fechaNac = Calendar.getInstance();
        
        fechaNac.setTime(fechanacimiento);

    	int years = hoy.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
    	if (hoy.get(Calendar.MONTH) < fechaNac.get(Calendar.MONTH)) years --;
    	if (hoy.get(Calendar.MONTH) == fechaNac.get(Calendar.MONTH) &&
    		hoy.get(Calendar.DATE) < fechaNac.get(Calendar.DATE)	) years --;
    	


        this.edad.setText(years + "");
    }

    public void setFechaNacimiento(Date fechanacimiento) {
        this.jXFechaNacimiento.setDate(fechanacimiento);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        jXFechaNacimiento.setFormats(dateFormat);
    }

    public void setGuardarListener(ActionListener al) {
          this.almacenar.addActionListener(al);
    }

    public void setMovil(String movil) {
        this.jtbCelular3.setText(movil);
    }

    public void setNombre(String nombres) {
        this.jtbNombre3.setText(nombres);
    }

    public void setSexo(char sexo) {

        this.jrbFemenino.setSelected(sexo=='F');
    }

    public void setTelefono(String telefono) {
        this.jtfTelefono3.setText(telefono);
    }

    private void hookCodigoPresiones() {
         boolean st;
         st = calcularPresionesSegundaToma();
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

    private void welcome() {

        Date hoy;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        hoy = new java.util.Date();
        fechaHoy.setFormats("dd/MM/yyyy");
        fechaHoy.setDate(hoy);
        fechaHoy.setEditable(false);
        plazo.setSelectedIndex(2);

        this.visionlejosOD.setValue("1.00");
        this.visionlejosOI.setValue("1.00");
        this.visionlejosCCOD.setValue("0.00");
        this.visionlejosCCOI.setValue("0.00");

        peso.setValue((Integer) 65);
        talla.setValue((Integer) 170);

        setFormatoMamografia();
        setFormatoPap();
        setFormatoAntitetanica();

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
        bgPeso = new javax.swing.ButtonGroup();
        jPanel5 = new javax.swing.JPanel();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        fechaHoy = new org.jdesktop.swingx.JXDatePicker();
        jPanel7 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jtbApellido3 = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel34 = new javax.swing.JLabel();
        jtbNombre3 = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel35 = new javax.swing.JLabel();
        jcbDomicilio3 = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel36 = new javax.swing.JLabel();
        jtbCiudad3 = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel37 = new javax.swing.JLabel();
        jtfTelefono3 = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel38 = new javax.swing.JLabel();
        jtbCelular3 = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jrbMasculino3 = new javax.swing.JRadioButton();
        jLabel41 = new javax.swing.JLabel();
        jXFechaNacimiento = new org.jdesktop.swingx.JXDatePicker();
        jrbFemenino = new javax.swing.JRadioButton();
        jcbEmail = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel5 = new javax.swing.JLabel();
        edad = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel43 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel42 = new javax.swing.JLabel();
        jcbProcedencia = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jtfCedulaIdentidad = new javax.swing.JFormattedTextField();
        jLabel44 = new javax.swing.JLabel();
        jtbNumero = new com.kcreativa.pitosycornetas.KTextBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        antecedentesHTA = new javax.swing.JCheckBox();
        antecedentesDiabetes = new javax.swing.JCheckBox();
        antecedentesOtros = new javax.swing.JCheckBox();
        jPanel8 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        antecedenteAlcohol = new javax.swing.JCheckBox();
        antecedenteDiabetes = new javax.swing.JCheckBox();
        antecedenteTiroideos = new javax.swing.JCheckBox();
        antecedenteFumador = new javax.swing.JCheckBox();
        jLabel47 = new javax.swing.JLabel();
        antecedentesMedicamentos = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel48 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        cardioOtros = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        antsiquiatricos = new javax.swing.JCheckBox();
        internaciones = new javax.swing.JCheckBox();
        antecedentesauditivos = new javax.swing.JCheckBox();
        antvenereos = new javax.swing.JCheckBox();
        jLabel63 = new javax.swing.JLabel();
        antEmbarazos = new javax.swing.JCheckBox();
        antAbortos = new javax.swing.JCheckBox();
        antDIU = new javax.swing.JCheckBox();
        antACO = new javax.swing.JCheckBox();
        antAutoexamenMama = new javax.swing.JCheckBox();
        antpapanicolau = new javax.swing.JCheckBox();
        antalergiaaliemntos = new javax.swing.JCheckBox();
        antalergiapolvos = new javax.swing.JCheckBox();
        antalergiamedicamentos = new javax.swing.JCheckBox();
        antalergiaotros = new javax.swing.JCheckBox();
        antpalpitaciones = new javax.swing.JCheckBox();
        anttaquicardias = new javax.swing.JCheckBox();
        antarritmia = new javax.swing.JCheckBox();
        antsoplo = new javax.swing.JCheckBox();
        antfiebrereumatica = new javax.swing.JCheckBox();
        jCheckBox10 = new javax.swing.JCheckBox();
        antinfartomiocardio = new javax.swing.JCheckBox();
        jCheckBox12 = new javax.swing.JCheckBox();
        asma = new javax.swing.JCheckBox();
        neumopatia = new javax.swing.JCheckBox();
        broncopatias = new javax.swing.JCheckBox();
        hepatitis = new javax.swing.JCheckBox();
        usalentes = new javax.swing.JCheckBox();
        tendinitis = new javax.swing.JCheckBox();
        lumbalgias = new javax.swing.JCheckBox();
        fracturas = new javax.swing.JCheckBox();
        artrosis = new javax.swing.JCheckBox();
        gastritis = new javax.swing.JCheckBox();
        ulcera = new javax.swing.JCheckBox();
        diverticulos = new javax.swing.JCheckBox();
        hemorroides = new javax.swing.JCheckBox();
        alteracionesdeltransito = new javax.swing.JCheckBox();
        litiasisvesicular = new javax.swing.JCheckBox();
        infeccionrinon = new javax.swing.JCheckBox();
        litiasisrenal = new javax.swing.JCheckBox();
        colicosnefriticos = new javax.swing.JCheckBox();
        infeccionurinariabaja = new javax.swing.JCheckBox();
        antquirurgicos = new javax.swing.JCheckBox();
        mamografia = new javax.swing.JCheckBox();
        vertigo = new javax.swing.JCheckBox();
        epilepsia = new javax.swing.JCheckBox();
        medicopap = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        clinicapap = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel14 = new javax.swing.JLabel();
        medicomamo = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel17 = new javax.swing.JLabel();
        clinicamamo = new com.kcreativa.pitosycornetas.KTextBox();
        tipodiabetesap = new javax.swing.JComboBox();
        tiroideosap = new javax.swing.JComboBox();
        fechapap = new com.kcreativa.pitosycornetas.KFormattedTextBox();
        fechamamo = new com.kcreativa.pitosycornetas.KFormattedTextBox();
        nocorrespondepap = new javax.swing.JCheckBox();
        nocorrespondemamo = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        peso = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        talla = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        pulso = new javax.swing.JSpinner();
        jLabel16 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        visionlejosOD = new javax.swing.JSpinner();
        actividadesfisicas = new javax.swing.JCheckBox();
        jLabel30 = new javax.swing.JLabel();
        antitetanica = new javax.swing.JCheckBox();
        jLabel31 = new javax.swing.JLabel();
        impresiongeneral = new com.kcreativa.pitosycornetas.KTextBox();
        cartamedico = new javax.swing.JCheckBox();
        causamedica = new javax.swing.JCheckBox();
        jLabel32 = new javax.swing.JLabel();
        plazo = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        micosis = new javax.swing.JCheckBox();
        alergias = new javax.swing.JCheckBox();
        psoriasis = new javax.swing.JCheckBox();
        lunares = new javax.swing.JCheckBox();
        linfoganglionar = new com.kcreativa.pitosycornetas.KTextBox();
        cuello = new javax.swing.JCheckBox();
        gradocuello = new javax.swing.JComboBox();
        svvarices = new javax.swing.JCheckBox();
        svri = new javax.swing.JCheckBox();
        soplos = new javax.swing.JCheckBox();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jspinnerPTSistolica = new javax.swing.JSpinner();
        jLabel51 = new javax.swing.JLabel();
        jspinnerPTDiastolica = new javax.swing.JSpinner();
        jLabel53 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jspinnerSTSistolica = new javax.swing.JSpinner();
        jLabel62 = new javax.swing.JLabel();
        jspinnerSTDiastolica = new javax.swing.JSpinner();
        tumoresmamas = new javax.swing.JCheckBox();
        mav = new javax.swing.JCheckBox();
        efabdomendolor = new javax.swing.JCheckBox();
        efabdomentumor = new javax.swing.JCheckBox();
        efabdomenblando = new javax.swing.JCheckBox();
        efabdomendepresible = new javax.swing.JCheckBox();
        efherniaid = new javax.swing.JCheckBox();
        efherniaii = new javax.swing.JCheckBox();
        effosaslumbares = new javax.swing.JCheckBox();
        efosteoarticular = new javax.swing.JCheckBox();
        efsectormuscular = new javax.swing.JCheckBox();
        efsugerenciaconsultamedica = new javax.swing.JCheckBox();
        efsugerenciaexlaboratorio = new javax.swing.JCheckBox();
        efsugerenciacontrolpeso = new javax.swing.JCheckBox();
        efsugerenciaejercicios = new javax.swing.JCheckBox();
        jLabel18 = new javax.swing.JLabel();
        perimetroabdominal = new javax.swing.JSpinner();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        observaciones = new javax.swing.JTextArea();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        frecuencia = new javax.swing.JSpinner();
        jLabel22 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        controlesmedicos = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        visionlejosOI = new javax.swing.JSpinner();
        gripe = new javax.swing.JCheckBox();
        hepatitisa = new javax.swing.JCheckBox();
        hepatitisc = new javax.swing.JCheckBox();
        tifoidea = new javax.swing.JCheckBox();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        abdomencicatriz = new com.kcreativa.pitosycornetas.KTextBox();
        pleuropulmonar = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel67 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        imc = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        visionlejosCCOD = new javax.swing.JSpinner();
        jLabel72 = new javax.swing.JLabel();
        visionlejosCCOI = new javax.swing.JSpinner();
        jLabel73 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        vencimientoantitetanica = new com.kcreativa.pitosycornetas.KFormattedTextBox();
        labelimc = new javax.swing.JLabel();
        almacenar = new javax.swing.JButton();
        jLabel68 = new javax.swing.JLabel();
        otorgadoanterior = new javax.swing.JFormattedTextField();
        otorgadovence = new javax.swing.JFormattedTextField();
        jLabel74 = new javax.swing.JLabel();

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );

        jFormattedTextField1.setText("jFormattedTextField1");

        jTextField1.setText("jTextField1");

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setFocusable(false);
        jPanel1.setMaximumSize(new java.awt.Dimension(800, 5000));
        jPanel1.setMinimumSize(new java.awt.Dimension(800, 5000));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 2500));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logomedilab150x94.jpg"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 153));
        jLabel2.setText("CARNE DE SALUD BÁSICO");

        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("FECHA");

        fechaHoy.setFormats("dd/mm/aaaa");

        jPanel7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel33.setText("APELLIDOS");

        jtbApellido3.setText("ap");
        jtbApellido3.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N

        jLabel34.setText("NOMBRES");

        jtbNombre3.setText("nom");
        jtbNombre3.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jtbNombre3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtbNombre3ActionPerformed(evt);
            }
        });

        jLabel35.setText("DOMICILIO");

        jcbDomicilio3.setText("calle y numero");
        jcbDomicilio3.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N

        jLabel36.setText("CIUDAD");

        jtbCiudad3.setText("Montevideo");
        jtbCiudad3.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N

        jLabel37.setText("TELÉFONO");

        jtfTelefono3.setText("numero ");
        jtfTelefono3.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N

        jLabel38.setText("CELULAR");

        jtbCelular3.setText("numero");
        jtbCelular3.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N

        jLabel39.setText("EMAIL");

        jLabel40.setText("SEXO");

        buttonGroup1.add(jrbMasculino3);
        jrbMasculino3.setSelected(true);
        jrbMasculino3.setText("Masculino");

        jLabel41.setText("FECHA DE NACIMIENTO");

        buttonGroup1.add(jrbFemenino);
        jrbFemenino.setText("Femenino");

        jcbEmail.setText("@");
        jcbEmail.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N

        jLabel5.setText("EDAD");

        edad.setEditable(false);

        jLabel43.setText("ESTADO CIVIL");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SOLTERO", "CASADO", "DIVORCIADO", "VIUDO" }));

        jLabel42.setText("PROCEDENCIA");

        jcbProcedencia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Particular" }));

        org.jdesktop.layout.GroupLayout jPanel7Layout = new org.jdesktop.layout.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel7Layout.createSequentialGroup()
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel7Layout.createSequentialGroup()
                        .add(jLabel33)
                        .add(6, 6, 6)
                        .add(jtbApellido3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 199, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabel34)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jtbNombre3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 178, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel7Layout.createSequentialGroup()
                        .add(jLabel35)
                        .add(2, 2, 2)
                        .add(jcbDomicilio3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel36, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jtbCiudad3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 255, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(177, 177, 177))
                    .add(jPanel7Layout.createSequentialGroup()
                        .add(jLabel37)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jtfTelefono3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 193, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jLabel38)
                        .add(18, 18, 18)
                        .add(jtbCelular3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 182, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel39)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jcbEmail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 181, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel7Layout.createSequentialGroup()
                        .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel7Layout.createSequentialGroup()
                                .add(jLabel41)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jXFechaNacimiento, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanel7Layout.createSequentialGroup()
                                .add(jLabel43)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jComboBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel7Layout.createSequentialGroup()
                                .add(jLabel40)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jrbMasculino3)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jrbFemenino))
                            .add(jPanel7Layout.createSequentialGroup()
                                .add(jLabel42)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jcbProcedencia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel5)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(edad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel33)
                    .add(jLabel34)
                    .add(jtbNombre3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jtbApellido3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel35)
                    .add(jcbDomicilio3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel36)
                    .add(jtbCiudad3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel37)
                    .add(jLabel38)
                    .add(jtfTelefono3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jtbCelular3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel39)
                    .add(jcbEmail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel41)
                    .add(jXFechaNacimiento, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel40)
                    .add(jrbMasculino3)
                    .add(jrbFemenino)
                    .add(jLabel5)
                    .add(edad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel43)
                    .add(jComboBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel42)
                    .add(jcbProcedencia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(10, 10, 10))
        );

        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("CÉDULA DE IDENTIDAD");

        jtfCedulaIdentidad.setEditable(false);
        jtfCedulaIdentidad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtfCedulaIdentidad.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N

        jLabel44.setForeground(new java.awt.Color(102, 102, 102));
        jLabel44.setText("NÚMERO");

        jtbNumero.setEditable(false);
        jtbNumero.setText("00000");
        jtbNumero.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N

        jPanel3.setBackground(new java.awt.Color(153, 153, 255));

        jLabel45.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jLabel45.setText("ANTECEDENTES FAMILIARES");

        antecedentesHTA.setText("HIPERTENSION ARTERIAL");
        antecedentesHTA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                antecedentesHTAActionPerformed(evt);
            }
        });

        antecedentesDiabetes.setText("DIABETES");
        antecedentesDiabetes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                antecedentesDiabetesActionPerformed(evt);
            }
        });

        antecedentesOtros.setText("OTROS");
        antecedentesOtros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                antecedentesOtrosActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(antecedentesHTA)
                        .add(32, 32, 32)
                        .add(antecedentesDiabetes)
                        .add(18, 18, 18)
                        .add(antecedentesOtros))
                    .add(jLabel45))
                .addContainerGap(550, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(20, 20, 20)
                .add(jLabel45)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(antecedentesHTA)
                    .add(antecedentesDiabetes)
                    .add(antecedentesOtros))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(153, 153, 153));

        jLabel46.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jLabel46.setText("ANTECEDENTES PERSONALES");

        antecedenteAlcohol.setText("CONSUME ALCOHOL");
        antecedenteAlcohol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                antecedenteAlcoholActionPerformed(evt);
            }
        });

        antecedenteDiabetes.setText("DIABETICOS");
        antecedenteDiabetes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                antecedenteDiabetesActionPerformed(evt);
            }
        });

        antecedenteTiroideos.setText("TIROIDEOS");
        antecedenteTiroideos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                antecedenteTiroideosActionPerformed(evt);
            }
        });

        antecedenteFumador.setText("FUMADOR");
        antecedenteFumador.setBackground(new java.awt.Color(204, 204, 255));
        antecedenteFumador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                antecedenteFumadorActionPerformed(evt);
            }
        });

        jLabel47.setText("MEDICAMENTOS");

        antecedentesMedicamentos.setEditando(true);

        jLabel48.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        jLabel48.setText("ALERGIAS");

        jLabel52.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        jLabel52.setText("CARDIOVASCULARES");

        jLabel54.setText("OTROS");

        cardioOtros.setEditando(true);

        jLabel55.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        jLabel55.setText("PLEUROPULMONARES");

        jLabel56.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        jLabel56.setText("DIGESTIVOS");

        jLabel57.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        jLabel57.setText("RENALES");

        jLabel58.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        jLabel58.setText("OSTEOARTICULARES");

        jLabel59.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        jLabel59.setText("OFTALMOLOGICOS");

        jLabel60.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        jLabel60.setText("NEUROLOGICOS");

        antsiquiatricos.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        antsiquiatricos.setText("ANTECEDENTES SIQUIATRICOS");
        antsiquiatricos.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        internaciones.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        internaciones.setText("INTERNACIONES");
        internaciones.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        antecedentesauditivos.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        antecedentesauditivos.setText("ANTECEDENTES AUDITIVOS");
        antecedentesauditivos.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        antvenereos.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        antvenereos.setText("ANTECEDENTES VENEREOS");
        antvenereos.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel63.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        jLabel63.setText("OBSTETRICOS");

        antEmbarazos.setText("EMBARAZOS");

        antAbortos.setText("ABORTOS");

        antDIU.setText("D.I.U");

        antACO.setText("A.C.O");

        antAutoexamenMama.setText("AUTOEXAMEN DE MAMA");

        antpapanicolau.setText("PAPANICOLAU");

        antalergiaaliemntos.setText("ALIMENTOS");

        antalergiapolvos.setText("POLVOS");

        antalergiamedicamentos.setText("MEDICAMENTOS");

        antalergiaotros.setText("OTROS");

        antpalpitaciones.setText("PALPITACIONES");

        anttaquicardias.setText("TAQUICARDIA");

        antarritmia.setText("ARRITMIA");

        antsoplo.setText("SOPLO");

        antfiebrereumatica.setText("FIEBRE REUMATICA");

        jCheckBox10.setText("HTA");

        antinfartomiocardio.setText("INFARTO MIOCARDIO");

        jCheckBox12.setText("E.C.G");
        jCheckBox12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox12ActionPerformed(evt);
            }
        });

        asma.setText("ASMA");

        neumopatia.setText("NEUMOPATIA");

        broncopatias.setText("BRONQUITIS");

        hepatitis.setText("HEPATITIS");

        usalentes.setText("USA LENTES");

        tendinitis.setText("TENDINITIS");

        lumbalgias.setText("LUMBALGIAS");

        fracturas.setText("FRACTURAS");

        artrosis.setText("ARTROSIS");
        artrosis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                artrosisActionPerformed(evt);
            }
        });

        gastritis.setText("GASTRITIS");

        ulcera.setText("ULCERA");

        diverticulos.setText("DIVERTICULOS");

        hemorroides.setText("HEMORROIDES");

        alteracionesdeltransito.setText("ALTERACIONES DEL TRANSITO");

        litiasisvesicular.setText("LITIASIS VESICULAR");

        infeccionrinon.setText("INFECCION DE RIÑON");

        litiasisrenal.setText("LITIASIS RENAL");

        colicosnefriticos.setText("COLICOS NEFRITICOS");

        infeccionurinariabaja.setText("INFECCION URINARIA BAJA");

        antquirurgicos.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        antquirurgicos.setText("ANTECEDENTES QUIRURGICOS");
        antquirurgicos.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        mamografia.setText("MAMOGRAFIA");

        vertigo.setText("Vertigo");

        epilepsia.setText("Epilepsia");

        jLabel10.setText("MEDICO");

        jLabel11.setText("CLINICA");

        jLabel14.setText("MEDICO");

        jLabel17.setText("CLINICA");

        tipodiabetesap.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tipo I", "Tipo II" }));

        tiroideosap.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "HIPERTIROIDISMO", "HIPOTIROIDISMO", "BOCIO" }));

        fechapap.setEditando(true);
        fechapap.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("dd/MM/yyyy"))));

        fechamamo.setEditando(true);
        fechamamo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));

        nocorrespondepap.setText("NO CORRESPONDE");

        nocorrespondemamo.setText("NO CORRESPONDE");

        org.jdesktop.layout.GroupLayout jPanel8Layout = new org.jdesktop.layout.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel8Layout.createSequentialGroup()
                        .add(hepatitis)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(gastritis)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(ulcera)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(diverticulos)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(alteracionesdeltransito))
                    .add(jPanel8Layout.createSequentialGroup()
                        .add(antecedenteFumador)
                        .add(18, 18, 18)
                        .add(antecedenteAlcohol)
                        .add(44, 44, 44)
                        .add(antecedenteDiabetes)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(tipodiabetesap, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(antecedenteTiroideos)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(tiroideosap, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel8Layout.createSequentialGroup()
                        .add(hemorroides)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(litiasisvesicular))
                    .add(jLabel57)
                    .add(jLabel46)
                    .add(jPanel8Layout.createSequentialGroup()
                        .add(jLabel47)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(antecedentesMedicamentos, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 736, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel8Layout.createSequentialGroup()
                        .add(112, 112, 112)
                        .add(antalergiapolvos)
                        .add(18, 18, 18)
                        .add(antalergiamedicamentos)
                        .add(18, 18, 18)
                        .add(antalergiaotros))
                    .add(antalergiaaliemntos)
                    .add(jLabel48)
                    .add(jLabel52)
                    .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel8Layout.createSequentialGroup()
                            .add(jCheckBox12)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(jLabel54)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(cardioOtros, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel8Layout.createSequentialGroup()
                            .add(jCheckBox10)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(antpalpitaciones)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                            .add(anttaquicardias)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(antarritmia)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(antsoplo)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(antfiebrereumatica)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(antinfartomiocardio)))
                    .add(jLabel55)
                    .add(jPanel8Layout.createSequentialGroup()
                        .add(asma)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(neumopatia)
                        .add(18, 18, 18)
                        .add(broncopatias))
                    .add(jPanel8Layout.createSequentialGroup()
                        .add(infeccionrinon)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(litiasisrenal)
                        .add(25, 25, 25)
                        .add(colicosnefriticos)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(infeccionurinariabaja))
                    .add(jLabel59)
                    .add(jPanel8Layout.createSequentialGroup()
                        .add(tendinitis)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(lumbalgias)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(fracturas)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(artrosis))
                    .add(jLabel58)
                    .add(internaciones)
                    .add(antquirurgicos)
                    .add(jLabel63)
                    .add(antsiquiatricos)
                    .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, antvenereos, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, antecedentesauditivos))
                    .add(jPanel8Layout.createSequentialGroup()
                        .add(vertigo)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(epilepsia))
                    .add(jLabel60)
                    .add(usalentes)
                    .add(jPanel8Layout.createSequentialGroup()
                        .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel8Layout.createSequentialGroup()
                                .add(antEmbarazos)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(antAbortos)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(antDIU)
                                .add(7, 7, 7)
                                .add(antACO)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(antAutoexamenMama))
                            .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel8Layout.createSequentialGroup()
                                    .add(mamografia)
                                    .add(12, 12, 12)
                                    .add(fechamamo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(jLabel14)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(medicomamo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 192, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(jLabel17)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(clinicamamo, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel8Layout.createSequentialGroup()
                                    .add(antpapanicolau)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(fechapap, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(jLabel10)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(medicopap, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 192, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(jLabel11)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(clinicapap, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 163, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(nocorrespondepap)
                            .add(nocorrespondemamo)))
                    .add(jLabel56))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel46)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(antecedenteFumador)
                    .add(antecedenteAlcohol)
                    .add(antecedenteDiabetes)
                    .add(tipodiabetesap, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(antecedenteTiroideos, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(tiroideosap, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel47)
                    .add(antecedentesMedicamentos, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jLabel48)
                .add(8, 8, 8)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(antalergiaaliemntos)
                    .add(antalergiapolvos)
                    .add(antalergiamedicamentos)
                    .add(antalergiaotros))
                .add(18, 18, 18)
                .add(jLabel52)
                .add(18, 18, 18)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jCheckBox10)
                    .add(antpalpitaciones)
                    .add(anttaquicardias)
                    .add(antarritmia)
                    .add(antsoplo)
                    .add(antfiebrereumatica)
                    .add(antinfartomiocardio))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jCheckBox12)
                    .add(jLabel54)
                    .add(cardioOtros, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jLabel55)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(asma)
                    .add(neumopatia)
                    .add(broncopatias))
                .add(19, 19, 19)
                .add(jLabel56)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(hepatitis)
                    .add(gastritis)
                    .add(ulcera)
                    .add(diverticulos)
                    .add(alteracionesdeltransito))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(hemorroides)
                    .add(litiasisvesicular))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel57)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(colicosnefriticos)
                    .add(infeccionurinariabaja)
                    .add(infeccionrinon)
                    .add(litiasisrenal))
                .add(18, 18, 18)
                .add(jLabel58)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(tendinitis)
                    .add(lumbalgias)
                    .add(fracturas)
                    .add(artrosis))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jLabel59)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(usalentes)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jLabel60)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(vertigo)
                    .add(epilepsia))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(internaciones)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(antecedentesauditivos)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(antvenereos)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(antsiquiatricos)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(antquirurgicos)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jLabel63)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(antEmbarazos)
                    .add(antAbortos)
                    .add(antDIU)
                    .add(antACO)
                    .add(antAutoexamenMama))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(antpapanicolau)
                    .add(medicopap, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel10)
                    .add(clinicapap, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel11)
                    .add(fechapap, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(nocorrespondepap))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(mamografia)
                    .add(jLabel14)
                    .add(fechamamo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(medicomamo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel17)
                    .add(clinicamamo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(nocorrespondemamo))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));

        jLabel6.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jLabel6.setText("EXÁMEN FÍSICO");

        jLabel7.setText("PESO");

        peso.setModel(new javax.swing.SpinnerNumberModel(65, 15, 350, 1));
        peso.setValue(40);
        peso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pesoMouseClicked(evt);
            }
        });
        peso.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                pesoStateChanged(evt);
            }
        });

        jLabel8.setText("TALLA");

        talla.setModel(new javax.swing.SpinnerNumberModel(170, 100, 240, 1));
        talla.setValue(150);
        talla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tallaMouseClicked(evt);
            }
        });
        talla.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tallaStateChanged(evt);
            }
        });
        talla.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tallaFocusLost(evt);
            }
        });
        talla.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tallaPropertyChange(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        jLabel9.setText("PIEL Y MUCOSAS");

        jLabel12.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        jLabel12.setText("SISTEMA VASCULAR");

        jLabel15.setText("PULSO");

        pulso.setModel(new javax.swing.SpinnerNumberModel(100, 20, 200, 5));
        pulso.setValue(100);

        jLabel16.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel16.setText("CENTRAL");

        jLabel64.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        jLabel64.setText("VISION");

        jLabel27.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel27.setText("DE LEJOS");

        visionlejosOD.setModel(new javax.swing.SpinnerListModel(new String[] {"0.00", "0.10", "0.20", "0.30", "0.40", "0.50", "0.60", "0.70", "0.80", "0.90", "1.00"}));

        actividadesfisicas.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        actividadesfisicas.setText("REALIZA ACTIVIDADES FISICAS");
        actividadesfisicas.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel30.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel30.setText("NUESTRAS SUGERENCIAS");

        antitetanica.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        antitetanica.setText("VAC. ATT.");
        antitetanica.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel31.setText("IMPRESION GENERAL");

        impresiongeneral.setEditando(true);

        cartamedico.setText("CARTA MEDICO TRATANTE");

        causamedica.setText("NEGADO CAUSA MEDICA");

        jLabel32.setText("PLAZO VIGENCIA");

        plazo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "6 MESES", "12 MESES", "24 MESES" }));

        jLabel13.setText("I.M.C");

        micosis.setText("MICOSIS");

        alergias.setText("ALERGIAS");

        psoriasis.setText("PSORIASIS");

        lunares.setText("LUNARES");

        linfoganglionar.setEditando(true);

        cuello.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        cuello.setText("CUELLO");
        cuello.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        gradocuello.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "G I", "G II", "G III", "G IV" }));

        svvarices.setText("VARICES");

        svri.setText("R.I");

        soplos.setText("SOPLOS");

        jLabel49.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel49.setText("1ª TOMA");

        jLabel50.setText("SISTÓLICA");

        jspinnerPTSistolica.setModel(new javax.swing.SpinnerNumberModel(120, 40, 210, 5));
        jspinnerPTSistolica.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jspinnerPTSistolicaStateChanged(evt);
            }
        });

        jLabel51.setText("DIASTÓLICA");

        jspinnerPTDiastolica.setModel(new javax.swing.SpinnerNumberModel(80, 40, 200, 5));

        jLabel53.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel53.setText("2ª TOMA");

        jLabel61.setText("SISTÓLICA");

        jspinnerSTSistolica.setModel(new javax.swing.SpinnerNumberModel(120, 40, 240, 5));
        jspinnerSTSistolica.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jspinnerSTSistolicaStateChanged(evt);
            }
        });

        jLabel62.setText("DIASTÓLICA");

        jspinnerSTDiastolica.setModel(new javax.swing.SpinnerNumberModel(80, 40, 200, 5));
        jspinnerSTDiastolica.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jspinnerSTDiastolicaStateChanged(evt);
            }
        });

        tumoresmamas.setText("TUMORACION");

        mav.setSelected(true);
        mav.setText("MAV");
        mav.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mavActionPerformed(evt);
            }
        });

        efabdomendolor.setSelected(true);
        efabdomendolor.setText("INDOLORO");

        efabdomentumor.setText("TUMOR");
        efabdomentumor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                efabdomentumorActionPerformed(evt);
            }
        });

        efabdomenblando.setSelected(true);
        efabdomenblando.setText("BLANDO");

        efabdomendepresible.setSelected(true);
        efabdomendepresible.setText("DEPRESIBLE");

        efherniaid.setText("Hernia I.D");

        efherniaii.setText("Hernia I.I");

        effosaslumbares.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        effosaslumbares.setSelected(true);
        effosaslumbares.setText("FOSAS LUMBARES LIBRES");
        effosaslumbares.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        efosteoarticular.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        efosteoarticular.setText("OSTEOARTICULAR");
        efosteoarticular.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        efsectormuscular.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        efsectormuscular.setText("SECTOR MUSCULAR");
        efsectormuscular.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        efsugerenciaconsultamedica.setText("CONSULTA MEDICA");

        efsugerenciaexlaboratorio.setText("EXAMEN LABORATORIO");

        efsugerenciacontrolpeso.setText("CONTROL PESO");

        efsugerenciaejercicios.setText("EJERCICIOS");

        jLabel18.setText("PERIMETRO ABDOMINAL");

        perimetroabdominal.setModel(new javax.swing.SpinnerNumberModel(80, 60, 240, 5));
        perimetroabdominal.setValue(150);
        perimetroabdominal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                perimetroabdominalFocusGained(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        jLabel19.setText("OBSERVACIONES");

        observaciones.setColumns(20);
        observaciones.setRows(5);
        jScrollPane2.setViewportView(observaciones);

        jLabel20.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        jLabel20.setText("LINFOGANGLIONAR");

        jLabel21.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel21.setText("T/A");

        frecuencia.setModel(new javax.swing.SpinnerNumberModel(100, 20, 200, 5));
        frecuencia.setValue(100);

        jLabel22.setText("FRECUENCIA");

        jLabel65.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel65.setText("CONTROLES MEDICOS QUE REALIZA");

        controlesmedicos.setEditando(true);

        jLabel28.setText("O.D.");

        jLabel29.setText("O.I.");

        visionlejosOI.setModel(new javax.swing.SpinnerListModel(new String[] {"0.00", "0.10", "0.20", "0.30", "0.40", "0.50", "0.60", "0.70", "0.80", "0.90", "1.00"}));

        gripe.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        gripe.setText("VAC. GRIPE");
        gripe.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        hepatitisa.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        hepatitisa.setText("VAC. HEPATITIS A");
        hepatitisa.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        hepatitisc.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        hepatitisc.setText("VAC. HEPATITIS B");
        hepatitisc.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        hepatitisc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hepatitiscActionPerformed(evt);
            }
        });

        tifoidea.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        tifoidea.setText("VAC.TIFOIDEA");
        tifoidea.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel23.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        jLabel23.setText("PLEUROPULMONAR");

        jLabel24.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        jLabel24.setText("MAMAS");

        jLabel25.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        jLabel25.setText("ABDOMEN");

        jLabel26.setText("CICATRIZ");

        jLabel67.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        jLabel67.setText("VENOSO");

        jLabel66.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        jLabel66.setText("ZONAS HERNARIAS");

        imc.setEditable(false);
        imc.setText("200");
        imc.setEnabled(false);

        jLabel69.setFont(new java.awt.Font("Lucida Grande", 0, 8)); // NOI18N
        jLabel69.setText("National Cholesterol Program U.S.A");

        jLabel70.setFont(new java.awt.Font("Lucida Grande", 0, 8)); // NOI18N
        jLabel70.setText("Clasificación propuesta OMS");

        jLabel71.setText("O.D.");

        visionlejosCCOD.setModel(new javax.swing.SpinnerListModel(new String[] {"0.00", "0.10", "0.20", "0.30", "0.40", "0.50", "0.60", "0.70", "0.80", "0.90", "1.00"}));
        visionlejosCCOD.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                visionlejosCCODStateChanged(evt);
            }
        });

        jLabel72.setText("O.I.");

        visionlejosCCOI.setModel(new javax.swing.SpinnerListModel(new String[] {"0.00", "0.10", "0.20", "0.30", "0.40", "0.50", "0.60", "0.70", "0.80", "0.90", "1.00"}));
        visionlejosCCOI.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                visionlejosCCOIStateChanged(evt);
            }
        });

        jLabel73.setFont(new java.awt.Font("Lucida Grande", 2, 13)); // NOI18N
        jLabel73.setText("CON CORRECCION");

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jCheckBox1.setDoubleBuffered(true);
        jCheckBox1.setEnabled(false);
        jCheckBox1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abdomen3.ok.png"))); // NOI18N
        jCheckBox1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abdomen3.bad.png"))); // NOI18N
        jPanel4.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 40, 50));

        jCheckBox2.setDoubleBuffered(true);
        jCheckBox2.setEnabled(false);
        jCheckBox2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abdomen1.ok.png"))); // NOI18N
        jCheckBox2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abdomen1.bad.png"))); // NOI18N
        jPanel4.add(jCheckBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 50));

        jCheckBox3.setDoubleBuffered(true);
        jCheckBox3.setEnabled(false);
        jCheckBox3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abdomen2.ok.png"))); // NOI18N
        jCheckBox3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abdomen2.bad.png"))); // NOI18N
        jPanel4.add(jCheckBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 70, 50));

        jCheckBox4.setDoubleBuffered(true);
        jCheckBox4.setEnabled(false);
        jCheckBox4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abdomen6.ok.png"))); // NOI18N
        jCheckBox4.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abdomen6.bad.png"))); // NOI18N
        jPanel4.add(jCheckBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, -1, 50));

        jCheckBox5.setDoubleBuffered(true);
        jCheckBox5.setEnabled(false);
        jCheckBox5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abdomen5.ok.png"))); // NOI18N
        jCheckBox5.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abdomen5.bad.png"))); // NOI18N
        jPanel4.add(jCheckBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 70, 50));

        jCheckBox6.setDoubleBuffered(true);
        jCheckBox6.setEnabled(false);
        jCheckBox6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abdomen4.ok.png"))); // NOI18N
        jCheckBox6.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abdomen4.bad.png"))); // NOI18N
        jPanel4.add(jCheckBox6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 50, 50));

        jCheckBox7.setDoubleBuffered(true);
        jCheckBox7.setEnabled(false);
        jCheckBox7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abdomen9.ok.png"))); // NOI18N
        jCheckBox7.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abdomen9.bad.png"))); // NOI18N
        jPanel4.add(jCheckBox7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, -1, 50));

        jCheckBox8.setDoubleBuffered(true);
        jCheckBox8.setEnabled(false);
        jCheckBox8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abdomen8.ok.png"))); // NOI18N
        jCheckBox8.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abdomen8.bad.png"))); // NOI18N
        jPanel4.add(jCheckBox8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 70, 50));

        jCheckBox9.setDoubleBuffered(true);
        jCheckBox9.setEnabled(false);
        jCheckBox9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abdomen7.ok.png"))); // NOI18N
        jCheckBox9.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abdomen7.bad.png"))); // NOI18N
        jPanel4.add(jCheckBox9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 50, 50));

        vencimientoantitetanica.setEditando(true);

        labelimc.setText("imc");

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2Layout.createSequentialGroup()
                                        .add(jLabel20)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                        .add(linfoganglionar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 515, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(jPanel2Layout.createSequentialGroup()
                                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(jPanel2Layout.createSequentialGroup()
                                                .add(jLabel21)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .add(jLabel49)
                                                .add(18, 18, 18)
                                                .add(jLabel50)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                                            .add(jPanel2Layout.createSequentialGroup()
                                                .add(jLabel16)
                                                .add(18, 18, 18)
                                                .add(svri)
                                                .add(53, 53, 53)))
                                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(jLabel22)
                                            .add(jspinnerPTSistolica, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(jLabel51)
                                            .add(frecuencia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 52, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(soplos)
                                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                                    .add(jLabel69)
                                                    .add(jPanel2Layout.createSequentialGroup()
                                                        .add(jspinnerPTDiastolica, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .add(29, 29, 29)
                                                        .add(jLabel53)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabel61))
                                                    .add(jLabel18))
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                                    .add(perimetroabdominal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 58, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                    .add(jPanel2Layout.createSequentialGroup()
                                                        .add(jspinnerSTSistolica, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 57, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .add(18, 18, 18)
                                                        .add(jLabel62)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jspinnerSTDiastolica, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 57, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))))
                                .add(252, 252, 252))
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jPanel2Layout.createSequentialGroup()
                                        .add(micosis)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                        .add(alergias)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(psoriasis)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(lunares))
                                    .add(jLabel6)
                                    .add(jLabel9)
                                    .add(jPanel2Layout.createSequentialGroup()
                                        .add(cuello)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                        .add(gradocuello, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(jPanel2Layout.createSequentialGroup()
                                        .add(jLabel7)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                        .add(peso, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 52, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                        .add(jLabel8)
                                        .add(18, 18, 18)
                                        .add(talla, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(jPanel2Layout.createSequentialGroup()
                                                .add(jLabel13)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                .add(imc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .add(18, 18, 18)
                                                .add(labelimc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 185, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                            .add(jLabel70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 122, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))
                        .addContainerGap())
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(jLabel23)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(mav)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(pleuropulmonar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jPanel2Layout.createSequentialGroup()
                                        .add(jLabel67)
                                        .add(18, 18, 18)
                                        .add(svvarices))
                                    .add(jPanel2Layout.createSequentialGroup()
                                        .add(jLabel25)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                        .add(jLabel26)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(jPanel2Layout.createSequentialGroup()
                                                .add(abdomencicatriz, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 144, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .add(18, 18, 18)
                                                .add(efabdomenblando)
                                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                                    .add(jPanel2Layout.createSequentialGroup()
                                                        .add(10, 10, 10)
                                                        .add(effosaslumbares))
                                                    .add(jPanel2Layout.createSequentialGroup()
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                                        .add(efabdomendepresible)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(efabdomendolor)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(efabdomentumor))))
                                            .add(jPanel2Layout.createSequentialGroup()
                                                .add(efherniaid)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                                .add(efherniaii))))
                                    .add(jLabel66))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .add(296, 296, 296))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(jLabel24)
                                .add(18, 18, 18)
                                .add(tumoresmamas))
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(jLabel15)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(pulso, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 52, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jLabel12)
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(jLabel27)
                                .add(26, 26, 26)
                                .add(jLabel28)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(visionlejosOD, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 57, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabel29)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(visionlejosOI, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 57, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(18, 18, 18)
                                .add(jLabel73)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabel71)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(visionlejosCCOD, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 57, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabel72)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(visionlejosCCOI, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 57, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jLabel64))
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel19)
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(jLabel30)
                                .add(110, 110, 110)
                                .add(efsugerenciaconsultamedica)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(efsugerenciaexlaboratorio)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(efsugerenciacontrolpeso)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(efsugerenciaejercicios))
                            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane2)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2Layout.createSequentialGroup()
                                    .add(jLabel65)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(controlesmedicos, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2Layout.createSequentialGroup()
                                    .add(jLabel31)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(impresiongeneral, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 720, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                    .add(actividadesfisicas)
                    .add(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(cartamedico)
                        .add(54, 54, 54)
                        .add(causamedica)
                        .add(28, 28, 28)
                        .add(jLabel32)
                        .add(18, 18, 18)
                        .add(plazo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(antitetanica)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(vencimientoantitetanica, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 91, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(gripe)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(hepatitisa)
                        .add(18, 18, 18)
                        .add(hepatitisc)
                        .add(40, 40, 40)
                        .add(tifoidea))
                    .add(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(efosteoarticular)
                        .add(18, 18, 18)
                        .add(efsectormuscular)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel6)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel13)
                                    .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                        .add(imc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(jLabel18)
                                        .add(labelimc)))
                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jLabel69))
                                    .add(jPanel2Layout.createSequentialGroup()
                                        .add(2, 2, 2)
                                        .add(jLabel70, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))))
                            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(jLabel7)
                                .add(peso, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabel8)
                                .add(talla, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(40, 40, 40)
                        .add(perimetroabdominal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(17, 17, 17)
                .add(jLabel9)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(micosis)
                    .add(alergias)
                    .add(psoriasis)
                    .add(lunares))
                .add(15, 15, 15)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel20)
                    .add(linfoganglionar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(gradocuello, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(cuello))
                        .add(12, 12, 12)
                        .add(jLabel12)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel16)
                            .add(svri)
                            .add(jLabel22)))
                    .add(frecuencia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(soplos))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel49)
                            .add(jLabel21)
                            .add(jLabel50)
                            .add(jspinnerPTSistolica, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel51)
                            .add(jspinnerPTDiastolica, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(2, 2, 2)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel15)
                            .add(pulso, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel24)
                            .add(tumoresmamas)))
                    .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel62)
                        .add(jLabel61)
                        .add(jLabel53)
                        .add(jspinnerSTSistolica, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jspinnerSTDiastolica, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel23)
                    .add(mav)
                    .add(pleuropulmonar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(74, 74, 74)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel67)
                            .add(svvarices))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel25)
                            .add(jLabel26)
                            .add(abdomencicatriz, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(efabdomenblando)
                            .add(efabdomendepresible)
                            .add(efabdomendolor)
                            .add(efabdomentumor))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel66)
                            .add(efherniaid)
                            .add(efherniaii)
                            .add(effosaslumbares))
                        .add(46, 46, 46)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(efosteoarticular)
                            .add(efsectormuscular))
                        .add(55, 55, 55)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                        .add(visionlejosCCOD, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(jLabel71)
                                        .add(jLabel73))
                                    .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                        .add(jLabel72)
                                        .add(visionlejosCCOI, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                                .add(jLabel29)
                                .add(visionlejosOI, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(jLabel64)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jLabel27)
                                    .add(visionlejosOD, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel28)))))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(50, 50, 50)
                        .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(11, 11, 11)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(antitetanica)
                        .add(vencimientoantitetanica, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(gripe)
                        .add(hepatitisa)
                        .add(hepatitisc)
                        .add(tifoidea)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(actividadesfisicas)
                .add(18, 18, 18)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel65)
                    .add(controlesmedicos, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel30)
                    .add(efsugerenciaconsultamedica)
                    .add(efsugerenciaexlaboratorio)
                    .add(efsugerenciacontrolpeso)
                    .add(efsugerenciaejercicios))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel31)
                    .add(impresiongeneral, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel19)
                .add(1, 1, 1)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cartamedico)
                    .add(causamedica)
                    .add(jLabel32)
                    .add(plazo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        almacenar.setText("Almacenar");

        jLabel68.setForeground(new java.awt.Color(102, 102, 102));
        jLabel68.setText("CARNE  ANTERIOR");

        otorgadoanterior.setEditable(false);
        otorgadoanterior.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        otorgadoanterior.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N

        otorgadovence.setEditable(false);
        otorgadovence.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        otorgadovence.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N

        jLabel74.setForeground(new java.awt.Color(102, 102, 102));
        jLabel74.setText("VENCIMIENTO");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2, 0, 988, Short.MAX_VALUE)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(399, 399, 399)
                        .add(almacenar))
                    .add(jPanel8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 168, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(98, 98, 98)
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jPanel1Layout.createSequentialGroup()
                                        .add(jLabel2)
                                        .add(18, 18, 18)
                                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(jPanel1Layout.createSequentialGroup()
                                                .add(jLabel4)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                .add(fechaHoy, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                            .add(jPanel1Layout.createSequentialGroup()
                                                .add(jLabel44)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                .add(jtbNumero, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 119, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                                    .add(jPanel1Layout.createSequentialGroup()
                                        .add(jLabel68)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(otorgadoanterior, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 118, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(1, 1, 1)
                                        .add(jLabel74)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(otorgadovence, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 118, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(jLabel3)
                                .add(12, 12, 12)
                                .add(jtfCedulaIdentidad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 118, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                    .add(jPanel7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(28, 28, 28)
                                .add(jLabel2)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jLabel68)
                                    .add(otorgadoanterior, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(otorgadovence, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel74)))
                            .add(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jLabel44)
                                    .add(jtbNumero, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(fechaHoy, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel4))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 32, Short.MAX_VALUE)))
                        .add(42, 42, 42)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel3)
                            .add(jtfCedulaIdentidad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(almacenar)
                .addContainerGap(2515, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        add(jScrollPane1);
    }// </editor-fold>//GEN-END:initComponents

    private void artrosisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_artrosisActionPerformed

}//GEN-LAST:event_artrosisActionPerformed

    private void jCheckBox12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox12ActionPerformed

}//GEN-LAST:event_jCheckBox12ActionPerformed

    private void antecedenteFumadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_antecedenteFumadorActionPerformed

    }//GEN-LAST:event_antecedenteFumadorActionPerformed

    private void antecedenteTiroideosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_antecedenteTiroideosActionPerformed

        tiroideosap.setEnabled(antecedenteTiroideos.isSelected());
}//GEN-LAST:event_antecedenteTiroideosActionPerformed

    private void antecedenteDiabetesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_antecedenteDiabetesActionPerformed


        tipodiabetesap.setEnabled(antecedenteDiabetes.isSelected());
}//GEN-LAST:event_antecedenteDiabetesActionPerformed

    private void antecedenteAlcoholActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_antecedenteAlcoholActionPerformed

}//GEN-LAST:event_antecedenteAlcoholActionPerformed

    private void visionlejosCCOIStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_visionlejosCCOIStateChanged

        if(!visionlejosCCOI.getValue().equals("0.00"))
            anularSinCorreccionOI();
}//GEN-LAST:event_visionlejosCCOIStateChanged

    private void visionlejosCCODStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_visionlejosCCODStateChanged

        if(!visionlejosCCOD.getValue().equals("0.00"))
            anularSinCorreccionOD();
}//GEN-LAST:event_visionlejosCCODStateChanged

    private void hepatitiscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hepatitiscActionPerformed

}//GEN-LAST:event_hepatitiscActionPerformed

    private void perimetroabdominalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_perimetroabdominalFocusGained

        //consignarIMC();
}//GEN-LAST:event_perimetroabdominalFocusGained

    private void efabdomentumorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_efabdomentumorActionPerformed

        jCheckBox1.setEnabled(efabdomentumor.isSelected());
        jCheckBox2.setEnabled(efabdomentumor.isSelected());
        jCheckBox3.setEnabled(efabdomentumor.isSelected());
        jCheckBox4.setEnabled(efabdomentumor.isSelected());
        jCheckBox5.setEnabled(efabdomentumor.isSelected());
        jCheckBox6.setEnabled(efabdomentumor.isSelected());
        jCheckBox7.setEnabled(efabdomentumor.isSelected());
        jCheckBox8.setEnabled(efabdomentumor.isSelected());
        jCheckBox9.setEnabled(efabdomentumor.isSelected());
}//GEN-LAST:event_efabdomentumorActionPerformed

    private void mavActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mavActionPerformed

}//GEN-LAST:event_mavActionPerformed

    private void jspinnerSTDiastolicaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jspinnerSTDiastolicaStateChanged

}//GEN-LAST:event_jspinnerSTDiastolicaStateChanged

    private void jspinnerSTSistolicaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jspinnerSTSistolicaStateChanged

        hookCodigoPresiones();
}//GEN-LAST:event_jspinnerSTSistolicaStateChanged

    private void jspinnerPTSistolicaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jspinnerPTSistolicaStateChanged

}//GEN-LAST:event_jspinnerPTSistolicaStateChanged

    private void tallaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tallaPropertyChange

        //consignarIMC();
}//GEN-LAST:event_tallaPropertyChange

    private void tallaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tallaFocusLost

        consignarIMC();
}//GEN-LAST:event_tallaFocusLost

    private void tallaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tallaStateChanged

        consignarIMC();
}//GEN-LAST:event_tallaStateChanged

    private void tallaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tallaMouseClicked

        //consignarIMC();
}//GEN-LAST:event_tallaMouseClicked

    private void pesoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_pesoStateChanged

        consignarIMC();
}//GEN-LAST:event_pesoStateChanged

    private void pesoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pesoMouseClicked

        //consignarIMC();
}//GEN-LAST:event_pesoMouseClicked

    private void jtbNombre3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtbNombre3ActionPerformed

}//GEN-LAST:event_jtbNombre3ActionPerformed

    private void antecedentesOtrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_antecedentesOtrosActionPerformed

}//GEN-LAST:event_antecedentesOtrosActionPerformed

    private void antecedentesDiabetesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_antecedentesDiabetesActionPerformed


    }//GEN-LAST:event_antecedentesDiabetesActionPerformed

    private void antecedentesHTAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_antecedentesHTAActionPerformed

}//GEN-LAST:event_antecedentesHTAActionPerformed


    private void consignarIMC() {
        float i = 0;
        int p;
        Float t;
        String xx;
        try {
        p = (Integer)this.peso.getValue();
        t = Float.parseFloat(this.talla.getValue()+"");
        if(t!=0)
         i = p / ((t / 100) * (t / 100));

        this.imc.setText(i+"");
        if(i < 18.5)
            xx="DELGADO";
        else
            if(i >=18.5 && i < 25)
                xx="NORMAL";
            else
                if(i >=25 && i < 30)
                    xx = "SOBREPESO";
                else
                    if(i >=30 && i < 35)
                        xx = "OBESO";
                    else
                        xx = "OBESIDAD MORBIDA";
        } catch(Exception ex) {

            xx = "imc";
        }
        labelimc.setText(xx);
    }
    private void anularSinCorreccionOD() {

        visionlejosOD.setValue("0.00");
    }

    private void anularSinCorreccionOI() {

        visionlejosOI.setValue("0.00");
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.kcreativa.pitosycornetas.KTextBox abdomencicatriz;
    private javax.swing.JCheckBox actividadesfisicas;
    private javax.swing.JCheckBox alergias;
    private javax.swing.JButton almacenar;
    private javax.swing.JCheckBox alteracionesdeltransito;
    private javax.swing.JCheckBox antACO;
    private javax.swing.JCheckBox antAbortos;
    private javax.swing.JCheckBox antAutoexamenMama;
    private javax.swing.JCheckBox antDIU;
    private javax.swing.JCheckBox antEmbarazos;
    private javax.swing.JCheckBox antalergiaaliemntos;
    private javax.swing.JCheckBox antalergiamedicamentos;
    private javax.swing.JCheckBox antalergiaotros;
    private javax.swing.JCheckBox antalergiapolvos;
    private javax.swing.JCheckBox antarritmia;
    private javax.swing.JCheckBox antecedenteAlcohol;
    private javax.swing.JCheckBox antecedenteDiabetes;
    private javax.swing.JCheckBox antecedenteFumador;
    private javax.swing.JCheckBox antecedenteTiroideos;
    private javax.swing.JCheckBox antecedentesDiabetes;
    private javax.swing.JCheckBox antecedentesHTA;
    private com.kcreativa.pitosycornetas.KTextBox antecedentesMedicamentos;
    private javax.swing.JCheckBox antecedentesOtros;
    private javax.swing.JCheckBox antecedentesauditivos;
    private javax.swing.JCheckBox antfiebrereumatica;
    private javax.swing.JCheckBox antinfartomiocardio;
    private javax.swing.JCheckBox antitetanica;
    private javax.swing.JCheckBox antpalpitaciones;
    private javax.swing.JCheckBox antpapanicolau;
    private javax.swing.JCheckBox antquirurgicos;
    private javax.swing.JCheckBox antsiquiatricos;
    private javax.swing.JCheckBox antsoplo;
    private javax.swing.JCheckBox anttaquicardias;
    private javax.swing.JCheckBox antvenereos;
    private javax.swing.JCheckBox artrosis;
    private javax.swing.JCheckBox asma;
    private javax.swing.ButtonGroup bgPeso;
    private javax.swing.JCheckBox broncopatias;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private com.kcreativa.pitosycornetas.KTextBox cardioOtros;
    private javax.swing.JCheckBox cartamedico;
    private javax.swing.JCheckBox causamedica;
    private com.kcreativa.pitosycornetas.KTextBox clinicamamo;
    private com.kcreativa.pitosycornetas.KTextBox clinicapap;
    private javax.swing.JCheckBox colicosnefriticos;
    private com.kcreativa.pitosycornetas.KTextBox controlesmedicos;
    private javax.swing.JCheckBox cuello;
    private javax.swing.JCheckBox diverticulos;
    private com.kcreativa.pitosycornetas.KTextBox edad;
    private javax.swing.JCheckBox efabdomenblando;
    private javax.swing.JCheckBox efabdomendepresible;
    private javax.swing.JCheckBox efabdomendolor;
    private javax.swing.JCheckBox efabdomentumor;
    private javax.swing.JCheckBox effosaslumbares;
    private javax.swing.JCheckBox efherniaid;
    private javax.swing.JCheckBox efherniaii;
    private javax.swing.JCheckBox efosteoarticular;
    private javax.swing.JCheckBox efsectormuscular;
    private javax.swing.JCheckBox efsugerenciaconsultamedica;
    private javax.swing.JCheckBox efsugerenciacontrolpeso;
    private javax.swing.JCheckBox efsugerenciaejercicios;
    private javax.swing.JCheckBox efsugerenciaexlaboratorio;
    private javax.swing.JCheckBox epilepsia;
    private org.jdesktop.swingx.JXDatePicker fechaHoy;
    private com.kcreativa.pitosycornetas.KFormattedTextBox fechamamo;
    private com.kcreativa.pitosycornetas.KFormattedTextBox fechapap;
    private javax.swing.JCheckBox fracturas;
    private javax.swing.JSpinner frecuencia;
    private javax.swing.JCheckBox gastritis;
    private javax.swing.JComboBox gradocuello;
    private javax.swing.JCheckBox gripe;
    private javax.swing.JCheckBox hemorroides;
    private javax.swing.JCheckBox hepatitis;
    private javax.swing.JCheckBox hepatitisa;
    private javax.swing.JCheckBox hepatitisc;
    private com.kcreativa.pitosycornetas.KTextBox imc;
    private com.kcreativa.pitosycornetas.KTextBox impresiongeneral;
    private javax.swing.JCheckBox infeccionrinon;
    private javax.swing.JCheckBox infeccionurinariabaja;
    private javax.swing.JCheckBox internaciones;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox12;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
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
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
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
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
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
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private org.jdesktop.swingx.JXDatePicker jXFechaNacimiento;
    private com.kcreativa.pitosycornetas.KTextBox jcbDomicilio3;
    private com.kcreativa.pitosycornetas.KTextBox jcbEmail;
    private javax.swing.JComboBox jcbProcedencia;
    private javax.swing.JRadioButton jrbFemenino;
    private javax.swing.JRadioButton jrbMasculino3;
    private javax.swing.JSpinner jspinnerPTDiastolica;
    private javax.swing.JSpinner jspinnerPTSistolica;
    private javax.swing.JSpinner jspinnerSTDiastolica;
    private javax.swing.JSpinner jspinnerSTSistolica;
    private com.kcreativa.pitosycornetas.KTextBox jtbApellido3;
    private com.kcreativa.pitosycornetas.KTextBox jtbCelular3;
    private com.kcreativa.pitosycornetas.KTextBox jtbCiudad3;
    private com.kcreativa.pitosycornetas.KTextBox jtbNombre3;
    private com.kcreativa.pitosycornetas.KTextBox jtbNumero;
    private javax.swing.JFormattedTextField jtfCedulaIdentidad;
    private com.kcreativa.pitosycornetas.KTextBox jtfTelefono3;
    private javax.swing.JLabel labelimc;
    private com.kcreativa.pitosycornetas.KTextBox linfoganglionar;
    private javax.swing.JCheckBox litiasisrenal;
    private javax.swing.JCheckBox litiasisvesicular;
    private javax.swing.JCheckBox lumbalgias;
    private javax.swing.JCheckBox lunares;
    private javax.swing.JCheckBox mamografia;
    private javax.swing.JCheckBox mav;
    private com.kcreativa.pitosycornetas.KTextBox medicomamo;
    private com.kcreativa.pitosycornetas.KTextBox medicopap;
    private javax.swing.JCheckBox micosis;
    private javax.swing.JCheckBox neumopatia;
    private javax.swing.JCheckBox nocorrespondemamo;
    private javax.swing.JCheckBox nocorrespondepap;
    private javax.swing.JTextArea observaciones;
    private javax.swing.JFormattedTextField otorgadoanterior;
    private javax.swing.JFormattedTextField otorgadovence;
    private javax.swing.JSpinner perimetroabdominal;
    private javax.swing.JSpinner peso;
    private javax.swing.JComboBox plazo;
    private com.kcreativa.pitosycornetas.KTextBox pleuropulmonar;
    private javax.swing.JCheckBox psoriasis;
    private javax.swing.JSpinner pulso;
    private javax.swing.JCheckBox soplos;
    private javax.swing.JCheckBox svri;
    private javax.swing.JCheckBox svvarices;
    private javax.swing.JSpinner talla;
    private javax.swing.JCheckBox tendinitis;
    private javax.swing.JCheckBox tifoidea;
    private javax.swing.JComboBox tipodiabetesap;
    private javax.swing.JComboBox tiroideosap;
    private javax.swing.JCheckBox tumoresmamas;
    private javax.swing.JCheckBox ulcera;
    private javax.swing.JCheckBox usalentes;
    private com.kcreativa.pitosycornetas.KFormattedTextBox vencimientoantitetanica;
    private javax.swing.JCheckBox vertigo;
    private javax.swing.JSpinner visionlejosCCOD;
    private javax.swing.JSpinner visionlejosCCOI;
    private javax.swing.JSpinner visionlejosOD;
    private javax.swing.JSpinner visionlejosOI;
    // End of variables declaration//GEN-END:variables

    public int getTipoDiabetesAP() {
        return(tipodiabetesap.getSelectedIndex());
    }

    public int getTipoTiroideoAP() {
        return(tiroideosap.getSelectedIndex());
    }

    public float getCCOD() {

        return(Float.parseFloat((String)visionlejosCCOD.getValue()));
    }

    public float getCCOI() {

        return(Float.parseFloat((String)visionlejosCCOI.getValue()));
    }

    public boolean getTumoresAbdominales() {

        return(efabdomentumor.isSelected());
    }

    public boolean getTumorZ1() {
        return(jCheckBox2.isSelected());
    }

    public boolean getTumorZ2() {
        return(jCheckBox3.isSelected());
    }
    public boolean getTumorZ3() {
        return(jCheckBox1.isSelected());
    }
    public boolean getTumorZ4() {
        return(jCheckBox6.isSelected());
    }
    public boolean getTumorZ5() {
        return(jCheckBox5.isSelected());
    }
    public boolean getTumorZ6() {
        return(jCheckBox4.isSelected());
    }
    public boolean getTumorZ7() {
        return(jCheckBox9.isSelected());
    }
    public boolean getTumorZ8() {
        return(jCheckBox8.isSelected());
    }
    public boolean getTumorZ9() {
        return(jCheckBox7.isSelected());
    }

    public void setPacienteEditable(boolean b) {

        if(b) {
                jtfCedulaIdentidad.setEditable(b);
                jrbMasculino3.setEnabled(b);
                jrbFemenino.setEnabled(b);
        }
    }

    public String getDocumento() {

        return(jtfCedulaIdentidad.getText());
    }

    public String getSexo() {

        String resp = "M";

        if(jrbMasculino3.isSelected())
            resp = "M";
        else
            resp = "F";
        return(resp);
    }

    public void setListaProcedencias(List<Clientes> lp) {


        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();

        for(Clientes p : lp) {

            dcbm.addElement(p.getNombre());
        }

        jcbProcedencia.setModel(dcbm);
    }

    public BufferedImage getImagenEpisodio() {

        BufferedImage img = null;

        SwingImageCreator sic = new SwingImageCreator();
        img = sic.createImage(this);

        return(img);
    }

    public void setVencimientoATT(Date vence) {

        vencimientoantitetanica.setText(formatFecha(vence));
    }

    public void setVencimientoPAP(Date vence) {
        fechapap.setText(formatFecha(vence));
    }

    public void setVencimientoMAMO(Date vence) {
        fechamamo.setText(formatFecha(vence));
    }

    private String formatFecha(Date d) {

        String fecha;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fecha = sdf.format(d);

        return(fecha);
    }

    public void setVencimientoCDS(Date vence) {
       otorgadovence.setText(formatFecha(vence));
    }

    public void setFechaOtorgadoAnterior(Date emitido) {
        if(emitido!=null) {
            otorgadoanterior.setText(formatFecha(emitido));
        }
    }

    public Boolean vigenciamedico(){
        Boolean meses6 = null;
        if (plazo.getSelectedIndex() == 0 )
          meses6 = true;
        return(meses6);
    }

}
