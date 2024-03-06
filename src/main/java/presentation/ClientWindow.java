package presentation;

import bll.ClientBLL;
import model.Client;

import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that creates the window for the clients table
 */
public class ClientWindow extends MainWindow<Client>{

    private JFrame frame;
    private JTextField textField_ID;
    private JTextField textField_NAME;
    private JTextField textField_ADDRESS;
    private JTextField textField_TELEPHONE;
    private JTable table;
    private JScrollPane sp = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    private ClientBLL Client = new ClientBLL();


    /**
     * Create the application.
     */
    public ClientWindow() {
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
            setTable(Client.findAllClients(),table);
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

        JLabel lblID = new JLabel("ID");
        lblID.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblID.setBounds(495, 43, 41, 19);
        frame.getContentPane().add(lblID);

        textField_NAME = new JTextField();
        textField_NAME.setColumns(10);
        textField_NAME.setBounds(66, 72, 120, 32);
        frame.getContentPane().add(textField_NAME);

        JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblName.setBounds(100, 43, 62, 19);
        frame.getContentPane().add(lblName);

        textField_ADDRESS = new JTextField();
        textField_ADDRESS.setColumns(10);
        textField_ADDRESS.setBounds(66, 144, 120, 32);
        frame.getContentPane().add(textField_ADDRESS);

        JLabel lblAddress = new JLabel("Address");
        lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblAddress.setBounds(87, 115, 75, 19);
        frame.getContentPane().add(lblAddress);

        textField_TELEPHONE = new JTextField();
        textField_TELEPHONE.setColumns(10);
        textField_TELEPHONE.setBounds(66, 216, 120, 32);
        frame.getContentPane().add(textField_TELEPHONE);

        JLabel lblTelephone = new JLabel("Telephone");
        lblTelephone.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblTelephone.setBounds(76, 187, 103, 19);
        frame.getContentPane().add(lblTelephone);

        JButton btnFindByID = new JButton("Find by ID");
        btnFindByID.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnFindByID.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg) {
                try {
                    int id = Integer.parseInt(textField_ID.getText());
                    List<model.Client> ls = new ArrayList<Client>();
                    try {
                        ls.add(Client.findClientByID("id", id));
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
                    setTable(Client.findAllClients(),table);
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
                    int tel = Integer.parseInt(textField_TELEPHONE.getText());
                    Client newC = new Client(textField_NAME.getText(), textField_ADDRESS.getText(), tel);
                    try {
                        Client.insertClient(newC);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }

                    try {
                        setTable(Client.findAllClients(),table);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Insert valid data!");
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
                    int tel = Integer.parseInt(textField_TELEPHONE.getText());
                    Client newClient = new Client(id, textField_NAME.getText(), textField_ADDRESS.getText(), tel);
                    try {
                        Client.updateClient(newClient);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }

                    try {
                        setTable(Client.findAllClients(),table);
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
                        Client.deleteClient(id);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }

                    try {
                        setTable(Client.findAllClients(),table);
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
     * @param clientList
     */
//    public void setTable(List<Client> clientList) {
//
//        if (!clientList.isEmpty()) {
//            DefaultTableModel model = new DefaultTableModel();
//            for (Field field : Client.class.getDeclaredFields()) {
//                model.addColumn(field.getName());
//            }
//
//            for (Client client : clientList) {
//                Object[] rowData = {client.getId(), client.getName(), client.getAddress(), client.getTelephone()};
//                model.addRow(rowData);
//            }
//            table.setModel(model);
//        }
//    }



}



