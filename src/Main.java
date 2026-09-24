import food.*;
import decorator.*;
import payment.*;
import order.Order;
import java.util.Scanner;
import java.io.PrintStream;

public class Main {
    public static void main(String[] args) throws Exception {
        PrintStream out = new PrintStream(System.out, true, "UTF-8");
        System.setOut(out);
        Scanner sc = new Scanner(System.in, "UTF-8");
        Order customer = new Order();

        out.println("=================================================");
        out.println("         FOOD DELIVERY ORDER SYSTEM");
        out.println("=================================================");
        out.print("Customer Name: ");    customer.customerName    = sc.nextLine();
        out.print("Contact Number: ");   customer.contactNumber   = sc.nextLine();
        out.print("Delivery Address: "); customer.deliveryAddress = sc.nextLine();

        out.println("\n=================================================");
        out.println("                  FOOD MENU");
        out.println("=================================================");
        out.println("[1] Burger       \u20b1150");
        out.println("[2] Pizza        \u20b1250");
        out.println("[3] Chicken Meal \u20b1180");
        out.print("Select Food: ");
        int foodChoice = Integer.parseInt(sc.nextLine());

        FoodOrder order;
        switch (foodChoice) {
            case 2:  order = new Pizza();       break;
            case 3:  order = new ChickenMeal(); break;
            default: order = new Burger();
        }

        double basePrice = order.getCost();
        String baseName  = order.getDescription();

        double cheeseAmt = 0, sauceAmt = 0, drinkAmt = 0, priorityAmt = 0;
        boolean hasCheese = false, hasSauce = false, hasDrink = false, hasPriority = false;

        out.println("\n=================================================");
        out.println("               ADD-ON SERVICES");
        out.println("=================================================");
        out.println("[1] Extra Cheese      \u20b130");
        out.println("[2] Extra Sauce       \u20b115");
        out.println("[3] Large Drink       \u20b140");
        out.println("[4] Priority Delivery \u20b150");
        out.println("[5] No More Add-ons");

        while (true) {
            out.print("Select: ");
            int addon = Integer.parseInt(sc.nextLine());
            if (addon == 5) break;
            switch (addon) {
                case 1: order = new ExtraCheese(order);  hasCheese  = true; cheeseAmt   = 30; break;
                case 2: order = new ExtraSauce(order);   hasSauce   = true; sauceAmt    = 15; break;
                case 3: order = new LargeDrink(order);   hasDrink   = true; drinkAmt    = 40; break;
                case 4:
                    if (order.getCost() < 151) {
                        out.println("Priority Delivery is only available for orders of \u20b1200 or more.");
                    } else {
                        order = new PriorityDelivery(order);
                        hasPriority = true; priorityAmt = 50;
                    }
                    break;
                default: out.println("Invalid option.");
            }
        }

        double total = order.getCost();

        if (total < 100) {
            out.println("Order cannot be processed. Minimum order amount is \u20b1100.00.");
            return;
        }

        out.println("\n=================================================");
        out.println("               ORDER SUMMARY");
        out.println("=================================================");
        out.println("Customer: " + customer.customerName);
        out.println("Contact:  " + customer.contactNumber);
        out.println("Address:  " + customer.deliveryAddress);
        out.println("\nOrder:");
        out.println(baseName);
        if (hasCheese)   out.println("+ Extra Cheese");
        if (hasSauce)    out.println("+ Extra Sauce");
        if (hasDrink)    out.println("+ Large Drink");
        if (hasPriority) out.println("+ Priority Delivery");
        out.printf("%nSubtotal: \u20b1%.2f%n", basePrice);
        if (hasCheese)   out.printf("Extra Cheese: \u20b1%.2f%n",      cheeseAmt);
        if (hasSauce)    out.printf("Extra Sauce: \u20b1%.2f%n",       sauceAmt);
        if (hasDrink)    out.printf("Large Drink: \u20b1%.2f%n",       drinkAmt);
        if (hasPriority) out.printf("Priority Delivery: \u20b1%.2f%n", priorityAmt);
        out.printf("TOTAL: \u20b1%.2f%n", total);

        out.println("\n=================================================");
        out.println("               PAYMENT METHOD");
        out.println("=================================================");
        out.println("[1] PayPal");
        out.println("[2] GCash");
        out.println("[3] Maya");
        out.print("Select Payment: ");
        int payChoice = Integer.parseInt(sc.nextLine());

        PaymentProcessor processor;
        switch (payChoice) {
            case 1:  processor = new PayPalAdapter(new PayPal()); break;
            case 3:  processor = new MayaAdapter(new Maya());     break;
            default: processor = new GCashAdapter(new GCash());
        }

        out.println("\n=================================================");
        out.println("              ADAPTER PATTERN");
        out.println("=================================================");
        processor.pay(total);

        out.println("\n=================================================");
        out.println("          THANK YOU FOR YOUR ORDER!");
        out.println("=================================================");
        sc.close();
    }
}
