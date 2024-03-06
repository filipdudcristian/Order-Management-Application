package model;

public class Product {

    private int id;
    private String name;
    private int price;
    private String producer;
    private int quantity;

    /**
     * The constructor for a Product object
     *
     * @param id
     * @param name
     * @param price
     * @param producer
     * @param quantity
     */
    public Product(int id, String name, int price, String producer, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.producer = producer;
        this.quantity = quantity;
    }

    public Product(String name, int price, String producer, int quantity) {
        this.name = name;
        this.price = price;
        this.producer = producer;
        this.quantity = quantity;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
