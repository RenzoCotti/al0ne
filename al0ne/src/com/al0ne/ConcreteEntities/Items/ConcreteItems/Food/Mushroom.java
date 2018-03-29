package com.al0ne.ConcreteEntities.Items.ConcreteItems.Food;

import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.ConcreteEntities.Items.Types.Food;
import com.al0ne.AbstractEntities.Player.Player;

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
