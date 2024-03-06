
package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     * Creates and calls the query to return all data from a table
     * @return a list that contains all rows of data of a given object
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = new String("SELECT * FROM " + type.getSimpleName() + " ;");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            rs = statement.executeQuery();

            return createObjects(rs);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Creates and calls the query to return the row with the given id
     * @param id
     * @return an object that has the corresponding id in the table
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = new String("SELECT * FROM " + type.getSimpleName() + " WHERE id =?");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Creates the list with all the rows from a table
     * @param resultSet
     * @return a list with all the rows from a table
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        try {
            while (resultSet.next()) {
                T instance = type.getDeclaredConstructor().newInstance();

                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();

                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (IllegalAccessException | SecurityException | InvocationTargetException | IllegalArgumentException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            System.out.println(e.getMessage());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    /**
     * Inserts a new row in a table with data from the parameter object
     * @param t
     * @return the object that has been inserted in the table
     */
    public T insert(T t) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = new String("INSERT INTO " + type.getSimpleName() + " VALUES ( ");
        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;

            try {
                value = field.get(t);
                query = query + "'" + value + "', ";
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        String queryNew = query.substring(0, query.length() - 2) + ");";
        System.out.println(queryNew);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(queryNew, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Inserts a new row in a table with data from the parameter object and a date
     * @param t
     * @param date
     * @return the object that has been inserted in the table
     */
    public T insert(T t, Timestamp date) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = new String("INSERT INTO " + type.getSimpleName() + " VALUES ( ");

        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;

            try {
                value = field.get(t);
                query = query + "'" + value + "', ";
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        query = query + "'" + date + "', ";

        String queryNew = query.substring(0, query.length() - 2) + ");";
        System.out.println(queryNew);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(queryNew, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Updates a row with new given data
     * @param t
     * @return the object that has been inserted in the table
     */
    public T update(T t) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = new String("UPDATE " + type.getSimpleName() + " SET ");

        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(t);
                query = query + field.getName() + "='" + value + "' , ";
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        String queryNew = query.substring(0, query.length() - 2) + "WHERE ";
        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;

            try {
                value = field.get(t);
                queryNew = queryNew + field.getName() + "='" + value + "' ;";
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            break;
        }

        System.out.println(queryNew);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(queryNew, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Removes from the table the row with the id given as parameter
     * @param id
     * @return true if the object has been found and deleted, otherwise false
     */
    public boolean delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String query = new String("DELETE FROM " + type.getSimpleName() + " WHERE " + "id" + "=" + id + ";");

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {

            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
            return false;
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

}

