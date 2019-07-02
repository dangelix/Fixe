package com.tikal.mensajeria.modelo.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Cliente {
	@Id Long id;
	private Nombre cliente;
	private Direccion origen;
	private String telefono;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Nombre getCliente() {
		return cliente;
	}
	public void setCliente(Nombre cliente) {
		this.cliente = cliente;
	}
	public Direccion getOrigen() {
		return origen;
	}
	public void setOrigen(Direccion origen) {
		this.origen = origen;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	
}
