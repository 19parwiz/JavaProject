package entities;

public class VIPUser implements UserType {
    @Override
    public double getDiscount() {
        return 0.10;
    }

    @Override
    public String getGift() {
        return "Dessert";
    }
}