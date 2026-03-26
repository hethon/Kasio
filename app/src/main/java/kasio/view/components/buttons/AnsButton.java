package kasio.view.components.buttons;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import kasio.view.components.Colors;


public class AnsButton extends JButton {
    public AnsButton(Font font) {
        super("Ans");
        setFont(font);
        setFocusable(false);
        setBackground(Colors.PRIMARY);
        setForeground(Colors.WHITE);
        setBorder(BorderFactory.createLineBorder(Colors.BLACK));
    }
}
