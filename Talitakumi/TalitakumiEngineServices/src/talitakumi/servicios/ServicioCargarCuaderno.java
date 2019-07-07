/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.io.BufferedReader;
import java.io.FileReader;
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
public class ServicioCargarCuaderno implements AbstractService {

    private EntityManager em;
    private Logger logger;

    public ServicioCargarCuaderno() {

        em = Sesion.getEntitymanager();
        logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        Date fecha = (Date) o;
        String resp = "";
        try {

            String s, filename;

            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            filename = "cuaderno"+sdf.format(fecha)+".html";
            System.out.println("=============> Cuaderno es : " + filename);
            FileReader fr = new FileReader("/srv/sistemas/talitakumi/" + filename);
            BufferedReader br = new BufferedReader(fr);
            while((s=br.readLine())!=null) {
                resp+=s+"\n";
            }
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
