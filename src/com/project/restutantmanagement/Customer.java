/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.restutantmanagement;

public class Customer {
	
	private String customerFirstName;
    private int tableNumber;
    
    public Customer() {

    }

    public Customer(String customerFirstName, int tableNumber) {
        this.customerFirstName = customerFirstName;
        this.tableNumber = tableNumber;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    @Override
    public String toString() {
        return "Customer{" + "customerFirstName=" + customerFirstName + ", tableNumber=" + tableNumber + '}';
    }

}
