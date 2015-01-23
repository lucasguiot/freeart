package freeart;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

public abstract class FacadeAbstraite<T> {
	// attribut typant la facade : c'est la classe de l'objet métier
	private Class classeEntite;

	/**
	 * Constructeur
	 * 
	 * @param classeEntite
	 *            la classe de l'objet métier
	 */
	public FacadeAbstraite(Class classeEntite) {
		this.classeEntite = classeEntite;
	}

	/**
	 * Méthode abstraite à définir dans chaque sous-classe qui renvoie
	 * l'EntityManager correspondant à la classe.
	 * 
	 * @return l'entity manager
	 */
	protected abstract EntityManager getEntityManager();

	/**
	 * Méthode de création d'un objet (ajout dans la base).
	 * 
	 * @param entite
	 */
	public void create(T entite) {
		getEntityManager().persist(entite);
	}

	/**
	 * Méthode de modification d'un objet.
	 * 
	 * @param entite
	 */
	public void edit(T entite) {
		getEntityManager().merge(entite);
	}

	/**
	 * Méthode de suppression d'un objet.
	 * 
	 * @param entite
	 */
	public void remove(T entite) {
		getEntityManager().remove(getEntityManager().merge(entite));
	}

	/**
	 * Méthode de recherche d'un objet à partir de son identifiant.
	 * 
	 * @param id
	 * @return
	 */
//	public T find(Object id) {
//		return (T) getEntityManager().find(classeEntite, id);
//	} 

	public List<Catalogue> find(String motsCles) {
		 
		String[] tab = motsCles.split(" ");
		String select = "select distinct * from catalogue ";
		String where = " where ";
		String mot = " motsCles like '%";
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
	
		return getEntityManager().createQuery(query).getResultList();
		/*
		Catalogue c = new Catalogue();
		c.setNom("younes");
		ArrayList<Catalogue> list =  new ArrayList<Catalogue>();
		list.add(c);*/
		//return list;
		}
	
	
	/** 
	 * Méthode recherchant tous les objets de ce type.
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
	 * Méthode renvoyant les n objets de ce type compris dans l'intervalle passé
	 * en paramètre (utile pour la pagination des résultats).
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
	 * Méthode renvoyant le nombre d'objet de ce type.
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