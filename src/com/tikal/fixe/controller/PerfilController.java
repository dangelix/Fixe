package com.tikal.mensajeria.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.mensajeria.dao.PerfilDAO;
import com.tikal.mensajeria.dao.SessionDao;
import com.tikal.mensajeria.dao.UsuarioDao;
import com.tikal.mensajeria.modelo.login.Perfil;
import com.tikal.util.AsignadorDeCharset;
import com.tikal.util.JsonConvertidor;



@Controller
@RequestMapping("/perfil")
public class PerfilController {

	
	 
	@Autowired
	@Qualifier("usuarioDao")
	UsuarioDao usuarioDao;
	
	@Autowired
	@Qualifier("perfilDAO")
	PerfilDAO perfilDAO;
	
	 @Autowired
	 @Qualifier("sessionDao")
	 SessionDao sessionDao;

	 

	
	@RequestMapping(value={"/prueba"},method = RequestMethod.GET)
	   
	   public void prueba(HttpServletResponse response, HttpServletRequest request) throws IOException {
		   response.getWriter().println("Prueba del mètodo Perfil");

	    }

	@RequestMapping(value = { "/add/{userName}" }, method = RequestMethod.POST, consumes = "Application/Json")
	public void crearPerfil(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody String json, @PathVariable String userName)throws IOException {
//		if(SesionController.verificarPermiso2(request, usuarioDao, perfilDAO, 45, sessionDao,userName)){
			AsignadorDeCharset.asignar(request, response);
			System.out.println(" edgar manda:"+json);
			Perfil perfil = (Perfil) JsonConvertidor.fromJson(json, Perfil.class);
			System.out.println("lista de permisos:"+perfil.getPermisos());
			perfilDAO.crearPerfil(perfil);
//		} else {
//			response.sendError(403);
//		}
	}

	@RequestMapping(value = { "/getAll" }, method = RequestMethod.GET, produces = "application/json")
	public void consultarUsuarios(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//if (SesionController.verificarPermiso(request, usuarioDao, perfilDAO, 10)) {
			AsignadorDeCharset.asignar(request, response);
			this.consult(response);
	//	}else{
	//		response.sendError(403);
	//	}
	}

	@RequestMapping(value = { "/update/{userName}" }, method = RequestMethod.POST, consumes = "Application/Json")
	public void actualizarUsuario(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json, @PathVariable String userName)throws IOException {
//		if(SesionController.verificarPermiso2(request, usuarioDao, perfilDAO, 46, sessionDao,userName)){
			AsignadorDeCharset.asignar(request, response);
			Perfil perfil = (Perfil) JsonConvertidor.fromJson(json, Perfil.class);
			Perfil aux = perfilDAO.consultarPerfilPorId(perfil.getId());
			String nombrePerfilViejo = aux.getTipo();
			perfilDAO.actulizaPerfil(perfil);
			usuarioDao.actualizarUsuarios(nombrePerfilViejo, perfil.getTipo());
//		} else {
//			response.sendError(403);
//		}

	}

	@RequestMapping(value = { "/delete/{idPerfil}/{userName}" }, method = RequestMethod.POST, consumes = "Application/Json")
	public void eliminarUsuario(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String json,@PathVariable String idPerfil, @PathVariable String userName)throws IOException {
//		if(SesionController.verificarPermiso2(request, usuarioDao, perfilDAO, 47, sessionDao,userName)){
			AsignadorDeCharset.asignar(request, response);
			//Perfil perfil = (Perfil) JsonConvertidor.fromJson(json, Perfil.class);
			Perfil per = perfilDAO.consultarPerfil(idPerfil);
			perfilDAO.eliminarPerfil(per.getTipo());
//		}else{
//			response.sendError(403);
//		}
	}
	
	//consulta los perfiles de cada usuario
	@RequestMapping(value = { "/getAllU" }, method = RequestMethod.GET, produces = "application/json")
	public void consultarPerfiles(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		if (SesionController.verificarPermiso(request, usuarioDao, perfilDAO, 7)) {
			AsignadorDeCharset.asignar(request, response);
			this.consult(response);
//		}else{
//			response.sendError(403);
//		}
	}
	
	private void consult(HttpServletResponse response) throws IOException{
		List<Perfil> lista = perfilDAO.consultarPerfiles();
		response.getWriter().println(JsonConvertidor.toJson(lista));
	}

}
