package com.al0ne.Behaviours.Pairs;
import com.al0ne.Behaviours.Item;

/**
 * Created by BMW on 09/03/2017.
 */
public class PairReactionItem {
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
