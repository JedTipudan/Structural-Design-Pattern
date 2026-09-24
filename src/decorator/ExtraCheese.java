package decorator;

import food.FoodOrder;

public class ExtraCheese extends FoodOrderDecorator {
    public ExtraCheese(FoodOrder order) { super(order); }
    public String getDescription() { return order.getDescription() + " + Extra Cheese"; }
    public double getCost() { return order.getCost() + 30.0; }
}
