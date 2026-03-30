package kasio.view.components.panels;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import kasio.view.components.Colors;
import kasio.view.components.Fonts;
import kasio.view.components.buttons.AllClearButton;
import kasio.view.components.buttons.AnsButton;
import kasio.view.components.buttons.AppendButton;
import kasio.view.components.buttons.DelButton;
import kasio.view.components.buttons.EqualsButton;
import kasio.view.components.buttons.WrapButton;

public class BasicKeypadPanel extends JPanel {

  private final List<AppendButton> appendButtons = new ArrayList<>();
  private final List<WrapButton> wrapButtons = new ArrayList<>();
  private final AllClearButton allClearButton = new AllClearButton(Fonts.KEYPAD_PRIMARY);
  private final DelButton delButton = new DelButton(Fonts.KEYPAD_PRIMARY);
  private final EqualsButton equalsButton = new EqualsButton(Fonts.KEYPAD_PRIMARY);
  private final AnsButton ansButton = new AnsButton(Fonts.KEYPAD_PRIMARY);

  public BasicKeypadPanel() {
    setBackground(Colors.SECONDARY);
    setLayout(new GridLayout(4, 5, 10, 10));
    setBorder(BorderFactory.createMatteBorder(0, 10, 10, 10, Colors.SECONDARY));

    addAppendBtn("7");
    addAppendBtn("8");
    addAppendBtn("9");
    add(delButton);
    add(allClearButton);

    addAppendBtn("4");
    addAppendBtn("5");
    addAppendBtn("6");
    addAppendBtn("×");
    addAppendBtn("÷");

    addAppendBtn("1");
    addAppendBtn("2");
    addAppendBtn("3");
    addAppendBtn("+");
    addAppendBtn("-");

    addAppendBtn("0");
    addAppendBtn(".");
    addAppendBtn("π");
    add(ansButton);
    add(equalsButton);
  }

  private void addAppendBtn(String text) {
    AppendButton button = new AppendButton(text, Fonts.KEYPAD_PRIMARY);
    appendButtons.add(button);
    add(button);
  }

  public List<AppendButton> getAppendButtons() {
    return List.copyOf(appendButtons);
  }

  public List<WrapButton> getWrapButtons() {
    return List.copyOf(wrapButtons);
  }

  public AllClearButton getAllClearButton() {
    return allClearButton;
  }

  public DelButton getDelButton() {
    return delButton;
  }

  public EqualsButton getEqualsButton() {
    return equalsButton;
  }

  public AnsButton getAnsButton() {
    return ansButton;
  }
}
