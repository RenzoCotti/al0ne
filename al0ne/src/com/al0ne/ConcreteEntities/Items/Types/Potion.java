package com.al0ne.ConcreteEntities.Items.Types;

import com.al0ne.AbstractEntities.Enums.Command;
import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.Engine.Physics.Behaviours.ConsumableBehaviour;
import com.al0ne.Engine.Physics.Behaviours.PotionBehaviour;

/**
 * Created by BMW on 02/02/2017.
 */
public abstract class Potion extends Item {
    public Potion(String id, String name, String longDescription) {
        super(id, name, longDescription, 0.3, Size.SMALL, Material.GLASS, null);
        addBehaviour(new PotionBehaviour());
        addBehaviour(new ConsumableBehaviour());
        addCommand(Command.DRINK);
    }

}
