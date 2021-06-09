package domini;

import java.util.Random;

public class TaulerMemory {

	// Delaració de constants	
	public static final int MIDA = 4;// númro de files i columnes de la matriu quadrada	
	private static final int quantsElements = 26;// mida dels elements
	public static final char celdaBuida = '*';// El text que fareu servir com a nul
	// Delaració dels atributs
	private Casella[][] tauler;// Matriu per emmagatzemar les 8 parelles de lletres
	private char[] lletres;// Vector per emmagatzemar les lletres de la A a la Z
	 

	// Constructor
	/* Cal inicialitzar els atributs degudament  i a continuació cridar al mètode omplirTauler()*/
	public TaulerMemory() {
		inicialitzarVectorElememtsParelles() ;
		inicialitzarTauler();
	}		

	public int getMida() {
		return MIDA;
	}
	/*
	 * Retorna una còpia de la matriu tauler no retorna la matriu teuler
	 */
	public String[][] getTauler() {
		String[][] CopiaTauler = new String[MIDA][MIDA];
		for (int i = 0; i < MIDA; i++)
			for (int j = 0; j < MIDA; j++)
				CopiaTauler[i][j] = String.valueOf(tauler[i][j].getContingut());
		return CopiaTauler;
	}

	public String getCeldaBuidal() {
		return String.valueOf(celdaBuida);
	}
	
	/*
	 * Cal emplenar el vector lletres amb els caràcters de l''A' a la 'Z' en total seran
	 * 26 lletres amb els codis enters del 65 al 90 per exemple el int 65 el podem
	 * convertir al caràcter 'A' fent char c = (char) 65;
	 * Aquest mètode és cridat pel constructor.
	 */
	private void inicialitzarVectorElememtsParelles()  {
		lletres = new char[quantsElements];
		char c;
		for (int i = 65; i < 91; i++) {
			c = (char) i;
			lletres[i - 65] = c;
		}
		
		for (int i = 0; i < quantsElements; i++) {
			int randomIndexToSwap = numeroAleatori(quantsElements);
			char temp = lletres[randomIndexToSwap];
			lletres[randomIndexToSwap] = lletres[i];
			lletres[i] = temp;
		}
	}
	
	/* Inicialitzar la matriu tauler amb la casella buida */
	private void inicialitzarTauler() {
		char[][] unTauler = new char[MIDA][MIDA];
		for (int i = 0; i < MIDA; i++)
			for (int j = 0; j < MIDA; j++)
				unTauler[i][j] =  celdaBuida;

		int collocades = 0;
		char lletra;
		while (collocades < MIDA * 2) {
			lletra = lletres[collocades];
			collocarLletra(unTauler, lletra);
			collocarLletra(unTauler, lletra);
			collocades++;
		}
		
		tauler = new Casella[MIDA][MIDA];
		for (int i = 0; i < MIDA; i++)
			for (int j = 0; j < MIDA; j++)
				tauler[i][j] =  new Casella(unTauler[i][j]);
	}
	
	
	/*Retorna un enter entre 0 i max-1, ambdos inclosos */
	private final int numeroAleatori(int max) {
		return (int) (Math.random() * max);
	}


	/*
	 *Col·locar el caràcter de l’argument a la matriu tauler en una casella triada aleatòriament
	 * i que tingui el valor nul.
	 */
	private void collocarLletra(char[][] unTauler, char lletra) {
		boolean noCollocada = true;
		int fila, columna;
		while (noCollocada) {
			fila = numeroAleatori(MIDA);
			columna = numeroAleatori(MIDA);
			if (unTauler[fila][columna] == celdaBuida) {
				unTauler[fila][columna] = lletra;
				noCollocada = false;
			}
		}
	}
	
	public void destapar(int[] coordenada) {
		Casella c = tauler [coordenada[0]][coordenada [1]];
		c.setVisible();
	}

	public void comprovar(int[] coordenada1, int[] coordenada2) {
		Casella c1 = tauler [coordenada1[0]][coordenada1 [1]];
		Casella c2 = tauler [coordenada2[0]][coordenada2 [1]];
		c1.setVisible();
		c2.setVisible();
		
		if (c1.getContingut() != c2.getContingut()) {
			c1.setInvisible();
			c2.setInvisible();
		}
		else
		{
			c1.setAcertada();
			c1.setAcertada();
		}
		
	}

}