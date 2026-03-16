package kasio.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;

public class ShiftButton extends JButton {
    ShiftButton(JTextField display) {
        String shiftIcon = "⇧";
        setText(shiftIcon);  
        setFont(Fonts.numFont);
        setFocusable(false);
        setBackground(Colors.PRIMARY);
        setForeground(Colors.WHITE);
        setBorder(BorderFactory.createLineBorder(Colors.BLACK));
        addActionListener(e -> {
            display.setText("sh");
        });
    }
}
