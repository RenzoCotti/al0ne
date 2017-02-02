package com.al0ne.Items.Items;

import com.al0ne.Items.Item;

/**
 * Created by BMW on 02/02/2017.
 */
public class Rope extends Item{
    public Rope() {
        super("rope", "11m of sturdy rope.", 1.0);
        addProperty("cuttable");
        addProperty("climb");
    }
}
