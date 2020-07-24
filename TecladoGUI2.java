
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.border.Border;

public class TecladoGUI2 extends JFrame {
	
	private String[] linha1 = { "~", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "+", "Backspace" }; // tamanho = 14
	private int[] keyCodeL1 = { KeyEvent.VK_DEAD_TILDE, KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5,
			KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9, KeyEvent.VK_0, KeyEvent.VK_MINUS, 107, KeyEvent.VK_BACK_SPACE };
	
	private String[] linha2 = { "Tab", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "[", "]", "\\" }; 		// tamanho = 14
	private int[] keyCodeL2 = { KeyEvent.VK_TAB, KeyEvent.VK_Q, KeyEvent.VK_W, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_T, KeyEvent.VK_Y,
			KeyEvent.VK_U, KeyEvent.VK_I, KeyEvent.VK_O, KeyEvent.VK_P, 91, 93, KeyEvent.VK_BACK_SLASH };
	
	private String[] linha3 = { "Caps", "A", "S", "D", "F", "G", "H", "J", "K", "L", ":", "*", "Enter" }; 		// tamanho = 13
	private int[] keyCodeL3 = { KeyEvent.VK_CAPS_LOCK, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_H,
			KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_SEMICOLON, 106, KeyEvent.VK_ENTER }; 		
	
	private String[] linha4 = { "Shift", "Z", "X", "C", "V", "B", "N", "M", ",", ".", "?", "/\\" }; 			// tamanho = 12
	private int[] keyCodeL4 = { KeyEvent.VK_SHIFT, KeyEvent.VK_Z, KeyEvent.VK_X, KeyEvent.VK_C, KeyEvent.VK_V, KeyEvent.VK_B, KeyEvent.VK_N,
			KeyEvent.VK_M, KeyEvent.VK_COMMA, KeyEvent.VK_PERIOD, KeyEvent.VK_SLASH, KeyEvent.VK_UP }; 		
	
	private String[] linha5 = { null, "<", "\\/", ">" };														// tamanho = 04	
	private int[] keyCodeL5 = { KeyEvent.VK_SPACE, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT };
	
	private Tecla[] botoesLinha1 = new Tecla[linha1.length];
	private Tecla[] botoesLinha2 = new Tecla[linha2.length];
	private Tecla[] botoesLinha3 = new Tecla[linha3.length];
	private Tecla[] botoesLinha4 = new Tecla[linha4.length];
	private Tecla[] botoesLinha5 = new Tecla[linha5.length];

	private static final int l = 45, i = 5; // "l" para as dimensoes dos botoes das teclas com string unitaria (letras, etc);
											// "i" para os insets (espaços entre botoes, linhas e colunas)
	private BotoesPanel botoes;
	private JPanel areaTexto, vazioWest, vazioEast;
	private JTextArea texto;
	private JLabel notificacoes;
	
	private String conteudo;

	public TecladoGUI2() {
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
		texto = new JTextArea("");
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
			botoesLinha1[count] = new Tecla(linha1[count], keyCodeL1[count]);
			
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
			botoesLinha2[count] = new Tecla(linha2[count], keyCodeL2[count]);
			
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

			botoesLinha3[count] = new Tecla(linha3[count], keyCodeL3[count]);							
			
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

			botoesLinha4[count] = new Tecla(linha4[count], keyCodeL4[count]);
			
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

			botoesLinha5[count] = new Tecla(linha5[count], keyCodeL5[count]);
            
			//tamanho
			if ( count == 0 )
				botoesLinha5[count].setPreferredSize(new Dimension( ( (int)(6*l+8*i-1) ), l) ); // barra de espaço 
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
	
	public class Tecla extends JButton {
		public final int keyCode;
        public final String nome;
        
        public Tecla ( String nome, int keyCode ) {
            this.keyCode = keyCode;
            this.nome = nome;
            this.setText(nome);
            this.setBorder(new RoundedBorder(10));
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
	
	public class AcaoTeclas implements KeyListener {
		private Color padrao = (new JButton().getBackground());
		@Override // trata pressionamento de qualquer tecla
		public void keyPressed(KeyEvent event) {
			//System.out.println(event.getExtendedKeyCode());
			for (int count = 0; count < linha1.length; count++) {
				if ( event.getExtendedKeyCode() == keyCodeL1[count] ) 
					botoesLinha1[count].setBackground(Color.BLUE);
					//botoesLinha1[count].doClick(); --> efeito do clik MAS FICA TRAVADO PACAS
				else if ( event.getExtendedKeyCode() == keyCodeL2[count] ) 
					botoesLinha2[count].setBackground(Color.BLUE);
				else if ( count == 0 && event.getExtendedKeyCode() == keyCodeL3[count] )
					if ( botoesLinha3[0].getBackground() == padrao )
						botoesLinha3[0].setBackground(Color.BLUE);
					else
						botoesLinha3[0].setBackground(padrao);
				else if ( count != 0 && count < keyCodeL3.length && event.getExtendedKeyCode() == keyCodeL3[count] ) {
						botoesLinha3[count].setBackground(Color.BLUE);		
						if (count == keyCodeL3.length - 1 ) { // ENTER -> capturar a string do textfield (mas tambem pula linha...)
							conteudo = texto.getText(); // IMPLEMENTAR TRY CATCH AQUI!
							System.out.println("CONTEUDO = " + conteudo);	
						}
				}
				else if ( count < keyCodeL4.length && event.getExtendedKeyCode() == keyCodeL4[count] ) 
					botoesLinha4[count].setBackground(Color.BLUE);
				else if ( count < keyCodeL5.length && event.getExtendedKeyCode() == keyCodeL5[count] ) 
					botoesLinha5[count].setBackground(Color.BLUE);
					
			}
		}
		
		@Override
		public void keyReleased(KeyEvent event) {
			for (int count = 0; count < linha1.length; count++) {
				if ( event.getExtendedKeyCode() == keyCodeL1[count] )
					botoesLinha1[count].setBackground(padrao);
				else if ( event.getExtendedKeyCode() == keyCodeL2[count] )
					botoesLinha2[count].setBackground(padrao);
				else if ( count != 0 && count < keyCodeL3.length && event.getExtendedKeyCode() == keyCodeL3[count] ) 
					botoesLinha3[count].setBackground(padrao);
				else if ( count < keyCodeL4.length && event.getExtendedKeyCode() == keyCodeL4[count] ) 
					botoesLinha4[count].setBackground(padrao);
				else if ( count < keyCodeL5.length && event.getExtendedKeyCode() == keyCodeL5[count] ) 
					botoesLinha5[count].setBackground(padrao);
			}
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public static void main(String[] args) {
		TecladoGUI2 print = new TecladoGUI2();
		print.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		print.setSize(950, 700); // set frame size
		print.setVisible(true); // display frame
	} //
}
