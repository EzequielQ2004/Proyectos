package model;

public class ItemCarrito {
    private Producto producto;
    private int cantidad;

    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getSubtotal() { return producto.getPrecio() * cantidad; }

    public void incrementarCantidad(int cantidad) {
        this.cantidad += cantidad;
    }

    @Override
    public String toString() {
        return String.format("%dx %s - $%.2f", cantidad, producto.getNombre(), getSubtotal());
    }
}