package models.repos;

import utils.UIStrings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DataBase {
    protected static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(UIStrings.DB_URL);
    }

    protected static void executeUpdate(String query) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(UIStrings.DB_QUERY_TIMEOUT);
        statement.executeUpdate(query);
        statement.close();
        connection.close();
    }
}
