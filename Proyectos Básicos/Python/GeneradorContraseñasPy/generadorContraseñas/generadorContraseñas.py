import random
import string

def generar_contrasena(longitud=8, incluir_mayusculas=True, incluir_numeros=True, incluir_simbolos=True):
    if longitud < 4:
        raise ValueError("La longitud mínima de la contraseña es 4 caracteres.")

    # Base de caracteres: minúsculas siempre incluidas
    caracteres = string.ascii_lowercase
    if incluir_mayusculas:
        caracteres += string.ascii_uppercase
    if incluir_numeros:
        caracteres += string.digits
    if incluir_simbolos:
        caracteres += string.punctuation

    # Garantizar que se incluya al menos un carácter de cada tipo
    contrasena = []
    if incluir_mayusculas:
        contrasena.append(random.choice(string.ascii_uppercase))
    if incluir_numeros:
        contrasena.append(random.choice(string.digits))
    if incluir_simbolos:
        contrasena.append(random.choice(string.punctuation))
    contrasena.append(random.choice(string.ascii_lowercase))

    # Completar hasta la longitud requerida
    contrasena += random.choices(caracteres, k=longitud - len(contrasena))

    # Mezclar los caracteres para mayor aleatoriedad
    random.shuffle(contrasena)

    return ''.join(contrasena)

print("Generador de contraseñas")

# Solicitar al usuario las configuraciones
longitud = int(input("Ingresa la longitud de la contraseña (mínimo 4): "))
usar_mayusculas = input("¿Incluir mayúsculas? (s/n): ").strip().lower() == 's'
usar_numeros = input("¿Incluir números? (s/n): ").strip().lower() == 's'
usar_simbolos = input("¿Incluir símbolos? (s/n): ").strip().lower() == 's'

# Generar la contraseña
try:
    contrasena = generar_contrasena(longitud, usar_mayusculas, usar_numeros, usar_simbolos)
    print(f"Tu contraseña generada es: {contrasena}")
except ValueError as e:
    print(f"Error: {e}")