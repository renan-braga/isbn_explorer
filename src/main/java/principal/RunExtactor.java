package principal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class RunExtactor {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Extrator de Capas");
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(frame);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		TelaInicial tela;
		try {
			tela = new TelaInicial();
			frame.add(tela, BorderLayout.CENTER);
			frame.setPreferredSize(new Dimension(455, 526));
			frame.pack();
			frame.setLocationByPlatform(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao criar a tela: \n" + e.getMessage());// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
