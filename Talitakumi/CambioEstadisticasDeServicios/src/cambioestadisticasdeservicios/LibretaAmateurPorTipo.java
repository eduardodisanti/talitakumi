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

public class LibretaAmateurPorTipo {

Map <Integer, ObjetosParaEstadisticas> mapa;
ServiceDelegatorRemote sdr;
Episodios epi;
Libretasdeconducir libretas = null;
int b, cantidad_contado, cantidad_credito, cuenta_aspirantes, cuenta_renovaciones, cuenta_otros;
Facturas factura;
char factura_tipo;
float ingreso_aux, ingreso_epi, importe, descuento, importe_epi, descuento_epi, ingreso, valor, valor_c, valor_r, ingreso_aspirantes,
        ingreso_renovaciones, ingreso_otros, valor_aspirantes, valor_renovaciones, valor_otros;
ServiceDelegatorRemote sdrr;

public LibretaAmateurPorTipo(){

    mapa = new HashMap< Integer, ObjetosParaEstadisticas >();
    System.out.println("Instancio el map Libretas Tipo");

    }

void libreta_amateur_tipo(){

     sdr = null;
        
       try {

  Properties props = new Properties();

  props.setProperty("java.naming.factory.initial",
                    "com.sun.enterprise.naming.SerialInitContextFactory");

  props.setProperty("java.naming.factory.url.pkgs",
                    "com.sun.enterprise.naming");

  props.setProperty("java.naming.factory.state",
                    "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");

 
  props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");


  props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

  InitialContext ic = new InitialContext(props);
           sdr = (ServiceDelegatorRemote) ic.lookup("services.ServiceDelegatorRemote");
                   System.out.println("entra sdr de libreta amateur por tipo   :  " + sdr);
     }

     catch (Exception ex) {
            System.out.println("**********EXCEPTION libreta profesional" + ex);
            ex.printStackTrace();
          }


         DataParameter dp = new DataParameter();

         Date fechadesde, fechahasta;
         Integer tipo_episodio;



            fechadesde = new Date("2010/12/01");
            fechahasta = new Date("2010/12/30");
            tipo_episodio = new Integer(1);


            System.out.println("fechadesde ="+fechadesde);
            System.out.println("fechahasta ="+fechahasta);
            System.out.println("tipo_episodio = " +tipo_episodio);

            dp.set("fechadesde", fechadesde);
            dp.set("fechahasta", fechahasta);
            dp.set("tipo_episodio", tipo_episodio);

          List<Episodios> lepi = (List<Episodios>) sdr.invoke("AACargarEpisodiosPorTipo", dp);
         
          if(lepi.size()<15){
            System.out.println("lepi = " +lepi);
          }

          System.out.println("lepi_size = " +lepi.size());

          cuenta_aspirantes = 0;
          cuenta_renovaciones = 0;
          cuenta_otros = 0;

          ingreso_aspirantes = 0;
          ingreso_renovaciones = 0;
          ingreso_otros = 0;

          valor_aspirantes = 0;
          valor_renovaciones = 0;
          valor_otros = 0;

          for (Episodios libretas_lepi : lepi) {

                    epi = (Episodios) sdr.invoke("CargarEpisodio", libretas_lepi.getId());

                    System.out.println("sdr "+sdr);

                    b = epi.getLibretasdeconducir().getTipo();

                    switch ( b ) {

                    case 1 : aspirantes(epi); break;

                    case 2 : renovaciones(epi); break;

                    default: otros(epi); break;

                    }

System.out.println("Termine de Agrupar !!!" );

Iterator clave = mapa.keySet().iterator();

            while(clave.hasNext()) {  // Para iterar en el map episodios

            Integer claveActual = (Integer) clave.next();
            ObjetosParaEstadisticas objo = mapa.get(claveActual);
            

       }

           }

           System.out.println("Cantidad de aspirantes es : " +cuenta_aspirantes);
           System.out.println("Cantidad de renovaciones es : " +cuenta_renovaciones);
           System.out.println("Cantidad de otros es : " +cuenta_otros);
           
           System.out.println("Ingreso belloni : " +ingreso_aspirantes);
           System.out.println("Ingreso coet : " +ingreso_renovaciones);
           System.out.println("Ingreso otros : " +ingreso_otros);

           System.out.println("valor belloni : " +valor_aspirantes);
           System.out.println("valor coet : " +valor_renovaciones);
           System.out.println("valor otros : " +valor_otros);

  }

//public int getcantidad(){
//
//    System.out.println(" el episodio es : " +epi.getFacturasCollection());
//    System.out.println(" factura es : " +factura);
//
//    cantidad_contado = 0;
//    cantidad_credito = 0;
//
//    factura = (Facturas) sdr.invoke("CargarFacturasEpisodio", factura);
//
//    for (Facturas factura : epi.getFacturasCollection()){
//
//        factura_tipo = factura.getTipo();
//        if (factura_tipo == 'C' ) cantidad_contado = cantidad_contado++;
//        else cantidad_credito = cantidad_credito++;
//    }
//        if (factura.getTipo() == 'C') return (cantidad_contado);
//        else return (cantidad_credito);
//}

public float getingreso (Episodios epi){
        
        ingreso_epi = 0;
        ingreso_aux = 0;

        //factura = (Facturas) sdrr.invoke("CargarFacturasEpisodio", factura);
        factura = epi.getFacturas();
//        for ( Facturas factura : epi.getFacturasCollection()){

            importe = factura.getImporte();
            descuento = factura.getDescuentos();

            importe_epi = importe_epi + factura.getImporte();
            descuento_epi = descuento_epi + factura.getDescuentos();
            ingreso_epi = importe_epi - descuento_epi;

 //       }

           return(ingreso_epi);


}

public float getvalor (Episodios epi){

        float valor_epi = 0;

        factura = epi.getFacturas();
        //factura = (Facturas) sdrr.invoke("CargarFacturasEpisodio", factura);
//        for (Facturas factura : epi.getFacturasCollection()){

                valor_epi = valor_epi + factura.getImporte();

//           }

           return(valor_epi);
    }

private void aspirantes(Episodios epi) {
      cuenta_aspirantes++;
      cargar_libreta(epi);
      System.out.println("aspirantes" +epi);
    }

private void renovaciones(Episodios epi) {
        cuenta_renovaciones++;
        cargar_libreta(epi);
        System.out.println("renovaciones" +epi);
        
    }

private void otros(Episodios epi) {
        cuenta_otros++;
        cargar_libreta(epi);
        System.out.println("otros" +epi);
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
        obj.setingreso(getingreso(epi));
        obj.setvalor(getvalor(epi));

        mapa.put(epi.getLibretasdeconducir().getTipo(), obj);
    }
}
