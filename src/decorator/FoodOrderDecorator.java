package decorator;

import food.FoodOrder;

public abstract class FoodOrderDecorator implements FoodOrder {
    protected FoodOrder order;
    public FoodOrderDecorator(FoodOrder order) { this.order = order; }
}
