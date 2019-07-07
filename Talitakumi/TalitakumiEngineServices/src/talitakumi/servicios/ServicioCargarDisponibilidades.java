/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Date;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.DataParameter;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;

/**
 *
 * @author rupertus
 */
public class ServicioCargarDisponibilidades implements AbstractService {

    private EntityManager em;
    private Logger logger;
    private Date fecha_desde;
    

    public ServicioCargarDisponibilidades() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();

    }

    public void setParameters(Object o) {

        DataParameter parametros;
        parametros = (DataParameter) o;
    }

    @Override
    public Object invoke(Object o) {
        
        String resp = null;
        this.setParameters(o);
        
//            Date hoy = tws.makeDate(xfecha);
//            Date hasta = new Date();
//            Calendar cale = new GregorianCalendar();
//            Calendar cale1 = new GregorianCalendar();
//            cale.setTime(hoy);
//            cale1.setTime(hasta);
//            cale1.add(Calendar.DAY_OF_YEAR, cantDias);
//
//
//        return(q.getResultList());
//    }
//
//    public String getDisponibilidades(
//                    @WebParam(name = "fecha") String xfecha,
//                    @WebParam(name = "cantDias") int cantDias,
//                    @WebParam(name = "servicio") int tiposervicio,
//                    @WebParam(name = "sid") Long sid
//                    ){
//
//        int z = (Integer) Sesion.getVariableEntorno("cliente");
//        System.out.println("El cliente es : " + z);
//        String resp = null;
//
//        try {
//
//            agregarASesion(sid, "actividad", tiposervicio);
//            TWSStandards tws = new TWSStandards();
//            tws.conectar();
//
//            Date hoy = tws.makeDate(xfecha);
//            Date hasta = new Date();
//            Calendar cale = new GregorianCalendar();
//            Calendar cale1 = new GregorianCalendar();
//            cale.setTime(hoy);
//            cale1.setTime(hasta);
//            cale1.add(Calendar.DAY_OF_YEAR, cantDias);
//
//            HorarioSerializado hs = null;
//            IFObjetoSerializado ds = new DisponibilidadesSerializado();
//
//            int i = 0;
//            int diadehoy = cale.get(Calendar.DAY_OF_WEEK);
//            String xhh;
//            String xmm;
//            while(cale.getTime().before(cale1.getTime())) {
//
//                int hh = 7;
//                while(hh <= 18) {
//
//                    for(int mm=0;mm<=45;mm+=15) {
//                        hs = new HorarioSerializado();
//
//                        xhh = hh+"";
//                        if(xhh.length()==1)
//                            xhh = "0"+xhh;
//
//                        xmm = mm+"";
//                        if(xmm.length()==1)
//                            xmm = "0"+xmm;
//
//                        hs.addTag("FECHA", tws.formatDate(cale.getTime()));
//                        hs.addTag("HORA", xhh + ":" + xmm);
//                        hs.addTag("DIADELASEMANA", diadehoy + "");
//
//                        ds.addTag("I-" + hs.getObjectName() + "-" + i, tws.serializeToXML(hs));
//                        System.out.println("Ahora voy en " + hh + " : " + mm);
//                        i++;
//                    }
//                    ++hh;
//                }
//                cale.add(Calendar.DAY_OF_YEAR, 1);
//            }
//
//            resp=tws.makeResponse(true, "HORARIOS DISPONIBLES", ds);
//        } catch (Exception ex) {
//            resp = "ERROR " + ex;
//            Logger.getLogger(AgendaWebServices.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("Error getDisponibilidades " + ex);
//        }

        return(resp);
    }
}
