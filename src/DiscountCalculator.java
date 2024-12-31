class DiscountCalculator {
    public double calculateDiscount(Order order) {
        return order.calculateTotal() * 0.10; // 10% discount
    }
}