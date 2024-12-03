
package listadetareas;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ToDoList {
    private List<Tarea> tareas;
    private final String archivo = "tareas.txt";

    public ToDoList() {
        this.tareas = new ArrayList<>();
        cargarDesdeArchivo();
    }

    public void agregarTarea(String descripcion) {
        tareas.add(new Tarea(descripcion));
        guardarEnArchivo();
    }

    public void eliminarTarea(int indice) {
        if (indice >= 0 && indice < tareas.size()) {
            tareas.remove(indice);
            guardarEnArchivo();
        } else {
            System.out.println("Índice no válido.");
        }
    }

    public void marcarTareaComoCompletada(int indice) {
        if (indice >= 0 && indice < tareas.size()) {
            tareas.get(indice).marcarComoCompletada();
            guardarEnArchivo();
        } else {
            System.out.println("Índice no válido.");
        }
    }

    public void mostrarTareas() {
        if (tareas.isEmpty()) {
            System.out.println("No hay tareas en la lista.");
        } else {
            for (int i = 0; i < tareas.size(); i++) {
                System.out.println(i + ". " + tareas.get(i));
            }
        }
    }

    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(tareas);
        } catch (IOException e) {
            System.out.println("Tarea guardada correctamente: " + e.getMessage());
        }
    }

    private void cargarDesdeArchivo() {
        File file = new File(archivo);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                tareas = (List<Tarea>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error al cargar las tareas: " + e.getMessage());
            }
        }
    }
}