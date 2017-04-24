package com.al0ne.Entities.Enemies;

import com.al0ne.Behaviours.Enemy;
import com.al0ne.Entities.Statuses.ConcreteStatuses.Poisoned;
import com.al0ne.Entities.Items.ConcreteItems.Food.SnakeSteak;

/**
 * Created by BMW on 06/04/2017.
 */
public class Snake extends Enemy{
    public Snake() {
        super("snake", "Snake","This snake hisses and looks at you menacingly.", "a snake");
        addItemLoot(new SnakeSteak(), 1, 80);
        setStats(5, 1, 40, 0, 40);
        addInflictedStatus(new Poisoned(2, 1), 40);
    }
}
