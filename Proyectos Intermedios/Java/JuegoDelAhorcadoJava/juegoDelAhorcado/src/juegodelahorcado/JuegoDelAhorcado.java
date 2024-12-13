package juegodelahorcado;

import java.util.Scanner;
import java.util.Random;

public class JuegoDelAhorcado {

    public static void main(String[] args) {
        jugar();
    }

    public static void jugar() {
        String[] palabras = {
            "programacion", "computadora", "java",
            "desarrollo", "software", "tecnologia"
        };

        Random random = new Random();
        String palabraSecreta = palabras[random.nextInt(palabras.length)];

        Scanner entrada = new Scanner(System.in);
        int intentosMaximos = 6;
        int intentosRestantes = intentosMaximos;
        boolean[] letrasAdivinadas = new boolean[palabraSecreta.length()];

        System.out.println("¡Bienvenido al Juego del Ahorcado!");
        System.out.println("Tienes " + intentosMaximos + " intentos para adivinar la palabra.");

        while (intentosRestantes > 0) {
            mostrarPalabra(palabraSecreta, letrasAdivinadas);

            mostrarAhorcado(intentosMaximos - intentosRestantes);

            System.out.println("\nIngresa una letra: ");
            char letra = entrada.next().toLowerCase().charAt(0);

            boolean letraCorrecta = false;
            for (int i = 0; i < palabraSecreta.length(); i++) {
                if (palabraSecreta.charAt(i) == letra) {
                    letrasAdivinadas[i] = true;
                    letraCorrecta = true;
                }
            }

            if (!letraCorrecta) {
                intentosRestantes--;
                System.out.println("\n¡Letra incorrecta! Te quedan " + intentosRestantes + " intentos.");
            }

            if (haGanado(palabraSecreta, letrasAdivinadas)) {
                mostrarPalabra(palabraSecreta, letrasAdivinadas);
                System.out.println("\n¡Felicidades! ¡Has adivinado la palabra!");
                return;
            }
        }

        System.out.println("\n¡Oh no! Se te han acabado los intentos.");
        System.out.println("La palabra era: " + palabraSecreta);
    }

    public static void mostrarPalabra(String palabraSecreta, boolean[] letrasAdivinadas) {
        System.out.println("\nPalabra: ");
        for (int i = 0; i < palabraSecreta.length(); i++) {
            if (letrasAdivinadas[i]) {
                System.out.println(palabraSecreta.charAt(i) + " ");
            } else {
                System.out.println("_ ");
            }
        }
        System.out.println();
    }

    public static void mostrarAhorcado(int intentosFallidos) {
        String[] ahorcado = {
            "",
            "  0  \n",
            "  0  \n  |  \n",
            "  0  \n /|  \n",
            "  0  \n /|\\ \n",
            "  0  \n /|\\ \n /   \n",
            "  0  \n /|\\ \n / \\ \n"
        };

        System.out.println("Ahorcado:");
        System.out.println(ahorcado[intentosFallidos]);
    }

    public static boolean haGanado(String palabraSecreta, boolean[] letrasAdivinadas) {
        for (boolean letraAdivinada : letrasAdivinadas) {
            if (!letraAdivinada) {
                return false;
            }
        }
        return true;
    }
}
