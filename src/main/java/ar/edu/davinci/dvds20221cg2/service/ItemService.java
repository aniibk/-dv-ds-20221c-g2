package ar.edu.davinci.dvds20221cg2.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.davinci.dvds20221cg2.domain.Item;
import ar.edu.davinci.dvds20221cg2.exception.BusinessException;


public interface ItemService {
	
	// Métodos de creación, modificación y borrado.
	Item save(Item item) throws BusinessException;
	Item update(Item item) throws BusinessException;
	public void delete(Item item);
	public void delete(Long id);
	
	// Método de búsqueda.
	public Item findById(Long id) throws BusinessException;

	// Método de listado.
	public List<Item> listAll();
	public Page<Item> list(Pageable pageable);

	// Método para contar cantidad de datos.
	public long count();


}
