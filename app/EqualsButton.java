package app;

import parser.Parser;

import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class EqualsButton extends JButton {
    EqualsButton() {
        setText("=");
        setFont(Fonts.symbolFont);
        setFocusable(false);
        setBackground(Colors.GREEN);
        setForeground(Colors.WHITE);
        setBorder(BorderFactory.createLineBorder(Colors.BLACK));
        addActionListener(e -> {
            String text = App.textField.getText();
            if (text.length() > 0) {
                // System.out.println(text);
                String modStr = text.replace("asin", "asi")
                                    .replace("acos", "acs")
                                    .replace("atan", "atn")
                                    .replace("ln", "lan")
                                    .replace("√", "srt")
                                    .replace("×", "*");
                
                // System.out.println(modStr);
                try {
                    App.textField.setText(Parser.fullParse(modStr));
                } catch (Exception err) {
                    App.textField.setText("Syntax Error");
                }
            }
        });
    }
}
