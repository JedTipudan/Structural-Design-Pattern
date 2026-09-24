package payment;

import java.io.PrintStream;

public class MayaAdapter implements PaymentProcessor {
    private Maya maya;
    public MayaAdapter(Maya maya) { this.maya = maya; }

    public void pay(double amount) {
        try { System.setOut(new PrintStream(System.out, true, "UTF-8")); } catch (Exception e) {}
        if (amount <= 0) { System.out.println("Invalid payment amount."); return; }
        System.out.println("Payment Method: Maya");
        System.out.printf("Amount: \u20b1%.2f%n", amount);
        maya.transferFunds(amount);
        System.out.println("Payment Successful!");
    }
}
