package control.ordine;

import model.CarrelloModel;
import model.acquisto.AcquistoBean;
import model.acquisto.AcquistoDAO;
import model.ordine.OrdineBean;
import model.ordine.OrdineDAO;
import model.prodotto.ProdottoOrdine;
import model.utente.UtenteBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/user/Checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//Forward a checkout.jsp
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Se il carrello � vuoto, redirect a carrello.jsp
		//Altrimenti, forward a checkout.jsp
		CarrelloModel carrelloModel = (CarrelloModel) req.getSession().getAttribute("carrello");
		if (carrelloModel == null || carrelloModel.getCarrello().isEmpty()) {
			resp.sendRedirect(req.getContextPath() + "/pages/carrello.jsp");
		} else {	
			req.getRequestDispatcher("/pages/views/checkout.jsp").forward(req, resp);
		}
	}
	
	//Esecuzione acquisto e salvataggio ordine nel database
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Recupera carrello e utente dalla sessione, data-consegna e prezzo-totale parametri di input nascosti generati da checkout.jsp
    	CarrelloModel carrelloModel = (CarrelloModel) req.getSession().getAttribute("carrello");
    	if (carrelloModel == null || carrelloModel.getCarrello().isEmpty()) {
    		resp.sendRedirect(req.getContextPath() + "/");
    		return;
    	}
        LocalDate dataConsegna = LocalDate.parse(req.getParameter("data-consegna"));
        UtenteBean utenteBean = (UtenteBean) req.getSession().getAttribute("utente");
        BigDecimal prezzoTot = new BigDecimal(req.getParameter("prezzo-totale"));

        OrdineBean ordineBean = new OrdineBean();
        ordineBean.setUsername(utenteBean.getUsername());
        ordineBean.setPrezzoTotale(prezzoTot);
        ordineBean.setDataConsegna(dataConsegna);
        ordineBean.setDataOrdine(LocalDate.now());
        //Indirizzo di consegna con destinatario viene compilato ad ogni ordine eseguito dall'utente
        //Perch� l'utente potrebbe ordinare ogni volta ad un indirizzo di spedizione diverso
        ordineBean.setNomeConsegna(req.getParameter("nome-spedizione"));
        ordineBean.setCognomeConsegna(req.getParameter("cognome-spedizione"));
        ordineBean.setCap(req.getParameter("cap-spedizione"));
        ordineBean.setVia(req.getParameter("via-spedizione"));
        ordineBean.setCitta(req.getParameter("citta-spedizione"));
        OrdineDAO ordineDAO = new OrdineDAO();
    	
        try {
	        //Salva l'ordine nel DB
	        ordineDAO.doSave(ordineBean);
	
	        for (ProdottoOrdine p: carrelloModel.getCarrello()) {
		    	//Inserisci in acquisto per ogni prodotto acqusitato l'id dell'ordine relativo, il prezzo e la quantit�
		        AcquistoBean acquistoBean = new AcquistoBean();
		        acquistoBean.setIDOrdine(new OrdineDAO().getMaxID() - 1);
		        acquistoBean.setIDProdotto(p.getProdottoBean().getID());
		        acquistoBean.setQuantita(p.getQuantita());
		        acquistoBean.setImmagine(p.getProdottoBean().getGrafica());
		        acquistoBean.setPrezzoAq(p.getProdottoBean().getPrezzo());
		        acquistoBean.setIvaAq(p.getProdottoBean().getIVA());
		
		        AcquistoDAO acquistoDAO = new AcquistoDAO();
		        acquistoDAO.doSave(acquistoBean);
	        }
        } catch (SQLException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Problema relativo al database");
			e.printStackTrace();
			return;
		}
        
        //Svuota il carrello
        req.getSession().removeAttribute("carrello");
        //Reindirizza ad una pagina di conferma di buon fine dell'acquisto
        resp.sendRedirect(req.getContextPath() + "/pages/acquisto.jsp");
    }
}
