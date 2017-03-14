package com.al0ne.Items;

/**
 * Created by BMW on 09/03/2017.
 */
public class Pricepair {
    private Item item;
    private int price;

    public Pricepair(Item item, int price) {
        this.item = item;
        this.price = price;
    }

    public Item getItem() {
        return item;
    }

    public int getPrice() {
        return price;
    }

}
