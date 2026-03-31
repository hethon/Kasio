package kasio.view;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import kasio.view.components.Colors;
import kasio.view.components.Fonts;
import kasio.view.components.RetroCaret;
import kasio.view.components.buttons.AllClearButton;
import kasio.view.components.buttons.AnsButton;
import kasio.view.components.buttons.AppendButton;
import kasio.view.components.buttons.DelButton;
import kasio.view.components.buttons.EqualsButton;
import kasio.view.components.buttons.WrapButton;
import kasio.view.components.panels.BasicKeypadPanel;
import kasio.view.components.panels.ScientificKeypadPanel;

public class CalculatorView {
  private JFrame frame;
  private ScientificKeypadPanel scientificPanel;
  private BasicKeypadPanel basicPanel;
  private MenuItem trayExitItem;

  final int contentWidth = 400;
  final int contentHeight = 660;

  final int textFieldHeight = contentHeight * 15 / 100; // 15 precent of the content height
  final int scientificPanelHeight = contentHeight * 30 / 100; // 30 percent of the content height
  final int basicPanelHeight = contentHeight * 55 / 100; // 55 percent of the content height

  private final JTextField textField;
  private final List<AppendButton> appendButtons = new ArrayList<>();
  private final List<WrapButton> wrapButtons = new ArrayList<>();
  private final AllClearButton allClearButton;
  private final DelButton delButton;
  private final EqualsButton equalsButton;
  private final AnsButton ansButton;

  private boolean allowProgrammaticEdit = false;

