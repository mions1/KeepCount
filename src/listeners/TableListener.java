package listeners;


import gui.DialogModificaTransazione;
import gui.Tabella;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import tabella.VisualizzaTabella;
import util.Periodo;
import conti.Transazione;

public class TableListener implements ActionListener,DocumentListener,MouseListener,WindowListener {

	Tabella source;
	JFrame context;
	public static int next = 0; //Tengo traccia della riga evidenziata
	
	public TableListener(Tabella source,JFrame context) {
		// TODO Auto-generated constructor stub
		this.source = source;
		this.context = context;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		/**Cambio le transazioni visualizzate a seconda del periodo scelto
		 * Setto la variabile "Periodo" che si trova in Tabella con il periodo scelto
		 * Notifico al tablemodel i cambiamenti, aggiorno il valore del totale nella tabella
		 * che è cambiato dato che è cambiato il periodo
		 */
		if (arg0.getSource().getClass().equals(JComboBox.class)) {
			String periodo = source.getPeriodoFromComboBox();
			
			if (periodo.equals(Tabella.PERIODO_MESE)){
				source.setPeriodo(new Periodo(Periodo.MESE));
			}
			else if (periodo.equals(Tabella.PERIODO_ANNO)){
				source.setPeriodo(new Periodo(Periodo.ANNO));
			}
			else if (periodo.equals(Tabella.PERIODO_OGGI))
				source.setPeriodo(new Periodo(Periodo.OGGI));
			else if (periodo.equals(Tabella.PERIODO_INTERO_TEMPO))
				source.setPeriodo(new Periodo(Periodo.INTERO_TEMPO));
			else if (periodo.equals(Tabella.PERIODO_SETTIMANA)) {
				source.setPeriodo(new Periodo(Periodo.SETTIMANA));
			}
			//Periodo custom: mostro i data picker per scegliere il periodo
			else if (periodo.equals(Tabella.PERIODO_CUSTOM)) {
				source.getDatePickerPanel().setVisible(true);
				source.setPeriodo(new Periodo(source.getDataDa(),source.getDataA()));
			}
			//nascondo i datapicker nel caso non sono nel periodo custom
			if (!periodo.equals(Tabella.PERIODO_CUSTOM))
				source.getDatePickerPanel().setVisible(false);
		}
		
		/**
		 * Opzioni menu tasto dx
		 * Modifica: modifica della transazione modificata tramite DialogModifica
		 * Elimina: Eliminazione transazione selezionata
		 */
		else if (arg0.getSource().equals(source.getModificaMenu())) {
			System.out.println("Modifica");
			DialogModificaTransazione nuovaTrans = new DialogModificaTransazione(source,source.getPatrimonio().getTransazioniFiltrate(source.getFiltro()).elementAt(source.getTabella().getSelectedRow()),source.getPatrimonio());
			nuovaTrans.pack();
			nuovaTrans.setVisible(true);
		}
		else if (arg0.getSource().equals(source.getEliminaMenu())) {
			Transazione t;
			for (int i = 0; i < source.getTabella().getSelectedRowCount(); i++) {
				t = source.getPatrimonio().getTransazioniFiltrate(source.getFiltro()).elementAt(source.getTabella().getSelectedRows()[i]-i);
				source.getPatrimonio().eliminaTransazione(t);
			}
		}

		//Stampa della tabella filtrata
		else if (arg0.getSource().equals(source.getStampa())) {
			try {
				Printable printable = source.getTabella().getPrintable(JTable.PrintMode.FIT_WIDTH, null, null);
				PrinterJob job = PrinterJob.getPrinterJob();// fetch a PrinterJob
				job.setPrintable(printable);// set the Printable on the PrinterJob
				boolean printAccepted = job.printDialog();
				if (printAccepted) // if the user didn't cancel the dialog
					job.print();
			} catch (PrinterException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Errore di stampa "+e.getMessage());
				e.printStackTrace();
			} finally {
			}
		}
		//Setto periodo custom nella variabile Periodo in tabella
		else if (arg0.getSource().equals(source.getOkPeriodoCustom())) {
			source.setPeriodo(new Periodo(source.getDataDa(),source.getDataA()));
		}
		
		/**
		 * Quando premo il bottone "trova" incremento next di 3
		 * (numero di colonne) cosi che vado alla riga successiva
		 * Next torna all'inizio quando arriva alla fine delle righe evidenziate
		 */
		else if (arg0.getSource().equals(source.getTrovaButton())) {
			next += 3;
			if (next+3 > VisualizzaTabella.contatore)
				next = 0;
		}
		
		if (!arg0.getSource().equals(source.getTrovaButton()))
			next = 0;
		//Aggiorno i filtri, notifico cambiamenti alla tabella e aggiorno valore patrimonio
		source.updateFiltro();
		source.getTableModel().fireTableDataChanged();
		source.getValorePatrimonio().setText(Float.toString(source.getPatrimonio().getTotaleFiltrato(source.getFiltro())));
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		/** Filtro le transazioni con la ricerca fatta dall'utente
		 *  oppure evidenzia quelle trovate con la barra "trova"
		 */
		source.updateFiltro();
		source.getTableModel().fireTableDataChanged();
		source.getValorePatrimonio().setText(Float.toString(source.getPatrimonio().getTotaleFiltrato(source.getFiltro())));
		next = 0;
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		/** Filtro le transazioni con la ricerca fatta dall'utente
		 * 
		 */
		source.updateFiltro();
		source.getTableModel().fireTableDataChanged();
		source.getValorePatrimonio().setText(Float.toString(source.getPatrimonio().getTotaleFiltrato(source.getFiltro())));
		next = 0;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		//Cambio colore dell opzione selezionata nel menu tasto dx
		if (e.getSource().getClass().equals(JMenuItem.class))
			((JMenuItem)(e.getSource())).setBackground(Color.lightGray);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		//Cambio colore dell'opzione deselezionata del menu tasto dx
		if (e.getSource().getClass().equals(JMenuItem.class))
			((JMenuItem)(e.getSource())).setBackground((new JMenuItem()).getBackground());
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		/**
		 * Mostro menu tasto dx nella posizione del cursore
		 * lo mostro solo se è stata selezionata almeno una riga
		 */
		if (source.getMenuTastoDx().isVisible())
			source.getMenuTastoDx().setVisible(false);
		if (e.getButton() == MouseEvent.BUTTON3) {
			if (source.getTabella().getSelectedRowCount() != 0) {
				source.getMenuTastoDx().setLocation(e.getLocationOnScreen());
				source.getMenuTastoDx().setVisible(true);
			}
		}
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
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		source.dispose();
		context.setEnabled(true);
		context.toFront();
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
