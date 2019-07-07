/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.odontologo.ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.model.core.Analisis;
import talitakumi.model.core.Carnetdesalud;
import talitakumi.model.core.Enfermedades;
import talitakumi.model.core.Episodioodontologo;
import talitakumi.model.core.EpisodioodontologoPK;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Estudios;
import talitakumi.model.core.Medicos;
import talitakumi.model.core.Pacientes;
import talitakumi.model.core.Personas;
import talitakumi.model.core.Vencimientosdoc;
import talitakumi.odontologo.ui.UIRevisionOdontologica;
import view.DialogoError;


/**
 *
 * @author rupertus
 */
public class RevisionOdontologicaController extends AbstractVisualController {

      private UIRevisionOdontologica uic;
      private Episodios epi, epiant;
      private DefaultTableModel dftm;
      private Medicos medico;
      private Vector <Episodioodontologo> eo;
      private Vector <Enfermedades> enf;
      private Logger logger;
      boolean existepatologiamaligna,
              existenlesionesblancas;
      private int cantidadcariesprofundas;
      private int cantidadextracciones;
      private int plazo;
      private String observaciones;

      Episodioodontologo uneo;
     
      public RevisionOdontologicaController(Episodios e) {

          enf = new Vector();
//          if(e.getEpisodioodontologoCollection().size()==0)
//              eo = new Vector();
//          else

              eo = (Vector<Episodioodontologo>) e.getEpisodioodontologoCollection();
          if(eo==null) {
              eo = new Vector<Episodioodontologo>();
          }
          
          epiant = null;
          try {
            epiant = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodioAnterior", e.getId());
          } catch(Exception ex) {
              System.out.println("No existe episodio anterior - " + ex);
          }
          Carnetdesalud cds = (Carnetdesalud) Sesion.getServiceDelegatorRemote().invoke("CargarCarneDeSalud", e);

          // ATENCION, ESTO SE HACER PORQUE POR ALGUNA RAZON NO SE CARGA EL CARNET DE SALUD
          if(cds!=null) {
              if(epiant != null)
                  if(epiant.getCarnetdesalud()==null)
                    epiant.setCarnetdesalud(cds);
          }
          
          if(epiant!=null) {
              e = copiarEpisodio(epiant, e);
              if(e.getCarnetdesalud()!=null) {
                  plazo = e.getCarnetdesalud().getVigenciaodontologo();
              }
          }
              else {
              plazo = 24;
          }
          cantidadcariesprofundas = cantidadextracciones = 0;
          existepatologiamaligna = existenlesionesblancas = false;

          logger = Sesion.getLogger();
          
          uic = new UIRevisionOdontologica();

          if (epi != null ){

              if(uic.getmeses6()){
                uic.setAlertaUltimaVigencia(true);
              }
              else{
                uic.setAlertaUltimaVigencia(false);
              }
          }

          epi = e;
          uic.setAlmacenarListener(new AlmacenarListener());
          uic.setAgregarListener(new AgregarListener());
          uic.setQuitarListener(new QuitarListener());
          uic.setIdEpisodio(e.getId());

          mostrarDatosPaciente();
          llenarVencimientos(e.getPaciente());
          perpararTablaDiagnosticos();
          mostrarDiagnosticos();
          
          try {
            cargarMedico();
        } catch (Exception ex) {
            DialogoError dlg = new DialogoError(null, "Error interno", "No se pudo cargar el medico", ex.toString(), true);
            dlg.setVisible(true);
            logger.error(ex);
        }

      }

      private Boolean ultimavigencia(){

        Boolean meses6 = uic.getmeses6();
        return(meses6);
    }


    private void llenarVencimientos(Pacientes p) {


        try {
            int difa = 24;

            for(Vencimientosdoc vd : p.getVencimientosdocCollection()) {

                if(vd.getDocumento()==900)  { // TODO - properties
                    Calendar f1 = Calendar.getInstance();
                    Calendar f2 = Calendar.getInstance();

                    f1.setTime(vd.getEmitido());
                    f2.setTime(vd.getVence());

                    difa = (f2.get(Calendar.YEAR) * 12 - f2.get(Calendar.MONTH)) - (f1.get(Calendar.YEAR) * 12 - f1.get(Calendar.MONTH));
                }
            }


            uic.setAlertaUltimaVigencia(difa <= 6);
  
       } catch(Exception ex) {

           System.out.println("Error revision odontologogica : " + ex);
       }
    }
    @Override
    public JPanel getPanel() {
        return(uic);
    }
   
    @Override
    public void guardarDatos() throws Exception {

       modificarEstudioExistente();
       uic.setVisible(false);

    }

    @Override
    public void cerrarTodo() {
        uic.setVisible(false);

    }

