package com.tikal.fixe.dao;

import com.tikal.fixe.modelo.Compra;

public interface CompraDAO {

	Compra get(String id);
	
	void save(Compra compra);
	
}
