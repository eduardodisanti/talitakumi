/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kcreativa.talitakumi.talitakumiwebapp.pages.agenda;

import com.kcreativa.talitakumi.talitakumiwebapp.services.ISesionWeb;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author rupertus
 */

@IncludeStylesheet("context:layout.css")
public class DatosPersonales {

    @Property
    private String empresa;

    @Inject
    private ISesionWeb sesion;

    public DatosPersonales() {

        empresa = (String) sesion.getVariable("empresa");
    }

}
