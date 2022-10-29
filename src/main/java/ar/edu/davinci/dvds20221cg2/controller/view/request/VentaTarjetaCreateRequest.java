package ar.edu.davinci.dvds20221cg2.controller.view.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaTarjetaCreateRequest {

	private Long clienteId;
	
	private Long tiendaId;

	private String fecha; 

	private Integer cantidadCuotas;

}

