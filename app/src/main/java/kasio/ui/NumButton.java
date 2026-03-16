package kasio.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import kasio.App;

public class NumButton extends JButton {
    public NumButton(String name) {
        setText(name);  
        setFont(Fonts.numFont);
        setFocusable(false);
        setBackground(Colors.PRIMARY);
        setForeground(Colors.WHITE);
        setBorder(BorderFactory.createLineBorder(Colors.BLACK));
        addActionListener(e -> {
            App.textField.setText(App.textField.getText() + name);
        });
    }
}
