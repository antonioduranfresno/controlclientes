package net.gefco.controlclientes.controladores;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import net.gefco.controlclientes.modelo.Comercial;
import net.gefco.controlclientes.negocio.AgenciaService;
import net.gefco.controlclientes.negocio.ComercialService;
import net.gefco.controlclientes.negocio.ComercialTipoService;
import net.gefco.controlclientes.negocio.SuiviEsService;
import net.gefco.controlclientes.util.AbstractDataTable;
import net.gefco.controlclientes.util.DataTableColumn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("session")
@SessionAttributes("usuarioSesion")
public class ComercialController extends AbstractDataTable<Comercial, ComercialService> {
	
	@Autowired
	private ComercialService 		comercialService;
	
	@Autowired
	private AgenciaService			agenciaService;
	
	@Autowired
	private ComercialTipoService	comercialTipoService;
	
	@Autowired
	private SuiviEsService			suiviEsService;
	
	DecimalFormat 						format		= new DecimalFormat("#,###,###,##0.##");

	@PostConstruct
	public void iniciarControler() {
		
		dt_service 			= comercialService;
		dt_paginaLista 		= "comercialLista";
		dt_orden			= "";
		dt_HQLfrom			= "Comercial c";
				
		dt_columnas = new LinkedHashMap<String, DataTableColumn>();
		dt_columnas.put("id",  					new DataTableColumn("Id", 			Integer.class, 	"c.id"));	
		dt_columnas.put("codigo", 				new DataTableColumn("Código", 		String.class, 	"c.come_codigo"));
		dt_columnas.put("nombre", 				new DataTableColumn("Nombre", 		String.class,	"c.come_nombre"));
		dt_columnas.put("agencia", 				new DataTableColumn("Agencia", 		String.class,   "c.agencia.agen_codigo"));
		dt_columnas.put("tipo", 				new DataTableColumn("Tipo",			String.class, 	"c.comercialTipo.coti_codigo"));
		dt_columnas.put("suiviEs", 				new DataTableColumn("Suivi", 		String.class,   "c.suiviEs.sues_nombre"));
		
	}
	
	@RequestMapping(value = "/comercialLista", method = RequestMethod.GET)
	public String lista(Model model, @ModelAttribute("comercial") Comercial comercial) 
			throws 	NoSuchMethodException, SecurityException, IllegalAccessException, 
					IllegalArgumentException, InvocationTargetException{
						
		filtrarLista();
		
		model.addAttribute("textoBuscar", 		dt_textoBusqueda);
		model.addAttribute("paginaActual", 		dt_paginaActual);
		model.addAttribute("numeroPaginas", 	dt_numeroPaginas);
		model.addAttribute("listaTerceros", 	dt_lista);
		model.addAttribute("columnas", 			dt_columnas);
		model.addAttribute("orden", 			dt_orden);
		model.addAttribute("numeroRegistros", 	dt_totalRegistros);		
		
		return "comercialLista";
	}
	
	@RequestMapping(value = "/comercialListaMoverAPagina{param1}", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/comercialLista&orden={campoOrden}", method = RequestMethod.GET)
	private String ordenar(@PathVariable("campoOrden") String campoOrden){
		super.ordenarLista(campoOrden);
		
		return "redirect:/" + dt_paginaLista;
	}
	
	@RequestMapping(value = "/buscarComerciales", method = RequestMethod.POST)	
	public String buscar(Model model, @ModelAttribute("textoBuscar") String textoBuscar){
		
		super.buscar(textoBuscar);
		
		return "redirect:/" + dt_paginaLista;
	}
	
	@RequestMapping(value = "/comercialForm", method = RequestMethod.GET)
	public String mostrarFormulario(Model model, @RequestParam(value="idComercial",required=false) Integer idComercial) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
				
		Comercial comercial = new Comercial();
		
		if(idComercial!=null){

			comercial = comercialService.buscarId(idComercial);
			
		}
		
		model.addAttribute("comercial", comercial);	
		model.addAttribute("listaAgencias", agenciaService.listado());
		model.addAttribute("listaComercialesTipo", comercialTipoService.listado());
		model.addAttribute("listaSuiviEs", suiviEsService.listado());	
		
		return "comercialForm";
	}
	
	
	@RequestMapping(value = "/comercialForm", method = RequestMethod.POST, params="action=Aceptar")
	public String aceptar(Model model,@ModelAttribute("comercial") @Valid Comercial comercial, BindingResult result) throws InvocationTargetException {
		
		if(comercial.getAgencia().getId()==0){			
			FieldError error = new FieldError("comercial", "agencia.id", "Por favor, seleccione una opción");			
			result.addError(error);			
		}
		
		if(comercial.getComercialTipo().getId()==0){			
			FieldError error = new FieldError("comercial", "comercialTipo.id", "Por favor, seleccione una opción");			
			result.addError(error);			
		}

		if(comercial.getSuiviEs().getId()==0){			
			FieldError error = new FieldError("comercial", "suiviEs.id", "Por favor, seleccione una opción");			
			result.addError(error);			
		}
		
		if(result.hasErrors()){			
			model.addAttribute("listaAgencias", agenciaService.listado());
			model.addAttribute("listaComercialesTipo", comercialTipoService.listado());
			model.addAttribute("listaSuiviEs", suiviEsService.listado());
			return "comercialForm";			
		}

		try{
				
			//Creación
			if(comercial.getId()==null || comercial.getId()==0){
			
				comercial.setId(0);	
				comercialService.guardar(comercial);				
				comercial = new Comercial();
                
			//Actualización	
			}else{				
				comercialService.actualizar(comercial);				
			}
            
			return "redirect:/comercialLista?success=true";
							                
         } catch (InvocationTargetException e) {
        	 
                FieldError error;
                if(e.getCause().toString().contains("key 2") || e.getCause().toString().contains("come_codigo")){
                	error = new FieldError("comercial", "terc_codigo", "Código de comercial ya existente.");
                }else{
                	error = new FieldError("comercial", "terc_codigo", "error no controlado: " + e.getCause().getMessage());
                }
                
                result.addError(error);     
                
    			model.addAttribute("listaAgencias", agenciaService.listado());
    			model.addAttribute("listaComercialesTipo", comercialTipoService.listado());
    			model.addAttribute("listaSuiviEs", suiviEsService.listado()); 
    			
                return "comercialForm";                
         } 
		
			
	}
	
	@RequestMapping(value = "/comercialLista&id={idComercial}/eliminar", method = RequestMethod.POST)
	@ResponseBody
	public String eliminarDeLista(@PathVariable("idComercial") Integer idComercial){
		
		try{
			Comercial comercial = comercialService.buscarId(idComercial);
			comercialService.eliminar(comercial);	
			return "ok";
			
		}catch(Exception e){			
			return "error";
		}		
	}
	
	
	@RequestMapping(value = "/comercialesExcel", method = RequestMethod.GET)
    public ModelAndView descargarExcel() {
		
		List <Comercial> listaExcel = new ArrayList<Comercial>();
		
		//Aplicar filtro
		try {
			for(Comercial terc : comercialService.listadoOrdenado(dt_orden)){
				if(terc.toString().toUpperCase().contains(dt_textoBusqueda.toUpperCase())){					
					listaExcel.add(terc);
				}
			}
			
		} catch ( SecurityException | IllegalArgumentException	| InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
		
		return new ModelAndView("excelViewComerciales", "comercialesExcel", listaExcel);
    }
	
}