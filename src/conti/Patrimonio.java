package conti;


import gui.*;

import java.util.Vector;

import util.FiltroTransazioni;
import util.Periodo;

/**
 * @author Simone
 * Classe Patrimonio che tiene conto di tutte le transazioni
 * e uscite
 */
public class Patrimonio {

	private Vector<Transazione> transazioni;
	private float totale; //Somma entrate e uscite
	
	/**
	 * Valori di default
	 */
	public Patrimonio() {
		transazioni = new Vector<Transazione>(10);
		totale = 0;
	}

	/**
	 * Restituisce il vettore transazioni
	 * @return vettore transazioni
	 */
	public Vector<Transazione> getTransazioni() {
		return transazioni;
	}
	/**
	 * Restituisce il totale del patrimonio
	 * @return somma algebrica entrate e uscite
	 */
	public float getTotale() {
		return totale;
	}
	
	/**
	 * Resituisce il totale filtrato
	 * p == null per periodo di default (INTERO_TEMPO)
	 * filtro == null per filtro di default ("") (vuoto)
	 * tipoTransazioni == -1 per tipo default (TRANSAZIONI_TUTTO)
	 * @param filtroTransazioni filtro delle transazioni da mostrare
	 * @return totale delle transazioni
	 */
	public float getTotaleFiltrato(FiltroTransazioni filtroTransazioni) {
		Periodo p = filtroTransazioni.getPeriodo();
		String filtro = filtroTransazioni.getFiltro();
		int tipoTransazioni = filtroTransazioni.getTipo();
		float tot = 0;
		
		if ((p == null))
			p = new Periodo(Periodo.INTERO_TEMPO);
		if (filtro == null)
			filtro = "";
		if (tipoTransazioni == -1)
			tipoTransazioni = Tabella.TRANSAZIONI_TUTTO;
		
		for (int i=0; i < transazioni.size(); i++) {
			Transazione tmp = transazioni.elementAt(i);
			if (p.compresaTra(tmp.getData())) {
				String s = tmp.getData().toString();
				s += " "+tmp.getDescrizione();
				s += " "+Float.toString(tmp.getImporto());
				if (s.contains(filtro)) {
					if (tipoTransazioni == Tabella.TRANSAZIONI_DEPOSITI
							&& tmp.getClass().equals(Deposito.class)) 
						tot += tmp.getImporto();
					
					else if (tipoTransazioni == Tabella.TRANSAZIONI_PRELIEVI
							&& tmp.getClass().equals(Prelievo.class))
						tot += tmp.getImporto();
					else if (tipoTransazioni == Tabella.TRANSAZIONI_TUTTO)
						tot += tmp.getImporto();
				}
			}
		}
		return tot;
	}
	
	/**
	 * Restitusce il transazioni filtrate (vedi getTotaleFiltrato)
	 * p == null per periodo default (INTERO_TEMPO)
	 * filtro == null per filtro default ("")(vuoto)
	 * tipoTransazioni == -1 per tipo default (TRANSAZIONI_TUTTO)
	 * @param filtroTransazioni filtro transazioni da mostrare
	 * @return vettore transazioni filtrato
	 */
	public Vector<Transazione> getTransazioniFiltrate(FiltroTransazioni filtroTransazioni) {
		Vector<Transazione> t = new Vector<Transazione>();
		Periodo p = filtroTransazioni.getPeriodo();
		String filtro = filtroTransazioni.getFiltro();
		int tipoTransazioni = filtroTransazioni.getTipo();
		if ((p == null))
			p = new Periodo(Periodo.INTERO_TEMPO);
		if (filtro == null)
			filtro = "";
		if (tipoTransazioni == -1)
			tipoTransazioni = Tabella.TRANSAZIONI_TUTTO;
			
		for (int i=0; i < transazioni.size(); i++) {
			Transazione tmp = transazioni.elementAt(i);
			if (p.compresaTra(tmp.getData())) {
				String s = tmp.getData().toString();
				s += " "+tmp.getDescrizione();
				s += " "+Float.toString(tmp.getImporto());
				if (s.contains(filtro)) {
					if (tipoTransazioni == Tabella.TRANSAZIONI_DEPOSITI
							&& tmp.getClass().equals(Deposito.class))
						t.add(tmp);
					else if (tipoTransazioni == Tabella.TRANSAZIONI_PRELIEVI
							&& tmp.getClass().equals(Prelievo.class))
						t.add(tmp);
					else if (tipoTransazioni == Tabella.TRANSAZIONI_TUTTO)
						t.add(tmp);
				}
			}
		}
		return t;
	}
	
	/**
	* Restituisce il totale delle entrate
	 * @return somma delle entrate
	 */
	public float getTotaleEntrate() {
		float tot = 0;
		for (int i=0;i<transazioni.size();i++) {
			if (transazioni.elementAt(i).getClass().equals(Deposito.class))
			tot += transazioni.elementAt(i).getImporto();
		}
		return tot;
	}
	/**
	 * Restituisce il totale delle uscite (con segno negativo)
	 * @return somma delle uscite (con segno negativo)
	 */
	public float getTotaleUscite() {
		float tot = 0;
		for (int i=0;i<transazioni.size();i++) {
			if (transazioni.elementAt(i).getClass().equals(Prelievo.class))
			tot += transazioni.elementAt(i).getImporto();
		}
		return tot*-1;
		
	}
	/**
	 * Aggiunge una nuova transazione al patrimonio
	 * @param t la nuova transazione,
	 * può essere Deposito o Prelievo
	 */
	public void aggiungiTransazione(Transazione t) {
		if (transazioni.size() == 0) {
			transazioni.addElement(t);
		}
		else if (t.data.compara(transazioni.lastElement().data) > 0) {
			transazioni.add(t);
		}
		else {
			for (int i = 0; i < transazioni.size(); i++)
				if (t.data.compara(transazioni.elementAt(i).data) < 0) {
					this.transazioni.add(i, t);
					break;
				}
		}
		this.totale += t.getImporto();
	}
	/**
	 * Elimina la transazione specificata
	 * @param t transazione da eliminare
	 */
	public void eliminaTransazione(Transazione t) {
		this.totale -= t.importo;
		transazioni.removeElement(t);
	}
	/**
	 * Modifica una transazione
	 * @param new_t Nuova transazione
	 * @param old_t Transazione da modificare
	 */
	public void modificaTransazione(Transazione new_t,Transazione old_t) {
		aggiungiTransazione(new_t);
		eliminaTransazione(old_t);
	}
	//Stampa a video le transazioni
	public void stampaTransazioni() {
		for (int i=0; i < transazioni.size(); i++)
			System.out.println(transazioni.elementAt(i));
	}
	/**
	 * Resetta patrimonio
	 */
	public void svuota() {
		transazioni = new Vector<Transazione>();
		totale = 0;
	}
}
