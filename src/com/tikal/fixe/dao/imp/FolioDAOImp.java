package com.tikal.kiosko.dao.imp;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.tikal.kiosko.dao.FolioDAO;
import com.tikal.kiosko.modelo.Folio;

public class FolioDAOImp implements FolioDAO{

	@Override
	public void crear() {
		
		List<Folio> l=ofy().load().type(Folio.class).list();
		if(l.size()==0){
			Folio f = new Folio();
			f.setFolio(1);
			ofy().save().entity(f).now();
		}
	}

	@Override
	public int getFolio() {
		List<Folio> l=ofy().load().type(Folio.class).list();
		return l.get(0).getFolio();
	}

	@Override
	public void incrementa() {
		List<Folio> l=ofy().load().type(Folio.class).list();
		Folio f= l.get(0);
		f.setFolio(f.getFolio()+1);
		ofy().save().entity(f).now();
	}

}
