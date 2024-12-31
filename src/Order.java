import java.util.ArrayList;
import java.util.List;

class Order {
    String orderId;
    List<Pizza> pizzas;
    int quantity;
    String deliveryType;
    double totalPrice;
    OrderStatus orderStatus;  // Add OrderStatus field
    List<Feedback> feedbackList;  // List to store feedback

    // Constructor to initialize Order
    public Order(String orderId, List<Pizza> pizzas, int quantity, String deliveryType) {
        this.orderId = orderId;
        this.pizzas = pizzas;
        this.quantity = quantity;
        this.deliveryType = deliveryType;
        this.orderStatus = new OrderStatus();  // Initialize OrderStatus
        this.orderStatus.update("Placed");  // Set initial status
        this.feedbackList = new ArrayList<>();  // Initialize feedback list
    }

    // Method to calculate the total price of the order
    public double calculateTotal() {
        totalPrice = pizzas.size() * 1400.0 * quantity; // Assuming each pizza costs 10
        return totalPrice;
    }

    // Method to add feedback to the order
    public void addFeedback(Feedback feedback) {
        feedbackList.add(feedback);
    }

    // Method to calculate average rating based on feedback
    public double calculateAverageRating() {
        if (feedbackList.isEmpty()) {
            return 0.0;  // No feedback yet
        }
        double totalRating = 0.0;
        for (Feedback feedback : feedbackList) {
            totalRating += feedback.rating;
        }
        return totalRating / feedbackList.size();  // Average rating
    }
}