package entities;

public class OrdinaryUser implements UserType {
    @Override
    public double getDiscount() {
        return 0;
    }

    @Override
    public String getGift() {
        return "";
    }
}