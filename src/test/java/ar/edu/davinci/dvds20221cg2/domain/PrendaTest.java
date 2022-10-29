package ar.edu.davinci.dvds20221cg2.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class PrendaTest {

	@Test
	void testBuilder() {
		//Given
		Long id = 1L;
		String descripcion = "Camisa Rosa";
		TipoPrenda tipo = TipoPrenda.CAMISA;
		BigDecimal precioBase = new BigDecimal(10);
		EstadoPrenda estado = EstadoPrenda.NUEVO;
		
		//When
		Prenda prenda = Prenda.builder()
				.id(id)
				.descripcion(descripcion)
				.tipo(tipo)
				.precioBase(precioBase)
				.estado(estado)
				.build();
		
		
		//Then
		assertNotNull(prenda);
		assertEquals(id, prenda.getId());
		assertEquals(descripcion, prenda.getDescripcion());
		assertEquals(tipo, prenda.getTipo());
		assertEquals(precioBase, prenda.getPrecioBase());
		assertEquals(estado, prenda.getEstado());
	}

}
