import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PizzaGUIFrame extends JFrame {
    // 3 Main Panels
    private JPanel menuPanel;

    private JPanel receiptPanel;

    private JPanel buttonPanel;

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
    private double[] sizePrices = {8.00, 12.00, 16.00, 20.00}; // Prices corresponding to pizzaSizes array's indexes
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
        JPanel crustPanel = new JPanel(new GridLayout(3, 1));
        crustPanel.setBorder(new TitledBorder(new EtchedBorder(), "Type of Crust"));
        thinCrustBtn = new JRadioButton("Thin");
        regularCrustBtn = new JRadioButton("Regular", true); // Auto Selects Regular
        deepDishCrustBtn = new JRadioButton("Deep Dish");

        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(thinCrustBtn);
        btnGroup.add(regularCrustBtn);
        btnGroup.add(deepDishCrustBtn);

        crustPanel.add(thinCrustBtn);
        crustPanel.add(regularCrustBtn);
        crustPanel.add(deepDishCrustBtn);

        // Size Panel
        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(new TitledBorder(new EtchedBorder(), "Pizza Sizes"));
        //Array for the ComboBox
        pizzaSizes = new String[]{"Small ($8.00)", "Medium ($12.00)", "Large ($16.00)", "Super ($20.00)"};
        sizeComboBox = new JComboBox<>(pizzaSizes);

        sizePanel.add(sizeComboBox);

        // Toppings Panel
        JPanel toppingsPanel = new JPanel(new GridLayout(3, 2));
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
        // JButtons
        JButton orderBtn = new JButton("Order");
        orderBtn.addActionListener((ActionEvent ae) -> {
            processOrder();
        });

        // Clear Button
        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener((ActionEvent ae) -> {
            clearForm();
        });

        // Quit Button
        JButton quitBtn = new JButton("Quit");
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

    /**
     * This method runs the functions of the Order button like, adding to the receipt TextArea what buttons, check boxes
     * and combo boxes I have chosen. Also has all the math logic to get sub-total, taxes, and grand total for the order.
     */
    private void processOrder() {
        String crust = getSelectedCrust(); //Returns what isSelected(); from the RadioButtons. (Thin,Regular,DeepDish)
        String size = (String) sizeComboBox.getSelectedItem(); // Get the String value of Item selected in Combo box.
        int sizeIndex = sizeComboBox.getSelectedIndex();//We need the index of the Selected Item from ComboBox since there's
        // another Array whose indexes corresponds to the indexes in the ComboBox array.
        double sizePrice = sizePrices[sizeIndex];//We use the selected ComboBox item and use it to find corresponding price in
        //sizePrices array. (Array is in declared in line 45)
        double totalToppingsPrice = calculateToppingsPrice();//Returns total toppings price based on which Checkboxes are selected with
        //isSelected() method.


        double subTotal = sizePrice + totalToppingsPrice; //Pizza Size Price + Total Toppings Picked Price = subtotal
        double tax = subTotal * 0.07; // 7% tax
        double total = subTotal + tax;

        // Prepare receipt
        String receipt = "";
        receipt+="=========================================\n";
        receipt += String.format("Type of Crust: %s\n\n", crust);
        receipt += String.format("Size: %s\n\n", size);
        receipt += "Ingredients:\n\n";

        if (pepCheckBox.isSelected()) receipt += "Pepperoni: $1.00\n";
        if (baconCheckBox.isSelected()) receipt += "Bacon: $1.00\n";
        if (mushroomCheckBox.isSelected()) receipt += "Mushrooms: $1.00\n";
        if (pineappleCheckBox.isSelected()) receipt += "Pineapple: $1.00\n";
        if (chickenCheckBox.isSelected()) receipt += "Chicken: $1.00\n";
        if (pickleCheckBox.isSelected()) receipt += "Pickles: $1.00\n";

        receipt += String.format("Sub-total: $%.2f\n\n", subTotal);
        receipt += String.format("Tax: $%.2f\n\n", tax);
        receipt += "---------------------------------------------------------------------\n";
        receipt += String.format("Total: $%.2f\n", total);
        receipt += "=========================================\n";

        receiptTA.setText(receipt);
    }

    /**
     * This method tells you what type of crust was picked (Radio buttons)
     * @return - returns the type of Crust in type String
     */
    private String getSelectedCrust() {
        if (thinCrustBtn.isSelected()) return "Thin Crust";
        if (regularCrustBtn.isSelected()) return "Regular Crust";
        if (deepDishCrustBtn.isSelected()) return "Deep Dish Crust";
        return null;
    }

    /**
     * This method checks the state of the checkboxes to see what was selected and adds a dollar to the total Toppings price
     * @return - returns the total toppings amount of type double.
     */
    private double calculateToppingsPrice() {
        double total = 0;

        JCheckBox[] toppings = {pepCheckBox, baconCheckBox,mushroomCheckBox,
                pickleCheckBox, pineappleCheckBox,chickenCheckBox};

        for (int i = 0; i < toppings.length; i++){
            if (toppings[i].isSelected()){
                total += toppingPrice;
            }
        }
        return total;
    }

    /**
     * This method sets all the buttons, check boxes, combo boxes to false (unchecked) or back to the first index of options (Combo Box)
     */
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


