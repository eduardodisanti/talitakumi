/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UIRecepcion.java
 *
 * Created on 20-nov-2008, 8:18:45
 */

package ui;

import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import ctrl.RecepcionCtrl.CambiarListado;
import ctrl.RecepcionCtrl.CarnetListener;
import ctrl.RecepcionCtrl.LibretaListener;
import ctrl.RecepcionCtrl.ListadoListener;
import ctrl.RecepcionCtrl.RecargarListener;
import ctrl.RecepcionCtrl.SalirListener;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JCheckBox;

/**
 *
 * @author rupertus
 */
public class UIRecepcion extends javax.swing.JFrame {

    HashMap <Integer, JCheckBox> consultorios;
    ActionListener consultoriosListener;
    Integer consultorioelegido;
    /** Creates new form UIRecepcion */
    public UIRecepcion() {
        initComponents();

        fechadesde.setFormats(new SimpleDateFormat("dd-MM-yyyy"));
        fechahasta.setFormats(new SimpleDateFormat("dd-MM-yyyy"));
        tablafacturas.setRowHeight(32);
        tablaagenda.setRowHeight(32);

        consultorios = new HashMap();
        consultorioelegido = 0;

        tablaagenda.setAutoCreateRowSorter(true);

        setExtendedState(getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);
    }

    public void cambiarEstadoConsultorio(Integer consultorio, boolean activo) {

        JCheckBox jcb = consultorios.get(consultorio);
        jcb.setSelected(activo);

    }

    public void crearLaboratorioListener(ActionListener labListener) {

        laboratorio.addActionListener(labListener);
    }

    public void crearPacientesListener(ActionListener pacientesListener) {
        
        pacientes.addActionListener(pacientesListener);
    }

    public Integer getConsultorioElegido() {

        return(consultorioelegido);
    }

    public int getColumnaTablaPacientes() {

        int z = this.tablaagenda.getSelectedColumn();
        
        return(z);
    }
    public int getIndiceTablaPacientes() {

        return(this.tablaagenda.getSelectedRow());
    }

    public void setConsultoriosListener(ActionListener al) {

        consultoriosListener = al;
    }

    public void agregarConsultorio(String descripcion, Integer id) {

        if(!consultorios.containsKey(id)) {
           JCheckBox jcb = new JCheckBox();

           jcb.setText(descripcion);
           jcb.setOpaque(true);
           jcb.setFont(new java.awt.Font("Helvetica", 1, 9));
           jcb.addActionListener(consultoriosListener);
           jcb.addActionListener(new ConsultorioTypeado());
           jcb.setName(id+"");
           this.Consultorios.add(jcb);
           consultorios.put(id, jcb);
        }
    }

    public void crearLibretaProfListener(ActionListener lprListener) {
        lprof.addActionListener(lprListener);
    }

    public void crearCarnetListener(CarnetListener carnetListener) {
        carnet.addActionListener(carnetListener);
    }

    public void crearCondicionesListadoListener(CambiarListado cambiarListado) {
        
        condicionesListado.addActionListener(cambiarListado);
    }

    public void crearListadoDiarioListener(ListadoListener al) {

        listado.addActionListener(al);
    }    

    public void crearFacturaElegidaListener(MouseListener ml) {

        tablafacturas.addMouseListener(ml);
    }

    public void crearPsicotecnicosListener(ActionListener listener) {
        psicotecnicos.addActionListener(listener);
    }

    public void crearVacunasListener(ActionListener listener) {
        vacunas.addActionListener(listener);
    }

    public void crearCopiaCDSListener(ActionListener listener) {
        copiacds.addActionListener(listener);
    }

    public void crearCopiaLaboratorioListener(ActionListener listener) {
        copialab.addActionListener(listener);
    }

    public void crearParaclinicosListener(ActionListener listener) {
        estudios.addActionListener(listener);
    }

    public void crearLibretaListener(LibretaListener libretaListener) {
        libreta.addActionListener(libretaListener);
    }

