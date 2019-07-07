/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.laboratorio.ctrl;

import com.kcreativa.pitosycornetas.KFormattedTextBox;
import java.awt.Color;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.framework.mq.Mensajero;
import talitakumi.laboratorio.ui.UIEstudios;
import talitakumi.model.core.Actividades;
import talitakumi.model.core.Analisis;
import talitakumi.model.core.Analisishechos;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Estudios;
import talitakumi.model.core.Estudioshechos;
import talitakumi.model.core.Pacientes;
import talitakumi.model.core.Personas;

/**
 *
 * @author rupertus
 */
public class EstudiosController extends AbstractVisualController {

      UIEstudios uic;
      Episodios epi;
      DefaultTableModel dftm;
      Vector <Estudios> resultados;
      Vector <Estudioshechos> eh;
      Vector <Estudios> estudios;
      Vector <Analisis> ea;
      boolean creatininemia = false;
      boolean esnuevo = false;
        
      public EstudiosController(Episodios e) {

          e = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodioLaboratorio", e.getId());
          resultados = new Vector<Estudios>();
          uic = new UIEstudios();
          epi = e;
          uic.setAlmacenarListener(new AlmacenarListener());
          uic.setIdEpisodio(e.getId());

          uic.setKeyListener(new EnterListener());

          cargarEstudiosPorActividad();
          mostrarDatosPaciente();
          perpararTablaEstudios();
          mostrarEstudios();
      }

    @Override
    public JPanel getPanel() {
        return(uic);
    }

    public void guardarEstudioNuevo() throws Exception {

        int linea = 0;

        eh = new Vector<Estudioshechos>();

         for(Estudios e : resultados) {
            ++linea;
            Vector <Analisis> ae = (Vector<Analisis>) e.getAnalisisCollection();

            Vector <Analisishechos> listaanalisishechos = new Vector();

            for(Analisis a : ae) {
                 Analisishechos ahecho = new Analisishechos();
                 ahecho.setAnalisis(a);
                 Object o = dftm.getValueAt(linea, 2);
                 String s = "";
                 
                 if(o==null){
                     o = (String) "";
                 }
                 else {
                    s =  o.toString();
                    if(a.getId().equals(5) || a.getId().equals(23))
                         s="LIMPIDO";
                     else
                        s="0";
                 }

                 if(s.equals("false"))
                     s = a.getValorrefminimo();
                 else
                     if(s.equals("true"))
                         s = "POSITIVO";

                 if(a.getId().equals(5) || a.getId().equals(23))
                     if(s.length()<1) {
                         s = "LIMPIDO";
                     }
                 ahecho.setValorhallado(s);
                 ahecho.setValidado(null);
                 ahecho.setEnsuma("");
                 linea++;
                 listaanalisishechos.add(ahecho);
             }

             Estudioshechos unestudiohecho = new Estudioshechos();
             unestudiohecho.setId(null);
             unestudiohecho.setAnalisishechosCollection(listaanalisishechos);
             unestudiohecho.setEstudio(e);
             unestudiohecho.setEpisodio(epi);
             unestudiohecho.setSupervisor(0);

             eh.add(unestudiohecho);
         }
         epi.setEstudioshechosCollection(eh);
     }

    @Override
    public void guardarDatos() throws Exception {

        //if(eh==null || eh.size()==0) {

        guardarEstudioNuevo();
        //}
        modificarEstudioExistente();

        uic.setVisible(false);
    }


    @Override
    public void cerrarTodo() {
        uic.setVisible(false);

    }

    private Object cargarResultadoEstudioHecho(Estudios unEstudio, Analisis unAnalisis) {

        Object resultado = null;

        eh = (Vector<Estudioshechos>) epi.getEstudioshechosCollection();

        if(eh!=null) {
            Integer idx = contieneEstudio(eh, unEstudio);
            if(idx !=null) {
                Estudioshechos unestudiohecho = eh.elementAt(idx);
                Vector <Analisishechos> ah = (Vector<Analisishechos>) unestudiohecho.getAnalisishechosCollection();
                if(ah != null) {
                    Integer idxa = contieneAnalisis(ah, unAnalisis);
                    if(idxa != null) {
                        Analisishechos unah = ah.elementAt(idxa);
                        resultado = unah.getValorhallado();
                    } else {

                        resultado = null;
                    }
                }
            }
         }

        return(resultado);
    }

