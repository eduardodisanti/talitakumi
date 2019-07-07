/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.com.medilab.cloud;

import java.io.IOException;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import talitakumi.engine.framework.invoker.ServiceDelegatorRemote;
import talitakumi.engine.framework.invoker.sesionweb.ClientSesion;

/**
 *
 * @author eduardodisanti
 */
public class ConnectionManager {
    
    public void doConnect() throws IOException, NamingException {
        
                ClientSesion.setLogged(false);
                Properties props = new Properties();
                props.load(Thread.currentThread().getContextClassLoader().getResource("jndi.properties").openStream());

                InitialContext ctx = new InitialContext(props);

                ServiceDelegatorRemote sdr = (ServiceDelegatorRemote) ctx.lookup("services.ServiceDelegatorRemote");
                ClientSesion.putVariableEntorno("ServiceDelegator", sdr);

                ClientSesion.setServiceDelegatorRemote(sdr);
                ClientSesion.setLogged(true);
    }
}
