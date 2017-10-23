package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.Agencia;

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
public class AgenciaDaoImpl implements AgenciaDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void guardar(Agencia agencia) {
		getSession().save(agencia);	
	}

	@Override
	public void actualizar(Agencia agencia) {
		getSession().update(agencia);
	}

	@Override
	public void eliminar(Agencia agencia) {
		getSession().delete(agencia);
	}

	@Override
	public Agencia buscarId(Integer id) {
		
		Criteria crit = getSession().createCriteria(Agencia.class);
		
		crit.add(Restrictions.eq("id", id));
		
		return (Agencia) crit.uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Agencia> listado() {		
		
		Query query = getSession().createQuery("from Agencia");
				
		return query.list();
	}
		
	@Override
	@SuppressWarnings("unchecked")
	public List<Agencia> listadoOrdenado(String campoOrden) {		
		
		if (campoOrden.equals("")) {
			return listado();
		} else {
			Query query = getSession().createQuery("from Agencia order by "+campoOrden);
		
			return query.list();
		}
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Agencia> listadoPaginado(Integer primero, Integer maximo, String campoOrden) {
		String orden = "";
		if (campoOrden.equals("")) {
			orden = "";
		} else {
			orden = " order by " + campoOrden;
		}
		Query query = getSession().createQuery("from Agencia" + orden).setFirstResult(primero).setMaxResults(maximo);
				
		return query.list();
	}
	
}
