package kasio.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
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
import kasio.view.components.buttons.ExitButton;
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
    private final List<AppendButton> appendButtons = Arrays.asList(
        new AppendButton("x³", "∧3"),
        new AppendButton("asin"),
        new AppendButton("acos"),
        new AppendButton("atan"),
        new AppendButton("!"),
        new AppendButton("√"),
        new AppendButton("log"),
        new AppendButton("sin"),
        new AppendButton("cos"),
        new AppendButton("tan"),
        new AppendButton("tan"),
        new AppendButton("abs"),
        new AppendButton("ln"),
        new AppendButton("("),
        new AppendButton(")"),
        new AppendButton("x²", "∧2"),
        new AppendButton("7"),
        new AppendButton("8"),
        new AppendButton("9"),
        new AppendButton("4"),
        new AppendButton("5"),
        new AppendButton("6"),
        new AppendButton("×"),
        new AppendButton("/"),
        new AppendButton("1"),
        new AppendButton("2"),
        new AppendButton("3"),
        new AppendButton("+"),
        new AppendButton("-"),
        new AppendButton("0"),
        new AppendButton("."),
        new AppendButton("π"),
        new AppendButton("∧")
    );

    private final List<WrapButton> wrapButtons = Arrays.asList(
        new WrapButton("1/x", "1/"),
        new WrapButton("-(x)", "-"),
        new WrapButton("()")
    );
    private final AllClearButton allClearButton = new AllClearButton();
    private final DelButton delButton = new DelButton();
    private final EqualsButton equalsButton = new EqualsButton();
    private final ExitButton exisButton = new ExitButton();


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

        scientificPanel.add(appendButtons.get(0));
        scientificPanel.add(appendButtons.get(1));
        scientificPanel.add(appendButtons.get(2));
        scientificPanel.add(appendButtons.get(3));
        scientificPanel.add(appendButtons.get(4));
        scientificPanel.add(exisButton);

        scientificPanel.add(wrapButtons.get(0));
        scientificPanel.add(appendButtons.get(5));
        scientificPanel.add(appendButtons.get(6));
        scientificPanel.add(appendButtons.get(7));
        scientificPanel.add(appendButtons.get(8));
        scientificPanel.add(appendButtons.get(9));

        scientificPanel.add(wrapButtons.get(1));
        scientificPanel.add(appendButtons.get(10));
        scientificPanel.add(wrapButtons.get(2));
        scientificPanel.add(appendButtons.get(11));
        scientificPanel.add(appendButtons.get(12));
        scientificPanel.add(appendButtons.get(13));
        scientificPanel.add(appendButtons.get(14));

        basicPanel = new JPanel();
        basicPanel.setBounds(0, textFieldHeight + scientificPanelHeight,
                contentWidth, basicPanelHeight);
        basicPanel.setBackground(Colors.SECONDARY);
        basicPanel.setLayout(new GridLayout(4, 5, 10, 10));
        basicPanel.setBorder(BorderFactory.createMatteBorder(0, 10, 10, 10, Colors.SECONDARY));

        basicPanel.add(appendButtons.get(15));
        basicPanel.add(appendButtons.get(16));
        basicPanel.add(appendButtons.get(17));
        basicPanel.add(delButton);
        basicPanel.add(allClearButton);

        basicPanel.add(appendButtons.get(18));
        basicPanel.add(appendButtons.get(19));
        basicPanel.add(appendButtons.get(20));
        basicPanel.add(appendButtons.get(21));
        basicPanel.add(appendButtons.get(22));

        basicPanel.add(appendButtons.get(23));
        basicPanel.add(appendButtons.get(24));
        basicPanel.add(appendButtons.get(25));
        basicPanel.add(appendButtons.get(26));
        basicPanel.add(appendButtons.get(27));

        basicPanel.add(appendButtons.get(28));
        basicPanel.add(appendButtons.get(29));
        basicPanel.add(appendButtons.get(30));
        basicPanel.add(equalsButton);
        basicPanel.add(appendButtons.get(31));

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
        exisButton.addActionListener(e -> {
            action.run();
        });
        off_menu.addActionListener(e -> {
            action.run();
        });
    }

    public void setVisible(boolean value) {
        frame.setVisible(value);
    }
}
