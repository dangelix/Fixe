package com.tikal.mensajeria.controller;

import java.io.IOException;
//import java.security.Principal;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.tikal.mensajeria.modelo.login.SessionEntity;
import com.tikal.mensajeria.modelo.login.Usuario;
import com.tikal.util.AsignadorDeCharset;
import com.tikal.util.JsonConvertidor;


//@SessionAttributes({"usuario"})
@Controller

public class SesionController {

	@Autowired
	UsuarioDao usuarioDao;  // para la autentificacion
	
	@Autowired
	@Qualifier ("sessionDao")
	SessionDao sessionDao;
	
	@Autowired
	PerfilDAO perfilDAO;
	
	////////////////////// checar si el usuario es válido

	@RequestMapping(value = { "/user" }, method = RequestMethod.POST, consumes="application/json", produces = "application/json")
	public void user(HttpServletResponse res, HttpServletRequest req, @RequestBody String json) throws IOException {

		AsignadorDeCharset.asignar(req, res);
		System.out.println("usuario edgar "+json);
		Usuario usuarioFront = (Usuario) JsonConvertidor.fromJson(json, Usuario.class);
		if (usuarioFront.getUsername().isEmpty() || usuarioFront.getPassword().isEmpty()){
			System.out.println("username vacio:"+usuarioFront.getUsername());
			System.out.println("Error 403 , usuario no autentificado");
			res.sendError(403);
		}else{
				System.out.println("username json:"+usuarioFront.getUsername());
				Usuario usuBase= usuarioDao.consultarUsuario(usuarioFront.getUsername());
				System.out.println("obeto usuario de bck:"+usuBase);
				if(usuBase==null){
					System.out.println("username vacio:"+usuarioFront.getUsername());
					System.out.println("Error 403 , usuario no autentificado");
					res.sendError(403);
				}else{
						String p= usuarioFront.getPassword(); 
						
						System.out.println("password front:"+p);
						System.out.println("password back:"+usuBase.getPassword());		
						String passfrontCryp= UsuarioController.otroMetodo(p);
						System.out.println("password encriptado front:"+passfrontCryp);
						
						if (usuBase.getPassword().equals(passfrontCryp) == false) {
							System.out.println("Error 403 , usuario no autentificado");
							res.sendError(403);
							 
						} else {
							System.out.println(" usuario Valido_Post");
							
							//usuario.resetPassword();
							//req.getSession().setAttribute("userName", usuarioFront.getUsername());
							HttpSession session = req.getSession();
							SessionEntity s = new SessionEntity();
							s.setIdSession(session.getId());
							s.setNameUser(usuarioFront.getUsername());
							s.setId(s.getIdSession());
							//s.setId(Long.parseLong("121112112222"));
							s.setEstatus("activa");
							System.out.println("objeto s++id+++"+s.getId());
							System.out.println("objeto s++++name+"+s.getNameUser());
							System.out.println("objeto s++idSession+++"+s.getIdSession());
							System.out.println("objeto s++++estatus+"+s.getEstatus());
							sessionDao.save(s);
							//s.setAttribute("usuario", usuarioFront);
						//	s.setAttribute("usuario", "yomero");
							System.out.println(" ++++++ sesion:"+session);
							System.out.println(" -+++++id:"+session.getId());
							req.setAttribute("usuario", usuarioFront.getUsername());
							s.setNameUser(usuarioFront.getUsername());
							System.out.println("req++++++++"+req.getAttribute("usuario"));
							//System.out.println("session:::::"+s.getAttribute("usuario"));
							 Cookie miCookie = new Cookie("userName",s.getNameUser());
                             System.out.println("cookie:::"+miCookie.getValue());
							
							res.getWriter().println(s.getNameUser());						
							//res.getWriter().println(JsonConvertidor.toJson(s.getNameUser()));
						}
				}
		}
	}

	
	@RequestMapping(value = { "/user" }, method = RequestMethod.GET, consumes="application/json", produces = "application/json")
	public void userg(HttpServletResponse res, HttpServletRequest req, @RequestBody String json) throws IOException {

		AsignadorDeCharset.asignar(req, res);
		System.out.println("usuario edgar "+json);
		Usuario usuarioFront = (Usuario) JsonConvertidor.fromJson(json, Usuario.class);
		if (usuarioFront.getUsername().isEmpty() || usuarioFront.getPassword().isEmpty()){
			System.out.println("username vacio:"+usuarioFront.getUsername());
			System.out.println("Error 403 , usuario no autentificado");
			res.sendError(403);
		}else{
				System.out.println("username json:"+usuarioFront.getUsername());
				Usuario usuBase= usuarioDao.consultarUsuario(usuarioFront.getUsername());
				System.out.println("obeto usuario de bck:"+usuBase);
				if(usuBase==null){
					System.out.println("username vacio:"+usuarioFront.getUsername());
					System.out.println("Error 403 , usuario no autentificado");
					res.sendError(403);
				}else{
						String p= usuarioFront.getPassword(); 
						
						System.out.println("password front:"+p);
						System.out.println("password back:"+usuBase.getPassword());		
						String passfrontCryp= UsuarioController.otroMetodo(p);
						System.out.println("password encriptado front:"+passfrontCryp);
						
						if (usuBase.getPassword().equals(passfrontCryp) == false) {
							System.out.println("Error 403 , usuario no autentificado");
							res.sendError(403);
							 
						} else {
							System.out.println(" usuario Valido---Get");
							/////////////////////////////////
							//HttpServletRequest request = this.getThreadLocalRequest();

							//HttpSession session = request.getSession();

							// in your authentication method
							//if(isCorrectPassword)
							  // session.setAttribute("authenticatedUserName", "name");
							////////////////////////////////////
							//usuario.resetPassword();
							//req.getSession().setAttribute("userName", usuarioFront.getUsername());
							//req.getRequestedSessionId().getSession()
							
							//usuario.resetPassword();
							//req.getSession().setAttribute("userName", usuarioFront.getUsername());
							HttpSession session = req.getSession();
							SessionEntity s = new SessionEntity();
							s.setIdSession(session.getId());
							//s.setAttribute("usuario", usuarioFront);
						//	s.setAttribute("usuario", "yomero");
							System.out.println(" ------ sesion:"+session);
							System.out.println(" ------id:"+session.getId());
							req.setAttribute("usuario", usuarioFront.getUsername());
							s.setNameUser(usuarioFront.getUsername());
							System.out.println("req:::::"+req.getAttribute("usuario"));
							//System.out.println("session:::::"+s.getAttribute("usuario"));
							System.out.println("objetossss s:::::"+s);
							sessionDao.save(s);
							
							 //final long DURATION = 1000 * 60 ; //duration remembering login. 2 weeks
                             //Date expires = new Date(System.currentTimeMillis() + (15*DURATION));
                            // Cookies.setCookie("sid", session.getId(), expires, null, "/", false);
                             Cookie miCookie = new Cookie("userName",s.getNameUser());
                             System.out.println("cookie:::"+miCookie.getValue());
                             res.addCookie(miCookie);
							res.getWriter().println(s.getNameUser());///ojo decirle a edgar como la lleva esta...
						}
				}
		}
	}

   
	
