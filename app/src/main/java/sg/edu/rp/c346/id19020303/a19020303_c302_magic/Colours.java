package sg.edu.rp.c346.id19020303.a19020303_c302_magic;

public class Colours {

    private String coloursID;
    private String color;

    public Colours(String coloursID, String color) {
        this.coloursID = coloursID;
        this.color = color;
    }

    public String getColoursID() {
        return coloursID;
    }

    public void setColoursID(String coloursID) {
        this.coloursID = coloursID;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color;
    }
}
