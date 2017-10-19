package net.gefco.controlclientes.controladores;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import net.gefco.controlclientes.modelo.TerceroTipo;
import net.gefco.controlclientes.negocio.TerceroTipoService;
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
public class TerceroTipoController extends AbstractDataTable<TerceroTipo, TerceroTipoService> {
	
	@Autowired
	private TerceroTipoService 		terceroTipoService;
	
	
	DecimalFormat 						format		= new DecimalFormat("#,###,###,##0.##");

	@PostConstruct
	public void iniciarControler() {
		
		service 		= terceroTipoService;
		paginaLista 	= "terceroTipoLista";
		orden			= "";
		
		encabezados = new HashMap<String, DataTableColumn>();
		encabezados.put("nombre", new DataTableColumn("Nombre",	"teti_nombre", paginaLista + "&orden=teti_nombre", ""));
	}
	
	
	@RequestMapping(value = "/terceroTipoLista", method = RequestMethod.GET)
	public String lista(Model model, @ModelAttribute("terceroTipo") TerceroTipo terceroTipo) 
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
		
		return "terceroTipoLista";
	}
	
	@RequestMapping(value = "/terceroTipoListaMoverAPagina{param1}", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/terceroTipoLista&orden={campoOrden}", method = RequestMethod.GET)
	private String ordenar(@PathVariable("campoOrden") String campoOrden){
		super.ordenarLista(campoOrden);
		
		return "redirect:/" + paginaLista;
	}
	
	@RequestMapping(value = "/buscarTercerosTipo", method = RequestMethod.POST)	
	public String buscar(Model model, @ModelAttribute("textoBuscar") String textoBuscar){
		
		super.buscar(textoBuscar);
		
		return "redirect:/" + paginaLista;
	}
	
	@RequestMapping(value = "/terceroTipoForm", method = RequestMethod.GET)
	public String mostrarFormulario(Model model, @RequestParam(value="idTerceroTipo",required=false) Integer idTerceroTipo) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
				
		TerceroTipo terceroTipo = new TerceroTipo();
		
		if(idTerceroTipo != null){

			terceroTipo = terceroTipoService.buscarId(idTerceroTipo);
			
		}
		
		model.addAttribute("terceroTipo", terceroTipo);	
				
		return "terceroTipoForm";
	}
	
	//Tanto para crear uno nuevo como para editar uno existente
	@RequestMapping(value = "/terceroTipoForm", method = RequestMethod.POST, params="action=Aceptar")
	public String aceptar(Model model,@ModelAttribute("terceroTipo") @Valid TerceroTipo terceroTipo, BindingResult result){
		
		if(result.hasErrors()){			
			return "terceroTipoForm";			
		}

		try{
				
			//Creación
			if(terceroTipo.getId()==null || terceroTipo.getId()==0){
			
				terceroTipo.setId(0);	
				terceroTipoService.guardar(terceroTipo);				
				terceroTipo = new TerceroTipo();
                
			//Actualización	
			}else{				
				terceroTipoService.actualizar(terceroTipo);				
			}
            
			return "redirect:/terceroTipoLista?success=true";
							                
         } catch (Exception e) {
        	 
                FieldError error;
                                
                if (e.getClass().equals(DataIntegrityViolationException.class)){
                	error = new FieldError("terceroTipo", "teti_nombre", "El nombre del grupo ya existente.");
                
                }else{
                	error = new FieldError("terceroTipo", "teti_nombre", "error no controlado: " + e.getMessage());
                }
                
                result.addError(error);     
                
                return "terceroForm";                
         } 
		
			
	}
	
	@RequestMapping(value = "/terceroTipoLista&id={idTerceroTipo}/eliminar", method = RequestMethod.POST)
	@ResponseBody
	public String eliminarDeLista(@PathVariable("idTerceroTipo") Integer idTerceroTipo) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		TerceroTipo terceroTipo = terceroTipoService.buscarId(idTerceroTipo);
		
		try{
			terceroTipoService.eliminar(terceroTipo);	
			return "ok";			
		}catch(Exception e){			
			return "error";			
		}		
	}
	
	
	@RequestMapping(value = "/tercerosTipoExcel", method = RequestMethod.GET)
    public ModelAndView descargarExcel() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List <TerceroTipo> listaExcel = new ArrayList<TerceroTipo>();
		
		//Aplicar filtro
		for(TerceroTipo tg : terceroTipoService.listadoOrdenado(orden)){
			if(tg.toString().toUpperCase().contains(textoBusqueda.toUpperCase())){					
				listaExcel.add(tg);
			}
		}
		
		return new ModelAndView("excelViewTercerosTipo", "tercerosTipoExcel", listaExcel);
    }
	
}