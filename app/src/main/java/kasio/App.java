package kasio;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

import kasio.ui.AllClearButton;
import kasio.ui.Colors;
import kasio.ui.DelButton;
import kasio.ui.EqualsButton;
import kasio.ui.ExitButton;
import kasio.ui.Fonts;
import kasio.ui.FuncButton;
import kasio.ui.NumButton;
import kasio.ui.SymbolButton;

public class App implements ActionListener {
    public static JFrame frame;
    public static JTextField textField;
    public static JPanel scientificPanel;
    public static JPanel basicPanel;

    final int contentWidth = 400;
    final int contentHeight = 660;

    final int textFieldHeight = contentHeight * 15 / 100; // 15 precent of the content height
    final int scientificPanelHeight = contentHeight * 30 / 100; // 30 percent of the content height
    final int basicPanelHeight = contentHeight * 55 / 100; // 55 percent of the content height

    App() {
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
        menuBar.add(minimize, BorderLayout.EAST);
        
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

        scientificPanel.add(new FuncButton("x³", "∧3", "right"));
        scientificPanel.add(new SymbolButton("asin"));
        scientificPanel.add(new SymbolButton("acos"));
        scientificPanel.add(new SymbolButton("atan"));
        scientificPanel.add(new SymbolButton("!"));
        scientificPanel.add(new ExitButton()); //

        scientificPanel.add(new FuncButton("1/x", "1/"));
        scientificPanel.add(new SymbolButton("√"));
        scientificPanel.add(new SymbolButton("log"));
        scientificPanel.add(new SymbolButton("sin"));
        scientificPanel.add(new SymbolButton("cos"));
        scientificPanel.add(new SymbolButton("tan"));

        scientificPanel.add(new FuncButton("±", "-"));
        scientificPanel.add(new SymbolButton("abs"));
        scientificPanel.add(new SymbolButton("ln"));
        scientificPanel.add(new SymbolButton("(")); //
        scientificPanel.add(new SymbolButton(")")); //
        scientificPanel.add(new FuncButton("x²", "∧2", "right"));

        basicPanel = new JPanel();
        basicPanel.setBounds(0, textFieldHeight + scientificPanelHeight,
                contentWidth, basicPanelHeight);
        basicPanel.setBackground(Colors.SECONDARY);
        basicPanel.setLayout(new GridLayout(4, 5, 10, 10));
        basicPanel.setBorder(BorderFactory.createMatteBorder(0, 10, 10, 10, Colors.SECONDARY));

        basicPanel.add(new NumButton("7"));
        basicPanel.add(new NumButton("8"));
        basicPanel.add(new NumButton("9"));
        basicPanel.add(new DelButton());
        basicPanel.add(new AllClearButton());

        basicPanel.add(new NumButton("4"));
        basicPanel.add(new NumButton("5"));
        basicPanel.add(new NumButton("6"));
        basicPanel.add(new NumButton("×")); //
        basicPanel.add(new NumButton("/")); //

        basicPanel.add(new NumButton("1"));
        basicPanel.add(new NumButton("2"));
        basicPanel.add(new NumButton("3"));
        basicPanel.add(new NumButton("+")); //
        basicPanel.add(new NumButton("-")); //

        basicPanel.add(new NumButton("0"));
        basicPanel.add(new NumButton("."));
        basicPanel.add(new NumButton("π"));
        basicPanel.add(new EqualsButton());
        basicPanel.add(new NumButton("∧"));

        frame.setJMenuBar(menuBar);
        frame.add(textField);
        frame.add(scientificPanel);
        frame.add(basicPanel);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        App app = new App();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
    }
}
