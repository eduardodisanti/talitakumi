/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.engine.framework;

import java.util.HashMap;
import java.util.Vector;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;

/**
 *
 * @author rupertus
 */
public class Sesion {

     static private Sesion sesion = null;
     static private String nombreusuario;
     static private int    numerousuario;
     static private int    numeromedico;
     static private Vector<String> roles;
     static boolean logged;
     private static Object lugarlogon;
     private static EntityManager entitymanager;
     private static Logger logger;

     private static HashMap entorno;

    /**
     * @return the lugarlogon
     */

    public static Object getLugarlogon() {
        return lugarlogon;
    }

    public static Object getVariableEntorno(String clave) {

        return(entorno.get(clave));
    }

    public static void putVariableEntorno(String clave, Object o) {

        if(entorno==null)
            entorno = new HashMap();
        
        entorno.put(clave, o);
    }
    /**
     * @param aLugarlogon the lugarlogon to set
     */
    public static void setLugarlogon(Object aLugarlogon) {
        lugarlogon = aLugarlogon;
    }

    /**
     * @return the entitymanager
     */
    public static EntityManager getEntitymanager() {
        return entitymanager;
    }

    /**
     * @param aEntitymanager the entitymanager to set
     */
    public static void setEntitymanager(EntityManager aEntitymanager) {
        entitymanager = aEntitymanager;
    }

    /**
     * @return the logger
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * @param aLogger the logger to set
     */
    public static void setLogger(Logger aLogger) {
        logger = aLogger;
    }

    /**
     * @return the numerousuario
     */
    public static int getNumerousuario() {
        return numerousuario;
    }

    /**
     * @param aNumerousuario the numerousuario to set
     */
    public static void setNumerousuario(int aNumerousuario) {
        numerousuario = aNumerousuario;
    }

    /**
     * @return the numeromedico
     */
    public static int getNumeromedico() {
        return numeromedico;
    }

    /**
     * @param aNumeromedico the numeromedico to set
     */
    public static void setNumeromedico(int aNumeromedico) {
        numeromedico = aNumeromedico;
    }

     private Sesion() { logged = false; }

     static public Sesion getSingleton() {

         if (sesion == null) {
             sesion = new Sesion();
         }
         return sesion;
     }

     /*
      * Metodos del singleton.
      */

     public String getNombreUsuario() {
         return(nombreusuario);
     }

     public Vector<String> getRoles() {

         return(roles);
     }

     public void addRol(String rol) {

         roles.add(rol);
     }

     public void setRoles(Vector lr) {

         roles = lr;
     }

    public static void setLogged(boolean ok) {

        logged = ok;
    }

     public static void setNombreUsuario(String usr) {

         nombreusuario = usr;
     }


}
