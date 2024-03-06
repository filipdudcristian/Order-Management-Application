package bll;

import dao.BillDAO;
import model.Bill;

import java.sql.Timestamp;
import java.util.List;

/**
 * Class that has the methods to validate if the queries executed correctly
 */
public class BillBLL {

    private BillDAO cd = new BillDAO();

    /**
     * Inserts a new bill in the table
     * @param bill
     * @return if the operation was successful
     * @throws Exception if the bill could not be inserted in the table
     */
    public boolean insertBill(Bill bill) throws Exception {
        if ( cd.insert(bill, new Timestamp(System.currentTimeMillis())) == null) {
            throw new Exception("The Bill could not be inserted in the database!");
        }
        return true;
    }

}
