package View;

import Model.MenuItem;
import Model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class WaiterGUI extends JFrame{

    private ArrayList<MenuItem> order;

    public JButton createOrder = new JButton("Order");
    public JButton createBill = new JButton("Bill");
    public JButton backButton = new JButton("Back");
    public JButton selectButton = new JButton("Select");
    public JButton displayButton = new JButton("Display");
    public JTextField idText = new JTextField();
    public JTextField tableText = new JTextField();
    public JTextField dayText = new JTextField();
    public JTextField monthText = new JTextField();
    public JTextField yearText = new JTextField();
    public JLabel idLabel = new JLabel("ID");
    public JLabel tableLabel = new JLabel("Table");
    public JTable table;
    public JTable orderTable;
    JScrollPane sp = new JScrollPane();
    JScrollPane spp = new JScrollPane();
    StringBuilder sb;

    private void sBounds(){
        this.setLocation(370, 100);
        createOrder.setBounds(10, 10, 80, 30);
        createBill.setBounds(10, 50, 80, 30);
        selectButton.setBounds(10, 90, 80, 30);
        backButton.setBounds(100, 90, 80, 30);
        idLabel.setBounds(530, 10, 80, 30);
        tableLabel.setBounds(530, 50, 80, 30);
        idText.setBounds(590, 16, 180, 20);
        tableText.setBounds(590, 56, 180, 20);
        dayText.setBounds(590, 96, 30, 20);
        yearText.setBounds(740, 96, 30, 20);
        monthText.setBounds(665, 96, 30, 20);
        displayButton.setBounds(100 ,10,80,30);
    }

    public WaiterGUI(){
        order = new ArrayList<>();
        sBounds();
        JPanel content = new JPanel();
        content.setLayout(null);
        content.setPreferredSize(new Dimension(800, 600));
        content.add(createOrder);
        content.add(createBill);
        content.add(backButton);
        content.add(selectButton);
        content.add(idLabel);
        content.add(tableLabel);
        content.add(idText);
        content.add(tableText);
        content.add(dayText);
        content.add(monthText);
        content.add(yearText);
        content.add(displayButton);
        content.add(sp);
        content.add(spp);

        this.setContentPane(content);
        this.pack();
        this.setTitle("Waiter panel");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void displayTable(ArrayList<Model.MenuItem> list){
        String[] col = {"Name", "Price"};
        DefaultTableModel tab = new DefaultTableModel();
        tab.setColumnIdentifiers(col);
        Object[] obj = new Object[2];
        Iterator<Model.MenuItem> iterator = list.iterator();
        while (iterator.hasNext()){
            MenuItem item = iterator.next();
            obj[0] = item.getName();
            obj[1] = item.getPrice();
            tab.addRow(obj);
        }
        table = new JTable(tab);
        sp.setVisible(true);
        sp.setBounds(10, 130, 370, 450);
        sp.setViewportView(table);
    }

    public void displayOrders(ArrayList<Order> orders, Map<Order, ArrayList<MenuItem>> map){
        String[] col = {"OrderID", "Table", "Date", "Items"};
        DefaultTableModel tab = new DefaultTableModel();
        tab.setColumnIdentifiers(col);
        Object[] obj = new Object[4];
        String s;
        Iterator<Order> iterator = orders.iterator();
        while (iterator.hasNext()){
            sb = new StringBuilder();
            Order order = iterator.next();
            obj[0] = order.getOrderID();
            obj[1] = order.getTable();
            s = order.getDay() + "/" + order.getMonth() + "/" + order.getYear();
            obj[2] = s;
            ArrayList<MenuItem> arr = map.get(order);
            Iterator<MenuItem> itt = arr.iterator();
            while (itt.hasNext()) {
                MenuItem mt = itt.next();
                sb.append(mt.getName());
                if (itt.hasNext())
                    sb.append(",");
            }
            obj[3] = sb.toString();
            tab.addRow(obj);
        }
        orderTable = new JTable(tab);
        spp.setVisible(true);
        spp.setBounds(390, 130, 370, 450);
        spp.setViewportView(orderTable);
    }

    public ArrayList<MenuItem> selectProduct(){
        int row = table.getSelectedRow();
        String nameAux = table.getModel().getValueAt(row, 0).toString();
        String priceAux = table.getModel().getValueAt(row, 1).toString();
        Model.MenuItem item = new MenuItem();
        item.setName(nameAux);
        item.setPrice(Integer.parseInt(priceAux));
        order.add(item);
        return order;
    }

    public String generateBillText(int price, Order order, Map<Order, ArrayList<MenuItem>> map){
        StringBuilder string = new StringBuilder();
        string.append("Bill for order: ").append(order.getOrderID()).append(System.lineSeparator());
        string.append("Table served: ").append(order.getTable()).append(System.lineSeparator());
        string.append("Date: ").append(order.getDay()).append("/").append(order.getMonth()).append("/").append(order.getYear()).append(System.lineSeparator());
        string.append("Order: ").append(System.lineSeparator());

        ArrayList<MenuItem> list = map.get(order);
        Iterator<MenuItem> iterator = list.iterator();
        while (iterator.hasNext()){
            MenuItem item = iterator.next();
            string.append(item.getName()).append(" ").append(item.getPrice()).append(System.lineSeparator());
        }
        string.append("Total price: ").append(price).append(System.lineSeparator());
        return string.toString();
    }

    public Order findByID(ArrayList<Order> orderList, int ID){
        for (Order order: orderList){
            if (order.getOrderID() == ID)
                return order;
        }
        return null;
    }

    public void clearArr(){
        order = new ArrayList<>();
    }

    public void emptyText(){
        idText.setText("");
        tableText.setText("");
        dayText.setText("");
        monthText.setText("");
        yearText.setText("");
    }

    public void addSelectListener(ActionListener select_lis){
        selectButton.addActionListener(select_lis);
    }
    public void addBackListener(ActionListener back_lis){
        backButton.addActionListener(back_lis);
    }
    public void addOrderListener(ActionListener order_lis){
        createOrder.addActionListener(order_lis);
    }
    public void addDisplayListener(ActionListener display_lis){
        displayButton.addActionListener(display_lis);
    }
    public void addBillListener(ActionListener bill_lis){
        createBill.addActionListener(bill_lis);
    }

}
