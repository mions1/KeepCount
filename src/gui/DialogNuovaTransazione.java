package gui;


import java.util.Date;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import listeners.DialogNuovaListener;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdesktop.xswingx.PromptSupport;

import util.Data;
import util.FormatoData;
import conti.Patrimonio;

/**
 * Dialog che si mostra all'aggiunta di una nuova transazione
 * @author Simone
 */
public class DialogNuovaTransazione extends JFrame {

	private JFrame context; //Finestra che apre questa dialog
	
	private JPanel mainPanel;
		private JPanel textFields;
		private JPanel buttons;
	
	protected JComboBox<String> tipo; //Combobox per decidere se deposito o prelievo
	protected JTextField importo; //Importo transazione (senza segno)
	private JDatePickerImpl datePicker; //Calendario per data
		protected UtilDateModel model; //Modello calendario
		private JDatePanelImpl datePanel; //Pannello calendario
	protected JTextField descrizione; //Descrizione transazione
	protected JButton salva; //Salva transazione
	protected JButton annulla; //Annulla transazione
	
	protected Patrimonio patrimonio;
	/**
	 * 
	 * @param context Finestra chiamante (sarà finestra principale)
	 * @param p patrimonio utente
	 */
	public DialogNuovaTransazione(JFrame context,Patrimonio p) {
		super();
		
		this.patrimonio = p;
		this.context = context;
		context.enable(false); //Disabilita finestra chiamante (riabilitata alla chiusura di questa)
		
		//Definizione componenti
		//Panels
		mainPanel = new JPanel();
		textFields = new JPanel();
		buttons = new JPanel();
		
		//Componenti
		tipo = new JComboBox<String>();
			tipo.addItem("Prelievo");
			tipo.addItem("Deposito");
		importo = new JTextField(null,3);
			PromptSupport.setPrompt("0.00", importo); //Suggerimento
		model = new UtilDateModel();
			Data d = new Data();
			model.setDate(d.getAnno(), d.getMese()-1, d.getGiorno());
			model.setSelected(true);
			datePanel = new JDatePanelImpl(model,new Properties());
		datePicker = new JDatePickerImpl(datePanel, new FormatoData());
		descrizione = new JTextField(null,25);
			PromptSupport.setPrompt("Descrizione", descrizione);
		salva = new JButton("Salva");
		annulla = new JButton("Annulla");
		
		/**
		 * Setting layout
		 * mainPanel = boxLayout verticale
		 * 	textFields;
		 * 	buttons;
		 * 
		 * textFields = boxLayout Orizzontale,
		 * 	importo;
		 *  datePicker;
		 *  
		 * buttons = boxLayout orizzontale,
		 * 	salva;
		 * 	annulla;
		 */
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		textFields.setLayout(new BoxLayout(textFields, BoxLayout.X_AXIS));
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		
		//Aggiunta componenti nei pannelli
		textFields.add(importo);
		textFields.add(datePicker);
		
		buttons.add(salva);
		buttons.add(annulla);
		
		//Aggiunta pannelli e componenti nel frame
		mainPanel.add(tipo);
		mainPanel.add(textFields);
		mainPanel.add(descrizione);
		mainPanel.add(buttons);
		
		add(mainPanel);
		
		//Ascoltatori
		addWindowListener(new DialogNuovaListener(this,context,patrimonio));
		annulla.addActionListener(new DialogNuovaListener(this,context,patrimonio));
		salva.addActionListener(new DialogNuovaListener(this,context,patrimonio));
		importo.addKeyListener(new DialogNuovaListener(this, context,patrimonio));
	}

	/**
	 * Restituisce il bottone salva della dialog
	 * @return bottone salva
	 */
	public JButton getSalva() {
		return salva;
	}
	/**
	 * Restituisce il bottone annulla della dialog
	 * @return bottone annulla
	 */
	public JButton getAnnulla() {
		return annulla;
	}
	/**
	 * Restituisce la combobox per vedere quale tipo di transazione bisogna salvare (prelievo o deposito)
	 * @return combobox tipo
	 */
	public JComboBox<String> getTipo() {
		return tipo;
	}

	/**
	 * Restituisce l'importo immesso
	 * @return importo transazione
	 */
	public JTextField getImporto() {
		return importo;
	}
	
	/**
	 * 
	 * @return patrimonio utente
	 */
	public Patrimonio getPatrimonio() {
		return patrimonio;
	}

	/**
	 * Restituisce la data del dataPicker
	 * @return data transazione
	 */
	public Data getData() {
		Date data = (Date) datePicker.getModel().getValue();
		Data d = new Data(data.getDate(),data.getMonth()+1,data.getYear()+1900);
		return d;
	}

	/**
	 * Restituisce il componente descrizione
	 * @return descrizione Transazione
	 */
	public JTextField getDescrizione() {
		return descrizione;
	}
	
	/**
	 * Torno ai valori di default
	 */
	public void azzera() {
		tipo.setSelectedIndex(0);
		importo.setText("");
		PromptSupport.setPrompt("Importo", importo);
		Data d = new Data();
		model.setDate(d.getAnno(),d.getMese()-1,d.getGiorno());
		model.setSelected(true);
		descrizione.setText("");
		PromptSupport.setPrompt("Descrizione", descrizione);
	}
}