    private void calcularPlazos() {

        plazo = 24;

        if((cantidadextracciones == 1 && cantidadcariesprofundas >= 6) ||
           (cantidadcariesprofundas > 6) || 
           (cantidadextracciones >1)) {
            plazo = 6;
        }

        if(existenlesionesblancas || existepatologiamaligna)
            plazo = 0;

        if(plazo==0)
            uic.setNegado(true);
        else
            if(plazo==6)
                uic.setPlazo6(true);
            else
                uic.setPlazo24(true);
    }

    private void cargarMedico() throws Exception {

         medico = (Medicos) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarMedico", Sesion.getNumeromedico());
    }

    private Object cargarResultadoEstudioHecho(Estudios unEstudio, Analisis unAnalisis) {

        Object resultado = null;
      
        return(resultado);
    }

    private void agregarDiagnostico() {

        List<Integer> piezas = uic.getPiezaElegida();
        Integer idxenfelegida = uic.getIndiceEnfermedadElegida();
        boolean lesionblanca = uic.getLesionBlanca();
        boolean patologiamaligna = uic.getPatologiaMaligna();

        Enfermedades unaenf = enf.elementAt(idxenfelegida);

        if(unaenf.getId()==113) {
            piezas.add(999);
        }
        
        EpisodioodontologoPK epk;
        
        for(Integer pieza : piezas) {

            uneo = new Episodioodontologo();
            epk = new EpisodioodontologoPK();
            
            epk.setEnfermedad(unaenf.getId());
            epk.setPieza(pieza);
            epk.setEpisodio(epi.getId());

            uneo.setEnfermedades(unaenf);
            uneo.setMedico(medico); // TODO - Meter el medico o el usuario en el Sesion
            uneo.setEpisodios(epi);
            if(patologiamaligna)
                uneo.setPatologiamaligna('S');
            if(lesionblanca)
                uneo.setLesionblanca('S');

            uneo.setEpisodioodontologoPK(epk);

            eo.add(uneo);

            if(lesionblanca)
                existenlesionesblancas = true;
            if(patologiamaligna)
                existepatologiamaligna = true;
            
            if(unaenf.getId()==24) {
                ++cantidadcariesprofundas;
            } else
                  if(unaenf.getId()==27) {
                  ++cantidadextracciones;
                }
                       
            agregarDiagnosticoEnTabla(pieza, unaenf.getDescripcion(), patologiamaligna, lesionblanca);
        }

        calcularPlazos();
        uic.vaciarListaDientes();
        
    }


    private void agregarDiagnosticoEnTabla(int pieza, String desc, boolean patologiamaligna, boolean lesionblanca) {

        String xpatologiamaligna, xlesionblanca;

        if(lesionblanca)
           xlesionblanca = "X";
        else
           xlesionblanca = "";

        if(patologiamaligna)
           xpatologiamaligna = "X";
        else
           xpatologiamaligna = "";

        ImageIcon img = new ImageIcon(getClass().getResource("/imagenes/piezas/3/"+pieza+".png"));
        dftm.addRow(new Object [] {img, pieza, desc, xpatologiamaligna, xlesionblanca});
    }

    private void quitarDiagnostico() {

        boolean lesionblanca, patologiamaligna;

        Integer idx = uic.getIndiceDiagnosticoElegido();

        if(idx != null && idx >= 0) {

            Enfermedades unaenf;

            unaenf = eo.elementAt(idx).getEnfermedades();

            if(unaenf.getId()==24) {
                --cantidadcariesprofundas;
            } else
                  if(unaenf.getId()==27) {
                      --cantidadextracciones;
                  }
            eo.removeElementAt(idx);
            dftm.removeRow(idx);

            existenlesionesblancas = false;
            existepatologiamaligna = false;
                      
            for(Episodioodontologo unepi : eo) {
              if(unepi.getLesionblanca()=='S')
                existenlesionesblancas = true;
              if(unepi.getPatologiamaligna()=='S')
                existepatologiamaligna = true;
                          
            }
            calcularPlazos();
        }
    }

