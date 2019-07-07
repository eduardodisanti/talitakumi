



package testservices;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.naming.InitialContext;
import objetosreporte.ObjetosDePendientesCarne;
import talitakumi.engine.framework.DataParameter;
import talitakumi.engine.framework.invoker.ServiceDelegatorRemote;
import talitakumi.model.core.Carnetdesalud;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Episodiosestudios;
import talitakumi.model.core.Episodiosvacunas;
import talitakumi.model.core.Estudios;
import talitakumi.model.core.Vacunas;


/**
 *
 * @author victoria
 */


public class EpisodiosPendientesCarneDeSalud {

    //Declaracion De Variables.

    private Map<Integer, ObjetosDePendientesCarne> mapa;

    //Fin De Declaracion De Variables.

    public EpisodiosPendientesCarneDeSalud() {
            mapa = new HashMap<Integer,ObjetosDePendientesCarne>();
     }

    void test() {

        ServiceDelegatorRemote sdr = null;

    try {
            Properties props = new Properties();
            props.load(new FileInputStream("jndi.properties"));
            InitialContext ctx = new InitialContext(props);

               sdr = (ServiceDelegatorRemote) ctx.lookup("services.ServiceDelegatorRemote");

        } catch (Exception ex) {
            System.out.println("EXECEPTION" + ex);

          }
            DataParameter dp = new DataParameter();
            Date d1, d2;

            d1 = new Date("2010/12/27");
            d2 = new Date("2010/12/27");

            System.out.println("++++++++++++++ fecha desde " +d1);
            System.out.println("++++++++++++++ fecha hasta " +d2);
            dp.set("fechadesde", d1);
            dp.set("fechahasta", d2);

            List<Episodios> lepi = (List<Episodios>) sdr.invoke("CargarEpisodiosPorRangoSinClientes", dp);

            System.out.println("tamlepi"+lepi.size());

            //org.eclipse.persistence.internal.helper.Helper.isZeroValidPrimaryKey = true;

//            int novacuna, nopap, nomamo, noodo, nomedi, nolabo, i ;
//            novacuna=0;
//            nopap=0;
//            nomamo=0;
//            noodo=0;
//            nomedi=0;
//            nolabo=0;
              int i=1;


            for (Episodios nepi : lepi) {

                    Episodios epi = (Episodios) sdr.invoke("CargarEpisodio", nepi.getId());

                    System.out.println("ESTOY EN EPISODIO " + i++  +" DE " +lepi.size() +" episodio:  " +epi.getId());

                    if(i==11)    //SOLO PARA PRUEBA!!!!!!!
                        break;

                    for (Episodiosvacunas epiv : epi.getEpisodiosvacunasCollection()){

                            Vacunas v = epiv.getVacunas();
                            if (!(v.getId()==1)){
                                agregarepisodiovacunas(epi); //  No tiene vacuna atte.
                                System.out.println("no vacuna atte");

                            }
                    }

                    for (Episodiosestudios epie : epi.getEpisodiosestudiosCollection()){

                        Estudios e = epie.getEstudios();

                        if (!(e.getId()==21)){
                            agregarepisodioestudiopap(epi); // NO tiene pap.
                            System.out.println("no pap");

                        }
                        if (!(e.getId()==22)){
                            agregarepisodioestudiomamo(epi); //  No tiene mamo.
                            System.out.println("no mamo");

                        }
                    }

                    if(epi.getCarnetdesalud()!= null){
                    Carnetdesalud cs = epi.getCarnetdesalud();

                             if (cs.getVigencialaboratorio()==-1){
                                  System.out.println("nolaboratorio"); //No vigencia laboratorio
                                  agregarepisodiovigencialabo(epi);

                             }
                             if (cs.getVigenciamedico()==-1){
                                  System.out.println("nomedico"); //No vigencia medico
                                  agregarepisodiovigenciamedi(epi);

                             }
                             if (cs.getVigenciaodontologo()==-1){
                                  System.out.println("noodontologo"); //No vigencia odontólogo
                                  agregarepisodiovigenciaodo(epi);

                             }

                     }

            } // del for episodios.


System.out.println("Termine de agregar !!!" );

            Iterator clave = mapa.keySet().iterator();

            List<Integer> novacuna = new ArrayList<Integer>();
            List<Integer> nolaboratorio = new ArrayList<Integer>();
            List<Integer> nomedico = new ArrayList<Integer>();
            List<Integer> noodontologo = new ArrayList<Integer>();
            List<Integer> nopap = new ArrayList<Integer>();
            List<Integer> nomamo= new ArrayList<Integer>();

            while(clave.hasNext()) {  // Para iterar en el map episodios

            Integer claveActual = (Integer) clave.next();
            ObjetosDePendientesCarne objo = mapa.get(claveActual);


            if(!objo.getVacunas())
                novacuna.add(objo.getdocumento());

            if(!objo.getLaboratorio())
                nolaboratorio.add(objo.getdocumento());

            if(!objo.getPap())
                nopap.add(objo.getdocumento());

            if(!objo.getMamografia())
                nomamo.add(objo.getdocumento());

            if(!objo.getMedico())
                nomedico.add(objo.getdocumento());

            if(!objo.getOdontologo())
                noodontologo.add(objo.getdocumento());



            System.out.println("No tiene : " + objo.getdocumento());

       }

            System.out.println("NO VACUNA : " +novacuna);
            System.out.println("NO LABORATORIO : " +nolaboratorio);
            System.out.println("NO MEDICO : " +nomedico);
            System.out.println("NO ODONTOLOGO : " +noodontologo);
            System.out.println("NO MAMO : " +nomamo);
            System.out.println("NO PAP : " +nopap);

       // }


//    catch (Exception ex) {
//  System.out.println("ENTRA AL CATCH" + ex);
//
//    }



                } // test()

