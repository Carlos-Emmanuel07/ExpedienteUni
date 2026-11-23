import controlador.gestorPaciente;
import java.util.Scanner;
import modelo.Paciente;

public class App {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        // Creamos el gestor con espacio para hasta 100 pacientes
        gestorPaciente gestor = new gestorPaciente(100);

        // Cargamos unos datos de prueba para poder probar rapido sin teclear todo cada vez
        cargarDatosEjemplo(gestor);

        int opcion = -1;
        System.out.println("----------------------------------------------------");
        System.out.println("-------------CATALOGO DE EXPEDIENTES-----------------");
        System.out.println("-----------------------------------------------------");

        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Registrar nuevo paciente");
            System.out.println("2. Mostrar catalogo original (sin ordenar)");
            System.out.println("3. Ordenar catalogo y ver resultados");
            System.out.println("4. Ver historial de comparaciones entre algoritmos");
            System.out.println("0. Salir");

            opcion = leerEntero(teclado, "Elige una opcion: ", 0, 4);

            if (opcion == 1) {
                registrarNuevoPaciente(teclado, gestor);
            } else if (opcion == 2) {
                gestor.mostrarCatalogoOriginal();
            } else if (opcion == 3) {
                ejecutarOrdenamiento(teclado, gestor);
            } else if (opcion == 4) {
                mostrarHistorial(gestor);
            } else if (opcion == 0) {
                System.out.println("\nSaliendo del sistema... ¡Exito con tu practica!");
            }

        } while (opcion != 0);

        teclado.close();
    }

    // Pide los datos del paciente validando que los numeros sean validos
    private static void registrarNuevoPaciente(Scanner teclado, gestorPaciente gestor) {
        System.out.println("\n--- REGISTRAR PACIENTE ---");
        int numExpediente = leerEntero(teclado, "Numero de expediente: ", 1, 999999);
        String nombre = leerTextoNoVacio(teclado, "Nombre del paciente: ");
        int edad = leerEntero(teclado, "Edad: ", 0, 125);
        String especialidad = leerTextoNoVacio(teclado, "Especialidad medica: ");
        int consultas = leerEntero(teclado, "Numero de consultas realizadas: ", 0, 10000);

        boolean agregado = gestor.registrarPaciente(numExpediente, nombre, edad, especialidad, consultas);
        if (agregado) {
            System.out.println("Paciente registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar: el catalogo esta lleno.");
        }
    }

    // Pide algoritmo, criterio y tipo de orden, y muestra todo el resultado
    private static void ejecutarOrdenamiento(Scanner teclado, gestorPaciente gestor) {
        if (gestor.getCantidadPacientes() == 0) {
            System.out.println("\nPrimero registra al menos un paciente.");
            return;
        }

        System.out.println("\n--- ELEGIR ALGORITMO ---");
        System.out.println("1. Bubble Sort");
        System.out.println("2. Selection Sort");
        System.out.println("3. Insertion Sort");
        int alg = leerEntero(teclado, "Algoritmo: ", 1, 3);

        System.out.println("\n--- ELEGIR CRITERIO ---");
        System.out.println("1. Numero de expediente");
        System.out.println("2. Nombre del paciente");
        System.out.println("3. Edad");
        System.out.println("4. Numero de consultas");
        int opCriterio = leerEntero(teclado, "Criterio: ", 1, 4);

        String criterio = "numero";
        if (opCriterio == 2) {
            criterio = "nombre";
        } else if (opCriterio == 3) {
            criterio = "edad";
        } else if (opCriterio == 4) {
            criterio = "consultas";
        }

        System.out.println("\n--- TIPO DE ORDEN ---");
        System.out.println("1. Ascendente");
        System.out.println("2. Descendente");
        int opOrden = leerEntero(teclado, "Orden: ", 1, 2);
        boolean ascendente = (opOrden == 1);

        // Mostramos el catalogo antes de ordenar para que se vea claro el cambio
        gestor.mostrarCatalogoOriginal();

        // Ordenamos sobre una copia y mostramos el catalogo ordenado
        Paciente[] ordenado = gestor.ordenarCatalogo(alg, criterio, ascendente);
        gestor.mostrarLista(ordenado, gestor.getCantidadPacientes(), "CATALOGO ORDENADO");

        // Mostramos metricas de la ejecucion actual
        System.out.println("\n--- METRICAS DE EJECUCION ---");
        System.out.println("Comparaciones realizadas: " + gestor.getOrdenador().getComparacionContador());
        System.out.println("Movimientos / intercambios: " + gestor.getOrdenador().getComparacionIntercambio());
    }

    // Muestra todas las pruebas hechas durante la sesion
    private static void mostrarHistorial(gestorPaciente gestor) {
        System.out.println("\n--- HISTORIAL DE PRUEBAS ---");
        String hist = gestor.getOrdenador().getHistorial();
        if (hist.isEmpty()) {
            System.out.println("Aun no has ejecutado ningun ordenamiento.");
        } else {
            System.out.println(hist);
        }
    }

    // Lee un numero entero dentro de un rango y evita que el programa truene con letras
    private static int leerEntero(Scanner teclado, String mensaje, int min, int max) {
        int numero = 0;
        boolean valido = false;

        while (!valido) {
            System.out.print(mensaje);
            String entrada = teclado.nextLine().trim();
            try {
                numero = Integer.parseInt(entrada);
                if (numero >= min && numero <= max) {
                    valido = true;
                } else {
                    System.out.println("Ingresa un numero entre " + min + " y " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Escribe solo un numero entero.");
            }
        }
        return numero;
    }

    // Lee texto asegurandose de que no quede vacio
    private static String leerTextoNoVacio(Scanner teclado, String mensaje) {
        String texto = "";
        while (texto.trim().isEmpty()) {
            System.out.print(mensaje);
            texto = teclado.nextLine();
            if (texto.trim().isEmpty()) {
                System.out.println("Este campo no puede ir vacio.");
            }
        }
        return texto.trim();
    }

    // Datos iniciales para probar rapido el programa
    private static void cargarDatosEjemplo(gestorPaciente gestor) {
        gestor.registrarPaciente(105, "Carlos Mendez", 34, "Cardiologia", 4);
        gestor.registrarPaciente(102, "Ana Lopez", 22, "Dermatologia", 2);
        gestor.registrarPaciente(109, "Luis Ramirez", 58, "Neurologia", 9);
        gestor.registrarPaciente(101, "Maria Torres", 41, "Pediatria", 6);
        gestor.registrarPaciente(107, "Jorge Soto", 29, "Traumatologia", 1);
    }
}