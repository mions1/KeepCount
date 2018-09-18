package gui;


import java.awt.BorderLayout;
import java.util.Date;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import listeners.TableListener;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdesktop.xswingx.PromptSupport;

import tabella.*;
import util.Data;
import util.FiltroTransazioni;
import util.FormatoData;
import util.Periodo;
import conti.Patrimonio;

/**
 * Visualizzazione transazioni in tabella
 * E' possibile specificare dei filtri che mostrano le transazioni desiderate
 * In piu è possibile cercare nelle transazioni il testo voluto
 * Tramite tasto destro si possono eliminare e modificare le transazioni
 * Anche la possibilità di stampare la tabella
 * @author Simone
 *
 */
public class Tabella extends JFrame {

	//Costanti che definiscono il periodo da mostrare
	public static final String PERIODO_MESE = "Questo mese";
	public static final String PERIODO_ANNO = "Questo anno";
	public static final String PERIODO_SETTIMANA = "Questa settimana";
	public static final String PERIODO_OGGI = "Oggi";
	public static final String PERIODO_INTERO_TEMPO = "Intero tempo";
	public static final String PERIODO_CUSTOM ="Scegli periodo";

	//Costanti che definiscono quali tipi di trans mostrare
	public static final int TRANSAZIONI_TUTTO = 1001;
	public static final int TRANSAZIONI_DEPOSITI = 1002;
	public static final int TRANSAZIONI_PRELIEVI = 1003;
	
	//Dichiarazioni
	//Pannelli
	private JPanel mainPanel;
		private JPanel headerPanel;
			private JPanel datePickerPanel;
			private JPanel trovaPanel;
			private JPanel buttonGroupPanel;
		private JScrollPane tablePanel;
		private JPanel bottomPanel;

	//Menu tasto dx
	private JPopupMenu menuTastoDx;
		private JMenuItem modificaMenu;
		private JMenuItem eliminaMenu;
	
	//Altri componenti
	private JComboBox<String> periodoComboBox;
	//Date picker utilizzati per periodo custom
	private UtilDateModel datePickerModelDa;
	private UtilDateModel datePickerModelA;
		private JDatePanelImpl datePanelDa;
		private JDatePanelImpl datePanelA;
		private JDatePickerImpl datePickerDa;
		private JDatePickerImpl datePickerA;
	private JLabel valorePatrimonio;
	private JTextField ricerca;
	private JTextField trova; //Evidenzia campi con questo filtro
	private JRadioButton soloPrelievi;
	private JRadioButton soloDepositi;
	private JRadioButton tutteTransazioni;
	private ButtonGroup filtroTransazioni;
	private JButton stampa;
	private JButton okPeriodoCustom;
	private JButton trovaButton;
	
	private ModelloTabella tab;
	private VisualizzaTabella tabella;
	private Patrimonio patrimonio;
	private Periodo periodo;
	private FiltroTransazioni filtro; //Filtro delle trans da mostrare (periodo,tipo,testo)
	private JFrame context;
	
