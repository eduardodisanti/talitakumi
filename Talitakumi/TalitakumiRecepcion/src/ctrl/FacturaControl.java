package ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import talitakumi.cajas.ctrl.TirillaCajaControl;
import talitakumi.engine.framework.DataParameter;
import talitakumi.framework.Sesion;
import talitakumi.framework.controller.AbstractVisualController;
import talitakumi.model.core.Actividades;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Facturas;
import talitakumi.model.core.Rollos;
import talitakumi.model.core.Usuarios;
import talitakumi.services.AbstractService;
import talitakumi.services.ServiceDelegator;
import talitakumi.servicios.Reporter;
import view.DialogoError;
import ui.UIFactura;

/**
 *
 * @author rupertus
 */
public class FacturaControl extends AbstractVisualController {

    UIFactura uif;
    Logger logger;
    Facturas f;
    private int numerofactura;
    List<Clientes> procedencias;
    List<Actividades> actividades;

    public FacturaControl(int numerofactura) {

        this.logger = Sesion.getLogger();
        this.numerofactura = numerofactura;
        cargarFactura();
    }

    @Override
    public void guardarDatos() throws Exception {

    }

    @Override
    public JPanel getPanel() {

        return null;
    }

    @Override
    public void cerrarTodo() {
         uif.setVisible(false);
    }

    private void mostrarFactura() throws Exception {

       float importe    = (float) (f.getImporte() / 1.10);
       //float descuentos = (float) (f.getDescuentos() / 1.10);
       float descuentos = f.getDescuentos();
       float iva     = (float) ((importe) * 0.1);
       float total   = importe - descuentos + iva;

       if(total < 0) {
            total = 0;
        }

       NumberFormat nf = NumberFormat.getNumberInstance();
       nf.setMaximumFractionDigits(2);
       nf.setMinimumFractionDigits(2);
       String f1 = nf.format(importe);
       String f2 = nf.format(iva);
       String f3 = nf.format(total);

        int cliente = f.getEpisodio().getCliente();
        if(cliente==1 || cliente == 373 || cliente == 0) {   // TODO - SOLUCIONAR MALO
          
            uif.setContadoHabilitado(false); //deshabilita la opcion credito
            uif.setContado(true);   //habilita
            uif.setEmitirTicket(true);
            uif.setRut("");
        } else {
            uif.setContadoHabilitado(true);
            uif.setContado(false);
            uif.setEmitirTicket(false);
          }
        
       uif.setNumero(f.getNumero());
       uif.setIva(f2);
       uif.setImporte(f1);
       uif.setTotal(f3);

       Boolean b = (Boolean)Sesion.getVariableEntorno("Cajeros");
       uif.habilitarEmitir(b);

       int actividad = f.getEpisodio().getTipoepisodio().getId();
       String detalleactividad;

              detalleactividad = obtenerNombreActividad(actividad);

              uif.setConcepto(detalleactividad);

              int indiceprocedencia = uif.getProcedenciaElegida();
              Clientes proc = procedencias.get(indiceprocedencia);

              uif.setRut(proc.getRut());
              
                  if(uif.isEntregar()) {

                    boolean paga = uif.getPaga();
                    //uif.dispose();

                    if(!paga) {
                      f.setTipo('C');
                      f.setComentario(uif.getMotivo());
                    } else {
                      f.setTipo('R');
                      f.setComentario(uif.getMotivo());
                    }

                    f.setDescuentos(uif.getDescuentos());
                    f.setEntregado("S");
                    f.setFechaentregado(new Date());
                }

    }

    private void setListaProcedencias() throws Exception {

        procedencias = (List) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarClientes", "S");
        uif.setListaProcedencias(procedencias);

    }

    private void setListaActividades() throws Exception {

        actividades = (List) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarActividades", f.getEpisodio().getTipoepisodio().getTipoactividad().getId());
        uif.setListaActividades(actividades);
    }

