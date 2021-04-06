package View;

import Model.MenuItem;
import Model.CompositeProduct;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class CompositeGUI extends JFrame {

    private final CompositeProduct cp = new CompositeProduct();

    public JButton selectProductButton = new JButton("Select");
    public JButton createCompositeButton = new JButton("Create");
    public JTable table;
    JScrollPane sp = new JScrollPane();


    private void sBounds(){
        this.setLocation(570, 100);
        selectProductButton.setBounds(10, 10, 80, 30);
        createCompositeButton.setBounds(10, 50, 80, 30);
    }

    public CompositeGUI(){
        sBounds();
        JPanel content = new JPanel();
        content.setLayout(null);
        content.setPreferredSize(new Dimension(400, 600));
        content.add(selectProductButton);
        content.add(createCompositeButton);
        content.add(sp);

        this.setContentPane(content);
        this.pack();
        this.setTitle("Composite product panel");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void displayTable(ArrayList<Model.MenuItem> list){
        cp.setComposition();
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

    public void selectItem(){
        int row = table.getSelectedRow();
        String nameAux = table.getModel().getValueAt(row, 0).toString();
        String priceAux = table.getModel().getValueAt(row, 1).toString();
        MenuItem item = new MenuItem();
        item.setName(nameAux);
        item.setPrice(Integer.parseInt(priceAux));
        cp.addComposition(item);
    }

    public void deleteAllComp(ArrayList<MenuItem> menu, ArrayList<CompositeProduct> menuComp, String name){
        Iterator<CompositeProduct> itr = menuComp.iterator();
        while (itr.hasNext()){
            CompositeProduct newCp = itr.next();
            Iterator<MenuItem> itrr = newCp.getComposition().iterator();
            while (itrr.hasNext()){
                MenuItem item = itrr.next();
                if (item.getName().compareTo(name) == 0){
                    Iterator<MenuItem> itrrr = menu.iterator();
                    while (itrrr.hasNext()){
                        MenuItem menuItem = itrrr.next();
                        if (menuItem.getName().compareTo(newCp.getName()) == 0){
                            itrrr.remove();
                            itr.remove();
                        }
                    }
                }
            }
        }
    }

    public void modifyAllComp(ArrayList<MenuItem> menu, ArrayList<CompositeProduct> menuComp, String name, int price){
        Iterator<CompositeProduct> itr = menuComp.iterator();
        while (itr.hasNext()){
            CompositeProduct newCp = itr.next();
            ListIterator<MenuItem> itrr = newCp.getComposition().listIterator();
            while (itrr.hasNext()){
                MenuItem item = itrr.next();
                if (item.getName().compareTo(name) == 0){
                   item.setName(name);
                   item.setPrice(price);
                   itrr.set(item);
                   newCp.setPrice(newCp.computePrice());
                   ListIterator<MenuItem> itrrr = menu.listIterator();
                   while (itrrr.hasNext()){
                       MenuItem item1 = itrrr.next();
                       if (item1.getName().compareTo(newCp.getName()) == 0){
                           itrrr.set(newCp);
                       }
                   }
                }
            }
        }
    }

    public ArrayList<MenuItem> getComposition(){
        return cp.getComposition();
    }

    public void addSelectCompListener(ActionListener selComp_lis){
        selectProductButton.addActionListener(selComp_lis);
    }

    public void addCreateCompListener(ActionListener selCreate_lis){
        createCompositeButton.addActionListener(selCreate_lis);
    }

}