	public Tabella(JFrame context, Patrimonio p) {
		
		super();
		this.patrimonio = p;
		periodo = new Periodo(Periodo.MESE); //Setto periodo di default
		tab = new ModelloTabella(patrimonio,this);
		tabella = new VisualizzaTabella(this,tab);
		this.context = context;
		context.setEnabled(false);
		
		//Definizioni
		mainPanel = new JPanel();
		headerPanel = new JPanel();
		tablePanel = new JScrollPane(tabella);
		trovaPanel = new JPanel();
		bottomPanel = new JPanel();
		buttonGroupPanel = new JPanel();
		datePickerPanel = new JPanel();
		
		menuTastoDx = new JPopupMenu();
			modificaMenu = new JMenuItem("Modifica");
			eliminaMenu = new JMenuItem("Elimina");
		
		periodoComboBox = new JComboBox<String>();
		datePickerModelDa = new UtilDateModel();
		datePickerModelA = new UtilDateModel();
			Data d = new Data();
			datePickerModelDa.setDate(d.getAnno(), d.getMese()-1, d.getGiorno());
			datePickerModelDa.setSelected(true);
			datePickerModelA.setDate(d.getAnno(), d.getMese()-1, d.getGiorno());
			datePickerModelA.setSelected(true);
			datePanelDa = new JDatePanelImpl(datePickerModelDa,new Properties());
			datePanelA = new JDatePanelImpl(datePickerModelA,new Properties());
			datePickerDa = new JDatePickerImpl(datePanelDa, new FormatoData());
			datePickerA = new JDatePickerImpl(datePanelA, new FormatoData());
		valorePatrimonio = new JLabel();
		ricerca =  new JTextField();
		trova = new JTextField();
		soloPrelievi = new JRadioButton("Solo Prelievi");
		soloDepositi = new JRadioButton("Solo Depositi");
		tutteTransazioni = new JRadioButton("Tutto");
		filtroTransazioni = new ButtonGroup();
		
		stampa = new JButton("Stampa");
		okPeriodoCustom = new JButton("Filtra");
		trovaButton = new JButton("Trova");
		
		//Setting componenti
		menuTastoDx.add(modificaMenu);
		menuTastoDx.add(eliminaMenu);
		
		periodoComboBox.addItem(PERIODO_MESE);
		periodoComboBox.addItem(PERIODO_ANNO);
		periodoComboBox.addItem(PERIODO_SETTIMANA);
		periodoComboBox.addItem(PERIODO_OGGI);
		periodoComboBox.addItem(PERIODO_INTERO_TEMPO);
		periodoComboBox.addItem(PERIODO_CUSTOM);
		periodoComboBox.setSelectedIndex(0);

		datePickerPanel.setVisible(false);
		
		filtroTransazioni.add(tutteTransazioni);
		tutteTransazioni.setSelected(true);
		filtroTransazioni.add(soloDepositi);
		filtroTransazioni.add(soloPrelievi);
		
		ricerca.setText("");
		trova.setText("");
		filtro = new FiltroTransazioni(new Periodo(Periodo.MESE), this.getRicerca().getText(), this.getCodeFiltroTransazioni());
		valorePatrimonio.setText(Float.toString(patrimonio.getTotaleFiltrato(filtro)));
		PromptSupport.setPrompt("Ricerca", ricerca);
		PromptSupport.setPrompt("Trova", trova);
		
		/**
		 * Layout pannelli
		 */
		mainPanel.setLayout(new BorderLayout());
		headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
		trovaPanel.setLayout(new BoxLayout(trovaPanel, BoxLayout.X_AXIS));
		datePickerPanel.setLayout(new BoxLayout(datePickerPanel, BoxLayout.X_AXIS));
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		buttonGroupPanel.setLayout(new BoxLayout(buttonGroupPanel, BoxLayout.X_AXIS));
		
		//Aggiunta componenti
		headerPanel.add(periodoComboBox);
		headerPanel.add(datePickerPanel);
		headerPanel.add(ricerca);
		headerPanel.add(trovaPanel);
		headerPanel.add(buttonGroupPanel);
		datePickerPanel.add(new JLabel("Da:"));
		datePickerPanel.add(datePickerDa);
		datePickerPanel.add(new JLabel("A:"));
		datePickerPanel.add(datePickerA);
		datePickerPanel.add(okPeriodoCustom);
		trovaPanel.add(trova);
		trovaPanel.add(trovaButton);
		bottomPanel.add(new JLabel("Totale: "));
		bottomPanel.add(valorePatrimonio);
		bottomPanel.add(stampa);
		buttonGroupPanel.add(tutteTransazioni);
		buttonGroupPanel.add(soloDepositi);
		buttonGroupPanel.add(soloPrelievi);
		
		//Aggiunta pannelli
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(tablePanel,BorderLayout.CENTER);
		mainPanel.add(bottomPanel,BorderLayout.SOUTH);
		
		add(mainPanel);
		
		//Listeners
		periodoComboBox.addActionListener(new TableListener(this,context));
		ricerca.getDocument().addDocumentListener(new TableListener(this,context));
		trova.getDocument().addDocumentListener(new TableListener(this,context));
		soloDepositi.addActionListener(new TableListener(this,context));
		soloPrelievi.addActionListener(new TableListener(this,context));
		tutteTransazioni.addActionListener(new TableListener(this,context));
		tabella.addMouseListener(new TableListener(this,context));
		stampa.addActionListener(new TableListener(this, context));
		okPeriodoCustom.addActionListener(new TableListener(this, context));
		trovaButton.addActionListener(new TableListener(this, context));
		modificaMenu.addActionListener(new TableListener(this,context));
		eliminaMenu.addActionListener(new TableListener(this,context));		
		modificaMenu.addMouseListener(new TableListener(this,context));
		eliminaMenu.addMouseListener(new TableListener(this,context));
		addWindowListener(new TableListener(this, context));
	}
	/**
	 * Restituisce il campo Ricerca dove si immette il testo
	 * per filtrare le transazioni
	 * @return campo Ricerca
	 */
	public JTextField getRicerca() {
		return ricerca;
	}
	/**
	 * Restituisce il campo Trova dove si immette il testo
	 * da trovare nelle transazioni
	 * @return campo Trova
	 */
	public JTextField getTrova() {
		return trova;
	}
	/**
	 * Restituisce il patrimonio
	 * @return patrimonio
	 */
	public Patrimonio getPatrimonio() {
		return patrimonio;
	}
	/**
	 * Restituisce il periodo sceltro
	 * @return periodo scelto
	 */
	public Periodo getPeriodo() {
		return periodo;
	}
	/**
	 * Restituisce la tabella
	 * @return tabella
	 */
	public VisualizzaTabella getTabella() {
		return tabella;
	}
	/**
	 * Modello di questa tabella
	 * @return modello tabella
	 */
	public AbstractTableModel getTableModel() {
		return tab;
	}
	/**
	 * Restituisce il valore totale del patrimonio della tabella visualizzata
	 * @return totale patrimonio tabella
	 */
	public JLabel getValorePatrimonio() {
		return valorePatrimonio;
	}
	/**
	 * Restituisce il periodo da visualizzare (es. Questo anno, Questo mese)
	 * @return periodo da mostrare
	 */
	public String getPeriodoFromComboBox() {
		return periodoComboBox.getSelectedItem().toString();
	}
	/**
	 * Menu tasto destro
	 * @return Menu tasto destro
	 */
	public JPopupMenu getMenuTastoDx() {
		return menuTastoDx;
	}
	/**
	 * Opzione tasto destro "Modifica"
	 * @return opzione tasto destro "Modifica"
	 */
	public JMenuItem getModificaMenu() {
		return modificaMenu;
	}
	/**
	 * Opzione tasto destro "Elimina"
	 * @return opzione tasto destro "elimina"
	 */
	public JMenuItem getEliminaMenu() {
		return eliminaMenu;
	}
	
