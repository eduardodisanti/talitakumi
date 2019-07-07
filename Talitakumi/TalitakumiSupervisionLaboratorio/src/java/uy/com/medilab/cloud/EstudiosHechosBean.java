/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.medilab.cloud;

/**
 *
 * @author eduardodisanti
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.DateSelectEvent;
import talitakumi.engine.framework.DataParameter;
import talitakumi.engine.framework.invoker.sesionweb.ClientSesion;
import talitakumi.model.core.Analisis;
import talitakumi.model.core.Analisishechos;
import talitakumi.model.core.Estudioshechos;
import talitakumi.uipojos.laboratorio.UIAnalisisHecho;
import talitakumi.uipojos.laboratorio.UIEstudioHecho;

/**
 *
 * @author eduardodisanti
 */
@ManagedBean
@RequestScoped
public class EstudiosHechosBean implements Serializable {
    
    
    private static List<UIEstudioHecho> estudioshechos;
    private static Date fecha;
    private final short MAXIMO = 2;
    private final short MINIMO = 1;
    
    @PostConstruct
    void initialiseSession() {
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

    public EstudiosHechosBean() {
    
        if(ClientSesion.getLogged()!=true) {
         
            ConnectionManager cm = new ConnectionManager();
            try {
                cm.doConnect();
            } catch(Exception ex) { // TODO solucionar esto
                System.out.println("Error al conectar " + ex);
            }
        }
    }
    
    
    public List<UIEstudioHecho> getEstudios() {
        
        return(estudioshechos);
    }
    
    public void almacenar() {
        
        DataParameter dp = new DataParameter();
        ArrayList<Integer> exa_validados = new ArrayList<Integer>();
        ArrayList<Integer> ana_validados = new ArrayList<Integer>();
        ArrayList<Integer> exa_no_validados = new ArrayList<Integer>();
        ArrayList<Integer> ana_no_validados = new ArrayList<Integer>();
        
        dp.set("examenes_validados", exa_validados);
        dp.set("analisis_validados", ana_validados);
        dp.set("examenes_no_validados", exa_no_validados);
        dp.set("analisis_no_validados", ana_no_validados);
        dp.set("supervisor", 1);
        dp.set("validador", 1);
        
        for(UIEstudioHecho est : estudioshechos) {
            
            boolean todos_validados = true;
            
            for(UIAnalisisHecho ana : est.getAnalisishechos()) {
                
                if(ana.isValidadoOK()) {
                    ana_validados.add(ana.getId());
                } else {
                    ana_no_validados.add(ana.getId());
                    todos_validados = false;
                }
            }
            if(todos_validados) {
                exa_validados.add(est.getId());
            } else {
                exa_no_validados.add(est.getId());
            }
        }
        
        ClientSesion.getServiceDelegatorRemote().invoke("ServicioValidarExamenesLaboratorio", dp);
        cargar();
        
        RequestContext context = RequestContext.getCurrentInstance();  
          //update panel  
          context.update("form:validar"); 
        /*
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        ViewHandler handler = context.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(context, viewId);
        root.setViewId(viewId);
        context.setViewRoot(root);
        
        */
    }
    
    public void cancelar() {
        
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void cargar() {
        
        DataParameter dp = new DataParameter();
        long episodioant = -1;

        if(fecha==null) {
            fecha = new Date();
        }
        dp.set("fecha", fecha);
        dp.set("resultados", null);
        
        estudioshechos = new ArrayList<UIEstudioHecho>();
        List<Estudioshechos> lent = (List<Estudioshechos>) ClientSesion.getServiceDelegatorRemote().invoke("CargarExamenesSinValidar", dp);
        int cuenta = 0;
        for(Estudioshechos ee : lent) {

            UIEstudioHecho uiee = new UIEstudioHecho();
            uiee.setEstudio(ee.getEstudio().getDescripcion());
            uiee.setFecha(ee.getEpisodio().getFecha());
            uiee.setId(ee.getId());
            uiee.setIdepisodio(ee.getEpisodio().getId());
            String nombre = ee.getEpisodio().getPaciente().getPersonas().getApellidos() + " " + ee.getEpisodio().getPaciente().getPersonas().getNombres();
            uiee.setPaciente(nombre);
            uiee.setServicio(ee.getEpisodio().getTipoepisodio().getDescripcion());

            List listaanalisis = new ArrayList<Analisishechos>(); 
            uiee.setAnalisishechos(listaanalisis);
            Estudioshechos este = (Estudioshechos) ClientSesion.getServiceDelegatorRemote().invoke("CargarEstudioHecho", ee.getId());
            //int score = (Integer)ClientSesion.getServiceDelegatorRemote().invoke("ObtenerEstadoExamenesLaboratorio", ee.getEpisodio());
            for(Analisishechos ah : este.getAnalisishechosCollection()) {
                
                UIAnalisisHecho uiah = new UIAnalisisHecho();
                uiah.setAnalisis(ah.getAnalisis().getDescripcion());
                uiah.setIdanalisis(ah.getAnalisis().getId());
                uiah.setId(ah.getId());
                uiah.setIdestudiohecho(ah.getEstudiohecho().getId());
                uiah.setReferencia(ah.getAnalisis().getValorrefminimo() + " - " + ah.getAnalisis().getValorrefmaximo());
                uiah.setValidado("");
                
                int validado = 0;
                if(ah.getValidado() != null) {
                      validado = ah.getValidado().getCodigo();
                }
                int score = obtenerScoringAnalisis(ah);
                
                if(score>=3 || validado!=0) {
                    uiah.setValidadoOK(true);
                    uiah.setColor("#FFFFFF");
                }
                else {
                    uiah.setValidadoOK(false);
                    uiah.setColor("#FF6666");
                }
                                
                uiah.setValorhallado(determinarValor(ah, ah.getValorhallado()));

                uiee.setObservaciones(ah.getEnsuma());
                listaanalisis.add(uiah);
            }
            
            uiee.setMostrar(false);
            if(episodioant != ee.getEpisodio().getId()) {
                uiee.setMostrar(true);
                episodioant = ee.getEpisodio().getId();
                ++cuenta;
                if(cuenta>2) {
                    break;
                }
            }
            estudioshechos.add(uiee);
        }
        
       System.out.println("TERMINE DE AGREGAR ESTUDIOSHECHOS ");
    }
    
    private int obtenerScoringAnalisis(Analisishechos ae) {

        int x = 0;
        float min, max, hallado;

        Analisis a = ae.getAnalisis();
        char disc = a.getTiporesultado();
        String vfm;
        String gvh;
        String numero;

        try {
            switch (disc) {

                case 'T':
                    vfm = a.getValorrefminimo();
                    gvh = ae.getValorhallado();
                    Boolean res = new Boolean(gvh);
                    if (res.booleanValue() == true && a.getId() != 21 && a.getId() != 22) {
                        x = 2;
                        if (a.getId() == 3) {
                            x = 1;
                        }
                    } else {
                        x = 3;
                    }
                    break;

                case 'N':
                    vfm = a.getValorrefminimo();
                    if (vfm.equals("")) {
                        vfm = "0";
                    }
                    gvh = ae.getValorhallado();
                    if (gvh.equals("")) {
                        gvh = "9999999999";
                    }
                    numero = purgar(a.getValorrefmaximo());
                    max = convertirResultado(numero, MAXIMO);
                    //max = Float.parseFloat(numero);
                    numero = purgar(a.getValorrefminimo());
                    min = convertirResultado(numero, MINIMO);
                    //min = Float.parseFloat(numero);
                    numero = purgar(ae.getValorhallado());
                    hallado = convertirResultado(numero, MAXIMO);

                    if (hallado >= min && hallado <= max) {
                        x = 3;
                    } else {
                        x = 2;
                    }
                    if (a.getId() == 1 && hallado >= 1.80) {
                        x = 1;
                    }

                    System.out.println("analisis " + a.getId() + " valor=" + hallado + " con " + x + " rango es : " + min + " y " + max);
                    break;

                case 'L':
                    vfm = a.getValorrefminimo();
                    gvh = ae.getValorhallado();
                    if (vfm.equals(gvh)) {
                        x = 3;
                    } else {
                        x = 2;
                    }
                    break;
            }
        } catch (Exception ex) {
            System.out.println("Error calculando scoring : " + ae.getAnalisis().getId() + " : " + ex);
            x = 0;
        }
        return (x);
    }
    
    private String purgar(String unnumero) {

        int l;

        String numerete = "";
        char c;

        l = unnumero.length();

        for(int i=0; i < l; i++) {

            c = unnumero.charAt(i);
            if(c==',')
                c='.';
            if((c >= 48 && c <= 57) || c=='.')
                numerete += c;
        }

        return(numerete);
    }

    private Float convertirResultado(String numero, short tipo) {

        Float res = null;

        try {
            res = Float.parseFloat(numero);
        } catch(Exception ex) {

            if(tipo==MAXIMO) {
                res = 999999F;
            }
            else {
                res = 0F;
            }
        }

        return(res);
    }
    
    public void handleDateSelect(DateSelectEvent event) {  
        FacesContext facesContext = FacesContext.getCurrentInstance();  
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cargando estudios a partir de :", format.format(event.getDate())));  
        fecha = event.getDate();
        cargar();
    } 

    private String determinarValor(Analisishechos ah, String valorhallado) {
        
        String resp, minimo, comentarios;
        
        resp = valorhallado;
        
        minimo      = ah.getAnalisis().getValorrefminimo();
        comentarios = ah.getAnalisis().getComentrios();
        
        if(ah.getAnalisis().getTiporesultado()=='T') {
            if(valorhallado.equals("false")) {
                resp = minimo;
            } else {
                resp = comentarios;
            }
        }

        if(ah.getAnalisis().getDescripcion().equals("HEXAGON") &&
           valorhallado.equals("false")     ) {
           resp = "-";
        }
        return(resp);
    }
}
