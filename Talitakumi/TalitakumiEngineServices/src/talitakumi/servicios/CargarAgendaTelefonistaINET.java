/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engine.framework.Sesion;
import talitakumi.engineservices.AbstractService;
import talitakumi.model.core.Agenda;
import talitakumi.model.core.Feriados;

/**
 *
 * @author rupertus
 */
public class CargarAgendaTelefonistaINET implements AbstractService {

    private EntityManager em;
    private Logger logger;
    private Date fechadesde, fechahasta;
    private Integer tipoListado;
    private String orden;
    private List<Feriados> lf;

    public CargarAgendaTelefonistaINET() {

        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    private void setParameters(Object o) {

        HashMap hm = (HashMap) o;

        fechadesde  = (Date) hm.get("fechadesde");
        fechahasta  = (Date) hm.get("fechahasta");
        tipoListado = (Integer) hm.get("tipolistado");
        orden       = (String) hm.get("orden");

        if(orden==null) {
            orden = "";
        }
    }

    @Override
    public Object invoke(Object o) {

            this.setParameters(o);
            lf = (List<Feriados>) new CargarFeriados().invoke(new Date());

            String condiciones = "";

            if(tipoListado==1) {
            condiciones = "";
        }
            else
                if(tipoListado==0) {
                    condiciones = " and (a.agendaPK.consultorio = 'INET' or a.agendaPK.consultorio = 'TEL')";
                }

            String elorden = "a.agendaPK.fecha, a.agendaPK.hora";

            if(orden.equals("alfabetico")) {

                orden = "order by " + elorden; //" order by a.episodio.paciente.personas.apellidos, " +  elorden;
            } else {
                     orden = "order by " + elorden;
            }

//            String qry = "SELECT a FROM Agenda a WHERE a.agendaPK.fecha >= :fechadesde and a.agendaPK.fecha <= :fechahasta " + condiciones + " " + orden;
//
//            Query q = em.createQuery(qry);
//
//            q.setParameter ("fechadesde", fechadesde);
//            q.setParameter ("fechahasta", fechahasta);
//
//
//            Vector<Agenda> lista = (Vector<Agenda>) q.getResultList();
            
//            for(Agenda a : cargarMolde()) {
//
//                lista2.add(a);
                
//                Agenda anot = cargarLista(a, lista);
//                if(anot==null)
//                    lista2.add(a);
//                else
//                    lista2.add(anot);
//            }

        return(cargarMolde());
    }

    
    private ArrayList<Object> cargarMolde() {
        
            ArrayList<Object> lista = new ArrayList<Object>();

            Calendar cale = new GregorianCalendar();
            Calendar cale1 = new GregorianCalendar();
            
            cale.setTime(fechadesde);
            cale.set(Calendar.SECOND, 0);
            cale.set(Calendar.MINUTE, 0);
            cale.set(Calendar.HOUR, 0);
            
            cale1.setTime(fechahasta);
            cale1.set(Calendar.SECOND, 59);
            cale1.set(Calendar.MINUTE, 59);
            cale1.set(Calendar.HOUR, 23);
            
            System.out.println("Voy a calcular desde " + fechadesde + " hasta " + fechahasta);
            while(cale.getTime().before(cale1.getTime())) {

                if(!esFeriado(cale.getTime())) {
                    List<Object> la = cargarDia(cale);
                    for(Object f : la) {
                        lista.add(f);
                    }
                }
                cale.add(Calendar.DAY_OF_YEAR, 1);
            }

       return(lista);
    }

    private Agenda cargarLista(Agenda a, Vector<Agenda> lista) {
        
        Agenda anot = null;
        
        int i=0;
        boolean flag = true;
        
        while(i<lista.size() && flag) {
            
            Agenda una = lista.get(i);
                        
            Date fa = a.getAgendaPK().getFecha();
            Date fanot = una.getAgendaPK().getFecha();
            
            String ha   = a.getAgendaPK().getHora();
            String hanot= una.getAgendaPK().getHora();
            
            System.out.println("Comparando " + fa + " con " + fanot + " y " + ha + " con " + hanot + " y " + a.getAgendaPK().getTurno() + " con " + una.getAgendaPK().getTurno());
                    
            System.out.println("Son fechas iguales " + sonFechasIguales(fanot, fa));
            System.out.println("Son horas  iguales " + ha.equals(ha));
            System.out.println("Son turnos iguales " + (a.getAgendaPK().getTurno()==una.getAgendaPK().getTurno()));
                    
            if(sonFechasIguales(fanot, fa) && ha.equals(hanot) && a.getAgendaPK().getTurno()==una.getAgendaPK().getTurno()) {
                
                anot = una;
                flag = false;
                System.out.println("ENCONTRE !!!");
            }
            i++;
        }
        
        return(anot);
    }

    private boolean sonFechasIguales(Date fanot, Date fa) {

        GregorianCalendar anot = new GregorianCalendar();
        GregorianCalendar actual = new GregorianCalendar();

        anot.setTime(fanot);
        anot.set(Calendar.SECOND, 0);
        anot.set(Calendar.MINUTE, 0);
        anot.set(Calendar.HOUR, 0);

        actual.setTime(fa);
        actual.set(Calendar.SECOND, 0);
        actual.set(Calendar.MINUTE, 0);
        actual.set(Calendar.HOUR, 0);

        System.out.println("sonFechasIguales(" + anot.getTime() + "," + actual.getTime() + ") " + actual.get(Calendar.HOUR));
        
        boolean resp = actual.get(Calendar.YEAR) == anot.get(Calendar.YEAR) &&
                       actual.get(Calendar.MONTH) == anot.get(Calendar.MONTH) &&
                       actual.get(Calendar.DAY_OF_MONTH) == anot.get(Calendar.DAY_OF_MONTH);
        
        System.out.println("sonFechasIguales(" + anot.getTime() + "," + actual.getTime() + ") " + actual.get(Calendar.HOUR) + " " + resp);
        return(resp);
    }

    private List<Object> cargarDia(Calendar cale) {

        Vector<Object> lista;
        Vector<Object> listaMolde;
        ArrayList<Object> listaResp = new ArrayList<Object>();

        String qry = "SELECT a.hora, a.minutos, sum(a.lugares) FROM Moldeagenda a WHERE a.dia = :dia group by a.hora, a.minutos order by a.hora, a.minutos";
        Query q = em.createQuery(qry);
        q.setParameter("dia", cale.get(Calendar.DAY_OF_WEEK) - 1);
        listaMolde = (Vector<Object>) q.getResultList();

        for(Object objeto : listaMolde) {

            Object [] molde = (Object[]) objeto;

            Integer  hora = (Integer)  molde[0];
            Integer  minutos = (Integer)  molde[1];
            Long totalLugares = (Long) molde[2];

            int cant = cargarLugaresOcupados(cale, hora, minutos);
            int disponibles = (int) (totalLugares - cant);
            
            if(disponibles > 0) {

                listaResp.add(new Object[]{hora, minutos, disponibles, cale.getTime()});
                
            }

//            System.out.println("Hora="+hora+"-minutos="+minutos);
        }

        return(listaResp);
    }

    private boolean esFeriado(Date fecha) {

            boolean encontre = false;
            int i = 0;
            Feriados f;
            while(!encontre && i != lf.size()) {

                f = lf.get(i);
                System.out.println("F1=(" + fecha + ")=(" + f.getFecha()+ ")");
                if(sonFechasIguales(fecha, f.getFecha()))
                    encontre = true;
                else
                    i++;
            }

        System.out.println("El día " + fecha + " es feriado " + encontre);
       return(encontre);
    }

    private int cargarLugaresOcupados(Calendar cale, int hh, int mm) {

        int cantidad = 0;

        String horadesde, horahasta;

        String xhh = hh+"";
        String xmm = mm+"";
        
        if(xhh.length()==1) {
            xhh = "0" + xhh;
        }
        
        if(xmm.length()==1) {
            xmm = "0" + xmm;
        }

        horadesde = xhh + ":" + xmm;
        horahasta = xhh + ":" + (mm + 15);

        
        System.out.println("========> ATENZAO MIRAR HORA DESDE - HASTA" + horadesde + " " + horahasta);
        String qry = "SELECT count (a.agendaPK.fecha) FROM Agenda a WHERE a.agendaPK.fecha = :fechadesde and a.agendaPK.hora>=:horadesde and a.agendaPK.hora<=:horahasta and (a.agendaPK.consultorio='INET' or a.agendaPK.consultorio='TEL')";

        Query q = em.createQuery(qry);
        q.setParameter("fechadesde", cale.getTime());
        q.setParameter("horadesde", horadesde);
        q.setParameter("horahasta", horahasta);

        Long i = (Long) q.getSingleResult();

        cantidad = Integer.parseInt(i+"");
        return(cantidad);
    }
}
