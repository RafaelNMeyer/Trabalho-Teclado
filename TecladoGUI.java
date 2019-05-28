
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.border.Border;

public class TecladoGUI extends JFrame {
	
	private String[] linha1 = { "~", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "+", "Backspace" }; // tamanho = 14
	private String[] linha2 = { "Tab", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "[", "]", "\\" }; 		// tamanho = 14
	private String[] linha3 = { "Caps", "A", "S", "D", "F", "G", "H", "J", "K", "L", ":", "*", "Enter" }; 		// tamanho = 13
	private String[] linha4 = { "Shift", "Z", "X", "C", "V", "B", "N", "M", ",", ".", "?", "/\\" }; 			// tamanho = 12
	private String[] linha5 = { null, "<", "\\/", ">" };														// tamanho = 04	

	private JButton[] botoesLinha1 = new JButton[linha1.length];
	private JButton[] botoesLinha2 = new JButton[linha2.length];
	private JButton[] botoesLinha3 = new JButton[linha3.length];
	private JButton[] botoesLinha4 = new JButton[linha4.length];
	private JButton[] botoesLinha5 = new JButton[linha5.length];
	
	private static final int l = 45, i = 5; // "l" para as dimensoes dos botoes das teclas com string unitaria (letras, etc);
											// "i" para os insets (espaços entre botoes, linhas e colunas)
	private BotoesPanel botoes;
	private Color padraoButtons = UIManager.getColor ( "alala.background" );
	
	private JPanel areaTexto, vazioWest, vazioEast;
	private JTextArea texto;
	private JLabel notificacoes;

