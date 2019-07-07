/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kcreativa.talitakumi.invoker;

import talitakumi.engine.framework.invoker.Invoker;
import talitakumi.engine.framework.invoker.ServiceDelegatorRemote;
import talitakumi.engine.framework.invoker.sesionweb.ClientSesion;


/**
 *
 * @author rupertus
 */
public class TalitakumiInvoker implements Invoker {

    static private TalitakumiInvoker invoker = null;

    static private ServiceDelegatorRemote sdr;

    public Object invoke(String nombreservicio, Object parametros) throws Exception {

        if(invoker==null) {
            instanciarServiceDelegatorRemote();
        }

        return(sdr.invoke(nombreservicio, parametros));
    }

    public Object invoke(Class unaClase, Object parametros) throws Exception {
        if(invoker==null) {
            instanciarServiceDelegatorRemote();
        }

        //return(sdr.invoke(unaClase, parametros));
        return(null);
    }

    private void instanciarServiceDelegatorRemote() {

        invoker = new TalitakumiInvoker();
        sdr = (ServiceDelegatorRemote) ClientSesion.getVariableEntorno("ServiceDelegator");
    }
}
