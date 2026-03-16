package kasio.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class ExitButton extends JButton{
    public ExitButton() {
        setText("Off");
        setFont(Fonts.alphaFont);
        setFocusable(false);
        setBackground(Colors.RED);
        setForeground(Colors.WHITE);
        setBorder(BorderFactory.createLineBorder(Colors.BLACK));
        addActionListener(e -> {
            System.exit(0);
        });
    }
}
