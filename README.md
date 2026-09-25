# Food Delivery & Payment Integration System
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

**Run (PowerShell):**
```powershell
$OutputEncoding = [System.Text.Encoding]::UTF8
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
java -cp out Main
```

> These three lines must be run together in PowerShell so the ₱ peso sign displays correctly instead of `?` or `Γé▒`.

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
