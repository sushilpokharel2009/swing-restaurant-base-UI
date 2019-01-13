/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.restutantmanagement;

public class Nutrient {

    public Nutrient() {

    }

    public Nutrient(String nutrientName, float nutrientQuantity) {
        this.nutrientName = nutrientName;
        this.nutrientQuantity = nutrientQuantity;
    }
    
    private String nutrientName;
    private float nutrientQuantity;

    public String getNutrientName() {
        return nutrientName;
    }

    public float getNutrientQuantity() {
        return nutrientQuantity;
    }

    public void setNutrientName(String nutrientName) {
        this.nutrientName = nutrientName;
    }

    public void setNutrientQuantity(float nutrientQuantity) {
        this.nutrientQuantity = nutrientQuantity;
    }

    @Override
    public String toString() {
        return "Nutrient{" + "nutrientName=" + nutrientName + ", nutrientQuantity=" + nutrientQuantity + '}';
    }

}
