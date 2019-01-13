/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.restutantmanagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseUtility {

    private final static String ENERGY = "Energy(KJ)";
    private final static String PROTEIN = "Protein (g)";
    private final static String CARBOHYADRATES = "Carbohydrates with sugar alcohols (g)";
    private final static String FAT = "Total fat (g)";
    private final static String FIBRE = "Dietary fibre (g)";

    class DBConnection {

        private final Connection connection = null;

        private DBConnection() {

        }

        public Connection getConnectionInstance() {
            if (connection == null) {
                DBConnection dbConnection = new DBConnection();
            }
            return connection;
        }

    }
    private final static String MENU_ITEM_TABLE = "CREATE TABLE IF NOT EXISTS  `MenuItem` ( menuItemId int NOT NULL,itemCourse VARCHAR(100), itemName VARCHAR(100), mealType VARCHAR(100), PRIMARY KEY (menuItemId))";
    private final static String MENU_NUTRIENT_TABLE = "CREATE TABLE IF NOT EXISTS  `Nutrient` ( nutrientId int NOT NULL AUTO_INCREMENT, menuItemId int, nutrientName VARCHAR(100),  nutrientQuantity VARCHAR(100), PRIMARY KEY (nutrientId), FOREIGN KEY (menuItemId) REFERENCES MenuItem(menuItemId))";
    private final static String ORDER_CUSTOMER_TABLE = "CREATE TABLE IF NOT EXISTS  `OrderedCustomer` ( orderId int NOT NULL AUTO_INCREMENT, customerName VARCHAR(100),  orderDate DATE, tableNumber int, orderStatus VARCHAR(100),orderType VARCHAR(100), PRIMARY KEY (orderId))";
    private final static String ORDER_MENU_ITEM_TABLE = "CREATE TABLE IF NOT EXISTS  `OrderMenuItem` ( id int NOT NULL AUTO_INCREMENT, orderId int NOT NULL, menuItemId int NOT NULL, PRIMARY KEY (id) )";

    private static Connection createConnection() throws IOException, ClassNotFoundException, SQLException {

        Connection connection;
        connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/restaurant_management", "root", "");
        return connection;
    }

    private static void runCreateTableStatement(Connection conn) throws IOException, ClassNotFoundException, SQLException {

        Statement statement = conn.createStatement();
        statement.execute(MENU_ITEM_TABLE);
        statement.execute(MENU_NUTRIENT_TABLE);
        statement.execute(ORDER_CUSTOMER_TABLE);
        statement.execute(ORDER_MENU_ITEM_TABLE);

    }

    public static boolean insertMenuItems(List<MenuItem> menuItems) {
        Connection con;
        try {
            con = createConnection();
            runCreateTableStatement(con);
            PreparedStatement menuStatement = con.prepareStatement("insert into MenuItem values(?,?,?,?)");
            PreparedStatement nutrientStatement = con.prepareStatement("insert into Nutrient (menuItemId, nutrientName, nutrientQuantity) values(?,?,?)");
            for (MenuItem menuItem : menuItems) {
                menuStatement.setInt(1, menuItem.getItemId());
                menuStatement.setString(2, menuItem.getItemCourse());
                menuStatement.setString(3, menuItem.getItemName());
                menuStatement.setString(4, menuItem.getMealType());
                menuStatement.executeUpdate();
                for (Nutrient nutrient : menuItem.getItemNutrients()) {
                    nutrientStatement.setInt(1, menuItem.getItemId());
                    nutrientStatement.setString(2, nutrient.getNutrientName());
                    nutrientStatement.setString(3, nutrient.getNutrientQuantity() + "");
                    nutrientStatement.executeUpdate();
                }
            }
            con.close();
            return true;
        } catch (IOException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void saveCustomerOrder(OrderedCustomer customerOrder) {
        Connection con;
        try {
            con = createConnection();
            runCreateTableStatement(con);
            String customerOrderSql = "insert into OrderedCustomer (customerName, orderDate, tableNumber,orderStatus, orderType ) values(?,?,?,?,?)";
            String orderMenuItemSql = "insert into orderMenuItem (orderId, menuItemId) values(?,?)";
            PreparedStatement orderCustomerStatement = con.prepareStatement(customerOrderSql, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement orderMenuItemStatement = con.prepareStatement(orderMenuItemSql);
            orderCustomerStatement.setString(1, customerOrder.getCustomerFirstName());
            orderCustomerStatement.setDate(2, new Date(new java.util.Date().getTime()));
            orderCustomerStatement.setInt(3, customerOrder.getTableNumber());
            orderCustomerStatement.setString(4, customerOrder.getOrderStatus());
            orderCustomerStatement.setString(5, customerOrder.getOrderType());
            orderCustomerStatement.executeUpdate();
            ResultSet rs = orderCustomerStatement.getGeneratedKeys();
            int orderId = 0;
            if (rs.next()) {
                orderId = rs.getInt(1);
            }
            for (MenuItem menuItem : customerOrder.getMenuItems()) {
                orderMenuItemStatement.setInt(1, orderId);
                orderMenuItemStatement.setInt(2, menuItem.getItemId());
                orderMenuItemStatement.executeUpdate();
            }
            con.close();

        } catch (IOException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseUtility.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static List<String[]> getMenuItemsByCourse(String course, String mealType) {
        Connection conn;
        try {
            conn = createConnection();
            runCreateTableStatement(conn);
            List<String[]> menuList = new ArrayList<>();
            String selectMenu = "select * from MenuItem where itemCourse =? and mealType = ?;";
            String selectNutrient = "select * from Nutrient where menuItemId =? ";
            PreparedStatement menuStatement = conn.prepareStatement(selectMenu);
            menuStatement.setString(1, course);
            menuStatement.setString(2, mealType);
            ResultSet rs = menuStatement.executeQuery();

            while (rs.next()) {
                String[] menuItemArr = new String[9];
                menuItemArr[0] = rs.getString("itemCourse");
                menuItemArr[1] = rs.getString("mealType");
                menuItemArr[2] = rs.getString("itemName");
                int menuItemId = rs.getInt("menuItemId");
                menuItemArr[8] = menuItemId + "";
                PreparedStatement nutrientStatement = conn.prepareStatement(selectNutrient);
                nutrientStatement.setInt(1, menuItemId);
                ResultSet rss = nutrientStatement.executeQuery();
                while (rss.next()) {
                    String nutrientName = rss.getString("nutrientName");
                    String nutrientQuantity = rss.getString("nutrientQuantity");
                    switch (nutrientName) {
                        case ENERGY:
                            menuItemArr[3] = nutrientQuantity;
                            break;
                        case PROTEIN:
                            menuItemArr[4] = nutrientQuantity;
                            break;
                        case CARBOHYADRATES:
                            menuItemArr[5] = nutrientQuantity;
                            break;
                        case FAT:
                            menuItemArr[6] = nutrientQuantity;
                            break;
                        case FIBRE:
                            menuItemArr[7] = nutrientQuantity;
                            break;
                        default:
                            break;
                    }
                }
                menuList.add(menuItemArr);
            }

            conn.close();
            return menuList;
        } catch (IOException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static List<String> getItemWithStatus(String status) {
        Connection conn;
        try {
            conn = createConnection();
            runCreateTableStatement(conn);
            List<String> itemList = new LinkedList<>();
            String selectFoodQuery = "select mi.menuItemId, oc.orderId from OrderedCustomer oc join OrderMenuItem om join MenuItem mi where om.orderId = oc.orderId and mi.menuItemId = om.menuItemId and oc.orderStatus=?; ";

            PreparedStatement selectFoodStatement = conn.prepareStatement(selectFoodQuery);
            selectFoodStatement.setString(1, status);
            ResultSet rs = selectFoodStatement.executeQuery();

            while (rs.next()) {
                itemList.add(rs.getString("menuItemId") + ":" + rs.getString("orderId"));
            }

            conn.close();
            return itemList;
        } catch (IOException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static List<OrderedCustomer> getCustomerOrderByTableNumber(int tableNumber) {
        Connection conn;
        try {
            List<OrderedCustomer> customerOrders = new ArrayList<>();
            conn = createConnection();
            runCreateTableStatement(conn);
            String selectCustomerOrderQuery = "select * from OrderedCustomer where tableNumber = ? ;";
            String selectOrderMenuQuery = "select * from OrderMenuItem where orderId = ? ;";
            String selectMenuItemQuery = "select * from MenuItem where menuItemId = ? ;";

            PreparedStatement selectCustomerOrderStmt = conn.prepareStatement(selectCustomerOrderQuery);
            selectCustomerOrderStmt.setInt(1, tableNumber);
            ResultSet rs = selectCustomerOrderStmt.executeQuery();

            while (rs.next()) {
                OrderedCustomer oc = new OrderedCustomer();
                int orderId = rs.getInt("orderId");
                oc.setCustomerFirstName(rs.getString("customerName"));
                oc.setOrderStatus(rs.getString("orderStatus"));
                oc.setOrderType(rs.getString("orderType"));
                oc.setTableNumber(rs.getInt("tableNumber"));
                PreparedStatement selectOrderMenuStmt = conn.prepareStatement(selectOrderMenuQuery);
                selectOrderMenuStmt.setInt(1, orderId);
                ResultSet rss = selectOrderMenuStmt.executeQuery();
                List<MenuItem> menuItems = new ArrayList<>();
                while (rss.next()) {
                    PreparedStatement selectMenuItemStmt = conn.prepareStatement(selectMenuItemQuery);
                    selectMenuItemStmt.setInt(1, rss.getInt("menuItemId"));
                    ResultSet rsss = selectMenuItemStmt.executeQuery();
                    while (rsss.next()) {
                        MenuItem menuItem = new MenuItem();
                        menuItem.setItemCourse(rsss.getString("itemCourse"));
                        menuItem.setItemName(rsss.getString("itemName"));
                        menuItem.setMealType(rsss.getString("mealType"));
                        menuItems.add(menuItem);
                    }
                    oc.setMenuItems(menuItems);
                }

                customerOrders.add(oc);
            }

            conn.close();
    
            return customerOrders;
        } catch (IOException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void updateOrderStatus(List<String> orderIds, String status) {
        Connection conn;
        try {
            conn = createConnection();
            runCreateTableStatement(conn);

            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < orderIds.size(); i++) {
                builder.append("?,");
            }
            String updateOrderStatusSql = "update OrderedCustomer set orderStatus =? where orderId in ("
                    + builder.deleteCharAt(builder.length() - 1).toString() + ");";

            PreparedStatement updateOrderStatusStmt = conn.prepareStatement(updateOrderStatusSql);
            updateOrderStatusStmt.setString(1, status);
            for (int i = 0; i < orderIds.size(); i++) {
                updateOrderStatusStmt.setInt(i + 2, Integer.parseInt(orderIds.get(i)));
            }
            updateOrderStatusStmt.executeUpdate();

            conn.close();

        } catch (IOException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseUtility.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String args[]) throws IOException, ClassNotFoundException, SQLException {

        // updateOrderStatus(Arrays.asList("1","2","3"), "Billed");
    }

}
