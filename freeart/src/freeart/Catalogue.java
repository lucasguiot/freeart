package freeart;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the catalogue database table.
 * 
 */
@Entity
@NamedQuery(name="Catalogue.findAll", query="SELECT c FROM Catalogue c")
public class Catalogue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int cataId;

	private String motsCles;

	private String nom;

	private int userId;

	public Catalogue() {
	}

	public int getCataId() {
		return this.cataId;
	}

	public void setCataId(int cataId) {
		this.cataId = cataId;
	}

	public String getMotsCles() {
		return this.motsCles;
	}

	public void setMotsCles(String motsCles) {
		this.motsCles = motsCles;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}