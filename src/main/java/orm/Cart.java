package orm;

public class Cart {
    private Long userid;

    private String book;
    private double price;
    private int number;

    public Cart(){}

    public Long getUserId() { return userid; }

    public void setUserId(Long userid) {
        this.userid = userid;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
