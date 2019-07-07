/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cambioestadisticasdeservicios;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.naming.InitialContext;
import talitakumi.engine.framework.DataParameter;
import talitakumi.engine.framework.invoker.ServiceDelegatorRemote;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Facturas;
import talitakumi.model.core.Libretasdeconducir;

/**
 *
 * @author victoria
 */

public class LibretaAmateurPorProcedencia {

Map <Integer, ObjetosParaEstadisticas> mapa;

ServiceDelegatorRemote sdr;

Episodios epi;

Libretasdeconducir libretas = null;

int b,
    cantidad_contado,
    cantidad_credito,
    cuenta_aspirantes,
    cuenta_renovaciones,
    cuenta_otros,
    cuenta_accord,
    cuenta_agraciada,
    cuenta_belfa,
    cuenta_belloni,
    cuenta_centro,
    cuenta_guyer,
    cuenta_isev,
    cuenta_leo,
    cuenta_union,
    cuenta_valencia;

Facturas factura;

char factura_tipo;

float ingreso2_c,
      ingreso2_r,
      importe,
      descuento,
      ingreso_c,
      ingreso_r,
      ingreso,
      valor,
      valor_c,
      valor_r;


public LibretaAmateurPorProcedencia(){

    mapa = new HashMap< Integer, ObjetosParaEstadisticas >();
    System.out.println("Instancio el mapa Libreta Procedencia ");

}

void libreta_amateur_procedencia(){

      sdr = null;


      try {
//           Properties props = new Properties();
//           props.load(new FileInputStream("jndi.properties"));
//           InitialContext ctx = new InitialContext(props);

 Properties props = new Properties();

  props.setProperty("java.naming.factory.initial",
                    "com.sun.enterprise.naming.SerialInitContextFactory");

  props.setProperty("java.naming.factory.url.pkgs",
                    "com.sun.enterprise.naming");

  props.setProperty("java.naming.factory.state",
                    "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");


  // optional.  Defaults to localhost.  Only needed if web server is running
  // on a different host than the appserver
  props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");

  // optional.  Defaults to 3700.  Only needed if target orb port is not 3700.
  props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

  InitialContext ic = new InitialContext(props);
           sdr = (ServiceDelegatorRemote) ic.lookup("services.ServiceDelegatorRemote");
                   System.out.println("entra sdr de libreta profesional" + sdr);
     }
     catch (Exception ex) {
            System.out.println("EXCEPTION libreta profesional" + ex);
            ex.printStackTrace();
          }

          DataParameter dp = new DataParameter();
          Date fechadesde, fechahasta ;
          Integer tipo_episodio;


          fechadesde = new Date("2010/12/01");
          fechahasta = new Date("2010/12/30");
          tipo_episodio = new Integer(1);

          System.out.println("fechadesde = " +fechadesde);
          System.out.println("fechahasta = " +fechahasta);
          System.out.println("tipo_episodio = "+tipo_episodio);

          dp.set("fechadesde", fechadesde);
          dp.set("fechahasta", fechahasta);
          dp.set("tipo_episodio", tipo_episodio);

          cuenta_accord = 0;
          cuenta_agraciada = 0;
          cuenta_belfa = 0;
          cuenta_belloni = 0;
          cuenta_centro = 0;
          cuenta_guyer = 0;
          cuenta_isev = 0;
          cuenta_leo = 0;
          cuenta_union = 0;
          cuenta_valencia = 0;


          List<Episodios> lepi = (List<Episodios>) sdr.invoke("CargarEpisodiosTipoPorRango", dp);

          System.out.println("lepi_size = " +lepi.size());

          for (Episodios libretas_lepi : lepi) {

                    epi = (Episodios) sdr.invoke("CargarEpisodio", libretas_lepi.getId());

                    System.out.println("sdr "+sdr);

                    b = epi.getLibretasdeconducir().getProcedencia();

                    switch ( b ) {

                    case 1 : accord(epi); break;

                    case 2 : agraciada(epi); break;

                    case 3 : belfa(epi); break;

                    case 4 : belloni(epi); break;

                    case 5: centro(epi); break;

                    case 6: guyer(epi); break;

                    case 7: isev(epi);break;

                    case 8: leo(epi); break;

                    case 9: union(epi); break;

                    case 10: valencia(epi); break;
                  
                    }

System.out.println("Termine de Agrupar !!!" );

 Iterator clave = mapa.keySet().iterator();

            while(clave.hasNext()) {  // Para iterar en el map episodios

            Integer claveActual = (Integer) clave.next();
            ObjetosParaEstadisticas objo = mapa.get(claveActual);

       }

           }

           System.out.println("Cantidad de accord es : " +cuenta_accord);
           System.out.println("Cantidad de agraciada es : " +cuenta_agraciada);
           System.out.println("Cantidad de belfa es : " +cuenta_belfa);
           System.out.println("Cantidad de belloni es : " +cuenta_belloni);
           System.out.println("Cantidad de centro es : " +cuenta_centro);
           System.out.println("Cantidad de guyer es : " +cuenta_guyer);
           System.out.println("Cantidad de isev es : " +cuenta_isev);
           System.out.println("Cantidad de leo es : " +cuenta_leo);
           System.out.println("Cantidad de union es : " +cuenta_union);
           System.out.println("Cantidad de valencia es : " +cuenta_valencia);

  }

