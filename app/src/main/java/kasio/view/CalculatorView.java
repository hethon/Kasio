package kasio.view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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

import kasio.view.components.Colors;
import kasio.view.components.Fonts;
import kasio.view.components.buttons.AllClearButton;
import kasio.view.components.buttons.AnsButton;
import kasio.view.components.buttons.AppendButton;
import kasio.view.components.buttons.DelButton;
import kasio.view.components.buttons.EqualsButton;
import kasio.view.components.buttons.WrapButton;

public class CalculatorView {
    private  JFrame frame;
    private  JPanel scientificPanel;
    private  JPanel basicPanel;
    private final JButton off_menu;

    final int contentWidth = 400;
    final int contentHeight = 660;

    final int textFieldHeight = contentHeight * 15 / 100; // 15 precent of the content height
    final int scientificPanelHeight = contentHeight * 30 / 100; // 30 percent of the content height
    final int basicPanelHeight = contentHeight * 55 / 100; // 55 percent of the content height

    private  final JTextField textField;
    private final List<AppendButton> appendButtons = new ArrayList<>();
    private final List<WrapButton> wrapButtons = new ArrayList<>();
    private final AllClearButton allClearButton = new AllClearButton(Fonts.KEYPAD_PRIMARY);
    private final DelButton delButton = new DelButton(Fonts.KEYPAD_PRIMARY);
    private final EqualsButton equalsButton = new EqualsButton(Fonts.KEYPAD_PRIMARY);
    private final AnsButton ansButton = new AnsButton(Fonts.KEYPAD_PRIMARY);

    private void addAppendBtn(JPanel panel, String text, Font font) {
        addAppendBtn(panel, text, text, font);
    }

    private void addAppendBtn(JPanel panel, String displayText, String evalText, Font font) {
        AppendButton btn = new AppendButton(displayText, evalText, font);
        appendButtons.add(btn); // Save it so Controller can attach listeners later
        panel.add(btn);         // Add it to the UI
    }

    private void addWrapBtn(JPanel panel, String text, String wrapPrefix, Font font) {
        WrapButton btn = new WrapButton(text, wrapPrefix, font);
        wrapButtons.add(btn);
        panel.add(btn);
    }

