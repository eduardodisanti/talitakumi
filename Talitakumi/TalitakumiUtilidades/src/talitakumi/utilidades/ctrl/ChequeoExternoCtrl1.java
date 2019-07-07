/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.utilidades.ctrl;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import talitakumi.framework.DataParameter;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.model.core.Actividades;
import talitakumi.model.core.Carnetdesalud;
import talitakumi.model.core.Chequeos;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Medicos;
import talitakumi.model.core.Pacientes;
import talitakumi.model.core.Personas;
import talitakumi.model.core.Procedencias;
import talitakumi.servicios.ConvertPersona;
import talitakumi.utilidades.ui.UIChequeoExterno1;
import view.DialogoInformacion;
import view.UIPaciente;

/**
 *
 * @author rupertus
 */
public class ChequeoExternoCtrl1 extends AbstractVisualController {

    private UIChequeoExterno1 panel;
    private List<Procedencias> procedencias;
    private List<Clientes> clientes;
    private HashMap result;
    private String lasterr = "";
    private DefaultTableModel dftmANT;
    private Personas paux;

    public ChequeoExternoCtrl1() throws Exception {

        
        result = null;
        panel = new UIChequeoExterno1();
        UIPaciente uipac = new UIPaciente();
        uipac.setFormatoCedulaLibre();
        uipac.esChequeoExterno();

        uipac.setVisible(true);
        panel.setPanelUtil(uipac);
        panel.setCancelarListener(new CancelarListener());
        panel.setGuardarListener(new AlmacenarListener());
        panel.setCedulaListener(new CedulaListener());
        setListaProcedencias();
        panel.empezarEdicion();
        setListaClientes();
        //setListaExamenes();
        uipac.getVencimientoCds();

        panel.setVisible(true);
                
    }

    private void setListaProcedencias() throws Exception {

        procedencias = (List) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarProcedencias", null);
        panel.setListaProcedencias(procedencias);
        System.out.println("cargó procedencias");
    }

    private void setListaClientes() throws Exception {

        clientes = (List) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarClientes", "S");
        panel.setListaClientes(clientes);
    }

    @Override
    public void cerrarTodo() {

        panel.setVisible(false);
        panel.dispose();
    }

