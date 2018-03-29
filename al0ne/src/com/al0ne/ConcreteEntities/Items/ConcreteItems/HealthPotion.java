package com.al0ne.ConcreteEntities.Items.ConcreteItems;

import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.ConcreteEntities.Items.Types.Potion;
import com.al0ne.ConcreteEntities.Statuses.PrintStatus;

/**
 * Created by BMW on 23/03/2017.
 */
public class HealthPotion extends Potion {
        public HealthPotion() {
            super("healthp", "Health Potion", "A potion for dire moments.");
        }

        @Override
        public boolean used(Player player){
            player.modifyHealth(+20);
            player.addStatus(new PrintStatus("You are healed by the potion"));
            return true;
        }

}
