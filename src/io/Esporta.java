package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jopendocument.dom.spreadsheet.SpreadSheet;

import conti.Transazione;

/**
 * Gestione esportazione/salvataggio patrimonio
 * Vengono presi i dati delle transazioni e vengono salvati sul file
 * secondo le regole dell'estensione
 * @author Simone
 *
 */
public class Esporta {

	/**
	 * Formato CSV: ogni riga è una transazione e ogni dato della trans è separato da ','
	 * @param file file su dove salvare
	 * @param t transazioni da salvare
	 */
	static public void esportaCSV(File file, Vector<Transazione> t) {
		if (file == null)
			return;
		
		String s = "";
		//Creo la stringa contente ogni transazione secondo le regole del CSV
		for (int i = 0; i < t.size(); i++) {
			s += t.get(i).getData().toString();
			s += ","+t.get(i).getDescrizione();
			s += ","+Float.toString(t.get(i).getImporto())+"\n";
		}
		FileWriter writer;
		//Salvo le transazioni su file
		try {
			writer = new FileWriter(file);
		    writer.write(s);
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Errore esportazione file "+file.getPath()+" "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Formato Txt: ogni riga è una transazione e ogni dato della trans è separato da ' '
	 * @param file file dove salvare il patrimonio
	 * @param t transazioni da salvare
	 */
	static public void esportaTxt(File file, Vector<Transazione> t) {
		if (file == null)
			return;
		
		String s = "";
		for (int i = 0; i < t.size(); i++) {
			s += t.get(i).getData().toString();
			s += " "+t.get(i).getDescrizione();
			s += " "+Float.toString(t.get(i).getImporto())+"\n";
		}
		FileWriter writer;
		try {
			writer = new FileWriter(file);
		    writer.write(s);
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Errore esportazione file "+file.getPath()+" "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Esportazione su OpenDocument (ods)
	 * @param file file dove salvo transazioni
	 * @param t transazioni da salvare
	 */
	static public void esportaExcel(File file, Vector<Transazione> t) {
		if (file == null)
			return;
		//Dati delle transazioni (Data,descrizione,importo) prelevati dal vettore
		final String[][] dati = new String[t.size()][3];
		//Intestazione colonne
		String[] colonne = new String[] {"Data","Descrizione","Importo"};
		//Prelevo dati dal vettore e li metto nella matrice dati
		for (int i = 0; i < t.size(); i++) {
			dati[i][0] = t.elementAt(i).getData().toString();
			dati[i][1] = t.elementAt(i).getDescrizione();
			dati[i][2] = Float.toString(t.elementAt(i).getImporto());
		}
		//Creo la tabella con i dati e la salvo nel foglio di calcolo
		TableModel model = new DefaultTableModel(dati,colonne);
		try {
			SpreadSheet.createEmpty(model).saveAs(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Errore file non trovate "+file.getPath()+" "+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Errore esportazione file "+file.getPath()+" "+e.getMessage());
			e.printStackTrace();
		}
	}	

}
