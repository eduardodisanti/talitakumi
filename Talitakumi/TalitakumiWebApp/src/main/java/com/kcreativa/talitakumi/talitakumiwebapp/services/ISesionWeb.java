/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kcreativa.talitakumi.talitakumiwebapp.services;

/**
 *
 * @author rupertus
 */
public interface ISesionWeb {

    public void setVariable(String clave, Object o);
    public Object getVariable(String clave);
    public void deleteVariable(String clave);
}
