package entities;

public class Order {
    private int userId;
    private String mealName;
    private double quantity;
    private int orderId;

    public Order(int userId, String mealName, double quantity) {
        this.userId = userId;
        this.mealName = mealName;
        this.quantity = quantity;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(){this.userId = userId;}
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getMealName() {
        return mealName;
    }

    public double getQuantity() {
        return quantity;
    }

}