    private Object cargarEnsumaEstudioHecho(Estudios unEstudio, Analisis unAnalisis) {

        Object resultado = null;

        eh = (Vector<Estudioshechos>) epi.getEstudioshechosCollection();

        if(eh!=null) {
            Integer idx = contieneEstudio(eh, unEstudio);
            if(idx !=null) {
                Estudioshechos unestudiohecho = eh.elementAt(idx);
                Vector <Analisishechos> ah = (Vector<Analisishechos>) unestudiohecho.getAnalisishechosCollection();
                if(ah != null) {
                    Integer idxa = contieneAnalisis(ah, unAnalisis);
                    if(idxa != null) {
                        Analisishechos unah = ah.elementAt(idxa);
                        resultado = unah.getEnsuma();
                    } else {

                        resultado = null;
                    }

                }
            }
         }

        return(resultado);
    }

    private void cargarEstudiosPorActividad() {
          try {

            //estudios =  (Vector<Estudios>)  Sesion.getServiceDelegatorRemote().invoke("ObtenerEstudiosPorActividad", epi.getTipoepisodio().getId());
            estudios =  (Vector<Estudios>)  Sesion.getServiceDelegatorRemote().invoke("ObtenerEstudiosPorEpisodio", epi);

         } catch(Exception e) {
            // - TODO tratar este error
            System.out.println(e);
         }
    }

    private Integer contieneEstudio(Vector<Estudioshechos> eh, Estudios unEstudio) {

        int idx = 0, largo;
        Integer resp = null;
        boolean encontre = false;
        largo = eh.size();

        while(!encontre && idx < largo) {
            if(eh.elementAt(idx).getEstudio().getId().equals(unEstudio.getId())) {
                encontre = true;
                resp = idx;
            }
            else
                ++idx;
        }
        return(resp);
    }

    private Integer contieneAnalisis(Vector<Analisishechos> eh, Analisis unAnalisis) {

        int idx = 0, largo;
        Integer resp = null;
        boolean encontre = false;
        largo = eh.size();

        while(!encontre && idx < largo)
            if(eh.elementAt(idx).getAnalisis().getId().equals(unAnalisis.getId())) {
                encontre = true;
                resp = idx;
            }
            else
                ++idx;

        return(resp);
    }

