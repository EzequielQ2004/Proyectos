package service;

import model.Contact;

import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class ContactManager {
    private static final String[] HEADERS = {"id", "name", "phone", "email", "address", "created_at", "updated_at"};
    private final Path csvPath;
    private final List<Contact> contacts = new ArrayList<>();

    public ContactManager(String csvPath) {
        this.csvPath = Paths.get(csvPath);
        ensureDataDirectory();
        loadContacts();
    }

    private void ensureDataDirectory() {
        try {
            Files.createDirectories(csvPath.getParent());

            if (!Files.exists(csvPath)) {
                try (BufferedWriter writer = Files.newBufferedWriter(csvPath, StandardCharsets.UTF_8)) {
                    writer.write(String.join(",", HEADERS));
                    writer.newLine();
                }
                System.out.println("✅ Archivo CSV creado en: " + csvPath.toAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("❌ Error creando directorio de datos: " + e.getMessage());
        }
    }

    public void loadContacts() {
        contacts.clear();
        try (BufferedReader reader = Files.newBufferedReader(csvPath, StandardCharsets.UTF_8)) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Saltar header
                }
                if (line.trim().isEmpty()) continue;

                try {
                    Contact contact = Contact.fromCsvRow(line);
                    contacts.add(contact);
                } catch (Exception e) {
                    System.err.println("⚠️ Error cargando contacto: " + e.getMessage());
                }
            }

            System.out.println("📥 " + contacts.size() + " contactos cargados desde: " + csvPath.getFileName());
        } catch (IOException e) {
            System.err.println("ℹ️ Archivo CSV no encontrado, se creará al guardar el primer contacto");
        }
    }

    public void saveContacts() {
        try (BufferedWriter writer = Files.newBufferedWriter(csvPath, StandardCharsets.UTF_8)) {
            writer.write(String.join(",", HEADERS));
            writer.newLine();

            for (Contact contact : contacts) {
                writer.write(contact.toCsvRow());
                writer.newLine();
            }

            System.out.println("💾 " + contacts.size() + " contactos guardados");
        } catch (IOException e) {
            System.err.println("❌ Error guardando contactos: " + e.getMessage());
        }
    }

    public Contact addContact(String name, String phone, String email, String address) throws Exception {
        // Generar ID único
        String contactId = java.util.UUID.randomUUID().toString().substring(0, 10);
        Contact contact = new Contact(contactId, name, phone, email, address);

        // Validar
        Contact.ValidationResult result = contact.isValid();
        if (!result.isValid()) {
            throw new Exception(result.getErrors());
        }

        // Verificar duplicados
        boolean exists = contacts.stream().anyMatch(c ->
                c.getEmail().equalsIgnoreCase(contact.getEmail()) ||
                        c.getPhone().equals(contact.getPhone())
        );

        if (exists) {
            throw new Exception("⚠️ Ya existe un contacto con este email o teléfono");
        }

        contacts.add(contact);
        saveContacts();
        return contact;
    }

    public List<Contact> searchContacts(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>(contacts);
        }

        String q = query.toLowerCase().trim();
        return contacts.stream()
                .filter(c -> c.getName().toLowerCase().contains(q) ||
                        c.getPhone().contains(q) ||
                        c.getEmail().contains(q) ||
                        c.getAddress().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public List<Contact> getAllContacts() {
        return contacts.stream()
                .sorted(Comparator.comparing(c -> c.getName().toLowerCase()))
                .collect(Collectors.toList());
    }

    public Contact getContactById(String id) {
        return contacts.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Contact updateContact(String id, String name, String phone, String email, String address) throws Exception {
        Contact contact = getContactById(id);
        if (contact == null) {
            throw new Exception("❌ Contacto no encontrado");
        }

        contact.update(name, phone, email, address);

        // Validar actualización
        Contact.ValidationResult result = contact.isValid();
        if (!result.isValid()) {
            throw new Exception(result.getErrors());
        }

        saveContacts();
        return contact;
    }

    public Contact deleteContact(String id) throws Exception {
        Contact contact = getContactById(id);
        if (contact == null) {
            throw new Exception("❌ Contacto no encontrado");
        }

        contacts.remove(contact);
        saveContacts();
        return contact;
    }
}