    public void doMaximize() {
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 48, screenSize.width, screenSize.height - 96);
    }

    public int getCondicionesListado() {

        return(condicionesListado.getSelectedIndex());
    }

    public int getFacturaElegida() {

        int idx = tablafacturas.getSelectedRow();
        Integer ifactura = (Integer) tablafacturas.getValueAt(idx, 1);

        int factura = ifactura;

        return(factura);
    }

    public Date getFechaDesde() {

        return(fechadesde.getDate());
    }

    public Date getFechaHasta() {

        return(fechahasta.getDate());
    }
    
    public void recargarListener(RecargarListener recargarListener) {
        recargar.addActionListener(recargarListener);
    }

    public void salirListener(SalirListener salirListener) {
        salir.addActionListener(salirListener);
    }

    public void setFechaDesde(Date fecha) {
        fechadesde.setDate(fecha);
    }

    public void setFechaHasta(Date fecha) {
        fechahasta.setDate(fecha);
    }

    public void setFuncionario(String usuario) {
        funcionario.setText(usuario);
    }

    public void setHora(String hora) {
        this.hora.setText(hora);
    }

    public void setModelListaAgenda(DefaultTableModel dftm) {

        tablaagenda.setModel(dftm);
    }

    public void setAnchoColumna(int i, int ancho) {

        tablaagenda.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumn col = tablaagenda.getColumnModel().getColumn(i);
        col.setPreferredWidth(ancho);
    }

    public void setAnchoColumnaFacturas(int i, int ancho) {

        tablafacturas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumn col = tablafacturas.getColumnModel().getColumn(i);
        col.setPreferredWidth(ancho);
    }
    public void setModelListaFacturas(DefaultTableModel dftm) {

        tablafacturas.setModel(dftm);
        tablafacturas.setRowHeight(16);

    }

    public void pintarFilaAgenda(int fila, Color color) {


    }

    public void setPacientesListener(MouseListener pacienteElegidoListener) {

        this.tablaagenda.addMouseListener(pacienteElegidoListener);
    }

    public void crearBuscarListener(ActionListener buscarListener) {

        this.tareas.addActionListener(buscarListener);
    }

    public void setPermitirCambiarConsultorios(boolean b) {

        for(JCheckBox j : consultorios.values()) {

            j.setEnabled(b);
        }
    }

    public void resumenDeCajaListener(ActionListener tirillaCajaListener) {

        btnResumenCaja.addActionListener(tirillaCajaListener);
    }

    public void tirillaCajaListener(ActionListener resumenCajaListener) {

        btnTirillaCaja.addActionListener(resumenCajaListener);
    }

    public Object getValorEnXY(int col, int fila) {

        return(this.tablaagenda.getValueAt(col, fila));
    }

    public void crearChequeosListener(ActionListener chequeosListener) {

        chequeos.addActionListener(chequeosListener);
        
    }

    public void cargarAlertas(DefaultTableModel dftm) {

        tablaalertas.setModel(dftm);
    }

    public void setAnchoColumnaAlertas(int i, int ancho) {

        tablaalertas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumn col = tablaalertas.getColumnModel().getColumn(i);
        col.setPreferredWidth(ancho);
    }

    public int getAnchoPantalla() {

        return(this.getWidth());
    }
