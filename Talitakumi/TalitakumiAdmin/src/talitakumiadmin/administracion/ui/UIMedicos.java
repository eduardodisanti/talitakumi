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
import java.text.DecimalFormat;
import java.text.ParseException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rupertus
 */
public class UIMedicos extends javax.swing.JPanel {

    /** Creates new form UIListaDePrecios */
    public UIMedicos() {
        initComponents();

        tablaEstudios.setAutoCreateRowSorter(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollEstudios = new javax.swing.JScrollPane();
        tablaEstudios = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        especialidad = new javax.swing.JTextField();
        codigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        almacenar = new javax.swing.JButton();
        cerrar = new javax.swing.JButton();
        activo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cedula = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        externo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        nombre = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        nuevo = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tablaEstudios.setModel(new javax.swing.table.DefaultTableModel(
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
        scrollEstudios.setViewportView(tablaEstudios);

        add(scrollEstudios, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 740, 290));

        jLabel1.setText("Especialidad");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 90, 20));

        especialidad.setText("1");
        especialidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                especialidadActionPerformed(evt);
            }
        });
        add(especialidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 340, 50, 30));

        codigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigoActionPerformed(evt);
            }
        });
        add(codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 310, 50, 30));

        jLabel3.setText("Activo");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 90, 20));

        almacenar.setText("Almacenar");
        add(almacenar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 430, -1, 40));

        cerrar.setText("Cerrar");
        add(cerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 430, -1, -1));

        activo.setText("S");
        add(activo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 400, 30, -1));

        jLabel4.setText("Cédula");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 90, 20));

        cedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cedulaActionPerformed(evt);
            }
        });
        add(cedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 370, 120, 30));

        jLabel7.setText("Externo");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 380, 50, 20));

        externo.setText("N");
        externo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                externoActionPerformed(evt);
            }
        });
        add(externo, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 370, 30, 30));

        jLabel5.setText("Codigo");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, -1, -1));

        nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreActionPerformed(evt);
            }
        });
        add(nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 310, 420, 30));

        jLabel8.setText("Nombre");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 320, 60, -1));

        nuevo.setText("Nuevo");
        add(nuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 420, -1, 40));
    }// </editor-fold>//GEN-END:initComponents

    private void especialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_especialidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_especialidadActionPerformed

    private void codigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoActionPerformed

    private void cedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cedulaActionPerformed

    private void externoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_externoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_externoActionPerformed

    private void nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField activo;
    private javax.swing.JButton almacenar;
    private javax.swing.JTextField cedula;
    private javax.swing.JButton cerrar;
    private javax.swing.JTextField codigo;
    private javax.swing.JTextField especialidad;
    private javax.swing.JTextField externo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField nombre;
    private javax.swing.JButton nuevo;
    private javax.swing.JScrollPane scrollEstudios;
    private javax.swing.JTable tablaEstudios;
    // End of variables declaration//GEN-END:variables

    public void addAlmacenarListener(ActionListener clientesListener) {

        almacenar.addActionListener(clientesListener);
    }

    public void addCerrarListener(ActionListener cancelarListener) {

        cerrar.addActionListener(cancelarListener);
    }

    public void addNuevoListener(ActionListener nuevoListener) {

        nuevo.addActionListener(nuevoListener);
    }

    public void setModeloFeriados(DefaultTableModel dftm) {
        tablaEstudios.setModel(dftm);
    }

    public Integer getUsuarioElegido() {
        
        Integer id = null;
        
        int i = tablaEstudios.getSelectedRow();
        if(i>=0) {

            id = (Integer) tablaEstudios.getValueAt(i, 0);
        }
        
        return(id);
    }

    public void addEstudioElegidoListener(MouseListener estudioElegidoListener) {

        tablaEstudios.addMouseListener(estudioElegidoListener);
    }

    public void setDescripcion(String descripcion) {

        this.especialidad.setText(descripcion);
    }

    public void setTagEstudio(String tagEstudio) {

        this.codigo.setText(tagEstudio);
    }

    public void setPrecioBase(float preciobase) {

        this.activo.setText(preciobase+"");
    }

    public String getDescricpion() {

        return(especialidad.getText());
    }

    public Float getPrecioBase() throws ParseException {

        String txt = activo.getText();

        DecimalFormat df = new DecimalFormat("0.00");

        Number n = df.parse(txt);
        n.floatValue();

        return(Float.parseFloat(txt));
    }


    public void setTipoActividad(String descripcion) {
        codigo.setText(descripcion);
    }

    public void setCargo(String cargo) {
        this.especialidad.setText(cargo);
    }

    public void setFuncionario(Integer funcionario) {
        
        this.cedula.setText(funcionario+"");
    }


    public void setNivel(int nivel) {
        
        this.activo.setText(nivel+"");
    }

    public void setRol(String rol) {
        
        this.externo.setText(rol);
    }

    public void setUsuario(String usuario) {
        
        this.codigo.setText(usuario);
    }

    public void setClave(String clave) {
        
        this.nombre.setText(clave);
    }

    public String getCargo() {
        
        return(this.especialidad.getText());
    }

    public Integer getFuncionario() {
        
        return(Integer.parseInt(this.cedula.getText()));
    }

    public int getNivel() {
        return(Integer.parseInt(this.activo.getText()));
    }

    public String getRol() {
        return(this.externo.getText());
    }

    public String getUsuario() {
        return(this.codigo.getText());
    }

    public String getClave() {
        return(this.nombre.getText());
    }

    public char getActivo() {
        
        return(activo.getText().charAt(0));
    }

    public int getCedula() {
        
        return(Integer.parseInt(cedula.getText()));
    }

    public Integer getCodigo() {
        
        return(Integer.parseInt(codigo.getText()));
    }

    public int getEspecialidad() {
        
        return(Integer.parseInt(especialidad.getText()));
    }

    public Character getExterno() {
        
        return(externo.getText().charAt(0));
    }

    public String getNombre() {
        
        return(nombre.getText());
    }

    public void setActivo(char act) {
        
        activo.setText(act+"");
    }

    public void setCedula(int ced) {
        
        cedula.setText(ced+"");
    }

    public void setCodigo(Integer codigo) {
        
        this.codigo.setText(codigo + "");
    }

    public void setEspecialidad(int especialidad) {
        
        this.especialidad.setText(especialidad + "");
    }

    public void setExterno(Character externo) {
        
        this.externo.setText(externo+ "");
    }

    public void setNombre(String nombre) {
        
        this.nombre.setText(nombre);
    }

}