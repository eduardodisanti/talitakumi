/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UIModificarEpisodioADM.java
 *
 * Created on 30-jul-2009, 16:29:41
 */

package view;

import com.kcreativa.pitosycornetas.ComboBoxRenderer;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import talitakumi.framework.ui.EachRowRenderer;

/**
 *
 * @author rupertus
 */
public class UIModificarEpisodioADM extends javax.swing.JFrame {

    EachRowRenderer rowRender;
    JPanel panelUtil;

    /** Creates new form UIModificarEpisodioADM */
    public UIModificarEpisodioADM(java.awt.Frame parent, boolean modal) {
        //super(parent, modal);
        initComponents();

        tablaactividades.setRowHeight(24);
        tablaactividades.setGridColor(Color.gray);
        tablaactividades.setAutoCreateRowSorter(true);

        rowRender = new EachRowRenderer();
    }

    public void setAnchoColumna(int i, int ancho) {

        tablaactividades.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumn col = tablaactividades.getColumnModel().getColumn(i);
        col.setPreferredWidth(ancho);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        scrollUtil = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaactividades = new javax.swing.JTable();
        imprimir = new javax.swing.JButton();
        ver = new javax.swing.JButton();
        comentar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Búsqueda");
        setResizable(false);

        jButton1.setText("Almacenar");

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        tablaactividades.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaactividades);

        imprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fileprint.png"))); // NOI18N
        imprimir.setText("Imprimir");
        imprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imprimirActionPerformed(evt);
            }
        });

        ver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mirar.gif"))); // NOI18N
        ver.setText("Ver");
        ver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verActionPerformed(evt);
            }
        });

        comentar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/txt2.png"))); // NOI18N
        comentar.setText("Comentarios");
        comentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comentarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 902, Short.MAX_VALUE)
                    .addComponent(scrollUtil, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 902, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(comentar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                        .addComponent(ver)
                        .addGap(176, 176, 176)
                        .addComponent(imprimir)
                        .addGap(99, 99, 99)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollUtil, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addComponent(imprimir)
                    .addComponent(ver)
                    .addComponent(comentar))
                .addContainerGap())
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-926)/2, (screenSize.height-519)/2, 926, 519);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imprimirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_imprimirActionPerformed

    private void verActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_verActionPerformed

    private void comentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comentarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comentarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton comentar;
    private javax.swing.JButton imprimir;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane scrollUtil;
    private javax.swing.JTable tablaactividades;
    private javax.swing.JButton ver;
    // End of variables declaration//GEN-END:variables


    public void setPanelPaciente(UIPaciente uip) {
      scrollUtil.setViewportView(uip);
      panelUtil = uip;
    }

    public Integer getCedula() {

        String x;


        x = ((UIPaciente)panelUtil).getCedula();
        
        Integer ced = null;

        if(x.contains("-")) {
           StringTokenizer st = new StringTokenizer(x, "-");
           ced = Integer.parseInt(st.nextToken());
        } else {
               StringTokenizer st = new StringTokenizer(x, " ");
               ced = Integer.parseInt(st.nextToken());
          }
        return(ced);
    }

    public void setCedulaListener(FocusListener cedulaListener) {

        ((UIPaciente)panelUtil).setCedulaListener(cedulaListener);
    }

    public void setModelTabla(DefaultTableModel dftm) {
        
        tablaactividades.setModel(dftm);
    }

    public void setEpisodioElegidoListener(MouseListener ml) {

        tablaactividades.addMouseListener(ml);
    }

    public Object getIdEpisodioElegido() {

        int id;
        int idx;

        idx = tablaactividades.getSelectedRow();
        id = (Integer)tablaactividades.getValueAt(idx, 0);

        return(id);
    }

    public void setEditorListCelda(int linea, DefaultCellEditor jlistEditor, Vector opciones) {
        ComboBoxRenderer comboBoxRenderer = new ComboBoxRenderer(opciones);
        
        rowRender.add(linea, comboBoxRenderer);
        tablaactividades.getColumn("Facturas").setCellRenderer(rowRender);
    }

    public void setBuscarImprimirEpisodio(ActionListener imprimirEpisodioListener) {
        imprimir.addActionListener(imprimirEpisodioListener);
    }

    public void setVerEpisodio(ActionListener verEpisodioListener) {

        ver.addActionListener(verEpisodioListener);
    }

    public void setComentarEpisodio(ActionListener verEpisodioListener) {

        comentar.addActionListener(verEpisodioListener);
    }
}
