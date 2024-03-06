package bll;

import dao.ClientDAO;
import model.Client;

import java.util.List;

/**
 * Class that has the methods to validate if the queries executed correctly
 */
public class ClientBLL {
    private ClientDAO cd = new ClientDAO();

    /**
     * Searches in the table if a client with the given id exists
     * @param s
     * @param id
     * @return the client
     * @throws Exception if the client with the given id does not exist
     */
    public Client findClientByID(String s, int id) throws Exception {
        Client client = cd.findById(id);
        if (client == null) {
            throw new Exception("The student with id =" + id + " was not found!");
        }
        return client;
    }

    /**
     * Retrieves all clients from the table
     * @return a list with all the clients in the table
     * @throws Exception if no clients exist
     */
    public List<Client> findAllClients() throws Exception{
        List<Client> c = cd.findAll();
        if (c == null) {
            throw new Exception("There are no Clients in the database!");
        }
        return c;
    }

    /**
     * Inserts a new client in the table
     * @param client
     * @return if the operation was successful
     * @throws Exception if the client could not be inserted in the table
     */
    public boolean insertClient(Client client) throws Exception {
        if ( cd.insert(client) == null) {
            throw new Exception("The Client could not be inserted in the database!");
        }
        return true;
    }

    /**
     * Updates the data of a client from the table
     * @param client
     * @return if the operation was successful
     * @throws Exception if the client could not be updated
     */
    public boolean updateClient (Client client) throws Exception{
        if(cd.update(client) == null)
        {
            throw new Exception("The client could not be updated");
        }
        return true;
    }

    /**
     * Deletes the client with the given id from the table
     * @param id
     * @throws Exception if the client was not found
     */
    public void deleteClient(int id) throws Exception {
        boolean r = cd.delete(id);
        if (r == false) {
            throw new Exception("The Client was not found!");
        }
    }
}
