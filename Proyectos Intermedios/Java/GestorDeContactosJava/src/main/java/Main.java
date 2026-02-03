import model.Contact;
import service.ContactManager;

import java.util.Scanner;
import java.util.List;

public class Main {
    private static final String CSV_PATH = "data/contacts.csv";
    private static final ContactManager manager = new ContactManager(CSV_PATH);
    private static final Scanner scanner = new Scanner(System.in);

    // Helper para repetir caracteres
    private static String repeatChar(char c, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        showWelcome();
        showMenu();
        scanner.close();
    }

    private static void showWelcome() {
        System.out.println("\n" + repeatChar('=', 60));
        System.out.println("        📱 GESTOR DE CONTACTOS v1.0 - JAVA        ");
        System.out.println(repeatChar('=', 60));
    }

    private static void showMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n" + repeatChar('-', 60));
            System.out.println("Menú Principal:");
            System.out.println("1. ➕ Agregar contacto");
            System.out.println("2. 🔍 Buscar contacto");
            System.out.println("3. 👁️  Ver todos los contactos");
            System.out.println("4. ✏️  Editar contacto");
            System.out.println("5. 🗑️  Eliminar contacto");
            System.out.println("6. ❌ Salir");
            System.out.println(repeatChar('-', 60));

            System.out.print("\nSeleccione una opción (1-6): ");
            String option = scanner.nextLine().trim();

            switch (option) {
                case "1":
                    addContactFlow();
                    break;
                case "2":
                    searchContactFlow();
                    break;
                case "3":
                    listContactsFlow();
                    break;
                case "4":
                    editContactFlow();
                    break;
                case "5":
                    deleteContactFlow();
                    break;
                case "6":
                    System.out.println("\n👋 ¡Hasta pronto!");
                    running = false;
                    break;
                default:
                    System.out.println("⚠️ Opción inválida. Seleccione 1-6");
                    break;
            }

            if (running && !option.equals("6")) {
                waitForEnter();
            }
        }
    }

    private static void addContactFlow() {
        clearScreen();
        System.out.println("\n--- ➕ AGREGAR NUEVO CONTACTO ---");

        try {
            System.out.print("Nombre completo: ");
            String name = scanner.nextLine().trim();

            System.out.print("Teléfono (ej: +54 9 260 123-4567): ");
            String phone = scanner.nextLine().trim();

            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Dirección (opcional): ");
            String address = scanner.nextLine().trim();

            Contact contact = manager.addContact(name, phone, email, address.isEmpty() ? "" : address);
            System.out.println("\n✅ Contacto agregado exitosamente:");
            System.out.println("   " + contact);
        } catch (Exception e) {
            System.out.println("\n❌ Error: " + e.getMessage());
        }
    }

    private static void searchContactFlow() {
        clearScreen();
        System.out.println("\n--- 🔍 BUSCAR CONTACTO ---");

        System.out.print("Buscar (nombre, teléfono, email o dirección): ");
        String query = scanner.nextLine().trim();

        List<Contact> results = manager.searchContacts(query);

        if (results.isEmpty()) {
            System.out.println("\n📭 No se encontraron contactos");
            return;
        }

        System.out.println("\n✅ Encontrados " + results.size() + " resultado(s):");
        for (int i = 0; i < results.size(); i++) {
            System.out.println("\n" + (i + 1) + ". " + results.get(i));
            System.out.println("   ID: " + results.get(i).getId());
        }
    }

    private static void listContactsFlow() {
        clearScreen();
        List<Contact> contacts = manager.getAllContacts();

        System.out.println("\n--- 👁️ TODOS LOS CONTACTOS ---");

        if (contacts.isEmpty()) {
            System.out.println("\n📭 Tu agenda está vacía");
            return;
        }

        System.out.println("\n✅ Tienes " + contacts.size() + " contacto(s):\n");
        for (int i = 0; i < contacts.size(); i++) {
            System.out.println((i + 1) + ". " + contacts.get(i));
        }
        System.out.println("\nTotal: " + contacts.size() + " contactos");
    }

    private static void editContactFlow() {
        clearScreen();
        System.out.println("\n--- ✏️ EDITAR CONTACTO ---");

        System.out.print("ID del contacto a editar: ");
        String id = scanner.nextLine().trim();

        Contact contact = manager.getContactById(id);
        if (contact == null) {
            System.out.println("❌ Contacto no encontrado");
            return;
        }

        System.out.println("\nEditando: " + contact.getName());
        System.out.println("Actual: " + contact.getPhone() + " | " + contact.getEmail());

        try {
            System.out.print("Nuevo nombre (" + contact.getName() + "): ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) name = contact.getName();

            System.out.print("Nuevo teléfono (" + contact.getPhone() + "): ");
            String phone = scanner.nextLine().trim();
            if (phone.isEmpty()) phone = contact.getPhone();

            System.out.print("Nuevo email (" + contact.getEmail() + "): ");
            String email = scanner.nextLine().trim();
            if (email.isEmpty()) email = contact.getEmail();

            System.out.print("Nueva dirección (" + (contact.getAddress().isEmpty() ? "vacío" : contact.getAddress()) + "): ");
            String address = scanner.nextLine().trim();
            if (address.isEmpty()) address = contact.getAddress();

            Contact updated = manager.updateContact(id, name, phone, email, address);
            System.out.println("\n✅ Contacto actualizado exitosamente:");
            System.out.println("   " + updated);
        } catch (Exception e) {
            System.out.println("\n❌ Error: " + e.getMessage());
        }
    }

    private static void deleteContactFlow() {
        clearScreen();
        System.out.println("\n--- 🗑️ ELIMINAR CONTACTO ---");

        System.out.print("ID del contacto a eliminar: ");
        String id = scanner.nextLine().trim();

        Contact contact = manager.getContactById(id);
        if (contact == null) {
            System.out.println("❌ Contacto no encontrado");
            return;
        }

        System.out.println("\n¿Eliminar a " + contact.getName() + "?");
        System.out.println("   " + contact);

        System.out.print("\nEscribe 'SI' para confirmar: ");
        String confirm = scanner.nextLine().trim();

        if (!confirm.equalsIgnoreCase("SI")) {
            System.out.println("Operación cancelada");
            return;
        }

        try {
            manager.deleteContact(id);
            System.out.println("\n✅ " + contact.getName() + " eliminado correctamente");
        } catch (Exception e) {
            System.out.println("\n❌ Error: " + e.getMessage());
        }
    }

    private static void waitForEnter() {
        System.out.print("\nPresione ENTER para continuar...");
        scanner.nextLine(); // Espera ENTER
    }

    private static void clearScreen() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Si falla la limpieza, simplemente imprime líneas vacías
            for (int i = 0; i < 20; i++) System.out.println();
        }
    }
}