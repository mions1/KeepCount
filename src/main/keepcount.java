package main;

import gui.MainLayout;

import java.io.File;

import javax.swing.JOptionPane;

import conti.Patrimonio;

public class keepcount {
	
	static MainLayout main; //Finestra principale, quindi avvio programma vero e proprio
	public Patrimonio patrimonio; //Patrimonio che si condividerà con tutte le finestre
	public final static String dirSave = "../save"; //Cartella di salvataggio di default
	
	public keepcount() {
		//Creazione patrimonio
		patrimonio = new Patrimonio();
	}
	
	/**
	 * Creazione cartella salvataggio se non esiste e 
	 * avvio finestra principale
	 * @param args argomenti
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
		if (!(new File(dirSave).exists()))
			(new File(dirSave)).mkdir();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Errore creazione cartella salvataggio: "+(new File(dirSave)).getPath()+" "+e.getMessage());
		}

		keepcount k = new keepcount();
		main = new MainLayout(k.patrimonio);
		main.setTitle("Keep Count");
		//main.pack();
		main.setVisible(true);		
	}


}
