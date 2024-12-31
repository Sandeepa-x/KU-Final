import java.util.List;

class PizzaBuilder {
    private String crust;
    private String sauce;
    private List<String> toppings;
    private String cheese;
    private String name;

    public PizzaBuilder withCrust(String crust) {
        this.crust = crust;
        return this;
    }

    public PizzaBuilder withSauce(String sauce) {
        this.sauce = sauce;
        return this;
    }

    public PizzaBuilder withToppings(List<String> toppings) {
        this.toppings = toppings;
        return this;
    }

    public PizzaBuilder withCheese(String cheese) {
        this.cheese = cheese;
        return this;
    }

    public Pizza build() {
        return new Pizza(crust, sauce, toppings, cheese, name);
    }
}