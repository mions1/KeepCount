package conti;


import util.Data;


/**
 * @author Simone
 * Sotto classe di Transazione specializzata in Prelievo
 * quindi importo negativo
 */
public class Prelievo extends Transazione {

	public Prelievo() {
		super();
	}
	/**
	 * 
	 * @param importo importo della transazione (senza segno)
	 * @param data data della transazione
	 * @param descrizione descrizione della transazione (facoltativa)
	 */
	public Prelievo(float importo,Data data,String descrizione) {
		super(importo,data,descrizione);
		super.importo = super.importo * -1; //Importo negativo
	}
	
	@Override
	public void setImporto(float importo) {
		super.importo = Math.abs(importo)*-1;
	}
}
