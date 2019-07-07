/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talitakumi.servicios.libretadeconducir;

import talitakumi.model.core.Episodios;
import talitakumi.model.core.Libretasdeconducir;

/**
 *
 * @author rupertus
 */
public class ServicioCalculadorVigencia {
    private

    int edad;
    private boolean criterioMedico;
    private int maximavigencia;
    private int codigo1;
    private int codigo2;
    private int codigo3;
    private String categoria;
    private Libretasdeconducir libreta;
    private Episodios episodio;
    private boolean usarsegundatomapresiones;

    /**
     * @return the edad
     */

    public ServicioCalculadorVigencia() {

        edad = 18;
        criterioMedico = false;
        categoria = "A1";
        codigo1 = codigo2 = codigo3 = 0;
    }

    public void calcular() {

        maximavigencia = 10;
        boolean aspirante = libreta.getTipo()==1;
        int cantidadcodigos = 0;
        float totalvision = this.episodio.getEpisodioagudezasvisuales().getCcod() +
                            this.episodio.getEpisodioagudezasvisuales().getCcoi();
        
        float visioncorregidacc = this.episodio.getEpisodioagudezasvisuales().getCcod() +
                                  this.episodio.getEpisodioagudezasvisuales().getCcoi();

        float visioncorregidaclc = this.episodio.getEpisodioagudezasvisuales().getClentescontactood() +
                                   this.episodio.getEpisodioagudezasvisuales().getClentescontactooi();
        
        float visioncorregidacli = this.episodio.getEpisodioagudezasvisuales().getClentesintraocularesod() +
                                   this.episodio.getEpisodioagudezasvisuales().getClentesintraocularesoi();

        float visioncorregida = visioncorregidacc;
        
        if(visioncorregidaclc > visioncorregida)
            visioncorregida = visioncorregidaclc;
        else
            if(visioncorregidacli > visioncorregida)
                visioncorregida = visioncorregidacli;

        if(visioncorregida > totalvision)
            totalvision = visioncorregida;
        
        int pad = this.episodio.getEpisodiopresion().getDiastolicasegundatoma();
        int pas = this.episodio.getEpisodiopresion().getSistolicasegundatoma();


        if(libreta.getCodigo1()!=0)
            ++cantidadcodigos;
        if(libreta.getCodigo2()!=0)
            ++cantidadcodigos;
        if(libreta.getCodigo3()!=0)
            ++cantidadcodigos;


        codigo1 = libreta.getCodigo1();
        codigo2 = libreta.getCodigo2();
        codigo3 = libreta.getCodigo3();


        int sumacodigos = codigo1 + codigo2 + codigo3;

        boolean existecodigo1 = (codigo1 == 1 || codigo2 == 1|| codigo3 == 1);
        boolean existecodigo2 = (codigo1 == 2 || codigo2 == 2|| codigo3 == 2);
        boolean existecodigo5 = (codigo1 == 5 || codigo2 == 5|| codigo3 == 5);
        boolean existecodigo6 = (codigo1 == 6 || codigo2 == 6|| codigo3 == 6);
        boolean existecodigo7 = (codigo1 == 7 || codigo2 == 7|| codigo3 == 7);
        boolean existecodigo8 = (codigo1 == 8 || codigo2 == 8|| codigo3 == 8);
        boolean existecodigo10 = (codigo1 == 10 || codigo2 == 10|| codigo3 == 10);
        boolean existecodigo11 = (codigo1 == 11 || codigo2 == 11|| codigo3 == 11);
        boolean existecodigo12 = (codigo1 == 12 || codigo2 == 12|| codigo3 == 12);
        boolean existecodigo15 = (codigo1 == 15 || codigo2 == 15|| codigo3 == 15);
        boolean existecodigo16 = (codigo1 == 16 || codigo2 == 16|| codigo3 == 16);
        boolean existecodigo17 = (codigo1 == 17 || codigo2 == 17|| codigo3 == 17);
        boolean existecodigo19 = (codigo1 == 19 || codigo2 == 19|| codigo3 == 19);
        boolean existecodigo20 = (codigo1 == 20 || codigo2 == 20|| codigo3 == 20);
        boolean existecodigo22 = (codigo1 == 22 || codigo2 == 22|| codigo3 == 22);
        boolean existecodigo23 = (codigo1 == 23 || codigo2 == 23|| codigo3 == 23);
        boolean existecodigo27 = (codigo1 == 27 || codigo2 == 27|| codigo3 == 27);
        boolean existecodigo28 = (codigo1 == 28 || codigo2 == 28|| codigo3 == 28);
        boolean existecodigo29 = (codigo1 == 29 || codigo2 == 29|| codigo3 == 29);
        boolean existecodigo30 = (codigo1 == 30 || codigo2 == 30|| codigo3 == 30);
        boolean existecodigo35 = (codigo1 == 35 || codigo2 == 35|| codigo3 == 35);
        boolean existecodigo38 = (codigo1 == 38 || codigo2 == 38|| codigo3 == 38);
        boolean existecodigo39 = (codigo1 == 39 || codigo2 == 39|| codigo3 == 39);
        boolean existecodigo40 = (codigo1 == 40 || codigo2 == 40|| codigo3 == 40);
        boolean existecodigo41 = (codigo1 == 41 || codigo2 == 41|| codigo3 == 41);



        criterioMedico = false;


        if(!aspirante) {

            if(cantidadcodigos==1)
                maximavigencia = 5;

            if(edad <= 59 && cantidadcodigos==0)
                maximavigencia = 10;
            
            if(existecodigo2 && edad <= 40) {

                criterioMedico = true;
                maximavigencia = 10;
            }

            if(edad > 59 && edad <= 69 && (sumacodigos==12))
                maximavigencia = 5;

            if(edad > 69 && edad <= 75 && (sumacodigos==12))
                maximavigencia = 3;

            if(existecodigo1 &&
               (codigo1 == 28 || codigo2 == 28|| codigo3 == 28))
                maximavigencia = 5;

            if(existecodigo1 &&
                (totalvision >= 0.8 && totalvision <= 1.2)) {
                criterioMedico = true;
                maximavigencia = 5;
            }
            
            if(existecodigo1 && visioncorregida >= 1.4)
                maximavigencia = 5;
            else 
                if(existecodigo1 && visioncorregida >= 1.2)
                   maximavigencia = 3;
                else
                    if(existecodigo1 && visioncorregida < 0.8)
                       maximavigencia = 2;

            if((existecodigo8 || existecodigo10 || existecodigo11 || existecodigo23 || existecodigo38
                             || existecodigo16 || existecodigo17 || existecodigo19 || existecodigo20|| existecodigo22)
                 && maximavigencia >= 10) {
                maximavigencia = 10;
                criterioMedico = true;
            }

            if(existecodigo41 && (episodio.getEpisodioagudezasvisuales().getClentescontactood()!=0 ||
                                  episodio.getEpisodioagudezasvisuales().getClentescontactooi()!=0 )
                              && maximavigencia >= 10)
                maximavigencia = 10;

            if(existecodigo2 && pas < 150 && existecodigo2 && pad < 90 && maximavigencia >= 10)
                maximavigencia = 10;
            
            if((pas >= 150 && pas < 180 || pad >= 90 && pad < 110) && maximavigencia >= 5)
                    maximavigencia = 5;
            else
                if(pas >= 180 || pad >= 110)
                    maximavigencia = 2;
            

            if(existecodigo2 && edad >= 40 && maximavigencia >= 5) {

                criterioMedico = true;
                maximavigencia = 5;
            }

            if(existecodigo6 && maximavigencia >= 5) {
                criterioMedico = false;
                maximavigencia = 5;
            }

            if((existecodigo12 || existecodigo15)  && maximavigencia >= 5) {
                maximavigencia = 5;
                criterioMedico = false;
            }

            if((existecodigo30 || existecodigo5) && maximavigencia >= 5){
                maximavigencia = 5;
                criterioMedico = false;
            }

            if((existecodigo30 && existecodigo5)){
                
                if(this.episodio.getEpisodioagudezasauditivas().getAudifono()=='B')
                    maximavigencia = 2;
                else
                    maximavigencia = 5;
                criterioMedico = false;
            }

            if(existecodigo5 && this.episodio.getEpisodioagudezasauditivas().getGrado() <= 1  && maximavigencia >= 5)
                maximavigencia = 5;
            else
                if(existecodigo5 && this.episodio.getEpisodioagudezasauditivas().getGrado() >= 2) {
                   maximavigencia = 2;
                   criterioMedico = false;
                }

                        
            if(existecodigo39 || existecodigo40 && maximavigencia >= 3) {
                
                maximavigencia = 3;
                criterioMedico = false;
            }


            if(existecodigo27)
                maximavigencia = 2;

            if(existecodigo1 &&
               (codigo1 == 28 || codigo2 == 28|| codigo3 == 28))
                maximavigencia = 2;
            
            if(existecodigo7)
                maximavigencia = 2;

            if(existecodigo28 )
                maximavigencia = 2;

            if(existecodigo15 && existecodigo1 && (totalvision < 0.8)) {

                maximavigencia=0;
            }
    
        } else
            {
                if(aspirante)
                    maximavigencia = 2;
                if(existecodigo1)
                    maximavigencia = 1;
                // CAMBIADO DR. BARANANO . SOLO LIMITA CODIGO 1
                //if(cantidadcodigos > 0)
                //    maximavigencia = 1;
            }

    }

