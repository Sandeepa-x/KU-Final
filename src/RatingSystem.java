class RatingSystem {
    public double calculateRating(Order order) {
        // Calculate average rating based on the feedback
        if (order.feedbackList.isEmpty()) {
            return 0.0;  // No feedback, return 0.0
        }
        double totalRating = 0.0;
        for (Feedback feedback : order.feedbackList) {
            totalRating += feedback.rating;
        }
        return totalRating / order.feedbackList.size();  // Average rating
    }
}