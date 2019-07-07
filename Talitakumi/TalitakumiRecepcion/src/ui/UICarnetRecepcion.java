/*
 * To change this template, choose Tools | Templates
 * and open the template in the edito
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
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import talitakumi.framework.DataParameter;
import talitakumi.model.core.Clientes;
import view.UIPaciente;

/**
 *
 * @author rupertus
 */
public class UICarnetRecepcion extends javax.swing.JFrame {
    public Integer getCodigo1;

    /** Creates new form UILibretaRecepcion */
    public UICarnetRecepcion() {
        initComponents();
        
        
    }

    public void empezarEdicion() {
        this.jcbProcedencia.grabFocus();
    }

//    public void ultimavigencia(){
//
//    }

    public String getApellidos() {

        return((UIPaciente)panelUtil).getApellidos();
    }

    public int getBotonCarnetElegido() {

        int res = 0;

        if(this.carnetcomun.isSelected())
            res = 2;
        else
            if(this.carnetradicacion.isSelected())
                res = 3;
            else
                if(this.carnetfichamedicamayor.isSelected())
                    res = 4;
                else
                   if(this.carnetfichamedicamenor.isSelected())
                      res = 7;

        if(this.sinlaboratorio.isSelected())
            res = res * 1000;
        return(res);
    }

    public Integer getCedula() {

        String cedula = ((UIPaciente)panelUtil).getCedula();
        Integer ced = null;

        if(cedula.contains("-")) {
           StringTokenizer st = new StringTokenizer(cedula, "-");
           ced = Integer.parseInt(st.nextToken());
        } else
               if(cedula.contains(" ")) {
                   StringTokenizer st = new StringTokenizer(cedula, " ");
                   try {
                      ced = Integer.parseInt(st.nextToken());
                   } catch(Exception ex) {
                       ced = -1;
                   } 
               } else
                            try {
                                    ced = Integer.parseInt(cedula);
                            } catch(Exception ex) {
                                    ced = 0;
                            }
        return(ced);
    }

    public String getCiudad() {
        return((UIPaciente)panelUtil).getCiudad();
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

    public String getEmail() {
        return((UIPaciente)panelUtil).getEmail();
    }
    public void setCambiarFormatoCedulaListener(ActionListener carnetComunListener) {


        carnetcomun.addActionListener(carnetComunListener);
        carnetfichamedicamenor.addActionListener(carnetComunListener);
        carnetradicacion.addActionListener(carnetComunListener);
        
    }

    public void cambiarFormatCedulaSegunTipoCarnet() {
        if(this.carnetcomun.isSelected())
          ((UIPaciente)panelUtil).setFormatoCedulaUruguaya();
        else
            if(this.carnetfichamedicamenor.isSelected() || this.carnetfichamedicamayor.isSelected() )
              ((UIPaciente)panelUtil).setFormatoCedulaUruguaya();
            else
                if(this.carnetradicacion.isSelected())
                  ((UIPaciente)panelUtil).setFormatoCedulaLibre();
    }

    public void setListaProcedencias(List<Clientes> lp) {


        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();

        for(Clientes p : lp) {

            dcbm.addElement(p.getNombre());
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

    public void setCedulaListener(FocusListener al) {

        ((UIPaciente)panelUtil).setCedulaListener(al);
    }
    public void setPersona(DataParameter dp) {

        ((UIPaciente)panelUtil).setPaciente(dp);
        ((UIPaciente)panelUtil).setEnabledVtoCds(false);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botonestipocarnet = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        panelUtil = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        numerolibreta = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jcbProcedencia = new javax.swing.JComboBox();
        carnetcomun = new javax.swing.JRadioButton();
        carnetradicacion = new javax.swing.JRadioButton();
        carnetfichamedicamenor = new javax.swing.JRadioButton();
        carnetfichamedicamayor = new javax.swing.JRadioButton();
        sinlaboratorio = new javax.swing.JCheckBox();
        scrollUtil = new javax.swing.JScrollPane();
        guardar = new javax.swing.JButton();
        cancelar = new javax.swing.JButton();
        panelExtra = new javax.swing.JTabbedPane();
        antecedentes = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaantecedentes = new javax.swing.JTable();
        adicionales = new javax.swing.JPanel();
        INMEDIATO = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Carné De Salud");
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

        jLabel17.setFont(new java.awt.Font("Lucida Grande", 0, 24));
        jLabel17.setForeground(new java.awt.Color(0, 51, 153));
        jLabel17.setText("CARNÉ DE SALUD");

        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("NÚMERO");

        numerolibreta.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        numerolibreta.setText("000000");

        jLabel18.setText("PROCEDENCIA");

        jcbProcedencia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Particular" }));

        botonestipocarnet.add(carnetcomun);
        carnetcomun.setSelected(true);
        carnetcomun.setText("COMUN");

        botonestipocarnet.add(carnetradicacion);
        carnetradicacion.setText("RADICACION");

        carnetfichamedicamenor.setBackground(new java.awt.Color(204, 204, 255));
        botonestipocarnet.add(carnetfichamedicamenor);
        carnetfichamedicamenor.setText("FICHA MEDICA MENOR");
        carnetfichamedicamenor.setOpaque(true);
        carnetfichamedicamenor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carnetfichamedicamenorActionPerformed(evt);
            }
        });

        carnetfichamedicamayor.setBackground(new java.awt.Color(204, 204, 255));
        botonestipocarnet.add(carnetfichamedicamayor);
        carnetfichamedicamayor.setText("FICHA MEDICA MAYOR");
        carnetfichamedicamayor.setOpaque(true);

        sinlaboratorio.setText("SIN LABORATORIO");
        sinlaboratorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sinlaboratorioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(numerolibreta, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(carnetcomun)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(carnetradicacion))
                            .addComponent(sinlaboratorio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(carnetfichamedicamayor)
                            .addComponent(carnetfichamedicamenor)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbProcedencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(552, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(numerolibreta)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(carnetcomun)
                                    .addComponent(carnetradicacion)
                                    .addComponent(carnetfichamedicamayor))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(carnetfichamedicamenor)
                                    .addComponent(sinlaboratorio)))
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcbProcedencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ok24.png"))); // NOI18N
        guardar.setText("Guardar");

        cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/remove.png"))); // NOI18N
        cancelar.setText("Cancelar");

        panelExtra.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        panelExtra.setToolTipText("Adicionales de laboratorio");
        panelExtra.setPreferredSize(new java.awt.Dimension(626, 150));

        antecedentes.setToolTipText("Antecedentes paciente");
        antecedentes.setLayout(new javax.swing.BoxLayout(antecedentes, javax.swing.BoxLayout.LINE_AXIS));

        tablaantecedentes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Antecedentes"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaantecedentes);

        antecedentes.add(jScrollPane1);

        panelExtra.addTab("Antecedentes", antecedentes);

        adicionales.setLayout(new javax.swing.BoxLayout(adicionales, javax.swing.BoxLayout.LINE_AXIS));
        panelExtra.addTab("Adicionales", adicionales);

        INMEDIATO.setText("INMEDIATO");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(589, 589, 589))
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollUtil, javax.swing.GroupLayout.PREFERRED_SIZE, 907, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(panelExtra, javax.swing.GroupLayout.PREFERRED_SIZE, 781, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(INMEDIATO)
                            .addComponent(cancelar)
                            .addComponent(guardar))))
                .addGap(10, 10, 10)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(scrollUtil, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(guardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cancelar)
                                .addGap(18, 18, 18)
                                .addComponent(INMEDIATO))
                            .addComponent(panelExtra, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))))
                .addContainerGap())
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-943)/2, (screenSize.height-561)/2, 943, 561);
    }// </editor-fold>//GEN-END:initComponents

    private void carnetfichamedicamenorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carnetfichamedicamenorActionPerformed
        
    }//GEN-LAST:event_carnetfichamedicamenorActionPerformed

    private void sinlaboratorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sinlaboratorioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sinlaboratorioActionPerformed

    /**
    * @param args the command line arguments
    */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox INMEDIATO;
    private javax.swing.JPanel adicionales;
    private javax.swing.JPanel antecedentes;
    private javax.swing.ButtonGroup botonestipocarnet;
    private javax.swing.JButton cancelar;
    private javax.swing.JRadioButton carnetcomun;
    private javax.swing.JRadioButton carnetfichamedicamayor;
    private javax.swing.JRadioButton carnetfichamedicamenor;
    private javax.swing.JRadioButton carnetradicacion;
    private javax.swing.JButton guardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox jcbProcedencia;
    private javax.swing.JLabel numerolibreta;
    private javax.swing.JTabbedPane panelExtra;
    private javax.swing.JPanel panelUtil;
    private javax.swing.JScrollPane scrollUtil;
    private javax.swing.JCheckBox sinlaboratorio;
    private javax.swing.JTable tablaantecedentes;
    // End of variables declaration//GEN-END:variables

    public void setContenidoAuxiliar(JComponent j) {

        adicionales.add(j);
    }

    public void setModeloTablaAntecedentes(DefaultTableModel dftmANT) {

        tablaantecedentes.setModel(dftmANT);
    }

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
        ((UIPaciente)panelUtil).setVencimientoLDC(sdf.format(d));
    }

    public void setVencimientoCarnetAnterior(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        
        ((UIPaciente)panelUtil).setVencimientoCds(sdf.format(d));
    }

}
