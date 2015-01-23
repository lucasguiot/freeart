package freeart;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

public abstract class FacadeAbstraite<T> {
	// attribut typant la facade : c'est la classe de l'objet m�tier
	private Class classeEntite;

	/**
	 * Constructeur
	 * 
	 * @param classeEntite
	 *            la classe de l'objet m�tier
	 */
	public FacadeAbstraite(Class classeEntite) {
		this.classeEntite = classeEntite;
	}

	/**
	 * M�thode abstraite � d�finir dans chaque sous-classe qui renvoie
	 * l'EntityManager correspondant � la classe.
	 * 
	 * @return l'entity manager
	 */
	protected abstract EntityManager getEntityManager();

	/**
	 * M�thode de cr�ation d'un objet (ajout dans la base).
	 * 
	 * @param entite
	 */
	public void create(T entite) {
		getEntityManager().persist(entite);
	}

	/**
	 * M�thode de modification d'un objet.
	 * 
	 * @param entite
	 */
	public void edit(T entite) {
		getEntityManager().merge(entite);
	}

	/**
	 * M�thode de suppression d'un objet.
	 * 
	 * @param entite
	 */
	public void remove(T entite) {
		getEntityManager().remove(getEntityManager().merge(entite));
	}

	/**
	 * M�thode de recherche d'un objet � partir de son identifiant.
	 * 
	 * @param id
	 * @return
	 */
//	public T find(Object id) {
//		return (T) getEntityManager().find(classeEntite, id);
//	} 

	public List<Catalogue> find(String motsCles) {
		 
		String[] tab = motsCles.split(" ");
		String select = "select distinct c.* from catalogue c ";
		String where = " where ";
		String mot = " c.motsCles like '%";
		String end = "%'";
		String or = " or ";
		String query = select + where + mot + motsCles + end ;
		
		
		for(int i=0; i<tab.length;i++)
		{
			if (i==0)
			{
				query += " union " + select+where+mot +tab[i]+end;
			}
			else
			{
				query += or +mot+ tab[i]+end; 
			}			
		}
		List<Object[]> list = (getEntityManager().createNativeQuery(query).getResultList());
		ArrayList<Catalogue> res = new ArrayList<Catalogue>();
		for(Object[] o : list)
		{
			res.add(new Catalogue((int)o[0], (String)o[1], (String)o[2]));
		}
		return res;
		
		/*
		Catalogue c = new Catalogue();
		c.setNom("younes");
		ArrayList<Catalogue> list =  new ArrayList<Catalogue>();
		list.add(c);*/
		//return list;
		}
	
	
	/** 
	 * M�thode recherchant tous les objets de ce type.
	 * 
	 * @return
	 */
	public List findAll() {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
				.getCriteriaBuilder().createQuery();
		cq.select(cq.from(classeEntite));
		return getEntityManager().createQuery(cq).getResultList();
	}

	/**
	 * M�thode renvoyant les n objets de ce type compris dans l'intervalle pass�
	 * en param�tre (utile pour la pagination des r�sultats).
	 * 
	 * @param etendue
	 * @return
	 */
	public List findRange(int[] etendue) {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
				.getCriteriaBuilder().createQuery();
		cq.select(cq.from(classeEntite));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		q.setMaxResults(etendue[1] - etendue[0]);
		q.setFirstResult(etendue[0]);
		return q.getResultList();
	}

	/**
	 * M�thode renvoyant le nombre d'objet de ce type.
	 * 
	 * @return
	 */
	public int count() {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
				.getCriteriaBuilder().createQuery();
		javax.persistence.criteria.Root rt = cq.from(classeEntite);
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}
}