package model;

public class Client {

    private int id;
    private String name;
    private String address;
    private int telephone;

    /**
     * The constructor for a Client object
     * @param name
     * @param address
     * @param telephone
     */
    public Client(String name, String address, int telephone) {
        //this.id = id;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
    }

    public Client(int id, String name, String address, int telephone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
    }
    public Client() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

}
