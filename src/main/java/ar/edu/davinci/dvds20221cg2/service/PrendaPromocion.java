package ar.edu.davinci.dvds20221cg2.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PrendaPromocion implements EstadoPrendaStrategy {
	
	Double valorDescuento = 30.0;

	@Override
	public BigDecimal precioVenta(BigDecimal PrecioBase) {
		// TODO Auto-generated method stub
		BigDecimal divisor = new BigDecimal(100);
		BigDecimal vDescuento = new BigDecimal(valorDescuento);
				
		
		BigDecimal precioFinal = PrecioBase.subtract(PrecioBase.multiply(vDescuento.divide(divisor))).setScale(2, RoundingMode.UP);
		
		
		return precioFinal;
	}
	
	public BigDecimal precioVentaDesc(BigDecimal PrecioBase, Double valorDescuento) {
		// TODO Auto-generated method stub
		BigDecimal divisor = new BigDecimal(100);
		BigDecimal vDescuento = new BigDecimal(valorDescuento);
				
		
		BigDecimal precioFinal = PrecioBase.subtract(PrecioBase.multiply(vDescuento.divide(divisor))).setScale(2, RoundingMode.UP);
		
		
		return precioFinal;
	}

}
