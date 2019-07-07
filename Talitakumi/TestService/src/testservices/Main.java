/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testservices;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import talitakumi.engine.framework.DataParameter;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Agenda;
import talitakumi.servicios.CargarAgendaTelefonistaINET;
import talitakumi.servicios.ServicioAlmacenarPrecioXactividad;

/**
 *
 * @author rupertus
 */
public class Main {

    private static EntityManager em;
    /**
     * @param args the command line arguments
     */



    public static void main(String[] args) {

        welcome();
//        EpisodiosPendientesCarneDeSalud ep = new EpisodiosPendientesCarneDeSalud();
//
//        ep.test();

        HashMap dp = new HashMap();

        dp.put("fechadesde",  new Date("01/07/2012"));
        dp.put("fechahasta",  new Date("01/07/2012"));
        dp.put("tipolistado", 0);
        dp.put("orden",       "alfabetico");

        List<Object> l = (List<Object>)new CargarAgendaTelefonistaINET().invoke(dp);
    }

    private static void welcome() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TalitakumiEngineModelPU");

        em = emf.createEntityManager();

        Sesion.setEntitymanager(em);
    }
}
