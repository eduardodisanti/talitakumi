/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UICarnetDeSalud.java
 *
 * Created on 29-oct-2008, 0:05:33
 */

package talitakumi.odontologo.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ActionListener;

/**
 *
 * @author rupertus
 */
public class UIRevisionOdontologica extends javax.swing.JPanel {

    DefaultTableModel dtm = new DefaultTableModel();
    DefaultListModel modelolistadientes = new DefaultListModel();
    /** Creates new form UICarnetDeSalud */
    public UIRevisionOdontologica() {
        initComponents();

        listadientes.setModel(modelolistadientes);
        welcome();

        dtm.addColumn("Pieza");
        dtm.addColumn("Diagnostico");
        dtm.setColumnCount(2);
        diagnosticos.setModel(dtm);
    }

    public Boolean getmeses6(){
        return(meses6.isSelected());
    }

    public Boolean getmeses24(){
        return(meses24.isSelected());
    }

    public Boolean getNegado(){
        return(negado.isSelected());
    }

    public Integer getIndiceDiagnosticoElegido() {

        Integer idx = diagnosticos.getSelectedRow();

        return(idx);
    }

    public Integer getIndiceEnfermedadElegida() {

        Integer idx = null;

        idx = this.listDiagnosticos.getSelectedIndex();
        
        return(idx);
    }

    public boolean getLesionBlanca() {
        return(this.lesionblanca.isSelected());
    }

    public boolean getPatologiaMaligna() {
        return(this.patologiamaligna.isSelected());
    }

    public boolean getAportaCertificado() {
        return(this.aportacertificado.isSelected());
    }

    public void guardarApCertYObservaciones(){
        Boolean b = true;
        if(getAportaCertificado())
            setAportaCertificado(b);
        if(getObservaciones()!=null)
            setObservaciones(getObservaciones());

    }

    public void setAportaCertificado(Boolean aportacertificado) {
        this.aportacertificado.setSelected(aportacertificado);
      
    }

    public String getObservaciones() {
        return(this.observaciones.getText());
    }

    public void setObservaciones(String observaciones) {
        this.observaciones.setText(observaciones);
    }

    public List<Integer> getPiezaElegida() {

        List<Integer> l = new ArrayList();
        ListModel m = listadientes.getModel();
        for(int i = 0;i < m.getSize();i++) {

            l.add((Integer) m.getElementAt(i));
        }
       return(l);
    }

    public void setAgregarListener(ActionListener agregarListener) {

        agregar.addActionListener(agregarListener);
    }

    public void setAlmacenarListener(ActionListener almacenarListener) {
        almacenar.addActionListener(almacenarListener);
    }

    public void setAltoFilas(int i) {
                diagnosticos.setRowHeight(i);
    }

    public void setAnchoColumna(int i, int ancho) {
        diagnosticos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumn col = diagnosticos.getColumnModel().getColumn(i);
        col.setPreferredWidth(ancho);
    }

    public void setApellidosPaciente(String apellidos) {
        this.jtbApellido3.setText(apellidos);
    }

    public void setCedula(int documento) {

        this.jtfCedulaIdentidad.setText(documento+"");
    }

    public void setFechaNacimiento(Date fechanacimiento) {
        
    }

    public void vaciarListaDientes() {

        modelolistadientes.clear();
    }
    public void setIdEpisodio(Integer id) {
        jtbNumero.setText(id+"");
    }

    public void setModeloDiagnosticos(DefaultTableModel dftm) {
        diagnosticos.setModel(dftm);
    }

    public void setModeloEnfermedades(DefaultListModel modelo) {
        listDiagnosticos.setModel(modelo);
    }

    public void setNegado(boolean b) {

        negado.setSelected(b);
    }

    public void setNombrePaciente(String nombres) {
        this.jtbNombre3.setText(nombres);
    }

    public void setPlazo24(boolean b) {
       meses24.setSelected(b);
    }