	@RequestMapping(value = { "/currentSession" }, method = RequestMethod.GET, produces = "application/json")
	public void currentUser(HttpServletResponse res, HttpServletRequest req) throws IOException, ServletException {
		System.out.println("currentSession:autenticate::"+req.authenticate(res));
		HttpSession s = req.getSession();
		String name = (String) req.getAttribute("usuario");
		System.out.println("usuario en req:"+req.getAttributeNames());
		System.out.println(" ------ sesion:"+s);
		String n = (String) s.getAttribute("usuario");
		
		System.out.println(" atributo usuario de la sesion:"+req.getAttribute("usuario"));
		if (n == null) {
			System.out.println(" No hay usuario asignado a la session");   
		 	res.sendError(400);
		}
	}
	
	
	/// verifica los permisos del usuario ya autentificado
	
	public static boolean verificarPermiso(HttpServletRequest request, UsuarioDao usuarioDao, PerfilDAO  perfildao, int per){
		HttpSession s = request.getSession();
		String nombreUsuario = (String) s.getAttribute("userName");
		System.out.println("cookie:::"+request.getCookies());
		System.out.println(" ------ usuario:"+nombreUsuario);
		System.out.println(" ------ req:atribute:usuario"+request.getAttribute("usuario"));
		if(nombreUsuario == null){
			System.out.println(" *****************usuario con permisos*******");
			return false;
		}else{
			System.out.println(" ----------------usuario SIN PERMISOS-----------");
			Usuario usuario = usuarioDao.consultarUsuario(nombreUsuario);
			Perfil perfil = perfildao.consultarPerfil(usuario.getPerfil());
			if(perfil.getPermisos()[per]==true){
				return true;
			}
		}
		return false;
	}
	
