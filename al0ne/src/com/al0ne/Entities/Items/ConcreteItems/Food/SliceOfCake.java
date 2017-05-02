package com.al0ne.Entities.Items.ConcreteItems.Food;

import com.al0ne.Engine.Size;
import com.al0ne.Entities.Items.Behaviours.Food;

/**
 * Created by BMW on 30/04/2017.
 */
public class SliceOfCake extends Food{
    public SliceOfCake() {
        super("sliceofcake","Slice of cake", "Maaan, this apple cake looks just delicious. " +
                "Both still warm AND fluffy? Can't wait to eat some!", 0.1, Size.VSMALL, 2);
    }
}
