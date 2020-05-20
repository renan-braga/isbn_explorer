package isbn_explorer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class GerenciadorArquivoExcel {

	public int IMAGEM;
	public final int TITULO = 4;
	public final int AUTOR = 3;
	public final int EDITORA = 5;
	public final int ISBN = 2;
	
	public POIFSFileSystem fis;
	private Sheet sheet;
	private Workbook workbook;

	
	public GerenciadorArquivoExcel(String path) throws FileNotFoundException, IOException, InvalidFormatException {
		
		workbook = WorkbookFactory.create(new BufferedInputStream(new FileInputStream(path)));
		sheet = workbook.getSheetAt(0);
		
		IMAGEM = primeiraColunaVazia();
	}
	
	private int primeiraColunaVazia() {
		return sheet.getRow(0).getLastCellNum();
	}

	public int retornaNumeroTotalLinhas() {
		return sheet.getLastRowNum();
	}
	
	public String retornaDadoLinhaColuna(int linha, int coluna) {
		if(sheet.getRow(linha) != null
				&& sheet.getRow(linha).getCell(coluna) != null
				&& sheet.getRow(linha).getCell(coluna).getStringCellValue() != null) {
			
			return sheet.getRow(linha).getCell(coluna).getStringCellValue();
		}
		return "";
	}
	
	public void gravaDadoLinhaColuna(int linha, int coluna, String dado) throws IOException {
		sheet.getRow(linha).createCell(coluna).setCellValue(dado);
	}
	
	public ArrayList<Livro> retornaListaPesquisa(int linha) {
		ArrayList<Livro> lista = new ArrayList<Livro>();
		for(int i = 1; i < linha; i++) {
		
			String titulo = retornaDadoLinhaColuna(i, TITULO);
			String autor = retornaDadoLinhaColuna(i, AUTOR);
			String isbn = retornaDadoLinhaColuna(i, ISBN);
			String editora = retornaDadoLinhaColuna(i, EDITORA);
			String imagem = "";
			String oclc = "";
			
			
			Livro livro = new Livro(titulo, autor, isbn, oclc, imagem, editora);
			livro.formatarCampos();
			
			lista.add(livro);
		}
		return lista;
	}

	
	public void registrarCapasPeloIsbnArquivoExterno(String pathArquivo) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(pathArquivo));
		
		String linha = "";
		while((linha = reader.readLine()) != null) {
			String resultado[] = linha.split(";");
			int posicaoISBN = linha.split(";").length - 2;
			String imagem = resultado[posicaoISBN+1];
			int linhaISBN = retornaLinhaExpressaoPorColuna(resultado[posicaoISBN], ISBN);
			if(linhaISBN != 0) {
				registraCapaExcel(linhaISBN, imagem);
				System.out.print("registrei na linha " + linhaISBN);
			}
		}
		reader.close();
	}
	
	private int retornaLinhaExpressaoPorColuna(String isbnBuscado, int colunaISBN) {
		for(int i = 1; i < sheet.getLastRowNum(); i++) {
			if(sheet.getRow(i).getCell(colunaISBN) != null) {
				if(sheet.getRow(i).getCell(colunaISBN).getStringCellValue().equals(isbnBuscado)) {
					System.out.println("achou " + i);
					return i;
				}
			}
		}
		return 0;
	}

	public void registraCapaExcel(int linha, String imagem) throws IOException {
		gravaDadoLinhaColuna(linha, IMAGEM, imagem);
	}
	
	public void registrarCabecalho(String titulo) throws IOException {
		gravaDadoLinhaColuna(0, IMAGEM, titulo);
	}
	
	public void salvaPlanilha(String path) throws FileNotFoundException, IOException {
		workbook.write(new FileOutputStream(path));
	}
	
    public void registrarCSV(String path) throws IOException {
    	BufferedWriter arquivo = new BufferedWriter(new FileWriter(path + ".csv"));
    	Row row = null;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
            	arquivo.append(row.getCell(j) + ";");
                System.out.print("\"" + row.getCell(j) + "\";");
            }
            arquivo.append("\n");
            System.out.println();
        }
        arquivo.close();
    }
	

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}
	
}