	public static boolean verificarPermiso2(HttpServletRequest request, UsuarioDao usuarioDao, PerfilDAO  perfildao, int per, SessionDao sessionDao, String userName){
		String u = (String) request.getAttribute("usuario");
		System.out.println("cookie:::"+request.getCookies());
	////	String nombreUsuario = (String) s.getAttribute("userName");
		System.out.println(" ------ usuario:"+u);
		//System.out.println(" ------ req:atribute:usuario"+request.getAttribute("usuario"));
	//	System.out.println(" --------- id de session consultada------"+s.getId());
//		SessionEntity ss = sessionDao.consult();
//		System.out.println(" ---------session consultada------"+ss);
		if(userName == null){
			System.out.println(" ****************No existe ese usuario en sessionEntity*******");
			return false;
		}else{
		//	System.out.println(" ----------------usuario con session-----------");
			SessionEntity s= sessionDao.getByName(userName);
			if (s==null){
				System.out.println(" *****No hay session con ese  usuario *******");
				return false;
			}
			Usuario usuario = usuarioDao.consultarUsuario(userName);
			Perfil perfil = perfildao.consultarPerfil(usuario.getPerfil());
			if(perfil.getPermisos()[per]==true){
				System.out.println(" ----------------usuario con Permisos-----------");
				return true;
			}
		}
		System.out.println(" ----------------usuario SIN PERMISOS----------");
		return false;
	}
	
	
	@RequestMapping(value = { "/valida/{userName}/{per}" }, method = RequestMethod.GET)
	public void valida(HttpServletResponse res, HttpServletRequest req, @RequestBody String json, @PathVariable String userName, @PathVariable int per) throws IOException {

		AsignadorDeCharset.asignar(req, res);
		System.out.println("usuario de edgar :"+userName);
		Usuario usuarioFront = (Usuario) JsonConvertidor.fromJson(json, Usuario.class);
	
		if(userName == null){
			System.out.println(" ****************No existe ese usuario en sessionEntity*******");
			System.out.println("Error 403 , usuario no autentificado");
			res.sendError(403);
		}else{
		//	System.out.println(" ----------------usuario con session-----------");
			SessionEntity s= sessionDao.getByName(userName);
			if (s==null){
				System.out.println(" *****No hay session con ese  usuario *******");
				System.out.println("Error 403 , usuario no autentificado");
				res.sendError(403);
			}
			Usuario usuario = usuarioDao.consultarUsuario(userName);
			Perfil perfil = perfilDAO.consultarPerfil(usuario.getPerfil());
			if(perfil.getPermisos()[per]==true){
				System.out.println(" ----------------usuario con Permisos-----------");
				res.getWriter().println("ok");
			}
		}
		System.out.println(" ----------------usuario SIN PERMISOS----------");
		System.out.println("Error 403 , usuario no autentificado");
		res.sendError(403);
	}
}

