package kasio.view.components.panels;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import kasio.view.components.Colors;
import kasio.view.components.Fonts;
import kasio.view.components.buttons.AppendButton;
import kasio.view.components.buttons.WrapButton;

public class ScientificKeypadPanel extends JPanel {

  private final List<AppendButton> appendButtons = new ArrayList<>();
  private final List<WrapButton> wrapButtons = new ArrayList<>();

  public ScientificKeypadPanel() {
    setBackground(Colors.SECONDARY);
    setLayout(new GridLayout(3, 6, 10, 10));
    setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Colors.SECONDARY));

    addAppendBtn("SIN", "SIN(");
    addAppendBtn("COS", "COS(");
    addAppendBtn("TAN", "TAN(");
    addAppendBtn("ASIN", "ASIN(");
    addAppendBtn("ACOS", "ACOS(");
    addAppendBtn("ATAN", "ATAN(");

    addAppendBtn("<html>X<sup>2</sup></html>", "∧2");
    addAppendBtn("<html>X<sup>Y</sup></html>", "∧");
    addAppendBtn("√");
    addAppendBtn("!");
    addAppendBtn("LOG", "LOG(");
    addAppendBtn("LN", "LN(");

    addWrapBtn("1/x", "1/");
    addWrapBtn("(x)", "");
    addAppendBtn("e");
    addWrapBtn("±", "-");
    addAppendBtn("(");
    addAppendBtn(")");
  }

  private void addAppendBtn(String text) {
    addAppendBtn(text, text);
  }

  private void addAppendBtn(String displayText, String evalText) {
    AppendButton button = new AppendButton(displayText, evalText, Fonts.KEYPAD_SECONDARY);
    appendButtons.add(button);
    add(button);
  }

  private void addWrapBtn(String text, String wrapPrefix) {
    WrapButton button = new WrapButton(text, wrapPrefix, Fonts.KEYPAD_SECONDARY);
    wrapButtons.add(button);
    add(button);
  }

  public List<AppendButton> getAppendButtons() {
    return List.copyOf(appendButtons);
  }

  public List<WrapButton> getWrapButtons() {
    return List.copyOf(wrapButtons);
  }
}
