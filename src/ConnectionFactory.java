import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static ConnectionFactory connectionFactory = null;

    private final String dbURL = "jdbc:mysql://localhost:3306/sounddb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final String dbUser = "sounduser";
    private final String dbPwd = "1234";

    private Connection connection = null;

    private ConnectionFactory() {}

    public Connection getConnection() {

        if (connection == null) {
            try {
                connection = DriverManager.getConnection(dbURL, dbUser, dbPwd);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Es Konnte keine Verbindung mit der Datenbank hergestellt werden. Bitte starten sie die Applikation erneut.");
                System.exit(10);
            }
        }
        return connection;
    }

    public static ConnectionFactory getInstance() {
        if (connectionFactory == null) {
            connectionFactory = new ConnectionFactory();
        }
        return connectionFactory;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}