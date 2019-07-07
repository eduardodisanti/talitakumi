 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.informes.control.medicos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import talitakumi.engine.framework.DataParameter;
import talitakumi.framework.Sesion;
import talitakumi.informes.ui.medicos.UIDiagnosticoSanitario;
import talitakumi.model.core.Analisis;
import talitakumi.model.core.Analisishechos;
import talitakumi.model.core.Clientes;
import talitakumi.model.core.Episodioexamenfisico;
import talitakumi.model.core.Episodioodontologo;
import talitakumi.model.core.Episodiopresion;
import talitakumi.model.core.Episodios;
import talitakumi.model.core.Episodiosestudios;
import talitakumi.model.core.Episodiosvicios;
import talitakumi.model.core.Estudioshechos;
import talitakumi.model.core.Pacientes;
import talitakumi.servicios.Reporter;
import view.UIProgreso;


/**
 *
 * @author rupertus
 */
public class DiagnosticoSanitario {

     private Date fechadesde, fechahasta;
     private final short MAXIMO = 2;
     private final short MINIMO = 1;

     public DiagnosticoSanitario() {
         
     }

     public void listar(Date fechadesde, Date fechahasta) {

         Vector <Clientes> lc = null;
         HashMap m = new HashMap();

         DefaultTableModel dftm = new DefaultTableModel();

         lc = (Vector<Clientes>) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarClientes", 0);

         dftm.addColumn("Codigo");
         dftm.addColumn("Cliente");

         for(Clientes c : lc) {
             dftm.addRow(new Object[]{c.getId(), c.getNombre()});
         }

         String sfechadesde, sfechahasta;

         DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
         sfechadesde = df.format(fechadesde);
                       df = new SimpleDateFormat("yyyy-MM-dd");
         sfechahasta = df.format(fechahasta);
         m.put("fecha_desde", sfechadesde);
         m.put("fecha_hasta", sfechahasta);

         UIDiagnosticoSanitario uic = new UIDiagnosticoSanitario(null, true);

         uic.setModeloClientes(dftm);

         uic.setAnchoColumna(0, 50);
         uic.setAnchoColumna(1, 350);

         uic.setVisible(true);

         if(uic.getReturnStatus()!=0) {

            int idx = uic.getCliente();
            fechadesde = uic.getFechaDesde();
            fechahasta = uic.getFechaHasta();

            Clientes cli = lc.get(idx);

            String xpond = uic.getPonderador();
            Float ponderador = Float.parseFloat(xpond);

            imprimirDiagnostico(fechadesde, fechahasta, cli, ponderador);
         }

         //new Reporter("listadocjp.jasper").listar(m);
     }

