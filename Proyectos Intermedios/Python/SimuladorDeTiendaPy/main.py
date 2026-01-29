from models import Producto
from cart import Carrito


def mostrar_productos(productos):
    print("\n" + "=" * 60)
    print(f"{'PRODUCTOS DISPONIBLES':^60}")
    print("=" * 60)
    print(f"{'ID':<6} | {'Producto':<25} | {'Precio':<12} | {'Stock':<6}")
    print("-" * 60)
    for p in productos:
        print(f"{p.id:<6} | {p.nombre:<25} | ${p.precio:>10,.2f} | {p.stock:<6}")
    print("=" * 60)


def buscar_producto(productos, id_buscar):
    for p in productos:
        if p.id.upper() == id_buscar.upper():
            return p
    return None


def main():
    # Inicializar productos de ejemplo
    productos = [
        Producto("P001", "Notebook ASUS", 150_000.0, 10),
        Producto("P002", "Mouse Logitech", 8_500.0, 25),
        Producto("P003", "Teclado Mecánico", 22_000.0, 15),
        Producto("P004", "Monitor 24\"", 95_000.0, 8),
        Producto("P005", "Auriculares Bluetooth", 18_000.0, 20),
        Producto("P006", "Webcam HD", 12_000.0, 12),
        Producto("P007", "Disco SSD 1TB", 28_000.0, 18),
    ]

    carrito = Carrito()

    while True:
        print("\n" + "🛒" * 20)
        print("=== SIMULADOR DE TIENDA - PYTHON ===")
        print("1. 📦 Ver productos disponibles")
        print("2. ➕ Agregar producto al carrito")
        print("3. 👀 Ver carrito")
        print("4. 🗑️  Vaciar carrito")
        print("5. 💳 Finalizar compra")
        print("6. ❌ Salir")
        print("🛒" * 20)

        opcion = input("\nSeleccione una opción (1-6): ").strip()

        if opcion == "1":
            mostrar_productos(productos)

        elif opcion == "2":
            print("\n--- AGREGAR PRODUCTO AL CARRITO ---")
            id_prod = input("ID del producto (ej: P001): ").strip()
            producto = buscar_producto(productos, id_prod)

            if not producto:
                print(f"⚠️  Producto con ID '{id_prod}' no encontrado")
                continue

            try:
                cantidad = int(input(f"Cantidad de '{producto.nombre}' (stock disponible: {producto.stock}): "))
                carrito.agregar_producto(producto, cantidad)
            except ValueError:
                print("⚠️  Cantidad inválida. Debe ser un número entero.")

        elif opcion == "3":
            carrito.mostrar_resumen()

        elif opcion == "4":
            if carrito.items:
                confirmar = input("¿Estás seguro de vaciar el carrito? (s/n): ").strip().lower()
                if confirmar == "s":
                    carrito.vaciar()
                else:
                    print("Operación cancelada")
            else:
                print("🛒 El carrito ya está vacío")

        elif opcion == "5":
            if not carrito.items:
                print("⚠️  El carrito está vacío. Agrega productos primero.")
                continue

            carrito.mostrar_resumen()
            confirmar = input("\n¿Confirmar compra? (s/n): ").strip().lower()
            if confirmar == "s":
                total = carrito.calcular_total()
                print("\n" + "✅" * 20)
                print(f"¡COMPRA REALIZADA EXITOSAMENTE!")
                print(f"📦 Total abonado: ${total:,.2f}")
                print("✅" * 20)
                carrito.vaciar()
            else:
                print("Compra cancelada")

        elif opcion == "6":
            print("\n👋 ¡Gracias por visitar nuestra tienda virtual!")
            print("Hasta pronto 👋")
            break

        else:
            print("⚠️  Opción inválida. Por favor seleccione una opción del 1 al 6.")

        input("\nPresione ENTER para continuar...")


if __name__ == "__main__":
    main()