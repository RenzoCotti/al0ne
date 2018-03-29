package com.al0ne.ConcreteEntities.Items.ConcreteItems.Helmet;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Helmet;

/**
 * Created by BMW on 23/03/2017.
 */
public class IronHelmet extends Helmet{
    public IronHelmet() {
        super("ironhelmet", "Helmet",
                "A helmet with a few dents in it. It has probably seen some action before",
                1.0, 1, Material.IRON);
    }
}
