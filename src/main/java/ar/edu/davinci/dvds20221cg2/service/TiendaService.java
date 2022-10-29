package ar.edu.davinci.dvds20221cg2.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.davinci.dvds20221cg2.domain.Tienda;
import ar.edu.davinci.dvds20221cg2.exception.BusinessException;


public interface TiendaService {
	
	// Métodos de creación, modificación y borrado.
	Tienda save(Tienda tienda) throws BusinessException;
	Tienda update(Tienda tienda) throws BusinessException;
	public void delete(Tienda tienda);
	public void delete(Long id);
	
	// Método de búsqueda.
	public Tienda findById(Long id) throws BusinessException;

	// Método de listado.
	public List<Tienda> list();
	public Page<Tienda> list(Pageable pageable);

	// Método para contar cantidad de datos.
	public long count();
	
	// Método para contar cantidad de datos.
	public BigDecimal gananciaDia(Long tiendaId, Date fecha) throws BusinessException;

}
