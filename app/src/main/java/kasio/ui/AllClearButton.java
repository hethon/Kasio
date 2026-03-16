package kasio.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import kasio.App;

public class AllClearButton extends JButton {
    public AllClearButton() {
        setText("AC");
        setFont(Fonts.alphaFont);
        setFocusable(false);
        setBackground(Colors.RED);
        setForeground(Colors.WHITE);
        setBorder(BorderFactory.createLineBorder(Colors.BLACK));
        addActionListener(e -> {
            App.textField.setText("");
        });
    }
}
