/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package talitakumibatch;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import talitakumi.model.core.Facturas;
import talitakumi.model.core.Personas;
import talitakumi.model.core.Tipodeactividad;

/**
 *
 * @author rupertus
 */
public class TalitakumiBatch {

    static EntityManager em;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //setEmisores(args[0], args[1]);
        
        setCedula8digitos();
        
        //System.out.println(convertirCedulaA8digitos(1597597));
    }

    private static void setCedula8digitos() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TalitakumiEngineModelPU");

        em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
            t.begin();
        String queryA = "select c from Personas c where c.documento > 9999999 and c.documento < 99999999";
        Query qA = em.createQuery(queryA);
        List<Personas> resA = (List<Personas>) qA.getResultList();
        for(Personas p : resA) {
            System.out.println("cedula a agregar 0 " + p.getDocumento());
            String s = p.getDocumento()+"0";
            p.setDocumento(Integer.parseInt(s));
            
            em.merge(p);
            
        }
        t.commit();
        String query = "select c from Personas c";
        
        Query q = em.createQuery(query);
                
        List<Personas> res = (List<Personas>) q.getResultList();
    
        int cuenta = 0;
        t = null;
        for(Personas p : res) {
            
                String s = p.getDocumento()+"";

                if(s.length()==7) {
                   if(cuenta==0) {
                        t = em.getTransaction();
                        t.begin();
                    }
                    int nc = convertirCedulaA8digitos(p.getDocumento());
                    p.setDocumento(nc);

                    try {
                            persistir(p, em);
                            

                            System.out.println("Nueva cedula es " + nc);
                            cuenta++;
                    } catch(Exception ex) {
                        System.out.println("NO CONVERTIDO " + nc);
                        if(cuenta==0) {
                            cuenta = 1;
                        }
                }

                if(cuenta==100) {
                    if(t!=null) {
                        t.commit();
                    }
                    cuenta = 0;
                }
        }
      }
    if(cuenta<100) {
            if(t!=null) {
                t.commit();
            }
    }

}
    
    private static int convertirCedulaA8digitos(int n) {
        
        int nc = n;
        
        String lacedula = n+"";
        
        if(lacedula.length()==6) {
            lacedula = "0" + lacedula;
        }
        
        int n0 = Integer.parseInt(""+lacedula.charAt(0));
        int n1 = Integer.parseInt(""+lacedula.charAt(1));
        int n2 = Integer.parseInt(""+lacedula.charAt(2));
        int n3 = Integer.parseInt(""+lacedula.charAt(3));
        int n4 = Integer.parseInt(""+lacedula.charAt(4));
        int n5 = Integer.parseInt(""+lacedula.charAt(5));
        int n6 = Integer.parseInt(""+lacedula.charAt(6));
        
        int suma = n0 * 2 + n1 * 9 + n2 * 8 + n3 * 7 + n4 * 6 + n5 * 3 + n6 * 4;

        int resto = suma % 10;
        int h = (10 - resto) % 10;
        
        nc = Integer.parseInt( n + "" + h);
        return(nc);
    }
    
    private static void setEmisores(String x1, String x2) {
        
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TalitakumiEngineModelPU");

        em = emf.createEntityManager();

        String query = "select f from Facturas f where f.fechaentregado >= :fecha1 and f.fechaentregado <= :fecha2";
        
        Query q = em.createQuery(query);
        
        Date fecha1 = new Date(x1);
        Date fecha2 = new Date(x2);
        
        q.setParameter("fecha1", fecha1);
        q.setParameter("fecha2", fecha2);
        
        EntityTransaction t = em.getTransaction();
        t.begin();
        List<Facturas> res = (List<Facturas>) q.getResultList();
        for(Facturas f : res) {
            
            Tipodeactividad ta = f.getEpisodio().getTipoepisodio().getTipoactividad();
            
            switch (ta.getId()) {

                case  1 :
                case  2 :
                case 11 :
                case 12 :
                    setEmisorFactura(f, 1);
                    break;
                    
                case 10 :
                case  9 :
                case 14 :                        
                    setEmisorFactura(f, 2);
                    break;

                case 13 :
                case 15:
                    setEmisorFactura(f, 3);
                    break;

                default : setEmisorFactura(f, 0);
                    break;
            }

        }
        t.commit();
        em.close();
    }

    private static void setEmisorFactura(Facturas f, int i) {
        
        //EntityTransaction t = em.getTransaction();
        //t.begin();
        f.setEmisor(i);
        System.out.println(f + " " + new Date());
        em.merge(f);
        //t.commit();
    }

    private static void persistir(Personas p, EntityManager em) {
        try {
            
            em.merge(p);
        } catch(Exception ex) {
            System.out.println("NO CONVERTIDO " + p.getDocumento());
        }
    }
}
