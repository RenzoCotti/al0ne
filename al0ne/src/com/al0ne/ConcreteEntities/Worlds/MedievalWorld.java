package com.al0ne.ConcreteEntities.Worlds;

import com.al0ne.AbstractEntities.Area;
import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.AbstractEntities.Enums.TechLevel;
import com.al0ne.AbstractEntities.Pairs.Pair;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.World;
import com.al0ne.ConcreteEntities.Areas.FirstAlphaArea;
import com.al0ne.ConcreteEntities.Areas.TestingArea;
import com.al0ne.ConcreteEntities.Areas.VillageArea;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Coin.MoneyPouch;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.BodyClothing;

public class MedievalWorld extends World {
    public MedievalWorld() {
        super("medieval", TechLevel.HIGH, new Player( "You're a boy, chestnut hair, brown eyes, " +
                "and big dreams for the future. You'd love to become a knight, one day. " +
                "Or maybe a wizard, you haven't decided yet.", true,
                10, 40, 40, 0, 1, 10));


        BodyClothing bc = new BodyClothing("roughclothes", "clothes",
                "Some rough clothes, they look a bit worn", 0.5, Size.NORMAL, Material.FABRIC);
        player.simpleAddItem(bc, 1);
        player.wear(bc);
        player.simpleAddItem(new MoneyPouch(), 1);

        Area alphaArea = new TestingArea(this);
        Area villageArea = new VillageArea(this);
        Area caveWorld = new FirstAlphaArea(this);



        addArea(alphaArea);
        addArea(villageArea);
        addArea(caveWorld);

        player.setStart(alphaArea);

    }

}