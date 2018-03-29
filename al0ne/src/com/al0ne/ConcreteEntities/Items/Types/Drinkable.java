package com.al0ne.ConcreteEntities.Items.Types;

import com.al0ne.AbstractEntities.Enums.Command;
import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.Engine.Physics.Behaviours.FoodBehaviour;
import com.al0ne.Engine.Physics.Behaviours.WaterBehaviour;
import com.al0ne.ConcreteEntities.Statuses.ConcreteStatuses.Thirst;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 23/03/2017.
 */
public class Drinkable extends Item{
    public Drinkable(String name, String description, double weight, Size size) {
        super(name, name, description, weight, size, Material.GLASS, null);
        addCommand(Command.DRINK);
        addBehaviour(new FoodBehaviour());
        addBehaviour(new WaterBehaviour());
    }

    @Override
    public boolean used(Player player) {

        if(player.hasNeeds()) return true;

        if (player.hasStatus("dehydrated")){
            player.removeStatus("dehydrated");
            printToLog("Aaaah, something to drink! Just what you needed.");
        } else{
            Thirst thirst = (Thirst) player.getStatus().get("thirst");
            thirst.setDuration(Thirst.THIRST_CLOCK);
        }
        return true;
    }
}
