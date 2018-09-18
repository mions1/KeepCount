package listeners;


import gui.MainLayout;
import io.Esporta;
import io.Importa;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import main.keepcount;

/**
 * Gestione eventi generati dalla menu bar del MainLayout:
 * Importa/Esporta patrimonio in vari formati (csv,txt,ods)
 * Esci dal programma
 * 
 * @author Simone
 *
 */
public class MenuBarListener implements ActionListener {

	MainLayout source;
	
	public MenuBarListener(MainLayout source) {
		// TODO Auto-generated constructor stub
		super();
		this.source = source;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JFileChooser fc = new JFileChooser(keepcount.dirSave);//Setto cartella predefinita
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);//Si possono selezionare solo i file

		//File->Esci
		if (e.getSource().equals(source.getEsci()))
			System.exit(0);	
		
		//Importazioni
		/**File->Importa... Chiamo i metodi statici della classe Importa
		*Che provvederanno a caricare da file il patrimonio
		*/
		else if (e.getSource().equals(source.getImportaCSV())){
			Importa.importaCSV(finestraImportaFile(".csv"),source.getPatrimonio());
		}
		else if (e.getSource().equals(source.getImportaTxt()))
			Importa.importaTxt(finestraImportaFile(".txt"), source.getPatrimonio());
		else if (e.getSource().equals(source.getImportaExcel()))
			Importa.importaExcel(finestraImportaFile(".ods"), source.getPatrimonio());
		
		//Esportazioni
		/**File->Esporta... Chiamo i metodi statici della classe Esporta
		*Che provvederanno a Salvare su file il patrimonio
		*/
		else if (e.getSource().equals(source.getEsportaCSV())) 
			Esporta.esportaCSV(finestraEsportaFile(".csv"),source.getPatrimonio().getTransazioni());				
		else if (e.getSource().equals(source.getEsportaTxt()))
			Esporta.esportaTxt(finestraEsportaFile(".txt"),source.getPatrimonio().getTransazioni());
		else if (e.getSource().equals(source.getEsportaExcel()))
			Esporta.esportaExcel(finestraEsportaFile(".ods"), source.getPatrimonio().getTransazioni());
			
	}
	
	/**
	 * Crea e mostra la finestra per l'esportazione del file
	 * Se il file scelto esiste chiederà conferma di sovrascrittura
	 * @param estensione estensione che deve avere il file compreso il punto (es. ".txt")
	 * @return file scelto, null se non si vuole sovrascrivere
	 */
	private File finestraEsportaFile(String estensione) {
		JFileChooser fc = new JFileChooser(keepcount.dirSave); //Cartella default
		String nomeFile = ".tmp"; //Qui ci si metterà il file selezionato
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		//Posso vedere solo i file con la giusta estensione
		fc.setAcceptAllFileFilterUsed(false);
		fc.setFileFilter(new FileFilter()
        {
           @Override
           public boolean accept(File file)
           {
              return file.getName().substring(file.getName().lastIndexOf("."), file.getName().length()).toUpperCase().equals(estensione.toUpperCase());
           }
           @Override
           public String getDescription()
           {
              return estensione+" files";
           }
        });
		int result;
		//Rimostro la selezione se si annulla la sovrascrizione
		do {
			result = fc.showSaveDialog(source);
			if (result == JFileChooser.APPROVE_OPTION){
				if (!fc.getSelectedFile().toString().contains(estensione))
					nomeFile = (fc.getSelectedFile()+estensione);
				else
					nomeFile = (fc.getSelectedFile()+"");
				File file = new File(nomeFile);
				if (file.exists()) {
					int res = JOptionPane.showConfirmDialog((Component) null, "File esistente. Sovrascrivere?",
						        "alert", JOptionPane.OK_CANCEL_OPTION);
					if (res == JOptionPane.OK_OPTION) 
						return file;			
				}
				else
					return file;
			}
		} while(result == JFileChooser.APPROVE_OPTION);
		return null;
	}

	/**
	 * Crea e mostra la finestra per l'importazione del file
	 * @return file scelto oppure null se non esiste
	 * @param estensione estensione dei file da mostrare nella finestra
	 */
	private File finestraImportaFile(String estensione) {
		JFileChooser fc = new JFileChooser(keepcount.dirSave);
		String nomeFile = ".tmp"; //Qui ci si metterà il file selezionato
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileFilter(new FileFilter()
        {
           
           @Override
           public String getDescription()
           {
              return estensione+" files";
           }

			@Override
			public boolean accept(File f) {
				// TODO Auto-generated method stub
				if (f.isDirectory())
					return true;
				if (f.isFile())
					if (estensione.equals(f.getName().substring(f.getName().lastIndexOf("."))))
						return true;
				return false;
			}
        }
		);
		int result;
		//Rimostro la selezione fino a che seleziono un file valido
		do {
			result = fc.showOpenDialog(source);
			if (result == JFileChooser.APPROVE_OPTION){
				nomeFile = (fc.getSelectedFile()+"");
				File file = new File(nomeFile);
				if (file.exists())
					return file;
				else
					JOptionPane.showMessageDialog(source, "File inesistente, selezionare un file valido");
				}
		} while(result == JFileChooser.APPROVE_OPTION);
		return null;
	}
	
}