    private void modificarEstudioExistente() throws Exception {

        int linea = 0;
        float valorcol = 0;
        float valorhdl = 0,
              valorvldl = 0,
              valorldl  = 0,
              valorir   = 0,
              valortrig = 0;
        NumberFormat nf;

         Boolean calcularColesteroles = true;

         for(Estudioshechos estudiohecho : eh) {
             ++linea;
             Vector <Analisishechos> listaanalisishechos = (Vector<Analisishechos>) estudiohecho.getAnalisishechosCollection();

             for(Analisishechos a : listaanalisishechos) {
                 
                 Object o = dftm.getValueAt(linea, 2);
                 String s;

                 if(o==null)
                    s="";
                 else
                    s =  o.toString();

                 String xvalorhallado = s;

                 if(a.getAnalisis().getId().equals(5) || a.getAnalisis().getId().equals(23)) {
                     if(s.equals("3") || s.length() < 2)
                         s = "LIMPIDO";
                    
                 }
               
                 if(a.getAnalisis().getId()==13) {

                     valortrig = Float.parseFloat(xvalorhallado);
                     if(valortrig>=400) {
                         calcularColesteroles = false;
                     }
                 }
                 if(!calcularColesteroles && (a.getAnalisis().getId()==15 || a.getAnalisis().getId()==16 || a.getAnalisis().getId()==17) ) {

                     s="";
                 }

                    if(a.getAnalisis().getId()==14 && Float.parseFloat(xvalorhallado) !=0 )
                        valorhdl = Float.parseFloat(xvalorhallado);
                    if(a.getAnalisis().getId()==2)
                        valorcol = Float.parseFloat(xvalorhallado);
//                    if(a.getAnalisis().getId()==13 && Float.parseFloat(xvalorhallado) !=0 && calcularColesteroles)
//                        valortrig = Float.parseFloat(xvalorhallado);

                     if(a.getAnalisis().getId()==15 && calcularColesteroles) {
                            valorvldl = valortrig / 5;
                            nf = NumberFormat.getNumberInstance();
                            nf.setMaximumFractionDigits(0);
                            nf.setMinimumFractionDigits(0);
                            s =  nf.format(valorvldl);
                     }
                     if(a.getAnalisis().getId()==17 && calcularColesteroles) {
                            valorir = valorcol / valorhdl;
                            nf = NumberFormat.getNumberInstance();
                            nf.setMaximumFractionDigits(1);
                            nf.setMinimumFractionDigits(1);
                            s =  nf.format(valorir);
                     }
                     if(a.getAnalisis().getId()==16 && calcularColesteroles) {
                            valorldl = valorcol - (valorvldl + valorhdl);
                            nf = NumberFormat.getNumberInstance();
                            nf.setMaximumFractionDigits(0);
                            nf.setMinimumFractionDigits(0);
                            s =  nf.format(valorldl);
                     }

                     if(a.getAnalisis().getId()==1) {
                     float valorgli = 0;

                     System.out.println("Entre por glicemia");
                     try {
                       //valorgli = Float.parseFloat(a.getValorhallado());
                       valorgli = Float.parseFloat(xvalorhallado);
                       System.out.println("entra try");
                     } catch(Exception e) {
                       valorgli = 0;
                       System.out.println("Error glicemia = " + e);
                   }
                   // TODO - CREATININEMIA SOLO PARA CARNET DE SALUD, OJO
                        if(valorgli > 1.26 && !creatininemia && epi.getTipoepisodio().getTipoactividad().getId()==2) {

                        System.out.println("entra al primer if");

                        int idact = epi.getTipoepisodio().getId() * 100 + 1;
                        Actividades act = (Actividades) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarActividad", idact);
                        epi.setTipoepisodio(act);

                        Personas p = epi.getPaciente().getPersonas();
                        String txt = "CREATININEMIA " + p.getApellidos() + " " + p.getNombres() + " " + p.getDocumento();
                        new Mensajero().enviar("CONSULTA MEDICA", "laboratorio", txt);
                   }
                 }

                     System.out.println("vuelve al for");
                 a.setValorhallado(s);
                     System.out.println("vuelve 2");
                 a.setEnsuma(uic.getObservaciones());

                     System.out.println("vuelve 3");
                 //System.out.println("Linea = " + linea + " estudiohecho.id = " + estudiohecho.getEstudio().getId());
                 //System.out.println("        valorhallado = " + s);

                 linea++;
                     System.out.println("vuelve 4");

             } // del for de los analisis de los estudios

         System.out.println("continua con el procedimiento");

         epi.setEstudioshechosCollection(eh);
         boolean ok = (Boolean)  Sesion.getServiceDelegatorRemote().invoke("AlmacenarEstudiosHechos", epi);

         System.out.println("SE ALMACENO EL ESTUDIO!!!!!!!!");

         if(!ok) {
             System.out.println("Error en AlmacenarEstudiosHechos");
         }

         } // del for de los estudios

//         System.out.println("continua con el procedimiento") ......etc.

    }

    private void mostrarDatosPaciente() {

        Pacientes pac = epi.getPaciente();
        Personas p = pac.getPersonas();
        uic.setNombrePaciente(p.getNombres());
        uic.setApellidosPaciente(p.getApellidos());
        uic.setFechaNacimiento(p.getFechanacimiento());
        uic.setSexo(p.getSexo());
        uic.setCedula(p.getDocumento());
    }

