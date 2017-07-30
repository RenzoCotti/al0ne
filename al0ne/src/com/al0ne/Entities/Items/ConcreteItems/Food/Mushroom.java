package com.al0ne.Entities.Items.ConcreteItems.Food;

import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Entities.Items.Types.Food;
import com.al0ne.Behaviours.Player;

import static com.al0ne.Engine.Main.printToLog;

public class Mushroom extends Food {
    public Mushroom() {
        super("Brown mushroom", "It has a very pungent smell",
                0.2, Size.SMALL, 6);
    }

    @Override
    public String used(Player player){
        player.modifyHealth(-2);
        return "The mushroom is poisonous!";
    }
}
