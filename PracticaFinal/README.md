# Analizador Sintáctico de Notación FEN

Este programa analiza y visualiza posiciones de ajedrez en notación FEN

## Características

- Valida la sintaxis de cadenas FEN
- Muestra el tablero de ajedrez visualmente
- Soporta dos modos de visualización:
  - Modo Unicode (♔♕♖♗♘♙♚♛♜♝♞♟) - piezas de ajedrez reales
  - Modo ASCII (R,D,T,A,C,P) - letras fáciles de entender

## Ejecutar el Programa

```bash
# Ejecución normal (detectará automáticamente si tu terminal soporta Unicode)
java -Dfile.encoding=UTF-8 -cp . Main

# Forzar modo ASCII si las piezas no se ven bien
java -Dfile.encoding=UTF-8 -Dfen.ascii=true -cp . Main
```

## Configurar Terminal para Ver Piezas Unicode

Para ver las piezas de ajedrez Unicode (♔♕♖♗♘♙), configura tu terminal:

### Windows Terminal (Recomendado)
1. Instala Windows Terminal desde Microsoft Store
2. Abre Windows Terminal
3. Configura la fuente:
   - Presiona Ctrl+, (Settings)
   - Selecciona PowerShell → Apariencia
   - Cambia la fuente a "Cascadia Code PL" o "Segoe UI Symbol"
4. Configura UTF-8:
   ```powershell
   [Console]::OutputEncoding = [System.Text.Encoding]::UTF8
   $OutputEncoding = [System.Text.Encoding]::UTF8
   chcp 65001
   ```

### PowerShell Classic
```powershell
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$OutputEncoding = [System.Text.Encoding]::UTF8
chcp 65001
```

### Solución de Problemas

Si ves caracteres extraños en lugar de piezas de ajedrez:

1. Verifica que usas una fuente compatible:
   - Cascadia Code PL
   - Cascadia Mono PL
   - Segoe UI Symbol
   - Noto Sans Mono

2. Asegúrate que la codificación es UTF-8:
   ```powershell
   chcp 65001
   ```

3. Si los problemas persisten, usa el modo ASCII:
   ```bash
   java -Dfile.encoding=UTF-8 -Dfen.ascii=true -cp . Main
   ```

## Ejemplo de Uso

1. Ejecuta el programa
2. Ingresa una cadena FEN (ejemplo):
   ```
   rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
   ```
3. El programa mostrará el tablero y la información de la posición

## Notación en Modo ASCII

Si usas el modo ASCII, las piezas se representan con letras:

Blancas (mayúsculas):
- T = Torre
- C = Caballo
- A = Alfil
- D = Dama
- R = Rey
- P = Peón

Negras (minúsculas):
- t = torre
- c = caballo
- a = alfil
- d = dama
- r = rey
- p = peón