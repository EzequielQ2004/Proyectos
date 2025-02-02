// Lista de Productos
const productos = [
    { id: 1, nombre: 'Producto 1', precio: 10 },
    { id: 2, nombre: 'Producto 2', precio: 20 },
    { id: 3, nombre: 'Producto 3', precio: 30 }
];

// Carrito de Compras
let carrito = [];

// Función para agregar un producto al carrito
function agregarAlCarrito(productoId) {
    const producto = productos.find(p => p.id === productoId);
    if (producto) {
        carrito.push(producto);
        actualizarCarrito();
    }
}

//Función para actualizar las visualizaciones del carrito
function actualizarCarrito() {
    const listaCarrito = document.getElementById('lista-carrito');
    listaCarrito.innerHTML = '';
    carrito.forEach(producto => {
        const li = document.createElement('li');
        li.textContent = `${producto.nombre} - $${producto.precio}`;
        listaCarrito.appendChild(li);
    });
}

// Función para calcular el total de la compra
function calcularTotal() {
    const subtotal = carrito.reduce((sum, producto) => sum + producto.precio, 0);
    const inpuesto = subtotal * 0.15;
    const descuento = subtotal >= 50 ? subtotal * 0.10 : 0;
    const total = subtotal + inpuesto - descuento;

    document.getElementById('total').textContent = total.toFixed(2);
}