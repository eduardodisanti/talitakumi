/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UILibretaRecepcion.java
 *
 * Created on 27-nov-2008, 22:01:43
 */

package ui;

import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import talitakumi.framework.DataParameter;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Procedencias;
import talitakumi.services.AbstractService;
import talitakumi.servicios.libretadeconducir.ServicioDeterminarTipoLibreta;
import view.UIPaciente;


/**
 *
 * @author rupertus
 */
public class UILibretaRecepcion extends javax.swing.JFrame {
    public Integer getCodigo1;

    /** Creates new form UILibretaRecepcion */
    public UILibretaRecepcion() {
        initComponents();
        
    }

    public void empezarEdicion() {
        this.jRadioButton1.grabFocus();
    }

    public String getApellidos() {

        return((UIPaciente)panelUtil).getApellidos();
    }

    public String getCategoria() {
        return((String)categoria.getSelectedItem());
    }

    public Integer getCedula() {

        String cedula = ((UIPaciente)panelUtil).getCedula();
        Integer ced = null;

        if(cedula.contains("-")) {
           StringTokenizer st = new StringTokenizer(cedula, "-");
           ced = Integer.parseInt(st.nextToken());
        } else {
               //StringTokenizer st = new StringTokenizer(cedula, " ");
                try {
                    ced = Integer.parseInt(cedula);
                } catch(Exception ex) {
                    ced = 0;
                }
          }
        return(ced);
    }

    public String getCiudad() {
        return((UIPaciente)panelUtil).getCiudad();
    }

    public int getClienteELegido() {
        return(jcbClientes.getSelectedIndex());
    }

    public int getIndiceCliente() {
        int idx = jcbProcedencia.getSelectedIndex();

        return(idx);
    }

    public Integer getCodigo1() {

        return(Integer.parseInt(codigo1.getText()));
    }

    public Integer getCodigo2() {

        return(Integer.parseInt(codigo2.getText()));
    }

    public Integer getCodigo3() {

        return(Integer.parseInt(codigo3.getText()));
    }

    public String getDomicilio() {
        return((UIPaciente)panelUtil).getDomicilio();
    }

    public Date getFechanacimiento() {
        return((UIPaciente)panelUtil).getFechanacimiento();
    }

    public String getMovil() {
        return((UIPaciente)panelUtil).getMovil();
    }

    public String getNombres() {
        return((UIPaciente)panelUtil).getNombres();
    }

    public JPanel getPanelUtil() {

        return(panelUtil);
    }

    public int getProcedenciaElegida() {
        return(jcbProcedencia.getSelectedIndex());
    }

    public char getSexo() {
        return((UIPaciente)panelUtil).getSexo();
    }

    public String getTelefono() {
        return((UIPaciente)panelUtil).getTelefono();
    }

    public int getTipoLibreta() throws Exception {


        AbstractService s = new ServicioDeterminarTipoLibreta(this.jRadioButton1.isSelected(), this.jRadioButton2.isSelected(), jcbHomologacion.isSelected(), this.jcbCambioCat.isSelected(), this.jRadioButton3.isSelected());
        Integer i = 0;
        i = (Integer) s.invoke();

        return(i);
    }

    public Date getVigenciaLibretaAnterior() throws ParseException {

        Date d = new GregorianCalendar().getTime();
        String s = vigenciaanterior.getText();

        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
        d = sf.parse(s);


        return(d);
    }

    public void setCedulaListener(FocusListener cedulaListener) {

        ((UIPaciente)panelUtil).setCedulaListener(cedulaListener);
    }

    public void setListaProcedencias(List<Procedencias> lp) {

        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();

        for(Procedencias p : lp) {

            dcbm.addElement(p.getDescripcion());
        }

        jcbProcedencia.setModel(dcbm);
    }

    public void setPanelUtil(JPanel uipac) {

      panelUtil = uipac;
      scrollUtil.setViewportView(uipac);
    }

    public void setGuardarListener(ActionListener al) {

        this.guardar.addActionListener(al);
    }

    public void setCancelarListener(ActionListener al) {

        this.cancelar.addActionListener(al);
    }

