//package control.prodotto;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.math.BigDecimal;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.Part;
//
//import model.prodotto.ProdottoBean;
//import model.prodotto.ProdottoDAO;
//
//@WebServlet("/admin/UpdateProdotto")
//@MultipartConfig
//public class UpdateProdotto extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        final String PATH = req.getServletContext().getInitParameter("WEBCONTENT_PATH") + File.separator;
//        
//        int ID = Integer.parseInt(req.getParameter("id"));
//        String nome  = req.getParameter("nome");
//        BigDecimal prezzo = new BigDecimal(req.getParameter("prezzo"));
//        int IVA = (int) Float.parseFloat(req.getParameter("IVA"));
//        String descrizione = req.getParameter("descrizione");
//        String pathGrafica = req.getParameter("path");
//        Part grafica = req.getPart("grafica");
//        int quantita = Integer.parseInt(req.getParameter("quantita"));
//        
//        String tipo = req.getParameter("tipo");
//        if (tipo == null)
//            tipo = req.getParameter("tipoVecchio");
//
//        ProdottoDAO prodottoDAO = new ProdottoDAO();
//
//        if (!grafica.getSubmittedFileName().equals("")) {
//            //Aggiunta nuova grafica
//            grafica = req.getPart("grafica");
//            String nomeFile;
//            int extensionIndex = grafica.getSubmittedFileName().lastIndexOf(".");
//
//            nomeFile = ID + tipo + grafica.getSubmittedFileName().substring(extensionIndex);
//
//            pathGrafica = nomeFile;
//
//            //Eliminazione vecchia grafica
//            File f = new File(PATH);
//            String[] matching = f.list();
//            if (matching != null) {
//                for (String s : matching) {
//                    if (s.startsWith(ID + tipo))
//                        Files.delete(Path.of(PATH + s));
//                }
//            }
//
//            try (OutputStream outputStream = new FileOutputStream(PATH + nomeFile); InputStream inputStream = grafica.getInputStream()) {
//                inputStream.transferTo(outputStream);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        ProdottoBean prodotto = new ProdottoBean();
//        prodotto.setID(ID);
//        prodotto.setNome(nome);
//        prodotto.setPrezzo(prezzo);
//        prodotto.setIVA(IVA);
//        prodotto.setTipo(tipo);
//        prodotto.setDescrizione(descrizione);
//        prodotto.setGrafica(pathGrafica);
//        prodotto.setQuantitaInStock(quantita);
//
//        prodottoDAO.doUpdate(prodotto);
//
//        resp.sendRedirect(req.getContextPath() + "/Catalogo");
//    }
//
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		doPost(req, resp);
//	}
//}
