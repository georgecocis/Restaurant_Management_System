package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame {
    public JButton adminButton = new JButton("Administrator");
    public JButton waiterButton = new JButton("Waiter");
    public JLabel optionLabel = new JLabel("Which panel would you like to open?");
    Font f = new Font("Times New Roman", Font.BOLD, 30);

    private void sBounds(){
        this.setLocation(470, 100);
        optionLabel.setBounds(65, 20, 500, 300);
        adminButton.setBounds(20, 250, 160, 35);
        waiterButton.setBounds(420, 250, 160, 35);optionLabel.setFont(f);
    }

    public MainGUI(){
        JPanel content = new JPanel();
        content.setLayout(null);
        sBounds();
        content.setPreferredSize(new Dimension(600, 400));
        content.add(optionLabel);
        content.add(adminButton);
        content.add(waiterButton);

        this.setContentPane(content);
        this.pack();
        this.setTitle("Main panel");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addAdminListener(ActionListener admin_lis){
        adminButton.addActionListener(admin_lis);
    }

    public void addWaiterListener(ActionListener wait_lis){
        waiterButton.addActionListener(wait_lis);
    }
}
