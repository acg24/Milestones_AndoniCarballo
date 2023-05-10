package Milestone6;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class dbconnector {
    private static final String URL = "jdbc:mariadb://localhost:3306/milestone6";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public dbconnector(){
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected!");
        } catch (SQLException e) {
            System.out.println("NOT CONECTED");
        }
    }


    public static List<Photographer> getPhotographers() throws SQLException {
        List<Photographer> photographers = new ArrayList<>();
        String query = "SELECT * FROM Photographers";
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("PhotographerId");
                String name = resultSet.getString("Name");
                int awardedInt = resultSet.getInt("Awared");
                boolean awarded = (awardedInt != 0);
                Photographer photographer = new Photographer(id, name, awarded);
                photographers.add(photographer);
            }
        }
        return photographers;
    }

    public static Photographer getPhotographerById(int id) {
        String query = "SELECT * FROM Photographers WHERE PhotographerId = ?";

        try (Connection connection = conn;
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("Name");
                Boolean awared = resultSet.getBoolean("Awarded");
                return new Photographer(id, name, awared);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void incrementVisits(Picture picture) throws SQLException {
        String query = "UPDATE Pictures SET Visits = Visits + 1 WHERE PictureId = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, picture.getId());
            preparedStatement.executeUpdate();
        }
    }

    public static List<Picture> getPictures(int photographerIndex, Date datePicker) {
        List<Picture> myPictureList = new ArrayList<Picture>();
        PreparedStatement myStatement = null;
        ResultSet myResultset = null;

        try {
            if(datePicker != null) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String datePickerString = df.format(datePicker);
                System.out.println(datePickerString);
                myStatement = conn.prepareStatement("SELECT * FROM Pictures WHERE PhotographerId = ? AND date > ?");
                myStatement.setInt(1, dbconnector.getPhotographers().get(photographerIndex).getId());
                myStatement.setString(2, datePickerString);
            } else {
                myStatement = conn.prepareStatement("SELECT * FROM Pictures WHERE PhotographerId = ?;");
                myStatement.setInt(1, dbconnector.getPhotographers().get(photographerIndex).getId());
            }
            myResultset = myStatement.executeQuery();

            while(myResultset.next()){
                int pictureId = myResultset.getInt("PictureId");
                String title = myResultset.getString("Title");
                Date date = myResultset.getDate("Date");
                String file = myResultset.getString("File");
                int visits = myResultset.getInt("Visits");
                int photographerId = myResultset.getInt("PhotographerId");

                myPictureList.add(new Picture(pictureId, title, date, file, visits, dbconnector.getPhotographers().get(photographerIndex).getId()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return myPictureList;
    }
}

