import random

numero_secreto = random.randint(1, 100)
print("¡Bienvenido al juego de Adivinar el Número!")
print("He pensado en un número entre 1 y 100. ¿Puedes adivinar cuál es?")
print("Tienes 10 intentos para adivinar el número. ¡Buena suerte!")

encontrado = False
intentos = 0
intentos_maximos = 10

while not encontrado and intentos < intentos_maximos:
    intento = int(input("Introduce tu número: "))
    intentos += 1

    diferencia = abs(numero_secreto - intento)

    if intento == numero_secreto:
        print(f"¡Felicidades! Has adivinado el número en {intentos} intentos.")
        encontrado = True
    elif diferencia > 20:
        print("Estás muy lejos.")
    elif diferencia > 10:
        print("Estás algo lejos.")
    elif diferencia > 5:
        print("Estás cerca.")
    else:
        print("Estás muy cerca.")

    if intento < numero_secreto:
        print("El número es más alto. Intenta de nuevo.")
    elif intento > numero_secreto:
        print("El número es más bajo. Intenta de nuevo.")

if not encontrado:
    print(f"Lo siento, has agotados tus {intentos_maximos} intentos. El número era {numero_secreto}.")