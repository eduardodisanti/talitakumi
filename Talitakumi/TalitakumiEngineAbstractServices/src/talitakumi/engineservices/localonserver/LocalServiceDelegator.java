/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.engineservices.localonserver;

/**
 *
 * @author eduardo
 */
public class LocalServiceDelegator implements AbstractLocalService {

    String nombreServicio;
    String prefijo;
    Object parametros;
    AbstractLocalService servicio;

    public LocalServiceDelegator() {

        System.out.println("LocalServiceDelegator ok ");
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

        //servicio.setParameters(parametros);
        return(servicio.invoke());
    }

    private void crearConcreteService() throws InstantiationException, ClassNotFoundException, IllegalAccessException {

        Class laclase = Class.forName(nombreServicio);
        servicio = (AbstractLocalService) laclase.newInstance();
    }

    public AbstractLocalService getService(String s) throws InstantiationException, ClassNotFoundException, IllegalAccessException {

        System.out.println("getService");
        prefijo = "talitakumi.servicios";
        nombreServicio = prefijo + "." + s;

        System.out.println("getService : " + nombreServicio);

        crearConcreteService();
        return(servicio);
    }
}
