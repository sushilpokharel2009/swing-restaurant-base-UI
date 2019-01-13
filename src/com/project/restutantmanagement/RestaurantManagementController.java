/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.restutantmanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* This is a controller class having all the business logics */
public class RestaurantManagementController {

    private final String ENERGY = "Energy(KJ)";
    private final String PROTEIN = "Protein (g)";
    private final String CARBOHYADRATES = "Carbohydrates with sugar alcohols (g)";
    private final String FAT = "Total fat (g)";
    private final String FIBRE = "Dietary fibre (g)";

    public boolean uploadMenuData(File menuData) throws FileNotFoundException, IOException {
        List<MenuItem> menuItem = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(menuData))) {
            String currentMenuItem;
            System.out.println(br.readLine());
            while ((currentMenuItem = br.readLine()) != null) {
                String[] menuItemData = currentMenuItem.split(",");
                menuItem.add(new MenuItem(Integer.parseInt(menuItemData[8]), menuItemData[0], menuItemData[2], menuItemData[1], getNutrientsObjects(Float.parseFloat(menuItemData[3]), Float.parseFloat(menuItemData[4]), Float.parseFloat(menuItemData[5]), Float.parseFloat(menuItemData[6]), Float.parseFloat(menuItemData[7]))));
            }
        }
        return DatabaseUtility.insertMenuItems(menuItem);

    }

    public List<MenuItem> getMenuItemByCourse(String course, String mealType) {
        List<MenuItem> menuItem = new ArrayList<>();
        List<String[]> menuItemArrayList = DatabaseUtility.getMenuItemsByCourse(course, mealType);
        for (String[] menuItemData : menuItemArrayList) {
            menuItem.add(new MenuItem(Integer.parseInt(menuItemData[8]), menuItemData[0], menuItemData[2],menuItemData[1], getNutrientsObjects(Float.parseFloat(menuItemData[3]), Float.parseFloat(menuItemData[4]), Float.parseFloat(menuItemData[5]), Float.parseFloat(menuItemData[6]), Float.parseFloat(menuItemData[7]))));
        }
        return menuItem;
    }

    public void saveCustomerOrder(OrderedCustomer orderedCustomer) {
        DatabaseUtility.saveCustomerOrder(orderedCustomer);
    }
    
    public List<String> getOrderItemByStatus(String status){
        return DatabaseUtility.getItemWithStatus(status);
    }
    
    public void updateOrderStatus(List<String> orderItems, String status){
        List<String> orderIds = new LinkedList<>();
        for(String orderItem: orderItems){
            orderIds.add(orderItem.split(":")[1]);
        }
        DatabaseUtility.updateOrderStatus(orderIds, status);
    }
    private List<Nutrient> getNutrientsObjects(float energy, float protein, float carbohyadrates, float fat, float fibre) {
        List<Nutrient> nutrients = new ArrayList<>();

        nutrients.add(new Nutrient(ENERGY, energy));
        nutrients.add(new Nutrient(PROTEIN, protein));
        nutrients.add(new Nutrient(CARBOHYADRATES, carbohyadrates));
        nutrients.add(new Nutrient(FAT, fat));
        nutrients.add(new Nutrient(FIBRE, fibre));

        return nutrients;
    }
    
    public List<OrderedCustomer> getCustomerOrderByTableNumber(int tableNumber){
        return DatabaseUtility.getCustomerOrderByTableNumber(tableNumber);
    }

}
