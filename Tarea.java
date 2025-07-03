package tpe;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Tarea {
	
	private TablaHash entrada;
	private Integer total;
	private List<Maquina> salida;
	private Integer estadosGenerados;
	
	public Tarea() {
        this.entrada = new TablaHash();
        this.total = 0;
        this.salida = new ArrayList<>();
        this.estadosGenerados = 0;
    }

	public void limpiar() {
        this.entrada.clear();
        this.total = 0;
        this.salida.clear();
        this.estadosGenerados = 0;
        System.out.println("Todas las estructuras han sido limpiadas.");
    }

	public void listarSalida() {
	     System.out.println(" \n \t \t Listado de Máquinas:");
	     for (Maquina maqui : this.salida) {
	          System.out.println(maqui);
	     }
	}
	
	@Override
	public String toString() {
    	System.out.println("\n Entrada de DATOS");
    	System.out.println("==================");
    	System.out.println(entrada);
    	System.out.print("\n Total de PIEZAS a fabricar: ");
    	System.out.println(this.total);
    	System.out.println("==============================");
    	System.out.println("\n Resultado (orden en que se deben prender las máquinas):");
    	System.out.println("=========================================================");
    	this.listarSalida();
    	return "Fin";
    }
	 
	//Método que obtiene los datos de un ARCHIVO.TXT y los guarda en ENTRADA
	public void obtener(String ruta) {
        try {
        	this.entrada.clear();
            File archivo = new File(ruta);
            Scanner lector = new Scanner(archivo);        
            // Leer el TOTAL
            int total = Integer.parseInt(lector.nextLine());
            System.out.println("TOTAL (desde archivo): " + total);
            this.total = total;
            // Leer el resto de las líneas: nombre,cantidad
            Integer contador = 1;
            while (lector.hasNextLine()) {
                String linea = lector.nextLine();
                String[] partes = linea.split(",");
                // Validación básica
                if (partes.length != 2) {
                    System.out.println("Línea mal formada: " + linea);
                    continue;
                }
                String nombre = partes[0];
                int cantidad = Integer.parseInt(partes[1]);
                System.out.println("Máquina: " + nombre + ", Cantidad: " + cantidad);
                //creo la maquina
                Maquina nueva = new Maquina(nombre,cantidad);
                //guardo la maquina nueva en la tabla hash
                this.entrada.agregar(contador,nueva);
                contador++;
            }
            lector.close();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado.");
            //e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir número.");
            //e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error en el formato de los datos.");
            //e.printStackTrace();
        }
    }
	
	/*
	* Breve explicación de la estrategia de resolución de Backtraking.
	* - La estrategia de backtraking que utilizamos es, ir seleccionando las maquinas si la cantidad que fabrican
	* no supera la cantidad que se desea fabricar, teniendo en cuenta las que ya se fabricaron, llevamos como parametro
	* un indice que le dice al algoritmo por que maquina continuar, para no repetir estados.
	* - El arbol de exploracion se genera de la siguiente forma para el ejemplo [7,3,4,1].
	* []
	* [7] - [7,3] - [7,3,1] - [7,3,1,1]
	* 	  - [7,4] - [7,4,1]
	* 	  - [7,1]
	* [3] - [3,7]
	* 	  - [3,3]
	* 	  - ...
	* [4] - [4,7]
	*  	  - ...
	* [1] - [1,7]
	* 	  - ...
	* 	  - [1,1]
	* - Fin de abrol de exploracion.
	* 
	* - Estados finales.
	* { [7,3,1,1],[7,4,1],[7,1],[3,7],[3,3],[3,4],[3,1],etc...}
	* 
	* - Estados solucion.
	* {[7,3,1,1],[7,4,1]}
	* 
	* - Posibles podas.
	* - Si la cantidad de piezas que faltan fabricar menos la cantidad de piezas de la maquina que vamos a prender es 
	* mayor o igual a cero, entonces le permito prenderla.
	* - Si la cantidad de maquinas prendidas que tiene la solucion parcial, mas la maquina que prendera en su proximo 
	* llamado, es menor a la cantidad de maquinas prendidas que tiene la solucion, entonces permito hacer el llamdo.
	* - Si ya recorri todo los estados posibles con la primera maquina, dejo de tener en cuenta esa maquina para los
	* siguientes llamados.
	* 
	*/
	 
	public void backtracking() {
		this.salida.clear();
        this.estadosGenerados = 0;
		List<Maquina> solucionParcial = new ArrayList<>();
		Integer faltanFabricar = this.total;
		backtracking( solucionParcial , faltanFabricar,  1 );
		System.out.print("Resultado FINAL: ");
		System.out.println(this.salida);
		System.out.print("Cantidad de piezas producidas: ");
		System.out.println(this.total);
		System.out.print("Cantidad de puestas en funcionamiento requeridas: ");
		System.out.println(this.salida.size());
		System.out.print("(Métrica para analizar el costo de la solución) Cantidad de estados generados: ");
		System.out.println(this.estadosGenerados);
	}
	
	private void backtracking( List<Maquina> solucionParcial , Integer faltanFabricar, int index ) {
        this.estadosGenerados ++;
		if ( faltanFabricar == 0) {
			if ((this.salida.size() == 0 ) || ( this.salida.size() > solucionParcial.size() )) {
				this.salida = new ArrayList<>(solucionParcial);
			}
		} else {
			for ( int i = index ; i <= this.entrada.cantidad() ; i++ ) {
				Maquina maquina = this.entrada.acceder(i);
				if ( (faltanFabricar - maquina.getPiezas() ) >= 0 ) {
					if ((this.salida.size() == 0 ) || ( this.salida.size() > (solucionParcial.size()+1) )) {
						solucionParcial.add(maquina);
						backtracking( solucionParcial , ( faltanFabricar - maquina.getPiezas() ), i );
						solucionParcial.remove(maquina);	
					}	
				}  
			}
		}
	}
	
	/*
	* Breve explicación de la estrategia de resolución de Greedy.
	* -La estrategia greedy que utilizamos a los candidatos que mas piezas fabriquen ordenarlos primero en la lista 
	* y agregarlos a la solucion, siempre que no superen la cantidad que se desea fabricar teniendo en cuenta 
	* las que ya se fabricaron.
	* - Los candidatos son [7,4,3,1].
	* - Estrategia de selección de candidatos ordenar primero los candidatos que mas piezas fabriquen.
	* - Si una maquina fabrica mas piezas de las que necesito, no la tomo en cuenta de nuevo en la solucion,
	* borro la maquina de mis candidatos y de lo contrario la agrego a la solucion.
	* - Puede darse para algunos casos que no exista solucion.
	* Por ejemplo para el caso [60,25,10,30] dondde se busquen fabricar 95 piezas, debido a que el algoritmo 
	* busca el mayor, el arreglo podria plantearse de la siguiente manera [60,30,25,10].
	* Agregaria los dos primeros, y cualquier cosa que agregara despues se pasaria, por lo cual, nuestro algoritmo no
	* tiene forma de dar una solucion.
	* - Caso contrario es el del ejemplo, donde tenemos [7,3,4,1] y se necesitan fabricar 12 piezas.
	* En este caso el arreglo puede representarse [7,4,3,1], agregaria los dos primeros numeros a la solucion y luego
	* de eliminar al numero 3, agregaria al numero uno a la solucion, instanciando a la solucion el siguiente arreglo
	* [7,4,1].
	* - El arbol de exploracion para este ejemplo es:
	* [7]
	* [7] - 7 *Elimina el 7 de candidatos.
	* [7] - 4
	* [7,4] - 4 *Elimina al 4 de candidatos.
	* [7,4] - 3 *Elimina al 3 de candidatos.
	* [7,4] - 1 *[7,4,1] Termina.
	*/
	
	public void greedy() {
		this.salida.clear();
        this.estadosGenerados = 0;
        List<Maquina> candidatos = new ArrayList<Maquina>();
        List<Maquina> solucionParcial = new ArrayList<>();
        Integer faltanFabricar = this.total;
        candidatos = this.entrada.copiarEnLista();
        candidatos.sort(Comparator.comparingInt( m -> ( (Maquina) m).getPiezas() ).reversed() );
		this.greedy(candidatos, solucionParcial , faltanFabricar );
		if(this.salida.size() > 0) {
			System.out.print("Resultado FINAL: ");
			System.out.println(this.salida);
			System.out.print("Cantidad de piezas producidas: ");
			System.out.println(this.total);
			System.out.print("Cantidad de puestas en funcionamiento requeridas: ");
			System.out.println(this.salida.size());
			System.out.print("(Métrica para analizar el costo de la solución) Cantidad de estados generados: ");
			System.out.println(this.estadosGenerados);
		}else {
			System.out.println("Mediante la estrategia greedy elegida no se pudo determinar una solucion.");
		}
	}
	
	private void greedy(List<Maquina> candidatos,List<Maquina> solucionParcial,Integer faltanFabricar) {
		while( (!candidatos.isEmpty()) && !(faltanFabricar==0) ) {
			this.estadosGenerados ++;
			Maquina maquina = candidatos.get(0);
			if(faltanFabricar-maquina.getPiezas() >= 0) {
				solucionParcial.add(maquina);
				faltanFabricar -= maquina.getPiezas();
			}else {
				candidatos.remove(0);
			}
		}
		if(faltanFabricar==0) {
			this.salida = new ArrayList<>(solucionParcial);
		}
	}
}
