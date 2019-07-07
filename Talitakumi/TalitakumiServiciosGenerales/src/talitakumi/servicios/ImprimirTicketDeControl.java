/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import com.kcreativa.printer.DirectPrintToPrinter;
import java.io.IOException;
import java.util.Date;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.DataParameter;
import talitakumi.framework.Sesion;
import talitakumi.model.core.Usuarios;
import talitakumi.services.AbstractService;

/**
 *
 * @author rupertus
 */

public class ImprimirTicketDeControl implements AbstractService {

    private Logger logger;
    private Long numtick;

    public void setEntityManager(EntityManager em) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void setParameters(Object o) {

        DataParameter dp = (DataParameter) o;
        
        numtick = (Long) dp.get("numtick");
    }

    @Override
    public Object invoke() {

        boolean b = false;
        try {
            b = imprimirTicket();
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

    public boolean imprimirTicket() throws IOException {

       boolean result;

       result = true;

       Usuarios u = (Usuarios) Sesion.getVariableEntorno("usuario");

        DirectPrintToPrinter dp = new DirectPrintToPrinter("lpt1:");

        dp.openDevice();


        dp.writeln(" ");
        dp.writeln(" ");
        dp.writeln(" ");
        dp.write((char)14);
        dp.writeln("TICKET DE CONTROL");
        dp.write((char)14);
        dp.write((char)15);
        dp.writeln("Gaston Moratorio RUT 214438560011");
        dp.write((char)18);

        dp.writeln(" ");
        dp.writeln("Funcionario : " + u.getUsuario());
        dp.writeln(" ");
        dp.writeln("F/H " + new Date());
        dp.writeln(" ");
        dp.writeln("TICKETS " + numtick);
        dp.writeln(" ");

        dp.writeln("--------------------------------");
        dp.writeln(" ");
        dp.writeln(" ");
        dp.writeln(" ");
        dp.writeln(" ");
        dp.writeln(" ");
        dp.writeln(" ");
        dp.write((char)27);
        dp.write((char)105);
        dp.closeDevice();
        
        return(result);
    }
}
