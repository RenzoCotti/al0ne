package com.al0ne.ConcreteEntities.Items.ConcreteItems.Coin;

import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.AbstractEntities.Pairs.Pair;
import com.al0ne.ConcreteEntities.Items.Types.Container;

public abstract class MoneyContainer extends Container {

    public MoneyContainer(String id, String name, String description, double weight, Material material) {
        super(id, name, description, weight, Size.SMALL, material, false);
        setUnique();
    }


    @Override
    public boolean putIn(Pair pair, Integer amount) {
        Item item = (Item) pair.getEntity();
        if(hasItem(item)){
            getPair(item).modifyCount(amount);
            pair.modifyCount(-amount);
            currentWeight+=item.getWeight()*amount;
        } else{
            items.add(new Pair(item, amount));
            pair.modifyCount(-amount);
            currentWeight+=item.getWeight()*amount;
        }
        return true;
    }




}
