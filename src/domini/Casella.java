package domini;

public class Casella {
	private final char contingut;
	private int estat;
	/* Possibles estats 
	 * 0 - oculta, mostra TaulerMemory.celdaBuida
	 * 1 - visible, mostra el seu valor de manera temporal
	 * 2 - acertada, mostra el seu valor definitivament
	 */

	public Casella( char contingut)
	{
		this.contingut = contingut;
		this.estat = 0;
	}
	
	public char getContingut() {
		if( estat == 0)
			return TaulerMemory.celdaBuida;
		return contingut;
	}
	
	public void setInvisible() {
		this.estat = 0;
	}
	
	public void setVisible() {
		this.estat = 1;
	}
	
	public void setAcertada() {
		this.estat = 2;
	}
	
}
