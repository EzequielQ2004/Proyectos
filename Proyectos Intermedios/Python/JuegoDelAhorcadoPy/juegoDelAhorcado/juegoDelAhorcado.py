import random

palabras = ['python', 'programacion', 'ahorcado', 'desarrollo', 'codigo']

palabra = random.choice(palabras)
letras_adivinadas = []
intentos = 6

def mostrar_progreso():
    progreso = ''
    for letra in palabra:
        if letra in letras_adivinadas:
            progreso += letra
        else:
            progreso += '_'
    return progreso

while intentos > 0:
    print(f'Palabra: {mostrar_progreso()}')
    print(f'Intentos restantes: {intentos}')
    letra = input('Adivina una letra: ').lower()

    if letra in letras_adivinadas:
        print('Ya has adivinado esa letra.')
    elif letra in palabra:
        letras_adivinadas.append(letra)
        print('¡Bien hecho!')
    else:
        intentos -= 1
        print('Letra incorrecta.')

    if set(letras_adivinadas) == set(palabra):
        print(f'¡Felicidades! Has adivinado la palabra {palabra}')
        break
else:
    print(f'Lo siento, has perdido, la palabra era: {palabra}')
