class FavoritePizza {
    String customName;
    Pizza pizza;
    String userId;

    public FavoritePizza(String customName, Pizza pizza, String userId) {
        this.customName = customName;
        this.pizza = pizza;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "FavoritePizza{" +
                "customName='" + customName + '\'' +
                ", pizza=" + pizza +
                ", userId='" + userId + '\'' +
                '}';
    }
}