    public void setPersona(DataParameter dp) {

        ((UIPaciente)panelUtil).setPaciente(dp);
        ((UIPaciente)panelUtil).setEnabledVtoLdc(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gupoTipoLibreta = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        panelUtil = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        numerolibreta = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jcbCambioCat = new javax.swing.JCheckBox();
        jRadioButton3 = new javax.swing.JRadioButton();
        jcbHomologacion = new javax.swing.JCheckBox();
        jLabel18 = new javax.swing.JLabel();
        jcbProcedencia = new javax.swing.JComboBox();
        jcbClientes = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        scrollUtil = new javax.swing.JScrollPane();
        guardar = new javax.swing.JButton();
        cancelar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        vigenciaanterior = new com.kcreativa.pitosycornetas.KFormattedTextBox();
        jLabel4 = new javax.swing.JLabel();
        codigo1 = new com.kcreativa.pitosycornetas.KTextBox();
        codigo2 = new com.kcreativa.pitosycornetas.KTextBox();
        codigo3 = new com.kcreativa.pitosycornetas.KTextBox();
        jLabel5 = new javax.swing.JLabel();
        categoria = new javax.swing.JComboBox();
        INMEDIATO = new javax.swing.JCheckBox();
        codigo4 = new com.kcreativa.pitosycornetas.KTextBox();
        codigo5 = new com.kcreativa.pitosycornetas.KTextBox();
        codigo6 = new com.kcreativa.pitosycornetas.KTextBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Libreta Recepcion");
        setBackground(new java.awt.Color(255, 255, 255));

        panelUtil.setBackground(new java.awt.Color(102, 0, 0));
        panelUtil.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(941, 941, 941)
                .addComponent(panelUtil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(229, 229, 229)
                .addComponent(panelUtil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logomedilab150x94.jpg"))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 51, 153));
        jLabel17.setText("LIBRETA DE CONDUCIR");

        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("NÚMERO");

        numerolibreta.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        numerolibreta.setText("000000");

        gupoTipoLibreta.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("ASPIRANTE");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        gupoTipoLibreta.add(jRadioButton2);
        jRadioButton2.setText("RENOVACIÓN");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jcbCambioCat.setText("CAMBIO DE CATEGORÍA");
        jcbCambioCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbCambioCatActionPerformed(evt);
            }
        });

        gupoTipoLibreta.add(jRadioButton3);
        jRadioButton3.setText("SOLO HOMOLOGACION");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        jcbHomologacion.setText("HOMOLOGACIÓN");
        jcbHomologacion.setEnabled(false);

        jLabel18.setText("Procedencia");

        jcbProcedencia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Particular" }));

        jcbClientes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Particular" }));

        jLabel19.setText("CLIENTE");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(78, 78, 78)
                        .addComponent(jLabel17))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(100, 100, 100)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(numerolibreta, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jRadioButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbHomologacion))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jcbCambioCat)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcbProcedencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jRadioButton3))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(numerolibreta)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton1)
                        .addGap(0, 0, 0)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButton2)
                            .addComponent(jcbHomologacion))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbCambioCat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcbProcedencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(71, 71, 71)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)))))
        );

        guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ok24.png"))); // NOI18N
        guardar.setText("Guardar");

        cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/remove.png"))); // NOI18N
        cancelar.setText("Cancelar");

        jLabel3.setText("VENCIMIENTO DE LA LIBRETA ANTERIOR");

        vigenciaanterior.setBackground(new java.awt.Color(246, 246, 246));
        vigenciaanterior.setEditando(true);
        try {
            vigenciaanterior.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##-##-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel4.setText("CODIGOS IMM EN LIBRETA ANTERIOR");

        codigo1.setBackground(new java.awt.Color(246, 246, 246));
        codigo1.setText("0");
        codigo1.setEditando(true);
        codigo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigo1ActionPerformed(evt);
            }
        });

        codigo2.setBackground(new java.awt.Color(246, 246, 246));
        codigo2.setText("0");
        codigo2.setEditando(true);
        codigo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigo2ActionPerformed(evt);
            }
        });

        codigo3.setBackground(new java.awt.Color(246, 246, 246));
        codigo3.setText("0");
        codigo3.setEditando(true);
        codigo3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigo3ActionPerformed(evt);
            }
        });

        jLabel5.setText("CATEGORIA");

        categoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A1", "G1", "G2", " " }));

        INMEDIATO.setText("INMEDIATO");

        codigo4.setBackground(new java.awt.Color(246, 246, 246));
        codigo4.setText("0");
        codigo4.setEditando(true);
        codigo4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigo4ActionPerformed(evt);
            }
        });

        codigo5.setBackground(new java.awt.Color(246, 246, 246));
        codigo5.setText("0");
        codigo5.setEditando(true);
        codigo5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigo5ActionPerformed(evt);
            }
        });

        codigo6.setBackground(new java.awt.Color(246, 246, 246));
        codigo6.setText("0");
        codigo6.setEditando(true);
        codigo6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigo6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollUtil, javax.swing.GroupLayout.PREFERRED_SIZE, 922, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(codigo2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(categoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(32, 32, 32)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(vigenciaanterior, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(47, 47, 47))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(INMEDIATO)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(guardar)
                                        .addGap(26, 26, 26)
                                        .addComponent(cancelar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(codigo3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(codigo1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(codigo4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(codigo5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(codigo6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(589, 589, 589))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(scrollUtil, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(vigenciaanterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addComponent(codigo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(categoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(codigo4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(codigo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(codigo5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(codigo3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(codigo6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(469, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(guardar)
                            .addComponent(cancelar)
                            .addComponent(INMEDIATO))))
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(928, 531));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
//         TODO add your handling code here:

        boolean b=this.jRadioButton2.isSelected();
        this.jcbHomologacion.setEnabled(b);

        this.vigenciaanterior.setEnabled(false);
        this.vigenciaanterior.setText("00-00-0000");
        ((UIPaciente)panelUtil).setFormatoCedulaUruguaya();
}//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        
        boolean b;

        b=this.jRadioButton2.isSelected();
        this.jcbHomologacion.setEnabled(b);

        this.vigenciaanterior.setEnabled(true);
        ((UIPaciente)panelUtil).setFormatoCedulaUruguaya();
}//GEN-LAST:event_jRadioButton2ActionPerformed

    public void setListaClientes(List<Clientes> lp) {


        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();

        for(Clientes p : lp) {

            dcbm.addElement(p.getNombre());
        }

        jcbClientes.setModel(dcbm);
    }

    private void jcbCambioCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbCambioCatActionPerformed

        boolean b;

        b=this.jRadioButton2.isSelected();
        this.jcbHomologacion.setEnabled(b);
}//GEN-LAST:event_jcbCambioCatActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed

                this.vigenciaanterior.setEnabled(true);
                ((UIPaciente)panelUtil).setFormatoCedulaLibre();
}//GEN-LAST:event_jRadioButton3ActionPerformed

    private void codigo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigo1ActionPerformed

    }//GEN-LAST:event_codigo1ActionPerformed

    private void codigo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigo2ActionPerformed

    }//GEN-LAST:event_codigo2ActionPerformed

    private void codigo3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigo3ActionPerformed

    }//GEN-LAST:event_codigo3ActionPerformed

    private void codigo4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigo4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codigo4ActionPerformed

    private void codigo5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigo5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codigo5ActionPerformed

    private void codigo6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigo6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codigo6ActionPerformed

    /**
    * @param args the command line arguments
    */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox INMEDIATO;
    private javax.swing.JButton cancelar;
    private javax.swing.JComboBox categoria;
    private com.kcreativa.pitosycornetas.KTextBox codigo1;
    private com.kcreativa.pitosycornetas.KTextBox codigo2;
    private com.kcreativa.pitosycornetas.KTextBox codigo3;
    private com.kcreativa.pitosycornetas.KTextBox codigo4;
    private com.kcreativa.pitosycornetas.KTextBox codigo5;
    private com.kcreativa.pitosycornetas.KTextBox codigo6;
    private javax.swing.JButton guardar;
    private javax.swing.ButtonGroup gupoTipoLibreta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JCheckBox jcbCambioCat;
    private javax.swing.JComboBox jcbClientes;
    private javax.swing.JCheckBox jcbHomologacion;
    private javax.swing.JComboBox jcbProcedencia;
    private javax.swing.JLabel numerolibreta;
    private javax.swing.JPanel panelUtil;
    private javax.swing.JScrollPane scrollUtil;
    private com.kcreativa.pitosycornetas.KFormattedTextBox vigenciaanterior;
    // End of variables declaration//GEN-END:variables

    public Boolean getInmediato() {
        return(INMEDIATO.isSelected());
    }

    public Date getVencimientoCDS() {

        Date f = null;

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
            f = sdf.parse(((UIPaciente)panelUtil).getVencimientoCds());
        } catch(Exception ex) {}

        return(f);
    }

    public Date getVencimientoLDC() {
        Date f = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
            f = sdf.parse(((UIPaciente)panelUtil).getVencimientoLDC());
        } catch(Exception ex) {}
        return(f);
    }

    public void setVencimientoLibretaAnterior(Date d) {
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        
        vigenciaanterior.setText(sdf.format(d));
    }
    
    public void setVencimientoCarnetAnterior(Date d) {
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        
        ((UIPaciente)panelUtil).setVencimientoCds(sdf.format(d));
    }

    public Integer getCodigo4() {
        
        return(Integer.parseInt(codigo4.getText()));
    }
    public Integer getCodigo5() {
        
        return(Integer.parseInt(codigo5.getText()));
    }
    public Integer getCodigo6() {
        
        return(Integer.parseInt(codigo6.getText()));
    }
}
