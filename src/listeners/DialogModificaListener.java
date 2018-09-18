package listeners;


import gui.*;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import util.Data;
import conti.Deposito;
import conti.Prelievo;
import conti.Transazione;

/**
 * Gestione eventi DialogModificaTransizione
 * modifico la transazione selezionata con la nuova create
 * @author Simone
 *
 */
public class DialogModificaListener extends DialogNuovaListener {
	
	public DialogModificaListener(DialogNuovaTransazione source,JFrame context) {
		super(source,context,source.getPatrimonio());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		/**Premuto il bottone "Annulla"
		 * Chiudo la dialog e ri-abilito il context
		 */
		if (e.getSource().equals(source.getAnnulla())) {
			source.setVisible(false);
			source.dispose();
			context.setEnabled(true);
		}
		
		/**Premuto il bottone "Modifica" (riferito alla transazione)
		 * 
		 * Recupero tutti i valori inseriti nel form della dialog
		 * Creo una nuova transazione (prelievo o deposito a seconda del tipo scelto)
		 * Modifico la transazione
		 * Chiudo la dialog
		 * 
		 */
		if (e.getSource().equals(source.getSalva())) {
			Transazione t;
			String tipo = source.getTipo().getSelectedItem().toString(); //Deposito o prelievo
			float importo = 0;
			if (!source.getImporto().getText().isEmpty()) {
				importo = Float.parseFloat(source.getImporto().getText());
			}
			String descrizione = source.getDescrizione().getText();
			Data data = source.getData();
			//Crera nuova transazione a seconda del tipo
			if (tipo.equals("Prelievo"))
				t = new Prelievo(importo,data,descrizione);
			else
				t = new Deposito(importo,data,descrizione);
			
			patrimonio.modificaTransazione(t, ((DialogModificaTransazione)source).getOld_t());
			((Tabella)context).getTableModel().fireTableDataChanged();
			source.setVisible(false);
			source.dispose();
			context.setEnabled(true);
			context.toFront();
			((Tabella)context).getValorePatrimonio().setText(Float.toString(patrimonio.getTotaleFiltrato(((Tabella)context).getFiltro())));
			
			e.setSource(new String());//Per evitare che esegue il listener in DialogNuovaListener
		}
	}
	
}
