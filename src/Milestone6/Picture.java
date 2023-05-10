package Milestone6;

import java.util.Date;

public class Picture {
    private int id;
    private String title;
    private Date date;
    private String file;
    private int visits;
    private int photographerId;

    public Picture(int id, String title, Date date, String file, int visits, int photographerId) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.file = file;
        this.visits = visits;
        this.photographerId = photographerId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public String getFile() {
        return file;
    }

    public int getVisits() {
        return visits;
    }

    public void incrementVisits() {
        visits++;
    }

    public int getPhotographerId() {
        return photographerId;
    }

    public Photographer getPhotographer() {
        return dbconnector.getPhotographerById(1);
    }

    @Override
    public String toString() {
        return title;
    }
}
