package com.al0ne.Entities.Items.ConcreteItems.Food;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Size;
import com.al0ne.Entities.Items.Behaviours.Food;

/**
 * Created by BMW on 06/04/2017.
 */
public class SnakeSteak extends Food{
    public SnakeSteak() {
        super("snakesteak","Snake Steak", "Some meat from a snake. Doesn't look too inviting",
                0.3, Size.NORMAL, 10);
    }
}
