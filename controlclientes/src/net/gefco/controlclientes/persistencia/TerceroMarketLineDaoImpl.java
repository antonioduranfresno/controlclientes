package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.TerceroMarketLine;

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
public class TerceroMarketLineDaoImpl implements TerceroMarketLineDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void guardar(TerceroMarketLine terceroMarketLine) {
		getSession().save(terceroMarketLine);	
	}

	@Override
	public void actualizar(TerceroMarketLine terceroMarketLine) {
		getSession().update(terceroMarketLine);
	}

	@Override
	public void eliminar(TerceroMarketLine terceroMarketLine) {
		getSession().delete(terceroMarketLine);
	}
	
	@Override
	public TerceroMarketLine buscarId(Integer id) {
		
		Criteria crit = getSession().createCriteria(TerceroMarketLine.class);
		
		crit.add(Restrictions.eq("id", id));
		
		return (TerceroMarketLine) crit.uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TerceroMarketLine> listado() {		
		
		Query query = getSession().createQuery("from TerceroMarketLine");
				
		return query.list();
	}
	@Override
	public Long totalRegistros (String hql){
		int posicionFrom = hql.indexOf(" from ");
		String hql2 = "select count(*)" + hql.substring(posicionFrom);
		return (Long) getSession().createQuery(hql2).uniqueResult();
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> listadoClaseCustom(String hql){
		
		Query query = getSession().createQuery(hql);
		
		return query.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> listadoClaseCustomOrdenado(String hql, String campoOrden){
		String orden = "";
		if (campoOrden.equals("")) {
			orden = "";
			
		} else {
			orden = " order by " + campoOrden;
			
		}
		
		Query query = getSession().createQuery(hql + orden);
		
		return query.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> listadoClaseCustomPaginado(String hql, Integer primero, Integer maximo, String campoOrden) {
		String orden = "";
		if (campoOrden.equals("")) {
			orden = "";
			
		} else {
			orden = " order by " + campoOrden;
			
		}
		
		Query query = getSession().createQuery(hql + orden).setFirstResult(primero).setMaxResults(maximo);
				
		return query.list();
	}

}
