package ar.edu.davinci.dvds20221cg2.controller.rest;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.davinci.dvds20221cg2.Constantes;
import ar.edu.davinci.dvds20221cg2.controller.TiendaAppRest;
import ar.edu.davinci.dvds20221cg2.controller.request.TiendaGananciasRequest;
import ar.edu.davinci.dvds20221cg2.controller.request.TiendaInsertRequest;
import ar.edu.davinci.dvds20221cg2.controller.request.TiendaUpdateRequest;
import ar.edu.davinci.dvds20221cg2.controller.response.TiendaGananciasResponse;
import ar.edu.davinci.dvds20221cg2.controller.response.TiendaResponse;
import ar.edu.davinci.dvds20221cg2.domain.Tienda;
import ar.edu.davinci.dvds20221cg2.exception.BusinessException;
import ar.edu.davinci.dvds20221cg2.service.TiendaService;
import ma.glasnost.orika.MapperFacade;

@RestController
public class TiendaControllerRest extends TiendaAppRest{
	
	private final Logger LOGGER = LoggerFactory.getLogger(TiendaControllerRest.class);

	@Autowired
	private TiendaService service;
	
	@Autowired
	private MapperFacade mapper;
	
	/**
	 * Listar
	 */
	@GetMapping(path = "/tiendas/all")
	public List<Tienda> getListAll() {
		LOGGER.info("listar todas las tiendas");

		return service.list();
	}

	/**
	 * Listar paginado
	 */
	@GetMapping(path = "/tiendas")
	public ResponseEntity<Page<TiendaResponse>> getList(Pageable pageable) {
		
		LOGGER.info("listar todas las tiendas paginadas");
		LOGGER.info("Pageable: " + pageable);
		
		Page<TiendaResponse> tiendaResponse = null;
		Page<Tienda> tiendas = null;
		try {
			tiendas = service.list(pageable);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			tiendaResponse = tiendas.map(tienda -> mapper.map(tienda, TiendaResponse.class));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(tiendaResponse, HttpStatus.OK);
	}
	
	/**
	 * Buscar tienda por id
	 * @param id identificador de la tienda
	 * @return retorna la tienda
	 */
	@GetMapping(path = "/tiendas/{id}")
	public ResponseEntity<Object> getTienda(@PathVariable Long id) {
		LOGGER.info("lista a la tienda solicitada");

		TiendaResponse tiendaResponse = null;
		Tienda tienda = null;
		try {
			
			tienda = service.findById(id);
	
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		try {
			tiendaResponse = mapper.map(tienda, TiendaResponse.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<>(tiendaResponse, HttpStatus.OK);
	}
	
	/**
	 * Ganancias de tienda por dia
	 * @param id identificador de la tienda
	 * @param date fecha
	 * @return retorna la tienda
	 */
	
	
	@PostMapping(path = "/tiendas/ganancias")
	public ResponseEntity<TiendaGananciasResponse> getGanancias(
			@RequestBody TiendaGananciasRequest datosGanancia) {
		
		LOGGER.info("ganancias por tienda");

		TiendaGananciasResponse tiendaGananciasResponse = null;
			
		BigDecimal tiendaGanancia;
		
		
		try {
			tiendaGananciasResponse = mapper.map(datosGanancia, TiendaGananciasResponse.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			//e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
		
		DateFormat formatearFecha = new SimpleDateFormat(Constantes.FORMATO_FECHA);
        
        
        Date dateF =null;
		try {
			dateF = formatearFecha.parse(datosGanancia.getDate());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
		}
        
		
		try {

			tiendaGanancia = service.gananciaDia(datosGanancia.getId(), dateF);
			LOGGER.info("Ganancia: "+ tiendaGanancia.toString());
			tiendaGananciasResponse.setGanancias(tiendaGanancia);
			
			
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
	
		
		return new ResponseEntity<>(tiendaGananciasResponse, HttpStatus.OK);
	}
	
	/**
	 * Grabar una nueva tienda
	 * 
	 * @param datosTienda son los datos para una nueva tienda
	 * @return un tienda nueva
	 */
	@PostMapping(path = "/tiendas")
	public ResponseEntity<TiendaResponse> createTienda(@RequestBody TiendaInsertRequest datosTienda) {
		Tienda tienda = null;
		TiendaResponse tiendaResponse = null;

		// Convertir TiendaInsertRequest en Tienda
		try {
			tienda = mapper.map(datosTienda, Tienda.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		// Grabar la nueva Tienda
		try {
			tienda = service.save(tienda);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}

		// Convertir Tienda en TiendaResponse
		try {
			tiendaResponse = mapper.map(tienda, TiendaResponse.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		return new ResponseEntity<>(tiendaResponse, HttpStatus.CREATED);
	}
	
	/**
	 * Modificar los datos de una tienda
	 * 
	 * @param id identificador de una tienda
	 * @param datosTienda datos a modificar de la tienda
	 * @return los datos de una tienda modificada
	 */
	@PutMapping("/tiendas/{id}")
	public ResponseEntity<Object> updateTienda(@PathVariable("id") long id,
			@RequestBody TiendaUpdateRequest datosTienda) {

		Tienda tiendaModificar = null;
		Tienda tiendaNueva = null;
		TiendaResponse tiendaResponse = null;

		// Convertir TiendaInsertRequest en Tienda
		try {
			tiendaNueva = mapper.map(datosTienda, Tienda.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			
			tiendaModificar = service.findById(id);

		} catch (BusinessException e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

		if (Objects.nonNull(tiendaModificar)) {
			tiendaModificar.setNombre(tiendaNueva.getNombre());
			
			// Grabar la Tienda Nueva en Tienda a Modificar
			try {
				tiendaModificar = service.update(tiendaModificar);
			} catch (BusinessException e) {
				LOGGER.error(e.getMessage());
				e.printStackTrace();
				return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());

				e.printStackTrace();

				return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
			}
		} else {
			LOGGER.error("Tienda a modificar es null");

			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		// Convertir Tienda en TiendaResponse
		try {
			tiendaResponse = mapper.map(tiendaModificar, TiendaResponse.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		return new ResponseEntity<>(tiendaResponse, HttpStatus.CREATED);
	}
	
	
	/**
	 * Borrado de la tienda
	 * @param id identificador de una tienda
	 * @return
	 */
	@DeleteMapping("/tiendas/{id}")
	public ResponseEntity<HttpStatus> deleteTienda(@PathVariable("id") Long id) {
		try {
			service.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	

}
