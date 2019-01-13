/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.restutantmanagement;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class RestaurantOrderGUI implements ActionListener {

    private JTextField textCustomerName;
    private JTextField textTableNumber;
    private JRadioButton breakFastRadio;
    private JRadioButton lunchRadio;
    private JRadioButton dinnerRadio;

    private JComboBox<MenuItem> foodList;
    private JComboBox<MenuItem> beverageList;
    private JButton btnEnterData;
    private JButton btnDisplayChoices;
    private JButton btnDisplayOrder;
    private JButton btnPrepare;
    private JButton btnBill;
    private JButton btnClearDisplay;
    private JButton btnQuit;
    private JTextArea textMenuChoices;
    private List<OrderedCustomer> customerOrders;
    private JButton fileChooserBtn;
    private RestaurantManagementController restaurantManagementController;
    private JList<String> waitingList;
    private JList<String> servedList;
    private JList<String> billedList;

    private List<MenuItem> foodItem = new LinkedList<>();
    private List<MenuItem> beverageItem = new LinkedList<>();

    public RestaurantOrderGUI() {
        initComponents();
    }

    private void initComponents() {

        restaurantManagementController = new RestaurantManagementController();

        JFrame f = new JFrame();
        fileChooserBtn = new JButton("Upload Menu Data");
        fileChooserBtn.setBounds(50, 5, 300, 25);
        fileChooserBtn.addActionListener(this);

        JLabel labelCustomerInfo = new JLabel("Customer Details:");
        labelCustomerInfo.setBounds(50, 45, 150, 20);
        JLabel labelCustomerName = new JLabel("Name:");
        labelCustomerName.setBounds(180, 45, 100, 20);

        textCustomerName = new JTextField();
        textCustomerName.setBounds(230, 45, 250, 20);

        JLabel labelTableInfo = new JLabel("Table Number:");
        labelTableInfo.setBounds(550, 45, 100, 20);

        textTableNumber = new JTextField();
        textTableNumber.setText("1");
        textTableNumber.setBounds(650, 45, 200, 20);

        JLabel labelMealTypeInfo = new JLabel("Meal Type:");
        labelMealTypeInfo.setBounds(50, 70, 150, 20);

        breakFastRadio = new JRadioButton("BreakFast");
        breakFastRadio.setSelected(true);
        breakFastRadio.setBounds(180, 70, 100, 20);

        lunchRadio = new JRadioButton("Lunch");
        lunchRadio.setBounds(320, 70, 100, 20);

        dinnerRadio = new JRadioButton("Dinner");
        dinnerRadio.setBounds(420, 70, 100, 20);

        ButtonGroup radioMenuTypeGroup = new ButtonGroup();
        radioMenuTypeGroup.add(breakFastRadio);
        radioMenuTypeGroup.add(lunchRadio);
        radioMenuTypeGroup.add(dinnerRadio);

        JLabel labelMenuChoices = new JLabel("Menu Choices:");
        labelMenuChoices.setBounds(50, 100, 100, 20);

        JLabel labelFood = new JLabel("Food:");
        labelFood.setBounds(180, 100, 100, 20);

        //foodList = new JComboBox<>
        foodList = new JComboBox<>(foodItem.toArray(new MenuItem[foodItem.size()]));
        foodList.setBounds(220, 100, 280, 20);

        JLabel labelBeverage = new JLabel("Beverage:");
        labelBeverage.setBounds(500, 100, 70, 20);

        beverageList = new JComboBox<>(beverageItem.toArray(new MenuItem[beverageItem.size()]));
        beverageList.setBounds(565, 100, 290, 20);

        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setBounds(20, 150, 800, 400);
        textAreaPanel.setBorder(BorderFactory.createTitledBorder("Your Menu Choices and Nutrition Information"));

        JPanel orderStatusPanel = new JPanel();
        orderStatusPanel.setBounds(20, 150, 800, 400);
        orderStatusPanel.setBorder(BorderFactory.createTitledBorder("Order's Status(MenuItem_ID:Order_ID)"));

        waitingList = new JList<>(getDefaultFoodItemListByStatus("Waiting"));
        waitingList.setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        waitingList.setPreferredSize(new Dimension(100, 400));
        JScrollPane waitingScrollPane = new JScrollPane(waitingList);
        waitingScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        servedList = new JList<>(getDefaultFoodItemListByStatus("Served"));

        servedList.setPreferredSize(new Dimension(100, 400));
        servedList.setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane servedScrollPane = new JScrollPane(servedList);
        servedScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        billedList = new JList<>(getDefaultFoodItemListByStatus("Billed"));

        billedList.setPreferredSize(new Dimension(100, 400));
        billedList.setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane billedScrollPane = new JScrollPane(billedList);
        billedScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        JPanel waitingListPanel = new JPanel();
        //waitingListPanel.setBounds(25, 150, 150, 400);
        waitingListPanel.setBorder(BorderFactory.createTitledBorder("Waiting Item List"));

        JPanel servedListPanel = new JPanel();
        //servedListPanel.setBounds(25, 200, 150, 400);
        servedListPanel.setBorder(BorderFactory.createTitledBorder("Served Item List"));

        JPanel billedListPanel = new JPanel();
        //billedListPanel.setBounds(25, 450, 150, 400);
        billedListPanel.setBorder(BorderFactory.createTitledBorder("Billed Item List"));

        waitingListPanel.add(waitingScrollPane);
        servedListPanel.add(servedScrollPane);
        billedListPanel.add(billedScrollPane);

        orderStatusPanel.add(waitingListPanel);
        orderStatusPanel.add(servedListPanel);
        orderStatusPanel.add(billedListPanel);

        textMenuChoices = new JTextArea(20, 63);
        textMenuChoices.setLineWrap(true);
        JScrollPane textScrollPane = new JScrollPane(textMenuChoices);
        textAreaPanel.add(textScrollPane);

        JPanel buttonAreaPanel = new JPanel();
        buttonAreaPanel.setBounds(5, 560, 800, 80);
        buttonAreaPanel.setBorder(BorderFactory.createTitledBorder("Command Buttons"));
        buttonAreaPanel.setForeground(new java.awt.Color(0, 102, 204));

        btnEnterData = new JButton("Enter Data");
        btnDisplayChoices = new JButton("Display Choices");
        btnPrepare = new JButton("Prepare");
        btnBill = new JButton("Bill");
        btnDisplayOrder = new JButton("Display Order");
        btnClearDisplay = new JButton("Clear Display");
        btnQuit = new JButton("Quit");

        btnEnterData.addActionListener(this);
        btnDisplayChoices.addActionListener(this);
        btnDisplayOrder.addActionListener(this);
        btnPrepare.addActionListener(this);
        btnBill.addActionListener(this);
        btnClearDisplay.addActionListener(this);
        btnQuit.addActionListener(this);
        textCustomerName.addActionListener(this);
        breakFastRadio.addActionListener(this);
        lunchRadio.addActionListener(this);
        dinnerRadio.addActionListener(this);

        buttonAreaPanel.add(btnEnterData);
        buttonAreaPanel.add(btnDisplayChoices);
        buttonAreaPanel.add(btnDisplayOrder);
        buttonAreaPanel.add(btnPrepare);
        buttonAreaPanel.add(btnBill);
        buttonAreaPanel.add(btnClearDisplay);
        buttonAreaPanel.add(btnQuit);
        JTabbedPane tp = new JTabbedPane();
        tp.setBounds(5, 135, 810, 420);
        tp.add("Nutration Infomation", textAreaPanel);
        tp.add("Order Status", orderStatusPanel);

        f.add(fileChooserBtn);
        f.add(labelCustomerInfo);
        f.add(labelCustomerName);
        f.add(textCustomerName);
        f.add(labelTableInfo);
        f.add(labelMealTypeInfo);
        f.add(breakFastRadio);
        f.add(lunchRadio);
        f.add(dinnerRadio);
        f.add(textTableNumber);
        f.add(labelMenuChoices);
        f.add(labelFood);
        f.add(foodList);
        f.add(labelBeverage);
        f.add(beverageList);
        //f.add(textAreaPanel);
        f.add(tp);
        f.add(buttonAreaPanel);
        f.setSize(870, 670);
        f.setLayout(null);
        f.setVisible(true);

        if (breakFastRadio.isSelected()) {
            populateMenuItem("BreakFast");
        } else if (lunchRadio.isSelected()) {
            populateMenuItem("Lunch");
        } else if (dinnerRadio.isSelected()) {
            populateMenuItem("Dinner");
        }

        // Initialize the customer list in constructor
        this.customerOrders = new LinkedList<>();
    }

    public static void main(String args[]) {
        new RestaurantOrderGUI();

    }

    private final String ENERGY = "Energy(KJ)";
    private final String PROTEIN = "Protein (g)";
    private final String CARBOHYADRATES = "Carbohydrates with sugar alcohols (g)";
    private final String FAT = "Total fat (g)";
    private final String FIBRE = "Dietary fibre (g)";

    private void reInitializeInputFields() {
        textCustomerName.setText("");
        textTableNumber.setText("1");
        foodList.setSelectedIndex(0);
        beverageList.setSelectedIndex(0);
    }

    private int errorCount = 0;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == textCustomerName) {
            btnBill.setEnabled(false);
            btnPrepare.setEnabled(false);
        }
        if (e.getSource() == btnEnterData) {
            btnBill.setEnabled(true);
            btnPrepare.setEnabled(true);
            try {
                int tableNumber = Integer.parseInt(textTableNumber.getText());
                String customerName = textCustomerName.getText();

                // Checks the validity of Customer Name
                if (customerName.isEmpty() || customerName.length() > 20) {
                    JOptionPane.showMessageDialog(new JFrame(), "Customer Name is invalid. It should not be empty or greater than length 20.", "Dialog",
                            JOptionPane.ERROR_MESSAGE);
                }

                // Checks the validity of Table Number
                if (tableNumber < 1 || tableNumber > 8) {
                    throw new NumberFormatException();
                }

                String orderType = "";
                if (breakFastRadio.isSelected()) {
                    orderType = "BreakFast";
                } else if (lunchRadio.isSelected()) {
                    orderType = "Lunch";
                } else if (dinnerRadio.isSelected()) {
                    orderType = "Dinner";
                }

                MenuItem food = (MenuItem) foodList.getSelectedItem();
                System.out.println(food.getItemId());
                MenuItem beverage = (MenuItem) beverageList.getSelectedItem();

                List<MenuItem> menuItems = new ArrayList<>();
                menuItems.add(food);
                menuItems.add(beverage);

                OrderedCustomer orderedCustomer = new OrderedCustomer(textCustomerName.getText(), tableNumber, menuItems, orderType, "Waiting");
                restaurantManagementController.saveCustomerOrder(orderedCustomer);

                reInitializeInputFields();
                errorCount = 0;
            } catch (NumberFormatException ne) {
                String quitMsg = "";
                if (errorCount++ > 3) {
                    quitMsg = " Error count is more than three times so exiting the application!!";
                }
                JOptionPane.showMessageDialog(new JFrame(), "Please insert number ranging 1-8 in the field Table Number." + quitMsg, "Dialog",
                        JOptionPane.ERROR_MESSAGE);
                if (errorCount++ > 3) {
                    System.exit(0);
                }
            }

        }
        if (e.getSource() == btnDisplayChoices) {
            btnBill.setEnabled(true);
            btnPrepare.setEnabled(true);
            MenuItem food = (MenuItem) foodList.getSelectedItem();
            MenuItem beverage = (MenuItem) beverageList.getSelectedItem();

            List<MenuItem> menuItems = new ArrayList<>();
            menuItems.add(new MenuItem(food));
            menuItems.add(new MenuItem(beverage));

            //Polymorphisim in create new customer instance with is OrderedCustomer eventually.
            OrderedCustomer customerOrder = new OrderedCustomer(null, 0, menuItems);
            StringBuilder menuInformation = new StringBuilder();
            menuInformation.append("Nutrition Information for current select items: \n");
            menuInformation.append("Food: " + food.getItemName() + "\n");
            menuInformation.append("--------------------------------------------\n");
            for (Nutrient nutrient : food.getItemNutrients()) {
                menuInformation.append(nutrient.getNutrientName() + ": " + nutrient.getNutrientQuantity() + "\n");
            }
            menuInformation.append("\nBeverage: " + beverage.getItemName() + "\n");
            menuInformation.append("--------------------------------------------\n");
            for (Nutrient nutrient : beverage.getItemNutrients()) {
                menuInformation.append(nutrient.getNutrientName() + ": " + nutrient.getNutrientQuantity() + "\n");
            }

            menuInformation.append("\nTotal Nutration Available in Seleted Items:\n");
            menuInformation.append("--------------------------------------------\n");
            menuInformation.append(ENERGY + ": " + customerOrder.getTotalEnergyValue() + "\n");
            menuInformation.append(PROTEIN + ": " + customerOrder.getTotalProtienValue() + "\n");
            menuInformation.append(CARBOHYADRATES + ": " + customerOrder.getTotalCarbohydrateValue() + "\n");
            menuInformation.append(FAT + ": " + customerOrder.getTotalFatValue() + "\n");
            menuInformation.append(FIBRE + ": " + customerOrder.getTotalFibreValue() + "\n");

            textMenuChoices.setText(menuInformation.toString());

        }

        if (e.getSource() == btnDisplayOrder) {
            btnBill.setEnabled(true);
            btnPrepare.setEnabled(true);
            try {
                int tableNumber = Integer.parseInt(textTableNumber.getText());
               
                List<OrderedCustomer> customerOrderPerTable = new ArrayList<>();
                customerOrders = restaurantManagementController.getCustomerOrderByTableNumber(tableNumber); 
               
                if (customerOrders.size() < 1) {
                    JOptionPane.showMessageDialog(new JFrame(), "Customer Order is empty. Please enter some order first!!", "Dialog",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                for (OrderedCustomer oc : customerOrders) {
                    if (tableNumber == oc.getTableNumber()) {
                        customerOrderPerTable.add(oc);
                    }
                }
                
                if (customerOrderPerTable.size() > 0) {
                    StringBuilder orders = new StringBuilder();
                    orders.append("Orders for Table Number: " + tableNumber + "\n");
                    orders.append("--------------------------------------------\n");

                    for (OrderedCustomer oct : customerOrderPerTable) {
                        orders.append("Customer Name: " + oct.getCustomerFirstName() + "\n");
                        for (int i = 0; i < oct.getMenuItems().size(); i++) {
                            orders.append("Item" + (i + 1) + ": " + oct.getMenuItems().get(i) + "\n");
                        }
                        orders.append("--------------------------------------------\n");
                    }
                    textMenuChoices.setText(orders.toString());
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Customer Order for this table is empty. Please enter some order first!!", "Dialog",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (NumberFormatException ne) {
                JOptionPane.showMessageDialog(new JFrame(), "Please insert number ranging 1-8 in the field Table Number.", "Dialog",
                        JOptionPane.ERROR_MESSAGE);
            }

        }

        if (e.getSource() == btnClearDisplay) {
            customerOrders = new ArrayList<>();

            textMenuChoices.setText("");
            reInitializeInputFields();
            JOptionPane.showMessageDialog(new JFrame(), "All the previous order has been cleared!!", "Dialog",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        if (e.getSource() == fileChooserBtn) {
            btnBill.setEnabled(true);
            btnPrepare.setEnabled(true);
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    if (restaurantManagementController.uploadMenuData(selectedFile)) {
                        JOptionPane.showMessageDialog(new JFrame(), "Menu Item data has been successfully uploaded!!", "Dialog",
                                JOptionPane.INFORMATION_MESSAGE);
                        if (breakFastRadio.isSelected()) {
                            populateMenuItem("BreakFast");
                        } else if (lunchRadio.isSelected()) {
                            populateMenuItem("Lunch");
                        } else if (dinnerRadio.isSelected()) {
                            populateMenuItem("Dinner");
                        }
                        fileChooserBtn.setEnabled(false);

                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Error in Uploading File!!", "Dialog",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (HeadlessException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(new JFrame(), "Error in Uploading File!!", "Dialog",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    Logger.getLogger(RestaurantOrderGUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        if (e.getSource() == btnPrepare) {

            if (waitingList.getSelectedIndex() != -1) {
                
                restaurantManagementController.updateOrderStatus(waitingList.getSelectedValuesList(), "Served");
            }
            refreshOrderStatusList();

        }
        if (e.getSource() == btnBill) {
            if (servedList.getSelectedIndex() != -1) {
                
                restaurantManagementController.updateOrderStatus(servedList.getSelectedValuesList(), "Billed");
            }
            refreshOrderStatusList();
        }

        if (e.getSource() == breakFastRadio) {
            populateMenuItem("BreakFast");
        }
        if (e.getSource() == lunchRadio) {
            populateMenuItem("Lunch");
        }
        if (e.getSource() == dinnerRadio) {
            populateMenuItem("Dinner");
        }
        if (e.getSource() == btnQuit) {
            System.exit(0);
        }
    }

    private void populateMenuItem(String mealType) {
        foodItem = new LinkedList<>();
        beverageItem = new LinkedList<>();
        foodItem.addAll(restaurantManagementController.getMenuItemByCourse("food", mealType));
        beverageItem.addAll(restaurantManagementController.getMenuItemByCourse("Beverage", mealType));
        foodList.removeAllItems();

        foodItem.forEach((item) -> {
            foodList.addItem(item);
        });

        beverageList.removeAllItems();
        beverageItem.forEach((item) -> {
            beverageList.addItem(item);
        });
        if (foodItem.size() > 0) {
            fileChooserBtn.setEnabled(false);
        }
    }

    private DefaultListModel<String> getDefaultFoodItemListByStatus(String status) {
        DefaultListModel<String> foodItemListModel = new DefaultListModel<>();
        restaurantManagementController.getOrderItemByStatus(status).forEach((item) -> {
            foodItemListModel.addElement(item);
        });

        return foodItemListModel;
    }

    private void refreshOrderStatusList() {
        DefaultListModel waitingListModel = (DefaultListModel) waitingList.getModel();
        waitingListModel.removeAllElements();
        waitingList.setModel(getDefaultFoodItemListByStatus("Waiting"));

        DefaultListModel servedListModel = (DefaultListModel) servedList.getModel();
        servedListModel.removeAllElements();
        servedList.setModel(getDefaultFoodItemListByStatus("Served"));

        DefaultListModel billedListModel = (DefaultListModel) billedList.getModel();
        billedListModel.removeAllElements();
        billedList.setModel(getDefaultFoodItemListByStatus("Billed"));
    }
}
