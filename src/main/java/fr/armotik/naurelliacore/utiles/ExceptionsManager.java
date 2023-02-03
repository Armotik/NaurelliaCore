package fr.armotik.naurelliacore.utiles;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionsManager {

    private static final Logger logger = Logger.getLogger(ExceptionsManager.class.getName());

    /**
     * Handle SQL exceptions
     * @param e SQLException
     */
    public static void sqlExceptionLog(SQLException e) {

        logger.log(Level.SEVERE, "SQLException \nError Code N°" + e.getErrorCode() +  "\nSQLState : " + e.getSQLState() + "\nMessage : " +e.getMessage() + "\n");

        for (int i = 0 ; i < e.getStackTrace().length ; i++) {

            logger.log(Level.INFO, "At " + e.getStackTrace()[i] + "\n");
        }
    }
}
