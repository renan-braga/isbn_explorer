package isbn_explorer;

public class Livro {
	
	public String titulo = "";
	public String autor = "";
	public String isbn = "";
	public String oclc = "";
	public String imagem = "";
	public String editora = "";

	public Livro(String titulo, String autor, String isbn, String oclc, String imagem, String editora) {
		this.titulo = titulo;
		this.autor = autor;
		this.isbn = isbn;
		this.oclc = oclc;
		this.imagem = imagem;
		this.editora = editora;
	}

	public Livro(String titulo, String isbn, String imagem) {
		this.titulo = titulo;
		this.isbn = isbn;
		this.imagem = imagem;
	}

	public Livro(String isbn, String imagem) {
		this.isbn = isbn;
		this.imagem = imagem;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getOclc() {
		return oclc;
	}

	public void setOclc(String oclc) {
		this.oclc = oclc;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public void formatarCampos() {
		titulo = titulo.replace(',', ' ');
		autor = autor.replace(",", " ");
		isbn = isbn.replace(',', ' ');
		editora = editora.replace(',', ' ');
		oclc = oclc.replace(',', ' ');
		imagem = imagem.replace(',', ' ');
		editora = editora.replace(',', ' ');
			
		
	}
	
	
	

	
	
}
