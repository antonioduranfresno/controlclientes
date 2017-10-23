package net.gefco.controlclientes.controladores;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

import javax.annotation.PostConstruct;

import net.gefco.controlclientes.modelo.Facturacion;
import net.gefco.controlclientes.negocio.FacturacionService;
import net.gefco.controlclientes.util.AbstractDataTable;
import net.gefco.controlclientes.util.DataTableColumn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@Scope("session")
@SessionAttributes("usuarioSesion")
public class FacturacionController extends AbstractDataTable<Facturacion, FacturacionService> {
	
	
	@Autowired
	private FacturacionService 			facturacionService;

	@PostConstruct
	public void iniciarControler() {
		
		dt_service 			= facturacionService;
		dt_paginaLista 		= "facturacionLista";
		dt_orden			= "";
		dt_HQLfrom			= "Facturacion f";
		
		dt_columnas = new LinkedHashMap<String, DataTableColumn>();
		
		dt_columnas.put("id",  				new DataTableColumn("Id", 			Integer.class, 		"f.id"));
		dt_columnas.put("periodo", 			new DataTableColumn("Periodo", 		Integer.class, 		"f.fact_periodo"));
		dt_columnas.put("tercero", 			new DataTableColumn("Tercero", 		String.class, 		"concat(f.tercero.terc_codigo, ' - ', f.tercero.terc_razonSocial)"));
		dt_columnas.put("agencia", 			new DataTableColumn("Agencia", 		String.class, 		"f.agencia.agen_codigo"));
		dt_columnas.put("actividad", 		new DataTableColumn("Act.", 		String.class,		"f.actividad.acti_codigo"));
		dt_columnas.put("ventaAgencia",		new DataTableColumn("Venta Ag", 	Double.class, 		"f.fact_ventaAgencia"));
		dt_columnas.put("compraAgencia", 	new DataTableColumn("Compra Ag", 	Double.class,		"f.fact_compraAgencia"));
		dt_columnas.put("margen", 			new DataTableColumn("Margen", 		Double.class,		"f.fact_compraAgencia")); //No tenemos margen en bbdd
		dt_columnas.put("ventaSap", 		new DataTableColumn("Venta SAP", 	Double.class, 		"f.fact_ventaAgenciaSAP"));
		
		//No olvidar llamar a este método después de configurar las columnas.
		iniciarControllerAbstract();
	}
	
	@RequestMapping(value = "/facturacionLista", method = RequestMethod.GET)
	public String lista(Model model, @ModelAttribute("facturacion") Facturacion facturacion) 
			throws 	NoSuchMethodException, SecurityException, IllegalAccessException, 
					IllegalArgumentException, InvocationTargetException{
						
		filtrarLista();
		
		model.addAttribute("textoBuscar", 		dt_textoBusqueda);
		model.addAttribute("paginaActual", 		dt_paginaActual);
		model.addAttribute("numeroPaginas", 	dt_numeroPaginas);
		model.addAttribute("listaFacturacion", 	dt_lista);
		model.addAttribute("columnas", 			dt_columnas);
		model.addAttribute("orden", 			dt_orden);
		model.addAttribute("numeroRegistros", 	dt_totalRegistros);		
		
		return "facturacionLista";
	}
	
	@RequestMapping(value = "/facturacionListaMoverAPagina{param1}", method = RequestMethod.GET)
	public String moverAPagina(@PathVariable(value = "param1") String param1){
		switch (param1) {
		case "Primera":
			irAPrimeraPagina();
			break;
		
		case "Anterior":
			super.irAPaginaAnterior();
			break;

		case "Ultima":		
			super.irAUltimaPagina();
			break;
		
		case "Siguiente":
			super.irAPaginaSiguiente();
			break;
		}	
		
		return "redirect:/" + dt_paginaLista;
	}
	
	@RequestMapping(value = "/facturacionLista&orden={campoOrden}", method = RequestMethod.GET)
	private String ordenar(@PathVariable("campoOrden") String campoOrden){
		
		super.ordenarLista(campoOrden);
		
		return "redirect:/" + dt_paginaLista;
	}
	