 public int getcantidad(){

    System.out.println(" el episodio es : " +epi.getFacturas());
    System.out.println(" factura es : " +factura);

    cantidad_contado = 0;
    cantidad_credito = 0;

    //factura = (Facturas) sdr.invoke("CargarFacturasEpisodio", factura.getId());

//    for (Facturas factura: epi.getFacturasCollection()){

        factura = epi.getFacturas();
        factura_tipo = factura.getTipo();
        if (factura_tipo == 'C' )
            cantidad_contado++;
        else
            cantidad_credito++;
//        }
        if (factura.getTipo() == 'C')
            return (cantidad_contado);
        else
            return (cantidad_credito);
     }

 public float getingreso (){
        // Facturas.importe - Facturas.descuento es el ingreso por cada factura de un episodio.
        ingreso2_c = 0;
        ingreso2_r = 0;

//                for ( Facturas factura : epi.getFacturasCollection()){

                factura = epi.getFacturas();
                factura = (Facturas) sdr.invoke("CargarFacturasEpisodio", factura.getId());
                importe = factura.getImporte();
                descuento = factura.getDescuentos();
                factura_tipo = factura.getTipo();

                    if(factura.getTipo()== 'C'){
                    ingreso_c = importe - descuento;
                    ingreso2_c = ingreso2_c + ingreso_c;
                    }
                    else { ingreso_r = importe - descuento;
                    ingreso2_r = ingreso2_r + ingreso_r;
                }
           
//        }

        if (factura.getTipo() == 'C') return (ingreso2_c);
        else return(ingreso2_r);

    }

 public float getvalor (){  //Calcular el valor = facturas.importe.

      System.out.println(" el episodio para valor es : " +epi);

      valor_c = 0;
      valor_r = 0;

      factura = epi.getFacturas();
//      for ( Facturas facturas : epi.getFacturasCollection()){

      //      factura = (Facturas) sdr.invoke("CargarFacturasEpisodio", factura.getId());
            importe = factura.getImporte();
            ingreso = importe;
            factura_tipo = factura.getTipo();
            if (factura_tipo == 'C') valor_c = valor_c + ingreso;
            else valor_r = valor_r + ingreso;
//      }

      if (factura_tipo == 'C') return (valor_c);
      else return (valor_r);
    }

    private void accord(Episodios epi) {
      cuenta_accord++;
      cargar_libreta(epi);
      System.out.println("accord" +epi);
    }

    private void agraciada(Episodios epi) {
        cuenta_agraciada++;
        cargar_libreta(epi);
        System.out.println("agraciada" +epi);

    }

    private void belfa(Episodios epi) {
        cuenta_belfa++;
        cargar_libreta(epi);
        System.out.println("otros" +epi);
    }

    private void belloni(Episodios epi){
        cuenta_belloni++;
        cargar_libreta(epi);
        System.out.println("belloni" +epi);
    }

    private void centro(Episodios epi){
        cuenta_centro++;
        cargar_libreta(epi);

    }

    private void guyer(Episodios epi){
        cuenta_guyer++;
        cargar_libreta(epi);

    }

    private void isev(Episodios epi){
        cuenta_isev++;
        cargar_libreta(epi);

    }

    private void leo(Episodios epi){
        cuenta_leo++;
        cargar_libreta(epi);

    }

    private void union(Episodios epi){
        cuenta_union++;
        cargar_libreta(epi);

    }

    private void valencia(Episodios epi){
        cuenta_valencia++;
        cargar_libreta(epi);
    }
    private void cargar_libreta (Episodios epi){
        ObjetosParaEstadisticas obj = mapa.get(epi.getLibretasdeconducir().getTipo());
        if (obj == null){
                obj=new ObjetosParaEstadisticas();
//                obj.setcantidad_contado(0);
//                obj.setcantidad_credito(0);
                obj.setingreso((float) 0.0);
                obj.setvalor((float) 0.0);
        }

//        obj.setcantidad_contado(getcantidad());
//        obj.setcantidad_credito(getcantidad());
        obj.setingreso(getingreso());
        obj.setvalor(getvalor());

        mapa.put(epi.getLibretasdeconducir().getTipo(), obj);
    }
}
