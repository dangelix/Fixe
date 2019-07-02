package com.tikal.kiosko.dao.imp;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Date;
import java.util.List;

import com.tikal.kiosko.dao.CompraDAO;
import com.tikal.kiosko.modelo.Compra;

public class CompraDAOImp implements CompraDAO {

	@Override
	public Compra get(String id) {
		Compra c=ofy().load().type(Compra.class).id(id).now();
		return c;
	}

	@Override
	public void save(Compra compra) {
		ofy().save().entity(compra).now();
	}

}
