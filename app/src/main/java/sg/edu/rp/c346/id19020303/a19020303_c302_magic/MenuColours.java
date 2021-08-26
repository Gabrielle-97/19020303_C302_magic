package sg.edu.rp.c346.id19020303.a19020303_c302_magic;

public class MenuColours {

    private String cardId;
    private String colourId;
    private String cardName;
    private String typeId;
    private int quantity;
    private double unitPrice;

    public MenuColours(String cardId, String colourId, String cardName, String typeId, int quantity, double unitPrice) {
        this.cardId = cardId;
        this.colourId = colourId;
        this.cardName = cardName;
        this.typeId = typeId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getColourId() {
        return colourId;
    }

    public void setColourId(String colourId) {
        this.colourId = colourId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return cardName + ' '+ ' '+ ' ' + unitPrice;
    }
}
