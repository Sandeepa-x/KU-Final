class LoyaltyProgram {
    public int earnPoints(Order order) {
        int points = (int) order.calculateTotal() / 10;
        System.out.println("Earning " + points + " loyalty points.");
        return points;
    }

    public int redeemPoints(String userId) {
        // Dummy point redemption logic
        return 10;
    }
}