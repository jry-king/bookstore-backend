package orm;

public class Cart {
    private Long id;

    private String book;
    private double price;
    private int number;
    private int user;

    public Cart(){}

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

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

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}