	public TecladoGUI() {
		super("pangrama");
		setLayout(new BorderLayout());
		
		notificacoes = new JLabel("Ainda não foi implementado");
		add(notificacoes, BorderLayout.NORTH);
		
		vazioWest = new JPanel();
		vazioEast = new JPanel();
		add(vazioWest, BorderLayout.WEST);
		add(vazioEast, BorderLayout.EAST);
		
		areaTexto = new JPanel();
		areaTexto.setBackground(Color.WHITE);
		areaTexto.setPreferredSize(new Dimension(900, 500));
		texto = new JTextArea();
		texto.setEditable(true);
		texto.addKeyListener(new AcaoTeclas());
		areaTexto.add(texto);
		add(areaTexto, BorderLayout.CENTER);
		
		botoes = new BotoesPanel();
		botoes.constraints.anchor = GridBagConstraints.WEST;
		botoes.constraints.insets = new Insets(0, i, i, 0); // define espaço entre cada botao e cada linha de botoes
		add(botoes, BorderLayout.SOUTH);
		// linha 1 e 2
		for (int count = 0; count < linha1.length; count++) {
			
			// linha 1
			botoesLinha1[count] = new JButton(linha1[count]);
			botoesLinha1[count].setBorder(new RoundedBorder(10));
			
			
			// definindo tamanho dos botoes de acordo com a posição no array dos botoes
			if ( count < linha1.length - 1 ) 
				botoesLinha1[count].setPreferredSize(new Dimension(l,l));
			else if ( count == linha1.length - 1 ) // tamanho do backspace
				botoesLinha1[count].setPreferredSize(new Dimension( ( (int) (2*l+2*i) ), l) ); // Backspace
			
			//adicionando e organizando alinhamento dos botoes
			if ( count == 0 )
				botoes.addComponent(botoesLinha1[count], 1, count, 1, 1);
			else if ( count == 1 ) { // coloca o 2º butao na 1ª celula (coluna)
				botoes.constraints.insets = new Insets(0, l+2*i, i, 0);
				botoes.addComponent(botoesLinha1[count], 1, count-1, 1, 1);
				botoes.constraints.insets = new Insets(0, i, i, 0);
			}
			else if ( count == 2 ) { // coloca o 3º butao na 1ª celula (coluna)
				botoes.constraints.insets = new Insets(0, 2*l+3*i, i, 0);
				botoes.addComponent(botoesLinha1[count], 1, count-2, 1, 1);
			}
			else if ( count == botoesLinha1.length - 1 ) { // regula a largura do backspace p/ 3 colunas
				botoes.constraints.insets = new Insets(0, i, i, -(i+3));
				botoes.addComponent(botoesLinha1[count], 1, count-2, 3, 1);
				botoes.constraints.insets = new Insets(0, i, i, 0);
			}	
			else 
				botoes.addComponent(botoesLinha1[count], 1, count-2, 1, 1);

			// linha2
			botoesLinha2[count] = new JButton(linha2[count]);
			botoesLinha2[count].setBorder(new RoundedBorder(10));
			
			// definindo tamanho dos botoes de acordo com a posição no array dos botoes
			if ( count > 0 ) 
				botoesLinha2[count].setPreferredSize(new Dimension(l,l));
			else 
				botoesLinha2[count].setPreferredSize(new Dimension( ( (int)(1.5*l + i) ), l) ); // Tab

			//adicionando e organizando alinhamento dos botoes
			if ( count == 0 )
				botoes.addComponent(botoesLinha2[count], 2, count, 1, 1);
			else if ( count == 1 ) {
				botoes.constraints.insets = new Insets(0, (int)(1.5*l + i)+2*i, i, 0);
				botoes.addComponent(botoesLinha2[count], 2, count-1, 1, 1);
				botoes.constraints.insets = new Insets(0, i, i, 0);
			}
			else if ( count == botoesLinha2.length - 1 ) {
				botoes.constraints.insets = new Insets(0, i, i, -(i+3));
				botoes.addComponent(botoesLinha2[count], 2, count-1, 2, 1);
			}
			else {//if ( count == 2 ) {
				botoes.constraints.insets = new Insets(0, -(((l-i)/2)-(int)i/2), i, 0);
				botoes.addComponent(botoesLinha2[count], 2, count-1, 1, 1);
				botoes.constraints.insets = new Insets(0, i, i, 0);
			}
		}

		for (int count = 0; count < linha3.length; count++) {

			botoesLinha3[count] = new JButton(linha3[count]);							
			botoesLinha3[count].setBorder(new RoundedBorder(10));
			
			// definindo tamanho dos botoes de acordo com a posição no array dos botoes
			if ( count == 0 ) 
				botoesLinha3[count].setPreferredSize(new Dimension( ( (int)(1.5*l + i) ), l) ); // Caps (= tamanho do Tab -> ver formula)
			else if ( count > 0 && count < linha3.length - 1) 
				botoesLinha3[count].setPreferredSize(new Dimension( l, l ));
			else 
				botoesLinha3[count].setPreferredSize(new Dimension( (int)(2*l + i), l )); // Enter	
			
			//adicionando e organizando alinhamento dos botoes
			if ( count == 0 )
				botoes.addComponent(botoesLinha3[count], 3, count, 1, 1);
			else if ( count == 1 ) {
				botoes.constraints.insets = new Insets(0, (int)(1.5*l +i)+2*i, i, 0);
				botoes.addComponent(botoesLinha3[count], 3, count-1, 1, 1);
				botoes.constraints.insets = new Insets(0, i, i, 0);
			}
			else if ( count == linha3.length - 1 ) { // condição para o ultimo botao (enter) ocupar 2 colunas (p/ alinhar os botoes)
				botoes.constraints.insets = new Insets(0, -(((l-i)/2)-(int)i/2), i, -(i+3));
				botoes.addComponent(botoesLinha3[count], 3, count-1, 2, 1);	
				botoes.constraints.insets = new Insets(0, i, i, 0);
			}
			else {
				botoes.constraints.insets = new Insets(0, -(((l-i)/2)-(int)i/2), i, 0);
				botoes.addComponent(botoesLinha3[count], 3, count-1, 1, 1);	
			}
			
		}

		for (int count = 0; count < linha4.length; count++) {

			botoesLinha4[count] = new JButton(linha4[count]);
			botoesLinha4[count].setBorder(new RoundedBorder(10));
			
			// tamanho
			if ( count == 0 ) 
				botoesLinha4[count].setPreferredSize(new Dimension( ( (int)(2*l + i) ), l) ); // Shift (= tamanho do Tab -> ver formula)
			else 
				botoesLinha4[count].setPreferredSize(new Dimension( l, l ));	
			
			// add e organizar
			if ( count == 0 )
				botoes.addComponent(botoesLinha4[count], 4, count, 1, 1);
			else if ( count == 1 ) {
				botoes.constraints.insets = new Insets(0, (int)(2*l + i) + 2*i, i, 0);
				botoes.addComponent(botoesLinha4[count], 4, count-1, 1, 1);
				botoes.constraints.insets = new Insets(0, i, i, 0);
			}
			else if ( count == linha4.length - 1 ) {
				botoes.constraints.insets = new Insets(0, -((int)(l/2) - 4), i, -(i+3)); // AQUI FOI NA CAGADA ESSE 4 (entao cuidado ao alterar as constantes!!!)
				botoes.addComponent(botoesLinha4[count], 4, count/* (+1) */, 1, 1); // separar a /\ dos outros bts
			}
			else 
				botoes.addComponent(botoesLinha4[count], 4, count-1, 1, 1);	
			
		}

		for (int count = 0; count < linha5.length; count++) {

			botoesLinha5[count] = new JButton(linha5[count]);
			botoesLinha5[count].setBorder(new RoundedBorder(10));
			
			//tamanho
			if ( count == 0 )
				botoesLinha5[count].setPreferredSize(new Dimension( ( (int)(6*l+8*i-1) ), l) ); // barra de espaço (tentar trocar p 8*5)
			else 
				botoesLinha5[count].setPreferredSize(new Dimension( l, l ));	
			
			// add e org
			if ( count == 0 ) {
				botoes.constraints.insets = new Insets(0, 0, i, 0);
				botoes.addComponent(botoesLinha5[count], 5, 2, 7, 1); // barra de espaco
			}
			else  if  (count == botoesLinha5.length - 1) {
				botoes.constraints.insets = new Insets(0, i, i, -(i+3));
				botoes.addComponent(botoesLinha5[count], 5, 9+count, 2, 1);
			}
			else {
				botoes.constraints.insets = new Insets(0, -((int)(l/2) - 4), i, 0);
				//botoes.constraints.insets = new Insets(0, i, i, 0);
				botoes.addComponent(botoesLinha5[count], 5, 9+count, 1, 1);
			}
				
		}
	}

