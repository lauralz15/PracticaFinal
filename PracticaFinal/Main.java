import java.util.Scanner;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Main {
    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // Si no se soporta UTF-8, se usa la salida por defecto
        }

        boolean useAscii = Boolean.getBoolean("fen.ascii");
        Scanner scanner = new Scanner(System.in);
        FENParser parser = new FENParser();
        
        if (useAscii) {
            System.out.println("+----------------------------------------------+");
            System.out.println("|                 PROYECTO FINAL               |");
            System.out.println("|     ANALIZADOR SINTACTICO DE NOTACION FEN    |");
            System.out.println("+----------------------------------------------+\n");
        } else {
            System.out.println("╔════════════════════════════════════════════════╗");
            System.out.println("║                  PROYECTO FINAL                ║");
            System.out.println("║      ANALIZADOR SINTÁCTICO DE NOTACIÓN FEN     ║");
            System.out.println("╚════════════════════════════════════════════════╝\n");
        }
        
        while (true) {
            System.out.println("\nIngresa una cadena FEN (o 'salir' para terminar):");
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("salir")) {
                System.out.println("\n" + (useAscii ? "Hasta pronto!" : "¡Hasta pronto!"));
                break;
            }
            
            System.out.println("\n" + (useAscii ? "=".repeat(50) : "═".repeat(50)));
            parser.parse(input);
            System.out.println(useAscii ? "=".repeat(50) : "═".repeat(50));
        }
        
        scanner.close();
    }
}