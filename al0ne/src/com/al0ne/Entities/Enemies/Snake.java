package com.al0ne.Entities.Enemies;

import com.al0ne.Entities.Behaviours.Enemy;
import com.al0ne.Entities.Behaviours.Statuses.Poisoned;
import com.al0ne.Items.ConcreteItems.Coin;
import com.al0ne.Items.ConcreteItems.Food.Apple;
import com.al0ne.Items.ConcreteItems.Food.SnakeSteak;

/**
 * Created by BMW on 06/04/2017.
 */
public class Snake extends Enemy{
    public Snake() {
        super("snake", "snake", "a snake", "This snake hisses and looks at you menacingly.");
        addItemLoot(new SnakeSteak(), 1, 80);
        setStats(5, 1, 40, 1, 40);
        addInflictedStatus(new Poisoned(3, 1), 80);
    }
}
