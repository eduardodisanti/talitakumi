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
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import talitakumi.framework.DataParameter;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Procedencias;
import view.UIPaciente;


/**
 *
 * @author rupertus
 */
public class UISantander extends javax.swing.JFrame {
    public Integer getCodigo1;

    /** Creates new form UILibretaRecepcion */
    public UISantander() {
        initComponents();
        
    }

    public String getApellidos() {

        return((UIPaciente)panelUtil).getApellidos();
    }


    public Integer getCedula() {

//        String cedula = ((UIPaciente)panelUtil).getCedula();
//
//        StringTokenizer st = new StringTokenizer(cedula, "-");
//
//        return Integer.parseInt(st.nextToken());

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

    public int getClienteELegido() {
        return(jcbClientes.getSelectedIndex());
    }

    public int getIndiceCliente() {
        int idx = jcbProcedencia.getSelectedIndex();

        return(idx);
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
        jLabel18 = new javax.swing.JLabel();
        jcbProcedencia = new javax.swing.JComboBox();
        jcbClientes = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
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
        setTitle("Santander");
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
        jLabel17.setText("SANTANDER");

        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("NÚMERO");

        numerolibreta.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        numerolibreta.setText("000000");

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
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbProcedencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(1206, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(numerolibreta)
                                .addComponent(jLabel2))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(71, 71, 71)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jcbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel18)
                                    .addComponent(jcbProcedencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(16, 16, 16))
        );

        guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ok24.png"))); // NOI18N
        guardar.setText("Guardar");

        cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/remove.png"))); // NOI18N
        cancelar.setText("Cancelar");

        panelExtra.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        panelExtra.setToolTipText("Paraclinicos");
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

        panelExtra.setSelectedIndex(1);

        INMEDIATO.setText("INMEDIATO");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollUtil, javax.swing.GroupLayout.PREFERRED_SIZE, 922, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(panelExtra, javax.swing.GroupLayout.PREFERRED_SIZE, 781, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(INMEDIATO)
                                    .addComponent(cancelar)
                                    .addComponent(guardar))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollUtil, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelExtra, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(cancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(INMEDIATO)
                                .addContainerGap())))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(guardar)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        panelExtra.getAccessibleContext().setAccessibleName("Paraclinicos");

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-928)/2, (screenSize.height-531)/2, 928, 531);
    }// </editor-fold>//GEN-END:initComponents

    public void setListaClientes(List<Clientes> lp) {


        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();

        for(Clientes p : lp) {

            dcbm.addElement(p.getNombre());
        }

        jcbClientes.setModel(dcbm);
    }

    /**
    * @param args the command line arguments
    */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox INMEDIATO;
    private javax.swing.JPanel adicionales;
    private javax.swing.JPanel antecedentes;
    private javax.swing.JButton cancelar;
    private javax.swing.JButton guardar;
    private javax.swing.ButtonGroup gupoTipoLibreta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox jcbClientes;
    private javax.swing.JComboBox jcbProcedencia;
    private javax.swing.JLabel numerolibreta;
    private javax.swing.JTabbedPane panelExtra;
    private javax.swing.JPanel panelUtil;
    private javax.swing.JScrollPane scrollUtil;
    private javax.swing.JTable tablaantecedentes;
    // End of variables declaration//GEN-END:variables

    public void empezarEdicion() {

    }

    public void setContenidoAuxiliar(JComponent j) {

        adicionales.add(j);
    }

    public void setModeloTablaAntecedentes(DefaultTableModel dftmANT) {

        tablaantecedentes.setModel(dftmANT);
    }

    public Boolean getInmediato() {
        return(INMEDIATO.isSelected());
    }

}
