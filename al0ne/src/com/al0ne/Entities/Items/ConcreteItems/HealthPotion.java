package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Behaviours.Player;
import com.al0ne.Entities.Items.Behaviours.Potion;
import com.al0ne.Behaviours.Room;

/**
 * Created by BMW on 23/03/2017.
 */
public class HealthPotion extends Potion {
        public HealthPotion() {
            super("healthp", "Health Potion", "A potion for dire moments.", "blue potion");
            addProperty("healing");
        }

        @Override
        public int used(Room currentRoom, Player player){
            player.modifyHealth(+20);
            return 1;
        }

}
