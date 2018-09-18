package tabella;


import gui.Tabella;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import conti.Patrimonio;
import conti.Transazione;

/**
 * Settaggio modello tabella transazioni
 * @author Simone
 *
 */
public class ModelloTabella extends AbstractTableModel {

	private Patrimonio patrimonio;
	private Tabella tabella;
	
	public ModelloTabella(Patrimonio p, Tabella tabella) {
		// TODO Auto-generated constructor stub
		this.patrimonio = p;
		this.tabella = tabella;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Data";
		case 1:
			return "Descrizione";
		case 2:
			return "Importo";
		default:
			break;
		}
		return "";
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return patrimonio.getTransazioniFiltrate(tabella.getFiltro()).size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		Vector<Transazione> t = patrimonio.getTransazioniFiltrate(tabella.getFiltro());

		if (arg1 == 0)
			return t.elementAt(arg0).getData();
		else if (arg1 == 1)
			return t.elementAt(arg0).getDescrizione();
		else 
			return t.elementAt(arg0).getImporto();
			
	}	
}
