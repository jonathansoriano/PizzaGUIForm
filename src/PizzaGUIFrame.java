import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PizzaGUIFrame extends JFrame {
    // 3 Main Panels
    private JPanel menuPanel;
    private JPanel crustPanel;
    private JPanel sizePanel;
    private JPanel toppingsPanel;

    private JPanel receiptPanel;

    private JPanel buttonPanel;

    // JButtons
    private JButton orderBtn;
    private JButton clearBtn;
    private JButton quitBtn;
    private ButtonGroup btnGroup;

    // Radio Buttons
    private JRadioButton thinCrustBtn;
    private JRadioButton regularCrustBtn;
    private JRadioButton deepDishCrustBtn;

    // Combo box
    private JComboBox<String> sizeComboBox;

    // Check boxes
    private JCheckBox pepCheckBox;
    private JCheckBox baconCheckBox;
    private JCheckBox mushroomCheckBox;
    private JCheckBox pineappleCheckBox;
    private JCheckBox chickenCheckBox;
    private JCheckBox pickleCheckBox;

    // TextArea
    private JTextArea receiptTA;

    // Other Variables
    private String[] pizzaSizes;
    private double[] sizePrices = {8.00, 12.00, 16.00, 20.00}; // Prices corresponding to sizes
    private final double toppingPrice = 1.00;

    public PizzaGUIFrame() {
        // Generate Menu Panel
        generateMenuPanel();
        this.add(menuPanel);
        // Generate Receipt Panel
        generateReceiptPanel();
        this.add(receiptPanel);
        // Generate Button Panel
        generateButtonPanel();
        this.add(buttonPanel);

        this.setLayout(new GridLayout(3, 1)); // Frame gets a LayoutManager
        this.setTitle("Pizza Form");
        this.setSize(600, 700);
        this.setLocationRelativeTo(null); // Centers JFrame to the center of Computer Screens
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void generateMenuPanel() {
        menuPanel = new JPanel(new GridLayout(1, 3));

        // Crust Panel
        crustPanel = new JPanel(new GridLayout(3, 1));
        crustPanel.setBorder(new TitledBorder(new EtchedBorder(), "Type of Crust"));
        thinCrustBtn = new JRadioButton("Thin");
        regularCrustBtn = new JRadioButton("Regular", true); // Auto Selects Regular
        deepDishCrustBtn = new JRadioButton("Deep Dish");

        btnGroup = new ButtonGroup();
        btnGroup.add(thinCrustBtn);
        btnGroup.add(regularCrustBtn);
        btnGroup.add(deepDishCrustBtn);

        crustPanel.add(thinCrustBtn);
        crustPanel.add(regularCrustBtn);
        crustPanel.add(deepDishCrustBtn);

        // Size Panel
        sizePanel = new JPanel();
        sizePanel.setBorder(new TitledBorder(new EtchedBorder(), "Pizza Sizes"));

        pizzaSizes = new String[]{"Small ($8.00)", "Medium ($12.00)", "Large ($16.00)", "Super ($20.00)"};
        sizeComboBox = new JComboBox<>(pizzaSizes);

        sizePanel.add(sizeComboBox);

        // Toppings Panel
        toppingsPanel = new JPanel(new GridLayout(3, 2));
        toppingsPanel.setBorder(new TitledBorder(new EtchedBorder(), "Pizza Toppings ($1 each)"));

        pepCheckBox = new JCheckBox("Pepperoni");
        baconCheckBox = new JCheckBox("Bacon");
        mushroomCheckBox = new JCheckBox("Mushrooms");
        pineappleCheckBox = new JCheckBox("Pineapple");
        chickenCheckBox = new JCheckBox("Chicken");
        pickleCheckBox = new JCheckBox("Pickles");

        toppingsPanel.add(pepCheckBox);
        toppingsPanel.add(baconCheckBox);
        toppingsPanel.add(mushroomCheckBox);
        toppingsPanel.add(pineappleCheckBox);
        toppingsPanel.add(chickenCheckBox);
        toppingsPanel.add(pickleCheckBox);

        // Adding the three Panels to the Menu Panel
        menuPanel.add(crustPanel);
        menuPanel.add(toppingsPanel);
        menuPanel.add(sizePanel);
    }

    public void generateReceiptPanel() {
        receiptPanel = new JPanel();
        receiptPanel.setBorder(new TitledBorder(new EtchedBorder(), "Order Total"));

        receiptTA = new JTextArea(12, 40);
        receiptTA.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(receiptTA);

        receiptPanel.add(scrollPane);
    }

    public void generateButtonPanel() {
        buttonPanel = new JPanel(new GridLayout(1, 3));

        // Order Button
        orderBtn = new JButton("Order");
        orderBtn.addActionListener((ActionEvent ae) -> {
            processOrder();
        });

        // Clear Button
        clearBtn = new JButton("Clear");
        clearBtn.addActionListener((ActionEvent ae) -> {
            clearForm();
        });

        // Quit Button
        quitBtn = new JButton("Quit");
        quitBtn.addActionListener((ActionEvent) -> {
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to Quit?");
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // Adding buttons to the buttonPanel
        buttonPanel.add(orderBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(quitBtn);
    }

    private void processOrder() {
        String crust = getSelectedCrust();
        String size = (String) sizeComboBox.getSelectedItem();
        int sizeIndex = sizeComboBox.getSelectedIndex();
        double sizePrice = sizePrices[sizeIndex];
        double totalToppingsPrice = calculateToppingsPrice();


        double subTotal = sizePrice + totalToppingsPrice;
        double tax = subTotal * 0.07; // 7% tax
        double total = subTotal + tax;

        // Prepare receipt
        StringBuilder receipt = new StringBuilder();
        receipt.append("=========================================\n");
        receipt.append(String.format("Type of Crust: %s\n\n", crust));
        receipt.append(String.format("Size: %s\n\n", size));
        receipt.append("Ingredients:\n\n");

        if (pepCheckBox.isSelected()) receipt.append("Pepperoni: $1.00\n");
        if (baconCheckBox.isSelected()) receipt.append("Bacon: $1.00\n");
        if (mushroomCheckBox.isSelected()) receipt.append("Mushrooms: $1.00\n");
        if (pineappleCheckBox.isSelected()) receipt.append("Pineapple: $1.00\n");
        if (chickenCheckBox.isSelected()) receipt.append("Chicken: $1.00\n");
        if (pickleCheckBox.isSelected()) receipt.append("Pickles: $1.00\n");

        receipt.append(String.format("Sub-total: $%.2f\n\n", subTotal));
        receipt.append(String.format("Tax: $%.2f\n\n", tax));
        receipt.append("---------------------------------------------------------------------\n");
        receipt.append(String.format("Total: $%.2f\n", total));
        receipt.append("=========================================\n");

        receiptTA.setText(receipt.toString());
    }

    private String getSelectedCrust() {
        if (thinCrustBtn.isSelected()) return "Thin Crust";
        if (regularCrustBtn.isSelected()) return "Regular Crust";
        if (deepDishCrustBtn.isSelected()) return "Deep Dish Crust";
        return null;
    }

    private double calculateToppingsPrice() {
        double total = 0;
        if (pepCheckBox.isSelected()) total += toppingPrice;
        if (baconCheckBox.isSelected()) total += toppingPrice;
        if (mushroomCheckBox.isSelected()) total += toppingPrice;
        if (pineappleCheckBox.isSelected()) total += toppingPrice;
        if (chickenCheckBox.isSelected()) total += toppingPrice;
        if (pickleCheckBox.isSelected()) total += toppingPrice;
        return total;
    }

    private void clearForm() {
        thinCrustBtn.setSelected(false);
        regularCrustBtn.setSelected(true); // Reset to default
        deepDishCrustBtn.setSelected(false);
        sizeComboBox.setSelectedIndex(0); // Reset to first item
        pepCheckBox.setSelected(false);
        baconCheckBox.setSelected(false);
        mushroomCheckBox.setSelected(false);
        pineappleCheckBox.setSelected(false);
        chickenCheckBox.setSelected(false);
        pickleCheckBox.setSelected(false);
        receiptTA.setText(""); // Clear receipt
    }
}
