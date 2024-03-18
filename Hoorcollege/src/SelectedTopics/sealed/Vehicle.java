package SelectedTopics.sealed;

public sealed class Vehicle permits Car, Truck {

    protected int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
