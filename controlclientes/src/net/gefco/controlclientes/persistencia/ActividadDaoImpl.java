package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.Actividad;

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
public class ActividadDaoImpl implements ActividadDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void guardar(Actividad actividad) {
		getSession().save(actividad);	
	}

	@Override
	public void actualizar(Actividad actividad) {
		getSession().update(actividad);
	}

	@Override
	public void eliminar(Actividad actividad) {
		getSession().delete(actividad);
	}

	@Override
	public Actividad buscarId(Integer id) {
		
		Criteria crit = getSession().createCriteria(Actividad.class);
		
		crit.add(Restrictions.eq("id", id));
		
		return (Actividad) crit.uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Actividad> listado() {		
		
		Query query = getSession().createQuery("from Actividad");
				
		return query.list();
	}
		
	@Override
	@SuppressWarnings("unchecked")
	public List<Actividad> listadoOrdenado(String campoOrden) {		
		
		if (campoOrden.equals("")) {
			return listado();
		} else {
			Query query = getSession().createQuery("from Actividad order by "+campoOrden);
		
			return query.list();
		}
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Actividad> listadoPaginado(Integer primero, Integer maximo, String campoOrden) {
		String orden = "";
		if (campoOrden.equals("")) {
			orden = "";
		} else {
			orden = " order by " + campoOrden;
		}
		Query query = getSession().createQuery("from Actividad" + orden).setFirstResult(primero).setMaxResults(maximo);
				
		return query.list();
	}

	@Override
	public Actividad buscarCodigo(String codigo) {
		
		Criteria crit = getSession().createCriteria(Actividad.class);
		
		crit.add(Restrictions.eq("acti_codigo", codigo));
		
		return (Actividad) crit.uniqueResult();
	}
	
}
