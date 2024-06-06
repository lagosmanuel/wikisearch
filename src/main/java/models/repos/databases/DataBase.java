package models.repos.databases;

import utils.UIStrings;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DataBase {
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(UIStrings.DB_URL);
    }
}
