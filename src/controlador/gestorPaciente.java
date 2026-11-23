package controlador;

import modelo.Paciente;

public class gestorPaciente {

    // Arreglo para guardar los pacientes y contador de cuantos llevamos registrados
    private Paciente[] pacientes;
    private int cantidadPacientes;
    private Ordenador ordenador;

    // Constructor: definimos un tamaño maximo (por ejemplo 50 pacientes)
    public gestorPaciente(int capacidadMaxima) {
        this.pacientes = new Paciente[capacidadMaxima];
        this.cantidadPacientes = 0;
        this.ordenador = new Ordenador();
    }

    public int getCantidadPacientes() {
        return cantidadPacientes;
    }

    public Ordenador getOrdenador() {
        return ordenador;
    }

    // Agrega un nuevo paciente al arreglo si todavia hay espacio
    public boolean registrarPaciente(int numeroPaciente, String nombrePaciente, int edadPaciente, String espeMedica, int numeroConsultas) {
        if (cantidadPacientes >= pacientes.length) {
            return false; // Arreglo lleno
        }

        Paciente nuevo = new Paciente(numeroPaciente, nombrePaciente, edadPaciente, espeMedica, numeroConsultas);
        pacientes[cantidadPacientes] = nuevo;
        cantidadPacientes++;
        return true;
    }

    // Crea una copia solo con los pacientes registrados para no desordenar el arreglo original
    private Paciente[] copiarPacientesRegistrados() {
        Paciente[] copia = new Paciente[cantidadPacientes];
        for (int i = 0; i < cantidadPacientes; i++) {
            copia[i] = pacientes[i];
        }
        return copia;
    }

    // Muestra un arreglo de pacientes en consola de forma facil de leer
    public void mostrarLista(Paciente[] lista, int total, String titulo) {
        System.out.println("\n=== " + titulo + " ===");
        if (total == 0) {
            System.out.println("No hay pacientes registrados todavia.");
            return;
        }

        for (int i = 0; i < total; i++) {
            Paciente p = lista[i];
            System.out.println(
                (i + 1) + ". Expediente: " + p.getNumeroPaciente()
                + " | Nombre: " + p.getNombrePaciente()
                + " | Edad: " + p.getEdadPaciente()
                + " | Especialidad: " + p.getEspeMedica()
                + " | Consultas: " + p.getNumeroConsultas()
            );
        }
    }

    // Muestra el catalogo tal cual esta guardado antes de ordenar
    public void mostrarCatalogoOriginal() {
        mostrarLista(pacientes, cantidadPacientes, "CATALOGO ORIGINAL DE PACIENTES");
    }

    // Ejecuta el algoritmo elegido sobre una copia y devuelve el arreglo ordenado
    public Paciente[] ordenarCatalogo(int opcionAlgoritmo, String criterio, boolean ascendente) {
        Paciente[] copia = copiarPacientesRegistrados();

        if (opcionAlgoritmo == 1) {
            ordenador.ordenamientoBurbuja(copia, cantidadPacientes, criterio, ascendente);
        } else if (opcionAlgoritmo == 2) {
            ordenador.ordenamientoSeleccion(copia, cantidadPacientes, criterio, ascendente);
        } else if (opcionAlgoritmo == 3) {
            ordenador.insercionSort(copia, cantidadPacientes, criterio, ascendente);
        }

        return copia;
    }
}