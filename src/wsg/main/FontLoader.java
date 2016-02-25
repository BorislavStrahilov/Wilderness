package wsg.main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class FontLoader {

	public Font font;
	private File font_file;

	public FontLoader(String path) {

		try {
			font_file = new File(path);
			font = Font.createFont(Font.TRUETYPE_FONT, font_file);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
