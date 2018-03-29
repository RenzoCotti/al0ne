package com.al0ne.ConcreteEntities.Items.ConcreteItems;

import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.AbstractEntities.Enums.Material;

/**
 * Created by BMW on 02/02/2017.
 */
public class Rope extends Item{
    public Rope() {
        super("rope", "Rope", "11m of sturdy rope.", 1.0, Size.NORMAL, Material.FABRIC, null);
//        addBehaviour("cuttable");
//        addBehaviour("climb");
    }
}