    public void setPlazo6(boolean b) {

        meses6.setSelected(b);
    }

    public void setQuitarListener(ActionListener quitarListener) {
        quitar.addActionListener(quitarListener);
    }

    public void setSexo(char sexo) {

        this.jrbMasculino3.setSelected(sexo=='M');
        this.jrbFemenino.setSelected(sexo=='F');
    }

    private void welcome() {

        Date hoy;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        hoy = new java.util.Date();
        fechaHoy.setFormats("dd/MM/yyyy");
        fechaHoy.setDate(hoy);
        fechaHoy.setEditable(false);
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
        plazos = new javax.swing.ButtonGroup();
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
        jLabel40 = new javax.swing.JLabel();
        jrbMasculino3 = new javax.swing.JRadioButton();
        jrbFemenino = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        edad = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel42 = new javax.swing.JLabel();
        jcbProcedencia = new javax.swing.JComboBox();
        alerta = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtfCedulaIdentidad = new javax.swing.JFormattedTextField();
        jLabel44 = new javax.swing.JLabel();
        jtbNumero = new com.kcreativa.pitosycornetas.KTextBox();
        jPanel2 = new javax.swing.JPanel();
        bocasuperior = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listDiagnosticos = new javax.swing.JList();
        bocainferior = new javax.swing.JLabel();
        agregar = new javax.swing.JButton();
        almacenar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        meses6 = new javax.swing.JRadioButton();
        meses24 = new javax.swing.JRadioButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        observaciones = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        quitar = new javax.swing.JButton();
        patologiamaligna = new javax.swing.JCheckBox();
        lesionblanca = new javax.swing.JCheckBox();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        diagnosticos = new com.kcreativa.pitosycornetas.TableIcon();
        negado = new javax.swing.JRadioButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        listadientes = new javax.swing.JList();
        aportacertificado = new javax.swing.JCheckBox();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(800, 600));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 600));
        jPanel1.setRequestFocusEnabled(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logomedilab150x94.jpg"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 18));
        jLabel2.setForeground(new java.awt.Color(0, 51, 153));
        jLabel2.setText("EXAMEN ODONTOLOGICO");

        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("FECHA");

        fechaHoy.setFormats("dd/mm/aaaa");

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel33.setForeground(new java.awt.Color(102, 102, 102));
        jLabel33.setText("APELLIDOS");

        jtbApellido3.setEditable(false);
        jtbApellido3.setText("ap");
        jtbApellido3.setFont(new java.awt.Font("Lucida Grande", 1, 13));

        jLabel34.setForeground(new java.awt.Color(102, 102, 102));
        jLabel34.setText("NOMBRES");

        jtbNombre3.setEditable(false);
        jtbNombre3.setText("nom");
        jtbNombre3.setFont(new java.awt.Font("Lucida Grande", 1, 13));
        jtbNombre3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtbNombre3ActionPerformed(evt);
            }
        });

        jLabel40.setText("SEXO");

        jrbMasculino3.setSelected(true);
        jrbMasculino3.setText("Masculino");
        jrbMasculino3.setEnabled(false);

        jrbFemenino.setText("Femenino");
        jrbFemenino.setEnabled(false);

        jLabel5.setText("EDAD");

        edad.setEditable(false);

        jLabel42.setText("PROCEDENCIA");

        jcbProcedencia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Particular" }));

        alerta.setBackground(new java.awt.Color(153, 0, 0));
        alerta.setForeground(new java.awt.Color(255, 255, 153));
        alerta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        alerta.setText("ULTIMA VIGENCIA 6 MESES");
        alerta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        alerta.setOpaque(true);

        org.jdesktop.layout.GroupLayout jPanel7Layout = new org.jdesktop.layout.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel7Layout.createSequentialGroup()
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel7Layout.createSequentialGroup()
                        .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel7Layout.createSequentialGroup()
                                .add(jLabel33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 94, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jtbApellido3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 178, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanel7Layout.createSequentialGroup()
                                .add(jLabel40)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jrbMasculino3)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jrbFemenino)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabel5)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(edad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel42)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jcbProcedencia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel7Layout.createSequentialGroup()
                        .add(jLabel34)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jtbNombre3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 326, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(20, 20, 20))
            .add(alerta, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel33)
                    .add(jtbApellido3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel34)
                    .add(jtbNombre3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(alerta)
                .add(2, 2, 2)
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel40)
                    .add(jrbMasculino3)
                    .add(jrbFemenino)
                    .add(jLabel5)
                    .add(edad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel42)
                    .add(jcbProcedencia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(63, 63, 63))
        );

        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("CÉDULA DE IDENTIDAD");

        jtfCedulaIdentidad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jtfCedulaIdentidad.setEditable(false);
        jtfCedulaIdentidad.setFont(new java.awt.Font("Lucida Grande", 1, 13));

        jLabel44.setForeground(new java.awt.Color(102, 102, 102));
        jLabel44.setText("NÚMERO");

        jtbNumero.setEditable(false);
        jtbNumero.setText("00000");
        jtbNumero.setFont(new java.awt.Font("Lucida Grande", 1, 13));

        jPanel2.setLayout(new java.awt.BorderLayout());

        bocasuperior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/bocaisuperior.jpg"))); // NOI18N
        bocasuperior.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bocasuperiorMouseClicked(evt);
            }
        });

        listDiagnosticos.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Extraccion indicada", "Caries", "Perdido", "Obturacion" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(listDiagnosticos);

        bocainferior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/bocainferior.jpg"))); // NOI18N
        bocainferior.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bocainferiorMouseClicked(evt);
            }
        });

        agregar.setText("+");
        agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarActionPerformed(evt);
            }
        });

        almacenar.setText("Almacenar");

        jLabel6.setText("Plazo");

        plazos.add(meses6);
        meses6.setText("6 meses");

        plazos.add(meses24);
        meses24.setSelected(true);
        meses24.setText("2 años");

        observaciones.setColumns(20);
        observaciones.setRows(5);
        jScrollPane4.setViewportView(observaciones);

        jLabel7.setText("Observaciones");

        quitar.setText("-");
        quitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitarActionPerformed(evt);
            }
        });

        patologiamaligna.setText("Patologia maligna");

        lesionblanca.setText("Lesion blanca");

        jScrollPane3.setViewportView(diagnosticos);

        plazos.add(negado);
        negado.setText("negado");

        listadientes.setFont(new java.awt.Font("Lucida Grande", 1, 14));
        listadientes.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "00" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listadientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(listadientes);

        aportacertificado.setText("Aporta certificado");
        aportacertificado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                aportacertificadoMouseClicked(evt);
            }
        });
        aportacertificado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aportacertificadoActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(bocainferior)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(1289, 1289, 1289)
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jPanel1Layout.createSequentialGroup()
                                        .add(413, 413, 413)
                                        .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(jPanel1Layout.createSequentialGroup()
                                        .add(81, 81, 81)
                                        .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(567, 567, 567)
                                .add(almacenar))))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(12, 12, 12)
                        .add(bocasuperior)
                        .add(18, 18, 18)
                        .add(jScrollPane5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(quitar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(166, 166, 166)
                                .add(jLabel7))
                            .add(jScrollPane4)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(aportacertificado)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabel6)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(meses6)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(meses24)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(negado))
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 406, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(lesionblanca)
                                    .add(patologiamaligna)
                                    .add(jPanel1Layout.createSequentialGroup()
                                        .add(12, 12, 12)
                                        .add(agregar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 55, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(jLabel3)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jtfCedulaIdentidad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 118, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jLabel2)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                                .add(jLabel44)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(jtbNumero, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 119, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                                .add(jLabel4)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 132, Short.MAX_VALUE)
                                .add(fechaHoy, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(74, 74, 74)
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(fechaHoy, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(jLabel2)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jtfCedulaIdentidad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel3))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jtbNumero, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel44)))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 111, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jLabel1))
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(11, 11, 11)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jScrollPane2, 0, 0, Short.MAX_VALUE)
                            .add(jScrollPane5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(26, 26, 26)
                                .add(agregar)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(patologiamaligna)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(lesionblanca)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 173, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(quitar)
                            .add(jLabel7))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jScrollPane4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel6)
                            .add(meses6)
                            .add(meses24)
                            .add(negado)
                            .add(aportacertificado))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(almacenar)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(385, 385, 385)
                        .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(8, 8, 8)
                        .add(bocasuperior)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(bocainferior)))
                .addContainerGap())
        );

        jScrollPane1.setViewportView(jPanel1);

        add(jScrollPane1);
    }// </editor-fold>//GEN-END:initComponents

    private void jtbNombre3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtbNombre3ActionPerformed
        
}//GEN-LAST:event_jtbNombre3ActionPerformed

    private void bocasuperiorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bocasuperiorMouseClicked
        
        Integer pieza = interpretoPosicionSuperior(evt.getX(), evt.getY());

        if(pieza==0)
            modelolistadientes.addElement(888);
        else
            modelolistadientes.addElement(pieza);
}//GEN-LAST:event_bocasuperiorMouseClicked

    private void bocainferiorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bocainferiorMouseClicked
        
        int pieza = interpretoPosicionInferior(evt.getX(), evt.getY());
        if(pieza==0)
            modelolistadientes.addElement(777);
        else
            modelolistadientes.addElement(pieza);
}//GEN-LAST:event_bocainferiorMouseClicked

    private void agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarActionPerformed
        
        //pasarDiagnostico();
        
}//GEN-LAST:event_agregarActionPerformed

    private void quitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitarActionPerformed
        
            int row = diagnosticos.getSelectedRow();

            if(row != -1)
             dtm.removeRow(row);
}//GEN-LAST:event_quitarActionPerformed

    private void aportacertificadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aportacertificadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_aportacertificadoActionPerformed

    private void aportacertificadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aportacertificadoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_aportacertificadoMouseClicked


