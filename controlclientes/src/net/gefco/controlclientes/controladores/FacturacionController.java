package net.gefco.controlclientes.controladores;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.HashMap;

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
	
	DecimalFormat 						format		= new DecimalFormat("#,###,###,##0.##");

	@PostConstruct
	public void iniciarControler() {
		
		service 		= facturacionService;
		paginaLista 	= "facturacionLista";
		orden			= "";
		
		encabezados = new HashMap<String, DataTableColumn>();
		encabezados.put("periodo", 	new DataTableColumn("Periodo", "fact_periodo", paginaLista + "&orden=fact_periodo", ""));
		encabezados.put("tercero", new DataTableColumn("Tercero", "tercero-terc_codigo", paginaLista + "&orden=tercero-terc_codigo", ""));
		encabezados.put("agencia", new DataTableColumn("Agencia", "agencia-agen_codigo", paginaLista + "&orden=agencia-agen_codigo", ""));
		encabezados.put("actividad", new DataTableColumn("Act.", "actividad-acti_codigo", paginaLista + "&orden=actividad-acti_codigo", ""));
		encabezados.put("ventaAgencia",	new DataTableColumn("Venta Ag", "fact_ventaAgencia", paginaLista + "&orden=fact_ventaAgencia", ""));
		encabezados.put("compraAgencia", new DataTableColumn("Compra Ag", "fact_compraAgencia", paginaLista + "&orden=fact_compraAgencia", ""));
		encabezados.put("margen", new DataTableColumn("Margen", "margenAgencia", paginaLista + "&orden=margenAgencia", ""));
		encabezados.put("ventaSap", new DataTableColumn("Venta SAP", "fact_ventaAgenciaSAP", paginaLista + "&orden=fact_ventaAgenciaSAP", ""));
		encabezados.put("ventaGlobal", new DataTableColumn("Venta Gl", "fact_ventaGlobal", paginaLista + "&orden=fact_ventaGlobal", ""));
		encabezados.put("compraGlobal", new DataTableColumn("Compra Gl", "fact_compraGlobal", paginaLista + "&orden=fact_compraGlobal", ""));
	}
	
	@RequestMapping(value = "/facturacionLista", method = RequestMethod.GET)
	public String lista(Model model, @ModelAttribute("facturacion") Facturacion facturacion) 
			throws 	NoSuchMethodException, SecurityException, IllegalAccessException, 
					IllegalArgumentException, InvocationTargetException{
						
		filtrarLista();
		model.addAttribute("textoBuscar", textoBusqueda);
		model.addAttribute("paginaActual", paginaActual);
		model.addAttribute("numeroPaginas", numeroPaginas);			
		model.addAttribute("listaFacturacion", lista);
		model.addAttribute("orden", orden);
		model.addAttribute("numeroRegistros", format.format(totalRegistros));		
		model.addAttribute("encabezados", encabezados);
		
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
		
		return "redirect:/" + paginaLista;
	}
	
	@RequestMapping(value = "/facturacionLista&orden={campoOrden}", method = RequestMethod.GET)
	private String ordenar(@PathVariable("campoOrden") String campoOrden){
		
		super.ordenarLista(campoOrden);
		
		return "redirect:/" + paginaLista;
	}
	
	@RequestMapping(value = "/buscarFacturacion", method = RequestMethod.POST)	
	public String buscar(Model model, @ModelAttribute("textoBuscar") String textoBuscar){
		
		super.buscar(textoBuscar);
		
		return "redirect:/" + paginaLista;
	}
	
	@RequestMapping(value = "/facturacionForm", method = RequestMethod.GET)
	public String mostrarFormulario(Model model, @RequestParam(value="idFacturacion",required=false) Integer idFacturacion) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
				
		Facturacion facturacion = new Facturacion();
		
		if(idFacturacion!=null){

			facturacion = facturacionService.buscarId(idFacturacion);
			
		}
		
		model.addAttribute("facturacion", facturacion);	
		
		return "terceroForm";
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