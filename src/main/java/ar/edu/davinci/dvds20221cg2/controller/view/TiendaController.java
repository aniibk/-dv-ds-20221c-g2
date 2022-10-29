package ar.edu.davinci.dvds20221cg2.controller.view;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.davinci.dvds20221cg2.Constantes;
import ar.edu.davinci.dvds20221cg2.controller.TiendaApp;
import ar.edu.davinci.dvds20221cg2.controller.response.TiendaGananciasResponse;
import ar.edu.davinci.dvds20221cg2.controller.view.request.TiendaGananciaCreateRequest;
import ar.edu.davinci.dvds20221cg2.domain.Tienda;
import ar.edu.davinci.dvds20221cg2.exception.BusinessException;
import ar.edu.davinci.dvds20221cg2.service.TiendaService;
import ma.glasnost.orika.MapperFacade;


@Controller
public class TiendaController extends TiendaApp {
	private final Logger LOGGER = LoggerFactory.getLogger(TiendaController.class);

	
	@Autowired
	private TiendaService tiendaService;
	
	@Autowired
	private MapperFacade mapper;
	
	
	@GetMapping(path = "/tiendas/list")
	public String showTiendaPage(Model model) {
		LOGGER.info("GET - showTiendaPage  - /tiendas/list");
		
		Pageable pageable = PageRequest.of(0, 20);
		Page<Tienda> tiendas = tiendaService.list(pageable);
		model.addAttribute("listTiendas", tiendas);

		LOGGER.info("tiendas.size: " + tiendas.getNumberOfElements());
		return "tiendas/list_tiendas";
	}
	
	@GetMapping(path = "/tiendas/new_tiendas")
	public String showNewTiendaPage(Model model) {
		LOGGER.info("GET - showNewTiendaPage - /tiendas/new_tiendas");
		Tienda tienda = new Tienda();
		model.addAttribute("tienda", tienda);

		LOGGER.info("tiendas: " + tienda.toString());

		return "tiendas/new_tiendas";
	}
	
	
	@PostMapping(value = "/tiendas/save")
	public String saveTienda(@ModelAttribute("tienda") Tienda tienda) {
		LOGGER.info("POST - saveTienda - /tiendas/save");
		LOGGER.info("tienda: " + tienda.toString());
		try {
			if (tienda.getId() == null) {
				tiendaService.save(tienda);
			} else {
				tiendaService.update(tienda);
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/tienda/tiendas/list";
	}
	
	@GetMapping(path = "/tiendas/ganancias_tienda")
	public String showGananciasTiendaPage(Model model) {
		LOGGER.info("GET - showNewTiendaPage - /tiendas/ganancias_tienda");
		TiendaGananciaCreateRequest tienda = new TiendaGananciaCreateRequest();
		
		model.addAttribute("tienda", tienda);

		LOGGER.info("tiendas: " + tienda.toString());

		return "tiendas/ganancias_tienda";
	}
	
	
	@PostMapping(path = "/tiendas/resultado_tiendas")
	public ModelAndView getGanancias(@ModelAttribute("tienda") TiendaGananciaCreateRequest datosTiendaGanancia) {
		LOGGER.info("GET - showNewTiendaPage - /tiendas/ganancias_tienda");
				
		ModelAndView mav = new ModelAndView("tiendas/resultado_tiendas");
		
		DateFormat formatearFecha = new SimpleDateFormat(Constantes.FORMATO_FECHA);
        
		TiendaGananciasResponse tiendaGananciasResponse = null;
		
		BigDecimal tiendaGanancia;
        
        Date dateF =null;
        
        try {
			tiendaGananciasResponse = mapper.map(datosTiendaGanancia, TiendaGananciasResponse.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			dateF = formatearFecha.parse(datosTiendaGanancia.getDate());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		List<TiendaGananciasResponse> gananciasToResponse = new ArrayList<TiendaGananciasResponse>();
		
		try {
			
			tiendaGanancia = tiendaService.gananciaDia(datosTiendaGanancia.getId(), dateF);
			tiendaGananciasResponse.setGanancias(tiendaGanancia);
			gananciasToResponse.add(tiendaGananciasResponse);
			
			LOGGER.info("Ganancias response: "+ gananciasToResponse.toString());
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		mav.addObject("listGanancias", gananciasToResponse);

		return mav;
	}
	
	
	
	@RequestMapping(value = "/tiendas/edit/{id}", method = RequestMethod.GET)
	public ModelAndView showEditTiendaPage(@PathVariable(name = "id") Long tiendaId) {
		LOGGER.info("GET - showEditTiendaPage - /tiendas/edit/{id}");
		LOGGER.info("tienda: " + tiendaId);

		ModelAndView mav = new ModelAndView("tiendas/edit_tiendas");
		try {
			Tienda tienda = tiendaService.findById(tiendaId);
			mav.addObject("tienda", tienda);
		} catch (BusinessException e) {
			LOGGER.error("ERROR AL TRAER LA PRENDA");
			e.printStackTrace();
		}
			
		return mav;
	}

	@RequestMapping(value = "/tiendas/delete/{id}", method = RequestMethod.GET)
	public String deleteTienda(@PathVariable(name = "id") Long tiendaId) {
		LOGGER.info("GET - deleteTienda - /tiendas/delete/{id}");
		LOGGER.info("tienda: " + tiendaId);
		tiendaService.delete(tiendaId);
		return "redirect:/tienda/tiendas/list";
	}
}


