package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.ComercialTipo;

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
public class ComercialTipoDaoImpl implements ComercialTipoDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void guardar(ComercialTipo comercialTipo) {
		getSession().save(comercialTipo);	
	}

	@Override
	public void actualizar(ComercialTipo comercialTipo) {
		getSession().update(comercialTipo);
	}

	@Override
	public void eliminar(ComercialTipo comercialTipo) {
		getSession().delete(comercialTipo);
	}

	@Override
	public ComercialTipo buscarId(Integer id) {
		
		Criteria crit = getSession().createCriteria(ComercialTipo.class);
		
		crit.add(Restrictions.eq("id", id));
		
		return (ComercialTipo) crit.uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ComercialTipo> listado() {		
		
		Query query = getSession().createQuery("from ComercialTipo");
				
		return query.list();
	}
		
	@Override
	@SuppressWarnings("unchecked")
	public List<ComercialTipo> listadoOrdenado(String campoOrden) {		
		
		if (campoOrden.equals("")) {
			return listado();
		} else {
			Query query = getSession().createQuery("from ComercialTipo order by "+campoOrden);
		
			return query.list();
		}
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ComercialTipo> listadoPaginado(Integer primero, Integer maximo, String campoOrden) {
		String orden = "";
		if (campoOrden.equals("")) {
			orden = "";
		} else {
			orden = " order by " + campoOrden;
		}
		Query query = getSession().createQuery("from ComercialTipo" + orden).setFirstResult(primero).setMaxResults(maximo);
				
		return query.list();
	}
		
}
