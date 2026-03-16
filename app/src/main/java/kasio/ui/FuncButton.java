package kasio.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;

public final class FuncButton extends JButton {
    public FuncButton(String name, JTextField display) {
        setUp(name);
        addActionListener(e -> {
            display.setText(
                    String.format("%s(%s)", name, display.getText()));
        });
    }

    public FuncButton(String name, String func, JTextField display) {
        setUp(name);
        addActionListener(e -> {
            display.setText(
                    String.format("%s(%s)", func, display.getText()));
        });
    }

    public FuncButton(String name, String func, String pos, JTextField display) {
        setUp(name);
        addActionListener(e -> {
            display.setText(
                    String.format("(%s)%s", display.getText(), func));
        });
    }

    void setUp(String name) {
        setText(name);
        setFont(Fonts.alphaFont);
        setFocusable(false);
        setBackground(Colors.PRIMARY);
        setForeground(Colors.WHITE);
        setBorder(BorderFactory.createLineBorder(Colors.BLACK));
    }
}
