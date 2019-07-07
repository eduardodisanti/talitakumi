/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import talitakumi.engine.framework.Sesion;
import talitakumi.engine.framework.invoker.ServiceDelegatorRemote;
import talitakumi.engineservices.AbstractService;

/**
 *
 * @author rupertus, o sea yo
 */
@Stateless (name="TalitakumiEngine", mappedName="services.ServiceDelegatorRemote")
public class ServiceDelegatorBean implements ServiceDelegatorRemote {

    @PersistenceContext(name="TalitakumiEngine-ejbPU")
    EntityManager em;
    AbstractService servicio;

    @Override
    public Object invoke(String nombre, Object parametros) {

        if(Sesion.getEntitymanager() ==null) {
            System.out.println("Inicializando bean");
            init();
        }

       // System.out.println("El entity manager es : " + em.toString());

        Object resp = null;
        String prefijo = "talitakumi.servicios";
        String nombreServicio = prefijo + "." + nombre;
        try {
            crearConcreteService(nombreServicio);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ServiceDelegatorBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("------------------------------error "+ex);
        }
        try {
            resp = servicio.invoke(parametros);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ServiceDelegatorBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("------------------------------error "+ex);
        }
        return(resp);
    }

    private void crearConcreteService(String nombre) throws Exception {

        //System.out.println("Voy a hacer el Reflection de " + nombre);
        Class laclase = Class.forName(nombre);
        //System.out.println("******************************Reflection ok ");
        servicio = (AbstractService) laclase.newInstance();
        //System.out.println("=============================new instance ok");
    }

    private void init() {
        
        Sesion.setEntitymanager(em);
    }

    @Override
    public void setVariableEntorno(String nombre, Object valor) {

        Sesion.putVariableEntorno(nombre, valor);
    }

    @Override
    public Object getVariableEntorno(String nombre) {

        return(Sesion.getVariableEntorno(nombre));
    }
    
}
