package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.Actividad;
import net.gefco.controlclientes.modelo.Agencia;
import net.gefco.controlclientes.modelo.Facturacion;
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
public class FacturacionDaoImpl implements FacturacionDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void guardar(Facturacion tercero) {
		getSession().save(tercero);	
	}

	@Override
	public void actualizar(Facturacion tercero) {
		getSession().update(tercero);
	}

	@Override
	public void eliminar(Facturacion tercero) {
		getSession().delete(tercero);
	}

	@Override
	public Facturacion buscarId(Integer id) {
		
		Criteria crit = getSession().createCriteria(Facturacion.class);
		
		crit.add(Restrictions.eq("id", id));
		
		return (Facturacion) crit.uniqueResult();
	}
	
	@Override
	public Facturacion buscarFacturacion(Integer periodo, Tercero tercero, Agencia agencia, Actividad actividad){
		
		Criteria crit = getSession().createCriteria(Facturacion.class);
		
		crit.add(Restrictions.eq("fact_periodo", periodo));
		crit.add(Restrictions.eq("tercero", tercero));
		crit.add(Restrictions.eq("agencia", agencia));
		crit.add(Restrictions.eq("actividad", actividad));
				
		return (Facturacion) crit.uniqueResult();		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Facturacion> listado() {		
		
		Query query = getSession().createQuery("from Facturacion");
				
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