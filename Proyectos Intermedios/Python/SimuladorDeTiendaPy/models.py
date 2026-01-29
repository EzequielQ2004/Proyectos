from dataclasses import dataclass

@dataclass
class Producto:
    id: str
    nombre: str
    precio: float
    stock: int

    def hay_stock(self, cantidad: int) -> bool:
        return self.stock >= cantidad

    def reducir_stock(self, cantidad: int) -> None:
        if self.hay_stock(cantidad):
            self.stock -= cantidad

    def __str__(self) -> str:
        return f"{self.nombre} - ${self.precio:,.2f} (Stock: {self.stock})"


@dataclass
class ItemCarrito:
    producto: Producto
    cantidad: int

    def subtotal(self) -> float:
        return self.producto.precio * self.cantidad

    def __str__(self) -> str:
        return f"{self.cantidad}x {self.producto.nombre:25s} | ${self.subtotal():>10,.2f}"