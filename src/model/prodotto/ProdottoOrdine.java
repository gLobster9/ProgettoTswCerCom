package model.prodotto;

import java.math.BigDecimal;

public class ProdottoOrdine {
    private ProdottoBean prodottoBean;
    private int quantita;

    public ProdottoOrdine(ProdottoBean prodottoBean) {
        this.prodottoBean = prodottoBean;
        quantita = 1;
    }

    public ProdottoBean getProdottoBean() {
        return prodottoBean;
    }

    public void setProdottoBean(ProdottoBean prodottoBean) {
        this.prodottoBean = prodottoBean;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public void incrementaQuantita() {
        quantita++;
    }

    public void decrementaQuantita() {
        quantita--;
    }

    public BigDecimal getPrezzoTotale() {
        return prodottoBean.getPrezzo().multiply(BigDecimal.valueOf(quantita));
    }

	public String toString() {
		return "ProdottoOrdine [prodottoBean=" + prodottoBean + ", quantita=" + quantita + "]";
	}
}
