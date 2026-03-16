package kasio.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;

public class NumButton extends JButton {
    public NumButton(String name, JTextField display) {
        setText(name);  
        setFont(Fonts.numFont);
        setFocusable(false);
        setBackground(Colors.PRIMARY);
        setForeground(Colors.WHITE);
        setBorder(BorderFactory.createLineBorder(Colors.BLACK));
        addActionListener(e -> {
            display.setText(display.getText() + name);
        });
    }
}
