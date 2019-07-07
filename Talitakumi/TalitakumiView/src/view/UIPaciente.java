/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UIPaciente.java
 *
 * Created on 27-nov-2008, 20:59:41
 */

package view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import talitakumi.framework.DataParameter;

/**
 *
 * @author rupertus
 */
public class UIPaciente extends javax.swing.JPanel {

    /** Creates new form UIPaciente */
    public UIPaciente() {
        initComponents();

        //cedula.setText("0000000");
        cedula.setSelectionStart(0);
        cedula.setSelectionEnd(15);
        //setFormatoCedulaUruguaya();
        setFormatoCedulaLibre();
       
    }

    public void esChequeoExterno(){
        fechanacimiento.setVisible(false);
        labelfnac.setVisible(false);
       
    }

    public void agregarActivarCamaraListener(ActionListener activarCamaraListener) {
        jButton1.addActionListener(activarCamaraListener);
    }

    public void agregarTomarFotoListener(ActionListener tomarFotoListener) {
        this.jButton2.addActionListener(tomarFotoListener);
    }

    public void setActivoBotonActivarCamara(boolean estado) {
        this.jButton1.setEnabled(estado);
    }

    public void setActivoBotonTomarFoto(boolean estado) {
        this.jButton2.setEnabled(estado);
    }

    public String getCedula() {

        return(cedula.getText());
    }

    public JPanel getPanelFoto() {
        return(this.panelFoto);
    }

        
    public void setPaciente(DataParameter p)  {

        apellidos.setText((String) p.get("apellidos"));
        nombres.setText((String) p.get("nombres"));
        domicilio.setText((String) p.get("domicilio"));
        ciudad.setText((String) p.get("ciudad"));
        telefono.setText((String) p.get("telefono"));
        celular.setText((String) p.get("movil"));
        email.setText((String)p.get("email"));
        observaciones.setText((String)p.get("observaciones"));
        cedula.setText(((Integer) p.get("documento")) + "");
  
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date d = (Date) p.get("fechanacimiento");

        String s = dateFormat.format(d);

        fechanacimiento.setText(s);
        Character sex =  (Character) p.get("sexo");
        if(sex=='M') {
            masculino.setSelected(true);
        }
        else {
            femenino.setSelected(true);
        }
    }

 
    public void setFormatoCedulaUruguaya() {
            String anterior = cedula.getText();
            //cedula.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#######")));
            cedula.setText(anterior);
    }

    public void setFormatoCedulaLibre() {


//            cedula.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##############")));
            //cedula.setText("0");
  
    }


    public int getAnchoFoto() {

        return(this.panelFoto.getWidth());
    }

    public int getAltoFoto() {

        return(this.panelFoto.getHeight());
    }
    
    public void setCedulaListener(FocusListener cedulaListener) {

        cedula.addFocusListener(cedulaListener);
    }

    public String getApellidos() {

        return(apellidos.getText());
    }

    public String getCiudad() {

        return(ciudad.getText());
    }

    public String getDomicilio() {
        return(domicilio.getText());
    }

    public void setFechanacimiento(String fn){
        this.fechanacimiento.setText(fn);
    }

    public void setFechanacimiento(Date fn){

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        this.fechanacimiento.setText(sdf.format(fn));
    }

    public Date getFechanacimiento() {

        Date d = null;
        String s = fechanacimiento.getText();

        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            d = sf.parse(s);
        } catch (ParseException ex) {
            Logger.getLogger(UIPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }

        return(d);
    }

    public String getMovil() {
        return(celular.getText());
    }

    public String getNombres() {
        return(nombres.getText());
    }

    public char getSexo() {

        char s;

        if(masculino.isSelected())
            s = 'M';
        else
            s = 'F';

        return(s);
    }

    public String getTelefono() {
        return(telefono.getText());
    }

    public String getEmail() {
        return(email.getText());
    }
    
