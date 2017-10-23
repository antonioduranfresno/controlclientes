package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.SuiviEs;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class SuiviEsDaoImpl implements SuiviEsDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void guardar(SuiviEs suiviEs) {
		getSession().save(suiviEs);	
	}

	@Override
	public void actualizar(SuiviEs suiviEs) {
		getSession().update(suiviEs);
	}

	@Override
	public void eliminar(SuiviEs suiviEs) {
		getSession().delete(suiviEs);
	}

	@Override
	public SuiviEs buscarId(Integer id) {
		
		Criteria crit = getSession().createCriteria(SuiviEs.class);
		
		crit.add(Restrictions.eq("id", id));
		
		return (SuiviEs) crit.uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<SuiviEs> listado() {		
		
		Query query = getSession().createQuery("from SuiviEs");
				
		return query.list();
	}
		
	@Override
	@SuppressWarnings("unchecked")
	public List<SuiviEs> listadoOrdenado(String campoOrden) {		
		
		if (campoOrden.equals("")) {
			return listado();
		} else {
			Query query = getSession().createQuery("from SuiviEs order by "+campoOrden);
		
			return query.list();
		}
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<SuiviEs> listadoPaginado(Integer primero, Integer maximo, String campoOrden) {
		String orden = "";
		if (campoOrden.equals("")) {
			orden = "";
		} else {
			orden = " order by " + campoOrden;
		}
		Query query = getSession().createQuery("from SuiviEs" + orden).setFirstResult(primero).setMaxResults(maximo);
				
		return query.list();
	}
	
}
