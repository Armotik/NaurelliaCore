package fr.armotik.naurelliacore.utiles;

import fr.armotik.naurelliacore.Naurelliacore;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Database {

    private static BasicDataSource dataSource;
    private static final ThreadLocal<Connection> connection = new ThreadLocal<>();
    private static final List<String> databaseStringList = Naurelliacore.getPlugin().getConfig().getStringList("Database");

    private Database() {
        throw new IllegalStateException("Utility Class");
    }

    private static void openDataSource() {
            dataSource = new BasicDataSource();
            dataSource.setUrl(databaseStringList.get(0));
            dataSource.setUsername(databaseStringList.get(1));
            dataSource.setPassword(databaseStringList.get(3));
            dataSource.setInitialSize(10);
            dataSource.setMaxTotal(500);
    }

    /**
     * Get new connection instance
     * @return connection instance or null in case of error
     */
    public static Connection getConnection() {

        if (dataSource.isClosed()) openDataSource();

        Connection conn = connection.get();

        try {

            if (conn == null) {

                conn = dataSource.getConnection();
            }
        } catch (SQLException e) {

            ExceptionsManager.sqlExceptionLog(e);
            return null;
        }

        return conn;
    }

    /**
     * Execute Query
     * @param sqlQuery query to execute
     * @return ResultSet or null in case of error
     */
    public static ResultSet executeQuery(String sqlQuery) {

        Connection conn = getConnection();
        PreparedStatement statement = null;

        try {
            assert conn != null;
             statement = conn.prepareStatement(sqlQuery);
            return statement.executeQuery();

        } catch (SQLException e) {
            ExceptionsManager.sqlExceptionLog(e);
            return null;
        } finally {
            try {
                if(statement != null) statement.close();
            } catch (SQLException e) {

                ExceptionsManager.sqlExceptionLog(e);
            }
        }
    }

    /**
     * Update Query
     * @param sqlQuery query to execute
     * @return the number new lines or -1 in case of error
     */
    public static int executeUpdate(String sqlQuery) {

        Connection conn = getConnection();
        PreparedStatement statement = null;

        try {
            assert conn != null;
             statement = conn.prepareStatement(sqlQuery);
            return statement.executeUpdate();

        } catch (SQLException e) {
            ExceptionsManager.sqlExceptionLog(e);
            return -1;
        } finally {
            try {
                if(statement != null) statement.close();
            } catch (SQLException e) {

                ExceptionsManager.sqlExceptionLog(e);
            }
        }
    }

    /**
     * Close current connection
     */
    public static void close() {

        Connection conn = connection.get();

        try {

            if (conn != null) {
                conn.close();
            }

            connection.remove();
        } catch (SQLException e) {
            ExceptionsManager.sqlExceptionLog(e);
        }
    }

    /**
     * Close the data source
     */
    public static void closeAll() {

        try {
            dataSource.close();

        } catch (SQLException e) {
            ExceptionsManager.sqlExceptionLog(e);
        }
    }
}
