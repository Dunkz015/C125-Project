package br.inatel.impedance_calculator.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class MyFrame extends JFrame implements ActionListener {
    /*------------------------------------------------------------------------*/
    private ImageIcon Zimage = new ImageIcon("Impedance_button.png");
    private ImageIcon icon = new ImageIcon("Icon.png");
    private JFrame frame;
    private JMenuBar mb;
    private JMenu menu;
    private JMenuItem Mhelp;
    private JPanel panel, Opanel;
    private JLabel label;
    private JTextField input;
    private JTextArea output;
    private JButton calculate, reset, copy;
    static String expression;
    static String ans = "";
    static String before = "";
    /*------------------------------------------------------------------------*/
    public MyFrame() {
        //Creating the Frame
        frame = new JFrame("ZCalculator");

        /*------------------------------------------------------------------------*/
        //Configuring frame
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        /*------------------------------------------------------------------------*/

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Creating the MenuBar and adding components
        mb = new JMenuBar();
        menu = new JMenu("Menu");
        Mhelp = new JMenuItem("Help");
        mb.add(menu); //Adding components to menu bar
        menu.add(Mhelp); //Adding components to m1

        //Creating the panel and adding components
        panel = new JPanel();
        Opanel = new JPanel();
        label = new JLabel(Zimage);
        input = new JTextField(30);
        input.setFont(input.getFont().deriveFont(Font.PLAIN, 20f));
        calculate = new JButton("="); //Calculate
        copy = new JButton("Ans"); //Last result
        reset = new JButton("C"); //Erase all

        //Configuring buttons
        copy.setFont(input.getFont().deriveFont(Font.PLAIN, 15f));
        calculate.setFont(input.getFont().deriveFont(Font.PLAIN, 15f));
        reset.setFont(input.getFont().deriveFont(Font.PLAIN, 15f));

        copy.setPreferredSize(new Dimension(60,30));
        calculate.setPreferredSize(new Dimension(50,30));
        reset.setPreferredSize(new Dimension(50,30));

        panel.add(label);
        panel.add(input);
        panel.add(calculate);
        panel.add(reset);

        //Buttons and menus listeners
        reset.addActionListener(this);
        calculate.addActionListener(this);
        Mhelp.addActionListener(this);
        copy.addActionListener(this);

        //Output
        output = new JTextArea();
        output.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        output.setBackground(Color.WHITE);
        output.setFont(input.getFont().deriveFont(Font.PLAIN, 20f));
        output.setEditable(false);
        Opanel.add(output);
        Opanel.add(copy);

        //Adding Components to the frame
        frame.getContentPane().add(BorderLayout.PAGE_END, Opanel);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);

        //Setting size and other properties
        frame.getRootPane().setDefaultButton(calculate); //Enter key activates button "="
        frame.setPreferredSize(new Dimension(700, 160));
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setIconImage(icon.getImage());
        frame.setVisible(true);
    }

    /*------------------------------------------------------------------------*/
    //Configuring buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        //Reset button action
        if (e.getSource() == reset) {
            input.setText("");
        }

        //Copy button action
        if (e.getSource() == copy) {
            try {
                if (!expression.equals("")) {
                    if (Objects.equals(input.getText(), before)) {
                        input.setText(ans);
                    }
                    else
                        input.setText(input.getText() + ans);
                }
            }catch (Exception ignored) {}
        }

        //Help button
        if (e.getSource() == Mhelp) {
            String helpText = """
                    How to use the calculator\s
                    Use '+' to calculate series impedance and '|' to calculate parallel impedance.
                    To enter a complex number, type real part + 'i' + imaginary part (You can also use 'I', 'j' and 'J').
                    Example: 3.5i2 (3.5 is the real part and 2 is the imaginary part).

                    To enter a capacitor or inductor value, type Capacitance/inductance + 'C or L' + Frequency (You can also use 'c' and 'l').
                    
                    You can also use metric prefixes, like m (10^-3). This calculator allows these prefixes:
                    m (10^-3) , u (10^-6), n (10^-9), p (10^-12), k (10^3), M (10^6) and G (10^9).
                    Example: 100mC1k (0.1 is the capacitance and 1000 is the frequency).
                    You may also use scientific notation. Example: 3E-6C2E3

                    To apply higher precedence use parentheses. Example: 3i3 + 2uC1k | (80mL1k + 2.5i-10).""";
            JOptionPane.showMessageDialog(null,
                    "<html><b style=\"color:blue; font-size:15px;\">" + helpText,
                    "Help",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

        //Calculate button action
        if (e.getSource() == calculate) {
            expression = input.getText();

            if (!expression.equals("")) {
                try {
                    output.setForeground(Color.BLACK);
                    output.setFont(input.getFont().deriveFont(Font.BOLD, 15f));
                    output.setText(
                            EvaluateInput.evaluate(expression).toString() + "\n" +
                                    Complex.polar(EvaluateInput.evaluate(expression))
                    );
                    ans = EvaluateInput.evaluate(expression).getReal() + "i" + EvaluateInput.evaluate(expression).getImaginary();
                    before = input.getText();
                } catch (Exception exception) {
                    output.setFont(input.getFont().deriveFont(Font.BOLD, 20f));
                    output.setForeground(Color.RED);
                    output.setText("Invalid Expression");
                }
            }
        }
    }
    /*------------------------------------------------------------------------*/
}