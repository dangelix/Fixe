package com.tikal.mensajeria.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tikal.mensajeria.dao.SessionDao;
import com.tikal.mensajeria.modelo.login.SessionEntity;
import com.tikal.mensajeria.modelo.login.Usuario;

@Service("sessionDao")

public class SessionDaoImpl implements SessionDao {
	
	 public void save(SessionEntity s) {    
		 System.out.println("DAOsession.id:"+s.getIdSession());
		 System.out.println("DAOsession.username:"+s.getNameUser());
	        ofy().save().entity(s).now();
	    }

	    
	    public void delete(SessionEntity s) {
	    	 System.out.println("si esta en daoimpl session eliminando"+s);
	        ofy().delete().entity(s).now();
	        System.out.println("eliminando...");
	    }

	   @Override
		public void update(SessionEntity s) {
		   System.out.print("session:"+s.getIdSession());
		SessionEntity old = this.consult(s.getId());
		System.out.print("old:"+old);
			if (old != null) {
				old.setEstatus(s.getEstatus());
				old.setNameUser(old.getNameUser());
					
			}

				ofy().save().entity(old);
	   }

	    
	   @Override
		public SessionEntity consult(String id) {
		   System.out.println("si esta en daoimpl consultando la Session.."+id);
	      return ofy().load().type(SessionEntity.class).id(id).now();
			
		}


	@Override
	public SessionEntity getByName(String nameUser) {
		// TODO Auto-generated method stub
		 System.out.println("si esta en daoimpl consultando la Session con el username.."+nameUser);
		 List<SessionEntity> s = ofy().load().type(SessionEntity.class).filter("nameUser", nameUser).list();
			System.out.println("lista de usuarios con ese username:"+nameUser);
			if (s.size() == 0) {
				return null;
			}
			SessionEntity nuevo = s.get(0);
			return nuevo;

	}



}