    private void modificarEstudioExistente() throws Exception {

        if(uic.getmeses6())
            plazo = 6;
        else
             if(uic.getmeses24())
                    plazo = 24;
             else
                if(uic.getNegado())
                    plazo = 0;
        if(epi.getCarnetdesalud()!=null)
           epi.getCarnetdesalud().setVigenciaodontologo(plazo);
         //epi.setEpisodioodontologoCollection(eo);

        if(epi.getTipoepisodio().getId()==15)
            epi.setTerminado('S');
       
        boolean aportacertificado = uic.getAportaCertificado();
        observaciones = uic.getObservaciones();

        System.out.println("observaciones : " +uic.getObservaciones());
        System.out.println("aporta certificado : " +uic.getAportaCertificado());

        if(aportacertificado){
            uneo.setAportacertificado('S');
        }else
            uneo.setAportacertificado('N');

        if(observaciones!=null){
            uneo.setObservaciones(observaciones);
        }
       
        Sesion.getServiceDelegatorRemote().invoke("AlmacenarEpisodioOdontologo", epi);
        
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

    private void mostrarDiagnosticos() {

        eo =   (Vector<Episodioodontologo>) epi.getEpisodioodontologoCollection();

        for(Episodioodontologo unepi : eo) {


            boolean lesionblanca = unepi.getLesionblanca()=='S';
            boolean patologiamaligna = unepi.getPatologiamaligna()=='S';
            
            Enfermedades unaenf = unepi.getEnfermedades();

            EpisodioodontologoPK epk = unepi.getEpisodioodontologoPK();
            epk.setEnfermedad(unaenf.getId());
            int pieza = epk.getPieza();
            String desc = unaenf.getDescripcion();

            agregarDiagnosticoEnTabla(pieza, desc, patologiamaligna, lesionblanca);

            if(unepi.getAportacertificado()==null)
                unepi.setAportacertificado('N');
            if(unepi.getAportacertificado()=='S')
                uic.setAportaCertificado(true);
            if(unepi.getObservaciones()!=null)
                uic.setObservaciones(unepi.getObservaciones());
        
        }

    }
    
    private void perpararTablaDiagnosticos() {
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
        dftm.addColumn("IMG");
        dftm.addColumn("PIEZA");
        dftm.addColumn("DIAGNOSTICO");
        dftm.addColumn("PAT. MALIGNA");
        dftm.addColumn("LESION BLANCA");

        uic.setModeloDiagnosticos(dftm);
        uic.setModeloEnfermedades(cargarModeloEnfermedades());

        uic.setAnchoColumna(0, 45);
        uic.setAnchoColumna(1, 40);
        uic.setAnchoColumna(2, 250);
        uic.setAnchoColumna(3, 95);
        uic.setAnchoColumna(4, 100);
        uic.setAltoFilas(50);
    }

    private DefaultListModel cargarModeloEnfermedades() {

        DefaultListModel tm = new DefaultListModel();

        try {

            enf = (Vector<Enfermedades>) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarEnfermedadesPorFamilia", 10);

            for(Enfermedades e : enf) {

                tm.addElement(e.getDescripcion());
            }
        } catch(Exception e) {

            logger.error(e);
            DialogoError dlg = new DialogoError(null, "Error interno", "No se pudo cargar el medico", e.toString(), true);
            dlg.setVisible(true);
        }
        return(tm);
    }

    public void setMostrarBotonAlmacenar(boolean b) {

        uic.setMostrarBotonAlmacenar(false);
    }

    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void vaciarDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    class AgregarListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {

            agregarDiagnostico();
        }
    }

    class QuitarListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {

            quitarDiagnostico();
        }
    }

    private Episodios copiarEpisodio(Episodios srcEpi, Episodios dstEpi) {

        dstEpi.setPaciente(srcEpi.getPaciente());

        dstEpi.setCliente(srcEpi.getCliente());
        dstEpi.setCarnetdesalud(srcEpi.getCarnetdesalud());
        dstEpi.setFecha(srcEpi.getFecha());
        
        if(eo.isEmpty()) {

           eo = new Vector<Episodioodontologo>();
           for(Episodioodontologo ueo : srcEpi.getEpisodioodontologoCollection()) {
               Episodioodontologo xeo = new Episodioodontologo();
               EpisodioodontologoPK xeopk = new EpisodioodontologoPK();
               xeo.setEpisodioodontologoPK(xeopk);
               xeo.setEnfermedades(ueo.getEnfermedades());
               xeo.getEpisodioodontologoPK().setEnfermedad(ueo.getEpisodioodontologoPK().getEnfermedad());
               xeo.getEpisodioodontologoPK().setEpisodio(srcEpi.getId());
               xeo.getEpisodioodontologoPK().setPieza(ueo.getEpisodioodontologoPK().getPieza());

               xeo.setEpisodios(ueo.getEpisodios());
               xeo.setLesionblanca(ueo.getLesionblanca());
               xeo.setMedico(ueo.getMedico());
               xeo.setPatologiamaligna(ueo.getPatologiamaligna());
               xeo.setAportacertificado(ueo.getAportacertificado());
               xeo.setObservaciones(ueo.getObservaciones());
               eo.add(xeo);
           }
           dstEpi.setEpisodioodontologoCollection(eo);
        }
        return(dstEpi);
    }


}
