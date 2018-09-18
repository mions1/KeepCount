package util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author Simone
 * Classe Data per gestire la data delle transazioni.
 */
public class Data extends Date {

	private int giorno,mese,anno;
	
	/**
	 * Di default si inserisce la data odierna
	 */
	public Data() {
		setOggi();
	}
	/**
	 * 
	 * @param giorno giorno del mese
	 * @param mese mese (1-Gennaio)
	 * @param anno anno
	 */
	public Data(int giorno,int mese,int anno) {
		super();
		
		this.giorno = giorno;
		this.mese = mese;
		this.anno = anno;
	};
	
	/**
	 * Settaggio data da stringa
	 * @param data data formato (gg_mm_aaaa)
	 */
	public Data(String data) {
		giorno = Integer.parseInt(data.substring(0, data.indexOf("_")));
		mese = Integer.parseInt(data.substring(data.indexOf("_")+1, data.lastIndexOf("_")));
		anno = Integer.parseInt(data.substring(data.lastIndexOf("_")+1,data.length()));
	}
	/**
	 * Restituisce la data in formato Date (java.util.Date)
	 * @return data in formato java.util.Date
	 */
	public Date getDateUtilFormat() {
		Date c = new Date();
		c.setYear(anno);
		c.setDate(giorno);
		c.setMonth(mese);
		
		return c;
	}
	
	/**
	 * Restiuisce il giorno della data
	 * @return giorno del mese
	 */
	public int getGiorno() {
		return giorno;
	}
	/**
	 * Restituisce il mese della data
	 * @return mese
	 */
	public int getMese() {
		return mese;
	}
	/**
	 * Resituisce l'anno della data
	 * @return anno
	 */
	public int getAnno() {
		return anno;
	}
	/**
	 * Setta il giorno
	 * ATTENZIONE: Nessun controllo sulla validità della data
	 * @param giorno giorno del mese
	 */
	public void setGiorno(int giorno) {
		this.giorno = giorno;
	}
	/**
	 * Setta il mese
	 * ATTENZIONE: Nessun controllo sulla validità della data
	 * @param mese mese
	 */
	public void setMese(int mese) {
		this.mese = mese;
	}
	/**
	 * Setta l'anno
	 * ATTENZIONE: Nessun controllo sulla validità della data
	 * @param anno anno
	 */
	public void setAnno(int anno) {
		this.anno = anno;
	}
	
	/**
	 * Restituisce la data odierna
	 * @return data di oggi
	 */
	static public Data getOggi() {
		Data data = new Data();
		GregorianCalendar g = new GregorianCalendar();
		data.setGiorno(g.get(Calendar.DAY_OF_MONTH));
		data.setMese(g.get(Calendar.MONTH)+1);
		data.setAnno(g.get(Calendar.YEAR));

		return data;
	}
	/**
	 * Setta la data odierna
	 */
	public void setOggi() {
		GregorianCalendar g = new GregorianCalendar();
		giorno = (g.get(Calendar.DAY_OF_MONTH));
		mese = (g.get(Calendar.MONTH) +1);
		anno = (g.get(Calendar.YEAR));
		
	}
	
	/**
	 * Restituisce l'ultimo giorno del mese corrente
	 * esempio: mese = 12 allora restituisce 31
	 * @return numero giorni del mese
	 */
	public int getUltimoGiornoDelMese() {
		GregorianCalendar c = new GregorianCalendar();
		if (c.isLeapYear(getAnno()) && getMese() == 2 )
			return 29;
		if (getMese() == 2)
			return 28;
		if (mese == 1 || mese == 3 || mese == 5 || mese == 7
				|| mese == 8 || mese == 10 || mese == 12)
			return 31;
		return 30;
	}
	
	/**
	 * Restituisce il giorno di lunedi della settimana corrente
	 * @return primo giorno della settimana
	 */
	public int getPrimoGiornoSettimana() {
		int giorno = GregorianCalendar.DAY_OF_WEEK;
		return getGiorno()-giorno+1;
	}
	public String toString() {
		super.toString();
		String giorno = Integer.toString(this.giorno);
		String mese = Integer.toString(this.mese);
		String anno = Integer.toString(this.anno);

		return giorno + "_" + mese + "_" + anno;
	}
	
	/**
	 * Comparazione tra data
	 * @param d data da comparare
	 * @return -1 se d è maggiore della data corrente
	 * +1 se d è minore della data corrente
	 * 0  se le date sono uguali
	 */
	public int compara (Data d) {
		if (this.equals(d))
			return 0;
		if (this.anno < d.anno)
			return -1;
		if (this.anno == d.anno && this.mese < d.mese)
			return -1;
		if (this.anno == d.anno && this.mese == d.mese && this.giorno < d.giorno)
			return -1;
		return 1;
	}
	
}
