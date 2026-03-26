package kasio.view.components;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

public class Fonts {
  public static final Font KEYPAD_PRIMARY = new Font(Font.SANS_SERIF, Font.BOLD, 24);
  public static final Font KEYPAD_SECONDARY = new Font(Font.SANS_SERIF, Font.BOLD, 17);

  // This will be initialized in the static block below
  public static Font displayFont;

  static {
    try {
      String fontPath = "/fonts/casio-calculator-font.ttf";
      InputStream is = Fonts.class.getResourceAsStream(fontPath);

      if (is == null) {
        throw new IOException("Font file not found at: " + fontPath);
      }

      Font customFont = Font.createFont(Font.TRUETYPE_FONT, is);

      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(customFont);

      // We use deriveFont to set the size.
      displayFont = customFont.deriveFont(16f);

    } catch (IOException | FontFormatException e) {
      System.err.println(
          "Could not load custom seven-segment font. Using a default monospaced font.");
      displayFont = new Font("Monospaced", Font.BOLD, 30);
    }
  }
}
