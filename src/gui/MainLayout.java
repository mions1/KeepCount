package gui;


import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import listeners.MainListener;
import listeners.MenuBarListener;
import conti.Patrimonio;

/**
 * 
 * @author Simone
 * Finestra principale
 * Viene mostrato il totale del patrimonio, il totale delle entrate ed uscite
 * Da qui si può, tramite la barra del menu, importare ed esportare il patrimonio in csv,txt e ods
 * Tramite i bottoni si possono visualizzare le transazioni effettuate ed aggiungere di nuove
 *
 */
public class MainLayout extends JFrame {
	//Dichiarazione componenti
		//Barra strumenti
	private JMenuBar menuBar; //Barra menu
		private JMenu file; //Nome menu sulla barra menu
			private JMenu importa; //Opzione importa: è un menu che consente la scelta dell estensione
				private JMenuItem importaCSV; 
				private JMenuItem importaTxt;
				private JMenuItem importaExcel;
			private JMenu esporta; //Come importa
				private JMenuItem esportaCSV;
				private JMenuItem esportaTxt;
				private JMenuItem esportaExcel;
			private JMenuItem esci;
	
	//Pannelli del frame
	private JPanel mainPanel; //Pannello principale dove inserire gli altri pannelli
		private JPanel headerPanel; //Pannello dell'header 
			private JPanel patrimonioPanel; //Pannello del patrimonio
		private JPanel inoutPanel; //Pannello di entrate e uscite
			private JPanel inPanel; //Pannello entrate
			private JPanel outPanel; //Pannello uscite
		private JPanel buttonPanel; //Pannello bottoni
	
	//Stringhe constanti (indicazioni per utente) e etichette relative ai valori e bottoni
	private JLabel patrimonioLabel; //Mostra patrimonioStringa
		private static final String patrimonioString = "Patrimonio: ";
	private JLabel patrimonioValue; //Mostra totale patrimonio
	
	private JLabel inLabel; //Mostra inStringa
		private static String inString = "Entrate:";
	private JLabel inValue; //Mostra totale entrate
	private JLabel outLabel; //Mostra outStringa
		private static String outString = "Uscite:";
	private JLabel outValue;//Mostra totale uscite
	
	private JLabel welcomeLabel; //Mostra messaggio benvenuto
		private static String welcomeString = "Benvenuto, comincia pure";
	
	private JButton nuovo; //Bottone nuova transazione
		private static String nuovoString = "nuova transazione"; //Testo bottone nuovo
	private JButton visualizza; //Bottone visualizza transazioni
		private static String visualizzaString = "visualizza"; //Testo bottone visualizza

	private Patrimonio patrimonio; //Patrimonio utente passato dal costruttore
	