	public class BotoesPanel extends JPanel {

		private GridBagLayout layout;
		private GridBagConstraints constraints;

		public BotoesPanel() {
			layout = new GridBagLayout();
			setLayout(layout);
			constraints = new GridBagConstraints();
			constraints.anchor = GridBagConstraints.WEST;
		}

		private void addComponent(Component component, int row, int column, int width, int height) {
			constraints.gridx = column;
			constraints.gridy = row;
			constraints.gridwidth = width;
			constraints.gridheight = height;
			layout.setConstraints(component, constraints);
			add(component);
		}

	}

	public static class RoundedBorder implements Border {

		private int radius;

		RoundedBorder(int radius) {
			this.radius = radius;
		}

		public Insets getBorderInsets(Component c) {
			return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
		}

		public boolean isBorderOpaque() {
			return true;
		}

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			// TODO Auto-generated method stub
			g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
		}

	}
	// $$$$$$$$$$$$$$$$$$$$$ PAREI AQUIIIIII $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
	public class AcaoTeclas implements KeyListener {
		@Override // trata pressionamento de qualquer tecla
		public void keyPressed(KeyEvent event) {
			for (int count = 0; count < linha1.length; count++)
				if ( KeyEvent.getKeyText(event.getKeyCode()).equals(linha1[count]) ) {
					System.out.println(KeyEvent.getKeyText(event.getKeyCode()));
					botoesLinha1[count].setBackground(Color.BLUE);
				}
																					
		}

		@Override
		public void keyReleased(KeyEvent event) {
			for (int count = 0; count < linha1.length; count++)
				if ( KeyEvent.getKeyText(event.getKeyCode()).equals(linha1[count]) ) {
					System.out.println(KeyEvent.getKeyText(event.getKeyCode()));
					botoesLinha1[count].setBackground(new JButton().getBackground());
				}
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public static void main(String[] args) {
		TecladoGUI print = new TecladoGUI();
		print.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		print.setSize(950, 700); // set frame size
		print.setVisible(true); // display frame
	} //
}
