package bll;

import dao.ProductDAO;
import model.Product;

import java.util.List;

/**
 * Class that has the methods to validate if the queries executed correctly
 */
public class ProductBLL {

    private ProductDAO cd = new ProductDAO();

    /**
     * Searches in the table if a product with the given id exists
     * @param s
     * @param id
     * @return the product
     * @throws Exception if the product with the given id does not exist
     */
    public Product findProductByID(String s, int id) throws Exception {
        Product product = cd.findById(id);
        if (cd.findById(id) == null) {
            throw new Exception("The product with id =" + id + " was not found!");
        }
        return product;
    }

    /**
     * Retrieves all products from the table
     * @return a list with all the products in the table
     * @throws Exception if no products exist
     */
    public List<Product> findAllProducts() throws Exception{
        List<Product> c = cd.findAll();
        if (c == null) {
            throw new Exception("There are no Products in the database!");
        }
        return c;
    }

    /**
     * Inserts a new product in the table
     * @param product
     * @return if the operation was successful
     * @throws Exception if the product could not be inserted in the table
     */
    public boolean insertProduct(Product product) throws Exception {
        //int c = cd.insert(cl);
        if (cd.insert(product) == null) {
            throw new Exception("The Product could not be inserted in the database!");
        }
        return true;
    }

    /**
     * Updates the data of a product from the table
     * @param product
     * @return if the operation was successful
     * @throws Exception if the product could not be updated
     */
    public boolean updateProduct (Product product) throws Exception{
        if (cd.update(product) == null) {
            throw new Exception("The Product could not be updated!");
        }
        return true;
    }

    /**
     * Deletes the product with the given id from the table
     * @param id
     * @throws Exception if the product was not found
     */
    public void deleteProduct(int id) throws Exception {
        boolean r = cd.delete(id);
        if (r == false) {
            throw new Exception("The Product was not found!");
        }
    }
}
