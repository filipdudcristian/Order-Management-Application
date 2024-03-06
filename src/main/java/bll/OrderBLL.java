package bll;

import dao.OrderDAO;
import model.Product_orders;

import java.util.List;

/**
 * Class that has the methods to validate if the queries executed correctly
 */
public class OrderBLL {

    private OrderDAO cd = new OrderDAO();

    /**
     * Searches in the table if a order with the given id exists
     * @param id
     * @return the order
     * @throws Exception if the order with the given id does not exist
     */
    public Product_orders findOrderByID(int id) throws Exception {
        Product_orders productorders = cd.findById(id);
        if (productorders == null) {
            throw new Exception("The order with id =" + id + " was not found!");
        }
        return productorders;
    }

    /**
     * Retrieves all orders from the table
     * @return a list with all the orders in the table
     * @throws Exception if no orders exist
     */
    public List<Product_orders> findAllOrder() throws Exception{
        List<Product_orders> productorders = cd.findAll();
        if (productorders == null) {
            throw new Exception("There are no Orders in the database!");
        }
        return productorders;
    }

    /**
     * Inserts a new order in the table
     * @param productOrders
     * @return if the operation was successful
     * @throws Exception if the order could not be inserted in the table
     */
    public boolean insertOrder(Product_orders productOrders) throws Exception {
        if ( cd.insert(productOrders) == null) {
            throw new Exception("The Order could not be inserted in the database!");
        }
        return true;
    }

    /**
     * Updates the data of a order from the table
     * @param productOrders
     * @return if the operation was successful
     * @throws Exception if the order could not be updated
     */
    public boolean updateOrder (Product_orders productOrders) throws Exception{
        if(cd.update(productOrders) == null)
        {
            throw new Exception("The client could not be updated");
        }
        return true;
    }


    /**
     * Deletes the order with the given id from the table
     * @param id
     * @throws Exception if the order was not found
     */
    public void deleteOrder(int id) throws Exception {
        boolean r = cd.delete(id);
        if (r == false) {
            throw new Exception("The Order was not found!");
        }
    }
}
