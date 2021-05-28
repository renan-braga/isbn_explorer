package isbn_explorer;

import java.util.ArrayList;

public class ListaImagensVazias {
	
	private static ArrayList<String> links = populaLista();

	public static boolean estaNalista(String imagem) {
		for(String link : links) {
			if(imagem == null) continue;
			if(imagem.contains(link))
				return true;
		}
		return false;
	}

	private static ArrayList<String> populaLista() {
		links = new ArrayList<String>();
		
		links.add("https://m.media-amazon.com/images/I/01AodW1Gh-L._AC_UL350_.jpg");
		links.add("https://m.media-amazon.com/images/I/01IA4pFSUFL._AC_UL350_.jpg");
		links.add("https://m.media-amazon.com/images/I/41oPT00RzBL._AC_UL350_.jpg");
		links.add("https://m.media-amazon.com/images/I/31i1wmRqPfL._AC_UL350_.jpg");
		links.add("https://m.media-amazon.com/images/I/413a7CisvZL._AC_UL350_.jpg");
		links.add("https://m.media-amazon.com/images/I/41PXbv0t5mL._AC_UL350_.jpg");
		links.add("https://m.media-amazon.com/images/I/31ARAo5Pg3L._AC_UL350_.jpg");
		links.add("https://m.media-amazon.com/images/I/31rKKhAA5GL._AC_UL350_.jpg");
		links.add("https://m.media-amazon.com/images/I/41pzdcT-2UL._AC_UL350_.jpg");
		links.add("https://m.media-amazon.com/images/I/3149ZZJX13L._AC_UL320_.350_.jpg");
		links.add("https://m.media-amazon.com/images/I/31q93o46RjL._AC_UL350_.jpg");
		return links;
	}

}
