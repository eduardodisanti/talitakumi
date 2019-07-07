/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import talitakumi.engineservices.AbstractService;
import talitakumi.engine.framework.Sesion;
import talitakumi.model.core.Analisis;
import talitakumi.model.core.Analisishechos;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Estudioshechos;
import talitakumi.model.core.Pacientes;


/**
 *
 * @author rupertus
 */
public class ObtenerEstadoExamenesLaboratorio implements AbstractService {

    EntityManager em;
    Logger logger;
    Collection <Estudioshechos> estudios;
    Episodios e;
    private final short MAXIMO = 2;
    private final short MINIMO = 1;

    public ObtenerEstadoExamenesLaboratorio() {


        this.em = Sesion.getEntitymanager();
        this.logger = Sesion.getLogger();
    }

    public void setParameters(Object o) {

        e = (Episodios) o;

    }

    @Override
    public Object invoke(Object o) {

        this.setParameters(o);
        int scoring = 4;
        int score;

        int edad = calcularEdad(e.getPaciente());
        if(edad > 14 ) {
            Query q = em.createQuery("select e from Estudioshechos e where e.episodio = :episodio");
            q.setParameter("episodio", e);
            estudios = (Collection<Estudioshechos>) q.getResultList();

            HashMap hm = new HashMap();
            for(Estudioshechos ee : estudios) {
                if(!ee.getEstudio().getLaboratorio().equals("E")) {
                   hm.put(ee.getEstudio().getId(), ee);
                }
            }

            if(estudios.size()<1) {
                score = 0;
            }
            for(Estudioshechos ee : (Collection <Estudioshechos>)hm.values()) {

                Collection <Analisishechos> analest = ee.getAnalisishechosCollection();
                for(Analisishechos ae : analest) {
                    String res = ae.getValorhallado();
                    if(res.length()>0) {
                        score = obtenerScoringAnalisis(ae);
                        if(score < scoring) {
                            scoring = score;
                            System.out.println("Baje el score de " + ae.getAnalisis().getId() + " a " + score);
                        }
                        if(score < 2) {
                            System.out.println("Corte en " + ae.getAnalisis().getId() + " con " + score);
                            break;
                        }
                    } else {
    //                       if(ae.getAnalisis().getId()!=21)
    //                          scoring = 0;
                    }
                }
            }
       } else {

            scoring = 3;
       }
       return(scoring);
    }

    private int obtenerScoringAnalisis(Analisishechos ae) {

        int x = 0;
        float min, max, hallado;

        Analisis a = ae.getAnalisis();
        char disc = a.getTiporesultado();
        String vfm;
        String gvh;
        String numero;

        try {
            switch (disc) {

                case 'T' :
                       vfm = a.getValorrefminimo();
                       gvh = ae.getValorhallado();
                       Boolean res = new Boolean(gvh);
                       if(res.booleanValue()==true && a.getId()!= 21 && a.getId()!= 22) {
                           x = 2;
                           if(a.getId()==3)
                               x = 1;
                       } else
                           x = 3;
                       break;

                case 'N' :
                       vfm = a.getValorrefminimo();
                       if(vfm.equals(""))
                           vfm="0";
                       gvh = ae.getValorhallado();
                       if(gvh.equals(""))
                           gvh="9999999999";
                       numero = purgar(a.getValorrefmaximo());
                       max = convertirResultado(numero, MAXIMO);
                       //max = Float.parseFloat(numero);
                       numero = purgar(a.getValorrefminimo());
                       min = convertirResultado(numero, MINIMO);
                       //min = Float.parseFloat(numero);
                       numero = purgar(ae.getValorhallado());
                       hallado = convertirResultado(numero, MAXIMO);

                       if(hallado >= min && hallado <= max) {
                           x = 3;
                       } else
                           x = 2;
                       if(a.getId()==1 && hallado >= 1.80) // TODO - MEJORAR LA ESTRUCTURA DEL ANALISIS PARA QUE MARQUE LOS VALORES LIMITANTES
                          x=1;

                       System.out.println("analisis " + a.getId() + " valor=" + hallado + " con " + x + " rango es : " + min + " y " + max);
                       break;

                case 'L' :
                       vfm = a.getValorrefminimo();
                       gvh = ae.getValorhallado();
                       if(vfm.equals(gvh))
                           x = 3;
                       else
                           x = 2;
                       break;
            }
        } catch(Exception ex) {
          System.out.println("Error calculando scoring : "+ ae.getAnalisis().getId() + " : " + ex);
          x = 0;
        }
        return(x);
    }

    private String purgar(String unnumero) {

        int l;

        String numerete = "";
        char c;

        l = unnumero.length();

        for(int i=0; i < l; i++) {

            c = unnumero.charAt(i);
            if(c==',')
                c='.';
            if((c >= 48 && c <= 57) || c=='.')
                numerete += c;
        }

        return(numerete);
    }

    private Float convertirResultado(String numero, short tipo) {

        Float res = null;

        try {
            res = Float.parseFloat(numero);
        } catch(Exception ex) {

            if(tipo==MAXIMO)
                res = 999999F;
            else
                res = 0F;
        }

        return(res);
    }

    private int calcularEdad(Pacientes p) {

        Calendar hoy = Calendar.getInstance();
        Calendar fechaNac = Calendar.getInstance();

        fechaNac.setTime(p.getPersonas().getFechanacimiento());

        int anios = hoy.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);


        return(anios);
    }
}
