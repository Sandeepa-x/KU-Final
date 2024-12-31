import java.util.List;

class Pizza {
    String crust;
    String sauce;
    List<String> toppings;
    String cheese;
    String name;
    Double price;

    public Pizza(String crust, String sauce, List<String> toppings, String cheese, String name ) {
        this.crust = crust;
        this.sauce = sauce;
        this.toppings = toppings;
        this.cheese = cheese;
        this.name = name;
        this.price = 1400.00;
    }

    @Override
    public String toString() {
        return name + " Pizza with " + crust + " crust, " + sauce + " sauce, " + cheese + " cheese, and toppings: " + toppings;
    }
}