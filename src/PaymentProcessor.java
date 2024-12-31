import java.util.Scanner;

class PaymentProcessor {

    public void processPayment(Order order, String paymentMethod, Scanner scanner) {
        double totalAmount = order.calculateTotal();

        if (paymentMethod.equalsIgnoreCase("card")) {
            System.out.print("Enter card details (dummy): ");
            String cardDetails = scanner.nextLine();
            if (validateCard(cardDetails)) {
                System.out.println("Payment processed using card. Total: LKR " + totalAmount);
            } else {
                System.out.println("Invalid card details. Payment failed.");
            }
        } else if (paymentMethod.equalsIgnoreCase("wallet")) {
            System.out.print("Enter wallet details (dummy): ");
            String walletDetails = scanner.nextLine();
            if (processWallet(walletDetails)) {
                System.out.println("Payment processed using wallet. Total: LKR " + totalAmount);
            } else {
                System.out.println("Invalid wallet details. Payment failed.");
            }
        } else {
            System.out.println("Invalid payment method. Please choose either 'card' or 'wallet'.");
        }
    }

    public boolean validateCard(String cardDetails) {
        // Dummy validation logic for the card
        return cardDetails != null && !cardDetails.isEmpty();
    }

    public boolean processWallet(String walletDetails) {
        // Dummy wallet processing logic
        return walletDetails != null && !walletDetails.isEmpty();
    }
}