/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Clientes;

/**
 *
 * @author rupertus
 */
public class AlmacenarCliente implements AbstractService {

    private EntityManager em;
    private Logger logger;

    public AlmacenarCliente() {

        em = Sesion.getEntitymanager();
        logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        Clientes e = (Clientes) o;
        Clientes e1 = em.find(Clientes.class, e.getId());

        if(e1 == null) {

            e1 = new Clientes();
        }
        
        e1.setId(e.getId());
        e1.setCiudad(e.getCiudad());
        e1.setDepartamento(e.getDepartamento());
        e1.setDomicilio(e.getDomicilio());
        e1.setDomicilioFiscal(e.getDomicilioFiscal());
        e1.setEmail(e.getEmail());
        e1.setFacturar(e.getFacturar());
        e1.setFacturarMismoMes(e.getFacturarMismoMes());
        e1.setHabilitado(e.getHabilitado());
        e1.setImporteContrato(e.getImporteContrato());
        e1.setNombre(e.getNombre());
        e1.setObservaciones(e.getObservaciones());
        e1.setRazonSocial(e.getRazonSocial());
        e1.setRut(e.getRut());
        e1.setTelefonos(e.getTelefonos());
        e1.setWeb(e.getWeb());
        e1.setEmisor(e.getEmisor());

        System.out.println("Cliente existente " + e1 + " " + e1.getNombre());
        em.merge(e1);
        em.persist(e1);
        em.flush();

       return(e);
    }

    public Object getResultado() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
