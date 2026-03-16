package kasio.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import kasio.App;

public class ShiftButton extends JButton {
    ShiftButton() {
        String shiftIcon = "⇧";
        setText(shiftIcon);  
        setFont(Fonts.numFont);
        setFocusable(false);
        setBackground(Colors.PRIMARY);
        setForeground(Colors.WHITE);
        setBorder(BorderFactory.createLineBorder(Colors.BLACK));
        addActionListener(e -> {
            App.textField.setText("sh");
        });
    }
}
