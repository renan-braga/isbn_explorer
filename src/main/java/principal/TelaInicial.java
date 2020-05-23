package principal;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.CompoundBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.apache.tools.ant.Main;

import isbn_explorer.ExtratorLivrosAmazon;

public class TelaInicial extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtOrigem;
	private JTextField txtDestino;
	private JLabel lblTitulo;
	private JSeparator separator;
	private Label lblOrigem;
	private JButton btnPesquisaOrigem;
	private JButton btnPesquisaDestino;
	private Label lblDestino;
	private JButton btnIniciarExtracao;
	private JLabel lblStatus;

	/**
	 * Create the panel.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public TelaInicial() throws FileNotFoundException, IOException {
		setBorder(new CompoundBorder());

		initComponents();
	}


	private void initComponents() throws FileNotFoundException, IOException {

		URL urlLogo = Main.class.getResource("/ares-logo2.png");

		ImageIcon icon = new ImageIcon(urlLogo);
		lblTitulo = new JLabel("");
		lblTitulo.setIcon(icon);
		lblTitulo.setBounds(32, 11, 387, 154);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

		separator = new JSeparator();
		separator.setBounds(32, 325, 387, 2);

		lblOrigem = new Label("Escolha a planilha de origem");
		lblOrigem.setBounds(32, 171, 150, 22);

		txtOrigem = new JTextField();
		txtOrigem.setBounds(32, 199, 286, 20);
		txtOrigem.setColumns(10);

		btnPesquisaOrigem = new JButton("Pesquisar...");
		btnPesquisaOrigem.setBounds(328, 196, 91, 30);
		btnPesquisaOrigem.addActionListener(this);

		txtDestino = new JTextField();
		txtDestino.setBounds(32, 276, 286, 20);
		txtDestino.setColumns(10);
			
		btnPesquisaDestino = new JButton("Pesquisar...");
		btnPesquisaDestino.setBounds(328, 273, 91, 27);
		btnPesquisaDestino.addActionListener(this);
		
		lblDestino = new Label("Escolha onde salvar");
		lblDestino.setBounds(32, 248, 112, 22);

		btnIniciarExtracao = new JButton("Iniciar Extra\u00E7\u00E3o");
		btnIniciarExtracao.setBounds(105, 338, 185, 45);

		lblStatus = new JLabel("Status da extração: pronto para iniciar");
		lblStatus.setHorizontalAlignment(SwingConstants.LEFT);
		lblStatus.setBounds(105, 406, 230, 55);
		
		URL urlIcon = Main.class.getResource("/extractor-icon.png");
		btnIniciarExtracao.setIcon(new ImageIcon(urlIcon));
		btnIniciarExtracao.setIconTextGap(10);
		btnIniciarExtracao.addActionListener(this);

		setLayout(null);
		add(lblTitulo);
		add(lblOrigem);
		add(txtOrigem);
		add(btnPesquisaOrigem);
		add(lblDestino);
		add(txtDestino);
		add(btnPesquisaDestino);
		add(separator);
		add(btnIniciarExtracao);
		add(lblStatus);

		btnIniciarExtracao.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(txtOrigem.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Preencha uma planilha de origem");
				else if(txtDestino.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Preencha uma planilha de destino");
				else {
					try {
						SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
							protected Void doInBackground() throws Exception {
								ExtratorLivrosAmazon.extrairDadosAmazon(txtOrigem.getText(), txtDestino.getText(), lblStatus);
								return null;
							};
						};
						worker.execute();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Entre em contato com o desenvolvedor, um erro ocorreu: \n" + e1.getMessage());
						e1.printStackTrace();
					}		
				}
			}
		});

	}


	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == btnPesquisaOrigem) {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int i = chooser.showSaveDialog(null);
			if(i != 1) {
				File arquivo = chooser.getSelectedFile();
				txtOrigem.setText(arquivo.getAbsolutePath());
			}
		}

		if(e.getSource() == btnPesquisaDestino) {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int i = chooser.showSaveDialog(null);
			if(i != 1) {
				File arquivo = chooser.getSelectedFile();
				String nomeDoArquivo = JOptionPane.showInputDialog("Digite o nome do arquivo");
				txtDestino.setText(arquivo.getAbsolutePath()+"\\"+nomeDoArquivo+".csv");
			}
		}

	}
}