//    public void crearHigieneListener(ActionListener higieneListener) {
//
//        higiene.addActionListener(higieneListener);
//
//    }

    public void  setEmitirCarnetListener(ActionListener actionListener) {
        
        emitirCarnets.addActionListener(actionListener);
    }
    
    public void setObservacionesTelefonistaListener(ActionListener observacionesTelefonistaListener) {
        
        obstelefono.addActionListener(observacionesTelefonistaListener);
    }

    private class ConsultorioTypeado implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            JCheckBox jcb = (JCheckBox) arg0.getSource();
            consultorioelegido = Integer.parseInt(jcb.getName());

        }
    }

    public void crearConsultoriosInmediatosListener(ActionListener al) {

        inmediatos.addActionListener(al);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        funcionario = new javax.swing.JLabel();
        hora = new javax.swing.JLabel();
        jToolBar2 = new javax.swing.JToolBar();
        Consultorios = new javax.swing.JPanel();
        laboratorio = new javax.swing.JButton();
        pacientes = new javax.swing.JButton();
        inmediatos = new javax.swing.JButton();
        obstelefono = new javax.swing.JButton();
        emitirCarnets = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        libreta = new javax.swing.JButton();
        carnet = new javax.swing.JButton();
        lprof = new javax.swing.JButton();
        psicotecnicos = new javax.swing.JButton();
        tareas = new javax.swing.JButton();
        chequeos = new javax.swing.JButton();
        listado = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnResumenCaja = new javax.swing.JButton();
        btnTirillaCaja = new javax.swing.JButton();
        vacunas = new javax.swing.JButton();
        estudios = new javax.swing.JButton();
        copiacds = new javax.swing.JButton();
        copialab = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        fechadesde = new org.jdesktop.swingx.JXDatePicker();
        fechahasta = new org.jdesktop.swingx.JXDatePicker();
        recargar = new javax.swing.JButton();
        condicionesListado = new javax.swing.JComboBox();
        salir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablafacturas = new com.kcreativa.pitosycornetas.TableIcon();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaalertas = new com.kcreativa.pitosycornetas.TableIcon();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaagenda = new com.kcreativa.pitosycornetas.TableIcon();

        setTitle("Recepcion");
        setUndecorated(true);
        setResizable(false);

        jPanel2.setMaximumSize(new java.awt.Dimension(32767, 64));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(545, 72));

        jLabel4.setText("Funcionario");

        funcionario.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        funcionario.setText("jLabel5");

        hora.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        hora.setText("hh:mm");

        jToolBar2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        Consultorios.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 1, 1));

        laboratorio.setFont(new java.awt.Font("DejaVu Sans", 0, 10)); // NOI18N
        laboratorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/laboratoriomini.png"))); // NOI18N
        laboratorio.setText("Laboratorio");
        laboratorio.setMaximumSize(new java.awt.Dimension(80, 38));
        laboratorio.setMinimumSize(new java.awt.Dimension(80, 38));
        laboratorio.setPreferredSize(new java.awt.Dimension(110, 40));
        Consultorios.add(laboratorio);

        pacientes.setFont(new java.awt.Font("DejaVu Sans", 0, 10)); // NOI18N
        pacientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/personal.png"))); // NOI18N
        pacientes.setText("Pacientes");
        pacientes.setPreferredSize(new java.awt.Dimension(110, 40));
        Consultorios.add(pacientes);

        inmediatos.setFont(new java.awt.Font("DejaVu Sans", 0, 10)); // NOI18N
        inmediatos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/wizard.png"))); // NOI18N
        inmediatos.setText("Inmediatos");
        inmediatos.setMinimumSize(new java.awt.Dimension(95, 44));
        inmediatos.setPreferredSize(new java.awt.Dimension(110, 40));
        Consultorios.add(inmediatos);

        obstelefono.setFont(new java.awt.Font("DejaVu Sans", 0, 10)); // NOI18N
        obstelefono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/telefonistamini.png"))); // NOI18N
        obstelefono.setText("Obs. Teléfono");
        obstelefono.setMinimumSize(new java.awt.Dimension(95, 44));
        obstelefono.setPreferredSize(new java.awt.Dimension(110, 40));
        Consultorios.add(obstelefono);

        emitirCarnets.setFont(new java.awt.Font("DejaVu Sans", 0, 10)); // NOI18N
        emitirCarnets.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fileprint.png"))); // NOI18N
        emitirCarnets.setText("Emisión carne");
        emitirCarnets.setMinimumSize(new java.awt.Dimension(95, 44));
        emitirCarnets.setPreferredSize(new java.awt.Dimension(110, 40));
        Consultorios.add(emitirCarnets);

        jToolBar2.add(Consultorios);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(funcionario, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 669, Short.MAX_VALUE)
                .addComponent(hora, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jToolBar2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1095, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(funcionario)
                    .addComponent(hora, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        libreta.setFont(new java.awt.Font("Lucida Grande", 0, 8)); // NOI18N
        libreta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/libretam.png"))); // NOI18N
        libreta.setText("Libreta de conducir");
        libreta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        libreta.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(libreta);

        carnet.setFont(new java.awt.Font("Lucida Grande", 0, 8)); // NOI18N
        carnet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/carnetm.png"))); // NOI18N
        carnet.setText("Carne de salud");
        carnet.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        carnet.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(carnet);

        lprof.setFont(new java.awt.Font("Lucida Grande", 0, 8)); // NOI18N
        lprof.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/taxi.jpg"))); // NOI18N
        lprof.setText("Libreta prof");
        lprof.setFocusable(false);
        lprof.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lprof.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(lprof);

        psicotecnicos.setFont(new java.awt.Font("Lucida Grande", 0, 8)); // NOI18N
        psicotecnicos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/autobus-rojo.png"))); // NOI18N
        psicotecnicos.setText("Psicotecnicos");
        psicotecnicos.setFocusable(false);
        psicotecnicos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        psicotecnicos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(psicotecnicos);

        tareas.setFont(new java.awt.Font("Lucida Grande", 0, 8)); // NOI18N
        tareas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscarm.png"))); // NOI18N
        tareas.setText("Buscar");
        tareas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tareas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(tareas);

        chequeos.setFont(new java.awt.Font("Lucida Grande", 0, 8)); // NOI18N
        chequeos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/humano.png"))); // NOI18N
        chequeos.setText("Chequeos");
        chequeos.setFocusable(false);
        chequeos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chequeos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(chequeos);

        listado.setFont(new java.awt.Font("Lucida Grande", 0, 8)); // NOI18N
        listado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/listam.png"))); // NOI18N
        listado.setText("Listado diario");
        listado.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        listado.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        listado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listadoActionPerformed(evt);
            }
        });
        jToolBar1.add(listado);

        jPanel3.setMaximumSize(new java.awt.Dimension(100, 100));
        jPanel3.setMinimumSize(new java.awt.Dimension(50, 84));
        jPanel3.setPreferredSize(new java.awt.Dimension(110, 64));
        jPanel3.setLayout(new java.awt.GridLayout(6, 2));

        btnResumenCaja.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        btnResumenCaja.setText("Resumen Caja");
        btnResumenCaja.setFocusable(false);
        btnResumenCaja.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnResumenCaja.setPreferredSize(new java.awt.Dimension(50, 14));
        btnResumenCaja.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnResumenCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResumenCajaActionPerformed(evt);
            }
        });
        jPanel3.add(btnResumenCaja);

        btnTirillaCaja.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        btnTirillaCaja.setText("Tirilla de Caja");
        btnTirillaCaja.setFocusable(false);
        btnTirillaCaja.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTirillaCaja.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel3.add(btnTirillaCaja);

        vacunas.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        vacunas.setText("Vacunas");
        vacunas.setFocusable(false);
        vacunas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        vacunas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel3.add(vacunas);

        estudios.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        estudios.setText("Paraclinicos");
        estudios.setFocusable(false);
        estudios.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        estudios.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel3.add(estudios);

        copiacds.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        copiacds.setFocusable(false);
        copiacds.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        copiacds.setLabel("Copia CDS");
        copiacds.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel3.add(copiacds);

        copialab.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        copialab.setFocusable(false);
        copialab.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        copialab.setLabel("Copia LAB");
        copialab.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel3.add(copialab);

        jToolBar1.add(jPanel3);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(null);

        fechadesde.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jPanel1.add(fechadesde);
        fechadesde.setBounds(0, 10, 110, 25);

        fechahasta.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jPanel1.add(fechahasta);
        fechahasta.setBounds(0, 30, 110, 25);

        recargar.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        recargar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/reload.png"))); // NOI18N
        recargar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(recargar);
        recargar.setBounds(110, 10, 50, 60);

        condicionesListado.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        condicionesListado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sin atender", "Todos", "Atendidos" }));
        jPanel1.add(condicionesListado);
        condicionesListado.setBounds(0, 50, 110, 27);

        salir.setFont(new java.awt.Font("Lucida Grande", 0, 8)); // NOI18N
        salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/exitm.png"))); // NOI18N
        salir.setText("Salir");
        salir.setToolTipText("salir de esta pantalla");
        salir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        salir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(salir);
        salir.setBounds(160, 10, 40, 62);

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(255, 255, 204));
        jTextArea1.setColumns(50);
        jTextArea1.setRows(5);
        jTextArea1.setMinimumSize(new java.awt.Dimension(200, 64));
        jScrollPane1.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(200, 0, 604, 80);

        jToolBar1.add(jPanel1);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_END);

        jSplitPane1.setDividerLocation(250);

        jPanel4.setLayout(new java.awt.GridLayout(2, 0));

        tablafacturas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Paciente", "Factura"
            }
        ));
        tablafacturas.setGridColor(new java.awt.Color(204, 204, 204));
        jScrollPane4.setViewportView(tablafacturas);

        jPanel4.add(jScrollPane4);

        jScrollPane5.setViewportView(tablaalertas);

        jPanel4.add(jScrollPane5);

        jSplitPane1.setLeftComponent(jPanel4);

        jScrollPane2.setViewportView(tablaagenda);

        jSplitPane1.setRightComponent(jScrollPane2);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void listadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_listadoActionPerformed

    private void btnResumenCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResumenCajaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnResumenCajaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Consultorios;
    private javax.swing.JButton btnResumenCaja;
    private javax.swing.JButton btnTirillaCaja;
    private javax.swing.JButton carnet;
    private javax.swing.JButton chequeos;
    private javax.swing.JComboBox condicionesListado;
    private javax.swing.JButton copiacds;
    private javax.swing.JButton copialab;
    private javax.swing.JButton emitirCarnets;
    private javax.swing.JButton estudios;
    private org.jdesktop.swingx.JXDatePicker fechadesde;
    private org.jdesktop.swingx.JXDatePicker fechahasta;
    private javax.swing.JLabel funcionario;
    private javax.swing.JLabel hora;
    private javax.swing.JButton inmediatos;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JButton laboratorio;
    private javax.swing.JButton libreta;
    private javax.swing.JButton listado;
    private javax.swing.JButton lprof;
    private javax.swing.JButton obstelefono;
    private javax.swing.JButton pacientes;
    private javax.swing.JButton psicotecnicos;
    private javax.swing.JButton recargar;
    private javax.swing.JButton salir;
    private com.kcreativa.pitosycornetas.TableIcon tablaagenda;
    private com.kcreativa.pitosycornetas.TableIcon tablaalertas;
    private com.kcreativa.pitosycornetas.TableIcon tablafacturas;
    private javax.swing.JButton tareas;
    private javax.swing.JButton vacunas;
    // End of variables declaration//GEN-END:variables

}
