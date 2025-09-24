import java.util.Scanner;

public class Input {

    public static String scanString(String message, Scanner scanner) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public static int scanInt(String message, Scanner scanner) {
        while (true) {
            try {
                System.out.print(message);
                String line = scanner.nextLine();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            }
        }
    }

    public static double scanDouble(String message, Scanner scanner) {
        while (true) {
            try {
                System.out.print(message);
                String line = scanner.nextLine();
                line = line.replace(',', '.');
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número (ex: 15.5 ou 15,5).");
            }
        }
    }
}

