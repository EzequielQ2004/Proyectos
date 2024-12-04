// Variables globales
let palabraSecreta = "";
let progreso = [];
let intentos = 6;
let letrasUsadas = [];

// Elementos del DOM
const progressElement = document.getElementById("progress");
const attemptsElement = document.getElementById("attempts");
const lettersElement = document.getElementById("letters");
const resultElement = document.getElementById("result");
const inputLetter = document.getElementById("inputLetter");
const submitLetter = document.getElementById("submitLetter");

// Función para inicializar el juego
function inicializarJuego() {
    progreso = Array(palabraSecreta.length).fill("_");
    intentos = 6;
    letrasUsadas = [];
    actualizarPantalla();
}

// Función para actualizar la pantalla
function actualizarPantalla() {
    progressElement.textContent = `Progreso: ${progreso.join(" ")}`;
    attemptsElement.textContent = `Intentos restantes: ${intentos}`;
    lettersElement.textContent = `Letras usadas: ${letrasUsadas.join(", ") || "Ninguna"}`;
    resultElement.textContent = ""; // Limpiar el mensaje de resultado
}

// Cargar palabras desde el archivo JSON
fetch("palabras.json")
    .then(response => response.json())
    .then(data => {
        const palabras = data.palabras;
        palabraSecreta = palabras[Math.floor(Math.random() * palabras.length)];
        inicializarJuego(); // Inicializa el juego con la palabra seleccionada
    })
    .catch(error => console.error("Error al cargar el archivo JSON:", error));

// Lógica para manejar la letra ingresada
submitLetter.addEventListener("click", () => {
    const letra = inputLetter.value.toLowerCase();
    inputLetter.value = "";

    if (!letra || letrasUsadas.includes(letra)) {
        resultElement.textContent = "Introduce una letra válida o que no hayas usado.";
        return;
    }

    letrasUsadas.push(letra);

    if (palabraSecreta.includes(letra)) {
        // Actualiza el progreso con la letra correcta
        for (let i = 0; i < palabraSecreta.length; i++) {
            if (palabraSecreta[i] === letra) {
                progreso[i] = letra;
            }
        }
        resultElement.textContent = "¡Bien hecho! Encontraste una letra.";
    } else {
        // Reduce los intentos
        intentos--;
        resultElement.textContent = "Letra incorrecta.";
    }

    // Verifica si ganó o perdió
    if (progreso.join("") === palabraSecreta) {
        resultElement.textContent = "¡Felicidades! Adivinaste la palabra.";
        submitLetter.disabled = true;
        inputLetter.disabled = true;
    } else if (intentos === 0) {
        resultElement.textContent = `¡Perdiste! La palabra era "${palabraSecreta}".`;
        submitLetter.disabled = true;
        inputLetter.disabled = true;
    }

    actualizarPantalla();
});