package kasio.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;

public class DelButton extends JButton{
    public DelButton(JTextField display) {
        setText("DEL");
        setFont(Fonts.alphaFont);
        setFocusable(false);
        setBackground(Colors.RED);
        setForeground(Colors.WHITE);
        setBorder(BorderFactory.createLineBorder(Colors.BLACK));
        addActionListener(e -> {
            String text = display.getText();
            if (text.length() > 0) {
                display.setText(text.substring(0, text.length() - 1));
            }
        });
    }
}
