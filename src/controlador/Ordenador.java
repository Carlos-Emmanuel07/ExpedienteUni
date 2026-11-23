package controlador;

import modelo.Paciente;

public class Ordenador {

    private int comparacionContador;
    private int comparacionIntercambio;
    private String historial;

    public Ordenador() {
        this.comparacionContador = 0;
        this.comparacionIntercambio = 0;
        this.historial = "";
    }

    public int getComparacionContador() {
        return comparacionContador;
    }

    public int getComparacionIntercambio() {
        return comparacionIntercambio;
    }

    public String getHistorial() {
        return historial;
    }

    // Burbuja: compara de dos en dos y va pasando el mayor/menor hacia el final
    public void ordenamientoBurbuja(Paciente[] pacientes, int total, String criterio, boolean ascendente) {
        comparacionContador = 0;
        comparacionIntercambio = 0;
        long inicio = System.nanoTime();

        for (int i = 0; i < total - 1; i++) {
            for (int j = 0; j < total - 1 - i; j++) {
                
                // Revisa si hay que cambiarlos de lugar
                if (debeCambiar(pacientes[j], pacientes[j + 1], criterio, ascendente)) {
                    Paciente aux = pacientes[j];
                    pacientes[j] = pacientes[j + 1];
                    pacientes[j + 1] = aux;
                    comparacionIntercambio++;
                }
            }
        }

        double ms = (System.nanoTime() - inicio) / 1_000_000.0;
        registrar("Bubble Sort", criterio, ascendente, ms);
    }

    // Seleccion: busca el que sigue en orden y lo pone en su lugar
    public void ordenamientoSeleccion(Paciente[] pacientes, int total, String criterio, boolean ascendente) {
        comparacionContador = 0;
        comparacionIntercambio = 0;
        long inicio = System.nanoTime();

        for (int i = 0; i < total - 1; i++) {
            int menorOMayor = i; // Guardamos la posicion del candidato

            for (int j = i + 1; j < total; j++) {
                if (debeCambiar(pacientes[menorOMayor], pacientes[j], criterio, ascendente)) {
                    menorOMayor = j;
                }
            }

            // Solo intercambiamos si encontramos otro distinto
            if (menorOMayor != i) {
                Paciente aux = pacientes[i];
                pacientes[i] = pacientes[menorOMayor];
                pacientes[menorOMayor] = aux;
                comparacionIntercambio++;
            }
        }

        double ms = (System.nanoTime() - inicio) / 1_000_000.0;
        registrar("Selection Sort", criterio, ascendente, ms);
    }

    // Insercion: toma un paciente y recorre los anteriores para meterlo en su lugar
    public void insercionSort(Paciente[] pacientes, int total, String criterio, boolean ascendente) {
        comparacionContador = 0;
        comparacionIntercambio = 0;
        long inicio = System.nanoTime();

        for (int i = 1; i < total; i++) {
            Paciente actual = pacientes[i];
            int j = i - 1;

            // Mueve hacia adelante los que van despues de "actual"
            while (j >= 0 && debeCambiar(pacientes[j], actual, criterio, ascendente)) {
                pacientes[j + 1] = pacientes[j];
                comparacionIntercambio++;
                j--;
            }

            // Acomoda a "actual" donde quedo el hueco
            pacientes[j + 1] = actual;
            comparacionIntercambio++;
        }

        double ms = (System.nanoTime() - inicio) / 1_000_000.0;
        registrar("Insertion Sort", criterio, ascendente, ms);
    }

    // Compara dos pacientes segun el criterio y si es ascendente o descendente
    private boolean debeCambiar(Paciente a, Paciente b, String criterio, boolean ascendente) {
        comparacionContador++;
        int dif = 0;
        String c = criterio.toLowerCase();

        if (c.equals("numero") || c.equals("expediente")) {
            dif = Integer.compare(a.getNumeroPaciente(), b.getNumeroPaciente());
        } else if (c.equals("nombre")) {
            dif = a.getNombrePaciente().compareToIgnoreCase(b.getNombrePaciente());
        } else if (c.equals("edad")) {
            dif = Integer.compare(a.getEdadPaciente(), b.getEdadPaciente());
        } else if (c.equals("consultas")) {
            dif = Integer.compare(a.getNumeroConsultas(), b.getNumeroConsultas());
        } else {
            dif = Integer.compare(a.getNumeroPaciente(), b.getNumeroPaciente());
        }

        // Si es ascendente, cambia si "a" es mayor que "b" (dif > 0)
        // Si es descendente, cambia si "a" es menor que "b" (dif < 0)
        return ascendente ? (dif > 0) : (dif < 0);
    }

    // Guarda un resumen de cada prueba
    private void registrar(String alg, String criterio, boolean ascendente, double ms) {
        String tipo = ascendente ? "Ascendente" : "Descendente";
        historial += alg + " (" + criterio + ", " + tipo + ") -> Comparaciones: "
                + comparacionContador + ", Intercambios/Movs: " + comparacionIntercambio
                + ", Tiempo: " + String.format("%.4f", ms) + " ms\n";
    }
}