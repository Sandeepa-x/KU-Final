import java.util.*;

public class PizzaOrderingSystem {
    PizzaMenu pizzaMenu;
    Map<String, UserProfile> userProfiles;
    OrderProcessor orderProcessor;
    PaymentProcessor paymentProcessor;
    LoyaltyProgram loyaltyProgram;
    DiscountCalculator discountCalculator;
    SeasonalPromotion seasonalPromotion;

    public PizzaOrderingSystem() {
        pizzaMenu = new PizzaMenu();
        userProfiles = new HashMap<>();
        orderProcessor = new OrderProcessor();
        paymentProcessor = new PaymentProcessor();
        loyaltyProgram = new LoyaltyProgram();
        discountCalculator = new DiscountCalculator();
        seasonalPromotion = new SeasonalPromotion();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Pizza Ordering System ---");
            System.out.println("1. Register User");
            System.out.println("2. Build a Pizza");
            System.out.println("3. Place an Order");
            System.out.println("4. Add Favorite Pizza");
            System.out.println("5. Reorder Favorite Pizza");
            System.out.println("6. View All Users");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    buildPizza(scanner);
                    break;
                case 3:
                    placeOrder(scanner);
                    break;
                case 4:
                    addFavoritePizza(scanner);
                    break;
                case 5:
                    reorderFavoritePizza(scanner);
                    break;
                case 6:
                    viewAllUsers();
                    break;
                case 7:
                    System.out.println("Thank you for using the Pizza Ordering System!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    private void registerUser(Scanner scanner) {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        userProfiles.put(userId, new UserProfile(userId, name, email));
        System.out.println("User registered successfully!");
    }

    private void buildPizza(Scanner scanner) {
        System.out.println("Available Crusts: ");
        for (int i = 0; i < pizzaMenu.crusts.size(); i++) {
            System.out.println((i + 1) + ". " + pizzaMenu.crusts.get(i));
        }
        System.out.print("Choose Crust: ");
        int crustChoice = scanner.nextInt() - 1;
        scanner.nextLine();
        String crust = pizzaMenu.crusts.get(crustChoice);

        System.out.println("Available Sauces: ");
        for (int i = 0; i < pizzaMenu.sauces.size(); i++) {
            System.out.println((i + 1) + ". " + pizzaMenu.sauces.get(i));
        }
        System.out.print("Choose Sauce: ");
        int sauceChoice = scanner.nextInt() - 1;
        scanner.nextLine();
        String sauce = pizzaMenu.sauces.get(sauceChoice);

        System.out.println("Available Toppings: ");
        for (int i = 0; i < pizzaMenu.toppings.size(); i++) {
            System.out.println((i + 1) + ". " + pizzaMenu.toppings.get(i));
        }
        System.out.print("Choose Toppings (comma-separated): ");
        String[] toppingArray = scanner.nextLine().split(",");
        List<String> toppingList = new ArrayList<>();
        for (String topping : toppingArray) {
            toppingList.add(pizzaMenu.toppings.get(Integer.parseInt(topping.trim()) - 1));
        }

        Pizza pizza = new PizzaBuilder()
                .withCrust(crust)
                .withSauce(sauce)
                .withToppings(toppingList)
                .withCheese("Mozzarella")
                .build();

        seasonalPromotion.apply(pizza);
        System.out.println("Pizza built successfully: " + pizza);

        // Ask the user if they want to add the pizza to their favorites
        System.out.print("Do you want to add this pizza to your favorites? (yes/no): ");
        String addToFavorites = scanner.nextLine();

        if (addToFavorites.equalsIgnoreCase("yes")) {
            System.out.print("Enter User ID to add this pizza to favorites: ");
            String userId = scanner.nextLine();

            if (!userProfiles.containsKey(userId)) {
                System.out.println("User not found. Please register first.");
            } else {
                UserProfile userProfile = userProfiles.get(userId);
                String customName = "";
                userProfile.favorites.add(new FavoritePizza(customName,pizza, userId));
                System.out.println("Pizza added to favorites: " + pizza);
            }
        }
    }


    private void placeOrder(Scanner scanner) {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        if (!userProfiles.containsKey(userId)) {
            System.out.println("User not found. Please register first.");
            return;
        }

        System.out.println("\nAvailable Pizzas: ");
        System.out.println("1. Thin Crust with Tomato Sauce, Mozzarella, Pepperoni");
        System.out.println("2. Thick Crust with White Sauce, Mozzarella, Mushrooms");
        System.out.print("Choose a pizza (1 or 2): ");
        int pizzaChoice = scanner.nextInt();
        scanner.nextLine();

        Pizza pizza = null;
        if (pizzaChoice == 1) {
            pizza = new Pizza("Thin Crust", "Tomato Sauce", Arrays.asList("Pepperoni"), "Mozzarella", "Pepperoni Pizza");
        } else if (pizzaChoice == 2) {
            pizza = new Pizza("Thick Crust", "White Sauce", Arrays.asList("Mushrooms"), "Mozzarella", "Mushroom Pizza");
        } else {
            System.out.println("Invalid choice, returning to the menu.");
            return;
        }

        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Choose Delivery Type:");
        System.out.println("1. Pickup");
        System.out.println("2. Delivery");
        int deliveryChoice = scanner.nextInt();
        scanner.nextLine();

        String deliveryType = (deliveryChoice == 1) ? "Pickup" : "Delivery";

        double deliveryFee = 0.0;
        if ("Delivery".equals(deliveryType)) {
            deliveryFee = 500.00; // delivery fee
            System.out.println("Delivery fee: LKR" + deliveryFee);
        }

        System.out.print("Enter Delivery Address: ");
        String deliveryAddress = scanner.nextLine();
        System.out.println("Delivery address: " + deliveryAddress);

        String orderId = "ORDER-" + System.currentTimeMillis();

        Order order = new Order(orderId, Arrays.asList(pizza), quantity, deliveryType);

        userProfiles.get(userId).orderHistory.add(order);

        orderProcessor.process(order);

        System.out.println("Choose Payment Option:");
        System.out.println("1. Card");
        System.out.println("2. Wallet");
        int paymentChoice = scanner.nextInt();
        scanner.nextLine();

        String paymentMethod = (paymentChoice == 1) ? "card" : "wallet";

        paymentProcessor.processPayment(order, paymentMethod, scanner);

        System.out.println("\nProcessing your order. Please wait...");

        try {
            System.out.println("Step 1: Preparing your pizza...");
            Thread.sleep(4000); // Wait for 4 seconds

            System.out.println("Step 2: Quality checking your order...");
            Thread.sleep(4000); // Wait for 4 seconds

            System.out.println("Step 3: Packing your order...");
            Thread.sleep(4000); // Wait for 4 seconds

            System.out.println("Your order is now ready!");
        } catch (InterruptedException e) {
            System.out.println("Order processing was interrupted. Please try again.");
        }

        // Loyalty points
        int pointsEarned = loyaltyProgram.earnPoints(order);
        System.out.println("Loyalty points earned: " + pointsEarned);

        UserProfile user = userProfiles.get(userId);
        user.loyaltyPoints += pointsEarned;

        // Discount calculation
        double discount = discountCalculator.calculateDiscount(order);
        System.out.println("Discount applied: LKR " + discount);

        // Calculate total price after discount
        double totalPrice = (pizza.price * quantity + deliveryFee) - discount; // Apply discount here
        System.out.println("Total Price (after discount and including delivery fee if applicable): LKR " + totalPrice);

        // Generate and display invoice
        System.out.println("Invoice generated for Order. Total: LKR " + totalPrice); // Display total price in the invoice

        // Feedback section
        System.out.println("\nWould you like to leave feedback for this order?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        int feedbackChoice = scanner.nextInt();
        scanner.nextLine();

        if (feedbackChoice == 1) {
            int rating = 0;
            String comments = "";

            try {
                System.out.print("Enter Rating (1 to 5): ");
                rating = scanner.nextInt();
                scanner.nextLine();

                if (rating < 1 || rating > 5) {
                    System.out.println("Invalid rating. Rating must be between 1 and 5.");
                    return;
                }

                System.out.print("Enter Comments: ");
                comments = scanner.nextLine();

                Feedback feedback = new Feedback(rating, comments);
                order.addFeedback(feedback);

                double averageRating = order.calculateAverageRating();
                System.out.println("Average Rating for this Order: " + averageRating);

            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid rating and comments.");
                scanner.nextLine();
            }

        } else {
            System.out.println("No feedback provided.");
        }
    }


    private void addFavoritePizza(Scanner scanner) {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        if (!userProfiles.containsKey(userId)) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("Do you want to build a new pizza or choose an existing pizza?");
        System.out.println("1. Build a New Pizza");
        System.out.println("2. Choose an Existing Pizza");
        int pizzaChoice = scanner.nextInt();
        scanner.nextLine();

        Pizza pizza = null;

        if (pizzaChoice == 1) {
            // Build a new pizza
            System.out.println("Available Crusts: ");
            for (int i = 0; i < pizzaMenu.crusts.size(); i++) {
                System.out.println((i + 1) + ". " + pizzaMenu.crusts.get(i));
            }
            System.out.print("Choose Crust: ");
            int crustChoice = scanner.nextInt() - 1;
            scanner.nextLine();
            String crust = pizzaMenu.crusts.get(crustChoice);

            System.out.println("Available Sauces: ");
            for (int i = 0; i < pizzaMenu.sauces.size(); i++) {
                System.out.println((i + 1) + ". " + pizzaMenu.sauces.get(i));
            }
            System.out.print("Choose Sauce: ");
            int sauceChoice = scanner.nextInt() - 1;
            scanner.nextLine();
            String sauce = pizzaMenu.sauces.get(sauceChoice);

            System.out.println("Available Toppings: ");
            for (int i = 0; i < pizzaMenu.toppings.size(); i++) {
                System.out.println((i + 1) + ". " + pizzaMenu.toppings.get(i));
            }
            System.out.print("Choose Toppings (comma-separated): ");
            String[] toppingArray = scanner.nextLine().split(",");
            List<String> toppingList = Arrays.asList(toppingArray);

            pizza = new PizzaBuilder()
                    .withCrust(crust)
                    .withSauce(sauce)
                    .withToppings(toppingList)
                    .withCheese("Mozzarella")
                    .build();
        } else if (pizzaChoice == 2) {
            System.out.println("Available Pizzas: ");
            System.out.println("1. Thin Crust with Tomato Sauce, Mozzarella, Pepperoni");
            System.out.println("2. Thick Crust with White Sauce, Mozzarella, Mushrooms");

            int existingPizzaChoice = scanner.nextInt();
            scanner.nextLine();

            if (existingPizzaChoice == 1) {
                pizza = new Pizza("Thin Crust", "Tomato Sauce", Arrays.asList("Pepperoni"), "Mozzarella", "Pepperoni Pizza");
            } else if (existingPizzaChoice == 2) {
                pizza = new Pizza("Thick Crust", "White Sauce", Arrays.asList("Mushrooms"), "Mozzarella", "Mushroom Pizza");
            } else {
                System.out.println("Invalid choice, returning to the menu.");
                return;
            }
        } else {
            System.out.println("Invalid choice, returning to the menu.");
            return;
        }

        System.out.print("Enter a custom name for your favorite pizza: ");
        String customName = scanner.nextLine();

        // Add the pizza to favorites
        FavoritePizza favoritePizza = new FavoritePizza(customName, pizza, userId);
        userProfiles.get(userId).favorites.add(favoritePizza);

        System.out.println("Pizza added to favorites: " + favoritePizza);
    }



    private void viewAllUsers() {
        if (userProfiles.isEmpty()) {
            System.out.println("No users registered yet.");
        } else {
            System.out.println("\n--- All Registered Users ---");
            for (Map.Entry<String, UserProfile> entry : userProfiles.entrySet()) {
                UserProfile user = entry.getValue();
                System.out.println("User ID: " + user.userId);
                System.out.println("Name: " + user.name);
                System.out.println("Email: " + user.email);

                // Display the loyalty point
                System.out.println("Loyalty Points: " + user.loyaltyPoints);

                System.out.println("Orders: ");
                if (user.orderHistory.isEmpty()) {
                    System.out.println("   No orders placed yet.");
                } else {
                    for (Order order : user.orderHistory) {
                        System.out.println("   Order ID: " + order.orderId);
                        System.out.println("   Delivery Type: " + order.deliveryType);
                        System.out.println("   Total Price: LKR " + order.totalPrice);

                        if (order.feedbackList.isEmpty()) {
                            System.out.println("   No feedback received for this order.");
                        } else {
                            System.out.println("   Feedback:");
                            for (Feedback feedback : order.feedbackList) {
                                System.out.println("   Rating: " + feedback.rating + " Stars");
                                System.out.println("   Comments: " + feedback.comments);
                            }
                        }
                        System.out.println("---");
                    }
                }

                System.out.println("Favorites: ");
                if (user.favorites.isEmpty()) {
                    System.out.println("   No favorite pizzas saved.");
                } else {
                    for (FavoritePizza favorite : user.favorites) {
                        Pizza pizza = favorite.pizza;
                        System.out.println("   Pizza Name: " + pizza.name);
                        System.out.println("      Crust: " + pizza.crust);
                        System.out.println("      Sauce: " + pizza.sauce);
                        System.out.println("      Toppings: " + String.join(", ", pizza.toppings));
                        System.out.println("      Cheese: " + pizza.cheese);
                        System.out.println();
                    }
                }

                System.out.println("---");
            }
        }
    }

    private void reorderFavoritePizza(Scanner scanner) {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        if (!userProfiles.containsKey(userId)) {
            System.out.println("User not found.");
            return;
        }

        UserProfile user = userProfiles.get(userId);

        if (user.favorites.isEmpty()) {
            System.out.println("No favorite pizzas found.");
            return;
        }

        System.out.println("Your favorite pizzas:");
        for (int i = 0; i < user.favorites.size(); i++) {
            // Display the pizza name
            System.out.println((i + 1) + ". " + user.favorites.get(i).pizza.name);
        }

        System.out.print("Enter the number of the pizza you want to reorder: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > user.favorites.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        FavoritePizza favoritePizza = user.favorites.get(choice - 1);
        Pizza pizzaToReorder = favoritePizza.pizza;

        System.out.println("Reordering pizza: " + pizzaToReorder.name);

        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Choose Delivery Type:");
        System.out.println("1. Pickup");
        System.out.println("2. Delivery");
        int deliveryChoice = scanner.nextInt();
        scanner.nextLine();

        String deliveryType = (deliveryChoice == 1) ? "Pickup" : "Delivery";

        double deliveryFee = 0.00;
        if ("Delivery".equals(deliveryType)) {
            deliveryFee = 500.00;
            System.out.println("Delivery fee: LKR" + deliveryFee);
        }

        String deliveryAddress = "";
        if ("Delivery".equals(deliveryType)) {
            System.out.print("Enter Delivery Address: ");
            deliveryAddress = scanner.nextLine();
        }

        String orderId = "ORDER-" + System.currentTimeMillis();

        Order order = new Order(orderId, Arrays.asList(pizzaToReorder), quantity, deliveryType);

        user.orderHistory.add(order);

        // Show order details
        System.out.println("Order placed successfully! Order details:");
        System.out.println("Order ID: " + order.orderId);
        System.out.println("Pizza: " + pizzaToReorder.name);
        System.out.println("Quantity: " + order.quantity);
        System.out.println("Delivery Type: " + order.deliveryType);
        System.out.println("Total Price (including delivery fee if applicable): LKR " + (pizzaToReorder.price * quantity + deliveryFee));

        System.out.println("Choose Payment Option:");
        System.out.println("1. Card");
        System.out.println("2. Wallet");
        int paymentChoice = scanner.nextInt();
        scanner.nextLine();

        String paymentMethod = (paymentChoice == 1) ? "Card" : "Wallet";

        paymentProcessor.processPayment(order, paymentMethod, scanner);

        int pointsEarned = loyaltyProgram.earnPoints(order);
        System.out.println("Loyalty points earned: " + pointsEarned);

        user.loyaltyPoints += pointsEarned;

        double discount = discountCalculator.calculateDiscount(order);
        System.out.println("Discount applied: " + discount);

        double totalPrice = pizzaToReorder.price * quantity + deliveryFee - discount;
        System.out.println("Total Price after discount: LKR" + totalPrice);

    }


    public static void main(String[] args) {
        PizzaOrderingSystem system = new PizzaOrderingSystem();
        system.start();
    }
}
