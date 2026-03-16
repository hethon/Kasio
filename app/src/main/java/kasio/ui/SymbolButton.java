package kasio.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;

public class SymbolButton extends JButton{
    public SymbolButton(String name, JTextField display) {
        setText(name);  
        if (name.length() == 1) {
            setFont(Fonts.symbolFont);
        } else {
            setFont(Fonts.alphaFont);
        }
        setFocusable(false);
        setBackground(Colors.PRIMARY);
        setForeground(Colors.WHITE);
        setBorder(BorderFactory.createLineBorder(Colors.BLACK));
        addActionListener(e -> {
            display.setText(display.getText() + name);
        });
    }
}
