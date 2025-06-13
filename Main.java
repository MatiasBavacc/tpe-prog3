package tpe;

public class Main {

	public static void main(String[] args) {

		System.out.println();
		System.out.println("                                      --------------- Trabajo Práctico Especial  ------------------------");
		System.out.println();
		// se debe indicar la RUTA y el ARCHIVO.TXT para obtener los datos
		String archivo1 = "C:\\Users\\Casa\\Desktop\\Mati\\Tudai\\Segundo Año\\Primer Cuatrimestre\\Programacion 3\\java\\tpe\\archivo.txt";
		System.out.println();
		System.out.println("******** Se crea la Class TAREA");
		Tarea nuevaTarea1 = new Tarea();
		System.out.println();
		System.out.println("******** Se obtienen los datos del archivo.txt");
		System.out.println();
		nuevaTarea1.obtener(archivo1);
		System.out.println();
		System.out.println("******** Se calcula el resultado usando BACKTRACKING");
		System.out.println();
		nuevaTarea1.backtracking();
		System.out.println();
		System.out.println("******** Se calcula el resultado usando GREEDY");
		System.out.println();
		nuevaTarea1.greedy();
		System.out.println("---------------------------------------------------------------------------------------------------------");
		System.out.println();
		System.out.println("                                                                                     --------FIN --------");
		
		
	}

}