    private void mostrarEstudios() {

        creatininemia = false;
        int linea = 0;

        int tubo = 0;
        if(epi.getEpisodioextraccionista()!=null) {
            
            tubo = epi.getEpisodioextraccionista().getTubo();
        }
        uic.setTubo(tubo);
        for(Estudios e : estudios) {

            resultados.add(e);
            dftm.addRow(new Object[]{e.getDescripcion(),"","",""});
            ++linea;
            ea = (Vector<Analisis>) e.getAnalisisCollection();
           for(Analisis a : ea) {

               if(a.getId()==20)
                   creatininemia = true;
                 Object valorhallado;
                 String ensuma;

                 //
                 valorhallado = cargarResultadoEstudioHecho(e, a);
                 if(valorhallado == null) {
                            Analisishechos unah = new Analisishechos();
                            unah.setAnalisis(a);
                            unah.setEnsuma("");
                            unah.setId(null);
                            unah.setValorhallado("");
                            unah.setValidado(null);
                            Integer idx = contieneEstudio(eh, e);
                 } //

                 if(a.getTiporesultado()=='T') {
                    valorhallado = new Boolean((String)cargarResultadoEstudioHecho(e, a));
                    if(valorhallado==null)
                        valorhallado = "false";
                  } else 
                      if (a.getTiporesultado()=='L') {
                          //Vector <String> lista = cargarListaOpcionesAnalisis(a);
                          //JComboBox jcb = new JComboBox(lista);
                          valorhallado = new String();
                          valorhallado = (String) cargarResultadoEstudioHecho(e, a);
                          //jcb.setSelectedItem(valorhallado);
                          //valorhallado = jcb;
                      } else {
                         valorhallado = new String();
                         valorhallado = (String) cargarResultadoEstudioHecho(e, a);
                         ensuma = new String();
                         ensuma = (String) cargarEnsumaEstudioHecho(e, a);
                         if(ensuma==null)
                             ensuma = "";
                         if(valorhallado==null)
                             valorhallado="";

                         uic.agreagarObs(ensuma);
                       }
                    dftm.addRow(new Object[]{"", a.getDescripcion(), valorhallado, a.getValorrefminimo() + " - " + a.getValorrefmaximo() + " " + a.getUnidades() + " " + a.getComentrios()});

                    if(a.getTiporesultado()=='T') {
                        JCheckBox checkBox = new JCheckBox();
                        checkBox.setHorizontalAlignment(JLabel.CENTER);
                        DefaultCellEditor checkBoxEditor = new DefaultCellEditor(checkBox);
                        uic.setEditorCheckBoxCelda(linea, checkBoxEditor);
                    } else {
                        if(a.getTiporesultado()=='L') {
                            JComboBox lista = new JComboBox(cargarListaOpcionesAnalisis(a));
                            DefaultCellEditor jlistEditor = new DefaultCellEditor(lista);
                            uic.setEditorListCelda(linea, jlistEditor, cargarListaOpcionesAnalisis(a));
                        } else {
                            KFormattedTextBox ktb = new KFormattedTextBox();
                            ktb.setEditable(true);
                            ktb.setEditando(true);
                            ktb.setSelectionStart(0);
                            ktb.setSelectionEnd(1);
                        try {
                            String mascara = a.getMascara();
                            if(mascara.length()<1) {
                                mascara="#";
                            }
                            ktb.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter(mascara)));
                        } catch (ParseException ex) {
                            Logger.getLogger(EstudiosController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                            ktb.setHorizontalAlignment(JLabel.CENTER);
                            ktb.setForeground(Color.red);
                            DefaultCellEditor ktbEditor = new DefaultCellEditor(ktb);
                            uic.setEditorKTextBoxCelda(linea, ktbEditor);
                    }
                }
               ++linea;
            }
       }
    }
    private void perpararTablaEstudios() {
        dftm  = new DefaultTableModel() {
@Override
      public boolean isCellEditable(int row, int column) {
        boolean flag;
        flag = !dftm.getValueAt(row, 1).equals("");
        if (column == 2 && flag) {
          return true;
        }
        return false;
      }
    };
        dftm.addColumn("EXAMEN");
        dftm.addColumn("ANALISIS");
        dftm.addColumn("V. HALLADO");
        dftm.addColumn("V. REFERENCIA");

        uic.setModeloTareas(dftm);

        uic.setAnchoColumna(0, 200);
        uic.setAnchoColumna(1, 200);
        uic.setAnchoColumna(2, 100);
        uic.setAnchoColumna(3, 350);
    }

    Vector <String> cargarListaOpcionesAnalisis(Analisis a) {


        Vector <String> v = new Vector();

        StringTokenizer st = new StringTokenizer(a.getComentrios(),"|");

        while(st.hasMoreTokens()) {
            v.add(st.nextToken());
        }
        return(v);
    }

    public void setMostrarBotonAlmacenar(boolean b) {
        uic.setMostrarBotonAlmacenar(b);
    }

    @Override
    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void vaciarDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private class EnterListener implements CellEditorListener {

        @Override
        public void editingStopped(ChangeEvent e) {

            System.out.println("Termine de editar ");
        }

        @Override
        public void editingCanceled(ChangeEvent e) {

        }
    }
}
