package com.tikal.kiosko.dao.imp;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Date;
import java.util.List;

import com.tikal.cacao.dao.FacturaDAO;
import com.tikal.cacao.model.Factura;

public class FacturaDAOImpl implements FacturaDAO {

	@Override
	public void guardar(Factura f) {
		ofy().save().entity(f).now();

	}

	@Override
	public Factura consultar(String uuid) {
		return ofy().load().type(Factura.class).id(uuid).now();
	}

	@Override
	public List<Factura> consutarTodas(String rfcEmisor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Factura> consutarTodas(String rfcEmisor, int page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean eliminar(Factura f) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void eliminar(String uuid) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getPaginas(String rfc) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Factura> buscar(Date fi, Date ff, String rfc) {
		// TODO Auto-generated method stub
		return null;
	}

}
