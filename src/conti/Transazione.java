package conti;


import util.Data;

/**
 * @author Simone
 * Classe astratta transazione
 * astratta perchè transazione non esiste
 * esiste solo deposito o prelievo
 */
public abstract class Transazione {
	
	protected float importo; 
	protected Data data;
	protected String descrizione;
	
	/**
	 * Vengono impostati valori default
	 */
	public Transazione() {
		importo = 0;
		data = new Data();
		//set data
		descrizione = "";
	}
	
	/**
	 * 
	 * @param importo importo della transazione (senza segno)
	 * @param data data della transazione
	 * @param descrizione descrizione della transazione (facoltativa)
	 */
	public Transazione(float importo, Data data,String descrizione) {
		this.importo = Math.abs(importo);
		this.data = data;
		this.descrizione = descrizione;
	}

	/**
	 * 
	 * @return importo della transazione
	 */
	public float getImporto() {
		return importo;
	}
	/**
	 * 
	 * @return data della transazione
	 */
	public Data getData() {
		return data;
	}
	/**
	 * 
	 * @return descrizione della transazione
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * Settaggio importo transazione (senza segno)
	 * @param importo importo transazione (senza segno)
	 */
	public void setImporto(float importo) {
		this.importo = Math.abs(importo);
	}
	/**
	 * Settaggio data transazione
	 * @param data data transazione
	 */
	public void setData(Data data) {
		this.data = data;
	}
	/**
	 * 
	 * @param descrizione descrizione transazione
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public String toString() {
		return "Importo: "+importo+" Data: "+data.toString()+" Descrizione: "+descrizione;
	}
	
}
