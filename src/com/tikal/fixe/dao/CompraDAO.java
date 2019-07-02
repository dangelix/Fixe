package com.tikal.kiosko.dao;

import com.tikal.kiosko.modelo.Compra;

public interface CompraDAO {

	Compra get(String id);
	
	void save(Compra compra);
	
}
