package isbn_explorer;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.PixelGrabber;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public abstract class ExtratorAbstrato {
	
	public static int acertos = 0;
	public static int erros = 0;

	protected static void escolheArquivoSaida(BufferedWriter writerCerto, BufferedWriter writerErrado, int i, String titulo,
			String autor, String oclc, String isbn, String imagem) throws IOException {
		if(dadosEstaoPreenchidos(oclc, isbn, imagem)) {
			acertos++;
			writerCerto.write("\n"+ i +";"+ titulo + ";" + autor +";"+ oclc +";"+ isbn +";"+ imagem + ";");
		} else {
			erros++;
			writerErrado.write("\n"+ i +";"+ titulo + ";" + autor +";"+ oclc +";"+ isbn +";"+ imagem + ";");
		}
		if(i % 1000 == 0) {
			System.out.printf("\n\nEncontrados %d: - Não Encontrados %d: \\n\\n", acertos, erros);
		}
	}
	
	protected static boolean dadosEstaoPreenchidos(String oclc, String isbn, String imagem) {
		return !oclc.equals("") || !isbn.equals("");
	}
	
	public boolean ehImagemBranca(String imagem) throws InterruptedException, IOException {
		boolean isValid = false;
		try {
			URL url = new URL("http://"+imagem.substring(2, imagem.length()-1));
			Image img = ImageIO.read(url);
			//img = img.getScaledInstance(300, -1, Image.SCALE_FAST);
			img = img.getScaledInstance(100, -1, Image.SCALE_FAST);
			int w = img.getWidth(null);
		    int h = img.getHeight(null);
		    int[] pixels = new int[w * h];
		    PixelGrabber pg = new PixelGrabber(img, 0, 0, w, h, pixels, 0, w);
		    pg.grabPixels();
		    for (int pixel : pixels) {
		        Color color = new Color(pixel);
		        if (color.getAlpha() == 0 || color.getRGB() != Color.WHITE.getRGB()) {
		            isValid = true;
		            break;
		        }
		    }
	    
	    }catch(Exception e) {
	    	return false;
	    }
	    return isValid;
	}
	
}