    public void setEpisodio(Episodios epis) {
       this.episodio = epis;
    }


    public void setLibreta(Libretasdeconducir l) {

          libreta = l;
    }

    public int getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * @return the criterioMedico
     */
    public boolean isCriterioMedico() {
        return criterioMedico;
    }

    /**
     * @param criterioMedico the criterioMedico to set
     */
    public void setCriterioMedico(boolean criterioMedico) {
        this.criterioMedico = criterioMedico;
    }

    /**
     * @return the maximavigencia
     */
    public int getMaximavigencia() {
        return maximavigencia;
    }

    /**
     * @param maximavigencia the maximavigencia to set
     */
    public void setMaximavigencia(int maximavigencia) {
        this.maximavigencia = maximavigencia;
    }

    /**
     * @return the codigo1
     */
    public int getCodigo1() {
        return codigo1;
    }

    /**
     * @param codigo1 the codigo1 to set
     */
    public void setCodigo1(int codigo1) {
        this.codigo1 = codigo1;
    }

    /**
     * @return the codigo2
     */
    public int getCodigo2() {
        return codigo2;
    }

    /**
     * @param codigo2 the codigo2 to set
     */
    public void setCodigo2(int codigo2) {
        this.codigo2 = codigo2;
    }

    /**
     * @return the codigo3
     */
    public int getCodigo3() {
        return codigo3;
    }

    /**
     * @param codigo3 the codigo3 to set
     */
    public void setCodigo3(int codigo3) {
        this.codigo3 = codigo3;
    }

    /**
     * @return the categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the usarsegundatomapresiones
     */
    public boolean isUsarsegundatomapresiones() {
        return usarsegundatomapresiones;
    }

    /**
     * @param usarsegundatomapresiones the usarsegundatomapresiones to set
     */
    public void setUsarsegundatomapresiones(boolean usarsegundatomapresiones) {
        this.usarsegundatomapresiones = usarsegundatomapresiones;
    }

}
