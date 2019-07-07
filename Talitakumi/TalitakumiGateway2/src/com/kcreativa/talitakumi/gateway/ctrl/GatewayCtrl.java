package com.kcreativa.talitakumi.gateway.ctrl;

import com.kcreativa.talitakumi.gateway.ui.UICargarPersona;
import com.kcreativa.talitakumi.gateway.ui.UIGateway;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Analisis;
import talitakumi.model.core.Analisishechos;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Estudios;
import talitakumi.model.core.Personas;
import view.DialogoInformacion;
import talitakumi.model.core.Estudioshechos;
import talitakumi.model.core.Numerador;
import talitakumi.servicios.ObtenerEstudiosPorEpisodio;
import talitakumi.servicios.ServicioCargarClientes;

/**
 *
 * @author rupertus
 */
public class GatewayCtrl {

    private UIGateway uig;
    private List<Clientes> ar;
    private Clientes cliente;
    private Date fecha;
    private ProgresoCargaCtrl pgc;

    private Properties props;
    private EntityManager em;
    private int elcliente;
    private Date fechaAcargar;
    private Episodios epi;

    private boolean preguntar;
      Vector <Estudios> resultados;
      Collection <Estudioshechos> eh;
      Vector <Estudios> estudios;
      Vector <Analisis> ea;

   // static Integer last;
    /**
     * @param args the command line arguments
     */

    private void  conectar() throws Exception {

    System.out.println("CONECTANDO !!! 1");
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TalitakumiEngineModelPU");

    System.out.println("CONECTANDO !!! 2");
        em = emf.createEntityManager();
    System.out.println("CONECTANDO !!! 3");

        Sesion.setEntitymanager(em);

        preguntar = true;
    }

    private void  desconectar() throws Exception {

        em.close();
    }


    public GatewayCtrl() {

        try {

           System.out.println("break 1");
           conectar();
           fecha = new Date();

           Sesion.setEntitymanager(em);
           uig = new UIGateway();
           uig.setElegirListener(new ElegirListener());

           AbstractService as = new ServicioCargarClientes();
           ar = (List<Clientes>) as.invoke(null);

           ArrayList lc = new ArrayList<Clientes>();
           ArrayList xar = new ArrayList<String>();
           for(Clientes c : ar) {
               xar.add(c.getNombre());
               lc.add(c);
           }

           uig.setFecha(fecha);
           uig.setListaClientes(xar);
           uig.setVisible(true);

       }  catch (Exception ex) {
            uig.setVisible(false);
            DialogoInformacion dlgerror = new DialogoInformacion(null, "Error", "Error al iniciar", "Error", true);
            dlgerror.agregarTexto("<br><b>Error</b>"+ex.toString());
            dlgerror.setVisible(true);
          }
    }

    private void archivoElegido() {

        File archivoElegido = uig.getArchivoElegido();
        System.out.println(archivoElegido.getAbsolutePath());

        int idx = uig.getIndiceCliente();
        elcliente = ar.get(idx).getId();
        fechaAcargar = uig.getFechaAcargar();

        try {
           cargrLAB(archivoElegido);
        } catch(Exception ex) {
            System.out.println("Gran cagada en " + ex);
            
        }
    }

