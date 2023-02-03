package fr.armotik.naurelliacore.utiles;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class DatabaseTest {

    @Test
    public void databaseConnectionTest() {

        Connection connection = Database.getConnection();
        Assertions.assertNotEquals(null, connection);

        Database.close();
        Assertions.assertNull(connection);

        connection = Database.getConnection();
        Assertions.assertNotEquals(null, connection);

        Database.closeAll();
        Assertions.assertNull(connection);

        Connection connection1 = Database.getConnection();
        Assertions.assertNotEquals(null, connection1);

        Connection connection2 = Database.getConnection();
        Assertions.assertNotEquals(null, connection2);

        Connection connection3 = Database.getConnection();
        Assertions.assertNotEquals(null, connection3);

        Database.closeAll();
        Assertions.assertNull(connection1);
        Assertions.assertNull(connection2);
        Assertions.assertNull(connection3);
    }
}
