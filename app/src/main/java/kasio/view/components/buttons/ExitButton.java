package kasio.view.components.buttons;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import kasio.view.components.Colors;
import kasio.view.components.Fonts;

public class ExitButton extends JButton {
    public ExitButton() {
        setText("Off");
        setFont(Fonts.alphaFont);
        setFocusable(false);
        setBackground(Colors.RED);
        setForeground(Colors.WHITE);
        setBorder(BorderFactory.createLineBorder(Colors.BLACK));
    }
}
