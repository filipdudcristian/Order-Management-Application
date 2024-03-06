package presentation;

import bll.ProductBLL;
import model.Product;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *  Class that creates the window for the product table
 *
 */
public class ProductWindow extends MainWindow<Product>{

    private JFrame frame;
    private JTextField textField_ID;
    private JTextField textField_NAME;
    private JTextField textField_PRICE;
    private JTextField textField_PRODUCER;
    private JTextField textField_QUANTITY;
    private JTable table;
    private JScrollPane sp = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    private ProductBLL Product = new ProductBLL();


    /**
     * Create the application.
     */
    public ProductWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(152, 196, 98));
        frame.setBounds(100, 100, 690, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        table = new JTable();

        try {
            setTable(Product.findAllProducts(),table);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sp.setViewportView(table);

        sp.setBounds(66, 325, 545, 240);
        frame.getContentPane().add(sp);
        sp.setVisible(true);
        table.setEnabled(false);


        textField_ID = new JTextField();
        textField_ID.setBounds(448, 72, 120, 32);
        frame.getContentPane().add(textField_ID);
        textField_ID.setColumns(10);

        JLabel lblID = new JLabel("ID");
        lblID.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblID.setBounds(495, 43, 41, 19);
        frame.getContentPane().add(lblID);

        textField_NAME = new JTextField();
        textField_NAME.setColumns(10);
        textField_NAME.setBounds(66, 53, 120, 32);
        frame.getContentPane().add(textField_NAME);

        JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblName.setBounds(100, 24, 62, 19);
        frame.getContentPane().add(lblName);

        textField_PRICE = new JTextField();
        textField_PRICE.setColumns(10);
        textField_PRICE.setBounds(66, 125, 120, 32);
        frame.getContentPane().add(textField_PRICE);

        JLabel lblPrice = new JLabel("Price");
        lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblPrice.setBounds(100, 95, 75, 19);
        frame.getContentPane().add(lblPrice);

        textField_PRODUCER = new JTextField();
        textField_PRODUCER.setColumns(10);
        textField_PRODUCER.setBounds(66, 197, 120, 32);
        frame.getContentPane().add(textField_PRODUCER);

        JLabel lblProducer = new JLabel("Producer");
        lblProducer.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblProducer.setBounds(88, 168, 84, 19);
        frame.getContentPane().add(lblProducer);

        JButton btnFindByID = new JButton("Find by ID");
        btnFindByID.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnFindByID.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg) {
                try{
                    int id = Integer.parseInt(textField_ID.getText());
                    List<model.Product> ls = new ArrayList<Product>();
                    try {
                        ls.add(Product.findProductByID("id", id));
                        setTable(ls,table);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "The Product was not found!");
                    }
                } catch (Exception e){
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
                    setTable(Product.findAllProducts(),table);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        });
        btnFindAll.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnFindAll.setBounds(238, 83, 146, 32);
        frame.getContentPane().add(btnFindAll);

        JButton btnInsert = new JButton("Insert");
        btnInsert.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg) {

                try {
                    int pr = Integer.parseInt(textField_PRICE.getText());
                    int qt = Integer.parseInt(textField_QUANTITY.getText());

                    Product newC = new Product(textField_NAME.getText(), pr, textField_PRODUCER.getText(), qt);
                    try {
                        Product.insertProduct(newC);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }

                    try {
                        setTable(Product.findAllProducts(),table);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Invalid data!");
                }

            }
        });
        btnInsert.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnInsert.setBounds(238, 126, 146, 32);
        frame.getContentPane().add(btnInsert);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg) {
                try {
                    int id = Integer.parseInt(textField_ID.getText());
                    int price = Integer.parseInt(textField_PRICE.getText());
                    int quantity = Integer.parseInt(textField_QUANTITY.getText());
                    Product newProduct = new Product(id,textField_NAME.getText(), price, textField_PRODUCER.getText(), quantity);
                    try {
                        Product.updateProduct(newProduct);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }

                    try {
                        setTable(Product.findAllProducts(),table);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                }catch (Exception e)
                {
                    JOptionPane.showMessageDialog(null, "Invalid data!");
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
                        Product.deleteProduct(id);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }

                    try {
                        setTable(Product.findAllProducts(),table);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                }catch (Exception e)
                {
                    JOptionPane.showMessageDialog(null, "Invalid ID!");
                }
            }
        });

        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnDelete.setBounds(238, 210, 146, 32);
        frame.getContentPane().add(btnDelete);


        textField_QUANTITY = new JTextField();
        textField_QUANTITY.setColumns(10);
        textField_QUANTITY.setBounds(66, 268, 120, 32);
        frame.getContentPane().add(textField_QUANTITY);

        JLabel lblQuantity = new JLabel("Quantity");
        lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblQuantity.setBounds(88, 239, 84, 19);
        frame.getContentPane().add(lblQuantity);

        frame.setVisible(true);
    }

    /**
     * It receives a list of clients and populates the table with them
     * @param productList
     */
//    public void setTable(List<Product> productList) {
//
//        if (!productList.isEmpty()) {
//            DefaultTableModel model = new DefaultTableModel();
//            for (Field field : Product.class.getDeclaredFields()) {
//                model.addColumn(field.getName());
//            }
//            for (Product product : productList) {
//                Object[] rowData = {product.getId(), product.getName(), product.getPrice(), product.getProducer(), product.getQuantity()};
//                model.addRow(rowData);
//            }
//            table.setModel(model);
//        }
//    }

}