	/**
	 * Costruttore finestra principale
	 * @param patrimonio Patrimonio utente
	 */
	public MainLayout(Patrimonio patrimonio) {
		super();

		//Definizione componenti
		this.patrimonio = patrimonio;
		
		//Barra Menu
		file = new JMenu("File");
			importa = new JMenu("Importa");
				importaCSV = new JMenuItem("csv...");
				importaTxt = new JMenuItem("txt...");
				importaExcel = new JMenuItem("excel...");
			esporta = new JMenu("Esporta");
				esportaCSV = new JMenuItem("csv...");
				esportaTxt = new JMenuItem("txt...");
				esportaExcel = new JMenuItem("excel...");
			esci = new JMenuItem("Esci");
		menuBar = new JMenuBar();
		
		//Pannelli
		mainPanel = new JPanel();
		headerPanel = new JPanel();
		patrimonioPanel = new JPanel();
		inoutPanel = new JPanel();
		inPanel = new JPanel();
		outPanel = new JPanel();
		buttonPanel = new JPanel();
		//Labels e bottoni
		patrimonioLabel = new JLabel(patrimonioString);
		patrimonioValue = new JLabel(Float.toString(patrimonio.getTotale()));

		inLabel = new JLabel(inString);
		inValue = new JLabel(Float.toString(patrimonio.getTotaleEntrate()));
		outLabel = new JLabel(outString);
		outValue = new JLabel(Float.toString(patrimonio.getTotaleUscite()));
		
		welcomeLabel = new JLabel(welcomeString);
		nuovo = new JButton(nuovoString);
		visualizza = new JButton(visualizzaString);
		
		/**
		 * Setting layout
		 * mainPanel = borderlayout,
		 * 	NORTH: headerPanel;
		 * 	WEST: inOutPanel;
		 * 	EAST: buttonPanel;
		 * 
		 * headerPanel = boxLayout verticale,
		 *  patrimonioPanel;
		 *  
		 * patrimonioPanel = boxlayout orizzontale,
		 * 	etichetta patrimonio;
		 * 	totale patrimonio;
		 * 
		 * InOutPanel = boxLayout verticale,
		 * 	inPanel;
		 *  outPanel;
		 * 
		 * inPanel = boxLayout orizzontale,
		 *  etichetta entrate;
		 *  valore entrate;
		 *  
		 * outPanel = boxLayout orizzontale,
		 * 	etichetta uscite;
		 * 	valore uscite;
		 * 
		 * buttonPanel = boxLayout verticale,
		 *  etichetta benvenuto;
		 * 	bottone nuovo;
		 * 	bottone visualizza;
		 */
		mainPanel.setLayout(new BorderLayout());
		headerPanel.setLayout(new BoxLayout(headerPanel,BoxLayout.Y_AXIS));
		patrimonioPanel.setLayout(new BoxLayout(patrimonioPanel,BoxLayout.X_AXIS));
		inoutPanel.setLayout(new BoxLayout(inoutPanel,BoxLayout.Y_AXIS));
		inPanel.setLayout(new BoxLayout(inPanel,BoxLayout.X_AXIS));
		outPanel.setLayout(new BoxLayout(outPanel,BoxLayout.X_AXIS));
		buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.Y_AXIS));
		
		//Colore Pannelli
		headerPanel.setBackground(Color.CYAN);
		inPanel.setBackground(Color.GREEN);
		outPanel.setBackground(Color.RED);
		
		//Aggiunta elementi ai pannelli
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(inoutPanel,BorderLayout.WEST);
		mainPanel.add(buttonPanel,BorderLayout.EAST);
		
		headerPanel.add(patrimonioPanel);
		patrimonioPanel.add(patrimonioLabel);
		patrimonioPanel.add(patrimonioValue);

		inoutPanel.add(inPanel);
		inoutPanel.add(outPanel);
		inPanel.add(inLabel);
		inPanel.add(inValue);
		outPanel.add(outLabel);
		outPanel.add(outValue);
		
		buttonPanel.add(welcomeLabel);
		buttonPanel.add(nuovo);
		buttonPanel.add(visualizza);
		
		//Aggiunta menu bar
		file.add(importa);
			importa.add(importaCSV);
			importa.add(importaTxt);
			importa.add(importaExcel);
		file.add(esporta);
			esporta.add(esportaCSV);
			esporta.add(esportaTxt);
			esporta.add(esportaExcel);
		file.add(esci);
		menuBar.add(file);
		
		//Settaggio frame
		setSize(500, 200);
		setJMenuBar(menuBar);
		add(mainPanel);
		
		//Ascoltatori
		nuovo.addActionListener(new MainListener(this,patrimonio));
		visualizza.addActionListener(new MainListener(this,patrimonio));
		addWindowListener(new MainListener(this,patrimonio));
		importaCSV.addActionListener(new MenuBarListener(this));
		importaTxt.addActionListener(new MenuBarListener(this));
		importaExcel.addActionListener(new MenuBarListener(this));
		esportaCSV.addActionListener(new MenuBarListener(this));
		esportaTxt.addActionListener(new MenuBarListener(this));
		esportaExcel.addActionListener(new MenuBarListener(this));
		esci.addActionListener(new MenuBarListener(this));
	}

	//Getters
	public Patrimonio getPatrimonio() {
		return patrimonio;
	}

	public float getPatrimonioValue() {
		return Float.parseFloat(patrimonioValue.getText());
	}

	public float getInValue() {
		return Float.parseFloat(inValue.getText());
	}

	public float getOutValue() {
		return Float.parseFloat(outValue.getText());
	}
	
	public JButton getNuovo() {
		return nuovo;
	}
	
	public JButton getVisualizza() {
		return visualizza;
	}
	
	public JMenuItem getImportaCSV() {
		return importaCSV;
	}

	public JMenuItem getImportaTxt() {
		return importaTxt;
	}

	public JMenuItem getImportaExcel() {
		return importaExcel;
	}

	public JMenuItem getEsportaCSV() {
		return esportaCSV;
	}

	public JMenuItem getEsportaTxt() {
		return esportaTxt;
	}

	public JMenuItem getEsportaExcel() {
		return esportaExcel;
	}

	public JMenuItem getEsci() {
		return esci;
	}
		
	/**
	 * Aggiorna valori del patrimonio,entrate e uscite
	 */
	public void aggiornaValori() {
		patrimonioValue.setText(Float.toString(patrimonio.getTotale()));
		inValue.setText(Float.toString(patrimonio.getTotaleEntrate()));
		outValue.setText(Float.toString(patrimonio.getTotaleUscite()));	
	}
}
