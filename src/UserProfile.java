import java.util.ArrayList;
import java.util.List;

class UserProfile {
    String userId;
    String name;
    String email;
    List<FavoritePizza> favorites;
    List<Order> orderHistory;
    public int loyaltyPoints;

    public UserProfile(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.favorites = new ArrayList<>();
        this.orderHistory = new ArrayList<>();
        this.loyaltyPoints = 0;
    }
    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
    }

}