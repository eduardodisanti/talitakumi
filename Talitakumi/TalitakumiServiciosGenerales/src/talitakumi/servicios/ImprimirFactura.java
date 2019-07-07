/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Date;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.DataParameter;
import talitakumi.framework.Sesion;
import talitakumi.model.core.Facturas;
import talitakumi.model.core.Usuarios;
import talitakumi.services.AbstractService;

/**
 *
 * @author rupertus
 */

public class ImprimirFactura implements AbstractService {

    private Facturas f;
    private String rutcomprador;
    private String nombreempresa;
    private Logger logger;
    private Long numtick;
    private String port = "lpt1:";

    public void setEntityManager(EntityManager em) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void setParameters(Object o) {

        DataParameter dp = (DataParameter) o;
        f = (Facturas) dp.get("factura");
        rutcomprador = (String) dp.get("rutcomprador");
        nombreempresa = (String) dp.get("nombreempresa");
        if(rutcomprador==null) {
            rutcomprador = "(Consumo Final)";
        }
        if(nombreempresa==null) {
            rutcomprador = "(Consumo Final)";
        }
        
        numtick = (Long) dp.get("numtick");
    }

    @Override
    public Object invoke() {

        boolean b = false;
        try {
            b = imprimirFactura();
            b = true;
        } catch (IOException ex) {
            logger.error(ex);
        }
        return(b);
    }

    @Override
    public Object getResultado() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean imprimirFactura() throws IOException {

       boolean result;

       result = true;

       Usuarios u = (Usuarios) Sesion.getVariableEntorno("usuario");
       float importe    = (float) (f.getImporte() / 1.10);
       float descuentos = (float) (f.getDescuentos() / 1.10);
       float iva     = (float) ((importe - descuentos) * 0.1);
       float total   = importe - descuentos + iva;

       NumberFormat nf = NumberFormat.getNumberInstance();
       nf.setMaximumFractionDigits(2);
       nf.setMinimumFractionDigits(2);
       String f1 = nf.format(importe);
       String f2 = nf.format(iva);
       String f3 = nf.format(total);
       String f4 = nf.format(descuentos);

       String nombre = f.getEpisodio().getPaciente().getPersonas().getNombres() + " "  + f.getEpisodio().getPaciente().getPersonas().getApellidos();
       String tipopago;
       //Date fecha;
       
       if(f.getTipo()=='C') {
            tipopago = "CONTADO";
        }
       else {
            tipopago = "CREDITO";
        }

        //DirectPrintToPrinter dp = new DirectPrintToPrinter(port);

        //dp.openDevice();
        //the following line writes to the printer      

        String emisor = "       MEDILAB";
        switch (f.getEmisor()) { // TODO - mejorar este tema es un poco chancho
            
            case 1: emisor = "Gaston Moratorio RUT 214438560011";
                    port = "lpt1:";
                    break;
            case 3: emisor = "Medicina Laboral SRL RUT 215770080015";
                    port = "lpt2:";
                    break;
            case 2: emisor = "Raúl Barañano    RUT --";
                    port = "lpt3:";
                    break;
        }
        
        FileOutputStream stream = new FileOutputStream(port);  
        PrintWriter dp = new PrintWriter(stream);  

        dp.write((char)14);
        dp.println("       MEDILAB");
        dp.println((char)14);
        dp.println(tipopago);
        dp.print((char)15);
        dp.println(emisor);
        dp.write((char)18);
        dp.println("Bvr.Artigas 2180      Tel 2.480.37.34");
        dp.println("IVA al dia              Rollo "+f.getRollo());
        dp.println(" ");
        dp.println("Nro:" + f.getNumero() + " - id:" + f.getEpisodio().getId());

        if(f.getEpisodio().getTipoepisodio().getTipoactividad().getId().equals(1)) {
            dp.println(f.getEpisodio().getPaciente().getPersonas().getDocumento() + " " + nombre);
        }

        dp.println(" ");
        dp.println("Cliente : " + nombreempresa);
        dp.println("RUT Comprador : " + rutcomprador);
        dp.println(" ");
        dp.println("                        Importe");
        dp.println("Servicio Medico         " + f1);
        if(descuentos != 0) {
           dp.println("Descuentos              " + f4);
        }
        dp.println("IVA                     " + f2);
        dp.println(" ");
        dp.println("Total                   " + f3);
        dp.println(" ");
        dp.println(" ");
        dp.println("Funcionario [" + u.getUsuario() + "]");
        dp.println(" ");
        dp.println("F/H " + new Date());
        dp.println(" ");
        dp.println(" ");
        dp.println(" ");
        dp.println(" ");
        dp.println(" ");
        dp.write((char)14);
        dp.println("PIN : " + f.getEpisodio().getId());
        dp.println(" ");

        dp.println("--------------------------------");
        dp.println(" " + port);
        dp.println(" ");
        dp.println(" ");
        dp.println(" ");
        dp.println(" ");
        dp.println(" ");
        dp.println(" ");
        dp.write((char)27);
        dp.write((char)105);
        
        dp.close();
        
        stream = new FileOutputStream(port);  
        dp = new PrintWriter(stream);  
        //dp.closeDevice();
        dp.println("");
        dp.println("--------------------------- " + numtick);
        dp.close();
        
        return(result);
    }
}
