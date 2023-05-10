package Milestone7;



import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class DBConnector {
    private Connection myConnection;
    private static final String driver = "org.mariadb.jdbc.Driver";
    private static final String url = "jdbc:mariadb://localhost:3306/milestone6";
    private static final String username = "root";
    private static final String password = "root";

    public DBConnector () {
        myConnection = null;
        try {
            Class.forName(driver);
            myConnection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected!");
        } catch (ClassNotFoundException e) {
            System.out.println("NOT CONECTED");
        } catch (SQLException e) {
            System.out.println("NOT CONECTED 2");
        }
    }

    public void closeCon () {
        if(myConnection != null){
            try {
                myConnection.close();
                System.out.println("Disconnected!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Photographer> getPhotographers() {
        List<Photographer> myPhotographerList = new ArrayList<Photographer>();
        Statement myStatement = null;
        ResultSet myResultset = null;

        try {
            myStatement = myConnection.createStatement();

            if(myStatement.execute("SELECT * FROM Photographers;")) {
                myResultset = myStatement.getResultSet();

                while (myResultset.next()) {
                    int photographerId = myResultset.getInt("PhotographerId");
                    String name = myResultset.getString("Name");
                    Boolean awarded = myResultset.getBoolean("Awared");
                    Photographer myPhotographer = new Photographer(photographerId, name, awarded);
                    myPhotographerList.add(myPhotographer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return myPhotographerList;
    }


    public List<Picture> getPictures(int photographerIndex, Date datePicker) {
        List<Picture> myPictureList = new ArrayList<Picture>();
        PreparedStatement myStatement = null;
        ResultSet myResultset = null;

        try {
            if(datePicker != null) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String datePickerString = df.format(datePicker);
                System.out.println(datePickerString);
                myStatement = myConnection.prepareStatement("SELECT * FROM Pictures WHERE PhotographerId = ? AND Date > ?");
                myStatement.setInt(1, this.getPhotographers().get(photographerIndex).getPhotographerId());
                myStatement.setString(2, datePickerString);
            } else {
                myStatement = myConnection.prepareStatement("SELECT * FROM Pictures WHERE PhotographerId = ?;");
                myStatement.setInt(1, this.getPhotographers().get(photographerIndex).getPhotographerId());
            }
            myResultset = myStatement.executeQuery();

            while(myResultset.next()){
                int pictureId = myResultset.getInt("PictureId");
                String title = myResultset.getString("Title");
                Date date = myResultset.getDate("Date");
                String file = myResultset.getString("File");
                int visits = myResultset.getInt("Visits");
                int photographerId = myResultset.getInt("PhotographerId");

                myPictureList.add(new Picture(pictureId, title, date, file, visits, this.getPhotographers().get(photographerIndex)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return myPictureList;
    }
}
