import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Databse {
    Connection conn;
    Statement statem;

    public Databse() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql:///IdealTablet", "root", "@Radhakrishna297");
            statem = conn.createStatement();
        }

        catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Class Not Found", JOptionPane.ERROR_MESSAGE);
        }
    }
}
