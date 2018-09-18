package listeners;


import gui.DialogNuovaTransazione;
import gui.MainLayout;
import gui.Tabella;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import conti.Patrimonio;

/**
 * Gestione eventi generati dalla MainLayout (Esclusa barra menu):
 * Click su bottone Nuovo crea una nuova transazione aprendo una dialog
 * Click su bottone Visualizza apre una tabella con le varie transazioni
 * 
 * @author Simone
 *
 */
public class MainListener implements ActionListener,WindowListener {

	private MainLayout source; //Riferimento al main (che è la finestra chiamante)
	private Patrimonio patrimonio;
	
	public MainListener(MainLayout source,Patrimonio p) {
		// TODO Auto-generated constructor stub
		this.source = source;
		this.patrimonio = p;
	} 
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//Bottone "Nuovo" apre la dialog per l'inserimento
		if (e.getSource().equals(source.getNuovo())) {
			nuovaTransizione();
		}
		
		//Bottone "Visualizza" apre la tabella
		if (e.getSource().equals(source.getVisualizza())) {
			visualizza();
		}
	}

	/** Crea la dialog per l'inserimento della nuova transazione
	 * 
	 */
	public void nuovaTransizione() {
		DialogNuovaTransazione nuovaTrans = new DialogNuovaTransazione(source,patrimonio);
		nuovaTrans.pack();
		nuovaTrans.setVisible(true);
	}
	
	/** Crea la finestra con la tabella delle transazioni
	 * 
	 */
	public void visualizza() {
		Tabella tab = new Tabella(source,source.getPatrimonio());
		
		tab.pack();
		tab.setVisible(true);
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		source.aggiornaValori();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		System.exit(0);
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
}
