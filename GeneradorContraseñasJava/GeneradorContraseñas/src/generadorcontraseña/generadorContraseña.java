package generadorcontraseña;

import java.util.Scanner;
import java.util.Random;

public class generadorContraseña {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        System.out.println("Bienvenido al Generador de Contraseñas");

        System.out.println("Ingresa la longitud deseada para la contraseña: ");
        int longitud = entrada.nextInt();

        System.out.println("¿Incluir números? (s/n): ");
        boolean incluirNumeros = entrada.next().equalsIgnoreCase("s");

        System.out.println("¿Incluir mayúsculas? (s/n): ");
        boolean incluirMayusculas = entrada.next().equalsIgnoreCase("s");

        System.out.println("¿Incluir caracteres especiales? (s/n): ");
        boolean incluirEspeciales = entrada.next().equalsIgnoreCase("s");

        String contraseña = generarContraseña(longitud, incluirNumeros, incluirMayusculas, incluirEspeciales);
        System.out.println("Tu contraseña generada es: " + contraseña);

        entrada.close();
    }

    public static String generarContraseña(int longitud, boolean incluirNumeros, boolean incluirMayusculas, boolean incluirEspeciales) {
        String letras = "abcdefghijklmnopqrstuvwxyz";
        String numeros = "0123456789";
        String mayusculas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String especiales = "!@#$%^&*()_-+<>?/";

        String conjunto = letras;
        if (incluirNumeros) {
            conjunto += numeros;
        }
        if (incluirMayusculas) {
            conjunto += mayusculas;
        }
        if (incluirEspeciales) {
            conjunto += especiales;
        }

        Random random = new Random();
        StringBuilder contraseña = new StringBuilder();

        for (int i = 0; i < longitud; i++) {
            int indice = random.nextInt(conjunto.length());
            contraseña.append(conjunto.charAt(indice));
        }

        return contraseña.toString();

    }
}
