/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Agenda;

/**
 *
 * @author rupertus
 */
public class CargarAgendaConsultorioGlobal implements AbstractService {

    EntityManager em;
    Logger logger;
    Integer tipolistado;
    Integer consultorio;
    Character vino;

    public CargarAgendaConsultorioGlobal() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    private void setParameters(Object o) {

        HashMap hm = (HashMap) o;

        consultorio = (Integer) hm.get("consultorio");
        tipolistado = (Integer) hm.get("filtro");
        vino = (Character) hm.get("vino");

    }

    @Override
    public Object invoke(Object o) {

        Date hoy = new Date();

            this.setParameters(o);
            String filtro = "";
            switch (tipolistado) {

                case 0 : filtro = "";
                        break;

                case 1 : filtro = " AND a.actividad = 1";
                        break;

                case 2 : filtro = " AND a.actividad = 2";
                        break;
            }

            String qry = "SELECT a FROM Agenda a WHERE a.vino = :vino AND a.episodio.medico.codigo = 0 " + filtro + " order by a.agendaPK.fecha, a.agendaPK.hora";
            Query q = em.createQuery(qry);

            q.setParameter ("consultorio", consultorio+"");
            q.setParameter ("vino", 'S');
            q.setParameter("hoy", hoy);
            Collection <Agenda> c = q.getResultList();
            for(Agenda a : c) {
                a.getEpisodio();
                a.getEpisodio().getLibretasdeconducir();
                a.getEpisodio().getCarnetdesalud();
            }
        return(c);
    }
}
