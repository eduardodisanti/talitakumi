/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UIListaDePrecios.java
 *
 * Created on 13-abr-2010, 16:00:35
 */

package talitakumiadmin.administracion.ui;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import talitakumi.framework.DataParameter;

/**
 *
 * @author rupertus
 */
public class UIMoldeAgenda extends javax.swing.JPanel {

    /** Creates new form UIListaDePrecios */
    public UIMoldeAgenda() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        precios = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        lunes = new javax.swing.JCheckBox();
        miercoles = new javax.swing.JCheckBox();
        martes = new javax.swing.JCheckBox();
        jueves = new javax.swing.JCheckBox();
        viernes = new javax.swing.JCheckBox();
        sabado = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        lugares = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        hora = new javax.swing.JSpinner();
        duracion = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        minutos = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        almacenar = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        precios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(precios);

        jSplitPane1.setLeftComponent(jScrollPane2);

        lunes.setText("Lunes");

        miercoles.setText("Miércoles");

        martes.setText("Martes");

        jueves.setText("Jueves");

        viernes.setText("Viernes");

        sabado.setText("Sábado");

        jLabel1.setText("LUGARES");

        lugares.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        lugares.setText("15");

        jLabel2.setText("HH");

        jLabel3.setText("DURACION");

        hora.setModel(new javax.swing.SpinnerNumberModel(7, 6, 20, 1));

        duracion.setModel(new javax.swing.SpinnerNumberModel(15, 0, 60, 5));

        jLabel4.setText("MM");

        minutos.setModel(new javax.swing.SpinnerNumberModel(0, 0, 45, 15));

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(lunes)
                    .add(martes)
                    .add(miercoles)
                    .add(jueves)
                    .add(viernes)
                    .add(sabado)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(hora, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(20, 20, 20)
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(minutos, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel3)
                            .add(jLabel1))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(lugares, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(duracion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(lunes)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(martes)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(miercoles)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jueves)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(viernes)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(sabado)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(17, 17, 17)
                        .add(jLabel2))
                    .add(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jLabel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(minutos)
                            .add(hora))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(duracion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3))
                .add(18, 18, 18)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lugares, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel1))
                .add(108, 108, 108))
        );

        jSplitPane1.setRightComponent(jPanel2);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        almacenar.setText("Almacenar");
        jPanel1.add(almacenar);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton almacenar;
    private javax.swing.JSpinner duracion;
    private javax.swing.JSpinner hora;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JCheckBox jueves;
    private javax.swing.JTextField lugares;
    private javax.swing.JCheckBox lunes;
    private javax.swing.JCheckBox martes;
    private javax.swing.JCheckBox miercoles;
    private javax.swing.JSpinner minutos;
    private javax.swing.JTable precios;
    private javax.swing.JCheckBox sabado;
    private javax.swing.JCheckBox viernes;
    // End of variables declaration//GEN-END:variables

    public void addAlmacenarListener(ActionListener clientesListener) {

        almacenar.addActionListener(clientesListener);
    }

    public void setModelListaActividades(DefaultTableModel dftm) {
        precios.setModel(dftm);
    }

    public void setAnchoColumnaPrecios(int idx, int ancho) {
        precios.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumn col = precios.getColumnModel().getColumn(idx);
        col.setPreferredWidth(ancho);

    }

    public void habilitarFormulario(boolean b) {

        lunes.setEnabled(true);
        martes.setEnabled(true);
        miercoles.setEnabled(true);
        jueves.setEnabled(true);
        viernes.setEnabled(true);
        sabado.setEnabled(true);

        hora.setEnabled(true);
        minutos.setEnabled(true);
        duracion.setEnabled(true);
        lugares.setEnabled(true);
    }

    public void addElegirActividadListener(MouseListener actividadListener) {

                precios.addMouseListener(actividadListener);
    }

    public int getFilaElegida() {
        return(precios.getSelectedRow());
    }

    public DataParameter getData() {

        DataParameter dp = new DataParameter();

        lunes.setEnabled(true);
        martes.setEnabled(true);
        miercoles.setEnabled(true);
        jueves.setEnabled(true);
        viernes.setEnabled(true);
        sabado.setEnabled(true);

        hora.setEnabled(true);
        minutos.setEnabled(true);
        duracion.setEnabled(true);

        dp.set("lunes", lunes.isSelected());
        dp.set("martes", martes.isSelected());
        dp.set("miercoles", miercoles.isSelected());
        dp.set("jueves", jueves.isSelected());
        dp.set("viernes", viernes.isSelected());
        dp.set("sabado", sabado.isSelected());

        dp.set("hora", (Integer) hora.getValue());
        dp.set("minutos", (Integer) minutos.getValue());
        dp.set("duracion", (Integer) duracion.getValue());
        dp.set("lugares", (Integer) Integer.parseInt(lugares.getText()));

        return(dp);
    }

    public void setData(DataParameter dp) {

        dp.set("lunes", lunes.isSelected());
        dp.set("martes", martes.isSelected());
        dp.set("miercoles", miercoles.isSelected());
        dp.set("jueves", jueves.isSelected());
        dp.set("viernes", viernes.isSelected());
        dp.set("sabado", sabado.isSelected());


        lunes.setEnabled(dp.get("lunes")!=null);
        martes.setEnabled(dp.get("martes")!=null);
        miercoles.setEnabled(dp.get("miercoles")!=null);
        jueves.setEnabled(dp.get("jueves")!=null);
        viernes.setEnabled(dp.get("viernes")!=null);
        sabado.setEnabled(dp.get("sabado")!=null);

        hora.setValue(dp.get("hora"));
        minutos.setValue(dp.get("minutos"));

        dp.set("duracion", (Integer) duracion.getValue());
        dp.set("lugares", (Integer) Integer.parseInt(lugares.getText()));
    }
}
