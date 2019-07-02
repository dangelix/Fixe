package com.tikal.fixe.util;

import com.googlecode.objectify.ObjectifyService;
import com.tikal.cacao.model.Factura;
import com.tikal.cacao.model.Imagen;
import com.tikal.fixe.modelo.Compra;
import com.tikal.fixe.modelo.Folio;
import com.tikal.fixe.modelo.RegistroBitacora;

public class EntityRegister {
	public EntityRegister() {
		registrar(Compra.class);
		registrar(Factura.class);
		registrar(Imagen.class);
		registrar(Folio.class);
		registrar(RegistroBitacora.class);
	}
	
	private <T> void registrar(Class<T> clase) {
		ObjectifyService.register(clase);
	}
}
