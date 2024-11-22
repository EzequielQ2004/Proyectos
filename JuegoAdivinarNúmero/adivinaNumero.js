const numeroSecreto = Math.floor(Math.random() * 100) + 1;
//console.log("Número secreto (solo para pruebas):", numeroSecreto);

const input = document.getElementById("adivinar");
const boton = document.getElementById("enviarAdivinanza");
const mensaje = document.getElementById("mensaje");

boton.addEventListener("click", () => {
    const intento = parseInt(input.value);

    if (isNaN(intento) || intento < 1 || intento > 100) {
        mensaje.textContent = "Por favor, introduce un número válido.";
        return;
    }

    if (intento === numeroSecreto) {
        mensaje.textContent = "¡Felicidades! Adivinaste el número.";
        boton.disabled = true;
        reiniciarBoton.style.display = "inline";
    } else if (intento < numeroSecreto) {
        mensaje.textContent = "El número es más alto.";
    } else {
        mensaje.textContent = "El número es más bajo.";
    }
});

const reiniciarBoton = document.createElement("boton");
reiniciarBoton.textContent = "Reiniciar juego";
reiniciarBoton.style.display = "none";
document.body.appendChild(reiniciarBoton);

reiniciarBoton.addEventListener("click", () => {
    location.reload();
})
