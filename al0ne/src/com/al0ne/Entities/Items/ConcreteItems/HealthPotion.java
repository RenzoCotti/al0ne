package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Behaviours.Player;
import com.al0ne.Entities.Items.Types.Potion;

/**
 * Created by BMW on 23/03/2017.
 */
public class HealthPotion extends Potion {
        public HealthPotion() {
            super("healthp", "Health Potion", "A potion for dire moments.");
        }

        @Override
        public String used(Player player){
            player.modifyHealth(+20);
            return "The potion heals you!";
        }

}
