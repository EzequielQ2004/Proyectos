package service;

import model.ItemCarrito;
import model.Producto;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private List<ItemCarrito> items;
    private static final double IVA = 0.21;

    public Carrito() {
        this.items = new ArrayList<>();
    }

    public boolean agregarProducto(Producto producto, int cantidad) {
        if (cantidad <= 0) {
            System.out.println("⚠️ Cantidad inválida");
            return false;
        }
        if (!producto.hayStock(cantidad)) {
            System.out.println("⚠️ Stock insuficiente para " + producto.getNombre());
            return false;
        }

        // Verificar si el producto ya está en el carrito
        for (ItemCarrito item : items) {
            if (item.getProducto().getId().equals(producto.getId())) {
                item.incrementarCantidad(cantidad);
                producto.reducirStock(cantidad);
                System.out.println("✅ " + producto.getNombre() + " agregado al carrito (x" + cantidad + ")");
                return true;
            }
        }

        // Nuevo producto en el carrito
        items.add(new ItemCarrito(producto, cantidad));
        producto.reducirStock(cantidad);
        System.out.println("✅ " + producto.getNombre() + " agregado al carrito (x" + cantidad + ")");
        return true;
    }

    public double calcularSubtotal() {
        return items.stream().mapToDouble(ItemCarrito::getSubtotal).sum();
    }

    public double calcularDescuento() {
        double subtotal = calcularSubtotal();
        // Descuento por volumen: 5% si supera $5000, 10% si supera $10000
        if (subtotal >= 10000) return subtotal * 0.10;
        if (subtotal >= 5000) return subtotal * 0.05;
        return 0;
    }

    public double calcularImpuestos() {
        return (calcularSubtotal() - calcularDescuento()) * IVA;
    }

    public double calcularTotal() {
        return calcularSubtotal() - calcularDescuento() + calcularImpuestos();
    }

    public void mostrarResumen() {
        if (items.isEmpty()) {
            System.out.println("\n🛒 Tu carrito está vacío");
            return;
        }

        System.out.println("\n================ CARRITO DE COMPRAS ================");
        items.forEach(System.out::println);
        System.out.println("-----------------------------------------------------");
        System.out.printf("Subtotal:      $%.2f%n", calcularSubtotal());
        System.out.printf("Descuento:     -$%.2f%n", calcularDescuento());
        System.out.printf("IVA (21%%):     $%.2f%n", calcularImpuestos());
        System.out.printf("TOTAL:         $%.2f%n", calcularTotal());
        System.out.println("=====================================================");
    }

    public void vaciar() {
        items.clear();
        System.out.println("🛒 Carrito vaciado");
    }
}