# Food Delivery & Payment Integration System
### 2nd Laboratory Activity — Structural Design Patterns

---

## Overview

This project is a console-based Food Ordering and Payment System built in Java. It demonstrates two structural design patterns:

- **Adapter Pattern** — integrates incompatible third-party payment services
- **Decorator Pattern** — dynamically adds optional services to a food order

---

## Project Structure

```
src/
├── payment/
│   ├── PaymentProcessor.java
│   ├── PayPal.java
│   ├── GCash.java
│   ├── Maya.java
│   ├── PayPalAdapter.java
│   ├── GCashAdapter.java
│   └── MayaAdapter.java
├── food/
│   ├── FoodOrder.java
│   ├── Burger.java
│   ├── Pizza.java
│   └── ChickenMeal.java
├── decorator/
│   ├── FoodOrderDecorator.java
│   ├── ExtraCheese.java
│   ├── ExtraSauce.java
│   ├── LargeDrink.java
│   └── PriorityDelivery.java
├── order/
│   └── Order.java
└── Main.java
```

---

## How to Run

**Compile:**
```bash
javac -d out src/payment/*.java src/food/*.java src/decorator/*.java src/order/*.java src/Main.java
```

**Run:**
```bash
java -cp out Main
```

> If the ₱ sign shows as `?`, run `chcp 65001` in your terminal before running the program.

---

## Design Patterns Used

### 1. Adapter Pattern

**Problem:** The system expects all payment providers to have a `pay()` method. But each third-party service has a different method:

| Provider | Their Method     |
|----------|-----------------|
| PayPal   | `makePayment()` |
| GCash    | `sendMoney()`   |
| Maya     | `transferFunds()` |

**Solution:** Create an adapter for each provider that implements `PaymentProcessor` and translates `pay()` into the correct method.

```
PaymentProcessor
├── PayPalAdapter → PayPal.makePayment()
├── GCashAdapter  → GCash.sendMoney()
└── MayaAdapter   → Maya.transferFunds()
```

**Example:**
```java
PaymentProcessor payment = new GCashAdapter(new GCash());
payment.pay(245.00);
// Output:
// Payment Method: GCash
// Amount: ₱245.00
// Payment sent through GCash.
// Payment Successful!
```

---

### 2. Decorator Pattern

**Problem:** Customers can add optional services to their order. Creating a class for every combination (BurgerWithCheese, BurgerWithCheeseAndSauce, etc.) would result in too many classes.

**Solution:** Each add-on is a decorator that wraps the existing order and adds its own cost and description.

```
FoodOrder (interface)
└── FoodOrderDecorator (abstract)
    ├── ExtraCheese    +₱30
    ├── ExtraSauce     +₱15
    ├── LargeDrink     +₱40
    └── PriorityDelivery +₱50
```

**Example:**
```java
FoodOrder order = new Burger();           // ₱150
order = new ExtraCheese(order);           // ₱180
order = new ExtraSauce(order);            // ₱195
order = new PriorityDelivery(order);      // ₱245

System.out.println(order.getDescription());
// Burger + Extra Cheese + Extra Sauce + Priority Delivery
System.out.println(order.getCost());
// 245.0
```

---

## Validation Rules

| Rule | Description | Message |
|------|-------------|---------|
| Rule 1 | Minimum order is ₱100 | `Order cannot be processed. Minimum order amount is ₱100.00.` |
| Rule 2 | Priority Delivery requires subtotal above ₱150 | `Priority Delivery is only available for orders of ₱200 or more.` |
| Rule 3 | Payment amount must be greater than 0 | `Invalid payment amount.` |

---

## Expected Program Flow

```
=================================================
         FOOD DELIVERY ORDER SYSTEM
=================================================
Customer Name: Juan Dela Cruz
Contact Number: 09123456789
Delivery Address: Mati City

=================================================
                  FOOD MENU
=================================================
[1] Burger       ₱150
[2] Pizza        ₱250
[3] Chicken Meal ₱180
Select Food: 1

=================================================
               ADD-ON SERVICES
=================================================
[1] Extra Cheese      ₱30
[2] Extra Sauce       ₱15
[3] Large Drink       ₱40
[4] Priority Delivery ₱50
[5] No More Add-ons
Select: 1
Select: 2
Select: 4
Select: 5

=================================================
               ORDER SUMMARY
=================================================
Customer: Juan Dela Cruz
Contact:  09123456789
Address:  Mati City

Order:
Burger
+ Extra Cheese
+ Extra Sauce
+ Priority Delivery

Subtotal: ₱150.00
Extra Cheese: ₱30.00
Extra Sauce: ₱15.00
Priority Delivery: ₱50.00
TOTAL: ₱245.00

=================================================
               PAYMENT METHOD
=================================================
[1] PayPal
[2] GCash
[3] Maya
Select Payment: 2

=================================================
              ADAPTER PATTERN
=================================================
Payment Method: GCash
Amount: ₱245.00
Payment sent through GCash.
Payment Successful!

=================================================
          THANK YOU FOR YOUR ORDER!
=================================================
```

