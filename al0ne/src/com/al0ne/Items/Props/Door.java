package com.al0ne.Items.Props;

import com.al0ne.Items.Item;
import com.al0ne.Items.Prop;

/**
 * Created by BMW on 02/02/2017.
 */
public class Door extends Prop {
    public Door() {
        super("door", "A sturdy wooden door", "An opened wooden door");
    }

    public Door(String id, String name, String description) {
        super(id, name, description);
    }

    @Override
    public boolean usedWith(Item item) {
        return false;
    }
}