    public void validarFecha() {

        String fecha = fechanacimiento.getText();

        if(fecha==null || fecha.equals("")) {
            labelfnac.setForeground(Color.RED);
            fechanacimiento.grabFocus();
        } else
               labelfnac.setForeground(Color.BLACK);
    }

    public String getVencimientoLDC(){

        return(vencimientoLDC.getText());
    }

    public void setVencimientoLDC(String vencimientocds){

        vencimientoLDC.setText(vencimientocds);
    }

    public String getVencimientoCds(){

        return(vencimientoCDS.getText());
    }

    public void setVencimientoCds(String vencimientocds){

        vencimientoCDS.setText(vencimientocds);
    }

    public void setEnabledVtoCds(Boolean a){

        vencimientoCDS.setVisible(a);
        labelVencimientoCarne.setVisible(a);
    }

    public void setEnabledVtoLdc(Boolean a){

        vencimientoLDC.setVisible(a);
        labelVencimientoLibreta.setVisible(a);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sexo = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        apellidos = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel3 = new javax.swing.JLabel();
        nombres = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel4 = new javax.swing.JLabel();
        domicilio = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel5 = new javax.swing.JLabel();
        ciudad = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel6 = new javax.swing.JLabel();
        telefono = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel7 = new javax.swing.JLabel();
        celular = new com.kcreativa.pitosycornetas.KTextBox();
        fechanacimiento = new com.kcreativa.pitosycornetas.KFormattedTextBox();
        labelfnac = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        masculino = new javax.swing.JRadioButton();
        femenino = new javax.swing.JRadioButton();
        panelFoto = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        email = new com.kcreativa.pitosycornetas.KTextBox();
        buscar = new javax.swing.JButton();
        vencimientoLDC = new com.kcreativa.pitosycornetas.KFormattedTextBox();
        labelVencimientoLibreta = new javax.swing.JLabel();
        vencimientoCDS = new com.kcreativa.pitosycornetas.KFormattedTextBox();
        labelVencimientoCarne = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        observaciones = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        cedula = new javax.swing.JTextField();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel10.setText("jLabel10");

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("DOCUMENTO");

        jLabel2.setText("APELLIDOS");

        apellidos.setBackground(new java.awt.Color(246, 246, 246));
        apellidos.setEditando(true);
        apellidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apellidosActionPerformed(evt);
            }
        });

        jLabel3.setText("NOMBRES");

        nombres.setBackground(new java.awt.Color(246, 246, 246));
        nombres.setEditando(true);

        jLabel4.setText("DOMICILIO");

        domicilio.setBackground(new java.awt.Color(246, 246, 246));
        domicilio.setEditando(true);

        jLabel5.setText("CIUDAD");

        ciudad.setBackground(new java.awt.Color(246, 246, 246));
        ciudad.setEditando(true);

        jLabel6.setText("TELEFONO");

        telefono.setBackground(new java.awt.Color(246, 246, 246));
        telefono.setEditando(true);

        jLabel7.setText("CELULAR");

        celular.setBackground(new java.awt.Color(246, 246, 246));
        celular.setEditando(true);

        fechanacimiento.setBackground(new java.awt.Color(246, 246, 246));
        fechanacimiento.setEditando(true);
        try {
            fechanacimiento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##-##-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        labelfnac.setText("FECHA DE NACIMIENTO");

        jLabel9.setText("SEXO");

        sexo.add(masculino);
        masculino.setText("MASCULINO");

        sexo.add(femenino);
        femenino.setSelected(true);
        femenino.setText("FEMENINO");
        femenino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                femeninoActionPerformed(evt);
            }
        });

        panelFoto.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelFoto.setMaximumSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout panelFotoLayout = new javax.swing.GroupLayout(panelFoto);
        panelFoto.setLayout(panelFotoLayout);
        panelFotoLayout.setHorizontalGroup(
            panelFotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 96, Short.MAX_VALUE)
        );
        panelFotoLayout.setVerticalGroup(
            panelFotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 96, Short.MAX_VALUE)
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/camaramini.png"))); // NOI18N

        jButton2.setBackground(new java.awt.Color(204, 204, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/camaramini.png"))); // NOI18N
        jButton2.setEnabled(false);

        jLabel8.setText("EMAIL");

        email.setBackground(new java.awt.Color(246, 246, 246));
        email.setEditando(true);

        buscar.setText("Buscar");

        vencimientoLDC.setBackground(new java.awt.Color(246, 246, 246));
        vencimientoLDC.setEditando(true);
        try {
            vencimientoLDC.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##-##-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        labelVencimientoLibreta.setText("VENCIMIENTO LIBRETA");

        vencimientoCDS.setBackground(new java.awt.Color(246, 246, 246));
        vencimientoCDS.setEditando(true);
        try {
            vencimientoCDS.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##-##-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        labelVencimientoCarne.setText("VENCIMIENTO CARNE");

        observaciones.setColumns(20);
        observaciones.setRows(5);
        jScrollPane1.setViewportView(observaciones);

        jLabel11.setText("Observaciones");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelVencimientoCarne)
                            .addComponent(labelfnac)
                            .addComponent(labelVencimientoLibreta))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fechanacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(110, 110, 110)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(masculino)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(femenino)
                                .addGap(87, 87, 87))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(vencimientoCDS, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(vencimientoLDC, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1)
                                .addGap(18, 18, 18)))
                        .addComponent(panelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addComponent(telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(celular, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(email, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                        .addGap(96, 96, 96))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(domicilio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(buscar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cedula, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(apellidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(nombres, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ciudad, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombres, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel2)
                        .addComponent(buscar)
                        .addComponent(jLabel1)
                        .addComponent(cedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(domicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(ciudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(celular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(masculino)
                                .addComponent(femenino))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(labelfnac)
                                .addComponent(fechanacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel11)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 16, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(labelVencimientoLibreta)
                                            .addComponent(vencimientoLDC, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(3, 3, 3)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(labelVencimientoCarne, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(vencimientoCDS, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panelFoto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jButton1)
                            .addGap(18, 18, 18)
                            .addComponent(jButton2))))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void femeninoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_femeninoActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_femeninoActionPerformed

    private void apellidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apellidosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.kcreativa.pitosycornetas.KTextBox apellidos;
    private javax.swing.JButton buscar;
    private javax.swing.JTextField cedula;
    private com.kcreativa.pitosycornetas.KTextBox celular;
    private com.kcreativa.pitosycornetas.KTextBox ciudad;
    private com.kcreativa.pitosycornetas.KTextBox domicilio;
    private com.kcreativa.pitosycornetas.KTextBox email;
    private com.kcreativa.pitosycornetas.KFormattedTextBox fechanacimiento;
    private javax.swing.JRadioButton femenino;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelVencimientoCarne;
    private javax.swing.JLabel labelVencimientoLibreta;
    private javax.swing.JLabel labelfnac;
    private javax.swing.JRadioButton masculino;
    private com.kcreativa.pitosycornetas.KTextBox nombres;
    private javax.swing.JTextArea observaciones;
    private javax.swing.JPanel panelFoto;
    private javax.swing.ButtonGroup sexo;
    private com.kcreativa.pitosycornetas.KTextBox telefono;
    private com.kcreativa.pitosycornetas.KFormattedTextBox vencimientoCDS;
    private com.kcreativa.pitosycornetas.KFormattedTextBox vencimientoLDC;
    // End of variables declaration//GEN-END:variables

    public void setBuscarPacienteListener(ActionListener buscarPacienteListener) {

        buscar.addActionListener(buscarPacienteListener);
    }

    public void setCedula(int unacedula) {
        this.cedula.setText(unacedula+"");
    }

    public void enfocarPaciente() {

        String x = cedula.getText();

        if(x.length()>0) {
            cedula.setSelectionStart(0);
            cedula.setSelectionEnd(x.length());
        }
    }
    
}
