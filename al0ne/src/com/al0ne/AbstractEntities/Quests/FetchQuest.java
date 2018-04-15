package com.al0ne.AbstractEntities.Quests;

import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Pairs.Pair;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Abstract.Entity;

/**
 * Created by BMW on 25/07/2017.
 */
public class FetchQuest extends Quest{

    protected Pair entityRequired;

    public FetchQuest(Entity entity, int amount) {
        super("Get");
        this.entityRequired = new Pair(entity, amount);
        if(amount == 1){
            setQuestName("Get "+entity.getName());
        } else{
            setQuestName("Get "+amount+" "+entity.getName());
        }
    }

    @Override
    public void checkCompletion(Player player) {
        if(player.hasItemInInventory(getEntityRequired().getEntity().getID())){
            Pair pair = player.getItemPair(getEntityRequired().getEntity().getID());
            if(pair.getCount() >= entityRequired.getCount()){
                player.removeAmountItem(pair, entityRequired.getCount());
                questReward(player);
                setCompleted();
            }
        }
    }

    public Pair getEntityRequired() {
        return entityRequired;
    }

    public void setEntityRequired(Pair entityRequired) {
        this.entityRequired = entityRequired;
    }
}
