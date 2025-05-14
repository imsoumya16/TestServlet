package ConnectionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBPool {

   public Connection getConnection() throws SQLException, IOException {
    // Retrieve properties from the database.properties file
    String dbName = getPropertyValues("DATABASE_NAME");
    String dbIp = getPropertyValues("DATABASE_IP");
    String dbUsername = getPropertyValues("DATABASE_USERNAME");
    String dbPassword = getPropertyValues("DATABASE_PWD");

    // Construct the JDBC URL for SQL Server with SSL disabled (for testing purposes)
    String jdbcUrl = "jdbc:sqlserver://" + dbIp + ";databaseName=" + dbName + ";encrypt=false;trustServerCertificate=true";

    try {
        // Load the SQL Server JDBC driver
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    } catch (ClassNotFoundException e) {
        System.out.println("JDBC Driver not found : "+ e);
    }

    // Establish the connection
    Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

    // Check if the connection is valid, and print a message
    if (connection != null && !connection.isClosed()) {
        System.out.println("Connection established for TestServlet");
    } else {
        System.out.println("Connection not established ");
    }

    // Return the connection
    return connection;
}

    public String getPropertyValues(String propName) throws IOException {
        Properties prop = new Properties();
        InputStream inputStream = null;

        inputStream = getClass().getClassLoader().getResourceAsStream("database.properties");
        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("Property file not found in the classpath");
        }

        return prop.getProperty(propName);
    }
}
