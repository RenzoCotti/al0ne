package com.al0ne.Entities.Items.ConcreteItems.Helmet;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Utility;
import com.al0ne.Entities.Items.Behaviours.Protective;
import com.al0ne.Entities.Items.Behaviours.Wearable.Helmet;

import static java.lang.Math.max;

/**
 * Created by BMW on 23/03/2017.
 */
public class GreatHelm extends Helmet {
    public GreatHelm(String id, String name, String description,
                     double weight, int armor, Material material) {
        super(id, name, description, weight, armor, 5, Size.SMALL, material);
    }

    public GreatHelm(String id, String name, String description,
                     double weight, int armor, int encumberment, Size s, Material material) {
        super(id, name, description, weight, armor, encumberment, s, material);
    }

    public GreatHelm(Material m) {
        super(Material.stringify(m)+"greathelm", "Great Helm",
                Utility.getArticle(Material.stringify(m))+" "
                        +Material.stringify(m)+" helm which covers the whole head.", Math.max(m.getWeight()-1, 1),
                max(m.getToughness()-1, 1), (int)m.getWeight(), Size.SMALL, m);
    }

    @Override
    public int used(Room currentRoom, Player player) {
        return 0;
    }
}
