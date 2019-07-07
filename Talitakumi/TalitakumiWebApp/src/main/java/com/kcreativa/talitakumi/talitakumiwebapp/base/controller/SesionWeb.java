/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kcreativa.talitakumi.talitakumiwebapp.base.controller;

import com.kcreativa.talitakumi.talitakumiwebapp.services.*;
import java.util.HashMap;
import talitakumi.engine.framework.invoker.ServiceDelegatorRemote;
/**
 *
 * @author rupertus
 */
public class SesionWeb implements ISesionWeb {

    static private SesionWeb sesion = null;
    static private HashMap data;
    static private ServiceDelegatorRemote sdr;

    /**
     * @return the sdr
     */
    public ServiceDelegatorRemote getServiceDelegator() {
        return sdr;
    }

    /**
     * @param aSdr the sdr to set
     */
    public void setServiceDelegator(ServiceDelegatorRemote aSdr) {
        sdr = aSdr;
    }

    public SesionWeb() {

        data = new HashMap();
    }
    
    @Override
    public void setVariable(String clave, Object o) {

        data.put(clave, o);
    }

    @Override
    public Object getVariable(String clave) {

        return(data.get(clave));
    }

    @Override
    public void deleteVariable(String clave) {

        data.remove(clave);
    }
}
