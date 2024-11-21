const tipoConversion = document.getElementById('tipo-conversion');
const valorInput = document.getElementById('valor');
const convertirBtn = document.getElementById('convertir');
const resultadoParrafo = document.getElementById('resultado');

function convertirUnidades(tipo, valor) {
    let resultado;
    switch (tipo) {
        case 'm-km':
            resultado = valor / 1000;
            return `${valor} metros son ${resultado} kilómetros.`;
        case 'km-m':
            resultado = valor * 1000;
            return `${valor} kilómetros son ${resultado} metros.`;
        case 'c-f':
            resultado = (valor * 9/5) + 32;
            return `${valor}°C son ${resultado}°F.`;
        case 'f-c':
            resultado = (valor - 32) * 5/9;
            return `${valor}°F son ${resultado.toFixed(2)}°C.`;
        default:
            return 'Conversión no válida.';
    }
}

convertirBtn.addEventListener('click', () => {
    const tipo = tipoConversion.value;
    const valor = parseFloat(valorInput.value);

    if (isNaN(valor)) {
        resultadoParrafo.textContent = 'Por favor, ingresa un valor numérico válido';
        return;
    }

    const resultado = convertirUnidades(tipo, valor);
    resultadoParrafo.textContent = resultado;
})