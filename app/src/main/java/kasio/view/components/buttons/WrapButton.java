package kasio.view.components.buttons;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import kasio.view.components.Colors;


public final class WrapButton extends JButton {

    private final String prefix;

    public WrapButton(String keypadText, String prefix, Font font) {
        super(keypadText);
        this.prefix = prefix;
        setUp(font);
    }

    public WrapButton(String keypadText, Font font) {
        super(keypadText);
        this.prefix = "";
        setUp(font);
    }

    void setUp(Font font) {
        setFont(font);
        setFocusable(false);
        setBackground(Colors.PRIMARY);
        setForeground(Colors.WHITE);
        setBorder(BorderFactory.createLineBorder(Colors.BLACK));
    }

    public String getPrefix() {
        return this.prefix;
    }
}
