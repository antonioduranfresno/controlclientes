package net.gefco.controlclientes.persistencia;

import java.util.List;

import net.gefco.controlclientes.modelo.Tercero;
import net.gefco.controlclientes.modelo.TerceroGrupo;

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
public class TerceroGrupoDaoImpl implements TerceroGrupoDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void guardar(TerceroGrupo terceroGrupo) {
		getSession().save(terceroGrupo);	
	}

	@Override
	public void actualizar(TerceroGrupo terceroGrupo) {
		getSession().update(terceroGrupo);
	}

	@Override
	public void eliminar(TerceroGrupo terceroGrupo) {
		getSession().delete(terceroGrupo);
	}

	@Override
	public TerceroGrupo buscarTerceroGrupo(Integer id) {
		
		Criteria crit = getSession().createCriteria(Tercero.class);
		
		crit.add(Restrictions.eq("id", id));
		
		return (TerceroGrupo) crit.uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TerceroGrupo> listarTercerosGrupo() {		
		
		Query query = getSession().createQuery("from TerceroGrupo");
				
		return query.list();
	}
		
}
