package com.tikal.mensajeria.dao;

import java.util.List;

import com.tikal.mensajeria.modelo.login.Usuario;

public interface UsuarioDao {
	
		public void add(Usuario usuario);
		
		public void update(Usuario usuario);
		
		public Usuario consult(String id);
		
		public void delete(Usuario usuario);	
		
		public boolean crearUsuario(Usuario usuario);
		public boolean eliminarUsuario(String usuario);
		public Usuario consultarUsuario(String usuario);
		public Usuario consult(Long usuario);
		public List<Usuario> consultarUsuarios();
		public boolean actualizarUsuario(Usuario usuario);
		public boolean eliminarUsuario(Usuario usuario);
		public boolean actualizarUsuarios(String nombrePerfilviejo, String nombrePerfilNuevo);
		public Usuario consultarPorEmail(String email);

}
