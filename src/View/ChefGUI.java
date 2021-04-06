package View;

import Control.Restaurant;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ChefGUI extends JFrame implements Observer {
    public ChefGUI(Restaurant restaurant){
        restaurant.addObserver(this);
        this.setLocation(570, 100);
        JPanel content = new JPanel();
        content.setLayout(null);
        content.setPreferredSize(new Dimension(400, 600));

        this.setContentPane(content);
        this.pack();
        this.setTitle("Chef panel");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    @Override
    public void update(Observable o, Object arg) {
        this.setVisible(true);
        int p = JOptionPane.showConfirmDialog(null, arg, "Kitchen notifier", 2);
        if (p == 0){
            JOptionPane.showMessageDialog(null,"Chef cooks.");
            this.setVisible(false);
        }
        else {
            JOptionPane.showMessageDialog(null, "Chef is already cooking.");
            this.setVisible(false);
        }
    }

}
