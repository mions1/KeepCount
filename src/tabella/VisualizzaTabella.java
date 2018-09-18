package tabella;


import gui.Tabella;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import listeners.TableListener;
import conti.Transazione;

public class VisualizzaTabella extends JTable {

	private Tabella context;
	public static int contatore = 0; //Lo uso per sapere quale riga colorare di verde
	
	public VisualizzaTabella(Tabella context, TableModel model) {
		super(model);
		this.context = context;
	}
	
	@Override
	/**
	 * Coloro di giallo le righe che contengono "trova"
	 * Coloro di verde le righe evidenziate al momento dal tasto "trova"
	 * Contatore serve per verificare quale celle devo colorare di verde
	 * a seconda di quanto vale next.
	 */
	public Component prepareRenderer(TableCellRenderer renderer, int row,
			int column) {
		// TODO Auto-generated method stub
		//Alla prima cella il contatore torna a 0
		if (row == 0 && column == 0)
			contatore = 0;
		Component c = super.prepareRenderer(renderer, row, column);
		Vector<Transazione> t = context.getPatrimonio().getTransazioniFiltrate(context.getFiltro());
		String trova = context.getTrova().getText();
		
		if (t.elementAt(row).getDescrizione().contains(trova) && !trova.equals("")) {
			if (contatore == TableListener.next ||
					contatore-1 == TableListener.next ||
					contatore-2 == TableListener.next)
				c.setBackground(Color.green);
			else
				c.setBackground(Color.YELLOW);
			contatore++;
		}
		else {
			c.setBackground(super.getBackground());
		}
		
	    return c;
	}
	
}
