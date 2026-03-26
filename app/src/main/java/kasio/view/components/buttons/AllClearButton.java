package kasio.view.components.buttons;

import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import kasio.view.components.Colors;

public class AllClearButton extends JButton {
  public AllClearButton(Font font) {
    super("AC");
    setFont(font);
    setFocusable(false);
    setBackground(Colors.RED);
    setForeground(Colors.WHITE);
    setBorder(BorderFactory.createLineBorder(Colors.BLACK));
  }
}
