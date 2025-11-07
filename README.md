# Analizador Sintáctico de Notación FEN

**Proyecto Final - Lenguajes y paradigmas de Programación**  
**Octubre 2025**

---

## Integrantes del Proyecto

- **Juan Jose Sierra**
- **Laura Sofia Lizarazo**

---

## Descripción del Proyecto

Este programa es un **analizador sintáctico (parser)** de notación FEN para ajedrez. El sistema valida cadenas FEN según la gramática BNF especificada y, si la cadena es válida, visualiza el tablero de ajedrez con las piezas en sus posiciones correspondientes.

### Características principales:
- Validación completa de la gramática FEN según el estándar  
- Detección de errores específicos con mensajes descriptivos  
- Visualización del tablero con símbolos Unicode (♜♞♝♛♚♟)  
- Modo ASCII alternativo para compatibilidad en caso de que no se muestren los simbolos 
- Muestra información detallada de la partida  

---

## Lenguaje de Programación

**Java**

---
## Video de sustentacion del proyecto final
https://youtu.be/I9L7jbj8SM0
---

## Requisitos Mínimos

- **Java Development Kit (JDK)**: Versión 8 o superior
- **Editor de código**: Visual Studio Code (recomendado) o cualquier IDE de Java
- **Sistema Operativo**: Windows, macOS o Linux
- **Terminal**: PowerShell (Windows), Terminal (macOS/Linux)

---

## Instalación y Configuración recomendada

### 1. Clonar o descargar el repositorio

```bash
git clone [URL_DEL_REPOSITORIO]
cd PracticaFinal
```

### 2. Estructura de archivos

```
PracticaFinal/
├── Main.java
└── FENParser.java
```

---

## Ejecución del Programa

### Desde la terminal del sistema

**Windows (PowerShell):**                                               
```powershell                                                             
cd ruta\a\PracticaFinal
$env:TERM = "xterm-256color"
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
javac Main.java FENParser.java
java Main
```

---

## Uso del Programa

1. Al ejecutar el programa, verás el menú principal
2. Ingresa una cadena FEN válida cuando se te solicite
3. El programa validará la cadena y mostrará:
   - Si es válida: el tablero de ajedrez y la información de la partida
   - Si es inválida: un mensaje de error específico indicando el problema
4. Escribe `salir` para terminar el programa

---
### Representación de piezas:
- **Mayúsculas**: piezas blancas (`P N B R Q K`)
- **Minúsculas**: piezas negras (`p n b r q k`)
- **Números (1-8)**: casillas vacías consecutivas

---

## Ejemplos de Cadenas FEN

### Cadenas Válidas:

**Posición inicial del ajedrez:**
```
rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
```

<img width="727" height="814" alt="image" src="https://github.com/user-attachments/assets/8a47062e-78f1-4a03-b7d0-9d9be4f4395a" />

**Posición intermedia con al paso:**
```
rnbqkbnr/ppp1p1pp/8/3pPp2/8/8/PPPP1PPP/RNBQKBNR w KQkq f6 0 3
```

<img width="778" height="814" alt="image" src="https://github.com/user-attachments/assets/350fa31b-a5db-4ccf-bfa8-b6aaa5c685de" />


**Final de partida:**
```
2r3k1/p3bqp1/Q2p3p/3Pp3/P3N3/8/5PPP/5RK1 b - - 1 27
```

<img width="674" height="815" alt="image" src="https://github.com/user-attachments/assets/3d3d3c37-1993-46f3-85ce-a73eb85fc20a" />


### Cadenas Inválidas (ejemplos de errores):

**Error: Fila con más de 8 casillas:**
```
2r3k17/p3bqp1/Q2p3p/3Pp3/P3N3/8/5PPP/5RK1 b - - 1 27
```

<img width="759" height="218" alt="image" src="https://github.com/user-attachments/assets/8342ca96-9973-41f1-bd36-7b003ec33449" />

**Error: Pieza no válida (C no existe):**
```
2r3k1/p3bqp1/Q2p3p/3Pp3/P3C3/8/5PPP/5RK1 b - - 1 27
```

<img width="934" height="233" alt="image" src="https://github.com/user-attachments/assets/ca3fd2dc-775b-4002-9c94-f55049a2f31b" />


**Error: Solo 7 filas (faltan filas):**
```
rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP w KQkq - 0 1
```

<img width="846" height="205" alt="image" src="https://github.com/user-attachments/assets/b163dd0b-c160-4cb9-b906-1b80c08b0995" />


**Error: Turno inválido:**
```
rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR x KQkq - 0 1
```

<img width="788" height="240" alt="image" src="https://github.com/user-attachments/assets/0ae4fa91-06e3-4a91-aeb3-d6b8a31f3c55" />


---

## Visualización del Tablero

### Modo Unicode (predeterminado):

El programa muestra las piezas con símbolos Unicode:

| Pieza | Blancas | Negras |
|-------|---------|--------|
| Rey   | ♔ | ♚ |
| Dama  | ♕ | ♛ |
| Torre | ♖ | ♜ |
| Alfil | ♗ | ♝ |
| Caballo | ♘ | ♞ |
| Peón | ♙ | ♟ |

### Modo ASCII (alternativo):

Si los símbolos Unicode no se visualizan correctamente, el programa puede usar letras:

**Piezas Blancas (mayúsculas):**
- `T` = Torre
- `C` = Caballo
- `A` = Alfil
- `D` = Dama
- `R` = Rey
- `P` = Peón

**Piezas Negras (minúsculas):**
- `t` = torre
- `c` = caballo
- `a` = alfil
- `d` = dama
- `r` = rey
- `p` = peón

## Arquitectura del Programa

### `Main.java`
- Punto de entrada del programa
- Gestiona la interfaz de usuario
- Configura la salida UTF-8 para símbolos Unicode

### `FENParser.java`
- Contiene toda la lógica de validación
- Implementa las reglas de la gramática BNF
- Gestiona la visualización del tablero
- Auto-detecta si debe usar Unicode o ASCII

---

**Fecha de entrega:** Semana 16 del curso  
**Profesor:** Alexander Narváez Berrío  
**Universidad EAFIT - 2025**
