package ar.edu.davinci.dvds20221cg2.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PrendaLiquidacion implements EstadoPrendaStrategy{
	
	
	@Override
	public BigDecimal precioVenta(BigDecimal PrecioBase) {
		// TODO Auto-generated method stub
		
		BigDecimal divisor = new BigDecimal(2);
		BigDecimal precioFinal = PrecioBase.divide(divisor).setScale(2, RoundingMode.UP);
		
		return precioFinal;
	}

}
