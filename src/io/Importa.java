package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import util.Data;
import conti.Deposito;
import conti.Patrimonio;
import conti.Prelievo;
import conti.Transazione;

/**
 * Importazione del patrimonio da file
 * @author Simone
 *
 */
public class Importa {

	public static void importaCSV(File file,Patrimonio p) {
		if (file == null)
			return;
		p.svuota(); //Svuoto patrimonio attuale
		Scanner sc = null;
		Transazione tmp = null;
		Data d;
		String descrizione;
		Float importo;
		try
		{	
			sc = new Scanner(file);
			sc = sc.useDelimiter("[,\n]");
		}
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Errore file non trovato "+file.getPath()+" "+e.getMessage());
		}
		//Scannerizzo ogni elemento del file e lo salvo nel patrimonio
		try {
			while(sc.hasNext()) {
				d = new Data(sc.next());
				descrizione = sc.next();
				importo = Float.parseFloat(sc.next());
				if (importo < 0)
					tmp = new Prelievo();
				else
					tmp = new Deposito();
				tmp.setData(d);
				tmp.setDescrizione(descrizione);
				tmp.setImporto(importo);
				p.aggiungiTransazione(tmp);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Errore nel thread "+e.getMessage());
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Errore file, probabilmente file invalido");
		}
	}
	
	public static void importaTxt(File file,Patrimonio p) {
		if (file == null)
			return;
		p.svuota();
		Scanner sc = null;
		Transazione tmp;
		Data d;
		String descrizione;
		Float importo;
		try
		{	
			sc = new Scanner(file);
			sc = sc.useDelimiter("[ \n]");
		}
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Errore file non trovato "+file.getPath()+" "+e.getMessage());
		}
		try {	
			while(sc.hasNext()) {
				d = new Data(sc.next());
				descrizione = sc.next();
				importo = Float.parseFloat(sc.next());
				if (importo < 0)
					tmp = new Prelievo();
				else
					tmp = new Deposito();
				tmp.setData(d);
				tmp.setDescrizione(descrizione);
				tmp.setImporto(importo);
				p.aggiungiTransazione(tmp);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Errore nel thread "+e.getMessage());
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Errore file, probabilmente file invalido");
		}
	}

	public static void importaExcel(File file,Patrimonio p) {
		if (file == null)
			return;
		p.svuota();
		Sheet sheet = null;
		Transazione tmp;
		Data d;
		String descrizione;
		Float importo;
		try {
			sheet = SpreadSheet.createFromFile(file).getFirstSheet();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Errore importazione file "+file.getPath()+" "+e.getMessage());
			e.printStackTrace();
			return;
		}
		try {
			//Per ogni riga del foglio prelevo i dati e li salvo nel patrimonio
			for (int i = 1; i < sheet.getRowCount(); i++) {
				d = new Data(sheet.getCellAt(0,i).getTextValue());
				descrizione = sheet.getCellAt(1,i).getTextValue();
				importo = Float.parseFloat(sheet.getCellAt(2, i).getTextValue());
				if (importo < 0)
					tmp = new Prelievo();
				else
					tmp = new Deposito();
				tmp.setData(d);
				tmp.setDescrizione(descrizione);
				tmp.setImporto(importo);
				p.aggiungiTransazione(tmp);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Errore nel thread "+e.getMessage());
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Errore file, probabilmente file invalido");
		}
	}
}
