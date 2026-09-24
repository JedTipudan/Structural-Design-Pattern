package decorator;

import food.FoodOrder;

public class PriorityDelivery extends FoodOrderDecorator {
    public PriorityDelivery(FoodOrder order) { super(order); }
    public String getDescription() { return order.getDescription() + " + Priority Delivery"; }
    public double getCost() { return order.getCost() + 50.0; }
}
