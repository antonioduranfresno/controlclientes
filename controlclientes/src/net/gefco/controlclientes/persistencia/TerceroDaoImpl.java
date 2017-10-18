package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.Tercero;

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
public class TerceroDaoImpl implements TerceroDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void guardar(Tercero tercero) {
		getSession().save(tercero);	
	}

	@Override
	public void actualizar(Tercero tercero) {
		getSession().update(tercero);
	}

	@Override
	public void eliminar(Tercero tercero) {
		getSession().delete(tercero);
	}

	@Override
	public Tercero buscarId(Integer id) {
		
		Criteria crit = getSession().createCriteria(Tercero.class);
		
		crit.add(Restrictions.eq("id", id));
		
		return (Tercero) crit.uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Tercero> listado() {		
		
		Query query = getSession().createQuery("from Tercero");
				
		return query.list();
	}
		
	@Override
	@SuppressWarnings("unchecked")
	public List<Tercero> listadoOrdenado(String campoOrden) {		
		
		if (campoOrden.equals("")) {
			return listado();
		} else {
			Query query = getSession().createQuery("from Tercero order by "+campoOrden);
		
			return query.list();
		}
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Tercero> listadoPaginado(Integer primero, Integer maximo, String campoOrden) {
		String orden = "";
		if (campoOrden.equals("")) {
			orden = "";
		} else {
			orden = " order by " + campoOrden;
		}
		Query query = getSession().createQuery("from Tercero" + orden).setFirstResult(primero).setMaxResults(maximo);
				
		return query.list();
	}
	
}
