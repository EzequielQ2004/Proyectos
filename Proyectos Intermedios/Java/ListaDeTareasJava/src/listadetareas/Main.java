
package listadetareas;

import java.util.Scanner;

public class Main {
    public static void main(String[] arg) {
        ToDoList lista = new ToDoList();
        Scanner entrada = new Scanner(System.in);
        boolean salir = false;
        
        while (!salir) {
            System.out.println("\n1. Agregar tarea");
            System.out.println("2. Eliminar tarea");
            System.out.println("3. Marcar tarea como completada");
            System.out.println("4. Mostar tareas");
            System.out.println("5. Salir");
            System.out.println("Selecciona una opción: ");
            
            int opcion = entrada.nextInt();
            entrada.nextLine();
            
            switch (opcion) {
                case 1 -> {
                    System.out.println("Descripción de la tarea: ");
                    String descripcion = entrada.nextLine();
                    lista.agregarTarea(descripcion);
                }
                case 2 -> {
                    System.out.println("Índice de la tarea a eliminar: ");
                    int indice = entrada.nextInt();
                    lista.eliminarTarea(indice);
                }
                case 3 -> {
                    System.out.println("Índice de la tarea a completar: ");
                    int indice = entrada.nextInt();
                    lista.marcarTareaComoCompletada(indice);
                }
                case 4 -> lista.mostrarTareas();
                case 5 -> salir = true;
                default -> System.out.println("Opción no válida.");
            }
        }
        
        entrada.close();
        System.out.println("¡Adiós!");
    }
}
