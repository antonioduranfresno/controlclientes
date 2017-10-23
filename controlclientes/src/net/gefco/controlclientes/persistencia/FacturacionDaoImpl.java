package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.Facturacion;

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
	@SuppressWarnings("unchecked")
	public List<Facturacion> listado() {		
		
		Query query = getSession().createQuery("from Facturacion");
				
		return query.list();
	}
		
	@Override
	@SuppressWarnings("unchecked")
	public List<Facturacion> listadoOrdenado(String campoOrden) {		
		
		if (campoOrden.equals("")) {
			return listado();
		} else {
			Query query = getSession().createQuery("from Facturacion order by "+campoOrden);
		
			return query.list();
		}
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Facturacion> listadoPaginado(Integer primero, Integer maximo, String campoOrden) {
		String orden = "";
		if (campoOrden.equals("")) {
			orden = "";
		} else {
			orden = " order by " + campoOrden;
		}
		Query query = getSession().createQuery("from Facturacion" + orden).setFirstResult(primero).setMaxResults(maximo);
				
		return query.list();
	}
	
}
