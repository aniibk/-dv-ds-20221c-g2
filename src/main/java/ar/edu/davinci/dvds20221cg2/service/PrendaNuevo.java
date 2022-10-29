package ar.edu.davinci.dvds20221cg2.service;

import java.math.BigDecimal;

public class PrendaNuevo implements EstadoPrendaStrategy {

	BigDecimal precioVenta;
	
	@Override
	public BigDecimal precioVenta(BigDecimal PrecioBase) {
		// TODO Auto-generated method stub
			
		return PrecioBase;
	}


}