private void pasarDiagnostico() {

    int i = listadientes.getSelectedIndex();
    if(i!=-1) {
        Integer pieza = (Integer) modelolistadientes.getElementAt(i);

        String diag = (String) listDiagnosticos.getSelectedValue();

        Vector col = new Vector();
        col.add(pieza);
        col.add(diag);
        dtm.addRow(col);
    }
}

private int interpretoPosicionSuperior(int x, int y) {

    int pieza = 0;

    // ********** CUADRANTE 1 *************

    if(x>=8 && x<=42 && y >=189 && y <=227)
        pieza = 18;
    if(x>=15 && x<=55 && y >=149 && y <=190)
        pieza = 17;
    if(x>=25 && x<=65 && y >=104 && y <=146)
        pieza = 16;
    if(x>=43 && x<=70 && y >=72 && y <=99)
        pieza = 15;
    if(x>=59 && x<=85 && y >=52 && y <=75)
        pieza = 14;
    if(x>=78 && x<=102 && y >=24 && y <=60)
        pieza = 13;
    if(x>=100 && x<=122 && y >=15 && y <=49)
        pieza = 12;
    if(x>=121 && x<=143 && y >=9 && y <=41)
        pieza = 11;

    // ********** CUADRANTE 2 *************

    if(x>=150 && x<=166 && y >=9 && y <=43)
        pieza = 21;
    if(x>=169 && x<=190 && y >=16 && y <=54)
        pieza = 22;
    if(x>=194 && x<=212 && y >=26 && y <=60)
        pieza = 23;
    if(x>=208 && x<=228 && y >=54 && y <=80)
        pieza = 24;
    if(x>=215 && x<=245 && y >=80 && y <=103)
        pieza = 25;
    if(x>=218 && x<=262 && y >=106 && y <=147)
        pieza = 26;
    if(x>=231 && x<=269 && y >=153 && y <=191)
        pieza = 27;
    if(x>=242 && x<=273 && y >=194 && y <=229)
        pieza = 28;

    // *************************************

    return(pieza);
}