    private void cargrLAB(File arch) throws FileNotFoundException, IOException {

        long lineas = 0, errores = 0, oks = 0, noalmacenados = 0;
        FileReader fr = new FileReader(arch);

        BufferedReader br = new BufferedReader(fr);

        String s;
        String xcedula, xSN, xnombre, xfiller, xfiller1, xfiller2, xfecha, xhora,xres, xcantRes;
        

        while((s=br.readLine())!=null) {

            try {
                StringTokenizer st = new StringTokenizer(s, ";");
                xcedula = st.nextToken();

                xSN     = st.nextToken();
                xnombre = st.nextToken();
                xfiller = st.nextToken();
                xfiller1= st.nextToken();
                xfiller2= st.nextToken();
                xfecha  = st.nextToken();
                xhora   = st.nextToken();
                xres    = st.nextToken();
                xcantRes= st.nextToken();

                if(xres.equals("OK") && xnombre.startsWith("-")) {

                    StringTokenizer sst = new StringTokenizer(xcedula, ".");
                    xcedula = sst.nextToken();
                    int documento = Integer.parseInt(limpiar(xcedula));
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
                    try {
                        fecha = sdf.parse(xfecha);
                    } catch(ParseException ex) {
                        System.out.println("Error en FECHA : ====> " + xfecha + ex);
                    }
                    try {
                    epi = cargarEpisodio(fecha, documento);
                    Integer epiid = epi.getId();
                    //Episodios e = (Episodios) new CargarEpisodioLaboratorio().invoke(epiid);
                    //resultados = new Vector<Estudios>();

                    cargarEstudiosPorActividad();
                    eh = epi.getEstudioshechosCollection();

                    int cantres = Integer.parseInt(limpiar(xcantRes));
                        String hdl_resultados = null, trig_resultados = null, col_resultados = null,
                                       vldl_resultados = null, ldl_resultados, indice_resultados;

                    for(int i = 1; i<=cantres;i++) {
                        String xcod = st.nextToken();
                        String xval = st.nextToken();
                        String unit = st.nextToken();

                        Integer cod = interpretarCodigo(xcod);
                        if(cod.equals(2))
                                        col_resultados = xval;
                        if(cod.equals(13))
                                        trig_resultados = xval;
                        if(cod.equals(14))
                                        hdl_resultados = xval;



                        for(Estudios estudio : estudios) {

                            Estudioshechos ee = new Estudioshechos();

                            EntityTransaction atx = em.getTransaction();
                            atx.begin();
                            Numerador numerador = new Numerador();
                            numerador = em.find(Numerador.class, 6);
                            numerador.setValor(numerador.getValor() + 1);
                            em.persist(numerador);
                            em.merge(numerador);
                            em.flush();

                            atx.commit();


                            ee.setId(numerador.getValor());
                            ee.setEpisodio(epi);
                            ee.setEstudio(estudio);

                            ArrayList<Analisishechos> ahc = new ArrayList<Analisishechos>();
                            int agregados = 0;
                            for(Analisis a : estudio.getAnalisisCollection()) {
                                
                                if(a.getId().equals(cod)) {

                                    Analisishechos ah = llenarAnalisisHecho(ee,a, xval);

                                    ahc.add(ah);
                                    ++agregados;
                                }

                                if(a.getId().equals(15) && trig_resultados != null) { // VLDL

                                    float valorvldl = Float.parseFloat(formatearNumero(trig_resultados)) / 5;
                                    NumberFormat nf = NumberFormat.getNumberInstance();
                                    nf.setMaximumFractionDigits(0);
                                    nf.setMinimumFractionDigits(0);
                                    vldl_resultados =  nf.format(valorvldl);

                                    Analisishechos ah = llenarAnalisisHecho(ee,a, vldl_resultados);

                                    ahc.add(ah);
                                    ++agregados;
                                }

                                if(a.getId().equals(16) && (hdl_resultados != null)&& (vldl_resultados != null)) { // LDL

                                    //ldl_resultados = xval;
                                    float valorldl = Float.parseFloat(formatearNumero(col_resultados)) -
                                                    Float.parseFloat(formatearNumero(vldl_resultados))  -
                                                    Float.parseFloat(formatearNumero(hdl_resultados));
                                    NumberFormat nf = NumberFormat.getNumberInstance();
                                    nf.setMaximumFractionDigits(0);
                                    nf.setMinimumFractionDigits(0);
                                    s =  nf.format(valorldl);
                                    ldl_resultados =  nf.format(valorldl);

                                    Analisishechos ah = llenarAnalisisHecho(ee,a, ldl_resultados);

                                    ahc.add(ah);
                                    ++agregados;
                                }

                                if(a.getId().equals(17) && (hdl_resultados != null)) { // INDICE RIESGO COLESTEROL

                                    indice_resultados = "";
                                    float valorindice = Float.parseFloat(formatearNumero(col_resultados)) /
                                                    Float.parseFloat(formatearNumero(hdl_resultados));
                                    NumberFormat nf = NumberFormat.getNumberInstance();
                                    nf.setMaximumFractionDigits(0);
                                    nf.setMinimumFractionDigits(0);
                                    s =  nf.format(valorindice);
                                    indice_resultados =  nf.format(valorindice);

                                    Analisishechos ah = llenarAnalisisHecho(ee,a, indice_resultados);

                                    ahc.add(ah);
                                    ++agregados;
                                }

                                if(a.getId().equals(3)) { // VDRL
                                    Analisishechos ah = llenarAnalisisHecho(ee,a, "NO REACTIVO");

                                    ahc.add(ah);
                                    ++agregados;
                                }

                                if(a.getId().equals(4)) { // VOGEL
                                    
                                    Analisishechos ah = llenarAnalisisHecho(ee,a, "" + randomizar(3,3,1));

                                    ahc.add(ah);
                                    ++agregados;
                                }

                                if(a.getId().equals(5)) { // ASPECTO ORINA
                                    Analisishechos ah = llenarAnalisisHecho(ee,a, "LIMPIDO");

                                    ahc.add(ah);
                                    ++agregados;
                                }

                                if(a.getId().equals(6)) { // PH
                                    Analisishechos ah = llenarAnalisisHecho(ee,a,  "" + randomizar(4,8, 1));

                                    ahc.add(ah);
                                    ++agregados;
                                }

                                if(a.getId().equals(7)) { // DENSIDAD
                                    Analisishechos ah = llenarAnalisisHecho(ee,a,  "" + randomizar(1010, 1030, 5));

                                    ahc.add(ah);
                                    ++agregados;
                                }

                                if(a.getId().equals(8)) { // PROTEINAS
                                    Analisishechos ah = llenarAnalisisHecho(ee,a,  "NEGATIVO");

                                    ahc.add(ah);
                                    ++agregados;
                                }

                                if(a.getId().equals(9)) { // PROTEINAS
                                    Analisishechos ah = llenarAnalisisHecho(ee,a,  "NEGATIVO");

                                    ahc.add(ah);
                                    ++agregados;
                                }

                                if(a.getId().equals(10)) { // LEUCOCITAS
                                    Analisishechos ah = llenarAnalisisHecho(ee,a,  "NEGATIVO");

                                    ahc.add(ah);
                                    ++agregados;
                                }

                                if(a.getId().equals(11)) { // GLUCOSURIA
                                    Analisishechos ah = llenarAnalisisHecho(ee,a,  "NEGATIVO");

                                    ahc.add(ah);
                                    ++agregados;
                                }

                                if(a.getId().equals(12)) { // PIGMENTOS BILIARES
                                    Analisishechos ah = llenarAnalisisHecho(ee,a,  "NEGATIVO");

                                    ahc.add(ah);
                                    ++agregados;
                                }
                            }
                            if(agregados>0)
                                ee.setAnalisishechosCollection(ahc);
                            if(agregados > 0) {
                                if(eh==null)
                                    eh = new ArrayList<Estudioshechos>();
                                eh.add(ee);
                            }
                        }
                    }

                    if(eh.size()>0)
                        epi.setEstudioshechosCollection(eh);
                    //System.out.println("ENTRAMOS nombre es " + xnombre);

                    EntityTransaction tx = em.getTransaction();

                    tx.begin();

                    em.merge(epi);
                    em.flush();
//                    for(Estudioshechos theest : epi.getEstudioshechosCollection()) {
//
//                        theest.setId(numerador.getValor());
//
//                        em.persist(theest);
//                        em.merge(theest);
//                        em.flush();
//                    }
                    tx.commit();

                    try {
                        desconectar();
                        conectar();
                    } catch (Exception ex) {
                        System.out.println("Error en transaccion " + ex);
                    }

                    
                   } catch (NoResultException nrex) {
                       System.out.println("No hay un episodio para la cedula " + xcedula + " " + xnombre);
                       ++errores;
                     }
                }
                ++lineas;
            }  catch (NoSuchElementException ex) {
                System.out.println("Error en formato en linea : " + lineas + " " + s + " ex : " + ex);
                //++errores;
            }
        }

        System.out.println("Terminado, LEIDOS " + oks + " errores " + errores + " sin almacenar " + noalmacenados + " lineas es " + lineas);
    }

