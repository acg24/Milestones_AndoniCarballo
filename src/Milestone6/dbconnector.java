package Milestone6;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dbconnector {
    /*Connection connection = null;
    public dbconnector() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mariadb://localhost:3306/milestone6",
                    "root", "root"
            );
            System.out.println("The conexion is opened.");
        } catch (SQLException e) {
            System.out.println("ERROR. The conexion failed");
        }
    }
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
            System.out.println("The conexion is closed.");
        } catch (SQLException e) {
            System.out.println("ERROR. The connexion is not correctly closed");
        }
    }
    public void statment(){
        String[] person= new String[3];
        try (PreparedStatement statement = connection.prepareStatement("""
            SELECT PhotographerId, Name, Awared
            FROM Photographers
            WHERE PhotographerId =  PhotographerId
             """)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                System.out.print("ID: " + rs.getInt("PhotographerId"));
                System.out.print("| Name: " + rs.getString("Name"));
                System.out.println("| Awared: " + rs.getBoolean("Awared"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
    private Connection con;
    private static final String url = "jdbc:mariadb://localhost:3306/kevin";
    private static final String user = "root";
    private static final String pass = "root";
    public dbconnector(){
        Connection con = null;
        try {
            con = DriverManager.getConnection(url,user,pass);
            System.out.println("Conected :)");
        } catch (SQLException e) {
            System.out.println("The connection failed :(");
        }
    }
    public void con(){
        if (con != null){
            try {
                con.close();
                System.out.println(" Connection closed :)");
            } catch (SQLException e) {
                System.out.println("The connection is not correctly closed  :(");
            }
        }
    }
    public List<Photographer> getPhotographer(){
        List<Photographer> photographerList =new ArrayList<Photographer>();


        return photographerList;
    }
}

