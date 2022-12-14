package easv_MTunes.DAL.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;


public class DBConnector {
    private SQLServerDataSource dataSource;


    //Defining server and database
    public DBConnector() {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setDatabaseName("MyTunes_Gruppe_5");
        dataSource.setUser("CSe22A_12"); // Type your given username
        dataSource.setPassword("Hamada007"); // Type your given password
        dataSource.setTrustServerCertificate(true);
        dataSource.setPortNumber(1433); // From school


    }
    //Creating connection to server and database
    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }

    //Only to see if we have connection or not
    public static void main(String[] args) throws SQLServerException {
        DBConnector dbConnector = new DBConnector();
        try(Connection connection =dbConnector.getConnection())
        {
            if(!connection.isClosed())
            {
                System.out.println("Is it opened..?\n" + !connection.isClosed());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
