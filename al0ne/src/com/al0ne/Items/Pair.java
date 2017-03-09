package com.al0ne.Items;

/**
 * Created by BMW on 09/03/2017.
 */
public class Pair {
    private Item item;
    private int count;

    public Pair(Item item, int count) {
        this.item = item;
        this.count = count;
    }

    public Item getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }

    public void addCount() {
        count++;
    }

    public void setCount(Integer amount) {
        count=amount;
    }

    public boolean subCount() {
        count--;
        if (count <= 0){
            return true;
        }
        return false;
    }
}
