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
    private Connection conn;
    private static final String driver = "org.mariadb.jdbc.Driver";
    private static final String url = "jdbc:mariadb://localhost:3306/milestone6";
    private static final String username = "root";
    private static final String password = "root";

    public DBConnector () {
        conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected!");
        } catch (ClassNotFoundException e) {
            System.out.println("NOT CONECTED");
        } catch (SQLException e) {
            System.out.println("NOT CONECTED 2");
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

    public List<Photographer> getPhotographers() {
        List<Photographer> PhotographersList = new ArrayList<Photographer>();
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
                    PhotographersList.add(new Photographer(photographerId, name, awarded));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return PhotographersList;
    }


    public List<Picture> getPictures(int photographerIndex, Date datePicker) {
        List<Picture> PicturesList = new ArrayList<Picture>();
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

                PicturesList.add(new Picture(pictureId, title, date, file, visits, this.getPhotographers().get(photographerIndex)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return PicturesList;
    }
    public Map<Integer, Integer> visitsMap() {
        Map<Integer, Integer> myVisitsMap = new HashMap<Integer, Integer>();
        Statement stmnt = null;
        ResultSet rs = null;

        try {
            stmnt = conn.createStatement();

            if(stmnt.execute("SELECT * FROM pictures;")){
                rs = stmnt.getResultSet();

                while(rs.next()) {
                    int pictureId = rs.getInt("pictureId");
                    String title = rs.getString("title");
                    Date date = rs.getDate("date");
                    String file = rs.getString("file");
                    int visits = rs.getInt("visits");
                    int photographerId = rs.getInt("photographerId");

                    if(myVisitsMap.containsKey(photographerId)) {
                        visits += myVisitsMap.get(photographerId);
                    }
                    System.out.println(photographerId + ": " + visits);
                    myVisitsMap.put(photographerId, visits);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return myVisitsMap;
    }
    public void award(int minVisits) {
        Map<Integer, Integer> VisitsMap = visitsMap();
        Iterator<Integer> it = VisitsMap.keySet().iterator();
        while(it.hasNext()){
            int photographerId = it.next();
            int visits = VisitsMap.get(photographerId);
            if(visits >= minVisits) {
                try {
                    PreparedStatement stmnt = conn.prepareStatement("SELECT * FROM photographers WHERE photographerId = ?;");
                    stmnt.setInt(1, photographerId);
                    ResultSet rs = stmnt.executeQuery();
                    rs.next();
                    int awards = rs.getInt("awarded");

                    stmnt = conn.prepareStatement("UPDATE photographers SET awarded = ? WHERE photographerId = ?");
                    stmnt.setInt(1, (awards+1));
                    stmnt.setInt(2,photographerId);
                    stmnt.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void remove() {
        List<Picture> myAllPictureList = new ArrayList<Picture>();
        Statement myStatement = null;
        ResultSet myResultset = null;

        try {
            myStatement = conn.createStatement();
            if(myStatement.execute("SELECT * FROM pictures;")){
                myResultset = myStatement.getResultSet();

                while(myResultset.next()){
                    int pictureId = myResultset.getInt("pictureId");
                    String title = myResultset.getString("title");
                    Date date = myResultset.getDate("date");
                    String file = myResultset.getString("file");
                    int visits = myResultset.getInt("visits");
                    int photographerId = myResultset.getInt("photographerId");

                    System.out.println(pictureId + ": " + visits);

                    if(visits == 0) {
                        int selected_option= JOptionPane.showConfirmDialog(null,"Delete " + file + "?","Confirm delete",JOptionPane.YES_NO_OPTION);
                        if(selected_option == 0) {

                            Map<Integer, Integer> myVisitsMap = visitsMap();
                            Iterator<Integer> it = myVisitsMap.keySet().iterator();
                            while(it.hasNext()){
                                int id = it.next();
                                int vists = myVisitsMap.get(id);
                                if(vists == 0) {
                                    int selected_option2 = JOptionPane.showConfirmDialog(null,"Delete photographer?","Confirm delete",JOptionPane.YES_NO_OPTION);
                                    if(selected_option2 == 0) {
                                        PreparedStatement myPreparedStatement2 = conn.prepareStatement("DELETE FROM photographers WHERE photographerId = ?");
                                        myPreparedStatement2.setInt(1, photographerId);
                                        myPreparedStatement2.executeUpdate();
                                        FileWriter foS = new FileWriter("secondoption.txt");
                                        foS.write("otra vez!");
                                        foS.close();
                                    }
                                }
                            }
                            PreparedStatement myPreparedStatement = conn.prepareStatement("DELETE FROM pictures WHERE pictureId = ?");
                            myPreparedStatement.setInt(1, pictureId);
                            myPreparedStatement.executeUpdate();
                            System.out.println("firstoption");
                        }
                    }
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
