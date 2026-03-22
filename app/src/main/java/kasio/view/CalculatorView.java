package kasio.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
    private final AllClearButton allClearButton = new AllClearButton();
    private final DelButton delButton = new DelButton();
    private final EqualsButton equalsButton = new EqualsButton();

    private void addAppendBtn(JPanel panel, String text) {
        addAppendBtn(panel, text, text);
    }

    private void addAppendBtn(JPanel panel, String displayText, String evalText) {
        AppendButton btn = new AppendButton(displayText, evalText);
        appendButtons.add(btn); // Save it so Controller can attach listeners later
        panel.add(btn);         // Add it to the UI
    }

    private void addWrapBtn(JPanel panel, String text, String wrapPrefix) {
        WrapButton btn = new WrapButton(text, wrapPrefix);
        wrapButtons.add(btn);
        panel.add(btn);
    }

    private void addWrapBtn(JPanel panel, String text) {
        WrapButton btn = new WrapButton(text);
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
        minimize.setFont(Fonts.symbolFont);
        minimize.setForeground(Colors.WHITE);
        minimize.setBorderPainted(false);
        minimize.setFocusable(false);
        minimize.setContentAreaFilled(false);
        minimize.addActionListener(e -> {frame.setExtendedState(JFrame.ICONIFIED);});

        off_menu = new JButton("✕");
        off_menu.setFont(Fonts.alphaFont);
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
        textField.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Colors.SECONDARY));
        textField.setFont(Fonts.displayFont);
        
        scientificPanel = new JPanel();
        scientificPanel.setBounds(0, textFieldHeight, contentWidth,
        scientificPanelHeight);
        scientificPanel.setBackground(Colors.SECONDARY);
        scientificPanel.setLayout(new GridLayout(3, 6, 10, 10));
        scientificPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Colors.SECONDARY));

        addAppendBtn(scientificPanel, "x³", "∧3");
        addAppendBtn(scientificPanel, "asin");
        addAppendBtn(scientificPanel, "acos");
        addAppendBtn(scientificPanel, "atan");
        addAppendBtn(scientificPanel, "!");

        addWrapBtn(scientificPanel, "1/x", "1/");
        addAppendBtn(scientificPanel, "√");
        addAppendBtn(scientificPanel, "log");
        addAppendBtn(scientificPanel, "sin");
        addAppendBtn(scientificPanel, "cos");
        addAppendBtn(scientificPanel, "tan");

        addWrapBtn(scientificPanel, "-(X)", "-");
        addWrapBtn(scientificPanel, "()");
        addAppendBtn(scientificPanel, "tan");
        addAppendBtn(scientificPanel, "abs");
        addAppendBtn(scientificPanel, "ln");
        addAppendBtn(scientificPanel, "(");
        addAppendBtn(scientificPanel, ")");

        basicPanel = new JPanel();
        basicPanel.setBounds(0, textFieldHeight + scientificPanelHeight,
                contentWidth, basicPanelHeight);
        basicPanel.setBackground(Colors.SECONDARY);
        basicPanel.setLayout(new GridLayout(4, 5, 10, 10));
        basicPanel.setBorder(BorderFactory.createMatteBorder(0, 10, 10, 10, Colors.SECONDARY));

        addAppendBtn(basicPanel, "x²", "∧2");
        addAppendBtn(basicPanel, "7");
        addAppendBtn(basicPanel, "8");
        basicPanel.add(delButton);
        basicPanel.add(allClearButton);

        addAppendBtn(basicPanel, "9");
        addAppendBtn(basicPanel, "4");
        addAppendBtn(basicPanel, "5");
        addAppendBtn(basicPanel, "6");
        addAppendBtn(basicPanel, "×");

        addAppendBtn(basicPanel, "÷");
        addAppendBtn(basicPanel, "1");
        addAppendBtn(basicPanel, "2");
        addAppendBtn(basicPanel, "3");
        addAppendBtn(basicPanel, "+");

        addAppendBtn(basicPanel, "-");
        addAppendBtn(basicPanel, "0");
        addAppendBtn(basicPanel, ".");
        addAppendBtn(basicPanel, "π");
        addAppendBtn(basicPanel, "∧");

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

    public void addExitButtonListener(Runnable action) {
        off_menu.addActionListener(e -> {
            action.run();
        });
    }

    public void setVisible(boolean value) {
        frame.setVisible(value);
    }
}
