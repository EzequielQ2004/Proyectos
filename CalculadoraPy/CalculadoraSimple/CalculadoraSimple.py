def sumar(a, b):
    return a + b

def restar(a, b):
    return a - b

def multiplicar(a, b):
    return a * b

def dividir(a, b):
    if b == 0:
        return "Error: No se puede dividir por cero."
    return a / b

def calculadora():
    print("Bienvenidos a la Calculadora Simple")
    print("Selecciona una operación:")
    print("1. Sumar")
    print("2. Restar")
    print("3. Multiplicar")
    print("4. Dividir")

    while True:
        opcion = input("Ingrese el número de la operación (1/2/3/4) o 'q' para salir: ")

        if opcion.lower() == 'q':
            print("¡Gracias por usar la calculadora! Adiós.")
            break

        if opcion not in ['1', '2', '3', '4']:
            print("Opción inválida. Intenta de nuevo.")
            continue

        try:
            num1 = float(input("Ingresa el primer número: "))
            num2 = float(input("Ingresa el segundo número: "))
        except ValueError:
            print("Por favor, ingresa números válidos.")
            continue

        if opcion == '1':
            resultado = sumar(num1, num2)
            print(f"El resultado de {num1} + {num2} es: {resultado}")
        elif opcion == '2':
            resultado = restar(num1, num2)
            print(f"El resultado de {num1} - {num2} es: {resultado}")
        elif opcion == '3':
            resultado = multiplicar(num1, num2)
            print(f"El resultado de {num1} * {num2} es: {resultado}")
        elif opcion == '4':
            resultado = dividir(num1, num2)
            print(f"El resultado de {num1} / {num2} es: {resultado}")

        print("\n")

if __name__ == "__main__":
    calculadora()