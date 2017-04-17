package com.al0ne.Entities.Items.ConcreteItems.Food;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Size;
import com.al0ne.Entities.Items.Behaviours.Food;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 17/04/2017.
 */
public class Ration extends Food {
    public Ration() {
        super("ration","Food ration", "Very nourishing", "food ration", 0.5, Size.SMALL, 8);
    }
}