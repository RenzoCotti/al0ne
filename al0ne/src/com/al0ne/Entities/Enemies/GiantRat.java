package com.al0ne.Entities.Enemies;

import com.al0ne.Behaviours.Enemy;
import com.al0ne.Entities.Statuses.BlackDeath;

/**
 * Created by BMW on 07/04/2017.
 */
public class GiantRat extends Enemy{
    public GiantRat() {
        super("giantrat", "Giant rat", "A big, disgusting rat. It looks somehow ill.", "a rat");
        setStats(5, 1, 40, 1, 40);
        addInflictedStatus(new BlackDeath(), 5);
    }
}
