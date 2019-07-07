/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cambioestadisticasdeservicios;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.naming.InitialContext;
import talitakumi.engine.framework.DataParameter;
import java.util.Iterator;
import java.util.List;
import talitakumi.engine.framework.invoker.ServiceDelegatorRemote;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Facturas;

/**
 *
 * @author victoria
 */
public class LibretaProfesional {
     
     Map <Integer,ObjetosParaEstadisticas> mapa; // CLAVE cliente.id ; VALOR Objetoparaestadisticas
     Integer b, contador_belloni, contador_coet, contador_particular, contador_otros;
     Episodios epi;
     float ingreso_epi,  importe,descuento, descuento_epi, ingreso_aux, importe_epi, ingreso_belloni,ingreso_otros, ingreso_coet,ingreso_particular, valor_belloni, valor_coet, valor_particular, valor_otros;
     ServiceDelegatorRemote sdrr;
     Facturas factura;

    public LibretaProfesional(){
      
        mapa = new HashMap< Integer, ObjetosParaEstadisticas >();
            System.out.println("Instancio el mapa Libreta Profesional");

    }

    void epil(){

        ServiceDelegatorRemote sdr = null;

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
        System.out.println("fechadesde ="+fechadesde);
     fechahasta = new Date("2010/12/30");
        System.out.println("fechahasta ="+fechahasta);
     tipo_episodio = new Integer(8);
        System.out.println("tipo_episodio = " +tipo_episodio);

     dp.set("fechadesde", fechadesde);
     dp.set("fechahasta", fechahasta);
     dp.set("tipo_episodio", tipo_episodio);
        
        
     List<Episodios> lepi = (List<Episodios>) sdr.invoke("AACargarEpisodiosPorTipo", dp);


     if(lepi.size()<15){
        System.out.println("lepi = " +lepi);
     }

     System.out.println("lepi_size = " +lepi.size());

     contador_belloni = 0;
     contador_coet = 0;
     contador_particular = 0;
     contador_otros = 0;

     ingreso_belloni = 0;
     ingreso_particular = 0;
     ingreso_coet = 0;
     ingreso_otros = 0;

     valor_belloni = 0;
     valor_coet = 0;
     valor_particular = 0;
     valor_otros = 0;
     
     for (Episodios nepi : lepi) {
        
        epi = (Episodios) sdr.invoke("CargarEpisodio", nepi.getId() );
        
        System.out.println("sdr "+sdr);
             
        b = epi.getCliente();

        switch ( b ) {

        case 294 : belloni(epi); break;

        case 332 : coet(epi); break;

        case 1 : particular(epi); break;

        default : otros(epi); break;

        }
           
     }

     System.out.println("episodios belloni : " +contador_belloni);
     System.out.println("episodios coet : " +contador_coet);
     System.out.println("episodios particular : " +contador_particular);
     System.out.println("episodios otros : " +contador_otros);

     Iterator clave = mapa.keySet().iterator();

     while(clave.hasNext()) {  // Para iterar en el map episodios
         
     Integer claveActual = (Integer) clave.next();
     ObjetosParaEstadisticas objo = mapa.get(claveActual);

     }    

     System.out.println("Ingreso belloni : " +ingreso_belloni);
     System.out.println("Ingreso coet : " +ingreso_coet);
     System.out.println("Ingreso particular : " +ingreso_particular);
     System.out.println("Ingreso otros : " +ingreso_otros);

     System.out.println("valor belloni : " +valor_belloni);
     System.out.println("valor coet : " +valor_coet);
     System.out.println("valor particular : " +valor_particular);
     System.out.println("valor otros : " +valor_otros);


    }

    public float getingreso (Episodios epi){
        
        ingreso_epi = 0;
        ingreso_aux = 0;

        //factura = (Facturas) sdrr.invoke("CargarFacturasEpisodio", factura);
        factura = epi.getFacturas();
        //for ( Facturas factura : epi.getFacturasCollection()){

            importe = factura.getImporte();
            descuento = factura.getDescuentos();
                      
            importe_epi = importe_epi + factura.getImporte();
            descuento_epi = descuento_epi + factura.getDescuentos();
            ingreso_epi = importe_epi - descuento_epi;

        //}

           return(ingreso_epi);
    }


    public float getvalor (Episodios epi){

     
       float valor_epi = 0;
          
        //factura = (Facturas) sdrr.invoke("CargarFacturasEpisodio", factura);
//        for (Facturas factura : epi.getFacturasCollection()){

       factura = epi.getFacturas();
                
       valor_epi = valor_epi + factura.getImporte();
                
//           }

       return(valor_epi);
    }

public void belloni(Episodios epi) {
    contador_belloni++;
    guardar_en_grupo(epi);
    ingreso_belloni = ingreso_belloni + getingreso(epi);
    valor_belloni = valor_belloni + getvalor(epi);
    
}

 private void coet(Episodios epi) {
     contador_coet++;
     guardar_en_grupo(epi);
     ingreso_coet = ingreso_coet + getingreso(epi);
     valor_coet = valor_coet + getvalor(epi);
     
 }

 private void particular(Episodios epi) {
     contador_particular++;
     guardar_en_grupo(epi);
     ingreso_particular = ingreso_particular + getingreso(epi);
     valor_particular = valor_particular + getvalor(epi);
     
 }

 private void otros(Episodios epi) {
     contador_otros++;
     guardar_en_grupo(epi);
     ingreso_otros = ingreso_otros + getingreso(epi);
     valor_otros = valor_otros + getvalor(epi);
     
 }

 private void guardar_en_grupo(Episodios epi){
   
 
   ObjetosParaEstadisticas obj = mapa.get(epi.getCliente());

            if (obj == null){
                obj=new ObjetosParaEstadisticas();
                obj.setingreso((float)0);
                obj.setvalor((float) 0);
            }

            obj.setingreso(getingreso(epi));

            obj.setvalor(getvalor(epi));
                
            mapa.put(epi.getCliente(), obj);

  }


}

