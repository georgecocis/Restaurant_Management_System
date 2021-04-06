package View;

import Model.MenuItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

public class AdministratorGUI extends JFrame {

    public JButton addButton = new JButton("Add");
    public JButton modifyButton = new JButton("Modify");
    public JButton deleteButton = new JButton("Delete");
    public JButton backButton = new JButton("Back");
    public JButton displayButton = new JButton("Display");
    public JTextField nameText = new JTextField();
    public JTextField priceText = new JTextField(10);
    private final JLabel nameLabel = new JLabel("Name");
    private final JLabel priceLabel = new JLabel("Price");
    public JTable table;
    JScrollPane sp = new JScrollPane();


    private void sBounds(){
        this.setLocation(570, 100);
        addButton.setBounds(10, 10, 80, 30);
        modifyButton.setBounds(10, 50, 80, 30);
        deleteButton.setBounds(10, 90, 80, 30);
        displayButton.setBounds(100, 90, 80, 30);
        backButton.setBounds(300, 90, 80, 30);
        nameLabel.setBounds(130, 10, 80, 30);
        priceLabel.setBounds(130, 50, 80, 30);
        nameText.setBounds(190, 16, 180, 20);
        priceText.setBounds(190, 56, 180, 20);
    }

    public AdministratorGUI(){
        JPanel content = new JPanel();
        content.setLayout(null);
        content.setPreferredSize(new Dimension(400, 600));
        sBounds();
        content.add(addButton);
        content.add(modifyButton);
        content.add(deleteButton);
        content.add(backButton);
        content.add(displayButton);
        content.add(nameLabel);
        content.add(priceLabel);
        content.add(nameText);
        content.add(priceText);
        content.add(sp);

        this.setContentPane(content);
        this.pack();
        this.setTitle("Administrator panel");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void displayTable(ArrayList<MenuItem> list){
        String[] col = {"Name", "Price"};
        DefaultTableModel tab = new DefaultTableModel();
        tab.setColumnIdentifiers(col);
        Object[] obj = new Object[2];
        Iterator<MenuItem> iterator = list.iterator();
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

    public void setEmpty(){
        priceText.setText("");
        nameText.setText("");
    }

    public boolean existingProduct(ArrayList<MenuItem> menu, String name){
        Iterator<MenuItem> itr = menu.iterator();
        while (itr.hasNext()){
            MenuItem item = itr.next();
            if (item.getName().compareTo(name) == 0)
                return true;
        }
        return false;
    }

    public void addBackListener(ActionListener back_lis){
        backButton.addActionListener(back_lis);
    }
    public void addInsertListener(ActionListener insert_lis){
        addButton.addActionListener(insert_lis);
    }

    public void addDisplayListener(ActionListener display_lis){
        displayButton.addActionListener(display_lis);
    }

    public void addDeleteListener(ActionListener delete_lis){
        deleteButton.addActionListener(delete_lis);
    }

    public void addModifyListener(ActionListener modify_lis){
        modifyButton.addActionListener(modify_lis);
    }

}
