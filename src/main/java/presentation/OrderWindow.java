package presentation;

import bll.BillBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import model.Bill;
import model.Product;
import model.Product_orders;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *  Class that creates the window for the orders table
 *
 */
public class OrderWindow extends MainWindow<Product_orders> {

    private JFrame frame;
    private JTextField textField_ID;
    private JTextField textField_ClientID;
    private JTextField textField_ProductId;
    private JTextField textField_Quantity;
    private JTable table = new JTable();
    private JLabel lblProductID;
    private JScrollPane sp = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);;


    private OrderBLL order = new OrderBLL();
    private BillBLL bill = new BillBLL();
    private ProductBLL product = new ProductBLL();

    /**
     * Create the application.
     */
    public OrderWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(152, 196, 98));
        frame.setBounds(100, 100, 690, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        table = new JTable();

        try {
            setTable(order.findAllOrder(),table);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sp.setViewportView(table);
        sp.setBounds(66, 286, 545, 240);
        frame.getContentPane().add(sp);
        sp.setVisible(true);
        table.setEnabled(false);

        textField_ID = new JTextField();
        textField_ID.setBounds(448, 72, 120, 32);
        frame.getContentPane().add(textField_ID);
        textField_ID.setColumns(10);

        textField_ClientID = new JTextField();
        textField_ClientID.setColumns(10);
        textField_ClientID.setBounds(100, 67, 120, 32);
        frame.getContentPane().add(textField_ClientID);

        JLabel lblClientID = new JLabel("ClientID");
        lblClientID.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblClientID.setBounds(121, 38, 86, 19);
        frame.getContentPane().add(lblClientID);

        textField_ProductId = new JTextField();
        textField_ProductId.setColumns(10);
        textField_ProductId.setBounds(100, 139, 120, 32);
        frame.getContentPane().add(textField_ProductId);

        lblProductID = new JLabel("ProductID");
        lblProductID.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblProductID.setBounds(120, 110, 99, 19);
        frame.getContentPane().add(lblProductID);

        textField_Quantity = new JTextField();
        textField_Quantity.setColumns(10);
        textField_Quantity.setBounds(100, 211, 120, 32);
        frame.getContentPane().add(textField_Quantity);

        JLabel lblQuantity = new JLabel("Quantity");
        lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblQuantity.setBounds(121, 181, 86, 19);
        frame.getContentPane().add(lblQuantity);

        JButton btnInsertOrder = new JButton("Insert order");
        btnInsertOrder.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg) {

                try {
                    int clientId = Integer.parseInt(textField_ClientID.getText());
                    int productId = Integer.parseInt(textField_ProductId.getText());
                    int quantity = Integer.parseInt(textField_Quantity.getText());

                    Product productInfo = null;

                    try {
                        productInfo = product.findProductByID("id", productId);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                    if (productInfo.getQuantity() < quantity) {
                        JOptionPane.showMessageDialog(null, "Nu s-a putut realiza comanda!\nNu sunt destule produse in stoc!");
                        return;
                    }

                    Product_orders o = new Product_orders(0, clientId, productId, quantity, (int) (quantity * productInfo.getPrice()));

                    try {
                        order.insertOrder(o);

                        int orderId = Integer.parseInt(table.getModel().getValueAt(table.getRowCount() - 1, 0).toString()) + 1;

                        Bill billInfo = new Bill(0, orderId, quantity, (int) (quantity * productInfo.getPrice()));
                        bill.insertBill(billInfo);

                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage());
                        return;
                    }

                    try {
                        setTable(order.findAllOrder(),table);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    productInfo.setQuantity(productInfo.getQuantity() - quantity);

                    try {
                        product.updateProduct(productInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Could not place the order!");
                }
            }
        });

        try {
            setTable(order.findAllOrder(),table);
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnInsertOrder.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnInsertOrder.setBounds(238, 126, 146, 32);
        frame.getContentPane().add(btnInsertOrder);

        JButton btnFindByID = new JButton("Find by ID");
        btnFindByID.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnFindByID.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg) {
                try {
                    int id = Integer.parseInt(textField_ID.getText());
                    List<model.Product_orders> ls = new ArrayList<Product_orders>();
                    try {
                        ls.add(order.findOrderByID(id));
                        setTable(ls,table);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "The Client was not found!");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Insert a valid ID!");
                }
            }
        });
        btnFindByID.setBounds(438, 124, 146, 32);
        frame.getContentPane().add(btnFindByID);

        JButton btnFindAll = new JButton("Find All");
        btnFindAll.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg) {
                try {
                    setTable(order.findAllOrder(),table);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        });
        btnFindAll.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnFindAll.setBounds(238, 83, 146, 32);
        frame.getContentPane().add(btnFindAll);


        JButton btnUpdate = new JButton("Update");

        btnUpdate.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg) {

                try {
                    int id = Integer.parseInt(textField_ID.getText());
                    int clientId = Integer.parseInt(textField_ClientID.getText());
                    int productId = Integer.parseInt(textField_ProductId.getText());
                    int quantity = Integer.parseInt(textField_Quantity.getText());
                    Product productInfo = null;

                    try {
                        productInfo = product.findProductByID("id", productId);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }

                    Product_orders newOrder = new Product_orders(id, clientId, productId, quantity, (int) (quantity * productInfo.getPrice()));
                    try {
                        order.updateOrder(newOrder);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }

                    try {
                        setTable(order.findAllOrder(),table);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Insert valid data!");
                }
            }
        });
        btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnUpdate.setBounds(238, 168, 146, 32);
        frame.getContentPane().add(btnUpdate);


        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg) {

                try {
                    int id = Integer.parseInt(textField_ID.getText());
                    try {
                        order.deleteOrder(id);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }

                    try {
                        setTable(order.findAllOrder(),table);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Insert valid data!");
                }
            }
        });
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnDelete.setBounds(238, 210, 146, 32);
        frame.getContentPane().add(btnDelete);

        frame.setVisible(true);
    }
    /**
     * It receives a list of clients and populates the table with them
     * @param ordersList
     */
//    public void setTable(List<Product_orders> ordersList) {
//
//        if (!ordersList.isEmpty()) {
//            DefaultTableModel model = new DefaultTableModel();
//            for (Field field : Product_orders.class.getDeclaredFields()) {
//                model.addColumn(field.getName());
//            }
//
//            for (Product_orders orders : ordersList) {
//                Object[] rowData = {orders.getId(), orders.getIdClient(), orders.getIdProduct(), orders.getQuantity(), orders.getPrice(), orders.getDate()};
//                model.addRow(rowData);
//            }
//            table.setModel(model);
//        }
//    }
}
