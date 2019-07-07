/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.services;

import javax.persistence.EntityManager;
import org.apache.log4j.Logger;

/**
 *
 * @author eduardo
 */
public class ServiceDelegator implements AbstractService {

    String nombreServicio;
    String prefijo;
    Object parametros;
    AbstractService servicio;
    Logger logger;
    EntityManager em;

    public ServiceDelegator() {

        parametros = null;
    }

    @Override
    public void setParameters(Object o) {

        parametros = o;
    }


    @Override
    public Object getResultado() {

        return(servicio.getResultado());
    }

    @Override
    public Object invoke() throws Exception{

        servicio.setParameters(parametros);
        return(servicio.invoke());
    }

    private void crearConcreteService() throws InstantiationException, ClassNotFoundException, IllegalAccessException {

        Class laclase = Class.forName(nombreServicio);
        servicio = (AbstractService) laclase.newInstance();
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public AbstractService getService(String s) throws InstantiationException, ClassNotFoundException, IllegalAccessException {

        prefijo = "talitakumi.servicios";
        nombreServicio = prefijo + "." + s;

        crearConcreteService();
        return(servicio);
    }
}
