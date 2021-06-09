package presentacio;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domini.ControladorJocMemory;

public class PresentacioSwingJocMemory extends JFrame {

	private JPanel contentPane;
	private ControladorJocMemory ctl;
	private MouseListener mouseListener;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new PresentacioSwingJocMemory ();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PresentacioSwingJocMemory() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 200, 600, 300);
		setTitle("JOC MEMORY");
		this.ctl = new ControladorJocMemory();
		contentPane = new JPanel();		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(4, 4, 10, 10));

		mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
					JButton casella =  (JButton) mouseEvent.getComponent();						
					ferMoviment(casella);
				}
			}
		};

		JButton casella;
		String[][] tauler = ctl.getTauler();
		for (int i = 0; i < tauler.length; i++)
			for (int j = 0; j < tauler[0].length; j++) {
				casella = new JButton();
				casella.setName(i+"-"+j);//Utilitzem l'atribut Name per guardar les coordenades
				casella.addMouseListener(mouseListener);
				contentPane.add(casella);
			}
		actualitzarCasellesGrafiques();
		this.setVisible(true);
	}	

	protected void ferMoviment(JButton casella) {
		try {
			destaparCasella(casella);
			if (!ctl.getContinuarJugant()) {
				fiJoc();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error al destapar casella", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void destaparCasella(JButton casella) {
		String coordenadesName = casella.getName();
		String[] coordenadesString = coordenadesName.split("-");
		int [] coordenades = new int [2];
		coordenades[0] = Integer.valueOf(coordenadesString[0]);
		coordenades[1] = Integer.valueOf(coordenadesString[1]);
		ctl.casellaADestapar(coordenades);
		actualitzarCasellesGrafiques();
	}	
	
	private void actualitzarCasellesGrafiques() {
		String[][] tauler = ctl.getTauler();
		int indexComponent = 0;
		String element;
		JButton casella;
		for(int i = 0; i < tauler.length; i++)
			for(int j = 0; j < tauler[0].length; j++) {
				element = tauler [i][j];
				casella = (JButton) contentPane.getComponent(indexComponent);
				casella.setText(element);
				indexComponent ++;
			}		
	}
	
	private void mostrarCopsDestapat() {
		int[][] copsDestapat = this.ctl.getCopsDestapat();
		int indexComponent = 0;
		JButton casella;
		String cops;
		for(int i = 0; i < copsDestapat.length; i++)
			for(int j = 0; j < copsDestapat[0].length; j++) {
				cops = String.valueOf(copsDestapat [i][j]);
				casella = (JButton) contentPane.getComponent(indexComponent);
				casella.setText(casella.getText()+ "-"+ cops);
				casella.removeMouseListener(mouseListener);
				indexComponent ++;
			}	
	}

	private void fiJoc() {
		mostrarCopsDestapat();
		JOptionPane.showMessageDialog(this, "Nombre d'intents " + ctl.getIntents() + ". " + ctl.getFelicitacio(),
				"Fi joc", JOptionPane.INFORMATION_MESSAGE);		
	}
}