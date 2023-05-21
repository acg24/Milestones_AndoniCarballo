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
    private static final String userName = "root";
    private static final String password = "root";

    public DBConnector () {
        conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);
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
                    int PhotographerId = rs.getInt("PhotographerId");
                    String Name = rs.getString("Name");
                    Boolean awarded = rs.getBoolean("Awared");
                    PhotographersList.add(new Photographer(PhotographerId, Name, awarded));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return PhotographersList;
    }


    public List<Picture> getPictures(int photographerIndex, Date DatePicker) {
        List<Picture> PicturesList = new ArrayList<Picture>();
        PreparedStatement stmnt = null;
        ResultSet rs = null;

        try {
            if(DatePicker != null) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String DatePickerString = df.format(DatePicker);
                System.out.println(DatePickerString);
                stmnt = conn.prepareStatement("SELECT * FROM Pictures WHERE PhotographerId = ? AND Date > ?");
                stmnt.setInt(1, this.getPhotographers().get(photographerIndex).getPhotographerId());
                stmnt.setString(2, DatePickerString);
            } else {
                stmnt = conn.prepareStatement("SELECT * FROM Pictures WHERE PhotographerId = ?;");
                stmnt.setInt(1, this.getPhotographers().get(photographerIndex).getPhotographerId());
            }
            rs = stmnt.executeQuery();

            while(rs.next()){
                int PictureId = rs.getInt("PictureId");
                String Title = rs.getString("Title");
                Date Date = rs.getDate("Date");
                String File = rs.getString("File");
                int Visits = rs.getInt("Visits");
                int PhotographerId = rs.getInt("PhotographerId");

                PicturesList.add(new Picture(PictureId, Title, Date, File, Visits, this.getPhotographers().get(photographerIndex)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return PicturesList;
    }
    public Map<Integer, Integer> VisitsMap() {
        Map<Integer, Integer> myVisitsMap = new HashMap<Integer, Integer>();
        Statement stmnt = null;
        ResultSet rs = null;

        try {
            stmnt = conn.createStatement();

            if(stmnt.execute("SELECT * FROM Pictures;")){
                rs = stmnt.getResultSet();

                while(rs.next()) {
                    int PictureId = rs.getInt("PictureId");
                    String Title = rs.getString("Title");
                    Date Date = rs.getDate("Date");
                    String File = rs.getString("File");
                    int Visits = rs.getInt("Visits");
                    int PhotographerId = rs.getInt("PhotographerId");

                    if(myVisitsMap.containsKey(PhotographerId)) {
                        Visits += myVisitsMap.get(PhotographerId);
                    }
                    System.out.println(PhotographerId + ": " + Visits);
                    myVisitsMap.put(PhotographerId, Visits);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return myVisitsMap;
    }
    public void award(int minVisits) {
        Map<Integer, Integer> VisitsMap = VisitsMap();
        Iterator<Integer> it = VisitsMap.keySet().iterator();
        while(it.hasNext()){
            int PhotographerId = it.next();
            int Visits = VisitsMap.get(PhotographerId);
            if(Visits >= minVisits) {
                try {
                    PreparedStatement stmnt = conn.prepareStatement("SELECT * FROM Photographers WHERE PhotographerId = ?;");
                    stmnt.setInt(1, PhotographerId);
                    ResultSet rs = stmnt.executeQuery();
                    rs.next();
                    int awards = rs.getInt("awarded");

                    stmnt = conn.prepareStatement("UPDATE Photographers SET awarded = ? WHERE PhotographerId = ?");
                    stmnt.setInt(1, (awards+1));
                    stmnt.setInt(2,PhotographerId);
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
            if(myStatement.execute("SELECT * FROM Pictures;")){
                myResultset = myStatement.getResultSet();

                while(myResultset.next()){
                    int PictureId = myResultset.getInt("PictureId");
                    String Title = myResultset.getString("Title");
                    Date Date = myResultset.getDate("Date");
                    String File = myResultset.getString("File");
                    int Visits = myResultset.getInt("Visits");
                    int PhotographerId = myResultset.getInt("PhotographerId");

                    System.out.println(PictureId + ": " + Visits);

                    if(Visits == 0) {
                        int selected_option= JOptionPane.showConfirmDialog(null,"Delete " + File + "?","Confirm delete",JOptionPane.YES_NO_OPTION);
                        if(selected_option == 0) {

                            Map<Integer, Integer> myVisitsMap = VisitsMap();
                            Iterator<Integer> it = myVisitsMap.keySet().iterator();
                            while(it.hasNext()){
                                int id = it.next();
                                int vists = myVisitsMap.get(id);
                                if(vists == 0) {
                                    int selected_option2 = JOptionPane.showConfirmDialog(null,"Delete photographer?","Confirm delete",JOptionPane.YES_NO_OPTION);
                                    if(selected_option2 == 0) {
                                        PreparedStatement myPreparedStatement2 = conn.prepareStatement("DELETE FROM Photographers WHERE PhotographerId = ?");
                                        myPreparedStatement2.setInt(1, PhotographerId);
                                        myPreparedStatement2.executeUpdate();
                                        FileWriter foS = new FileWriter("secondoption.txt");
                                        foS.write("otra vez!");
                                        foS.close();
                                    }
                                }
                            }
                            PreparedStatement myPreparedStatement = conn.prepareStatement("DELETE FROM Pictures WHERE PictureId = ?");
                            myPreparedStatement.setInt(1, PictureId);
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
