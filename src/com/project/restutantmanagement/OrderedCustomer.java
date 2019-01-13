/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.restutantmanagement;

import java.util.List;

enum OrderStatus {
    WAITING,
    INPROCESS,
    SERVED,
    BILLED
}

public class OrderedCustomer extends Customer {

    public OrderedCustomer() {

    }

    // These variables are for calculating total nutrition of each attribute
    private final String ENERGY = "Energy(KJ)";
    private final String PROTEIN = "Protein (g)";
    private final String CARBOHYADRATES = "Carbohydrates with sugar alcohols (g)";
    private final String FAT = "Total fat (g)";
    private final String FIBRE = "Dietary fibre (g)";

    public OrderedCustomer(String customerName, int tableNumber, List<MenuItem> menuItems) {
        super(customerName, tableNumber);
        this.menuItems = menuItems;

    }

    public OrderedCustomer(String customerName, int tableNumber, List<MenuItem> menuItems, String orderType) {
        super(customerName, tableNumber);
        this.menuItems = menuItems;
        this.orderType = orderType;
    }

    public OrderedCustomer(String customerName, int tableNumber, List<MenuItem> menuItems, String orderType, String orderStatus) {
        super(customerName, tableNumber);
        this.menuItems = menuItems;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
    }

    private String orderType;
    private String orderStatus;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    private List<MenuItem> menuItems;

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    private float getTotalNutration(String nutrationType) {
        float totalNutritutionValue = 0;
        for (MenuItem menuItem : this.menuItems) {
            for (Nutrient nutrient : menuItem.getItemNutrients()) {
                if (nutrationType.equals(nutrient.getNutrientName())) {
                    totalNutritutionValue += nutrient.getNutrientQuantity();
                }
            }
        }
        return totalNutritutionValue;
    }

    public float getTotalEnergyValue() {

        return getTotalNutration(ENERGY);
    }

    public float getTotalProtienValue() {
        return getTotalNutration(PROTEIN);
    }

    public float getTotalCarbohydrateValue() {
        return getTotalNutration(CARBOHYADRATES);
    }

    public float getTotalFatValue() {
        return getTotalNutration(FAT);
    }

    public float getTotalFibreValue() {
        return getTotalNutration(FIBRE);
    }

    @Override
    public String toString() {
        return super.toString() + " OrderedCustomer{" + "menuItems=" + menuItems + '}';
    }

}
