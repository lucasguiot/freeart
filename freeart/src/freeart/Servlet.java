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

//import model.Catalogue;
//import model.Produit;
//import ejb.FacadeCategorie;
//import ejb.FacadeProduit;

/**
 * Servlet implementation class BioServlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// ici vous utilisez évidemment votre outil de persistance
	// ces deux attributs sont mes EJB à moi qui gère ma persistance JPA
	@EJB
	private FacadeCatalogue facadeCatalogue;
	@EJB
	private FacadeUser facadeUser;

	/**
	 * Réponse aux requêtes de type GET, inutile dans ce TP.
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.println("<h2>FreeArt MOTHER FUCKER YEAH !</h2>");
	}
 
	/**
	 * Réponse aux requêtes de type POST, à modifier, compléter, etc.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		String action = request.getParameter("action");
 
		if (action.equals("search")) {
			out.println(getHTMLCatalogue(request.getParameter("motsCles")));
		}
		else if(action.equals("inscription"))
		{
			setHTMLUser(request.getParameter("login"),request.getParameter("mdp"));
		}
		
		
//		if (action.equals("searchid")) {
//			out.println(getHTMLUser(request.getParameter("idproduit")));
//		}
	}
    private void setHTMLUser(String login, String mdp)
    {
    	mdp = User.encryptPassword(mdp);
    	facadeUser.setUser();
    }
	/**
	 * Méthode renvoyent une chaîne de caractères au format HTML
	 * contenant la liste des catégories disponibles dans la base
	 * @return une chaîne au format HTML
	 */
	private String getHTMLCatalogue(String motsCles) {
		String html = "<ul>";
		// ici vous utilisez évidemment votre outil de persistance
		List<Catalogue> l = facadeCatalogue.findCatalogue(motsCles);
		for (Catalogue s : l) {
			html += "<li>" + s.getNom() + "</li>";
		}
		html += "</ul>";
		return html;
	}

	/**
	 * Méthode renvoyant une chaîne de caractères au format HTML 
	 * décrivant l'objet d'identifiant id dans la base, et la chaîne
	 * "Aucun produit ne correspond à cet identifiant." s'il n'est pas
	 * trouvé.
	 * @return une chaîne au format HTML
	 */
//	private String getHTMLProduit(String id) {
//		String html = "";
//		Produit p = null;
//		try {
//			// ici vous utilisez évidemment votre outil de persistance
//			p = (Produit) facadeProduit.find(Integer.parseInt(id));
//		} catch (NumberFormatException ex) {
//			p = null;
//		}
//		if (p != null) {
//			html += "<p>" + p.getNom() + "</p>";
//			html += "<ul>";
//			html += "<li> Catégorie : " + p.getCategorie() + "</li>";
//			html += "<li> Prix : &euro; " + p.getPrix() + " ("
//					+ p.getDescription() + ")</li>";
//			html += "</ul>";
//		} else {
//			html += "<p>Aucun produit ne correspond à cet identifiant.</p>";
//		}
//		return html;
//	}

}
