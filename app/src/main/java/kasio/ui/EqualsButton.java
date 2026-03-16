package kasio.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;

import kasio.parser.Parser;

public class EqualsButton extends JButton {
    public EqualsButton(JTextField display) {
        setText("=");
        setFont(Fonts.symbolFont);
        setFocusable(false);
        setBackground(Colors.GREEN);
        setForeground(Colors.WHITE);
        setBorder(BorderFactory.createLineBorder(Colors.BLACK));
        addActionListener(e -> {
            String text = display.getText();
            if (text.length() > 0) {
                // System.out.println(text);
                String modStr = text.replace("asin", "asi")
                                    .replace("acos", "acs")
                                    .replace("atan", "atn")
                                    .replace("ln", "lan")
                                    .replace("√", "srt")
                                    .replace("×", "*")
                                    .replace("∧", "^");
                
                // System.out.println(modStr);
                try {
                    display.setText(Parser.fullParse(modStr));
                } catch (Exception err) {
                    display.setText("Syntax Error");
                }
            }
        });
    }
}
