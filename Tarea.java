package tpe;

import java.util.List;
import java.util.ArrayList;
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
	 
	//Método que halla la solución usando BACKTRACKING y la guarda en SALIDA
	public void backtracking() {
		this.salida.clear();
        this.estadosGenerados = 0;
		List<Maquina> solucionParcial = new ArrayList<>();
		Integer faltanFabricar = this.total;
		backtracking( solucionParcial , faltanFabricar );
		System.out.print("Resultado FINAL: ");
		System.out.println(this.salida);
		System.out.print("Cantidad de piezas producidas: ");
		System.out.println(this.total);
		System.out.print("Cantidad de puestas en funcionamiento requeridas: ");
		System.out.println(this.salida.size());
		System.out.print("(Métrica para analizar el costo de la solución) Cantidad de estados generados: ");
		System.out.println(this.estadosGenerados);
	}
	
	//private void backtracking
	private void backtracking( List<Maquina> solucionParcial , Integer faltanFabricar ) {
        this.estadosGenerados ++;
		if ( faltanFabricar == 0) {
			if ((this.salida.size() == 0 ) || ( this.salida.size() > solucionParcial.size() )) {
				this.salida = new ArrayList<>(solucionParcial);
			}
		} else {
			for ( int i = 1 ; i <= this.entrada.cantidad() ; i++ ) {
				Maquina maquina = this.entrada.acceder(i);
				if ( (faltanFabricar - maquina.getPiezas() ) >= 0 ) {
					if ((this.salida.size() == 0 ) || ( this.salida.size() > (solucionParcial.size()+1) )) {
						solucionParcial.add(maquina);
						backtracking( solucionParcial , ( faltanFabricar - maquina.getPiezas() ) );
						solucionParcial.remove(maquina);	
					}	
				}  
			}
		}
	}
	
	public void greedy() {
		this.salida.clear();
        this.estadosGenerados = 0;
        TablaHash candidatos = new TablaHash(this.entrada);
		List<Maquina> solucionParcial = new ArrayList<>();
		Integer faltanFabricar = this.total;
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
	
	private void greedy(TablaHash candidatos,List<Maquina> solucionParcial,Integer faltanFabricar) {
		while( (!candidatos.isEmpty()) && !(faltanFabricar==0) ) {
			this.estadosGenerados ++;
			Integer maquina = candidatos.buscarMayor();
			if(faltanFabricar-candidatos.acceder(maquina).getPiezas() >= 0) {
				solucionParcial.add(candidatos.acceder(maquina));
				faltanFabricar -= candidatos.acceder(maquina).getPiezas();
			}else {
				candidatos.borrar(maquina);
			}
		}
		if(faltanFabricar==0) {
			this.salida = new ArrayList<>(solucionParcial);
		}
	}
}
