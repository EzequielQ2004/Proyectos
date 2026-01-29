from typing import List
from models import Producto, ItemCarrito


class Carrito:
    IVA = 0.21

    def __init__(self):
        self.items: List[ItemCarrito] = []

    def agregar_producto(self, producto: Producto, cantidad: int) -> bool:
        if cantidad <= 0:
            print("⚠️  Cantidad inválida")
            return False

        if not producto.hay_stock(cantidad):
            print(f"⚠️  Stock insuficiente para {producto.nombre} (disponible: {producto.stock})")
            return False

        # Verificar si el producto ya está en el carrito
        for item in self.items:
            if item.producto.id == producto.id:
                item.cantidad += cantidad
                producto.reducir_stock(cantidad)
                print(f"✅ {producto.nombre} agregado al carrito (x{cantidad})")
                return True

        # Nuevo producto
        self.items.append(ItemCarrito(producto, cantidad))
        producto.reducir_stock(cantidad)
        print(f"✅ {producto.nombre} agregado al carrito (x{cantidad})")
        return True

    def calcular_subtotal(self) -> float:
        return sum(item.subtotal() for item in self.items)

    def calcular_descuento(self) -> float:
        subtotal = self.calcular_subtotal()
        # Descuentos por volumen
        if subtotal >= 100_000:
            return subtotal * 0.15  # 15%
        elif subtotal >= 50_000:
            return subtotal * 0.10  # 10%
        elif subtotal >= 20_000:
            return subtotal * 0.05  # 5%
        return 0.0

    def calcular_impuestos(self) -> float:
        base_imponible = self.calcular_subtotal() - self.calcular_descuento()
        return base_imponible * self.IVA

    def calcular_total(self) -> float:
        return self.calcular_subtotal() - self.calcular_descuento() + self.calcular_impuestos()

    def mostrar_resumen(self) -> None:
        if not self.items:
            print("\n🛒 Tu carrito está vacío")
            return

        print("\n" + "=" * 60)
        print(f"{'ITEMS EN TU CARRITO':^60}")
        print("=" * 60)
        print(f"{'Producto':<27} | {'Cantidad':<8} | {'Subtotal':>12}")
        print("-" * 60)

        for i, item in enumerate(self.items, 1):
            prod = item.producto
            print(f"{i}. {prod.nombre:<25s} | {item.cantidad:<8d} | ${item.subtotal():>10,.2f}")

        print("=" * 60)
        subtotal = self.calcular_subtotal()
        descuento = self.calcular_descuento()
        impuestos = self.calcular_impuestos()
        total = self.calcular_total()

        print(f"{'Subtotal:':<45} ${subtotal:>10,.2f}")
        if descuento > 0:
            print(f"{'Descuento (' + str(int(descuento / subtotal * 100)) + '%):':<45} -$ {descuento:>9,.2f}")
        print(f"{'IVA (21%):':<45} ${impuestos:>10,.2f}")
        print("-" * 60)
        print(f"{'TOTAL:':<45} ${total:>10,.2f}")
        print("=" * 60)

    def vaciar(self) -> None:
        self.items.clear()
        print("🛒 Carrito vaciado correctamente")