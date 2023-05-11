package Milestone6;



import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class dbconnector {
    private Connection conn;
    private static final String url = "jdbc:mariadb://localhost:3306/milestone6";
    private static final String user = "root";
    private static final String pass = "root";

    public dbconnector () {
        conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Correctly connected to the databesa :)");
        } catch (SQLException e) {
            System.out.println("Error! Can't conect to the database :( ");
        }
    }

    public void closeCon () {
        if(conn != null){
            try {
                conn.close();
                System.out.println("Disconnected!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Milestone6.Photographer> getPhotographers() {
        List<Milestone6.Photographer> photographersList = new ArrayList<Milestone6.Photographer>();
        Statement stmnt = null;
        ResultSet rs = null;

        try {
            stmnt = conn.createStatement();
            if(stmnt.execute("SELECT * FROM Photographers;")) {
                rs = stmnt.getResultSet();
                while (rs.next()) {
                    int photographerId = rs.getInt("PhotographerId");
                    String name = rs.getString("Name");
                    Boolean awarded = rs.getBoolean("Awared");
                    photographersList.add(new Milestone6.Photographer(photographerId, name, awarded));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return photographersList;
    }


    public List<Milestone6.Picture> getPictures(int photographerIndex, Date datePicker) {
        List<Milestone6.Picture> picturesList = new ArrayList<Milestone6.Picture>();
        PreparedStatement stmnt = null;
        ResultSet rs = null;

        try {
            if(datePicker != null) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String datePickerString = df.format(datePicker);
                System.out.println(datePickerString);
                stmnt = conn.prepareStatement("SELECT * FROM Pictures WHERE PhotographerId = ? AND Date > ?");
                stmnt.setInt(1, this.getPhotographers().get(photographerIndex).getPhotographerId());
                stmnt.setString(2, datePickerString);
            } else {
                stmnt = conn.prepareStatement("SELECT * FROM Pictures WHERE PhotographerId = ?;");
                stmnt.setInt(1, this.getPhotographers().get(photographerIndex).getPhotographerId());
            }
            rs = stmnt.executeQuery();

            while(rs.next()){
                int pictureId = rs.getInt("PictureId");
                String title = rs.getString("Title");
                Date date = rs.getDate("Date");
                String file = rs.getString("File");
                int visits = rs.getInt("Visits");
                int photographerId = rs.getInt("PhotographerId");
                picturesList.add(new Picture(pictureId, title, date, file, visits, this.getPhotographers().get(photographerIndex)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return picturesList;
    }
}
