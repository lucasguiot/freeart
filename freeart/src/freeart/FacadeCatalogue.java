package freeart;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EJB session pour la classe Catalogue
 */
@Stateless
public class FacadeCatalogue extends FacadeAbstraite {
	@PersistenceContext(unitName = "freeart")
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}

	public FacadeCatalogue() {
		super(Catalogue.class);
	}
}