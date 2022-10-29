package ar.edu.davinci.dvds20221cg2.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ar.edu.davinci.dvds20221cg2.domain.Tienda;
import ar.edu.davinci.dvds20221cg2.domain.Venta;
import ar.edu.davinci.dvds20221cg2.exception.BusinessException;
import ar.edu.davinci.dvds20221cg2.repository.TiendaRepository;


@Service
public class TiendaServiceImpl implements TiendaService {

	private final Logger LOGGER = LoggerFactory.getLogger(TiendaServiceImpl.class);

	private final TiendaRepository tiendaRepository;

	@Autowired
	public TiendaServiceImpl(final TiendaRepository tiendaRepository) {
		this.tiendaRepository = tiendaRepository;

	}

	@Override
	public void delete(Tienda tienda) {
		tiendaRepository.delete(tienda);
	}

	@Override
	public void delete(Long id) {
		tiendaRepository.deleteById(id);
	}

	@Override
	public Tienda findById(Long id) throws BusinessException {
		LOGGER.debug("Busqueda de una tienda por ID");

		Optional<Tienda> itemOptional = tiendaRepository.findById(id);
		if (itemOptional.isPresent()) {
			return itemOptional.get();
		}

		throw new BusinessException("No se encotró la tienda por el id: " + id);
	}

	@Override
	public Tienda save(Tienda tienda) throws BusinessException {
		LOGGER.debug("Grabamos el item con el id: " + tienda.getId());

		if (tienda.getId() == null) {
			return tiendaRepository.save(tienda);
		}
		throw new BusinessException("No se puede crear una tienda con un id específico");
	}

	@Override
	public Tienda update(Tienda tienda) throws BusinessException {
		LOGGER.debug("Modificamos la tienda con el id: " + tienda.getId());

		if (tienda.getId() != null) {
			return tiendaRepository.save(tienda);
		}
		throw new BusinessException("No se puede modificar una tienda no creada");

	}

	@Override
	public List<Tienda> list() {

		LOGGER.debug("Listado de todas las tiendas");

		return tiendaRepository.findAll();

	}

	@Override
	public Page<Tienda> list(Pageable pageable) {
		LOGGER.debug("Listado de todas las tiendas por páginas");
		LOGGER.debug("Pageable: offset: " + pageable.getOffset() + ", pageSize: " + pageable.getPageSize()
				+ " and pageNumber: " + pageable.getPageNumber());

		return tiendaRepository.findAll(pageable);
	}

	@Override
	public long count() {
		return tiendaRepository.count();
	}

	@Override
	public BigDecimal gananciaDia(Long tiendaId, Date fecha) throws BusinessException {
		
		BigDecimal ganancia = new BigDecimal(2);
		
		tiendaRepository.findById(tiendaId);
		
		Tienda tienda = getTienda(tiendaId);
		
		for (Venta venta : tienda.getVentas() ) {
			if (venta.esDeFecha(fecha)) {
				ganancia = ganancia.add(venta.importeFinal());
			}
		}
		
		
		return ganancia;
	}
	
	private Tienda getTienda(Long tiendaId) throws BusinessException {
		Optional<Tienda> tiendaOptional = tiendaRepository.findById(tiendaId);
		if (tiendaOptional.isPresent()) {
			return tiendaOptional.get();
		} else {
			throw new BusinessException("Tienda no encotrado para el id: " + tiendaId);
		}
	}

}
