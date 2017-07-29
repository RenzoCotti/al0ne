package com.al0ne.Entities.Items.ConcreteItems.Food;

import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Entities.Items.Types.Food;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 17/04/2017.
 */
public class Ration extends Food {
    public Ration() {
        super("ration", "Very nourishing", 0.5, Size.SMALL, 8);
    }
}