import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;

public class TecladoGUI extends JFrame {

	private SequentialFileManager manager;
	private ArrayList<ConteudoSerializable> historicoFrases = new ArrayList<ConteudoSerializable>();
	private String mostraHistorico;

	private String[] linha1 = { "~", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "+", "Backspace" };
	private int[] keyCodeL1 = { KeyEvent.VK_DEAD_TILDE, KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4,
			KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9, KeyEvent.VK_0, KeyEvent.VK_MINUS,
			107, KeyEvent.VK_BACK_SPACE };

	private String[] linha2 = { "Tab", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "[", "]", "\\" };
	private int[] keyCodeL2 = { KeyEvent.VK_TAB, KeyEvent.VK_Q, KeyEvent.VK_W, KeyEvent.VK_E, KeyEvent.VK_R,
			KeyEvent.VK_T, KeyEvent.VK_Y, KeyEvent.VK_U, KeyEvent.VK_I, KeyEvent.VK_O, KeyEvent.VK_P, 91, 93,
			KeyEvent.VK_BACK_SLASH };

	private String[] linha3 = { "Caps", "A", "S", "D", "F", "G", "H", "J", "K", "L", ":", "*", "Enter" };
	private int[] keyCodeL3 = { KeyEvent.VK_CAPS_LOCK, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_F,
			KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_SEMICOLON, 106,
			KeyEvent.VK_ENTER };

	private String[] linha4 = { "Shift", "Z", "X", "C", "V", "B", "N", "M", ",", ".", "?", "/\\" };
	private int[] keyCodeL4 = { KeyEvent.VK_SHIFT, KeyEvent.VK_Z, KeyEvent.VK_X, KeyEvent.VK_C, KeyEvent.VK_V,
			KeyEvent.VK_B, KeyEvent.VK_N, KeyEvent.VK_M, KeyEvent.VK_COMMA, KeyEvent.VK_PERIOD, KeyEvent.VK_SLASH,
			KeyEvent.VK_UP };

	private String[] linha5 = { null, "<", "\\/", ">" };
	private int[] keyCodeL5 = { KeyEvent.VK_SPACE, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT };

	private Tecla[] botoesLinha1 = new Tecla[linha1.length];
	private Tecla[] botoesLinha2 = new Tecla[linha2.length];
	private Tecla[] botoesLinha3 = new Tecla[linha3.length];
	private Tecla[] botoesLinha4 = new Tecla[linha4.length];
	private Tecla[] botoesLinha5 = new Tecla[linha5.length];

	private static final int l = 45, i = 5; // "l" para as dimensoes dos botoes das teclas com string unitaria (letras,
											// . , / etc);
											// "i" para os insets (espaços entre botoes, linhas e colunas)
	private BotoesPanel botoes;
	private JPanel areaTexto;
	private JTextArea texto;
	private JPanel panelNotificacoes;
	private JLabel labelNotificacoes, labelResultado;

	private JTextArea historicoText;

	private String conteudo;
	private String frases[] = { "Um pequeno jabuti xereta viu dez cegonhas felizes.",
			"Quem traz CD, LP, fax, engov e whisky JB?", "Gazeta publica hoje breve nota de faxina na quermesse.",
			"Jovem craque belga prediz falhas no xote.", "Bancos fúteis pagavam-lhe queijo, whisky e xadrez." };
	private int fraseSelecionada = -1, acerto = 0, erro = 0;

