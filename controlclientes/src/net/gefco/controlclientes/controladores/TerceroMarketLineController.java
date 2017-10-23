package net.gefco.controlclientes.controladores;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import net.gefco.controlclientes.modelo.TerceroMarketLine;
import net.gefco.controlclientes.negocio.TerceroMarketLineService;
import net.gefco.controlclientes.util.AbstractDataTable;
import net.gefco.controlclientes.util.DataTableColumn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
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
public class TerceroMarketLineController extends AbstractDataTable<TerceroMarketLine, TerceroMarketLineService> {
	
	@Autowired
	private TerceroMarketLineService 		terceroMarketLineService;
	
	
	DecimalFormat 						format		= new DecimalFormat("#,###,###,##0.##");

	@PostConstruct
	public void iniciarControler() {
		
		service 		= terceroMarketLineService;
		paginaLista 	= "terceroMarketLineLista";
		orden			= "";
		
		encabezados = new HashMap<String, DataTableColumn>();
		encabezados.put("nombre", new DataTableColumn("Nombre",	"teml_nombre", paginaLista + "&orden=teml_nombre", ""));
	}
	
	
	@RequestMapping(value = "/terceroMarketLineLista", method = RequestMethod.GET)
	public String lista(Model model, @ModelAttribute("terceroMarketLine") TerceroMarketLine terceroMarketLine) 
			throws 	NoSuchMethodException, SecurityException, IllegalAccessException, 
					IllegalArgumentException, InvocationTargetException{
						
		filtrarLista();
		model.addAttribute("textoBuscar", textoBusqueda);
		model.addAttribute("paginaActual", paginaActual);
		model.addAttribute("numeroPaginas", numeroPaginas);			
		model.addAttribute("lista", lista);
		model.addAttribute("orden", orden);
		model.addAttribute("numeroRegistros", format.format(totalRegistros));
		
		model.addAttribute("encabezados", encabezados);
		
		return "terceroMarketLineLista";
	}
	
	@RequestMapping(value = "/terceroMarketLineListaMoverAPagina{param1}", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/terceroMarketLineLista&orden={campoOrden}", method = RequestMethod.GET)
	private String ordenar(@PathVariable("campoOrden") String campoOrden){
		super.ordenarLista(campoOrden);
		
		return "redirect:/" + paginaLista;
	}
	
	@RequestMapping(value = "/buscarTercerosMarketLine", method = RequestMethod.POST)	
	public String buscar(Model model, @ModelAttribute("textoBuscar") String textoBuscar){
		
		super.buscar(textoBuscar);
		
		return "redirect:/" + paginaLista;
	}
	
	@RequestMapping(value = "/terceroMarketLineForm", method = RequestMethod.GET)
	public String mostrarFormulario(Model model, @RequestParam(value="idTerceroMarketLine",required=false) Integer idTerceroMarketLine) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
				
		TerceroMarketLine terceroMarketLine = new TerceroMarketLine();
		
		if(idTerceroMarketLine != null){

			terceroMarketLine = terceroMarketLineService.buscarId(idTerceroMarketLine);
			
		}
		
		model.addAttribute("terceroMarketLine", terceroMarketLine);	
				
		return "terceroMarketLineForm";
	}
	
	//Tanto para crear uno nuevo como para editar uno existente
	@RequestMapping(value = "/terceroMarketLineForm", method = RequestMethod.POST, params="action=Aceptar")
	public String aceptar(Model model,@ModelAttribute("terceroMarketLine") @Valid TerceroMarketLine terceroMarketLine, BindingResult result){
		
		if(result.hasErrors()){			
			return "terceroMarketLineForm";			
		}

		try{
				
			//Creación
			if(terceroMarketLine.getId()==null || terceroMarketLine.getId()==0){
			
				terceroMarketLine.setId(0);	
				terceroMarketLineService.guardar(terceroMarketLine);				
				terceroMarketLine = new TerceroMarketLine();
                
			//Actualización	
			}else{				
				terceroMarketLineService.actualizar(terceroMarketLine);				
			}
            
			return "redirect:/terceroMarketLineLista?success=true";
							                
         } catch (Exception e) {
        	 
                FieldError error;
                                
                if (e.getClass().equals(DataIntegrityViolationException.class)){
                	error = new FieldError("terceroMarketLine", "teml_nombre", "El nombre del grupo ya existente.");
                
                }else{
                	error = new FieldError("terceroMarketLine", "teml_nombre", "error no controlado: " + e.getMessage());
                }
                
                result.addError(error);     
                
                return "terceroForm";                
         } 
		
			
	}
	
	@RequestMapping(value = "/terceroMarketLineLista&id={idTerceroMarketLine}/eliminar", method = RequestMethod.POST)
	@ResponseBody
	public String eliminarDeLista(@PathVariable("idTerceroMarketLine") Integer idTerceroMarketLine) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		TerceroMarketLine terceroMarketLine = terceroMarketLineService.buscarId(idTerceroMarketLine);
		
		try{
			terceroMarketLineService.eliminar(terceroMarketLine);	
			return "ok";			
		}catch(Exception e){			
			return "error";			
		}		
	}
	
	
	@RequestMapping(value = "/tercerosMarketLineExcel", method = RequestMethod.GET)
    public ModelAndView descargarExcel() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List <TerceroMarketLine> listaExcel = new ArrayList<TerceroMarketLine>();
		
		//Aplicar filtro
		for(TerceroMarketLine tg : terceroMarketLineService.listadoOrdenado(orden)){
			if(tg.toString().toUpperCase().contains(textoBusqueda.toUpperCase())){					
				listaExcel.add(tg);
			}
		}
		
		return new ModelAndView("excelViewTercerosMarketLine", "tercerosMarketLineExcel", listaExcel);
    }
	
}