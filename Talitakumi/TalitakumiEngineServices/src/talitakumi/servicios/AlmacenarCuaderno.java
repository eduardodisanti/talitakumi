/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;

/**
 *
 * @author rupertus
 */
public class AlmacenarCuaderno implements AbstractService {

    private EntityManager em;
    private Logger logger;

    public AlmacenarCuaderno() {

        em = Sesion.getEntitymanager();
        logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        Date fecha = new Date();
        String resp = (String) o;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            String filename = "cuaderno"+sdf.format(fecha)+".html";

            FileWriter fw = new FileWriter("/srv/sistemas/talitakumi/" + filename);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(resp);

            bw.close();
            fw.close();
        } catch(Exception ex) {
            System.out.println("ERROR Cargando cuaderno " + ex);
            logger.error("ERROR Cargando cuaderno " + ex);
        }
        //tx.commit();
        
        return(resp);
    }

    public Object getResultado() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
