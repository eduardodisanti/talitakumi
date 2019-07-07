package com.kcreativa.talitakumi.talitakumiwebapp.pages;

import java.util.Date;
import java.util.Properties;
import javax.naming.InitialContext;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import talitakumi.engine.framework.invoker.ServiceDelegatorRemote;
import talitakumi.engine.framework.invoker.sesionweb.ClientSesion;
import talitakumi.model.core.Usuarios;

/**
 * Start page of application TalitakumiWebApp.
 */
@IncludeStylesheet("context:layout.css")
public class Index
{
    @Persist
    @Property
    private static final String empresa = "Medilab";

    @Component(id = "index")
	private Form _form;

    	@Property
	private String _usuario;

	@Property
	private String _clave;

        @Component(id = "usuario")
	private TextField _usuarioTF;

	@Component(id = "clave")
	private PasswordField _claveTF;

	@InjectPage
	private Logoncentral _logon;


	public Date getCurrentTime() 
	{ 
		return new Date(); 
	}

        public String getEmpresa() {

            return(empresa);
        }

       void onValidateForm() {
           int i = 0;
           
		if (_usuario == null || _usuario.trim().equals("")) {
			_form.recordError(_usuarioTF, "Se requiere un usuario.");
		} else {
               i++;
           }
		if (_clave == null || _clave.trim().equals("")) {
			_form.recordError(_claveTF, "Se requiere la clave.");
		} else {
               i++;
           }
           
           if(i==2) {

               /*IDataParameter dp = new DataParameter();
               dp.set("login", _usuario);
               dp.set("clave", _clave);
               dp.set("empresa", empresa);
               boolean ok = false;
               Usuarios o = (Usuarios) loginvalidator.invoke(dp);
               if(o!=null) {
                   if(o.getClave().equals(_clave))
                       ok = true;
               }*/

               try {
                    System.out.println("break 1");
                    conectar();
                   ServiceDelegatorRemote ti = (ServiceDelegatorRemote) ClientSesion.getServiceDelegatorRemote();
                   System.out.println("break 2");
                   Usuarios u = (Usuarios) ClientSesion.getServiceDelegatorRemote().invoke("CargarUsuario", _usuario);
                   System.out.println("break 3");

               if(u==null) {
                   _form.recordError(_claveTF, "Error de autenticaci?n, usuario o clave incorrecta");
               } else {
                                          System.out.println("break 4");
                        ClientSesion.putVariableEntorno("usuario", u);
                                           System.out.println("break 5");
                        ClientSesion.putVariableEntorno("empresa", empresa);
                                           System.out.println("break 6");
               }
               
               } catch(Exception e) {
                      // _form.recordError(null, "Error interno del sistema " + e);
                       System.out.println("Error : " + e);
               }
           }

	}

	Object onSuccess() {

            _logon.welcome();
            return _logon;
	}

   public void conectar() throws Exception {

                Properties props = new Properties();
                props.load(Thread.currentThread().getContextClassLoader().getResource("jndi.properties").openStream());
//                props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
//                props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
//                props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
//                props.setProperty("org.omg.CORBA.ORBInitialHost", "elias");
//                props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

                InitialContext ctx = new InitialContext(props);

                ServiceDelegatorRemote sdr = (ServiceDelegatorRemote) ctx.lookup("services.ServiceDelegatorRemote");
                ClientSesion.putVariableEntorno("ServiceDelegator", sdr);

                ClientSesion.setServiceDelegatorRemote(sdr);
                //sesion.setVariable("servicedelegator", sdr);
   }
}
