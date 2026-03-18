package kasio.view.components.buttons;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import kasio.view.components.Colors;
import kasio.view.components.Fonts;


public final class WrapButton extends JButton {

    private final String wrapPrefix;

    public WrapButton(String text, String wrapPrefix) {
        super(text);
        this.wrapPrefix = wrapPrefix;
        setUp();
    }

    public WrapButton(String name) {
        super(name);
        this.wrapPrefix = "";
        setUp();
    }

    void setUp() {
        setFont(Fonts.alphaFont);
        setFocusable(false);
        setBackground(Colors.PRIMARY);
        setForeground(Colors.WHITE);
        setBorder(BorderFactory.createLineBorder(Colors.BLACK));
    }

    public String getWrapPrefix() {
        return this.wrapPrefix;
    }
}
