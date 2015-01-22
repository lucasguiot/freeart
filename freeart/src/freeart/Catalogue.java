package freeart;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


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

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="catalogue")
	private List<User> users;

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

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setCatalogue(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setCatalogue(null);

		return user;
	}

}