	public TecladoGUI() {
		super("Professor de digitação");
		// logica para ler o conteudo do arquivo serializavel no início da execução do
		// programa
		mostraHistorico = "";
		historicoText = new JTextArea();

		manager = new SequentialFileManager(); // classe que administra as operacoes de criar e modificar o file
		manager.openFileOutput();
		manager.openFileInput();
		try {
			historicoFrases = manager.readRecords();
		} catch (NullPointerException vazio) {
			// exceção para a primeira leitura do arquivo ( pois o arquivo estará vazio )
		}
		manager.closeFileOutput();

		// lógica para atribuir o conteudo capturado do arquivo serializado para a
		// JTextArea da aba histórico
		if (historicoFrases.isEmpty()) {
			mostraHistorico = "";
			historicoText.setText("");
		} else {
			historicoText.setRows(historicoFrases.size());
			mostraHistorico = "Histórico de tentativas:\n\n";
			for (int i = 0; i < historicoFrases.size(); i++)
				mostraHistorico += ("Frase: " + historicoFrases.get(i).getFrase() + "\nAcertos: "
						+ historicoFrases.get(i).getAcertos() + "/" + historicoFrases.get(i).getTamanho() + "\tErros: "
						+ historicoFrases.get(i).getErros() + "\n\n");
			historicoText.setText(mostraHistorico);
		}

		// declaracao das abas
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel abaTeclado = new JPanel();
		tabbedPane.addTab("Teclado", null, abaTeclado, "Teclado");
		JPanel abaPangramas = new JPanel();
		abaPangramas.setLayout(new BorderLayout());
		tabbedPane.addTab("Pangramas", null, abaPangramas, "Pangramas");
		JPanel historico = new JPanel();

		historicoText.setEditable(false);
		historicoText.setVisible(true);
		historicoText.setPreferredSize(new Dimension(900, 650));
		historico.add(historicoText);
		tabbedPane.addTab("Histórico", null, historico, "Histórico");
		add(tabbedPane);

		JPanel frasesPangramas = new JPanel();
		frasesPangramas.setLayout(new GridLayout(5, 1));
		JLabel explicaPangrama = new JLabel(
				"Selecione um dos pangramas a seguir, e retorne a aba \"Teclado\" para testar sua habilidade:");
		explicaPangrama.setPreferredSize(new Dimension(500, 125));
		JPanel vazio = new JPanel();
		vazio.setPreferredSize(new Dimension(500, 300));
		abaPangramas.add(vazio, BorderLayout.SOUTH);
		abaPangramas.add(frasesPangramas, BorderLayout.CENTER);
		abaPangramas.add(explicaPangrama, BorderLayout.NORTH);

		JRadioButton[] botoesFrases = new JRadioButton[frases.length];
		ButtonGroup selecionaPangrama = new ButtonGroup();
		for (int count = 0; count < frases.length; count++) {
			botoesFrases[count] = new JRadioButton(frases[count]);
			frasesPangramas.add(botoesFrases[count]);
			selecionaPangrama.add(botoesFrases[count]);
			botoesFrases[count].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					for (int count = 0; count < botoesFrases.length; count++) {
						if (botoesFrases[count].isSelected()) {
							System.out.println(frases[count].length());
							labelNotificacoes.setText(frases[count]);
							fraseSelecionada = count;
							acerto = 0;
							erro = 0;
							labelResultado.setText("");
							break;
						} // end if
					} // end for
				}
			});
		}

		abaTeclado.setLayout(new BorderLayout());
		String abaTeste = "Teste sua habilidade escolhendo um pangrama na aba \"Pangramas\".";
		panelNotificacoes = new JPanel();
		panelNotificacoes.setLayout(new BorderLayout());
		labelNotificacoes = new JLabel(abaTeste, SwingConstants.CENTER);
		labelNotificacoes.setPreferredSize(new Dimension(100, 40));
		labelResultado = new JLabel("", SwingConstants.CENTER);
		labelResultado.setPreferredSize(new Dimension(100, 10));
		panelNotificacoes.add(labelNotificacoes, BorderLayout.NORTH);
		panelNotificacoes.add(labelResultado, BorderLayout.CENTER);
		abaTeclado.add(panelNotificacoes, BorderLayout.CENTER);
		abaTeclado.add(new JPanel(), BorderLayout.WEST);
		abaTeclado.add(new JPanel(), BorderLayout.EAST);

		areaTexto = new JPanel();
		areaTexto.setBackground(Color.WHITE);
		areaTexto.setPreferredSize(new Dimension(500, 250));
		texto = new JTextArea();
		texto.setEditable(true);
		texto.addKeyListener(new AcaoTeclas());
		areaTexto.add(texto);
		abaTeclado.add(areaTexto, BorderLayout.NORTH);

		// lógica para configurar a posição dos botoes no teclado virtual
		botoes = new BotoesPanel(); // a classe BotoesPanel possui como layout padrao o GridBagLayout
		botoes.constraints.anchor = GridBagConstraints.WEST;
		botoes.constraints.insets = new Insets(0, i, i, 0); // define espaço entre cada botao e cada linha de botoes
		abaTeclado.add(botoes, BorderLayout.SOUTH);

		// linhas 1 e 2
		for (int count = 0; count < linha1.length; count++) {

			// linha 1
			botoesLinha1[count] = new Tecla(linha1[count], keyCodeL1[count]);

			// definindo tamanho dos botoes de acordo com a posição no array dos botoes
			if (count < linha1.length - 1)
				botoesLinha1[count].setPreferredSize(new Dimension(l, l));
			else if (count == linha1.length - 1) // tamanho do backspace
				botoesLinha1[count].setPreferredSize(new Dimension(((int) (2 * l + 2 * i)), l)); // Backspace

			// adicionando e organizando alinhamento dos botoes
			if (count == 0)
				botoes.addComponent(botoesLinha1[count], 1, count, 1, 1);
			else if (count == 1) { // coloca o 2º butao na 1ª celula (coluna)
				botoes.constraints.insets = new Insets(0, l + 2 * i, i, 0);
				botoes.addComponent(botoesLinha1[count], 1, count - 1, 1, 1);
				botoes.constraints.insets = new Insets(0, i, i, 0);
			} else if (count == 2) { // coloca o 3º butao na 1ª celula (coluna)
				botoes.constraints.insets = new Insets(0, 2 * l + 3 * i, i, 0);
				botoes.addComponent(botoesLinha1[count], 1, count - 2, 1, 1);
			} else if (count == botoesLinha1.length - 1) { // regula a largura do backspace p/ 3 colunas
				botoes.constraints.insets = new Insets(0, i, i, -(i + 3));
				botoes.addComponent(botoesLinha1[count], 1, count - 2, 3, 1);
				botoes.constraints.insets = new Insets(0, i, i, 0);
			} else
				botoes.addComponent(botoesLinha1[count], 1, count - 2, 1, 1);

			// linha2
			botoesLinha2[count] = new Tecla(linha2[count], keyCodeL2[count]);

			// definindo tamanho dos botoes de acordo com a posição no array dos botoes
			if (count > 0)
				botoesLinha2[count].setPreferredSize(new Dimension(l, l));
			else
				botoesLinha2[count].setPreferredSize(new Dimension(((int) (1.5 * l + i)), l)); // Tab

			// adicionando e organizando alinhamento dos botoes
			if (count == 0)
				botoes.addComponent(botoesLinha2[count], 2, count, 1, 1);
			else if (count == 1) {
				botoes.constraints.insets = new Insets(0, (int) (1.5 * l + i) + 2 * i, i, 0);
				botoes.addComponent(botoesLinha2[count], 2, count - 1, 1, 1);
				botoes.constraints.insets = new Insets(0, i, i, 0);
			} else if (count == botoesLinha2.length - 1) {
				botoes.constraints.insets = new Insets(0, i, i, -(i + 3));
				botoes.addComponent(botoesLinha2[count], 2, count - 1, 2, 1);
			} else {// if ( count == 2 ) {
				botoes.constraints.insets = new Insets(0, -(((l - i) / 2) - (int) i / 2), i, 0);
				botoes.addComponent(botoesLinha2[count], 2, count - 1, 1, 1);
				botoes.constraints.insets = new Insets(0, i, i, 0);
			}
		}

		for (int count = 0; count < linha3.length; count++) {

			botoesLinha3[count] = new Tecla(linha3[count], keyCodeL3[count]);

			// definindo tamanho dos botoes de acordo com a posição no array dos botoes
			if (count == 0)
				botoesLinha3[count].setPreferredSize(new Dimension(((int) (1.5 * l + i)), l)); // Caps (= tamanho do Tab
																								// -> ver formula)
			else if (count > 0 && count < linha3.length - 1)
				botoesLinha3[count].setPreferredSize(new Dimension(l, l));
			else
				botoesLinha3[count].setPreferredSize(new Dimension((int) (2 * l + i), l)); // Enter

			// adicionando e organizando alinhamento dos botoes
			if (count == 0)
				botoes.addComponent(botoesLinha3[count], 3, count, 1, 1);
			else if (count == 1) {
				botoes.constraints.insets = new Insets(0, (int) (1.5 * l + i) + 2 * i, i, 0);
				botoes.addComponent(botoesLinha3[count], 3, count - 1, 1, 1);
				botoes.constraints.insets = new Insets(0, i, i, 0);
			} else if (count == linha3.length - 1) { // condição para o ultimo botao (enter) ocupar 2 colunas (p/
														// alinhar os botoes)
				botoes.constraints.insets = new Insets(0, -(((l - i) / 2) - (int) i / 2), i, -(i + 3));
				botoes.addComponent(botoesLinha3[count], 3, count - 1, 2, 1);
				botoes.constraints.insets = new Insets(0, i, i, 0);
			} else {
				botoes.constraints.insets = new Insets(0, -(((l - i) / 2) - (int) i / 2), i, 0);
				botoes.addComponent(botoesLinha3[count], 3, count - 1, 1, 1);
			}

		}

		for (int count = 0; count < linha4.length; count++) {

			botoesLinha4[count] = new Tecla(linha4[count], keyCodeL4[count]);

			// tamanho
			if (count == 0)
				botoesLinha4[count].setPreferredSize(new Dimension(((int) (2 * l + i)), l)); // Shift (= tamanho do Tab
																								// -> ver formula)
			else
				botoesLinha4[count].setPreferredSize(new Dimension(l, l));

			// add e organizar
			if (count == 0)
				botoes.addComponent(botoesLinha4[count], 4, count, 1, 1);
			else if (count == 1) {
				botoes.constraints.insets = new Insets(0, (int) (2 * l + i) + 2 * i, i, 0);
				botoes.addComponent(botoesLinha4[count], 4, count - 1, 1, 1);
				botoes.constraints.insets = new Insets(0, i, i, 0);
			} else if (count == linha4.length - 1) {
				botoes.constraints.insets = new Insets(0, -((int) (l / 2) - 4), i, -(i + 3));
				botoes.addComponent(botoesLinha4[count], 4, count, 1, 1); // separar a /\ dos outros bts
			} else
				botoes.addComponent(botoesLinha4[count], 4, count - 1, 1, 1);

		}

		for (int count = 0; count < linha5.length; count++) {

			botoesLinha5[count] = new Tecla(linha5[count], keyCodeL5[count]);

			// tamanho
			if (count == 0)
				botoesLinha5[count].setPreferredSize(new Dimension(((int) (6 * l + 8 * i - 1)), l)); // barra de espaço
			else
				botoesLinha5[count].setPreferredSize(new Dimension(l, l));

			// add e org
			if (count == 0) {
				botoes.constraints.insets = new Insets(0, 0, i, 0);
				botoes.addComponent(botoesLinha5[count], 5, 2, 7, 1); // barra de espaco
			} else if (count == botoesLinha5.length - 1) {
				botoes.constraints.insets = new Insets(0, i, i, -(i + 3));
				botoes.addComponent(botoesLinha5[count], 5, 9 + count, 2, 1);
			} else {
				botoes.constraints.insets = new Insets(0, -((int) (l / 2) - 4), i, 0);
				// botoes.constraints.insets = new Insets(0, i, i, 0);
				botoes.addComponent(botoesLinha5[count], 5, 9 + count, 1, 1);
			}
		}
		// FIM da lógica para configurar a posição dos botoes no teclado virtual
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

		public Tecla(String nome, int keyCode) {
			this.keyCode = keyCode;
			this.nome = nome;
			this.setText(nome);
			this.setBorder(new RoundedBorder(10));
			this.setOpaque(true);
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
			g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
		}
	}

	public class TeclasInvalidasException extends Exception {
		int kc;

		public TeclasInvalidasException(int keyCode) {
			kc = keyCode;
			texto.setEditable(false);
		}

		public String toString() {
			return "Tecla inválida!";
		}
	}

	public class AcaoTeclas implements KeyListener {

		private Color padrao = (new JButton().getBackground());

		public void analisarTecla(int keyCode) throws TeclasInvalidasException {
			int achou = 0;
			// lógica abaixo serve apenas para concatenar o array de keycodes de cada linha
			// para um unico array de keycodes
			int quant = keyCodeL1.length + keyCodeL2.length + keyCodeL3.length + keyCodeL4.length + keyCodeL5.length;
			int[] keyCodes = new int[quant];
			System.arraycopy(keyCodeL1, 0, keyCodes, 0, keyCodeL1.length);
			System.arraycopy(keyCodeL2, 0, keyCodes, keyCodeL1.length, keyCodeL2.length);
			System.arraycopy(keyCodeL3, 0, keyCodes, keyCodeL1.length + keyCodeL2.length, keyCodeL3.length);
			System.arraycopy(keyCodeL4, 0, keyCodes, keyCodeL1.length + keyCodeL2.length + keyCodeL3.length,
					keyCodeL4.length);
			System.arraycopy(keyCodeL5, 0, keyCodes,
					keyCodeL1.length + keyCodeL2.length + keyCodeL3.length + keyCodeL4.length, keyCodeL5.length);

			// percorre o array com todos os keycodes das teclas do teclado virtual
			// comparando com o keycode da tecla pressionada. caso não ache nenhum keycode
			// igual ao do array, significa que a tecla não pertence ao teclado virtual e ativa a exceção.
			for (int i = 0; i < quant; i++)
				if (keyCode == keyCodes[i])
					achou++;
			if (achou == 0)
				throw new TeclasInvalidasException(keyCode);
			texto.setEditable(true);
		}

		@Override // trata açao do pressionamento de qualquer tecla
		public void keyPressed(KeyEvent event) {
			try { // trata a exceção do pressionamento das teclas que não pertencem ao teclado
					// virtual
				analisarTecla(event.getExtendedKeyCode());
			} catch (TeclasInvalidasException ex1) {
				JOptionPane.showMessageDialog(null, ex1);
			}
			labelResultado.setText(""); // limpa a area de resultados ao iniciar uma nova tentativa
										// (digitar qualquer tecla apos dar enter)

			// logica que mapeia a tecla pressionada com seu respectivo keycode (para não
			// ocorrer erro de compatibilidade com diferentes teclados de maquinas
			// distintas)
			for (int count = 0; count < linha1.length; count++) {
				if (event.getExtendedKeyCode() == keyCodeL1[count])
					botoesLinha1[count].setBackground(Color.LIGHT_GRAY);
				else if (event.getExtendedKeyCode() == keyCodeL2[count])
					botoesLinha2[count].setBackground(Color.LIGHT_GRAY);
				else if (count == 0 && event.getExtendedKeyCode() == keyCodeL3[count])
					if (botoesLinha3[0].getBackground() == padrao) // logica que mantem o CAPS LOCK aceso enquanto
																	// estiver
																	// ativo
						botoesLinha3[0].setBackground(Color.LIGHT_GRAY);
					else
						botoesLinha3[0].setBackground(padrao);
				else if (count != 0 && count < keyCodeL3.length && event.getExtendedKeyCode() == keyCodeL3[count]) {
					botoesLinha3[count].setBackground(Color.LIGHT_GRAY);
					if (count == keyCodeL3.length - 1)
						teclaEnter();
				} else if (count < keyCodeL4.length && event.getExtendedKeyCode() == keyCodeL4[count])
					botoesLinha4[count].setBackground(Color.LIGHT_GRAY);
				else if (count < keyCodeL5.length && event.getExtendedKeyCode() == keyCodeL5[count])
					botoesLinha5[count].setBackground(Color.LIGHT_GRAY);
			}
		}

		// metodo que define todas as açoes ativadas com o pressionamento da tecla enter
		public void teclaEnter() {
			texto.setEditable(false); // -> apenas para a JTextArea não pular uma linha
			conteudo = texto.getText();
			// logica para analisar a frase digitada e calcular a quantidade de acertos e
			// erros
			if (fraseSelecionada != -1)
				if (conteudo.length() > frases[fraseSelecionada].length()) {
					erro += conteudo.length() - frases[fraseSelecionada].length();
					for (int i = 0; i < frases[fraseSelecionada].length(); i++)
						if (frases[fraseSelecionada].charAt(i) == conteudo.charAt(i))
							acerto++;
						else
							erro++;
				} else if (conteudo.length() <= frases[fraseSelecionada].length()) {
					erro += frases[fraseSelecionada].length() - conteudo.length();
					for (int i = 0; i < conteudo.length(); i++)
						if (frases[fraseSelecionada].charAt(i) == conteudo.charAt(i))
							acerto++;
						else
							erro++;
				}
			// logica que define o que será mostrado nos labels de resultado (acertos,
			// erros), dependendo se acertou o pangrama ou não
			labelNotificacoes.setText("<html>" + frases[fraseSelecionada] + "<br/>" + conteudo + "</html>");
			if (acerto == frases[fraseSelecionada].length())
				labelResultado.setText("<html>A frase foi digitada corretamente!<br/>" + "Quantidade de acertos: "
						+ acerto + "<br/>Quantidade de erros: " + erro + " </html>");
			else
				labelResultado.setText("<html>Observe acima que a frase NÃO foi digitada corretamente!<br/>"
						+ "Quantidade de acertos: " + acerto + "<br/>Quantidade de erros: " + erro + "<br/>"
						+ "Para tentar novamente o mesmo pangrama, continue digitando... </html>");

			// Lógica para reescrever o arquivo serializavel com as ultimas informaçoes
			// obtidas (frase digitada, acertos, erros) da tentativa mais recente
			manager.openFileOutput();
			manager.deleteFile(); // o deleteFile() tem o mesmo código do openFile, ou seja, serve apenas para
									// sobrescreve o mesmo file com um file vazio, "apagando" o conteudo do file
									// anterior

			int tamanhoDoPangrama = frases[fraseSelecionada].length();
			// cria um objeto serializavel com as informações a respeito da frase digitada
			ConteudoSerializable c = new ConteudoSerializable(conteudo, acerto, tamanhoDoPangrama, erro);

			// adiciona o objeto serializado recem criado a lista de objetos serializaveis
			// resgatada do arquivo serializado
			historicoFrases.add(c);

			// registra no novo arquivo serializado a lista com todo o historico anterior
			// resgatado mais o objeto contendo a ultima tentativa
			manager.addRecords(historicoFrases);
			manager.closeFileOutput();

			// lógica para atribuir todo o conteudo capturado do arquivo serializado para
			// uma String para depois "joga-la" no JTextArea da aba histórico
			mostraHistorico = "Histórico de tentativas:\n\n";
			for (int i = 0; i < historicoFrases.size(); i++)
				mostraHistorico += ("Frase: " + historicoFrases.get(i).getFrase() + "\nAcertos: "
						+ historicoFrases.get(i).getAcertos() + "/" + historicoFrases.get(i).getTamanho() + "\tErros: "
						+ historicoFrases.get(i).getErros() + "\n\n");
			historicoText.setText(mostraHistorico);

			mostraHistorico = "";
			texto.setText(null);
			conteudo = null;
			acerto = 0;
			erro = 0;
		}

		// lógica para retornar a cor padrao dos botoes do teclado virtual ao
		// "despressionar" as teclas
		@Override
		public void keyReleased(KeyEvent event) {
			for (int count = 0; count < linha1.length; count++) {
				if (event.getExtendedKeyCode() == keyCodeL1[count])
					botoesLinha1[count].setBackground(padrao);
				else if (event.getExtendedKeyCode() == keyCodeL2[count])
					botoesLinha2[count].setBackground(padrao);
				else if (count != 0 && count < keyCodeL3.length && event.getExtendedKeyCode() == keyCodeL3[count])
					botoesLinha3[count].setBackground(padrao);
				else if (count < keyCodeL4.length && event.getExtendedKeyCode() == keyCodeL4[count])
					botoesLinha4[count].setBackground(padrao);
				else if (count < keyCodeL5.length && event.getExtendedKeyCode() == keyCodeL5[count])
					botoesLinha5[count].setBackground(padrao);
			}
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
		}
	}
}