   private void cargarFactura() {

       Date hoy = null;


       // TODO tiene que ser un servicio


       f = (Facturas) Sesion.getServiceDelegatorRemote().invoke("CargarUnaFactura", numerofactura);

       uif = new UIFactura(null, true);

       try {
            setListaProcedencias();
            int cl = f.getEpisodio().getCliente();

            int idx = cargarIndiceProcedencia(cl);
            uif.setProcedenciaActual(idx);

            setListaActividades();

            int act = f.getEpisodio().getTipoepisodio().getId();

            int idxact = cargarIndiceActividad(act);
            uif.setActividadActual(idxact);

       } catch(Exception ex) {

           System.out.println("Error " + ex);
       }
       uif.setCambiarDescuentosListener(new CambiarDescuentosListener());
       uif.setCambiarProcedenciasListener(new CambiarProcedenciasListener());
       uif.setCambiarActividadesListener(new CambiarActividadesListener());

       try {
            mostrarFactura();
            //uif.setVisible(true);

            int actividad = f.getEpisodio().getTipoepisodio().getId();
            String detalleactividad;

              detalleactividad = obtenerNombreActividad(actividad);

              uif.setConcepto(detalleactividad);

              uif.setVisible(true);

              int indiceprocedencia = uif.getProcedenciaElegida();
              Clientes proc = procedencias.get(indiceprocedencia);
              if(uif.getPendiente()) {
                    f.setComentario(uif.getMotivo());
                    f.setEntregado("N");
                    Sesion.getServiceDelegatorRemote().invoke("AlmacenarFacturaFacturada", f);
              } else {
                  if(uif.isEntregar()) {

                    boolean paga = uif.getPaga();
                    //uif.dispose();

                    if(!paga) {
                      f.setTipo('C');
                      f.setComentario(uif.getMotivo());
                    } else {
                      f.setTipo('R');
                      f.setComentario(uif.getMotivo());
                    }

                    f.setDescuentos(uif.getDescuentos());
                    f.setEntregado("S");
                    f.setFechaentregado(new Date());

                    Rollos rollo = (Rollos) Sesion.getServiceDelegatorRemote().invoke("ServicioObtenerRollo", f.getEmisor());

                    //EntityTransaction tx = em.getTransaction();

                    Integer num;
                    Boolean debeImprimir = uif.getDebeImprimir();
                    if(uif.getNumeroFacturaManual()==0 || debeImprimir) {
                        int numerator;
                      if(f.getEmisor()==1) {
                          numerator = 3;
                      } else {
                          numerator = 7;
                       }
                      num = (Integer) Sesion.getServiceDelegatorRemote().invoke("ServicioNumerador", numerator);
                      f.setRollo(rollo.getId());
                    } else {
                            num = uif.getNumeroFacturaManual();
                            f.setRollo(0);
                      }
                    f.setNumero(num);
                    f.setFuncionario((Usuarios) Sesion.getVariableEntorno("usuario"));
                    f.setSerie(""); // TODO ponerlo en el numerador o una propertry


                    Sesion.getServiceDelegatorRemote().invoke("AlmacenarFacturaFacturada", f);

                    f.getEpisodio().setCliente(proc.getId());
                    Sesion.getServiceDelegatorRemote().invoke("ServicioAlmacenarEpisodio", f.getEpisodio());
                    if(debeImprimir) {
                        String rutcomprador = uif.getRUTComprador();
                        String nombreempresa = uif.getNombreEmpresa();
                        imprimirFactura(f, rutcomprador, nombreempresa);
                    }
//                    if(f.getEpisodio().getTipoepisodio().getTipoactividad().getId()!=1) {
//                        emitirTirilla(f);
//                    }
                }
            }

        } catch (Exception e) {

                DialogoError de = new DialogoError(null, "Error fatal", "No se pudo grabar la factura", e.toString(), true);
                de.setVisible(true);
        }
      
   }

    private void emitirTirilla(Facturas f) {

        Episodios e = f.getEpisodio();
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
         m.put("id", e.getId());
         new Reporter("TirillaLaboratorio.jasper").listar(m);

       
   }
    private String obtenerNombreActividad(int actividad) throws Exception {

        String nombre;
        Actividades act;

        act = (Actividades) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarActividad", actividad);

        nombre = act.getDescripcion();
        return(nombre);
    }

    private void imprimirFactura(Facturas f, String rut, String nombreempresa) throws Exception {

        DataParameter dp = new DataParameter();
        dp.set("factura", f);
        dp.set("rutcomprador", rut);
        dp.set("nombreempresa", nombreempresa);
        
        dp.set("numtick", cargarNumeroTicket());
        
        AbstractService s = new ServiceDelegator().getService("ImprimirFactura");
        s.setParameters(dp);
        s.invoke();
    }

    private void cambiarDescuentos() throws Exception {

        f.setDescuentos(uif.getDescuentos());
        if(f.getImporte() < f.getDescuentos())
            f.setDescuentos(f.getImporte());
        mostrarFactura();
    }

    private int cargarIndiceProcedencia(int clid) {

        int i = 0, idx = 0;
        for(Clientes cl : procedencias) {

            if(cl.getId().equals(clid)) {
                idx = i;
                break;
            } else
                i++;
        }

        return(idx);
    }

    private int cargarIndiceActividad(int clid) {

        int i = 0, idx = 0;
        for(Actividades cl : actividades) {

            if(cl.getId().equals(clid)) {
                idx = i;
                break;
            } else
                i++;
        }

        return(idx);
    }

    @Override
    public void setActividad(String descripcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Long cargarNumeroTicket() {
        
        Long numero = (Long) Sesion.getServiceDelegatorRemote().invoke("CargarCantidadTicketsPorFecha", new Date());
        
        return(numero);
    }

    @Override
    public void vaciarDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private class CambiarDescuentosListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent arg0) {

        }

        @Override
        public void focusLost(FocusEvent arg0) {
            try {
                cambiarDescuentos();
            } catch(Exception ex) {
                
            }
        }
    }

    private class CambiarProcedenciasListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            try {
                    int indiceprocedencia = uif.getProcedenciaElegida();
                    Clientes proc = procedencias.get(indiceprocedencia);
                    f.getEpisodio().setCliente(proc.getId());

                    float preciobase = f.getEpisodio().getTipoepisodio().getPrecio();
                    DataParameter dp = new DataParameter();
                    dp.set("cliente", f.getEpisodio().getCliente());
                    dp.set("actividad", f.getEpisodio().getTipoepisodio().getId());

                    Float precio = (Float) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarPrecioXactividad", dp);
                    if(precio== null || precio==0F) {
                        precio = preciobase;
                    }
                    f.setImporte(precio);

                    mostrarFactura();

            } catch(Exception ex) {
                
            }
        }
    }

    private class CambiarActividadesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            try {
                    int indiceact = uif.getActividadElegida();
                    Actividades act = actividades.get(indiceact);
                    f.getEpisodio().setTipoepisodio(act);
                    float preciobase = act.getPrecio();
                    DataParameter dp = new DataParameter();
                    dp.set("cliente", f.getEpisodio().getCliente());
                    dp.set("actividad", act.getId());

                    Float precio = (Float) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarPrecioXactividad", dp);
                    if(precio== null || precio==0F) {
                        precio = preciobase;
                    }
                    f.setImporte(precio);
                    mostrarFactura();

            } catch(Exception ex) {

            }
        }
   }

}
