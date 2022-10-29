package ar.edu.davinci.dvds20221cg2.controller.response;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TiendaGananciasResponse {
	
	private Long id;

	private String date;
	
	private BigDecimal ganancias;

}