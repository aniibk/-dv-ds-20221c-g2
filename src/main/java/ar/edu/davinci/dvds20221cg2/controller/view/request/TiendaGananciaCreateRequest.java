package ar.edu.davinci.dvds20221cg2.controller.view.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TiendaGananciaCreateRequest {
	
	private Long id;

	private String date;

}
