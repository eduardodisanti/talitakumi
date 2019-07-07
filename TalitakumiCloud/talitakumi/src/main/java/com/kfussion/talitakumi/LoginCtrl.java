/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kfussion.talitakumi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
import org.apache.log4j.Logger;
import talitakumi.framework.Sesion;
import talitakumi.model.core.Consultorios;
import talitakumi.model.core.Usuarios;
import view.UILogin;



/**
 *
 * @author rupertus
 */
public class LoginCtrl {


    UILogin dlg;
    Logger logger;
    boolean permiso;
    private String usuario;
    List<Consultorios> lugares;
    private Consultorios lugarelegido;


    public LoginCtrl() throws Exception {

        permiso = false;
        logger = Sesion.getLogger();

        dlg = new UILogin(null, true);
        dlg.registrarLoginListener(new OKListener());
        dlg.registrarSalirListener(new SalirListener());
        dlg.cargarListaLugares(this.cargarLugares());
        doLogin();
    }


    void disponer() {
       dlg.dispose();
    }

    boolean getPermission() {

        return(permiso);
    }

    public String getUsuario() {

        return(usuario);
    }

    public Consultorios getLugar() {

        return(lugarelegido);
    }

    private Vector<String> cargarLugares() throws Exception {


        lugares = (List) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarLugares", "c.descripcion");

        Vector<String>llugares = new Vector();
        for(Consultorios l : lugares)
            llugares.add(l.getDescripcion());
        return(llugares);
    }

    private String codificarClave(char[] clave) {

        int l = clave.length;
        int i;
        String s = "";

        for(i=0;i<l;i++)
            s = s + clave[i];


        return(s);
    }

    private void doLogin() {

         dlg.setVisible(true);
    }

   private void ejecutarLogin() {

       String claveBD = "";
       String claveDLG = "nones";
       Usuarios u = null;

       try {
                
                usuario = dlg.getUsuario();

          //      lugares = (List) Sesion.getServiceDelegatorRemote().invoke("ServicioCargarLugares", "c.descripcion");
           /*AbstractService as = new ServiceDelegator().getService("CargarUsuario");
           as.setParameters(usuario);*/

                 u = (Usuarios) Sesion.getServiceDelegatorRemote().invoke("CargarUsuario", usuario);
                claveBD = u.getClave();
                char[] clave= dlg.getClave();
                claveDLG = codificarClave(clave);
                int idx = dlg.getLugarElegido();
                lugarelegido = lugares.get(idx);

       }
          catch(Exception e) {
                dlg.mostrarMensaje("No existe el usuario");
          }

       if(!claveBD.equals(claveDLG)) {
           permiso = false;
           dlg.mostrarMensaje("Clave incorrecta o usuario inexistente");
           this.doLogin();
       } else {
                permiso = true;
                Sesion.setNumerousuario(u.getFuncionario());
                Sesion.setNombreUsuario(u.getUsuario());
                Sesion.setNumeromedico(u.getMedico());
                Sesion.putVariableEntorno("usuario", u);
                Sesion.putVariableEntorno("numerofuncionario", u.getFuncionario());
                Sesion.getServiceDelegatorRemote().setVariableEntorno("usuario", u);
                Sesion.getServiceDelegatorRemote().setVariableEntorno("numerofuncionario", u.getFuncionario());
                
                dlg.setVisible(false);
              }
   }

    public class OKListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            ejecutarLogin();
        }
    }

    public class SalirListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            cerrarTodo();
        }

        private void cerrarTodo() {

            permiso = false;
            dlg.setVisible(false);
        }
    }
}
