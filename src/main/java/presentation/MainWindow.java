package presentation;

import model.Client;

import java.awt.EventQueue;

import javax.swing.*;

import java.awt.Font;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Vector;

/**
 * Class that creates the window to access all other windows for each table
 */
public class MainWindow<T> {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainWindow window = new MainWindow();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MainWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame with buttons that create a new window for each table.
     */
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(152, 196, 98));
        frame.setBounds(100, 100, 500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton btnClients = new JButton("Clients");
        btnClients.setFont(new Font("Tahoma", Font.PLAIN, 22));
        btnClients.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg) {

                ClientWindow clientWindow = new ClientWindow();

            }

        });
        btnClients.setBounds(120, 92, 239, 60);
        frame.getContentPane().add(btnClients);

        JButton btnProducts = new JButton("Products");
        btnProducts.setFont(new Font("Tahoma", Font.PLAIN, 22));
        btnProducts.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg) {

                ProductWindow productWindow = new ProductWindow();

            }

        });
        btnProducts.setBounds(120, 197, 239, 60);
        frame.getContentPane().add(btnProducts);

        JButton btnOrders = new JButton("Orders");
        btnOrders.setFont(new Font("Tahoma", Font.PLAIN, 22));
        btnOrders.setBounds(120, 308, 239, 60);
        frame.getContentPane().add(btnOrders);
        btnOrders.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg) {

                OrderWindow orderWindow = new OrderWindow();

            }

        });
    }

    public void setTable(List<T> list, JTable table) {

        if (!list.isEmpty()) {
            DefaultTableModel model = new DefaultTableModel();
            for (Field field : list.get(0).getClass().getDeclaredFields()) {
                model.addColumn(field.getName());
                System.out.println(field.getName());
            }

            for (T t : list) {
                Vector row = new Vector<>();
                for (Field field : t.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    Object value;
                    try {
                        value = field.get(t);
                        row.add(value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
                model.addRow(row);
            }
            table.setModel(model);
        }
    }
}