    @Override
    public void guardarDatos() throws Exception { 
        System.out.println("1");
       int cedula = panel.getCedula();
       System.out.println("2");
       Personas p = obtenerPersona(cedula);
       System.out.println("FECHA NACIMIENTO DEFECTO ES  : " +panel.getFechanacimiento());

       Boolean nuevo = false;

        if(p==null) {
           p = new Personas();
           p.setDocumento(cedula);
           p.setId(null);
           nuevo = true;
                    
        }

        p.setApellidos(panel.getApellidos());
        p.setCiudad(panel.getCiudad());
        p.setDocumento(cedula);
        p.setDomicilio(panel.getDomicilio());
        p.setFechanacimiento(calcularNacimientoHoy());
        
//        if(nuevo = true){
// /*ver*/    Date d = calcularNacimientoHoy();
//            System.out.println("!!!!!!!!!!!!FNAC ES   : " +d);
//            p.setFechanacimiento(d);
//        }

        p.setMovil(panel.getMovil());
        p.setNombres(panel.getNombres());
        p.setSexo(panel.getSexo());
        p.setTelefono(panel.getTelefono());


        String fecha = panel.getFecha();
        java.util.Date fechad = null;
        if(validarDatos(p)) {
            Chequeos ldc = new Chequeos();

            Procedencias pr;

            int idx = panel.getProcedenciaElegida();
            pr = procedencias.get(idx);

            Clientes cl;
            
            idx = panel.getClienteELegido();
            cl = clientes.get(idx);

            Episodios e = new Episodios();

            try {
            SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy");
            fechad =sdf.parse(fecha);
            System.out.println("Fecha es : " + fecha);
                        
            Pacientes pac = new Pacientes();
            pac.setPersonas(p);
            pac.setId(p.getId());
            e.setPaciente(pac);
            e.setCliente(cl.getId());
            e.setFecha(fechad);
            Actividades act = (Actividades) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarActividad", 36);
            e.setTipoepisodio(act);
            

            try {
                Medicos m = (Medicos) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarMedico", 0);
                e.setMedico(m);
            }  catch (Exception ex) {
                //logger.error(new Date()+" error llamando ServicioCargarMedico");
                //logger.error(ex);
                System.out.println("Error : " + ex);
            }

            ldc.setEpisodio(e);

            int medico = panel.getMedico();
            int odo = panel.getOdontologo();
            int lab = panel.getLaboratorio();
            int psi = panel.getPsico();
            String vatt = panel.getVacuna();
            String pap = panel.getPAP();
            String mamo = panel.getMamo();
            String foto = panel.getFoto();
            char dictamenapto = panel.getDictamenApto();
            String textlabo = panel.getTextlabo();

            int vigenciai = panel.getVigencia();
            System.out.println("-------------------VIGENCIA : " +vigenciai);

            String vigencia;

            if(vigenciai == 0)
                vigencia= "3";

            else if(vigenciai == 1)
                vigencia = "6";

            else vigencia = "12";

            ldc.setMedico(medico);
            ldc.setLaboratorio(lab);
            ldc.setOdontologo(odo);
            ldc.setPsicologo(psi);
            ldc.setFoto(foto);
            ldc.setDictamenApto(dictamenapto);
            ldc.setTextLabo(textlabo);
            ldc.setVigencia(vigencia);

            java.util.Date vattd,mamod,papd;
            try {
                vattd =sdf.parse(vatt);
            } catch (Exception ex) {
                vattd=null;
            }
            try{
            mamod =sdf.parse(mamo);
            }
            catch (Exception ex){
                mamod=null;
            }
            try{
            papd =sdf.parse(pap);
            }
            catch (Exception ex){
                papd = null;
            }
            ldc.setVacuna(vattd);
            ldc.setMamo(mamod);
            ldc.setPap(papd);

            
            if(panel.getDictamenApto()=='S') {
                ldc.setDictamenApto('S');
                
            }

            else
                ldc.setDictamenApto('N');


            if(panel.getTextlabo()!=null)
                ldc.setTextLabo(textlabo);

            if(panel.getNoCorrespondeLaboratorio()) {
                ldc.setNocorrespondelab("S");
                ldc.setLaboratorio(2);
            }

            else
                ldc.setNocorrespondelab("N");

            if(panel.getNoCorrespondeFoto()) {
                ldc.setNocorrespondefoto("S");
                ldc.setFoto("S");
            }

            else {
                ldc.setFoto(panel.getFoto());
                ldc.setNocorrespondefoto("N");
            }
           
            
            if(panel.getNoCorrespondeOdontologo()) {
                ldc.setNocorrespondeodo("S");
                ldc.setOdontologo(2);
            }

            else
                ldc.setNocorrespondeodo("N"); //ver tabla chequeos

            if(panel.getNoCorrespondePsicologo()) {
                ldc.setNocorrespondepsico("S");
                ldc.setPsicologo(2);
            }

            else
                ldc.setNocorrespondepsico("N");

            }catch (Exception ee) {

                System.out.println("Falta ingresar una fecha  " +ee);
                DialogoInformacion dlg = new DialogoInformacion(null, "ATENCION FALTA INGRESAR FECHA", "ATENCION FALTA INGRESAR FECHA", "Falta completar fecha", true);
                dlg.setVisible(true);
            }
            
            Boolean inmediato = false;
            talitakumi.engine.framework.DataParameter dp = new talitakumi.engine.framework.DataParameter();
            dp.set("actividad", ldc);
            dp.set("inmediato", inmediato);
            dp.set("fecha", fechad);
            dp.set("externo", "S");

            if(!nuevo){
                        paux = (Personas) Sesion.getServiceDelegatorRemote().invoke("ServicioGuardarPersona", p);
            }

            result = (HashMap) Sesion.getServiceDelegatorRemote().invoke("ServicioGuardarChequeoExterno", dp);

            String msg, info;

            if(result == null) {
                msg = "Error interno - no se pudo agendar -";
                info = "Error ChequeoExternoCtrl";
            } else {
                  info = "<h2>Pasado en consultorio " + result.get("consultorio") + "</h2>";
                  msg = "Almacenado correctamente";
               }
            DialogoInformacion dlg = new DialogoInformacion(null, "Resultado de la transaccion", msg, info, true);
            dlg.setVisible(true);
            if(result!=null) {

               //panel.setVisible(false);
               //panel.dispose();
               //emitirTirilla(e);
            }
      } else {
            new DialogoInformacion(null, "Error en los datos", "Falla en los requisitos", "La persona no cumple los requisitos para su categoria<br>"+lasterr, true).setVisible(true);
       }


    }

    @Override
    public JPanel getPanel() {

        return(panel.getPanelUtil());
    }

    public Personas obtenerPersona(int cedula) throws Exception {
        Personas p = null;

        p = (Personas) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarPersona", cedula);
        if(p!=null)
            panel.getHabilitarPapYMamo(p.getSexo());
        System.out.println("5");
        panel.setFechanacimiento("05-06-1981");
        System.out.println("Salgo de obtener persona");
        //panel.setEdad(calcularEdad(p.getFechanacimiento()));
        
        return(p);
    }

    public Object getResult() {

        return(result);
    }

    private void actuarEnPersona() {

        int cedula = panel.getCedula();
        Personas p = null;
        try {
            p = obtenerPersona(cedula);
            
        } catch (Exception ex) {

            panel.setAlwaysOnTop(false);
            new DialogoInformacion(null, "Error buscando persona", "", ex.getMessage(), true).setVisible(true);
            panel.setAlwaysOnTop(true);
        }

        if(p!=null) {
            try {
                DataParameter dp = new DataParameter();
                ConvertPersona conv = new ConvertPersona();
                dp = conv.fromObject(p);

                panel.setPersona(dp);
            } catch(Exception e) {
                //logger.error(new Date() + " " + e);
            }
        }
    }

