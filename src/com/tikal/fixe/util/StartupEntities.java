package com.tikal.mensajeria.util;

import org.springframework.stereotype.Component;

import com.googlecode.objectify.ObjectifyService;
import com.tikal.mensajeria.modelo.entity.Empresa;
import com.tikal.mensajeria.modelo.entity.Envio;
import com.tikal.mensajeria.modelo.entity.Paquete;
import com.tikal.mensajeria.modelo.login.Perfil;
import com.tikal.mensajeria.modelo.login.SessionEntity;
import com.tikal.mensajeria.modelo.login.Sucursal;
import com.tikal.mensajeria.modelo.login.Usuario;




@Component
public class StartupEntities  {
	
	
	public StartupEntities() {
		
		
		
		ObjectifyService.register(Perfil.class);
		ObjectifyService.register(SessionEntity.class);
		ObjectifyService.register(Sucursal.class);
		ObjectifyService.register(Usuario.class);
		ObjectifyService.register(Paquete.class);
		ObjectifyService.register(Envio.class);
		ObjectifyService.register(Empresa.class);
		
	}
	  
    
    
}
