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
	
	@Override
	public Tercero buscarCodigo(String codigo) {
		
		Criteria crit = getSession().createCriteria(Tercero.class);
		
		crit.add(Restrictions.eq("terc_codigo", codigo));
		
		return (Tercero) crit.uniqueResult();
	}
}