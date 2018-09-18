package listeners;


import gui.DialogNuovaTransazione;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import util.Data;
import conti.Deposito;
import conti.Patrimonio;
import conti.Prelievo;
import conti.Transazione;

public class DialogNuovaListener implements KeyListener,ActionListener,WindowListener {

	protected DialogNuovaTransazione source; //Finestra di dialogo chiamante
	protected JFrame context; //Finestra che ha chiamato la finestra di dialogo
	protected Patrimonio patrimonio;
	/**
	 * 
	 * @param source Finestra di dialogo chiamante
	 * @param context finestra chiamante la finestra di dialogo (sarà sempre la finestra principale)
	 * @param p patrimonio utente
	 */
	public DialogNuovaListener(DialogNuovaTransazione source,JFrame context,Patrimonio p) {
		// TODO Auto-generated constructor stub
		this.source = source;
		this.context = context;
		this.patrimonio = p;
	} 
	
	/**
	 * Implementazione ascoltatore
	 * Nel caso di salva si creerà una nuova transazione
	 * con i valori della dialog e verrà aggiunta al patrimonio
	 * e si azzererà la dialog per poterne aggiungere altre
	 * Nel caso di annulla si chiuderà la dialog e si riattiverà la finestra chiamante
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		/**Premuto il bottone "Annulla"
		 * Chiudo la dialog e ri-abilito il main
		 */
		if (e.getSource().equals(source.getAnnulla())) {
			source.setVisible(false);
			source.dispose();
			context.setEnabled(true);
			context.toFront();
		}
		
		/**Premuto il bottone "Salva" (riferito alla transazione)
		 * 
		 * Recupero tutti i valori inseriti nel form della dialog
		 * Creo una nuova transazione (prelievo o deposito a seconda del tipo scelto)
		 * Aggiungo la transazione al Patrimonio e aggiorno i valori nel main
		 * Azzero i campi nella dialog
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
			patrimonio.aggiungiTransazione(t);
			
			source.azzera();
		}
		
		}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		/**
		 * Controllo validità dati inseriti in importo
		 * in particolare verifico se i caretteri inseriti
		 * siano numeri oppure un solo punto decimale
		 */
		if (e.getSource().equals(source.getImporto()))
			source.getImporto().setText(bonifica(source.getImporto().getText()));
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		/**
		 * Come con il bottone "Annulla" chiudo la dialog e riabilito il main
		 */
		source.dispose();
		context.setEnabled(true);;
		context.toFront();
	}
	
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Controlla se il carattere c è un numero
	 * @param c character
	 * @return true se non è un numero, false altrimenti
	 */
	private boolean NaN(char c) {
		if (c != '0' && c != '1' && c != '2' &&
				c != '3' && c != '4' && c != '5' &&
				c != '6' && c != '7' && c != '8' && c != '9')
			return true;
		else 
			return false;
	}
	
	/**
	 * Utilizzato per bonificare l'importo inserito, elimina tutto ciò che non è 
	 * un numero oppure elimina tutti i punti decimali meno che uno
	 * @param testo testo da bonificare
	 * @return testo bonificato
	 */
	private String bonifica(String testo) {
		for (int i = testo.length()-1; i >= 0; i--)
			if (NaN(testo.charAt(i)) && 
					testo.charAt(i) != '.' && 
					testo.length() != 0)
				testo = testo.substring(0, testo.length()-1);
			else if (testo.charAt(i) == '.' &&
					testo.indexOf(".") != 
					testo.lastIndexOf(".")) {
				testo = testo.substring(0, testo.length()-1);
			}
		return testo;
	}
	
}
