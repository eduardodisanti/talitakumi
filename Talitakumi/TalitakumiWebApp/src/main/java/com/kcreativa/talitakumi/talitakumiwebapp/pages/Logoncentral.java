/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kcreativa.talitakumi.talitakumiwebapp.pages;


import com.kcreativa.talitakumi.talitakumiwebapp.pages.consultorio.CarnetDeSaludPage;
import com.kcreativa.talitakumi.talitakumiwebapp.ui.pojos.ElementoMenu;
import java.util.ArrayList;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import talitakumi.engine.framework.invoker.sesionweb.ClientSesion;
import talitakumi.model.core.Usuarios;
/**
 *
 * @author rupertus
 */

@IncludeStylesheet("context:layout.css")
public class Logoncentral {

    @Property
    private ArrayList<ElementoMenu> tareas;

    @Property
    private String empresa;

    @Property
    private ElementoMenu tarea;

    @InjectPage
    private Index index;

    @InjectPage
    private CarnetDeSaludPage cds;

    private Usuarios usr;

	// onActivate() is called by Tapestry to pass in the activation context from the URL.

        public Logoncentral() {
            empresa = (String) ClientSesion.getVariableEntorno("empresa");
            tareas = new ArrayList();
        }

	void welcome() {

            //empresa = (String) sesion.getVariable("empresa");
            usr = (Usuarios) ClientSesion.getVariableEntorno("usuario");
            cargarTareas();
	}

        public void cargarTareas() {

            //tareas = new ArrayList<ElementoMenu>();
            ElementoMenu em;
            String rol = usr.getRol();
            System.out.println("El rol es : " + rol);
            for(int i=0;i<rol.length();i++) {
                char s = rol.charAt(i);
                
                String descripcion="Inhabilitado", url="";
                if(s=='1') {

                        descripcion="Consultorio";
                        url="consultorio";
                } else
                       if(s=='2') {
                        descripcion="Recepcion";
                        url="recepcion";
                       }
                if(url.length() > 0) {
                    em = new ElementoMenu(i, descripcion, url);
                    tareas.add(em);
                    System.out.println("EM =  " + descripcion);
                }
            }
           em = new ElementoMenu(99, "Salir", "Salir");
           tareas.add(em);
        }

   private Object onActionFromMenu(Integer id) {

       Object resp = null;
       switch (id) {

           case 0 :
                   resp = cds;
                   break;
                   
           case 1 :
                   break;

           case 99 :
                    resp = index;
                    break;

       }
       return(resp);
   }

}
