package store;

public class Fruit {
    private String name;
    private String imgSrc;
    private double price;
    private String color;
    private String seller;
    private String qtytype;
    private float qty;
    public String getName() {
        return name;
    }
    public String getSeller() {
        return seller;
    }
    public void setSeller(String seller) {
        this.seller = seller;
    }
    public String getqtytype() {
        return qtytype;
    }
    public void setqtytyp(String qutytyp) {
        this.qtytype = qutytyp;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public float getqty() {
        return qty;
    }

    public void setqty(float quantity) {
        this.qty = quantity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