	/**
	 * Bottone stampa tabella
	 * @return bottone stampa
	 */
	public JButton getStampa() {
		return stampa;
	}
	
	/**
	 * Bottone applicazione periodo custom
	 * @return bottone periodo custom
	 */
	public JButton getOkPeriodoCustom() {
		return okPeriodoCustom;
	}
	
	/**
	 * Bottone per le corrispondenze successive del testo da cercare
	 * nelle transazioni
	 * @return bottone trova
	 */
	public JButton getTrovaButton() {
		return trovaButton;
	}
	
	public JPanel getDatePickerPanel() {
		return datePickerPanel;
	}

	/**
	 * Settaggio periodo transazioni da mostrare
	 * @param periodo periodo delle transazioni da mostrare
	 */
	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}
	
	/**
	 * Filtro dei tipi delle transazioni da mostrare (tutte, depositi, prelievi)
	 * @return filtro tipi transazioni da mostrare
	 */
	public ButtonGroup getFiltroTransazioni() {
		return filtroTransazioni;
	}
	
	/**
	 * 
	 * @return constante che definisce il tipo di trans da mostrare
	 */
	public int getCodeFiltroTransazioni() {
		if (soloDepositi.isSelected())
			return TRANSAZIONI_DEPOSITI;
		if (soloPrelievi.isSelected())
			return TRANSAZIONI_PRELIEVI;
		return TRANSAZIONI_TUTTO;
	}
	
	/**
	 * Filtro transazioni che stabilisce quali mostrare
	 * @return filtro
	 */
	public FiltroTransazioni getFiltro() {
		return filtro;
	}
	
	//Aggiorno filtro secondo le ultime modifiche
	public void updateFiltro() {
		filtro = new FiltroTransazioni(periodo, ricerca.getText(), getCodeFiltroTransazioni());
	}
	
	/**
	 * Restituisce la data del dataPicker "Da" utilizzato nel periodo custom
	 * @return data transazione
	 */
	public Data getDataDa() {
		Date data = (Date) datePickerDa.getModel().getValue();
		Data d = new Data(data.getDate(),data.getMonth()+1,data.getYear()+1900);
		return d;
	}
	/**
	 * Restituisce la data del dataPicker "A" utilizzato nel periodo custom
	 * @return data transazione
	 */
	public Data getDataA() {
		Date data = (Date) datePickerA.getModel().getValue();
		Data d = new Data(data.getDate(),data.getMonth()+1,data.getYear()+1900);
		return d;
	}
}