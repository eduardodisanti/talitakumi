/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kcreativa.talitakumi.talitakumiwebapp.base.localservices;

import com.kcreativa.talitakumi.talitakumiwebapp.services.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.tapestry5.ioc.annotations.Inject;
import talitakumi.engine.framework.IDataParameter;
import talitakumi.engine.framework.invoker.ServiceDelegatorRemote;
import talitakumi.model.core.Usuarios;

/**
 *
 * @author rupertus
 */
public class LoginValidator implements ILoginValidator {

    @Inject
    private ISesionWeb sesion;

    public LoginValidator() {

    }
    
    @Override
    public Object invoke(IDataParameter params) {

        String _usuario = (String) params.get("login");
        Object _clave = params.get("clave");
        Usuarios usr = null;
        try {

                Properties props = new Properties();
                //props.load(new FileInputStream("jndi.properties"));

                props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
                props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
                props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
                props.setProperty("org.omg.CORBA.ORBInitialHost", "elias");
                props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

                InitialContext ctx = new InitialContext(props);
                ServiceDelegatorRemote sdr = (ServiceDelegatorRemote) ctx.lookup("services.ServiceDelegatorRemote");

                sesion.setVariable("servicedelegator", sdr);
                

                usr = (Usuarios) sdr.invoke("CargarUsuario", _usuario);
                if(usr.getClave().equals(_clave)) {

                        sesion.setVariable("empresa", "MEDILAB");
                        sesion.setVariable("usuario", usr);
                } else
                       usr = null;

            } catch (NamingException ex) {
                Logger.getLogger(LoginValidator.class.getName()).log(Level.SEVERE, null, ex);
            }
        return(usr);
    }

}
