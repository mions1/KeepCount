package util;


/**
 * Filtro transazioni, utilizzato per filtrare le transazioni
 * secondo le regole settate dall'utente nella tabella
 * @author Simone
 *
 */
public class FiltroTransazioni {

	Periodo periodo; //Periodo da-a transazioni
	String filtro; //Filtro testuale
	int tipo; //Prelievo, deposito o tutto
	
	public FiltroTransazioni(Periodo periodo,String filtro,int tipo) {
			this.periodo = periodo;
			this.filtro = filtro;
			this.tipo = tipo;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public String getFiltro() {
		return filtro;
	}

	public int getTipo() {
		return tipo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
}
