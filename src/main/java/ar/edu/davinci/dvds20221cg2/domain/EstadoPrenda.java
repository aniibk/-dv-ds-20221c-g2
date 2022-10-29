package ar.edu.davinci.dvds20221cg2.domain;

import java.util.LinkedList;
import java.util.List;

public enum EstadoPrenda {
	
	NUEVO("Nuevo"),
	PROMOCION("Promocion"),
	LIQUIDACION("Liquidacion");
	
	private String descripcion;
	
	private EstadoPrenda(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getDescripcionEstado() {
		return descripcion;
	}

	public static List<EstadoPrenda> getEstadoPrendas() {
		List<EstadoPrenda> estadoPrendas = new LinkedList<EstadoPrenda>();
		estadoPrendas.add(EstadoPrenda.NUEVO);
		estadoPrendas.add(EstadoPrenda.PROMOCION);
		estadoPrendas.add(EstadoPrenda.LIQUIDACION);
				
		return estadoPrendas;
	}

}
