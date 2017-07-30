package com.al0ne.Entities.Items.Types;

import com.al0ne.Behaviours.Enums.Command;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Room;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Engine.Physics.Behaviours.FoodBehaviour;
import com.al0ne.Entities.Statuses.ConcreteStatuses.Hunger;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 11/02/2017.
 */
public class Food extends Item {

    private int foodValue;
    public Food(String name, String description, double weight, Size size, int foodValue) {
        super(name, name, description, weight, size, null, null);
        addCommand(Command.EAT);
        addBehaviour(new FoodBehaviour());
        this.foodValue = foodValue;
    }

    public int getFoodValue() {
        return foodValue;
    }

    public void setFoodValue(int foodValue) {
        this.foodValue = foodValue;
    }

    @Override
    public int used(Room currentRoom, Player player) {
        if(!player.hasNeeds()){
            printToLog("Yum, tasty!");
            return 1;
        }
        if (player.hasStatus("starving")){
            player.removeStatus("starving");
            printToLog("FoodBehaviour! Finally!");
        } else{
            Hunger hunger = (Hunger) player.getStatus().get("hunger");
            hunger.modifyDuration(+foodValue*20);
        }
        return 1;
    }
}
