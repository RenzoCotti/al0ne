package com.al0ne.ConcreteEntities.Items.ConcreteItems.Food;

import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.ConcreteEntities.Items.Types.Food;

/**
 * Created by BMW on 06/04/2017.
 */
public class SnakeSteak extends Food{
    public SnakeSteak() {
        super("Snake Steak", "Some meat from a snake. Doesn't look too inviting",
                0.3, Size.NORMAL, 10);
    }
}
