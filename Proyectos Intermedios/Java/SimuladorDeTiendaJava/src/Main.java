import model.Producto;
import service.Carrito;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Carrito carrito = new Carrito();

        // Inicializar productos de ejemplo
        Producto[] productos = {
            new Producto("P001", "Notebook ASUS", 150000.0, 10),
            new Producto("P002", "Mouse Logitech", 8500.0, 25),
            new Producto("P003", "Teclado Mecánico", 22000.0, 15),
            new Producto("P004", "Monitor 24\"", 95000.0, 8),
            new Producto("P005", "Auriculares Bluetooth", 18000.0, 20)
        };

        boolean ejecutando = true;
        while (ejecutando) {
            System.out.println("\n=== 🛒 SIMULADOR DE TIENDA ===");
            System.out.println("1. Ver productos disponibles");
            System.out.println("2. Agregar producto al carrito");
            System.out.println("3. Ver carrito");
            System.out.println("4. Vaciar carrito");
            System.out.println("5. Finalizar compra");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.println("\n--- PRODUCTOS DISPONIBLES ---");
                    for (int i = 0; i < productos.length; i++) {
                        System.out.printf("%d. %s%n", i + 1, productos[i]);
                    }
                    break;

                case 2:
                    System.out.println("\n--- AGREGAR PRODUCTO ---");
                    System.out.print("ID del producto (P001-P005): ");
                    String id = scanner.nextLine().trim().toUpperCase();
                    System.out.print("Cantidad: ");
                    int cantidad = scanner.nextInt();
                    scanner.nextLine();

                    Producto prod = buscarProducto(productos, id);
                    if (prod != null) {
                        carrito.agregarProducto(prod, cantidad);
                    } else {
                        System.out.println("⚠️ Producto no encontrado");
                    }
                    break;

                case 3:
                    carrito.mostrarResumen();
                    break;

                case 4:
                    carrito.vaciar();
                    break;

                case 5:
                    if (carrito.calcularSubtotal() == 0) {
                        System.out.println("⚠️ El carrito está vacío");
                    } else {
                        carrito.mostrarResumen();
                        System.out.print("\n¿Confirmar compra? (s/n): ");
                        String confirmar = scanner.nextLine();
                        if (confirmar.equalsIgnoreCase("s")) {
                            System.out.println("\n✅ ¡Compra realizada exitosamente!");
                            System.out.printf("📦 Total abonado: $%.2f%n", carrito.calcularTotal());
                            carrito.vaciar();
                        }
                    }
                    break;

                case 6:
                    ejecutando = false;
                    System.out.println("👋 ¡Gracias por tu visita!");
                    break;

                default:
                    System.out.println("⚠️ Opción inválida");
            }
        }
        scanner.close();
    }

    private static Producto buscarProducto(Producto[] productos, String id) {
        for (Producto p : productos) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }
}