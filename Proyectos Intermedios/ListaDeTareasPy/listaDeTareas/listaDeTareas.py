import json

class ToDoList:
    def __init__(self, file_name="todolist.json"):
        """
        Inicializa la lista de tareas y carga las tareas guardadas desde un archivo JSON.
        """
        self.file_name = file_name
        self.tasks = self.load_tasks()

    def save_tasks(self):
        """
        Guarda las tareas actuales en un archivo JSON.
        """
        with open(self.file_name, "w") as file:
            json.dump(self.tasks, file, indent=4)
        print("Tareas guardadas exitosamente.")

    def load_tasks(self):
        """
        Carga las tareas desde un archivo JSON. Si el archivo no existe, devuelve una lista vacía.
        """
        try:
            with open(self.file_name, "r") as file:
                return json.load(file)
        except FileNotFoundError:
            return []

    def add_task(self, task):
        """
        Agrega una nueva tarea a la lista.
        """
        self.tasks.append({"task": task, "completed": False})
        self.save_tasks()
        print(f"Tarea agregada: '{task}'")

    def mark_task_completed(self, index):
        """
        Marca una tarea como completada según el índice proporcionado.
        """
        if 0 <= index < len(self.tasks):
            self.tasks[index]["completed"] = True
            self.save_tasks()
            print(f"Tarea '{self.tasks[index]['task']}' marcada como completada.")
        else:
            print("Índice de tarea inválido.")

    def delete_task(self, index):
        """
        Elimina una tarea de la lista según el índice proporcionado.
        """
        if 0 <= index < len(self.tasks):
            task = self.tasks.pop(index)
            self.save_tasks()
            print(f"Tarea eliminada: '{task['task']}'")
        else:
            print("Índice de tarea inválido.")

    def show_tasks(self):
        """
        Muestra todas las tareas con su estado (completada o no completada).
        """
        if not self.tasks:
            print("No hay tareas.")
        else:
            for i, task in enumerate(self.tasks):
                status = "✅" if task["completed"] else "❌"
                print(f"{i}. {task['task']} [{status}]")

if __name__ == "__main__":
    todo = ToDoList()
    while True:
        print("\nOpciones: 1. Agregar  2. Marcar como completada  3. Eliminar  4. Mostrar  5. Salir")
        opcion = input("Elige una opción: ")
        if opcion == "1":
            tarea = input("Escribe la tarea: ")
            todo.add_task(tarea)
        elif opcion == "2":
            try:
                index = int(input("Índice de la tarea completada: "))
                todo.mark_task_completed(index)
            except ValueError:
                print("Por favor, introduce un número válido.")
        elif opcion == "3":
            try:
                index = int(input("Índice de la tarea a eliminar: "))
                todo.delete_task(index)
            except ValueError:
                print("Por favor, introduce un número válido.")
        elif opcion == "4":
            todo.show_tasks()
        elif opcion == "5":
            print("¡Hasta luego!")
            break
        else:
            print("Opción no válida.")