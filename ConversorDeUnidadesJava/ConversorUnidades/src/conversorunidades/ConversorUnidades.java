
package conversorunidades;

import java.util.Scanner;

public class ConversorUnidades {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        int opcion;
        
        do {
            System.out.println("\n---Conversor de Unidades---");
            System.out.println("1. Metros a Kilómetros");
            System.out.println("2. Kilómetros a Metros");
            System.out.println("3. Celsius a Fahrenheit");
            System.out.println("4. Fahrenheit a Celsius");
            System.out.println("5. Kilogramos a Libras");
            System.out.println("6. Libras a Kilogramos");
            System.out.println("7. Salir");
            opcion = entrada.nextInt();
            
            switch (opcion) {
                case 1:
                    convertirMetrosAKilometros(entrada);
                    break;
                case 2:
                    convertirKilometrosAMetros(entrada);
                    break;
                case 3:
                    converirCelsiusAFahrenheit(entrada);
                    break;
                case 4:
                    convertirFahrenheitACelsius(entrada);
                    break;
                case 5:
                    convertirKilogramosALibras(entrada);
                    break;
                case 6:
                    convertirLibrasAKilogramos(entrada);
                    break;
                case 7:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 7);  
        entrada.close();
    }
    
    public static void convertirMetrosAKilometros(Scanner entrada) {
        System.out.println("Ingrese la cantidad en metros: ");
        double metros = entrada.nextDouble();
        double kilometros = metros / 1000;
        System.out.printf("%.2f m son %.2f km.%n", metros, kilometros);
    }
    
    public static void convertirKilometrosAMetros(Scanner entrada) {
        System.out.println("Ingrese la cantidad en kilómetros: ");
        double kilometros = entrada.nextDouble();
        double metros = kilometros * 1000;
        System.out.printf("%.2f km son %.2f m.%n", kilometros, metros);
    }
    
    public static void converirCelsiusAFahrenheit(Scanner entrada) {
        System.out.println("Ingrese la temperatura en Celsius: ");
        double celsius = entrada.nextDouble();
        double fahrenheit = (celsius * 9/5) + 32;
        System.out.printf("%.2f °C son %.2f °F.%n", celsius, fahrenheit);
    }
    
    public static void convertirFahrenheitACelsius(Scanner entrada) {
        System.out.println("Ingrese la temperatura en Fahrenheit: ");
        double fahrenheit = entrada.nextDouble();
        double celsius = (fahrenheit - 32) * 5/9;
        System.out.printf("%.2f °F son %.2f °C.%n", fahrenheit, celsius);
    }
    
    public static void convertirKilogramosALibras(Scanner entrada) {
        System.out.println("Ingrese el peso en kilogramos: ");
        double kilogramos = entrada.nextDouble();
        double libras = kilogramos * 2.20462;
        System.out.printf("%.2f kg son %.2f lb.%n", kilogramos, libras);
    }
    
    public static void convertirLibrasAKilogramos(Scanner entrada) {
        System.out.println("Ingresa el peso en libras: ");
        double libras = entrada.nextDouble();
        double kilogramos = libras / 2.20462;
        System.out.printf("%.2f lb son %.2f kg.%n", libras, kilogramos);
    }
}