	@RequestMapping(value = "/buscarFacturacion", method = RequestMethod.POST)	
	public String buscar(Model model, @ModelAttribute("textoBuscar") String textoBuscar){
		
		super.buscar(textoBuscar);
		
		return "redirect:/" + dt_paginaLista;
	}
	
	@RequestMapping(value = "/facturacionForm", method = RequestMethod.GET)
	public String mostrarFormulario(Model model, @RequestParam(value="idFacturacion",required=false) Integer idFacturacion) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
				
		Facturacion facturacion = new Facturacion();
		
		if(idFacturacion!=null){

			facturacion = facturacionService.buscarId(idFacturacion);
			
		}
		
		model.addAttribute("facturacion", facturacion);	
		
		return "facturacionForm";
	}
	
	/*
	@RequestMapping(value = "/terceroForm", method = RequestMethod.POST, params="action=Aceptar")
	public String aceptar(Model model,@ModelAttribute("tercero") @Valid Tercero tercero, BindingResult result) throws InvocationTargetException {
		
		if(tercero.getTerceroGrupo().getId()==0){			
			FieldError error = new FieldError("tercero", "terceroGrupo.id", "Por favor, seleccione una opción");			
			result.addError(error);			
		}
		
		if(tercero.getTerceroMarketLine().getId()==0){			
			FieldError error = new FieldError("tercero", "terceroMarketLine.id", "Por favor, seleccione una opción");			
			result.addError(error);			
		}
		
		if(tercero.getTerceroTipo().getId()==0){			
			FieldError error = new FieldError("tercero", "terceroTipo.id", "Por favor, seleccione una opción");			
			result.addError(error);			
		}
		
		if(result.hasErrors()){			
			model.addAttribute("listaTercerosGrupo", terceroGrupoService.listado());			
			model.addAttribute("listaTercerosMarketLine", terceroMarketLineService.listado());
			model.addAttribute("listaTercerosTipo", terceroTipoService.listado());
			return "terceroForm";			
		}

		try{
				
			//Creación
			if(tercero.getId()==null || tercero.getId()==0){
			
				tercero.setId(0);	
				terceroService.guardar(tercero);				
				tercero = new Tercero();
                
			//Actualización	
			}else{				
				terceroService.actualizar(tercero);				
			}
            
			return "redirect:/terceroLista?success=true";
							                
         } catch (InvocationTargetException e) {
        	 
                FieldError error;
                if(e.getCause().toString().contains("key 2") || e.getCause().toString().contains("terc_codigo")){
                	error = new FieldError("tercero", "terc_codigo", "Código de tercero ya existente.");
                }else if(e.getCause().toString().contains("key 3") || e.getCause().toString().contains("terc_razonSocial")){
                	error = new FieldError("tercero", "terc_razonSocial", "Razón Social ya existente.");
                }else{
                	error = new FieldError("tercero", "terc_codigo", "error no controlado: " + e.getCause().getMessage());
                }
                
                result.addError(error);     
                
                model.addAttribute("listaTercerosGrupo", terceroGrupoService.listado());
    			model.addAttribute("listaTercerosMarketLine", terceroMarketLineService.listado());
    			model.addAttribute("listaTercerosTipo", terceroTipoService.listado());    
    			
                return "terceroForm";                
         } 
		
			
	}
		
	@RequestMapping(value = "/tercerosExcel", method = RequestMethod.GET)
    public ModelAndView descargarExcel() {
		
		List <Tercero> listaExcel = new ArrayList<Tercero>();
		
		//Aplicar filtro
		try {
			for(Tercero terc : terceroService.listadoOrdenado(orden)){
				if(terc.toString().toUpperCase().contains(textoBusqueda.toUpperCase())){					
					listaExcel.add(terc);
				}
			}
			
		} catch ( SecurityException | IllegalArgumentException	| InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
		
		return new ModelAndView("excelViewTerceros", "tercerosExcel", listaExcel);
    }
	*/
}
