package tpe;

public class Maquina {
	
	    private String nombre;
	    private Integer piezas;

	    public Maquina(String nombre, Integer piezas) {
	        this.nombre = nombre;
	        this.piezas = piezas;
	    }

	    public String getNombre() {
	        return this.nombre;
	    }

	    public Integer getPiezas() {
	        return this.piezas;
	    }

	    @Override
	    public String toString() {
	        return "Maquina: " + nombre + " - Piezas: " + piezas;
	    }

}
