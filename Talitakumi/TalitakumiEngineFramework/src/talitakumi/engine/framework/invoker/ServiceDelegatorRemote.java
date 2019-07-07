package talitakumi.engine.framework.invoker;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import javax.ejb.Remote;

/**
 *
 * @author rupertus a
 */
@Remote
public interface ServiceDelegatorRemote {

    Object invoke(String nombre, Object parametros);
    public void setVariableEntorno(String nombre, Object valor);
    public Object getVariableEntorno(String nombre);
}
