package com.al0ne.Items.Props;

import com.al0ne.Items.Behaviours.Weapon;
import com.al0ne.Items.Item;
import com.al0ne.Items.Prop;

/**
 * Created by BMW on 09/02/2017.
 */
public class CuttableRope extends Prop{
//    private static int counter=0;
    public CuttableRope() {
        super("rope", "Rope", "A tightened rope holds the Graken still.", "The rope has been cut, and the Graken has disappeared.");
        addType("sharp");
    }

    @Override
    public boolean usedWith(Item item) {
        if(( item.hasProperty("sharp"))){
            System.out.println("You cut the rope, freeing the Graken");
            active = true;
            return true;
        }
        else{
            System.out.println("You get the feeling a sharp item would be more effective.");
            return false;
        }
    }
}