    private Personas cargarDatosPersonas(Personas p) {


        UICargarPersona uic = new UICargarPersona(null, true);
        String xfn;

        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        if(p.getFechanacimiento()!=null) {
            xfn = sdf.format(p.getFechanacimiento());
            uic.setFechaNacimiento(xfn);
        } else {
                uic.setFechaNacimiento("");
        }

        uic.setDocumento(p.getDocumento());
        uic.setApellidos(p.getApellidos());
        uic.setNombres(p.getNombres());
        uic.setSexo(p.getSexo());
        
        uic.setVisible(true);

        p.setApellidos(uic.getApellidos());
        p.setNombres(uic.getNombres());
        p.setSexo(uic.getSexo());

        try {
                p.setFechanacimiento(uic.getFechaNacimiento());
        } catch (Exception ex) {
            
        }

        preguntar = !uic.getPreguntar();
        return(p);
    }

    private boolean existeEpisodio(Date fechaAcargar, int documento) {

        Query q = em.createQuery("select e from Episodios e where e.paciente.personas.documento=:doc and e.fecha=:fecha");

        q.setParameter("doc", documento);
        q.setParameter("fecha", fechaAcargar);

        List<Episodios> le = q.getResultList();

        return(le.size() > 0);

    }

    private Episodios cargarEpisodio(Date fechaAcargar, int documento) {

        Query q = em.createQuery("select e from Episodios e where e.paciente.personas.documento=:doc and e.fecha>=:fecha");

        q.setParameter("doc", documento);
        q.setParameter("fecha", fechaAcargar);

        Episodios ep = null;
        ep = (Episodios) q.getSingleResult();

        return(ep);

    }

