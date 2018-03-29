package com.al0ne.AbstractEntities.Pairs;
import com.al0ne.AbstractEntities.Abstract.Item;

import java.io.Serializable;

/**
 * Created by BMW on 09/03/2017.
 */
public class PairReactionItem implements Serializable{
    private String questID;
    private Item reward;

    public PairReactionItem(String questID, Item reward) {
        this.questID = questID;
        this.reward = reward;
    }

    public String getQuestID() {
        return questID;
    }

    public Item getReward() {
        return reward;
    }
}
