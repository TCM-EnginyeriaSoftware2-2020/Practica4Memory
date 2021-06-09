package domini;

public class ControladorJocMemory {

	private TaulerMemory jocMemory;//Cal canviar el tipus de l'atribut respecta la versió 2
	private String[][] tauler;// serà el que veura el jugador
	private int[][] copsDestapat;// per comptar quantes vegades ha destapat una casella
	private int[] coordenada1, coordenada2;// les coordenades de la parella que entrarà el jugador
	private int intentsPerFerParlles;// Quants intents ha fet el jugador per acabar el joc.

	/*
	 * Constructor: cal inicialitzar els atributs de la classe.
	 * L'atribut tauler haurà de tenir totes les caselles amb el
	 * valor de la casella buida.
	 * Inicialment els dos atributs coordenada seran nuls.
	 * Segons l'argument inicialitzarem el joc amb caràcters o amb
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
	 * Té com a finalitat preparar el tauler que es mostrarà al jugador. Si hi ha
	 * algun error retorna un tex explicatiu, altrament retorna nul.Per fer-ho cal:
	 * 1. Si l'argument no és correcte (mètode validarCoordenada()) retornar al
	 *    missatge d'error escaient
	 * 2. Si ja ha destapat dues caselles, caldrà comprovar si el seu contingut són 
	 *    iguals o no (mètode comprovarResultat())    
	 * 3. Si la casella de l'argument ja està destapada, retornar al missatge d'error
	 *    escaient 
	 * 4. Actualitzar la casella de l'atribut tauler amb la informació de la 
	 *    coordenada de l'argument. 
	 * 5. Actualitzar l'atribut copsDestapat. 
	 * 6. Caldrà actualitzar l'atribut coordenada1 o coordenada2 depenent de si és 
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
			throw new IllegalArgumentException ("Error aquesta casella ja està destapada.");
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
	 * número d'intens, doncs la última parella no es compte
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
	 * coordenada2 són diferents, cal col·locar, a les dues caselles de l'atribut tauler,
	 * la celdaBuida. Un cop fet al pas anterior, sempre caldrà: 
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

	// Retorna el número d'intents per fer parelles
	public int getIntents() {
		return intentsPerFerParlles;
	}

	/*
	 * Retorna un tex segons el nombre d'intents per fer parelles 
	 * 1. Si només 8 intents retorna "Perfecte, menys d'això és impossible"
	 * 2. Si més de 8 i menys de 10 retorna "Tents molt bona memòria" 
	 * 3. Si més de 9 i menys de 16 retorna "Tens bona memòria" 
	 * 4. si més de 16 retorna "Cal prestar més atenció"
	 */
	public String getFelicitacio() {
		if (intentsPerFerParlles == 8)
			return "Perfecte, menys d'això és impossible";
		if (intentsPerFerParlles > 8 && intentsPerFerParlles < 10)
			return "Tents molt bona memòria";
		if (intentsPerFerParlles > 9 && intentsPerFerParlles < 16)
			return "Tens bona memòria";
		return "Cal prestar més atenció";
	}

	/*
	 * Retorna la matriu copsDestapat on hi ha la informació de quantes vegades s'ha
	 * destapat cada casella del tauler
	 */
	public int[][] getCopsDestapat() {
		return this.copsDestapat;
	}
	
	public String getElement(int [] coordenada) {
		return tauler [coordenada[0]][coordenada[1]];
	}
}