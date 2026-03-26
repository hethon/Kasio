package kasio.view.components.buttons;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import kasio.view.components.Colors;


public class AppendButton extends JButton {

    private final String inputText;

    public AppendButton(String keypadText, Font font) {
        this(keypadText, keypadText, font);
    }

    public AppendButton(String keypadText, String inputText, Font font) {
        super(keypadText);
        this.inputText = inputText;
        
        setFont(font);
        setFocusable(false);
        setBackground(Colors.PRIMARY);
        setForeground(Colors.WHITE);
        setBorder(BorderFactory.createLineBorder(Colors.BLACK));
    }

    /**
     * Gets the input-ready string value.
     */
    public String getInputText() {
        return inputText;
    }
}
