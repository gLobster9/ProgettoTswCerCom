package control.prodotto;

//import java.io.File;
import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.prodotto.ProdottoDAO;

@WebServlet("/admin/DeleteProdotto")
public class DeleteProdotto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //final String PATH =  req.getServletContext().getInitParameter("WEBCONTENT_PATH") + File.separator + "images" +
		//       File.separator + "grafiche" + File.separator;

        int ID = Integer.parseInt(req.getParameter("ID"));

        ProdottoDAO prodottoDAO = new ProdottoDAO();

	    try {
			if (!prodottoDAO.deleteProdotto(ID))
			    System.out.println("Errore nel deleteProdotto");
		} catch (SQLException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problema relativo al database");
			e.printStackTrace();
			return;
		}
        
    /* Cancella anche l'immagine della grafica dalla cartella
        
        File f = new File(PATH);
        String[] matching = f.list();
        if (matching != null) {
           for (String s : matching) {
                if (s.startsWith(ID + tipo))
                    Files.delete(Path.of(PATH + s));
           }
       } 
   */
        
	    resp.sendRedirect(req.getContextPath() + "/Catalogo");
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
