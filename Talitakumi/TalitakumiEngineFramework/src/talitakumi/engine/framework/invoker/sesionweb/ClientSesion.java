/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.engine.framework.invoker.sesionweb;

import java.util.HashMap;
import talitakumi.engine.framework.invoker.Invoker;
import talitakumi.engine.framework.invoker.ServiceDelegatorRemote;

/**
 *
 * @author rupertus
 */
public class ClientSesion {

     static private ClientSesion sesion = null;
     static boolean logged;
     private static Invoker invoker;
     private static ServiceDelegatorRemote sdr;
     private static HashMap entorno;

    /**
     * @return the lugarlogon
     */

    public static Object getVariableEntorno(String clave) {

        return(entorno.get(clave));
    }

    public static void putVariableEntorno(String clave, Object o) {

        if(entorno==null) {
            entorno = new HashMap();
        }

        entorno.put(clave, o);
    }
    /**
     * @param aLugarlogon the lugarlogon to set
     */
    /**
     * @return the entitymanager
     */
    public static Invoker getInvoker() {
        return invoker;
    }

    /**
     * @param aEntitymanager the entitymanager to set
     */
    public static void setInvoker(Invoker unInvoker) {
        invoker = unInvoker;
    }

    public static void setServiceDelegatorRemote(ServiceDelegatorRemote unInvoker) {
        sdr = unInvoker;
    }

    public static ServiceDelegatorRemote getServiceDelegatorRemote() {
        
        return(sdr);
    }


    /**
     * @return the numerousuario
     */

     private ClientSesion() {
         logged = false;
     }

     static public ClientSesion getSingleton() {

         if (sesion == null) {
             sesion = new ClientSesion();
         }
         return sesion;
     }

     /*
      * Metodos del singleton.
      */

    public static void setLogged(boolean ok) {

        logged = ok;
    }

    public static boolean getLogged() {

        return(logged);
    }
}
