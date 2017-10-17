package net.gefco.controlclientes.controladores;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.gefco.controlclientes.modelo.Usuario;
import net.gefco.controlclientes.negocio.UsuarioService;
import net.gefco.controlclientes.util.Encriptacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Controller
@SessionAttributes("usuarioSesion")
public class IndexController {

	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping("/")
	public String showIndex(Model model){
		
		return "index";
	}

	@RequestMapping("login")  
	public void login(Model model, HttpServletRequest request, HttpServletResponse response){

		try {
			
			Gson 					gson 						= new Gson(); 
			JsonObject 				myObj 						= new JsonObject();
			
			JsonElement				jsMensajeLogin 				= null;
						
			String login 	= request.getParameter("login");
			String password = request.getParameter("password");
			
			if(usuarioService.buscarMatricula(login.toUpperCase())!=null){
				
				Usuario usuarioAspirante = usuarioService.buscarMatricula(login.toUpperCase());
				
				if(Encriptacion.encriptar(password).equalsIgnoreCase(usuarioAspirante.getUsua_pw())){
				
					model.addAttribute("usuarioSesion", usuarioAspirante);
					
				}else{				
					jsMensajeLogin = gson.toJsonTree("Error. Password incorrecto.");				
				}	
				
			}else{			
				jsMensajeLogin = gson.toJsonTree("Error. El usuario no existe en la base de datos.");			
			}
			
			myObj.add("mensaje", jsMensajeLogin);		
			PrintWriter out = response.getWriter();		
			out.println(myObj.toString());	
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}