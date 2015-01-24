package freeart;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the image database table.
 * 
 */
@Entity
@NamedQuery(name="Image.findAll", query="SELECT i FROM Image i")
public class Image implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int imageId;

	private int cataId;

	@Lob
	private byte[] contenu;

	public Image() {
	}

	public int getImageId() {
		return this.imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public int getCataId() {
		return this.cataId;
	}

	public void setCataId(int cataId) {
		this.cataId = cataId;
	}

	public byte[] getContenu() {
		return this.contenu;
	}

	public void setContenu(byte[] contenu) {
		this.contenu = contenu;
	}

}