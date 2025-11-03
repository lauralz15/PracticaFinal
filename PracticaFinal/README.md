# Analizador Sintáctico de Notación FEN

Este programa analiza y visualiza posiciones de ajedrez en notación FEN (Forsyth–Edwards Notation).

## Configuración del Terminal (PowerShell)

Para ver correctamente los símbolos Unicode de las piezas de ajedrez, ejecuta estos comandos en PowerShell:
```powershell
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
chcp 65001
```

## Ejecución del Programa

```bash
# Ejecución normal (con piezas Unicode)
java -Dfile.encoding=UTF-8 -cp . Main

# Modo ASCII alternativo (si los símbolos Unicode no se ven bien)
java -Dfile.encoding=UTF-8 -Dfen.ascii=true -cp . Main
```

## Formato FEN

La notación FEN describe una posición de ajedrez y debe incluir 6 componentes separados por espacios:
1. Posición de piezas
2. Turno (w/b)
3. Enroque disponible (KQkq/-)
4. Casilla al paso
5. Número de medios movimientos
6. Número de movimiento completo

Ejemplo: `rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1`

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