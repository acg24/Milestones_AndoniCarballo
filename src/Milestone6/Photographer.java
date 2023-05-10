package Milestone6;

public class Photographer {
    private int id;
    private String name;
    private boolean awarded;

    public Photographer(int id, String name, boolean awarded) {
        this.id = id;
        this.name = name;
        this.awarded = awarded;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isAwarded() {
        return awarded;
    }

    @Override
    public String toString() {
        return name;
    }
}
