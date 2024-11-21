package calculadorasimple;

import java.util.Scanner;

public class CalculadoraSimple {

    public static double sumar(double a, double b) {
        return a + b;
    }

    public static double restar(double a, double b) {
        return a - b;
    }

    public static double multiplicar(double a, double b) {
        return a * b;
    }

    public static double dividir(double a, double b) {
        if (b == 0) {
            System.out.println("Error: No se puede dividir por cero.");
            return Double.NaN;
        }
        return a / b;
    }

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        System.out.println("Bienvenido a la Calculadora Simple");
        System.out.println("1. Sumar");
        System.out.println("2. Restar");
        System.out.println("3. Multiplicar");
        System.out.println("4. Dividir");
        System.out.println("5. Salir");

        while (true) {
            System.out.println("\nIngresa tu opción (1/2/3/4/5): ");
            int opcion = entrada.nextInt();

            if (opcion == 5) {
                System.out.println("¡Gracias por usar la calculadora! Adiós.");
                break;
            }

            System.out.println("Ingrese el primer número: ");
            double num1 = entrada.nextDouble();
            System.out.println("Ingresa el segundo número: ");
            double num2 = entrada.nextDouble();

            double resultado;
            switch (opcion) {
                case 1:
                    resultado = sumar(num1, num2);
                    System.out.println("El resultado de la suma es: " + resultado);
                    break;
                case 2:
                    resultado = restar(num1, num2);
                    System.out.println("El resultado de la resta es: " + resultado);
                    break;
                case 3:
                    resultado = multiplicar(num1, num2);
                    System.out.println("El resultado de la multiplicación es: " + resultado);
                    break;
                case 4:
                    resultado = dividir(num1, num2);
                    if (!Double.isNaN(resultado)) {
                        System.out.println("El resultado de la división es: " + resultado);
                    }
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
        entrada.close();
    }
}
