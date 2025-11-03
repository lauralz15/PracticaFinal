import java.util.regex.Pattern;

public class FENParser {
    // Si true, usar símbolos ASCII en lugar de Unicode (p. ej. pasar -Dfen.ascii=true)
    private boolean useAscii;
    
    public FENParser() {
        // Siempre usar ASCII por defecto
        useAscii = true;
        
        try {
            // 1. Verificar codificación del sistema
            String encoding = System.getProperty("file.encoding", "").toLowerCase();
            String consoleEncoding = System.getProperty("sun.stdout.encoding", "").toLowerCase();
            
            if (encoding.contains("utf") || consoleEncoding.contains("utf")) {
                // 2. Verificar si TERM o WT_SESSION están definidos (indica terminal moderna)
                String term = System.getenv("TERM");
                String wtSession = System.getenv("WT_SESSION");
                
                // 3. Intentar escribir y limpiar un carácter de prueba
                if (term != null || wtSession != null) {
                    System.out.print("\r♔\b");
                    System.out.flush();
                    useAscii = false;
                }
            }
        } catch (Exception e) {
            // Si algo falla, mantener ASCII
            useAscii = true;
        }
    }
    
    // Helper para elegir texto unicode o ASCII según la configuración
    private String t(String unicode, String ascii) {
        return useAscii ? ascii : unicode;
    }
    // Expresiones regulares para validar cada componente
    private static final Pattern PIECE_PATTERN = Pattern.compile("[pnbrqkPNBRQK]");
    private static final Pattern DIGIT_PATTERN = Pattern.compile("[1-8]");
    private static final Pattern SIDE_PATTERN = Pattern.compile("[wb]");
    private static final Pattern CASTLING_PATTERN = Pattern.compile("^(-|K?Q?k?q?)$");
    private static final Pattern EN_PASSANT_PATTERN = Pattern.compile("^(-|[a-h][36])$");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]+$");
    private static final Pattern POSITIVE_NUMBER_PATTERN = Pattern.compile("^[1-9][0-9]*$");
    
    // Símbolos Unicode para las piezas
    private static final String BLACK_ROOK = "♜";
    private static final String BLACK_KNIGHT = "♞";
    private static final String BLACK_BISHOP = "♝";
    private static final String BLACK_QUEEN = "♛";
    private static final String BLACK_KING = "♚";
    private static final String BLACK_PAWN = "♟";
    
    private static final String WHITE_ROOK = "♖";
    private static final String WHITE_KNIGHT = "♘";
    private static final String WHITE_BISHOP = "♗";
    private static final String WHITE_QUEEN = "♕";
    private static final String WHITE_KING = "♔";
    private static final String WHITE_PAWN = "♙";
    
    public void parse(String fen) {
        if (fen == null || fen.isEmpty()) {
            System.out.println(t(" Cadena FEN inválida"," Cadena FEN invalida"));
            System.out.println(t("Error: Cadena FEN vacía","Error: Cadena FEN vacia"));
            return;
        }

        String[] parts = fen.split(" ");

        // Validar que tenga exactamente 6 componentes
        if (parts.length != 6) {
            System.out.println(t(" Cadena FEN inválida"," Cadena FEN invalida"));
            System.out.println(t("Error: La notación FEN debe tener 6 componentes separados por espacios.",
                                 "Error: La notacion FEN debe tener 6 componentes separados por espacios."));
            System.out.println(t("Formato: <posición> <turno> <enroque> <al paso> <medio-mov> <mov-completo>",
                                 "Formato: <posicion> <turno> <enroque> <al paso> <medio-mov> <mov-completo>"));
            return;
        }
        
        // Validar cada componente
        if (!validatePiecePlacement(parts[0])) return;
        if (!validateSideToMove(parts[1])) return;
        if (!validateCastling(parts[2])) return;
        if (!validateEnPassant(parts[3])) return;
        if (!validateHalfmoveClock(parts[4])) return;
        if (!validateFullmoveCounter(parts[5])) return;
        
    // Si todo es válido, mostrar el tablero
    System.out.println(t(" Cadena FEN válida\n"," Cadena FEN valida\n"));
        displayBoard(parts[0]);
        displayGameInfo(parts);
    }
    
    private boolean validatePiecePlacement(String placement) {
        String[] ranks = placement.split("/");
        
        if (ranks.length != 8) {
            System.out.println(t(" Cadena FEN inválida"," Cadena FEN invalida"));
            System.out.println(t("Error: El tablero debe tener exactamente 8 filas separadas por '/'",
                                 "Error: El tablero debe tener exactamente 8 filas separadas por '/'."));
            return false;
        }
        
        // Validar cada fila
        for (int i = 0; i < ranks.length; i++) {
            String rank = ranks[i];
            int squareCount = 0;
            
            for (char c : rank.toCharArray()) {
                if (Character.isDigit(c)) {
                    int digit = Character.getNumericValue(c);
                    if (digit < 1 || digit > 8) {
                        System.out.println(t(" Cadena FEN inválida"," Cadena FEN invalida"));
                        System.out.println(t("Error en fila " + (8-i) + ": Los números deben estar entre 1 y 8",
                                             "Error en fila " + (8-i) + ": Los numeros deben estar entre 1 y 8"));
                        return false;
                    }
                    squareCount += digit;
                } else if (PIECE_PATTERN.matcher(String.valueOf(c)).matches()) {
                    squareCount++;
                } else {
                    System.out.println(t(" Cadena FEN inválida"," Cadena FEN invalida"));
                    System.out.println(t("Error en fila " + (8-i) + ": Carácter inválido '" + c + "'",
                                         "Error en fila " + (8-i) + ": Caracter invalido '" + c + "'"));
                    System.out.println(t("Solo se permiten: p,n,b,r,q,k (negras) P,N,B,R,Q,K (blancas) y números 1-8",
                                         "Solo se permiten: p,n,b,r,q,k (negras) P,N,B,R,Q,K (blancas) y numeros 1-8"));
                    return false;
                }
            }
            
            if (squareCount != 8) {
                System.out.println(t(" Cadena FEN inválida"," Cadena FEN invalida"));
                System.out.println(t("Error en fila " + (8-i) + ": Tiene " + squareCount + " casillas, debe tener exactamente 8",
                                     "Error en fila " + (8-i) + ": Tiene " + squareCount + " casillas, debe tener exactamente 8"));
                return false;
            }
        }
        
        return true;
    }
    
    private boolean validateSideToMove(String side) {
        if (!SIDE_PATTERN.matcher(side).matches()) {
            System.out.println(t(" Cadena FEN inválida"," Cadena FEN invalida"));
            System.out.println(t("Error en 'Side to move': Debe ser 'w' (blancas) o 'b' (negras)",
                                 "Error en 'Side to move': Debe ser 'w' (blancas) o 'b' (negras)"));
            System.out.println(t("Recibido: '" + side + "'","Recibido: '" + side + "'"));
            return false;
        }
        return true;
    }
    
    private boolean validateCastling(String castling) {
        if (!CASTLING_PATTERN.matcher(castling).matches()) {
            System.out.println(t(" Cadena FEN inválida"," Cadena FEN invalida"));
            System.out.println(t("Error en 'Castling ability': Debe ser '-' o una combinación de K, Q, k, q",
                                 "Error en 'Castling ability': Debe ser '-' o una combinacion de K, Q, k, q"));
            System.out.println(t("Recibido: '" + castling + "'","Recibido: '" + castling + "'"));
            return false;
        }
        
        // Validar que no haya letras duplicadas
        if (castling.length() != castling.chars().distinct().count()) {
            System.out.println(t(" Cadena FEN inválida"," Cadena FEN invalida"));
            System.out.println(t("Error en 'Castling ability': No puede haber letras duplicadas",
                                 "Error en 'Castling ability': No puede haber letras duplicadas"));
            return false;
        }
        
        return true;
    }
    
    private boolean validateEnPassant(String enPassant) {
        if (!EN_PASSANT_PATTERN.matcher(enPassant).matches()) {
            System.out.println(t(" Cadena FEN inválida"," Cadena FEN invalida"));
            System.out.println(t("Error en 'En passant': Debe ser '-' o una casilla como 'e3' o 'e6'",
                                 "Error en 'En passant': Debe ser '-' o una casilla como 'e3' o 'e6'"));
            System.out.println(t("Recibido: '" + enPassant + "'","Recibido: '" + enPassant + "'"));
            return false;
        }
        return true;
    }
    
    private boolean validateHalfmoveClock(String halfmove) {
        if (!NUMBER_PATTERN.matcher(halfmove).matches()) {
            System.out.println(t(" Cadena FEN inválida"," Cadena FEN invalida"));
            System.out.println(t("Error en 'Halfmove clock': Debe ser un número entero no negativo",
                                 "Error en 'Halfmove clock': Debe ser un numero entero no negativo"));
            System.out.println(t("Recibido: '" + halfmove + "'","Recibido: '" + halfmove + "'"));
            return false;
        }
        return true;
    }
    
    private boolean validateFullmoveCounter(String fullmove) {
        if (!POSITIVE_NUMBER_PATTERN.matcher(fullmove).matches()) {
            System.out.println(t(" Cadena FEN inválida"," Cadena FEN invalida"));
            System.out.println(t("Error en 'Fullmove counter': Debe ser un número entero positivo (mayor a 0)",
                                 "Error en 'Fullmove counter': Debe ser un numero entero positivo (mayor a 0)"));
            System.out.println(t("Recibido: '" + fullmove + "'","Recibido: '" + fullmove + "'"));
            return false;
        }
        return true;
    }
    
    private void displayBoard(String placement) {
        String[] ranks = placement.split("/");
        String fileHeader = "    a   b   c   d   e   f   g   h";
        String topBorder = useAscii ? "  +---+---+---+---+---+---+---+---+" : "  ┌───┬───┬───┬───┬───┬───┬───┬───┐";
        String middleBorder = useAscii ? "  +---+---+---+---+---+---+---+---+" : "  ├───┼───┼───┼───┼───┼───┼───┼───┤";
        String bottomBorder = useAscii ? "  +---+---+---+---+---+---+---+---+" : "  └───┴───┴───┴───┴───┴───┴───┴───┘";
        String leftNumSep = useAscii ? " |" : " │";
        String cellEmpty = "   ";
        String cellFmt = useAscii ? " %s |" : " %s │";

        System.out.println(fileHeader);
        System.out.println(topBorder);
        
        for (int i = 0; i < ranks.length; i++) {
            System.out.print((8 - i) + leftNumSep);
            
            String rank = ranks[i];
            for (char c : rank.toCharArray()) {
                if (Character.isDigit(c)) {
                    int emptySquares = Character.getNumericValue(c);
                    for (int j = 0; j < emptySquares; j++) {
                        System.out.print(cellEmpty + (useAscii ? "|" : "│"));
                    }
                } else {
                    String piece = getPieceSymbol(c);
                    System.out.print(String.format(cellFmt, piece));
                }
            }
            
            System.out.print(" " + (8 - i));
            
            if (i < ranks.length - 1) {
                System.out.println();
                System.out.println(middleBorder);
            }
        }
        
        System.out.println();
        System.out.println(bottomBorder);
        System.out.println(fileHeader + "\n");
    }
    
    private String getPieceSymbol(char piece) {
        if (useAscii) {
            switch (piece) {
                // Piezas negras en minúsculas
                case 'r': return "t";  // torre
                case 'n': return "c";  // caballo
                case 'b': return "a";  // alfil
                case 'q': return "d";  // dama
                case 'k': return "r";  // rey
                case 'p': return "p";  // peón
                // Piezas blancas en mayúsculas
                case 'R': return "T";  // Torre
                case 'N': return "C";  // Caballo
                case 'B': return "A";  // Alfil
                case 'Q': return "D";  // Dama
                case 'K': return "R";  // Rey
                case 'P': return "P";  // Peón
                default: return " ";
            }
        } else {
            switch (piece) {
                case 'r': return BLACK_ROOK;
                case 'n': return BLACK_KNIGHT;
                case 'b': return BLACK_BISHOP;
                case 'q': return BLACK_QUEEN;
                case 'k': return BLACK_KING;
                case 'p': return BLACK_PAWN;
                case 'R': return WHITE_ROOK;
                case 'N': return WHITE_KNIGHT;
                case 'B': return WHITE_BISHOP;
                case 'Q': return WHITE_QUEEN;
                case 'K': return WHITE_KING;
                case 'P': return WHITE_PAWN;
                default: return " ";
            }
        }
    }
    
    private void displayGameInfo(String[] parts) {
    System.out.println(t("📋 Información de la partida:", "Informacion de la partida:"));
        System.out.println("   Turno: " + (parts[1].equals("w") ? "Blancas" : "Negras"));
        
        String castling = parts[2];
        if (castling.equals("-")) {
            System.out.println("   Enroque: Ninguno disponible");
        } else {
            System.out.println("   Enroque disponible: " + castling);
        }
        
        String enPassant = parts[3];
        if (enPassant.equals("-")) {
            System.out.println("   Al paso: No disponible");
        } else {
            System.out.println("   Casilla al paso: " + enPassant);
        }
        
        System.out.println("   Medios movimientos: " + parts[4]);
        System.out.println("   Movimiento completo: " + parts[5]);
    }
}