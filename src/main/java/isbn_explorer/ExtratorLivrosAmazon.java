package isbn_explorer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class ExtratorLivrosAmazon extends ExtratorAbstrato{
	
	
	private static final String AMAZON_SEARCH = "https://www.amazon.com.br/s?k=";

	private static JFrame frame;
	
	public static void extrairDadosAmazon(String pathPlanilha, String novaPlanilha, JLabel lblStatus) throws Exception {
		

		//extrair dados amazon vazios
		GerenciadorArquivoExcel excel = new GerenciadorArquivoExcel(pathPlanilha);
		ArrayList<Livro> lista = excel.retornaListaPesquisa(excel.retornaNumeroTotalLinhas());
		
		int i = 1;
		long inicio = System.currentTimeMillis();
		int acertos = 0;
		
		DriverExtrator extrator = new DriverExtrator(false);
		
		ArrayList<String> isbnUsados = new ArrayList<String>();

		frame = new JFrame();
		NotificacaoProcesso notificacao = new NotificacaoProcesso();
		frame.setLayout(new BorderLayout());
		frame.add(notificacao, BorderLayout.NORTH);
		frame.setPreferredSize(new Dimension(300, 250));
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
		
		excel.registrarCabecalho("Capas");
		
		String titulo = "";
		String isbn = "";
		String imagem = "";
		PrintWriter log = new PrintWriter(new FileWriter(novaPlanilha + ".log", true));
		for(Livro livro : lista) {
			titulo = livro.getTitulo();
			isbn = livro.getIsbn();
			imagem = "";

			
				if(!isbn.equals("") && !isbn.startsWith("000000000") && ehIsbnUnico(isbn, isbnUsados)) {
					try {
						isbnUsados.add(isbn);
						extrator.getDriver().get(AMAZON_SEARCH + isbn);
						extrator.waitForLoad();
						WebElement listaResultados = extrator.getDriver().findElements(By.xpath("//*[@class='s-result-list s-search-results sg-row']")).get(0);
		
						if(listaResultados.findElements(By.xpath("//*[@data-image-index='0']")).size() == 1) {
							imagem = listaResultados.findElement(By.xpath("//*[@data-image-index='0']")).getAttribute("src");
							if( imagem!= "") {
								acertos++; 
								lblStatus.setText("<html> Encontradas: "+acertos+" capas <br> Titulo: " +titulo+" </html>");
							}
						}
					}
					catch(Exception e) {
						lblStatus.setText("Erro na extração no título " + titulo);
						log.append(System.lineSeparator() + "##### NOVO ERRO #####" + System.lineSeparator());
						log.append(titulo + System.lineSeparator());
						e.printStackTrace(log);
						log.close();
					}
					finally {
						excel.registraCapaExcel(i, imagem);
						excel.salvaPlanilha(novaPlanilha);
					}
			}
			i++;
		}
		long fim = System.currentTimeMillis();
		long total = (fim - inicio) / 1000 / 60;
		lblStatus.setText("<html>Finalizado com sucesso! Capas encontradas: "+acertos+" <br>Tempo decorrido: "+total+" minutos</html>");
		System.out.println("\nTempo Total: " + total + " minutos");
	}

	private static boolean ehIsbnUnico(String isbn, ArrayList<String> isbnUsados) {
		for(String isbnTmp : isbnUsados) {
			if(isbnTmp.equals(isbn))
				return false;
		}
		return true;
	}
	
}
