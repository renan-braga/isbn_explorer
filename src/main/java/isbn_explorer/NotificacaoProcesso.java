package isbn_explorer;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NotificacaoProcesso extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblLista;
	private JLabel lblQuantidadeDeConsultas;
	private JLabel lblConsultasVlidas;

	/**
	 * Create the panel.
	 */
	public NotificacaoProcesso() {
		
		lblLista = new JLabel("Livros na Lista: ");
		
		lblQuantidadeDeConsultas = new JLabel("Quantidade de Consultas: ");
		
		lblConsultasVlidas = new JLabel("Consultas V\u00E1lidas: ");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(33)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblQuantidadeDeConsultas, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblConsultasVlidas, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLista, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(70)
					.addComponent(lblQuantidadeDeConsultas)
					.addGap(16)
					.addComponent(lblConsultasVlidas)
					.addGap(16)
					.addComponent(lblLista))
		);
		setLayout(groupLayout);

	}
	
	public void iniciarProcecsso(int lista) {
		lblLista.setText("Livros na lista: " + lista);
		revalidate();
		lblLista.revalidate();
	}

	public void consultaValida(int acertos) {
		lblConsultasVlidas.setText("Consultas válidas: " + acertos);
		revalidate();
		lblConsultasVlidas.revalidate();
	}

	public void consultaRealizada(int i) {
		lblQuantidadeDeConsultas.setText("Quantidade de Consultas: " + i);
		revalidate();
		lblQuantidadeDeConsultas.revalidate();
	}
	
	
}