    public CalculatorView() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.getContentPane().setPreferredSize(new Dimension(contentWidth, contentHeight));
        frame.setAlwaysOnTop(true);
        frame.setUndecorated(true);
        frame.setIconImage(
            new ImageIcon(getClass().getResource("/icon/icon.png")).getImage()
        );
        
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Colors.PRIMARY);
        menuBar.setBorder(BorderFactory.createEmptyBorder());
        menuBar.setLayout(new BorderLayout());
        MouseAdapter mouseAdapter = new MouseAdapter() {
            private int pX, pY;
            
            @Override
            public void mousePressed(MouseEvent e) {
                pX = e.getX();
                pY = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                frame.setLocation(frame.getLocation().x + e.getX() - pX, frame.getLocation().y + e.getY() - pY);
            }
        };

        menuBar.addMouseListener(mouseAdapter);
        menuBar.addMouseMotionListener(mouseAdapter);
        
        JMenu mode = new JMenu("Mode");
        mode.setForeground(Colors.WHITE);
        menuBar.add(mode, BorderLayout.WEST);
        
        JButton minimize = new JButton("—");
        minimize.setFont(Fonts.KEYPAD_PRIMARY);
        minimize.setForeground(Colors.WHITE);
        minimize.setBorderPainted(false);
        minimize.setFocusable(false);
        minimize.setContentAreaFilled(false);
        minimize.addActionListener(e -> {frame.setExtendedState(JFrame.ICONIFIED);});

        off_menu = new JButton("✕");
        off_menu.setFont(Fonts.KEYPAD_PRIMARY);
        off_menu.setForeground(Colors.WHITE);
        off_menu.setBorderPainted(false);
        off_menu.setFocusable(false);
        off_menu.setContentAreaFilled(false);

        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        eastPanel.setOpaque(false); // optional if you want transparency

        eastPanel.add(minimize);
        eastPanel.add(off_menu);

        menuBar.add(eastPanel, BorderLayout.EAST);
        
        JMenuItem basicMode = new JMenuItem("Basic");
        basicMode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK));
        basicMode.addActionListener(e -> {
            frame.remove(scientificPanel);
            basicPanel.setBounds(0, textFieldHeight, contentWidth, basicPanelHeight);
            frame.getContentPane().setPreferredSize(new Dimension(contentWidth, textFieldHeight+basicPanelHeight));
            frame.pack();
        });
        mode.add(basicMode);
        
        JMenuItem scientificMode = new JMenuItem("Scientific");
        scientificMode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                KeyEvent.CTRL_DOWN_MASK));
        scientificMode.addActionListener(e -> {
            frame.add(scientificPanel);
            basicPanel.setBounds(0, textFieldHeight + scientificPanelHeight, contentWidth, basicPanelHeight);
            frame.getContentPane().setPreferredSize(new Dimension(contentWidth, contentHeight));
            frame.pack();
        });
        mode.add(scientificMode);
        
        textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setBounds(0, 0, contentWidth, textFieldHeight);
        textField.setEditable(false);
        textField.setBackground(Colors.RETRO);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(10, 10, 10, 10, Colors.SECONDARY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10) // padding
        ));
        textField.setFont(Fonts.displayFont);
        
        scientificPanel = new JPanel();
        scientificPanel.setBounds(0, textFieldHeight, contentWidth,
        scientificPanelHeight);
        scientificPanel.setBackground(Colors.SECONDARY);
        scientificPanel.setLayout(new GridLayout(3, 6, 10, 10));
        scientificPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Colors.SECONDARY));

        addAppendBtn(scientificPanel, "SIN", "SIN(", Fonts.KEYPAD_SECONDARY);
        addAppendBtn(scientificPanel, "COS", "COS(", Fonts.KEYPAD_SECONDARY);
        addAppendBtn(scientificPanel, "TAN", "TAN(", Fonts.KEYPAD_SECONDARY);
        addAppendBtn(scientificPanel, "ASIN", "ASIN(", Fonts.KEYPAD_SECONDARY);
        addAppendBtn(scientificPanel, "ACOS", "ACOS(", Fonts.KEYPAD_SECONDARY);
        addAppendBtn(scientificPanel, "ATAN", "ATAN(", Fonts.KEYPAD_SECONDARY);

        addAppendBtn(scientificPanel, "<html>X<sup>2</sup></html>", "∧2", Fonts.KEYPAD_SECONDARY);
        addAppendBtn(scientificPanel, "<html>X<sup>Y</sup></html>", "∧", Fonts.KEYPAD_SECONDARY);
        addAppendBtn(scientificPanel, "√", Fonts.KEYPAD_SECONDARY);
        addAppendBtn(scientificPanel, "!", Fonts.KEYPAD_SECONDARY);
        addAppendBtn(scientificPanel, "LOG", "LOG(", Fonts.KEYPAD_SECONDARY);
        addAppendBtn(scientificPanel, "LN", "LN(", Fonts.KEYPAD_SECONDARY);

        addWrapBtn(scientificPanel, "1/x", "1/", Fonts.KEYPAD_SECONDARY);
        addWrapBtn(scientificPanel, "(x)", "", Fonts.KEYPAD_SECONDARY);
        addAppendBtn(scientificPanel, "e", Fonts.KEYPAD_SECONDARY);
        addWrapBtn(scientificPanel, "±", "-", Fonts.KEYPAD_SECONDARY);
        addAppendBtn(scientificPanel, "(", Fonts.KEYPAD_SECONDARY);
        addAppendBtn(scientificPanel, ")", Fonts.KEYPAD_SECONDARY);

        basicPanel = new JPanel();
        basicPanel.setBounds(0, textFieldHeight + scientificPanelHeight,
                contentWidth, basicPanelHeight);
        basicPanel.setBackground(Colors.SECONDARY);
        basicPanel.setLayout(new GridLayout(4, 5, 10, 10));
        basicPanel.setBorder(BorderFactory.createMatteBorder(0, 10, 10, 10, Colors.SECONDARY));

        addAppendBtn(basicPanel, "7", Fonts.KEYPAD_PRIMARY);
        addAppendBtn(basicPanel, "8", Fonts.KEYPAD_PRIMARY);
        addAppendBtn(basicPanel, "9", Fonts.KEYPAD_PRIMARY);
        basicPanel.add(delButton);
        basicPanel.add(allClearButton);

        addAppendBtn(basicPanel, "4", Fonts.KEYPAD_PRIMARY);
        addAppendBtn(basicPanel, "5", Fonts.KEYPAD_PRIMARY);
        addAppendBtn(basicPanel, "6", Fonts.KEYPAD_PRIMARY);
        addAppendBtn(basicPanel, "×", Fonts.KEYPAD_PRIMARY);
        addAppendBtn(basicPanel, "÷", Fonts.KEYPAD_PRIMARY);
        
        addAppendBtn(basicPanel, "1", Fonts.KEYPAD_PRIMARY);
        addAppendBtn(basicPanel, "2", Fonts.KEYPAD_PRIMARY);
        addAppendBtn(basicPanel, "3", Fonts.KEYPAD_PRIMARY);
        addAppendBtn(basicPanel, "+", Fonts.KEYPAD_PRIMARY);
        addAppendBtn(basicPanel, "-", Fonts.KEYPAD_PRIMARY);

        addAppendBtn(basicPanel, "0", Fonts.KEYPAD_PRIMARY);
        addAppendBtn(basicPanel, ".", Fonts.KEYPAD_PRIMARY);
        addAppendBtn(basicPanel, "π", Fonts.KEYPAD_PRIMARY);
        basicPanel.add(ansButton);
        basicPanel.add(equalsButton);

        frame.setJMenuBar(menuBar);
        frame.add(textField);
        frame.add(scientificPanel);
        frame.add(basicPanel);

        frame.pack();
    }

    public void setDisplayValue(String value) {
        this.textField.setText(value);
    }

    public void addAppendButtonListener(Consumer<String> action) {
        for (AppendButton button : this.appendButtons) {
            button.addActionListener(e -> {
                action.accept(button.getEvalText());
            });
        }
    }

    public void addWrapButtonListener(Consumer<String> action) {
        for (WrapButton button : this.wrapButtons) {
            button.addActionListener(e -> {
                action.accept(button.getWrapPrefix());
            });
        }
    }

    public void addAllClearButtonListener(Runnable action) {
        allClearButton.addActionListener(e -> {
            action.run();
        });
    }

    public void addDelButtonListener(Runnable action) {
        delButton.addActionListener(e -> {
            action.run();
        });
    }

    public void addEqualsButtonListener(Runnable action) {
        equalsButton.addActionListener(e -> {
            action.run();
        });
    }

    public void addAnsButtonListener(Runnable action) {
        ansButton.addActionListener(e -> {
            action.run();
        });
    }

    public void addExitButtonListener(Runnable action) {
        off_menu.addActionListener(e -> {
            action.run();
        });
    }

    public void setVisible(boolean value) {
        frame.setVisible(value);
    }
}
