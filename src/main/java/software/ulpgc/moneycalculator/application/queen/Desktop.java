package software.ulpgc.moneycalculator.application.queen;

import software.ulpgc.moneycalculator.architecture.control.Command;
import software.ulpgc.moneycalculator.architecture.model.Currency;
import software.ulpgc.moneycalculator.architecture.ui.CurrencyDialog;
import software.ulpgc.moneycalculator.architecture.ui.MoneyDialog;
import software.ulpgc.moneycalculator.architecture.ui.MoneyDisplay;
import software.ulpgc.moneycalculator.architecture.model.Money;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Desktop extends JFrame {
    private final Map<String, Command> commands;
    private final List<Currency> currencies;
    private JTextField inputAmount;
    private JComboBox<Currency> inputCurrency;
    private JTextField outputAmount;
    private JComboBox<Currency> outputCurrency;

    // Colores modernos
    private final Color ACCENT_COLOR = new Color(52, 152, 219); // Azul moderno
    private final Color BACKGROUND_COLOR = new Color(245, 245, 245);

    public Desktop(List<Currency> currencies) {
        this.commands = new HashMap<>();
        this.currencies = currencies;
        setupFrame();
        this.add(createMainPanel());
    }

    private void setupFrame() {
        this.setTitle("Money Calculator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(450, 300); // Tamaño más compacto y elegante
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.getContentPane().setBackground(BACKGROUND_COLOR);
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(BACKGROUND_COLOR);

        mainPanel.add(createRowPanel("From:", inputAmount = createTextField(), inputCurrency = createComboBox()));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        mainPanel.add(createRowPanel("To:    ", outputAmount = createOutputField(), outputCurrency = createComboBox()));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        mainPanel.add(createButtonPanel());

        return mainPanel;
    }

    private JPanel createRowPanel(String labelText, JTextField textField, JComboBox<Currency> comboBox) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(BACKGROUND_COLOR);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));

        panel.add(label, BorderLayout.WEST);
        panel.add(textField, BorderLayout.CENTER);
        panel.add(comboBox, BorderLayout.EAST);

        return panel;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("SansSerif", Font.PLAIN, 16));
        field.setPreferredSize(new Dimension(100, 35));
        return field;
    }

    private JTextField createOutputField() {
        JTextField field = createTextField();
        field.setEditable(false);
        field.setBackground(Color.WHITE);
        field.setForeground(ACCENT_COLOR);
        field.setFont(new Font("SansSerif", Font.BOLD, 16));
        return field;
    }

    private JComboBox<Currency> createComboBox() {
        JComboBox<Currency> combo = new JComboBox<>(currencies.toArray(new Currency[0]));
        combo.setPreferredSize(new Dimension(80, 35));
        return combo;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(BACKGROUND_COLOR);

        JButton button = new JButton("Convert Currency");
        button.setBackground(ACCENT_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 40));

        button.addActionListener(e -> commands.get("exchange").execute());

        panel.add(button);
        return panel;
    }

    public void addCommand(String name, Command command) {
        this.commands.put(name, command);
    }

    public MoneyDialog moneyDialog() {
        return () -> new Money(Double.parseDouble(inputAmount.getText()), (Currency) inputCurrency.getSelectedItem());
    }

    public CurrencyDialog currencyDialog() {
        return () -> (Currency) outputCurrency.getSelectedItem();
    }

    public MoneyDisplay moneyDisplay() {
        return money -> outputAmount.setText(String.format("%.2f", money.amount()));
    }
}