    private boolean validarDatos(Personas p) {

        Boolean resultado = true;

        Date fdn = p.getFechanacimiento();
        long edad = (new Date().getTime() - fdn.getTime());

        edad = edad / 1000 / 60 / 60 / 24 / 367;

        // TODO - ver que si tiene 17 falte solo un mes para los 18
        
        if(edad > 80 || edad < 18)
            resultado = false;

        return(resultado);
    }

    private boolean validarVigencia(Date vigencialibretaanterior) {

        Date fechahoy = new Date();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(fechahoy);

        gc.add(GregorianCalendar.DATE, 31);
        fechahoy = gc.getTime();

        if(!vigencialibretaanterior.before(fechahoy)) {
            lasterr = "Vigencia libreta anterior en mas de 30 dias ";
        }
        return(vigencialibretaanterior.before(fechahoy));
    }

    @Override
    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Date calcularNacimientoHoy() {

        Calendar hoy = Calendar.getInstance();
        int año = hoy.get(Calendar.YEAR);
        String edad = panel.getEdad();
        int iedad = Integer.parseInt(edad);
        int año_nacimiento = ((año)-(iedad));

        hoy.set(Calendar.YEAR, año_nacimiento);
        hoy.set(Calendar.DATE, hoy.get(Calendar.MONTH) );
        hoy.set(Calendar.DATE, hoy.get(Calendar.DAY_OF_MONTH));

        Date fn = hoy.getTime();

        System.out.println("La fecha de nacimiento es " +fn);

        return(fn);

    }

    private String calcularEdad(Date fechanacimiento) {

        
        Calendar nacimiento = Calendar.getInstance();
        nacimiento.setTime(fechanacimiento);

        GregorianCalendar hoy = new GregorianCalendar();
        
        int añohoy = hoy.get(Calendar.YEAR);
        int añonac = nacimiento.get(Calendar.YEAR);

        int edad = ((añohoy)-(añonac));
                
        System.out.println("EDAD ES " +edad);

        String sedad= edad+"";

        return(sedad);
    }

    public class CedulaListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent arg0) {

        }

        @Override
        public void focusLost(FocusEvent arg0) {

            actuarEnPersona();
        }
    }

    private void crearModeloTablaAntecedentes() {
            dftmANT = new DefaultTableModel();
            dftmANT.addColumn("Id");
            dftmANT.addColumn("Actividad");
            dftmANT.addColumn("Fecha");
            dftmANT.addColumn("Vencimiento CDS");
            dftmANT.addColumn("Vencimiento Odo");

            //panel.setModeloTablaAntecedentes(dftmANT);
    }

    private void cargarAntecedentes(Personas p) {

        crearModeloTablaAntecedentes();
        List<Episodios> le = (List<Episodios>) Sesion.getServiceDelegatorRemote().invoke("ObtenerActividadesDePersona", p);

        String fechax;
        String xVencimientoCDS, xVencimientoODO;
        int linea = 0;
        for(Episodios e : le) {
            Carnetdesalud cds = e.getCarnetdesalud();

            Calendar f1 = Calendar.getInstance();

            if(cds!=null) {
            
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                fechax = sdf.format(e.getFecha());
                                
                f1.setTime(e.getFecha());
                int num = cds.getVigencia();
                if(num==-1)
                    xVencimientoCDS = "NO VISTO";
                    else
                    if(num==0)
                        xVencimientoCDS = "   -";
                    else {
                            f1.add(Calendar.MONTH, num);
                            xVencimientoCDS = sdf.format(f1.getTime());
                    }


                f1.setTime(e.getFecha());
                num = cds.getVigenciaodontologo();
                if(num==-1)
                    xVencimientoODO = "NO VISTO";
                else
                    if(num==0)
                        xVencimientoODO = "NEGADO";
                    else {
                        f1.add(Calendar.MONTH, num);
                        xVencimientoODO = sdf.format(f1.getTime());
                    }

                dftmANT.addRow(new Object[]{e.getId(), e.getTipoepisodio().getDescripcion(), fechax, xVencimientoCDS, xVencimientoODO});
                linea++;
                
                
            }
        }
    }

    private void emitirTirilla(Episodios e) {

        Calendar hoy = Calendar.getInstance();
        Calendar fechaNac = Calendar.getInstance();

        fechaNac.setTime(e.getPaciente().getPersonas().getFechanacimiento());

    	int years = hoy.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
    	if (hoy.get(Calendar.MONTH) < fechaNac.get(Calendar.MONTH)) years --;
    	if (hoy.get(Calendar.MONTH) == fechaNac.get(Calendar.MONTH) &&
    		hoy.get(Calendar.DATE) < fechaNac.get(Calendar.DATE)	) years --;


         HashMap m = new HashMap();

         m.put("fecha", e.getFecha());
         m.put("apellidos", e.getPaciente().getPersonas().getApellidos());
         m.put("nombres", e.getPaciente().getPersonas().getNombres());
         m.put("cedula", e.getPaciente().getPersonas().getDocumento());
         m.put("hora", " ");
         m.put("edad", years);
         m.put("id", (Integer)result.get("episodio"));
         //new Reporter("TirillaLaboratorio.jasper").listar(m);

    }
    public void vaciarDatos() {
        
    }
}
