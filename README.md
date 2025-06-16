# Trabajo Práctico Especial – Programación III (2025)

**Facultad de Ciencias Exactas**  
**Universidad Nacional del Centro de la Provincia de Buenos Aires (UNICEN)**

## 📚 Descripción

Este proyecto corresponde al Trabajo Práctico Especial de la materia **Programación III** de la carrera de TUDAI, cursada en el año 2025.

El objetivo principal es resolver un problema de optimización en una fábrica de autopartes, implementando y comparando dos técnicas de resolución algorítmica:

- 🧭 **Backtracking**
- ⚡ **Greedy**

## 🏭 Contexto del Problema

En la fábrica se dispone de un conjunto de máquinas. Cada una produce una cantidad fija de piezas y, una vez activada, no puede detenerse hasta completar esa producción. Solo puede estar una máquina funcionando a la vez, aunque puede reutilizarse cualquier máquina las veces que sea necesario.

### Objetivo:
Dada una cantidad total de piezas a fabricar, se debe encontrar (de existir) la **secuencia de máquinas** que minimice la cantidad de puestas en funcionamiento necesarias.

Ejemplo:

Máquinas disponibles:
12
M1, 7
M2, 3
M3, 4
M4, 1


Piezas a fabricar: `12`  
Solución óptima posible: `[M1, M3, M4]` o `[M3, M3, M3]`

## 💻 Funcionalidad de la Aplicación

1. ✅ Carga los datos desde un archivo de texto con el siguiente formato:

<total_piezas>
<nombre_maquina_1>,<cantidad_piezas>
<nombre_maquina_2>,<cantidad_piezas>

2. ✅ Resuelve el problema usando dos enfoques:
   - Algoritmo de **Backtracking**
   - Algoritmo **Greedy**

3. ✅ Muestra los resultados para ambas estrategias, incluyendo:
   - Secuencia de máquinas utilizadas
   - Piezas producidas
   - Cantidad total de activaciones de máquinas
   - Métricas de costo (estados generados o candidatos considerados)

4. ✅ Incluye explicaciones breves de cada estrategia antes de la función principal correspondiente.

## 🧠 Estrategias Implementadas

### 🧭 Backtracking

Breve explicación de la estrategia de resolución de Backtraking.
- La estrategia de backtraking que utilizamos es, ir seleccionando las maquinas si la cantidad que fabrican
no supera la cantidad que se desea fabricar, teniendo en cuenta las que ya se fabricaron.
- El arbol de exploracion se genera de la siguiente forma para el ejemplo [7,3,4,1].

--[]
--[7] - [7,3] - [7,3,1] - [7,3,1,1]
--   - [7,4] - [7,4,1]
--    - [7,1]
--[3] - [3,7]
--   - [3,3]
    - ...
--[4] - [4,7]
--    - ...
--[1] - [1,7]
--    - ...
--    - [1,1]
- Fin de abrol de exploracion.

- Estados finales.
- { [7,3,1,1],[7,4,1],[7,1],[3,7],[3,3],[3,4],[3,1],etc...}

- Estados solucion.
- {[7,3,1,1],[7,4,1]}

- Posibles podas.
- Si la cantidad de piezas que faltan fabricar menos la cantidad de piezas de la maquina que vamos a prender es 
mayor o igual a cero, entonces le permito prenderla.
- Si la cantidad de maquinas prendidas que tiene la solucion parcial, mas la maquina que prendera en su proximo 
llamado, es menor a la cantidad de maquinas prendidas que tiene la solucion, entonces permito hacer el llamdo.
	

public void backtracking(...) { ... }

### ⚡ Greedy

Breve explicación de la estrategia de resolución de Greedy.
-La estrategia greedy que utilizamos es buscar los candidatos que mas piezas fabriquen y agregarlos a la
solucion, siempre que no superen la cantidad que se desea fabricar teniendo en cuenta las que ya se fabricaron.

- Los candidatos son [7,4,3,1].

- Estrategia de selección de candidatos es buscar el candidato que mas piezas fabrique.
La busqueda se hace recorriendo una hashtable, lo que no nos permite ordenarla.

- Si una maquina fabrica mas piezas de las que necesito, no la tomo en cuenta de nuevo en la solucion,
borro la maquina de mis candidatos y de lo contrario la agrego.

- Puede darse para algunos casos que no exista solucion.
Por ejemplo para el caso [60,25,10,30] dondde se busquen fabricar 95 piezas, debido a que el algoritmo 
busca el mayor, el arreglo podria plantearse de la siguiente manera [60,30,25,10].
Agregaria los dos primeros, y cualquier cosa que agregara despues se pasaria, por lo cual, nuestro algoritmo no
tiene forma de dar una solucion.

- Caso contrario es el del ejemplo, donde tenemos [7,3,4,1] y se necesitan fabricar 12 piezas.
En este caso el arreglo puede representarse [7,4,3,1], agregaria los dos primeros numeros a la solucion y luego
de eliminar al numero 3, agregaria al numero uno a la solucion, instanciando a la solucion el siguiente arreglo
[7,4,1].

- El arbol de exploracion para este ejemplo es:
[7]
[7] - 7 *Elimina el 7 de candidatos.
[7] - 4
[7,4] - 4 *Elimina al 4 de candidatos.
[7,4] - 3 *Elimina al 3 de candidatos.
[7,4] - 1 *[7,4,1] Termina.

public void greedy(...) { ... }

🧑‍💻 Autores
Trabajo realizado por: Alejandro Garay, Matias Bava.
Materia: Programación III
Año: 2025 – UNICEN

