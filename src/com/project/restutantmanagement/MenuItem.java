/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.restutantmanagement;

import java.util.List;

public class MenuItem {

    public MenuItem() {

    }

    public MenuItem(String itemName, List<Nutrient> itemNutrients) {
        this.itemName = itemName;
        this.itemNutrients = itemNutrients;
    }

//    public MenuItem(int itemId, String itemCourse, String itemName, List<Nutrient> itemNutrients) {
//        this.itemCourse = itemCourse;
//        this.itemId = itemId;
//        this.itemName = itemName;
//        this.itemNutrients = itemNutrients;
//    }
    
     public MenuItem(int itemId, String itemCourse, String itemName, String mealType, List<Nutrient> itemNutrients) {
        this.itemCourse = itemCourse;
        this.itemId = itemId;
        this.itemName = itemName;
        this.mealType = mealType;
        this.itemNutrients = itemNutrients;
    }


    public MenuItem(MenuItem menuItem) {
        this.itemName = menuItem.itemName;
        this.itemNutrients = menuItem.getItemNutrients();
    }
    
    private String itemName;
    private String itemCourse;
    private String mealType;

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getItemCourse() {
        return itemCourse;
    }

    public void setItemCourse(String itemCourse) {
        this.itemCourse = itemCourse;
    }
    private int itemId;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    private List<Nutrient> itemNutrients;

    public String getItemName() {
        return itemName;
    }

    public List<Nutrient> getItemNutrients() {
        return itemNutrients;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemNutrients(List<Nutrient> itemNutrients) {
        this.itemNutrients = itemNutrients;
    }

    @Override
    public String toString() {
        return this.itemName;
    }

}
