package decorator;

import food.FoodOrder;

public class LargeDrink extends FoodOrderDecorator {
    public LargeDrink(FoodOrder order) { super(order); }
    public String getDescription() { return order.getDescription() + " + Large Drink"; }
    public double getCost() { return order.getCost() + 40.0; }
}
