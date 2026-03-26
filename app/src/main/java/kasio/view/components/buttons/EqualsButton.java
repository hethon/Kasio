package kasio.view.components.buttons;

import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import kasio.view.components.Colors;

public class EqualsButton extends JButton {
  public EqualsButton(Font font) {
    setText("=");
    setFont(font);
    setFocusable(false);
    setBackground(Colors.GREEN);
    setForeground(Colors.WHITE);
    setBorder(BorderFactory.createLineBorder(Colors.BLACK));
  }
}
