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
		lblStatus.setText("Iniciando extração...");
		GerenciadorArquivoExcel excel = new GerenciadorArquivoExcel(pathPlanilha);
		ArrayList<Livro> lista = excel.retornaListaPesquisa(excel.retornaNumeroTotalLinhas());
		
		int i = 1;
		long inicio = System.currentTimeMillis();
		int acertos = 0;
		
		DriverExtrator extrator = new DriverExtrator(false);
		
//		ArrayList<String> isbnUsados = new ArrayList<String>();
		
		excel.registrarCabecalhoImagem("Capas");
		
		String titulo = "";
		String isbn = "";
		String imagem = "";
		PrintWriter log = new PrintWriter(new FileWriter(novaPlanilha + ".log", true));
		for(Livro livro : lista) {
			titulo = livro.getTitulo();
			isbn = livro.getIsbn();
			imagem = "";

//				if(!isbn.equals("") && !isbn.startsWith("000000000") && ehIsbnUnico(isbn, isbnUsados)) {
				if(!isbn.equals("") && !isbn.startsWith("000000000")) {
					try {
//						isbnUsados.add(isbn);
						extrator.getDriver().get(AMAZON_SEARCH + isbn);
						extrator.waitForLoad();
						WebElement listaResultados = extrator.getDriver().findElements(By.xpath("//*[@class='s-result-list s-search-results sg-row']")).get(0);
		
						if(listaResultados.findElements(By.xpath("//*[@data-image-index='0']")).size() == 1) {
							imagem = listaResultados.findElement(By.xpath("//*[@data-image-index='0']")).getAttribute("src");
							if( imagem!= "") {
								acertos++; 
								lblStatus.setText("<html>Processando extração...<br> Encontradas: "+acertos+" capas <br> Titulo: " +titulo+"<br>ISBN: "+isbn+" </html>");
								imagem = retornaImagemResolucao350(imagem);
							}
						}
					}
					catch(Exception e) {
						lblStatus.setText("Erro na extração no título " + titulo + " isbn " + isbn);
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
		lblStatus.setText("<html>Finalizado com sucesso! <br>Capas encontradas: "+acertos+" <br>Tempo decorrido: "+total+" minutos</html>");
	}

	private static String retornaImagemResolucao350(String imagem) {
		String[] resultado = imagem.split("_.jpg");
		String prefixo = resultado[0];
		String extensao = "_.jpg";
		prefixo = prefixo.substring(0, prefixo.length()-3);
		prefixo = prefixo + "350";
		imagem = prefixo + extensao;
		return imagem;
	}

	private static boolean ehIsbnUnico(String isbn, ArrayList<String> isbnUsados) {
		for(String isbnTmp : isbnUsados) {
			if(isbnTmp.equals(isbn))
				return false;
		}
		return true;
	}
	
}
