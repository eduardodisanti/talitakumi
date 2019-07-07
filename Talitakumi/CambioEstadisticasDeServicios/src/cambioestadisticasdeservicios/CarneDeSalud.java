/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cambioestadisticasdeservicios;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.naming.InitialContext;
import talitakumi.engine.framework.DataParameter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import talitakumi.engine.framework.invoker.ServiceDelegatorRemote;
import talitakumi.model.core.Actividades;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Facturas;

/**
 *
 * @author victoria
 */

public class CarneDeSalud {

     // CLAVE cliente.id ; VALOR Objetoparaestadisticas
     Map <Integer,ObjetosParaEstadisticas> mapa;
     Integer b,cuenta_ficha_menor, cuenta_ficha_mayor, cuenta_radicacion, cuenta_particular, cuenta_empresa, cuenta_belloni, cuenta_liga;
     Episodios epi, episodio,episodio_libreta;
     float ingreso_c,valor, valor_c,valor_r, importe, descuento, ingreso2_c, ingreso, ingreso2_r, ingreso_r;
     int cantidad_contado, cantidad_credito, tipo_episodio, a ;
     char factura_tipo;
     Facturas factura;
     Clientes clientes;
     Actividades actividades;


public CarneDeSalud(){


        mapa = new HashMap< Integer, ObjetosParaEstadisticas >();
            System.out.println("Instancio mapa carne de salud");

}

void carne_salud(){

        ServiceDelegatorRemote sdr = null;


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
         Date fechadesde, fechahasta;
         Integer tipo_episodio;
 


            fechadesde = new Date("2010/12/01");
            fechahasta = new Date("2010/12/30");
            tipo_episodio = new Integer(2);


            System.out.println("fechadesde ="+fechadesde);
            System.out.println("fechahasta ="+fechahasta);
            System.out.println("tipo_episodio =" +tipo_episodio);

            dp.set("fechadesde", fechadesde);
            dp.set("fechahasta", fechahasta);
            dp.set("tipo_episodio", tipo_episodio);

          List<Episodios> lepi = (List<Episodios>) sdr.invoke("CargarEpisodiosTipoPorRango", dp);


          System.out.println("lepi_size = " +lepi.size());

            cuenta_particular = 0;
            cuenta_empresa = 0;
            cuenta_belloni = 0;
            cuenta_radicacion =0;
            cuenta_ficha_menor = 0;
            cuenta_ficha_mayor = 0;
            cuenta_liga= 0;

            
            for (Episodios nepi : lepi) {

                    epi = (Episodios) sdr.invoke("CargarEpisodio", nepi.getId() );

                    System.out.println("sdr "+sdr);

                    b = clientes.getId();

                    switch(b){

                        case 1:   particular(epi); break;

                        case 292: liga_universitaria(epi); break;

                        case 294: belloni(epi); break;

                        default: empresa(epi); break;
                    }

                    a = actividades.getId();

                    switch(a){

                        case 3: radicacion(epi); break;

                        case 4: ficha_med_dep_mayor(epi); break;

                        case 7: ficha_med_dep_menor(epi); break;

                    }

        }

System.out.println("episodios empresa : " +cuenta_empresa);
System.out.println("episodios particular : " +cuenta_particular);
System.out.println("episodios radicacion : " +cuenta_radicacion);
System.out.println("episodios ficha_med_dep_menor : " +cuenta_ficha_menor);
System.out.println("episodios ficha_med_dep_mayor : " +cuenta_ficha_mayor);
System.out.println("episodios ficha_liga : " +cuenta_liga);
System.out.println("episodios ficha_belloni : " +cuenta_belloni);


System.out.println("Termine de Agrupar !!!" );

            Iterator clave = mapa.keySet().iterator();

            while(clave.hasNext()) {  // Para iterar en el map episodios

            Integer claveActual = (Integer) clave.next();
            ObjetosParaEstadisticas objo = mapa.get(claveActual);

       }

    } // de carne_salud

ServiceDelegatorRemote sdrr;

//public int getcantidad(){
//
//    System.out.println(" el episodio es : " +epi.getFacturasCollection());
//    System.out.println(" factura es : " +factura);
//
//    cantidad_contado = 0;
//    cantidad_credito = 0;
//
//    factura = (Facturas) sdrr.invoke("CargarFacturasEpisodio", factura.getId());
//
//    for (Facturas factura : epi.getFacturasCollection()){
//
//        factura_tipo = factura.getTipo();
//        if (factura_tipo == 'C' ) cantidad_contado = cantidad_contado++;
//        else cantidad_credito = cantidad_credito++;
//        }
//
//        if (factura.getTipo() == 'C') return (cantidad_contado);
//        else return (cantidad_credito);
//     }


  public float getingreso (){
        // Facturas.importe - Facturas.descuento es el ingreso por cada factura de un episodio.
        ingreso2_c = 0;
        ingreso2_r = 0;

        factura = epi.getFacturas();
//                for ( Facturas factura : epi.getFacturasCollection()){
//
//                factura = (Facturas) sdrr.invoke("CargarFacturasEpisodio", factura.getId());
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
                
//                }

        if (factura.getTipo() == 'C') return (ingreso2_c);
        else return(ingreso2_r);

    }

 public float getvalor (){ //Calcular el valor = facturas.importe.

      System.out.println("el episodio para valor es : " +epi);

//        for ( Facturas facturas : epi.getFacturasCollection()){

//            factura = (Facturas) sdrr.invoke("CargarFacturasEpisodio", factura.getId());

            Facturas facturas = epi.getFacturas();
            importe = facturas.getImporte();
            ingreso = importe;
            factura_tipo = factura.getTipo();
            if (factura_tipo == 'C') valor_c = valor_c + ingreso;
            else valor_r = valor_r + ingreso;
//      }

      if (factura_tipo == 'C') return (valor_c);
      else return (valor_r);
    }

public void empresa(Episodios epi) {
    cuenta_empresa++;
    guardar_en_grupo(epi);
    System.out.println("episodios belloni : " +epi);
 }

 private void belloni(Episodios epi) {
     cuenta_belloni++;
     guardar_en_grupo(epi);
     System.out.println("episodios coet : " +epi);
 }

 private void particular(Episodios epi) {
     cuenta_particular++;
     guardar_en_grupo(epi);
     System.out.println("ewpisodios particular : " +epi);
 }

 private void liga_universitaria(Episodios epi) {
     cuenta_liga++;
     guardar_en_grupo(epi);
     System.out.println("episodios otros : " +epi);
 }

 private void guardar_en_grupo(Episodios epi){
   ObjetosParaEstadisticas obj = mapa.get(epi.getCliente());
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

        mapa.put(epi.getCliente(), obj);
 }

    private void radicacion(Episodios epi) {
        cuenta_radicacion++;
        guardar_en_grupo(epi);

    }

    private void ficha_med_dep_menor(Episodios epi){
        cuenta_ficha_menor++;
        guardar_en_grupo(epi);
    }

    private void ficha_med_dep_mayor(Episodios epi){
        cuenta_ficha_mayor++;
        guardar_en_grupo(epi);
    }
} //  del epil

