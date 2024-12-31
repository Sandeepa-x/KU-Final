class OrderTracker {
    public void notify(Order order) {
        System.out.println("Order status: " + order.pizzas.size() + " pizzas, status: " + order.calculateTotal());
    }
}