    private Integer interpretarCodigo(String xcod) {

        Integer resp = null;

        if(xcod.startsWith("COL L")) {
            resp = 2;
        }
        if(xcod.startsWith("GLU L")) {
            resp = 1;
        }
        if(xcod.startsWith("TG")) {
            resp = 13;
        }
        if(xcod.startsWith("HDLplu")) {
            resp = 14;
        }
        if(xcod.startsWith("CRE")) {
            resp = 20;
        }
        if(xcod.startsWith("AUR Lb")) {
            resp = 18;
        }


        return(resp);
    }

    private Analisis cargarAnalisis(Integer cod) {


        Query q = em.createQuery("select e from Analisis e e.id =:cod");

        q.setParameter("cod", cod);

        Analisis a = (Analisis) q.getSingleResult();

        return(a);

    }

    private Analisishechos llenarAnalisisHecho(Estudioshechos ee, Analisis a, String xval) {

        Analisishechos ah = new Analisishechos();
        
        ah.setAnalisis(a);
        ah.setEnsuma("TTGW");
        ah.setEstudiohecho(ee);
        ah.setValidado(null);
        ah.setValorhallado(formatearNumero(xval));

        return(ah);

    }

    private class ElegirListener implements ActionListener {

        public void actionPerformed(ActionEvent ae) {

            archivoElegido();
        }
    }

    private void cerrarVentanaCarga() {

        pgc.cerrarVentana();

        uig.setVisible(false);
    }

    private String limpiar(String x) {

        String res = "";
        x = x.trim();
        for(int i = 0; i<x.length();i++)
            switch(x.charAt(i)) {

                case ' ' :
                        res = res + "0";
                break;
                case ',' :
                        res = res + ".";
                break;

                default :

                    res = res + x.charAt(i);
                break;
            }

       return(res);
    }

    private String formatearNumero(String x) {

        String res = "";
        for(int i = 0; i<x.length();i++)
            switch(x.charAt(i)) {

                case ',' :
                        res = res + ".";
                break;

                default :

                    res = res + x.charAt(i);
                break;
            }

       return(res);
    }

    private class CerrarProgresoListener implements ActionListener {

        public void actionPerformed(ActionEvent ae) {

            cerrarVentanaCarga();
        }

    }

    private void cargarEstudiosPorActividad() {
          try {

            //estudios =  (Vector<Estudios>)  Sesion.getServiceDelegatorRemote().invoke("ObtenerEstudiosPorActividad", epi.getTipoepisodio().getId());
            estudios =  (Vector<Estudios>)  new ObtenerEstudiosPorEpisodio().invoke(epi);

         } catch(Exception e) {
            // - TODO tratar este error
            System.out.println(e);
         }
    }

    private int randomizar(int a, int b, int redondeo) {

        int difa = b - a;
        int resp = a;

        double z = Math.round(Math.random() * difa);
        double resto = z % redondeo;
        if(resto != 0)
            z = resto * redondeo;

        if(z>b)
            z=b;
        resp+=z;

        return(resp);
    }

}