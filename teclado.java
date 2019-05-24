import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

public class Teclado extends JFrame {

	private String[] linha1 = { "~", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "+", "Backspace" };
	private String[] linha2 = { "Tab", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "[", "]", "\\" };
	private String[] linha3 = { "Caps", "A", "S", "D", "F", "G", "H", "J", "K", "L", ":", "*", "Enter" };
	private String[] linha4 = { "Shift", "Z", "X", "C", "V", "B", "N", "M", ",", ".", "?", "^" };
	private String[] linha5 = { "                                          ", "<", "\\/", ">" };

	private JButton[] botoesLinha1 = new JButton[linha1.length];
	private JButton[] botoesLinha2 = new JButton[linha2.length];
	private JButton[] botoesLinha3 = new JButton[linha3.length];
	private JButton[] botoesLinha4 = new JButton[linha4.length];
	private JButton[] botoesLinha5 = new JButton[linha5.length];
	
	private JPanel botoes1, botoes2, botoes3, botoes4, botoes5;

	public Teclado() {
		super("Teclado");
		JPanel teclas = new JPanel();
		BoxLayout layoutT = new BoxLayout(teclas, 2);
		//layoutT.addLayoutComponent(horizontal1, 1);
		GridLayout layoutTeclas = new GridLayout(5, 1, 5, 5);
		teclas.setLayout(layoutT);
		teclas.setSize(500, 20);

		botoes1 = new BotoesPanel();
		botoes2 = new BotoesPanel();
		botoes3 = new BotoesPanel();
		botoes4 = new BotoesPanel();
		botoes5 = new BotoesPanel();
		teclas.add(botoes1);
		teclas.add(botoes2);
		teclas.add(botoes3);
		teclas.add(botoes4);
		teclas.add(botoes5);
		add(teclas);

		botoes1.setLayout(new FlowLayout());
		botoes2.setLayout(new FlowLayout());
		botoes3.setLayout(new FlowLayout());
		botoes4.setLayout(new FlowLayout());
		botoes5.setLayout(new FlowLayout());

		// linha 1
		for (int count = 0; count < linha1.length; count++) {

			botoesLinha2[count] = new JButton(linha2[count]);

			botoesLinha2[count].setBorder(new RoundedBorder(10));
			botoes2.add(botoesLinha2[count]);

			botoesLinha1[count] = new JButton(linha1[count]);
			botoes1.add(botoesLinha1[count]);

			botoesLinha1[count].setBorder(new RoundedBorder(10));

			// botoes1.constraints.insets = new Insets(0,5,5,0);

		}

		for (int count = 0; count < linha3.length; count++) {

			botoesLinha3[count] = new JButton(linha3[count]);

			botoesLinha3[count].setBorder(new RoundedBorder(10));
			botoes3.add(botoesLinha3[count]);
		}
		
		for (int count = 0; count < linha4.length; count++) {

			botoesLinha4[count] = new JButton(linha4[count]);

			botoesLinha4[count].setBorder(new RoundedBorder(10));
			botoes4.add(botoesLinha4[count]);
		}
		for (int count = 0; count < linha5.length; count++) {

			botoesLinha5[count] = new JButton(linha5[count]);

			botoesLinha5[count].setBorder(new RoundedBorder(10));
			botoes5.add(botoesLinha5[count]);
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

	public static void main(String[] args) {
		Teclado print = new Teclado();
		print.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		print.setSize(800, 300); // set frame size
		print.setVisible(true); // display frame
	} //
}
