package freeart;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class BioServlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// ici vous utilisez �videmment votre outil de persistance
	// ces deux attributs sont mes EJB � moi qui g�re ma persistance JPA
	@EJB
	private FacadeUser facadeUser;
	@EJB
	private FacadeCatalogue facadeCatalogue;

	/**
	 * R�ponse aux requ�tes de type GET, inutile dans ce TP.
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.println("<h2>Bienvenue dans FreeArt !</h2>");
	}

	/**
	 * R�ponse aux requ�tes de type POST, � modifier, compl�ter, etc.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		String action = request.getParameter("action");

		if (action.equals("lstcat")) {
			out.println(getHTMLUsers());
		}
		/*
		if (action.equals("searchid")) {
			out.println(getHTMLProduit(request.getParameter("idproduit")));
		}*/
	}

	/**
	 * M�thode renvoyent une cha�ne de caract�res au format HTML
	 * contenant la liste des cat�gories disponibles dans la base
	 * @return une cha�ne au format HTML
	 */
	private String getHTMLUsers() {
		String html = "<ul>";
		// ici vous utilisez �videmment votre outil de persistance
		List<User> l = facadeUser.findAll();
		for (Categorie s : l) {
			html += "<li>" + s.getNom() + "</li>";
		}
		html += "</ul>";
		return html;
	}

	/**
	 * M�thode renvoyant une cha�ne de caract�res au format HTML 
	 * d�crivant l'objet d'identifiant id dans la base, et la cha�ne
	 * "Aucun produit ne correspond � cet identifiant." s'il n'est pas
	 * trouv�.
	 * @return une cha�ne au format HTML
	 */
	private String getHTMLProduit(String id) {
		String html = "";
		Produit p = null;
		try {
			// ici vous utilisez �videmment votre outil de persistance
			p = (Produit) facadeProduit.find(Integer.parseInt(id));
		} catch (NumberFormatException ex) {
			p = null;
		}
		if (p != null) {
			html += "<p>" + p.getNom() + "</p>";
			html += "<ul>";
			html += "<li> Cat�gorie : " + p.getCategorie() + "</li>";
			html += "<li> Prix : &euro; " + p.getPrix() + " ("
					+ p.getDescription() + ")</li>";
			html += "</ul>";
		} else {
			html += "<p>Aucun produit ne correspond � cet identifiant.</p>";
		}
		return html;
	}

}