---

## Guide Questions & Answers

**Q1. What problem does the Adapter Pattern solve in the payment system?**
The system only knows how to call `pay()`, but each payment provider has a different method name. The Adapter Pattern bridges this gap by wrapping each provider in an adapter that translates `pay()` into the correct method call.

**Q2. Why can't PayPal, GCash, and Maya be used directly with PaymentProcessor?**
Because they don't implement the `PaymentProcessor` interface. Each has a different method name, so calling `pay()` on them directly would cause a compile error.

**Q3. What is the responsibility of GCashAdapter?**
It implements `PaymentProcessor`, holds a reference to a `GCash` object, and inside `pay()` it calls `gcash.sendMoney()`. It also validates the amount — if it's 0 or negative, it prints an error.

**Q4. What problem does the Decorator Pattern solve in the food ordering system?**
It avoids a class explosion. Without decorators, you'd need a separate class for every combination of food and add-ons. With 4 add-ons and 3 food items, that's potentially 48 classes. Decorators let you stack add-ons dynamically using just one class per add-on.

**Q5. Why is using multiple decorators better than creating classes like BurgerWithCheeseAndSauce?**
With N add-ons you'd need 2^N subclasses per food item. Decorators are reusable — ExtraCheese works with Burger, Pizza, and ChickenMeal. You write it once and it works everywhere.

**Q6. How can multiple decorators be combined?**
Each decorator wraps the previous one. When `getCost()` is called, each layer adds its price and delegates to the inner object until it reaches the base food item.

**Q7. What is the difference between Adapter and Decorator?**

| Adapter | Decorator |
|---------|-----------|
| Changes the interface | Keeps the same interface |
| Used to fix incompatibility | Used to add new behavior |
| Wraps an incompatible object | Wraps a compatible object |

**Q8. Which pattern changes the interface of an existing class?**
The **Adapter Pattern** — it converts one interface into another that the client expects.

**Q9. Which pattern adds new behavior to an existing object dynamically?**
The **Decorator Pattern** — it wraps an existing object and extends its behavior at runtime.

**Q10. If the company adds another payment provider, what class should be created?**
A new Adapter class (e.g., `EWalletAdapter`) that implements `PaymentProcessor` and delegates to the new provider's method. No existing code needs to change.

**Q11. If the company adds a new add-on like ExtraRice, what class should be created?**
A new Decorator class `ExtraRice` that extends `FoodOrderDecorator`, overrides `getDescription()` and `getCost()`. No existing classes need to change.

**Q12. How do these patterns improve the maintainability of the system?**
Both follow the **Open/Closed Principle** — open for extension, closed for modification. New payment methods or add-ons only require new classes, never changes to existing ones.

---

## Short Explanation of Implementation

**Adapter Pattern:** Three adapter classes (PayPalAdapter, GCashAdapter, MayaAdapter) each implement `PaymentProcessor` and hold a reference to their respective third-party object. When `pay()` is called, each adapter translates it into the provider's specific method. This lets `Main.java` work with any payment provider through one unified interface.

**Decorator Pattern:** `FoodOrderDecorator` is an abstract class that implements `FoodOrder` and holds a reference to another `FoodOrder`. Each concrete decorator (ExtraCheese, ExtraSauce, etc.) extends it and adds its own cost and description on top of the wrapped order. This allows unlimited stacking of add-ons at runtime.

**Integration:** The full flow is — Food Order → Decorator → Calculate Total → Adapter → Payment. The customer builds their order using decorators, the system validates and calculates the total, then passes it to a PaymentProcessor adapter to complete the payment.

---

*2nd Laboratory Activity | Structural Design Patterns | Java*
