package com.al0ne.Behaviours;

import com.al0ne.Behaviours.Enums.Material;

/**
 * Created by BMW on 08/05/2017.
 */
public class InvisibleProp extends Prop{
    public InvisibleProp(String name, String description, String shortDescription, Material m) {
        super(name, description, shortDescription, m);
        this.invisible=true;
    }

    public InvisibleProp(String name, String description) {
        super(name, description);
        this.invisible=true;
    }
}