  public CalculatorView() {
    frame = new JFrame("Calculator");
    frame.setType(java.awt.Window.Type.UTILITY);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setLayout(null);
    frame.getContentPane().setPreferredSize(new Dimension(contentWidth, contentHeight));
    frame.setAlwaysOnTop(true);
    frame.setUndecorated(true);
    frame.setIconImage(new ImageIcon(getClass().getResource("/icon/icon.png")).getImage());

    JMenuBar menuBar = new JMenuBar();
    menuBar.setBackground(Colors.PRIMARY);
    menuBar.setBorder(BorderFactory.createEmptyBorder());
    menuBar.setLayout(new BorderLayout());
    MouseAdapter mouseAdapter =
        new MouseAdapter() {
          private int pX, pY;

          @Override
          public void mousePressed(MouseEvent e) {
            pX = e.getX();
            pY = e.getY();
          }

          @Override
          public void mouseDragged(MouseEvent e) {
            frame.setLocation(
                frame.getLocation().x + e.getX() - pX, frame.getLocation().y + e.getY() - pY);
          }
        };

    menuBar.addMouseListener(mouseAdapter);
    menuBar.addMouseMotionListener(mouseAdapter);

    JMenu mode = new JMenu("Mode");
    mode.setForeground(Colors.WHITE);
    menuBar.add(mode, BorderLayout.WEST);

    JPanel controls = new JPanel();
    controls.setOpaque(false);
    controls.setLayout(new FlowLayout(FlowLayout.RIGHT));

    JButton minimize = new JButton("—");
    minimize.setFont(Fonts.KEYPAD_PRIMARY);
    minimize.setForeground(Colors.WHITE);
    minimize.setBorderPainted(false);
    minimize.setFocusable(false);
    minimize.setContentAreaFilled(false);
    minimize.addActionListener(
        e -> {
          frame.setVisible(false);
        });
    controls.add(minimize);

    JButton close = new JButton("X");
    close.setFont(Fonts.KEYPAD_PRIMARY);
    close.setForeground(Colors.WHITE);
    close.setBorderPainted(false);
    close.setFocusable(false);
    close.setContentAreaFilled(false);
    close.addActionListener(
        e -> {
          System.exit(0);
        });
    controls.add(close);

    menuBar.add(controls, BorderLayout.EAST);

    JMenuItem basicMode = new JMenuItem("Basic");
    basicMode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK));
    basicMode.addActionListener(
        e -> {
          frame.remove(scientificPanel);
          basicPanel.setBounds(0, textFieldHeight, contentWidth, basicPanelHeight);
          frame
              .getContentPane()
              .setPreferredSize(new Dimension(contentWidth, textFieldHeight + basicPanelHeight));
          frame.pack();
        });
    mode.add(basicMode);

    JMenuItem scientificMode = new JMenuItem("Scientific");
    scientificMode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
    scientificMode.addActionListener(
        e -> {
          frame.add(scientificPanel);
          basicPanel.setBounds(
              0, textFieldHeight + scientificPanelHeight, contentWidth, basicPanelHeight);
          frame.getContentPane().setPreferredSize(new Dimension(contentWidth, contentHeight));
          frame.pack();
        });
    mode.add(scientificMode);

    textField = new JTextField();
    textField.setHorizontalAlignment(JTextField.LEFT);
    textField.setBounds(0, 0, contentWidth, textFieldHeight);
    ((AbstractDocument) textField.getDocument())
        .setDocumentFilter(
            new DocumentFilter() {

              private boolean allowed() {
                return allowProgrammaticEdit;
              }

              @Override
              public void insertString(
                  FilterBypass fb, int offset, String string, AttributeSet attr)
                  throws BadLocationException {
                if (allowed()) {
                  super.insertString(fb, offset, string, attr);
                }
              }

              @Override
              public void replace(
                  FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                  throws BadLocationException {
                if (allowed()) {
                  super.replace(fb, offset, length, text, attrs);
                }
              }

              @Override
              public void remove(FilterBypass fb, int offset, int length)
                  throws BadLocationException {
                if (allowed()) {
                  super.remove(fb, offset, length);
                }
              }
            });
    textField.setBackground(Colors.DISPLAY);
    textField.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(10, 10, 10, 10, Colors.SECONDARY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10) // padding
            ));
    textField.setFont(Fonts.displayFont);
    textField.setCaret(new RetroCaret());
    textField.requestFocusInWindow();

    scientificPanel = new ScientificKeypadPanel();
    scientificPanel.setBounds(0, textFieldHeight, contentWidth, scientificPanelHeight);
    basicPanel = new BasicKeypadPanel();
    basicPanel.setBounds(
        0, textFieldHeight + scientificPanelHeight, contentWidth, basicPanelHeight);

    appendButtons.addAll(scientificPanel.getAppendButtons());
    appendButtons.addAll(basicPanel.getAppendButtons());
    wrapButtons.addAll(scientificPanel.getWrapButtons());
    wrapButtons.addAll(basicPanel.getWrapButtons());

    allClearButton = basicPanel.getAllClearButton();
    delButton = basicPanel.getDelButton();
    equalsButton = basicPanel.getEqualsButton();
    ansButton = basicPanel.getAnsButton();

    frame.setJMenuBar(menuBar);
    frame.add(textField);
    frame.add(scientificPanel);
    frame.add(basicPanel);

    frame.pack();

    java.awt.GraphicsEnvironment ge = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
    java.awt.Rectangle screenBounds = ge.getMaximumWindowBounds();

    // Calculate the bottom-right corner, with a 10-pixel padding
    int x = screenBounds.width - frame.getWidth() - 10;
    int y = screenBounds.height - frame.getHeight() - 10;

    frame.setLocation(x, y);

    // --- SYSTEM TRAY SETUP ---
    if (SystemTray.isSupported()) {
      SystemTray tray = SystemTray.getSystemTray();
      Image image = new ImageIcon(getClass().getResource("/icon/icon.png")).getImage();

      PopupMenu popup = new PopupMenu();
      trayExitItem = new MenuItem("Exit Kasio");
      popup.add(trayExitItem);

      TrayIcon trayIcon = new TrayIcon(image, "Kasio Calculator", popup);
      trayIcon.setImageAutoSize(true);

      trayIcon.addMouseListener(
          new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
                // TOGGLE VISIBILITY
                if (frame.isVisible()) {
                  frame.setVisible(false);
                } else {
                  frame.setVisible(true);
                  frame.setExtendedState(JFrame.NORMAL);
                  frame.toFront();
                  frame.requestFocus();
                }
              }
            }
          });

      try {
        tray.add(trayIcon);
      } catch (AWTException e) {
        System.err.println("TrayIcon could not be added.");
      }
    }

    frame.addWindowFocusListener(
        new java.awt.event.WindowAdapter() {
          @Override
          public void windowLostFocus(java.awt.event.WindowEvent e) {
            frame.setVisible(false);
          }
        });
  }

  public void setDisplayValue(String value) {
    this.allowProgrammaticEdit = true;
    this.textField.setText(value);
    this.allowProgrammaticEdit = false;
  }

  public void addAppendButtonListener(Consumer<String> action) {
    for (AppendButton button : this.appendButtons) {
      button.addActionListener(
          e -> {
            action.accept(button.getInputText());
          });
    }
  }

  public void addWrapButtonListener(Consumer<String> action) {
    for (WrapButton button : this.wrapButtons) {
      button.addActionListener(
          e -> {
            action.accept(button.getPrefix());
          });
    }
  }

  public void addAllClearButtonListener(Runnable action) {
    allClearButton.addActionListener(
        e -> {
          action.run();
        });
  }

  public void addDelButtonListener(Runnable action) {
    delButton.addActionListener(
        e -> {
          action.run();
        });
  }

  public void addEqualsButtonListener(Runnable action) {
    equalsButton.addActionListener(
        e -> {
          action.run();
        });
  }

  public void addAnsButtonListener(Runnable action) {
    ansButton.addActionListener(
        e -> {
          action.run();
        });
  }

  public void addTrayExitListener(Runnable action) {
    if (trayExitItem != null) {
      trayExitItem.addActionListener(e -> action.run());
    }
  }

  public void setVisible(boolean value) {
    frame.setVisible(value);
  }

  public void setCaretVisibility(boolean isVisible) {
    textField.getCaret().setVisible(isVisible);
    if (isVisible) {
      // needed to force it to start blinking again
      textField.requestFocusInWindow();
    }
  }
}