    private void imprimirDiagnostico(Date fechadesde, Date fechahasta, Clientes cli, float ponderador) {

        List<Episodios> lepi;
        DataParameter dp = new DataParameter();

        int cuenta = 0;

        dp.set("fechadesde", fechadesde);
        dp.set("fechahasta", fechahasta);
        dp.set("cliente", cli);

        UIProgreso uip = new UIProgreso(null, false);
        uip.setMensaje("INICIALIZANDO INFORME");
        uip.setCantidadTareas(5);
        uip.setProgreso(1);
        uip.setVisible(true);

        lepi = (List<Episodios>) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodiosPorRango", dp);

        uip.setMensaje("GENERAR REPORTE");


        int fun_tot = lepi.size();
        uip.setCantidadTareas(fun_tot);
        int fun_tot_hom = 0;
        int fun_tot_muj = 0;

        int fun_hom_18 = 0;
        int fun_muj_18 = 0;
        int fun_hom_40 = 0;
        int fun_muj_40 = 0;
        int fun_hom_50 = 0;
        int fun_muj_50 = 0;

        int tal_hom_170 = 0;
        int tal_muj_170 = 0;
        int tal_hom_175 = 0;
        int tal_muj_175 = 0;
        int tal_hom_180 = 0;
        int tal_muj_180 = 0;
        int tal_hom_185 = 0;
        int tal_muj_185 = 0;

        int fr_tab_hom = 0;
        int fr_tab_muj = 0;

        int fr_hta_hom_18 = 0;
        int fr_hta_muj_18 = 0;
        int fr_hta_hom_40 = 0;
        int fr_hta_muj_40 = 0;
        int fr_hta_hom_50 = 0;
        int fr_hta_muj_50 = 0;

        int fr_sed_hom = 0;
        int fr_sed_muj = 0;

        int fr_imc_hom_ade_18 = 0;
        int fr_imc_muj_ade_18 = 0;
        int fr_imc_hom_ade_40 = 0;
        int fr_imc_muj_ade_40 = 0;
        int fr_imc_hom_ade_50 = 0;
        int fr_imc_muj_ade_50 = 0;
        int fr_imc_hom_sal_18 = 0;
        int fr_imc_muj_sal_18 = 0;
        int fr_imc_hom_sal_40 = 0;
        int fr_imc_muj_sal_40 = 0;
        int fr_imc_hom_sal_50 = 0;
        int fr_imc_muj_sal_50 = 0;
        int fr_imc_hom_sob_18 = 0;
        int fr_imc_muj_sob_18 = 0;
        int fr_imc_hom_sob_40 = 0;
        int fr_imc_muj_sob_40 = 0;
        int fr_imc_hom_sob_50 = 0;
        int fr_imc_muj_sob_50 = 0;
        int fr_imc_hom_obe_18 = 0;
        int fr_imc_muj_obe_18 = 0;
        int fr_imc_hom_obe_40 = 0;
        int fr_imc_muj_obe_40 = 0;
        int fr_imc_hom_obe_50 = 0;
        int fr_imc_muj_obe_50 = 0;

        int med_pap_con = 0;
        int med_pap_sin = 0;
        int med_mam_con = 0;
        int med_mam_sin = 0;

        int exa_odo_sin = 0;
        int exa_odo_con = 0;
        int exa_odo_neg = 0;

        int exa_gli_nom = 0;
        int exa_gli_alt = 0;

        int exa_col_nom = 0;
        int exa_col_alt = 0;

        int exa_ori_nom = 0;
        int exa_ori_alt = 0;

        int exa_vdr_nom = 0;
        int exa_vdr_alt = 0;

        int res_tab_18 = 0;
        int res_col_18 = 0;
        int res_hta_18 = 0;
        int res_imc_18 = 0;
        int res_sed_18 = 0;
        int res_gli_18 = 0;

        int res_tab_40 = 0;
        int res_col_40 = 0;
        int res_hta_40 = 0;
        int res_imc_40 = 0;
        int res_sed_40 = 0;
        int res_gli_40 = 0;

        int res_tab_50 = 0;
        int res_col_50 = 0;
        int res_hta_50 = 0;
        int res_imc_50 = 0;
        int res_sed_50 = 0;
        int res_gli_50 = 0;

        for(Episodios e : lepi) {

            Episodios ee = (Episodios) Sesion.getServiceDelegatorRemote().invoke("CargarEpisodio", e.getId());

            if(ee.getId()== 23570) {
                System.out.println("Llegue");
            }
            ++cuenta;
            uip.setProgreso(cuenta);
            uip.repaint();
            Pacientes p = e.getPaciente();
            char sexo = p.getPersonas().getSexo();
            int edad = calcularEdad(p);
            int talla = obtenerTalla(ee);
            int peso  = obtenerPeso(ee);
            boolean fuma = obtenerFuma(ee);
            boolean hta = obtenerHta(ee);
            boolean sedentario = obtenerSedentarismo(ee);
            int posimc = calcularIMC(peso, talla);

            int posodo = estadoOdontologo(ee);

           boolean glicemiaalt = glicemiaAlterada(ee);
           if(glicemiaalt)
               ++exa_gli_alt;
           else
               ++exa_gli_nom;

           boolean colalt = colesterolAlterado(ee);
           if(colalt)
               ++exa_col_alt;
           else
               ++exa_col_nom;

            if(sexo=='M') { // ****** MUJERES ********
                if(talla <= 170)
                    ++tal_hom_170;
                else
                  if(talla <= 175)
                      ++tal_hom_175;
                  else
                    if(talla <= 180)
                        ++tal_hom_180;
                    else
                        ++tal_hom_185;

                if(fuma)
                    ++fr_tab_hom;
                if(sedentario) {
                    ++fr_sed_hom;
                }
                
                ++fun_tot_hom;
                if(edad >=18 && edad <=39) {
                    ++fun_hom_18;
                    if(hta)
                        ++fr_hta_hom_18;
                    if(fuma)
                        ++res_tab_18;
                    if(sedentario)
                        ++res_sed_18;
                    if(glicemiaalt)
                        ++res_gli_18;
                    if(colalt)
                        ++res_col_18;

                if(posimc == 1)
                    ++fr_imc_hom_ade_18;
                else
                    if(posimc == 2)
                        ++fr_imc_hom_sal_18;
                    else
                        if(posimc == 3)
                            ++fr_imc_hom_sob_18;
                        else
                            if(posimc == 4)
                                ++fr_imc_hom_obe_18;

                } else
                    if(edad >=40 && edad <=49) {
                        ++fun_hom_40;
                        if(hta)
                            ++fr_hta_hom_40;
                        if(fuma)
                            ++res_tab_40;
                        if(sedentario)
                            ++res_sed_40;
                        if(glicemiaalt)
                            ++res_gli_40;
                        if(colalt)
                            ++res_col_40;
                        if(posimc == 1)
                            ++fr_imc_hom_ade_40;
                        else
                            if(posimc == 2)
                                ++fr_imc_hom_sal_40;
                            else
                                if(posimc == 3)
                                    ++fr_imc_hom_sob_40;
                                else
                                    if(posimc == 4)
                                        ++fr_imc_hom_obe_40;
                    } else {
                        ++fun_hom_50;
                        if(hta)
                            ++fr_hta_hom_50;
                        if(fuma)
                            ++res_tab_50;
                        if(sedentario)
                            ++res_sed_50;
                        if(glicemiaalt)
                            ++res_gli_50;
                        if(colalt)
                            ++res_col_50;
                        if(posimc == 1)
                            ++fr_imc_hom_ade_50;
                        else
                            if(posimc == 2)
                                ++fr_imc_hom_sal_50;
                            else
                                if(posimc == 3)
                                    ++fr_imc_hom_sob_50;
                                else
                                    if(posimc == 4)
                                        ++fr_imc_hom_obe_50;

                      }
            } else {       // ****** MUJERES ********
                if(talla <= 170)
                    ++tal_muj_170;
                else
                  if(talla <= 175)
                      ++tal_muj_175;
                  else
                    if(talla <= 180)
                        ++tal_muj_180;
                    else
                        ++tal_muj_185;
                if(fuma)
                    ++fr_tab_muj;
                if(sedentario)
                    ++fr_sed_muj;
                ++fun_tot_muj;
                if(edad >=18 && edad <=39) {
                    ++fun_muj_18;
                    if(hta)
                        ++fr_hta_muj_18;
                    if(fuma)
                        ++res_tab_18;
                    if(sedentario)
                          ++res_sed_18;
                    if(colalt)
                        ++res_col_18;
                    if(glicemiaalt)
                          ++res_gli_18;
                    if(posimc == 1)
                        ++fr_imc_muj_ade_18;
                    else
                        if(posimc == 2)
                            ++fr_imc_muj_sal_18;
                        else
                            if(posimc == 3)
                                ++fr_imc_muj_sob_18;
                            else
                                if(posimc == 4)
                                    ++fr_imc_muj_obe_18;

                } else
                    if(edad >=40 && edad <=49) {
                        ++fun_muj_40;
                        if(hta)
                            ++fr_hta_muj_40;
                        if(fuma)
                            ++res_tab_40;
                        if(sedentario)
                            ++res_sed_40;
                        if(glicemiaalt)
                            ++res_gli_40;
                        if(colalt)
                            ++res_col_40;
                        if(posimc == 1)
                            ++fr_imc_muj_ade_40;
                        else
                            if(posimc == 2)
                                ++fr_imc_muj_sal_40;
                            else
                                if(posimc == 3)
                                    ++fr_imc_muj_sob_40;
                                else
                                    if(posimc == 4)
                                    ++fr_imc_muj_obe_40;
                    } else {
                        ++fun_muj_50;
                        if(hta)
                            ++fr_hta_muj_50;
                        if(fuma)
                            ++res_tab_50;
                        if(hta)
                            ++fr_hta_muj_50;
                        if(colalt)
                            ++res_col_50;
                        if(glicemiaalt)
                            ++res_gli_50;
                        if(sedentario)
                            ++res_sed_50;
                        if(posimc == 1)
                            ++fr_imc_muj_ade_50;
                        else
                            if(posimc == 2)
                                ++fr_imc_muj_sal_50;
                            else
                                if(posimc == 3)
                                    ++fr_imc_muj_sob_50;
                                else
                                    if(posimc == 4)
                                    ++fr_imc_muj_obe_50;
                      }

                if(tienePAP(ee))
                    ++med_pap_con;
                else
                    ++med_pap_sin;

                if(tieneMAMO(ee))
                    ++med_mam_con;
                else
                    ++med_mam_sin;

              }

           if(posodo==1 || posodo==0)
               ++exa_odo_sin;
           else
               if(posodo==2)
                   ++exa_odo_con;
               else
                   ++exa_odo_neg;

           if(orinaAlterada(ee))
               ++exa_ori_alt;
           else
               ++exa_ori_nom;

           if(vdrlAlterado(ee))
               ++exa_vdr_alt;
           else
               ++exa_vdr_nom;
        }

        res_imc_18 = fr_imc_hom_ade_18 + fr_imc_muj_ade_18 +
                     fr_imc_hom_sob_18 + fr_imc_muj_sob_18 +
                     fr_imc_hom_obe_18 + fr_imc_muj_obe_18;

        res_imc_40 = fr_imc_hom_ade_40 + fr_imc_muj_ade_40 +
                     fr_imc_hom_sob_40 + fr_imc_muj_sob_40 +
                     fr_imc_hom_obe_40 + fr_imc_muj_obe_40;

        res_imc_50 = fr_imc_hom_ade_50 + fr_imc_muj_ade_50 +
                     fr_imc_hom_sob_50 + fr_imc_muj_sob_50 +
                     fr_imc_hom_obe_50 + fr_imc_muj_obe_50;

        res_hta_18 = fr_hta_hom_18 + fr_hta_muj_18;
        res_hta_40 = fr_hta_hom_40 + fr_hta_muj_40;
        res_hta_50 = fr_hta_hom_50 + fr_hta_muj_50;


        uip.setVisible(false);

        HashMap<String, Object> hm = new HashMap<String, Object>();

        hm.put("fun_tot", Math.round(fun_tot * ponderador));
        hm.put("fun_tot_hom", Math.round(fun_tot_hom * ponderador));
        hm.put("fun_tot_muj", Math.round(fun_tot_muj * ponderador));

        hm.put("fun_hom_18", Math.round(fun_hom_18 * ponderador));
        hm.put("fun_muj_18", Math.round(fun_muj_18 * ponderador));
        hm.put("fun_hom_40", Math.round(fun_hom_40 * ponderador));
        hm.put("fun_muj_40", Math.round(fun_muj_40 * ponderador));
        hm.put("fun_hom_50", Math.round(fun_hom_50 * ponderador));
        hm.put("fun_muj_50", Math.round(fun_muj_50 * ponderador));

        hm.put("tal_hom_170", Math.round(tal_hom_170 * ponderador));
        hm.put("tal_muj_170", Math.round(tal_muj_170 * ponderador));
        hm.put("tal_hom_175", Math.round(tal_hom_175 * ponderador));
        hm.put("tal_muj_175", Math.round(tal_muj_175 * ponderador));
        hm.put("tal_hom_180", Math.round(tal_hom_180 * ponderador));
        hm.put("tal_muj_180", Math.round(tal_muj_180 * ponderador));
        hm.put("tal_hom_185", Math.round(tal_hom_185 * ponderador));
        hm.put("tal_muj_185", Math.round(tal_muj_185 * ponderador));

        hm.put("fr_tab_hom", Math.round(fr_tab_hom * ponderador));
        hm.put("fr_tab_muj", Math.round(fr_tab_muj * ponderador));

        hm.put("fr_hta_hom_18", Math.round(fr_hta_hom_18 * ponderador));
        hm.put("fr_hta_muj_18", Math.round(fr_hta_muj_18 * ponderador));
        hm.put("fr_hta_hom_40", Math.round(fr_hta_hom_40 * ponderador));
        hm.put("fr_hta_muj_40", Math.round(fr_hta_muj_40 * ponderador));
        hm.put("fr_hta_hom_50", Math.round(fr_hta_hom_50 * ponderador));
        hm.put("fr_hta_muj_50", Math.round(fr_hta_muj_50 * ponderador));

        hm.put("fr_sed_hom", Math.round(fr_sed_hom * ponderador));
        hm.put("fr_sed_muj", Math.round(fr_sed_muj * ponderador));

        hm.put("fr_imc_hom_ade_18", Math.round(fr_imc_hom_ade_18 * ponderador));
        hm.put("fr_imc_muj_ade_18", Math.round(fr_imc_muj_ade_18 * ponderador));
        hm.put("fr_imc_hom_ade_40", Math.round(fr_imc_hom_ade_40 * ponderador));
        hm.put("fr_imc_muj_ade_40", Math.round(fr_imc_muj_ade_40 * ponderador));
        hm.put("fr_imc_hom_ade_50", Math.round(fr_imc_hom_ade_50 * ponderador));
        hm.put("fr_imc_muj_ade_50", Math.round(fr_imc_muj_ade_50 * ponderador));
        hm.put("fr_imc_hom_sal_18", Math.round(fr_imc_hom_sal_18 * ponderador));
        hm.put("fr_imc_muj_sal_18", Math.round(fr_imc_muj_sal_18 * ponderador));
        hm.put("fr_imc_hom_sal_40", Math.round(fr_imc_hom_sal_40 * ponderador));
        hm.put("fr_imc_muj_sal_40", Math.round(fr_imc_muj_sal_40 * ponderador));
        hm.put("fr_imc_hom_sal_50", Math.round(fr_imc_hom_sal_50 * ponderador));
        hm.put("fr_imc_muj_sal_50", Math.round(fr_imc_muj_sal_50 * ponderador));
        hm.put("fr_imc_hom_sob_18", Math.round(fr_imc_hom_sob_18 * ponderador));
        hm.put("fr_imc_muj_sob_18", Math.round(fr_imc_muj_sob_18 * ponderador));
        hm.put("fr_imc_hom_sob_40", Math.round(fr_imc_hom_sob_40 * ponderador));
        hm.put("fr_imc_muj_sob_40", Math.round(fr_imc_muj_sob_40 * ponderador));
        hm.put("fr_imc_hom_sob_50", Math.round(fr_imc_hom_sob_50 * ponderador));
        hm.put("fr_imc_muj_sob_50", Math.round(fr_imc_muj_sob_50 * ponderador));
        hm.put("fr_imc_hom_obe_18", Math.round(fr_imc_hom_obe_18 * ponderador));
        hm.put("fr_imc_muj_obe_18", Math.round(fr_imc_muj_obe_18 * ponderador));
        hm.put("fr_imc_hom_obe_40", Math.round(fr_imc_hom_obe_40 * ponderador));
        hm.put("fr_imc_muj_obe_40", Math.round(fr_imc_muj_obe_40 * ponderador));
        hm.put("fr_imc_hom_obe_50", Math.round(fr_imc_hom_obe_50 * ponderador));
        hm.put("fr_imc_muj_obe_50", Math.round(fr_imc_muj_obe_50 * ponderador));

        hm.put("med_pap_con", Math.round(med_pap_con * ponderador));
        hm.put("med_pap_sin", Math.round(med_pap_sin * ponderador));
        hm.put("med_mam_con", Math.round(med_mam_con * ponderador));
        hm.put("med_mam_sin", Math.round(med_mam_sin * ponderador));

        hm.put("exa_odo_sin", Math.round(exa_odo_sin * ponderador));
        hm.put("exa_odo_con", Math.round(exa_odo_con * ponderador));
        hm.put("exa_odo_neg", Math.round(exa_odo_neg * ponderador));

        hm.put("exa_gli_nom", Math.round(exa_gli_nom * ponderador));
        hm.put("exa_gli_alt", Math.round(exa_gli_alt * ponderador));

        hm.put("exa_col_nom", Math.round(exa_col_nom * ponderador));
        hm.put("exa_col_alt", Math.round(exa_col_alt * ponderador));

        hm.put("exa_ori_nom", Math.round(exa_ori_nom * ponderador));
        hm.put("exa_ori_alt", Math.round(exa_ori_alt * ponderador));

        hm.put("exa_vdr_nom", Math.round(exa_vdr_nom * ponderador));
        hm.put("exa_vdr_alt", Math.round(exa_vdr_alt * ponderador));

        hm.put("res_tab_18", Math.round(res_tab_18 * ponderador));
        hm.put("res_col_18", Math.round(res_col_18 * ponderador));
        hm.put("res_hta_18", Math.round(res_hta_18 * ponderador));
        hm.put("res_imc_18", Math.round(res_imc_18 * ponderador));
        hm.put("res_sed_18", Math.round(res_sed_18 * ponderador));
        hm.put("res_gli_18", Math.round(res_gli_18 * ponderador));

        hm.put("res_tab_40", Math.round(res_tab_40 * ponderador));
        hm.put("res_col_40", Math.round(res_col_40 * ponderador));
        hm.put("res_hta_40", Math.round(res_hta_40 * ponderador));
        hm.put("res_imc_40", Math.round(res_imc_40 * ponderador));
        hm.put("res_sed_40", Math.round(res_sed_40 * ponderador));
        hm.put("res_gli_40", Math.round(res_gli_40 * ponderador));

        hm.put("res_tab_50", Math.round(res_tab_50 * ponderador));
        hm.put("res_col_50", Math.round(res_col_50 * ponderador));
        hm.put("res_hta_50", Math.round(res_hta_50 * ponderador));
        hm.put("res_imc_50", Math.round(res_imc_50 * ponderador));
        hm.put("res_sed_50", Math.round(res_sed_50 * ponderador));
        hm.put("res_gli_50", Math.round(res_gli_50 * ponderador));

        hm.put("empresa", cli.getNombre());

        new Reporter("diagnosticosanitario.jasper").listar(hm);
    }

