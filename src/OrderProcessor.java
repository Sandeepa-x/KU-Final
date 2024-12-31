class OrderProcessor {
    public void process(Order order) {
        System.out.println("Processing order with " + order.pizzas.size() + " pizzas.");
        System.out.println("Total amount: LKR " + order.calculateTotal());
    }

    public String generateInvoice(Order order) {
        return "Invoice generated for Order. Total: LKR " + order.calculateTotal();
    }
}
