package domini;

public class ControladorJocMemory {

	private TaulerMemory jocMemory;//Cal canviar el tipus de l'atribut respecta la versi� 2
	private String[][] tauler;// ser� el que veura el jugador
	private int[][] copsDestapat;// per comptar quantes vegades ha destapat una casella
	private int[] coordenada1, coordenada2;// les coordenades de la parella que entrar� el jugador
	private int intentsPerFerParlles;// Quants intents ha fet el jugador per acabar el joc.

	/*
	 * Constructor: cal inicialitzar els atributs de la classe.
	 * L'atribut tauler haur� de tenir totes les caselles amb el
	 * valor de la casella buida.
	 * Inicialment els dos atributs coordenada seran nuls.
	 * Segons l'argument inicialitzarem el joc amb car�cters o amb
	 * textos
	 */
	public ControladorJocMemory() {
		jocMemory = new TaulerMemory();
		tauler = jocMemory.getTauler();
		this.copsDestapat = new int[jocMemory.getMida()][jocMemory.getMida()];
		this.intentsPerFerParlles = 0;
	}

	// Retorna el tauler que veure el jugador
	public String[][] getTauler() {
		return tauler;
	}

	/*
	 * T� com a finalitat preparar el tauler que es mostrar� al jugador. Si hi ha
	 * algun error retorna un tex explicatiu, altrament retorna nul.Per fer-ho cal:
	 * 1. Si l'argument no �s correcte (m�tode validarCoordenada()) retornar al
	 *    missatge d'error escaient
	 * 2. Si ja ha destapat dues caselles, caldr� comprovar si el seu contingut s�n 
	 *    iguals o no (m�tode comprovarResultat())    
	 * 3. Si la casella de l'argument ja est� destapada, retornar al missatge d'error
	 *    escaient 
	 * 4. Actualitzar la casella de l'atribut tauler amb la informaci� de la 
	 *    coordenada de l'argument. 
	 * 5. Actualitzar l'atribut copsDestapat. 
	 * 6. Caldr� actualitzar l'atribut coordenada1 o coordenada2 depenent de si �s 
	 *    la primera o segona casella que vol destapar. 
	 * 7. Si no hi ha cap error, retornar null.
	 */
	public void casellaADestapar(int[] coordenada) {
		String casella = null;
		if (coordenada1 != null && coordenada2 != null) {
			comprovarResultat();
		}
		try {
			casella = tauler [coordenada[0]][coordenada [1]];			
		} catch (Exception e) {
			throw new IllegalArgumentException ("Error coordenada fora de rang. " + e.getMessage());
		}			
		if (!casella.equals(jocMemory.getCeldaBuidal())) {
			throw new IllegalArgumentException ("Error aquesta casella ja est� destapada.");
		}			
		jocMemory.destapar(coordenada);
		tauler = jocMemory.getTauler();
		copsDestapat[coordenada[0]][coordenada[1]]++;
		if (coordenada1 == null) {
			coordenada1 = coordenada;
		} else {
			coordenada2 = coordenada;
		}
	}		

	/*
	 * Retorna cert si hi ha alguna casella buida i fals altrament
	 * En cas de final de joc (totes les caselles no buide) cal incrementar el 
	 * n�mero d'intens, doncs la �ltima parella no es compte
	 */
	public boolean getContinuarJugant() {
		for (int i = 0; i < tauler.length; i++)
			for (int j = 0; j < tauler.length; j++)
				if (tauler[i][j].equals(jocMemory.getCeldaBuidal()))
					return true;
		intentsPerFerParlles++;
		return false;
	}

	/*
	 * Si el contingut de les caselles que referncien els atributs coordenada1 i
	 * coordenada2 s�n diferents, cal col�locar, a les dues caselles de l'atribut tauler,
	 * la celdaBuida. Un cop fet al pas anterior, sempre caldr�: 
	 * 1. actualitzar l'atribut intentsPerFerParelles. 
	 * 2. possar a nul els dos atributs coordenada.
	 */
	private void comprovarResultat() {
		jocMemory.comprovar(coordenada1,coordenada2);
		
		tauler = jocMemory.getTauler();
		intentsPerFerParlles++;
		coordenada1 = null;
		coordenada2 = null;
	}

	// Retorna el n�mero d'intents per fer parelles
	public int getIntents() {
		return intentsPerFerParlles;
	}

	/*
	 * Retorna un tex segons el nombre d'intents per fer parelles 
	 * 1. Si nom�s 8 intents retorna "Perfecte, menys d'aix� �s impossible"
	 * 2. Si m�s de 8 i menys de 10 retorna "Tents molt bona mem�ria" 
	 * 3. Si m�s de 9 i menys de 16 retorna "Tens bona mem�ria" 
	 * 4. si m�s de 16 retorna "Cal prestar m�s atenci�"
	 */
	public String getFelicitacio() {
		if (intentsPerFerParlles == 8)
			return "Perfecte, menys d'aix� �s impossible";
		if (intentsPerFerParlles > 8 && intentsPerFerParlles < 10)
			return "Tents molt bona mem�ria";
		if (intentsPerFerParlles > 9 && intentsPerFerParlles < 16)
			return "Tens bona mem�ria";
		return "Cal prestar m�s atenci�";
	}

	/*
	 * Retorna la matriu copsDestapat on hi ha la informaci� de quantes vegades s'ha
	 * destapat cada casella del tauler
	 */
	public int[][] getCopsDestapat() {
		return this.copsDestapat;
	}
	
	public String getElement(int [] coordenada) {
		return tauler [coordenada[0]][coordenada[1]];
	}
}