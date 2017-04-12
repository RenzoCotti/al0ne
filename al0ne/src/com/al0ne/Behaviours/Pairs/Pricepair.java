package com.al0ne.Behaviours.Pairs;

import com.al0ne.Behaviours.Item;

import java.io.Serializable;

/**
 * Created by BMW on 09/03/2017.
 */
public class Pricepair implements Serializable{
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