    private int calcularEdad(Pacientes p) {

        Calendar hoy = Calendar.getInstance();
        Calendar fechaNac = Calendar.getInstance();

        fechaNac.setTime(p.getPersonas().getFechanacimiento());

        int anios = hoy.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);

        
        return(anios);
    }

    private int obtenerTalla(Episodios ee) {

        int talla = 0;

        Collection <Episodioexamenfisico> eefcol = ee.getEpisodioexamenfisicoCollection();

        for(Episodioexamenfisico eef : eefcol) {
            if(eef.getExamenfisico().getId()==2) {
                talla = eef.getValor();
                break;
            }
        }

        return(talla);
    }

    private int obtenerPeso(Episodios ee) {
        int peso = 0;

        Collection <Episodioexamenfisico> eefcol = ee.getEpisodioexamenfisicoCollection();

        for(Episodioexamenfisico eef : eefcol) {
            if(eef.getExamenfisico().getId()==1) {
                peso = eef.getValor();
                break;
            }
        }

        return(peso);
    }

    private boolean obtenerFuma(Episodios ee) {
        boolean fuma = false;

        Collection <Episodiosvicios> eefcol = ee.getEpisodiosviciosCollection();

        for(Episodiosvicios eef : eefcol) {
            if(eef.getViciossociales().getId()==2) {
                fuma = true;
                break;
            }
        }

        return(fuma);
    }

    private boolean obtenerHta(Episodios ee) {


        boolean hta = false;

        Episodiopresion ep = ee.getEpisodiopresion();

        if(ep!=null) {

            int dst = ep.getDiastolicasegundatoma();
            int sst = ep.getSistolicasegundatoma();

            if(sst > 140 ||
               dst > 90) {
                hta = true;
            }
        }
        return(hta);
    }

    private boolean obtenerSedentarismo(Episodios ee) {

       boolean sedentario = true;

        Collection <Episodiosestudios> eefcol = ee.getEpisodiosestudiosCollection();

        for(Episodiosestudios eef : eefcol) {
            if(eef.getEstudios().getId()==99) {
                sedentario = false;
                break;
            }
        }

       return(sedentario);
    }

    private int calcularIMC(int peso, int talla) {

        float i = 0;
        int pos = 0;

        if(talla!=0)
         i = peso / (2 * talla / 100);
        if(i < 18.5)
            pos = 1;
        else
            if(i >=18.5 && i < 25)
                pos = 2;
            else
                if(i >=25 && i < 30)
                    pos = 3;
                else
                    if(i >=30 && i < 35)
                        pos = 4;
                    else
                        pos = 5;

        return(pos);
    }

    private boolean tienePAP(Episodios ee) {

        boolean tiene = false;

        Collection <Episodiosestudios> eefcol = ee.getEpisodiosestudiosCollection();

        for(Episodiosestudios eef : eefcol) {
            if(eef.getEstudios().getId()==21) {
                tiene= true;
                break;
            }
        }

        return(tiene);
    }

    private boolean tieneMAMO(Episodios ee) {

        boolean tiene = false;

        Collection <Episodiosestudios> eefcol = ee.getEpisodiosestudiosCollection();

        for(Episodiosestudios eef : eefcol) {
            if(eef.getEstudios().getId()==22) {
                tiene= true;
                break;
            }
        }
        
        return(tiene);
    }

    private int estadoOdontologo(Episodios ee) {

        int pos = 1;
        boolean negado = false;

        int caries = 0, extracciones = 0;
        Collection <Episodioodontologo> eefcol = ee.getEpisodioodontologoCollection();

        for(Episodioodontologo eef : eefcol) {

            int enfe = eef.getEnfermedades().getId();
            if(enfe == 24 || enfe == 25)
                ++caries;
            if(enfe == 27)
                ++extracciones;

            if(eef.getLesionblanca()=='S' || eef.getPatologiamaligna()=='S') {
                negado = true;
                break;
            }
        }

        if(negado)
            pos = 3;
        else
            if((extracciones == 1 && caries >= 6) ||
               (caries > 6) ||
               (extracciones >=2)) {
                pos = 2;
            }

        return(pos);
    }

    private boolean glicemiaAlterada(Episodios une) {

        boolean alterada = false;


        Collection <Estudioshechos> eefcol = une.getEstudioshechosCollection();

        for(Estudioshechos ee : eefcol) {

            if(ee.getEstudio().getId()==1) {

                Collection <Analisishechos> aecol = ee.getAnalisishechosCollection();
                for(Analisishechos ae : aecol) {
                    int idanalisis = ae.getAnalisis().getId();
                    if(idanalisis==1) {
                        alterada = analisisAlterado(ae);
                        break;
                    }
                 }
                break;
              }   
            }
        return(alterada);
    }

    private boolean orinaAlterada(Episodios une) {

        boolean alterada = false;


        Collection <Estudioshechos> eefcol = une.getEstudioshechosCollection();

        for(Estudioshechos ee : eefcol) {

            if(ee.getEstudio().getId()==4) {

                Collection <Analisishechos> aecol = ee.getAnalisishechosCollection();
                for(Analisishechos ae : aecol) {
                        alterada = analisisAlterado(ae);
                        if(alterada)
                           break;
                 }
                  break;
                }
            }
        return(alterada);
    }

    private boolean colesterolAlterado(Episodios une) {

        boolean alterada = true;


        Collection <Estudioshechos> eefcol = une.getEstudioshechosCollection();

        for(Estudioshechos ee : eefcol) {

            if(ee.getEstudio().getId()==2) {

                Collection <Analisishechos> aecol = ee.getAnalisishechosCollection();
                for(Analisishechos ae : aecol) {

                    int idanalisis = ae.getAnalisis().getId();

                    if(idanalisis==2) {
                      alterada = analisisAlterado(ae);
                      break;
                    }
                 }
                  break;
                }
            }
        return(alterada);
    }

    private boolean vdrlAlterado(Episodios une) {

        boolean alterada = false;


        Collection <Estudioshechos> eefcol = une.getEstudioshechosCollection();

        for(Estudioshechos ee : eefcol) {

            if(ee.getEstudio().getId()==3) {

                Collection <Analisishechos> aecol = ee.getAnalisishechosCollection();
                for(Analisishechos ae : aecol) {

                    int idanalisis = ae.getAnalisis().getId();
                    if(idanalisis==3) {
                        alterada = analisisAlterado(ae);
                        break;
                    }                 
                }
                break;
             }
            }
        return(alterada);
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

    private boolean analisisAlterado(Analisishechos ae) {

          boolean alterado = false;
          String vfm, gvh;
          
                Analisis a = ae.getAnalisis();
                float minimo = 0, maximo = 999999, hallado = 0;
                if(a.getTiporesultado()=='N') {

                    minimo = convertirResultado(a.getValorrefminimo(), MINIMO);
                    maximo = convertirResultado(a.getValorrefmaximo(), MAXIMO);
                    hallado= convertirResultado(ae.getValorhallado(), MINIMO);
                    if(hallado >= minimo && hallado <=maximo)
                        alterado = false;
                    else
                        alterado = true;
                } else
                  if(a.getTiporesultado()=='T') {

                       vfm = a.getValorrefminimo();
                       gvh = ae.getValorhallado();
                       Boolean res = new Boolean(gvh);
                       if(res.booleanValue()==true && a.getId()!= 21 && a.getId()!= 22)
                           alterado = true;
                  }
                if(a.getTiporesultado()== 'L') {
                       vfm = a.getValorrefminimo();
                       gvh = ae.getValorhallado();
                       if(!vfm.equals(gvh))
                           alterado = true;
                }

          return(alterado);
    }

    private Float convertirResultado(String numero, short tipo) {

        Float res = null;

        try {
            res = Float.parseFloat(purgar(numero));
        } catch(Exception ex) {

            if(tipo==MAXIMO)
                res = 999999F;
            else
                res = 0F;
        }
        return(res);
    }
}
