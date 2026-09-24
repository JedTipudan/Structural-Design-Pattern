package payment;

import java.io.PrintStream;

public class PayPalAdapter implements PaymentProcessor {
    private PayPal paypal;
    public PayPalAdapter(PayPal paypal) { this.paypal = paypal; }

    public void pay(double amount) {
        try { System.setOut(new PrintStream(System.out, true, "UTF-8")); } catch (Exception e) {}
        if (amount <= 0) { System.out.println("Invalid payment amount."); return; }
        System.out.println("Payment Method: PayPal");
        System.out.printf("Amount: \u20b1%.2f%n", amount);
        paypal.makePayment(amount);
        System.out.println("Payment Successful!");
    }
}
