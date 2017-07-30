package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Entities.Items.Types.Container;
import com.al0ne.Behaviours.Enums.Material;

/**
 * Created by BMW on 12/04/2017.
 */
public class Chest extends Container {
    public Chest() {
        super("woodchest", "Chest", "A fairly large wooden chest.",
                10, Size.VLARGE, Material.WOOD, true);
    }

}
