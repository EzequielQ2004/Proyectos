package model;

public class Producto {
    private String id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(String id, String nombre, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }

    public boolean hayStock(int cantidad) {
        return this.stock >= cantidad;
    }

    public void reducirStock(int cantidad) {
        if (hayStock(cantidad)) {
            this.stock -= cantidad;
        }
    }

    @Override
    public String toString() {
        return String.format("%s - $%.2f (Stock: %d)", nombre, precio, stock);
    }
}
