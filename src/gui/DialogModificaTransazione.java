package gui;


import javax.swing.JFrame;

import listeners.DialogModificaListener;
import conti.Patrimonio;
import conti.Transazione;

/**
 * Sottoclasse di DialogNuovaTransazione
 * Apre una dialog come la sua sopraclasse i campi sono 
 * inizializzati con i valori della transazione da modificare
 * e al momento del salvataggio viene eliminata la vecchia
 * e salvata la nuova
 * @author Simone
 *
 */
public class DialogModificaTransazione extends DialogNuovaTransazione {

	private Transazione old_t; //Transazione da modificare
	
	public DialogModificaTransazione(JFrame context,Transazione t,Patrimonio p) {
		super(context,p);
		old_t = t;
		
		if (t.getImporto() > 0)
			tipo.setSelectedIndex(1);
		model.setDate(t.getData().getAnno(), t.getData().getMese()-1, t.getData().getGiorno());
		importo.setText(Float.toString( Math.abs(t.getImporto())));
		salva.setText("Modifica");
		
		salva.addActionListener(new DialogModificaListener(this, context));
	}
	
	public Transazione getOld_t(){
		return old_t;
	}
	
}
