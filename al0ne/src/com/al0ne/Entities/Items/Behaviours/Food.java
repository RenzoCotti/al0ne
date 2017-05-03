package com.al0ne.Entities.Items.Behaviours;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Room;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Entities.Statuses.ConcreteStatuses.Hunger;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 11/02/2017.
 */
public abstract class Food extends Item {

    private int foodValue;
    public Food(String id, String name, String description, double weight, Size size, int foodValue) {
        super(id, name, description, weight, size);
        addCommand("eat");
        addProperty("food");
        addProperty("consumable");
        this.foodValue = foodValue;
    }

    @Override
    public int used(Room currentRoom, Player player) {
        if(!player.hasNeeds()){
            //to review
            return 0;
        }
        if (player.hasStatus("starving")){
            player.removeStatus("starving");
            printToLog("Food! Finally!");
        } else{
            Hunger hunger = (Hunger) player.getStatus().get("hunger");
            hunger.modifyDuration(+foodValue*20);
        }
        return 1;
    }
}
