package com.al0ne.ConcreteEntities.Items.ConcreteItems.Food;

import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.ConcreteEntities.Items.Types.Food;

/**
 * Created by BMW on 30/04/2017.
 */
public class SliceOfCake extends Food{
    public SliceOfCake() {
        super("Slice of cake", "Maaan, this apple cake looks just delicious. " +
                "Both still warm AND fluffy? Can't wait to eat some!", 0.1, Size.VSMALL, 2);
    }
}
