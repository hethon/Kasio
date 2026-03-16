package kasio.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;

public class AllClearButton extends JButton {
    public AllClearButton(JTextField display) {
        setText("AC");
        setFont(Fonts.alphaFont);
        setFocusable(false);
        setBackground(Colors.RED);
        setForeground(Colors.WHITE);
        setBorder(BorderFactory.createLineBorder(Colors.BLACK));
        addActionListener(e -> {
            display.setText("");
        });
    }
}
