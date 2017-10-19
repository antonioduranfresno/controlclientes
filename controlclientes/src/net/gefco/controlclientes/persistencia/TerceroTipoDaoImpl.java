package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.TerceroTipo;

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
public class TerceroTipoDaoImpl implements TerceroTipoDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void guardar(TerceroTipo terceroTipo) {
		getSession().save(terceroTipo);	
	}

	@Override
	public void actualizar(TerceroTipo terceroTipo) {
		getSession().update(terceroTipo);
	}

	@Override
	public void eliminar(TerceroTipo terceroTipo) {
		getSession().delete(terceroTipo);
	}

	@Override
	public TerceroTipo buscarId(Integer id) {
		
		Criteria crit = getSession().createCriteria(TerceroTipo.class);
		
		crit.add(Restrictions.eq("id", id));
		
		return (TerceroTipo) crit.uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TerceroTipo> listado() {		
		
		Query query = getSession().createQuery("from TerceroTipo");
				
		return query.list();
	}
		
	@Override
	@SuppressWarnings("unchecked")
	public List<TerceroTipo> listadoOrdenado(String campoOrden) {		
		
		if (campoOrden.equals("")) {
			return listado();
		} else {
			Query query = getSession().createQuery("from TerceroTipo order by "+campoOrden);
		
			return query.list();
		}
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TerceroTipo> listadoPaginado(Integer primero, Integer maximo, String campoOrden) {
		String orden = "";
		if (campoOrden.equals("")) {
			orden = "";
		} else {
			orden = " order by " + campoOrden;
		}
		Query query = getSession().createQuery("from TerceroTipo" + orden).setFirstResult(primero).setMaxResults(maximo);
				
		return query.list();
	}
		
}