    private void agregarepisodiovacunas(Episodios epi) {

        ObjetosDePendientesCarne obj = mapa.get(epi.getId());
        if (obj == null){
                obj=new ObjetosDePendientesCarne();
        }
        obj.setVacunas(false);
        rpp(epi,obj);
        mapa.put(epi.getId(), obj);    // int i = epi.getId()

    }

    private void agregarepisodioestudiopap(Episodios epi) {

        ObjetosDePendientesCarne obj = mapa.get(epi.getId());
        if (obj == null){
                obj=new ObjetosDePendientesCarne();
        }
        obj.setPap(false);
        rpp (epi,obj);
        mapa.put(epi.getId(), obj);

    }

    private void agregarepisodioestudiomamo(Episodios epi) {

        ObjetosDePendientesCarne obj = mapa.get(epi.getId());
        if (obj == null){
                obj=new ObjetosDePendientesCarne();
        }
        obj.setMamografia(false);
        rpp (epi,obj);
        mapa.put(epi.getId(), obj);

    }

    private void agregarepisodiovigencialabo(Episodios epi) {

        ObjetosDePendientesCarne obj = mapa.get(epi.getId());
        if (obj == null){
                obj=new ObjetosDePendientesCarne();
        }
        obj.setLaboratorio(false);
        rpp (epi,obj);
        mapa.put(epi.getId(), obj);

    }

    private void agregarepisodiovigenciamedi(Episodios epi) {

        ObjetosDePendientesCarne obj = mapa.get(epi.getId());
        if (obj == null){
                obj=new ObjetosDePendientesCarne();
        }
        obj.setMedico(false);
        rpp (epi,obj);
        mapa.put(epi.getId(), obj);

    }

    private void agregarepisodiovigenciaodo(Episodios epi) {

        ObjetosDePendientesCarne obj = mapa.get(epi.getId());
        if (obj == null){
                obj=new ObjetosDePendientesCarne();
        }
        obj.setOdontologo(false);
        rpp (epi,obj);
        mapa.put(epi.getId(), obj);

    }

    private void rpp (Episodios epi, ObjetosDePendientesCarne obj){

        obj.setApellidos(epi.getPaciente().getPersonas().getApellidos());
        obj.setNombres(epi.getPaciente().getPersonas().getNombres());
        obj.setDocumento(epi.getPaciente().getPersonas().getDocumento());
    }

}

