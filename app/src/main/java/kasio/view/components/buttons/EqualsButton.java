package kasio.view.components.buttons;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import kasio.view.components.Colors;
import kasio.view.components.Fonts;

public class EqualsButton extends JButton {
    public EqualsButton() {
        setText("=");
        setFont(Fonts.symbolFont);
        setFocusable(false);
        setBackground(Colors.GREEN);
        setForeground(Colors.WHITE);
        setBorder(BorderFactory.createLineBorder(Colors.BLACK));
    }
}
