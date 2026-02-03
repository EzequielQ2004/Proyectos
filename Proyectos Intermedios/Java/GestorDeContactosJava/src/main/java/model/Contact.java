package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class Contact {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Patrones de validación
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[\\d\\s\\-()]{7,15}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Contact(String id, String name, String phone, String email, String address) {
        this.id = id;
        this.name = name.trim();
        this.phone = phone.trim();
        this.email = email.trim().toLowerCase();
        this.address = (address != null) ? address.trim() : "";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Validación completa
    public ValidationResult isValid() {
        ValidationResult result = new ValidationResult();

        if (name == null || name.length() < 2) {
            result.addError("Nombre debe tener al menos 2 caracteres");
        }

        if (phone == null || !PHONE_PATTERN.matcher(phone).matches()) {
            result.addError("Teléfono inválido (ej: +54 9 260 123-4567)");
        }

        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            result.addError("Email inválido");
        }

        return result;
    }

    // Actualizar contacto
    public void update(String name, String phone, String email, String address) {
        if (name != null && !name.trim().isEmpty()) this.name = name.trim();
        if (phone != null && !phone.trim().isEmpty()) this.phone = phone.trim();
        if (email != null && !email.trim().isEmpty()) this.email = email.trim().toLowerCase();
        if (address != null) this.address = address.trim();
        this.updatedAt = LocalDateTime.now();
    }

    // Para CSV
    public String toCsvRow() {
        return String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
                id, name, phone, email, address,
                createdAt.format(FORMATTER),
                updatedAt.format(FORMATTER));
    }

    public static Contact fromCsvRow(String csvLine) {
        // Soporta comillas y comas dentro de campos
        String[] parts = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
            if (parts[i].startsWith("\"") && parts[i].endsWith("\"")) {
                parts[i] = parts[i].substring(1, parts[i].length() - 1);
            }
        }

        Contact contact = new Contact(
                parts[0], // id
                parts[1], // name
                parts[2], // phone
                parts[3], // email
                parts.length > 4 ? parts[4] : "" // address
        );

        if (parts.length > 5 && !parts[5].isEmpty()) {
            contact.createdAt = LocalDateTime.parse(parts[5], FORMATTER);
        }
        if (parts.length > 6 && !parts[6].isEmpty()) {
            contact.updatedAt = LocalDateTime.parse(parts[6], FORMATTER);
        }

        return contact;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("🆔 ").append(id)
                .append(" | 👤 ").append(name)
                .append(" | 📱 ").append(phone)
                .append(" | ✉️ ").append(email);
        if (!address.isEmpty()) {
            sb.append(" | 📍 ").append(address);
        }
        return sb.toString();
    }

    // Clase interna para resultado de validación
    public static class ValidationResult {
        private boolean valid = true;
        private StringBuilder errors = new StringBuilder();

        public void addError(String error) {
            valid = false;
            if (errors.length() > 0) errors.append("\n");
            errors.append("❌ ").append(error);
        }

        public boolean isValid() { return valid; }
        public String getErrors() { return errors.toString(); }
    }
}