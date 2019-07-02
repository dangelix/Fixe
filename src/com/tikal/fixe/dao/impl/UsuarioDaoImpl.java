package com.tikal.mensajeria.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tikal.mensajeria.dao.UsuarioDao;
import com.tikal.mensajeria.modelo.login.Usuario;


@Repository
@Service("usuarioDao")  ///esta se la puse yo
public class UsuarioDaoImpl implements UsuarioDao{
	
	@Override
	public void add(Usuario usuario) {
		ofy().save().entity(usuario);

	}

	@Override
	public void update(Usuario usuario) {
		ofy().save().entity(usuario);

	}

	@Override
	public Usuario consult(String id) {
		return ofy().load().type(Usuario.class).id(id).now();
	}

	@Override
	public void delete(Usuario usuario) {
		ofy().delete().entity(usuario);

	}


	@Override
	public boolean crearUsuario(Usuario usuario) {
//		System.out.println(this.consultarPorEmail(usuario.getEmail()).getEmail()+"PRIMER IF");
//		System.out.println(this.consultarUsuario(usuario.getUsername())+"SEGUNDO IF");

		//if(this.consultarPorEmail(usuario.getEmail()).getEmail()!=null){
			//return false;
		//}
		System.out.println("entra a crear usuario"+usuario.getUsername());
		if (this.consultarUsuario(usuario.getUsername())==null) {
			System.out.println("xxxxxxxxx");
			ofy().save().entity(usuario).now();
		} else {
			System.out.println("yyyyyyyyyyyyyy");
			return false;
		}

		return true;
	}

	@Override
	public boolean eliminarUsuario(String usuario) {
		ofy().delete().entities(this.consultarUsuario(usuario)).now();
		return true;
	}

	@Override
	public Usuario consultarUsuario(String usuario) {
		System.out.println("consultar usuario");
		List<Usuario> usu = ofy().load().type(Usuario.class).filter("usuario", usuario).list();
		System.out.println("lista de usuarios con ese username:"+usu);
		if (usu.size() == 0) {
			return null;
		}
		Usuario nuevo = usu.get(0);
		return nuevo;
	}
	
	@Override
	public Usuario consult(Long idUsuario) {
		System.out.println("lista de usuarios con ese username:"+idUsuario);
		return ofy().load().type(Usuario.class).id(idUsuario).now();
		
		
	}

	@Override
	public List<Usuario> consultarUsuarios() {

		return ofy().load().type(Usuario.class).list();
	}

	@Override
	public boolean actualizarUsuario(Usuario u) {
		
		 System.out.print("usu:"+u.getId());
			Usuario old = this.consultarUsuario(u.getUsername());
			System.out.print("old:"+old);
				if (old != null) {
					old.setUsername(u.getUsername());
					old.setNombre(u.getNombre());
					old.setaPaterno(u.getaPaterno());
					old.setaMaterno(u.getaMaterno());
					old.setPerfil(u.getPerfil());
				
					
				}

					ofy().save().entity(old);
		
		return true;
	}

	@Override
	public boolean eliminarUsuario(Usuario usuario) {
		ofy().delete().entity(usuario).now();
		return true;
	}

	@Override
	public boolean actualizarUsuarios(String nombrePerfilviejo, String nombrePerfilNuevo) {
		List<Usuario> lista =  ofy().load().type(Usuario.class).filter("perfil", nombrePerfilviejo).list();
		for(int i = 0; i < lista.size(); i++){
			lista.get(i).setPerfil(nombrePerfilNuevo);
		}
		ofy().save().entities(lista).now();
		return true;
	}

	@Override
	public Usuario consultarPorEmail(String email) {
		List<Usuario> lista = ofy().load().type(Usuario.class).filter("email", email).list();	
//		System.out.println("Tama�o de la lista: " + lista.size());
		Usuario usuario = new Usuario();
		if(lista.size()>0){
			return lista.get(0);
		}
		return usuario;	
	}

}



