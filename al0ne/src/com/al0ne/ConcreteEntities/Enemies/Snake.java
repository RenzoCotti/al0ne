package com.al0ne.ConcreteEntities.Enemies;

import com.al0ne.AbstractEntities.Abstract.Enemy;
import com.al0ne.ConcreteEntities.Statuses.ConcreteStatuses.Poisoning;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Food.SnakeSteak;

/**
 * Created by BMW on 06/04/2017.
 */
public class Snake extends Enemy{
    public Snake() {
        super("Snake","This snake hisses and looks at you menacingly.", "a snake",
                5, 40, 40, 0, 1);
        addItemLoot(new SnakeSteak(), 1, 80);
        addInflictedStatus(new Poisoning(3, 1), 40);
    }
}
