// Lista de palabras para adivinar
const palabras = [
    'JAVASCRIPT', 'PROGRAMACION', 'COMPUTADORA', 'DESARROLLO', 
    'TECNOLOGIA', 'INTERNET', 'APRENDIZAJE', 'CODIGO'
];

// Variables del juego
let palabraActual = '';
let palabraOculta = [];
let intentosRestantes = 6;
let letrasUsadas = new Set();

// Elementos del DOM
const wordDisplay = document.getElementById('word-display');
const attemptsCount = document.getElementById('attempts-count');
const usedLettersDisplay = document.getElementById('used-letters-display');
const keyboard = document.getElementById('keyboard');
const message = document.getElementById('message');
const restartBtn = document.getElementById('restart-btn');

// Partes del cuerpo en el dibujo del ahorcado
const bodyParts = [
    'head', 'body', 'left-arm', 'right-arm', 'left-leg', 'right-leg'
];

// Iniciar el juego
function iniciarJuego() {
    // Reiniciar variables
    intentosRestantes = 6;
    letrasUsadas.clear();
    message.textContent = '';
    restartBtn.style.display = 'none';
    
    // Ocultar todas las partes del cuerpo
    bodyParts.forEach(part => {
        document.getElementById(part).style.display = 'none';
    });

    // Seleccionar una palabra al azar
    palabraActual = palabras[Math.floor(Math.random() * palabras.length)];
    
    // Crear la palabra oculta con guiones bajos
    palabraOculta = palabraActual.split('').map(() => '_');
    
    // Actualizar la pantalla
    actualizarPantalla();
    
    // Crear teclado
    crearTeclado();
}

// Actualizar la pantalla del juego
function actualizarPantalla() {
    // Mostrar palabra oculta
    wordDisplay.textContent = palabraOculta.join(' ');
    
    // Actualizar intentos restantes
    attemptsCount.textContent = intentosRestantes;
    
    // Mostrar letras usadas
    usedLettersDisplay.textContent = Array.from(letrasUsadas).join(', ');
}

// Crear teclado de letras
function crearTeclado() {
    // Limpiar teclado anterior
    keyboard.innerHTML = '';
    
    // Crear botones para cada letra del alfabeto
    for (let i = 65; i <= 90; i++) {
        const letra = String.fromCharCode(i);
        const boton = document.createElement('button');
        boton.textContent = letra;
        boton.classList.add('keyboard-letter');
        boton.addEventListener('click', () => procesarLetra(letra));
        keyboard.appendChild(boton);
    }
}

// Procesar la letra seleccionada
function procesarLetra(letra) {
    // Verificar si ya se usó la letra
    if (letrasUsadas.has(letra)) return;
    
    letrasUsadas.add(letra);
    
    // Actualizar botón de letra
    const botonLetra = Array.from(keyboard.children)
        .find(btn => btn.textContent === letra);
    
    if (palabraActual.includes(letra)) {
        // Letra correcta
        botonLetra.classList.add('correct');
        
        // Actualizar palabra oculta
        palabraActual.split('').forEach((letraActual, index) => {
            if (letraActual === letra) {
                palabraOculta[index] = letra;
            }
        });
        
        // Verificar si se adivinó la palabra
        if (!palabraOculta.includes('_')) {
            finDelJuego(true);
        }
    } else {
        // Letra incorrecta
        botonLetra.classList.add('incorrect');
        
        // Reducir intentos y mostrar parte del cuerpo
        intentosRestantes--;
        mostrarParteCuerpo();
        
        // Verificar si se acabaron los intentos
        if (intentosRestantes === 0) {
            finDelJuego(false);
        }
    }
    
    // Desactivar botón
    botonLetra.disabled = true;
    
    // Actualizar pantalla
    actualizarPantalla();
}

// Mostrar parte del cuerpo cuando se falla
function mostrarParteCuerpo() {
    const partIndex = 6 - intentosRestantes - 1;
    if (partIndex >= 0 && partIndex < bodyParts.length) {
        document.getElementById(bodyParts[partIndex]).style.display = 'block';
    }
}

// Fin del juego
function finDelJuego(victoria) {
    if (victoria) {
        message.textContent = '¡Felicidades! Has adivinado la palabra.';
        message.style.color = 'green';
    } else {
        message.textContent = `Juego terminado. La palabra era: ${palabraActual}`;
        message.style.color = 'red';
        // Mostrar todas las partes del cuerpo
        bodyParts.forEach(part => {
            document.getElementById(part).style.display = 'block';
        });
    }
    
    // Desactivar teclado
    Array.from(keyboard.children).forEach(btn => btn.disabled = true);
    
    // Mostrar botón de reinicio
    restartBtn.style.display = 'block';
}

// Evento de reinicio
restartBtn.addEventListener('click', iniciarJuego);

// Iniciar el juego al cargar la página
iniciarJuego();