package io;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.Vector;

import conti.*;

public class Stampa implements Printable {

	private Vector<Transazione> transazioni;
	
	public Stampa(Vector<Transazione> t) {
		super();
		transazioni = t;
	}
	@Override
	public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
	
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
		String s = "";
		for (int i = 0; i < transazioni.size(); i++)
			s += (transazioni.elementAt(i).getData().toString()+
					"   "+transazioni.elementAt(i).getDescrizione()+
					"   "+transazioni.elementAt(i).getImporto()+"\n");
		g.drawString(s, 5, 5);
	    return PAGE_EXISTS;
	}
}
