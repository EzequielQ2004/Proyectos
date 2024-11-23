
package adivinarnumero;

import java.util.Scanner;
import java.util.Random;

public class adivinarNumero {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        Random random = new Random();
        int numeroAleatorio = random.nextInt(100) + 1;
        
        boolean numeroAdivinado = false;
        int intentos = 0;
        int intentosMaximos = 10;
        
        System.out.println("¡Bienvenido al juego de adivinar el número!");
        System.out.println("He pensado en un número entre 1 y 100. ¿Puedes adivinar cuál es?");
        System.out.println("Tienes 10 intentos para adivinar el número. ¡Buena suerte!");
        System.out.println(numeroAleatorio);
        
        while (!numeroAdivinado || intentos < intentosMaximos) {
            System.out.println("Introduce tu número: ");
            int intento = entrada.nextInt();
            intentos += 1;
            
            if (intentos == intentosMaximos) {
            System.out.println("Lo siento, has agotados tus " + intentosMaximos + " intentos. El número era "+ numeroAleatorio);
            break;
            }
            
            if (intento < numeroAleatorio) {
                System.out.println("El número es más alto. Intenta de nuevo.");
            } else if (intento > numeroAleatorio) {
                System.out.println("El número es más bajo. Intenta de nuevo.");
            } else if (intento == numeroAleatorio) {
                System.out.println("¡Felicidades! Has adivinado el número en " + intentos + " intentos.");
                break;
            }
        }
        entrada.close();
    }
}
