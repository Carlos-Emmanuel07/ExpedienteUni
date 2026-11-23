package modelo;

public class Paciente {

    private int numeroPaciente;
    private String nombrePaciente;
    private int edadPaciente;
    private String espeMedica;
    private int numeroConsultas;

    public int getNumeroPaciente() {
        return numeroPaciente;
    }

    public void setNumeroPaciente(int numeroPaciente) {
        this.numeroPaciente = numeroPaciente;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public int getEdadPaciente() {
        return edadPaciente;
    }

    public void setEdadPaciente(int edadPaciente) {
        this.edadPaciente = edadPaciente;
    }

    public String getEspeMedica() {
        return espeMedica;
    }

    public void setEspeMedica(String espeMedica) {
        this.espeMedica = espeMedica;
    }

    public int getNumeroConsultas() {
        return numeroConsultas;
    }

    public void setNumeroConsultas(int numeroConsultas) {
        this.numeroConsultas = numeroConsultas;
    }

    //Constuctor: Sirve para llenar los datos del paciente al momento de crearlo.
    public Paciente(int numeroPaciente, String nombrePaciente, int edadPaciente, String espeMedica, int numeroConsultas){
        this.numeroPaciente = numeroPaciente; //Indica que el dato pertenece a este paciente y no al que acaba de llegar.
        this.nombrePaciente = nombrePaciente;
        this.edadPaciente = edadPaciente;
        this.espeMedica = espeMedica;  
        this.numeroConsultas = numeroConsultas;
    }
}
