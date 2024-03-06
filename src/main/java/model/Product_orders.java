package model;

import java.sql.Timestamp;

public class Product_orders {

    private int id;
    private int idClient;
    private int idProduct;
    private int quantity;
    private int price;
    private Timestamp date;

    /**\
     * The constructor for a Product_orders object
     * @param id
     * @param idClient
     * @param idProduct
     * @param quantity
     * @param price
     */
    public Product_orders(int id, int idClient, int idProduct, int quantity, int price) {
        this.id = id;
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.price = price;
        this.date = new Timestamp(System.currentTimeMillis());
    }

    public Product_orders() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
