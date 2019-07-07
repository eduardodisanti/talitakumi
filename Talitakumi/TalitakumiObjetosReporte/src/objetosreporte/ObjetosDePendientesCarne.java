/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package objetosreporte;

/**
 *
 * @author victoria
 */

//Constructor

public class ObjetosDePendientesCarne {


    private int documento;
    private String nombres;
    private String apellidos;
    private boolean vacunas;
    private boolean odontologo;
    private boolean laboratorio;
    private boolean medico;
    private boolean mamografia;
    private boolean pap;




    public ObjetosDePendientesCarne() {

        vacunas = true;
        odontologo = true;
        laboratorio = true;
        medico = true;
        mamografia = true;
        pap = true;

    }


    public int getdocumento() {
        return documento;
    }


    public void setDocumento( int documento) {
        this.documento = documento;
    }


    public String getApellidos() {
        return apellidos;
    }


    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }


    public String getNombres() {
        return nombres;
    }


    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public boolean getVacunas() {
        return vacunas;
    }


    public void setVacunas(boolean vacunas) {
        this.vacunas = vacunas;
    }

    public boolean getPap() {
        return pap;
    }

    public void setPap(boolean pap) {
        this.pap = pap;
    }
    
     public boolean getMamografia() {
        return mamografia;
    }

    public void setMamografia(boolean mamografia) {
        this.mamografia = mamografia;
    }

     public boolean getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(boolean laboratorio) {
        this.laboratorio = laboratorio;
    }

     public boolean getMedico() {
        return medico;
    }

    public void setMedico(boolean medico) {
        this.medico = medico;
    }

     public boolean getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(boolean odontologo) {
        this.odontologo = odontologo;
    }


   }
