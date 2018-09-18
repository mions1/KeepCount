package conti;


import util.*;

/**
 * @author Simone
 * Sotto classe di Transazione specializzata in Deposito
 * quindi importo positivo
 */
public class Deposito extends Transazione {

	public Deposito() {
		super();
	}
	/**
	 * 
	 * @param importo importo della transazione (senza segno)
	 * @param data data della transazione
	 * @param descrizione descrizione della transazione (facoltativa)
	 */
	public Deposito(float importo,Data data,String descrizione) {
		super(importo,data,descrizione);
	}
	
}