private int interpretoPosicionInferior(int x, int y) {

    int pieza = 0;

    // ********** CUADRANTE 4 *************

    if(x>=12 && x<=51 && y >=9 && y <=41)
        pieza = 48;
    if(x>=18 && x<=49 && y >=44 && y <=85)
        pieza = 47;
    if(x>=26 && x<=68 && y >=85 && y <=126)
        pieza = 46;
    if(x>=37 && x<=76 && y >=131 && y <=153)
        pieza = 45;
    if(x>=50 && x<=80 && y >=156 && y <=178)
        pieza = 44;
    if(x>=64 && x<=93 && y >=177 && y <=204)
        pieza = 43;
    if(x>=91 && x<=113 && y >=188 && y <=227)
        pieza = 42;
    if(x>=113 && x<=142 && y >=192 && y <=241)
        pieza = 41;

    // ********** CUADRANTE 3 *************

    if(x>=145 && x<=176 && y >=192 && y <=241)
        pieza = 31;
    if(x>=176 && x<=197 && y >=187 && y <=227)
        pieza = 32;
    if(x>=197 && x<=222 && y >=178 && y <=211)
        pieza = 33;
    if(x>=209 && x<=239 && y >=157 && y <=178)
        pieza = 34;
    if(x>=216 && x<=247 && y >=129 && y <=158)
        pieza = 35;
    if(x>=228 && x<=263 && y >=87 && y <=132)
        pieza = 36;
    if(x>=237 && x<=270 && y >=48 && y <=85)
        pieza = 37;
    if(x>=247 && x<=282 && y >=16 && y <=45)
        pieza = 38;

    return(pieza);
}

   private String formatFecha(Date d) {

        String fecha;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fecha = sdf.format(d);

        return(fecha);
    }

  public void permitirEditarCI(Boolean b) {

        jtfCedulaIdentidad.setEnabled(b);
        jtfCedulaIdentidad.setEditable(b);
 }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregar;
    private javax.swing.JLabel alerta;
    private javax.swing.JButton almacenar;
    private javax.swing.JCheckBox aportacertificado;
    private javax.swing.ButtonGroup bgPeso;
    private javax.swing.JLabel bocainferior;
    private javax.swing.JLabel bocasuperior;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.kcreativa.pitosycornetas.TableIcon diagnosticos;
    private com.kcreativa.pitosycornetas.KTextBox edad;
    private org.jdesktop.swingx.JXDatePicker fechaHoy;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox jcbProcedencia;
    private javax.swing.JRadioButton jrbFemenino;
    private javax.swing.JRadioButton jrbMasculino3;
    private com.kcreativa.pitosycornetas.KTextBox jtbApellido3;
    private com.kcreativa.pitosycornetas.KTextBox jtbNombre3;
    private com.kcreativa.pitosycornetas.KTextBox jtbNumero;
    private javax.swing.JFormattedTextField jtfCedulaIdentidad;
    private javax.swing.JCheckBox lesionblanca;
    private javax.swing.JList listDiagnosticos;
    private javax.swing.JList listadientes;
    private javax.swing.JRadioButton meses24;
    private javax.swing.JRadioButton meses6;
    private javax.swing.JRadioButton negado;
    private javax.swing.JTextArea observaciones;
    private javax.swing.JCheckBox patologiamaligna;
    private javax.swing.ButtonGroup plazos;
    private javax.swing.JButton quitar;
    // End of variables declaration//GEN-END:variables

    public String getDocumento() {


        return(jtfCedulaIdentidad.getText());
    }

    public void setMostrarBotonAlmacenar(boolean b) {

        almacenar.setVisible(b);
    }

    public void setAlertaUltimaVigencia(boolean b) {
        alerta.setVisible(b);
    }
    
}
