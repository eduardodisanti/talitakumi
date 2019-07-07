package talitakumi.servicios;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Actividades;
import talitakumi.model.core.Agenda;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Episodios;

public class ServicioGenerarFacturasContratos implements AbstractService {

    private EntityManager em;
    private Logger logger;
    Agenda a;

    public ServicioGenerarFacturasContratos() {

        em = Sesion.getEntitymanager();
        logger = Sesion.getLogger();
    }

    @Override
    public Object invoke(Object o) {

        
        boolean res = false;

        try {

            String sql = "select p from Clientes p where p.importeContrato > 0";
            Query q = em.createQuery(sql);

            for(Clientes c : (List<Clientes>)q.getResultList()) {

                Date fechafactura;
                Episodios e = new Episodios();
                e.setComentarios("FACTURACION AUTOMÁTICA " + new Date());
                e.setCliente(c.getId());
                e.setFacturas(null);

                if(c.getFacturarMismoMes()=='S') {
                    fechafactura = getFechaInicioMesCorriente();
                } else {
                    fechafactura = getFechaFinMesCorriente();
                  }
                e.setFecha(fechafactura);
                e.setPaciente(null);
                e.setMedico(null);
                e.setTipoepisodio(cargarTipoEpisodio(100));
                e.setTimming(null);
            }

        } catch(Exception ex) {

            System.out.println("Error servicio almacenar agenda " + ex);
            res = false;
        }
        return(res);
    }

    private Actividades cargarTipoEpisodio(int i) {

        ServicioCargarTipoActividad scta = new ServicioCargarTipoActividad();
        return(Actividades) (scta.invoke(i));
    }

    private Date getFechaInicioMesCorriente() {

        GregorianCalendar cal = new GregorianCalendar();

        cal.setTime(new Date());

        cal.set(Calendar.DAY_OF_MONTH, 1);

        return(cal.getTime());
    }

    private Date getFechaFinMesCorriente() {

        GregorianCalendar cal = new GregorianCalendar();

        cal.setTime(new Date());

        cal.set(Calendar.DAY_OF_MONTH, 30);
        cal.add(Calendar.MONTH, -1);

        return(cal.getTime());

    }
}
