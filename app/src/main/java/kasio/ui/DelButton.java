package kasio.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import kasio.App;

public class DelButton extends JButton{
    public DelButton() {
        setText("DEL");
        setFont(Fonts.alphaFont);
        setFocusable(false);
        setBackground(Colors.RED);
        setForeground(Colors.WHITE);
        setBorder(BorderFactory.createLineBorder(Colors.BLACK));
        addActionListener(e -> {
            String text = App.textField.getText();
            if (text.length() > 0) {
                App.textField.setText(text.substring(0, text.length() - 1));
            }
        });
    }
}
