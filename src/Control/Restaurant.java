package Control;

import Model.BaseProduct;
import Model.CompositeProduct;
import Model.MenuItem;
import Model.Order;
import View.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class Restaurant extends Observable implements IRestaurantProcessing {

    private final ArrayList<MenuItem> menu;
    public ArrayList<CompositeProduct> menuComposite;
    public ArrayList<MenuItem> orderedItems;
    public Map<Order, ArrayList<MenuItem>> map;
    public ArrayList<Order> orderList;

    private final MainGUI c_mainGui;
    private final AdministratorGUI c_adminGui;
    private final ChefGUI c_chefGui;
    private final WaiterGUI c_waiterGui;
    private final CompositeGUI c_compGui;

    public StringBuilder sb;
    public int finalPrice;


    public Restaurant(MainGUI view){
        menu = new ArrayList<>();
        menuComposite = new ArrayList<>();
        orderedItems = new ArrayList<>();
        orderList = new ArrayList<>();
        map = new HashMap<>();
        c_mainGui = view;
        c_adminGui = new AdministratorGUI();
        c_chefGui = new ChefGUI(this);
        c_waiterGui = new WaiterGUI();
        c_compGui = new CompositeGUI();
        sb = new StringBuilder();
        createListeners();
    }

    public Map<Order, ArrayList<MenuItem>> getOrderMap() {
        return this.map;
    }

    private void createListeners(){
        c_mainGui.addAdminListener(new operationListener());
        c_mainGui.addWaiterListener(new operationListener());
        c_adminGui.addBackListener(new operationListener());
        c_adminGui.addInsertListener(new operationListener());
        c_adminGui.addDisplayListener(new operationListener());
        c_adminGui.addDeleteListener(new operationListener());
        c_adminGui.addModifyListener(new operationListener());
        c_waiterGui.addBackListener(new operationListener());
        c_waiterGui.addSelectListener(new operationListener());
        c_waiterGui.addOrderListener(new operationListener());
        c_waiterGui.addBillListener(new operationListener());
        c_waiterGui.addDisplayListener(new operationListener());
        c_compGui.addSelectCompListener(new operationListener());
        c_compGui.addCreateCompListener(new operationListener());
    }

    @Override
    public void createNewMenuItem() {
        String price = c_adminGui.priceText.getText();
        String name = c_adminGui.nameText.getText();
        int preSize = menu.size();
        try {
            if (!(name.compareTo("") == 0) && !(price.compareTo("") == 0)) {
                BaseProduct bp = new BaseProduct(c_adminGui.nameText.getText(), Integer.parseInt(c_adminGui.priceText.getText()));
                if (!c_adminGui.existingProduct(menu, name)) {
                    menu.add(bp);
                    int postSize = menu.size();
                    assert postSize == preSize + 1;
                    JOptionPane.showMessageDialog(null, "Item added");
                    c_adminGui.setEmpty();
                }
                else
                    JOptionPane.showMessageDialog(null, "Item already in menu");
            } else if (!(name.compareTo("") == 0)) {
                if (!c_adminGui.existingProduct(menu, name)) {
                    c_adminGui.setVisible(false);
                    c_compGui.setVisible(true);
                    c_compGui.displayTable(menu);
                }
                else
                    JOptionPane.showMessageDialog(null, "Item already in menu");
            } else
                JOptionPane.showMessageDialog(null, "Input error");
        }
        catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Input error");
        }
    }

    @Override
    public void deleteMenuItem(){
        String price = c_adminGui.priceText.getText();
        String name = c_adminGui.nameText.getText();
        boolean exist = true;
        int preSize = menu.size();
        try {
            if (!(name.compareTo("") == 0) && (price.compareTo("") == 0)) {
                Iterator<MenuItem> itr = menu.iterator();
                while (itr.hasNext()) {
                    MenuItem it = itr.next();
                    if (it.getName().compareTo(name) == 0) {
                        itr.remove();
                        int postSize = menu.size();
                        assert postSize == preSize - 1;
                        JOptionPane.showMessageDialog(null, "Item & composites containing it removed");
                        exist = false;
                    }
                }
                c_compGui.deleteAllComp(menu, menuComposite, name);
                if (exist)
                    JOptionPane.showMessageDialog(null, "Item not existent");
            } else
                JOptionPane.showMessageDialog(null, "Input error");
        }
        catch (NullPointerException ex){
            JOptionPane.showMessageDialog(null, "Empty menu");
        }
        c_adminGui.setEmpty();
    }

    @Override
    public void editMenuItem() {
        String price = c_adminGui.priceText.getText();
        String name = c_adminGui.nameText.getText();
        try {
            if (!(name.compareTo("") == 0) && !(price.compareTo("") == 0)){
                c_compGui.modifyAllComp(menu, menuComposite, name, Integer.parseInt(price));
                ListIterator<MenuItem> itr = menu.listIterator();
                while (itr.hasNext()){
                    MenuItem item = itr.next();
                    if (item.getName().compareTo(name) == 0){
                        item.setPrice(Integer.parseInt(price));
                        item.setName(name);
                        itr.set(item);
                        JOptionPane.showMessageDialog(null, "Item & composites containing it modified");
                    }
                }
            }
            else
                JOptionPane.showMessageDialog(null, "Input error");
        }
        catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Input error");
        }
        c_adminGui.setEmpty();
    }

    @Override
    public void createNewOrder() {
        try {
            if (!(c_waiterGui.idText.getText().compareTo("") == 0) && !(c_waiterGui.tableText.getText().compareTo("") == 0)
                    && !(c_waiterGui.dayText.getText().compareTo("") == 0) && !(c_waiterGui.monthText.getText().compareTo("") == 0)
                    && !(c_waiterGui.yearText.getText().compareTo("") == 0)) {
                int orderID = Integer.parseInt(c_waiterGui.idText.getText());
                int table = Integer.parseInt(c_waiterGui.tableText.getText());
                int day = Integer.parseInt(c_waiterGui.dayText.getText());
                int month = Integer.parseInt(c_waiterGui.monthText.getText());
                int year = Integer.parseInt(c_waiterGui.yearText.getText());
                Order order = new Order(orderID, table, day, month, year);
                Order oldOrder = order;
                orderList.add(order);
                map.put(order, orderedItems);
                assert oldOrder.equals(order);
                c_waiterGui.clearArr();
                String s = "New order!";
                setChanged();
                notifyObservers(s);
            }
            else
                JOptionPane.showMessageDialog(null, "Input error");
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Input error");
        }
        c_waiterGui.emptyText();
    }

    @Override
    public void computePriceOrder() {
        finalPrice = 0;
        int initialPrice = finalPrice;
        try {
            int ID = Integer.parseInt(c_waiterGui.idText.getText());
            Order order = c_waiterGui.findByID(orderList, ID);
            ArrayList<MenuItem> array = map.get(order);
            Iterator<MenuItem> iterator = array.iterator();
            while (iterator.hasNext()) {
                MenuItem item = iterator.next();
                finalPrice += item.getPrice();
            }
            assert finalPrice != initialPrice;
        } catch (NumberFormatException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Order not found");
        }
    }

    @Override
    public void generateBill() {
        int ID;
        Order order;
        String init = "";
        assert init.equals("");
        try {
            ID = Integer.parseInt(c_waiterGui.idText.getText());
            computePriceOrder();
            order = c_waiterGui.findByID(orderList, ID);
             try{
                 init = c_waiterGui.generateBillText(finalPrice, order, map);
                 assert !init.equals("");
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("bill" + ID + ".txt")));
                writer.write(init);
                writer.close();
                JOptionPane.showMessageDialog(null, "Bill created!");
             }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }
        catch (NumberFormatException | NullPointerException ex){
            JOptionPane.showMessageDialog(null, "Error creating the bill");
        }
    }

    class operationListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent){
            if (actionEvent.getSource() == c_mainGui.adminButton){
                c_mainGui.setVisible(false);
                c_adminGui.setVisible(true);
            }
            else if (actionEvent.getSource() == c_mainGui.waiterButton) {
                c_mainGui.setVisible(false);
                c_waiterGui.setVisible(true);
                c_waiterGui.displayTable(menu);
            }
            else if (actionEvent.getSource() == c_waiterGui.backButton){
               c_waiterGui.setVisible(false);
               c_mainGui.setVisible(true);
            }
            else if (actionEvent.getSource() ==c_adminGui.backButton){
                c_adminGui.setVisible(false);
                c_mainGui.setVisible(true);
            }
            else if (actionEvent.getSource() == c_adminGui.addButton){
                createNewMenuItem();
            }
            else if (actionEvent.getSource() == c_adminGui.displayButton){
                c_adminGui.displayTable(menu);
            }
            else if (actionEvent.getSource() == c_adminGui.modifyButton){
                editMenuItem();
            }
            else if (actionEvent.getSource() == c_adminGui.deleteButton){
                deleteMenuItem();
            }
            else if (actionEvent.getSource() == c_waiterGui.createOrder){
                createNewOrder();
            }
            else if (actionEvent.getSource() == c_waiterGui.selectButton){
                orderedItems = c_waiterGui.selectProduct();
                System.out.println("Item selected!");
            }
            else if (actionEvent.getSource() == c_waiterGui.displayButton){
                c_waiterGui.displayOrders(orderList, getOrderMap());
            }
            else if (actionEvent.getSource() == c_waiterGui.createBill){
                generateBill();
            }
            else if (actionEvent.getSource() == c_compGui.selectProductButton){
                c_compGui.selectItem();
                System.out.println("Item selected!");
            }
            else if (actionEvent.getSource() == c_compGui.createCompositeButton){
                CompositeProduct cp = new CompositeProduct();
                cp.composition = c_compGui.getComposition();
                cp.setName(c_adminGui.nameText.getText());
                cp.setPrice(cp.computePrice());
                menu.add(cp);
                menuComposite.add(cp);
                JOptionPane.showMessageDialog(null, "Item added");
                c_adminGui.setEmpty();
                c_compGui.setVisible(false);
                c_adminGui.setVisible(true);
            }
        }
    }
}
