package util;


import java.util.Date;

/**
 * Periodo di date, formato da una data di inizio e una di fine
 * @author Simone
 *
 */
public class Periodo {
	
	//Costanti per definire periodi default
	public static final int MESE = 1001; //Questo mese
	public static final int ANNO = 1002; //Questo anno
	public static final int SETTIMANA = 1003; //Questa Settimana
	public static final int OGGI = 1004; //Oggi
	public static final int INTERO_TEMPO = 1005; //Di sempre
	
	private Data inizio;
	private Data fine;
	
	/**
	 * Costruisco un periodo specificando le date di inizio e fine
	 * @param inizio inizio periodo
	 * @param fine fine periodo
	 */
	public Periodo(Data inizio, Data fine) {
		this.inizio = inizio;
		this.fine = fine;
		//Se inizio è > di fine allora li scambio
		if (inizio.compara(fine) > 1) {
			this.inizio = fine;
			this.fine = inizio;
		}
	}
	/**
	 * Creazione periodo default
	 * @param PERIODO costanti
	 */
	public Periodo(int PERIODO) {
		inizio = new Data();
		fine = new Data();
		switch (PERIODO) {
		case MESE:
			inizio.setGiorno(1);
			fine = new Data(inizio.getUltimoGiornoDelMese(),
					inizio.getMese(),inizio.getAnno());
			break;
		case ANNO:
			inizio.setGiorno(1);
			inizio.setMese(1);
			fine.setGiorno(31);
			fine.setMese(12);
			break;
		case OGGI:
			break;
		case INTERO_TEMPO:
			inizio = null;
			fine = null;
			break;
		case SETTIMANA:
			inizio.setGiorno(inizio.getPrimoGiornoSettimana());
			fine.setGiorno(fine.getPrimoGiornoSettimana()+6);
			break;
		default:
			inizio = null;
			fine = null;
			break;
		}
	}
	
	/**
	 * 
	 * @return data inizio periodo
	 */
	public Data getInizio() {
		return inizio;
	}
	
	/**
	 * 
	 * @return data fine periodo
	 */
	public Data getFine() {
		return fine;
	}
	
	/**
	 * Resituisce vero se la data è compresa tra il periodo, falso il contrario
	 * @param d data da verificare se si trova nel periodo
	 * @return vero se d è compresa tra la data di inizio e fine del periodo
	 */
	public boolean compresaTra(Data d) {
		if (this.inizio == null) {
			return true;
		}
		Date daComparare;
		Date inizio;
		Date fine;
		daComparare = d.getDateUtilFormat();
		inizio = this.inizio.getDateUtilFormat();
		fine = this.fine.getDateUtilFormat();
		
		if (daComparare.compareTo(inizio) >= 0 && daComparare.compareTo(fine) <= 0)
			return true;		
		return false;
	}
	
	public String toString() {
		return "Inizio: "+inizio.toString()+" Fine: "+fine.toString();
	}
}
