def mostrar_menu():
    print("Bienvenidos al Conversor de Unidades")
    print("1. Metros a Kilómetros")
    print("2. Kilómetros a Metros")
    print("3. Celsius a Fahrenheit")
    print("4. Fahrenheit a Celsius")
    print("5. Kilogramos a Libras")
    print("6. Libras a Kilogramos")
    print("7. Salir")

def convertir_metros_a_kilometros(metros):
    return metros / 1000
def convertir_kilometros_a_metros(kilometros):
    return kilometros * 1000
def convertir_celsius_a_fahrenheit(celsius):
    return (celsius * 9/5) + 32
def convertir_fahrenheit_a_celsius(fahrenheit):
    return (fahrenheit - 32) * 5/9
def convertir_kilogramos_a_libras(kilogramos):
    return kilogramos * 2.20462
def convertir_libras_a_kilogramos(libras):
    return libras / 2.20462

def main():
    while True:
        mostrar_menu()
        opcion = input("Selecciona una opción (1-7): ")

        if opcion == "1":
            metros = float(input("Ingresa la cantidad en metros: "))
            print(f"{metros} metros son {convertir_metros_a_kilometros(metros)} kilómetros.")
        elif opcion == "2":
            kilometros = float(input("Ingresa la cantidad en kilómetros: "))
            print(f"{kilometros} kilómetros son {convertir_kilometros_a_metros(kilometros)} metros.")
        elif opcion == "3":
            celsius = float(input("Ingresa la temperatura en Celsius: "))
            print(f"{celsius}°C son {convertir_celsius_a_fahrenheit(celsius)}°F.")
        elif opcion == "4":
            fahrenheit = float(input("Ingresa la temperatura en Fahrenheit: "))
            print(f"{fahrenheit}°F son {convertir_fahrenheit_a_celsius(fahrenheit)}°C.")
        elif opcion == "5":
            kilogramos = float(input("Ingresa el peso en kilogramos: "))
            print(f"{kilogramos} kg son {convertir_kilogramos_a_libras(kilogramos)} lb.")
        elif opcion == "6":
            libras = float(input("Ingresa el peso en libras: "))
            print(f"{libras} lb son {convertir_libras_a_kilogramos(libras)} kg.")
        elif opcion == "7":
            print("¡Gracias por usar el Conversor de Unidades!")
            break
        else:
            print("Opción no válida. Por favor, intenta de nuevo.")
        print()

if __name__=="__main__":
    main()