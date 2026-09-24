package decorator;

import food.FoodOrder;

public class ExtraSauce extends FoodOrderDecorator {
    public ExtraSauce(FoodOrder order) { super(order); }
    public String getDescription() { return order.getDescription() + " + Extra Sauce"; }
    public double getCost() { return order.getCost() + 15.0; }
}
