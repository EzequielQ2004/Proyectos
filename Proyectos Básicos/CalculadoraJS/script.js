function calcular(operador) {
    const num1 = parseFloat(document.getElementById("num1").value);
    const num2 = parseFloat(document.getElementById("num2").value);
    let resultado;

    if (isNaN(num1) || isNaN(num2)) {
        document.getElementById("resultado").textContent = "Por favor, ingrese números válidos."
        return;
    }

    switch (operador) {
        case '+':
            resultado = num1 + num2;
            break;
        case '-':
            resultado = num1 - num2;
            break;
        case '*':
            resultado = num1 * num2;
        case '/':
            if (num2 === 0) {
                document.getElementById("resultado").textContent = "Error: No se puede dividir por cero.";
                return;
            }
            resultado = num1 / num2;
            break;
        default:
            resultado = "Operación no válida.";
    }

    document.getElementById("resultado"). textContent = "Resultado: " + resultado;
}