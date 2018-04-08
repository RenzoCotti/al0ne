package com.al0ne.ConcreteEntities.Spells;

import com.al0ne.AbstractEntities.Abstract.Entity;
import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Pairs.Pair;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Coin.Coin;
import com.al0ne.Engine.Utility.Utility;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 22/04/2017.
 */
public class MidasSpell extends TargetSpell{
    public MidasSpell() {
        super("midas", "Touch of gold", "This spell turns an item to gold", Item.class);
    }

    @Override
    public boolean isCasted(Player player, Entity entity) {
        if(!(entity instanceof Item)){
            printToLog("You can only target objects.");
            return false;
        } else {
            Item item = (Item) entity;

            if (item.isUnique()){
                printToLog("Your spell doesn't work on that item.");
                return false;
            } else{
                if(player.hasItemInInventory(entity.getID())){
                    if (player.isWearingItem(entity.getID())){
                        player.unequipItem(entity.getID());
                    }
                    if(!player.getInventory().get(item.getID()).modifyCount(-1)){
                        player.getInventory().remove(item.getID());
                    }
                    player.recalculateWeight();

                    int amt = Utility.randomNumber(2*(int) (item.getWeight()*10));

                    player.getCurrentRoom().addEntity(new Coin(), amt);

                    printToLog("You turn the "+item.getName()+" into gold! "+amt+" coins drop on the floor.");
                    return true;

                } else{
                    printToLog("You need to be holding that item.");
                    return false;
                }
            }
        }
    }
}
